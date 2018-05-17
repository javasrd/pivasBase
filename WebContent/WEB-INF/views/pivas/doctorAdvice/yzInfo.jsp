<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${success==true}">

    <link href="${pageContext.request.contextPath}/assets/pivas/css/style.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/assets/pivas/css/common.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
    <script rel="script" src="${pageContext.request.contextPath}/assets/pivas/js/cssjs.js" type="application/javascript"></script>
    <script rel="script" src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>

<script type="application/javascript">
$(function() {
	$("#yzzt").val(getDicValue("yzzt",$("#yzzt").val()));
	//$("#yzlx").val(getDicValue("yzlx",$("#yzlx").val()));
	$("#p_sex").val(getDicValue("sex",$("#p_sex").val()));
	$("#age").val('${yzMain.age}'+getDicValue("ageUnit",'${yzMain.ageunit}'));
	$(".dic_yzzt").each(function(){
		$(this).html(getDicValue("yzzt",$(this).html()));
	});
	$("#yzshzt").html(getDicValue("yzshzt",$("#yzshzt").html()));
});

</script>

<div class="main-div">
<div class="content-container" style="width:100%;">
<div class="content">

	<c:if test="${yzMain.yzshzt==0}">
	<div class="steps">
		<ol>
            <li class="current-step">未审<span class="next-arrow-holder"></span></li>
            <li><div>审核通过</div></li>
            <li>入仓扫描</li>
            <li>出仓扫描</li>
            <li>停止</li>
        </ol>
    </div>
	</c:if>
	
	<c:if test="${yzMain.yzshzt==1}">
	<div class="steps">
		<ol>
            <li class="prev-step">未审<span class="prev-arrow-holder"></span></li>
            <li class="current-step"><div>审核通过</div><span class="next-arrow-holder"></span></li>
            <li>入仓扫描</li>
            <li>出仓扫描 </li>
            <li>停止</li>
        </ol>
	</div>
	</c:if>
	
	<c:if test="${yzMain.yzshzt==2}">
    <div class="steps unpass">
      <ol>
            <li class="prev-step">未审<span class="prev-arrow-holder"></span></li>
	        <li class="current-step"><div>审核不通过</div><span class="next-arrow-holder"></span></li>
	        <li>入仓扫描</li>
	        <li>出仓扫描</li>
	        <li>停止</li>
		</ol>
    </div>
	</c:if>
	
    <div class="asked-info">
        <div class="patient-info">
            <div class="group-item">
                <dl>
                    <dt><spring:message code='pivas.yz1.parentNo'/></dt><!-- 组号 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.parentNo}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz1.startTime2'/></dt><!-- 医嘱开立时间 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.startTimeS}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz1.end2'/></dt><!-- 医嘱结束时间 -->
                    <dd><input type="text" readonly="readonly" value='${yzMain.endTimeS}'></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz1.yzzt'/></dt><!-- 医嘱状态 -->
                    <dd><input type="text" readonly="readonly" id="yzzt" value="${yzMain.yzzt}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz1.yzlx'/></dt><!-- 医嘱类型 -->
                    <dd><input type="text" readonly="readonly" id="yzlx" value="${yzMain.yzlx==0?'长嘱':'临嘱'}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz3.mediType'/></dt><!-- 药品类型：特殊判断 -->
                    <dd><input type="text" readonly="readonly" value="${medNames}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz3.dropSpeed'/></dt><!-- 滴速 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.dropSpeed}"></dd>
                </dl>
                <dl>
                    <dt>&nbsp;</dt>
                    <dd><input type="text" readonly="readonly" value="" style="visibility: hidden;" ></dd>
                </dl>
            </div>
            <div class="group-span"></div>
            <div class="group-item">
                <dl>
                    <dt><spring:message code='pivas.yz1.doctor'/></dt><!-- 医嘱开立医生工号 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.doctor}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz1.doctorName'/></dt><!-- 医嘱开立医生姓名 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.doctorName}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz1.inpatientNo'/></dt><!-- 住院流水号 --> 
                    <dd><input type="text" readonly="readonly" value="${yzMain.inpatientNo}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz1.drawer'/></dt><!-- 录入护士的工号 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.drawer}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz1.drawername'/></dt><!-- 录入护士的姓名 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.drawername}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz1.freqCode'/>\用法</dt><!-- 频次 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.freqCode}"></dd>
                </dl>
               <%--  <dl>
                    <dt><spring:message code='pivas.yz1.supplyCode'/></dt><!-- 用药途径 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.supplyName}"></dd>
                </dl> --%>
                <dl>
                    <dt><spring:message code='pivas.yz2.age'/></dt><!-- 年龄 -->
                    <dd><input type="text" readonly="readonly" id="age" value="${yzMain.age}"></dd>
                </dl>
                <dl>
                    <dt>&nbsp;</dt>
                   <dd><input type="text" readonly="readonly" value="" style="visibility: hidden;" ></dd>
                </dl>
            </div>
            <div class="group-span"></div>
            <div class="group-item">
            	<dl>
                    <dt>病区</dt><!-- 病人-->
                    <dd><input type="text" readonly="readonly" value="${yzMain.wardName}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz2.patname'/></dt><!-- 病人-->
                    <dd><input type="text" readonly="readonly" value="${yzMain.patname}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz2.bedno'/></dt><!-- 床号 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.bedno}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz2.caseId'/></dt><!-- 病人住院号 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.caseId}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz2.sex'/></dt><!-- 性别 -->
                    <dd><input type="text" readonly="readonly" id="p_sex" value="${yzMain.sex}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz2.avdp'/></dt><!-- 体重 -->
                    <dd><input type="text" readonly="readonly" id="p_sex" value="${yzMain.avdp}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz2.birthday'/></dt><!-- 出生日期 -->
                    <dd><input type="text" readonly="readonly" value="${yzMain.birthdayS}"></dd>
                </dl>
               	<dl>
                   <dt>&nbsp;</dt>
                   <dd><input type="text" readonly="readonly" value="" style="visibility: hidden;" ></dd>
                </dl>
            </div>
        </div>
        <div class="medicine-tab" style="  width: 98%;">
            <div class="tabs-title">
                <ul style="  float: left;">
                    <li><a class="tab-title-1 select"><spring:message code='pivas.yz3.mediInfo'/></a></li>
                    <li><a class="tab-title-2 "><spring:message code='pivas.yz3.mediBill'/></a></li>
                    <li><a class="tab-title-3 "><spring:message code='pivas.yz3.mediHist'/></a></li>
                </ul>
            </div>
            <div class="tabs-content">
                <div class="tab-content-1"  style="display: block;">
                    <table>
                        <thead>
                            <tr>
                            	<th class="wp7"><spring:message code='pivas.yz1.actOrderNo'/></th><!-- 药品编码 -->
                                <th class="wp7"><spring:message code='pivas.yz1.chargeCode'/></th><!-- 药品编码 -->
                                <th class="wp18"><spring:message code='pivas.yz1.drugname'/></th><!-- 药品 -->
                                <th class="wp12"><spring:message code='pivas.yz1.specifications'/></th><!-- 药品规格 -->
                                <th class="wp7"><spring:message code='pivas.yz1.dose'/></th><!-- 单次剂量 -->
                                <th class="wp7"><spring:message code='pivas.yz1.doseUnit'/></th><!-- 计量单位 -->
                                <th class="wp7"><spring:message code='pivas.yz1.quantity'/></th><!-- 数量 -->
                                <th class="wp7"><spring:message code='pivas.yz1.medicamentsPackingUnit'/></th><!-- 数量单位 -->
                                <th class="wp10"><spring:message code='pivas.yz1.startTime'/></th><!-- 开始时间 -->
                                <th class="wp10"><spring:message code='pivas.yz1.endTime'/></th><!-- 结束时间 -->
                                <th class="wp6"><spring:message code='pivas.yz1.yzzt'/></th><!-- 医嘱状态 -->
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${yzList}" var="yz" step="1" >
                        	<c:if test="${yz.yzzt==0}">
                            <tr>
                            	<td>${yz.actOrderNo}</td>
                                <td>${yz.chargeCode}</td>
                                <td>${yz.medicamentsName}</td>
                                <td>${yz.specifications2}</td>
                                <td>${yz.dose}</td>
                                <td>${yz.doseUnit}</td>
                                <td>${yz.quantity}</td>
                                <td>${yz.medicamentsPackingUnit}</td>
                                <td>${yz.startTimeS}</td>
                                <td>${yz.endTimeS}</td>
                                <td class="dic_yzzt">${yz.yzzt}</td>
                            </tr>
                            </c:if>
                        	</c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="tab-content-2" style="display: none;">
                    <table class="narrow-table">
                        <thead>
                            <tr>
                            <th class="wp14"><spring:message code='pivas.yzyd.zxbc'/></th><!-- 批次 -->
                            <th class="wp21"><spring:message code='pivas.yzyd.yyrq'/></th><!-- 用药时间 -->
                            <th class="wp21"><spring:message code='pivas.yzyd.ydpq'/></th><!-- 瓶签号 -->
                           <%--  <th class="wp32"><spring:message code='pivas.yzyd.xh'/></th><!-- 执行序号 --> --%>
                            <th class="wp11"><spring:message code='pivas.yzyd.run'/></th><!-- 启用 -->
                        </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${ydpqList}" var="ydpd" step="1" >
                        	<tr>
                        	<td>${ydpd.pb_name}</td>
                        	<td>${ydpd.yyrqS}</td>
                        	<td>${ydpd.ydpq}</td>
                        	<%-- <td>${ydpd.pb_num}</td> --%>
                        	<td>${ydpd.pb_enabled==0?"不启用":"启用"}</td>
                        	</tr>
                        	</c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="tab-content-3" style="display: none;">
                    <table>
                        <thead>
                            <tr>
                            	<th class="wp7"><spring:message code='pivas.yz1.actOrderNo'/></th><!-- 药品编码 -->
                                <th class="wp7"><spring:message code='pivas.yz1.chargeCode'/></th><!-- 药品编码 -->
                                <th class="wp18"><spring:message code='pivas.yz1.drugname'/></th><!-- 药品 -->
                                <th class="wp12"><spring:message code='pivas.yz1.specifications'/></th><!-- 药品规格 -->
                                <th class="wp7"><spring:message code='pivas.yz1.dose'/></th><!-- 单次剂量 -->
                                <th class="wp7"><spring:message code='pivas.yz1.doseUnit'/></th><!-- 计量单位 -->
                                <th class="wp7"><spring:message code='pivas.yz1.quantity'/></th><!-- 数量 -->
                                <th class="wp7"><spring:message code='pivas.yz1.medicamentsPackingUnit'/></th><!-- 数量单位 -->
                                <th class="wp10"><spring:message code='pivas.yz1.startTime'/></th><!-- 开始时间 -->
                                <th class="wp10"><spring:message code='pivas.yz1.endTime'/></th><!-- 结束时间 -->
                                <th class="wp6"><spring:message code='pivas.yz1.yzzt'/></th><!-- 医嘱状态 -->
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${yzList}" var="yz" step="1" >
                            <tr>
                            	<td>${yz.actOrderNo}</td>
                                <td>${yz.chargeCode}</td>
                                <td>${yz.medicamentsName}</td>
                                <td>${yz.specifications2}</td>
                                <td>${yz.dose}</td>
                                <td>${yz.doseUnit}</td>
                                <td>${yz.quantity}</td>
                                <td>${yz.medicamentsPackingUnit}</td>
                                <td>${yz.startTimeS}</td>
                                <td>${yz.endTimeS}</td>
                                <td class="dic_yzzt">${yz.yzzt}</td>
                            </tr>
                        	</c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="check-result">
            <div class="result-title"><spring:message code='pivas.yz1.yzshzt'/></div><!-- 审方结果 -->
            <div class="result-content">
                <table class="narrow-table">
                    <thead>
                    <tr>
                        <th style="width: 50%;"><spring:message code='pivas.yz3.type'/></th><!-- 类型 -->
                        <th><spring:message code='pivas.yz3.info'/></th><!-- 信息 -->
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                        <label id="yzshzt">${yzMain.yzshzt}</label>
                        <label id="yzshbtglx">
                        <c:forEach items="${errTypeList}" var="errType" >
                        <c:if test="${yzMain.yzshzt!=1 && yzMain.yzshbtglx==errType.gid}">
                        	，${errType.name}
                        </c:if>
                        </c:forEach>
                        </label>
                       </td><!-- 审核不同过，剂量超标 -->
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="remark">
            <div class="remark-title"><spring:message code='pivas.yz3.desc'/></div><!-- 备注 -->
            <div class="remark-content">
            	<c:if test="${yzMain.yzshzt!=1}">
                 	<textarea disabled="disabled">${yzMain.yzshbtgyy}</textarea>
                </c:if>
                
            </div>
        </div>
        <div class="infoBack">
        <a class="button" style="  padding: 0.6em 2em;" onclick="backMain()" >返回</a>
        </div>
    </div>
</div>
</div>
</div>

</c:if>
