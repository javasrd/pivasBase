<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
<head>
	<style type="text/css">
		.medicSearchInput{
			width:100px;
			border:1px solid #9E9E9E;
			margin-right: 10px;
		}
		.search-div {
			padding: 14px 1em 1em 0em;
		}
	</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/pivas/js/strings.js"></script>
	<script>
	var drugDamageProblemObj={};
	var drugDamageLinkObj={};
	
	var ajax_params = {
    }
	var datatable;
	function initDatatable() {
        datatable = $('.datatable').DataTable({
            "dom": '<"toolbar">frtip',
            "searching": false,
            "processing": true,
            "serverSide": true,
            "select": true,
            "ordering": false,
            "pageLength": 20,
            "language": {
                "url": "${pageContext.request.contextPath}/assets/datatables/cn.json"
            },
            "preDrawCallback": function () {
                //sublime.showLoadingbar($(".main-content"));
            },
            "drawCallback": function () {
                //sublime.closeLoadingbar($(".main-content"));
            },
            "ajax": {
                "url": "${pageContext.request.contextPath}/dgDmg/getAllDmg",
                "type": "post",
                "data": function (d) {
                    d.rp = d.length;
                    d.page = d.start / d.length + 1;
                    
                    $.each(ajax_params, function(k, v) {
                    	if (v) {
	                        d[k] = v;
                    	}
                    });
                },
                "dataSrc": function (json) {
                	json.data = json.rawRecords;
                    //data.draw = 1;
                    json.recordsFiltered = json.total;
                    json.recordsTotal = json.total;
                    return json.data;
                }
            },
            "columns": [
	             {"data": "gid", "bSortable": false},
                {"data": "drugCode", "bSortable": false, "defaultContent":""},
                {"data": "drugName", "bSortable": false, "defaultContent":""},
                {"data": "specifications", "bSortable": false, "defaultContent":""},
                {"data": "drugPlace", "bSortable": false, "defaultContent":""},
                {"data": "damageProblem", "bSortable": false, "defaultContent":""},
                {"data": "damageLink", "bSortable": false, "defaultContent":""},
                {"data": "quantity", "bSortable": false, "defaultContent":""},
                {"data": "registName", "bSortable": false, "defaultContent":""},
                {"data": "registTime", "bSortable": false, "defaultContent":""},
            ],
            columnDefs: [
            	 {
                    //   指定第一列，从0开始，0表示第一列，1表示第二列……
                    targets: 0,
                    render: function(data, type, row, meta) {
                        return '<input type="checkbox" style="margin-top:3px;" class="itemchk" name="checklist" value="' + data + '" />';
                    }
                }, 
                {
					targets: 10,
					render: function (data, type, row) {
						var str = "";
                     	<shiro:hasPermission name="PIVAS_BTN_126">
                     		str +=  '&nbsp;<a class="button btn-bg-green" href="javascript:;" onclick="editRecord({0});">修改</a>'.format([row.gid])
                		</shiro:hasPermission>
                        return str;
					}
            	},
            ],
        });
    }
	
	function editRecord(id) {
		var ids = [id];
		if (ids && ids.length === 1) {
			$.ajax({
				type : 'POST',
				url : "${pageContext.request.contextPath}/dgDmg/dispDgDmg",
				dataType : 'json',
				cache : false,
				data : [ {name : 'gid',value : ids} ],
				success : function(data) {
					if(data.success == false){
	            		$(".pReload").click();
	            		layer.alert( data.msg, {'title': '', icon: 1});
	            		
	                	return;
	            	}
					else{
						$("#gid").val(data.gid);
						$("#drugCode").val(data.drugCode);
						$("#drugCode").attr("disabled","disabled");
						$("#drugName").val(data.drugName);
						$("#drugName").attr("disabled","disabled");
						$("#specifications").val(data.specifications); 
						$("#specifications").attr("disabled","disabled");
						$("#drugPlace").val(data.drugPlace); 
						$("#drugPlace").attr("disabled","disabled");
						$("#damageProblem").val(data.damageProblem); 
						$("#damageLink").val(data.damageLink); 
						$("#quantity").val(data.quantity); 
						$("#registName").val(data.registName); 
	            	}
					$("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.druglabel.update'/>");
					$("#editView-div").dialog("open");
				},
				error : function() {}
			});
		} else {
			layer.alert( '<spring:message code="common.plzSelectOneRecourd"/>', {'title': '', icon: 0});
		}
	}
	
	function chkAll(self) {
		$('input[name="checklist"]').each(function(i){
		     this.checked = $(self).attr("checked");
		 });
	}
	
	$(function() {
		initDatatable();
		sdfun.fn.trimAll("editView-div");
		$("#damageProblem option").each(function(i,v){
			drugDamageProblemObj[$(this).val()]=$(this).text();
		});
		
		$("#damageLink option").each(function(i,v){
			drugDamageLinkObj[$(this).val()]=$(this).text();
		});
		
		$("#aSearch").bind("click",function(){
			qry();
		});
		
		$("#editView-div").dialog({
			autoOpen: false,
			height: 300,
			width: 650,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					if($("#drugName").val()==""){
						layer.alert("药品名称不能为空", {'title': '', icon: 0});
						return ;
					}
					var quantity =$("#quantity").val();
					if(quantity.length ==0 ){
						layer.alert("数量不能为空", {'title': '', icon: 0});
						return ;
					}else if(isNaN(quantity)){
						layer.alert("请输入有效的数字", {'title': '', icon: 0});
						return ;
					}else if(parseFloat(quantity) <= 0 ){
						layer.alert("请输入正数", {'title': '', icon: 0});
						return ;
					}else if(parseFloat(quantity)%1 !==0 ){
						layer.alert("请输入整数", {'title': '', icon: 0});
						return ;
					}
					
					if($("#registName").val()==""){
						layer.alert("登记人不能为空", {'title': '', icon: 0});
						return ;
					}
					
					var url = "${pageContext.request.contextPath}/dgDmg/addDgDmg";
					var params = {
							"drugCode":$("#drugCode").val(),
							"drugName":$("#drugName").val(),
							"specifications":$("#specifications").val(),
							"drugPlace":$("#drugPlace").val(),
							"damageProblem":$("#damageProblem").val(),
							"damageLink":$("#damageLink").val(),
							"quantity":parseFloat($("#quantity").val()),
							"registName":$("#registName").val()
						};
					if($("#gid").val() && $("#gid").val()!=""){
						url = "${pageContext.request.contextPath}/dgDmg/updDgDmg";
						params["gid"]=$("#gid").val() ;
					}
					$.ajax({
						type : 'POST',
						url : url,
						dataType : 'json',
						cache : false,
						data : params,
						success : function(data) {
			            	if(data.success == true || data.code == '<%=ExceptionCodeConstants.RECORD_DELETE%>') {
			            		$("#editView-div").dialog("close");
					    	}
			            	qry();
					    	/* message({
				    			data: data
			            	}); */
					    	layer.alert( data.msg, {'title': '', icon: 1});
						},
						error : function() {}
					});
				},
				"<spring:message code='common.cancel'/>": function() {
					$("#editView-div").dialog("close");
				}
			},
			close: function() {
					$("#gid").val("");
					$("#drugCode").val("");
					$("#drugCode").removeAttr("disabled");
					$("#drugName").val("");
					$("#drugName").removeAttr("disabled");
					$("#specifications").val("");
					$("#specifications").removeAttr("disabled");
					$("#drugPlace").val("");
					$("#drugPlace").removeAttr("disabled");
					$("#damageProblem").val("1");
					$("#damageLink").val("1");
					$("#quantity").val("");
					$("#registName").val("");
			}
		});
		
		$("#medicl-div").dialog({
			autoOpen: false,
			height: 450,
			width: 900,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var ids = getFlexiGridSelectedRowText($("#labelFlexGrid"), 2);
					var names = getFlexiGridSelectedRowText($("#labelFlexGrid"), 3);
					var specifications = getFlexiGridSelectedRowText($("#labelFlexGrid"), 4);
					var drugPlace = getFlexiGridSelectedRowText($("#labelFlexGrid"), 5);
					$("#editView-form input[name='drugCode']").val(ids);
					$("#editView-form input[name='drugName']").val(names);
					$("#editView-form input[name='specifications']").val(specifications);
					$("#editView-form input[name='drugPlace']").val(drugPlace);
					$(this).dialog("close");
				},
				"<spring:message code='common.cancel'/>": function() {
					$(this).dialog("close");
				}
			},
			open:function(){
				loadLabelGrid();
			},
			close: function() {
			}
		});
		
		$("#labelFlexGrid").flexigrid({
			width : 740,
			height : 240,
			dataType : 'json',
			colModel : [ 
				{display: '药品编码', name : 'medicamentsCode', width : 80},
				{display: '药品名称', name : 'medicamentsName', width : 320},
				{display: '药品规格', name : 'medicamentsSpecifications', width : 120},
				{display: '药品产地', name : 'medicamentsPlace', width : 120}
			],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : false, //是否分页
			autoload : true, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			showcheckbox : true, //是否显示多选框
			rowbinddata : true,
			numCheckBoxTitle : "<spring:message code='common.selectall'/>"
		});
		
		
		$("#drugName").click(function(){
			$("#medicl-div").dialog("open");
		});
		
		//新增按钮
		$( "#addDataDicRoleBtn").bind("click",function(){
			$("#editView-div").prev().find('.ui-dialog-title').text("新增药品破损登记");
			$("#editView-div").dialog("open");
		});
		
		function getCheckedId() {
			var arr = new Array(0); 
			$('input[name="checklist"]:checked').each(function(i){
				arr.push(this.value);  
			 });
			
			return arr;
		}
		
		
		$("#mecicSearchBtn").bind("click",function(){
			loadLabelGrid()
		});
		
		$("#deleteDataDicRoleBtn").bind("click",function() {
			var ids = getCheckedId();
			if (ids && ids.length >0) {
				message({
	    			html: '<spring:message code="common.deleteConfirm"/>',
	    			showCancel:true,
	    			confirm:function(){
						$.ajax({
							type : 'POST', 
				            url : "${pageContext.request.contextPath}/dgDmg/delDgDmg",   
				            dataType : 'json',  
				            cache : false,
				            data : [{name : 'gids', value : ids}],
				            success : function(data) {
			            		qry();
						    	layer.alert( data.msg, {'title': '', icon: 1});
				            },
				            error : function () {
				            	layer.alert(  '<spring:message code="common.op.error"/>', {'title': '', icon: 0});
					        }
						});
	    			}
            	});
			} else {
				layer.alert('<spring:message code="common.plzSelectOneRecourd"/>', {'title': '', icon: 0});
			}
		});
	});
	
	function loadLabelGrid(){
		var querParam=[];
		querParam.push({"name":"medicamentsCode","value":$("#medicamentsCode").val()});
		querParam.push({"name":"medicamentsName","value":$("#medicamentsName").val()});
		querParam.push({"name":"medicamentsPlace","value":$("#medicamentsPlace").val()});
		var categoryId=$("#medicamentCateDIV input:checked").val();
		if(categoryId != "-1"){
			querParam.push({"name":"categoryId","value":categoryId});
		}
		$('#labelFlexGrid').flexOptions({
			newp: 1,
            extParam : querParam,
			url: "${pageContext.request.contextPath}/dgDmg/qryDgs"
        }).flexReload();
	}
	
	function qry() {
		$("#tbSearch input:text").each(function() {
			if ($(this).attr("name")) {
				ajax_params[$(this).attr("name")] = $(this).val();
			}
		});
		
		datatable.ajax.reload();
	}
	
	function medicamentTypeChanged(obj){
		$("#medicamentCateDIV input[type='checkbox']").each(function(checkIndex){
			$(this).removeAttr("checked");
		});
		$(obj).attr("checked", true);
		loadLabelGrid();
	}
	</script>
