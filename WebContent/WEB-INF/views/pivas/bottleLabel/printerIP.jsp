<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../../common/common.jsp" %>

<html>
<style>
    a.button2 {
        margin: 0 0 0px;
        padding: 6px 22px;
        background-color: #ebb800;
        color: #ffffff;
        display: inline-block;
        cursor: pointer;
        margin-left: 1px;
        height: 26px;
    }
</style>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/pivas/js/strings.js"></script>
    <script>
        var datatable;
        var qryParam;
        var patt1 = new RegExp("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");

        function editConf(id) {
            var ids = [id];
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/printerIP/getPrinterIPById',
                dataType: 'json',
                cache: false,
                data: [{name: 'gid', value: ids}],
                success: function (data) {
                    if (data.success == false) {

                        layer.alert(data.description, {'title': '操作提示', icon: 0});
                        return;
                    } else {
                        $("#gid").val(data.gid);
                        $("#compName").val(data.compName);
                        $("#ipAddr").val(data.ipAddr);
                        $("#prinName").val(data.prinName);
                    }
                    $("#editView-div").prev().find('.ui-dialog-title').text("修改打印机配置");
                    $("#editView-div").dialog("open");
                },
                error: function () {
                }
            });
        }

        function initDatatable(){
            datatable = $('.datatable').DataTable({
                "dom": '<"toolbar">frtip',
                "searching": false,
                "processing": true,
                "serverSide": true,
                "select": true,
                "ordering": false,
                "pageLength": 20,
                "language": {
                    "url": "${pageContext.request.contextPath}/assets/datatables/cn.json"
                },
                "preDrawCallback": function () {
                    //sublime.showLoadingbar($(".main-content"));
                },
                "drawCallback": function () {
                    //sublime.closeLoadingbar($(".main-content"));
                },
                "ajax": {
                    "url": "${pageContext.request.contextPath}/printerIP/getPrinterList",
                    "type": "post",
                    "data": function (d) {
                        var params = [];
                        if (qryParam) {
                            params = qryParam.concat();
                        }
                        for (var index in params) {
                            d[params[index].name] = params[index].value;
                        }

                        d.rp = d.length;
                        d.page = d.start / d.length + 1;
                    },

                    "dataSrc": function (data) {
                        data.data = data.rawRecords;
                        //data.draw = 1;
                        data.recordsFiltered = data.total;
                        data.recordsTotal = data.total;
                        return data.data;
                    }
                },
                "columns": [
                    {"data": "gid", "bSortable": false},
                    {"data": "compName", "bSortable": false},
                    {"data": "ipAddr", "bSortable": false},
                    {"data": "prinName", "bSortable": false},

                ],
                columnDefs: [
                    {
                        targets: 0,
                        render: function (data, type, row) {
                            return "<input type='checkbox' name='cbs' value='"+data+"'/>";
                        }
                    },
                    {
    					targets: 4,
    					render: function (data, type, row) {
    						var str = "";
                         	<shiro:hasPermission name="PIVAS_BTN_403">
                         		str +=  '&nbsp;<a class="button ui-search-btn" href="javascript:;" onclick="editConf({0});">修改</a>'.format([row.gid])
                    		</shiro:hasPermission>
                            return str;
    					}
                	},
                ],
            });
        }
        $(function () {
            sdfun.fn.trimAll("editView-div");
            var compuName = $("#compName");
            var ipAddr = $("#ipAddr");
            var allFieldsInfo = $([]).add(compuName).add(ipAddr);
            initDatatable();
            $(".datatable > thead").on("change","input:checkbox",function(){
                var selected = $(this).attr("checked") == "checked";
                $(".datatable > tbody").find("input:checkbox").each(function (i, v) {
                    if (selected) {
                        $(v).attr("checked", 'checked');
                    } else {
                        $(v).removeAttr("checked");
                    }
                });
            });

            //查询列表
//            queryList();

            //点击新增/更新 后弹出对话框
            $("#editView-div").dialog({
                autoOpen: false,
                height: 220,
                width: 500,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        allFieldsInfo.removeClass("ui-state-error");
                        var compNameStr = $("#compName").val();
                        var ipAddrStr = $("#ipAddr").val().trim();
                        var prinNameStr = $("#prinName").val();

                        if (compNameStr.length == 0) {
                            layer.alert("请输入主机名", {'title': '操作提示', icon: 0});
                            return;
                        }

                        if (ipAddrStr.length == 0) {
                            layer.alert("请输入IP地址", {'title': '操作提示', icon: 0});
                            return;
                        }

                        if (!patt1.test(ipAddrStr)) {
                            layer.alert("请输入正确格式的IP地址", {'title': '操作提示', icon: 0});
                            return;
                        }

                        if (prinNameStr.length == 0) {
                            layer.alert("请选择打印机", {'title': '操作提示', icon: 0});
                            return;
                        }

                        var url = "${pageContext.request.contextPath}/printerIP/addPrinter";
                        var params = {
                            "compName": $("#compName").val(),
                            "ipAddr": $("#ipAddr").val().trim(),
                            "prinName": $("#prinName").val(),
                        };

                        if ($("#gid").val() && $("#gid").val() != "") {
                            url = "${pageContext.request.contextPath}/printerIP/modifyPrinter";
                            params["gid"] = $("#gid").val();
                        }

                        $.ajax({
                            type: 'POST',
                            url: url,
                            dataType: 'json',
                            cache: false,
                            data: params,
                            success: function (data) {
                                if (data.success == true) {
                                    $("#editView-div").dialog("close");
                                    queryList();
                                } else {
                                    layer.alert(data.description, {'title': '操作提示', icon: 0});
                                }
                            },
                            error: function () {
                            }
                        });
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        allFieldsInfo.val("").removeClass("ui-state-error");
                        $("#editView-div").dialog("close");
                    }
                },
                close: function () {
                    closeDeal();
                    allFieldsInfo.val("").removeClass("ui-state-error");
                }
            });

            //新增打印配置项
            $("#addDataDicBtn").bind("click", function () {
                $("#editView-div").prev().find('.ui-dialog-title').text("添加打印机配置");
                $("#editView-div").dialog("open");
            });

            //删除打印配置项
            $("#deleteDataDicBtn").bind("click", function () {
                var ids = getSelectedRow();
                if (ids && ids.length > 0) {
                    layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                        btn: ['确定', '取消'], icon: 3, title: '提示'
                    }, function () {
                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/printerIP/delPrinter',
                            dataType: 'json',
                            cache: false,
                            data: [{name: 'ids', value: ids}],
                            success: function (data) {
                                queryList();
                                layer.alert(data.description, {'title': '操作提示', icon: 0});
                            },
                            error: function () {
                                layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
                            }
                        });
                    });

                } else {
                    layer.alert('<spring:message code="common.plzSelectOneRecourd"/>', {'title': '操作提示', icon: 0});
                }
            });

        });

        function getSelectedRow() {
            var arr = new Array(0);
            $(".datatable > tbody").find("input:checked").each(function (i, v) {
                arr.push($(v).val())
            });
            return arr;
        }
        function queryList(param) {
            <%--$('#flexiGridId').flexOptions({--%>
                <%--newp: 1,--%>
                <%--extParam: param || [],--%>
                <%--url: "${pageContext.request.contextPath}/printerIP/getPrinterList"--%>
            <%--}).flexReload();--%>

            qryParam = param;
            datatable.ajax.reload();
        }

        function closeDeal() {
            $("#editView-div").find("input").each(function () {
                $(this).val("");
            });
            $("#editView-div").find("select").each(function () {
                $(this).val("");
            });

        }

    </script>
