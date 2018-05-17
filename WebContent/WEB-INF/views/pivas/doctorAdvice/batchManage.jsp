<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/multDropList/multDropList.css" rel="stylesheet" type="text/css"/>
<script src="${pageContext.request.contextPath}/assets/common/js/my97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<style type="text/css">
.medicine-tab {
	margin-top: 10px;
}

.tabs-title {
	height: 28px;
}

.content {
	width: 99%;
}

.classX {
	width: 15px;
	min-height: 15px;
	margin-top: -17px;
	margin-left: 250px;
	background:
		url('${pageContext.request.contextPath}/assets/search/images/search_reset.gif')
		center center no-repeat;
}

.rightDiv table tr:nth-child(2n) {
	background: transparent;
}

.rightDiv table tr td {
	padding: 12px 2px;
	/*background: #5366bf;*/
	padding-left: 10px;

}
.table-yz-info tr td{
	border:1px solid #3f51b53b;
}
.fontBold {
	font-weight: bold;
}

.cbit-grid div.bDiv td {
	background-color: transparent;
}

.cbit-grid tr.erow td {
	background-color: transparent;
	background: transparent;
}

.trSelect {
	background-color: #ffeece; /*#E1E9F8;*/
}

.button:hover{
 	color:blue;
}
/*药物优先级 左中右样式 --开始------------------*/
.tabMain2 {
	height: 100%;
	width: 100%;
	padding-left: 10px;
	padding-top: 20px;
}

.tdSortTitle {
	vertical-align: bottom;
	padding-right: 5px;
	color: white;
}

.divSortTitle {
	height: 100%;
	padding-top: 8px;
	padding-left: 5px;
	background-color: #6F96E5;
	font-size: 14px;
}

.aSortTitle {
	float: right;
	padding-left: 10px;
}

.tdSortContent {
	vertical-align: top;
	padding-right: 5px;
}

.divSortContent {
	height: 100%;
	overflow-y: auto;
}

.tdSortRow {
	width: 100%;
	height: 30px;
	cursor: pointer;
	padding-left: 10px;
}

.tdMedSelec {
	background-color: #99bbe8;
}

#divSynToOther tr:nth-child(2n) {
	background: transparent;
}

/*药物优先级 左中右样式 --结束------------------*/
<!--
0,
执行  1,停止  2,撤销 3,未打印  4,已打印  5,入仓扫描核对完成  6,仓内扫描核对完成 7,出仓扫描核对完成
f1818d；f8b117；3acaec；a5d065；ef99f2；7b99f1
已打印	已入仓	已出仓	已配置  已审方   已排批次
-->.circleClass99 {
	
}
.stcolor0{
	color: #B4B5B9;
}
.stcolor1{
	color: #B4B5B9;
}
.stcolor2{
	color: #ef99f2;
}
.stcolor3{
	color: #7b99f1;
}
.stcolor4{
	color: #f1818d;
}
.stcolor5{
	color: #f8b117;
}
.stcolor6{
	color: #a5d065;
}
.stcolor7{
	color: #3acaec;
}
.stcolor8{
	color:#9ACD32;
}
.stcolor-1{
	color:#ff0000;
}
/*0 灰色：已停止*/
.circleClass1 {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin-left: 4px;
	margin-right: 4px;
	background-color: #B4B5B9;
}
/*2 粉色。 未确认*/
.circleClass2 {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin-left: 4px;
	margin-right: 4px;
	background-color: #ef99f2; /*2撤销--无颜色*/
}
/*3 蓝色。 已确认*/
.circleClass3 {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin-left: 4px;
	margin-right: 4px;
	background-color: #7b99f1; /*3未打印-已排批次--*/
}
/*4 棕色。 已打印*/
.circleClass4 {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin-left: 4px;
	margin-right: 4px;
	background-color: #f1818d; /*4已打印--*/
}
/*5 黄色。 已入仓*/
.circleClass5 {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin-left: 4px;
	margin-right: 4px;
	background-color: #f8b117; /*5已入仓*/
}
/*6 仓内*/
.circleClass6 {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin-left: 4px;
	margin-right: 4px;
	background-color: #a5d065; /*6仓内完成*/
}
/*7 绿色 已出仓*/
.circleClass7 {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin-left: 4px;
	margin-right: 4px;
	background-color: #3acaec; /*7已出仓*/
}

.circleClass {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin-left: 4px;
	margin-right: 4px;
	background-color: transparent; /*9已生成未确认--无颜色*/
}

