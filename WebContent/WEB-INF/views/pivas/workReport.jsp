<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../common/common.jsp" %>
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

<body>
	<div id="filterCondition" class="header">
		<table style="width: 100%; background-color: gainsboro;">
			<tr style="">
				<td width="60" style="padding-left: 5px;"><span>统计周期:</span></td>
				<td width="125">
					<input type="text" style="height: 26px;width: 120px;" id="searchStartTime" name="searchStartTime" class="Wdate" maxlength="32" onclick="time()"/>
				</td>
				<td width="150">
					<span>~</span>
					<input type="text" style="height: 26px;width: 120px;" id="searchEndTime" name="searchEndTime" class="Wdate" maxlength="32" onclick="time()" />
				</td>
				<td width="50">
					<span style="margin-left: 5px;">班次：</span>
				</td>
				<td width="60">
					<div id="workListDivID">
						<select id="workListID" name="workList[]" multiple="multiple" size="15" style="width:100px;">
							<c:forEach items="${workList}" var="work">
								<option value="${work.workId}">${work.workName}</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<td>
					<button class="btn btn-group btn-primary" style="float:left; margin-left: 10px;" id="startBtn" value="false" onclick="toBirt()" >统计</button>
				</td>
				<td align="right" style="padding-right: 10px;">
					<div style="float: right;border: 1px solid #ccc;" id="exportImg"><img onclick="exportExcel();" src="${pageContext.request.contextPath}/assets/sysImage/statistics/export.png"/></div>
				</td>
			</tr>
		</table>
	</div>


	

	<div id="birtReport" >
		 <iframe name="frame1" width="100%" height="450" frameborder="0" src="" style="margin-top: 10px;margin-left: 0px"></iframe>
	</div>
	
    <script type="text/javascript">
           
    </script>
    
    <form method="post" id="export"></form>
</body>

<script type="text/javascript">
	function toBirt() {
		$("#birtImg")
				.attr("src",
						"${pageContext.request.contextPath}/assets/sysImage/statistics/table2.png");

		$("#exportImg").show();
		$("#birtReport").show();
		$("#deptNameSearchDivID").show();

		var searchEndTime = $('#searchEndTime').val();

		var searchStartTime = $('#searchStartTime').val();

		var workList = $("#workListID").selectedValuesString();
		var url = contextPath
				+ "/run?__report=report/workReport.rptdesign&__parameterpage=false&__toolbar=false&__locale="
				+ bLanguage;

		url += "&searchStartTime="
		if (searchStartTime != null && searchStartTime !== '') {
			url += searchStartTime;
		}

		url += "&workList="
		if (workList != null && workList !== '') {
			url += workList;
		}

		url += "&searchEndTime="
		if (searchEndTime != null && searchEndTime !== '') {
			url += searchEndTime;
		}

		var titel = "&title=" + '班次统计' + '(' + searchStartTime + '~'
				+ searchEndTime + ')';
		url += encodeURI(titel);

		var user = "&user="
				+ '<spring:message code="pivas.statistics.tab"/>'
				+ '<spring:escapeBody htmlEscape="true"><shiro:principal property="name"/></spring:escapeBody>';
		url += encodeURI(user);

		var iheight = parseFloat($(document).height()) - 100;
		$("#birtReport iframe").css("height", iheight).attr("src", url);
	}

	function changeState() {
		var searchEndTime = $('#searchEndTime').val();
		var searchStartTime = $('#searchStartTime').val();

		if (searchEndTime < searchStartTime) {
			changeDateType();
			message({
				html : '结束时间必须大于等于起始时间！',
				showConfirm : true
			});
		}

		var time = new Date(addDate(searchStartTime, 31));

		if (time < new Date(searchEndTime)) {
			changeDateType();
			message({
				html : '班次统计周期不能大于一个月！',
				showConfirm : true
			});
		}

	}

	function addDate(date, days) {
		var d = new Date(date);
		d.setDate(d.getDate() + days);
		var m = d.getMonth() + 1;
		return d.getFullYear() + '-' + m + '-' + d.getDate();
	}

	function time() {
		var dateFmt = 'yyyy-MM-dd';

		WdatePicker({
			isShowClear : false,
			dateFmt : dateFmt,
			readOnly : true,
			onpicked : function(dp) {
				changeState();
			}
		});
	}

	function changeDateType() {
		var dateFmt = 'yyyy-MM-dd';
		//var currentDate = getCurrentDate(dateFmt, null, 0);

		var now = new Date();
		var nowTime = now.getTime();
		var day = now.getDay();
		var oneDayLong = 24 * 60 * 60 * 1000;

		var MondayTime = nowTime - (day - 1) * oneDayLong;
		var SundayTime = nowTime + (7 - day) * oneDayLong;

		var monday = new Date(MondayTime);
		var sunday = new Date(SundayTime);

		$('#searchEndTime').val(getCurrentDate(dateFmt, sunday, 0));

		//dateFmt = 'yyyy-MM';
		//currentDate = getCurrentDate(dateFmt, null, 0);

		$('#searchStartTime').val(getCurrentDate(dateFmt, monday, 0));
		changeState();
	}

	var english = true;
	var contextPath;
	$(function() {
		$("#searchDataType").hide();
		english = "zh-cn" == bLanguage ? false : true;

		// 设置默认统计时间
		changeDateType();

		contextPath = '${pageContext.request.contextPath}';
		// 初始化echarts
		initEcharts(contextPath);

		$("#workListID").multiSelect({
			"selectAll" : false,
			"noneSelected" : "选择班次",
			"oneOrMoreSelected" : "选择班次"
		}, function() {
			changeState();
		});

	});

	function exportExcel() {
		if ($(window.frames["frame1"].document).find("table .style_8 tbody")
				.children().length > 5) {
			var exportDoc = document.getElementById("export");
			var url = $("#birtReport iframe").attr("src");
			if (url) {
				// 根据需求修改想要的文件类型例如docx
				url += "&__format=xlsx";
				exportDoc.action = url;
				exportDoc.submit();
			}
		} else {
        	layer.alert('表格无数据，未导出Excel', {icon: 6});
		}
	}
</script>
</html>