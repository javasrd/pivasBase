<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../common/common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
	.header{margin:10px 0px 10px 0px;}
	body{font-size: 0.75em}
	select{height: 24px;padding-left:5px;padding-right:5px;}
	input[type='text']{padding-left:5px;padding-right:5px;}
	.ui-combobox-input
</style>
<script type="text/javascript">
	var userLanguage = '${sessionScope.language}' === 'zh' ? 'zh-cn' : 'en';
</script>
<script src="${pageContext.request.contextPath}/assets/echarts/echarts.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/echarts/echartsUtil.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/multDropList/multDropList.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<link href="${pageContext.request.contextPath}/assets/multDropList/multDropList.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
	var myChart2;
	var myChart

	function clear(chart)
	{
		if (chart != undefined)
		{
			chart.clear();
			chart = null;
		}
	}
	
	function toBirt()
	{
        $("#birtImg").addClass("ui-btn-bg-green");
        $("#barImg").removeClass("ui-btn-bg-green");
        $("#pieImg").removeClass("ui-btn-bg-green");
		
		$("#barDiv").hide();
		$("#pieDiv").hide();
		$("#birtReport").show();
		$("#stDimValueDivID").hide();
		$("#batchSearchDivID").show();
		$("#deptNameSearchDivID").show();
		
		var userLanguage = '${sessionScope.language}' === 'en' ? 'en_US' : 'zh';
		var batchSearchIDStr = $("#batchSearchID").selectedValuesString();
		var deptNameSearchstr = $("#deptNameSearchID").selectedValuesString();
		var singleState = $('#singleStateID').val();
		var statisticsTime = $('#statisticsTime').val();
		var url = contextPath + "/run?__report=report/scanWorkload.rptdesign&__parameterpage=falsemedicSingle.rptdesign&__toolbar=false&__locale=" + userLanguage;
		
		url += "&searchTime="
		if(statisticsTime != null && statisticsTime !== ''){
			url += statisticsTime;
			var titel = "&title=" + '扫描工作量统计' + '('+statisticsTime + ')';
			url += encodeURI(titel);
		}
		url += "&searchBatch="
		if(batchSearchIDStr != null && batchSearchIDStr !== ''){
			url += batchSearchIDStr;			
		}
		url += "&searchDeptName="
		if(deptNameSearchstr != null && deptNameSearchstr !== ''){
			url += deptNameSearchstr;			
		}
		
		url += "&singleState="
			if(singleState != null && singleState !== ''){
				url += singleState;			
			}
		
		var user = "&user=" + '<spring:message code="pivas.statistics.tab"/>' + '<spring:escapeBody htmlEscape="true"><shiro:principal property="name"/></spring:escapeBody>';
		url += encodeURI(user);
		var iheight = parseFloat($(document).height())-100;
		$("#birtReport iframe").css("height",iheight).attr("src", url);
		clear(myChart2);
		clear(myChart);
		$("#exportImg").show();
	}
	
	function changeState()
	{
	   toBirt();
	}
	
	function time()
	{
		var dateFmt = 'yyyy-MM-dd';
		var dateType = $("#dateType").val();
		if ("0" == dateType)
		{
			dateFmt = 'yyyy-MM';
		}
		
		WdatePicker({isShowClear:false,dateFmt:dateFmt,readOnly:true,onpicked:function(dp){changeState();}});
	}
	
	function changeDateType()
	{
		var dateFmt = 'yyyy-MM-dd';
		var dateType = $("#dateType").val();
		if ("0" == dateType)
		{
			dateFmt = 'yyyy-MM';
		}
		
		var currentDate = getCurrentDate(dateFmt, null, 0);
		$('#statisticsTime').val(currentDate);
		changeState();
	}
	
		
	var english = true;
	var contextPath;
	$(function() {
	    english = "zh-cn" == userLanguage ? false:true;
	    
	    changeDateType();

		//querySelectData();

	    contextPath = '${pageContext.request.contextPath}';
		initEcharts(contextPath);
		
		toBirt();
		
		$("#batchSearchID").multiSelect({ "selectAll": false,"noneSelected": "选择批次","oneOrMoreSelected":"选择批次" },function(){
			changeState();
		});
		
		$("#deptNameSearchID").multiSelect({ "selectAll": false,"noneSelected": "选择病区","oneOrMoreSelected":"选择病区" },function(){
			changeState();
		});
	});
	

