<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.zc.base.common.util.CommonUtil"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page import="java.util.Map" %> 
<%@ page import="java.util.HashMap" %> 
<html>

<head>
<title>PIVAS管理系统</title>
<script type="text/javascript">
	var bLanguage = '${sessionScope.language}' === 'en' ? 'en' : 'zh-cn';
</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/jquery-ui/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/pivas/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/sysCss/common/popup.css">

<script src="${pageContext.request.contextPath}/assets/jquery/jquery-1.8.3.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/jquery-ui/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/grid/js/jquery.form.js"></script>
<script src="${pageContext.request.contextPath}/assets/common/js/my97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/assets/common/js/util.js"></script>
<script src="${pageContext.request.contextPath}/assets/pivas/js/cssjs.js"></script>
<script src="${pageContext.request.contextPath}/assets/pivas/js/comm.js"></script>
<script src="${pageContext.request.contextPath}/assets/layer/layer.js" type="text/javascript"></script>
<style type="text/css">
.popup div.row div.column span.ui-combobox a{height: 24px;}
#bottomMenu{
	border-radius:5px 0px 0 5px;
}
#bottomMenu li{
	width:175px;
	float:left;
}
#bottomMenu li ul{
}
#bottomMenu li ul li{
	background:url(${pageContext.request.contextPath}/assets/pivas/images/shaixuan_er.png) left no-repeat;
	margin-top:-5px;
	border-bottom:none;
}
#bottomMenu li a,#bottomMenu li ul li a{
	float:left;
	width:100%;}
#bottomMenu li span{
	font-size: 0.75em;
    color: #000000;
    }
.ui-dialog .ui-dialog-titlebar{
	height: 28px;
}
.menu_div{
	height:46px;
	line-height:46px;
	font-size:15px;
	font-weight:700;
	text-indent:53px;
}
.span1 {
	word-break:break-all;
	float:left;
	width:135px;
}
.span2 {
	/* word-break:break-all; */
	margin-top:3px;
	float:left;
	width:40px;
	height:20px;
	background:#39aeaa;
	border-radius:5px;
	line-height:20px;
}
</style>
	
