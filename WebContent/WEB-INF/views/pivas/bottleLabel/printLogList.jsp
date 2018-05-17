<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page
	import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp"%>
<script src="${pageContext.request.contextPath}/assets/common/js/my97DatePicker/WdatePicker.js"></script>
<html>

<head>
<link
	href="${pageContext.request.contextPath}/assets/sysCss/common/style.css"
	type="text/css" rel="stylesheet" />
<script>
	var paramTemp__1 ;
	
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
                "url": "${pageContext.request.contextPath}/printLog/printBottleLabelList",
                "type": "post",
                "data": function (d) {
                    d.rp = d.length;
                    d.page = d.start / d.length + 1;
                    
                    ajax_params.printStart = $('#printStart').val();
            		ajax_params.printEnd = $('#printEnd').val();
                    $.each(ajax_params, function(k, v) {
                    	if (v) {
	                        d[k] = v;
                    	}
                    });
                },
                "dataSrc": function (json) {
                	json.data = json.rawRecords;
                    json.recordsFiltered = json.total;
                    json.recordsTotal = json.total;
                    return json.data;
                }
            },
            "columns": [
            	{"data": "opreName", "bSortable": false, "defaultContent":""},
                {"data": "printIndex", "bSortable": false, "defaultContent":""},
                {"data": "printDate", "bSortable": false, "defaultContent":""},
                {"data": "printCondition", "bSortable": false, "defaultContent":""},
            ],
        });
    }
	
	$(function() {
		initDatatable();
		
		$("#printStart").val(getCurrentDate("yyyy-MM-dd",null,0));
		$("#printEnd").val(getCurrentDate("yyyy-MM-dd",null,0));
		
		$("#aSearch").bind("click",function(){
			qry();
		});
	});

	function qry() {
		ajax_params.printStart = $('#printStart').val();
		ajax_params.printEnd = $('#printEnd').val();
		$("#tbSearch input:text").each(function() {
			if ($(this).attr("name")) {
				ajax_params[$(this).attr("name")] = $(this).val();
			}
		});
		
		datatable.ajax.reload();
	}
	
	function dataPick(){
		var searchStartTime = $('#printStart').val();
		var searchEndTime = $('#printEnd').val();
		
		ajax_params.printStart = $('#printStart').val();
		ajax_params.printEnd = $('#printEnd').val();
		
		if (searchEndTime < searchStartTime)
		{
			layer.alert("结束时间必须大于等于起始时间！", {'title': '', icon: 2});
		}
		if (searchEndTime >= searchStartTime)
		{
			datatable.ajax.reload();
		}
	}
	
</script>

</head>
<body>


	<div id="searchDiv">
		<div data-qryMethod class="ui-search-header ui-search-box" id="tbSearch" style="display: inline; ">
			<input type="text" id="printStart" style="color: #555555;height:26px;width: 100px;" class="Wdate" empty="false" readonly="readonly"
					onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:dataPick});">
~
<input type="text" id="printEnd"
					style="color: #555555;height:26px;width: 100px;" class="Wdate"
					empty="false" readonly="readonly"
					onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:dataPick});">
					
					<input placeholder="打印人" id="opreNames" name="opreNames" size="8" data-cnd="true">
					<input placeholder="打印序号" id="printIndexs" name="printIndexs" size="8" data-cnd="true">

					<button class="button ui-search-btn ui-btn-bg-green" onclick="qry()"><i class="am-icon-search"></i><span>搜索</span></button>
					<button class="button ui-search-btn ui-btn-bg-yellow" onclick='$("#opreNames, #printIndexs").val("");'><i class="am-icon-trash"></i><span>清空</span></button>
		</div>
	</div>

		
	<table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
		<thead>
		<tr>
			<th>打印人</th>
			<th>打印序号</th>
			<th>打印日期</th>
			<th>打印条件</th>
		</tr>
		</thead>
	</table>

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