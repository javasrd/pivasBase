<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="/WEB-INF/views/common/common.jsp" %>

<!DOCTYPE html>
<html>
<head>

    <link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>
	 <style type="text/css">
	.medicine-tab{
		margin-top: 10px;
	}
	.tabs-title{
		height: 28px;
	}
	.content {
	  width: 99%;
  	}
  	
  	.classX{
  		width: 15px;
  		min-height: 15px;
  	 	margin-top: -17px;
  	 	margin-left: 250px;
    	background: url('${pageContext.request.contextPath}/assets/search/images/search_reset.gif') center center no-repeat;
  	}
	.rightDiv table tr:nth-child(2n){
		background: transparent;
	}
	.rightDiv table tr td{
		 padding: 0px 2px;
	}
	.fontBold{
		 font-weight: bold;
	}
	.cbit-grid div.bDiv td{
		background-color: transparent;
	}
	.cbit-grid tr.erow td{
		background-color: transparent;
		background: transparent;
	}
	/*
	tr:nth-child(2n){
		background-color: white;
		background: white;
	}*/
	.trSelect{
		background-color: #E1E9F8;
	}
	
</style>


	<style type="text/css">
	/*药物优先级 左中右样式 --开始------------------*/
	.td200Left{
		width: 200px;vertical-align: top;
	}
	.divLeftMenuAll{
		height: 32px;padding-top: 10px; padding-left: 24px; font-size: 14px;
	}
	.divLeftMenuList{
		overflow-y: auto;  padding-left: 10px;padding-right: 5px; font-size: 14px;
	}
	.tabWith100{
		width:100%;
	}
	.tabWith50{
		width:50%;
	}
	.tdLeft10{
		padding-left: 10px;cursor: pointer;
	}
	.tabMain2{
		height: 100%;width:100%;padding-left: 10px;padding-top: 20px;
	}
	.tdSortTitle{
		vertical-align: bottom ;padding-right: 5px;color: white;
	}
	.divSortTitle{
		height:100%;padding-top: 8px;padding-left:5px; background-color: #6F96E5;font-size: 14px;
	}
	.aSortTitle{
		float: right;padding-left: 10px;
	}
	.tdSortContent{
		vertical-align: top;padding-right: 5px;
	}
	.divSortContent{
		height:100%;overflow-y: auto;
	}
	.tdSortRow{
		width: 100%;height: 30px;cursor: pointer;padding-left: 10px;
	}
	
	.tdMedSelec{
		background-color: #99bbe8;
	}
	.trArea{
		height: 30px;
	}
	
	.areaSel{
		background: #6F96E5;
		color: white;
	}
	.trArea td{
		padding-left: 10px;cursor: pointer;
	}
					
	#divSynToOther tr:nth-child(2n) {
	    background: transparent;
	}
	
	/*药物优先级 左中右样式 --结束------------------*/
	
	</style>
	
	<script type="text/javascript">
	var _pageWidth = 0;
	var _pageHeight = 0;
	var areaCodeNow__1 = "${deptCodeFirst}";
	var areaCodeNow__2 = "${deptCodeFirst}";
	
	function resizePageSize(){
		_pageWidth = $(window).width();
		_pageHeight = $(window).height();
		
	}
	
	$(function() {
		$(window).resize(function(){
			resizePageSize();
		});
		resizePageSize();
		
		showSearch1();
		$(".tab-title-1").bind("click",function(){
			showSearch1();
		});
		$(".tab-title-2").bind("click",function(){
			showSearch2();
		});
		
		$(".divLeftMenuList").css("height",_pageHeight-100);
		
		var _columnWidth = ( (parseInt(_pageWidth)-260)/2 -20) / 5;
		$("#flexGrid__1").flexigrid({
			width : (parseInt(_pageWidth)-260)/2,
			height : _pageHeight-180,
			url: "${pageContext.request.contextPath}/doctorAdvice/qryPtPageByYZ",
			dataType : 'json',
			colModel : [ 
						{display: 'inhospNo', name : 'inhospno', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'parentNo', name : 'parentNo', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'pidsj', name : 'pidsj', width : 0, sortable : false, align: 'center',hide:'true'},
						
						{display: '结果', name : 'yzshzt', width : _columnWidth, sortable : true, align: 'center',process: function(v,_trid,_row) {
							return "<label id='pidsj_"+_row.pidsj+"'>"+(v==0?"未审核":(v==1?"通过":"不通过"))+"</label>";	
						}},
						{display: '床号', name : 'bedno', width : _columnWidth, sortable : true, align: 'center'},
						{display: '病人姓名', name : 'patname', width : _columnWidth, sortable : true, align: 'center'},
						{display: '组号', name : 'parentNo', width : _columnWidth, sortable : true, align: 'center'},
						{display: '审方日期', name : 'sfrqS', width : _columnWidth, sortable : true, align: 'center'}
						//{display: '床号', name : 'bedno', width : _columnWidth, sortable : true, align: 'center'},
						//{display: '姓名', name : 'patname', width : _columnWidth, sortable : true, align: 'center'},
						//{display: '出生日期', name : 'birthday', width : _columnWidth, sortable : true, align: 'center'},
						//{display: '年龄', name : 'age', width : _columnWidth, sortable : true, align: 'center',process: function(v,_trid,_row) {
						//	return v+getDicValue("ageUnit",_row.ageunit);	
						//}},
						//{display: '<spring:message code="pivas.patient.avdp"/>', name : 'avdp', width : _columnWidth, sortable : true, align: 'center'}
						],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			rowbinddata : true,
			rowhandler : rowClick__1,
			onSuccess: gridSucc__1
		});
		
		if(areaCodeNow__1){
			$("#tabArea__1 td:first").addClass("areaSel");
			qry__1();
		}
		
		$("#aSearch__1").bind("click",function(){
			qry__1();
		});
		
		$("#tabArea__1 td").bind("click",function(){
			areaCodeNow__1 = $(this).attr("areacode");
			areaNameNow__1 = $(this).html();
			$(this).parent().parent().find("tr").each(function(){
				$(this).removeClass("areaSel");
				$(this).css("background","transparent");
			});
			$(this).parent().addClass("areaSel"); 
			$(this).parent().css("background","#6F96E5");
			qry__1();
		});
		
		$("#aCheckOK__1").bind("click",function(){
			var pidsj = $("#yzInfo__1 [name=pidsj]").html();
			var freqCode = $("#yzInfo__1 [name=freqCode]").html();
			var drugname = $("#yzInfo__1 [name=drugname]").html();
			checkErr = "" ;
			if("${SYN_YZ_DATA_MODE}"=="3"){
				if(checkErr==""){// && yzlx==0
					var f = 0 ;
					var freqCode = freqCode.toUpperCase();
					if($("#ruleKey__1 [name="+freqCode+"]").length>0){//如果 关键字规则 没有
						$("#ruleKey__1 [name="+freqCode+"]").each(function(){
							f = -1 ;
							if( drugname.indexOf($(this).html())>-1 ){
								f = 1; 
							}
						});
					}
					if(f<1){
						if($("#batchRule [name="+freqCode+"]").length>0){//如果 没有一般规则对应的频次
							f = 2 ;
						}else{
							f = f -1 ;
						}
					}
					if(f==-2){
						checkErr = "频次["+freqCode+"] 没有找到对应规则，无法审核通过";
					}else if(f<1){
						checkErr = "频次["+freqCode+"] 没有对应的批次数据，无法审核通过";
					}
				
				}
				if(checkErr!=""){
					message({
		    			html: checkErr
		        	});
					return ;
				}
			}
			checkOne(pidsj,1);
		});
		
		$("#aCheckNO__1").bind("click",function(){
			var pidsj = $("#yzInfo__1 [name=pidsj]").html();
			if($("#yzshbtglx__1").val() && $("#yzshbtglx__1").val()!=""){
				$("#yzshbtglxTit__1").hide();
			}else{
				$("#yzshbtglxTit__1").show();
				return ;
			}
			checkOne(pidsj,2);
		});
		
	});
	
	function checkOne(pidsj,newYzshzt){
		var param = {"pidsjN":pidsj,"repeatCheck":"Y","newYzshzt":newYzshzt,yzshbtglx:$("#yzshbtglx__1").val(),"yzshbtgyy":$("#yzshbtgyy__1").val(),"yzParea":"1","checkType":"yssf"};
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
			dataType : 'json',
			cache : false,
			showDialog: false,
			data : param,
			success : function(response) {
				if(response.code==0){
					if(newYzshzt==1){
						$("#aCheckOK__1").css("background-color","#73AB66");
						$("#aCheckNO__1").css("background-color","#ebb800");
						$("#yzshbtglx__1").val("");
						$("#yzshbtglxTit__1").hide();
						$("#yzshbtgyy__1").val("");
						$("#pidsj_"+pidsj).html("通过");
					}else{
						$("#aCheckOK__1").css("background-color","#ebb800");
						$("#aCheckNO__1").css("background-color","#d45120");
						$("#pidsj_"+pidsj).html("不通过");
					}
				}else{
					message({html: response.msg});
				}
			}
		});
	}
	
	function qry__1(){
		//初始化右侧页面
		$("#yzInfo__1 .fontBold").each(function(){
			$(this).html("");
		});
		$("#yzInfo__1 [name=yzzt]").html("");
		$("#yzInfo__1 [name=medNames]").html("");
		$("#aCheckNO__1").css("background-color","#ebb800");
		$("#aCheckNO__1").css("background-color","#ebb800");
		
		$("#medicTab__1 .medicTd").remove();
		
		
		var parms = {} ;
		parms['ydztLowN'] = 5;
		parms['yzztLowN'] = 1;
		parms['areaEnabled'] = 1;
		parms['filterTimeLow12']=1;
		parms['wardCode']=areaCodeNow__1;
		parms['yzlx'] = 0;
		parms['yzResource'] = 1;
		//parms['yzlx'] = 1;
		//parms['yzResourceUpEQN'] = 2;
		$('#flexGrid__1').flexOptions({
			newp: 1,
			extParam: parms,
        	url: "${pageContext.request.contextPath}/doctorAdvice/qryPtPageByYZ"
        }).flexReload();
	}
	
	function showSearch1(){
		$("#yzMainDiv__1").css("display","block");
		$(".tab-title-1").addClass("select");
		$("#yzMainDiv__2").css("display","none");
		$(".tab-title-2").removeClass("select");
		
		$("#yzMainDiv__1").css("display","block");
		$("#yzMainDiv__1 .oe_searchview222").removeClass("oe_searchview222").addClass("oe_searchview");
		$("#yzMainDiv__1 .oe_searchview_head222").removeClass("oe_searchview_head222").addClass("oe_searchview_head");
		$("#yzMainDiv__1 .divselect222").removeClass("divselect222").addClass("divselect");
		$("#yzMainDiv__1 .oe_search_txt222").removeClass("oe_search_txt222").addClass("oe_search_txt");
		$("#yzMainDiv__1 .oe_tag_dark222").removeClass("oe_tag_dark222").addClass("oe_tag_dark");
		
		$("#yzMainDiv__2").css("display","none");
		$("#yzMainDiv__2 .oe_searchview").removeClass("oe_searchview").addClass("oe_searchview222");
		$("#yzMainDiv__2 .oe_searchview_head").removeClass("oe_searchview_head").addClass("oe_searchview_head222");
		$("#yzMainDiv__2 .divselect").removeClass("divselect").addClass("divselect222");
		$("#yzMainDiv__2 .oe_search_txt").removeClass("oe_search_txt").addClass("oe_search_txt222");
		$("#yzMainDiv__2 .oe_tag_dark").removeClass("oe_tag_dark").addClass("oe_tag_dark222");
	}
	function showSearch2(){
		$("#yzMainDiv__2").css("display","block");
		$(".tab-title-2").addClass("select");
		$("#yzMainDiv__1").css("display","none");
		$(".tab-title-1").removeClass("select");
		
		$("#yzMainDiv__2").css("display","block");
		$("#yzMainDiv__2 .oe_searchview222").removeClass("oe_searchview222").addClass("oe_searchview");
		$("#yzMainDiv__2 .oe_searchview_head222").removeClass("oe_searchview_head222").addClass("oe_searchview_head");
		$("#yzMainDiv__2 .divselect222").removeClass("divselect222").addClass("divselect");
		$("#yzMainDiv__2 .oe_search_txt222").removeClass("oe_search_txt222").addClass("oe_search_txt");
		$("#yzMainDiv__2 .oe_tag_dark222").removeClass("oe_tag_dark222").addClass("oe_tag_dark");
		
		$("#yzMainDiv__1").css("display","none");
		$("#yzMainDiv__1 .oe_searchview").removeClass("oe_searchview").addClass("oe_searchview222");
		$("#yzMainDiv__1 .oe_searchview_head").removeClass("oe_searchview_head").addClass("oe_searchview_head222");
		$("#yzMainDiv__1 .divselect").removeClass("divselect").addClass("divselect222");
		$("#yzMainDiv__1 .oe_search_txt").removeClass("oe_search_txt").addClass("oe_search_txt222");
		$("#yzMainDiv__1 .oe_tag_dark").removeClass("oe_tag_dark").addClass("oe_tag_dark222");
	}
	function gridSucc__1(grid){
		$("#flexGrid__1 tr:first").addClass("trSelect");
		var columnsArray =$("#flexGrid__1 tr:first").attr('ch').split("_FG$SP_");
		showYZ__1(columnsArray[2]);
	}
	function rowClick__1(r) {
		
		$(r).click(
		function() {
			$("#flexGrid__1 tr").each(function(){
				$(this).removeClass("trSelect");
			});
			$(r).addClass("trSelect");
			//获取该行的所有列数据
			var columnsArray = $(r).attr('ch').split("_FG$SP_");
			showYZ__1(columnsArray[2]);
		});
	}
	
	function showYZ__1(pidsj){
		if(pidsj && pidsj!=""){
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/doctorAdvice/dctrAdvcInfo',
				dataType : 'json',
				cache : false,
				showDialog: false,
				data : {"pidsj":pidsj},
				success : function(result) {
					if(result && result.code>0){
						var yzMain = result.data.yzMain;
						$("#yzInfo__1 .fontBold").each(function(){
							$(this).html(yzMain[$(this).attr("name")]?yzMain[$(this).attr("name")]:"");
						});
						$("#yzInfo__1 [name=yzzt]").html(getDicValue('yzzt',yzMain['yzzt']));
						$("#yzInfo__1 [name=medNames]").html(result.data.medNames?result.data.medNames:"");
						
						//ebb800 黄色。73AB66 绿色，d45120 红色
						if(yzMain.yzshzt==1){
							$("#aCheckOK__1").css("background-color","#73AB66");
							$("#aCheckNO__1").css("background-color","#ebb800");
							$("#yzshbtglxTit__1").hide();
							$("#yzshbtglx__1").val("");
							$("#yzshbtgyy__1").val("");
						}else if(yzMain.yzshzt==2){
							$("#aCheckOK__1").css("background-color","#ebb800");
							$("#aCheckNO__1").css("background-color","#d45120");
							
							$("#yzshbtglx__1").val(yzMain.yzshbtglx);
							$("#yzshbtgyy__1").val(yzMain.yzshbtgyy);
						}else if(yzMain.yzshzt==0){
							$("#yzshbtglx__1").val("");
							$("#yzshbtgyy__1").val("");
						}
						$("#medicTab__1 .medicTd").remove();
						var yzList = result.data.yzList;
						for(var i in yzList){
							$("#medicTab__1").append("<tr class='medicTd'><td>"+yzList[i].chargeCode+"</td><td>"+yzList[i].medicamentsName+"</td><td>"+yzList[i].specifications2+"</td><td>"+yzList[i].dose+yzList[i].doseUnit+"</td><td>"+yzList[i].quantity+yzList[i].medicamentsPackingUnit+"</td></tr>");
						}
					}else{
						$("#yzInfo__1 .fontBold").each(function(){
							$(this).html("");
						});
						$("#medicTab__1 .medicTd").remove();
					}
				}
			});
		}else{
			alert(11);
		}
	}
	
	</script>
	
	
