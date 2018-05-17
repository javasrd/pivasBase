<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet"/>
    <title></title>
</head>
<body>
<div class="main-div" style="width: 100%">
    <div id="qryView-div">
        <div class="search-div">
            <shiro:hasPermission name="PIVAS_BTN_981"><a class="ui-search-btn ui-btn-bg-green" style="margin-left:0px;"
                                                         id="addBtn"><i class="am-icon-plus"></i><span><spring:message
                    code='common.add'/></span></a></shiro:hasPermission>
            <shiro:hasPermission name="PIVAS_BTN_982"><a class="ui-search-btn  ui-btn-bg-yellow" id="updateBtn"><i class="am-icon-edit"></i><span><spring:message
                    code='common.edit'/></span></a></shiro:hasPermission>
            <shiro:hasPermission name="PIVAS_BTN_983"><a class="ui-search-btn  ui-btn-bg-red" id="delBtn"><i class="am-icon-trash"></i><span><spring:message
                    code='common.del'/></span></a></shiro:hasPermission>
        </div>
    </div>
    <div class="tbl">
        <%--<table id="flexGrid" style="display: block;"></table>--%>
        <table id="datatable" data-qryMethod funname="gridQuery"  class="table datatable ui-data-table display dataTable no-footer">
            <thead>
            <tr>

                <th><input type='checkbox' name='cbs'/></th>
                <th>组名</th>
                <th>优先级</th>
                <th>病区</th>

            </tr>
            </thead>
        </table>
    </div>
</div>
<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="" align="center" style="display: none;">
    <form id="editView-form" method="post">
        <div class="popup">
            <div class="row" style="float:left">
                <div class="column" style="width:auto;">
                    <label class="tit" style="width:30px;">组名</label>
                    <input type="text" class="edit" name="groupName" id="groupName" maxlength="32"
                           title="<spring:message code='common.op.remind16'/>" style="width:150px;"/>
                    <span class="mand">*</span>
                </div>
                <div class="column" style="width:auto;">
                    <label class="tit" style="width:50px;">优先级</label>
                    <input type="text" class="edit" name="orderNum" id="orderNum"
                           title="<spring:message code='common.plzInputInteger1'/>" style="width:150px;"/>
                    <span class="mand">*</span>
                </div>
                <input type="hidden" value="" id="groupId">
                <input type="hidden" value="" id="oldOrder">
            </div>
        </div>
    </form>

    <div style="border-top: solid 1px #CCCCCC;margin-top: 50px;"></div>
    <div id="groupSelect" style="height: 320px;width: 100%;position: relative;overflow-y:auto;">

    </div>
    <div style="border-top: solid 1px #CCCCCC;margin-top: 10px;"></div>
</div>

