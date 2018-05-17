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
});

</script>

<div class="main-div">
<div class="content-container">
<div class="content">
	
    <div class="asked-info">
        <div class="patient-info">
            <div class="group-item" style="  width: 45%;">
                <dl>
                    <dt><spring:message code='pivas.yz1.parentNo2'/></dt><!-- 医嘱组号 -->
                    <dd><input type="text" readonly="readonly" value="${ydMain.parentNo}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz1.yzzt'/></dt><!-- 医嘱状态 -->
                    <dd><input type="text" readonly="readonly" id="yzzt" value="${ydMain.ydzxzt}"></dd>
                </dl>
                 <dl>
                    <dt><spring:message code='pivas.yz1.wardname'/></dt><!-- 病区 -->
                    <dd><input type="text" readonly="readonly" value="${ydMain.wardName}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz1.drawer'/></dt><!-- 医生。。。。。。doctorName -->
                    <dd><input type="text" readonly="readonly" value=""></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz3.mediType'/></dt><!-- 药品类型：特殊判断 -->
                    
                    <dd><input type="text" readonly="readonly" value="${medNames}"></dd>
                </dl>
            </div>
            <div class="group-span"></div>
            <div class="group-item"style="  width: 45%;">
                <dl>
                    <dt><spring:message code='pivas.yz2.patname'/></dt><!-- 病人-->
                    <dd><input type="text" readonly="readonly" value="${ydMain.patname}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz2.bedno'/></dt><!-- 床号 -->
                    <dd><input type="text" readonly="readonly" value="${ydMain.bedno}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz2.caseId'/></dt><!-- 病人住院号 -->
                    <dd><input type="text" readonly="readonly" value="${ydMain.caseId}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz2.sex'/></dt><!-- 性别 -->
                    <dd><input type="text" readonly="readonly" id="p_sex" value="${ydMain.sex=='0'?"女":"男"}"></dd>
                </dl>
                <dl>
                    <dt><spring:message code='pivas.yz2.age'/></dt><!-- 年龄  0天 1月 2年 -->
                    <dd><input type="text" readonly="readonly" id="age" value="${ydMain.age}${ydMain.ageunit== null ?'':(ydMain.ageunit==0?'天':(ydMain.ageunit==1?'月':'岁'))}"></dd>
                </dl>
            </div>
            <div class="group-span"></div>
        </div>
        <div class="medicine-tab">

            <div class="tabs-title">
                <ul style="float: left;">
                    <li><a class="tab-title-1 select"><spring:message code='pivas.yz3.mediBill'/></a></li>
                </ul>
            </div>
            
            
            <div class="tabs-content">
                <div class="tab-content-1"  style="display: block;">
                    <table>
                        <thead>
                            <tr>
                                <th width="6%">配置费规则名称</th><!-- 配置费规则名称 -->
                                <th width="8%">配置费编码</th><!-- 配置费编码 -->
                                <th width="12%">配置费名称</th><!-- 配置费名称-->
                                <th width="6%">数量</th><!-- 数量-->       
                                 <th width="6%">单价(元)</th><!-- 数量-->                                
                                <th width="6%">操作人</th><!-- 操作人 -->
                                <th width="6%">操作日期</th><!-- 操作日期< -->
                                <th width="6%">操作</th><!--操作-->
                                <th width="6%">详情</th><!-- 详情 -->
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${chargeList}" var="charge" step="1" >
                            <tr>
                            	<td>${charge.rulename}</td>
                            	<td>${charge.costcode}</td>
                                <td>${charge.costname}</td>
                                <td>${charge.amount}</td>
                                <td>${charge.price}</td>
                                <td>${charge.czymc}</td>
                                <td>${charge.pzfsqrq}</td>
                                <td>
                                <c:choose>
								    <c:when test="${charge.pzfzt=='0'}">
								     <a href="javascript:chargeNow('${charge.ydPzfId}','${charge.costcode}', '${charge.pzfzt}')">收费</a>
								    </c:when>
								    <c:when test="${charge.pzfzt=='1'||charge.pzfzt=='2'}">
								      <a href="javascript:chargeNow('${charge.ydPzfId}','${charge.costcode}', '${charge.pzfzt}')">退费</a>
								    </c:when>  
								 </c:choose>
                                </td>
                                <td>
                                  <a href="javascript:chargeDetail('${charge.costcode}')">详情</a>
								</td>
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
</div>
</div>
</div>
<div id="chargeDetailDiv" class="medicine-tab" style="display: none;" title="收费历史" >
    <div class="tabs-content" style="margin-top: 20px;">
            	<div class="tab-content-1"  style="display: block;">
		<table>
                    <thead>
                        <tr>
                        	<th width="25%">收费时间</th>
                            <th width="25%">收费人</th>
                            <th width="25%">收费状态</th>
                            <th width="25%">失败原因</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<c:forEach items="${chargeListAll}" var="charge" step="1" >
                        <tr class="yd_${charge.costcode}" >
                        	<td>${charge.pzfsqrq}</td>
                        	<td>${charge.czymc}</td>
                        	<td>
                        	<c:choose>
							    <c:when test="${charge.pzfzt=='0'}">收费失败  </c:when>
							    <c:when test="${charge.pzfzt=='1'}">收费成功  </c:when>
							    <c:when test="${charge.pzfzt=='2'}">退费失败</c:when> 
							    <c:when test="${charge.pzfzt=='3'}">退费成功</c:when> 
							</c:choose>
                        	<td>${charge.pzfsbyy}</td>
                        </tr>
                    	</c:forEach>
                    </tbody>
                	</table>
		</div>
		</div>
</div>
</c:if>
