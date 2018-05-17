<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../../common/common.jsp"%>

<html>
<meta charset="UTF-8">
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/assets/extjs/ext-all.js"></script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/assets/extjs/locale/ext-lang-zh_CN.js"></script>--%>
<%--<link type="text/css" href="${pageContext.request.contextPath}/assets/extjs/resources/css/ext-all.css" rel="stylesheet" />--%>

<link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/assets/pivas/css/edit.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>

<style type="text/css">
	.medicineListItem{
		padding:0px 10px;
	}	
	
	.medicineListItem_div{
		display:inline-block;
		float:right;
	}
	
	.button_a{
		height:30px;
		width:80px;
		font-size:13px;
		background: #EBB701;
		color:white;
	}
	
	.button_b{
		color:#BBBBBB;
		border:1px solid #BBBBBB;
		height:30px;
		width:80px;
		font-size:13px;
		background:#ffffff;
	}
	a.multiSelect:link,a.multiSelect:visited, a.multiSelect:hover, a.multiSelect:active{
		margin-top: 0px;
	}
	span{
		font-size:12px;
	}
	
	td{
		vertical-align:top;
		padding:0;
		font-size:13px;
		color:#636363;
	}
	.mainPanelRight{
		width:82%;
		height: 100%;
		float:right;
	}
	
	.checkbox_a{
		vertical-align:middle;
		width:20px;
		height:20px;
		margin-right:10px
	}
	
	.checkbox_b{
		width:16px;
		height:16px;
		margin-top: 2px;
	}
	
	#labelPanel th{
		color:#fff;
		font-size:14px;
	}
	
	#printLableList td{
		color:#636363;
		font-size:13px;
		
	}
	
	#printLableList td div{
		color:#636363;
		font-size:13px;
		
	}
	.tit{
		font-size:13px;
		color:#636363;
		width:100%;
	}
	
	.td_search_right{
		width: 7%;
		text-align: right;
		vertical-align: middle;
	}
	
	.td_search_right_two{
		text-align: right;
		vertical-align: middle;
		float:left;
		height: 25px;
		line-height:25px;
	}
	
	.td_search_left{
		vertical-align: middle;
	}
	
	.td_search_left_two{
		vertical-align: middle;
		float:left;
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

.showLabelItem{
	width:100%;
	height:49%;
	padding:0 4%;
}

.labelItem{
	float:left;
	width:25%;
	height:100%;
	color:#404040;
	padding:5px;
}

.labelItemDoc{
	border:1px solid #707070;
	width: 150%;
	height: 150%;
 	transform:scale(0.66) translate(-25%,-25%) 
	
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
}

</style>
<head>

<script>
	var _gridWidth = 0;
	var _gridHeight = 0;
	var shadowWidth=0; 
	var shadowHeight=0; 
	
	//页面自适应
	function resizePageSize() {
		_gridWidth = $(document).width()-10;/*  -189 是去掉左侧 菜单的宽度，   -12 是防止浏览器缩小页面 出现滚动条 恢复页面时  折行的问题 */
		_gridHeight = $(document).height()-220; /* -32 顶部主菜单高度，   -90 查询条件高度*/
		shadowWidth= $(document).width()-100;
		shadowHeight= $(document).height()-100;
	}
	$(function() {
		$(window).resize(function(){
				resizePageSize();
			});
		resizePageSize();
	});
</script>

<body >
	<div>
		<div style="width:100%;padding:15px;margin-bottom:15px;border-radius:10px;">
			<span style="font-size: 13px;color:#636363">设置条件</span>
			<select style="height: 30px;width: 320px;font-size: 13px;color:#636363" id="printConfigSelect" readonly>
				<option value=""> <spring:message code="common.select" /> </option>
				<c:forEach items="${printLableLConfList}" var="item">
					<c:if test="${item.useType == 0}">
						<option value="${empty item.yyrq ? ' ':item.yyrq}&&${empty item.mediclabel ? ' ':item.mediclabel}&&${empty item.batchid ? ' ':item.batchid}&&${empty item.medicCategory ? ' ':item.medicCategory}&&${empty item.printState ? ' ':item.printState}&&${empty item.medical ? ' ':item.medical}&&${empty item.deptCode ? ' ':item.deptCode}&&${empty item.isPack ? ' ':item.isPack}">
							<spring:escapeBody htmlEscape="true">${item.name}</spring:escapeBody>
						</option>
					</c:if>
				</c:forEach>
			</select>
			
			<button class="ui-search-btn ui-btn-bg-green" id="advancedSearchBtn"><i class="am-icon-search"></i><span>高级查询</span></button>
			<shiro:hasPermission name="PIVAS_BTN_623">
				<button class="ui-search-btn ui-btn-bg-blue" id="printBtn"><i class="am-icon-print"></i><span>打印</span></button>
			</shiro:hasPermission>
			<shiro:hasPermission name="PIVAS_BTN_624">
				<button class="ui-search-btn ui-btn-bg-yellow" id="rePrintBtn" ><i class="am-icon-print"></i><span>重新打印</span></button>
			</shiro:hasPermission>
			<button class="ui-search-btn" id="medicStatisBtn"><i class="am-icon-area-chart"></i><span>药品统计</span></button>
			<div style="height:30px;line-height:30px;width:180px;font-size:15px;display:inline-block;margin-left: 10px;">
				瓶签数量: <span id="pageTotal" style="font-size:17px;">0</span>
			</div>
			<div style="width:180px;display: inline-block;">
				<shiro:hasPermission name="PIVAS_BTN_625">
					<input type="checkbox" class="checkbox_a" id="collectionCheckBox" checked="checked"/>
					<button class="ui-search-btn ui-btn-bg-green" style="margin-right:10px;margin-left: 0px;" id="collectionBtn"><i class="am-icon-file-text-o"></i><span>汇总单</span></button>
				</shiro:hasPermission>
				<button class="button_b" id="previewBtn" style="display:none"><i class="am-icon-search-plus"></i><span>预览</span></button>
			</div>
		</div>
		
		<div id="advancedQueryPanel" style="display:none;padding-top:0px;margin-bottom:15px;width:100%;">
			<table>
				<tr style="height: 35px;width:100%;">
					<td class="td_search_right" >
						<span class="tit">用药时间：</span>
					</td>
					<td class="td_search_left" >
						<input type="text" id="useDate" name="useDate" style="height: 25px;color: #555555;width:100%;padding:0px 5px;" class="Wdate" empty="false"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
					</td>
	
					<td class="td_search_right" >
						<span class="tit">药品标签：</span>
					</td>
					<td class="td_search_left" >
						<select class="tit" style="height: 25px;width:100%;font-size: 13px" id="medicamentsLabelNo" name="medicamentsLabelNo" multiple="multiple">
							<option value=""> <spring:message code="common.select" /> </option>
							<c:forEach items="${allMedicLabel}" var="item">
								<option value="${item.labelId}">
									<spring:escapeBody htmlEscape="true">${item.labelName}</spring:escapeBody>
								</option>
							</c:forEach>
						</select>
					</td>
					
					<td class="td_search_right" >
						<span class="tit"><spring:message code='rule.batch' />：</span>
					</td>
					<td class="td_search_left" >
						<select class="tit" style="height: 25px;width:100%;font-size: 13px" id="batchId" name="batchId" readonly>
							<option value=""> <spring:message code="common.select" /> </option>
							<option value="-1">T</option>
							<option value="0">KU</option>
							<option value="1">#</option>
							<c:forEach items="${batchList}" var="item">
								<option value="${item.id}">
									<spring:escapeBody htmlEscape="true">${item.name}</spring:escapeBody>
								</option>
							</c:forEach>
						</select>
					</td>
					
					<td class="td_search_right" >
						<span class="tit">药品分类：</span>
					</td>
					<td class="td_search_left" >
						<select class="tit" style="height: 25px;width:100%;font-size: 13px" id="categoryId" name="categoryId" multiple="multiple">
							<option value="">
								<spring:message code="common.select" />
							</option>
							<c:forEach items="${medicCategories}" var="item">
								<option  value="${item.categoryId}">
									<spring:escapeBody htmlEscape="true">${item.categoryName}</spring:escapeBody>
								</option>
							</c:forEach>
						</select>
					</td>
					
					<td class="td_search_right" >
						<span class="tit">药单/药数：</span>
					</td>
					<td class="td_search_left" >
						<select class="tit" style="height: 25px;width:90%;font-size: 13px" id="medicamentsCounts" name="medicamentsCounts" readonly>
							<option value=""> <spring:message code="common.select" /> </option>
							<c:forEach items="${medicSingleCount}" var="count">
								<option value="${count}">${count}</option>
							</c:forEach>
						</select>
					</td>
					
				</tr>
				<tr style="height: 35px;width:100%;">
					<td class="td_search_right">
						<span class="tit">是否打印：</span>
					</td>
					<td class="td_search_left" >
						<select class="tit" style="height: 25px;width:100%;font-size: 13px;" id=dybz name="dybz" readonly>
							<option value=""><spring:message code="common.select" /></option>
							<option value="0">已打印</option>
							<option value="1">未打印</option>
						</select>
					</td>
					<td class="td_search_right" >
						<span class="tit">排序：</span>
					</td>
					<td class="td_search_left" >
						<select class="tit" style="height: 25px;width:100%;font-size: 13px" id="sortType" name="sortType" readonly>
							<option value=""><spring:message code="common.select" /></option>
							<option value="0">批次</option>
							<option value="1">病区</option>
						</select>
					</td>
	
					<td class="td_search_right">
						<span class="tit">打印序号：</span>
					</td>
					<td class="td_search_left" >
						<input  class="tit" type="text" id="printIndex" style="width:160px;border:1px solid #999999;padding:0px 5px">
					</td>
					
					<td class="td_search_right">
						<span class="tit">页码：</span>
					</td>
					<td class="td_search_left" colspan="1">
						<div>
							<input class="tit" type="text" id="printStartIndex" style="width:60px;border:1px solid #999999;padding:0px 5px"/> -
							<input class="tit" type="text" id="printEndIndex"  style="width:60px;border:1px solid #999999;padding:0px 5px"/>
						</div>
					</td>
					<td class="td_search_right" >
						<span class="tit">摆药人：</span>
					</td>
					<td class="td_search_left" >
						<!-- <input type="text" id="putDrugPersonName" style="width:120px;border:1px solid #999999;padding:0px 5px"/> -->
						<select class="tit" style="width: 73px;" id ="putDrugPersonName" name="putDrugPersonName">
							<option value=""><spring:message code="common.select" /></option>
							<c:forEach items="${allUsers}" var="user">
								<option value="${user.account}">
									<spring:escapeBody htmlEscape="true">${user.account}</spring:escapeBody>
								</option>
							</c:forEach>
						</select>
					</td>
					
				</tr>
				
				<tr style="height: 35px;width:100%;" funname="condiQuery" data-qryMethod>
					<%--
					<td></td>
					<td class="">
						<div class="oe_searchview" style="margin-top:-1px; width: 100%x;z-index: 999; ">
						<div class="oe_searchview_facets">
							<div class="oe_searchview_input oe_searchview_head"></div>
							<div class="oe_searchview_input" id="inputsearch__1">
								<input id="txt__1" type="text" class="oe_search_txt"
									style="border: none; max-height:18px; width: 220px;" />
							</div>
						</div>
						<img alt="" style="top:160px;position: absolute;" src="${pageContext.request.contextPath}/assets/search/images/searchblue.png">
						<div class="oe_searchview_clear" onclick="clearclosedinputall();" style="left: 355px;position: absolute;top: 155px;"></div>
						<div class="oe-autocomplete"></div>
						<div style="border: 1px solid #D2D2D2; display: none;" width="50px" heiht="50px" class="divselect" >
						<cite>请选择...</cite>
						<ul class="ulQry" style="-webkit-border-radius: 20;" funname="condiQuery">
						<li show="<spring:message code='pivas.yz2.bedno'/>" name="bednoS"><spring:message code='pivas_yz1.search' /><spring:message code='pivas.yz2.bedno' />：<span class="searchVal"></span></li>
						<li show="<spring:message code='pivas.yz1.drugname'/>" name="drugnameQry" ><spring:message code='pivas_yz1.search'/><spring:message code='pivas.yz1.drugname'/>：<span class="searchVal"></span></li>
						</ul>
						</div>
						</div> <!-- 搜索条件--结束 -->
					</td>
					--%>
					<td class="td_search_right"><spring:message code='pivas.yz2.bedno'/>：</td>
					<td class="td_search_left"><input class="tit" name="bednoS" data-cnd="true" style="border:1px solid #999999;padding:0px 5px"></td>
					<td class="td_search_right"><spring:message code='pivas.yz1.drugname'/>：</td>
					<td class="td_search_left"><input class="tit" name="drugnameQry" data-cnd="true" style="border:1px solid #999999;padding:0px 5px"></td>
					<td></td>
					<td class="td_search_left">
						<button class="ui-search-btn ui-btn-bg-green" id="justSearch" style="float:left;margin-right:20px;">查&nbsp;&nbsp;询</button>
					</td>
					<%--<td class="td_search_right"><button class="button ui-btn-bg-green" style="float:left;margin-right:20px;" onclick="cndRest()">清&nbsp;&nbsp;空</button></td>--%>
				</tr>
			</table>
		</div>
	</div>
	<div id="mainPanel" style="width:100%;" >
		<div  style="width:17%;height: 100%;float:left; padding:10px;background: #e2e2e2;margin-right: 5px;border-radius:5px 5px 0 0">
			<div id="medicineListSearch" style="width:100%;height:30px;padding:5px 10px">
				<select class="tit" style="height: 30px;width: 100%;font-size: 13px" id="medicTotal" name="medicTotal" readonly>
					<option value=""> --药品数量选择-- </option>
					<c:forEach items="${medicTotalCount}" var="count">
						<option value="${count}">${count}</option>
					</c:forEach>
					<!-- <option value="&lt;10">&lt;=10</option>
					<option value="=20">=20</option>
					<option value="&gt;35">>=35</option> -->
				</select>
			</div>
			<div id="medicineListShowPanel" style="width:100%;overflow:auto;height:400px;padding:10px;">
				<table id="medicineListShowTable">
					<!-- <tr>
						<td><input type="checkbox" class="checkbox_b" value="1"></td>
						<td><span>&nbsp;&nbsp;(64)&nbsp;&nbsp;</span></td>
						<td style='word-break:break-all;'><span >复方氨基酸注射液(9AA,5.6%)</span></td>
						<td style='display:none'></td>
					</tr> -->
				</table>
			</div>
		</div>
		<div class="mainPanelRight">
			<div id="labelPanel" style="width:100%;height:100%">
				<table id="prtDataTable" width="100%">
					<thead>
					<tr>
						<td><input type="checkbox" id="all_checked"></td>
						<td>病区</td>
						<td>床号</td>
						<td>批次</td>
						<td>组号</td>
						<td>打印状态</td>
						<td>病人</td>
						<td>频次</td>
						<td>输液量</td>
						<td>药品</td>
						<td>用药时间</td>
						<td>单词剂量</td>
						<td>剂量单位</td>
						<td>打印时间</td>
						<td>打印人</td>
					</tr>
					</thead>
				</table>
        	</div>
		</div>
	</div>
	
	<!--预览  -->
	<div id="labelListShadow" class="labelshadow" style="background:#000000;opacity:0.3;display:none">
		
		<img alt="" src="/pivasBase/assets/common/images/loader.gif" style="width:60px;height:60px;position: fixed;left:50%;top:50%;margin-left: -30px;margin-top: -30px">
	</div>
	<div id="labelList" class="labelshadow"  style="background:#ffffff;opacity:1;color:#ffffff;display: none;border-radius:10px;">	
		<div class="labelListTitle">
			<span style="font-size:16px;font-weight:700;font-family: 微软雅黑;">瓶签</span>
			<img style="float:right;margin-top: 11px" onclick="closeListShadow()" src="${pageContext.request.contextPath}/assets/pivas/images/close-icon.png">
		</div>
		<%-- <div style="margin:15px 15px 10px 15px;text-align: right;">
			<shiro:hasPermission name="PIVAS_BTN_624">
				<button class="labelListBtn" id="labelListBtn">打印</button> 
			</shiro:hasPermission>
		</div>	 --%>
		<div id="labelListContain" style="width:100%;height:94%;background: #525659;overflow:auto;border-bottom-left-radius: 5px;border-bottom-right-radius: 5px;">
			<iframe id="labelPdfFrame" style="width:100%;height:100%;padding:0px 5px 5px 5px;" scrolling="yes" marginwidth="0" marginheight="0" frameborder="0" src="${pageContext.request.contextPath}/assets/label/showLabelList.html"></iframe>
		</div>
	</div>
	
	<!-- 放大 -->
	<div id="labelSingleShadow" class="labelshadow" style="background:#000000;opacity:0.3;z-index: 9998;display: none;"></div>
	<div id="labelSingleInfo" class="labelshadow" style="background:#ffffff;opacity:1;z-index: 9999;display: none;border-radius:10px;">
		<div class="labelListTitle">
			<span style="font-size:16px;font-weight:700;font-family: 微软雅黑;">瓶签</span>
			<img style="float:right;margin-top: 11px" onclick="closeSingleShadow()" src="${pageContext.request.contextPath}/assets/pivas/images/close-icon.png">
		</div>
		<div style="margin:15px 15px 10px 15px;text-align: right;">
			<shiro:hasPermission name="PIVAS_BTN_624">
				<button class="labelListBtn" style="color:white" id="labelSingleBtn">打印</button> 
			</shiro:hasPermission>
		</div>
		<input type="hidden" id="singleLabelPidjs"/>
		<div style="width:100%;height:83%;background: #ffffff;padding:20px;">
			<div style="border:2px solid #3E3E3E;width:100%;height:100%;">
				<div id="labelSingleContain" style="width:75%;height:75%;background:#ffffff"></div>
			</div>
		</div>
	</div>
	<!--汇总单 -->
	<div id="statisticLabelShadow" class="labelshadow" style="background:#000000;opacity:0.3;display: none;"></div>
	<div id="statisticLabelInfo" class="labelshadow"  style="background:#ffffff;opacity:1;color:#ffffff;display: none;border-radius:10px;">	
		<div class="labelListTitle">
			<span style="font-size:16px;font-weight:700;font-family: 微软雅黑;">汇总单</span>
			<img style="float:right;margin-top: 11px" onclick="closeStatisticShadow()" src="${pageContext.request.contextPath}/assets/pivas/images/close-icon.png">
		</div>
		<div style="margin:15px 15px 10px 15px;text-align: right;">
			<shiro:hasPermission name="PIVAS_BTN_625">
				<button class="labelListBtn" id="printCollecttionbtn">打印</button> 
			</shiro:hasPermission>
		</div>	
		<div id="statisticLabeContain" style="width:100%;height:83%;overflow:auto;padding:0px 20px;color:#323232;">
		</div>
	</div>
	
	<!-- 新加的功能 用于药品汇总的 --> 
	<div id="statisticMedicShadow" class="labelshadow" style="background:#000000;opacity:0.3;display: none;"></div>
	<div id="statisticMedicInfo" class="labelshadow"  style="background:#ffffff;opacity:1;color:#ffffff;display: none;border-radius:10px;">	
		<div class="labelListTitle">
			<span style="font-size:16px;font-weight:700;font-family: 微软雅黑;">药品数量统计</span>
			<img style="float:right;margin-top: 11px" onclick="closeStatMedicShadow()" src="${pageContext.request.contextPath}/assets/pivas/images/close-icon.png">
		</div>
		
		<div id="tabs" style="min-height:80px;background: whitesmoke;margin-top: -7px;">
			<ul>
			    <li><a href="#tabs-medic">药品汇总</a></li>
			    <li><a href="#tabs-bingx">冰箱药汇总</a></li>
			</ul>
			<div id="tabs-medic">
				<div style="margin:15px 15px 0px 15px;height:30px;">
					<%-- <shiro:hasPermission name="PIVAS_BTN_625"> --%>
						<select style="float:left;height:30px;margin-top: 0px;width:250px" id="mediStaticSelect" readonly>
							<option value=""> <spring:message code="common.select" /> </option>
						<c:forEach items="${printLableLConfList}" var="item">
							<c:if test="${item.useType == 1}">
								<option value="${empty item.yyrq ? ' ':item.yyrq}&&${empty item.mediclabel ? ' ':item.mediclabel}&&${empty item.batchid ? ' ':item.batchid}&&${empty item.medicCategory ? ' ':item.medicCategory}&&${empty item.printState ? ' ':item.printState}&&${empty item.medical ? ' ':item.medical}&&${empty item.deptCode ? ' ':item.deptCode}&&${empty item.isPack ? ' ':item.isPack}">
									<spring:escapeBody htmlEscape="true">${item.name}</spring:escapeBody>
								</option>
							</c:if>
						</c:forEach>
						</select>
						<button class="labelListBtn" style="float: left;margin-left:10px;width:80px;" id="showPrintStatMeicbtn">高级搜索</button> 
						<button class="labelListBtn" style="float: right;" id="printStatMeicbtn">打印</button> 
					<%-- </shiro:hasPermission> --%>
				</div>	
				<div id="showPrintStatMeicPanel" style="padding:0 25px 0 0px;margin-top:10px;display:none;height: 60px;">
					<div style="height: 35px">
						<div class="td_search_right_two" style="width:80px">
							<span class="tit">用药时间：</span>
						</div>
						<div class="td_search_left_two" style="width: 186px;">
							<input type="text" id="useDateSta" style="height: 25px;color: #555555;width:100%;" class="Wdate" empty="false"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
						</div>
						
						<div class="td_search_right_two" style="width:50px;">
							<span class="tit">批次：</span>
						</div>
						<div class="td_search_left_two" style="width:85px;">
							<select class="tit" style="height: 25px;width:100%;font-size: 13px;margin-top:0px;" id="batchIdSta" name="batchIdSta" readonly>
								<option value=""> <spring:message code="common.select" /> </option>
								<option value="0">KU</option>
								<option value="1">#</option>
								<c:forEach items="${batchList}" var="item">
									<option value="${item.id}">
										<spring:escapeBody htmlEscape="true">${item.name}</spring:escapeBody>
									</option>
								</c:forEach>
							</select>
						</div>
						<div class="td_search_right_two" style="width:80px;">
							<span class="tit">是否打印：</span>
						</div>
						<div class="td_search_left_two" style="width:90px;" >
							<select class="tit" style="height: 25px;width:100%;font-size: 13px;margin-top:0px;" id="printStatusSta" name="printStatusSta"  readonly>
								<option value=""><spring:message code="common.select" /></option>
								<option value="0">已打印</option>
								<option value="1">未打印</option>
							</select>
						</div>
				
						<div class="td_search_right_two" style="width:80px">
							<span class="tit">药品分类：</span>
						</div>
						<div class="td_search_left_two"  style="width: 10%;color:#636363;">
							<select class="tit" style="height: 25px;width:100%;font-size: 13px" id="categoryIdSta" name="categoryIdSta" multiple="multiple">
								<option value="">
									<spring:message code="common.select" />
								</option>
								<c:forEach items="${medicCategories}" var="item">
									<option  value="${item.categoryId}">
										<spring:escapeBody htmlEscape="true">${item.categoryName}</spring:escapeBody>
									</option>
								</c:forEach>
							</select>
						</div>
					
						<div class="td_search_right_two" style="width:80px;margin-left:20px;">
							<span class="tit">药品标签：</span>
						</div>
						<div class="td_search_left_two" style="width: 10%;color:#636363;">
							<select class="tit" style="height: 25px;width:100%;font-size: 13px" id="medLabelSta" name="medLabelSta" multiple="multiple">
								<option value=""> <spring:message code="common.select" /> </option>
								<c:forEach items="${allMedicLabel}" var="item">
									<option value="${item.labelId}">
										<spring:escapeBody htmlEscape="true">${item.labelName}</spring:escapeBody>
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div style="height: 35px">
						<div class="td_search_right_two" style="width:30px;margin-left:4px;">
							<input id="needPrintTimecCK" type="checkbox" style="width:19px;height:19px;margin-top:3px">
						</div>
						<div class="td_search_right_two" style="width:70px;">
							<span class="tit">打印时间：</span>
						</div>
						<div class="td_search_left_two" style="width: 186px;">
							<input type="text" id="printStartTime" style="height: 25px;color: #555555;width:100%;" class="Wdate" empty="false"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
						</div>
						<div class="td_search_right_two" style="width:15px;">
							<span class="tit">—</span>
						</div>
						<div class="td_search_left_two" style="width: 186px;margin-left:4px;">
							<input type="text" id="printEndTime" style="height: 25px;color: #555555;width:100%;" class="Wdate" empty="false"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
						</div>
						<button class="labelListBtn" style="float: right;" id="medicStaticSearchBtn">查询</button> 
					</div>
				</div>
			</div>
			<div id="tabs-bingx">
				<div id="showPrintStatMeicPanel" style="padding:0 25px 0 0px;margin-top:10px;height: 30px;">
					<div style="height: 35px">
						<div class="td_search_right_two" style="width:80px">
							<span class="tit">用药时间：</span>
						</div>
						<div class="td_search_left_two" style="width: 100px;">
							<input type="text" id="useDateStaForBX" style="height: 25px;color: #555555;width:100%;" class="Wdate" empty="false"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
						</div>
						
						<div class="td_search_right_two" style="width:50px;">
							<span class="tit">批次：</span>
						</div>
						<div class="td_search_left_two" style="width:85px;">
							<select class="tit" style="height: 25px;width:100%;font-size: 13px;margin-top:0px;" id="batchIdStaForBX" name="batchIdStaForBX" readonly>
								<option value=""> <spring:message code="common.select" /> </option>
								<option value="0" selected="selected">KU</option>
								<option value="1">#</option>
								<c:forEach items="${batchList}" var="item">
									<option value="${item.id}">
										<spring:escapeBody htmlEscape="true">${item.name}</spring:escapeBody>
									</option>
								</c:forEach>
							</select>
						</div>
						<div class="td_search_right_two" style="width:80px;">
							<span class="tit">是否打印：</span>
						</div>
						<div class="td_search_left_two" style="width:90px;" >
							<select class="tit" style="height: 25px;width:100%;font-size: 13px;margin-top:0px;" id="printStatusStaForBX" name="printStatusStaForBX"  readonly>
								<option value=""><spring:message code="common.select" /></option>
								<option value="0" selected="selected">已打印</option>
								<option value="1">未打印</option>
							</select>
						</div>
						
						<div class="td_search_right_two" style="width:30px;margin-left:4px;">
							<input id="needPrintTimecCKForBX" type="checkbox" style="width:19px;height:19px;margin-top:3px">
						</div>
						<div class="td_search_right_two" style="width:70px;">
							<span class="tit">打印时间：</span>
						</div>
						<div class="td_search_left_two" style="width: 186px;">
							<input type="text" id="printStartTimeForBX" style="height: 25px;color: #555555;width:100%;" class="Wdate" empty="false"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
						</div>
						<div class="td_search_right_two" style="width:15px;">
							<span class="tit">—</span>
						</div>
						<div class="td_search_left_two" style="width: 186px;margin-left:4px;">
							<input type="text" id="printEndTimeForBX" style="height: 25px;color: #555555;width:100%;" class="Wdate" empty="false"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
						</div>
						<button class="labelListBtn" style="float: right;" id="medicStaticSearchBtnForBX">查询</button> 
					</div>
				</div>
			</div>
		</div>
			   
		<div style="border-top:1px dashed #AAAAAA;margin:10px 10px 0px 10px ;"></div>
		<div id="statisticMedicContain" style="width:100%;height:77%;overflow:auto;padding:20px 20px;color:#323232;"></div>
		<div id="birtReport" style="width:100%;height:77%;overflow:auto;padding:20px 20px;color:#323232;display: none">
		 	<iframe name="frame1"  frameborder="0" src="" style="margin-top: 0px;margin-left: 0px;width:100%;height:100%;"></iframe>
		</div>
	</div>
</div>
</body>

<script>
	var paramTemp;

	var inpatientString = function(){
		return window.parent.getInpatientInfo();
	}
	var inpatientParam = "";
	var isAdvanceSearch=true;
	
	//瓶签的pdf路径
	var bottleNumPDFPath = "";
	//药品统计 生成的PDF 路径
	var medicStatisticPath = "";
	//冰箱药统计  生成的pdf 路径
	var fridgeStatisticPath = "";
	
	
	var dateChange = ${dateChange};
	
	var contextPath = '${pageContext.request.contextPath}';
	function toBirt()
	{
		var yyrq = $('#useDateStaForBX').val();
		
		var searchStartTime = $('#printStartTimeForBX').val();
		var searchEndTime = $('#printEndTimeForBX').val();
		var isPrintTime = '0';
		
		if($("#needPrintTimecCKForBX").is(":checked")){
			isPrintTime = '1';
		}
		
		var searchBatch = $("#batchIdStaForBX").val();
		var searchDeptCode = inpatientString();
		var isPrintStatus = $("#printStatusStaForBX").val();
		
		var url = contextPath + "/frameset?__report=report/drugStatistics.rptdesign&__parameterpage=fasle&__navigationbar=true&__toolbar=true&__locale=" + bLanguage;
		
		url += "&yyrq="
		if(yyrq != null && yyrq !== ''){
			url += yyrq;
		}
		
		url += "&searchDeptCode="
		if(searchDeptCode != null && searchDeptCode !== ''){
			url += searchDeptCode;
		}
		
		url += "&searchStartTime="
		if(searchStartTime != null && searchStartTime !== ''){
			url += searchStartTime;
		}
		
		url += "&searchEndTime="
		if(searchEndTime != null && searchEndTime !== ''){
			url += searchEndTime;
		}
		
		url += "&isPrintTime="
		if(isPrintTime != null && isPrintTime !== ''){
			url += isPrintTime;
		}
		
		var titel = '';
		if(isPrintTime == '1')
		{
			titel = "&title=" + '冰箱药统计' + '('+searchStartTime+ '~' + searchEndTime + ')';
		}
		
		if(isPrintTime == '0')
		{
			titel = "&title=" + '冰箱药统计' + '('+yyrq + ')';
		}
		
		url += encodeURI(titel);
		
		url += "&searchBatch="
		if(searchBatch != null && searchBatch !== ''){
			url += searchBatch;			
		}
		
		url += "&isPrint="
		if(isPrintStatus != null && isPrintStatus !== ''){
			url += isPrintStatus;			
		}
		
		
		var user = "&user=" + '<spring:message code="pivas.statistics.tab"/>' + '<spring:escapeBody htmlEscape="true"><shiro:principal property="name"/></spring:escapeBody>';
		url += encodeURI(user);
		
		$("#birtReport iframe").attr("src", url);
		
	}
	
	function printPdf(option){
		var pdfPrintBtn = $("#labelPdfFrame")[0].contentWindow.document.getElementById("print");
		pdfPrintBtn.addEventListener("click", function(){
			//var pidsj = getFlexiGridSelectedRowText($("#printLableList"), 4);
			var pidsj = getDataTableSelectRowData($("#prtDataTable"), prtDataTable, 'bottleNum');
			inpatientParam = "";
			if(pidsj && pidsj.length <1) {
				pidsj = "";

				$("#prtDataTable tbody tr").each(function(i){
                    pidsj += prtDataTable.row(i).data()['bottleNum']+",";
					//pidsj += $(this).children().eq(3).children("div").text()+",";
				});
			}else{
				var bottomNumList="";
				$.each(pidsj,function(i,v){
					bottomNumList += v+",";
				});
				pidsj=bottomNumList;
			}
			if(pidsj.length == 0){
				message({html:"无相关打印信息",showConfirm : true});
				return;
			}
			
			var param =[{name : 'pidsj', value : pidsj},{name : 'printIndex', value : $("#printIndex").val()}];
			
			if(isAdvanceSearch){
				var printConfig = $("#printConfigSelect").val();
				var printConfigName = $("#printConfigSelect").find("option:selected").text();
				if(printConfig.length !=0 ){
					printConfig = printConfig.split("&&");
					if(printConfig[0]==0){
						param.push({name : 'useDate', value : getCurrentDate("yyyy-MM-dd",null,0)});
					}else if(printConfig[0]==1){
						param.push({name : 'useDate', value : getCurrentDate("yyyy-MM-dd",null,1)});
					}else if(printConfig[0]==2){
						param.push({name : 'useDate', value : getCurrentDate("yyyy-MM-dd",null,-1)});
					}
					param.push({name : 'medicamentsLabelNo', value : printConfig[1]});
					param.push({name : 'batchIds', value : printConfig[2]});
					param.push({name : 'categoryId', value : printConfig[3]});
					param.push({name : 'dybz', value : printConfig[4]});
					param.push({name : 'isPack', value : printConfig[7]});
					param.push({name : 'printConfigName', value : printConfigName});
					inpatientParam = printConfig[6];
				}else{
					param.push({name : 'useDate', value : getCurrentDate("yyyy-MM-dd",null,0)});
				}
				if(inpatientParam.length == 0){
					inpatientParam = inpatientString();
				}
				param.push({name : 'wardCode', value : inpatientParam});
			}else{
				param.push({name : 'useDate', value : $("#useDate").val()});
				param.push({name : 'medicamentsLabelNo', value : $("#medicamentsLabelNo").selectedValuesString()});
				param.push({name : 'batchIds', value : $("#batchId").val()});
				param.push({name : 'categoryId', value : $("#categoryId").selectedValuesString()});
				param.push({name : 'dybz', value : $("#dybz").val()});
				param.push({name : 'MedicamentsCountsStr', value : $("#medicamentsCounts").val()});
				param.push({name : 'sortType', value : $("#sortType").val()});
				param.push({name : 'wardCode', value : inpatientString()});
				
				/* if (paramTemp) {
					param = param.concat(paramTemp);
				} */
				
			}
			
			if($("#collectionCheckBox").is(":checked")){
				var medicamentsCodeList="";
				var checkBoxInfo="";
				
				if($("#checkboxAll")[0].checked){
					checkBoxInfo = "#medicineListShowTable input";
				}else{
					checkBoxInfo = "#medicineListShowTable input:checked";
				}
				
				$(checkBoxInfo).each(function(checkIndex){
					var code=$(this).parent().siblings("td:eq(2)").text();
					if(code.length !=0 ){
						medicamentsCodeList += $(this).parent().siblings("td:eq(2)").text()+",";
					}
				});
				param.push({name : 'isPrintCollection', value : true});
				param.push({name : 'medicamentsCodeList', value : medicamentsCodeList});
			}
			
			if(isAdvanceSearch){
				var printConfig = $("#printConfigSelect").val();
				if(printConfig.length !=0 ){
					printConfig = printConfig.split("&&");
					if(printConfig[5].length != 0){
						var drugNameList="";
						if(!$("#checkboxAll")[0].checked){
							$("#medicineListShowTable input:checked").each(function(checkIndex){
								var drugName=$(this).parent().siblings("td:eq(1)").text();
								if(drugName.length !=0 ){
									drugNameList += $(this).parent().siblings("td:eq(1)").text()+",";
								}
							});
							param.push({name : 'drugNameList', value : drugNameList});
						}
					}
				}
			}
			
			param.push({name : 'putDrugPersonName', value : $("#putDrugPersonName").val()});
			param.push({name : 'bottleNumPDFPath', value : bottleNumPDFPath});
			if (typeof(option) != "undefined"){
				param.push(option);
			}
				$.ajax({
					type:'POST',
					url:'${pageContext.request.contextPath}/printLabel/changePrintMark',
					dataType:'json',
					cache:false,
					data:param,
					success:function(data) {
						if(data.success == false){
							message({html : data.msg, showConfirm : true});
						}else{
							getAllMedicament();
							closeListShadow();
						}
					},
					error:function(){
						message({html : '<spring:message code="common.op.error"/>',showConfirm : true});
					}
			}); 
		});
	}
	
	//获取左侧瓶签列表数据
	function getAllMedicament(){
		inpatientParam="";
		var paramData={};
		if(isAdvanceSearch){
			var printConfig = $("#printConfigSelect").val();
			if(printConfig.length != 0){
				printConfig = printConfig.split("&&");
				if(printConfig[0]==0){
					paramData["useDate"] = getCurrentDate("yyyy-MM-dd",null,0);
				}else if(printConfig[0]==1){
					paramData["useDate"] = getCurrentDate("yyyy-MM-dd",null,1);
				}else if(printConfig[0]==2){
					paramData["useDate"] = getCurrentDate("yyyy-MM-dd",null,-1);
				}
				
				paramData["medicamentsLabelNo"] = printConfig[1];
				paramData["batchIds"] = printConfig[2];
				paramData["categoryId"] = printConfig[3];
				paramData["dybz"] = printConfig[4];
				paramData["medicamentsCodeList"] = printConfig[5];
				paramData["medicTotal"] = $("#medicTotal").val();
				paramData["isPack"] = printConfig[7];
				inpatientParam = printConfig[6];
			}else{
				paramData["useDate"] = getCurrentDate("yyyy-MM-dd",null,0);
			}
			if(inpatientParam.length == 0){
				inpatientParam = inpatientString();
			}
			paramData["wardCode"] = inpatientParam;
		}else{
			paramData={
   				useDate:$("#useDate").val(),
   				medicamentsLabelNo:$("#medicamentsLabelNo").selectedValuesString(),
   				batchIds:$("#batchId").val(),
   				categoryId:$("#categoryId").selectedValuesString(),
   				medicamentsCountsStr:$("#medicamentsCounts").val(),
   				dybz:$("#dybz").val(),
   				medicTotal:$("#medicTotal").val(),
   				wardCode:inpatientString()
   			}

            qryByCnd();//多个搜索框用
			if (paramTemp) {
				for(var i in paramTemp){
					if(paramTemp[i].name == "bednoS"){
						paramData["bednoS"] = paramTemp[i].value == null ? "": paramTemp[i].value.toString();
					}else if(paramTemp[i].name == "drugnameQry"){
						paramData["drugnameS"] = paramTemp[i].value == null ? "": paramTemp[i].value.toString();
					}
					
				}
			}
			
		}
		$.ajax({
   			type: "POST",
   			url: "${pageContext.request.contextPath}/printLabel/qryAllMdcs",
   			data:paramData,
   			success: function(data){
				$("#medicineListShowTable").html("");
				if(data){
					var totalCount=0;
					$.each(data,function(n,jsonData){
						totalCount+=jsonData.singleDrugTotalCount;
						$("#medicineListShowTable").append("<tr style='height:40px;'><td><input type='checkbox' class='checkbox_b' value='"+jsonData.drugCode+"'></td>"+
											"<td><span style='font-size:13px'>&nbsp;&nbsp;("+jsonData.singleDrugTotalCount+")&nbsp;&nbsp;</span></td>"+
											"<td style='word-break:break-all;'><span style='font-size:13px'>"+jsonData.drugName+"</span></td><td style='display:none'>"+jsonData.medicamentsCode+"</td></tr>");
					});
					
					$("#medicineListShowTable").prepend("<tr id='checkAllTr' style='height:40px;'><td><input id='checkboxAll' type='checkbox' class='checkbox_b' value='all' checked></td>"+
								"<td><span style='font-size:13px'>&nbsp;&nbsp;("+totalCount+")&nbsp;&nbsp;</span></td><td><span style='font-size:13px'>全部</span></td><td style='display:none'></td></tr>");
					
					$("#medicineListShowTable input").each(function(index){
						$(this).on("change",function(){
							if($(this).attr("id")=="checkboxAll"){
								$("#medicineListShowTable tr[id!='checkAllTr']").each(function(index){
									$(this).children().first().children("input").removeAttr("checked");
								});
							}else{
								$("#checkboxAll").removeAttr("checked");
							}
							getAllCheck();
						});
					});
					
					$("#medicineListShowTable tr").each(function(index){
						$(this).children("td:gt(0)").on("click",function(){
								if(($(this).parent().children().first().children("input"))[0].checked){
									$(this).parent().children().first().children("input").removeAttr("checked");
								}else{
									$(this).parent().children().first().children("input").attr("checked", true);
								}
								if($(this).parent().children().first().children("input").attr("id") != "checkboxAll"){
									$("#checkboxAll").removeAttr("checked");
								}else{
									$("#medicineListShowTable tr[id!='checkAllTr']").each(function(index){
										$(this).children().first().children("input").removeAttr("checked");
									});
								}
								getAllCheck();
						})
					});
					getAllCheck();
				}
   			}
		}); 
	}
	
	
	function getAllCheck(){
		var medicamentsCodeList = "";
		inpatientParam = "";
		var checkBoxInfo="";
		if($("#checkboxAll")[0].checked){
			checkBoxInfo = "#medicineListShowTable input";
		}else{
			checkBoxInfo = "#medicineListShowTable input:checked";
		}
		$(checkBoxInfo).each(function(checkIndex){
			var code=$(this).parent().siblings("td:eq(2)").text();
			if(code.length !=0 ){
				medicamentsCodeList += $(this).parent().siblings("td:eq(2)").text()+",";
			}
		});
		var printStartNumberVal=$("#printStartIndex").val();
		var printEndNumberVal=$("#printEndIndex").val();
		var printIndexVal=$("#printIndex").val();
		if(""!=printIndexVal){
			if(""!=printStartNumberVal && ""!=printEndNumberVal){
				if(parseInt(printStartNumberVal)>parseInt(printEndNumberVal)){
					message({html:"请输入合法的起止页码",showConfirm : true});
					return ;
				}
			}
		}
		var queryParam={};
		if(isAdvanceSearch){
			var printConfig = $("#printConfigSelect").val();
			if(printConfig.length !=0 ){
				printConfig = printConfig.split("&&");
				
				if(printConfig[0]==0){
					queryParam["useDate"] = getCurrentDate("yyyy-MM-dd",null,0);
				}else if(printConfig[0]==1){
					queryParam["useDate"] = getCurrentDate("yyyy-MM-dd",null,1);
				}else if(printConfig[0]==2){
					queryParam["useDate"] = getCurrentDate("yyyy-MM-dd",null,-1);
				}
				
				queryParam["medicamentsLabelNo"] = printConfig[1];
				queryParam["batchIds"] = printConfig[2];
				queryParam["categoryId"] = printConfig[3];
				queryParam["dybz"] = printConfig[4];
				queryParam["isPack"] = printConfig[7];
				inpatientParam = printConfig[6];
			}else{
				queryParam["useDate"] = getCurrentDate("yyyy-MM-dd",null,0);
			}
			
			if(inpatientParam.length == 0){
				inpatientParam = inpatientString();
			}
			queryParam["medicamentsCodeList"] = medicamentsCodeList;
			queryParam["wardCode"] = inpatientParam;
		}else{
			queryParam={
					useDate:$("#useDate").val(),//用药时间
					medicamentsLabelNo:$("#medicamentsLabelNo").selectedValuesString(),//药品标签
					batchIds:$("#batchId").val(),//批次
					categoryId:$("#categoryId").selectedValuesString(),//药品分类
					medicamentsCountsStr:$("#medicamentsCounts").val(),//药单药数
					sortType:$("#sortType").val(),//排序方式
					dybz:$("#dybz").val(),//打印状态
					printIndex:printIndexVal,//打印序号
					printStartNumber:printStartNumberVal,//起始页
					printEndNumber:printEndNumberVal,//结束页
					medicamentsCodeList:medicamentsCodeList,
					wardCode:inpatientString()
			};
			
			if (paramTemp) {
				for(var i in paramTemp){
					if(paramTemp[i].name == "bednoS"){
						queryParam["bednoS"] = paramTemp[i].value == null ? "": paramTemp[i].value.toString();
					}else if(paramTemp[i].name == "drugnameQry"){
						queryParam["drugnameS"] = paramTemp[i].value == null ? "": paramTemp[i].value.toString();
					}
					
				}
			}
			
		}

		paramAll = queryParam;
		if (prtDataTable) {
		    prtDataTable.ajax.reload();
		} else {
			initDataTable();
		}
	}
	
	var staticHeight = "";
	
	$(function(){
		if(dateChange){
			$("#useDate").val(getCurrentDate("yyyy-MM-dd",null,1));
		}else{
			$("#useDate").val(getCurrentDate("yyyy-MM-dd",null,0));
		}
		$("#useDateSta").val(getCurrentDate("yyyy-MM-dd",null,0));
		$("#useDateStaForBX").val(getCurrentDate("yyyy-MM-dd",null,0));
		
		
		$("#mainPanel").height($(document).height()-90);
		var medlistpanel = $("#mainPanel").height()-$("#medicineListSearch").outerHeight()-20;
		$("#medicineListShowPanel").height(medlistpanel);
		staticHeight = $("#statisticMedicContain").css("height");
		$("#advancedSearchBtn").click(function(){
			$("#advancedQueryPanel").toggle();
			if($("#medicineListShowPanel").height()==medlistpanel){
				var heightNew = medlistpanel-$("#advancedQueryPanel").height();
				$("#medicineListShowPanel").height(heightNew-20);
				$("#mainPanel").height(heightNew-2);
			}else{
				$("#medicineListShowPanel").height(medlistpanel);
				$("#mainPanel").height(medlistpanel+20);
			}
			
		});
		
		//药品数量统计  高级查询按钮 显示与否
		$("#showPrintStatMeicbtn").click(function(){
			$("#showPrintStatMeicPanel").toggle();
			var currentHe = parseInt($("#statisticMedicContain").outerHeight(true)/$("#statisticMedicInfo").height()*100);
			if((((currentHe+1)+"%") == staticHeight) || (((currentHe-1)+"%") == staticHeight) || ((currentHe+"%") == staticHeight)){
				$("#statisticMedicContain").css("height","66%");
			}else{
				$("#statisticMedicContain").css("height","77%");
			}
			clearMeidcStatInfo();			
		});
		
		$("#labelList").height(shadowHeight);
		$("#labelList").width(shadowWidth);
		$("#labelList").css("top","50%");
		$("#labelList").css("left","50%");
		$("#labelList").css("margin-top",-shadowHeight/2);
		$("#labelList").css("margin-left",-shadowWidth/2);
		
		$("#labelSingleInfo").height(shadowHeight+40);
		$("#labelSingleInfo").width(shadowWidth/2);
		$("#labelSingleInfo").css("top","50%");
		$("#labelSingleInfo").css("left","50%");
		$("#labelSingleInfo").css("margin-top",-shadowHeight/2-20);
		$("#labelSingleInfo").css("margin-left",-shadowWidth/4);
		
		$("#statisticLabelInfo").height(shadowHeight);
		$("#statisticLabelInfo").width(shadowWidth);
		$("#statisticLabelInfo").css("top","50%");
		$("#statisticLabelInfo").css("left","50%");
		$("#statisticLabelInfo").css("margin-top",-shadowHeight/2);
		$("#statisticLabelInfo").css("margin-left",-shadowWidth/2);
		
		$("#statisticMedicInfo").height(shadowHeight);
		$("#statisticMedicInfo").width(shadowWidth);
		$("#statisticMedicInfo").css("top","50%");
		$("#statisticMedicInfo").css("left","50%");
		$("#statisticMedicInfo").css("margin-top",-shadowHeight/2);
		$("#statisticMedicInfo").css("margin-left",-shadowWidth/2);
		
		$("#advancedQueryPanel").css("display","block");
		$("#statisticMedicInfo").css("display","block");
		$("#showPrintStatMeicPanel").css("display","block");
		$("#medicamentsLabelNo").multiSelect({ "selectAll": false,"noneSelected": "--请选择--","oneOrMoreSelected":"*" });
		$("#medLabelSta").multiSelect({ "selectAll": false,"noneSelected": "--请选择--","oneOrMoreSelected":"*" });
		$("#categoryId").multiSelect({ "selectAll": false,"noneSelected": "--请选择--","oneOrMoreSelected":"*" });
		$("#categoryIdSta").multiSelect({ "selectAll": false,"noneSelected": "--请选择--","oneOrMoreSelected":"*" });
		$("#advancedQueryPanel").css("display","none");
		$("#statisticMedicInfo").css("display","none");
		$("#showPrintStatMeicPanel").css("display","none");
		
		//药单统计  高级查询  打印时间初始化
		//$("#printStartTime").val(); $("#printEndTime").val() 
		var timeFormat = "yyyy-MM-dd";
		var currentTime = getCurrentDate(timeFormat, null, 0);
		$("#printStartTime").val(currentTime + " 00:00:00");
		$("#printEndTime").val(currentTime + " 08:00:00");
		
		$("#tabs").tabs({
			beforeActivate: function( event, ui ) {
				var type = ui.newPanel.attr("id");
				$("#statisticMedicContain").html("没有检索到数据");
				if(type=="tabs-medic"){
					$("#statisticMedicContain").show();
					$("#birtReport").hide();
					medicStatisticPath = "";
					$("#mediStaticSelect").val("");
					clearMeidcStatInfo();
				}else{
					$("#statisticMedicContain").hide();
					$("#birtReport").show();
				}
			}	
		});
		
		$("#medicTotal").change(function(){
			getAllMedicament();
		});
		
		//getAllMedicament();
		var labelPanelWidth=$("#labelPanel").width();
		var labelPanelHeight=$("#labelPanel").height();
		var _columnWidth = labelPanelWidth/10;
		
		//设置条件  下拉列表  改变条件后事件处理
		$("#printConfigSelect").change(function(){
			isAdvanceSearch=true;
			getAllMedicament();
		});
		
		//查询按钮事件
		$("#justSearch").click(function(){
			isAdvanceSearch=false;
			getAllMedicament();
		});

		//打印按钮
		$("#printBtn").bind("click",function(){
			printLableList();
		});
		
		//重新打印按钮
		$("#rePrintBtn").bind("click",function(){
			message({
				html: "确认重新打印？",
				showCancel:true,
				confirm:function(){
					printLableList({name : "printAgain", value : "printAgain"});
				}
	    	});
		});
		
		//药品统计按钮
		$("#medicStatisBtn").bind("click",function(){
			$("#mediStaticSelect").val("");
			clearMeidcStatInfo();
			openStatMedicShadow();
			getStatisMedicByNet(0);
		});
		
		//清空药品统计界面数据
		function clearMeidcStatInfo(){
			$("#useDateSta").val(getCurrentDate("yyyy-MM-dd",null,0));
			$("#batchIdSta").val("");
			$("#printStatusSta").val("");
			/*$("#medLabelSta").multiSelect({"noneSelected": "--请选择--","oneOrMoreSelected":"*" });
			 $("#categoryIdSta").multiSelect(); */
			$("#needPrintTimecCK").removeAttr("checked");
		}
		
		//药品统计  下拉列表
		$("#mediStaticSelect").change(function(){
			getStatisMedicByNet(1);
		});
		
		//药品统计界面上的查询按钮
		$("#medicStaticSearchBtn").bind("click",function(){
			getStatisMedicByNet(2);
		});
		
		$("#medicStaticSearchBtnForBX").bind("click",function(){
			toBirt();
		});
		
		$("#printStatMeicbtn").bind("click",function(){
			if(medicStatisticPath.length == 0 ){
				message({html : "没有打印信息",showConfirm : true});
				return ;
			}else{
				window.open("${pageContext.request.contextPath}/printLabelDownLoad/<shiro:principal property="account"/>/"+medicStatisticPath);
				closeStatMedicShadow();
			}
			
		});
					
		//发送请求获取到统计的药品
		function getStatisMedicByNet(type){
			var param = {};
			//从页面打开时请求数据
			if(type == 0 ){
                var pidsj = getDataTableSelectRowData($("#prtDataTable"), prtDataTable, 'bottleNum');
				if(pidsj && pidsj.length <1) {
					pidsj = "";
					$("#prtDataTable tbody tr").each(function(i){
                        pidsj += prtDataTable.row(i).data()['bottleNum']+",";
					});
				}else{
					var bottomNumList="";
					$.each(pidsj,function(i,v){
						bottomNumList += v+",";
					});
					pidsj=bottomNumList;
				}
				param["type"] = 0;
				param["pidsj"] = pidsj;
			}else if(type == 1){
				//界面上的下拉框 下拉改变后请求数据
				var mediStaticSelect = $("#mediStaticSelect").val();
				var mediStaticSelectName = $("#mediStaticSelect").find("option:selected").text();
				param["type"] = 1;
				if(mediStaticSelect.length != 0 ){
					mediStaticSelect = mediStaticSelect.split("&&");
					if(mediStaticSelect[0]==0){
						param["yyrq"] = getCurrentDate("yyyy-MM-dd",null,0);
					}else if(mediStaticSelect[0]==1){
						param["yyrq"] = getCurrentDate("yyyy-MM-dd",null,1);
					}else if(mediStaticSelect[0]==2){
						param["yyrq"] = getCurrentDate("yyyy-MM-dd",null,-1);
					}
					param["medLabelNos"] = mediStaticSelect[1];
					param["batchIds"] = mediStaticSelect[2];
					param["categoryIds"] = mediStaticSelect[3];
					param["dybz"] = mediStaticSelect[4];
					param["menstruums"] = mediStaticSelect[5];
					inpatientParam = mediStaticSelect[6];
					//param["isPack"] = mediStaticSelect[7];
					param["selectName"] =  mediStaticSelectName;
				}else{
					param["yyrq"] = getCurrentDate("yyyy-MM-dd",null,0);
				}
				
				if(inpatientParam.length == 0){
					inpatientParam = inpatientString();
				}
				param["wardCode"] = inpatientParam;
					
			}else if(type == 2){
				param["type"] = 2;
				param["yyrq"] = $("#useDateSta").val();
				param["batchIds"] = $("#batchIdSta").val();
				param["dybz"] = $("#printStatusSta").val();
				param["categoryIds"] = $("#categoryIdSta").selectedValuesString();
				param["medLabelNos"] = $("#medLabelSta").selectedValuesString();
				param["wardCode"] = inpatientString();
				if($("#needPrintTimecCK").is(":checked")){
					param["printStartTime"] = $("#printStartTime").val();
					param["printEndTime"] = $("#printEndTime").val(); 
				}
			}
			
			$.ajax({
				type:'POST',
				url:'${pageContext.request.contextPath}/printLabel/callMedicStatisticHtml',
				dataType:'html',
				cache:false,
				data:param,
				success:function(data) {
					if(data.success == false){
						message({html : data.msg, showConfirm : true});
					}else{
						var htmlArr = data.split("!!@@##");
						$("#statisticMedicContain").html(htmlArr[0]);
						if(htmlArr[1]){
							medicStatisticPath = htmlArr[1];
						}else{
							medicStatisticPath = "";
						}
					}
				},
				error:function(){
					message({html : '<spring:message code="common.op.error"/>',showConfirm : true});
				}
			}); 
		}
		
		//打印事件
		function printLableList(option){
			//获取勾选的要打印的药单的pidsj
            var pidsj = getDataTableSelectRowData($("#prtDataTable"), prtDataTable, 'bottleNum');
			if(pidsj && pidsj.length <1) {
				pidsj = "";
				$("#prtDataTable tbody tr").each(function(i){
                    pidsj += prtDataTable.row(i).data()['bottleNum']+",";
				});
			}else{
				var bottomNumList="";
				$.each(pidsj,function(i,v){
					bottomNumList += v+",";
				});
				pidsj=bottomNumList;
			}
			if(pidsj.length == 0){
				message({html:"无相关打印信息",showConfirm : true});
				return;
			}
			//添加请求参数 1.药单pidsj 2.打印页码下标（用于判断是否将打印打印的瓶签记录到打印历史表中）
			var param =[{name : 'pidsj', value : pidsj},
			            {name : 'printIndex', value : $("#printIndex").val()},
			            {name : 'batchIds', value : $("#batchId").val()}];
			
			//判断是高级查询还是打印设置
			if(isAdvanceSearch){
				var printConfig = $("#printConfigSelect").val();
				var printConfigName = $("#printConfigSelect").find("option:selected").text();
				if(printConfig.length !=0 ){
					printConfig = printConfig.split("&&");
					param.push({name : 'isPack', value : printConfig[7]});
					param.push({name : 'printConfigName', value : printConfigName});
				}
			}
			
			//是否打印汇总单
			if($("#collectionCheckBox").is(":checked")){
				param.push({name : 'isPrintCollection', value : true});
			}
			
			//是否是重复打印
			if (typeof(option) != "undefined"){
				param.push(option);
			}
			$.ajax({
					type:'POST',
					url:'${pageContext.request.contextPath}/printLabel/print',
					dataType:'json',
					cache:false,
					data:param,
					success:function(data) {
						if(data.success == false){
							message({html : data.msg, showConfirm : true});
						}else{
							/* window.open("${pageContext.request.contextPath}/printLabelDownLoad/<shiro:principal property="account"/>/"+ data.msg);                                           
							getAllMedicament(); */
							openListShadow();
							//printLabelDownLoad/<shiro:principal property='account'/>/"+data.msg+"
							// ${pageContext.request.contextPath}/printLabel/showLabelJsp', ?file=<shiro:principal property='account'/>/"+data.msg+"
							$("#labelPdfFrame").attr("src","${pageContext.request.contextPath}/assets/label/showLabelList.html?file=../../printLabelDownLoad/<shiro:principal property='account'/>/"+data.msg);
							bottleNumPDFPath = data.msg;
							setTimeout(function(){
								printPdf(option);								
							},1500);
							
						}
					},
					error:function(){
						message({html : '<spring:message code="common.op.error"/>',showConfirm : true});
					}
			}); 
		}
		
		//汇总单按钮
		$("#collectionBtn").bind("click", function() {
			
			//获取勾选的要打印的药单的pidsj
            var pidsj = getDataTableSelectRowData($("#prtDataTable"), prtDataTable, 'bottleNum');
			if(pidsj && pidsj.length <1) {
				pidsj = "";
				$("#prtDataTable tbody tr").each(function(i){
                    pidsj += prtDataTable.row(i).data()['bottleNum']+",";
				});
			}else{
				var bottomNumList="";
				$.each(pidsj,function(i,v){
					bottomNumList += v+",";
				});
				pidsj=bottomNumList;
			}
			
			var queryParam={};
			
			//是否是设置条件的过滤后的数据
			if(isAdvanceSearch){
				queryParam["pidsj"] = pidsj;
				var printConfig = $("#printConfigSelect").val();
				var printConfigName = $("#printConfigSelect").find("option:selected").text();
				if(printConfig.length !=0){
					printConfig = printConfig.split("&&");
					queryParam["isPack"] = printConfig[7];
					queryParam["printConfigName"] = printConfigName;
					inpatientParam = printConfig[6];
				}
				queryParam["isPrint"] = false;
			}else{
				queryParam["pidsj"] = pidsj;
				queryParam["isPrint"] =	false;
			}
			
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/printLabel/printStatistics',
				dataType : 'html',
				cache : false,
				data : queryParam, 
				success : function(data) {
					if(data.success == false){
						message({html : data.msg, showConfirm : true});
					}else{
						openStatisticShadow();
						data = data.replace(/class="myheadtwo"/g, 'class="myheadtwooo"')
						$("#statisticLabeContain").html(data);
					}
				},
				error : function() {
					message({
						html : '<spring:message code="common.op.error"/>',
						showConfirm : true
					});
				}
			});
		});
		
		//汇总单上的打印按钮事件
		$("#printCollecttionbtn").bind("click", function() {
            var pidsj = getDataTableSelectRowData($("#prtDataTable"), prtDataTable, 'bottleNum');
			if(pidsj && pidsj.length <1) {
				pidsj = "";
				$("#prtDataTable tbody tr").each(function(i){
                    pidsj += prtDataTable.row(i).data()['bottleNum']+",";
				});
			}else{
				var bottomNumList="";
				$.each(pidsj,function(i,v){
					bottomNumList += v+",";
				});
				pidsj=bottomNumList;
			}
			
			var queryParam={};
			if(isAdvanceSearch){
				queryParam["pidsj"] = pidsj;
				var printConfig = $("#printConfigSelect").val();
				var printConfigName = $("#printConfigSelect").find("option:selected").text();
				if(printConfig.length !=0){
					printConfig = printConfig.split("&&");
					queryParam["isPack"] = printConfig[7];
					queryParam["printConfigName"] = printConfigName;
				}
				queryParam["isPrint"] = true;
			}else{
				queryParam["pidsj"] = pidsj;
				queryParam["isPrint"] =	true;
			}
			
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/printLabel/printStatistics',
				dataType : 'json',
				cache : false,
				data :queryParam,
				success : function(data) {
					if(data.success == false){
						message({html : data.msg, showConfirm : true});
					}else{
						window.open("${pageContext.request.contextPath}/printLabelDownLoad/<shiro:principal property="account"/>/"+data.msg);
					}
				},
				error : function() {
					message({
						html : '<spring:message code="common.op.error"/>',
						showConfirm : true
					});
				}
			});
		});

		/* //预览
		$("#printBtn").click(function(){
			$("#labelListShadow").css("display","block");
			$("#labelListContain").html("");
			setTimeout("showLableListAA()",100);
		}); */
		
		//预览界面上的打印按钮事件
		$("#labelListBtn").bind("click", function() {
			printLableList();
		});
		
		//瓶签放大界面上的打印按钮事件
		$("#labelSingleBtn").bind("click", function() {
			inpatientParam = "";
			var pidsj=$("#singleLabelPidjs").val()+",";
			var param=[];
			param.push({name : 'pidsj', value : pidsj});
			param.push({name : 'printIndex', value : $("#printIndex").val()});
			if(isAdvanceSearch){
				var printConfig = $("#printConfigSelect").val();
				if(printConfig.length != 0 ){
					printConfig = printConfig.split("&&");
					if(printConfig[0]==0){
						param.push({name : 'useDate', value : getCurrentDate("yyyy-MM-dd",null,0)});
					}else if(printConfig[0]==1){
						param.push({name : 'useDate', value : getCurrentDate("yyyy-MM-dd",null,1)});
					}else if(printConfig[0]==2){
						param.push({name : 'useDate', value : getCurrentDate("yyyy-MM-dd",null,-1)});
					}
					param.push({name : 'medicamentsLabelNo', value : printConfig[1]});
					param.push({name : 'batchIds', value : printConfig[2]});
					param.push({name : 'categoryId', value : printConfig[3]});
					param.push({name : 'dybz', value : printConfig[4]});
					inpatientParam = printConfig[6];
				}else{
					param.push({name : 'useDate', value : getCurrentDate("yyyy-MM-dd",null,0)});
				}
				if(inpatientParam.length == 0){
					inpatientParam = inpatientString();
				}
				param.push({name : 'wardCode', value : inpatientParam});
				
			}else{
				param.push({name : 'useDate', value : $("#useDate").val()});
				param.push({name : 'medicamentsLabelNo', value : $("#medicamentsLabelNo").selectedValuesString()});
				param.push({name : 'batchIds', value : $("#batchId").val()});
				param.push({name : 'categoryId', value : $("#categoryId").selectedValuesString()});
				param.push({name : 'dybz', value : $("#dybz").val()});
				param.push({name : 'wardCode', value : inpatientString()});
				param.push({name : 'MedicamentsCountsStr', value : $("#medicamentsCounts").val()});
				param.push({name : 'putDrugPersonName', value : $("#putDrugPersonName").val()});
				param.push({name : 'sortType', value : $("#sortType").val()});
			}
				
			$.ajax({
				type:'POST',
				url:'${pageContext.request.contextPath}/printLabel/print',
				dataType:'json',
				data:param,
				success:function(data) {
					closeSingleShadow();
					if(data.success == false){
						message({html : "该瓶签已打印", showConfirm : true});
					}else{
						window.open("${pageContext.request.contextPath}/printLabelDownLoad/<shiro:principal property="account"/>/"+ data.msg);
					}
				},
				error:function(){
					closeSingleShadow();
					message({html : '<spring:message code="common.op.error"/>',showConfirm : true});
				}
			});
		});
		
	});//初始化事件END

	var prtDataTable;
	var paramAll;
	function initDataTable() {
        prtDataTable = $('#prtDataTable').DataTable({
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
                "url": "${pageContext.request.contextPath}/printLabel/qryPrintBottleLableList",
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
                {"data": "wardName", "bSortable": false},
                {"data": "bedNo", "bSortable": false},
                {"data": "batchName", "bSortable": false},
                {"data": "parentNo", "bSortable": false},
                {"data": "dybz", "bSortable": false},
                {"data": "patName", "bSortable": false},
                {"data": "freqCode", "bSortable": false},
                {"data": "transfusion", "bSortable": false},
                {"data": "drugName", "bSortable": false},
                {"data": "useDateString", "bSortable": false},
                {"data": "dose", "bSortable": false},
                {"data": "doseUnit", "bSortable": false},
                {"data": "printTimeStr", "bSortable": false},
                {"data": "printName", "bSortable": false},
            ],
            columnDefs: [
                {
                    targets: 0,
                    data: null,
                    defaultContent:"<input type ='checkbox' name='pidsj'>",
                },
                {
                    targets: 5,
                    render: function (data) {
                        return data === 0 ? "已打印" :  "未打印";
                    }
                },
                {
                    targets: 9,
                    render: function (data) {
                        return data.replace(new RegExp("@@","g"),"<br>");
                    }
                },
                {
                    targets: 11,
                    render: function (data) {
                        return data.replace(new RegExp("@@","g"),"<br>");
                    }
                },
                {
                    targets: 12,
                    render: function (data) {
                        return data.replace(new RegExp("@@","g"),"<br>");
                    }
                }
            ],
            "fnDrawCallback": function(){
                $("#all_checked").prop("checked",false);
            }
		});
	}

    //datatable下checkbox实现全选功能
    $("#all_checked").click(function(){
        $('[name=pidsj]:checkbox').prop('checked',this.checked).change();;//checked为true时为默认显示的状态
    });

    //查询条件
    function condiQuery(param){
        paramTemp = param;
    }

	function openListShadow(){
		$("#labelListShadow").css("display","block");
		$("#labelList").css("display", "block");	
	}
	
	function closeListShadow(){
		$("#labelListShadow").css("display","none");
		$("#labelList").css("display", "none");
	}
	
	function openSingleShadow(){
		$("#labelSingleShadow").css("display","block");
		$("#labelSingleInfo").css("display", "block");	
	}
	
	function closeSingleShadow(){
		$("#labelSingleShadow").css("display","none");
		$("#labelSingleInfo").css("display", "none");
	}
	
	function openStatisticShadow(){
		$("#statisticLabelShadow").css("display","block");
		$("#statisticLabelInfo").css("display", "block");	
	}
	
	function closeStatisticShadow(){
		$("#statisticLabelShadow").css("display","none");
		$("#statisticLabelInfo").css("display", "none");
	}
	
	//打印药品统计的界面
	function openStatMedicShadow(){
		$("#statisticMedicShadow").css("display","block");
		$("#statisticMedicInfo").css("display", "block");	
	}
	
	//关闭药品统计的界面
	function closeStatMedicShadow(){
		$("#statisticMedicShadow").css("display","none");
		$("#statisticMedicInfo").css("display", "none");
		$("#tabs").tabs("select",0);
	}
	
	function showLableListAA(){
        var pidsj = getDataTableSelectRowData($("#prtDataTable"), prtDataTable, 'bottleNum');
		if(pidjs && pidjs.length <1) {
			pidjs = new Array(0);
			
			$("#prtDataTable tbody tr").each(function(i){
                pidjs.push(prtDataTable.row(i).data()['bottleNum']);
			});
		}
		if(pidjs && pidjs.length <1){
			$("#labelListShadow").css("display","none");
			message({html:"无相关打印信息",showConfirm : true});
			return;
		}
		
		if(pidjs.length >100){
			$("#labelListShadow").css("display","none");
			message({html:"最多支持预览100张瓶签",showConfirm : true});
			return;
		}
		
		var pidjsStr="";
		$.each(pidjs,function(i,v){
			pidjsStr += v+",";
		});
		pidjsStr = pidjsStr.substring(0,pidjsStr.length-1);
		var queryParam={};
		if(isAdvanceSearch){
			var printConfig = $("#printConfigSelect").val();
			if(printConfig.length != 0){
				printConfig = printConfig.split("&&");
				if(printConfig[0]==0){
					queryParam["useDate"] = getCurrentDate("yyyy-MM-dd",null,0);
				}else if(printConfig[0]==1){
					queryParam["useDate"] = getCurrentDate("yyyy-MM-dd",null,1);
				}else if(printConfig[0]==2){
					queryParam["useDate"] = getCurrentDate("yyyy-MM-dd",null,-1);
				}
			}else{
				queryParam["useDate"] = getCurrentDate("yyyy-MM-dd",null,0);
			}
		}else{
			queryParam["useDate"] = $("#useDate").val()
		}
		queryParam["pidsj"] = pidjsStr;
		queryParam["isPreview"] = 1;
		queryParam["printIndex"]=$("#printIndex").val();
		queryParam["printStartNumber"]=$("#printStartIndex").val();
		queryParam["printEndNumber"]=$("#printEndIndex").val();
		
		$.ajax({
			type:'POST',
			url:'${pageContext.request.contextPath}/printLabel/previewPrintLabel',
			dataType:'json',
			data:queryParam,
			success:function(response) {
				var html = "" ;
				var currentRow="";
				$.each(response, function(index, value){
					if(index%4==0){
						$("#labelListContain").append("<div id='lableList_"+index+"' class='showLabelItem'></div>");
						currentRow = index;
					}
					$("#lableList_"+currentRow).append("<div class='labelItem'><div id='"+pidjs[index]+"' class='labelItemDoc' onclick='showSinglePrintInfo(this)'>"+value.mainHtml+"</div></div>"); 
				 
				 $("#labelListContain").append("<div class='labelItem_back'><div id='"+pidjs[index]+"' class='labelItemDoc' onclick='showSinglePrintInfo(this)'>"+value.mainHtml+"</div></div>");
				
				}); 
				
				$("#labelList").css("display","block");
			}
		});
		
	}

	function showSinglePrintInfo(obj){
		openListShadow();
		openSingleShadow();
		
		$("#labelSingleContain").html("");
		$("#singleLabelPidjs").val();
		
		$("#labelSingleContain").html($(obj).html());
		$("#singleLabelPidjs").val($(obj).attr("id"));
		
		$("#labelSingleContain").css("transform","scale(1.32) translate(12.5%,12.5%)");
		$("#labelSingleContain").css("-webkit-transform","scale(1.32) translate(12.5%,12.5%)");
		$("#labelSingleContain").css("-moz-transform","scale(1.32) translate(12.5%,12.5%)");
		$("#labelSingleContain").css("-o-transform","scale(1.32) translate(12.5%,12.5%)");
		$("#labelSingleContain").css("-ms-transform","scale(1.32) translate(12.5%,12.5%)");
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