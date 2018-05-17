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
		
		

		var _columnWidth = ( (parseInt(_pageWidth)-200)/2-20 ) / 5;
		//alert(_pageWidth+"---"+(parseInt(_pageWidth)-400)/2);
		$("#flexGrid__1").flexigrid({
			width : (parseInt(_pageWidth)-300)/2,
			height : _pageHeight-160,
			url: "${pageContext.request.contextPath}/pati/patiList",
			dataType : 'json',
			colModel : [ 
						{display: 'inhospNo', name : 'inhospno', width : 0, sortable : false, align: 'center',hide:'true'},
						
						{display: '<spring:message code="pivas.patient.birthday"/>', name : 'birthDay', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.age"/>', name : 'ageDetail', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.avdp"/>', name : 'avdp', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.bedNo"/>', name : 'bedno', width : _columnWidth, sortable : true, align: 'center'}
					],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			rowbinddata : true,
			rowhandler : rowClick__1
		});
		

		$("#aSearch__1").bind("click",function(){
			qry__1();
		});
		qry__1();
		
		
	});
	
	function qry__1(){
		var params = {} ;
		$('#flexGrid__1').flexOptions({
			newp: 1,
			extParam: params,
        	url: "${pageContext.request.contextPath}/pati/patiList"
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
	
	function rowClick__1(r) {
		$(r).click(
		function() {
			//获取该行的所有列数据
			var columnsArray = $(r).attr('ch').split("_FG$SP_");
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/doctorAdvice/yzListByPati',
				dataType : 'html',
				cache : false,
				showDialog: false,
					data : {"inpatientNo":columnsArray[0]},
				success : function(data) {
					$("#yzInfo__1").html(data).show();
					$("#yzMain__1").hide();
					$("#yzInfo__1 .panelHeadBtTD").bind("click",function(){
						$err = $("#checkNOReason_"+$(this).attr("pidsj"));
						if($err.css("display")=="block"){
							$err.css("display","none");
						}else{
							$err.css("display","block");
						}
					});
					$("#yzInfo__1 .panelHeadBtTD").each(function(){$(this).parent().css("background-color","transparent")});
				}
			});
		});
	}
	
	
	</script>
	
	
</head>
<body>



<div class="medicine-tab" style="" ><!-- max-width: 1100px; -->
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
			<div class="divLeftMenuList" >
				<table class="tabWith100" >
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
			<td style="width: 50%" >
			
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
					<a class="button" id="aPrint__1" style="display: ;">打印</a>
				</div>
			
				<div style="width: 100%;    display: flex;margin-top: 18px;" >
					<table id="flexGrid__1" style="margin: 0px;"></table>
				</div>
				
			</td>
			<td style="vertical-align: top;">
			
			<div class="rightDiv" style="background-color: white;width:100%;margin-top: 10px;" >
				<div style="background-color: #6E96E4;text-align: center;color:white;height:40px;padding-top: 10px;font-size: 14px;">
				处方信息
				</div>
				<div style="padding-left: 10px;padding-right: 10px;margin-top: 10px;" >
				<table>
					<tr style="background: transparent;" >
						<td style="width:100%;text-align: right;" colspan="4" >
							<a class="button" id="aPrint__1" style="display: ;background-color: #73AB66">通过</a>
							<a class="button" id="aPrint__1" style="display: ;">不通过</a>
						</td>
					</tr>
					<tr>
						<td colspan="4" style="height: 10px;">
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							病区：
						</td>
						<td width="30%" >
							普外一病区
						</td>
						<td width="20%" style="text-align: right;" >
							批次：
						</td>
						<td width="30%" >
							1
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							病区：
						</td>
						<td width="30%" >
							普外一病区
						</td>
						<td width="20%" style="text-align: right;" >
							批次：
						</td>
						<td width="30%" >
							1
						</td>
					</tr>
					<tr>
						<td colspan="4" style="height: 10px;">
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							病区：
						</td>
						<td width="30%" >
							普外一病区
						</td>
						<td width="20%" style="text-align: right;" >
							批次：
						</td>
						<td width="30%" >
							1
						</td>
					</tr>
					<tr style="background: transparent;" >
						<td colspan="4" style="height:10px;background-color: white;" >
					</tr>
				</table>
				
				<table>
					<tr style="background: #B5B5B5;" >
						<td width="15%" >编码</td>
						<td width="40%" >药品名称</td>
						<td width="15%" >规则</td>
						<td width="15%" >用法</td>
						<td width="15%" >皮试</td>
					</tr>
					<tr>
						<td>125</td>
						<td>达到按到完全带我去</td>
						<td>100ml</td>
						<td>每天一次</td>
						<td></td>
					</tr>
					<tr style="background: #D9D9D9;">
						<td>125</td>
						<td>达到按到完全带我去</td>
						<td>100ml</td>
						<td>每天一次</td>
						<td></td>
					</tr>
				</table>
				
				<table>
					<tr style="height: 30px;">
						<td>
							自动审方结果
						</td>
					</tr>
					<tr style="height: 30px;">
						<td>
							<select>
								<option>不通过原因</option>
							</select>
							<input type="text" style="float: right;" > 
						</td>
					</tr>
					<tr>
						<td>
							<div style="border: #d4d4d4 1px solid;    font-size: 14px; padding-top: 10px;padding-left: 10px;padding-bottom: 10px;" >
							<label style="color:#C02624">年龄禁忌</label>
							<br>
							描述：老年患者身体机能降低，故用药期间注意观察。
							</div>
							
							
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