</head>
<body>
<div class="main-div" style="width:100%">
<!-- 搜索条件--开始 -->
	<div data-qryMethod funname="qryList"  class="ui-search-header ui-search-box" id="tbSearch" style="display: inline; ">
		<div style="float: right">
		<input placeholder="药品编码" name="drugCode" size="8" data-cnd="true">&nbsp;&nbsp;
		<input placeholder="药品名称" name="drugName" size="8" data-cnd="true">&nbsp;&nbsp;
		<input placeholder="药品产地" name="drugPlace" size="8" data-cnd="true">&nbsp;&nbsp;
		<button class="button ui-search-btn ui-btn-bg-green" onclick="qry()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
		<button  class="button ui-search-btn ui-btn-bg-yellow"  onclick='$("#tbSearch input:text").val("");'><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
		</div>
		<shiro:hasPermission name="PIVAS_BTN_948"><button class="button ui-search-btn ui-btn-bg-green" id="addDataDicRoleBtn"><i class="am-icon-plus"></i><span><spring:message code='common.add'/></span></button></shiro:hasPermission>
		<%--
		 <shiro:hasPermission name="PIVAS_BTN_946"><button class="button ui-search-btn ui-btn-bg-green" id="updateDataDicRoleBtn"><spring:message code='common.edit'/></button></shiro:hasPermission>
		 --%>
		<shiro:hasPermission name="PIVAS_BTN_947"><button class="button ui-search-btn ui-btn-bg-green ui-btn-bg-red" id="deleteDataDicRoleBtn"><i class="am-icon-trash"></i><span><spring:message code='common.del'/></span></button></shiro:hasPermission>
	</div>
	<table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
		<thead>
		<tr>
			<th>
				<input style="margin-top:2px;" type="checkbox" onclick="chkAll(this);" title="全选" class="noborder">						
			</th>
			<th>药品编码</th>
			<th>药品名称</th>
			<th>药品规格</th>
			<th>药品产地</th>
			<th>质量问题</th>
			<th>破损环节</th>
			<th>数量</th>
			<th>登记人</th>
			<th>登记时间</th>
			<th>操作</th>
		</tr>
		</thead>
	</table>
