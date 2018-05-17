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
	var statisticsType = "DrugUse";

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
		
		$("#exportImg").show();
		$("#searchDataType").hide();
		$("#barDiv").hide();
		$("#pieDiv").hide();
		$("#birtReport").show();
		$("#deptNameSearchDivID").show();
		$("#flowStatusID").val("0");
		
		var searchEndTime = $('#searchEndTime').val();
		
		var searchStartTime = $('#searchStartTime').val();
		
		var compareResult = compareTime(searchStartTime);
		
		var singleState = $('#singleStateID').val();
		
		var batchSearchIDStr = $("#batchSearchID").selectedValuesString();
		var deptNameSearchstr = $("#deptNameSearchID").selectedValuesString();
		
		var medicCategorystr = $("#medicCategorySearchID").selectedValuesString();
		
		var medicLabelstr = $("#medicLabelSearchID").selectedValuesString();
		
		var url = contextPath + "/run?__report=report/" + statisticsType +".rptdesign&__parameterpage=false&__toolbar=false&__locale=" + bLanguage;
		
		if (compareResult == 0)
		{
			url = contextPath + "/run?__report=report/" + statisticsType +"His.rptdesign&__parameterpage=false&__toolbar=false&__locale=" + bLanguage;
		}
		
		url += "&searchStartTime="
		if(searchStartTime != null && searchStartTime !== ''){
			url += searchStartTime;
		}
		
		url += "&searchEndTime="
		if(searchEndTime != null && searchEndTime !== ''){
			url += searchEndTime;
		}
		
		var titel = "&title=" + '<spring:message code="pivas.druguse.title"/>' + '('+searchStartTime+ '~' + searchEndTime + ')';
		url += encodeURI(titel);
		
		url += "&searchBatch="
		if(batchSearchIDStr != null && batchSearchIDStr !== ''){
			url += batchSearchIDStr;			
		}
		url += "&searchDeptName="
		if(deptNameSearchstr != null && deptNameSearchstr !== ''){
			url += deptNameSearchstr;			
		}
		
		url += "&medicLabel="
		if(medicLabelstr != null && medicLabelstr !== ''){
			url += medicLabelstr;			
		}
		
		url += "&medicCategory="
		if(medicCategorystr != null && medicCategorystr !== ''){
			url += medicCategorystr;			
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
	}
	
	function toBar()
	{
        $("#birtImg").removeClass("ui-btn-bg-green");
        $("#barImg").addClass("ui-btn-bg-green");
        $("#pieImg").removeClass("ui-btn-bg-green");
		
		$("#batchSearchDivID").show();
		$("#exportImg").hide();
		$("#searchDataType").show();
		$("#birtReport").hide();
		$("#birtReport iframe").attr("src","");
		$("#flowStatusID").val("1");
		$("#barDiv").show();
		$("#pieDiv").hide();
		
		clear(myChart2);
		clear(myChart);
		
	    var searchDataType = $('#searchDataType').val();
	    if(searchDataType == 0){
			$("#batchSearchDivID").show();
			$("#deptNameSearchDivID").hide();
	    	queryBatchBar();
	    }else{
			$("#batchSearchDivID").hide();
			$("#deptNameSearchDivID").show();
	    	queryDeptBar();
	    }
	    var batchSearchIDStr = $("#batchSearchID").selectedValuesString();
	}
	
	
	function toPie()
	{
        $("#birtImg").removeClass("ui-btn-bg-green");
        $("#barImg").removeClass("ui-btn-bg-green");
        $("#pieImg").addClass("ui-btn-bg-green");
		
		$("#batchSearchDivID").show();
		$("#exportImg").hide();
		$("#searchDataType").show();
		$("#birtReport").hide();
		$("#birtReport iframe").attr("src","");
		$("#barDiv").hide();
		$("#pieDiv").show();
		clear(myChart);
		clear(myChart2);
		$("#flowStatusID").val("2");
		var searchDataType = $('#searchDataType').val();
		
		if(searchDataType == 0){
			$("#batchSearchDivID").show();
			$("#deptNameSearchDivID").hide();
			initPieBatch();
	    }else{
			$("#batchSearchDivID").hide();
			$("#deptNameSearchDivID").show();
			initPieDeptName();
	    }
	}
	
	function changeState()
	{
		var searchEndTime = $('#searchEndTime').val();
		var searchStartTime = $('#searchStartTime').val();
		
		if (searchEndTime < searchStartTime)
		{
            layer.alert('结束时间必须大于等于起始时间！', {'title': '操作提示', icon: 0});
		} else {
            var flowStatusID = $("#flowStatusID").val();
            if(flowStatusID == 1){
                toBar();
            }else if(flowStatusID == 2){
                toPie();
            }else if(flowStatusID == 0){
                toBirt();
            }
		}
	}
	
	/**
	*按批次查询药物使用情况
	*/
	function queryBatchBar()
	{
		var option = {
			domId:"barDiv",
			legendNames:[],
			xAxisData:[],
			seriesData:[]
		};
		
		var searchEndTime = $('#searchEndTime').val();
		var searchStartTime = $('#searchStartTime').val();
		
		var batchSearchIDStr = $("#batchSearchID").selectedValuesString();
		var singleState = $('#singleStateID').val();
		
		var medicCategorystr = $("#medicCategorySearchID").selectedValuesString();
		var medicLabelstr = $("#medicLabelSearchID").selectedValuesString();
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/drugUse/queryBatchBar',
			dataType : 'json',
			cache : false,
			data : [{name : 'searchStartTime',value :searchStartTime},
			        {name : 'searchEndTime',value :searchEndTime},
			        {name : 'singleState', value : singleState},
			        {name : 'searchBatchs',value :batchSearchIDStr},
			        {name : 'medicCategorys',value :medicCategorystr},
			        {name : 'medicLabels',value :medicLabelstr},
			        {name : 'compareResult', value: compareTime(searchStartTime)}
			        ],
			success : function(data) {
			    console.log(data);
				if(data.batchNameList != null){
					$.each(data.batchNameList,function(index,value){
						option.xAxisData.push(value);
					});
				}
				
				if(data.drugClassList != null){
					$.each(data.drugClassList,function(index,value){
						option.legendNames.push(value);
					});
				}
				
				if(data.drugUse2BatchList != null){
					$.each(data.drugUse2BatchList,function(index,value){
						var batchBar = {
					            name:'',
					            type:'bar',
					            stack: ' ',
					            data:[]
					        };
						
						batchBar.name = value.drugClassName;
						batchBar.data = value.ydCountList;
						option.seriesData.push(batchBar);
					});
		
					option.costData = data.costMap;
				}

				createBar(option);
			}
		});
		
		
	}
	
	/**
	*按病区查询药物使用情况
	*/
	function queryDeptBar()
	{
		var option = {
			data:[],
			domId:"barDiv",
			legendNames:[],
			xAxisData:[],
			seriesData:[],
			costData:{}
		};
		var searchEndTime = $('#searchEndTime').val();
		var searchStartTime = $('#searchStartTime').val();
		var singleState = $('#singleStateID').val();
		var deptNameSearchstr = $("#deptNameSearchID").selectedValuesString();
		var medicCategorystr = $("#medicCategorySearchID").selectedValuesString();
		var medicLabelstr = $("#medicLabelSearchID").selectedValuesString();
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/drugUse/queryDeptBar',
			dataType : 'json',
			cache : false,
			data : [{name : 'searchStartTime',value :searchStartTime},
			        {name : 'searchEndTime',value :searchEndTime},
			        {name : 'singleState', value : singleState},
			        {name : 'searchDeptNames',value :deptNameSearchstr},
			        {name : 'medicCategorys',value :medicCategorystr},
			        {name : 'medicLabels',value :medicLabelstr},
			        {name : 'compareResult', value: compareTime(searchStartTime)}],
			success : function(data) {
				if(data.wardNameList != null){
					$.each(data.wardNameList,function(index,value){
						option.xAxisData.push(value);
					});
				}
				
				if(data.drugClassList != null){
					$.each(data.drugClassList,function(index,value){
						option.legendNames.push(value);
					});
				}
				
				if(data.drugUse2DeptList != null){
					$.each(data.drugUse2DeptList,function(index,value){
						var batchBar = {
					            name:'',
					            type:'bar',
					            stack: ' ',
					            data:[]
					        };
						batchBar.name = value.drugClassName;
						batchBar.data = value.ydCountList;
						option.seriesData.push(batchBar);
					});
					
					option.costData = data.costMap;
				}

				createBar(option);
			}
		});
		
		
	}
	

	function initPieBatch()
	{
		option = {
				seriesName:	'<spring:message code="pivas.yzyd.zxbc"/>',
				data:[{value:0,itemStyle:{normal:{label:{show:false},labelLine:{show:false}}}}],
		        domId:'main3',
		        clickCall:clickPieBatch
			};
			var searchEndTime = $('#searchEndTime').val();
			var searchStartTime = $('#searchStartTime').val();
			var singleState = $('#singleStateID').val();
			var medicCategorystr = $("#medicCategorySearchID").selectedValuesString();
			var medicLabelstr = $("#medicLabelSearchID").selectedValuesString();
			var batchSearchIDStr = $("#batchSearchID").selectedValuesString();
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/statistics/drugUse/queryBatchPieList',
				dataType : 'json',
				cache : false,
				data : [{name : 'searchStartTime',value :searchStartTime},
				        {name : 'searchEndTime',value :searchEndTime},
				        {name : 'singleState', value : singleState},
				        {name : 'searchBatchs',value :batchSearchIDStr},
				        {name : 'medicCategorys',value :medicCategorystr},
				        {name : 'medicLabels',value :medicLabelstr},
				        {name : 'compareResult', value: compareTime(searchStartTime)}],
				success : function(data) {
					$.each(data,function(index,value){
						option.data.push(value);
					});

					myChart = createPie(option);
				}
			});
		
	}
	
	function initPieDeptName()
	{
		option = {
				seriesName:	'<spring:message code="pivas.yz2.deptname"/>',
				data:[
					{value:0,itemStyle:{normal:{label:{show:false},labelLine:{show:false}}}}
		            ],
		        domId:'main3',
		        clickCall:clickPieDept
			};
			var searchEndTime = $('#searchEndTime').val();
			var searchStartTime = $('#searchStartTime').val();
			var singleState = $('#singleStateID').val();
			var deptNameSearchstr = $("#deptNameSearchID").selectedValuesString();
			var medicCategorystr = $("#medicCategorySearchID").selectedValuesString();
			var medicLabelstr = $("#medicLabelSearchID").selectedValuesString();
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/statistics/drugUse/queryDeptPieList',
				dataType : 'json',
				cache : false,
				data : [{name : 'searchStartTime',value :searchStartTime},
				        {name : 'searchEndTime',value :searchEndTime},
				        {name : 'singleState', value : singleState},
				        {name : 'searchDeptNames',value :deptNameSearchstr},
				        {name : 'medicCategorys',value :medicCategorystr},
				        {name : 'medicLabels',value :medicLabelstr},
				        {name : 'compareResult', value: compareTime(searchStartTime)}],
				success : function(data) {
					$.each(data,function(index,value){
						option.data.push(value);
					});
					myChart = createPie(option);
				}
			});
		
		
	}
	
	function clickPieBatch(param) {
		
		var singleState = $('#singleStateID').val();
		var searchEndTime = $('#searchEndTime').val();
		var searchStartTime = $('#searchStartTime').val();
		var medicCategorystr = $("#medicCategorySearchID").selectedValuesString();
		var medicLabelstr = $("#medicLabelSearchID").selectedValuesString();
		if (myChart2)
		{
			myChart2.clear();
			myChart2 = null;
		}
		
		var option = {
				domId:'main4',
				legendNames:[],
		        colorList:[],
		        radius:'40%'
			};
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/drugUse/queryBatchDrugClassPieList',
			dataType : 'json',
			cache : false,
			data : [{name : 'batchID',value :param.data.zxbc},
			        {name : 'searchStartTime',value :searchStartTime},
			        {name : 'searchEndTime',value :searchEndTime},
			        {name : 'singleState', value : singleState},
			        {name : 'medicCategorys',value :medicCategorystr},
			        {name : 'medicLabels',value :medicLabelstr},
			        {name : 'compareResult', value: compareTime(searchStartTime)}],
			success : function(data) 
			{
				if(data != null)
				{
					$.each(data,function(index,value){
						option.legendNames.push(value.name);
					});
				}
				
				option.data = data;
				
				option.seriesName = param.name;
				option.formatter = function(a)
				{
					var ret = "";
					ret = a["0"] + "<br/>";
					ret += a["5"]["name"];
					ret += ":" + "(<spring:message code='druguse.num'/>:"+ a["5"]["value"] + ",<spring:message code='druguse.cost'/>:" + a["5"]["ststsCost"] + "<spring:message code='druguse.unit'/>)";
		        	ret += "<br/>";
		        	
					return ret;
				}
				
			
				myChart2 = createPie(option);
			}
		});
	}
	
	function clickPieDept(param) {
		
		var singleState = $('#singleStateID').val();
		var searchEndTime = $('#searchEndTime').val();
		var searchStartTime = $('#searchStartTime').val();
		var medicCategorystr = $("#medicCategorySearchID").selectedValuesString();
		var medicLabelstr = $("#medicLabelSearchID").selectedValuesString();
		if (myChart2)
		{
			myChart2.clear();
			myChart2 = null;
		}

		
		var option = {
			domId:'main4',
			legendNames:[],
	        colorList:[],
	        radius:'40%'
		};
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/drugUse/queryDeptDrugClassPieList',
			dataType : 'json',
			cache : false,
			data : [{name : 'deptName',value :param.data.name},
			        {name : 'searchStartTime',value :searchStartTime},
			        {name : 'searchEndTime',value :searchEndTime},
			        {name : 'singleState', value : singleState},
			        {name : 'medicCategorys',value :medicCategorystr},
			        {name : 'medicLabels',value :medicLabelstr},
			        {name : 'compareResult', value: compareTime(searchStartTime)}],
			success : function(data) {
				
				if(data != null){
					$.each(data,function(index,value){
						option.legendNames.push(value.name);
					});
				}

				option.data = data;
				
				option.seriesName = param.name;
				option.formatter = function(a)
				{
					var ret = "";
					ret = a["0"] + "<br/>";
					ret += a["5"]["name"];
					ret += ":" + "(<spring:message code='druguse.num'/>:"+ a["5"]["value"] + ",<spring:message code='druguse.cost'/>:" + a["5"]["ststsCost"] + "<spring:message code='druguse.unit'/>)";
		        	ret += "<br/>";
		        	
		        	return ret;
				}
	        	
				myChart2 = createPie(option);
			}
		});
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
		
		$('#searchEndTime').val(currentDate);
		dateFmt = 'yyyy-MM';
		currentDate = getCurrentDate(dateFmt, null, 0);
		$('#searchStartTime').val(currentDate + '-01');
		changeState();
	}
	
	function changeStatisticsType()
	{
		var type = $("#statisticsType").val();
		if ("0" == type)
		{
			statisticsType = 'DrugUse';
		}
		
		if ("1" == type)
		{
			statisticsType = 'DrugUseAmount';
		}
		
		if ("2" == type)
		{
			statisticsType = 'DrugUsePrice';
		}
		toBirt();
	}
	
		
	var english = true;
	var contextPath;
	$(function() {
		$("#searchDataType").hide();
	    english = "zh-cn" == bLanguage ? false:true;
	    
	    changeDateType();

	    contextPath = '${pageContext.request.contextPath}';
		initEcharts(contextPath);
		
		toBirt();
		
		$("#batchSearchID").multiSelect({ "selectAll": false,"noneSelected": "选择批次","oneOrMoreSelected":"选择批次" },function(){
			changeState();
		});
		
		$("#deptNameSearchID").multiSelect({ "selectAll": false,"noneSelected": "选择病区","oneOrMoreSelected":"选择病区" },function(){
			changeState();
		});
		
		$("#medicCategorySearchID").multiSelect({ "selectAll": false,"noneSelected": "选择药品分类","oneOrMoreSelected":"选择药品分类" },function(){
			changeState();
		});
		
		$("#medicLabelSearchID").multiSelect({ "selectAll": false,"noneSelected": "选择药品标签","oneOrMoreSelected":"选择药品标签" },function(){
			changeState();
		});
	});
	
