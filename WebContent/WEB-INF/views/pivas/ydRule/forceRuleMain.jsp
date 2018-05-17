<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="/WEB-INF/views/common/common.jsp" %>

<!DOCTYPE html>
<html>
<head>

	<style type="text/css">
	<%--药物优先级 左中右样式 --开始------------------%>
	.td200Left{
		width: 200px;vertical-align: top;
	}
	.divLeftMenuAll{
		height: 55px;
		padding-top: 20px;
		padding-left: 24px;
		font-size: 0.75rem;
		border-bottom: 1px solid #ddd;
	}
	.divLeftMenuList{
		overflow-y: auto;  padding-left: 0px; font-size: 0.75rem;
	}
	.tabWith100{
		width:100%;
	}
	.tabWith50{
		width:50%;
	}
	.tdLeft10{
		padding-left: 10px;cursor: pointer;
	}
	.tabMain2{
		height: 100%;width:100%;padding-left: 10px;padding-top: 0px;
		margin-left:6px;
	}
	.search-div{
		padding: 5px 1em 1em 0em;
	}
	.tdSortTitle{
		vertical-align: bottom ;padding-right: 5px;color: white;
	}
	.divSortTitle{
		height:100%;padding-top: 8px;padding-left:5px; background-color: #6F96E5;font-size: 0.75rem;
	}
	.aSortTitle{
		float: right;padding-left: 10px;
	}
	.tdSortContent{
		vertical-align: top;padding-right: 5px;
	}
	.divSortContent{
		height:100%;overflow-y: auto;
	}
	.tdSortRow{
		width: 100%;height: 30px;cursor: pointer;padding-left: 10px;
	}
	
	.tdMedSelec{
		background-color: #99bbe8;
	}
	.trArea{
		height: 30px;
	}
	
	.areaSel{
		background: #6F96E5;
		color: white;
	}
	.trArea td{
		padding-left: 10px;cursor: pointer;
	}
					
	#divSynToOther tr:nth-child(2n) {
	    background: transparent;
	}
	
	
	.button{
		 margin-left: 1px;
	}   
	.ui-dialog .ui-dialog-buttonpane button{
		    border-color: transparent;
	}


	.ui-td-left{
		border:1px solid #ddd;
	}

	.down-arrow,.up-arrow{
		position: relative;
		float: right;
		margin-right:5px;
		width:35px;
		height:35px;
		display: block;
		cursor:pointer;
	}
	.down-arrow{
		margin-right:5px;
	}
	.down-arrow:before,.down-arrow:after {
		position: relative;
		border: 10px solid transparent;
		border-top: 10px solid #f5f5f5;
		width: 0;
		height: 0;
		position: absolute;
		top: 10px;
		/*right: -20px;*/
		content: ' ';
	}
	.down-arrow:before {
		border-top-color: #4CAF50;
		top: 12px;
	}
	.up-arrow:before,.up-arrow:after {
		position: relative;
		border: 10px solid transparent;
		border-bottom: 10px solid #f5f5f5;
		width: 0;
		height: 0;
		position: absolute;
		top: 5px;
		content: ' ';
	}
	.up-arrow:before {
		border-bottom-color: #4CAF50;
		top: 3px;
	}
	.down-arrow img,.up-arrow img{
		display: none;
		visibility: hidden;
	}
	</style>

	<script type="text/javascript">
        var _pageWidth = 0;
        var _pageHeight = 0;

        var ruleTable;
        var medicTable;
		var qryParam;

        function resizePageSize() {
            _pageWidth = $(window).width();
            _pageHeight = $(window).height();
            $("#divMain1").css("height", _pageHeight);
            $("#divArea").css("height", _pageHeight - 70);
        }

        var dialogWidth = 900;
        var dialogHeight = 520;
        var medicGridWidth = dialogWidth - 40;
        <%--宽度比弹窗的宽度小--%>
        var medicGridheight = dialogHeight - 210;
        <%--宽度比弹窗的高度小--%>

        var prType = "${prType}";
        var areaCodeNow = "${areaCodeNow}";
        var areaNameNow = "${areaNameNow}";

        var medicIDs = [];

        $(function () {
            $(window).resize(function () {
                resizePageSize();
            });
            resizePageSize();

            $("#divSynToOther").dialog({
                autoOpen: false,
                width: 560,
                height: 400,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var areaCodeN = "";
                        var areaNameN = "";
                        $(".chkOneArea").each(function () {
                            if ($(this).attr("checked") == "checked") {
                                if (areaCodeNow != $(this).val()) {
                                    areaCodeN = areaCodeN + $(this).val() + ",";
                                    areaNameN = areaNameN + $(this).attr("deptname") + ",";
                                }
                            }
                        });
                        if (areaCodeN.endWith(",")) {
                            areaCodeN = areaCodeN.substring(0, areaCodeN.length - 1);
                            areaNameN = areaNameN.substring(0, areaNameN.length - 1);
                        }

                        var medicCodeStr = getRuleSelectedAttrVal(5).join(",");
                        if (areaCodeN != "") {
                            $.ajax({
                                type: 'POST',
                                url: '${pageContext.request.contextPath}/ydRule/synMdcmRuleToOtherArea',
                                dataType: 'json',
                                cache: false,
                                showDialog: false,
                                data: {
                                    "areaCodeNow": areaCodeNow,
                                    "areaNameNow": areaNameNow,
                                    "areaCodeN": areaCodeN,
                                    "areaNameN": areaNameN,
                                    "prType": prType,
                                    "medicCodeStr": medicCodeStr
                                },
                                success: function (result) {
                                    if (result.code > 0) {
                                        $("#divSynToOther").dialog("close");
                                        layer.alert("已成功同步到相关病区", {'title': '操作提示', icon: 0});
                                    } else {
                                        layer.alert(result.mess || "操作异常，请稍后再试", {'title': '操作提示', icon: 0});
                                    }
                                }
                            });
                        } else {
                            layer.alert("请选择病区", {'title': '操作提示', icon: 0});
                        }
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        $(this).dialog("close");
                    }
                },
                close: function () {

                }
            });

            $(".chkAllArea").bind("change", function () {
                if ($(this).attr("checked") == "checked") {
                    $(".chkOneArea").each(function () {
                        if ($(this).val() == areaCodeNow) {

                        } else {
                            $(this).attr("checked", "checked");
                        }
                    });
                    <%--$(".chkOneArea").attr("checked","checked");--%>
                } else {
                    $(".chkOneArea").each(function () {
                        if ($(this).val() == areaCodeNow) {

                        } else {
                            $(this).removeAttr("checked");
                        }
                    });
                    <%--$(".chkOneArea").removeAttr("checked");--%>
                }
            });

            $(".chkOneArea").bind("change", function () {
                var checkAll = true;
                $(".chkOneArea").each(function () {
                    if ($(this).attr("checked") == undefined) {
                        checkAll = false;
                    }
                });
                if (checkAll) {
                    $(".chkAllArea").attr("checked", "checked");
                } else {
                    $(".chkAllArea").removeAttr("checked");
                }
            });

            $("#tabArea td").bind("click", function () {
                areaCodeNow = $(this).attr("areacode");
                areaNameNow = $(this).html();
                $(this).parent().parent().find("tr").each(function () {
                    $(this).removeClass("areaSel");
                    $(this).css("background", "transparent");
                });
                $(this).parent().addClass("areaSel");
                $(this).parent().css("background", "#6F96E5");
                showAreaData();
            });

        });


        $(function () {
            <%--药物优先级处理--%>
            initMedicTable();
            $("#medicTable > thead").on("change", "input:checkbox", function () {
                var selected = $(this).attr("checked") == "checked";
                $("#medicTable > tbody").find("[name='mediccbs']:checkbox").each(function (i, v) {
                    if (selected) {
                        $(v).attr("checked", 'checked');
                    } else {
                        $(v).removeAttr("checked");
                    }
                });
            });

            $("#divAddMed").dialog({
                autoOpen: false,
                width: dialogWidth,
                height: dialogHeight + 15,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        var medIdS = getMedicSelectedRowColText(3);
                        var medNameS = getMedicSelectedRowColText(2);
                        if (medIdS && medIdS.length > 0) {
                            var medIdN = medIdS.join(",");
                            var medNameN = medNameS.join(",");
                            sendToAdd({
                                "deptcode": areaCodeNow,
                                "deptname": areaNameNow,
                                "prType": prType,
                                "medIdN": medIdN,
                                "medNameN": medNameN,
                                "medicType": "1"
                            });
                        } else {
                            var medicaType = $("#medicamentCateDIV input:checked");
                            if ($(medicaType).val() == -1) {
                                layer.alert("请选择药品或者药品种类再保存", {'title': '操作提示', icon: 0});
                            } else {
                                if ($.inArray($(medicaType).val(), medicIDs) != -1) {
                                    $("#divAddMed").dialog("close");
                                    layer.alert("该类型的药品已经添加", {'title': '操作提示', icon: 0});
                                    return;
                                }
                                sendToAdd({
                                    "deptcode": areaCodeNow,
                                    "deptname": areaNameNow,
                                    "prType": prType,
                                    "medIdN": $(medicaType).val(),
                                    "medNameN": $(medicaType).next().text(),
                                    "medicType": "2"
                                });
                            }
                        }
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        $(this).dialog("close");
                    }
                },
                close: function () {

                }
            });

            function sendToAdd(param) {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/ydRule/addPLRule',
                    dataType: 'json',
                    cache: false,
                    data: param,
                    success: function (result) {
                        if (result.code > 0) {
                            $("#divAddMed").dialog("close");
                            showAreaData();
                        }
                        layer.alert(result.mess || "操作异常，请稍后再试", {'title': '操作提示', icon: 0});
                    }
                });
            }

            $("#synDataDicBtn").bind("click", function () {
                <%--获取到当前勾选的强制规则信息--%>
                var prIds = getRuleSelectedAttrVal(0);
                if (prIds.length == 0) {
                    layer.alert("请至少选择一条规则后同步", {'title': '操作提示', icon: 0});
                    return;
                }

                $(".chkAllArea").removeAttr("checked");
                $(".chkOneArea").each(function () {
                    $(this).removeAttr("checked");
                    if ($(this).val() == areaCodeNow) {
                        $(this).attr("checked", "checked");
                        $(this).attr("disabled", "disabled");
                        $(this).parent().next().css("background-color", "rgba(216, 216, 216, 0.8)")
                    } else {
                        $(this).removeAttr("disabled");
                        $(this).parent().next().css("background-color", "transparent")
                    }
                });
                $("#divSynToOther").dialog("open");
            });

            $("#aAdd").bind("click", function () {
                $("#divAddMed").dialog("open");
                if ($("#medicamentCateDIV div").length === 1) {
                    qryMedicamentCategory();
                } else {
                    $("#medicamentCateDIV input:checkbox").each(function (i, v) {
                        if (i === 0) {
                            $(this).attr("checked", true);
                        } else {
                            $(this).removeAttr("checked");
                        }
                    });
                }
                qryList();
            });

            $("#aDel").bind("click", function () {
                var prIds = getRuleSelectedAttrVal(0);
                if (prIds && prIds.length > 0) {
                    layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                        btn: ['确定', '取消'], icon: 3, title: '提示'
                    }, function () {
                        var prIdN = prIds.join(",");
                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/ydRule/delPLRule',
                            dataType: 'json',
                            cache: false,
                            data: {"prIdN": prIdN, "deptcode": areaCodeNow, "deptname": areaNameNow, "prType": prType},
                            success: function (result) {
                                if (result.code > 0) {
                                    showAreaData();
                                }
                                layer.alert(result.mess || "操作异常，请稍后再试", {'title': '操作提示', icon: 0});
                            }
                        });
                    });
                } else {
                    layer.alert("请至少选择一条数据后操作", {'title': '操作提示', icon: 0});
                }
            });

            //TODO 初始化时将默认条件加到ruleQryParam，让表格第一次就按条件查询
            initRuleTable();
            $("#ruleTable > thead").on("change", "input:checkbox", function () {
                var selected = $(this).attr("checked") == "checked";
                $("#ruleTable > tbody").find("[name='rulecbs']:checkbox").each(function (i, v) {
                    if (selected) {
                        $(v).attr("checked", 'checked');
                    } else {
                        $(v).removeAttr("checked");
                    }
                });
            });
        });

        function getRuleSelectedAttrVal(index, selected) {
            return getSelectedAttrVal("#ruleTable", "rulecbs", index, selected);
        }

        function getSelectedAttrVal(table, cbname, index, selected) {
            var arr = new Array(0);
            $(table + " > tbody").find("[name='" + cbname + "']:" + (selected ? "checked" : "checkbox")).each(function (i, v) {
                arr.push($(v).attr("text").split(",")[index]);
            });
            return arr;
        }

        function getRuleSelectedRowColText(colNum, selected) {
            return getSelectedRowColText("#ruleTable", "rulecbs", colNum, selected);
        }

        function getMedicSelectedRowColText(colNum, selected) {
            return getSelectedRowColText("#medicTable", "mediccbs", colNum, selected);
        }

        function getSelectedRowColText(table, cbname, colNum, selected) {
            var arr = new Array(0);
            $(table + " > tbody").find("[name='" + cbname + "']:" + (selected ? "checked" : "checkbox")).each(function (i, v) {
                arr.push(colNum == 0 ? $(v).val() : $(v).parent().nextAll().eq(colNum - 1).text());
            });
            return arr;
        }

        function initRuleTable() {
            ruleTable = $("#ruleTable").DataTable({
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
                    "url": "${pageContext.request.contextPath}/ydRule/getForceRuleByArea",
                    "type": "post",
                    "data": function (d) {
                        var params = [{"name": "deptcode", "value": areaCodeNow}, {"name": "prType", "value": prType}];
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
                    {"data": "prId", "bSortable": false},
                    {"data": "prId", "bSortable": false},
                    {"data": "prOrder", "bSortable": false},
                    {"data": "batchId", "bSortable": false},
                    {"data": "prType", "bSortable": false},
                    {"data": "deptcode", "bSortable": false},
                    {"data": "medicId", "bSortable": false},
                    {"data": "medicName", "bSortable": false},
                    {"data": "batchName", "bSortable": false},
                    {"data": "enabled", "bSortable": false},
                    {"data": "enabled", "bSortable": false}
                ],
                "columnDefs": [
                    {
                        "targets": 0,
                        "render": function (data, type, row) {
                            var attrText = row.prId + "," + row.prOrder + "," + row.batchId + "," + row.prType + "," + row.deptcode + "," + row.medicId + "," + row.enabled;
                            return "<input type='checkbox' name='rulecbs' text='" + attrText + "' value='" + data + "'/>";
                        }
                    },{
                        "targets": 6,
                        "render": function (data) {
                            medicIDs.push(data);
                            return data;
                        }
                    },{
                        "targets": 8,
                        "render": function (data, type, row) {
                            return "<select id='rule_" + row.prId + "' onchange='changeBatch(this)' onclick='myHasPermission()' ><option value='' >---请选择---</option>"
                                + "<c:forEach items='${batchList}' var='batch' ><option value='${batch.id}' " + (row.batchId == '${batch.id}' ? "selected='selected'" : "") + " >${batch.name}</option></c:forEach>"
                                + "</select>";
                        }
                    },{
                        "targets": 9,
                        "render": function (data, type, row) {
                            return "<input type='checkbox' id='rule_" + row.prId + "' onchange='changeForce(this)' " + (data == 1 ? "checked='checked'" : "") + " >";
                        }
                    }, {
                        "visible": false,
						"targets": [1, 2, 3, 4, 5, 6, 10],
						"render": function (data) {
                            return data == undefined ? "" : data;
                        }
                    }
                ]
            });
        }

        function initMedicTable() {
            medicTable = $("#medicTable").DataTable({
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
                    "url": "${pageContext.request.contextPath}/mdcm/mdcmslist",
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
                    {"data": "medicamentsId", "bSortable": false},
                    {"data": "categoryName", "bSortable": false},
                    {"data": "medicamentsName", "bSortable": false},
                    {"data": "medicamentsCode", "bSortable": false},
                    {"data": "medicamentsSpecifications", "bSortable": false},
                    {"data": "medicamentsDosage", "bSortable": false},
                    {"data": "medicamentsSuchama", "bSortable": false},
                    {"data": "medicamentsPackingUnit", "bSortable": false},
                    {"data": "medicamentsMenstruum", "bSortable": false},
                    {"data": "medicamentsPlace", "bSortable": false},
                    {"data": "labelNames", "bSortable": false},
                    {"data": "medicamentsDanger", "bSortable": false}
                ],
                "columnDefs": [
                    {
                        "targets": 0,
                        "render": function (data, type, row) {
                            var attrText = row.prId + "," + row.prOrder + "," + row.batchId + "," + row.prType + "," + row.deptcode + "," + row.medicId + "," + row.enabled;
                            return "<input type='checkbox' name='mediccbs' text='" + attrText + "' value='" + data + "'/>";
                        }
                    },{
                        "targets": [8,11],
                        "render": function (data) {
                            return data == 0 ? "<spring:message code="common.yes"/>" : "<spring:message code="common.no"/>";
                        }
                    }
                ]
            });
        }

        <%--处理强制规则的 --%>
        var updateSortRuning = false;

        function updateSort(type) {
            if (updateSortRuning) {
                return;
            }
            var medIdS = getRuleSelectedAttrVal(5);
            if (medIdS.length == 0) {
                layer.alert("请选择要调整的药物", {'title': '操作提示', icon: 0});
                return;
            } else if (medIdS.length > 1) {
                layer.alert("请选择一条数据", {'title': '操作提示', icon: 0});
                return;
            }

            var row1 = $("#ruleTable > tbody").find("[name='rulecbs']:checked").parent().parent();
            var row2;
            if (type == "up") {
                row2 = row1.prev();
            } else {
                row2 = row1.next();
            }
            if (row1) {
                if (row2.length > 0) {
                    var targetVals = row2.children(":eq(0)").children(":eq(0)").attr("text").split(",");
                    var prId = getRuleSelectedAttrVal(0)[0];
                    var prId2 = targetVals[0];
                    var prorder = getRuleSelectedAttrVal(1)[0];
                    var prorder2 = targetVals[1];
                    var params = {
                        "deptname": areaNameNow,
                        "prId1": prId2,
                        "prOrder1": prorder,
                        "prId2": prId,
                        "prOrder2": prorder2,
                        "type": type
                    };
                    updateSortRuning = true;
                    $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/ydRule/updPLRules',
                        dataType: 'json',
                        cache: false,
                        showDialog: false,
                        data: params,
                        success: function (result) {
                            updateSortRuning = false;
                            if (result.code > 0) {
                                if (type == "up") {
                                    row2.insertAfter(row1);
                                } else {
                                    row1.insertAfter(row2);
                                }
                            } else {
                                layer.alert(result.mess || "操作异常，请稍后再试", {'title': '操作提示', icon: 0});
                            }
                        },
                        fail: function () {
                            updateSortRuning = false;
                        }
                    });
                } else {
                    layer.alert(type == "up" ? "已是第一条，无法上移" : "已是最后一条，无法下移", {'title': '操作提示', icon: 0});
                }
            } else {
                layer.alert("请选择数据再操作", {'title': '操作提示', icon: 0});
            }
        }

        function myHasPermission() {
            var changeSpan = $("#changeSpan")[0];
            if (typeof(changeSpan) == "undefined") {
                layer.alert("您没有修改批次的权限", {'title': '操作提示', icon: 0});
                return;
            }
        }

        function changeBatch(obj) {
            var prId = $(obj).attr("id").replace("rule_", "");
            var param = {"prId": prId, "batchId": $(obj).val(), "deptname": areaNameNow}
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/ydRule/updForceRule',
                dataType: 'json',
                cache: false,
                data: param,
                success: function (result) {
                    if (result.code > 0) {
                        showAreaData();
                    } else {
                        layer.alert(result.mess || "操作异常，请稍后再试", {'title': '操作提示', icon: 0});
                    }
                }
            });
        }

        function changeForce(obj) {
            var changeSpan = $("#changeSpan")[0];
            if (typeof(changeSpan) == "undefined") {
                layer.alert("您没有修改批次的权限", {'title': '操作提示', icon: 0});
                if ($("#label_" + $(obj).attr("id").replace("rule_", "")).text() == 0) {
                    $(obj).removeAttr("checked");
                } else {
                    $(obj).attr("checked", true);
                }
                return;
            }
            var prId = $(obj).attr("id").replace("rule_", "");
            var param = {"prId": prId, "enabled": ($(obj).prop("checked") ? 1 : 0), "deptname": areaNameNow}
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/ydRule/updForceRule',
                dataType: 'json',
                cache: false,
                data: param,
                success: function (result) {
                    if (result.code > 0) {
                        showAreaData();
                    } else {
                        layer.alert(result.mess || "操作异常，请稍后再试", {'title': '操作提示', icon: 0});
                    }
                }
            });
        }

        function showAreaData() {
            medicIDs = [];
            ruleTable.ajax.reload();
        }

        var searchParm;

        function qryList(otherParm) {
            if (typeof(otherParm) != "undefined") {
                searchParm = otherParm;
            }
            var categoryId = $("#medicamentCateDIV input:checked").val();
            var param = [];
            param.push({"name": "filterMedicArea", "value": areaCodeNow});
            param.push({"name": "prType", "value": prType});
            if (categoryId != -1) {
                param.push({"name": "categoryId", "value": categoryId});
            }
            if (typeof(searchParm) != "undefined") {
                param = param.concat(searchParm);
            }

			qryParam = param;
            medicTable.ajax.reload();
        }

        function qryMedicamentCategory() {
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/ydRule/getMdcmCate',
                dataType: 'json',
                cache: false,
                showDialog: false,
                success: function (result) {
                    $.each(result, function (index, value) {
                        $("#medicamentCateDIV").append("<div><input type='checkbox' style='vertical-align: middle' onchange='medicamentTypeChanged(this)' value='" + value.categoryId + "'/><label>" + value.categoryName + "</label></div>");
                    });
                }
            });
        }

        function medicamentTypeChanged(obj) {
            $("#medicamentCateDIV input[type='checkbox']").each(function (checkIndex) {
                $(this).removeAttr("checked");
            });
            $(obj).attr("checked", true);
            qryList();
        }
	</script>
	