</head>
<body>

<div class="main-div" style="width:100%">
    <div id="qryView-div">
        <div class="search-div">
            <shiro:hasPermission name="PIVAS_BTN_402">
                <a class="button ui-search-btn ui-btn-bg-green" id="addDataDicBtn"><i class="am-icon-plus"></i><span>新增</span></a>
            </shiro:hasPermission>
            <shiro:hasPermission name="PIVAS_BTN_404">
                <a class="button ui-search-btn ui-btn-bg-red" id="deleteDataDicBtn"><i class="am-icon-trash"></i><span>删除</span></a>
            </shiro:hasPermission>
        </div>
        <div class="tbl">
            <%--<table id="flexiGridId" style="display: block;margin: 0px;"></table>--%>
            <table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
                <thead>
                <tr>
                    <th><input type='checkbox' name='cbs'/></th>
                    <th>主机名</th>
                    <th>IP地址</th>
                    <th>打印机名</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>

<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="修改打印机配置" align="center" style="display: none;">
    <form id="editView-form" action="" method="post">
        <input type="hidden" id="gid" name="gid"/>
        <div class="popup">
            <div class="row">
                <div class="column">
                    <label class="tit">主机名</label>
                    <input type="text" class="edit" name="compName" id="compName" title="请输入主机名"/>
                    <span class="mand">*</span>
                </div>
                <div class="column">
                    <label class="tit">IP地址</label>
                    <input type='text' class="edit" name="ipAddr" id="ipAddr" title="请输入有效IP地址"/>
                    <span class="mand">*</span>
                </div>
                <div class="column">
                    <label class="tit">打印机名称</label>
                    <select id="prinName" width="183px">
                        <option value=""><spring:message code="common.select"/></option>
                        <c:forEach items="${printer}" var="item">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                    <span class="mand">*</span>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>