<script type="text/javascript" >

	var allFieldsInfo;
	
	var jspURL = "";
	
	function onloadwin()
	{
		if(parseFloat($(".left-menu").css("width"))>0){
			if(window.navigator.userAgent.indexOf("Firefox") >= 0){
	            //$('#bg').css({'width':w+'px'});
				$("#mainFrame").css("width",$(document).width()-240+"px").css("height",'700');
				if($(document).height()>1000){
                    $(".left-menu").css("height",window.screen.height-80+"px");
				}else{
                    $(".left-menu").css("height",$(document).height()-80+"px");
				}
				$(".dl-second-slib").css("height","100");
                $("#body_new").css("background","url(${pageContext.request.contextPath}/assets/pivas/images/main_bg.png) #f5f5f5 left repeat-y");
                $(".path").css("margin","20px 0 15px 217px");
			}else{
				$("#mainFrame").css("width",$(window).width()-240).css("height",$(window).height()-130);
				$(".left-menu").css("height",$(window).height()-80);
				$(".dl-second-slib").css("height","100");
                $("#body_new").css("background","#f5f5f5");
                $(".path").css("margin","20px 0 15px 217px");
			}
			$(".left-menu").show();
		}else{
			if(window.navigator.userAgent.indexOf("Firefox") >= 0){
				$("#mainFrame").css("width","98%");
				$("#mainFrame").css("height","50%");
                $("#body_new").css("background","#f5f5f5");
                $(".path").css("margin","20px 0 15px 5px");
			}else {
				$("#mainFrame").css("width","98%");
                $("#body_new").css("background","#f5f5f5");
                $(".path").css("margin","20px 0 15px 5px");
			}
			$(".left-menu").hide();
		}
	}
	
	$(function() {
		$(window).resize(function(){
			onloadwin();
		});
		
		onloadwin();
		initPwdWind();

		menuShowSub('menu_1');
		menuShowPage('1','menu_1_1_1');
		
		$(".dl-second-slib").bind("click",function(){
			if(parseFloat($(".left-menu").css("width"))>19){
				$(".left-menu").css("width","10");
				$(".dl-second-slib").css("left","0").css("background","url('${pageContext.request.contextPath}/assets/pivas/images/left-slib.gif') no-repeat -6px center transparent");
				$("#mainFrame").css("width","98%");
                $("#body_new").css("background","#f5f5f5");
                $(".path").css("margin","20px 0 15px 5px");
				$(".left-menu").hide();
				//$("#mainFrame").css("height",$(window).height()-80);
			}else{
				$(".left-menu").css("width","208");
				$(".dl-second-slib").css("left","204px").css("background","url('${pageContext.request.contextPath}/assets/pivas/images/left-slib.gif') no-repeat 0px center transparent");
				$("#mainFrame").css("width",$(window).width()-260);
                $("#body_new").css("background","url(${pageContext.request.contextPath}/assets/pivas/images/main_bg.png) #f5f5f5 left repeat-y");
                $(".path").css("margin","20px 0 15px 217px");
				$(".left-menu").show();
			}
		});

		var accountID = $("#accID");
		var empCodeNu = $("#empCodeID");
		var accountName = $("#naID");
		var phoneInfo = $(" #phoneID");
		var faxInfo = $("#faxID");
		var editUnId = $("#unitId_update");
		var editUnName = $("#unitName");
		var emailInfo = $("#email");
		var addressInfo = $("#addressID");
		var descriptionInfo = $(" #descriptionID");
		var allFields = $([]).add(accountID).add(empCodeNu).add(accountName)
				.add(phoneInfo).add(faxInfo).add(emailInfo).add(addressInfo)
				.add(descriptionInfo).add(editUnId).add(editUnName);
		
		//修改用户弹出框初始化
		$( "#upUserDataID" ).dialog({
			autoOpen: false,
			height: 360,
			width: 460,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );
					var unitID = $("#unId_add").val();
					var bValid = true;
					allFields.removeClass("ui-state-error");

					//<spring:message code='user.zhanghao'/>
					bValid = bValid && checkLength(accountID, 1, 32);
					bValid = bValid && checkRegexp(accountID, /^([0-9a-zA-Z_])+$/);
					
					bValid = bValid && checkLength(accountName, 1, 32);
					//电话
					bValid = bValid && checkLength(phoneInfo, 0, 32);
					bValid = bValid && checkRegexp(phoneInfo,/^([0-9-]){0,32}$/);
					
					
					//建筑
					if(!bValid)
					return;	
					if(editUnId.val() == ""){
						$("#unitName").addClass( "ui-state-error" );
						$("#unitName").focus();
						bValid = false;
					}
					
					
					//邮箱
					//bValid = bValid && checkLength(emailInfo, 1, 128);
					bValid = bValid
					&& checkRegexp(emailInfo,sdfun.constant.reg.email);

					//<spring:message code='user.beizhu'/>
					bValid = bValid && checkLength(descriptionInfo, 0,
									256);
					if ( bValid ) {
						$('#upUserDataFormID').submit();
					}
				},
				"<spring:message code='common.cancel'/>": function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				allFields.val( "" ).removeClass( "ui-state-error" );
				
			}
		});
		

		$('#upUserDataFormID').ajaxForm({
			 dataType: "json",
	            success : function(data) {
	            	//修改成功，关闭新增弹出框
	            	if(data.success || data.code == '<%=ExceptionCodeConstants.RECORD_DELETE%>') {
	            		$('#upUserDataID').dialog('close');
			    		}
	            	
	            	//不管成功还是失败，刷新列表
	            	$(".pReload").click();
	            	
                    layer.alert(data.description, {'title': '提示', icon: 0});
	             },
	            error : function() {
                    layer.alert('<spring:message code="common.op.error"/>', {'title': '提示', icon: 0});
	            },
	    		complete : function(response, status) {
	            
	    		}
	    	});
	});
	function initUserInfo(){
		var ids= ['<spring:escapeBody htmlEscape="true"><shiro:principal property="userId"/></spring:escapeBody>'];
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/sys/user/initUpdateUser',
			dataType : 'json',
			cache : false,
			data : [ {
				name : 'userId',
				value : ids
			} ],
			success : function(data) {
				if(data.success == false){
            		$(".pReload").click();
                    layer.alert(data.description, {'title': '提示', icon: 0});
                	return;
            	}
				else{
            		//将返回的值设置到修改页面的控件中
            		$('#upUserDataID').find(':input').each(function() {
            			//调用解码方法后再设置value
	            		$(this).val(sdfun.fn.htmlDecode(data[this.name]));
	            	}).end().dialog('open');
            		
            		//清楚修改页面错误提示信息
	           	$('#upUserDataID').removeClass( "ui-state-error" );
            	}
				$("#upUserDataID" ).dialog( "open" );
			},
			error : function() {
			}
		});
	}
	
	function menuShowSub(menuid){
		$(".top-menu ul li").each(function(){
			$(this).removeClass("selected");
        });
		$("#"+menuid).addClass("selected");
		
		$(".menu2").each(function(){
			if($(this).attr("name")==menuid){
				$(this).show();
			}else{
				$(this).hide();
			}
		})
		
		var firstMenu= $("#"+menuid+"_1").children().children("span").eq(1).text();
		if( firstMenu =="任务管理"){
			var totalMenuHeight=$(".left-menu").height();
			var topMenuHeight=$("#menu_1_1").outerHeight(true)+$("#menu_1_2").outerHeight(true);
			$("#topMenu").height(topMenuHeight);
			$("#bingqu").height(totalMenuHeight-topMenuHeight-$("#bottomMenuTitle").outerHeight(true)-80);
			$("#topMenu").css("overflow-y","hidden");
			$("#bottomMenu").show();
		}else{
			$("#topMenu").height("100%");
			$("#topMenu").css("overflow-y","auto");
			$("#bottomMenu").hide();
		}
		
	}

	function menuShowPage(sysid,menuId){

		$(".menu3").removeClass("selected");
		var obj = $("#"+menuId);
		$(obj).addClass("selected");
		$("#aMenuPath").html($(obj).attr("path"));
		var url = $(obj).attr("url");
		var text = $(obj).text();
		if(startsWith(url, "fun:")){
			 var funName = eval(url.replace("fun:",""));
			 new funName();
			 return ;
		}
		<%--if(startsWith(url, "schedule/")){--%>
			 <%--$("#mainFrame").attr("src","http://${serverName}:${httpPort}/"+url);--%>
			 <%--return ;--%>
		<%--}--%>
		if(url && url!=undefined && url!="" ){
			
			//加载病区list
			loadInpatient(text);
			
			if(url.substring(0,4)=="http"){
				$("#mainFrame").attr("src",url);
			}else{
				$("#mainFrame").attr("src","${pageContext.request.contextPath}/"+url);
				
				jspURL = "${pageContext.request.contextPath}/"+url;
			}
			//resizeMainFrame();
			//$("#mainFrame").attr("src","http://127.0.0.1:8080/schedule/user/edit"); 
		}else{
			$("#mainFrame").attr("src","${pageContext.request.contextPath}/error/404");
		}
	}

	function loadInpatient(value){
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/mainMenu/init',
			dataType : 'html',
			cache : false,
			//async : true,
			data : {"menu" : value},
			success : function(data) {
				$("#bingqu").html(data);
			},
			error : function(data){
                layer.alert("获取病区信息错误", {'title': '提示', icon: 0});
			}
		});
		
	} 
	
	function changepass(){
		$("#oldPwd" ).val('');

		$('#updatePassword').dialog( 'open' );
	}

    function getInpatientInfo(){
        var i = 0;
        var inpatientString = new Array();
        $("#bingqu").find("input").each(function(e){
            var flag = $(this).is(':checked');
            if(flag){
                var value = $(this).attr("data-value");
                var state = $(this).attr("data-state");
                if( state != 1){
                    inpatientString.push(value);
                }
                i++;
            }
        });
        if($("#bingqu").find("input").length == 0){
            inpatientTotalString = "";
        }else{
            if(i == 0){
                inpatientTotalString = "-1";
            }else{
                if(inpatientString.toString().indexOf("-1") != "-1"){
                    inpatientTotalString = "";
                }else{
                    inpatientTotalString = inpatientString.toString();
                }
            }
        }


        return inpatientTotalString;
    }
	
	function openLogout(){
        <%--
        layer.confirm('您确定要退出登录吗？', {
            btn: ['确定', '取消'], icon: 3, title: '提示'
        }, function () {
            window.location.href='${pageContext.request.contextPath}/logout';
        });
		--%>
        if(confirm("您确定要退出登录吗？")){
            window.location.href='${pageContext.request.contextPath}/logout';
        }
	}


	function logout1(){
		window.location.href='${pageContext.request.contextPath}/logout';
	}
	
	function initPwdWind(){
		var oldPwd = $( "#oldPwd" );
		var newPwd = $("#newPwd");
		var newPwd1 = $("#newPwd1");
		var	allFields = $( [] ).add(newPwd).add(newPwd1);
		$( "#updatePassword" ).dialog({
			autoOpen: false,
			height: 260,
			width: 500,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					if(!oldPwd.val()){
                        layer.alert("<spring:message code='user.yuanmimaInput'/>", {'title': '操作提示', icon: 0});
				     	return;
					}
						var bValid = true;
						allFields.removeClass( "ui-state-error" );
						bValid = bValid && checkLength(newPwd, '${minLength}', '${maxLength}');
						bValid = bValid && checkLength(newPwd1, '${minLength}', '${maxLength}');
						if(bValid&&newPwd.val()!=newPwd1.val()){
                            layer.alert("<spring:message code='user.mimabuyizhi'/>", {'title': '操作提示', icon: 0});
			            	return;
						}
						if ( bValid ) {
							$('#updatePasswordForm').submit();
						}
				},
				"<spring:message code='common.cancel'/>": function() {
					$(this).dialog("close");
				}
			},
			close: function() {
				allFields.val( "" ).removeClass( "ui-state-error" );
				
			}
		});
		
		$('#updatePasswordForm').ajaxForm({
		   	dataType: "json",
            success : function(data) {
            	//更新成功，关闭新增弹出框
            	if(data.success) {
	            	$('#updatePassword').dialog('close');
                    layer.alert('<spring:message code="user.mimaxiugaiSuccess"/>', {'title': '修改密码', icon: 1}, function (index) {
                        layer.close(index);
                        window.location.href='${pageContext.request.contextPath}/logout';
                    });
		    	}else{
                    layer.alert(data.description, {'title': '操作提示', icon: 0});
		    	}
             },
            error : function() {
                layer.alert('<spring:message code="common.op.error"/>', {'title': '修改密码', icon: 0});
            },
    		complete : function(response, status) {
            
    		}
    	});
    	
    	//密码长度
		var minLength = '${minLength}';
		var maxLength = '${maxLength}';
		var pswdMsg = "<spring:message code='user.mimajiaoyan'/>".replace('a', minLength).replace('b', maxLength);
		$("#newPwd").attr("title",pswdMsg);
	}
	
