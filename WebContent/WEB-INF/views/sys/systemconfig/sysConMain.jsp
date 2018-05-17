<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@include file="../../common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
<style type="text/css">
	.ui-button-text-only .ui-button-text{
		padding: 0.8em 6em;
		background: #4CAF50;
	}
</style>


<script type="text/JavaScript">

	//参数是否修改
	var valueChange = false;
	var flag = false ;
	
	$(function() {
		
		//报表参数配置 语言下拉框设置
		$("#updateOtherConfig").combobox();
		
		$("#updateSysConUserPolicy").ajaxForm({
			 dataType: "json",
	         success : function(data) 
	         {
		         	message({
			    		data:data
		         	});
		         	if(data.success)
		         	{
			         	valueChange = false;
		         	}
	          }
	 	});
		
	});
	
	//参数修改设置
	function valueChanged()
	{	
		valueChange = true;
		flag=false;
	}
	//提交user表单
	function userSubmit(){
		if(valueChange){
			if(validateLock()){
				$('#updateSysConUserPolicy').submit();
			}
		}else{
			message({
	        	html: '<spring:message code="systemConfig.noUpdate"/>',
	        	showConfirm: true
	 		});
		}
	}
	
	
	// 账户锁定策略验证
	function validateLock()
	{
		var error_count = $("#error_count");
		var clear_time =$("#clear_time");
		var error_time = $("#error_time");
		error_count.val($.trim(error_count.val()));
		clear_time.val($.trim(clear_time.val()));
		error_time.val($.trim(error_time.val()));
		
		var max_online_count = $("#max_online_count");
		var user_pwd_indate = $("#user_pwd_indate");
		var pwd_expire_notice_days = $("#pwd_expire_notice_days");
		max_online_count.val($.trim(max_online_count.val()));
		user_pwd_indate.val($.trim(user_pwd_indate.val()));
		pwd_expire_notice_days.val($.trim(pwd_expire_notice_days.val()));
		
		var reg = /^\+?[1-9][0-9]*$/;
		
		if(!checkRegexp( error_count, reg ))
		{	
			error_count.addClass( "ui-state-error" );
			message({
	        	html: '<spring:message code="systemConfig.inputError"/>',
	        	showConfirm: true
	 		});
			return false;
		}
		error_count.removeClass( "ui-state-error" );	
		
		if(!checkLength(error_count, 1, 3)){
			message({
	        	html: '<spring:message code="systemConfig.input"/>[<spring:message code="systemConfig.errorCount"/>]',
	        	showConfirm: true
	 		});
			return false;
		}
		error_count.removeClass( "ui-state-error" );	
		if(!checkLength(clear_time, 1, 2)){
			message({
	        	html: '<spring:message code="systemConfig.input"/>[<spring:message code="systemConfig.clearTime"/>]',
	        	showConfirm: true
	 		});
			return false;
		}
		clear_time.removeClass( "ui-state-error" );	
		if(!checkLength(error_time, 1, 2)){
			message({
	        	html: '<spring:message code="systemConfig.input"/>[<spring:message code="systemConfig.errorTime"/>]',
	        	showConfirm: true
	 		});
			return false;
		}
		error_time.removeClass( "ui-state-error" );	
		if(!checkLength(max_online_count, 1, 3)){
			message({
	        	html: '<spring:message code="systemConfig.input"/>[<spring:message code="systemConfig.maxOnlineCount"/>]',
	        	showConfirm: true
	 		});
			return false;
		}
		max_online_count.removeClass( "ui-state-error" );	
		if(!checkLength(user_pwd_indate, 1, 2)){
			message({
	        	html: '<spring:message code="systemConfig.input"/>[<spring:message code="systemConfig.userPwdIndate"/>]',
	        	showConfirm: true
	 		});
			return false;
		}
		user_pwd_indate.removeClass( "ui-state-error" );	
		if(!checkLength(pwd_expire_notice_days, 1, 1)){
			message({
	        	html: '<spring:message code="systemConfig.input"/>[<spring:message code="systemConfig.pwdExpireNoticeDays"/>]',
	        	showConfirm: true
	 		});
			return false;
		}
		pwd_expire_notice_days.removeClass( "ui-state-error" );	
		if($.trim(error_count.val()) < 1 || $.trim(error_count.val()) > 100)
		{	
			error_count.addClass( "ui-state-error" );
			message({
	        	html: '<spring:message code="systemConfig.inputError"/>',
	        	showConfirm: true
	 		});
			return false;
		}
		error_count.removeClass( "ui-state-error" );	
		if(!checkRegexp( clear_time, reg ) || $.trim(clear_time.val()) > 60 || $.trim(clear_time.val()) < 1)
		{	
			clear_time.addClass( "ui-state-error" );
			message({
	        	html: '<spring:message code="systemConfig.inputError"/>',
	        	showConfirm: true
	 		});
			return false;
		}
		clear_time.removeClass( "ui-state-error" );	
		if(!checkRegexp( error_time, reg ) || $.trim(error_time.val()) > 60 || $.trim(error_time.val()) < 1)
		{	
			error_time.addClass( "ui-state-error" );
			message({
	        	html: '<spring:message code="systemConfig.inputError"/>',
	        	showConfirm: true
	 		});
			return false;
		}
		error_time.removeClass( "ui-state-error" );	
		if(!checkRegexp(max_online_count, reg )  || !isPInt(max_online_count))
		{	
			max_online_count.addClass( "ui-state-error" );
			message({
	        	html: '<spring:message code="systemConfig.inputError"/>',
	        	showConfirm: true
	 		});
			return false;
		}
		max_online_count.removeClass( "ui-state-error" );	
		if(!checkRegexp(user_pwd_indate, reg ) || $.trim(user_pwd_indate.val()) > 90 || $.trim(user_pwd_indate.val()) < 1)
		{	
			user_pwd_indate.addClass( "ui-state-error" );
			message({
	        	html: '<spring:message code="systemConfig.inputError"/>',
	        	showConfirm: true
	 		});
			return false;
		}
		user_pwd_indate.removeClass( "ui-state-error" );	
		
		if(!checkRegexp(pwd_expire_notice_days, reg ) || $.trim(pwd_expire_notice_days.val()) > 7 || $.trim(pwd_expire_notice_days.val()) < 1)
		{	
			pwd_expire_notice_days.addClass( "ui-state-error" );
			message({
	        	html: '<spring:message code="systemConfig.inputError"/>',
	        	showConfirm: true
	 		});
			return false;
		}
		pwd_expire_notice_days.removeClass( "ui-state-error" );	
		return true;
	}
