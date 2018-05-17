<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page
	import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp"%>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>

<html>

<head>

<style type="text/css">

table {
	width: 100%;
	text-align: left;
	table-layout: fixed;
	word-break: break-strict;
	font-size: 12px;
}

table thead tr th {
	padding-bottom: 8px;
}

</style>


</head>


	
<script type="text/javascript">
var valuestr = "" ;

var inparentAreas  = "";

$(function() {
	$("#useDate").val(getCurrentDate("yyyy-MM-dd",null,0));
	
	$("#batchId").multiSelect({ "selectAll": false,"noneSelected": "","oneOrMoreSelected":"*" },function(){
		valuestr = $("#batchId").selectedValuesString();
	});
	
});
		
</script>
<body>

	<div
		style="margin-top: 11px;z-index: 1000000;">
		<table style="font: 12px/1.5 '宋体',tahoma, Srial, helvetica, sans-serif;">
			<tr style="height: 35px;">
				<td style="width: 10%;text-align: right;" ><label class="tit">用药日期</label>：</td>
				<td style="width: 20%" ><input type="text" id="useDate" class="Wdate" name="useDate" style="height: 25px;color: #555555;"
					onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd'});" /> &nbsp;</td>
				
				<td style="width: 10%;text-align: right;" ><label class="tit"><spring:message code='rule.batch' /></label>：</td>
				<td style="width: 20%" ><select style="height: 25px;width: 184px;" id ="batchId" name="batchId[]" multiple="multiple" readonly>
						<option value="">
							<spring:message code="common.select" />
						</option>
						<c:forEach items="${batchList}" var="item">
							<option value="${item.id}">
								<spring:escapeBody htmlEscape="true">${item.name}</spring:escapeBody>
							</option>
						</c:forEach>
				</select> &nbsp;</td>
				
				<td colspan="6" style="text-align: center;">
			<a class="button" id="qryReciver">查询</a> 
			<shiro:hasPermission name="PIVAS_BTN_627">
			<a class="button" id="printReciver">打印</a>
			</shiro:hasPermission>
				</td>
			</tr>

		</table>
	</div>
	
	<div class="tbl" id ="tbl">
	</div>


</body>

<script type="text/javascript">

$(function() {
	$("#useDate").val(getCurrentDate("yyyy-MM-dd",null,0));
});
	var useDate = $( "#useDate" );
	
	var inpatientString = "${inpatientString}";

	$(document).ready(function(){
		$("#useDate").val(getCurrentDate("yyyy-MM-dd",null,0));
	});
	
	$("#qryReciver").bind("click", function() {
		
		var bValid = true;
		
		bValid = bValid && checkEmpty(useDate);
		if (!bValid ) {
			message({
				html : '日期过滤条件必选！',
				showConfirm : true
			});
			return;
		}
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/print/qryReciverLabel',
			dataType : 'html',
			cache : false,
			data : [ {name : 'useDate', value : $('#useDate').val()}, 
			         {name : 'batchIds',value : valuestr}, 
			         {name : 'wardCode',value : inpatientString},
			         {name : 'isPrint',value : false}
			         ],
			success : function(data) {
				$("#tbl").html(data);
			},
			error : function() {
				message({
					html : '<spring:message code="common.op.error"/>',
					showConfirm : true
				});
			}
		});
	});
	
	$("#printReciver").bind("click", function() {
		
		var bValid = true;
		
		bValid = bValid && checkEmpty(useDate);
		if (!bValid ) {
			message({
				html : '病区、日期过滤条件必选！',
				showConfirm : true
			});
			return;
		}
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/print/qryReciverLabel',
			dataType : 'json',
			cache : false,
			data : [ {name : 'useDate', value : $('#useDate').val()}, 
			         {name : 'batchIds',value : valuestr}, 
			         {name : 'wardCode',value : inpatientString},
			         {name : 'isPrint',value : true}
			         ],
			success : function(data) {
				window.open("${pageContext.request.contextPath}/printLabelDownLoad/<shiro:principal property="account"/>/"+data.msg);
			},
			error : function() {
				message({
					html : '<spring:message code="common.op.error"/>',
					showConfirm : true
				});
			}
		});
	});
</script>
</html>