</script>


<script type="text/javascript">

	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);

	$(function() {
		$( document ).tooltip({
			track: true,
			focusin: false
		});
		if($.browser.msie) {
			try {
				var v = parseInt($.browser.version);
				$text = $('#search div.search_column input:text:not(.ui-combobox-input)');
				var w = $text.first().width();
				$text.width(w + 1);
			} catch (e) {
			}
		}
	});
	
	function message(config) {
		var buttons = {};
		var data = config.data;
		var timeout = false;
		if(sessionTimeout(data) == true) {
			return;
		}
		if(config.showConfirm !== false) {
			var confirmText = '<spring:message code="common.confirm"/>';
			if(config.confirmText){
				confirmText = config.confirmText;
			}
			buttons[confirmText] = function() {
				$(this).dialog('close');
				if(typeof config.confirm === 'function') {
					config.confirm();
				}
			}
		}
		
		if(config.showCancel) {
			var cancelText = '<spring:message code="common.cancel"/>';
			if(config.cancelText){
				cancelText = config.cancelText;
			}
			buttons[cancelText] = function() {
				$(this).dialog('close');
				if(typeof config.cancel === 'function') {
					config.cancel();
				}
			}
		}
		
		var $msg = $('#msgDialog');
		var html = config.html;
		if(data) {
			html = '';
			if(!data.success) {
				var showAll = false;
				var desc = data.description || '系统异常，请联系管理员';
				var msg = data.msg || '系统异常';
				var solution = data.solution || '请联系管理员';
				html += '<table style="width:100%;margin:0px;padding:0px;">';
				
				if(showAll){
					html += '<tr>';
					html += '<td align="right" nowrap="nowrap" style="white-space:nowrap;vertical-align:top;">错误信息：</td><td align="left">'+msg+'</td>';
					html += '</tr>';
				}
				
				html += '<tr>';
				if(showAll){
					html += '<td align="right" nowrap="nowrap" style="white-space:nowrap;vertical-align:top;">错误描述：</td>';
					html += '<td align="left">'+desc+'</td>';
				}else{
					html += '<td align="center">'+desc+'</td>';
				}
				
				html += '</tr>';
				
				if(showAll){
					html += '<tr>';
					html += '<td align="right" nowrap="nowrap" style="white-space:nowrap;vertical-align:top;">解决方案：</td><td align="left">'+solution+'</td>';
					html += '</tr>';
				}
				
				html += '</table>';
			} else {
				html = data.description;
			}
		}
		
		$msg.dialog({
			autoOpen: !!config.autoOpen,
			height: 'auto',
			minHeight: 'none',
			width: config.width || 300,
			modal: config.modal !== false,
			resizable: config.resizable === true,
			buttons: buttons,
			close: config.close || function() {}
		}).html(html);
		
		try{
			$('#msgDialog').dialog("close");
		}catch(e){
			
		}
		
		$msg.dialog('open');
		var $p = $msg.parent(),
			$w = $(window);
		$p.css({left: ($w.width() - $p.width()) / 2 + $w.scrollLeft(), top: ($w.height() - $p.height()) / 2 + $w.scrollTop()});
		
		$msg.prev().find('.ui-dialog-title').text(config.title || '<spring:message code="common.op.t"/>');
	}
	
	function reLogin(){
		sdfun.fn.dialog.hide();
		top.location.href='${pageContext.request.contextPath}/reLogin';
	}
	
	function sessionTimeout(data) {
		if(data && data.success === false && data.msg && data.solution && data.code === '<%=ExceptionCodeConstants.SESSION_TIMEOUT%>') {
			reLogin();
			return true;
		}
	}
	
	$(function() {
		setDialogDivSize();
	});
	
	$(window).bind('resize', function() {
		setDialogDivSize();
	});
	
	function setDialogDivSize() {
		$('#dialogDiv').css(getBodySize()).find('div').css({left: $(window).width() / 2, top: $(window).height() / 2});
	}
	
	function getBodySize() {
		var w = window, d = document, width, height, scrollHeight, offsetHeight, scrollWidth, offsetWidth;
		if ($.browser.msie) {
			scrollWidth = Math.max(d.documentElement.scrollWidth, d.body.scrollWidth);
			offsetWidth = Math.max(d.documentElement.offsetWidth, d.body.offsetWidth);
			scrollHeight = Math.max(d.documentElement.scrollHeight, d.body.scrollHeight);
			offsetHeight = Math.max(d.documentElement.offsetHeight, d.body.offsetHeight);
			if (scrollWidth < offsetWidth) {
				width = $(w).width();
			} else {
				width = scrollWidth;
			}
			if (scrollHeight < offsetHeight) {
				height = $(w).height();
			} else {
				height = scrollHeight;
			}
		} else {
			width = $(d).width();
			height = $(d).height();
		}
		return {height:height,width:width};
	}
	
	var sdfunLoadingCount = 0;
	
	$.ajaxSetup({
		cache: false,
		type: 'POST',
		dataType: 'json'
	});
	
	$(document)
	.ajaxStart(function() {

	})

	.ajaxSend(function(event, xhr, options) {
		if(options.showDialog !== false) {
			sdfunLoadingCount++;
			sdfun.fn.dialog.show();
		}
	})

	.ajaxSuccess(function(event, status, settings) {
		if(status && status.responseText){
			var data = toJsonData(status.responseText);
			if(sessionTimeout(data) == true){
				return;
			}
		}
	})

	.ajaxError(function(event, status, settings, errorThrown) {
		sdfunLoadingCount = 0;
		sdfun.fn.dialog.hide();
        layer.alert('<spring:message code="common.op.error"/>', {'title': '提示', icon: 0});
	})

	.ajaxComplete(function(XMLHttpRequest, status) {
		if(sdfunLoadingCount > 0){
			sdfunLoadingCount--;
		}
		
		if(sdfunLoadingCount == 0){
			sdfun.fn.dialog.hide();
		}
	})

	.ajaxStop(function() {

	});
	
	$(document).bind("keydown",function () {
        var activeElement = document.activeElement || event.currentTarget;
        
		if(activeElement.tagName.toLowerCase() === 'input'
            && activeElement.type === 'password'){
			return true;
		}
      
        var isInputOrTextArea = activeElement.tagName.toLowerCase() === 'input' 
                && activeElement.type === 'text' || activeElement.tagName.toLowerCase() === 'textarea';
        var keyCode = event.keyCode;
        
        if((!isInputOrTextArea || activeElement.readOnly) && keyCode === 8) {
                return false;
        }
	});
	
	$(function() {
		if(!top.isTop) {
			top.isTop = true;
			var h = $(document.body).css('overflow-y','auto').height();
			if(h < $(window).height()) {
				h = $(window).height();
			}
			$(top.document.body).find('iframe').css({'height':h});
		}
		
	});
	</script>
	
