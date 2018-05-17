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
	var bLanguage = '${sessionScope.language}' === 'zh' ? 'zh-cn' : 'en';
</script>
<script src="${pageContext.request.contextPath}/assets/echarts/echarts.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/echarts/echartsUtil.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/multDropList/multDropList.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<link href="${pageContext.request.contextPath}/assets/multDropList/multDropList.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
	var myChart2;
	var myChart;
	var statisticsType = "DrugOpenAmount";

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
		
		$("#exportImg").show();
		$("#birtReport").show();
		$("#deptNameSearchDivID").show();
		
		var searchEndTime = $('#searchEndTime').val();
		
		var searchStartTime = $('#searchStartTime').val();
		
		var userStr = $("#userListID").selectedValuesString();
		
		var url = contextPath + "/run?__report=report/drugOpenWorkload.rptdesign&__parameterpage=false&__toolbar=false&__locale=" + bLanguage;
		
		url += "&searchStartTime="
		if(searchStartTime != null && searchStartTime !== ''){
			url += searchStartTime;
		}
		
		url += "&operator="
		if(userStr != null && userStr !== ''){
				url += userStr;
		}
		
		url += "&searchEndTime="
		if(searchEndTime != null && searchEndTime !== ''){
			url += searchEndTime;
		}
		
		var titel = "&title=" + '拆药量统计' + '('+searchStartTime+ '~' + searchEndTime + ')';
		url += encodeURI(titel);
		
		var user = "&user=" + '<spring:message code="pivas.statistics.tab"/>' + '<spring:escapeBody htmlEscape="true"><shiro:principal property="name"/></spring:escapeBody>';
		url += encodeURI(user);
		
		var iheight = parseFloat($(document).height())-100;
		$("#birtReport iframe").css("height",iheight).attr("src", url);
		
		clear(myChart2);
		clear(myChart);
	}
	
	function changeState()
	{
		var searchEndTime = $('#searchEndTime').val();
		var searchStartTime = $('#searchStartTime').val();
		
		if (searchEndTime < searchStartTime)
		{
            layer.alert('结束时间必须大于等于起始时间！', {'title': '操作提示', icon: 0});
		}
		
		if(searchEndTime <= searchStartTime)
		{
			toBirt();
		}
	   
	}
	
	function time()
	{
		var dateFmt = 'yyyy-MM-dd';
		
		WdatePicker({isShowClear:false,dateFmt:dateFmt,readOnly:true,onpicked:function(dp){changeState();}});
	}
	
	function changeDateType()
	{
		var dateFmt = 'yyyy-MM-dd';
		
		var currentDate = getCurrentDate(dateFmt, null, 0);
		
		$('#searchEndTime').val(currentDate);
		dateFmt = 'yyyy-MM';
		currentDate = getCurrentDate(dateFmt, null, 0);
		$('#searchStartTime').val(currentDate + '-01');
		changeState();
	}

	var english = true;
	var contextPath;
	$(function() {
		$("#searchDataType").hide();
	    english = "zh-cn" == bLanguage ? false:true;
	    
	    // 设置默认统计时间
	    changeDateType();

	    contextPath = '${pageContext.request.contextPath}';
		// 初始化echarts
		initEcharts(contextPath);
		
		$("#userListID").multiSelect({ "selectAll": false,"noneSelected": "选择拆药人","oneOrMoreSelected":"选择拆药人" },function(){
			changeState();
		});
		
		toBirt();
		
		
	});
	

function exportExcel()
{
	if($(window.frames["reportFrame"].document).find("table .style_8 tbody").children().length>5){
		var exportDoc = document.getElementById("export");
		var url = $("#birtReport iframe").attr("src");
		if (url)
		{
			// 根据需求修改想要的文件类型例如docx
			url +="&__format=xlsx";
			exportDoc.action=url;
			exportDoc.submit();
		}
	}else{
        layer.alert('表格无数据，未导出Excel', {'title': '操作提示', icon: 0});
	}
}
</script>
</head>
<body>
	<div id="filterCondition" class="header">
		<table class="report-search">
			<tr>
				<td>
					<div style="float: left;border: 1px solid #ccc;" class="ui-search-btn ui-btn-bg-green" onclick="toBirt();" id="birtImg"><i class="am-icon-table"></i><span>表格</span></div>
					<shiro:hasPermission name="PIVAS_BTN_974">
						<div style="float: left;border: 1px solid #ccc;" onclick="exportExcel();"  class="ui-search-btn ui-btn-bg-green" id="exportImg"><i class="am-icon-download"></i><span>下载</span></div>
					</shiro:hasPermission>
				</td>
			</tr>
			<tbody class="ui-tool-right">
			<tr>
				<td><span><spring:message code="MedicSingle.statisticalCycle"/>:</span></td>
				<td>
					<input type="text" style="height: 26px;width: 100px;" id="searchStartTime" name="searchStartTime" class="Wdate" maxlength="32" onclick="time()"/>
				</td>
				<td>
					<span>~</span>
					<input type="text" style="height: 26px;width: 100px;" id="searchEndTime" name="searchEndTime" class="Wdate" maxlength="32" onclick="time()" />
				</td>
				 
				<td>
					<div id="userListDivID">
						<select id="userListID" name="userList[]" multiple="multiple" size="5" style="width:100px;">
							<c:forEach items="${userList}" var="user" varStatus="status">
								<option value="${user.account}">${user.name}</option>
							</c:forEach>
						</select>			
					</div>
				</td>
			</tr>
			</tbody>
		</table>
	</div>

	<%--<div class="header">--%>
		<%--<div style="float: left;border: 1px solid #ccc;" ><img onclick="toBirt();" id="birtImg" src="${pageContext.request.contextPath}/assets/sysImage/statistics/table2.png"/></div>--%>
		<%--<shiro:hasPermission name="PIVAS_BTN_974">--%>
		<%--<div style="float: left;border: 1px solid #ccc;" id="exportImg"><img onclick="exportExcel();" src="${pageContext.request.contextPath}/assets/sysImage/statistics/export.png"/></div>--%>
		<%--</shiro:hasPermission>--%>
	<%--</div>--%>
	

	<div id="birtReport" >
		 <iframe name="reportFrame" width="100%" height="450" frameborder="0" src="" style="margin-top: 10px;margin-left: 0px"></iframe>
	</div>
	
    <script type="text/javascript">
           
    </script>
    
    <form method="post" id="export"></form>
</body>
</html>