</script>
</head>

<body>

	<%-- 新增编辑弹出框 --%>
	<div id="editView-div"  align="center" style="max-width:700px;padding-top: 20px;">
		<form id="updateSysConUserPolicy"  action="${pageContext.request.contextPath}/systemConfig/updateUserPolicy" method="post">
		<div class="popup" >
	    	<div class="row">
				<div class="column">
					<label class="tit"><spring:message code="systemConfig.errorCount"/></label>
						<input type="text"  style="height: 23px;width:100px;display: none;" />
					<input type="text" id="error_count" name="error_count" value="<spring:escapeBody htmlEscape='true'>${sysConMap['error_count'].sys_value}</spring:escapeBody>" maxlength="3" onchange="javascript:valueChanged()" />
					<span class="mand" style="color: #FA5A28;"><spring:message code="systemConfig.input4"/></span>
				</div>
				<div class="column">
					<label class="tit"><spring:message code="systemConfig.clearTime"/></label>
					<input type="text" id="clear_time" name="clear_time"  value="<spring:escapeBody htmlEscape='true'>${sysConMap['clear_time'].sys_value}</spring:escapeBody>" maxlength="2" onchange="javascript:valueChanged()"  />
					<span class="mand" style="color: #FA5A28;"><spring:message code="systemConfig.input5"/></span>
				</div>
			</div>
			<div class="row">
				<div class="column">
					<label class="tit"><spring:message code="systemConfig.errorTime"/></label>
					<input type="text" id="error_time" name="error_time"    value="<spring:escapeBody htmlEscape='true'>${sysConMap['error_time'].sys_value}</spring:escapeBody>" maxlength="2" onchange="javascript:valueChanged()" />
					<span class="mand" style="color: #FA5A28;"><spring:message code="systemConfig.input5"/></span>
				</div>
				<div class="column">
					<label class="tit"><spring:message code="systemConfig.maxOnlineCount"/></label>
					<input type="text" id="max_online_count" name="max_online_count"    value="<spring:escapeBody htmlEscape='true'>${sysConMap['max_online_count'].sys_value}</spring:escapeBody>" maxlength="3" onchange="javascript:valueChanged()" />
					<span class="mand" style="color: #FA5A28;"><spring:message code="systemConfig.input9"/></span>
				</div>
			</div>
			<div class="row">
				<div class="column">
					<label class="tit"><spring:message code="systemConfig.pwdRepeateCount"/></label>
					<select id="pwd_not_repeat_count" name="pwd_not_repeat_count" autoDestroy readonly  onchange="javascript:valueChanged()">
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '0'}">selected="selected"</c:if>>0</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '1'}">selected="selected"</c:if>>1</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '2'}">selected="selected"</c:if>>2</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '3'}">selected="selected"</c:if>>3</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '4'}">selected="selected"</c:if>>4</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '5'}">selected="selected"</c:if>>5</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '6'}">selected="selected"</c:if>>6</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '7'}">selected="selected"</c:if>>7</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '8'}">selected="selected"</c:if>>8</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '9'}">selected="selected"</c:if>>9</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '10'}">selected="selected"</c:if>>10</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '11'}">selected="selected"</c:if>>11</option>
						<option <c:if test="${sysConMap['pwd_not_repeat_count'].sys_value == '12'}">selected="selected"</c:if>>12</option>
					</select>
					<span class="mand" style="color: #FA5A28;"></span>
				</div>
				<div class="column">
					<label class="tit"><spring:message code="systemConfig.userPwdIndate"/></label>
					<input type="text" id="user_pwd_indate" name="user_pwd_indate"    value="<spring:escapeBody htmlEscape='true'>${sysConMap['user_pwd_indate'].sys_value}</spring:escapeBody>" maxlength="2" onchange="javascript:valueChanged()" />
					<span class="mand" style="color: #FA5A28;"><spring:message code="systemConfig.input7"/></span>
				</div>
			</div>
			<div class="row">
				<div class="column">
					<label class="tit"><spring:message code="systemConfig.pwdExpireNoticeDays"/></label>
					<input type="text" id="pwd_expire_notice_days" name="pwd_expire_notice_days"    value="<spring:escapeBody htmlEscape='true'>${sysConMap['pwd_expire_notice_days'].sys_value}</spring:escapeBody>" maxlength="1" onchange="javascript:valueChanged()" />
					<span class="mand" style="color: #FA5A28;"><spring:message code="systemConfig.input8"/></span>
				</div>
			</div>
			<div class="row">
				<div class="column" style="margin-left: -42px;">
					<button type="button" onclick="userSubmit()" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
					<span class="ui-button-text">确定</span>
				</button>
				</div>
			</div>	
				
		</div>
	</div>
	
</body>
</html>
