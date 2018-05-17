<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet"/>
<head>
    <script>
        var oldOrder = "";
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
                    "url": "${pageContext.request.contextPath}/drugscategory/listDrugsCategory",
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
                    {"data": "categoryId", "bSortable": false, "width": 5},
                    {"data": "categoryName", "bSortable": false},
                    {"data": "categoryCode", "bSortable": false,"defaultContent":""},
                    {"data": "categoryPriority", "bSortable": false, "defaultContent":""},
                    {"data": "categoryIsHard", "bSortable": false, "defaultContent":""},
                    {"data": "categoryId", "bSortable": false, "width": "10%", "defaultContent":""}
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
                            return (row.categoryIsHard === 0) ? "是" : "否";
                        }
                    },{
                        targets: 5,
                        render: function (data, type, row) {
                            var str = "";
                            <shiro:hasPermission name="PIVAS_BTN_215">
                            str += "<a class='button btn-bg-green' href='javascript:updMedicCatalog(" + data + ");'><i class=\"am-icon-edit\"></i><span>&nbsp;修改</span></a>";
                            </shiro:hasPermission>
                            return str;
                        }
                    }
                ],
            });
        }

        $(function () {
            sdfun.fn.trimAll("editView-div");
            var categoryName = $("#categoryName");
            var categoryCode = $("#categoryCode");
            var categoryPriority = $("#categoryPriority");
            var categoryId = $("#categoryId");
            var categoryIsHard = $("#categoryIsHard");
            $("#categoryIsHard").combobox("reset", 1);
            var allFieldsInfo = $([]).add(categoryName).add(categoryCode).add(categoryPriority).add(categoryId).add(categoryIsHard);

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

            function process(v) {
                if (v == 0) {
                    return "<spring:message code="common.yes"/>";
                } else if (v == 1) {
                    return "<spring:message code="common.no"/>";
                }
            }

            $("#aSearch").bind("click", function () {
                qryList();
            });

            $("#editView-div").dialog({
                autoOpen: false,
                height: 270,
                width: 480,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var bValid = true;
                        allFieldsInfo.removeClass("ui-state-error");
                        bValid = bValid && checkLength(categoryName, 1, 32);
                        bValid = bValid && isPInt(categoryPriority);
                        if (!bValid) {
                            return;
                        }
                        var url = "${pageContext.request.contextPath}/drugscategory/addDrugsCategory";
                        var params = {
                            "categoryName": $("#categoryName").val(),
                            "categoryCode": $("#categoryCode").val(),
                            "categoryPriority": $("#categoryPriority").val(),
                            "categoryIsHard": $("#categoryIsHard").val()
                        };
                        if ($("#categoryId").val() && $("#categoryId").val() != "") {
                            url = "${pageContext.request.contextPath}/drugscategory/updateDrugsCategory";
                            params["categoryId"] = $("#categoryId").val();

                            var order = $("#categoryPriority").val();
                            if (oldOrder == order) {
                                params["categoryPriority"] = "";
                            }

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
                        $("#categoryIsHard").combobox("reset", 1);
                        $("#editView-div").dialog("close");
                    }
                },
                close: function () {
                    allFieldsInfo.val("").removeClass("ui-state-error");
                    $("#categoryIsHard").combobox("reset", 1);
                }
            });

            //新增按钮
            $("#addDataDicRoleBtn").bind("click", function () {
                $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='medic.addCategory'/>");
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
                            url: '${pageContext.request.contextPath}/drugscategory/delDrugsCategory',
                            dataType: 'json',
                            cache: false,
                            data: [{name: 'categorys', value: ids}],
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

        function updMedicCatalog(lId) {
            if (lId) {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/drugscategory/displayDrugsCategory',
                    dataType: 'json',
                    cache: false,
                    data: [{name: 'categoryId', value: lId}],
                    success: function (data) {
                        if (data.success == false) {
                            qryList();
                            layer.alert(data.description, {'title': '操作提示', icon: 0});
                            return;
                        }
                        else {
                            $("#categoryId").val(data.categoryId);
                            $("#categoryName").val(data.categoryName);
                            $("#categoryCode").val(data.categoryCode);
                            $("#categoryPriority").val(data.categoryPriority);
                            $("#categoryIsHard").combobox("reset", data.categoryIsHard);

                            oldOrder = data.categoryPriority;

                        }
                        $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='medic.updateCategory'/>");
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
    </script>
</head>
<body>

<div class="main-div" style="width:100%">

    <%-- 搜索条件--开始 --%>
    <div data-qryMethod funname="qryList"  class="ui-search-header ui-search-box" id="qryView-div" style="display: inline; ">
        <div style="float:right ">
            <input placeholder="名称" name="categoryNames" size="20" data-cnd="true">&nbsp;&nbsp;
            <button  class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
            <button  class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
        </div>
        <shiro:hasPermission name="PIVAS_BTN_212"><a class="ui-search-btn ui-btn-bg-green" id="addDataDicRoleBtn"><i class="am-icon-plus"></i><span>新增</span></a></shiro:hasPermission>&nbsp;
        <shiro:hasPermission name="PIVAS_BTN_213"><a class="ui-search-btn ui-btn-bg-red" id="deleteDataDicRoleBtn"><i class="am-icon-remove"></i><span>删除</span></a></shiro:hasPermission>
    </div>
    <%-- 搜索条件--结束 --%>
    <div class="tbl">
        <table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
            <thead>
            <tr>
                <th><input type='checkbox' name='cbs'/></th>
                <th>分类名称</th>
                <th>瓶签编码</th>
                <th>优先级</th>
                <th>是否难配药</th>
                <th>操作</th>

            </tr>
            </thead>
        </table>
    </div>
</div>


<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="<spring:message code='medic.addCategory'/>" align="center" style="display: none;">
    <form id="editView-form" action="" method="post">
        <input type="hidden" id="categoryId" name="categoryId"/>
        <div class="popup">
            <div class="row">
                <div class="column">
                    <label class="tit"><spring:message code='medic.categoryName'/></label>
                    <input type="text" class="condition" name="categoryName" id="categoryName" maxlength="32"
                           title="<spring:message code='common.op.remind2'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column">
                    <label class="tit"><spring:message code='medic.categoryCode'/></label>
                    <input type="text" class="condition" name="categoryCode" id="categoryCode" maxlength="32"
                           title="<spring:message code='common.op.remind2'/>"/>
                </div>
                <div class="column">
                    <label class="tit"><spring:message code='medic.categoryPriority'/></label>
                    <input type="text" class="condition" name="categoryPriority" id="categoryPriority" maxlength="32"
                           title="<spring:message code='common.plzInputZInteger'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column">
                    <label class="tit"><spring:message code='medic.categoryIsHard'/></label>
                    <sd:select id="categoryIsHard" name="categoryIsHard" width="184px" required="true"
                               categoryName="pivas.common.yesorno" tableName="sys_dict"></sd:select>
                </div>
            </div>
        </div>
    </form>
</div>
</body>

</html>