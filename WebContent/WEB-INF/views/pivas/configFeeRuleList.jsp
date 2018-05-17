<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet"/>
<style>
    .Tcontrol td {
        text-align: center;
    }
</style>
<head>
    <script>
        var configFeeSelectHtml = "";
        var oldName = "";
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
                    "url": "${pageContext.request.contextPath}/configFeeRule/getFeeRuleList",
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
                    {"data": "ruleName", "bSortable": false},
                    {"data": "drugTypeName", "bSortable": false},
                    {"data": "drugName", "bSortable": false},
                    {"data": "volume", "bSortable": false}
                ],
                columnDefs: [
                    {
                        targets: 0,
                        render: function (data, type, row) {
                            return row.ruleName ==null?"":row.ruleName ;
                        }
                    },
                    {
                        targets: 1,
                        render: function (data, type, row) {
                            return row.drugTypeName ==null?"":row.drugTypeName ;
                        }
                    },
                    {
                        targets: 2,
                        render: function (data, type, row) {
                            return row.drugName ==null?"":row.drugName ;
                        }
                    },
                    {
                        targets: 3,
                        render: function (data, type, row) {
                            return row.volume ==null?"":row.volume ;
                        }
                    },
                    {
                        targets: 4,
                        "width": "15%",
                        render: function (data, type, row) {
                            var opt = "";
                            <shiro:hasPermission name="PIVAS_BTN_285">
                            opt +=  "<a class='button btn-bg-green' href='javascript:edit(" + row.gid + ");'><i class='am-icon-edit'></i><span>&nbsp;<spring:message code='common.edit'/></span></a>";
                            </shiro:hasPermission>
                            <shiro:hasPermission name="PIVAS_BTN_283">
                            opt += " <a class='button ui-btn-bg-red' href='javascript:del(" + row.gid + ");'><i class='am-icon-remove'></i><span>&nbsp;<spring:message code='common.del'/></span></a>";
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
                url: '${pageContext.request.contextPath}/configFeeRule/initUpdateFeeRule',
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

                        $("#editView-div input").each(function () {
                            var name = $(this).attr("name");
                            $(this).val(data[name]);
                        });
                        $("#editView-div select").each(function () {
                            var name = $(this).attr("name");
                            $(this).combobox("reset", data[name]);
                        });

                        oldName = data.ruleName;
                        if (data.configFeeDetailList) {
                            $.each(data.configFeeDetailList, function (i, d) {
                                addRow();
                                var $tr = $('#ruleListTable tbody tr:eq(' + i + ')');
                                $tr.find("select[name$='].costCode']").val(sdfun.fn.ifnull(d["costCode"]));
                                $tr.find("input[name$='].amount']").val(sdfun.fn.ifnull(d["amount"]));
                                $tr.find("select[name$='].rate']").val(sdfun.fn.ifnull(d["rate"]));
                            });
                        }
                    }

                    $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.confingfee.update'/>");
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
                    url: '${pageContext.request.contextPath}/configFeeRule/delFeeRule',
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
        }
        $(function () {
            $("#editView-form input[readonly='readonly'][name!='drugName']").addClass("gray");
            $("#editView-form select").combobox();
            sdfun.fn.trimAll("editView-div");
            $("#editView-form select").combobox();
            querySelectData();
            var ruleName = $("#ruleNameEdit");
            var volume = $("#volumeEdit");
            var drugCode = $("#drugCodeEdit");
            var allFieldsInfo = $([]).add(ruleName).add(volume).add(drugCode);

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

            // 修改建筑Form的ajax交互
            $('#editView-form').ajaxForm({
                dataType: "json",
                success: function (data) {
                    if (data.success || data.code == '<%=ExceptionCodeConstants.RECORD_DELETE%>') {
                        $('#editView-div').dialog('close');
                        qryList();
                    }
                    //弹出提示信息
                    layer.alert(data.description, {'title': '操作提示', icon: 0});
                }
            });

            $("#editView-div").dialog({
                autoOpen: false,
                height: 430,
                width: 900,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var bValid = validateInput("editView-div") == null ? true : false;
                        if (!bValid) {
                            return;
                        }

                        var drugCodes = $("#drugCodeEdit").val();
                        var drugCodeArray = drugCodes.split(",");
                        if (drugCodeArray.length > 20) {
                            layer.alert('最多选取20个药品', {'title': '操作提示', icon: 0});
                            return;
                        }

                        var url = "${pageContext.request.contextPath}/configFeeRule/addcfgFeeRule";

                        var id = $("#editView-div").find("input[name='gid']").val();
                        if (id && id != "") {

                            $("#isSameName").val("");
                            var newName = $("#ruleNameEdit").val();
                            if (oldName == newName) {
                                $("#isSameName").val("true");
                            }

                            url = "${pageContext.request.contextPath}/configFeeRule/mdfFeeRule";
                        }

                        $("#editView-form").attr("action", url);

                        _prepareIndex_();
                        $("#editView-form").submit();
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        $(this).dialog("close");
                    }
                },
                close: function () {
                    $('#ruleListTable tbody tr').each(function () {
                        if ($(this).attr("id") != "addTr") {
                            $(this).remove();
                        }
                    });
                }
            });

            //新增按钮
            $("#addDataDicBtn").bind("click", function () {
                $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.confingfeerule.add'/>");
                resetForm("editView-form");
                querySelectData("add");
            });

            $("#medicl-div").dialog({
                autoOpen: false,
                height: 450,
                width: 500,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {

                        var ids = getFlexiGridSelectedRowText($("#labelFlexGrid"), 2);
                        var names = getFlexiGridSelectedRowText($("#labelFlexGrid"), 3);
                        $("#editView-form input[name='drugCode']").val(ids);
                        $("#editView-form input[name='drugName']").val(names);
                        $(this).dialog("close");
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        $(this).dialog("close");
                    }
                },
                open: function () {
                    $("#ypmcid").val("");
                    loadLabelGrid();
                },
                close: function () {
                    $("#ypmcid").val("");
                }
            });

            $("#editView-form input[name='drugName']").click(function () {
                $("#medicl-div").dialog("open");
            });
        });

        // 下拉数据查询
        function querySelectData(type) {
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/configFeeRule/querySelectDatareq',
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data.success == true) {
                        var html = [];
                        html.push("<option value=\"\">请选择</option>");
                        //药品分类
                        $.each(data.mediCategoryList, function (i, d) {
                            html.push('<option value="' + d.categoryId + '">');
                            html.push(sdfun.fn.htmlDecode(d.categoryName));
                            html.push('</option>');
                        });

                        $("#drugTypeSelect").html(html.join(""));

                        html = [];
                        //药品列表
                        $.each(data.medicamentsList, function (i, d) {
                            html.push('<option value="' + d.medicamentsId + '">');
                            html.push(sdfun.fn.htmlDecode(d.medicamentsName));
                            html.push('</option>');
                        });
                        $("#drugSelect").html(html.join(""));

                        //配置费/材料费
                        html = [];
                        html.push("<select name='configFeeDetailList[index].costCode'");
                        html.push(" onchange='changeCostCode(this);'");
                        html.push(" style='width:130px;' empty='false' readonly >");
                        html.push('<option value=""><spring:message code="common.select"/></option>');
                        $.each(data.configFeeList, function (i, d) {
                            html.push('<option value="' + d.costCode + '">');
                            html.push(sdfun.fn.htmlDecode(d.costName));
                            html.push('</option>');
                        });
                        html.push("</select>");
                        configFeeSelectHtml = html.join("");
                    }
                    resetForm("editView-form");
                    if (type == "add") {
                        $("#editView-div").dialog("open");
                    }
                }
            });
        }

        function changeCategoryId() {
            $("#drugCodeEdit").val('');
            var drugTypeCode = $("#drugTypeSelect").val();
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/configFeeRule/querySelectDatareq',
                dataType: 'json',
                cache: false,
                data: [{name: 'drugTypeCode', value: drugTypeCode}],
                success: function (data) {
                    if (data.success == true) {
                        html = [];
                        //药品列表
                        $.each(data.medicamentsList, function (i, d) {
                            html.push('<option value="' + d.medicamentsId + '">');
                            html.push(sdfun.fn.htmlDecode(d.medicamentsName));
                            html.push('</option>');
                        });
                        $("#drugSelect").html(html.join(""));

                        $("#drugNameEdit").val("");
                    }
                }
            });
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

        //动态添加行
        function addRow() {
            var html = [];
            html.push("<tr>");
            html.push("<td>");
            html.push(configFeeSelectHtml);
            html.push('<span style="color:red; margin-left: 5px;">*</span>');
            html.push("</td>");
            html.push("<td>");
            html.push("<input name='configFeeDetailList[index].amount' empty='false' regex='int' minvalue='1' maxvalue = '2147483647' ");
            html.push(' title="<spring:message code="common.plzInputZInteger"/>"/>');
            html.push('<span style="color:red; margin-left: 5px;">*</span>');
            html.push("</td>");
            html.push("<td>");
            html.push("<select name='configFeeDetailList[index].rate' readonly>");
            html.push('<option value="0"><spring:message code="common.yes"/></option>');
            html.push('<option value="1"><spring:message code="common.no"/></option>');
            html.push("</select>");
            html.push("</td>");
            html.push("<td>");
            html.push('<a onclick="delRow(this)"><spring:message code="common.del"/></a>');
            html.push("</td>");
            html.push("</tr>");
            $("#addTr").before(html.join(""));
        }

        //删除行
        function delRow(obj) {
            $(obj).parent().parent().remove();
        }

        function process(v) {
            if (v == 0) {
                return "<spring:message code="common.yes"/>";
            } else if (v == 1) {
                return "<spring:message code="common.no"/>";
            }
        }

        function _prepareIndex_() {
            $('#ruleListTable tbody tr').each(function () {
                var index = this.rowIndex - 1;
                $(this).find(":input[name*='].']").each(function () {
                    this.name = this.name.replace(/\[(\d+|index)\]/, '[' + index + ']');
                });
            });
        }

        function changeCostCode(obj) {
            var repeat = false;
            $("#ruleListTable select").each(function () {
                var val = $(this).val();
                if (val != "" && this != obj && val == $(obj).val()) {
                    $(obj).val("");
                    repeat = true;
                    return false;
                }
            });

            if (repeat) {
                layer.alert("<spring:message code="pivas.confingfeerule.costcoderepaeat"/>", {'title': '操作提示', icon: 0});
                return false;
            }
        }

        function loadLabelGrid() {
            $("#labelFlexGrid").flexigrid({
                width: 470,
                height: 250,
                dataType: 'json',
                colModel: [
                    {display: 'medicamentsId', name: 'medicamentsId', width: 50, hide: 'true'},
                    {display: '<spring:message code="pivas.confingfeerule.drug"/>', name: 'medicamentsName', width: 400}
                ],
                resizable: false, //resizable table是否可伸缩
                usepager: true,
                useRp: true,
                usepager: false, //是否分页
                autoload: false, //自动加载，即第一次发起ajax请求
                hideOnSubmit: true, //是否在回调时显示遮盖
                showcheckbox: true, //是否显示多选框
                rowbinddata: true,
                numCheckBoxTitle: "<spring:message code='common.selectall'/>",
                gridClass: "bbit-grid",
                onSuccess: gridSucc
            });
            var categoryId = $("#drugTypeSelect").val();

            $('#labelFlexGrid').flexOptions({
                newp: 1,
                extParam: [{name: 'categoryId', value: categoryId}],
                url: "${pageContext.request.contextPath}/configFeeRule/qryDgs"
            }).flexReload();


            $("#qryButton").click(function () {
                qryButton();
            });
        }

        function qryButton() {
            var categoryId = $("#drugTypeSelect").val();
            var ypName = $("#ypmcid").val().trim();
            $('#labelFlexGrid').flexOptions({
                newp: 1,
                extParam: [{name: 'categoryId', value: categoryId}, {name: 'medicamentsName', value: ypName}],
                url: "${pageContext.request.contextPath}/configFeeRule/qryDgs"
            }).flexReload();

        }

        function gridSucc() {

            var drugCodeStr = $("#drugCodeEdit").val().trim();
            var drugCodeArray = new Array();
            if (drugCodeStr != null && drugCodeStr != '') {
                drugCodeArray = drugCodeStr.split(',');
            }
            for (var i in drugCodeArray) {
                var code = drugCodeArray[i];
                var codeDiv = $("#labelFlexGrid").find("tr").find("td:eq(1):contains('" + code + "')");
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
    <div data-qryMethod funname="qryList" class="ui-search-header ui-search-box" id="qryView-div" style="display: inline; ">
        <div style="float:right ">
        <input placeholder="规则名称" name="ruleNames" size="8" data-cnd="true">&nbsp;&nbsp;
        <input placeholder="药品分类" name="drugTypeNames" size="8" data-cnd="true">&nbsp;&nbsp;
        <input placeholder="容积" name="volumes" size="8" data-cnd="true">&nbsp;&nbsp;
            <button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
            <button class="ui-search-btn ui-btn-bg-yellow"  onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
        </div>
        <shiro:hasPermission name="PIVAS_BTN_282"><a class="ui-search-btn ui-btn-bg-green" id="addDataDicBtn"><i class="am-icon-plus"></i><span><spring:message
                code='common.add'/></span></a></shiro:hasPermission>&nbsp;
    </div>
    <%-- 搜索条件--结束 --%>
    <div class="tbl">
        <table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
            <thead>
            <tr>
                <th><spring:message code="pivas.confingfeerule.rulename"/></th>
                <th><spring:message code="pivas.confingfeerule.drugtype"/></th>
                <th><spring:message code="pivas.confingfeerule.drug"/></th>
                <th><spring:message code="pivas.confingfeerule.volume"/></th>
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
            <div class="popup" style="height: 60px;">
                <div class="row">
                    <div class="column columnL">
                        <label class="tit"><spring:message code='pivas.confingfeerule.rulename'/></label>
                        <input type="text" class="edit" empty="false" name="ruleName" id="ruleNameEdit" maxlength="32"
                               title="<spring:message code='common.op.remind2'/>"/>
                        <span class="mand">*</span>
                        <input type="hidden" id="isSameName" name="isSameName"/>
                    </div>
                    <div class="column columnR">
                        <label class="tit"><spring:message code='pivas.confingfeerule.volume'/></label>
                        <input type="text" class="edit" regex="positive" name="volume" id="volumeEdit" maxlength="32"
                               title="请输入1-32位正整数"/>

                    </div>
                </div>
                <div class="row">
                    <div class="column columnL">
                        <label class="tit"><spring:message code='pivas.confingfeerule.drugtype'/></label>
                        <select id="drugTypeSelect" name="drugTypeCode" style="width: 185px;" readonly
                                onchange="changeCategoryId();"></select>
                    </div>
                    <div class="column columnR">
                        <label class="tit"><spring:message code='pivas.confingfeerule.drug'/></label>
                        <input type="hidden" name="drugCode" id="drugCodeEdit"/>
                        <input type="text" name="drugName" id="drugNameEdit" readonly="readonly" class="hand"/>
                    </div>
                </div>
            </div>

            <div class="configfee">
                <p style="text-align:left;font-size:150%;">
                    <spring:message code="pivas.confingfeerule.fee"/>
                </p>
            </div>
            <div class="layeForm" style="margin: 0;overflow: auto;width: 100%;">
                <table id="ruleListTable" class="Tcontrol" style="vertical-align: top; margin-left: 0px;">
                    <thead>
                    <tr class="sdfunTableHeaderTr" style="font-size:14px;">
                        <th class="thC1"><spring:message code="pivas.confingfeeruledetail.fee"/></th>
                        <th class="thC1"><spring:message code="pivas.confingfeeruledetail.amount"/></th>
                        <th class="thC1"><spring:message code="pivas.confingfeeruledetail.onceoneday"/></th>
                        <th class="thC1"><spring:message code="common.op"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr id="addTr">
                        <td style="text-align: center;"><a onclick="addRow();">添加</a></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
</div>

<div id="medicl-div" title="<spring:message code="common.choose.drug"/>" style="display: none;">

    <label class="tit" style="width:30px;">药品名称</label>
    <input type="text" name="ypmc" id="ypmcid" style="width:200px;border:1px solid #ccc;"/>
    <a class="button" id="qryButton">查询</a>
    <div style="border-top: solid 1px #CCCCCC;margin-bottom: 10px;margin-top: 10px;"></div>
    <table id="labelFlexGrid" style="display: block;margin: 0px;"></table>

</div>
</body>

</html>