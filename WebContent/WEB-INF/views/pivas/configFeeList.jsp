<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet"/>
<style>

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
                    "url": "${pageContext.request.contextPath}/configFee/getAllFeeList",
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
                        data.recordsFiltered = data.total;
                        data.recordsTotal = data.total;
                        return data.data;
                    }
                },
                "columns": [
                    {"data": "gid", "bSortable": false},
                    {"data": "costCode", "bSortable": false},
                    {"data": "costName", "bSortable": false},
                    {"data": "price", "bSortable": false},
                    {"data": "configFeeTypeName", "bSortable": false},
                    {"data": "gid", "bSortable": false}
                ],
                columnDefs: [
                    {
                        targets: 0,
                        render: function (data, type, row) {
                            return "<input type='checkbox' name='cbs' value='"+data+"'/>";
                        }
                    },{
                        targets: 5,
                        render: function (data, type, row) {
                            var str = "";
                            <shiro:hasPermission name="PIVAS_BTN_295">
                            str += "<a class='button btn-bg-green' href='javascript:updConfigFee(" + data + ");'><i class='am-icon-edit'></i><span>&nbsp;修改</span></a>";
                            </shiro:hasPermission>
                            return str;
                        }
                    }
                ]
            });
        }
        $(function () {
            sdfun.fn.trimAll("editView-div");
            $("#editView-form select").combobox();
            querySelectData();
            var costCode = $("#costCodeEdit");
            var costName = $("#costNameEdit");
            var price = $("#priceEdit");
            var configFeeType = $("#configFeeTypeEdit");
            var allFieldsInfo = $([]).add(costCode).add(costName).add(price).add(configFeeType);
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
                height: 300,
                width: 500,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var bValid = true;
                        allFieldsInfo.removeClass("ui-state-error");
                        bValid = bValid && checkDecimals(price);
                        if (price.val() < 0) {
                            bValid = false;
                        }

                        bValid = bValid && checkLength(costCode, 1, 32);
                        bValid = bValid && checkLength(costName, 1, 32);
                        if (!bValid) {
                            return;
                        }
                        var url = "${pageContext.request.contextPath}/configFee/addConfigFee";
                        var params = {
                            "costCode": $("#costCodeEdit").val(),
                            "costName": $("#costNameEdit").val(),
                            "price": $("#priceEdit").val(),
                            "configFeeType": $("#configFeeTypeEdit").val()
                        };
                        if ($("#gid").val() && $("#gid").val() != "") {
                            url = "${pageContext.request.contextPath}/configFee/mdfConfigFee";
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
                $("#configFeeTypeEdit").combobox("reset", '0');
                $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.confingfee.add'/>");
                querySelectData("add")
                //$("#editView-div").dialog("open");
            });

            //编辑按钮
            $("#updateDataDicBtn").bind("click", function () {
                var ids = getSelectedRow();
                if (ids && ids.length === 1) {
                    $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/configFee/initUpdateConfigFee',
                        dataType: 'json',
                        cache: false,
                        data: [{name: 'gid', value: ids}],
                        success: function (data) {
                            if (data.success == false) {
                                qryList();
                                layer.alert(data.description, {'title': '操作提示', icon: 0});
                                return;
                            }
                            else {
                                $("#gid").val(data.gid);
                                $("#costCodeEdit").val(data.costCode);
                                $("#costNameEdit").val(data.costName);
                                $("#priceEdit").val(data.price);
                                $("#configFeeTypeEdit").combobox("reset", data.configFeeType);
                            }
                            $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.confingfee.update'/>");
                            $("#editView-div").dialog("open");
                        },
                        error: function () {
                        }
                    });
                } else {
                    layer.alert('<spring:message code="common.plzSelectOneRecourd"/>', {'title': '操作提示', icon: 0});
                }
            });

            $("#deleteDataDicBtn").bind("click", function () {
                var ids = getSelectedRow();
                if (ids && ids.length > 0) {
                    layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                        btn: ['确定', '取消'], icon: 3, title: '提示'
                    }, function () {
                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/configFee/delConfigFee',
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

        function updConfigFee(lId) {
            if (lId) {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/configFee/initUpdateConfigFee',
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
                            $("#costCodeEdit").val(data.costCode);
                            $("#costNameEdit").val(data.costName);
                            $("#priceEdit").val(data.price);
                            $("#configFeeTypeEdit").combobox("reset", data.configFeeType);
                        }
                        $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.confingfee.update'/>");
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

        // 下拉数据查询
        function querySelectData(type) {
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/configFee/querySelectDatareq',
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data.success == true) {
                        var html = [];

                        //药品分类
                        $.each(data.configFeeTypeList, function (i, d) {
                            html.push('<option value="' + d.gid + '">');
                            html.push(sdfun.fn.htmlDecode(d.typeDesc));
                            html.push('</option>');
                        });

                        $("#configFeeTypeEdit").html(html.join(""));

                    }
                    resetForm("editView-form");
                    if (type == "add") {
                        $("#editView-div").dialog("open");
                    }
                }
            });
        }
    </script>
