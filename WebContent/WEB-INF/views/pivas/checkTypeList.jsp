<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet"/>

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
                },
                "drawCallback": function () {
                },
                "ajax": {
                    "url": "${pageContext.request.contextPath}/inspectType/inspectTypeList",
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
                    {"data": "orderID", "bSortable": false},
                    {"data": "checkName", "bSortable": false},
                    {"data": "checkTypeName", "bSortable": false},
                    {"data": "isShowResult", "bSortable": false},
                    {"data": "isChargeResult", "bSortable": false},
                    {"data": "isEffectResult", "bSortable": false},
                    {"data": "isStockResult", "bSortable": false},
                    {"data": "gid", "bSortable": false}
                ],
                columnDefs: [
                    {
                        targets: 0,
                        render: function (data, type, row) {
                            return "<input type='checkbox' name='cbs' value='"+data+"'/>";
                        }
                    },{
                        targets: 8,
                        render: function (data, type, row) {
                            var str = "";
                            <shiro:hasPermission name="PIVAS_BTN_207">
                            str += "<a class='button btn-bg-green' href='javascript:updCheckType(" + data + ");'><i class='am-icon-edit'></i><span>&nbsp;修改</span></a>";
                            </shiro:hasPermission>
                            return str;
                        }
                    }
                ]
            });
        }
        $(function () {
            sdfun.fn.trimAll("editView-div");
            var orderID = $("#orderIDEdit");
            var checkName = $("#checkNameEdit");
            var isShow = $("#isShowEdit");
            var isCharge = $("#isChargeEdit");
            var isEffect = $("#isEffectEdit");
            var checkType = $("#checkTypeEdit");
            var isStock = $("#isStockEdit");
            var allFieldsInfo = $([]).add(orderID).add(checkName).add(isShow).add(isCharge).add(isEffect).add(checkType).add(isStock);
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
                height: 380,
                width: 500,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var bValid = true;
                        allFieldsInfo.removeClass("ui-state-error");
                        bValid = bValid && isPInt(orderID);
                        bValid = bValid && checkLength(checkName, 1, 32);
                        if (!bValid) {
                            return;
                        }
                        var url = "${pageContext.request.contextPath}/inspectType/addInspectType";
                        var params = {
                            "orderID": $("#orderIDEdit").val(),
                            "checkName": $("#checkNameEdit").val(),
                            "isShow": $("#isShowEdit").val(),
                            "isCharge": $("#isChargeEdit").val(),
                            "isEffect": $("#isEffectEdit").val(),
                            "checkType": $("#checkTypeEdit").val(),
                            "isStock": $("#isStockEdit").val()
                        };
                        if ($("#gid").val() && $("#gid").val() != "") {
                            url = "${pageContext.request.contextPath}/inspectType/updateInspectType";
                            params["gid"] = $("#gid").val();
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
                                    qryList();
                                    layer.alert(data.description, {'title': '操作提示', icon: 1});
                                }else{
                                    if(data.code == '<%=ExceptionCodeConstants.RECORD_DELETE%>'){

                                    }
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
                    resetForm("editView-div");
                    allFieldsInfo.val("").removeClass("ui-state-error");
                }
            });

            //新增按钮
            $("#addDataDicBtn").bind("click", function () {
                $("#checkTypeEdit").combobox("reset", '0');
                $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.checktype.add'/>");
                $("#editView-div").dialog("open");
            });

            $("#deleteDataDicBtn").bind("click", function () {
                var ids = getSelectedRow();
                if (ids && ids.length > 0) {
                    layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                        btn: ['确定', '取消'], icon: 3, title: '提示'
                    }, function () {
                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/inspectType/delInspectType',
                            dataType: 'json',
                            cache: false,
                            data: [{name: 'gid', value: ids}],
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

        function updCheckType(lId) {
            if (lId) {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/inspectType/initUpdateInspectType',
                    dataType: 'json',
                    cache: false,
                    data: [{name: 'gid', value: lId}],
                    success: function (data) {
                        if (data.success == false) {
                            qryList();
                            layer.alert(data.description, {'title': '操作提示', icon: 0});
                            return;
                        }
                        else {
                            $("#gid").val(data.gid);
                            $("#orderIDEdit").val(sdfun.fn.htmlDecode(data.orderID));
                            $("#checkNameEdit").val(sdfun.fn.htmlDecode(data.checkName));
                            $("#isShowEdit").combobox("reset", data.isShow);
                            $("#isChargeEdit").combobox("reset", data.isCharge);
                            $("#isEffectEdit").combobox("reset", data.isEffect);
                            $("#checkTypeEdit").combobox("reset", data.checkType);
                            $("#isStockEdit").combobox("reset", data.isStock);
                        }
                        $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.checktype.update'/>");
                        $("#editView-div").dialog("open");
                    },
                    error: function () {
                    }
                });
            } else {
                layer.alert('<spring:message code="common.plzSelectOneRecourd"/>', {'title': '操作提示', icon: 0});
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

        function closeDeal() {
            $("#editView-div").find("input").each(function () {
                $(this).val("");
            });
            $("#editView-div").find("textarea").each(function () {
                $(this).val("");
            });
        }
    </script>
</head>
<body>

<div class="main-div" style="width:100%">

    <div id="qryView-div">
        <div class="search-div">
            <shiro:hasPermission name="PIVAS_BTN_272"><a class="ui-search-btn ui-btn-bg-green" id="addDataDicBtn"><i class="am-icon-plus"></i><span><spring:message
                    code='common.add'/></span></a></shiro:hasPermission>&nbsp;
            <shiro:hasPermission name="PIVAS_BTN_273"><a class="ui-search-btn ui-btn-bg-red" id="deleteDataDicBtn"><i class="am-icon-remove"></i><span><spring:message
                    code='common.del'/></span></a></shiro:hasPermission>
        </div>
        <div class="tbl">
            <table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
                <thead>
                <tr>
                    <th><input type='checkbox' name='cbs'/></th>
                    <th><spring:message code="pivas.checktype.orderid"/></th>
                    <th><spring:message code="pivas.checktype.checkname"/></th>
                    <th><spring:message code="pivas.checktype.type"/></th>

                    <th><spring:message code="pivas.checktype.isshow"/></th>
                    <th><spring:message code="pivas.checktype.ischarge"/></th>
                    <th><spring:message code="pivas.checktype.iseffect"/></th>
                    <th>是否减库存</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>


</div>


<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="<spring:message code='pivas.checktype.update'/>" align="center" style="display: none;">
    <form id="editView-form" action="" method="post">
        <input type="hidden" id="gid" name="gid"/>
        <div class="popup">
            <div class="row">
                <div class="column">
                    <label class="tit"><spring:message code='pivas.checktype.orderid'/></label>
                    <input type="text" style="display: none;">
                    <input type="text" class="edit" name="orderID" id="orderIDEdit" maxlength="32"
                           title="<spring:message code='common.plzInputZInteger'/>" style="width:184px;"/>
                    <span class="mand">*</span>
                </div>
                <div class="column">
                    <label class="tit"><spring:message code='pivas.checktype.checkname'/></label>
                    <input type="text" class="edit" name="checkName" id="checkNameEdit" maxlength="32"
                           title="<spring:message code='common.op.remind2'/>"/>
                    <span class="mand">*</span>
                </div>
            </div>

            <div class="row">
                <div class="column">
                    <label class="tit"><spring:message code='pivas.checktype.isshow'/></label>
                    <sd:select id="isShowEdit" name="isShow" width="184px" required="true"
                               categoryName="pivas.common.yesorno" tableName="sys_dict"></sd:select>
                </div>
                <div class="column">
                    <label class="tit"><spring:message code='pivas.checktype.ischarge'/></label>

                    <sd:select id="isChargeEdit" name="isCharge" width="184px" required="true"
                               categoryName="pivas.common.yesorno" tableName="sys_dict"></sd:select>
                </div>
            </div>

            <div class="row">
                <div class="column">
                    <label class="tit"><spring:message code='pivas.checktype.iseffect'/></label>
                    <sd:select id="isEffectEdit" name="isEffect" width="184px" required="true"
                               categoryName="pivas.common.yesorno" tableName="sys_dict"></sd:select>
                </div>

                <div class="column">
                    <label class="tit"><spring:message code='pivas.checktype.type'/></label>
                    <sd:select id="checkTypeEdit" width="184px" required="true" categoryName="pivas.checktype.type"
                               tableName="sys_dict"></sd:select>
                </div>

                <div class="column">
                    <label class="tit">是否减库存</label>
                    <sd:select id="isStockEdit" name="isStock" width="184px" required="true"
                               categoryName="pivas.common.yesorno" tableName="sys_dict"></sd:select>
                </div>
            </div>
        </div>
    </form>
</div>

</body>

</html>