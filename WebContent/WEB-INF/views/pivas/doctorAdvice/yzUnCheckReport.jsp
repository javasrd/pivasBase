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
</style>
<script type="text/javascript">
	var bLanguage = '${sessionScope.language}' === 'zh' ? 'zh-cn' : 'en';
</script>
<script src="${pageContext.request.contextPath}/assets/echarts/echarts.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/echarts/echartsUtil.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/multDropList/multDropList.js" type="text/javascript"></script>
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
		$("#barDiv").hide();
		$("#pieDiv").hide();
		$("#birtReport").show();
		
		//$("#birtReport iframe").attr("src", contextPath + "/frameset?__report=report/prescription.rptdesign&__format=HTML&__showtitle=false&__format=xlsx&__parameterpage=false");
		var statisticsTime = $('#statisticsTime').val();
		var url = contextPath + "/frameset?__report=report/yzUnCheck.rptdesign&__format=HTML&__showtitle=false&__format=xlsx&__parameterpage=false";
		if(statisticsTime != null && statisticsTime !== ''){
			url += "&statisticsTime=" + statisticsTime;
		}
		$("#birtReport iframe").attr("src", url);
		clear(myChart2);
		clear(myChart);
	}
	
	function toBar()
	{
		$("#birtReport").hide();
		$("#birtReport iframe").attr("src","");
		
		$("#barDiv").show();
		$("#pieDiv").hide();
		clear(myChart2);
		clear(myChart);
		
	    //initBar();
		queryBatchBar();
	}
	
	
	function toPie()
	{
		$("#birtReport").hide();
		$("#birtReport iframe").attr("src","");
		$("#barDiv").hide();
		$("#pieDiv").show();
		
		clear(myChart);
		clear(myChart2);

		//initPie();
		initPieBatch();
	}
	
	
	/**
	*按批次查询药单状态
	*/
	function queryBatchBar()
	{
		var option = {
			domId:"barDiv",
			legendNames:['<spring:message code="MedicSingle.status.noraml"/>','<spring:message code="MedicSingle.status.stop"/>'],
			xAxisData:[],
			seriesData:[]
		};
		
		var normalStatus = {
	            name:'<spring:message code="MedicSingle.status.noraml"/>',
	            type:'bar',
	            itemStyle: {
	                normal: {
	                    color: '#FFB880'
	                }
	        	},
	            stack: ' ',
	            data:[335,120,22,33,44,66]
	        };
		var normalStatus = {
	            name:'<spring:message code="MedicSingle.status.noraml"/>',
	            type:'bar',
	            itemStyle: {
	                normal: {
	                    color: '#FFB880'
	                }
	        	},
	            stack: ' ',
	            data:[335,120,22,33,44,66]
	        };
		var stopStatus = {
	            name:'<spring:message code="MedicSingle.status.stop"/>',
	            type:'bar',
	            itemStyle: {
	                normal: {
	                    color: '#D97980'
	                }
	        	},
	            stack: ' ',
	            data:[310,120,22,33,44,88]
	        };
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryBatchStatusBar',
			dataType : 'json',
			cache : false,
			data : [],
			success : function(data) {
				//alert(data.batchNameList);
				//option.xAxisData.push(data.batchNameList);
				if(data.batchNameList != null){
					$.each(data.batchNameList,function(index,value){
						option.xAxisData.push(value);
					});
				}
				
				if(data.status2BatchList != null){
					$.each(data.status2BatchList,function(index,value){
						//alert(value.statusKey);
						//alert(value.ydBatchCountList);
						if(value.statusKey == 0){
							normalStatus.data = value.ydBatchCountList;
							option.seriesData.push(normalStatus);
						}else{
							stopStatus.data = value.ydBatchCountList;
							option.seriesData.push(stopStatus);
						}
					});
				}

				createBar(option);
			}
		});
		
		
	}
	

	function initPieBatch()
	{
		option = {
			seriesName:	' ',
			data:[
	            ],
	        domId:'main3',
	        clickCall:clickPie
		};
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryBatchPieList',
			dataType : 'json',
			cache : false,
			data : [],
			success : function(data) {
				option.data = data;
				myChart = createPie(option);
			}
		});
		
		
	}
	
	function clickPie(param) {
		// 释放资源重新创建
		if (myChart2)
		{
			myChart2.clear();
			myChart2 = null;
		}

		
		var option = {
			seriesName:	'<spring:message code="MedicSingle.ydStatus"/>',
	        domId:'main4',
	        legendNames:['<spring:message code="MedicSingle.status.noraml"/>',
	                     '<spring:message code="MedicSingle.status.stop"/>'],
	        colorList:['#FFB880','#D97980'],
	        data:[

	            ],
	       radius:'40%'
		};
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryBatchStatusPieList',
			dataType : 'json',
			cache : false,
			data : [{name : 'batchID',value :param.data.zxbc}],
			success : function(data) {
				option.data = data;
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
		
		WdatePicker({dateFmt:dateFmt,readOnly:true});
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
	}
	
		
	var english = true;
	var contextPath;
	$(function() {
	    english = "zh-cn" == bLanguage ? false:true;
	    
	    // 设置默认统计时间
	    changeDateType();

		//querySelectData();

	    contextPath = '${pageContext.request.contextPath}';
		// 初始化echarts
		initEcharts(contextPath);
		
		toBirt();
	});
	

</script>
</head>
<body>
	<div id="filterCondition" class="header">
		<table>
			<tr>
				<td><span><spring:message code="MedicSingle.statisticalCycle"/>:</span></td>
				<td>
					<select id="dateType" onchange="changeDateType()" style="height: 20px">
						<option value="0"><spring:message code="common.month"/></option>
						<option value="1"><spring:message code="common.day"/></option>
					</select>
				</td>
				
				<td>
					<input type="text" id="statisticsTime" name="statisticsTime" class="Wdate" maxlength="32" onclick="time()"/>
				</td>
				
				<td>
					<input type="text" id="batchSelect" value=""/>
					<input type="hidden" Id="batchSelectVal" value="" /> 
				</td>
				
				<td>
					<input type="text" id="inpatientAreaSelect" value=""/>
					<input type="hidden" Id="inpatientAreaSelectVal" value="" /> 
				</td>
				
				<td>
					<div>  

					</div>  
				</td>
			</tr>
		</table>
	</div>

	<div class="header">
		<span><img onclick="toBirt();" src="${pageContext.request.contextPath}/assets/sysImage/statistics/table.png"/></span><span><img onclick="toBar();" src="${pageContext.request.contextPath}/assets/sysImage/statistics/bar.png"/></span><span><img onclick="toPie();" src="${pageContext.request.contextPath}/assets/sysImage/statistics/pie.png"/></span>
	</div>
	

	<div id="birtReport">
		 <iframe width="800" height="450" frameborder="0" src=""></iframe>
	</div>


	<div id="barDiv" style="height:400px;" width="500px;"></div>

	
	<div style="float:left;width:100%;height:400px;" id="pieDiv">
		<div id="main3" style="height:400px;width:45%;display:inline-block;"></div>
		<div id="main4" style="height:400px;width:45%;display:inline-block;"></div>
	</div>

    
    <script type="text/javascript">
           
    </script>
</body>
</html>