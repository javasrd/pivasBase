<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/assets/multDropList/multDropList.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/assets/pivas/css/yd.status.main.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
</head>
<body onload="setfocus()">

<div class="medicine-tab" style="width: 100%;">
	<!-- max-width: 1100px; -->
	<div class="tabs-content" style="margin-top: -2px;">
		<div class="tab-content-1" style="display: block;">
			<div id="yzMainDiv__1" class="main-div" style="min-width:1100px;">
				<table data-qryMethod funname="queryList">
					<tr>
						<td >
							<div style="width: 40px;position: relative;top: 8px;">
								<span style="position: relative;top: 4px;">病区：</span>
							</div>
							<div style="width: 150px;position: relative; top: -5px;left: 38px;">
								<select id="deptNameSelect" name="deptNameSelect[]" multiple="multiple" size="5" style="width:100px;">
									<c:forEach items="${inpAreaList}" var="inpAreaList" varStatus="status">
										<option value="${inpAreaList.deptCode}">${inpAreaList.deptName}</option>
									</c:forEach>
								</select>
							</div>
						</td>
						<td >
							用药日期：
							<input type="text" id="yyrq" style="color: #555555; height: 26px; width: 100px;"
								   class="Wdate" empty="false" readonly="readonly"
								   onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:datePick});">
						</td>
						<td >
							医嘱类型：
							<select id="yzlx" style="height: 26px;">
								<option value="0" selected="selected" >长嘱</option>
								<option value="1">临嘱</option>
							</select>
						</td>
						<td >
							<div style="width: 40px;position: relative;top: 8px;">
								<span style="position: relative;top: 4px;">批次：</span>
							</div>
							<div style="width: 150px;position: relative; top: -5px;left: 38px;">
								<select id="batchSelect"  name="batchSelect[]" size="5" style="height: 26px;" multiple="multiple">
									<option value="">--请选择--</option>
									<c:forEach items="${batchList}" var="batch">
										<c:if test="${batch.enabled==1}">
											<option value="${batch.id}" isSecendAdvice="${batch.isSecendAdvice}">${batch.name}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</td>
						<td><input placeholder="<spring:message code='pivas.yz2.bedno'/>" name="bednoS" size="8" data-cnd="true"></td>
						<td><input placeholder="<spring:message code='pivas.yz2.patname'/>" name="patnameS" size="8" data-cnd="true"></td>
						<td><input placeholder="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" size="8" data-cnd="true"></td>
						<td><input placeholder="<spring:message code='pivas.yz1.drugname'/>" name="drugnameQry" size="8" data-cnd="true"></td>
						<td><button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-plus"></i><span>搜索</span></button></td>
						<td>&nbsp;&nbsp;<button class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button></td>

						<td>
							<shiro:hasPermission name="PIVAS_BTN_964">
								<div style=""><input placeholder="扫描" id="scanText" type="text" style="width: 220px;" />
									<a class="ui-search-btn ui-btn-bg-green" id="scanButton" style="" >一键签收</a></div>
							</shiro:hasPermission>
						</td>
					</tr>

				</table>

				<table style="margin-top: 10px;table-layout: fixed;min-width:1000px;" >
					<tbody>
					<tr>
						<td width="50%" style="vertical-align: top;" id="gridTd">

							<div style="width: 100%; display: flex; margin-top: 15px;line-height: 20px;">
								<div class="circleClass0"></div>
								<span class="stcolor0">已停止</span>
								<div class="circleClass2"></div>
								<span class="stcolor2">未确认</span>
								<div class="circleClass3"></div>
								<span class="stcolor3">已确认</span>
								<div class="circleClass4"></div>
								<span class="stcolor4">已打印</span>
								<div class="circleClass5"></div>
								<span class="stcolor5">已入仓</span>
								<div class="circleClass7"></div>
								<span class="stcolor7">已出仓</span>
								<div class="circleClass8"></div>
								<span class="stcolor8">已签收</span>
								<div class="circleClass-1"></div>
								<span class="stcolor-1">退费</span>
							</div>

							<div style="width: 100%; margin-top: 10px;">
									<table class="table datatable ui-data-table display dataTable no-footer">
										<thead>
										<tr>
											<th>床号</th>
											<th>批次</th>
											<th>病人</th>
											<th>频次</th>
											<th>医嘱状态</th>
											<th>医嘱类型</th>
											<th>状态</th>
										</tr>
										</thead>
									</table>
							</div>
						</td>

						<td  style="vertical-align: top;">
							<div class="rightDiv" style="background-color: white; width: 100%; margin-top: 45px;"  id="leftHeight">
								<div style="background-color: #5d6ebd; text-align: left; color: white;
			height: 40px; padding-top: 10px;padding-left: 10px; font-size: 15px;">瓶签号 <span class="fontBold" id="bottleLabelNum"></span>
								</div>
								<div style="padding-left: 0px; padding-right: 10px; margin-top: 7px;">

									<table id="yzInfo__1" class="table-yz-info">
										<tr style="background: #E1E9F8; padding-left: 5px; padding-right: 5px;">
											<td width="20%" style="text-align: right;">开立医生：</td>
											<td width="30%" class="fontBold" name="doctorName"></td>
											<td width="20%" style="text-align: right;">开立时间：</td>
											<td width="30%" class="fontBold" name="startTimeS"></td>
										</tr>
										<tr style="background: #E1E9F8; padding-left: 5px; padding-right: 5px;border-bottom: 1px dashed;">
											<td width="20%" style="text-align: right;">病区：</td>
											<td width="30%" class="fontBold" name="wardName"></td>
											<td width="20%" style="text-align: right;">结束时间：</td>
											<td width="30%" class="fontBold" name="endTimeS"></td>
										</tr>
										<tr style="background: #E1E9F8; padding-left: 5px; padding-right: 5px;">
											<td width="20%" style="text-align: right;">病人姓名：</td>
											<td width="30%" class="fontBold" name="patname"></td>
											<td width="20%" style="text-align: right;">体重：</td>
											<td width="30%" class="fontBold" name="avdp"></td>
										</tr>
										<tr style="background: #E1E9F8; padding-left: 5px; padding-right: 5px;">
											<td width="20%" style="text-align: right;">年龄：</td>
											<td width="30%" class="fontBold" name="age"></td>
											<td width="20%" style="text-align: right;">频次：</td>
											<td width="30%" class="fontBold" name="freqCode"></td>
										</tr>
										<tr style="background: #E1E9F8; padding-left: 5px; padding-right: 5px;">
											<td width="20%" style="text-align: right;">床号：</td>
											<td width="30%" class="fontBold" name="bedno"></td>
											<td width="20%" style="text-align: right;">滴速：</td>
											<td width="30%" class="fontBold" name="dropSpeed"></td>
										</tr>
										<tr style="background: #E1E9F8; padding-left: 5px; padding-right: 5px;">
											<td width="20%" style="text-align: right;">性别：</td>
											<td width="30%" class="fontBold" name="sex"></td>
											<td width="20%" style="text-align: right;">出生日期：</td>
											<td width="30%" class="fontBold" name="birthdayS"></td>
										</tr>
										<tr style="background: transparent;">
											<td colspan="4" style="height: 7px;"></td>
										</tr>
									</table>

									<div style="overflow-y:scroll;background: #5d6ebd;padding-left:10px;">
										<table>
											<thead>
											<tr style="">
												<td style="color:white;" width="15%">药品编码</td>
												<td style="color:white;" width="40%">药品名称</td>
												<td style="color:white;" width="15%">规格</td>
												<td style="color:white;" width="15%">剂量</td>
												<td style="color:white;" width="15%">数量</td>
											</tr>
											</thead>
										</table>
									</div>
									<div style="height:150px;overflow-y:scroll;">
										<table id="medicTab__1" >
											<thead style="display:none;">
											<tr style="background: #5d6ebd;padding-left:10px;">
												<td width="15%">药品编码</td>
												<td width="40%">药品名称</td>
												<td width="15%">规格</td>
												<td width="15%">剂量</td>
												<td width="15%">数量</td>
											</tr>
											</thead>
											<tbody id="medicTabBody">

											</tbody>
										</table>
									</div>
									<div style="overflow-y:scroll;background: #5d6ebd;padding-left:10px;">
										<table>
											<thead>
											<tr style="">
												<td style="color:white;" width="30%">追踪状态</td>
												<td style="color:white;" width="40%">操作时间</td>
												<td style="color:white;" width="30%">操作人</td>
											</tr>
											</thead>
										</table>
									</div>
									<div style="min-height:140px;overflow-y:scroll;" id="bottomHeight">
										<table id="traceTab" >
											<thead style="display:none;">
											<tr style="background: #B5B5B5;">
												<td width="30%">追踪状态</td>
												<td width="40%">操作时间</td>
												<td width="30%">操作人</td>
											</tr>
											</thead>
											<tbody id="traceBody">
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

