<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../../common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/assets/multDropList/multDropList.css" rel="stylesheet" type="text/css"/>
<script src="${pageContext.request.contextPath}/assets/common/js/my97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<title></title>

<style type="text/css">
a.multiSelect:link, a.multiSelect:visited, a.multiSelect:hover, a.multiSelect:active {
    color: #666666;
    text-decoration: none;
    height: 26px;
}

#medicTable td{
height:30px;
}

#traceTable td{
height:30px;
}

.ui-widget-header {
    background-color: white;
    color: #222222/*{fcHeader}*/;
    font-weight: bold;
}
.ui-state-active a, .ui-state-active a:link, .ui-state-active a:visited {
    color: white/*{fcActive}*/;
   	background-color:#6E97E4;
}

.ui-tabs .ui-tabs-nav {
    padding: 0em 1em 0em 1em;
    background: whitesmoke;
}
.ui-tabs .ui-tabs-panel {
    padding: 0em 0em 0em 1em;
}
.ui-tabs {
    padding: 0em;
}

.button:hover{
 	color:blue;
}

.labelshadow { 
	position: fixed; 
	top: 0; 
	left: 0; 
	right: 0; 
	bottom: 0; 
	width: 100%; 
	height: 100%; 
	display: block; 
	z-index: 300; 
	background: rgba(255, 255, 255, 1); 
	opacity: 0; 
}

.labelListTitle{
	height:40px;
	width:100%;
	background: #DBE2F2;
	color:#3E3E3E;
	font-size:22px;
	line-height:40px;
	padding-left: 30px;
	font-weight:500;
	padding-right: 30px;
	border-top-left-radius: 10px;
	border-top-right-radius: 10px;
}

.labelListBtn{
	height: 30px;
	width:60px;
	background: #EBB701;
	font-size:14px;
	color:#ffffff;
	border:1px solid #EBB701;
}

#labelListContain{
	color:#000000;
	padding:10px 15px;
}

.backColor{
	background-color:#00FF00 !important;
}

</style>
</head>
<body>

<audio id="sound_success"> <source src ="${pageContext.request.contextPath}/assets/systemMusic/正常.wav" >  </audio>
<audio id="sound_fail"> <source src ="${pageContext.request.contextPath}/assets/systemMusic/cs_wrong.wav" >  </audio>