</head>
<body>



<div class="medicine-tab" style="    width: 98%;" ><!-- max-width: 1100px; -->
<div class="tabs-title" >
		<ul style="  float: left;">
			<li><a style="width: 90px;display: block;height: 26px;line-height: 26px;width: 90px;text-align: center;cursor: pointer;" class="tab-title-1 select">长嘱</a></li>
			<li><a style="width: 90px;display: block;height: 26px;line-height: 26px;width: 90px;text-align: center;cursor: pointer;" class="tab-title-2 ">临嘱</a></li>
		</ul>
</div>
<div class="tabs-content" style="margin-top: -2px;border-top: 1.5px solid;">
	
	
	<div style="height:0px;">
	
			<ul id="batchRule" style="visibility: hidden;height: 0px;" >
			<c:forEach items="${batchRuleList}" var="batchRule" >
			<c:if test="${empty batchRule.ru_key}">
			<li name="${batchRule.pinc_code}" >${batchRule.pinc_name}</li>
			</c:if>
			</c:forEach>
			</ul>
			
			<ul id="ruleKey" style="visibility: hidden;height: 0px;" >
			<c:forEach items="${ruleList}" var="rule" >
			<c:if test="${!empty rule.ru_key}">
			<li name="${rule.pinc_code}" >${rule.ru_key}</li>
			</c:if>
			</c:forEach>
			</ul>
			
	</div>
	
	
	<div class="tab-content-1"  style="display: block;">
	<div id="yzMainDiv__1" class="main-div">
	 	
	 	
	<table style="height: 100%;width:100%;" >
	<tr>
		<td class="td200Left" style="padding: 0px 0px;">
			<div class="divLeftMenuAll" >
			全部
			</div>
			<div class="divLeftMenuList" style="overflow-y: auto;" >
				<table class="tabWith100" id="tabArea__1" >
					<c:forEach items="${inpAreaList}" var="area" varStatus="rowStatus" >
					<tr class="trArea <c:if test="${rowStatus.index==0}">areaSel</c:if>" >
						<td class="tdLeft10" areacode="${area.deptCode}" >${area.deptName}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</td>
		<td style="vertical-align: top;    padding: 0px 4px;">
			
			
			<table>
			<tr>
			<td style="width: 50%;vertical-align: top;min-width: 400px;    padding: 0px 3px;" >
			
				<!-- 搜索条件--开始 -->
				<div class="oe_searchview" style="margin-top: 12px;width:270px;min-height: 30px;"><!-- 0115bianxw修改 添加宽度设置 -->
				    <div class="oe_searchview_facets" >
					    <div class="oe_searchview_input oe_searchview_head"></div>
					    <div class="oe_searchview_input"  id="inputsearch__1" >
					    	  <input id="txt__1" type="text" class="oe_search_txt" style="border: none;max-height: 18px;width:130px;"/>
					    </div>
				    </div>
				    <img alt="" style="top:55px;position: absolute;" src="${pageContext.request.contextPath}/assets/search/images/searchblue.png">
				    <div class="classX" onclick="clearclosedinputall();" style="" ></div><!-- 0115bianxw修改 left: 735px; -->
					<div class="oe-autocomplete" ></div>
					<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
						<cite>请选择...</cite>
						<ul class="ulQry" style="-webkit-border-radius: 20;" funname="qryList__1" >
							<li show="<spring:message code='pivas.yz2.bedno'/>" name="bednoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.bedno'/>：<span class="searchVal"></span></li>
							<li show="<spring:message code='pivas.yz2.patname'/>" name="patnameS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.patname'/>：<span class="searchVal"></span></li>
							<li show="<spring:message code='pivas.yz1.parentNo'/>" name="parentNoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.parentNo'/>：<span class="searchVal"></span></li>
							<li show="<spring:message code='pivas.yz1.wardname'/>" name="wardNameS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.wardname'/>：<span class="searchVal"></span></li>
							<li show="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.freqCode'/>：<span class="searchVal"></span></li>
							<li show="<spring:message code='pivas.yzyd.zxbc'/>" name="zxbcS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yzyd.zxbc'/>：<span class="searchVal"></span></li>
						</ul>
					</div>
					
				</div>
				<!-- 搜索条件--结束 -->
				
				<div style="margin-top: 17px;">
					<a class="button" id="aPrint__1" style="display: ;background-color: #73AB66">通过</a>
				</div>
				
				
				<div style="width: 100%;    display: flex;margin-top: 18px;" >
					<table id="flexGrid__1" style="margin: 0px;"></table>
				</div>
				
			</td>
			<td style="vertical-align: top;padding: 0px 3px;">
			
			<div class="rightDiv" style="background-color: white;width:100%;margin-top: 10px;" >
				<div style="background-color: #6E96E4;text-align: center;color:white;height:40px;padding-top: 10px;font-size: 14px;">
				处方信息
				</div>
				<div style="padding-left: 10px;padding-right: 10px;margin-top: 7px;" >
				<table id="yzInfo__1" >
					<tr style="background: transparent;" >
						<td style="width:100%;text-align: right;" colspan="4" >
							<a class="button" id="aCheckOK__1">通过</a>
							<a class="button" id="aCheckNO__1">不通过</a>
						</td>
					</tr>
					<tr>
						<td colspan="4" style="height: 7px;">
							<label class="fontBold" name="pidsj" style="visibility: hidden;" />
							<label class="fontBold" name="drugname" style="visibility: hidden;" />
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							开立医生：
						</td>
						<td width="30%" class="fontBold" name="doctorName" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							频次：
						</td>
						<td width="30%" class="fontBold" name="freqCode" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							开立时间：
						</td>
						<td width="30%" class="fontBold" name="startTimeS" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							医嘱状态：
						</td>
						<td width="30%" name="yzzt" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							结束时间：
						</td>
						<td width="30%" class="fontBold" name="endTimeS" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							药品类型：
						</td>
						<td width="30%" name="medNames" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							用药途径：
						</td>
						<td width="30%" class="fontBold" name="supplyName" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							滴速：
						</td>
						<td width="30%" class="fontBold" name="dropSpeed" >
							
						</td>
					</tr>
					<tr>
						<td colspan="4" style="height: 7px;"></td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							病人姓名：
						</td>
						<td width="30%" class="fontBold" name="patname" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							床号：
						</td>
						<td width="30%" class="fontBold" name="bedno" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							性别：
						</td>
						<td width="30%" class="fontBold" name="sex" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							年龄：
						</td>
						<td width="30%" class="fontBold" name="age" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							出生日期：
						</td>
						<td width="30%" class="fontBold" name="birthdayS" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							体重：
						</td>
						<td width="30%" class="fontBold" name="avdp" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							住院号：
						</td>
						<td width="30%" class="fontBold" name="caseId" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							流水号：
						</td>
						<td width="30%" class="fontBold" name="inpatientNo" >
							
						</td>
					</tr>
					
					<tr style="background: transparent;" >
						<td colspan="4" style="height: 7px;"></td>
					</tr>
				</table>
				
				<table id="medicTab__1">
					<tr style="background: #B5B5B5;" id="medicTr__1" >
						<td width="15%" >药品编码</td>
						<td width="40%" >药品名称</td>
						<td width="15%" >规则</td>
						<td width="15%" >剂量</td>
						<td width="15%" >数量</td>
					</tr>
					<!-- <tr class="medicTd"><td>125</td><td>达到按到完全带我去</td><td>100ml</td><td>每天一次</td><td></td></tr> -->
				</table>
				<hr style="border : 1px dashed gray;" />
				<table>
					<tr style="height: 30px;font-size: 15px;">
						<td id="yzAutoResult__1">
							自动审方结果
						</td>
					</tr>
					<tr style="height: 40px;">
						<td>
							<select style="height: 23px;" id="yzshbtglx__1" >
								<option value=""><spring:message code='comm.mess19'/></option>
								<c:forEach items="${errTypeList}" var="errType"  >
								<option value="${errType.gid}">${errType.name}</option>
								</c:forEach>
							</select>
							<span class="tip" id="yzshbtglxTit__1" style="display: none;">
                                        <div class="arrow" style="left:0px;">
                                            <div class="tip-content" style="width:93px;top: -16px;left: 15px;"><!-- 0115bianxw修改 top: -28px;left: -130px; -->
                                                		请选择原因<!--请选择不通过原因-->
                                            </div>
                                        </div>
                            </span>
							<input type="text" style="float: right;" id="yzshbtgyy__1" > 
						</td>
					</tr>
					<tr>
						<td>
							<div style="border: #d4d4d4 1px solid;font-size: 14px; padding-top: 10px;padding-left: 10px;padding-bottom: 10px;" >
							<label style="color:#C02624">年龄禁忌</label>
							<br>
							描述：老年患者身体机能降低，故用药期间注意观察。
							</div>
							
							
							<ul id="batchRule__1" style="visibility: hidden;height: 0px;" >
							<c:forEach items="${batchRuleList}" var="batchRule" >
							<c:if test="${empty batchRule.ru_key}">
							<li name="${batchRule.pinc_code}" >${batchRule.pinc_name}</li>
							</c:if>
							</c:forEach>
							</ul>			
											
							<ul id="ruleKey__1" style="visibility: hidden;height: 0px;" >
							<c:forEach items="${ruleList}" var="rule" >
							<c:if test="${!empty rule.ru_key}">
							<li name="${rule.pinc_code}" >${rule.ru_key}</li>
							</c:if>
							</c:forEach>
							</ul>
							
							
							
						</td>
					</tr>
				</table>
				</div>
			</div>
			

			
			
			</td>
			</tr>
			</table>
	



			
		</td>
	</tr>
	</table>
	</div>


	<div id="yzMainDiv__2" class="tab-content-2" style="display:none;" >
	


	
	</div>
	
	</div>   

</div>
</div>

</body>

</html>