<%--<div class="oe_searchview">
      <div class="oe_searchview_facets" >
	    <div class="oe_searchview_input oe_searchview_head"></div>
	    <div class="oe_searchview_input"  id="inputsearch" >
	    	  <input id="txt" type="text" style="width:280px;" class="oe_search_txt" onkeydown="this.onkeyup();" onkeyup="this.size=(this.value.length>1?this.value.length:1);" size="1"/>
	    </div>
    </div>
    <div class="oe_searchview_clear" onclick="clearclosedinputall();"></div>
    	<button class="oe_searchview_search"  type="button" id="searchbtn">搜索</button>
	<div class="oe-autocomplete" ></div>
	<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
		<cite>请选择...</cite>
		<ul class="ulQry" style="-webkit-border-radius: 20;" funname="qryList" >
			<li show="药品编码" name="drugCode" >搜索 药品编码：<span class="searchVal"></span></li>
			<li show="药品名称" name="drugName" >搜索 药品名称：<span class="searchVal"></span></li>
			<li show="药品产地" name="drugPlace" >搜索 药品产地：<span class="searchVal"></span></li>
		</ul>
	</div>
</div>--%>
<!-- 搜索条件--结束 -->

   <%-- <div id="qryView-div">
        <div class="search-div">
    		<div class="oe_view_manager_view_search"></div>
	            <shiro:hasPermission name="PIVAS_BTN_948"><a class="button" id="addDataDicRoleBtn"><spring:message code='common.add'/></a></shiro:hasPermission>
		       	<shiro:hasPermission name="PIVAS_BTN_946"><a class="button" id="updateDataDicRoleBtn"><spring:message code='common.edit'/></a></shiro:hasPermission>
		        <shiro:hasPermission name="PIVAS_BTN_947"><a class="button ui-btn-bg-red" id="deleteDataDicRoleBtn"><spring:message code='common.del'/></a></shiro:hasPermission>
        	</div>
        </div>
        <div class="tbl">
            <table id="flexGridRole" style="display: block;margin: 0px;"></table>
        </div>

	</div>--%>
        
