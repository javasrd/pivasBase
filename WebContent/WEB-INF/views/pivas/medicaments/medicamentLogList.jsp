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
			updateDate: null,
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
                "url": "${pageContext.request.contextPath}/mdcmtLog/mdcmtLogList",
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
                    json.recordsFiltered = json.total;
                    json.recordsTotal = json.total;
                    return json.data;
                }
            },
            "columns": [
            	{"data": "drugName", "bSortable": false, "defaultContent":""},
            	{"data": "categoryName", "bSortable": false, "defaultContent":""},
            	{"data": "drugCode", "bSortable": false, "defaultContent":""},
            	{"data": "checkCode", "bSortable": false, "defaultContent":""},
            	{"data": "drugPlace", "bSortable": false, "defaultContent":""},
            	{"data": "stock_last", "bSortable": false, "defaultContent":""},
            	{"data": "stock_now", "bSortable": false, "defaultContent":""},
            	{"data": "operator", "bSortable": false, "defaultContent":""},
            	{"data": "updateDate", "bSortable": false, "defaultContent":""},
            ],
        });
    }
	
	$(function() {
		initDatatable();
		
		$("#aSearch").bind("click",function(){
			qry();
		});
	});

	function qry() {
		$("#tbSearch input:text").each(function() {
			if ($(this).attr("name")) {
				ajax_params[$(this).attr("name")] = $(this).val();
			}
		});
		
		datatable.ajax.reload();
	}
	
	
	function dataPick(){
		qry();
	}
	
</script>

</head>
<body>

<div id="searchDiv">
	<div data-qryMethod class="ui-search-header ui-search-box" id="tbSearch" style="display: inline; ">
				修改日期：<input type="text" id="scrq" name="updateDate" empty="false" readonly="readonly"
				style="color: #555555; height: 26px; width: 100px;" class="Wdate"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:dataPick});">
				<input placeholder="药品名称" name="drugName" size="8" data-cnd="true">
				<input placeholder="药品分类" name="categoryName" size="8" data-cnd="true">
				<input placeholder="药品编码" name="drugCode" size="8" data-cnd="true">
				<input placeholder="速查码" name="checkCode" size="8" data-cnd="true">
				<input placeholder="药品产地" name="drugPlace" size="8" data-cnd="true">
				<input placeholder="修改人" name="operator" size="8" data-cnd="true">
				<button class="button ui-search-btn ui-btn-bg-green" onclick="qry()"><i class="am-icon-search"></i><span>搜索</span></button>
				<button class="button ui-search-btn ui-btn-bg-yellow" onclick='$("#tbSearch input:text").val("");'><i class="am-icon-trash"></i><span>清空</span></button>
	</div>
</div>

<table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
	<thead>
	<tr>
		<th>药品名称</th>
		<th>药品分类</th>
		<th>药品编码</th>
		<th>速查码</th>
		<th>药品产地</th>
		<th>上次库存量</th>
		<th>药品库存</th>
		<th>修改人</th>
		<th>修改时间</th>
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