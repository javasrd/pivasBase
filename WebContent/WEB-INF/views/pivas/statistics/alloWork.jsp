<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		$("#birtImg").attr("src","${pageContext.request.contextPath}/assets/sysImage/statistics/table2.png");
		$("#barImg").attr("src","${pageContext.request.contextPath}/assets/sysImage/statistics/bar.png");
		$("#pieImg").attr("src","${pageContext.request.contextPath}/assets/sysImage/statistics/pie.png");
		
		$("#exportImg").show();
		$("#goBack").hide();
		$("#barDiv").hide();
		$("#pieDiv").hide();
		$("#birtReport").show();
		$("#flowStatusID").val("0");
		
		var statisticsTime = $('#statisticsTime').val();
		var doctorSearchIDStr = $("#doctorSearchID").selectedValuesString();
		var compareResult = compareTime(statisticsTime);
		
		var url = contextPath + "/frameset?__report=report/AllowWork.rptdesign&__parameterpage=false&__navigationbar=true&__toolbar=false&__locale=" + bLanguage;
		
		url += "&searchTime="
		if(statisticsTime != null && statisticsTime !== ''){
			url += statisticsTime;
			
			var title = "&title=" + '配置工作量统计' + '('+statisticsTime + ')';
			url += encodeURI(title);
		}
		
		url += "&searchDoctor=";
		if (doctorSearchIDStr != null && doctorSearchIDStr !== '') {
			url += doctorSearchIDStr;
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
		$("#birtImg").attr("src","${pageContext.request.contextPath}/assets/sysImage/statistics/table.png");
		$("#barImg").attr("src","${pageContext.request.contextPath}/assets/sysImage/statistics/bar2.png");
		$("#pieImg").attr("src","${pageContext.request.contextPath}/assets/sysImage/statistics/pie.png");
		
		$("#exportImg").hide();
		$("#goBack").hide();
		$("#birtReport").hide();
		$("#birtReport iframe").attr("src","");
		$("#flowStatusID").val("1");
		$("#barDiv").show();
		$("#pieDiv").hide();
		
		clear(myChart2);
		clear(myChart);
		
	  	queryBar();
	}
	
	
	function toPie()
	{	
		$("#birtImg").attr("src","${pageContext.request.contextPath}/assets/sysImage/statistics/table.png");
		$("#barImg").attr("src","${pageContext.request.contextPath}/assets/sysImage/statistics/bar.png");
		$("#pieImg").attr("src","${pageContext.request.contextPath}/assets/sysImage/statistics/pie2.png");
		
		$("#exportImg").hide();
		$("#boBack").hide();
		$("#birtReport").hide();
		$("#birtReport iframe").attr("src","");
		$("#barDiv").hide();
		$("#pieDiv").show();
		clear(myChart);
		clear(myChart2);
		$("#flowStatusID").val("2");
		// 数据过滤类型
		initPie();
	}
	
	function changeState()
	{
	    var flowStatusID = $("#flowStatusID").val();
	    if(flowStatusID == 1){
	        toBar();
	    }else if(flowStatusID == 2){
	        toPie();
	    }else if(flowStatusID == 0){
	    	toBirt();
	    }
	}
	
	/**
	* 查询配置量信息
	*/
	function queryBar()
	{
		var option = {
			data:[],
			domId:"barDiv",
			legendNames:[],
			xAxisData:[],
			seriesData:[],
			costData:{}
		};
		var statisticsTime = $('#statisticsTime').val();
		var doctorSearchIDStr = $("#doctorSearchID").selectedValuesString();
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/alloWork/queryBar',
			dataType : 'json',
			cache : false,
			data : [{name : 'searchTime',value :statisticsTime},
			        {name : 'searchDoctorIds',value :doctorSearchIDStr},
			        {name : 'compareResult', value: compareTime(statisticsTime)}],
			success : function(data) {
				if(data.pzMcList != null){
					$.each(data.pzMcList,function(index,value){
						option.xAxisData.push(value);
					});
				}
				
				if(data.ruleNameList != null){
					$.each(data.ruleNameList,function(index,value){
						option.legendNames.push(value);
					});
				}
				
				if(data.alloWork2BarList != null){
					$.each(data.alloWork2BarList,function(index,value){
						var bar = {
					            name:'',
					            type:'bar',
					            stack: ' ',
					            data:[]
					        };
						bar.name = value.ruleName;
						bar.data = value.ydCountList;
						option.seriesData.push(bar);
					});
					
					option.costData = data.costMap;
				}

				createBar(option);
			}
		});
		
		
	}
	

	function initPie()
	{
		option = {
				seriesName:	'配置药师',
				data:[{value:0,itemStyle:{normal:{label:{show:false},labelLine:{show:false}}}}],
		        domId:'main3',
		        clickCall:clickPie
			};
			var statisticsTime = $('#statisticsTime').val();
			var doctorSearchIDStr = $("#doctorSearchID").selectedValuesString();

			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/alloWork/queryPieList',
				dataType : 'json',
				cache : false,
				data : [{name : 'searchTime',value :statisticsTime},
				        {name : 'searchDoctorIds',value :doctorSearchIDStr},
				        {name : 'compareResult', value: compareTime(statisticsTime)}],
				success : function(data) {
					$.each(data,function(index,value){
						option.data.push(value);
					});

					myChart = createPie(option);
				}
			});
		
	}
	
	
	function clickPie(param) {
		var statisticsTime = $('#statisticsTime').val();
		// 释放资源重新创建
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
			url : '${pageContext.request.contextPath}/alloWork/queryDetailPieList',
			dataType : 'json',
			cache : false,
			data : [{name : 'searchTime',value :statisticsTime},
			        {name : 'searchDoctorId', value :param.data.pzCode},
			        {name : 'compareResult', value: compareTime(statisticsTime)}],
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
					ret += ":" + "(<spring:message code='druguse.num'/>:"+ a["5"]["value"] + ",<spring:message code='druguse.cost'/>:" + a["5"]["cost"] + "<spring:message code='druguse.unit'/>)";
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
		$('#statisticsTime').val(currentDate);
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
		
		toBirt();
		
		toBirt();

		$("#doctorSearchID").multiSelect({
			"selectAll" : false,
			"noneSelected" : "选择配置药师",
			"oneOrMoreSelected" : "选择配置药师"
		}, function() {
			changeState();
		});
	});
	