.circleClass-1 {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin-left: 4px;
	margin-right: 4px;
	background-color: #FF0000; 
}

.circleClass8 {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin-left: 4px;
	margin-right: 4px;
	background-color: #9ACD32; /*8已签收 */
}

.circleClass0 {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin-left: 4px;
	margin-right: 4px;
	background-color: #B4B5B9; /*1停止--无颜色*/
}
.medicine-tab table td {
    padding: 0px 0px 0px 10px;
    height: 26px;
}
</style>

</head>
<body onload="setfocus()">

<div class="medicine-tab">
<!-- max-width: 1100px; -->
<div class="tabs-content" style="margin-top: -2px;">
	<div class="tab-content-1" style="display: block;">
		<div id="yzMainDiv__1" class="main-div" style="min-width:1100px;">
			<div class="search_div ui-search-header">
			<table style="width:auto;">
			<tr>
				<td><input name="bednoS" placeholder="<spring:message code='pivas.yz2.bedno'/>" size="8" data-cnd="true"></td>
				<td><input name="patnameS" placeholder="<spring:message code='pivas.yz2.patname'/>" size="8" data-cnd="true"></td>
				<td><input name="freqCodeS" placeholder="<spring:message code='pivas.yz1.freqCode'/>" size="8" data-cnd="true"></td>
				<td><input name="drugnameQry" placeholder="<spring:message code='pivas.yz1.drugname'/>" size="8" data-cnd="true"></td>
			<td >
			用药日期：
			<input type="text" id="yyrq" style="color: #555555; height: 26px; width: 100px;"
			class="Wdate" empty="false" readonly="readonly" 
			onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:dataPick});">
			</td>
			<td>
			医嘱类型：
			<select id="yzlx" class="select_new"  style="width:75px;height: 26px;">
			<option value="0" selected="selected" >长嘱</option>
			<option value="1">临嘱</option>
			</select>
			</td>
			<td >
			打印：
			<select id="printStatus" class="select_new" style="height: 26px;">
			<option value="">请选择</option>
			<option value="1">未打印</option>
			<option value="0">已打印</option>
			</select>
			</td>
			<td >
			<div style="width: 40px;position: relative;top: 8px;">
			<span style="position: relative;top: 4px;">批次：</span>
			</div>
			<div style="width: 150px;position: relative; top: -5px;left: 38px;">
			<select id="batchSelect"  name="batchSelect[]" size="5" style="height: 26px;" multiple="multiple">
			<option value="">--请选择--</option>
			<option value="-1">T</option>
			<option value="0">KU</option>
			<option value="1">#</option>
			<c:forEach items="${batchList}" var="batch">
			<c:if test="${batch.enabled==1}">
			<option value="${batch.id}" isSecendAdvice="${batch.isSecendAdvice}">${batch.name}</option>
			</c:if>
			</c:forEach>
			</select>
			</div>
				<td><button onclick="qryByCnd()">搜索</button>&nbsp;&nbsp;<button onclick="cndRest()">清空</button></td>
			</td>
			<%--<td style="width: 260px;">
			<div class="oe_searchview" style="margin-top:0px; width: 260px;z-index: 999; ">
			<div class="oe_searchview_facets">
				<div class="oe_searchview_input oe_searchview_head"></div>
				<div class="oe_searchview_input" id="inputsearch__1">
					<input id="txt__1" type="text" class="oe_search_txt"
						style="border: none; max-height:26px; width: 220px;" />
				</div>
			</div>
			<img alt="" style="top: 20px;padding-left:5px;position: absolute;" src="${pageContext.request.contextPath}/assets/search/images/searchblue.png">
			<div class="oe_searchview_clear" onclick="clearclosedinputall();" style="left: 890px;position: absolute;top: 15px;"></div>
			<div class="oe-autocomplete"></div>
			<div style="border: 1px solid #D2D2D2; display: none;" width="50px" heiht="50px" class="divselect" >
			<cite>请选择...</cite>
			<ul class="ulQry" style="-webkit-border-radius: 20;" funname="search__1">
			<li show="<spring:message code='pivas.yz2.bedno'/>" name="bednoS">
			<spring:message code='pivas_yz1.search' /> 
			<spring:message code='pivas.yz2.bedno' />：<span class="searchVal"></span>
			</li>
			<li show="<spring:message code='pivas.yz2.patname'/>" name="patnameS">
			<spring:message code='pivas_yz1.search' /> 
			<spring:message code='pivas.yz2.patname' />：<span class="searchVal"></span>
			</li>
			&lt;%&ndash; <li show="<spring:message code='pivas.yz1.parentNo'/>" name="parentNoS"><spring:message code='pivas_yz1.search' />
			<spring:message code='pivas.yz1.parentNo' />：<span class="searchVal"></span>
			</li> &ndash;%&gt;
			<li show="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS"><spring:message code='pivas_yz1.search' />
			<spring:message code='pivas.yz1.freqCode' />：<span class="searchVal"></span>
			</li>
			<li show="<spring:message code='pivas.yz1.drugname'/>" name="drugnameQry" ><spring:message code='pivas_yz1.search'/>
			<spring:message code='pivas.yz1.drugname'/>：<span class="searchVal"></span>
			</li>
			</ul>
			</div>
			</div> <!-- 搜索条件--结束 -->
			</td>--%>
			<td>
			<!-- <a class="button" id="changeBatchs" >批量修改批次</a> -->
			
			<a class="button_new green_jianbian" id="changeTF" >批量退费</a>
			
			<a class="button_new green_jianbian" id="confirmTF"  style="background:#FF9800;">退费确认</a>
			</td>
			</tr>
			<tr funname="search__1" data-qryMethod>


			</tr>
			
			</table>
			</div>
			<table style="margin-top: 10px;table-layout: fixed;min-width:1000px;" >
			<tbody>
			<tr>
			<td width="50%" style="vertical-align: top;" id="gridTd">
			
			<div style="width: 100%; display: flex;line-height: 20px; margin-top: 15px;">
				<div class="circleClass0"></div>
				<span class="stcolor0">已停止</span>
				<div class="circleClass2"></div>
				<span class="stcolor2">未确认</span>
				<div class="circleClass3"></div>
				<span class="stcolor3">已确认</span>
				<div class="circleClass4"></div>
				<span class="stcolor4">已打印</span>
				<div class="circleClass5"></div>
				<span class="stcolor5">已入仓</span>
				<div class="circleClass7"></div>
				<span class="stcolor7">已出仓</span>
				<div class="circleClass8"></div>
				<span class="stcolor8">已签收</span>
				<div class="circleClass-1"></div>
				<span class="stcolor-1">退费</span>
			</div>
			
			<div style="width: 100%; margin-top: 10px;">
				<table id="dataTable_1" class="table datatable ui-data-table display dataTable no-footer">
					<thead>
					<tr>
						<th><input type="checkbox" id="all_checked"></th>
						<th>床号</th>
						<th>批次</th>
						<th>病人</th>
						<th>频次</th>
						<th>用药时间</th>
						<th>医嘱类型</th>
						<th>状态</th>
						<th>退费处理</th>
					</tr>
					</thead>
				</table>
			</div>
			</td>
			
			<td  style="vertical-align: top;">
			<div class="rightDiv" style="background-color: white; width: 100%; margin-top: 45px;"  id="leftHeight">
			<div style="background-color: #777777; text-align: left; color: white;height: 40px; padding-top: 10px;padding-left: 10px; font-size: 15px;">瓶签号 <span class="fontBold" id="bottleLabelNum"></span>
			</div>
			<div style="padding-left: 0px; padding-right: 10px; margin-top: 7px;">
			
			<table id="yzInfo__1" class="table-yz-info">
			<tr style="background: #e1f8f4; padding-left: 0px; padding-right: 5px;">
			<td width="20%" style="text-align: right;">开立医生：</td>
			<td width="30%" class="fontBold" name="doctorName"></td>
			<td width="20%" style="text-align: right;">开立时间：</td>
			<td width="30%" class="fontBold" name="startTimeS"></td>
			</tr>
			<tr style="background: #e1f8f4; padding-left: 5px; padding-right: 5px;border-bottom: 1px dashed;">
			<td width="20%" style="text-align: right;">病区：</td>
			<td width="30%" class="fontBold" name="wardName"></td>
			<td width="20%" style="text-align: right;">结束时间：</td>
			<td width="30%" class="fontBold" name="endTimeS"></td>
			</tr>
			<tr style="background: #e1f8f4; padding-left: 5px; padding-right: 5px;">
			<td width="20%" style="text-align: right;">病人姓名：</td>
			<td width="30%" class="fontBold" name="patname"></td>
			<td width="20%" style="text-align: right;">体重：</td>
			<td width="30%" class="fontBold" name="avdp"></td>
			</tr>
			<tr style="background: #e1f8f4; padding-left: 5px; padding-right: 5px;">
			<td width="20%" style="text-align: right;">年龄：</td>
			<td width="30%" class="fontBold" name="age"></td>
			<td width="20%" style="text-align: right;">频次：</td>
			<td width="30%" class="fontBold" name="freqCode"></td>
			</tr>
			<tr style="background: #e1f8f4; padding-left: 5px; padding-right: 5px;">
			<td width="20%" style="text-align: right;">床号：</td>
			<td width="30%" class="fontBold" name="bedno"></td>
			<td width="20%" style="text-align: right;">滴速：</td>
			<td width="30%" class="fontBold" name="dropSpeed"></td>
			</tr>
			<tr style="background: #e1f8f4; padding-left: 5px; padding-right: 5px;">
			<td width="20%" style="text-align: right;">性别：</td>
			<td width="30%" class="fontBold" name="sex"></td>
			<td width="20%" style="text-align: right;">出生日期：</td>
			<td width="30%" class="fontBold" name="birthdayS"></td>
			</tr>
			<tr style="background: transparent;">
			<td colspan="4" style="height: 7px;"></td>
			</tr>
			</table>
			
			<div style="overflow-y:scroll;padding-left:10px;background: #777777;">
			<table>
			<thead>
			<tr style="">
				<td style="color:white;" width="15%">药品编码</td>
				<td style="color:white;" width="40%">药品名称</td>
				<td style="color:white;" width="15%">规格</td>
				<td style="color:white;" width="15%">剂量</td>
				<td style="color:white;" width="15%">数量</td>
			</tr>
			</thead>
			</table>
			</div>
			<div style="height:150px;overflow-y:scroll;padding-left:10px;">
			<table id="medicTab__1" >
			<thead style="display:none;">
			<tr style="background: #B5B5B5;">
				<td width="15%">药品编码</td>
				<td width="40%">药品名称</td>
				<td width="15%">规格</td>
				<td width="15%">剂量</td>
				<td width="15%">数量</td>
			</tr>
			</thead>
			<tbody id="medicTabBody">
			
			</tbody>
			</table>
			</div>
			<div style="overflow-y:scroll;background: #777777;padding-left:10px;">
			<table>
			<thead>
			<tr style="">
				<td style="color:white;" width="30%">追踪状态</td>
				<td style="color:white;" width="40%">操作时间</td>
				<td style="color:white;" width="30%">操作人</td>
			</tr>
			</thead>
			</table>
			</div>
			<div style="min-height:140px;overflow-y:scroll;" id="bottomHeight">
			<table id="traceTab" >
			<thead style="display:none;">
			<tr style="background: #B5B5B5;">
				<td width="30%">追踪状态</td>
				<td width="40%">操作时间</td>
				<td width="30%">操作人</td>
			</tr>
			</thead>
			<tbody id="traceBody">
			</tbody>
			</table>
			</div>
			</div>
			</div>
			</td>
			</tr>
			</tbody>
			</table>
