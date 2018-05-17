<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet"/>
<head>
    <script>
        var datatable;
        var qryParam;
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
                    "url": "${pageContext.request.contextPath}/drugslabel/listDrugsLabel",
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
                    {"data": "labelId", "bSortable": false},
                    {"data": "labelName", "bSortable": false},
                    {"data": "labelNo", "bSortable": false},
                    {"data": "isNull", "bSortable": false},
                    {"data": "labelId", "bSortable": false}
                ],
                columnDefs: [

                    {
                        targets: 0,
                        render: function (data, type, row) {
                            return "<input type='checkbox' name='cbs' value='"+data+"'/>";
                        }
                    }, {
                        targets: 3,
                        render: function (data, type, row) {
                            return (row.isNull === 1) ? "是" : "否";
                        }
                    },{
                        targets: 4,
                        "width":"10%",
                        render: function (data, type, row) {
                            var str = "";
                            <shiro:hasPermission name="PIVAS_BTN_207">
                                str += "<a class='button btn-bg-green' href='javascript:updMedicLabel(" + data + ");'><i class=\"am-icon-edit\"></i><span>&nbsp;修改</span></a>";
                            </shiro:hasPermission>
                            return str;
                        }
                    }
                ],
            });
        }

        $(function () {
            sdfun.fn.trimAll("editView-div");
            var labelName = $("#labelName");
            var labelId = $("#labelId");
            var labelNo = $("#labelNo");
            var isActive = $("#isActive");
            var allFieldsInfo = $([]).add(labelName).add(labelId).add(isActive);

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

            $("#aSearch").bind("click", function () {
                qryList();
            });

            $("#editView-div").dialog({
                autoOpen: false,
                height: 250,
                width: 450,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var bValid = true;
                        allFieldsInfo.removeClass("ui-state-error");
                        bValid = bValid && checkLength(labelName, 1, 32);
                        bValid = bValid && checkLength(isActive, 1, 32);
                        bValid = bValid && checkLength(labelNo, 1, 32);
                        if (!bValid) {
                            return;
                        }
                        var url = "${pageContext.request.contextPath}/drugslabel/addDrugsLabel";
                        var params = {
                            "labelName": $("#labelName").val(),
                            "labelNo": $("#labelNo").val(),
                            "isActive": $("#isActive").val(),
                            "isNull": $("#isNull").val()
                        };
                        if ($("#labelId").val() && $("#labelId").val() != "") {
                            url = "${pageContext.request.contextPath}/drugslabel/updateDrugsLabel";
                            params["labelId"] = $("#labelId").val();
                        }
                        $.ajax({
                            type: 'POST',
                            url: url,
                            dataType: 'json',
                            cache: false,
                            data: params,
                            success: function (data) {
                                if (data.success == true || data.code == '<%=ExceptionCodeConstants.RECORD_DELETE%>') {
                                    $("#editView-div").dialog("close");
                                }
                                qryList();
                                layer.alert(data.description, {'title': '操作提示', icon: 1});
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
                    resetForm("editView-div");
                    allFieldsInfo.val("").removeClass("ui-state-error");
                }
            });

            //新增按钮
            $("#addDataDicRoleBtn").bind("click", function () {
                $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.druglabel.add'/>");
                $("#editView-div").dialog("open");
            });


            $("#deleteDataDicRoleBtn").bind("click", function () {
                var ids = getSelectedRow();
                if (ids && ids.length > 0) {
                    layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                        btn: ['确定', '取消'], icon: 3, title: '提示'
                    }, function () {
                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/drugslabel/deleteDrugsLabel',
                            dataType: 'json',
                            cache: false,
                            data: [{name: 'labels', value: ids}],
                            success: function (data) {
                                qryList();
                                layer.alert(data.description, {'title': '操作提示', icon: 1});
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

        function updMedicLabel(lId) {
            if (lId) {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/drugslabel/displayDrugsLabel',
                    dataType: 'json',
                    cache: false,
                    data: [{name: 'labelId', value: lId}],
                    success: function (data) {
                        if (data.success == false) {
                            layer.alert(data.description, {'title': '操作提示', icon: 0});
                            return;
                        }
                        else {
                            $("#labelId").val(data.labelId);
                            $("#labelName").val(data.labelName);
                            $("#labelNo").val(data.labelNo);
                            $("#isActive").val(data.isActive);
                            $("#isNull").val(data.isNull);
                        }
                        $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.druglabel.update'/>");
                        $("#editView-div").dialog("open");
                    },
                    error: function () {
                    }
                });
            } else {
                layer.alert('<spring:message code="common.plzSelectOneRecourd"/>', {'title': '操作提示', icon: 0});
            }
        }

        function process(v) {
            if (v == "是") {
                return "<spring:message code="common.no"/>";
            } else if (v == "否") {
                return "<spring:message code="common.yes"/>";
            }
        }

        function getSelectedRow() {
            var arr = new Array(0);
            $(".datatable > tbody").find("input:checked").each(function (i, v) {
                arr.push($(v).val())
            });
            return arr;
        }
        function qryList(param) {
            qryParam = param;
            datatable.ajax.reload();
        }
    </script>
</head>
<body>

<div class="main-div" style="width:100%">
    <%-- 搜索条件--开始 --%>
    <div data-qryMethod funname="qryList" class="ui-search-header ui-search-box" id="qryView-div" style="display: inline; ">
        <div style="float:right ">
            <input placeholder="名称" name="labelNames" size="20" data-cnd="true">&nbsp;&nbsp;
            <button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
            <button class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
        </div>
        <shiro:hasPermission name="PIVAS_BTN_204"><a class="ui-search-btn ui-btn-bg-green" id="addDataDicRoleBtn"><i class="am-icon-plus"></i><span>新增</span></a></shiro:hasPermission>&nbsp;
        <shiro:hasPermission name="PIVAS_BTN_205"><a class="ui-search-btn ui-btn-bg-red"  id="deleteDataDicRoleBtn"><i class="am-icon-remove"></i><span>删除</span></a></shiro:hasPermission>
    </div>
    <%-- 搜索条件--结束 --%>
    <div class="tbl">
        <table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
            <thead>
            <tr>
                <th><input type='checkbox' name='cbs'/></th>
                <th>标签名称</th>
                <th>编码</th>
                <th>是否空包</th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>


<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="<spring:message code='pivas.druglabel.update'/>" align="center" style="display: none;">
    <form id="editView-form" action="" method="post">
        <input type="hidden" id="labelId" name="labelId"/>
        <div class="popup">
            <div class="row">
                <div class="column">
                    <label class="tit">编码</label>
                    <input type="text" class="condition" name="labelNo" id="labelNo" empty="true" maxlength="32"
                           title="<spring:message code='common.op.remind2'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column">
                    <label class="tit"><spring:message code='medic.labelName'/></label>
                    <input type="text" class="condition" name="labelName" id="labelName" maxlength="32"
                           title="<spring:message code='common.op.remind2'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column">
                    <label class="tit">是否空包</label>
                    <select id="isNull" name="isNull" readonly empty="false" style="width: 184px;">
                        <option value="0"><spring:message code="common.no"/></option>
                        <option value="1"><spring:message code="common.yes"/></option>
                    </select>
                    <span class="mand">*</span>
                </div>

                <div class="column" style="display: none">
                    <label class="tit"><spring:message code='medic.labelisActive'/></label>
                    <select id="isActive" name="isActive" readonly empty="false" style="width: 184px;">
                        <option value="1"><spring:message code="common.yes"/></option>
                        <option value="0"><spring:message code="common.no"/></option>
                    </select>
                    <span class="mand">*</span>
                </div>
            </div>
        </div>
    </form>
</div>
</body>

</html>