<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    Object obj = request.getAttribute(org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	boolean flag = false;
	Integer msgCode = 0;
	if (obj != null) {
		//验证码超时
		if ("com.zc.zchl.sys.modules.login.CaptchaTimeoutException".equals(obj.toString())) {
			msgCode = 1;
			//验证码不正确
		} else if ("com.zc.zchl.sys.modules.login.CaptchaIncorrectException".equals(obj.toString())) {
			msgCode = 2;
			//用户名或密码错误
		} else if ("org.apache.shiro.authc.UnknownAccountException".equals(obj.toString()) || "org.apache.shiro.authc.IncorrectCredentialsException".equals(obj.toString())) {
			msgCode = 3;
		} else if ("org.apache.shiro.authc.LockedAccountException".equals(obj.toString())) {
			msgCode = 4;
		} else if ("com.zc.zchl.sys.modules.login.MaxOnlineCountException".equals(obj.toString())) {
			msgCode = 6;
		} else if ("com.zc.zchl.sys.modules.login.PasswordExpiredException".equals(obj.toString())) {
			msgCode = 7;
		}else if("com.zc.zchl.sys.modules.login.NonCloudAccessException".equals(obj.toString())){
			//云平台登录,非管理员不能登录 add guojing  2014-12-21
			msgCode = 8;
		}
		else {
			msgCode = 5;
		}
	}

	//放置消息码
	request.setAttribute("msgCode", msgCode);

	//放置语言
	String language = RequestContextUtils.getLocale(request) .getLanguage();
	request.setAttribute("language", language);
	session.setAttribute("language", language);
%>

<html>
<head>
<title><spring:message code="common.login" /></title>

<script type="text/javascript">
	if (self === top) {
	} else {
		top.location.href = self.location.href;
	}
</script>

<link href="${pageContext.request.contextPath}/assets/common/css/login.css" type="text/css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/assets/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/jquery-validation/1.11.0/messages_bs_zh.js" type="text/javascript"></script>
<script>
	function onClickCode() {
		$("#onCode_id").attr('src',"${pageContext.request.contextPath}/captchaCode?_dc="+ (new Date()).getTime());
	}
	
	function login() {
		var name = $("#account").val();
		var password = $("#password").val();
		var code_id = $("#code_id").val();
		var admin = $("#onCode_id").val();

		if (name == "") {
			document.getElementById("name_div").style.display = "";
			document.getElementById("pw_div").style.display = "none";
			<c:if test="${enableCaptcha}">
			document.getElementById("code_div").style.display = "none";
			</c:if>
			document.getElementById("msg_id").style.display = "none";
			return false;
		} else if (password == "") {
			document.getElementById("pw_div").style.display = "";
			document.getElementById("name_div").style.display = "none";
			<c:if test="${enableCaptcha}">
			document.getElementById("code_div").style.display = "none";
			</c:if>
			document.getElementById("msg_id").style.display = "none";
			return false;
		} else if (code_id == "") {
			<c:if test="${enableCaptcha}">
			document.getElementById("code_div").style.display = "";
			</c:if>
			document.getElementById("pw_div").style.display = "none";
			document.getElementById("name_div").style.display = "none";
			document.getElementById("msg_id").style.display = "none";
			return false;
		}
	}
	
	function myreset() {
		$("#username").val("");
		$("#password").val("");
		$("#logincode").val("");
		$("#username").focus();
	}

	/**
	 * 回车事件，调用登录接口
	 */
	function loginEvent(event) {
		if (event.keyCode === 13) {
			login();
		}
	}

	$(function() {
		$('#account').focus();
		var account = $('#account').val();
		// 如果用户名为空，则光标置于用户名输入框
		if (!account) {
			$('#account').focus();
			// 否则，则光标置于密码输入框
		} else {
			$('#password').focus();
		}

		//为form绑定验证器
		$("#loginForm").validate({
			rules : {
				logincode : {
					required : true,
					validCode : $("#code")
				}
			}
		});

		$("#siteLanguageSelector").bind('change', function(combo) {
			window.location.search = '?siteLanguage=' + $("#siteLanguageSelector").val();
		});
	});
</script>
</head>

<body onkeydown="loginEvent(event);" >

	<span style="position: absolute; right: 40px; top: 3px;display:none;" >
	   <span style="font-size: 12px;height:30px;line-height:28px;margin-left:5px;">语言选择:</span>
	   <select id="siteLanguageSelector" style="width: 120px;height: 23px; line-height: 23px; padding: 0;margin-top: 5px;">
			<c:forEach items="${languages}" var="var_language">
				<option value="${var_language.code}"
					<c:if test="${var_language.code eq language}">selected="selected"</c:if>>${var_language.description}</option>
			 </c:forEach>
	    </select>
	</span>
	
	<%--<table style="width:100%;">--%>
	<%--<tr>--%>
	<%--<td style="width:925px;height: 315px;text-align: center;background-color: #5b7ec2;">--%>
	<%--<input type="button" class="header">--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--</table>--%>
	
		<form action="${pageContext.request.contextPath}/login" id="loginForm" method="post" style="margin: 0;">
			<%--<table id="MainMiddle_Login" cellspacing="0" cellpadding="0" border="0" align="center" style="margin-top:45px;">--%>
				<%--<tr>--%>
					<%--<td><div class="userImg"></div></td><td><input id="account" name="username" type="text" style="width:250px;" value=""/></td>--%>
				<%--</tr>--%>
				<%----%>
				<%--<tr class="trH"></tr>--%>
				<%----%>
				<%--<tr>--%>
					<%--<td><div class="passwordImg"></div></td><td><input id="password" name="password" type="password" style="width:250px;" value=""/></td>--%>
				<%--</tr>--%>
				<%----%>
				<%--<tr class="trH"></tr>--%>
				<%--<!-- --%>
				<%--<c:if test="${enableCaptcha}">--%>
					<%--<tr>--%>
						<%--<td>--%>
							<%--<spring:message code="login.identifyingCode" />--%>
						<%--</td>--%>
						<%--<td>--%>
							<%--<div style="float: left;">--%>
								<%--<input class="Text" type="text" style="width: 82px; height: 16px; line-height: 22px;" id="code_id" name="captcha" />--%>
							<%--</div> --%>
						    <%--<img style="margin: 10px;" src="${pageContext.request.contextPath}/captchaCode" onClick="onClickCode();" id="onCode_id" />--%>
						<%--</td>--%>
					<%--</tr>--%>
				<%--</c:if>--%>
				<%----%>
				<%--<tr class="trH"></tr>--%>
						 <%---->		--%>
				<%--<tr>--%>
					<%--<td></td>--%>
					<%--<td>--%>
						<%--<div id="name_div" style="display: none; color: #FF0000; font-size: 15px;">--%>
						    <%--<spring:message code="login.msg.warn.noUsername" />--%>
						<%--</div>--%>
						<%--<div id="pw_div" style="display: none; color: #FF0000; font-size: 15px;">--%>
							<%--<spring:message code="login.msg.warn.noPassword" />--%>
						<%--</div>--%>
						<%--<c:if test="${enableCaptcha}">--%>
							<%--<div id="code_div" style="display: none; color: #FF0000; font-size: 15px;">--%>
								<%--<spring:message code="login.msg.warn.noIdentifyingCode" />--%>
							<%--</div>--%>
						<%--</c:if>--%>
						<%--<div id="msg_id" style="color: #FF0000; font-size: 15px">--%>
							<%--<c:if test="${msgCode eq 1}">--%>
								<%--<spring:message code="login.msg.err.identifyingCodeTimeout" />--%>
							<%--</c:if>--%>
							<%--<c:if test="${msgCode eq 2}">--%>
								<%--<spring:message code="login.msg.err.identifyingIncorrect" />--%>
							<%--</c:if>--%>
							<%--<c:if test="${msgCode eq 3}">--%>
								<%--<spring:message--%>
									<%--code="login.msg.err.usernameOrPasswordIncorrect" />--%>
							<%--</c:if>--%>
							<%--<c:if test="${msgCode eq 4}">--%>
								<%--<spring:message code="login.msg.err.userLocked" />--%>
							<%--</c:if>--%>
							<%--<c:if test="${msgCode eq 5}">--%>
								<%--<spring:message code="login.msg.err.serverSideError" />--%>
							<%--</c:if>--%>
							<%--<c:if test="${msgCode eq 6}">--%>
								<%--<spring:message code="user.ExceedMaxOnlineCount" />--%>
							<%--</c:if>--%>
							<%--<c:if test="${msgCode eq 7}">--%>
								<%--<spring:message code="user.PwdExpired" />--%>
							<%--</c:if>--%>
							<%--<c:if test="${msgCode eq 8}">--%>
								<%--<spring:message code="log.msg.err.cloud" />--%>
							<%--</c:if>--%>
						<%--</div>--%>
					<%--</td>--%>
				<%--</tr>--%>

				<%--<tr class="trH">--%>
			    	<%--<td colspan="2"><input type="submit" onclick="return login();" class="login" value="<spring:message code="common.login1" />" style="width:300px;text-align:center;text-indent:0px; height:45px;background-color:#ffc107d4;border:0px;padding:0px;" onclick="window.location.href='index.html'"/></td>--%>
				<%--</tr>--%>
			<%--</table>--%>
			<div class="login-warpper">
				<div class="login-box-position">
					<div class="left">

					</div>
					<div class="right">
						<div class="login-box">
							<div class="login-titile">PIVAS管理系统</div>
							<div class="input-group-item">
								<div class="lt-icon-user"></div>
								<div class="input-text"><input type="text"  id="account" name="username"  placeholder="请输入用户名" value=""></div>
							</div>
							<div class="input-group-item">
								<div class="lt-icon-user"></div>
								<div class="input-text"><input  id="password" name="password"  placeholder="请输入密码" type="password"  value=""></div>
							</div>
							<%-- <c:if test="${enableCaptcha}">
								<div class="input-group-item">
									<spring:message code="login.identifyingCode" />
									<div style="float: left;">
										<input class="Text" type="text" style="width: 82px; height: 16px; line-height: 22px;" id="code_id" name="captcha" />
									</div>
							    	<img style="margin: 10px;" src="${pageContext.request.contextPath}/captchaCode" onClick="onClickCode();" id="onCode_id" />
							    </div>
							</c:if> --%>
							<div class="input-group-item">
								<div class="lt-icon-user"></div>
								<div class="input-text"><input type="submit" onclick="return login();" class="login" value="登录" style="text-align:center;text-indent:0px; height:45px;background-color:#4CAF50;border:0px;padding:0px;"></div>
							</div>
							<div class="input-error-tip">
								<div id="name_div" style="display: none; color: #FF0000; font-size: 15px;">
									帐号不能为空
								</div>
								<div id="pw_div" style="display: none; color: #FF0000; font-size: 15px;">
									密码不能为空
								</div>
								<c:if test="${enableCaptcha}">
									<div id="code_div" style="display: none; color: #FF0000; font-size: 15px;">
										验证码不能为空
									</div>
								</c:if>
								<div id="msg_id" style="color: #FF0000; font-size: 15px">
									<c:if test="${msgCode eq 1}">
										验证码超时
									</c:if>
									<c:if test="${msgCode eq 2}">
										验证码不正确
									</c:if>
									<c:if test="${msgCode eq 3}">
										帐号或密码错误
									</c:if>
									<c:if test="${msgCode eq 4}">
										用户已经锁定,请联系管理员
									</c:if>
									<c:if test="${msgCode eq 5}">
										服务器内部错误
									</c:if>
									<c:if test="${msgCode eq 6}">
										<spring:message code="user.ExceedMaxOnlineCount" />
									</c:if>
									<c:if test="${msgCode eq 7}">
										<spring:message code="user.PwdExpired" />
									</c:if>
									<c:if test="${msgCode eq 8}">
										云系统接入,非管理员不能登录
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
</body>
</html>