</div>
</div>
</div>
</div>
<%-- 批量修改批次 --%>
<div id="batchPopup" title="批量修改批次" align="center" style="display: none;">
	<form id="batch-form" action="" method="post">
			<div class="popup" style="padding-top: 24px;">
				<div class="row">
						<label class="tit2">批次：</label>
						<select id="batchChange" style="height: 26px;" >
						<option value="">--请选择--</option>
						<c:forEach items="${batchList}" var="batch">
						<c:if test="${batch.enabled==1}">
						<option value="${batch.id}" isSecendAdvice="${batch.isSecendAdvice}">${batch.name}</option>
						</c:if>
						</c:forEach>
						</select>
				</div>
			</div>
	</form>
</div>
<input type="hidden" id="day"  value="${day}"/>
</body>
<script type="text/javascript">
var _pageWidth = 0;
var _pageHeight = 0;
var gridWidth = 0;

var paramTemp__1;
var pcStr;
var areaCodeStr = function(){
	return window.parent.getInpatientInfo();
};
var tCount;

var currentDay = getCurrentDate("yyyy-MM-dd", null, 0);

var changeColor = false;
var changPCs = new Array();

//dataTable API
var dataTable;
//dataTable 请求参数
var paramAll;

function setfocus(){
	
	$("#scanText").focus();
}
$(function() {
	
	pageWidth = window.innerWdith - 12;
	_pageHeight = window.innerHeight-165;
	
	
	var bottomHeight = _pageHeight - 150 - 230;
	$("#bottomHeight").height(bottomHeight);
	
	
	_pageHeight = _pageHeight < $("#leftHeight").height() - 70 ? $("#leftHeight").height() - 70 : _pageHeight;
	gridWidth = parseInt($("#gridTd").width()) - 10;

	
	
	var dayFlag = $("#day").val();
	if(dayFlag == "tomorrow"){
		$("#yyrq").val(getCurrentDate("yyyy-MM-dd", null, 1));
	}else{
		$("#yyrq").val(getCurrentDate("yyyy-MM-dd", null, 0));
	}
	
	$("#batchSelect").multiSelect({ "selectAll": false,"noneSelected": "选择批次","oneOrMoreSelected":"*" },function(){
		pcStr = $("#batchSelect").selectedValuesString();
		qry__1();
	});
	
	$("#yzlx").change(function(){
		qry__1();
	});
	
	$("#printStatus").change(function(){
		qry__1();
	});
	
	var _columnWidth__1 = parseInt((gridWidth - 80) / 8);
	if(_columnWidth__1 < 70){
		_columnWidth__1 = 70;
	}
	
	timedCount();

	qry__1();
	$("#scanText").focus();
	
	$("#changeBatchs").click(function(){
		
		var pidsj = getDataTableSelectRowData($("#dataTable_1"), dataTable, 'pidsj');

		if(pidsj.length == 0){
			message({html:"至少选择一条数据"});
			return;
		}
		$("#batchPopup").dialog("open");
		
		$("#batchChange").val("");
	});
	
	$("#changeTF").click(function(){
        var pidsj = getDataTableSelectRowData($("#dataTable_1"), dataTable, 'pidsj');
		if(pidsj.length == 0){
			message({html:"至少选择一条数据"});
			return;
		}
		
		message({
			html: "确认退费？",
			showCancel:true,
			confirm:function(){
				stopYd();
			}
    	});
	});
	
	$("#confirmTF").click(function(){
        var pidsj = getDataTableSelectRowData($("#dataTable_1"), dataTable, 'pidsj');
		if(pidsj.length == 0){
			message({html:"至少选择一条数据"});
			return;
		}
		
		message({
			html: "确认退费完成？",
			showCancel:true,
			confirm:function(){
				confirmpTFYd();
			}
    	});
	});
		
		function confirmpTFYd(){
            var pidsj = getDataTableSelectRowData($("#dataTable_1"), dataTable, 'pidsj');
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/doctorAdvice/doMultTFYdCfm',
				dataType : 'json',
				cache : false,
				data : {"pidsj":pidsj.toString()},
				success : function(data) {
					if(data.code == -1){
						message({html:data.mess});
					}else if(data.code == 1){
						qry__1();
					}
				},
				error : function(data){
					message({html:'<spring:message code="common.op.error"/>'});
				}
			});
		}
	
	function stopYd(){
        var pidsj = getDataTableSelectRowData($("#dataTable_1"), dataTable, 'pidsj');
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/doMultTF',
			dataType : 'json',
			cache : false,
			data : {"pidsj":pidsj.toString()},
			success : function(data) {
				if(data.code == -1){
					message({html:data.mess});
				}else if(data.code == 1){
					qry__1();
				}
			},
			error : function(data){
				message({html:'<spring:message code="common.op.error"/>'});
			}
		});
	}
	
	
	
	$("#batchPopup").dialog({
		autoOpen: false,
		height: 200,
		width: 300,
		modal: true,
		resizable: false,
		buttons: {
			"<spring:message code='common.confirm'/>": function() {
				
				var v = $("#batchChange").val();
				var n = $("#batchChange option:selected").text();
				if(v == ""){
					message({html:"请选择批次"});
					return;
				}
				$(this).dialog("close");
				
				batchChanges(v,n);
				
				
			},"<spring:message code='common.cancel'/>": function() {
				$(this).dialog("close");
			}
		},
		close: function() {
			
			
		}
	});
	
});

