<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/assets/pivas/css/spectrum.css" type="text/css" rel="stylesheet"/>

<script src="${pageContext.request.contextPath}/assets/pivas/js/spectrum.js"></script>
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
        var spectrumOption;
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
                "ajax": {
                    "url": "${pageContext.request.contextPath}/errType/errTypeList",
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
                    {"data": "name", "bSortable": false},
                    {"data": "levelName", "bSortable": false, "defaultContent":""},
                    {"data": "color", "bSortable": false, "defaultContent":""}
                ],
                columnDefs: [
                    {
                        targets: 2,
                        render: function (data, type, row) {
                            return "<div style='width:30px;height:15px;border-radius:3px; background:" + row.color + "'></div>";
                        }
                    },
                    {
                        targets: 3,
                        "width": "15%",
                        render: function (data, type, row) {
                            var opt = "";
                            <shiro:hasPermission name="PIVAS_BTN_305">
                            opt +=  "<a class='button btn-bg-green' href='javascript:edit(" + row.gid + ");'><i class='am-icon-edit'></i><span>&nbsp;<spring:message code='common.edit'/></span></a>";
                            </shiro:hasPermission>
                            <shiro:hasPermission name="PIVAS_BTN_303">
                            opt += " <a class='button ui-btn-bg-red' href='javascript:del(" + row.gid + ",\"" + row.name + "\");'><i class='am-icon-remove'></i><span>&nbsp;<spring:message code='common.del'/></span></a>";
                            </shiro:hasPermission>
                            return opt;
                        }
                    }
                ]
            });
        }

        function edit(ids) {
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/errType/initErrTypeUpdate',
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
                        $("#nameEdit").val(data.name);
                        spectrumOption.color = data.color;
                        $("#errorColor").spectrum(spectrumOption);
                        $("#trialColor").val(data.color);
                        $("#levelEdit").combobox("reset", data.level);
                    }
                    $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.errortype.update'/>");
                    $("#editView-div").dialog("open");
                },
                error: function () {
                }
            });
        }
        
        function del(ids,names) {
            layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                btn: ['确定', '取消'], icon: 3, title: '提示'
            }, function () {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/errType/delErrType',
                    dataType: 'json',
                    cache: false,
                    data: [{name: 'gid', value: ids}, {name: 'name', value: names}],
                    success: function (data) {
                        qryList();
                        layer.alert(data.description, {'title': '操作提示', icon: 1});
                    },
                    error: function () {
                        layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
                    }
                });
            });
        }
        $(function () {
             spectrumOption = {
                color: '#ff0000',
                allowEmpty: true,
                flat: false,
                showInput: true,
                showInitial: true,
                showPalette: true,
                showSelectionPalette: true,
                maxPaletteSize: 10,
                preferredFormat: "hex",
                localStorageKey: "spectrum.demo",
                cancelText: "取消",
                chooseText: "确认",
                change: function (color) {
                    $("#trialColor").val(color);
                },
                palette: [
                    ["rgb(0, 0, 0)", "rgb(67, 67, 67)", "rgb(102, 102, 102)",
                        "rgb(204, 204, 204)", "rgb(217, 217, 217)", "rgb(255, 255, 255)"],
                    ["rgb(152, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 153, 0)", "rgb(255, 255, 0)", "rgb(0, 255, 0)",
                        "rgb(0, 255, 255)", "rgb(74, 134, 232)", "rgb(0, 0, 255)", "rgb(153, 0, 255)", "rgb(255, 0, 255)"],
                    ["rgb(230, 184, 175)", "rgb(244, 204, 204)", "rgb(252, 229, 205)", "rgb(255, 242, 204)", "rgb(217, 234, 211)",
                        "rgb(208, 224, 227)", "rgb(201, 218, 248)", "rgb(207, 226, 243)", "rgb(217, 210, 233)", "rgb(234, 209, 220)",
                        "rgb(221, 126, 107)", "rgb(234, 153, 153)", "rgb(249, 203, 156)", "rgb(255, 229, 153)", "rgb(182, 215, 168)",
                        "rgb(162, 196, 201)", "rgb(164, 194, 244)", "rgb(159, 197, 232)", "rgb(180, 167, 214)", "rgb(213, 166, 189)",
                        "rgb(204, 65, 37)", "rgb(224, 102, 102)", "rgb(246, 178, 107)", "rgb(255, 217, 102)", "rgb(147, 196, 125)",
                        "rgb(118, 165, 175)", "rgb(109, 158, 235)", "rgb(111, 168, 220)", "rgb(142, 124, 195)", "rgb(194, 123, 160)",
                        "rgb(166, 28, 0)", "rgb(204, 0, 0)", "rgb(230, 145, 56)", "rgb(241, 194, 50)", "rgb(106, 168, 79)",
                        "rgb(69, 129, 142)", "rgb(60, 120, 216)", "rgb(61, 133, 198)", "rgb(103, 78, 167)", "rgb(166, 77, 121)",
                        "rgb(91, 15, 0)", "rgb(102, 0, 0)", "rgb(120, 63, 4)", "rgb(127, 96, 0)", "rgb(39, 78, 19)",
                        "rgb(12, 52, 61)", "rgb(28, 69, 135)", "rgb(7, 55, 99)", "rgb(32, 18, 77)", "rgb(76, 17, 48)"]
                ]
            }
            //初始化颜色选择器
            $("#errorColor").spectrum(spectrumOption);

            sdfun.fn.trimAll("editView-div");
            var name = $("#nameEdit");
            var level = $("#levelEdit");
            var allFieldsInfo = $([]).add(name).add(level);

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
                width: 500,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var bValid = true;
                        allFieldsInfo.removeClass("ui-state-error");
                        bValid = bValid && checkLength(name, 1, 32);
                        if (!bValid) {
                            return;
                        }
                        var url = "${pageContext.request.contextPath}/errType/addErrType";
                        var params = {
                            "name": $("#nameEdit").val(),
                            "level": $("#levelEdit").val(),
                            "color": $("#trialColor").val(),
                        };
                        if ($("#gid").val() && $("#gid").val() != "") {
                            url = "${pageContext.request.contextPath}/errType/mdfErrType";
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
                                }
                                if (data.code != "00003") {
                                    qryList();
                                }
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
            $("#addDataDicBtn").bind("click", function () {
                $("#levelEdit").combobox("reset", '0');
                $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.errortype.add'/>");
                $("#editView-div").dialog("open");
                spectrumOption.color = '';
                $("#errorColor").spectrum(spectrumOption);
            });

        });

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
            <shiro:hasPermission name="PIVAS_BTN_302"><a class="ui-search-btn ui-btn-bg-green" id="addDataDicBtn"><i class="am-icon-plus"></i><span><spring:message
                    code='common.add'/></span></a></shiro:hasPermission>&nbsp;
        </div>
        <div class="tbl">
            <table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
                <thead>
                <tr>
                    <th><spring:message code="pivas.errortype.name"/></th>
                    <th><spring:message code="pivas.errortype.level"/></th>
                    <th>错误类型颜色</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>

<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="<spring:message code='pivas.errortype.update'/>" align="center" style="display: none;">
    <form id="editView-form" action="" method="post">
        <input type="hidden" id="gid" name="gid"/>
        <div class="popup">
            <div class="row">
                <div class="column">
                    <input type="text" style="display:none;">
                    <label class="tit"><spring:message code='pivas.errortype.name'/></label>
                    <input type="text" class="edit" name="name" id="nameEdit" maxlength="32"
                           title="<spring:message code='common.op.remind2'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column">
                    <label class="tit"><spring:message code='pivas.errortype.level'/></label>
                    <sd:select id="levelEdit" width="183px" required="true" categoryName="pivas.errortype.level"
                               tableName="sys_dict"></sd:select>
                    <span class="mand">*</span>
                </div>
                <div class="column">
                    <label class="tit"><spring:message code='pivas.errortype.color'/></label>
                    <input type="hidden" id="trialColor"/>
                    <div style="background: #ff0000;display:inline-block;float:left">
                        <input type='text' id="errorColor"/>
                    </div>
                </div>
            </div>


        </div>
    </form>
</div>

</body>

</html>