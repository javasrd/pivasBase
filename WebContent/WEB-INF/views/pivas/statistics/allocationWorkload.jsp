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
	.header{
		margin:10px 0px 10px 0px;
	}
	
	body{
		font-size: 0.75em
	}
	
	select{
		height: 24px;
		padding-left:5px;
		padding-right:5px;
	}
	
	input[type='text']{
		padding-left:5px;
		padding-right:5px;
	}
	
	.dataModalDiv{
		float:left;
	}
	
	.reportMain {
		width: 100%;
		overflow: auto;
		padding:20px;
		background: #fff;
	}
	
	.reportSub{
		/*width: 80%;*/
		margin:0 auto;
	}
	
	.error{
		position: absolute;
		top:50%;
		left:50%;
		margin-left: -30px;
	}
	
	.button_a{
		height:26px;
		width:80px;
		font-size:13px;
		background: #4CAF50;
		color:white;
		border:1px solid white;
	}
	.button_b{
		background: #3F51B5;
	}
	.button_c{
		background: #FF9800;
	}
	.button_a:hover{
		height:26px;
		width:80px;
		font-size:13px;
		background: #EBD073;
		color:white;
		border:1px solid white;
	}

</style>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<link href="${pageContext.request.contextPath}/assets/pivas/css/edit.css" type="text/css" rel="stylesheet" />

</head>
<body>
	<div id="filterCondition" class="header">
		<table class="ui-tool-right">
			<tr>
				<td>时间类型：</td>
				<td>
					<select id="dataTimeType" onchange="dataTimeTypeChange()">
						<option value="1" selected>日</option>
						<option value="2">月</option>
						<option value="3">自定义</option>
					</select>
				</td> 
				<td>
					<div id="dataModal_a" class="dataModalDiv">
						<input type="text" style="height: 26px;width: 120px;" id="timeTypeA" name="timeTypeA" class="Wdate" maxlength="32" onclick="timeTypeA()"/>
					</div>
					<div id="dataModal_b" class="dataModalDiv" style="display:none">
						<input type="text" style="height: 26px;width: 120px;" id="timeTypeB" name="timeTypeB" class="Wdate" maxlength="32" onclick="dateType()"/>
					</div>
					<div id="dataModal_c" class="dataModalDiv" style="display:none">
						<input type="text" style="height: 26px;width: 120px;" id="timeTypeCStart" name="timeTypeCStart" class="Wdate" maxlength="32" onclick="timeTypeStart()"/>
						<span>~</span>  
						<input type="text" style="height: 26px;width: 120px;" id="timeTypeCEnd" name="timeTypeCEnd" class="Wdate" maxlength="32" onclick="timeTypeEnd()"/>
					</div> 
				</td>  
				<td>批次：</td>
				<td>
					<select id="batchIdList" name="batchIdList[]" multiple="multiple" size="5" style="width:100px;">
						<c:forEach items="${batchList}" var="batch" varStatus="status">
							<option value="${batch.id}">${batch.name}</option>
						</c:forEach>
					</select>	
				</td>
				<td>
					<button style="margin-left: 10px;" class="button_a" id="searchBtn" onclick="searchBtnClick()">查询</button>
				</td>
				<td>
					<button class="button_a button_b" id="printBtn"  onclick="printBtnClick()">打印</button>
				</td>
				<td>
					<button class="button_a button_c" id="exportBtn"  onclick="exportBtnClick()">导出</button>
				</td>
			</tr>
		</table>
	</div>

	<div class="reportMain" id="reportMain">
		<div id="birtReport" class="reportSub">
		</div>
	</div>
</body>   