function exportExcel()
{
	if($(window.frames["reportFrame"].document).find("table .style_8 tbody").children().length>5){
		var exportDoc = document.getElementById("export");
		var url = $("#birtReport iframe").attr("src");
		if (url)
		{
			url +="&__format=xlsx";
			exportDoc.action=url;
			exportDoc.submit();
		}
	}else{
        layer.alert('表格无数据，未导出Excel！', {'title': '操作提示', icon: 0});
	}
}

function changeSingleState()
{
	changeState();
}
</script>
</head>
<body>
	<div id="filterCondition" class="header">
		<table class="report-search">
			<tr>
				<td>
					<div style="float: left;border: 1px solid #ccc;" class="ui-search-btn ui-btn-bg-green"   onclick="toBirt();" id="birtImg"><i class="am-icon-table"></i><span>表格</span></div>
					<shiro:hasPermission name="PIVAS_BTN_725">
						<div style="float: left;border: 1px solid #ccc;" onclick="exportExcel();"  class="ui-search-btn ui-btn-bg-green" id="exportImg"><i class="am-icon-download"></i><span>下载</span></div>
					</shiro:hasPermission>
				</td>
			</tr>
			<tbody class="ui-tool-right">
			<tr>
				<td><span><spring:message code="MedicSingle.statisticalCycle"/>:</span></td>
				<td>
				<input id="flowStatusID" type="hidden"/>
					<select id="dateType" onchange="changeDateType()" style="height: 26px">
						<option value="0"><spring:message code="common.month"/></option>
						<option value="1"><spring:message code="common.day"/></option>
					</select>
				</td>
				
				<td>
					<input type="text"  style="height: 26px;width:100px;display: none;" id="" name=""  />
					<input type="text" style="height: 26px;width: 100px;" id="statisticsTime" name="statisticsTime" class="Wdate" maxlength="32" onclick="time()" />
				</td>
				<td>
					<div id="batchSearchDivID" style="margin-left: 10px;">
						<select id="batchSearchID" name="batchSearch[]" multiple="multiple" size="5" style="width:100px;height: 23px">
							<c:forEach items="${batchList}" var="batchItem" varStatus="batchStatus">
								<option value="${batchItem.id}">${batchItem.name}</option>
							</c:forEach>
						</select>			
					</div>
				</td>
				
				<td>
					<div id="deptNameSearchDivID" style="margin-left: 10px;">
						<select id="deptNameSearchID" name="deptNameSearch[]" multiple="multiple" size="5" style="width:100px;">
						    <c:forEach items="${inpatientAreaList}" var="inpatientAreaItem" varStatus="inpatientAreaStatus">
							    <option value="${inpatientAreaItem.deptName}">${inpatientAreaItem.deptName}</option>
						    </c:forEach>
						</select>			
					</div>
				</td>
				
				<td><span>扫描状态:</span></td>
				<td>
					<select id="singleStateID" style="height: 26px"  onchange="changeSingleState()">
						<option value="">--请选择--</option>
						<option value="0">进仓</option>
						<option value="1">仓内</option>
						<option value="2">出仓</option>
					</select>			
				</td>
			</tr>
			</tbody>
		</table>
	</div>

    <%--<div class="header">--%>
		<%--<div style="float: left;border: 1px solid #ccc;" ><img onclick="toBirt();" id="birtImg" src="${pageContext.request.contextPath}/assets/sysImage/statistics/table2.png"/></div>--%>
		<%--<shiro:hasPermission name="PIVAS_BTN_725">--%>
			<%--<div style="float: left;border: 1px solid #ccc;" id="exportImg"><img onclick="exportExcel();" src="${pageContext.request.contextPath}/assets/sysImage/statistics/export.png"/></div>--%>
		<%--</shiro:hasPermission>--%>
	<%--</div>--%>

	<div id="birtReport" style="margin-top:10px;">
		 <iframe name="reportFrame" width="100%" height="450" frameborder="0" src="" style="margin-top: 10px;margin-left: 0px"></iframe>
	</div>

<form method="post" id="export"></form>
</body>
</html>