</head>
<body id="body_new"  onload="onloadwin();">
<div class="top-div">
	<div class="piva-logo">
			PIVAS管理系统
	</div>
    <div class="top-menu">
        <ul class="menu-items">
        	<c:forEach items="${subSysList}" var="sysRow" varStatus="status1" >
        		<li id="menu_${status1.count}" onclick="menuShowSub('menu_${status1.count}')" <c:if test="${status1.index==0}">class="selected" </c:if> >
					<span class="lt-menu-icon"><img src="${pageContext.request.contextPath}/assets/pivas/images/li${status1.count}.png"></span>
					<a style="<c:if test="${status1.index==0}">cursor: default;</c:if>" >${sysRow.sysName}</a>
				</li>
            </c:forEach>

        </ul>
		<div class="user-tool">
			<div class="top_right">
				<div class="top_right_two">
					<span id="dynTime"></span>
				</div>
			</div>
			<div class="top_right">
				<div class="top_right_one">
					<div id="menu_8" onclick="initUserInfo()"><span><spring:escapeBody htmlEscape="true"><shiro:principal property="name"/></spring:escapeBody></span>&nbsp;&nbsp;欢迎登录！</div>
				</div>
				<div class="top_right_one" id="menu_9" onclick="changepass()">
					<a style="">修改密码</a>
				</div>
				<div class="top_right_one" id="menu_10"  onclick="openLogout()">
					<a style="">退出</a>
				</div>
			</div>
		</div>
    </div>