</body>
<script type="text/javascript">
    var paramAll;
    var pcStr;
    var areaCodeStr;
    var tCount;
    var currentDay = getCurrentDate("yyyy-MM-dd", null, 0);
    var datatable;

    $(function() {

        $("#yyrq").val(getCurrentDate("yyyy-MM-dd", null, 0));

        $("#batchSelect").multiSelect({ "selectAll": false,"noneSelected": "选择批次","oneOrMoreSelected":"*" },function(){
            pcStr = $("#batchSelect").selectedValuesString();
            search();
        });

        $("#deptNameSelect").multiSelect({ "selectAll": false,"noneSelected": "选择病区","oneOrMoreSelected":"*" },function(){
            areaCodeStr = $("#deptNameSelect").selectedValuesString();
            search();
        });

        $("#yzlx").change(function(){
            search();
        });

        $("#scanButton").click(function(){
            var params = [
                {"name" : "yzlx","value" : $("#yzlx").val()},
                {"name" : "yyrq","value" : $("#yyrq").val()},
                {"name" : "areaS","value" : areaCodeStr},
                {"name" : "pcS","value" : pcStr}
            ];
            if (paramAll) {
                params = params.concat(paramAll);
            }

            $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/doctorAdvice/signForAll',
                dataType : 'json',
                cache : false,
                //async : true,
                data : params,
                success : function(data) {
                    if(data.code == -1){
                        layer.alert(data.mess, {'title': '操作提示', icon: 0});
                    }else if(data.code == 1){
                        layer.alert(data.mess, {'title': '操作提示', icon: 0});
                        search();
                    }
                },
                error : function(data){
                    layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 2});

                }
            });

        });

        timedCount();

        //状态类型
        var yzStatus = {
            0: '已停止',
            2: '未确认',
            3: '已打印',
            5: '已入仓',
            7: '已出仓',
            8: '已签收',
            '-1': '退费'
        };

        $("#scanText").focus();

        initDatatable();
    });

    function setfocus(){
        $("#scanText").focus();
    }

    function initDatatable() {
        datatable = $('.datatable').DataTable({
            "dom": '<"toolbar">frtip',
            "searching": false,
            "processing": true,
            "serverSide": true,
            "select": true,
            "ordering": false,
            "order": [],
            "pageLength": 50,
            "language": {
                "url": "${pageContext.request.contextPath}/assets/datatables/cn.json"
            },
            "preDrawCallback": function () {
                //初始化右侧页面
                $("#yzInfo__1 .fontBold").each(function() {
                    $(this).html("");
                });

                $("#bottleLabelNum").html("");

                $("#medicTabBody .medicTd").remove();
                $("#traceBody .medicTd").remove();
            },
            "drawCallback": function () {

            },
            "createdRow": function (row, data, idx) {
                $(row).on('click', function () {
                    $(".datatable tr").each(function () {
                        $(this).removeClass("selected");
                    });
                    $(this).addClass('selected');
                    showYzDetail(data.pidsj);
                });

                if (idx > 0) {
                    if ( datatable.row(idx - 1).data()['parentNo'] != data['parentNo'] ) {
                        $(row).find("td").css("border-top", "2px solid black");
                    }
                } else {
                    $(row).addClass("selected");
                    showYzDetail(data['pidsj']);
                }
            },
            "ajax": {
                "url" : "${pageContext.request.contextPath}/doctorAdvice/qryYdPage",
                "type": "post",
                "data": function (d) {
                    var params = [];
                    if (paramAll) {
                        params = paramAll.concat();
                    }

                    params.push({
                        "name" : "pcNotNull",
                        "value" : 1
                    });
                    params.push({
                        "name" : "yzlx",
                        "value" : $("#yzlx").val()
                    });
                    params.push({
                        "name" : "yyrq",
                        "value" : $("#yyrq").val()
                    });

                    params.push({
                        "name" : "areaS",
                        "value" : areaCodeStr
                    });
                    params.push({
                        "name" : "pcS",
                        "value" : pcStr
                    });
                    params.push({
                        "name" : "compareTime",
                        "value" : compareTime($("#yyrq").val())
                    });
                    params.push({
                        "name" : "ydStatus",
                        "value" : "1"
                    });

                    params.push({
                        "name" : "pici",
                        "value" : "1"
                    });
                    params.push({
                        "name" : "operationLog",
                        "value" : "1"
                    });

					for (var index in params) {
						if(params[index].value){
							d[params[index].name] = params[index].value;
						}
					}
                },
                "dataSrc": function (data) {
                    data.data = data.rawRecords;
                    data.recordsFiltered = data.total;
                    data.recordsTotal = data.total;
                    return data.data;
                }
            },
			"columns": [
                {"data": "bedno", "bSortable": false},
                {"data": "zxbc", "bSortable": false},
                {"data": "patname", "bSortable": false},
                {"data": "freqCode", "bSortable": false},
                {"data": "ydzxzt", "bSortable": false},
                {"data": "yzlx", "bSortable": false},
                {"data": "pqYdzt", "bSortable": false}
            ],
            columnDefs: [
                {
                    targets: 1,
                    render: function (v, type, _row) {
                        if (currentDay <= _row.scrqS && _row.rucangOKNum < 1 && _row.ydzxzt == 0) {
                            return "<select id='yd__1_"
                                + _row.pidsj
                                + "' oldvalue='"
                                + v
                                + "' yzlx='"+_row.yzlx
                                + "' parentno='"
                                + _row.parentNo
                                + "' pidsj='"
                                + _row.pidsj
                                + "' scrqS='"
                                + _row.scrqS
                                + "' onchange='changePC(this)' style='width:70px'  >" //"<option value='' >--请选择--</option>"
                                + "<c:forEach items='${batchList}' var='batch' ><option value='${batch.id}' issecendadvice='${batch.isSecendAdvice}' "
                                + (v == '${batch.id}' ? "selected='selected'" : "")
                                + " >${batch.num}</option></c:forEach>"
                                + "</select>";
                        } else {
                            return _row.pb_num;
                        }
                    }
                },
                {
                    targets: 4,
                    render: function (v) {
                        return v == 1 ? "停止" : "执行中";
                    }
                },
                {
                    targets: 5,
                    render: function (v) {
                        return v == 0 ? "长嘱" : "临嘱";
                    }
                },
                {
                    targets: 6,
                    render: function (v, type, _row) {
                        return "<input class='circleClass"
                            + (_row.ydzxzt == 1 ? 0 : (v == -1 ? v : ((_row.ydreorderStatus == 1 && v > 3) ? v : (_row.ydreorderStatus == 1 ? 3 : 2))))
                            + "' readonly='readonly' />";
                    }
                }
            ]
        });
    }

    function timedCount(){
        var value = $("#scanText").val();
        if(value != "" && typeof(value) != "undefined" ){
            value = value.trim();
            $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/doctorAdvice/signFor',
                dataType : 'json',
                cache : false,
                //async : true,
                data : {"pqStr":value},
                success : function(data) {
                    if(data.code == -1){
                        layer.alert(data.mess, {'title': '操作提示', icon: 0});
                    }else if(data.code == 1){
                        search();
                    }

                    $("#scanText").val("");
                    tCount=setTimeout("timedCount()",1000);

                },
                error : function(data){
                    layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 2});
                    $("#scanText").val("");
                    tCount=setTimeout("timedCount()",1000);
                }
            });

        }else{
            tCount=setTimeout("timedCount()",1000);
        }

    }



    function datePick() {
        search();
    }

    function queryList(param) {
        paramAll = param;
        search();
    }

    function search(param) {
        datatable.ajax.reload();
        $("#scanText").focus();
    }

    function showYzDetail(pidsj) {
        if (pidsj && pidsj != "") {
            $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/doctorAdvice/prescriptionDetail',
                dataType : 'json',
                cache : false,
                showDialog : false,
                data : {
                    "pidsj" : pidsj
                },
                success : function(result) {
                    if (result && result.code > 0) {
                        var ydMain = result.data.ydMain;

                        $("#bottleLabelNum").html(ydMain['bottleLabelNum'] == null ? "" : ydMain['bottleLabelNum']);

                        $("#yzInfo__1 .fontBold").each(function() {
                            $(this).html(ydMain[$(this).attr("name")] ? ydMain[$(this).attr("name")]: "");
                        });

                        //$("#yzInfo__1 [name=printStatus]").html(ydMain['dybz'] == 0 ? "已打印" : "未打印");
                        //$("#yzInfo__1 [name=ydzxzt]").html(ydMain['ydzxzt'] == 0 ? "执行中" : "停止");
                        //$("#yzInfo__1 [name=yzlx]").html(ydMain['yzlx'] == 0 ? "长嘱" : "临嘱");
                        $("#yzInfo__1 [name=sex]").html(ydMain['sex'] == 0 ? "女" : "男");
                        //$("#yzInfo__1 [name=medNames]").html(result.data.medNames ? result.data.medNames : "");
                        //$("#yzInfo__1 [name=transfusion]").html(ydMain['transfusion'] + "ml");
                        $("#yzInfo__1 [name=age]").html(ydMain['age'] + getDicValue("ageUnit", ydMain['ageunit']));

                        $("#medicTabBody .medicTd").remove();
                        var ydList = result.data.ydList;
                        for ( var i in ydList) {
                            $("#medicTabBody").append(
                                "<tr class='medicTd'><td width='15%'>"
                                + ydList[i].chargeCode
                                + "</td><td width='40%'>"
                                + ydList[i].drugname
                                + "</td><td width='15%'>"
                                + ydList[i].specifications2
                                + "</td><td width='15%'>"
                                + ydList[i].dose
                                + ydList[i].doseUnit
                                + "</td><td width='15%'>"
                                + ydList[i].quantity
                                + ydList[i].medicamentsPackingUnit
                                + "</td></tr>");
                        }

                        $("#traceBody .medicTd").remove();
                        var recordList = result.data.recordList;
                        for ( var n in recordList) {
                            $("#traceBody").append(
                                "<tr class='medicTd'><td width='30%' >"
                                + recordList[n].type_name
                                + "</td><td width='40%'>"
                                + recordList[n].operation_time
                                + "</td><td width='30%'>"
                                + recordList[n].operator
                                + "</td></tr>");
                        }

                    } else {
                        $("#yzInfo__1 .fontBold").each(function() {
                            $(this).html("");
                        });
                        $("#bottleLabelNum").html("");
                        $("#medicTabBody .medicTd").remove();
                        $("#traceBody.medicTd").remove();
                    }
                }
            });
        } else {
        }
    }
    function changePC(obj) {

        var pidsj = $(obj).attr("pidsj");
        var parentno = $(obj).attr("parentno");
        var pcId = $(obj).val();
        var isSecendAdvice = $(obj).find("option:selected").attr("issecendadvice");
        var parent_pc_map = {};

        var yzlx = $(obj).attr("yzlx");
        $(".datatable select").each(
            function() {
                if ($(this).attr("pidsj") != pidsj) {
                    parent_pc_map[$(this).attr("parentno") + "__1_"
                    + $(this).attr("oldvalue")] = 1;
                }
            });
        if (parent_pc_map[$(obj).attr("parentno") + "__1_" + pcId] != null) {
            $(obj).val($(obj).attr("oldvalue"));
            layer.alert("同一组中已存在这个批次，无法修改", {'title': '操作提示', icon: 0});
            return;
        }

        var oldV = $(obj).attr("oldvalue");
        var oldPcName = $("#batchSelect").next(".multiSelectOptions").find("input[value='"+oldV+"']").parent("label").text().trim();
        var newPcName = $("#batchSelect").next(".multiSelectOptions").find("input[value='"+pcId+"']").parent("label").text().trim();

        $.ajax({
            type : 'POST',
            url : '${pageContext.request.contextPath}/doctorAdvice/ydPCSubmit',
            dataType : 'json',
            cache : false,
            data : {
                "pcDataList" : pidsj,
                "parentnoS" : parentno,
                "piId" : pcId,
                "isSecendAdvice" : isSecendAdvice,
                "oldPcIdListS" : $(obj).attr("oldvalue"),
                "oldPcNameS" : oldPcName,
                "newPcNameS" : newPcName,
                "from" : "management",
                "yyrq":$("#yyrq").val(),
                "yzlx":yzlx
            },
            success : function(response) {
                if (response.code == 0) {
                    $(obj).attr("oldvalue", pcId);
                    $(obj).parents("tr:first").css("color", "#C71585");

                } else {
                    $(obj).val($(obj).attr("oldvalue"));
                    layer.alert(response.msg, {'title': '操作提示', icon: 0});
                }

                $("#scanText").focus();
            }
        });
    }
</script>

</html>