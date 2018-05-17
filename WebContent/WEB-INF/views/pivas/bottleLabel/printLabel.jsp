<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
input::-ms-clear{display:none;}
</style>
<div id="printLabelView-div" title="${title}" align="center" style="display: none;">
	<form id="printLabelView-form" action="${pageContext.request.contextPath}/printPdf/printLabel" method="post">
		<input type="hidden" name="printType" value="${printType}"/>
		<div class="popup">
			<div class="row">
				<div class="column">
					<label class="tit"><spring:message code='common.date'/></label>
					<input type="text" name="useDate" style="color: #555555;" class="Wdate" empty="false" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
	            	<span class="mand">*</span>
				</div>
				<c:if test="${printType eq 1}">
				<input type="hidden" name="groupByMedical" value="0" >
				<!-- 
				<div class="column">
					<label class="tit"><spring:message code="pivas.print.groupByMedical"/></label>
					<select style="width: 184px;" name="groupByMedical" autoDestroy readonly>
						<option value="1"><spring:message code="common.yes"/></option>
						<option value="0" selected="selected"><spring:message code="common.no"/></option>
					</select>
				</div> -->
				</c:if>
			</div>
			
			<c:if test="${printType eq 0}">
			<div class="row">
				<div class="column">
					<label class="tit"><spring:message code="pivas.confingfeerule.drugtype"/></label>
					<select style="width: 184px;" name="categoryId" autoDestroy readonly>
						<option value=""><spring:message code="common.select"/></option>
						<c:forEach items="${medicCategories}" var="item">
						<option value="${item.categoryId}"><spring:escapeBody htmlEscape="true">${item.categoryName}</spring:escapeBody></option>
						</c:forEach>
					</select>
				</div>
			</div>
			</c:if>
			
			<div class="row">
				<div class="column">
					<label class="tit"><spring:message code='rule.batch'/></label>
					<select style="width: 184px;" name="batchId" readonly>
						<option value=""><spring:message code="common.select"/></option>
						<c:forEach items="${batchList}" var="item">
						<option value="${item.id}"><spring:escapeBody htmlEscape="true">${item.name}</spring:escapeBody></option>
						</c:forEach>
					</select>
				</div>
				<div class="column">
					<label class="tit"><spring:message code='pivas.patient.wardName'/></label>
					<select style="width:184px;" name="wardCode" autoDestroy readonly>
						<option value=""><spring:message code="common.select"/></option>
						<c:forEach items="${inpatientAreaList}" var="item">
						<option value="${item.deptCode}"><spring:escapeBody htmlEscape="true">${item.deptName}</spring:escapeBody></option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
	</form>
</div>

<script>
	
//修改建筑Form的ajax交互
$('#printLabelView-form').ajaxForm({
    dataType: "json",
    success : function(data){
    	if(data.success){
    		window.open("${pageContext.request.contextPath}/printLabelDownLoad/<shiro:principal property="account"/>/"+data.msg);
    	}else{
	    	message({
				data: data
	    	});
    	}
    }
});

$("#printLabelView-form select").combobox();
$("#printLabelView-form input[name='useDate']").val(getCurrentDate("yyyy-MM-dd"));

$("#printLabelView-div").dialog({
	autoOpen: true,
	height: 240,
	width: 500,
	modal: true,
	resizable: false,
	buttons: {
		"<spring:message code='common.confirm'/>": function() {
			var bValid = validateInput("printLabelView-form") == null?true:false;
			if (!bValid ) {
				return ;
			}
			
			$("#printLabelView-form").submit();
		},
		"<spring:message code='common.cancel'/>": function() {
			$("#printLabelView-div").dialog("close");
		}
	},
	close: function() {
		resetForm("printLabelView-div");
	}
});

</script>