function changeSearchDataType()
{
	var searchDataType = $('#searchDataType').val();
	
    //按批次
	if(searchDataType == 0){
		$("#batchSearchDivID").show();
		$("#deptNameSearchDivID").hide();
    }else{
		$("#batchSearchDivID").hide();
		$("#deptNameSearchDivID").show();
    }
    
	changeState();
}

function exportExcel()
{
	if($(window.frames["frame1"].document).find("table .style_6 tbody").children().length>4){
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
		message({html: "表格无数据，未导出Excel"});
	}
}
</script>
</head>
<body>
	<div id="filterCondition" class="header">
		<table>
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
					<input type="text" style="height: 26px;width: 100px;" id="statisticsTime" name="statisticsTime" class="Wdate" maxlength="32" onclick="time()"/>
				</td>
				 
				<td>
					<div id="doctorSearchDivID" style="margin-left: 10px;">
						<select id="doctorSearchID" name="doctorSearch[]"
							multiple="multiple" size="5" style="width:100px;height: 23px">
							<c:forEach items="${doctorNameList}" var="doctorItem"
								varStatus="batchStatus">
								<c:if test="${not empty doctorItem.doctorName}">
									<option value="${doctorItem.doctorId}">${doctorItem.doctorName}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
		</table>
	</div>

	<div class="header">
		<div style="float: left;border: 1px solid #ccc;" ><img onclick="toBirt();" id="birtImg" src="${pageContext.request.contextPath}/assets/sysImage/statistics/table2.png"/></div>
		<div style="float: left;border: 1px solid #ccc;" ><img onclick="toBar();" id="barImg" src="${pageContext.request.contextPath}/assets/sysImage/statistics/bar.png"/></div>
		<div style="float: left;border: 1px solid #ccc;" ><img onclick="toPie();" id="pieImg" src="${pageContext.request.contextPath}/assets/sysImage/statistics/pie.png"/></div>
		
		<shiro:hasPermission name="PIVAS_BTN_723">
		<div style="float: left;border: 1px solid #ccc;" id="exportImg"><img onclick="exportExcel();" src="${pageContext.request.contextPath}/assets/sysImage/statistics/export.png"/></div>
		</shiro:hasPermission>
		
		<div style="float: left;border: 1px solid #ccc;" id="goBack"><img onclick="toBirt();" src="${pageContext.request.contextPath}/assets/sysImage/statistics/goBack.png"/></div>
	</div>
	
	<div id="birtReport" style="margin-top:40px;">
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