//状态类型
var yzStatus = {
    0: '已停止',
    2: '未确认',
    3: '已打印',
    5: '已入仓',
    7: '已出仓',
    8: '已签收',
    '-1': '退费'
};

function initDataTable() {
    dataTable = $('#dataTable_1').DataTable({
        "dom": '<"toolbar">frtip',
        "searching": false,
        "processing": true,
        "serverSide": true,
        "select": true,
        "pageLength": 2000,
        "ordering": true,
        "order": [],
        //"scrollX": true,
        "language": {
            "url": "${pageContext.request.contextPath}/assets/datatables/cn.json"
        },
        "ajax": {
            "url": "${pageContext.request.contextPath}/doctorAdvice/qryYdPage",
            "type": "post",
            "data": function (d) {
                if (paramAll) {
                    d = $.extend(d, paramAll);
                }
                d.rp = 20000;
                d.page = 1;
            },
            "dataSrc": function (data) {
                data.data = data.rawRecords;
                //data.draw = 1;
                data.recordsFiltered = data.total;
                data.recordsTotal = data.total;
                $("#pageTotal").text(data.total);
                return data.data;
            }
        },
        "columns": [
            {"data": "", "bSortable": false},
            {"data": "bedno", "bSortable": false},
            {"data": "zxbc", "bSortable": false, "defaultContent":""},
            {"data": "patname", "bSortable": false},
            {"data": "freqCode", "bSortable": false},
            {"data": "yyrqS", "bSortable": false},
            {"data": "yzlx", "bSortable": false},
            {"data": "pqYdzt", "bSortable": false},
            {"data": "tfAccount", "bSortable": false}
        ],
        columnDefs: [
            {
                targets: 0,
                data: null,
                defaultContent:"<input type ='checkbox' name='pidsj'>",
            },
            {
                targets: 2,
                render: function (data, type, row) {
                    if (currentDay <= row.scrqS
                        && row.pqYdzt < 6
                        && row.ydzxzt == 0) {//&& _row.ydreorderStatus>0
                        return "<select id='yd__1_"
                            + row.pidsj
                            + "' oldvalue='"
                            + data
                            + "' yzlx='"+row.yzlx
                            + "' parentno='"
                            + row.parentNo
                            + "' pidsj='"
                            + row.pidsj
                            + "' scrqS='"
                            + row.scrqS
                            + "' onchange='changePC(this)' style='width:70px'  >" //"<option value='' >--请选择--</option>"
                            + "<c:forEach items='${batchList}' var='batch' ><option value='${batch.id}' issecendadvice='${batch.isSecendAdvice}' "
                            + (v == '${batch.id}' ? "selected='selected'"
                                : "")
                            + " >${batch.name}</option></c:forEach>"
                            + "</select>";
                    } else {
                        return row.pb_num == undefined ? "" : row.pb_num;
                    }
                }
            },
            {
                targets: 7,
                render: function (data, type, row) {
                    /*
                                    确认状态，药单状态
                                    0		0
                                    1		0
                                    1		4
                                    1		5
                                    1		7
                                     */
                    //药单状态步骤  0,执行  1,停止  2,撤销 3,未打印  4,已打印  5,入仓扫描核对完成  6,仓内扫描核对完成 7,出仓扫描核对完成 ';   1停止 3未打印 4 已打印 5 已入仓 6仓内完成 7 已出仓
                    //(_row.ydreorderStatus==1&&v>3)?v:(_row.ydreorderStatus==1:0)
                    //"<input class='circleClass"+(v==0&&_row.ydreorderStatus==0?2:(_row.ydreorderStatus==1?3:v))+"' readonly='readonly' />"
                    var _pqydzt = row.ydzxzt == 1 ? 0 : (data == -1 ? v : ((row.ydreorderStatus == 1 && data > 3) ? data : (row.ydreorderStatus == 1 ? 3 : 2)));
                    var _pqydztText = yzStatus[_pqydzt];
                    return "<input class='circleClass"
                        + _pqydzt
                        + "' readonly='readonly' />"
                        +"<span class='stcolor"+_pqydzt+"'>"
                        +(_pqydztText == undefined ? "" : _pqydztText)
                        +"</span>";
                }
            }
        ],
        "createdRow":function (row, data, idx) {
            $(row).on('click' , 'td:gt(0)', function(){
                $(row).siblings().removeClass('selected');
                $(row).addClass('selected');
                //获取该行的所有列数据
                showYZ__1(data['pidsj']);
            });
            if (idx > 0) {
                if ( dataTable.row(idx - 1).data()['parentNo'] != data['parentNo'] ) {
                    $(row).find("td").css("border-top", "2px solid black");
                }
            } else {
                $(row).addClass("selected");
                showYZ__1(data['pidsj']);
            }

        },
        "fnDrawCallback": function(){
            $("#all_checked").prop("checked",false);
        }
    });
}

