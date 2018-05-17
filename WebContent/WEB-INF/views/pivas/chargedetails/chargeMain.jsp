<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../../common/common.jsp" %>

<html>
<head>

<style type="text/css">
	.cbit-grid div.bDiv td div {
		cursor: pointer;
	}

	.oe_searchview {
		margin-top: 10px;
	}

	.search-div {
		padding: 1em 1em 0.7em 0em;
	}
</style>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<link href="${pageContext.request.contextPath}/assets/pivas/css/edit.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/assets/common/js/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
    var rowParam;
    var refush = false;
    var paramTemp;
    var paramDate;
    var paramCharge;
    var paramAll;
    var areaStr = "";
    var datatable;

    function detail(r) {
        if (r != undefined) {
            rowParam = {
                "gid": r.gid,
                "parentNo": r.parentNo,
                "zxrq": r.zxrq,
                "zxbc": r.zxbc
            };
            showDetail();
        }
    }

    $(function () {
        $("#scrq").val(getCurrentDate("yyyy-MM-dd"));
        paramDate = [];
        paramDate.push({"name": "chargeDate", "value": $("#scrq").val()});
        addParam();

        $("#deptNameSelect").multiSelect({
            "selectAll": false,
            "noneSelected": "选择病区",
            "oneOrMoreSelected": "*"
        }, function () {
            areaStr = $("#deptNameSelect").selectedValuesString();
            qry();
        });

        $("#control_1").multiSelect({
            "selectAll": false,
            "noneSelected": "收费状态",
            "oneOrMoreSelected": "*"
        }, function () {
            var valuestr = $("#control_1").selectedValuesString();
            var param1 = [];
            if (valuestr && valuestr.length > 0) {
                param1.push({"name": "chargeStateS", "value": valuestr});
                setParamCharge(param1);
            } else {
                param1 = [];
                setParamCharge(param1);
            }
        });

        initDatatable();
        datatable.on('dblclick', 'tr', function () {
            var data = datatable.row(this).data();
            detail(data);
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
            "ajax": {
                "url": "${pageContext.request.contextPath}/chargedetails/list",
                "type": "post",
                "data": function (d) {
                    var params = [];
                    if (paramAll) {
                        params = paramAll.concat();
                    }
                    params.push({"name": "areaS", "value": areaStr});
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
                {"data": "pcName", "bSortable": false, "defaultContent":""},
                {"data": "yyrqS", "bSortable": false},
                {"data": "wardName", "bSortable": false},
                {"data": "bedno", "bSortable": false},
                {"data": "patname", "bSortable": false},
                {"data": "age", "bSortable": false},
                {"data": "freqCode", "bSortable": false},
                {"data": "drugname", "bSortable": false},
                {"data": "dose", "bSortable": false},
                {"data": "doseUnit", "bSortable": false},
                {"data": "quantity", "bSortable": false}
            ],
            columnDefs: [
                {
					targets: 11,
					render: function (data, type, row) {
                        return "<a class='button ui-btn-bg-green' style='text-decoration:none;' href='#' onclick='detail(" + JSON.stringify(row) + ")' >详情</a>";
					}
            	}
            ],
        });
    }

    function showDetail() {
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/chargedetails/info',
            dataType: 'html',
            cache: false,
            data: rowParam,
            success: function (data) {
                refush = false;
                $("#chargeDetailDiv").remove();
                $("#yzInfo").html(data).show();
                $("#yzMain").hide();

                $("#chargeDetailDiv").dialog({
                    autoOpen: false,
                    height: 370,
                    width: 650,
                    modal: true,
                    resizable: false,
                    buttons: {
                        "返回": function () {
                            $("#chargeDetailDiv").dialog("close");
                        }
                    },
                    close: function () {
                    }
                });
            }
        });
    }

    function qry() {
        datatable.ajax.reload();
    }

    function setParamCharge(param) {
        paramCharge = [];
        if (param != undefined && param != null) {
            paramCharge = param;
        }
        addParam();
        qry();
    }

    function setParamTemp(param) {
        paramTemp = [];
        if (param != undefined && param != null) {
            paramTemp = param;
        }
        addParam();
        qry();
    }


    function backMain() {
        $("#yzInfo").hide().html("");
        $("#yzMain").show();
        if (refush) {
            qry();
        }
    }

    function chargeDetail(costcode) {
        $("#chargeDetailDiv tbody tr").hide();
        $(".yd_" + costcode).show();
        $("#chargeDetailDiv").dialog("open");
    }