<script type="text/javascript">

	var pdfPrintPath = "";
	var startTimeBefore = "";
	var endTimeBefore = "";
	var exportParam = {};
	
	function initDateAndTime(){
		var timeFormat = "yyyy-MM-dd";
		var dateFormat = "yyyy-MM";
		
		var currentTime = getCurrentDate(timeFormat, null, 0);
		var currentDate = getCurrentDate(dateFormat, null, 0);
		
		$("#timeTypeA").val(currentTime);
		$("#timeTypeCStart").val(currentTime);
		$("#timeTypeCEnd").val(currentTime);
		startTimeBefore = currentTime;
		endTimeBefore = currentTime;
		$("#timeTypeB").val(currentDate);
	}
	
	function timeTypeA(){
		var timeFormat = 'yyyy-MM-dd';
		WdatePicker({
			isShowClear: false,
			dateFmt: timeFormat,
			readOnly:true,
			onpicked:function(dp){}
		});
	}
	
	// 日期选择框  月
	function dateType(){
		var dateFormat = 'yyyy-MM';
		WdatePicker({
			isShowClear: false,
			dateFmt: dateFormat,
			readOnly:true,
			onpicked:function(dp){}
		});
	}
	
	function timeTypeStart(){
		var timeFormat = 'yyyy-MM-dd';
		WdatePicker({
			isShowClear: false,
			dateFmt: timeFormat,
			readOnly:true,
			onpicked:function(dp){
				startTimeChange();
			}
		});
	}
	
	function timeTypeEnd(){
		var timeFormat = 'yyyy-MM-dd';
		WdatePicker({
			isShowClear: false,
			dateFmt: timeFormat,
			readOnly:true,
			onpicked:function(dp){
			    endTimeChange();
			}
		});
	}
	
	function startTimeChange(){
		var timeTypeCStart = $("#timeTypeCStart").val();
		var timeTypeCEnd = $("#timeTypeCEnd").val();
		
		if (timeTypeCEnd < timeTypeCStart)
		{
            layer.alert('结束时间必须大于等于起始时间！', {'title': '操作提示', icon: 0}, function (index) {
                layer.close(index);
                $("#timeTypeCStart").val(startTimeBefore);
            });
		} else {
			startTimeBefore = timeTypeCStart;
            $("#searchBtn").click();
		}
	}
	
	function endTimeChange(){
		var timeTypeCStart = $("#timeTypeCStart").val();
		var timeTypeCEnd = $("#timeTypeCEnd").val();
		
		if (timeTypeCEnd < timeTypeCStart)
		{
            layer.alert('结束时间必须大于等于起始时间！', {'title': '操作提示', icon: 0}, function (index) {
                layer.close(index);
                $("#timeTypeCEnd").val(endTimeBefore);
            });
		} else {
			endTimeBefore = timeTypeCEnd;
			$("#searchBtn").click();
		}
	}
	
	function searchBtnClick(){
		getDrugOpenList();
	}
	
	function printBtnClick(){
		if(pdfPrintPath !== ""){
			window.open("${pageContext.request.contextPath}/alloDownLoad/<shiro:principal property="account"/>/"+pdfPrintPath);
		} else {
            layer.alert('未找到打印文件！', {'title': '操作提示', icon: 0});
		}
	}
	
	function exportBtnClick(){
		exportExcel();
	}
	
	function getDrugOpenList(){
		var startTime = "";
		var endTime = "";
		var startMonth = "";
		var batchList = "";
		
		switch($("#dataTimeType").val()) {
			case "1":
				startTime = $("#timeTypeA").val();
				endTime = $("#timeTypeA").val();
			  	break;
			case "2":
				startMonth = $("#timeTypeB").val();
			  	break;
			case "3":
				startTime = $("#timeTypeCStart").val();
				endTime = $("#timeTypeCEnd").val();
			  	break;
			default:
				break;
		}
		
		batchList = $("#batchIdList").selectedValuesString();
		
		var param = {
			"startTime": startTime,
			"endTime": endTime,
			"startMonth": startMonth,
			"batchList": batchList
		};
		
		$.ajax({
			type: "POST",
			url: "${pageContext.request.contextPath}/statistics/getWorkloadList",
			dataType: "html",
			cache:false,
			data:param,
			success:function(data) {
				$("#birtReport").html("");
				if("error_1" !== data){
					if(data.indexOf("msg") > -1){
						var dataJSON = JSON.parse(data);
						$("#birtReport").html("<div class='error'></div>");
						exportParam = {};
                        layer.alert(dataJSON.description, {'title': '操作提示', icon: 0});
					}else {
						var htmlArr = data.split("@@@@");
						$("#birtReport").html(htmlArr[0]);
						pdfPrintPath = htmlArr[1];
						exportParam = param;
					}
				} else {
					$("#birtReport").html("<div class='error'>没有符合条件的数据</div>");
					exportParam = {};
				}
			}
		}); 
	}
	
	function exportExcel(){
		if(!exportParam.startTime){
            layer.alert('没有可导出的数据！', {'title': '操作提示', icon: 0});
			return;
		}
		
		$.ajax({
			type: "POST",
			url: "${pageContext.request.contextPath}/statistics/exportExcel",
			dataType: "json",
			cache:false,
			data:exportParam,
			success:function(data) {
				if(data.success) {
					window.open("${pageContext.request.contextPath}/allocationExport/<shiro:principal property="account"/>/"+data.msg);
				}else{
                    layer.alert(data.msg, {'title': '操作提示', icon: 0});
				}
			},
			error:function(){
                layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
			}
		}); 
	}
	
	//日期类型下拉框事件
	function dataTimeTypeChange(){		
		switch($("#dataTimeType").val()) {
			case "1":
				$("#dataModal_a").css("display","block");
				$("#dataModal_b").css("display","none");
				$("#dataModal_c").css("display","none");
			  	break;
			case "2":
				$("#dataModal_a").css("display","none");
				$("#dataModal_b").css("display","block");
				$("#dataModal_c").css("display","none");
			  	break;
			default:
				$("#dataModal_b").css("display","none");
				$("#dataModal_a").css("display","none");
				$("#dataModal_c").css("display","block");
				break;
		}
	}
	
	$(function() {
		
		var windHeight = $(document).height()-100;
		
		$("#reportMain").height(windHeight);
		$("#birtReport").height(windHeight);
	    initDateAndTime();
		
		$("#batchIdList").multiSelect({ 
			"selectAll": false,
			"noneSelected": "--请选择--",
			"oneOrMoreSelected":"*" 
		},function(){});
		
		getDrugOpenList();
		
	});

</script>
</html>