//datatable下checkbox实现全选功能
$("#all_checked").click(function(){
    $('[name=pidsj]:checkbox').prop('checked',this.checked).change();;//checked为true时为默认显示的状态
});

function timedCount(){
	
	var value = $("#scanText").val();
	if(value != "" && typeof(value) != "undefined" ){
	value = value.trim();
	$.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/doctorAdvice/signFor',
		dataType : 'json',
		cache : false,
		//async : true,
		data : {"pqStr":value},
		success : function(data) {
			if(data.code == -1){
				
				message({html:data.mess});
				
			}else if(data.code == 1){
				qry__1();
			}
			
			$("#scanText").val("");
			tCount=setTimeout("timedCount()",1000);
			
		},
		error : function(data){
			message({html:'<spring:message code="common.op.error"/>'});
			$("#scanText").val("");
			tCount=setTimeout("timedCount()",1000);
		}
	});
	
	}else{
		tCount=setTimeout("timedCount()",1000);
	}
	
}



function dataPick() {
	qry__1();
}
function search__1(param) {
	paramTemp__1 = param;
	qry__1();
}
function qry__1() {
	var params = [];
	if (paramTemp__1) {
		params = paramTemp__1.concat();
	}
	//初始化右侧页面
	$("#yzInfo__1 .fontBold").each(function() {
		$(this).html("");
	});

	$("#medicTabBody .medicTd").remove();
	$("#traceBody .medicTd").remove();
	
	params.push({
		"name" : "pcNotNull",
		"value" : 1
	});
	params.push({
		"name" : "yzlx",
		"value" : $("#yzlx").val()
	});
	params.push({
		"name" : "yyrq",
		"value" : $("#yyrq").val()
	});
	
	params.push({
		"name" : "areaS",
		"value" : areaCodeStr
	});
	params.push({
		"name" : "pcS",
		"value" : pcStr
	});
	params.push({
		"name" : "compareTime",
		"value" : compareTime($("#yyrq").val())
	});
	params.push({
		"name" : "ydStatus",
		"value" : "1"
	});
	

	//批次界面进行批次排序操作
	params.push({
		"name" : "pici",
		"value" : "1"
	});
	//是否关联批次修改记录
	params.push({
		"name" : "operationLog",
		"value" : "1"
	});
	
	params.push({
		"name" : "dyzt",
		"value" : $("#printStatus").val()
	});

	paramAll = params;
	if (dataTable) {
        dataTable.ajax.reload();
	} else {
	    initDataTable();
	}
	
	$("#scanText").focus();
}

