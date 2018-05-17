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
		overflow-y: auto;  padding-left: 0px;padding-right: 0px; font-size: 0.75rem;
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
		margin-left:10px;
	}
	.tdSortTitle{
		vertical-align: bottom ;padding-right: 5px;color: white;
	}
	.divSortTitle{
		height: 35px;
		background-color: #6F96E5;
		font-size: 0.75rem;
		line-height: 35px;
		padding-left: 10px;
	}
	.aSortTitle{
		float: right;
		padding-left: 10px;
		margin-top: 7px;
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

	<%--药物优先级 左中右样式 --结束------------------%>

	<%--更多按钮样式
	.pre-more{
		position: absolute; margin-left: 218px;width: 80px;margin-top: 8px;
		display: none;
	}
	.liBtH{
		height: 26px;
	}--%>


	.button{
		 margin-left: 1px;
	}
	.ui-dialog .ui-dialog-buttonpane button{
		    border-color: transparent;
	}

	.ui-td-left {
		border: 1px solid #ddd;
	}
	.search-div{
		padding: 5px 1em 1em 0em;
	}

	</style>

    <script type="text/javascript">
        var _pageWidth = 0;
        var _pageHeight = 0;
        var datatable;

        function resizePageSize() {
            _pageWidth = $(window).width();
            _pageHeight = $(window).height();
            $("#divMain1").css("height", _pageHeight);
            $("#divArea").css("height", _pageHeight - 70);

        }

        var dialogWidth = 500;
        var dialogHeight = 300;
        var medicGridWidth = dialogWidth - 40;
        <%--宽度比弹窗的宽度小--%>
        var medicGridheight = dialogHeight - 210;
        <%--宽度比弹窗的高度小--%>
        var vrType = "${vrType}";
        var areaCodeNow = "${areaCodeNow}";
        var areaNameNow = "${areaNameNow}";
        var batchValArray = [];
        var batchTextArray = [];
        $(function () {
            $(window).resize(function () {
                resizePageSize();
            });
            resizePageSize();

            $("#batchId option").each(function (i, v) {
                batchValArray.push($(this).val());
                batchTextArray.push($(this).text());

            });

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

                        <%--获取到当前勾选的容积规则信息--%>
                        var vrBatches = getSelectedAttrVal(1);
                        var vrBatchStr = "";
                        $.each(vrBatches, function (i, v) {
                            vrBatchStr += v + ",";
                        });
                        vrBatchStr = vrBatchStr.substring(0, vrBatchStr.length - 1);
                        if (areaCodeN != "") {
                            $.ajax({
                                type: 'POST',
                                url: '${pageContext.request.contextPath}/ydRule/synVolRuleToOtherArea',
                                dataType: 'json',
                                cache: false,
                                showDialog: false,
                                data: {
                                    "areaCodeNow": areaCodeNow,
                                    "areaNameNow": areaNameNow,
                                    "areaCodeN": areaCodeN,
                                    "areaNameN": areaNameN,
                                    "vrType": vrType,
                                    "vrBatchStr": vrBatchStr
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

            $("#synDataDicBtn").bind("click", function () {

                <%--获取到当前勾选的容积规则信息--%>
                var vrIDs = getSelectedAttrVal(0);
                if (vrIDs.length == 0) {
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
            $("#divAddBatch").dialog({
                autoOpen: false,
                width: dialogWidth,
                height: dialogHeight,
                modal: true,
                resizable: false,
                buttons: {
                    "<spring:message code='common.confirm'/>": function () {
                        if (!!!$("#editView-form [name='minval']").val()) {
                            $("#editView-form [name='minval']").addClass("ui-state-error");
                            return;
                        } else {
                            $("#editView-form [name='minval']").removeClass("ui-state-error");
                        }
                        if (!!!$("#editView-form [name='maxval']").val()) {
                            $("#editView-form [name='maxval']").addClass("ui-state-error");
                            return;
                        } else {
                            $("#editView-form [name='maxval']").removeClass("ui-state-error");
                        }
                        if (parseFloat($("#editView-form [name='minval']").val()) > parseFloat($("#editView-form [name='maxval']").val())) {
                            layer.alert("容积下限不能超过容积上限！", {'title': '操作提示', icon: 0});
                            return;
                        }
                        if ($("#editView-form [name='vrId']").val() == "") {
                            var param = initAddParam('editView-form');
                            param["deptcode"] = areaCodeNow;
                            param["deptname"] = areaNameNow;
                            param["vrType"] = vrType;
                            $.ajax({
                                type: 'POST',
                                url: '${pageContext.request.contextPath}/ydRule/addVolRule',
                                dataType: 'json',
                                cache: false,
                                data: param,
                                success: function (result) {
                                    if (result.code > 0) {
                                        $("#divAddBatch").dialog("close");
                                        showAreaData();
                                    }
                                    layer.alert(result.mess || "操作异常，请稍后再试", {'title': '操作提示', icon: 0});

                                }
                            });
                        } else {
                            var param = initUpdParam('editView-form');
                            param["deptcode"] = areaCodeNow;
                            param["deptname"] = areaNameNow;
                            param["vrType"] = vrType;
                            $.ajax({
                                type: 'POST',
                                url: '${pageContext.request.contextPath}/ydRule/updVolRule',
                                dataType: 'json',
                                cache: false,
                                data: param,
                                success: function (result) {
                                    if (result.code > 0) {
                                        $("#divAddBatch").dialog("close");
                                        showAreaData();
                                    }
                                    layer.alert(result.mess || "操作异常，请稍后再试", {'title': '操作提示', icon: 0});
                                }
                            });
                        }
                    },
                    "<spring:message code='common.cancel'/>": function () {
                        $(this).dialog("close");
                    }
                },
                close: function () {
                }
            });

            $("#aAdd").bind("click", function () {
                clearParam("editView-form");
                $("#editView-form condition").each(function () {
                    $(this).attr("oldvalue", "");
                });

                var seldBatIds = getSelectedAttrVal(1, false);
                var seldBatIdsStr = "," + seldBatIds.join(",") + ",";
                <%--默认选中 可选择的第一个--%>
                $("#batchId").html("");
                var optionArray = [];
                $.each(batchValArray, function (i, v) {
                    if (seldBatIdsStr.indexOf("," + v + ",") == -1) {
                        if (optionArray.length == 0) {
                            optionArray.push("<option value='" + v + "' selected >" + batchTextArray[i] + "</option>");
                        } else {
                            optionArray.push("<option value='" + v + "'>" + batchTextArray[i] + "</option>");
                        }
                    }
                });
                $("#batchId").append(optionArray.join(""));
                $("#divAddBatch").prev().find('.ui-dialog-title').text("新增容积规则");
                $("#divAddBatch").dialog("open");
            });

            $("#aUpd").bind("click", function () {
                var seldBatIds = getSelectedAttrVal(1);
                if (seldBatIds.length == 1) {
                    var optionArray = [];
                    $.each(batchValArray, function (i, v) {
                        if (seldBatIds[0] == v) {
                            optionArray.push("<option value='" + v + "' selected >" + batchTextArray[i] + "</option>");
                        } else {
                            optionArray.push("<option value='" + v + "'>" + batchTextArray[i] + "</option>");
                        }
                    });
                    $("#batchId").append(optionArray.join(""));

                    $("#editView-form [name='vrId']").val(getSelectedAttrVal(0)[0]);
                    $("#editView-form [name='batchId']").val(seldBatIds[0]);
                    $("#editView-form [name='overrun']").val(getSelectedRowColText(2)[0]);
                    $("#editView-form [name='minval']").val(getSelectedRowColText(3)[0]);
                    $("#editView-form [name='maxval']").val(getSelectedRowColText(4)[0]);
                    $("#divAddBatch").prev().find('.ui-dialog-title').text("修改容积规则");
                    $("#divAddBatch").dialog("open");
                } else if (seldBatIds.length > 1) {
                    layer.alert("只能选择一条操作", {'title': '操作提示', icon: 0});
                } else {
                    layer.alert("请选择数据再操作", {'title': '操作提示', icon: 0});
                }
            });

            $("#aDel").bind("click", function () {
                var vrIDs = getSelectedAttrVal(0);
                if (vrIDs && vrIDs.length > 0) {
                    layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                        btn: ['确定', '取消'], icon: 3, title: '提示'
                    }, function () {
                        var vrIdN = "";
                        for (var i = 0; i < vrIDs.length; i++) {
                            vrIdN = vrIdN + vrIDs[i] + ",";
                        }
                        var param = {};
                        param["deptcode"] = areaCodeNow;
                        param["deptname"] = areaNameNow;
                        param["vrIdN"] = vrIdN;
                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/ydRule/delVolRule',
                            dataType: 'json',
                            cache: false,
                            data: param,
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

            initDatatable();

            $(".datatable > thead").on("change", "input:checkbox", function () {
                var selected = $(this).attr("checked") == "checked";
                $(".datatable > tbody").find("input:checkbox").each(function (i, v) {
                    if (selected) {
                        $(v).attr("checked", 'checked');
                    } else {
                        $(v).removeAttr("checked");
                    }
                });
            });

        });

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
                    "url": "${pageContext.request.contextPath}/ydRule/getVolRuleByArea",
                    "type": "post",
                    "data": function (d) {
                        var params = [{"name": "deptcode", "value": areaCodeNow}, {"name": "vrType", "value": vrType}];
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
                    {"data": "vrId", "bSortable": false},
                    {"data": "vrId", "bSortable": false},
                    {"data": "batchId", "bSortable": false},
                    {"data": "vrType", "bSortable": false},
                    {"data": "deptcode", "bSortable": false},
                    {"data": "batchName", "bSortable": false},
                    {"data": "overrun", "bSortable": false},
                    {"data": "minval", "bSortable": false},
                    {"data": "maxval", "bSortable": false}
                ],
                "columnDefs": [
                    {
                        "targets": 0,
                        "render": function (data, type, row) {
                            var attrText = row.vrId + "," + row.batchId + "," + row.vrType + "," + row.deptcode;
                            return "<input type='checkbox' name='cbs' text='" + attrText + "' value='" + data + "'/>";
                        }
                    },
                    {"visible": false, "targets": [1, 2, 3, 4]}
                ]
            });
        }

        function getSelectedAttrVal(index, selected) {
            var arr = new Array(0);
            $(".datatable > tbody").find("input:" + (selected ? "checked" : "checkbox")).each(function (i, v) {
                arr.push($(v).attr("text").split(",")[index]);
            });
            return arr;
        }

        function getSelectedRowColText(colNum, selected) {
            var arr = new Array(0);
            $(".datatable > tbody").find("input:" + (selected ? "checked" : "checkbox")).each(function (i, v) {
                arr.push(colNum == 0 ? $(v).val() : $(v).parent().nextAll().eq(colNum - 1).text());
            });
            return arr;
        }

        function showAreaData() {
            datatable.ajax.reload();
        }

        function checkNum(obj) {
            if (checkDecimals($(obj))) {
                if ($(obj).val() < 0) {
                    $(obj).val($(obj).attr("oldvalue"));
                } else {
                    return true;
                }
            } else {
                $(obj).val($(obj).attr("oldvalue"));
            }
        }

        <%--校验是否是数字和小数--%>

        function checkDecimals(obj) {
            var patrn = /^([\d]+)([.]?)([\d]*)$/;
            if (patrn.test(obj.val())) {
                return true;
            }
            else {
                return false;
            }
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
						<shiro:hasPermission name="PIVAS_BTN_653">
							<a class="ui-search-btn ui-btn-bg-green" id="synDataDicBtn" >是否应用到其他病区</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="PIVAS_BTN_654">
							<a class="ui-search-btn ui-btn-bg-green" id="aAdd" ><i class="am-icon-plus"></i><span>新增</span></a>
						</shiro:hasPermission>
		                <shiro:hasPermission name="PIVAS_BTN_655">
							<a class="ui-search-btn  ui-btn-bg-yellow" id="aUpd"><i class="am-icon-edit"></i><span>修改</span></a>
		             	</shiro:hasPermission>
		             	<shiro:hasPermission name="PIVAS_BTN_656">
							<a class="ui-search-btn ui-btn-bg-red" id="aDel"><i class="am-icon-trash"></i><span>删除</span></a>
		            	</shiro:hasPermission>
             			<a class="ui-search-btn ui-btn-bg-green" onclick="showAreaData()"><i class="am-icon-refresh"></i><span>刷新</span></a>
					</td>
				</tr>
			<tr>
				<td class="tdSortContent" >
					<div class="divSortContent">
						<table class="table datatable ui-data-table display dataTable no-footer">
							<thead>
							<tr>
								<th><input type='checkbox' name='cbs'/></th>
								<th>vrId</th>
								<th>batchId</th>
								<th>vrType</th>
								<th>deptcode</th>
								<th>批次</th>
								<th>超限值</th>
								<th>下限(毫升/ml)</th>
								<th>上限(毫升/ml)</th>
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



</div><%-- divMain1 --%>

		<div id="divAddBatch" title="新增容积规则" align="center" style="display: none;">
		<form id="editView-form" method="post">
			<div class="popup">
				<div class="row">
					<input type="hidden" name="vrId"/>
					<div class="column">
						<label class="tit">批次：</label>
						<select name="batchId" id="batchId" style="width: 184px;">
							<c:forEach items="${batchList}" var="batch" >
								<option value="${batch.id}">${batch.name}</option>
							</c:forEach>
						</select>
						<span class="mand">*</span>
					</div>
					<div class="column">
						<label class="tit">超限值：</label>
						<input type="text" class="condition" name="overrun" maxlength="20" title="超限值" oldvalue="" onchange="checkNum(this)" />
						<span class="mand"></span>
					</div>
					<div class="column">
						<label class="tit">下限：</label>
						<input type="text" class="condition" name="minval" maxlength="20" title="下限(毫升/ml)" oldvalue="" onchange="checkNum(this)" />
						<span class="mand">*</span>
					</div>
					<div class="column">
						<label class="tit">上限：</label>
						<input type="text" class="condition" name="maxval" maxlength="20" title="上限(毫升/ml)" oldvalue="" onchange="checkNum(this)" />
						<span class="mand">*</span>
					</div>
				</div>
			</div>
		</form>
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
						<input type="checkbox" class="chkOneArea" value="${area.deptCode}" deptname="${area.deptName}">
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
						<input type="checkbox" class="chkOneArea" value="${area.deptCode}" deptname="${area.deptName}">
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