</div>

<div class="main-div" style="width:100%;top:65px;position: absolute;">
	
    <div class="left-menu">
		<div class="user-info-menu">
		</div>
        <div style="height:100%;overflow-y: auto;" id="topMenu">
        	<c:forEach items="${subSysList}" var="sysRow" varStatus="status1" >
        		<c:set var="index2" value="0" />
	        	<c:forEach items="${priList}" var="priRow2" varStatus="status2" >
	        		<c:if test="${priRow2.parentId==sysRow.sysCode}" >
		        		<c:set var="index2" value="${index2+1}" /> 
			       		<div id="menu_${status1.count}_${index2}" name="menu_${status1.count}" class="menu-block config menu2" >
				        	<c:choose>
   								<c:when test="${index2 == 1}">
   									<div class="menu_div hover" onclick="showMenu(this)">
					       				<span style="display:none">down</span>
					       	 			<span style="font-family: 微软雅黑;">${priRow2.name}</span>
					       				<a style="margin-left: 30px;text-decoration:none;" >
					       					<img width="15px" height="15px" src="${pageContext.request.contextPath}/assets/pivas/images/left_button_open.png">
					       				</a>
					       			</div>
					       			<ul>
					       				<c:set var="index3" value="0" />
					       				<c:forEach items="${priList}" var="priRow3" varStatus="status3" >
					       					<c:if test="${priRow3.parentId==priRow2.privilegeId}" >
					       						<c:set var="index3" value="${index3+1}" /> 
					       						<li class="menu3" id="menu_${status1.count}_${index2}_${index3}" onclick="menuShowPage('${status1.count}','menu_${status1.count}_${index2}_${index3}')" path="当前位置：<span>${sysRow.sysName}</span> > <span>${priRow2.name}</span> ><span> ${priRow3.name}</span>" url="${priRow3.url}" ><a>${priRow3.name}</a></li>
					       					</c:if>   		
					       				</c:forEach>
					        		</ul>
   								</c:when>
   								<c:otherwise>
	   								<div class="menu_div" onclick="showMenu(this)"> 
					       				<span style="display:none">up</span>
					       	 			<span style="font-family: 微软雅黑;">${priRow2.name}</span>
					       				<a style="margin-left: 30px;text-decoration:none;" >
					       					<img width="15px" height="15px" src="${pageContext.request.contextPath}/assets/pivas/images/left_button_close.png">
					       				</a>
					       			</div>
					       			<ul style="display:none">
					       				<c:set var="index3" value="0" />
					       				<c:forEach items="${priList}" var="priRow3" varStatus="status3" >
					       					<c:if test="${priRow3.parentId==priRow2.privilegeId}" >
					       						<c:set var="index3" value="${index3+1}" /> 
					       						<li class="menu3" id="menu_${status1.count}_${index2}_${index3}" onclick="menuShowPage('${status1.count}','menu_${status1.count}_${index2}_${index3}')" path="当前位置：<span>${sysRow.sysName}</span> > <span>${priRow2.name}</span> ><span> ${priRow3.name}</span>" url="${priRow3.url}" ><a>${priRow3.name}</a></li>
					       					</c:if>   		
					       				</c:forEach>
					        		</ul>
   								</c:otherwise>
							</c:choose> 
			       		</div>
	        		</c:if>
	        	</c:forEach>
         	</c:forEach>
		</div>
		<div id="bottomMenu" style="border-radius:5px 0 0 5px;" >
         		<div id="bottomMenuTitle" class="menu_div_new ui-tabs-panel">
         			<span style="vertical-align:middle;color:white;">病区筛选</span>
         		</div>
         		<div id="bingqu" style="height:40%;overflow-y: auto;background:#cccccc" ></div>
         </div>

    </div>
    <div class="dl-second-slib" style="height:100px"></div>
    
    <div>
    <div class="path"><a id="aMenuPath" style="padding-left: 10px;" href="javascript:backMain()"></a></div><!-- ${NAVIGATION_NAME} -->
    <iframe id="mainFrame" style="margin-left:10px;" frameborder="no" border="0" marginwidth="0" marginheight="0">
    </iframe>
    </div>

	<div id="updatePassword" title="修改密码" align="center" style="display: none;">
			<form id="updatePasswordForm" action="${pageContext.request.contextPath}/sys/user/updatePassword" method="post">
				<div class="popup">
					<div class="row">
						<div class="column">
							<label class="tit"><spring:message code='user.yuanmima'/></label>
							<input type="password" name="oldPassword" id="oldPwd"  title="<spring:message code='user.yuanmimatip'/>" />
							<span class="mand">*</span>
						</div>
					</div>
					<div class="row">
						<div class="column">
							<label class="tit"><spring:message code='user.xinmima'/></label>
							<input type="password" name="password" id="newPwd" maxlength="${maxLength}" title="<spring:message code='user.mimajiaoyan'/>" />
							<span class="mand">*</span>
						</div>
					</div>
					<div class="row">
						<div class="column">
							<label class="tit"><spring:message code='user.querenmima'/></label>
							<input type="password" name="newPwd1" id="newPwd1" maxlength="${maxLength}" title="<spring:message code='user.querenmimatishi'/>" />
							<span class="mand">*</span>
						</div>
					</div>
				</div>
			</form>
	</div>
	
	
	<!-- 修改 -->
	<div id="upUserDataID" title="修改用户" align="center" style="display: none;">
		<form id="upUserDataFormID" action="${pageContext.request.contextPath}/sys/user/updateUser" method="post">
		<input type="hidden" id="userUpId" name="userId" />
		<div class="popup">
		<div class="row">
			<div class="column">
				<label class="tit"><spring:message code='user.zhanghao'/></label>
					<input type="text" name="account" id="accID"  maxlength="32" title="<spring:message code='common.op.remind1'/>" disabled="disabled"/>  
				<span class="mand">*</span>
			</div>
			<div class="column">
				<label class="tit"><spring:message code='user.yonghuming'/></label>
				<input type="text" name="name" id="naID" value=""  maxlength="32" title="<spring:message code='common.op.remind2'/>" />
				<span class="mand">*</span>
			</div>
		</div>
		<div class="row">
			<div class="column">
				<label class="tit"><spring:message code='user.dianhua'/></label>
				<input type="text" name="telephone" id="phoneID" value=""  maxlength="32" title="<spring:message code='user.dianhuajiaoyan'/>" />
				<span class="mand"></span>
			</div>
			<div class="column">
				<label class="tit"><spring:message code='user.youxiang'/></label>
				<input type="text" name="email" id="email" value=""  maxlength="128" title="<spring:message code='user.youxiangjiaoyan'/>" />
			</div>
		</div>
		<div class="row">
			<div class="column">
				<label class="tit"><spring:message code='common.remark'/></label>
				<textarea name="description" id="descriptionID" maxlength="256" title="<spring:message code='common.op.remind6'/>"></textarea>
				<span class="mand">*</span>
			</div>
		</div>

		</div>
		</form>
	</div>
	
	
    