</head>
<body>

<div id="divMain1" style="width:100%;height:100%;margin-top:10px;" >
	
	<table style="height: 100%;width:100%;" >
	<tr>
		<td class="td200Left ui-td-left" >
			<div id="leftTop" class="divLeftMenuAll" >
			全部
			</div>
			<div id="divArea" class="divLeftMenuList" >
				<table id="tabArea" class="tabWith100" >
					<c:forEach items="${inpAreaList}" var="area" varStatus="rowStatus" >
					<tr class="trArea <c:if test="${rowStatus.index==0}">areaSel</c:if>" >
						<td class="tdLeft10" areacode="${area.deptCode}" >${area.deptName}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</td>
		<td>
			
			<table class="tabMain2" >
				<tr style="height: 26px;" >
					<td colspan="2" class="search-div" >
						<shiro:hasPermission name="PIVAS_BTN_663">
							<a class="ui-search-btn ui-btn-bg-green" id="synDataDicBtn" >是否应用到其他病区</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="PIVAS_BTN_664">
							<a class="ui-search-btn ui-btn-bg-green" id="aAdd" ><i class="am-icon-plus"></i><span>新增</span></a>
						</shiro:hasPermission>
						<shiro:hasPermission name="PIVAS_BTN_666">
							<a class="ui-search-btn  ui-btn-bg-red" id="aDel"><i class="am-icon-trash"></i><span>删除</span></a>
		                </shiro:hasPermission>
             			<a class="ui-search-btn ui-btn-bg-green" onclick="showAreaData()" ><i class="am-icon-refresh"></i><span>刷新</span></a>
             			<shiro:hasPermission name="PIVAS_BTN_665">
             				<span id="changeSpan"></span>
             			</shiro:hasPermission>
             			
             			<shiro:hasPermission name="PIVAS_BTN_667">
             			<div class="down-arrow" style=""  onclick="updateSort('down')">
							<img src="${pageContext.request.contextPath}/assets/pivas/images/down.png" onclick="updateSort('down')">
						</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="PIVAS_BTN_668">
             			<div class="up-arrow" style=""  onclick="updateSort('up')">
							<img src="${pageContext.request.contextPath}/assets/pivas/images/up.png" onclick="updateSort('up')">
						</div>
						</shiro:hasPermission>
					</td>
				</tr>
			<tr>
				<td class="tdSortContent" >
					<div class="divSortContent">
						<table id="ruleTable" class="table datatable ui-data-table display dataTable no-footer">
							<thead>
							<tr>
								<th><input type='checkbox' name='cbs'/></th>
								<th>prId</th>
								<th>prOrder</th>
								<th>batchId</th>
								<th>prType</th>
								<th>deptcode</th>
								<th>medicId</th>
								<th>药品名称</th>
								<th>批次</th>
								<th>是否强制</th>
								<th>enabled</th>
							</tr>
							</thead>
						</table>
					</div>
				</td>
			</tr>
			</table>
			
		</td>
	</tr>
	</table>
	


