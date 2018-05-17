<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
<head>
	<script>
	
	var _gridWidth = 0;
	var _gridHeight = 0; 
	
	//页面自适应
	function resizePageSize(){
		_gridWidth = $(document).width()-12;/*  -189 是去掉左侧 菜单的宽度，   -12 是防止浏览器缩小页面 出现滚动条 恢复页面时  折行的问题 */
		_gridHeight = $(document).height()-32-100; /* -32 顶部主菜单高度，   -90 查询条件高度*/
	}
	
	$(function() {
		
		sdfun.fn.trimAll("editView-div");
		$(window).resize(function(){
			//resizePageSize();
		});
		resizePageSize();

		var code = $( "#codeEdit" );
		var unity = $("#unityEdit");
		var proType = $("#proTypeEdit");
		var isUse = $("#isUseEdit");
		var model = $("#modelEdit");
		var precis = $("#precisEdit");
		var scale = $("#scaleEdit");
		
		var	allFieldsInfo = $( [] ).add( code ).add(unity).add( proType ).add(isUse).add(model).add( precis ).add(scale);
		
		var _columnWidth = (parseInt(_gridWidth)-55) / 3;
		$("#flexiGridId").flexigrid({
			width : _gridWidth,
			height : _gridHeight,
			url: "${pageContext.request.contextPath}/measureUnit/measureUnitList",
			dataType : 'json',
			colModel : [
					{display: 'gid', name : 'gid', width : 50, sortable : false, align: 'center',hide:'true'},
					{display: '<spring:message code="pivas.measureunit.code"/>', name : 'code', width : _columnWidth, sortable : true, align: 'left'},
					{display: '<spring:message code="pivas.measureunit.unity"/>', name : 'unityName', width : _columnWidth, sortable : false, align: 'left'},
					{display: '<spring:message code="pivas.measureunit.protype"/>', name : 'proTypeName', width : _columnWidth, sortable : false, align: 'left'}
			],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			showcheckbox : true, //是否显示多选框
			rowbinddata : true,
			numCheckBoxTitle : "<spring:message code='common.selectall'/>"
		});
		qryList();
		
		$("#aSearch").bind("click",function(){
			qryList();
		});
		
		$("#editView-div").dialog({
			autoOpen: false,
			height: 360,
			width: 500,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var bValid = validateInput("editView-div") == null?true:false;
					if (!bValid ) {
						return ;
					}
					var url = "${pageContext.request.contextPath}/measureUnit/addMeasureUnit";
					var params = {"code":$("#codeEdit").val(),"unity":$("#unityEdit").val(),"proType":$("#proTypeEdit").val(),"isUse":$("#isUseEdit").val(),"model":$("#modelEdit").val(),"precis":$("#precisEdit").val(),"scale":$("#scaleEdit").val()};
					if($("#gid").val() && $("#gid").val()!=""){
						url = "${pageContext.request.contextPath}/measureUnit/updateMeasureUnit";
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
			            	qryList();
					    	message({
				    			data: data
			            	});
						},
						error : function() {}
					});
				},
				"<spring:message code='common.cancel'/>": function() {
					allFieldsInfo.val("").removeClass("ui-state-error");
					$("#editView-div").dialog("close");
				}
			},
			close: function() {
				resetForm("editView-div");
				allFieldsInfo.val("").removeClass("ui-state-error");
			}
		});
		
		//新增按钮
		$( "#addDataDicBtn").bind("click",function(){
			$("#modelEdit").combobox("reset",'0');
			$("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.measureunit.add'/>");
			$("#editView-div").dialog("open");
		});
		
		//编辑按钮
		$( "#updateDataDicBtn").bind("click",function(){
			var ids = getFlexiGridSelectedRowText($("#flexiGridId"), 2);
			if (ids && ids.length === 1) {
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/measureUnit/initUpdateMeasureUnit',
					dataType : 'json',
					cache : false,
					data : [ {name : 'gid',value : ids} ],
					success : function(data) {
						if(data.success == false){
		            		$(".pReload").click();
		            		message({
		    	    			data: data
		                	});
		                	return;
		            	}
						else{
							 $("#gid").val(data.gid);
							 $("#codeEdit" ).val(data.code);
							 $("#unityEdit").combobox("reset",data.unity);
							 $("#proTypeEdit").combobox("reset",data.proType);
							 $("#isUseEdit").combobox("reset",data.isUse);
							 $("#modelEdit").combobox("reset",data.model);
							 $("#precisEdit").val(data.precis);
							 $("#scaleEdit").val(data.scale);
		            	}
						$("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.measureunit.update'/>");
						$("#editView-div").dialog("open");
					},
					error : function() {}
				});
			} else {
				message({html: '<spring:message code="common.plzSelectOneRecourd"/>',showConfirm: true});
			}
		});
		
		$("#deleteDataDicBtn").bind("click",function(){
			var ids = getFlexiGridSelectedRowText($("#flexiGridId"), 2);
			if (ids && ids.length >0) {
				message({
	    			html: '<spring:message code="common.deleteConfirm"/>',
	    			showCancel:true,
	    			confirm:function(){
						$.ajax({
							type : 'POST', 
				            url : '${pageContext.request.contextPath}/measureUnit/delMeasureUnit',   
				            dataType : 'json',  
				            cache : false,
				            data : [{name : 'gid', value : ids}],
				            success : function(data) {
			            		qryList();
						    	message({
					    			data: data
				            	});
				            },
				            error : function () {
				            	message({
				            		html: '<spring:message code="common.op.error"/>',
				            		showConfirm: true
				            	});
					        }
						});
	    			}
            	});
			} else {
				message({html: '<spring:message code="common.plzSelectOneRecourd"/>',showConfirm: true});
			}
		});
		
	});
	function qryList(param){
		$('#flexiGridId').flexOptions({
			newp: 1,
			extParam: param||[],
        	url: "${pageContext.request.contextPath}/measureUnit/measureUnitList"
        }).flexReload();
	}
	function closeDeal(){
		$("#editView-div").find("input").each(function(){
			$(this).val("");
		});
		$("#editView-div").find("textarea").each(function(){
			$(this).val("");
		});
	}
	</script>