</div>

	<div id="msgDialog" title="<spring:message code="common.op.t"/>" align="center" style="display: none;margin-top:8px;"></div>
	
	<div id="dialogDiv" style="display:none;position:absolute;left:0px;top:0px;;z-index:99999;background-color:#000;filter:alpha(opacity=50);-moz-opacity:0.5;opacity:0.5;">
		<div style="position:absolute;width:60px;height:60px;margin-left:-30px;margin-top:-30px;">
			<img src="${pageContext.request.contextPath}/assets/common/images/loader.gif" width="100%" height="100%">
		</div>
	</div>

<script>	

var inpatientTotalString = "";

function checkBox(e,v){

    var box = $(e).find("input");
    var checked = box.is(':checked');
    var firstMenu = $("#bingqu").find("input:first");

    if(v == 0){
        if(checked){
            $("#bingqu").find("input").each(function(e){
                $(this).attr("checked",false)
            });
        }else{
            $("#bingqu").find("input").each(function(e){
                $(this).attr("checked",true)
            });
        }
    }else{
        if(checked){
            box.attr("checked",false);
            var state = box.attr("data-state");
            var level = box.attr("data-level");
            if(state == 1){
                $(e).next("ul").find("input").each(function(e){
                    $(this).attr("checked",false)
                });
            }else if(state == 0){
                if(level == 2){
                    $(e).closest("ul").parent("li").children("a").find("input").attr("checked",false);
                }
            }
            if($("#inpatient_area_id").is(':checked')){
                $("#inpatient_area_id").attr("checked",false);
            }
        }else{
            box.attr("checked",true);

            var state = box.attr("data-state");
            var level = box.attr("data-level");
            if(state == 1){
                $(e).next("ul").find("input").each(function(e){
                    $(this).attr("checked",true)
                });
            }else if(state == 0){
                if(level == 2){
                    var liLength=$(e).closest("ul").children("li").length;
                    var checkedlength = $(e).closest("ul").find("input:checked").length;
                    if(liLength == checkedlength){
                        $(e).closest("ul").parent("li").children("a").find("input").attr("checked",true);
                    }
                }
            }

            var allInput = $("#bingqu").find("input").length;
            var allCheckInput = $("#bingqu").find("input:input:checked").length;
            if((allInput-1) == allCheckInput){
                $("#inpatient_area_id").attr("checked",true);
            }
        }
    }

    /* loadJSP(inpatientString); */

    // 医嘱列表
    if (jspURL.indexOf("/doctorAdvice/main") != -1) {
        $("#mainFrame")[0].contentWindow.qry();
    }

    //审方核对
    if (jspURL.indexOf("/doctorAdvice/yzPCheck") != -1) {
        $("#mainFrame")[0].contentWindow.queryYZ();
    }

    // 药师审方2
    if (jspURL.indexOf("/doctorAdvice/yzPCheck2") != -1) {
        $("#mainFrame")[0].contentWindow.queryYZ();
    }

    // 批次优化
    if (jspURL.indexOf("/doctorAdvice/batchOptn") != -1 || jspURL.indexOf("/doctorAdvice/batchManage") != -1) {
        $("#mainFrame")[0].contentWindow.qry__1();
    }

    //打印瓶签
    if (jspURL.indexOf("/printLabel/init") != -1) {
        $("#mainFrame")[0].contentWindow.getAllMedicament();
    }

    // 瓶签扫描
    if (jspURL.indexOf("/scans/init") != -1) {
        $("#mainFrame")[0].contentWindow.loadList();
        $("#mainFrame")[0].contentWindow.loadSmResult();
        $("#mainFrame")[0].contentWindow.timedCount();
    }
}