</body>
<script>

    //save or edit
    var checkType = "";
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
                "url": "${pageContext.request.contextPath}/wardRegionGroup/qryList",
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
                {"data": "id", "bSortable": false},
                {"data": "deptname", "bSortable": false},
                {"data": "ordernum", "bSortable": false},
                {"data": "wardgroup", "bSortable": false}
            ],
            columnDefs: [

                {
                    targets: 0,
                    render: function (data, type, row) {
                        return "<input type='checkbox' name='cbs' value='"+data+"'/>";
                    }
                },
                {
                    targets: 3,
                    render: function (data, type, row) {
                        return row.wardgroup ==null?"":row.wardgroup;
                    }
                }
            ],
        });
    }
    $(function () {
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

        var fieldsInfo = $("#editView-form").find("input.edit");
        sdfun.fn.trimAll("editView-div");

        $("#editView-div").dialog({
            autoOpen: false,
            height: 500,
            width: 500,
            modal: true,
            resizable: false,
            buttons: {
                "<spring:message code='common.confirm'/>": function () {

                    if (checkType == "save") {

                        var bValid = true;
                        fieldsInfo.removeClass("ui-state-error");

                        var name = $("#groupName");
                        var order = $("#orderNum");

                        bValid = bValid && checkLength(name, 1, 32);
                        bValid = bValid && checkIllegalChar(name, name.val());
                        bValid = bValid && checkLength(order, 1, 32);
                        bValid = bValid && isPInt(order);

                        if (!bValid) {
                            return;
                        }

                        var groupName = $("#groupName").val();
                        var groupOrder = $("#orderNum").val();
                        var idArray = new Array();

                        $("#groupSelect").find("input:checked").each(function () {
                            var id = $(this).val();
                            idArray.push(id);
                        });

                        var params = {
                            "idStrs": idArray.toString(),
                            "groupName": groupName,
                            "groupOrder": groupOrder
                        };

                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/wardRegionGroup/saveWardGroup',
                            dataType: 'json',
                            cache: false,
                            //async : true,
                            data: params,
                            success: function (data) {
                                if (data.code == -1) {
                                    layer.alert(data.mess, {'title': '操作提示', icon: 0});
                                } else if (data.code == 1) {
                                    resetForm("editView-div");
                                    fieldsInfo.removeClass("ui-state-error");
                                    $("#editView-div").dialog("close");
                                    gridQuery();
                                }
                            },
                            error: function (data) {
                                layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
                            }
                        });

                    } else if (checkType == "edit") {

                        var bValid = true;
                        fieldsInfo.removeClass("ui-state-error");

                        var order = $("#orderNum");
                        bValid = bValid && checkLength(order, 1, 32);
                        bValid = bValid && isPInt(order);

                        if (!bValid) {
                            return;
                        }

                        var groupOrder = $("#orderNum").val();
                        var idArray = new Array();
                        var parentid = $("#groupId").val();
                        var oldOrder = $("#oldOrder").val();
                        var checkOrder = true;
                        if (groupOrder == oldOrder) {
                            checkOrder = false;
                        }

                        $("#groupSelect").find("input:checked").each(function () {
                            var id = $(this).val();
                            idArray.push(id);
                        });

                        var params = {
                            "idStrs": idArray.toString(),
                            "groupOrder": groupOrder,
                            "parentid": parentid,
                            "checkOrder": checkOrder
                        };

                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/wardRegionGroup/updateGroup',
                            dataType: 'json',
                            data: params,
                            success: function (data) {
                                if (data.code == -1) {
                                    layer.alert(data.mess, {'title': '操作提示', icon: 0});
                                } else if (data.code == 1) {
                                    resetForm("editView-div");
                                    fieldsInfo.removeClass("ui-state-error");
                                    $("#editView-div").dialog("close");
                                    gridQuery();
                                }
                            },
                            error: function () {
                                layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
                            }
                        });

                    }

                },
                "<spring:message code='common.cancel'/>": function () {
                    fieldsInfo.removeClass("ui-state-error");
                    $("#editView-div").dialog("close");
                }
            },
            close: function () {
                resetForm("editView-div");
                fieldsInfo.removeClass("ui-state-error");
            }
        });

        $("#addBtn").click(function () {

            fieldsInfo.removeClass("ui-state-error");
            $("#editView-div").dialog("open");
            $("#groupName").prop("disabled", false);
            $("#groupName").focus();
            $("#groupId").val("");
            $("#oldOrder").val("");
            checkType = "save";

            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/wardRegionGroup/initSelWard',
                dataType: 'html',
                cache: false,
                //async : true,
                success: function (data) {
                    $("#groupSelect").html(data);
                },
                error: function (data) {
                    layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
                }
            });

        });

        $("#updateBtn").click(function () {

            checkType = "edit";
            var ids = getSelectedRow();
            if (ids.length == 1) {

                fieldsInfo.removeClass("ui-state-error");
                $("#editView-div").dialog("open");

                var id = ids[0];

                var tr = undefined;
                $(".datatable > tbody > tr").each(function(i,v){
                    $(v).children().each(function(ii,vv){
                        if($(vv).children().first().val() == id){
                            tr = $(v);
                        }
                    });
                });

                if(tr != undefined){
//                    var names = getFlexiGridSelectedRowText($("#flexGrid"), 4);
//                    var orders = getFlexiGridSelectedRowText($("#flexGrid"), 5);
                    var names = tr.children().eq(1).text();
                    var orders = tr.children().eq(2).text();


                    $("#groupName").val(names);
                    $("#groupName").prop("disabled", true);
                    $("#orderNum").val(orders);
                    $("#groupId").val(id);
                    $("#oldOrder").val(orders);

                    $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/wardRegionGroup/initSelWard',
                        dataType: 'html',
                        cache: false,
                        //async : true,
                        data: {"wardId": id},
                        success: function (data) {
                            $("#groupSelect").html(data);
                        },
                        error: function (data) {
                            layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
                        }
                    });
                }
            } else {
                layer.alert('<spring:message code="common.plzSelectOneRecourd"/>', {'title': '操作提示', icon: 0});
            }

        });

        $("#delBtn").click(function () {

            var ids = getSelectedRow();
            if (ids.length >= 1) {

                layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                    btn: ['确定', '取消'], icon: 3, title: '提示'
                }, function () {
                    $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/wardRegionGroup/delGroup',
                        dataType: 'json',
                        data: {"idStrs": ids.toString()},
                        success: function (data) {

                            if (data.code == -1) {
                                layer.alert(data.mess, {'title': '操作提示', icon: 0});
                            } else if (data.code == 1) {
                                gridQuery();
                                layer.alert(data.mess, {'title': '操作提示', icon: 1});
                            }
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

    function gridQuery(param) {
        qryParam = param;
        datatable.ajax.reload();
    }

</script>

</html>