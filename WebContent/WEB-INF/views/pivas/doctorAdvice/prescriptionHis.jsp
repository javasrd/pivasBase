<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp"%>

<html>
<head>
<link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>

<style type="text/css">
.button:hover {
	color: blue;
}

.medicine-tab table td {
	padding: 0px 0px;
	height: 26px;
}

.multiSelectOptions {
	width: 99px;
}

.cbit-grid div.bDiv td div {
	cursor: pointer;
}

#txt__1 {
	border-style: solid;
	border-width: 0;
	background: transparent;
	border: 0;
	outline: none;
	width: 120px;
}

#txt__2 {
	border-style: solid;
	border-width: 0;
	background: transparent;
	border: 0;
	outline: none;
	width: 120px;
}

.medicine-tab {
	margin-top: 10px;
}

.tabs-title {
	height: 28px;
}
.oe_searchview .oe_searchview_clear {
	margin-top: 10px;
}

.oe_searchview .oe_searchview_search {
	margin-top: 36px;
}

.oe_searchview {
	line-height: 18px;
	border: 1px solid #ababab;
	background: white;
	width: 323px;
	-moz-border-radius: 13px;
	cursor: text;
	padding: 1px 0;
	float: left;
	border: 1px solid #ababab;
	margin-top: 10px;
}
</style>
<link href="${pageContext.request.contextPath}/assets/pivas/css/edit.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	var paramAll ;
	var filterVal ;
 	var area_1 = "";
	var batchVal ;
    var datatable;

    function datePick() {
        var searchStartTime = $('#scrqStart').val();
        var searchEndTime = $('#scrqEnd').val();

        if (searchEndTime < searchStartTime) {
            layer.alert('结束时间必须大于等于起始时间！', {'title': '操作提示', icon: 0});
        }
        if (searchEndTime >= searchStartTime) {
            search();
        }
    }

    function initDate(){
        $("#scrqStart").val(getCurrentDate("yyyy-MM-dd", null, -1));
        $("#scrqEnd").val(getCurrentDate("yyyy-MM-dd", null, -1));
	}

    $(function() {
        initDate();

        $("#filterCnd").bind("change", function () {
            if ($(this).val() == "unprint" || $(this).val() == "") {

            } else {
                $("#scrq").val("");
            }
            filterVal = $(this).val();
            search();
        });

        $("#batchSelect").bind("change", function () {
            batchVal = $(this).val();
            search();
        });

        $("#scrq").bind("change", function () {
            if ($(this).val() && $(this).val() != "") {
            } else {
                search();
            }
        });
		
		$("#deptNameSelect").multiSelect({ "selectAll": false,"noneSelected": "选择病区","oneOrMoreSelected":"*" },function(){
			area_1 = $("#deptNameSelect").selectedValuesString();
			search();
		});
		
		$("#printBtn").bind("click", function(){
            var ydreorderStatusN = getDataTableSelectRowData($('.datatable'), 'ydreorderStatus');
            if (ydreorderStatusN && ydreorderStatusN.length > 0) {
                var f = false;
                for (var i = 0; i < ydreorderStatusN.length; i++) {
                    if (ydreorderStatusN[i] == 1) {
                        f = true;
                    }
                }
                if (!f) {
                    layer.alert("药单未确认，无法打印", {'title': '操作提示', icon: 0});
                    return;
                }
            } else {
                return;
            }
			
            var pidsjN = getDataTableSelectRowData($('.datatable'), 'pidsj');
            var pidsjS = "";
            for (var k = 0; k < pidsjN.length; k++) {
                if (k == 0) {
                    pidsjS = pidsjN[k];
                } else {
                    pidsjS = pidsjS + "," + pidsjN[k];
                }
            }
            if (pidsjS != "") {
                $.ajax({
                    url: "${pageContext.request.contextPath}/printLabel/print",
                    type: "POST",
                    data: {
                        printType: 0,
                        isHistoryPrint: 1,
                        pidsj: pidsjS
                    },
                    success: function (data) {
                        if (data.success) {
                            for (var k = 0; k < pidsjN.length; k++) {
                                $("#print_sts_" + pidsjN[k]).html("已打印");
                            }
                            window.open("${pageContext.request.contextPath}/printLabelDownLoad/<shiro:principal property="account"/>/" + data.msg);
                        } else {
                            layer.alert(data.description, {'title': '操作提示', icon: 0});
                        }
                    }
                });
            }
        });

		$("#aSearch__1").bind("click",function(){
			search();
		});

        initDatatable();

        //全选/反选
        $(".checkall").click(function () {
            var check = $(this).prop("checked");
            $(".checkchild").prop("checked", check).change();
        });
	});

	function search(){
        datatable.ajax.reload();
	}
	function qryList(param){
		paramAll = param ;
		search();
	}

	function showDetail(pidsj){
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/ydInfo',
			dataType : 'html',
			cache : false,
			data : {"pidsj":pidsj},
			success : function(data) {
				$("#ydInfo").html(data).show();
				$("#ydHis").hide();
			}
		});
	}

	function backMain(){
		$("#ydInfo").hide().html("");
		$("#ydHis").show();
	}
    var patientName = "" ;
    var colorF = false ;
    function initDatatable() {
        datatable = $('.datatable').DataTable({
            "dom": '<"toolbar">frtip',
            "searching": false,
            "processing": true,
            "serverSide": true,
            "select": true,
            "ordering": true,
            "order": [],
            "pageLength": 20,
            "language": {
                "url": "${pageContext.request.contextPath}/assets/datatables/cn.json"
            },
            "createdRow": function (row, data, index) {
                $(row).on('change', 'td:eq(0) > input[type="checkbox"]', function(){
                    if ( $(this).parents('tbody').find('input[type="checkbox"]:checked').size() > 0 ) {
                        $("#printBtn").show();
                    } else {
                        $("#printBtn").hide();
                    }
                });
                var startDayDelNum = data.startDayDelNum;
                var startTimeS = data.startTimeS;
                var ydzt = data.ydzxzt;
                var hour = startTimeS.substring(startTimeS.indexOf(" ") + 1, startTimeS.indexOf(":"));
                if (startDayDelNum >= 0) {
                    if (hour >= 12) {
                        $(row).find("td").each(function (i) {
                            $(this).css("color", "rgba(234,147,147,1)");
                            $(this).css("border-bottom", "1px dotted #ddd");
                        });
                    } else if (hour >= 11) {
                        $(row).find("td").each(function (i) {
                            $(this).css("color", "#6F96E5");
                            $(this).css("border-bottom", "1px dotted #ddd");
                        });
                    } else {
                        $(row).find("td").each(function (i) {
                            //$(this).css("color", "black");
                            $(this).css("border-bottom", "1px dotted #ddd");
                        });
                    }
                }

                var patientName2 = data.caseId;
                if (patientName != "" && patientName != patientName2) {
                    $(row).find("td").each(function (i) {
                        $(this).css("border-top", "2px solid black");
                    });
                }
                if(colorF){
                    $(row).find("td").each(function(i){
                        $(this).css("background-color", "#efeff7");
                    });
                }else{
                    $(row).find("td").each(function(i){
                        $(this).css("background-color", "white");
                    });
                }

                if (ydzt == -1 || ydzt == 1 || ydzt == 2) {
                    $(row).find("td").each(function(i){
                        $(this).css("color", "red");
                    });
                }
                colorF = !colorF ;
                patientName = patientName2;

            },
            "ajax": {
                "url": "${pageContext.request.contextPath}/doctorAdvice/qryYdPage",
                "type": "post",
                "data": function (d) {
                    var params = [];
                    if (paramAll) {
                        params = paramAll.concat();
                    }
                    params.push({"name": "pcNotNull", "value": 1});
                    params.push({"name": "scrqStart", "value": $("#scrqStart").val()});
                    params.push({"name": "scrqEnd", "value": $("#scrqEnd").val()});
                    params.push({"name": "filter", "value": filterVal});
                    params.push({"name": "areaS", "value": area_1});
                    params.push({"name": "pcS", "value": batchVal});
                    params.push({"name": "compareTime", "value": 1});
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
                {"data": null, "bSortable": false},
                {"data": "bedno", "bSortable": false},
                {"data": "pb_num", "bSortable": false},
                {"data": "ydreorderStatus", "bSortable": false},
                {"data": "parentNo", "bSortable": false},
                {"data": "yyrqS", "bSortable": false},
                {"data": "wardName", "bSortable": false},
                {"data": "freqCode", "bSortable": false},
                {"data": "patname", "bSortable": false},
                {"data": "age", "bSortable": false},
                {"data": "transfusion", "bSortable": false},
                {"data": "drugname", "bSortable": false},
		 		{"data": "dose", "bSortable": false},
                {"data": "doseUnit", "bSortable": false},
                {"data": "quantity", "bSortable": false},
                {"data": "medicamentsPackingUnit", "bSortable": false},
                {"data": "dybz", "bSortable": false},
                {"data": "startTimeS", "bSortable": false},
                {"data": "yzlx", "bSortable": false},
                {"data": "ydzxzt", "bSortable": false},
                {"data": null, "bSortable": false}
            ],
            columnDefs: [
                {
                    targets: 0,
                    render: function (data, type, row) {
                        return '<input type="checkbox" class="checkchild" value="" />';
                    }
                },
                {
                    targets: 3,
                    render: function (data, type, row) {
                        return data == 0 ? "未确认" : "已确认";
                    }
                },
                {
                    targets: 9,
                    render: function (data, type, row) {
                        return data + getDicValue('ageUnit', row.ageunit);
                    }
                },
                {
                    targets: 16,
                    render: function (data, type, row) {
                        return "<label id='print_sts_" + row.bottleLabelNum + "'>" + getDicValue('printStatus', data) + "</label>";
                    }
                },
                {
                    targets: 18,
                    render: function (data, type, row) {
                        return getDicValue('yzlx', data);
                    }
                },
                {
                    targets: 19,
                    render: function (data, type, row) {
                        return getDicValue('ydzxzt', data);
                    }
                },
                {
                    targets: 20,
                    render: function (data, type, row) {
                        return "<a class='button ui-btn-bg-green' style='text-decoration:none;' href='#' onclick=showDetail('" + row.pidsj + "') >详情</a>";
                    }
                }
            ]
        });
    }

    function getDataTableSelectRowData(dom, key){
        var ary = [];
        $(dom).find('tbody input[type="checkbox"]:checked').each(function(){
            ary.push(datatable.row($(this).parents('tr')).data()[key]);
        });
        return ary;
    }

	</script>
</head>
<body>
		<div class="tabs-content-main" style="margin-top: -2px;border-top: 1.5px solid;">
			<div class="tab-content-1">
				<div id="ydHis" class="main-div" style="width:100%">
					<div style="height: 32px;float: left;margin-top: 12px;z-index: 1000000;float:right">
						<table data-qryMethod funname="qryList">
							<tr>
								<td>生成日期：</td>
								<td><input type="text" id="scrqStart"
									style="color: #555555;height:26px;width: 100px;" class="Wdate"
									empty="false" readonly="readonly"
									onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:datePick,skin:'whyGreen'});">
								</td>
								
								<td>
								<span>~</span>
								<input type="text" id="scrqEnd"
									style="color: #555555;height:26px;width: 100px;" class="Wdate"
									empty="false" readonly="readonly"
									onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:datePick,skin:'whyGreen'});">
								</td>
								<td>&nbsp;过滤器：</td>
								<td><select id="filterCnd"
									style="width:80px;height: 26px;margin-top: -2px;">
										<option value="">--请选择--</option>
										<option value="unprint">未打印</option>
										<option value="yyrqToday">今日用药</option>
										<option value="yyrqNextDay">明日用药</option>
										<option value="yesterday">昨日用药</option>
								</select> &nbsp;</td>
								<td>批次：</td>
								<td><select id="batchSelect" style="width:80px;margin-top: -2px;  height: 26px;">
										<option value="">--请选择--</option>
										<c:forEach items="${batchList}" var="batch">
											<option value="${batch.id}">${batch.name}</option>
										</c:forEach>
								</select></td>
								<td>&nbsp;
								病区：
								</td>
								<td>
								<select id="deptNameSelect" name="deptNameSelect[]" multiple="multiple" size="5" style="width:100px;">
							    <c:forEach items="${inpAreaList}" var="inpAreaList" varStatus="status">
								    <option value="${inpAreaList.deptCode}">${inpAreaList.deptName}</option>
							    </c:forEach>
								</select>									
								</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;<input placeholder="<spring:message code='pivas.yz2.bedno'/>" name="bednoS" size="8" data-cnd="true"></td>
								<td><input placeholder="<spring:message code='pivas.yz2.patname'/>" name="patnameS" size="8" data-cnd="true"></td>
								<td><input placeholder="<spring:message code='pivas.yz1.parentNo'/>" name="parentNoS" size="8" data-cnd="true"></td>
								<td><input placeholder="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" size="8" data-cnd="true"></td>
								<td>&nbsp;&nbsp;<button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button></td>
								<td>&nbsp;&nbsp;<button   class="ui-search-btn ui-btn-bg-yellow"  onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button></td>
								<td>&nbsp;&nbsp;<a class="ui-search-btn ui-btn-bg-green" id="printBtn" style="display: none;"><i class="am-icon-print"></i><span>打印</span></a></td>
							</tr>
						</table>
					</div>
					<div id="qryView-div__1">
						<div class="search-div" style="height:33px;">
							<div class="oe_view_manager_view_search"></div>
							<a class="button" id="aSearch__1" style="visibility: hidden;"></a>
							<ul id="ulYZCheckMany__1" class="pre-more" tabindex='-1'
								style="display: none;  margin-top: -32px;left: 780px;">
								<li class="liBtH" style="background-color: transparent;">
									<select id="batchSel__1"
									style="background-color: #EBB700;color: white; height: 26px; border-color: transparent;">
										<option value="">
											&nbsp;&nbsp;
											<spring:message code='pivas.yz1.pleaseSelPC2' />
											&nbsp;
										</option>
										<c:forEach items="${batchList}" var="batch">
											<c:if test="${batch.enabled==1}">
												<option value="${batch.id}"
													isSecendAdvice="${batch.isSecendAdvice}">&nbsp;&nbsp;${batch.name}</option>
											</c:if>
										</c:forEach>
								</select>
								</li>
							</ul>
						</div>
					</div>
                        <table class="table datatable ui-data-table display dataTable no-footer">
                            <thead>
                            <tr>
                                <th>
                                    <input type="checkbox" class="checkall" />
                                </th>
                                <th>床号</th>
                                <th>执行批次</th>
                                <th>确认状态</th>
                                <th>组号</th>
                                <th>用药时间</th>
                                <th>病区</th>
                                <th>频次</th>
                                <th>病人</th>
                                <th>年龄</th>
                                <th>输液量(ML)</th>
                                <th>药品名称</th>
                                <th>单次剂量</th>
                                <th>剂量单位</th>
                                <th>药品数量</th>
                                <th>单位</th>
                                <th>打印标志</th>
                                <th>开立时间</th>
                                <th>医嘱类型</th>
                                <th>药单状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                        </table>
				</div>
			</div>
		</div>

	<div id="ydInfo" class="main-div" style="width:100%;dispaly:none">
	</div>
</body>
</html>