<%--
    function chargeNow(ydPzfId, costcode, charge) {
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/chargedetails/changOne',
            dataType: 'json',
            cache: false,
            data: {"ydPzfId": ydPzfId, "costCode": costcode, "charge": charge},
            success: function (response) {
                if (response == 0) {
                    showDetail();
                    refush = true;
                    layer.alert("收费失败", {'title': '操作提示', icon: 0});
                } else if (response == 1) {
                    showDetail();
                    refush = true;
                    layer.alert("收费成功", {'title': '操作提示', icon: 1});
                } else if (response == 2) {
                    showDetail();
                    refush = true;
                    layer.alert("退费失败", {'title': '操作提示', icon: 0});
                } else if (response == 3) {
                    showDetail();
                    refush = true;
                    layer.alert("退费成功", {'title': '操作提示', icon: 1});
                }
            }
        });
    }
--%>
    function addParam() {
        paramAll = [];
        if (paramTemp != undefined && paramTemp.length > 0) {
            paramAll = paramAll.concat(paramTemp);
        }

        if (paramCharge != undefined && paramCharge.length > 0) {
            paramAll = paramAll.concat(paramCharge);
        }

        if (paramDate != undefined && paramDate.length > 0) {
            paramAll = paramAll.concat(paramDate);
        }

    }

    function datePick() {
        paramDate = [];
        paramDate.push({"name": "chargeDate", "value": $("#scrq").val()});
        addParam();
        qry();
    }

</script>
</head>
<body>
<div id="yzMain">
	<div class="ui-search-header ui-search-box" style="display: inline; float: right" data-qryMethod funname="setParamCharge">
		<label class="tit" id='chargeDateText'>收费日期：</label>
		<input type="text" id="scrq" style="color: #555555;height:26px;width: 100px;" class="Wdate" empty="false" readonly="readonly" onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:datePick,skin:'whyGreen'});"/>

		<select id="control_1" name="control_1[]" multiple="multiple" size="1">
			<option value="0">收费失败</option>
			<option value="1">收费成功</option>
			<option value="2">退费失败</option>
			<option value="3">退费成功</option>
		</select>

		<select id="deptNameSelect" name="deptNameSelect[]" multiple="multiple" size="5" >
			<c:forEach items="${inpAreaList}" var="inpAreaList" varStatus="status">
				<option value="${inpAreaList.deptCode}">${inpAreaList.deptName}</option>
			</c:forEach>
		</select>

		<input placeholder="床号" name="bednoS" size="8" data-cnd="true">
		<input placeholder="病人" name="patnameS" size="8" data-cnd="true">
		<input placeholder="组号" name="parentNoS" size="8" data-cnd="true">
		<input placeholder="病区" name="wardNameS" size="8" data-cnd="true">
		<input placeholder="频次" name="freqCodeS" size="8" data-cnd="true">
		<input placeholder="批次" name="freqZXBCS" size="8" data-cnd="true">
		<button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>
		<button class="ui-search-btn ui-btn-bg-yellow"  onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>
	</div>
	<div class="tbl">
		<table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
			<thead>
			<tr>
				<th>执行批次</th>
				<th>用药时间</th>
				<th>病区</th>
				<th>床号</th>
				<th>病人</th>
				<th>年龄</th>
				<th>频次</th>
				<th>药品名称</th>
				<th>单次剂量</th>
				<th>剂量单位</th>
				<th>药品数量</th>
				<th>操作</th>
			</tr>
			</thead>
		</table>
	</div>
</div>

<div id="yzInfo" class="main-div" style="width:100%;dispaly:none"></div>
</body>
</html>