function showYZ__1(pidsj) {
	if (pidsj && pidsj != "") {
		$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/doctorAdvice/prescriptionDetail',
					dataType : 'json',
					cache : false,
					showDialog : false,
					data : {
						"pidsj" : pidsj
					},
					success : function(result) {
						if (result && result.code > 0) {
							var ydMain = result.data.ydMain;
							
							$("#bottleLabelNum").html(ydMain['bottleLabelNum'] == null ? "" : ydMain['bottleLabelNum']);
							
							$("#yzInfo__1 .fontBold").each(function() {
								$(this).html(ydMain[$(this).attr("name")] ? ydMain[$(this).attr("name")]: "");
											});
							
							//$("#yzInfo__1 [name=printStatus]").html(ydMain['dybz'] == 0 ? "已打印" : "未打印");
							//$("#yzInfo__1 [name=ydzxzt]").html(ydMain['ydzxzt'] == 0 ? "执行中" : "停止");
							//$("#yzInfo__1 [name=yzlx]").html(ydMain['yzlx'] == 0 ? "长嘱" : "临嘱");
							$("#yzInfo__1 [name=sex]").html(ydMain['sex'] == 0 ? "女" : "男");
							//$("#yzInfo__1 [name=medNames]").html(result.data.medNames ? result.data.medNames : "");
							//$("#yzInfo__1 [name=transfusion]").html(ydMain['transfusion'] + "ml");
							$("#yzInfo__1 [name=age]").html(ydMain['age'] + getDicValue("ageUnit", ydMain['ageunit']));

							$("#medicTabBody .medicTd").remove();
							var ydList = result.data.ydList;
							for ( var i in ydList) {
								$("#medicTabBody").append(
												"<tr class='medicTd'><td width='15%'>"
														+ ydList[i].chargeCode
														+ "</td><td width='40%'>"
														+ ydList[i].drugname
														+ "</td><td width='15%'>"
														+ ydList[i].specifications2
														+ "</td><td width='15%'>"
														+ ydList[i].dose
														+ ydList[i].doseUnit
														+ "</td><td width='15%'>"
														+ ydList[i].quantity
														+ ydList[i].medicamentsPackingUnit
														+ "</td></tr>");
							}
							
							$("#traceBody .medicTd").remove();
							var recordList = result.data.recordList;
							for ( var n in recordList) {
								$("#traceBody").append(
												"<tr class='medicTd'><td width='30%' >"
														+ recordList[n].type_name
														+ "</td><td width='40%'>"
														+ recordList[n].operation_time
														+ "</td><td width='30%'>"
														+ recordList[n].operator
														+ "</td></tr>");
							}
								
						} else {
							$("#yzInfo__1 .fontBold").each(function() {
								$(this).html("");
							});
							$("#bottleLabelNum").html("");
							$("#medicTabBody .medicTd").remove();
							$("#traceBody.medicTd").remove();
						}
					}
				});
	} else {
	}
}

