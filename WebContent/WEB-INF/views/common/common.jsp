<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants" %>
<%@ page import="com.zc.base.common.util.CommonUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="/WEB-INF/tlds/sd.tld" prefix="sd" %>
<%
    String type = CommonUtil.getDataBaseType();
    if (type.equals("mysql")) {
        pageContext.setAttribute("reportPath", "mysql/");
    } else if (type.equals("oracle")) {
        pageContext.setAttribute("reportPath", "oracle/");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 去掉IE版本限制 -->
<!-- <meta http-equiv="X-UA-Compatible" content="IE=8"/> -->
<script type="text/javascript">
    var bLanguage = '${sessionScope.language}' === 'zh' ? 'zh-cn' : 'en';
</script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery-1.8.3.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.qrcode.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/tripledes/tripledes.js"></script>
<script src="${pageContext.request.contextPath}/assets/tripledes/BASE64.js"></script>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/jquery-ui/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css">
<script src="${pageContext.request.contextPath}/assets/jquery-ui/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/pivas/css/style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/sysCss/common/popup.css">
<%--<!-- 自定义的公共JS和样式 -->--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/common/js/util.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/pivas/js/cssjs.js"></script>

<script src="${pageContext.request.contextPath}/assets/common/js/my97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/assets/common/js/print.js"></script>
<script src="${pageContext.request.contextPath}/assets/commjs/commjs.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/commjs/commcss.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/commjs/extcssread.css">
<%--<!-- grid使用的js和样式 -->--%>
<link href="${pageContext.request.contextPath}/assets/grid/css/flexigrid.css" type="text/css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/assets/grid/css/flexigrid2.css" type="text/css" rel="stylesheet"/>
<script src="${pageContext.request.contextPath}/assets/grid/js/jquery.form.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/grid/js/jquery.flexigrid.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/common/js/json2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/pivas/js/comm.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/layer/layer.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/assets/datatables/css/jquery.dataTables.min.css" type="text/css" rel="stylesheet"/>
<script src="${pageContext.request.contextPath}/assets/datatables/js/jquery.dataTables.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/pivas/css/tablesearch.css">
<style type="text/css">
    .ui-dialog {
        font: 12px/1.5 '宋体', tahoma, Srial, helvetica, sans-serif;
        color: #585858;
        -webkit-border-radius: 0px;
    }

    input::-ms-clear {
        display: none;
    }
</style>

<script type="text/javascript">

    <%--flexigrid控制的国际化--%>
    var i18n_flexigrid_queryFail = "<spring:message code='flexigrid.tip.queryFail' />";
    var i18n_flexigrid_recordDisplayFromTo = "<spring:message code='flexigrid.tip.recordDisplayFromTo' />";
    var i18n_flexigrid_loading = "<spring:message code='flexigrid.tip.loading' />";
    var i18n_flexigrid_noRecord = "<spring:message code='flexigrid.tip.noRecord' />";
    var i18n_flexigrid_toFirstPage = "<spring:message code='flexigrid.tip.toFirstPage' />";
    var i18n_flexigrid_toPreviousPage = "<spring:message code='flexigrid.tip.toPreviousPage' />";
    var i18n_flexigrid_currentPage = "<spring:message code='flexigrid.tip.currentPage' />";
    var i18n_flexigrid_totalPage = "<spring:message code='flexigrid.tip.totalPage' />";
    var i18n_flexigrid_toNextPage = "<spring:message code='flexigrid.tip.toNextPage' />";
    var i18n_flexigrid_toLastPage = "<spring:message code='flexigrid.tip.toLastPage' />";
    var i18n_flexigrid_refresh = "<spring:message code='flexigrid.tip.refresh' />";
    var i18n_flexigrid_everyPageCount = "<spring:message code='flexigrid.tip.everyPageCount' />";
    var i18n_flexigrid_quickSearch = "<spring:message code='flexigrid.tip.quickSearch' />";
    var i18n_flexigrid_queryError = "<spring:message code='flexigrid.tip.queryError' />";
    var color_picker_tip = "<spring:message code="colorpicker.tip"/>";

    <%--时间控件国际化--%>
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);

    $(function () {
        <%--设置悬浮提示--%>
        $(document).tooltip({
            track: true,
            focusin: false
        });
        if ($.browser.msie) {
            try {
                var v = parseInt($.browser.version);
                $text = $('#search div.search_column input:text:not(.ui-combobox-input)');
                var w = $text.first().width();
                $text.width(w + 1);
            } catch (e) {
            }
        }
    });

    function getAbsoluteLeft(o) {
        oLeft = o.offsetLeft;
        while (o.offsetParent != null) {
            oParent = o.offsetParent;
            oLeft += oParent.offsetLeft - oParent.scrollLeft;
            o = oParent;
        }
        return oLeft;
    }

    function getAbsoluteTop(o) {
        oTop = o.offsetTop;
        while (o.offsetParent != null) {
            oParent = o.offsetParent;
            oTop += oParent.offsetTop - oParent.scrollTop;
            o = oParent;
        }
        return oTop;
    }

    <%--获取系统管理数据字典值--%>
    function getDictName(categroy, code) {
        var dicName = "";
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/sys/dict/getDictName',
            dataType: 'json',
            cache: false,
            async: false,
            data: {
                category: categroy,
                code: code
            },
            success: function (data) {
                if (data.isSuccess == false) {
                    message({
                        data: data
                    });
                } else {
                    dicName = data;
                }
            }
        });
        return dicName;
    }

    <%--公共提示信息--%>
    function message(config) {
        var buttons = {};
        var data = config.data;
        var timeout = false;
        if (sessionTimeout(data) == true) {
            return;
        }
        if (config.showConfirm !== false) {
            var confirmText = '<spring:message code="common.confirm"/>';
            if (config.confirmText) {
                confirmText = config.confirmText;
            }
            buttons[confirmText] = function () {
                $(this).dialog('close');
                if (typeof config.confirm === 'function') {
                    config.confirm();
                }
            }
        }

        if (config.showCancel) {
            var cancelText = '<spring:message code="common.cancel"/>';
            if (config.cancelText) {
                cancelText = config.cancelText;
            }
            buttons[cancelText] = function () {
                $(this).dialog('close');
                if (typeof config.cancel === 'function') {
                    config.cancel();
                }
            }
        }

        var $msg = $('#msgDialog');
        var html = config.html;
        if (data) {
            html = '';
            if (!data.success) {
                <%--屏蔽错误提示信息--%>
                var showAll = false;
                var desc = data.description || '系统异常，请联系管理员';
                var msg = data.msg || '系统异常';
                var solution = data.solution || '请联系管理员';
                html += '<table style="width:100%;margin:0px;padding:0px;">';

                if (showAll) {
                    html += '<tr>';
                    html += '<td align="right" nowrap="nowrap" style="white-space:nowrap;vertical-align:top;">错误信息：</td><td align="left">' + msg + '</td>';
                    html += '</tr>';
                }

                html += '<tr>';
                if (showAll) {
                    html += '<td align="right" nowrap="nowrap" style="white-space:nowrap;vertical-align:top;">错误描述：</td>';
                    html += '<td align="left">' + desc + '</td>';
                } else {
                    html += '<td align="center"';
                    if (config.fontSize) {
                        html += 'style="font-size:' + config.fontSize + 'px;"';
                    }
                    html += '>' + desc + '</td>';
                }

                html += '</tr>';

                if (showAll) {
                    html += '<tr>';
                    html += '<td align="right" nowrap="nowrap" style="white-space:nowrap;vertical-align:top;">解决方案：</td><td align="left">' + solution + '</td>';
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
            close: config.close || function () {
            }
        }).html(html);

        try {
            <%--增加关闭弹出框的方法，关闭之前的弹出框，防止弹出多个，但是第一个弹出框调用时会报错，所以要捕获异常--%>
            $('#msgDialog').dialog("close");
        } catch (e) {

        }

        $msg.dialog('open');
        var $p = $msg.parent(),
            $w = $(window);
        $p.css({
            left: ($w.width() - $p.width()) / 2 + $w.scrollLeft(),
            top: ($w.height() - $p.height()) / 2 + $w.scrollTop()
        });

        $msg.prev().find('.ui-dialog-title').text(config.title || '<spring:message code="common.op.t"/>');
    }

    <%--超时注销--%>
    function loginOut() {
        top.location.href = '${pageContext.request.contextPath}/logout';
    }

    <%--返回首页--%>
    function returnHome() {
        top.location.href = '${pageContext.request.contextPath}/main';
    }

    <%--注销--%>
    function logout() {
        message({
            html: '<spring:message code="common.warn.logout" />',
            showCancel: true,
            confirm: function () {
                top.location.href = '${pageContext.request.contextPath}/logout';
            }
        });
    }

    <%--重新登录--%>
    function reLogin() {
        sdfun.fn.dialog.hide();
        top.location.href = '${pageContext.request.contextPath}/reLogin';
    }

    function sessionTimeout(data) {
        if (data && data.success === false && data.msg && data.solution && data.code === '<%=ExceptionCodeConstants.SESSION_TIMEOUT%>') {
            reLogin();
            return true;
        }
    }

    $(function () {
        setDialogDivSize();
    });

    $(window).bind('resize', function () {
        setDialogDivSize();
    });

    function setDialogDivSize() {
        $('#dialogDiv').css(getBodySize()).find('div').css({
            left: $(document).width() / 2,
            top: $(window).height() / 2
        });
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
        return {height: height, width: width};
    }

    <%--页面销毁处理--%>
    function sdfunPageDestroy() {
        <%--逐个销毁，防止报错后不销毁后面的--%>
        $.each($('select[autoDestroy]'), function (i, data) {
            try {
                $(data).combobox("destroy");
            } catch (e) {

            }
        });
    }

    <%--正在加载的个数，用于控制loading遮罩层--%>
    var sdfunLoadingCount = 0;

    <%--jquery ajax 全局属性(默认)--%>
    $.ajaxSetup({
        cache: false,
        type: 'POST',
        dataType: 'json'
    });

    <%--jquery ajax 全局事件--%>
    $(document)
        .ajaxStart(function () {

        })

        .ajaxSend(function (event, xhr, options) {
            if (options.showDialog !== false) {
                sdfunLoadingCount++;
                sdfun.fn.dialog.show();
            }
        })

        .ajaxSuccess(function (event, status, settings) {
            if (status && status.responseText) {
                var data = toJsonData(status.responseText);
                if (sessionTimeout(data) == true) {
                    return;
                }
            }
        })

        .ajaxError(function (event, status, settings, errorThrown) {
            sdfunLoadingCount = 0;
            sdfun.fn.dialog.hide();
            layer.alert('<spring:message code="common.op.error"/>', {title:"操作提示",icon: 0});
        })

        .ajaxComplete(function (XMLHttpRequest, status) {
            if (sdfunLoadingCount > 0) {
                sdfunLoadingCount--;
            }

            if (sdfunLoadingCount == 0) {
                sdfun.fn.dialog.hide();
            }
        })

        .ajaxStop(function () {

        });

    <%--解决文本框或者文本域disabled或者readOnly导致浏览--%>
    $(document).bind("keydown", function () {
        var activeElement = document.activeElement || event.currentTarget;

        <%--密码框不过滤--%>
        if (activeElement.tagName.toLowerCase() === 'input'
            && activeElement.type === 'password') {
            return true;
        }

        var isInputOrTextArea = activeElement.tagName.toLowerCase() === 'input'
            && activeElement.type === 'text' || activeElement.tagName.toLowerCase() === 'textarea';
        var keyCode = event.keyCode;

        <%--删除键--%>
        if ((!isInputOrTextArea || activeElement.readOnly) && keyCode === 8) {
            return false;
        }
    });

    function inputNumber(e) {
        if (isNaN(String.fromCharCode(e.keyCode))) {
            e.keyCode = 0;
            if ($.browser.msie) {
                e.returnValue = false;
            } else {
                e.preventDefault();
            }
            return false;
        }
    }

    <%--ip验证--%>
    function validIp(checkIP, existIP) {
        var ipReg = /^(((?!127)([1-9]|[1-9]\d|1\d\d|2[0-1]\d|22[0-3]))\.)(([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.){2}([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;

        <%--验证ip格式--%>
        if (!ipReg.test(checkIP)) {
            return false;
        }
        <%--ip重复验证--%>
        if (existIP && existIP.indexOf(checkIP) != -1) {
            return false;
        }
        return true;
    }

    <%--如果 嵌入的iframe的高度比最外层高 则修改iframe高度--%>
    $(function () {
        if (!top.isTop) {
            top.isTop = true;
            var h = $(document.body).css('overflow-y', 'hidden').height();
            if (h < $(window).height()) {
                h = $(window).height();
            }
            $(top.document.body).find('iframe').css({'height': h});
        }

    });

    function initAddParam(divId) {
        var paramTemp = {};
        var $search = $('#' + divId);
        $search.find("input").each(function () {
            if ($(this).attr("name") && isNotNull($(this).val())) {
                paramTemp[$(this).attr("name")] = $(this).val();
            }
        });
        $search.find("select").each(function () {
            if ($(this).attr("name") && isNotNull($(this).val())) {
                paramTemp[$(this).attr("name")] = $(this).val();
            }
        });
        $search.find("textarea").each(function () {
            if ($(this).attr("name") && isNotNull($(this).val())) {
                paramTemp[$(this).attr("name")] = $(this).val();
            }
        });
        return paramTemp;
    }

    function initUpdParam(divId) {
        var paramTemp = {};
        var $search = $('#' + divId);
        $search.find("input").each(function () {
            if ($(this).attr("name")) {
                paramTemp[$(this).attr("name")] = $(this).val();
            }
        });
        $search.find("select").each(function () {
            if ($(this).attr("name")) {
                paramTemp[$(this).attr("name")] = $(this).val();
            }
        });
        $search.find("textarea").each(function () {
            if ($(this).attr("name")) {
                paramTemp[$(this).attr("name")] = $(this).val();
            }
        });
        return paramTemp;
    }

    function nullDeal(obj, strDefault) {
        if (obj == null || obj == "" || typeof(obj) == "undefined") {
            return strDefault;
        }
        return obj;
    };

    function isNull(obj) {
        if (obj != undefined && ("" + obj) != "" && obj != "null") {
            return false;
        }
        return true;
    }

    function isNotNull(obj) {
        if (obj != undefined && ("" + obj) != "" && obj != "null") {
            return true;
        }
        return false;
    }

    function clearParam(divId) {
        var $search = $('#' + divId);
        $search.find("input").val("");
        $search.find("select").each(function () {
            $(this).find("option:first").attr('selected', 'selected');
        });
        $search.find("textarea").val("");
    }

    String.prototype.startWith = function (str) {
        if (str == null || str == "" || this.length == 0 || str.length > this.length)
            return false;
        if (this.substr(0, str.length) == str)
            return true;
        else
            return false;
        return true;
    }
    String.prototype.endWith = function (str) {
        if (str == null || str == "" || this.length == 0 || str.length > this.length)
            return false;
        if (this.substring(this.length - str.length) == str)
            return true;
        else
            return false;
        return true;
    }
</script>
</head>
<body>
<%--提示信息--%>
<div id="msgDialog" title="<spring:message code="common.op.t"/>" align="center"
     style="display: none;margin-top:8px;"></div>

<%--遮罩--%>
<div id="dialogDiv"
     style="display:none;position:absolute;left:100px;top:0px;;z-index:99999;background-color:#000;filter:alpha(opacity=50);-moz-opacity:0.5;opacity:0.5;">
    <div style="position:absolute;width:60px;height:60px;margin-left:-30px;margin-top:-30px;">
        <img src="${pageContext.request.contextPath}/assets/common/images/loader.gif" width="100%" height="100%">
    </div>
</div>
</body>
</html>