</head>
<body>

<div class="main-div" style="width:100%">
		<!-- 搜索条件--开始 -->
		<div class="oe_searchview">
     		<div class="oe_searchview_facets" >
	    		<div class="oe_searchview_input oe_searchview_head"></div>
	    <div class="oe_searchview_input"  id="inputsearch" >
	    	  <input id="txt" type="text" class="oe_search_txt" onkeydown="this.onkeyup();" onkeyup="this.size=(this.value.length>1?this.value.length:1);" size="1"/>
	    </div>
    		</div>
   			<div class="oe_searchview_clear" onclick="clearclosedinputall();"></div>
    			<button class="oe_searchview_search"  type="button" id="searchbtn">搜索</button>
				<div class="oe-autocomplete" ></div>
				<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
					<cite>请选择...</cite>
					<ul class="ulQry" style="-webkit-border-radius: 20;" funname="qryList" >
						<li show="产品类别" name="proTypes" >搜索 产品类别：<span class="searchVal"></span></li>
						<li show="计量单位" name="unityNames" >搜索 计量单位：<span class="searchVal"></span></li>
					</ul>
				</div>
		</div>
		<!-- 搜索条件--结束 -->
		
        <div id="qryView-div">
    	<div class="search-div">
            <shiro:hasPermission name="PIVAS_BTN_265"><a class="button" id="addDataDicBtn"><spring:message code='common.add'/></a></shiro:hasPermission>
	        <ul class="pre-more" tabindex='-1' >
	        		<li class="liBtH">
						<a class="button2">更多</a>
                     </li>
                     <li class="liBtN" style="display: none;">
						<shiro:hasPermission name="PIVAS_BTN_263"><a class="button2" id="updateDataDicBtn"><spring:message code='common.edit'/></a></shiro:hasPermission>
                     </li>
                     <li class="liBtN" style="display: none;">
						<shiro:hasPermission name="PIVAS_BTN_266"><a class="button2" id="deleteDataDicBtn"><spring:message code='common.del'/></a></shiro:hasPermission>
                     </li>
             </ul> 
        </div>
        <div class="tbl">
            <table id="flexiGridId" class="table-data" style="display: block;margin: 0px;"></table>
        </div>
        </div>
        

</div>
		<%-- 新增编辑弹出框 --%>
		<div id="editView-div" title="<spring:message code='pivas.measureunit.update'/>" align="center" style="display: none;">
			<form id="editView-form" action="" method="post">
				<input type="hidden" id="gid" name="gid"/>
					<div class="popup">
						<div class="row">
							<div class="column">
								<label class="tit"><spring:message code='pivas.measureunit.unity'/></label>
								<sd:select id="unityEdit" width="185px"  required="true" categoryName="pivas.measureunit.unity" tableName="sys_dict" />
				            	<span class="mand">*</span>
							</div>
							<div class="column">
								<label class="tit"><spring:message code='pivas.measureunit.code'/></label>
								<input type="text" class="edit" name="code" empty="false" id="codeEdit" maxlength="32" empty="false" title="<spring:message code='common.op.remind2'/>"/>
				            	<span class="mand">*</span>
							</div>
						</div>
						
						<div class="row">
							<div class="column">
								<label class="tit"><spring:message code='pivas.measureunit.protype'/></label>
								<sd:select id="proTypeEdit" name="proType" required="true" categoryName="pivas.measureunit.protype" tableName="sys_dict" /></td>
				            	<span class="mand">*</span>
							</div>
							<div class="column">
								<label class="tit"><spring:message code='pivas.measureunit.isuse'/></label>
								<sd:select id="isUseEdit" name="isUse" required="true" categoryName="pivas.common.yesorno" tableName="sys_dict" /></td>
								<span class="mand">*</span>
							</div>
						</div>
						
						<div class="row">
							<div class="column">
								<label class="tit"><spring:message code='pivas.measureunit.model'/></label>
								<sd:select id="modelEdit" width="185px" required="true"  categoryName="pivas.measureunit.model" tableName="sys_dict" />
								<span class="mand">*</span>
							</div>
							<div class="column">
								<label class="tit"><spring:message code='pivas.measureunit.precis'/></label>
								<input type="text" class="edit" name="precis" id="precisEdit" maxlength="32" title="<spring:message code='common.op.remind2'/>"/>
				            </div>
							
							<div class="column">
								<label class="tit"><spring:message code='pivas.measureunit.proportion'/></label>
								<input type="text" class="edit" name="scale" id="scaleEdit" maxlength="32" title="<spring:message code='common.op.remind2'/>"/>
				            </div>
						</div>
					</div>
			</form>
		</div>

</body>

</html>