function batchChanges(pcid,pcname){
	
    var pidsj = getDataTableSelectRowData($("#dataTable_1"), dataTable, 'pidsj');
    var parentno = getDataTableSelectRowData($("#dataTable_1"), dataTable, 'parentNo');

	$.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/doctorAdvice/batchChanges',
		dataType : 'json',
		data : {
			"pidsj" : pidsj.toString(),
			"parentNo" : parentno.toString(),
			"pcId" : pcid,
			"pcName":pcname,
			"yyrq":$("#yyrq").val()
		},
		success : function(response) {
			if (response.code == 1) {
				changeColor = true;
				changPCs = response.data;

			} else {
				message({
					html : response.msg
				});
			}
			qry__1();
			
			$("#scanText").focus();
		}
	});
	
	
}


function changePC(obj) {
	
	var pidsj = $(obj).attr("pidsj");
	var parentno = $(obj).attr("parentno");
	var pcId = $(obj).val();
	var isSecendAdvice = $(obj).find("option:selected").attr("issecendadvice");
	var parent_pc_map = {};
	
	var yzlx = $(obj).attr("yzlx");
	$("#dataTable_1 select").each(
			function() {
				if ($(this).attr("pidsj") != pidsj) {
					parent_pc_map[$(this).attr("parentno") + "__1_"
							+ $(this).attr("oldvalue")] = 1;
				}
			});
	if (parent_pc_map[$(obj).attr("parentno") + "__1_" + pcId] != null) {
		$(obj).val($(obj).attr("oldvalue"));
		message({
			html : "同一组中已存在这个批次，无法修改"
		});
		return;
	}
	
	var oldV = $(obj).attr("oldvalue");
	var oldPcName = $("#batchSelect").next(".multiSelectOptions").find("input[value='"+oldV+"']").parent("label").text().trim();
	var newPcName = $("#batchSelect").next(".multiSelectOptions").find("input[value='"+pcId+"']").parent("label").text().trim();
	
	if (newPcName.indexOf("T") != -1) {
		$(obj).val($(obj).attr("oldvalue"));
		message({
			html : "不能修改为退费批次,如需修改点击批量退费按钮！"
		});
		return;
	}
		
	$.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/doctorAdvice/ydPCSubmit',
		dataType : 'json',
		cache : false,
		data : {
			"pcDataList" : pidsj,
			"parentnoS" : parentno,
			"piId" : pcId,
			"isSecendAdvice" : isSecendAdvice,
			"oldPcIdListS" : $(obj).attr("oldvalue"),
			"oldPcNameS" : oldPcName,
			"newPcNameS" : newPcName,
			"from" : "management",
			"yyrq":$("#yyrq").val(),
			"yzlx":yzlx
		},
		success : function(response) {
			if (response.code == 0) {
				$(obj).attr("oldvalue", pcId);
				$(obj).parents("tr:first").css("color", "#C71585");

			} else {
				$(obj).val($(obj).attr("oldvalue"));
				message({
					html : response.msg
				});
			}
			
			$("#scanText").focus();
		}
	});
}

//取datatable 选中行数据
function getDataTableSelectRowData($dom, DataTableAPi, key){
    var ary = new Array();
    $($dom).find('tbody input[type="checkbox"]:checked').each(function(){
        var v = DataTableAPi.row($(this).parents('tr')).data()[key];
        if (v) {
            ary.push(v);
        }
    });
    return ary;
}
</script>

</html>