<div id="mainContainer">
	<table width="100%" height="100%" style="table-layout:fixed;min-width:1200px;border: 0px;margin-top: 15px;">
	<tr>
	<td width="40%" style="border: 0px;vertical-align: top;" id="leftHeight">
		<div id="search" class="search_div" style="height:40px;line-height:40px;min-width: 500px;">
		核对类型：
		<select id="hdlx" class="select_new" style="height: 25px;width:90px;">
			<c:forEach items="${checkTypeList}" var="checkType" varStatus="status">
			<c:choose> 
			<c:when test="${status.first}">
			<option value="${checkType.checkType}" selected="selected" >${checkType.checkName}</option>
			</c:when>
			<c:otherwise>
			<option value="${checkType.checkType}">${checkType.checkName}</option>
			</c:otherwise>
			</c:choose>
			</c:forEach>
		</select>
		用药时间：
		<input type="text" id="yyrq" style="color: #555555; height: 26px; width: 100px;" 
			class="Wdate" empty="false" readonly="readonly" 
			onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:dataPick});">
		批次：
		<select id="batchSelect"  name="batchSelect[]" size="5" style="height: 25px;" multiple="multiple">
			<option value="">--请选择--</option>
			<c:forEach items="${batchList}" var="batch" varStatus="status">
			<c:if test="${batch.enabled==1}">
			<option value="${batch.id}" <c:if test="${status.isFirst()}" >selected</c:if> >${batch.name}</option>
			</c:if>
			</c:forEach>
		</select>
		</div>
		
		<div style="min-width: 500px;border-bottom: 1px dashed;">&nbsp;</div>
		
		<table id="scanModel" width="100%" style="margin-top:10px;margin-bottom:10px;">
			<thead>
			<shiro:hasPermission name="PIVAS_BTN_593">
			<tr>
				<td><input id="scanText" type="text" style="width: 215px;" /></td>
				<td>
				<a class="button_new green_jianbian" id="printReceiverLabel" style="padding:0.55em 15px">打印接收单</a>
				<a class="button_new green_jianbian" id="scanButton" style="padding:0.55em 20px">一键扫描</a>
					<shiro:hasPermission name="PIVAS_BTN_594">
						<a class="button_new green_jianbian" id="print4" style="padding:0.55em 20px;">打印4#接收单</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="PIVAS_BTN_593">
						<input type="checkbox" id="receiverDetail"  style="height: 15px;vertical-align: middle"/>接收单明细
					</shiro:hasPermission>
				</td>
			</tr>
			</shiro:hasPermission>
			<%--<tr>--%>
				<%--<td></td>--%>
				<%--<td>--%>
				<%--<shiro:hasPermission name="PIVAS_BTN_594">--%>
				<%--<a class="button" id="print4" style="padding:0.55em 20px;">打印4#接收单</a>--%>
				<%--</shiro:hasPermission>--%>
				<%--<shiro:hasPermission name="PIVAS_BTN_593">--%>
				<%--<input type="checkbox" id="receiverDetail"  style="margin-top: 15px;height: 15px;"/>接收单明细--%>
				<%--</shiro:hasPermission>--%>
				<%--</td>--%>
			<%--</tr>--%>
			</thead>
		</table>
		
		<div id="resultModel" style="height:150px;background-color: white;">
		<span class="scan-result-tittle">扫描结果：</span>
		<p style="text-align: center;font-size: xx-large;margin: 10px;" id="smResult"></p>
		</div>
		
		<div style="overflow-y:scroll;margin-top: 10px;">
		<table id="medicTitle" width="100%" style="border-spacing: 0px;">
			<thead>
			<tr style="background: #39aeaa;height: 25px;padding-left:10px;">
				<th style="color:white;" width="15%">药品编码</th>
				<th style="color:white;" width="40%">药品名称</th>
				<th style="color:white;" width="20%">规格</th>
				<th style="color:white;" width="15%">剂量</th>
				<th style="color:white;" width="10%">数量</th>
			</tr>
			</thead>
		</table>
		</div>
		<div id="medicModel" style="overflow-y:scroll;height:150px;">
		<table id="medicTable" width="100%" style="border-spacing: 0px;">
			<thead style="display:none;">
			<tr style="padding-left:10px;background: #39aeaa;">
				<th width="15%">药品编码</th>
				<th width="40%">药品名称</th>
				<th width="20%">规格</th>
				<th width="15%">剂量</th>
				<th width="10%">数量</th>
			</tr>
			</thead>
			<tbody id="medicTabBody">
			
			</tbody>
		</table>
		</div>
		<div style="overflow-y:scroll;background: #39aeaa;padding-left:10px;">
		<table id="traceTitle" width="100%" style="border-spacing: 0px;">
			<thead>
			<tr style="height: 25px;">
				<td style="color:white;" width="35%">扫描时间</td>
				<td style="color:white;" width="25%">瓶签号</td>
				<td style="color:white;" width="20%">扫描结果</td>
				<td style="color:white;" width="20%">操作人</td>
			</tr>
			</thead>
		</table>
		</div>
		<div id="traceModel" style="overflow-y:scroll;min-height:150px;">
		<table id="traceTable" width="100%" style="border-spacing: 0px;">
			<thead style="display:none;">
			<tr style="background: #B5B5B5;">
				<th width="35%">扫描时间</th>
				<th width="25%">瓶签号</th>
				<th width="20%">扫描结果</th>
				<th width="20%">操作人</th>
			</tr>
			</thead>
			<tbody id="traceTabBody">
			
			</tbody>
		</table>
		</div>
	</td>
	<td style="border: 0px;vertical-align: top;">
	
	<div id="tabs" style="min-height:500px;background: whitesmoke;margin-top: -7px;">
	<ul>
    <li><a href="#tabs-pq">瓶签列表</a></li>
    <li><a href="#tabs-pc">批次统计</a></li>
    <li><a href="#tabs-stop">停退信息</a></li>
    <li><a class="button" id="refreshButton" style="padding:0.55em 20px;">刷新</a></li>
	</ul>
	<div id="tabs-pq">
	<table id="pqTable" width="100%">


	</table>
	</div>
	<div id="tabs-pc">
	</div>
	<div id="tabs-stop">
	<table id="stopTable" width="100%">


	</table>
	</div>
	</div>
	</td>
	</tr>
	</table>

	<div id="labelListShadow" class="labelshadow" style="background:#000000;opacity:0.3;display:none"></div>
	<div id="labelList" class="labelshadow"  style="background:#ffffff;opacity:1;color:#ffffff;border-radius:10px;display:none">
		<div class="labelListTitle">
			<span style="font-size:16px;font-weight:700;font-family: 微软雅黑;">药物接收单</span>
			<img style="float:right;margin-top: 11px" onclick="closeListShadow()" src="${pageContext.request.contextPath}/assets/pivas/images/close-icon.png">
		</div>
		<div style="margin:15px 15px 10px 15px;text-align: right;">
				<button class="labelListBtn" id="receivePrintBtn">打印</button>
				<input type="hidden" id="isSpecial" />
		</div>
		<div id="labelListContain" style="width:100%;height:83%;overflow-y:auto;">
			<div id="receiverLabelContain" style="width:300px;height:100%;margin: 0 auto;"></div>
		</div>
	</div>