<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="<spring:message code='pivas.druglabel.update'/>" align="center" style="display: none;">
	<form id="editView-form" action="" method="post">
		<input type="hidden" id="gid" name="gid"/>
		<div class="popup">
			<div class="row">
				<div class="column">   
					<div class="column columnL">
						<label class="tit">药品名称</label>
						<input id="drugName" type="text" name="drugName" readonly="readonly" /><span class="mand">*</span>
					</div>
					<div class="column columnR">
						<label class="tit">药品编码</label>
						<input id="drugCode" type="text" name="drugCode" readonly="readonly" /><span class="mand">*</span>
					</div>
				</div>
				<div class="column">
					<div class="column columnL">
						<label class="tit">药品规格</label>
						<input id="specifications" type="text" name="specifications" readonly="readonly" />
					</div>
					<div class="column columnR">
						<label class="tit">药品产地</label>
						<input id="drugPlace" type="text" name="drugPlace" readonly="readonly" />
					</div>
				</div>
				<div class="column">
					<div class="column columnL">
						<label class="tit">质量问题</label>
						<select id="damageProblem" name="damageProblem">
							<c:forEach items="${damageProblem}" var="damageProblemObj">
								<option value="${damageProblemObj.code}">${damageProblemObj.description}</option>
							</c:forEach>
						</select>
						<span class="mand">*</span>
					</div>
					<div class="column columnR">
						<label class="tit">破损环节</label>
						<select id="damageLink" name="damageLink">
							<c:forEach items="${damageLink}" var="damageLinkObj">
								<option value="${damageLinkObj.code}">${damageLinkObj.description}</option>
							</c:forEach>
						</select>
						<span class="mand">*</span>
					</div>
				</div>
				<div class="column">
					<div class="column columnL">
						<label class="tit">数量</label>
						<input id="quantity" type="text" name="quantity" regex="number" maxlength="32" title="请输入正整数" />
						<span class="mand">*</span>
					</div>
					<div class="column columnR">
						<label class="tit">登记人</label>
						<select  id ="registName" name="registName"  readonly>
							<option value=""><spring:message code="common.select" /></option>
							<c:forEach items="${allUsers}" var="user">
								<option value="${user.account}">
									<spring:escapeBody htmlEscape="true">${user.account}</spring:escapeBody>
								</option>
							</c:forEach>
						</select>
						<span class="mand">*</span>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>

