<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
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
                "ajax": {
                    "url": "${pageContext.request.contextPath}/printLabelCon/printBottleLabelList",
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
                    {"data": "name", "bSortable": false},
                    {"data": "yyrq", "bSortable": false},
                    {"data": "batchName", "bSortable": false},
                    {"data": "medicCategory", "bSortable": false},
                    {"data": "mediclabel", "bSortable": false},
                    {"data": "printStateName", "bSortable": false},
                    {"data": "medical", "bSortable": false},
                    {"data": "deptName", "bSortable": false},
                    {"data": "isPack", "bSortable": false},
                    {"data": "orderNum", "bSortable": false},
                    {"data": "useType", "bSortable": false}
                ],
                columnDefs: [
                    {
                        targets: 2,
                        render: function (data, type, row) {
                            return row.batchName == null?"":row.batchName;
                        }
                    },
                    {
                        targets: 3,
                        render: function (data, type, row) {
                            return row.medicCategory ==null?"":row.medicCategory;
                        }
                    },
                    {
                        targets: 4,
                        render: function (data, type, row) {
                            return row.mediclabel ==null?"":row.mediclabel;
                        }
                    },
                    {
                        targets: 5,
                        render: function (data, type, row) {
                            return row.printStateName ==null?"":row.printStateName;
                        }
                    },
                    {
                        targets: 6,
                        render: function (data, type, row) {
                            return row.medical ==null?"":row.medical;
                        }
                    },
                    {
                        targets: 7,
                        render: function (data, type, row) {
                            return row.deptName ==null?"":row.deptName;
                        }
                    },
                    {
                        targets: 8,
                        render: function (data, type, row) {
                            return getShowText(row.isPack);
                        }
                    },
                    {
                        targets: 10,
                        render: function (data, type, row) {
                            return getShowText(row.useType);
                        }
                    },
                    {
                        targets: 11,
                        "width": "10%",
                        render: function (data, type, row) {
                            var opt = "";
                            <shiro:hasPermission name="PIVAS_BTN_929">
                            opt += " <a class='button btn-bg-green' href='javascript:edit(" + row.id + ");'><i class='am-icon-edit'></i><span>&nbsp;<spring:message code='common.edit'/></span></a>";
                            </shiro:hasPermission>
                            <shiro:hasPermission name="PIVAS_BTN_930">
                            opt += " <a class='button ui-btn-bg-red' href='javascript:del(" + row.id + ");'><i class='am-icon-remove'></i><span>&nbsp;<spring:message code='common.del'/></span></a>";
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
                url: '${pageContext.request.contextPath}/printLabelCon/initUpdateprtLabelCon',
                dataType: 'json',
                cache: false,
                data: [{name: 'id', value: ids}],
                success: function (data) {
                    if (data.success == false) {
                        $(".pReload").click();
                        layer.alert(data.description, {'title': '操作提示', icon: 0});
                        return;
                    }
                    else {
                        $("#id").val(data.id);
                        $("#nameEdit").val(data.name);
                        $("#yyrq").val(data.yyrq);
                        $("#batchID").val(data.batchid);
                        $("#batchName").val(data.batchName);
                        $("#medicCategory").val(data.medicCategory);
                        $("#mediclabel").val(data.mediclabel);
                        $("#medicCategoryID").val(data.medicCategoryID);
                        $("#medicLabelID").val(data.medicLabelID);
                        $("#printState").val(data.printState);
                        $("#medical").val(data.medical);
                        $("#medicalID").val(data.medicalID);
                        $("#deptCode").val(data.deptCode);
                        $("#deptName").val(data.deptName);
                        $("#isPack").val(data.isPack);
                        $("#orderNum").val(data.orderNum);
                        $("#orderOld").val(data.orderNum);
                        $("#useType").val(data.useType);
                    }
                    $("#editView-div").prev().find('.ui-dialog-title').text("修改打印瓶签条件");
                    $("#editView-div").dialog("open");
                },
                error: function () {
                }
            });
        }

        function del(ids) {
            layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                btn: ['确定', '取消'], icon: 3, title: '提示'
            }, function () {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/printLabelCon/delprtLabelCon',
                    dataType: 'json',
                    cache: false,
                    data: [{name: 'id', value: ids}],
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

        function getShowText(v){
            if (v == "0") {
                flag = "否";
            } else if (v == "1") {
                flag = "是";
            }
            return flag;
        }
        $(function () {
            sdfun.fn.trimAll("editView-div");
            var name = $("#nameEdit");
            var level = $("#levelEdit");
            var batchID = $("#batchID");
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
                height: 350,
                width: 700,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var bValid = true;
                        allFieldsInfo.removeClass("ui-state-error");
                        bValid = bValid && checkLength(name, 1, 32);

                        var order = $("#orderNum");
                        bValid = bValid && checkLength(order, 1, 32);
                        bValid = bValid && isPInt(order);

                        if (!bValid) {
                            return;
                        }
                        var url = "${pageContext.request.contextPath}/printLabelCon/addPrtLabelCon";
                        var params = {
                            "name": $("#nameEdit").val(),
                            "yyrq": $("#yyrq").val(),
                            "batchid": $("#batchID").val(),
                            "medicCategoryID": $("#medicCategoryID").val(),
                            "medicLabelID": $("#medicLabelID").val(),
                            "printState": $("#printState").val(),
                            "medicalID": $("#medicalID").val(),
                            "deptCode": $("#deptCode").val(),
                            "isPack": $("#isPack").val(),
                            "orderNum": $("#orderNum").val(),
                            "useType": $("#useType").val()
                        };
                        if ($("#id").val() && $("#id").val() != "") {
                            url = "${pageContext.request.contextPath}/printLabelCon/updatePrtLabelCon";
                            params["id"] = $("#id").val();

                            var oldP = $("#orderOld").val();
                            var newP = $("#orderNum").val();
                            if (oldP == newP) {
                                params["isSame"] = "true";
                            } else {
                                params["isSame"] = "false";
                            }
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

                $("#levelEdit").combobox("reset", '0');
                $("#editView-div").prev().find('.ui-dialog-title').text("增加打印瓶签条件");

                $("#isPack").val("1");
                $("#orderOld").val("");
                $("#useType").val("0");

                $("#editView-div").dialog("open");
            });

            $("#medicl-batch-div").dialog({
                autoOpen: false,
                height: 350,
                width: 380,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var ids = getFlexiGridSelectedRowText($("#batchFlexGrid"), 2);
                        var names = getFlexiGridSelectedRowText($("#batchFlexGrid"), 3);

                        $("#editView-form input[name='batchID']").val(ids);
                        $("#editView-form input[name='batchName']").val(names);
                        $(this).dialog("close");
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        $(this).dialog("close");
                    }
                },
                open: function () {
                    loadBatchGrid();
                },
                close: function () {
                }
            });

            $("#medicl-category-div").dialog({
                autoOpen: false,
                height: 350,
                width: 380,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var ids = getFlexiGridSelectedRowText($("#medicCategoryFlexGrid"), 2);
                        var names = getFlexiGridSelectedRowText($("#medicCategoryFlexGrid"), 3);
                        $("#editView-form input[name='medicCategoryID']").val(ids);
                        $("#editView-form input[name='medicCategory']").val(names);
                        $(this).dialog("close");
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        $(this).dialog("close");
                    }
                },
                open: function () {
                    loadMedicCategoryGrid();
                },
                close: function () {
                }
            });

            $("#medicl-label-div").dialog({
                autoOpen: false,
                height: 360,
                width: 380,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var ids = getFlexiGridSelectedRowText($("#labelFlexGrid"), 2);

                        var names = getFlexiGridSelectedRowText($("#labelFlexGrid"), 3);
                        $("#editView-form input[name='medicLabelID']").val(ids);
                        $("#editView-form input[name='mediclabel']").val(names);
                        $(this).dialog("close");
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        $(this).dialog("close");
                    }
                },
                open: function () {
                    loadLabelFlexGrid();
                },
                close: function () {
                }
            });

            $("#medicl-div").dialog({
                autoOpen: false,
                height: 360,
                width: 370,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var ids = getFlexiGridSelectedRowText($("#medicalFlexGrid"), 2);
                        var names = getFlexiGridSelectedRowText($("#medicalFlexGrid"), 3);

                        $("#editView-form input[name='medicalID']").val(ids);
                        $("#editView-form input[name='medical']").val(names);
                        $(this).dialog("close");
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        $(this).dialog("close");
                    }
                },
                open: function () {
                    loadMedicalFlexGrid();
                },
                close: function () {
                }
            });

            $("#dept-div").dialog({
                autoOpen: false,
                height: 350,
                width: 380,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var ids = getFlexiGridSelectedRowText($("#deptFlexGrid"), 2);
                        var names = getFlexiGridSelectedRowText($("#deptFlexGrid"), 3);

                        $("#editView-form input[name='deptCode']").val(ids);
                        $("#editView-form input[name='deptName']").val(names);
                        $(this).dialog("close");
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        $(this).dialog("close");
                    }
                },
                open: function () {
                    loadDeptFlexGrid();
                },
                close: function () {
                }
            });

            $("#editView-form input[name='batchName']").click(function () {
                $("#medicl-batch-div").dialog("open");
            });

            $("#editView-form input[name='medicCategory']").click(function () {
                $("#medicl-category-div").dialog("open");
            });

            $("#editView-form input[name='mediclabel']").click(function () {
                $("#medicl-label-div").dialog("open");
            });

            $("#editView-form input[name='medical']").click(function () {
                $("#medicl-div").dialog("open");
            });

            $("#editView-form input[name='deptName']").click(function () {
                $("#dept-div").dialog("open");
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

        function loadBatchGrid() {
            $("#batchFlexGrid").flexigrid({
                width: 350,
                height: 200,
                dataType: 'json',
                colModel: [
                    {display: 'id', name: 'id', width: 10, hide: 'true'},
                    {display: '批次', name: 'name', width: 300}
                ],
                resizable: false, 
                usepager: true,
                useRp: true,
                usepager: false, 
                autoload: false, 
                hideOnSubmit: true, 
                showcheckbox: true, 
                rowbinddata: true,
                numCheckBoxTitle: "<spring:message code='common.selectall'/>",
                gridClass: "bbit-grid",
                onSuccess: gridBatchSucc
            });
            $('#batchFlexGrid').flexOptions({
                newp: 1,
                extParam: [{name: 'batchID', value: batchID}],
                url: "${pageContext.request.contextPath}/printLabelCon/qryBatchs"
            }).flexReload();
        }

        function gridBatchSucc() {
            var batchIDStr = $("#batchID").val().trim();
            var batchIDArray = new Array();
            if (batchIDStr != null && batchIDStr != '') {
                batchIDArray = batchIDStr.split(',');
            }
            for (var i in batchIDArray) {
                var code = batchIDArray[i];
                var codeDiv = $("#batchFlexGrid").find("tr").find("td:eq(1):contains('" + code + "')");
                if (typeof(codeDiv) != "undefined") {
                    var parentTr = $(codeDiv).parent("tr");
                    $(parentTr).addClass("trSelected");
                    var box = $(parentTr).find("input[type='checkbox']");
                    box.attr("checked", true);
                }

            }
        }

        function loadMedicCategoryGrid() {
            $("#medicCategoryFlexGrid").flexigrid({
                width: 350,
                height: 200,
                dataType: 'json',
                colModel: [
                    {display: 'categoryId', name: 'categoryId', width: 10, hide: 'true'},
                    {display: '药品分类', name: 'categoryName', width: 300}
                ],
                resizable: false, 
                usepager: true,
                useRp: true,
                usepager: false, 
                autoload: false, 
                hideOnSubmit: true, 
                showcheckbox: true, 
                rowbinddata: true,
                numCheckBoxTitle: "<spring:message code='common.selectall'/>",
                gridClass: "bbit-grid",
                onSuccess: gridMedicCategorySucc
            });
            $('#medicCategoryFlexGrid').flexOptions({
                newp: 1,
                extParam: [{name: 'medicCategoryID', value: medicCategoryID}],
                url: "${pageContext.request.contextPath}/printLabelCon/qryMedicCategorys"
            }).flexReload();
        }

        function gridMedicCategorySucc() {
            var idStr = $("#medicCategoryID").val().trim();
            var idArray = new Array();
            if (idStr != null && idStr != '') {
                idArray = idStr.split(',');
            }
            for (var i in idArray) {
                var code = idArray[i];
                var codeDiv = $("#medicCategoryFlexGrid").find("tr").find("td:eq(1):contains('" + code + "')");
                if (typeof(codeDiv) != "undefined") {
                    var parentTr = $(codeDiv).parent("tr");
                    $(parentTr).addClass("trSelected");
                    var box = $(parentTr).find("input[type='checkbox']");
                    box.attr("checked", true);
                }

            }

        }

        function loadLabelFlexGrid() {
            $("#labelFlexGrid").flexigrid({
                width: 350,
                height: 200,
                dataType: 'json',
                colModel: [
                    {display: 'labelId', name: 'labelId', width: 10, hide: 'true'},
                    {display: '药品标签', name: 'labelName', width: 300}
                ],
                resizable: false, 
                usepager: true,
                useRp: true,
                usepager: false, 
                autoload: false, 
                hideOnSubmit: true, 
                showcheckbox: true, 
                rowbinddata: true,
                numCheckBoxTitle: "<spring:message code='common.selectall'/>",
                gridClass: "bbit-grid",
                onSuccess: gridLabelSucc
            });
            $('#labelFlexGrid').flexOptions({
                newp: 1,
                extParam: [{name: 'medicLabelID', value: medicLabelID}],
                url: "${pageContext.request.contextPath}/printLabelCon/qryMedicLabels"
            }).flexReload();
        }

        function gridLabelSucc() {
            var idStr = $("#medicLabelID").val().trim();
            var idArray = new Array();
            if (idStr != null && idStr != '') {
                idArray = idStr.split(',');
            }
            for (var i in idArray) {
                var code = idArray[i];
                var codeDiv = $("#labelFlexGrid").find("tr").find("td:eq(1):contains('" + code + "')");
                if (typeof(codeDiv) != "undefined") {
                    var parentTr = $(codeDiv).parent("tr");
                    $(parentTr).addClass("trSelected");
                    var box = $(parentTr).find("input[type='checkbox']");
                    box.attr("checked", true);
                }
            }
        }

        function loadMedicalFlexGrid() {
            $("#medicalFlexGrid").flexigrid({
                width: 350,
                height: 200,
                dataType: 'json',
                colModel: [
                    {display: 'medicamentsCode', name: 'medicamentsCode', width: 10, hide: 'true'},
                    {display: '药品名称', name: 'medicamentsName', width: 300}
                ],
                resizable: false, 
                usepager: true,
                useRp: true,
                usepager: false, 
                autoload: false, 
                hideOnSubmit: true, 
                showcheckbox: true, 
                rowbinddata: true,
                numCheckBoxTitle: "<spring:message code='common.selectall'/>",
                gridClass: "bbit-grid",
                onSuccess: gridMedicalSucc
            });
            $('#medicalFlexGrid').flexOptions({
                newp: 1,
                extParam: [{name: 'medicLabelID', value: medicLabelID}],
                url: "${pageContext.request.contextPath}/printLabelCon/qryMedicals"
            }).flexReload();
        }

        function gridMedicalSucc() {
            var idStr = $("#medicalID").val().trim();
            var idArray = new Array();
            if (idStr != null && idStr != '') {
                idArray = idStr.split(',');
            }
            for (var i in idArray) {
                var code = idArray[i];
                var codeDiv = $("#medicalFlexGrid").find("tr").find("td:eq(1):contains('" + code + "')");
                if (typeof(codeDiv) != "undefined") {
                    var parentTr = $(codeDiv).parent("tr");
                    $(parentTr).addClass("trSelected");
                    var box = $(parentTr).find("input[type='checkbox']");
                    box.attr("checked", true);
                }
            }
        }

        function loadDeptFlexGrid() {
            $("#deptFlexGrid").flexigrid({
                width: 350,
                height: 200,
                dataType: 'json',
                colModel: [
                    {display: 'deptCode', name: 'deptCode', width: 10, hide: 'true'},
                    {display: '病区', name: 'deptName', width: 300}
                ],
                resizable: false, 
                usepager: true,
                useRp: true,
                usepager: false, 
                autoload: false, 
                hideOnSubmit: true, 
                showcheckbox: true, 
                rowbinddata: true,
                numCheckBoxTitle: "<spring:message code='common.selectall'/>",
                gridClass: "bbit-grid",
                onSuccess: gridDeptSucc
            });
            $('#deptFlexGrid').flexOptions({
                newp: 1,
                extParam: [{name: 'deptCode', value: deptCode}],
                url: "${pageContext.request.contextPath}/printLabelCon/qryDepts"
            }).flexReload();
        }

        function gridDeptSucc() {
            var idStr = $("#deptCode").val().trim();
            var idArray = new Array();
            if (idStr != null && idStr != '') {
                idArray = idStr.split(',');
            }
            for (var i in idArray) {
                var code = idArray[i];
                var codeDiv = $("#deptFlexGrid").find("tr").find("td:eq(1):contains('" + code + "')");
                if (typeof(codeDiv) != "undefined") {
                    var parentTr = $(codeDiv).parent("tr");
                    $(parentTr).addClass("trSelected");
                    var box = $(parentTr).find("input[type='checkbox']");
                    box.attr("checked", true);
                }
            }
        }
    </script>
</head>
<body>

<div class="main-div" style="width:100%">

    <div id="qryView-div">
        <div class="search-div">
            <shiro:hasPermission name="PIVAS_BTN_928">
                <a class="ui-search-btn ui-btn-bg-green" id="addDataDicBtn"><i class="am-icon-plus"></i><span><spring:message code='common.add'/></span></a>&nbsp;
            </shiro:hasPermission>
        </div>
        <table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
            <thead>
            <tr>
                <th>条件名</th>
                <th><spring:message code="pivas.yzyd.yyrq"/></th>
                <th><spring:message code="pivas.yzyd.zxbc"/></th>
                <th><spring:message code="medicaments.categoryId"/></th>
                <th>药品标签</th>
                <th>打印状态</th>
                <th><spring:message code="medicaments.medicamentsMenstruum"/></th>
                <th>病区</th>
                <th>打包条件</th>
                <th>优先级</th>
                <th>是否用于药品统计</th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="修改打印瓶签条件" align="center"
     style="display: none;">
    <form id="editView-form" action="" method="post">
        <input type="hidden" id="id" name="id"/>
        <div class="popup">
            <div class="row">
                <div class="column columnL">
                    <label class="tit">条件名</label>
                    <input type="text" class="edit" name="name" id="nameEdit" maxlength="32"
                           title="<spring:message code='common.op.remind2'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column columnR">
                    <label class="tit"><spring:message code="pivas.yzyd.yyrq"/></label>
                    <select name="yyrq" id="yyrq" readonly empty="false">
                        <option value="2">昨日</option>
                        <option value="0">今日</option>
                        <option value="1">明日</option>
                    </select>
                    <span class="mand">*</span>
                </div>
            </div>
            <div class="row">
                <div class="column columnL">
                    <label class="tit">批次</label>
                    <input type="hidden" name="batchID" id="batchID"/>
                    <input type="text" name="batchName" id="batchName" readonly="readonly" class="hand"/>

                </div>
                <div class="column columnR">
                    <label class="tit">药品分类</label>
                    <input type="hidden" name="medicCategoryID" id="medicCategoryID"/>
                    <input type="text" name="medicCategory" id="medicCategory" readonly="readonly" class="hand"/>
                </div>
            </div>

            <div class="row">
                <div class="column columnL">
                    <label class="tit">药品标签</label>
                    <input type="hidden" name="medicLabelID" id="medicLabelID"/>
                    <input type="text" name="mediclabel" id="mediclabel" readonly="readonly" class="hand"/>
                </div>
                <div class="column columnR">
                    <label class="tit">打印状态</label>
                    <select name="printState" id="printState" readonly empty="false" style="width: 184px">
                        <option value=""><spring:message code="common.select"/></option>
                        <option value="1"><spring:message code="common.no"/></option>
                        <option value="0"><spring:message code="common.yes"/></option>
                    </select>
                </div>
            </div>

            <div class="row">
                <div class="column columnL">
                    <label class="tit">溶媒</label>
                    <input type="hidden" name="medicalID" id="medicalID"/>
                    <input type="text" name="medical" id="medical" readonly="readonly" class="hand"/>
                </div>

                <div class="column columnR">
                    <label class="tit">病区</label>
                    <input type="hidden" name="deptCode" id="deptCode"/>
                    <input type="text" name="deptName" id="deptName" readonly="readonly" class="hand"/>
                </div>
            </div>

            <div class="row">
                <div class="column columnL">
                    <label class="tit">打包条件</label>
                    <select name="isPack" id="isPack">
                        <option value="0"><spring:message code="common.no"/></option>
                        <option value="1" selected><spring:message code="common.yes"/></option>
                    </select>
                </div>
                <div class="column columnR">
                    <label class="tit">用于药品统计</label>
                    <select name="useType" id="useType" readonly empty="false">
                        <option value="0" selected><spring:message code="common.no"/></option>
                        <option value="1"><spring:message code="common.yes"/></option>
                    </select>
                </div>

            </div>

            <div class="row">

                <div class="column columnL">
                    <label class="tit">优先级</label>
                    <input type="text" name="orderNum" id="orderNum"
                           title="<spring:message code='common.plzInputInteger1'/>"/>
                    <input type="hidden" id="orderOld"/>
                    <span class="mand">*</span>
                </div>
            </div>
        </div>
    </form>
</div>
<div id="medicl-batch-div" title="选择批次" style="display: none;">
    <table id="batchFlexGrid" style="display: block;margin: 0px;"></table>
</div>

<div id="medicl-category-div" title="选择药品分类" style="display: none;">
    <table id="medicCategoryFlexGrid" style="display: block;margin: 0px;"></table>
</div>

<div id="medicl-label-div" title="选择药品标签" style="display: none;">
    <table id="labelFlexGrid" style="display: block;margin: 0px;"></table>
</div>

<div id="medicl-div" title="选择溶媒" style="display: none;">
    <table id="medicalFlexGrid" style="display: block;margin: 0px;"></table>
</div>

<div id="dept-div" title="选择病区" style="display: none;">
    <table id="deptFlexGrid" style="display: block;margin: 0px;"></table>
</div>

</body>

</html>