</div>

<input type="hidden" id="day"  value="${day}"/>
</body>
<script type="text/javascript">
var pcStr = "";
var pageHeight = window.innerHeight -30;
var inpatientString = function(){
	return window.parent.getInpatientInfo();
}
var type="tabs-pq";
var tCount;
var pqnum = "";

var shadowWidth=0;
var shadowHeight=0;

$(function(){
	shadowWidth= $(document).width()-100;
	shadowHeight= $(document).height()-100;
	$("#labelList").height(shadowHeight);
	$("#labelList").width(shadowWidth);
	$("#labelList").css("top","50%");
	$("#labelList").css("left","50%");
	$("#labelList").css("margin-top",-shadowHeight/2);
	$("#labelList").css("margin-left",-shadowWidth/2);


	var traceModelHeight = pageHeight - 150 - 150 -200;
	$("#traceModel").height(traceModelHeight);

	$("#scanText").focus();

	var dayFlag = $("#day").val();
	if(dayFlag == "tomorrow"){
		$("#yyrq").val(getCurrentDate("yyyy-MM-dd", null, 1));
	}else{
		$("#yyrq").val(getCurrentDate("yyyy-MM-dd", null, 0));
	}

	$("#batchSelect").multiSelect({ "selectAll": false,"noneSelected": "选择批次","oneOrMoreSelected":"*" },function(){
		pcStr = $("#batchSelect").selectedValuesString();
		loadList();
	});

	var selectBatch = $("input[name='batchSelect[]']:checked").val();
	pcStr = selectBatch;


	$( "#tabs" ).tabs({
		beforeActivate: function( event, ui ) {
			type = ui.newPanel.attr("id");
			loadList();
		}
	});

	var leftHeightTemp = $("#leftHeight").height();
	var leftHeight = pageHeight < leftHeightTemp ? leftHeightTemp : pageHeight;
	$( "#tabs" ).height(leftHeight);

	$("#hdlx").change(function(){
		loadList();
	});


	$("#refreshButton").click(function(){

		loadList();
		$("#scanText").focus();
	});

	$("#scanButton").click(function(){
		message({
			html: '确定一键扫描？',
			showCancel:true,
			confirm:function(){

				var yyrq = $("#yyrq").val();
				var checkType = $("#hdlx").val();

				var params =
				[
					{name:"yyrq",value:yyrq},
					{name:"inpatientStr",value:inpatientString},
					{name:"pcStr",value:pcStr},
					{name:"checkType",value:checkType},
					{name:"scansType",value:$("#hdlx").val()}
				];

				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/scans/ScansResultAll',
					dataType : 'json',
					cache : false,
					data : params,
					success : function(data) {

						$("#smResult").css("color","black");

						if(data.success){
							$("#smResult").text(data.msg);
						}

						//loadList();
						loadSmResult();
					},
					error:function(data){
						message({html:'<spring:message code="common.op.error"/>'});
					}

				});

			}
    	});

	});

	//打印药物接收单
	$("#printReceiverLabel").click(function(){
		openListShadow();
		var isDetail = $("#receiverDetail").is(':checked');

		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/print/qryReciverLabel',
			dataType : 'html',
			cache : false,
			data : [ {name : "useDate", value : $("#yyrq").val()},
			         {name : "batchIds",value : pcStr},
			         {name : "wardCode",value : inpatientString},
			         {name : "isPrint",value : false},
			         {name : "isDetail",value : isDetail}
			         ],
			success : function(data) {
				$("#receiverLabelContain").html(data);
			},
			error : function() {
				message({
					html : '<spring:message code="common.op.error"/>',
					showConfirm : true
				});
			}
		});
	});

	$("#print4").click(function(){

		openListShadow();
		var isDetail = $("#receiverDetail").is(':checked');

		$("#isSpecial").val("4#");

		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/print/qryReciverLabel',
			dataType : 'html',
			cache : false,
			data : [ {name : "useDate", value : $("#yyrq").val()},
			         {name : "batchIds",value : pcStr},
			         {name : "wardCode",value : inpatientString},
			         {name : "isPrint",value : false},
			         {name : "isDetail",value : isDetail},
			         {name : "specialBtach",value:"4#"}
			         ],
			success : function(data) {
				$("#receiverLabelContain").html(data);
			},
			error : function() {
				message({
					html : '<spring:message code="common.op.error"/>',
					showConfirm : true
				});
			}
		});

	});


	$("#receivePrintBtn").click(function(){

		var isDetail = $("#receiverDetail").is(':checked');

		var params =  [ {name : "useDate", value : $("#yyrq").val()},
	         {name : "batchIds",value : pcStr},
	         {name : "wardCode",value : inpatientString},
	         {name : "isPrint",value : true},
	         {name : "isDetail",value : isDetail}
	     ];

		var batch = $("#isSpecial").val();
		if(batch != null && batch != ""){
			params.push({name : "specialBtach",value:batch});
		}

		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/print/qryReciverLabel',
			dataType : 'json',
			cache : false,
			data : params,
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

	var tableHeight = leftHeight - 60;
	var tableWidth = $("#tabs").width() - 10;

	//停退信息表头
	var stopCol =
	[
	   	{display: '状态', name : 'yDZT', width : 80, sortable : false, align: 'left',process: function(v,_trid,_row) {
	   		var flag = "";
	   		if(v == "1"){
	   			flag="停止";
	   		}else if(v == "-1"){
	   			flag="退费";
	   		}
	   		return flag;
	   	}},
	   	{display: '病区', name : 'deptName', width : 120, sortable : false, align: 'left'},
	   	{display: '床号', name : 'bedNO', width : 80, sortable : false, align: 'left'},
	   	{display: '病人', name : 'patName', width : 100, sortable : false, align: 'left'},
	   	{display: '批次', name : 'pcName', width : 100, sortable : false, align: 'left'},
	   	{display: '瓶签号', name : 'yDPQ', width : 120, sortable : false, align: 'left'},
	   	{display: '配置人', name : 'pzmc', width : 100, sortable : false, align: 'left'},
	   	{display: '扫描时间', name : 'smRQ', width : 150, sortable : false, align: 'left'},
	   	{display: '备注', name : 'smSBYY', width : 120, sortable : false, align: 'left'}
	];
	//停退信息
	$("#stopTable").flexigrid({
		width : tableWidth,
		height : tableHeight,
		url: "${pageContext.request.contextPath}/scans/initStop",
		dataType : 'json',
		colModel : stopCol,
		resizable : false, //resizable table是否可伸缩
		useRp : false,
		usepager : false, //是否分页
		autoload : false, //自动加载，即第一次发起ajax请求
		hideOnSubmit : true, //是否在回调时显示遮盖
		onSuccess:function(){
		}
	});

	//瓶签信息表头
	var pqCol =
	[
		{display: '瓶签号', name : 'yDPQ', width : 120, sortable : false, align: 'left'},
	   	{display: '状态', name : 'yDZT', width : 80, sortable : false, align: 'left',process: function(v,_trid,_row) {
	   		var flag = "";
	   		if(v == "0"){
	   			flag="未打印";
	   		}else if(v == "2"){
	   			flag="撤销";
	   		}else if(v == "3"){
	   			flag="未打印";
	   		}else if(v == "4"){
				flag="已打印";
	   		}else if(v == "5"){
	   			flag="已入仓";
	   		}else if(v == "6"){
				flag="已仓内";
	   		}else if(v == "7"){
				flag="已出仓";
	   		}else if(v == "8"){
	   			flag="已签收";
	   		}else if(v == "-1"){
	   			flag="已退费";
	   		}
	   		return flag;
	   	}},
	   	{display: '病区', name : 'deptName', width : 120, sortable : false, align: 'left'},
	   	{display: '床号', name : 'bedNO', width : 80, sortable : false, align: 'left'},
	   	{display: '病人', name : 'patName', width : 100, sortable : false, align: 'left'},
	   	{display: '批次', name : 'pcName', width : 100, sortable : false, align: 'left'},
	   	{display: '配置人', name : 'configurator', width : 100, sortable : false, align: 'left'},
	   	{display: '扫描时间', name : 'smRQ', width : 150, sortable : false, align: 'left'},
	   	{display: '备注', name : 'smSBYY', width : 120, sortable : false, align: 'left'}

	];
	//瓶签信息
	$("#pqTable").flexigrid({
		width : tableWidth,
		height : tableHeight,
		url: "${pageContext.request.contextPath}/scans/getPagePQ",
		dataType : 'json',
		colModel : pqCol,
		resizable : false, //resizable table是否可伸缩
		useRp : false,
		usepager : false, //是否分页
		autoload : false, //自动加载，即第一次发起ajax请求
		hideOnSubmit : true, //是否在回调时显示遮盖
		rowbinddata : true,
		rowhandler : rowClick,
		onSuccess:function(){

			var checkType = $("#hdlx").val();

			$("#pqTable").find("tbody tr").each(function(){

				var zt = $(this).find("td:eq(1)").text().trim();
				if(checkType == 1 && zt == "已仓内"){
					$(this).find("td").addClass("backColor");
				}
				if(checkType == 2 && zt == "已出仓"){
					$(this).find("td").addClass("backColor");
				}

			});

			if(pqnum != ""){
				$("#pqTable").find("tbody tr").each(function(){
			    	var pq = $(this).find("td:eq(0)").text();
			    	if(pq == pqnum){
			    		$(this).css("color","red");

			    		var de = $(this).offset().top-$("#pqTable").offset().top;
			    		$("#pqTable").parent().scrollTop(de);

			    		pqnum = "";
			    	}
			    });
			}
		}
	});



	$("#scanText").on("keypress",function(e){
		if(event.keyCode == "13")
	    {
			timedCount();
	    }
	});

	loadList();
	loadSmResult();
	//timedCount();

});

