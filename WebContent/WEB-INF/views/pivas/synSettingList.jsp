<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet"/>

<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/pivas/js/strings.js"></script>
    <script>

        var _gridWidth = 0;
        var _gridHeight = 0;
        var qryParam;
        var datatable;

        function initDatatable() {
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
                    "url": "${pageContext.request.contextPath}/synSet/synSetList",
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
                    {"data": "taskID", "bSortable": false},
                    {"data": "taskName", "bSortable": false},
                    {"data": "taskTypeName", "bSortable": false},
                    {"data": "taskInteval", "bSortable": false},
                    {"data": "taskExecuteModeName", "bSortable": false},
                    {"data": "taskTaskPriority", "bSortable": false},

                    {"data": "taskExecuteTime", "bSortable": false},
                    {"data": "taskContentTypeName", "bSortable": false},
                    {"data": "taskStatusName", "bSortable": false},


                ],
                columnDefs: [
                    {
                        targets: 0,
                        render: function (data, type, row) {
                            return "<input type='checkbox' name='cbs' value='"+data+"'/>";
                        }
                    },
                    {
                        targets: 9,
                        render: function (data, type, row) {
                        	var str = "";
                        	<shiro:hasPermission name="PIVAS_BTN_346">
                        	str += '<a class="button ui-search-btn" onclick="updateRecord({0})"><spring:message code="common.edit"/></a>'.format([row.taskID]);
                     		</shiro:hasPermission>
                            return str;
                        }
                    },

                ]
            });
        }
        
        function updateRecord(id) {
        	var ids = [id];
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/synSet/initUpdateSynSet',
                dataType: 'json',
                cache: false,
                data: [{name: 'taskID', value: ids}],
                success: function (data) {
                    if (data.success == false) {
                    	datatable.ajax.reload();
                        layer.alert(data.description, {'title': '操作提示', icon: 0});
                        return;
                    }
                    else {
                        $("#taskID").val(data.taskID);
                        $("#taskName").val(data.taskName).attr("disabled", "disabled");
                        $("#taskStatus").combobox("reset", data.taskStatus);
                        $("#taskInteval").val(data.taskInteval);
                        $("#taskExecuteMode").combobox("reset", data.taskExecuteMode);
                        $("#taskTaskPriority").val(data.taskTaskPriority);
                        $("#taskType").combobox("reset", data.taskType);
                        $("#taskExecuteTime").val(data.taskExecuteTime);
                        $("#taskContentType").combobox("reset", data.taskContentType);
                        $("#taskRemark").val(data.taskRemark);
                        $("#retryTimes").val(data.retryTimes);
                        $("#retryInterval").val(data.retryInterval);
                        changeTaskType();
                    }
                    $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.synsetting.update'/>");
                    $("#editView-div").dialog("open");
                },
                error: function () {
                }
            });
        }

        $(function () {
            $("#editView-form sdfun:select").combobox();
            sdfun.fn.trimAll("editView-div");
            var taskName = $("#taskName");
            var taskStatus = $("#taskStatus");
            var taskInteval = $("#taskInteval");
            var taskExecuteMode = $("#taskExecuteMode");
            var taskTaskPriority = $("#taskTaskPriority");
            var taskType = $("#taskType");
            var taskExecuteTime = $("#taskExecuteTime");
            var taskContentType = $("#taskContentType");
            var taskRemark = $("#taskRemark");
            var retryTimes = $("#retryTimes");
            var retryInterval = $("#retryInterval");
            var allFieldsInfo = $([]).add(taskName).add(taskStatus).add(taskInteval).add(taskExecuteMode).add(taskTaskPriority).add(taskType).add(taskExecuteTime).add(taskContentType).add(taskRemark).add(retryTimes).add(retryInterval);

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
                height: 400,
                width: 880,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var url = "${pageContext.request.contextPath}/synSet/addSynSet";
                        var bValid = true;
                        allFieldsInfo.removeClass("ui-state-error");
                        bValid = bValid && checkLength(taskName, 1, 32);

                        if ($("#taskType").val() && $("#taskType").val() != "1") {
                            bValid = bValid && isPInt(taskInteval);
                        }
                        ;

                        bValid = bValid && isPInt(taskTaskPriority);
                        bValid = bValid && checkLength(taskExecuteTime, 1, 32);
                        bValid = bValid && isPInt(retryTimes);
                        bValid = bValid && isPInt(retryInterval);
                        if (!bValid) {
                            return;
                        }
                        var params = {
                            "taskName": $("#taskName").val(),
                            "taskStatus": $("#taskStatus").val(),
                            "taskInteval": $("#taskInteval").val(),
                            "taskInteval": $("#taskInteval").val(),
                            "taskExecuteMode": $("#taskExecuteMode").val(),
                            "taskTaskPriority": $("#taskTaskPriority").val(),
                            "taskType": $("#taskType").val(),
                            "taskExecuteTime": $("#taskExecuteTime").val(),
                            "taskContentType": $("#taskContentType").val(),
                            "taskRemark": $("#taskRemark").val(),
                            "retryTimes": $("#retryTimes").val(),
                            "retryInterval": $("#retryInterval").val()
                        };

                        if ($("#taskID").val() && $("#taskID").val() != "") {
                            url = "${pageContext.request.contextPath}/synSet/updateSynSet";
                            params["taskID"] = $("#taskID").val();
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
                                layer.alert(data.description, {'title': '操作提示', icon: 0});
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
                $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='pivas.synsetting.add'/>");
                $("#editView-div").dialog("open");
                $("#setTaskTime").show();
                $("#taskName").val('').removeAttr("disabled", "disabled");
            });

            //删除按钮
            $("#deleteDataDicBtn").bind("click", function () {
                var ids =getSelectedRow();
                if (ids && ids.length > 0) {

                    layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                        btn: ['确定', '取消'], icon: 3, title: '提示'
                    }, function () {
                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/synSet/delSynSet',
                            dataType: 'json',
                            cache: false,
                            data: [{name: 'taskID', value: ids}],
                            success: function (data) {
                                qryList();
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

            // 同步启动按钮
            $("#startDataDicBtn").bind("click", function () {
                var ids = getSelectedRow();
                if (ids && ids.length > 0) {
                    layer.confirm('<spring:message code="common.startSynTask"/>', {
                        btn: ['确定', '取消'], icon: 3, title: '提示'
                    }, function () {
                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/synSet/startSynSet',
                            dataType: 'json',
                            cache: false,
                            data: [{name: 'taskID', value: ids}],
                            success: function (data) {
                                qryList();
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

            // 同步停止按钮
            $("#stopDataDicBtn").bind("click", function () {
                var ids = getSelectedRow();
                if (ids && ids.length > 0) {

                    layer.confirm('<spring:message code="common.startSynTask"/>', {
                        btn: ['确定', '取消'], icon: 3, title: '提示'
                    }, function () {
                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/synSet/killsynSet',
                            dataType: 'json',
                            cache: false,
                            data: [{name: 'taskID', value: ids}],
                            success: function (data) {
                                qryList();
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

        function changeTaskType() {
            if ($("#taskType").val() && $("#taskType").val() != "0") {
                $("#setTaskTime").hide();
                $("#taskInteval").val('');
            } else {
                $("#setTaskTime").show();
            }
            ;
        }
    </script>
</head>
<body>

<div class="main-div" style="width:100%">
    <!-- 搜索条件--开始 -->
    <div data-qryMethod funname="qryList" class="ui-search-header ui-search-box" id="qryView-div" style="display: inline; ">
        <div style="float:right ">
        <input placeholder="名称" name="taskNames" size="8" data-cnd="true">
        <button  class="button ui-search-btn ui-btn-bg-green"  onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>
        <button  class="button ui-search-btn ui-btn-bg-yellow"  onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>
        </div>
        <shiro:hasPermission name="PIVAS_BTN_343">
            <a  class="button ui-search-btn ui-btn-bg-green" id="addDataDicBtn"><i class="am-icon-plus"></i><span>新增</span></a>
        </shiro:hasPermission>
        <shiro:hasPermission name="PIVAS_BTN_344"><a class="button ui-search-btn ui-btn-bg-red" id="deleteDataDicBtn"><i class="am-icon-trash"></i><span>删除</span></a></shiro:hasPermission>
        <shiro:hasPermission name="PIVAS_BTN_347"><a class="button ui-search-btn ui-btn-bg-green" id="startDataDicBtn"><i class="am-icon-play"></i><span>启动</span></a></shiro:hasPermission>
        <shiro:hasPermission name="PIVAS_BTN_348"><a class="button ui-search-btn ui-btn-bg-red" id="stopDataDicBtn"><i class="am-icon-stop"></i><span>停止</span></a></shiro:hasPermission>
    </div>
    <%-- 搜索条件--结束 --%>
    <div class="tbl">
        <table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
            <thead>
            <tr>
                <th><input type='checkbox' name='cbs'/></th>
                <th><spring:message code="pivas.confingfee.costname"/></th>
                <th><spring:message code="pivas.synsetting.tasktype"/></th>
                <th><spring:message code="pivas.synsetting.intervaltime"/></th>
                <th><spring:message code="pivas.synsetting.intervalunit"/></th>
                <th><spring:message code="pivas.synsetting.priority"/></th>
                <th><spring:message code="pivas.synsetting.taskexecutetime"/></th>
                <th><spring:message code="pivas.synsetting.contenttype"/></th>
                <th><spring:message code="pivas.synsetting.statename"/></th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>


<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="<spring:message code='pivas.synsetting.update'/>" align="center" style="display: none;">
    <form id="editView-form" action="" method="post">
        <input type="hidden" id="taskID" name="taskID"/>
        <div class="popup">
            <div class="row">
                <div class="column columnL">
                    <label class="tit"><spring:message code='medical.freq.name'/></label>
                    <input type="text" class="edit" name="taskName" id="taskName" maxlength="32"
                           title="<spring:message code='common.op.remind2'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column columnR">
                    <label class="tit"><spring:message code='pivas.synsetting.statename'/></label>
                    <sd:select id="taskStatus" width="185px" required="true" categoryName="pivas.synsetting.taskstatus"
                               tableName="sys_dict"/>
                    <span class="mand">*</span>
                </div>
            </div>

            <div class="row">
                <div class="column columnL" style="margin-left: -418px;margin-top:3px;">
                    <label class="tit"><spring:message code='pivas.synsetting.executtime'/></label>
                    <input type="text" id="taskExecuteTime" name="taskExecuteTime" readonly="readonly" class="Wdate"
                           maxlength="32"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
                    <span class="mand">*</span>
                </div>
                <div class="column columnR">
                    <label class="tit"><spring:message code='pivas.synsetting.taskcontenttype'/></label>
                    <sd:select id="taskContentType" name="taskContentType" width="185px" required="true"
                               categoryName="pivas.synsetting.taskcontenttype" tableName="sys_dict"/></td>
                    <span class="mand">*</span>
                </div>
            </div>

            <div class="row">
                <div class="column columnL">
                    <label class="tit"><spring:message code='pivas.synsetting.priority'/></label>
                    <input type="text" class="edit" name="taskTaskPriority" id="taskTaskPriority" maxlength="32"
                           title="<spring:message code='common.plzInputZInteger'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column columnR">
                    <label class="tit"><spring:message code='pivas.synsetting.tasktype'/></label>
                    <sd:select id="taskType" width="185px" required="true" categoryName="pivas.synsetting.tasktype"
                               tableName="sys_dict" onchange="changeTaskType()"/></td>
                    <span class="mand">*</span>
                </div>
            </div>

            <div class="row" id="setTaskTime">
                <div class="column columnL">
                    <label class="tit"><spring:message code='pivas.synsetting.intervaltime'/></label>
                    <input type="text" class="edit" name="taskInteval" id="taskInteval" maxlength="32"
                           title="<spring:message code='common.plzInputZInteger'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column columnR">
                    <label class="tit"><spring:message code='pivas.synsetting.intervalunit'/></label>
                    <sd:select id="taskExecuteMode" width="185px" required="true"
                               categoryName="pivas.synsetting.intervalunit" tableName="sys_dict"/></td>
                    <span class="mand">*</span>
                </div>
            </div>

            <div class="row" id="setRetryTime">
                <div class="column columnL">
                    <label class="tit"><spring:message code='pivas.synsetting.retrytimes'/></label>
                    <input type="text" class="edit" name="retryTimes" id="retryTimes" maxlength="32"
                           title="<spring:message code='common.plzInputZInteger'/>"/>
                    <span class="mand">*</span>
                </div>
                <div class="column columnR">
                    <label class="tit"><spring:message code='pivas.synsetting.retyyinterval'/></label>
                    <input type="text" class="edit" name="retryInterval" width="185px" id="retryInterval" maxlength="32"
                           title="<spring:message code='common.plzInputZInteger'/>"/>
                    <span class="mand">*</span>
                </div>
            </div>

            <div class="row">
                <div class="column columnL">
                    <label class="tit"><spring:message code='common.remark'/></label>
                    <textarea rows="1" name="taskRemark" id="taskRemark" maxlength="256"
                              title="<spring:message code='common.op.remind6'/>" style="resize:none"></textarea>
                    <span class="mand"></span>
                </div>
            </div>
        </div>
    </form>
</div>

</body>

</html>