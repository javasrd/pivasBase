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
                    <dt><spring:message code='pivas.yz1.freqCode'/></dt><!-- 频次 -->
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
        <div class="medicine-tab" >
            <div class="tabs-title">
                <ul style="  float: left;">
                	<li><a class="tab-title-2 select">操作记录</a></li>
                    <li><a class="tab-title-1 "><spring:message code='pivas.yz3.mediInfo'/></a></li>
                </ul>
            </div>
            <div class="tabs-content">
                
                <div class="tab-content-2" style="display: block;">
                    <table>
                        <thead>
                            <tr>
                            <th class="wp14">操作人</th><!-- 操作人 -->
                            <th class="wp21">操作时间</th><!-- 操作时间 -->
                            <th class="wp21">操作类型</th><!-- 操作类型 -->
                        	</tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${trackingRecordList}" var="trackingRecord" step="1" >
                        	<tr>
                        	<td>${trackingRecord.operator}</td>
                        	<td>${trackingRecord.operation_time}</td>
                        	<td>${trackingRecord.type_name}</td>
                        	</tr>
                        	</c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <div class="tab-content-1"  style="display: none;">
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
                                <th class="wp10">生成日期</th><!-- 生成日期 -->
                                <th class="wp10">用药日期</th><!-- 用药日期 -->
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${ydList}" var="yd" step="1" >
                            <tr>
                            	<td>${yd.actOrderNo}</td>
                                <td>${yd.chargeCode}</td>
                                <td>${yd.drugname}</td>
                                <td>${yd.specifications2}</td>
                                <td>${yd.dose}</td>
                                <td>${yd.doseUnit}</td>
                                <td>${yd.quantity}</td>
                                <td>${yd.medicamentsPackingUnit}</td>
                                <td>${yd.scrqS}</td>
                                <td>${yd.yyrqS}</td>
                            </tr>
                        	</c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
       
        <div class="infoBack">
        <a class="button" style="  padding: 0.6em 2em;" onclick="backMain()" >返回</a>
        </div>
    </div>

</c:if>
