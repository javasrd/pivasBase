<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/pivas/css/fm.selectator.jquery.css"/>
<script src="${pageContext.request.contextPath}/assets/pivas/js/fm.selectator.jquery.js"></script>
<style>
    .Tcontrol td {
        text-align: center;
    }

    .search-div button {
        margin-left: 0px;
        margin-right: 10px;

    }

    .button_a {
        background: #EAB702;
        color: #ffffff;
    }

    .myTitleThLeft {
        height: 35px;
        width: 50%;
        background: #DBE3F3;
        border: 1px solid #C5C6C9;
    }

    .myTitleThRight {
        height: 35px;
        width: 50%;
        background: #DBE3F3;
        border-top: 1px solid #C5C6C9;
        border-right: 1px solid #C5C6C9;
        border-bottom: 1px solid #C5C6C9;
    }

    .myTitletd_left {
        height: 35px;
        text-align: center;
        width: 50%;
        background: #ffffff;
        border: 1px solid #C5C6C9;
    }

    .myTitletd_right {
        height: 35px;
        text-align: center;
        width: 50%;
        background: #ffffff;
        border-top: 1px solid #C5C6C9;
        border-right: 1px solid #C5C6C9;
        border-bottom: 1px solid #C5C6C9;
    }

    .myTitle_checked {
        background: #FFEECE;
    }

    .titleChecked {

    }