function changeSearchDataType()
{
	var searchDataType = $('#searchDataType').val();
	
	if(searchDataType == 0){
		$("#batchSearchDivID").show();
		$("#deptNameSearchDivID").hide();
    }else{
		$("#batchSearchDivID").hide();
		$("#deptNameSearchDivID").show();
    }
    
	changeState();
}

function changeSingleState()
{
	changeState();
}

function exportExcel()
{
	if($(window.frames["frame1"].document).find("table .style_8 tbody").children().length>5){
		var exportDoc = document.getElementById("export");
		var url = $("#birtReport iframe").attr("src");
		if (url)
		{
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
		<input id="flowStatusID" type="hidden"/>
		<table class="report-search">
			<tr>
				<td>
					<div style="float: left;border: 1px solid #ccc;" class="ui-search-btn ui-btn-bg-green"   onclick="toBirt();" id="birtImg"><i class="am-icon-table"></i><span>表格</span></div>
					<div style="float: left;border: 1px solid #ccc;" class="ui-search-btn"  onclick="toBar();" id="barImg" ><i class="am-icon-bar-chart"></i><span>图表</span></div>
					<div style="float: left;border: 1px solid #ccc;"  class="ui-search-btn" onclick="toPie();" id="pieImg" ><i class="am-icon-pie-chart"></i><span>饼图</span></div>
					<shiro:hasPermission name="PIVAS_BTN_583">
						<div style="float: left;border: 1px solid #ccc;" onclick="exportExcel();"  class="ui-search-btn ui-btn-bg-green" id="exportImg"><i class="am-icon-download"></i><span>下载</span></div>
					</shiro:hasPermission>
				</td>
			</tr>
			<tbody class="ui-tool-right">
			<tr>
			    <td><span>药单状态:</span></td>
				<td>
					<select id="singleStateID" style="height: 26px"  onchange="changeSingleState()">
						<option value="0">扫描</option>
						<option value="1">非扫描</option>
					</select>			
				</td>
				
				<td><span>统计维度:</span></td>
				<td>
					<input id="statisticsTypeID" type="hidden"/>
					<select id="statisticsType" onchange="changeStatisticsType()" style="height: 26px">
						<option value="0">全部</option>
						<option value="1">数量</option>
						<option value="2">金额</option>
					</select>
				</td>
				
				<td><span><spring:message code="MedicSingle.statisticalCycle"/>:</span></td>
				<td>
					<input type="text" style="height: 26px;width: 100px;" id="searchStartTime" name="searchStartTime" class="Wdate" maxlength="32" onclick="time()"/>
				</td>
				<td>
					<span>~</span>
					<input type="text" style="height: 26px;width: 100px;" id="searchEndTime" name="searchEndTime" class="Wdate" maxlength="32" onclick="time()" />
				</td>
				
				<td>
					<select id="searchDataType" id="searchDataType" onchange="changeSearchDataType()" style="height: 26px">
						<option value="0"><spring:message code="pivas.statistics.searchtype.batch"/></option>
						<option value="1"><spring:message code="pivas.statistics.searchtype.inpatientarea"/></option>
					</select>
				</td>

				<td>
					<div id="batchSearchDivID">
						<select id="batchSearchID" name="batchSearch[]" multiple="multiple" size="5" style="width:100px;">
							<c:forEach items="${batchList}" var="batchItem" varStatus="batchStatus">
								<option value="${batchItem.id}">${batchItem.name}</option>
							</c:forEach>
						</select>			
					</div>
				</td>
				
				<td>
					<div id="deptNameSearchDivID">
						<select id="deptNameSearchID" name="deptNameSearch[]" multiple="multiple" size="5" style="width:100px;">
							<c:forEach items="${inpatientAreaList}" var="inpatientArea" varStatus="deptStatus">
								<option value="${inpatientArea.deptCode}">${inpatientArea.deptName}</option>
							</c:forEach>
						</select>			
					</div>
				</td>
				
				<td>
					<div id="medicCategoryDivID">
						<select id="medicCategorySearchID" name="medicCategorySearch[]" multiple="multiple" size="5" style="width:100px;">
							<c:forEach items="${medicCategoryList}" var="medicCategory" varStatus="category">
								<option value="${medicCategory.categoryId}">${medicCategory.categoryName}</option>
							</c:forEach>
						</select>			
					</div>
				</td>
				
				<td>
					<div id="medicLabelSearchDivID">
						<select id="medicLabelSearchID" name="medicLabelSearch[]" multiple="multiple" size="5" style="width:100px;">
							<c:forEach items="${medicLabelList}" var="medicLabel" varStatus="medicLabelStatus">
								<option value="${medicLabel.labelId}">${medicLabel.labelName}</option>
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
		<%--<div style="float: left;border: 1px solid #ccc;" ><img onclick="toBar();" id="barImg" src="${pageContext.request.contextPath}/assets/sysImage/statistics/bar.png"/></div>--%>
		<%--<div style="float: left;border: 1px solid #ccc;" ><img onclick="toPie();" id="pieImg" src="${pageContext.request.contextPath}/assets/sysImage/statistics/pie.png"/></div>	--%>
		<%--<shiro:hasPermission name="PIVAS_BTN_583">--%>
		<%--<div style="float: left;border: 1px solid #ccc;" id="exportImg"><img onclick="exportExcel();" src="${pageContext.request.contextPath}/assets/sysImage/statistics/export.png"/></div>--%>
		<%--</shiro:hasPermission>--%>
	<%--</div>--%>
	

	<div id="birtReport" style="margin-top:10px;">
		 <iframe name="frame1" width="100%" height="450" frameborder="0" src="" style="margin-top: 10px;margin-left: 0px"></iframe>
	</div>


	<div id="barDiv" style="height:400px;" width="500px;"></div>

	
	<div style="float:left;width:100%;height:400px;" id="pieDiv">
		<div id="main3" style="height:400px;width:45%;display:inline-block;"></div>
		<div id="main4" style="height:400px;width:45%;display:inline-block;"></div>
	</div>
    
    <script type="text/javascript">
           
    </script>
    
    <form method="post" id="export"></form>
</body>
<script type="text/javascript">
function createBar(option,unit)
{
	var config = {
		domId:"main",
		legendNames:[],
		seriesData:[],
		xAxisData:[],
		costData:{}
	};
			
	var op = $.extend(config, option); 

	option = {
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {         
		            type : 'shadow'
		        },
		        formatter:function(a)
		        {
		        	  //console.log(JSON.stringify(a));
		        	  var ret = "";
		        	  var str1 = "";
		        	  var str2 = "";
		        	  var key = "";
		        	  var cost = "0";
		        	  $.each(a, function(k,v)
		        	  {
		        		  if (k == 0)
		        		  {
		        			 str1 = a[k]["1"];
			        	  	 ret += a[k]["1"] + "<br/>"
		        	      } 
		        		  
		        		  str2 = a[k]["0"];
		        		  key = str1 + "," + str2;
		        		  
		        		  if (op.costData[key])
		        		  {
		        	         cost = op.costData[key];
		        		  }
		        		  else
		        		  {
		        			 cost = "0";  
		        		  }
		        		  
			        	  ret += a[k]["0"];
			        	  ret += ":" + "(<spring:message code='druguse.num'/>:"+ a[k]["2"] + ",<spring:message code='druguse.cost'/>:" + cost + "<spring:message code='druguse.unit'/>)";
			        	  ret += "<br/>";
		        	  });
		        	  
		              return ret;
		        }
		    },
		    legend: {
		        data:op.legendNames
		    },
		    calculable : true,
		    xAxis : [
		        { 
		            type : 'category',
		            data : op.xAxisData
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series:op.seriesData
		};	
	
	var myChart = echarts.init(document.getElementById(op.domId));
	myChart.setOption(option);
	
	return myChart;
}
</script>
</html>