</div>

		<%-- 新增编辑弹出框 --%>
		<div id="divAddMed" title="<spring:message code='pivas.medLevel.addMedTitle'/>" align="center" style="display: none;">
			<div style="width:20%;height:100%;float:left;padding:15px 10px">
				<p style="text-align: left;margin:0px 0px;margin-bottom:10px;font-size: 16px;font-family: 微软雅黑;">药品分类</p>
				<div id="medicamentCateDIV" style="text-align: left;width:100%">
					<div><input type="checkbox" style="vertical-align: middle" onchange="medicamentTypeChanged(this)" value="-1" checked /><label>全部</label></div>
				</div>
			</div>
			<div style="width:80%;float:right; ">
				<div class="main-div" >
					<div data-qryMethod funname="qryList" class="ui-search-header ui-search-box" id="qryView-div" style="display: inline; ">
						分类：<input name="categoryNames" size="8" data-cnd="true">&nbsp;&nbsp;
						名称：<input name="medicamentsName" size="8" data-cnd="true">&nbsp;&nbsp;
						编码：<input name="medicamentsCode" size="8" data-cnd="true">&nbsp;&nbsp;
						产地：<input name="medicamentsPlace" size="8" data-cnd="true">&nbsp;&nbsp;
						<button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()">搜索</button>&nbsp;&nbsp;
						<button  class="ui-search-btn ui-btn-bg-yellow"  onclick="cndRest()">清空</button>
					</div>
				</div>
				<div style="width:100%;overflow-x:auto ">
			        <div class="tbl">
						<table id="medicTable" class="table datatable ui-data-table display dataTable no-footer">
							<thead>
							<tr>
								<th><input type='checkbox' name='mediccbs'/></th>
								<th><spring:message code="medicaments.categoryId"/></th>
								<th><spring:message code="medicaments.medicamentsName"/></th>
								<th><spring:message code="medicaments.medicamentsCode"/></th>
								<th><spring:message code="medicaments.medicamentsSpecifications"/></th>
								<th><spring:message code="medicaments.medicamentsDosage"/></th>
								<th><spring:message code="medicaments.medicamentsSuchama"/></th>
								<th><spring:message code="medicaments.medicamentsPackingUnit"/></th>
								<th><spring:message code="medicaments.medicamentsMenstruum"/></th>
								<th><spring:message code="medicaments.medicamentsPlace"/></th>
								<th><spring:message code="medicaments.medicamentsUserInfo"/></th>
								<th>是否高危</th>
							</tr>
							</thead>
						</table>
			        </div>
		        </div>
	        </div>
		</div>
		
		
		<%-- 新增编辑弹出框 --%>
		<div id="divSynToOther" title="选择病区" align="center" style="display: none;">
			<table style="width: 100%;" >
				<tr style="height:35px;">
					<td style="width: 10%;text-align: right;" >
						<input type="checkbox" class="chkAllArea" >
					</td>
					<td style="width: 40%;" >
						全部
					</td>
					<c:forEach items="${inpAreaList}" var="area" varStatus="rowStatus" >
					<c:if test="${rowStatus.index==0}">
					<td style="width: 10%;text-align: right;" >
						<input type="checkbox" class="chkOneArea" value="${area.deptCode}" deptcode="${area.deptCode}" deptname="${area.deptName}" >
					</td>
					<td style="width: 40%;" >
						${area.deptName}
					</td>
					</c:if>
					</c:forEach>
				</tr>
				
				<c:forEach items="${inpAreaList}" var="area" varStatus="rowStatus" >
				<c:if test="${rowStatus.index>0}">
				
				<c:if test="${rowStatus.index%2==1}">
				<tr style="height:35px;">
				</c:if>
				
					<td style="width: 10%;text-align: right;" >
						<input type="checkbox" class="chkOneArea" value="${area.deptCode}" deptcode="${area.deptCode}" deptname="${area.deptName}" >
					</td>
					<td style="width: 40%;" >
						${area.deptName}
					</td>
				
				<c:if test="${rowStatus.index%2==0}">
				</tr>
				</c:if>
				
				</c:if>
				</c:forEach>
				
			</table>
		</div>


</body>
</html>