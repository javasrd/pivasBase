<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page
	import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp"%>

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
$(function() {
	$("#useDate").val(getCurrentDate("yyyy-MM-dd",null,0));
});
		
</script>
<body>

	<div style="margin-top: 11px;z-index: 1000000;">
		<table style="font: 12px/1.5 '宋体',tahoma, Srial, helvetica, sans-serif;">
			<tr style="height: 35px;">
				<td style="width: 10%;text-align: right;" ><label class="tit">生成日期</label>：</td>
				<td style="width: 20%" ><input type="text" id="useDate" name="useDate" style="height: 25px;color: #555555;"
					class="Wdate" empty="false"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" /> &nbsp;</td>
					
			    <td style="width: 10%;text-align: right;" ><label class="tit"><spring:message code='pivas.patient.wardName' /></label>：</td>
				<td style="width: 20%" ><select style="height: 25px;width:184px;" id ="wardCode" name="wardCode" autoDestroy readonly>
						<option value="">
							<spring:message code="common.select" />
						</option>
						<c:forEach items="${inpatientAreaList}" var="item">
							<option value="${item.deptCode}">
								<spring:escapeBody htmlEscape="true">${item.deptName}</spring:escapeBody>
							</option>
						</c:forEach>
				</select> &nbsp;</td>

				<td style="width: 10%;text-align: right;" ><label class="tit"><spring:message code='rule.batch' /></label>：</td>
				<td style="width: 20%" ><select style="height: 25px;width: 184px;" id ="batchId" name="batchId" readonly>
						<option value="">
							<spring:message code="common.select" />
						</option>
						<c:forEach items="${batchList}" var="item">
							<option value="${item.id}">
								<spring:escapeBody htmlEscape="true">${item.name}</spring:escapeBody>
							</option>
						</c:forEach>
				</select> &nbsp;</td>
			</tr>
			<tr style="height: 35px;">
				<td colspan="6" style="text-align: center;">
					<a class="button" id="qryLabel">查询</a> 
					<a class="button" id="printLabel">打印</a>
				</td>
			</tr>
		</table>
	</div>

	<div class="tbl" id ="tbl">
	</div>

</body>

<script type="text/javascript">

	var useDate = $( "#useDate" );
	
	
	$(document).ready(function(){
		$("#useDate").val(getCurrentDate("yyyy-MM-dd",null,0));
	});
	
	$("#qryLabel").bind("click", function() {
		
		var bValid = true;
		bValid = bValid && checkEmpty(useDate);
		if (!bValid ) {
			message({
				html : '用药日期过滤条件必选！',
				showConfirm : true
			});
			return;
		}
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/printStatisticsLabel/print',
			dataType : 'html',
			cache : false,
			data : [ {name : 'useDate', value : $('#useDate').val()}, 
			         {name : 'batchId',value : $('#batchId').val()}, 
			         {name : 'wardCode',value : $('#wardCode').val()},
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
	
	$("#printLabel").bind("click", function() {
		var bValid = true;
		bValid = bValid && checkEmpty(useDate);
		if (!bValid ) {
			message({
				html : '用药日期过滤条件必选！',
				showConfirm : true
			});
			return;
		}
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/printStatisticsLabel/print',
			dataType : 'json',
			cache : false,
			data : [  {name : 'useDate', value : $('#useDate').val()}, 
				      {name : 'batchId',value : $('#batchId').val()}, 
				      {name : 'wardCode',value : $('#wardCode').val()},
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