function rowClick(r) {
	$(r).click(
	function() {

		var pqid = $.trim($(this).find("td:first div").text());
		if(pqid != null && pqid != ""){

			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/scans/ydDetail',
				dataType : 'html',
				cache : false,
				data:{"pqStr":pqid},
				//async : true,
				success : function(data) {
					$("#medicTabBody").html(data);
				},
				error : function(data){
					message({html:'<spring:message code="common.op.error"/>'});
				}
			});
		}

	});
}



function timedCount(){

	var value = $("#scanText").val().trim();
	if(value != "" && typeof(value) != "undefined"){

		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/scans/ydDetail',
			dataType : 'html',
			cache : false,
			data:{"pqStr":value},
			//async : true,
			success : function(data) {
				$("#medicTabBody").html(data);

				commitUsbDate(value);

				$("#scanText").val("");
				//tCount=setTimeout("timedCount()",1000);
			},
			error : function(data){
				message({html:'<spring:message code="common.op.error"/>'});
				$("#scanText").val("");
				//tCount=setTimeout("timedCount()",1000);
			}
		});
	}/* else{
		tCount=setTimeout("timedCount()",1000);
	} */

}

function commitUsbDate(value)
{
	var sound_success = document.getElementById("sound_success");

	/* [ {name : 'barcode',value : _barcode},
      {name : 'scansType',value :'${checkType}'},
      {name : 'batchID',value :'${batchID}'},
      {name : 'batchName',value :'${batchName}'},
      {name : 'qryRQ',value :'${qryRQ}'}
    ] */

	$.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/scans/ScansResult',
		dataType : 'json',
		cache : false,
		data : {
				'pqstr':value,
				'scansType':$("#hdlx").val(),
				'qryYyrq':$("#yyrq").val(),
				'pcStr':pcStr,
				'inpatientStr':inpatientString
				},
		success : function(data) {

			$("#smResult").css("color","black");

			if(data.success ){
				$("#smResult").text("扫描成功");
				sound_success.load();
				sound_success.play();
			} else if (data.msg.indexOf("重复扫描") != -1)
			{
				$("#smResult").text(data.msg);
				sound_success.load();
				sound_success.play();
			}
			else{
				$("#smResult").text(data.msg);
				$("#smResult").css("color","red");
				sound_fail.load();
				sound_fail.play();
			}

			pqnum = value;
			//loadList();
			loadSmResult();
		},
		error:function(data){
			message({html:'<spring:message code="common.op.error"/>'});
		}
	});
}

