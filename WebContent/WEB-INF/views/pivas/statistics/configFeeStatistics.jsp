<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="../../common/common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
.header {
margin: 10px 0px 10px 0px;
}
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
		$("#flowStatusID").val("0");
		
		var userLanguage = '${sessionScope.language}' === 'en' ? 'en_US' : 'zh';
		//$("#birtReport iframe").attr("src", contextPath + "/frameset?__report=report/prescription.rptdesign&__format=HTML&__showtitle=false&__format=xlsx&__parameterpage=false");
		var statisticsTime = $('#statisticsTime').val();
		var batchSearchIDStr = $("#batchSearchID").selectedValuesString();
		var deptNameSearchstr = $("#deptNameSearchID").selectedValuesString();
		
		var url = contextPath + "/run?__report=report/configfee.rptdesign&__parameterpage=false&__toolbar=false&__locale=" + userLanguage;
		
		if(compareTime(statisticsTime) == 0)
		{
			url = contextPath + "/run?__report=report/configfeeHis.rptdesign&__parameterpage=false&__toolbar=false&__locale=" + userLanguage;
		}
		
		
		url += "&searchTime="
		if(statisticsTime != null && statisticsTime !== ''){
			url += statisticsTime;
			
			var titel = "&title=" + '<spring:message code="pivas.configfee.title"/>' + '('+statisticsTime + ')';
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
		
		var user = "&user=" + '<spring:message code="pivas.statistics.tab"/>' + '<spring:escapeBody htmlEscape="true"><shiro:principal property="name"/></spring:escapeBody>';
		url += encodeURI(user);
		$("#birtReport iframe").attr("src", url);
		clear(myChart2);
		clear(myChart);
		$("#exportImg").show();
	}

	function toBar()
	{
        $("#birtImg").removeClass("ui-btn-bg-green");
        $("#barImg").addClass("ui-btn-bg-green");
        $("#pieImg").removeClass("ui-btn-bg-green");
		
		$("#birtReport").hide();
		$("#birtReport iframe").attr("src","");
		
		$("#barDiv").show();
		$("#pieDiv").hide();
		$("#stDimValueDivID").show();
		$("#flowStatusID").val("1");
		$("#ydStatisticsType").css('display','inline-block');
		$("#ydStatisticsType").css('height','26');
		$("#ydStatisticsType").parent().find(".ui-combobox").css('display','none');
		//$("#ydStatisticsType").parent().find(".ui-combobox").css("border","1px solid #ccc");
		//("#ydStatisticsType").parent().find(".ui-combobox").re
		
		clear(myChart2);
		clear(myChart);
		
		
	    //initBar();
	    var ydStatisticsType = $('#ydStatisticsType').val();
	  //按批次
	    if(ydStatisticsType == 0){
			$("#batchSearchDivID").show();
			$("#deptNameSearchDivID").hide();
	    	queryBatchBar();
	    }else{
			$("#batchSearchDivID").hide();
			$("#deptNameSearchDivID").show();
	    	queryDeptBar();
	    }
		//queryBatchBar();
		//queryDeptBar();
		
	    $("#exportImg").hide();
	}
	
	
	function toPie()
	{
        $("#birtImg").removeClass("ui-btn-bg-green");
        $("#barImg").removeClass("ui-btn-bg-green");
        $("#pieImg").addClass("ui-btn-bg-green");
		
		$("#birtReport").hide();
		$("#birtReport iframe").attr("src","");
		$("#barDiv").hide();
		$("#pieDiv").show();
		
		$("#stDimValueDivID").show();
		$("#flowStatusID").val("2");
		$("#ydStatisticsType").parent().find(".ui-combobox").css("border","1px solid #ccc");

		
		clear(myChart);
		clear(myChart2);
		var ydStatisticsType = $('#ydStatisticsType').val();
	    //按批次
		if(ydStatisticsType == 0){
			$("#batchSearchDivID").show();
			$("#deptNameSearchDivID").hide();
	    	initPieBatch();
	    }else{
			$("#batchSearchDivID").hide();
			$("#deptNameSearchDivID").show();
	    	initPieDept();
	    }
		//initPie();
		//initPieBatch();
		//initPieDept();
		
		$("#exportImg").hide();
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

	function queryBatchBar()
	{
		var option = {
			domId:"barDiv",
			legendNames:[],
			xAxisData:[],
			seriesData:[]
		};

		var statisticsTime = $('#statisticsTime').val();
		var batchSearchIDStr = $("#batchSearchID").selectedValuesString();
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryConfigFeeBatchStatusBar',
			dataType : 'json',
			cache : false,
			data : [{name : 'searchTime',value :statisticsTime},
			        {name : 'searchBatchs',value :batchSearchIDStr},
			        {name : 'compareResult', value: compareTime($('#statisticsTime').val())}],
			success : function(data) {
			    
				//option.xAxisData.push(data.batchNameList);
				if(data.batchNameList != null && data.batchNameList.length > 0){
					$.each(data.batchNameList,function(index,value){
						option.xAxisData.push(value);
					});

				if(data.status2BatchList != null){
					$.each(data.status2BatchList,function(index,value){
						var configFeeType = {
					            name:'',
					            type:'bar',
					            stack: ' ',
					            data:[]
					        };
						var configFreeTypes = $('#configFreeType option[value=' + value.statusKey +']').text();
			
						option.legendNames.push(configFreeTypes);
						configFeeType.name = configFreeTypes;
						configFeeType.data = value.cfBatchCountList;
						option.seriesData.push(configFeeType);

					});
				}
				}

				createBar(option,'元');
			}
		});
		
		
	}
	
	/**
	*按病区查询药单状态
	*/
	function queryDeptBar()
	{
		var option = {
			domId:"barDiv",
			legendNames:[],
			xAxisData:[],
			seriesData:[]
		};

		var statisticsTime = $('#statisticsTime').val();
		var deptNameSearchstr = $("#deptNameSearchID").selectedValuesString();
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryConfigFeeDeptStatusBarData',
			dataType : 'json',
			cache : false,
			data : [{name : 'searchTime',value :statisticsTime},
			        {name : 'searchDeptNames',value :deptNameSearchstr},
			        {name : 'compareResult', value: compareTime($('#statisticsTime').val())}],
			success : function(data) {
				
				//option.xAxisData.push(data.batchNameList);
				if(data.deptNameList != null && data.deptNameList.length > 0){
					$.each(data.deptNameList,function(index,value){
						option.xAxisData.push(value);
					});
				
				
				if(data.status2DeptList != null){
					$.each(data.status2DeptList,function(index,value){
						//alert(value.statusKey);
						//alert(value.ydBatchCountList);
						var configFeeType = {
					            name:'',
					            type:'bar',
					            stack: ' ',
					            data:[]
					        };
						var configFreeTypes = $('#configFreeType option[value=' + value.statusKey +']').text();
						option.legendNames.push(configFreeTypes);
						configFeeType.name = configFreeTypes;
						configFeeType.data = value.cfDeptCountList;
						option.seriesData.push(configFeeType);

					});
				}
				}

				createBar(option,'元');
			}
		});
		
		
	}
	

	function initPieBatch()
	{
		option = {
			seriesName:	' ',
			data:[{value:0,itemStyle:{normal:{label:{show:false},labelLine:{show:false}}}} 
	            ],
	        domId:'main3',
	        clickCall:clickPieBatch
		};
		var statisticsTime = $('#statisticsTime').val();
		var batchSearchIDStr = $("#batchSearchID").selectedValuesString();
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryConfigFeeBatchPieList',
			dataType : 'json',
			cache : false,
			data : [{name : 'searchTime',value :statisticsTime},
			        {name : 'searchBatchs',value :batchSearchIDStr},
			        {name : 'compareResult', value: compareTime($('#statisticsTime').val())}],
			success : function(data) {
				$.each(data,function(index,value){
					option.data.push(value);
				});
				myChart = createPie(option,"元");
			}
		});
		
		
	}
	
	function clickPieBatch(param) {
		if (myChart2)
		{
			myChart2.clear();
			myChart2 = null;
		}

		
		var option = {
			seriesName:'配置费类别',
	        domId:'main4',
	        legendNames:[],
	        data:[{value:0,itemStyle:{normal:{label:{show:false},labelLine:{show:false}}}} 

	            ],
	       radius:'40%'
		};
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryConfigFeeBatchStatusPieList',
			dataType : 'json',
			cache : false,
			data : [{name : 'batchID',value :param.data.zxbc},
			        {name : 'searchTime',value :$('#statisticsTime').val()},
			        {name : 'compareResult', value: compareTime($('#statisticsTime').val())}],
			success : function(data) {
				//option.data = data;
				$.each(data,function(index,value){
					var item = {
							   name:'',
							   value:10
							 };
					var configFreeTypes = $('#configFreeType option[value=' + value.statusKey +']').text();
					item.name = configFreeTypes;
					item.value = value.value;
					option.legendNames.push(configFreeTypes);
					option.data.push(item);
				});				
				myChart2 = createPie(option,"元");
			}
		});
	}
	
	function initPieDept()
	{
		option = {
			seriesName:	' ',
			data:[{value:0,itemStyle:{normal:{label:{show:false},labelLine:{show:false}}}}],
	        domId:'main3',
	        clickCall:clickPieDept
		};
		var statisticsTime = $('#statisticsTime').val();
		var deptNameSearchstr = $("#deptNameSearchID").selectedValuesString();
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryConfigFeeDeptPieList',
			dataType : 'json',
			cache : false,
			data : [{name : 'searchTime',value :statisticsTime},
			        {name : 'searchDeptNames',value :deptNameSearchstr},
			        {name : 'compareResult', value: compareTime($('#statisticsTime').val())}],
			success : function(data) {
				$.each(data,function(index,value){
					option.data.push(value);
				});
				
				myChart = createPie(option,"元");
			}
		});
		
		
	}
	
	function clickPieDept(param) {
		if (myChart2)
		{
			myChart2.clear();
			myChart2 = null;
		}

		var option = {
			seriesName:	'配置费类别',
	        domId:'main4',
	        legendNames:[],
	        data:[{value:0,itemStyle:{normal:{label:{show:false},labelLine:{show:false}}}}],
	       radius:'40%'
		};
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryConfigFeeDeptStatusPieList',
			dataType : 'json',
			cache : false,
			data : [{name : 'deptName',value :param.data.name},
			        {name : 'searchTime',value :$('#statisticsTime').val()},
			        {name : 'compareResult', value: compareTime($('#statisticsTime').val())}],
			success : function(data) {
				$.each(data,function(index,value){
					var item = {
							   name:'',
							   value:10
							 };
					var configFreeTypes = $('#configFreeType option[value=' + value.statusKey +']').text();
					
					item.name = configFreeTypes;
					item.value = value.ststsCount;
					option.legendNames.push(configFreeTypes);
					option.data.push(item);
				});				
				myChart2 = createPie(option,"元");
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
	    english = "zh-cn" == userLanguage ? false:true;
	    
	    changeDateType();

		querySelectData();

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
	
function changeCustType(value)
{
	var ydStatisticsType = $('#ydStatisticsType').val();
    //按批次
	if(ydStatisticsType == 0){
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
	if($(window.frames["frame1"].document).find("table .style_8 tbody").children().length>4){
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


// 下拉数据查询
function querySelectData(){
	$.ajax({
		type:'POST',
		url:'${pageContext.request.contextPath}/configFee/querySelectDatareq',
		dataType:'json',
		cache : false,
		success:function(data){
			if(data.success == true){
				var html = [];
				//配置费类别分类
				$.each(data.configFeeTypeList, function(i, d){
					html.push('<option value="'+ d.gid +'">');
					html.push(sdfun.fn.htmlDecode(d.typeDesc));
					html.push('</option>');
				});

				$("#configFreeType").html(html.join(""));
			}
		}
	});
}
</script>
</head>
<body>
	<div id="filterCondition" class="header">
		<table class="report-search">
			<tr>
				<td>
					<div style="float: left;border: 1px solid #ccc;" class="ui-search-btn ui-btn-bg-green"   onclick="toBirt();" id="birtImg"><i class="am-icon-table"></i><span>表格</span></div>
					<div style="float: left;border: 1px solid #ccc;" class="ui-search-btn"  onclick="toBar();" id="barImg" ><i class="am-icon-bar-chart"></i><span>图表</span></div>
					<div style="float: left;border: 1px solid #ccc;"  class="ui-search-btn" onclick="toPie();" id="pieImg" ><i class="am-icon-pie-chart"></i><span>饼图</span></div>
					<shiro:hasPermission name="PIVAS_BTN_563">
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
					<input type="text" style="height: 26px;width:100px;" id="statisticsTime" name="statisticsTime" class="Wdate" maxlength="32" onclick="time()" />
				</td>
				<div> 
					<td>
						<div id="stDimValueDivID" style="width: 130px;margin-left: 10px">
						  <sd:select style="width: 120px;height:26px;"  name="ydStatisticsType" id="ydStatisticsType" required="true" onchange="changeCustType(value)" categoryName="medicSingle.Statistics.type" tableName="sys_dict"></sd:select>
						</div> 
						<div style="display: none">
						  <%--<zchl:select  name="configFreeType" id="configFreeType" required="false" categoryName="pivas.configfee.type" tableName="sys_dict"></zchl:select> --%>
							<select id="configFreeType" name="configFreeType" required="false" style="width: 185px;" readonly "></select>
						</div> 
					
					</td>
				</div> 
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
			</tr>
			</tbody>
		</table>
	</div>

	<%--<div class="header">--%>
		<%--<div style="float: left;border: 1px solid #ccc;" ><img onclick="toBirt();" id="birtImg" src="${pageContext.request.contextPath}/assets/sysImage/statistics/table2.png"/></div>--%>
		<%--<div style="float: left;border: 1px solid #ccc;" ><img onclick="toBar();" id="barImg" src="${pageContext.request.contextPath}/assets/sysImage/statistics/bar.png"/></div>--%>
		<%--<div style="float: left;border: 1px solid #ccc;" ><img onclick="toPie();" id="pieImg" src="${pageContext.request.contextPath}/assets/sysImage/statistics/pie.png"/></div>	--%>
		<%--<shiro:hasPermission name="PIVAS_BTN_563">--%>
		<%--<div style="float: left;border: 1px solid #ccc;" id="exportImg"><img onclick="exportExcel();" src="${pageContext.request.contextPath}/assets/sysImage/statistics/export.png"/></div>--%>
		<%--</shiro:hasPermission>--%>
	<%--</div>--%>
    <div id="birtReport" style="margin-top:10px;">
		 <iframe name="frame1" width="100%" height="550" frameborder="0" src="" style="margin-top: 10px;margin-left: 0px"></iframe>
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
</html>