<div id="medicl-div" title="<spring:message code="common.choose.drug"/>" style="display: none;">
	<div style="width:15%;height:100%;float:left;padding:15px 10px">
		<p style="text-align: left;margin:0px 0px;margin-bottom:10px;font-size: 16px;font-family: 微软雅黑;">药品分类</p>
		<div id="medicamentCateDIV" style="text-align: left;width:100%">
			<div>
				<input type="checkbox" style="vertical-align: middle" onchange="medicamentTypeChanged(this)" value="-1" checked />
					<label>全部</label>
			</div>
			<c:forEach items="${categorys}" var="category">
				<div>
					<input type="checkbox" style="vertical-align: middle" onchange="medicamentTypeChanged(this)" value="${category.categoryId}" />
						<label>${category.categoryName}</label>
				</div>
			</c:forEach>
		</div>
	</div>
	<div style="width:85%;float:right; ">
		<!-- 搜索条件--开始 -->
		<div style="height:50px;line-height:50px;">
			<div >
				<table>
					<tr>
						<td>药品编码：</td>
						<td>
							<input id="medicamentsCode" class="medicSearchInput" type="text" >
						</td>
							<td>药品名称：</td>
						<td>
							<input id="medicamentsName" class="medicSearchInput" type="text" >
						</td>
							<td>药品产地：</td>
						<td>
							<input id="medicamentsPlace" class="medicSearchInput" type="text" >
						</td>
						<td>
							<a class="button" id="mecicSearchBtn">搜索</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 搜索条件--结束 -->
		<div style="width:100%;overflow-x:auto ">
			<div class="tbl">
				<table id="labelFlexGrid" style="display: block;margin: 0px;"></table>
			</div>
		</div>
	</div>
</div>

</body>

</html>