</style>
<head>
    <script>
        var datatable;
        var qryParam;
        var yzTitleArray = ["床号", "病区", "病人", "年龄", "频次", "用药名称", "单次剂量", "剂量单位", "药品数量", "包装单位", "医嘱类型", "医嘱状态", "审方药师", "审方日期", "审方结果", "审方问题"];
        var yzTitleValArray = ["bedno", "wardname", "patname", "age", "freqcode", "drugname", "dose", "doseunit", "quantity", "medicamentspackingunit", "yzlx", "yzzt", "sfysmc", "sfrq", "yzshzt", "yzshbtglx"];
        var batchTitleArray = ["床号", "执行批次", "组号", "用药时间", "病区", "频次", "病人", "年龄", "输液量（ml）", "药品名称", "单次剂量", "剂量单位", "药品数量", "单位",
            "打印标志", "开立时间", "异常信息", "操作记录", "批次确认人"];
        var batchTitleValArray = ["bedno", "zxbc", "parentNo", "yyrqS", "wardName", "freqCode", "patname", "age", "transfusion", "drugname",
            "dose", "doseUnit", "quantity", "medicamentsPackingUnit", "dybz", "startTimeS", "ydreorderMess", "operationLog", "yzconfigUname"];

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
                    "url": "${pageContext.request.contextPath}/doctorShowTitle/getTilteList",
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
//                    {"data": "confId", "bSortable": false},
                    {"data": "confName", "bSortable": false},
                    {"data": "confType", "bSortable": false},
                    {"data": "useBy", "bSortable": false},
                    {"data": "createTime", "bSortable": false}
                ],
                columnDefs: [
                    {
                        targets:1,
                        render: function (data, type, row) {
                            return row.confType === 1 ? "医嘱" : "批次";
                        }
                    },
                    {
                        targets: 4,
                        "width": "15%",
                        render: function (data, type, row) {
                            var opt = "";
                            <shiro:hasPermission name="PIVAS_BTN_954">
                            opt +=  "<a class='button ui-bg-green' style='text-decoration:none;' href='#' onclick='edit(" + JSON.stringify(row) + ")' ><i class='am-icon-edit'></i><span>&nbsp;<spring:message code='common.edit'/></span></a>";
                            </shiro:hasPermission>
                            <shiro:hasPermission name="PIVAS_BTN_952">
                            opt += " <a class='button ui-btn-bg-red' href='javascript:del(" + row.confId + ");'><i class='am-icon-remove'></i><span>&nbsp;<spring:message code='common.del'/></span></a>";
                            </shiro:hasPermission>
                            return opt;
                        }
                    }

                ],
            });
        }

        function edit(row) {
            var cid = row.confId;
            var configName = row.confName;
            var configType = row.confType;
            var userNames = row.useBy;
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/doctorShowTitle/getTitleDetail',
                dataType: 'json',
                cache: false,
                data: [{name: 'confId', value: cid}],
                success: function (data) {
                    if (data.success == false) {
                        layer.alert(data.description, {'title': '操作提示', icon: 0});
                        return;
                    }
                    else {
                        $("#confId").val(cid);
                        $("#input_configname").val(configName);
                        $("#configTitleType").val(configType);
                        $("#input_configuser option").each(function (index) {
                            $(this).remove();
                        });
                        var userArray = userNames.split(",");
                        for (var i = 0; i < userArray.length; i++) {
                            $("#input_configuser").append("<option value='" + userArray[i] + "' selected >" + userArray[i] + "</option>");
                        }
                        getAllAccounts();
                        if ($("#input_configuser").data('selectator') === undefined) {
                            $("#input_configuser").selectator({
                                showAllOptionsOnFocus: true,
                                keepOpen: true
                            });
                        }
                        var selectArray = [];
                        if (configType == 1) {
                            selectArray = yzTitleValArray;
                        } else {
                            selectArray = batchTitleValArray;
                        }
                        $.each(data, function (index, value) {
                            var selectIndex = $.inArray(value.field, selectArray);
                            $("#addTr").before(
                                "<tr><td class='myTitletd_left' onclick='showSingleChecked(this)'>" + getSelectHtml(selectIndex) + "</td>" +
                                "<td class='myTitletd_right' onclick='showSingleChecked(this)'><a onclick='delRow(this)'><spring:message code='common.del'/></a></td></tr>");
                        });

                        $("#editView-div").prev().find('.ui-dialog-title').text("修改配置项");
                        $("#editView-div").dialog("open");
                    }
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
                    url: '${pageContext.request.contextPath}/doctorShowTitle/delData',
                    dataType: 'json',
                    cache: false,
                    data: [{name: 'confIds', value: ids}],
                    success: function (data) {
                        qryList();
                        layer.alert(data.description, {'title': '操作提示', icon: 0});
                    },
                    error: function () {
                        layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
                    }
                });
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

            $('#editView-form').ajaxForm({
                dataType: "json",
                success: function (data) {
                    if (data.success || data.code == '<%=ExceptionCodeConstants.RECORD_DELETE%>') {
                        $("#editView-div").dialog("close");
                        qryList();
                    }
                    layer.alert(data.description, {'title': '操作提示', icon: 0});

                }
            });

            $("#editView-div").dialog({
                autoOpen: false,
                height: 450,
                width: 900,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        if ($("#input_configname").val() == "") {
                            layer.alert("名称不能为空", {'title': '操作提示', icon: 0});
                            return;
                        }

                        if ($("#input_configuser").val() == null) {
                            layer.alert("用户不能为空", {'title': '操作提示', icon: 0});
                            return;
                        }

                        if ($("#mytbody tr[id!='addTr']").length === 0) {
                            layer.alert("请添加要展示的列", {'title': '操作提示', icon: 0});
                            return;
                        } else {
                            var valArray = [];
                            $("#mytbody tr[id!='addTr']").each(function (i, v) {
                                valArray.push($(this).children().eq(0).children("select").val());
                            });

                            var titleArray;
                            var titleValArray;
                            if ("1" == $("#configTitleType").val()) {
                                titleArray = yzTitleArray;
                                titleValArray = yzTitleValArray;
                            } else {
                                titleArray = batchTitleArray;
                                titleValArray = batchTitleValArray;
                            }

                            var repeatStr = "";
                            var valString = valArray.join(",") + ",";
                            for (var i = 0; i < valArray.length; i++) {
                                if (valString.replace(valArray[i] + ",", "").indexOf(valArray[i] + ",") > -1) {
                                    repeatStr = valArray[i];
                                    break;
                                }
                            }
                            if (repeatStr.length != 0) {
                                var index = $.inArray(repeatStr, titleValArray);
                                repeatStr = titleArray[index];
                                layer.alert('有重复列名"' + repeatStr + '",请修改', {'title': '操作提示', icon: 0});
                                return;
                            }
                        }
                        var url = "${pageContext.request.contextPath}/doctorShowTitle/addData";
                        var id = $("#editView-div").find("input[name='confId']").val();
                        if (id && id != "") {
                            url = "${pageContext.request.contextPath}/doctorShowTitle/updateData";
                        }
                        getAllTitleListData();
                        $("#editView-form").attr("action", url);
                        $("#editView-form").submit();
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        $(this).dialog("close");
                    }
                },
                close: function () {
                    $("#input_configuser option").each(function () {
                        $(this).removeAttr("selected");
                    });
                    $("#input_configuser").selectator("destroy");
                    $('#ruleListTable tbody tr').each(function () {
                        if ($(this).attr("id") != "addTr") {
                            $(this).remove();
                        }
                    });
                }
            });

            //新增按钮
            $("#addBtn").bind("click", function () {
                $("#editView-div").prev().find('.ui-dialog-title').text("新增配置项");
                $("#confId").val("");
                $("#titleList").val("");
                $("#input_configname").val("");

                $("#input_configuser option").each(function () {
                    $(this).remove();
                });
                getAllAccounts();
                if ($("#input_configuser").data('selectator') === undefined) {
                    $("#input_configuser").selectator({
                        showAllOptionsOnFocus: true,
                        keepOpen: true
                    });
                }
                $("#editView-div").dialog("open");
            });

            $("#configTitleType").bind("change", function () {
                $("#mytbody tr[id!='addTr']").each(function (i, v) {
                    $(this).remove();
                });
            });
        });

        function getAllAccounts() {
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/doctorShowTitle/getAccountList",
                dataType: "json",
                async: false,
                success: function (data) {
                    $.each(data, function (i, v) {
                        $("#input_configuser").append("<option value='" + v + "'>" + v + "</option>")
                    });
                }
            });
        }

        function getAllTitleListData() {
            var titleListStr = [];
            $("#mytbody tr[id!='addTr']").each(function (index) {
                titleListStr.push($(this).children().eq(0).children().val());
                titleListStr.push(":");
                titleListStr.push(index);
                titleListStr.push("@@");
            });
            var listStr = titleListStr.join("");
            $("#titleList").val(listStr.substring(0, listStr.lastIndexOf("@@")));
        }

        function getSelectHtml(selectedVal) {
            var titleArray;
            var titleValArray;
            var selectOption = [];
            selectOption.push("<select onclick='stopPropaga(event)'>");
            if ("1" == $("#configTitleType").val()) {
                titleArray = yzTitleArray;
                titleValArray = yzTitleValArray;
            } else {
                titleArray = batchTitleArray;
                titleValArray = batchTitleValArray;
            }
            $.each(titleArray, function (index, value) {
                if (selectedVal != -1 && selectedVal == index) {
                    selectOption.push("<option value='" + titleValArray[index] + "' selected >" + value + "</option>");
                } else {
                    selectOption.push("<option value='" + titleValArray[index] + "'>" + value + "</option>");
                }

            });
            selectOption.push("</select>");
            return selectOption.join("");
        }

        //动态添加行
        function addRow() {
            var html = [];
            html.push("<tr>");
            html.push("<td class='myTitletd_left' onclick='showSingleChecked(this)'> ");
            html.push(getSelectHtml(-1));
            html.push("</td>");
            html.push("<td class='myTitletd_right' onclick='showSingleChecked(this)'>");
            html.push('<a onclick="delRow(this)"><spring:message code="common.del"/></a>');
            html.push("</td>");
            html.push("</tr>");
            $("#addTr").before(html.join(""));
        }

        function showSingleChecked(obj) {
            $(obj).parent().siblings().removeClass("titleChecked");
            $(obj).parent().siblings().children().each(function () {
                $(this).removeClass("myTitle_checked");
            });

            if ($(obj).hasClass("myTitle_checked")) {
                $(obj).parent().removeClass("titleChecked");
                $(obj).removeClass("myTitle_checked");
                $(obj).siblings().removeClass("myTitle_checked");
            } else {
                $(obj).parent().addClass("titleChecked");
                $(obj).addClass("myTitle_checked");
                $(obj).siblings().addClass("myTitle_checked");
            }

        }

        function stopPropaga(obj) {
            event.stopPropagation();
        }

        //删除行
        function delRow(obj) {
            $(obj).parent().parent().remove();
        }
        function getSelectedRow() {
            var arr = new Array(0);
            $(".datatable > tbody").find("input:checked").each(function (i, v) {
                arr.push($(v).val())
            });
            return arr;
        }
        function qryList(param) {
         /*   $('#flexiGridId').flexOptions({
                newp: 1,
                extParam: param || [],
                url: "${pageContext.request.contextPath}/doctorShowTitle/getTilteList"
            }).flexReload();*/
            qryParam = param;
            datatable.ajax.reload();
        }

        //调整优先级
        function updateSort(type) {
            var myTitleChecked = [];
            $("#mytbody tr:not('#addTr') td:first-child").each(function () {
                if ($(this).hasClass("myTitle_checked")) {
                    myTitleChecked.push("1");
                }
            });
            if (myTitleChecked.length == 0) {
                layer.alert("请选择要调整的列名", {'title': '操作提示', icon: 0});
                return;
            }

            var row1 = $("#mytbody .titleChecked");
            var row2;
            if (type == "up") {
                row2 = row1.prev();
            } else {
                row2 = row1.next();
            }
            if (row2.length > 0 && $(row2).attr("id") != "addTr") {
                if (type == "up") {
                    row2.insertAfter(row1);
                } else {
                    row1.insertAfter(row2);
                }
            } else {
                layer.alert(type == "up" ? "已是第一条，无法上移" : "已是最后一条，无法下移", {'title': '操作提示', icon: 0});
            }
        }

    </script>