function loadSmResult(){

	$.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/scans/getSmResultList',
		dataType : 'html',
		data:{'yyrq':$("#yyrq").val()},
		cache : false,
		//async : true,
		success : function(data) {
			$("#traceTabBody").html(data);
		},
		error : function(data){
			message({html:'<spring:message code="common.op.error"/>'});
		}
	});

	$("#scanText").focus();
}

function loadList(){

	var yyrq = $("#yyrq").val();
	var checkType = $("#hdlx").val();

	var params =
	[
		{name:"yyrq",value:yyrq},
		{name:"inpatientStr",value:inpatientString},
		{name:"pcStr",value:pcStr},
		{name:"checkType",value:checkType}
	];

	var url = '';
	if(type == 'tabs-pq'){
		url = '${pageContext.request.contextPath}/scans/getPagePQ';
		$('#pqTable').flexOptions({
			newp: 1,
			extParam: params,
	    	url: url
	    }).flexReload();
	}else if(type == 'tabs-pc'){
		url = '${pageContext.request.contextPath}/scans/initPC';
		$.ajax({
			type : 'POST',
			url : url,
			dataType : 'html',
			cache : false,
			data:params,
			//async : true,
			success : function(data) {
					$("#tabs-pc").html(data);
			},
			error : function(data){
				message({html:'<spring:message code="common.op.error"/>'});
			}
			});

	}else if(type == 'tabs-stop'){
		url = '${pageContext.request.contextPath}/scans/initStop';
		$('#stopTable').flexOptions({
			newp: 1,
			extParam: params,
	    	url: url
	    }).flexReload();
	}
	
	$("#scanText").focus();
	
}

function dataPick() {
	loadList();
	loadSmResult();
}


function openListShadow(){
	$("#isSpecial").val("");
	$("#labelListShadow").css("display","block");
	$("#labelList").css("display", "block");	
}

function closeListShadow(){
	$("#labelListShadow").css("display","none");
	$("#labelList").css("display", "none");
}

</script>
</html>