</head>
<body>

<div class="main-div" style="width:100%">

    <div data-qryMethod funname="qryList" class="ui-search-header ui-search-box" id="qryView-div" style="display: inline; ">
        <div style="float:right ">
            <input placeholder="名称" name="costNames" size="15" data-cnd="true">&nbsp;&nbsp;
            <input placeholder="收费编码" name="costCodes" size="15" data-cnd="true">&nbsp;&nbsp;
            <button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
            <button  class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
        </div>
        <shiro:hasPermission name="PIVAS_BTN_292"><a class="ui-search-btn ui-btn-bg-green"  id="addDataDicBtn"><i class="am-icon-plus"></i><span><spring:message
                code='common.add'/></span></a></shiro:hasPermission>&nbsp;
        <shiro:hasPermission name="PIVAS_BTN_293"><a class="ui-search-btn ui-btn-bg-red" id="deleteDataDicBtn"><i class="am-icon-remove"></i><span><spring:message
                code='common.del'/></span></a></shiro:hasPermission>

    </div>
    <%-- 搜索条件--结束 --%>
    <div class="tbl">
        <table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
            <thead>
            <tr>

                <th><input type='checkbox' name='cbs'/></th>
                <th>收费编码</th>
                <th><spring:message code="pivas.confingfee.costname"/></th>
                <th><spring:message code="pivas.confingfee.price"/></th>
                <th><spring:message code="pivas.configfee.configfeetype"/></th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
    </div>

</div>

<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="<spring:message code='pivas.measureunit.update'/>" align="center" style="display: none;">
    <form id="editView-form" action="" method="post">
        <input type="hidden" id="gid" name="gid"/>
        <div class="popup">
            <div class="row">
                <div class="column">
                    <label class="tit">收费编码</label>
                    <input type="text" class="edit" name="costCode" id="costCodeEdit" maxlength="32"
                           title="<spring:message code='common.op.remind2'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column">
                    <label class="tit"><spring:message code='pivas.confingfee.costname'/></label>
                    <input type="text" class="edit" name="costName" id="costNameEdit" maxlength="32"
                           title="<spring:message code='common.op.remind2'/>"/>
                    <span class="mand">*</span>
                </div>
            </div>

            <div class="row">
                <div class="column">
                    <label class="tit"><spring:message code='pivas.confingfee.price'/></label>
                    <input type="text" class="edit" name="price" id="priceEdit" maxlength="32"
                           title="<spring:message code='common.plzInputZNumber0'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column">
                    <label class="tit"><spring:message code='pivas.configfee.configfeetype'/></label>
                    <%-- <cbems:select id="configFeeTypeEdit" width="185px" required="true" categoryName="pivas.configfee.type" tableName="sys_dict" /></td> --%>
                    <select id="configFeeTypeEdit" name="configFeeType" required="true" style="width: 185px;" readonly
                    "></select>

                </div>
            </div>
        </div>
    </form>
</div>
</body>

</html>