function showMenu(docObj){
	var v=$(docObj).children().first().text();
	if(v == 'down'){
	    $(docObj).removeClass("hover");
		$(docObj).children().last().children("img").attr("src","${pageContext.request.contextPath}/assets/pivas/images/left_button_close.png");
		$(docObj).next().hide();
		$(docObj).children().first().text("up");
	}else if(v=='up'){
        $(docObj).addClass("hover");
		$(docObj).children().last().children("img").attr("src","${pageContext.request.contextPath}/assets/pivas/images/left_button_open.png");
		$(docObj).next().show();
		$(docObj).children().first().text("down");
	}
	
	var firstMenu= $(docObj).children("span").eq(1).text();
	if( firstMenu =="任务管理" ){
		var totalMenuHeight=$(".left-menu").height();
		var topMenuHeight=$("#menu_1_1").outerHeight(true)+$("#menu_1_2").outerHeight(true);
		$("#topMenu").height(topMenuHeight);
		$("#bingqu").height(totalMenuHeight-topMenuHeight-$("#bottomMenuTitle").outerHeight(true));
	}else{
		$("#topMenu").height("100%");
		$("#topMenu").css("overflow-y","auto");
	}
}
function tick()
{
    var today;
    today = new Date();
    $("#dynTime").html(showLocale(today));
};
setInterval("tick()", 1000);
</script>
</body>
</html>