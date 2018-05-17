<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page
	import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp"%>

<html>

<head>
<link
	href="${pageContext.request.contextPath}/assets/sysCss/common/style.css"
	type="text/css" rel="stylesheet" />
<script>
	var power = 0;
	
	function setOpenDrugTime()
	{
		var dateFmt = 'yyyy-MM-dd';
		var currentDate = getCurrentDate(dateFmt, null, 0);
		$('#openConfirmDate').val(currentDate);
	}
	
	function getCheckedId() {
		var arr = new Array(0); 
		$('input[name="checklist"]:checked').each(function(i){
			arr.push(this.value);  
		 });
		
		return arr;
	}
	
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
                "url": "${pageContext.request.contextPath}/mdictStk/dgOpenAmtList",
                "type": "post",
                "data": function (d) {
                    d.rp = d.length;
                    d.page = d.start / d.length + 1;
                    
                    d.openConfirmDate = $("#openConfirmDate").val();
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
	            {"data": "id", "bSortable": false},
                {"data": "name", "bSortable": false, "defaultContent":""},
                {"data": "code", "bSortable": false, "defaultContent":""},
                {"data": "place", "bSortable": false, "defaultContent":""},
                {"data": "amountPlan", "bSortable": false, "defaultContent":""},
                {"data": "amount", "bSortable": false, "defaultContent":""},
                {"data": "operator", "bSortable": false, "defaultContent":""},
            ],
            columnDefs: [
            	 {
                    //   指定第一列，从0开始，0表示第一列，1表示第二列……
                    targets: 0,
                    render: function(data, type, row, meta) {
                        return '<input type="checkbox" style="margin-top:3px;" class="itemchk" name="checklist" value="' + data + '" />';
                    }
                }, 
            ],
        });
    }
	
	function chkAll(self) {
		$('input[name="checklist"]').each(function(i){
		     this.checked = $(self).attr("checked");
		 });
	}
	
	function qry() {
		$("#searchDiv input:text").each(function() {
			if ($(this).attr("name")) {
				ajax_params[$(this).attr("name")] = $(this).val();
			}
		});
		
		datatable.ajax.reload();
	}
	
	$(function() {
		initDatatable();
		
		// 设置默认统计时间
	    setOpenDrugTime();
		
		
		$("#aSearch").bind("click",function(){
			qry();
		});
		
		
		$("#openDrugCheck").bind("click",function(){
			message({
					html: '检查库存拆药量？',
	    			showCancel:true,
	    			confirm:function(){
						$.ajax({
							type : 'POST', 
							url: '${pageContext.request.contextPath}/mdictStk/chkOpenDg',   
				            dataType : 'json',  
				            cache : false,
				            success : function(data) {
				            	message({
				            		html: data.msg,
					            	showConfirm: true
				            	});
				            	qry();
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
		});
		
		$("#confirmAdmin").bind("click",function(){
			message({
					html: '保存检索出的药品拆药量?',
	    			showCancel:true,
	    			confirm:function(){
						$.ajax({
							type : 'POST', 
							url: '${pageContext.request.contextPath}/mdictStk/dgOpenCfmAdmin',   
				            dataType : 'json',  
				            cache : false,
				            success : function(data) {
				            	message({
				            		html: data.msg,
					            	showConfirm: true
				            	});
				            	qry();
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
		});
		
		$("#confirm").bind("click",function(){
			var ids = getCheckedId();
			if (ids.length > 0) {
				message({
	    			html: '是否确认拆药人拆药量？',
	    			showCancel:true,
	    			confirm:function(){
						$.ajax({
							type : 'POST', 
				            url : '${pageContext.request.contextPath}/mdictStk/drgOpenCfm', 
				            dataType : 'json',  
				            cache : false,
				            data : [{name : 'ids', value : ids}],
				            success : function(data) {
				            	qry();
						    	message({
					    			data: data
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

	
	
	function selectDrugOpenData(){
		qry();
	}
	
	function changeAmount(obj){
		var id = $(obj).attr("openAmountID");
		var textdivid = 'divTextOpenAmount_' + id;
		var labelid = 'labelOpenAmount_' + id;
		$("#"+textdivid).show();
		$("#"+labelid).hide();
	}
	
	
	function toConfirm(obj){
		var id = $(obj).attr("openAmountID");
		var textid = 'textOpenAmount_' + id;
		var textdivid = 'divTextOpenAmount_' + id;
		var labelid = 'labelOpenAmount_' + id;
		var amount = $('#' + textid).val();
		
	
		var bValid = true;
		
		bValid = bValid && checkEmpty($('#' + textid));
		if (!bValid ) {
			message({
				html : '该选项必填！',
				showConfirm : true
			});
			return;
		}
		
		bValid = bValid && isInt($('#' + textid));
		if (!bValid ) {
			message({
				html : '拆药量必须为非负整数！',
				showConfirm : true
			});
			return;
		}
		
		$.ajax({
			type : 'POST', 
			url: '${pageContext.request.contextPath}/mdictStk/updOpenAmt',   
            dataType : 'json',  
            cache : false,
            data : [{name : 'id', value : id},
                    {name : 'amount', value : amount}
            ],
            success : function(data) {
            	$("#"+labelid).html(amount).show();
            	$("#"+textid).html(amount);
            	$("#"+textdivid).hide();
            },
            error : function () {
            	message({
            		html: '<spring:message code="common.op.error"/>',
            		showConfirm: true
            	});
	        }
		});
	}
	
	function toCancel(obj){
		var id = $(obj).attr("openAmountID");
		var textid = 'textOpenAmount_' + id;
		var textdivid = 'divTextOpenAmount_' + id;
		var labelid = 'labelOpenAmount_' + id;
		
		$("#"+textdivid).hide();
		$("#"+labelid).show();
	}
	
	
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/jquery/ajaxfileupload.js"></script>

</head>
<body>
<div id="searchDiv">
	<div style="height: 32px;float: left;margin-top: 10px;z-index: 1000000;">
	<table>
	<tr>
		<td>拆药日期：</td>
		<td><input type="text" id="openConfirmDate" class="Wdate" name="openConfirmDate" style="height: 25px;color: #555555;"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"  onchange="selectDrugOpenData();"/> 
		</td>
		</tr>
	</table>
	</div>
	&nbsp;&nbsp;
	<div data-qryMethod class="ui-search-header ui-search-box" id="tbSearch" style="display: inline; ">
		<div style="float: right">
		<input placeholder="药品名称" name="names" size="8" data-cnd="true">&nbsp;&nbsp;
		<input  placeholder="药品编码" name="codes" size="8" data-cnd="true">&nbsp;&nbsp;
		<input  placeholder="药品产地" name="places" size="8" data-cnd="true">&nbsp;&nbsp;
		<input placeholder="药品速查码" name="suchamas" size="8" data-cnd="true">&nbsp;&nbsp;
		<input placeholder="拆药人" name="operators" size="8" data-cnd="true">&nbsp;&nbsp;
		<button class="button ui-search-btn ui-btn-bg-green" onclick="qry()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
		<button class="button ui-search-btn ui-btn-bg-yellow" onclick='$("#tbSearch input:text").val("");'><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
		</div>
		<shiro:hasPermission name="PIVAS_BTN_943">
			<button class="button ui-search-btn ui-btn-bg-green" id="openDrugCheck"><i class="am-icon-edit"></i><span>拆药量检查</span></button>
			<button class="button ui-search-btn ui-btn-bg-green" id="confirmAdmin"><i class="am-icon-save"></i><span>保存</span></button>
		</shiro:hasPermission>
	
		<shiro:hasPermission name="PIVAS_BTN_944">
			<button class="button ui-search-btn ui-btn-bg-green" id="confirm"><i class="am-icon-check"></i><span>确认</span></button>
		</shiro:hasPermission>
	
	</div>
</div>
<table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
	<thead>
	<tr>
		<th>
			<input style="margin-top:2px;" type="checkbox" onclick="chkAll(this);" title="全选" class="noborder">						
		</th>
		<th>药品名称</th>
		<th>药品编码</th>
		<th>药品产地</th>
		<th>计划拆药量</th>
		<th>实际拆药量</th>
		<th>拆药人</th>
	</tr>
	</thead>
</table>
<%--
<!-- 搜索条件--开始 -->
<div class="oe_searchview" style="margin-left: 10px;  width: 260px;">
    <div class="oe_searchview_facets" >
	    <div class="oe_searchview_input oe_searchview_head"></div>
	    <div class="oe_searchview_input"  id="inputsearch__1" >
	    	  <input id="txt__1" type="text" class="oe_search_txt" style="max-height: 18px;width:230px;" width="170px" />
	    </div>
    </div>
    <img alt=""  onclick="qry();" style="top: 15px;position: absolute;" src="${pageContext.request.contextPath}/assets/search/images/searchblue.png">
    <div class="oe_searchview_clear" onclick="clearclosedinputall();" style="left: 480px;top: 1px;" ></div>
	<div class="oe-autocomplete" ></div>
	<div style="border:1px solid #D2D2D2;display:none;" width="260px" heiht="50px" class="divselect" style="">
		<cite>请选择...</cite>
		<ul class="ulQry" style="-webkit-border-radius: 20;"
					funname="qryList">
					<li show="药品名称" name="names">搜索 药品名称：<span
						class="searchVal"></span></li>
					<li show="药品编码" name="codes">搜索 药品编码：<span
						class="searchVal"></span></li>
					<li show="药品产地" name="places">搜索 药品产地：<span
						class="searchVal"></span></li>
					<li show="药品速查码" name="suchamas">搜索 药品速查码：<span
						class="searchVal"></span></li>
					<li show="拆药人" name="operators">搜索 拆药人：<span
						class="searchVal"></span></li>
					
		</ul>
	</div>
</div>
<!-- 搜索条件--结束 -->

		<div style="top: 8px;">
			<div>
				<div class="oe_view_manager_view_search"></div>

				<shiro:hasPermission name="PIVAS_BTN_943">
					<a class="button" id="openDrugCheck">拆药量检查</a>
							
					<a class="button" id="confirmAdmin">确认</a>
				</shiro:hasPermission>

				<shiro:hasPermission name="PIVAS_BTN_944">
					<a class="button" id="confirm">确认</a>
				</shiro:hasPermission>

			</div>
		</div>
		
		<div class="tbl">
			<table id="flexiGrid" style="display: block;margin: 0px;"></table>
		</div>
	
--%>

</body>
<style type="text/css" >
.oe_searchview .oe_searchview_clear{
		  margin-top: 10px;
	}
	.oe_searchview .oe_searchview_search{
		  margin-top: 36px;
	}
	.oe_searchview{
   	line-height: 18px; 
   	border: 1px solid #ababab;
   	background: white; 
   	width:323px;
    -moz-border-radius: 13px;
    cursor: text;
    padding: 1px 0;
    float:left;
    border: 1px solid #ababab;
    margin-top: 10px;
	}
</style>
</html>