</head>
<body>

<div class="main-div" style="width:100%">
    <div data-qryMethod funname="qryList" class="ui-search-header ui-search-box"  id="qryView-div" style="display: inline; ">
        <div style="float:right ">
        <input placeholder="名称" name="confName" size="20" data-cnd="true">&nbsp;&nbsp;
        <button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
            <button  class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
        </div>
        <shiro:hasPermission name="PIVAS_BTN_951">
            <a class="ui-search-btn ui-btn-bg-green" id="addBtn"><i class="am-icon-plus"></i><span><spring:message code='common.add'/></span></a>
        </shiro:hasPermission>

    </div>
    <%-- 搜索条件--结束 --%>
    <table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
        <thead>
        <tr>
            <th>配置名称</th>
            <th>配置类型</th>
            <th>用户</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>

<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="<spring:message code='pivas.measureunit.update'/>" align="center">
    <form id="editView-form" action="" method="post">
        <input type="hidden" id="confId" name="confId"/>
        <input type="hidden" id="titleList" name="titleList"/>
        <div class="popup">
            <div class="popup" style="">
                <div class="row" style="width:100%;height:100%;border-bottom:1px dashed #969696;">
                    <table style="width:100%;height:100%;margin:10px;">
                        <tr style="width:100%;height:100%;">
                            <td style="width:6%;text-align: right">
                                <span>名称：</span>
                            </td>
                            <td style="width:20%;">
                                <input id="input_configname" name="confName" type="text" width="100%;"
                                       style="border:1px solid #969696;padding:0px 5px;"/>
                            </td>
                            <td>
                                <span class="mand" style="margin-left:5px;color:#ff0000">*</span>
                            </td>
                            <td style="width:6%;text-align: right">
                                <span>类型：</span>
                            </td>
                            <td style="width:20%;">
                                <select id="configTitleType" name="confType" style="width:80%;height:25px">
                                    <option value="1">医嘱</option>
                                </select>
                            </td>
                            <td style="width:6%;text-align: right">
                                <span>用户：</span>
                            </td>
                            <td style="width:40%;">
                                <select id="input_configuser" name="useByNames" multiple readonly
                                        style="width:100%;height:25px">
                                </select>
                            </td>
                            <td style="padding-left:10px;">
                                <span class="mand" style="color:#ff0000">*</span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <div style="height:35px;">
                <div style="float:left;">
			    	<span style="text-align:left;font-size:150%;margin-bottom: 10px;font-size:18px;">
			    		列表明细
			    	</span>
                </div>

                <div style="float:right;background:#EBB700;padding:2px;margin-right: 20px">
                    <img src="${pageContext.request.contextPath}/assets/pivas/images/down.png"
                         onclick="updateSort('down')">
                </div>

                <div style="float:right;background:#EBB700;padding:2px;margin-right: 15px">
                    <img src="${pageContext.request.contextPath}/assets/pivas/images/up.png" onclick="updateSort('up')">
                </div>
            </div>
            <div class="layeForm" style="margin: 0;overflow: auto;width: 100%;height:230px;">
                <table id="ruleListTable" style="vertical-align: top; margin-left: 0px;">
                    <thead>
                    <tr class="" style="font-size:14px;">
                        <th class="myTitleThLeft">列名</th>
                        <th class="myTitleThRight"><spring:message code="common.op"/></th>
                    </tr>
                    </thead>
                    <tbody id="mytbody">
                    <tr id="addTr">
                        <td class="myTitletd_left"><a onclick="addRow();">添加</a></td>
                        <td class="myTitletd_right"></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
</div>
</body>

</html>