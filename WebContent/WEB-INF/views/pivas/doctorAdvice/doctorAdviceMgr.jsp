<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/assets/multDropList/multDropList.css" rel="stylesheet" type="text/css" />
	<script src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>

	<style type="text/css">
		.medicine-tab {
			margin-top: 15px;
		}

		.rightDiv table tr:nth-child(2n) {
			background: transparent;
		}



		.fontBold {
			font-weight: bold;
		}

		.trSelect {
			background-color: #ffeece; /*#E1E9F8;*/
		}

		.button {
			margin-left: 5px;
		}

		.button:hover{
			color:blue;
		}

		#searchModel table td{
			vertical-align: top;
		}

		#mainTable table > td{
			vertical-align: top;
		}

	</style>

</head>
<body>
<div class="medicine-tab" style="width: 100%;">
	<div id="yzMainDiv__1" class="main-div">
		<div id="searchModel">
			<table style="width:auto;" data-qryMethod funname="queryList">
				<tr>
					<td>
						<select id="stateSelect" style="height:26px;">
							<option value="">请选择</option>
							<option value="todayNew">新医嘱</option>
							<option value="unCheck">未审核</option>
							<option value="checkOK">审方通过</option>
							<option value="checkNO">审方不通过</option>
							<option value="forcePack">强制打包</option>
							<option value="refuse">拒绝</option>
						</select>
					</td>
					<td>
						<div style="width: 40px;position: relative;top: 0px;">
							<span style="position: relative;top: 4px;">病区：</span>
						</div>
						<div style="width: 150px;position: relative; top: -13px;left: 38px;">
							<select id="deptNameSelect" name="deptNameSelect[]" multiple="multiple" size="5" style="width:100px;">
								<c:forEach items="${inpAreaList}" var="inpAreaList" varStatus="status">
									<option value="${inpAreaList.deptCode}">${inpAreaList.deptName}</option>
								</c:forEach>
							</select>
						</div>
					</td>
					<td>
						<div style="width: 80px;position: relative;top: 0px;">
							<span style="position: relative;top: 4px;">医嘱类型：</span>
						</div>
						<div style="width: 98px;position: relative; top: -15px;left: 58px;">
							<select id=yzlxSelect style="height: 26px;">
								<option value="0" selected="selected">长嘱</option>
								<option value="1">临嘱</option>
							</select>
						</div>
					</td>
					<td><input placeholder="<spring:message code='pivas.yz2.bedno'/>" name="bednoS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz2.patname'/>" name="patnameS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz1.parentNo'/>"  name="parentNoS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz1.drugname'/>" name="drugnameQry" size="8" data-cnd="true"></td>
					<td><button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button></td>
					<td>&nbsp;&nbsp;<button class="ui-search-btn ui-btn-bg-yellow"  onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button></td>
				</tr>
			</table>
		</div>

		<div id="mainContainer">
			<table id="mainTable" style="height: 100%; width: 100%;">
				<tr>
					<td width="50%">
						<div style="width:100%;height:100%">
							<table class="table datatable ui-data-table display dataTable no-footer">
								<thead>
								<tr>
									<th>审方结果</th>
									<th>床号</th>
									<th>病人姓名</th>
									<th>组号</th>
									<th>审方日期</th>
									<th>医嘱类型</th>
								</tr>
								</thead>
							</table>
						</div>
					</td>
					<td>
						<div class="rightDiv" style="width: 100%;height:100%;">
							<div style="background-color: #5d6ebd; text-align: left; color: white; height: 40px; padding-top: 10px;padding-left: 10px; font-size: 14px;">
								组号<span id="parentnoId"></span>
							</div>
							<div style="padding-left: 0px; padding-right: 5px; margin-top: 7px;background: #fff;padding-bottom:20px;">
								<table id="yzInfo__1" class="table-yz-info">
									<tr>
										<td colspan="4" style="height: 7px;">
											<div class="fontBold" name="pidsj" style="visibility: hidden; height: 0px; max-height: 0px;"></div>
											<div class="fontBold" name="drugname" style="visibility: hidden; height: 0px; max-height: 0px;"></div>
											<div class="fontBold" name="yzzdshzt" style="visibility: hidden; height: 0px; max-height: 0px;"></div>
											<div class="fontBold" name="yzzdshbtglx" style="visibility: hidden; height: 0px; max-height: 0px;"></div>
										</td>
									</tr>
									<tr style="background: #E1E9F8; padding-left: 5px; padding-right: 5px;">
										<td width="20%" style="text-align: right;">开立医生：</td>
										<td width="30%" class="fontBold" name="doctorName"></td>
										<td width="20%" style="text-align: right;">开立时间：</td>
										<td width="30%" class="fontBold" name="startTimeS"></td>
									</tr>
									<tr style="background: #E1E9F8; padding-left: 5px; padding-right: 5px;">
										<td width="20%" style="text-align: right;">病区：</td>
										<td width="30%" class="fontBold" name="wardName"></td>
										<td width="20%" style="text-align: right;">结束时间：</td>
										<td width="30%" class="fontBold" name="endTimeS"></td>
									</tr>
									<tr>
										<td colspan="4" style="height: 7px;"></td>
									</tr>
									<tr style="background: #E1E9F8; padding-left: 5px; padding-right: 5px;">
										<td width="20%" style="text-align: right;">病人姓名：</td>
										<td width="30%" class="fontBold" name="patname"></td>
										<td width="20%" style="text-align: right;">床号：</td>
										<td width="30%" class="fontBold" name="bedno"></td>
									</tr>
									<tr style="background: #E1E9F8; padding-left: 5px; padding-right: 5px;">
										<td width="20%" style="text-align: right;">性别：</td>
										<td width="30%" class="fontBold" name="sex"></td>
										<td width="20%" style="text-align: right;">体重：</td>
										<td width="30%" class="fontBold" name="avdp"></td>

									</tr>
									<tr style="background: #E1E9F8; padding-left: 5px; padding-right: 5px;">
										<td width="20%" style="text-align: right;">年龄：</td>
										<td width="30%" class="fontBold" name="age"></td>
										<td width="20%" style="text-align: right;">出生日期：</td>
										<td width="30%" class="fontBold" name="birthdayS"></td>
									</tr>
								</table>

								<div style="overflow-y:scroll;margin-top: 10px">
									<table id="medicTitle" width="100%" style="border-spacing: 1px;">
										<thead>
										<tr style=";background: #5d6ebd;">
											<td style="color:white;padding-left:10px;" width="15%">药品编码</td>
											<td style="color:white;"  width="40%">药品名称</td>
											<td style="color:white;"  width="20%">规格</td>
											<td style="color:white;"  width="15%">剂量</td>
											<td style="color:white;"  width="10%">数量</td>
										</tr>
										</thead>
									</table>
								</div>

								<div id="medicModel" style="overflow-y:scroll;min-height:80px;max-height:140px;">
									<table id="medicTab__1" style="border-spacing: 1px;">
										<thead style="display:none;">
										<tr style="background: #B5B5B5;">
											<td width="15%">药品编码</td>
											<td width="40%">药品名称</td>
											<td width="20%">规格</td>
											<td width="15%">剂量</td>
											<td width="10%">数量</td>
										</tr>
										</thead>
										<tbody id="medicTabBody">
										</tbody>
									</table>
								</div>

								<hr style="border: 1px dashed gray;" />
								<table style="border-collapse: separate;border-spacing: 10px;" id = "checkPassNot">
									<tr style="height: 30px; font-size: 15px;">
										<td style="width:100px;">不通过原因：</td>
										<td>
											<select style="height: 23px;" id="yzshbtglx__1" disabled="disabled">
												<option value=""><spring:message code='comm.mess19' /></option>
												<c:forEach items="${errTypeList}" var="errType">
													<option value="${errType.gid}">${errType.name}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td style="width:100px;"><label>备注：</label></td>
										<td><textarea style="width: 300px;height:100px;"
													  id="yzshbtgyy__1" maxlength="256" disabled="disabled"> </textarea></td>
									</tr>
									<tr>
										<td>
											<label>病区复核：</label>
										</td>
										<td>
											<input id = "recheckcause" style="width: 200px;" id="bqfh" maxlength="256" >
											<a class="button" id="refuseButton">申诉</a>
											<a class="button" id="compulsoryButton">强制打包</a>
										</td>
									</tr>
								</table>
							</div>
						</div>
				</tr>
			</table>
		</div>

	</div>
</div>

</body>
<script type="text/javascript">
    var paramTemp;
    var areaCodeStr;
    var pageHeight = window.innerHeight - 90;
    var datatable;

    $(function() {
        $("#yzlxSelect").change(function(){
            queryYZ();
        });

        $("#stateSelect").change(function(){
            queryYZ();
        });

        $("#deptNameSelect").multiSelect({ "selectAll": false,"noneSelected": "选择病区","oneOrMoreSelected":"*" },function(){
            areaCodeStr = $("#deptNameSelect").selectedValuesString();
            queryYZ();
        });

        initDatatable();
    });

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
                $("#yzInfo__1 [name=yzzt]").html("");
                $("#yzInfo__1 [name=medNames]").html("");

                $("#medicTab__1.medicTd").remove();
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
                    if ( datatable.row(idx - 1).data()['inhospno'] != data['inhospno'] ) {
                        $(row).find("td").css("border-top", "2px solid black");
                    }
                } else {
                    $(row).addClass("selected");
                    showYzDetail(data['pidsj']);
                }
            },
            "ajax": {
                "url" : "${pageContext.request.contextPath}/doctorAdvice/qryPtPageByYZ",
                "type": "post",
                "data": function (d) {
                    var params = [
                        {name:"inpatientString",value:areaCodeStr},
                        {name:"yzlx",value:$("#yzlxSelect").val()},
                        {name:"yzztLowN",value:1},
                        {name:"state",value:$("#stateSelect").val()},
                    ];
                    if (paramTemp) {
                        params = params.concat(paramTemp);
                    }
                    for (var index in params) {
                        d[params[index].name] = params[index].value;
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
                {"data": "yzshzt", "bSortable": false},
                {"data": "bedno", "bSortable": false},
                {"data": "patname", "bSortable": false},
                {"data": "parentNo", "bSortable": false},
                {"data": "sfrqS", "bSortable": false},
                {"data": "yzlx", "bSortable": false}
            ],
            columnDefs: [
                {
                    targets: 0,
                    render: function (v, type, _row) {
                        var recheckstate = _row.recheckstate;
                        return "<label id='pidsj_"+_row.pidsj+"'>"
                            + (v == 0 ? "未审核": (v == 1 ? ( recheckstate == 1? "强制打包":"通过"):(recheckstate == 2? "拒绝":"不通过")))
                            + "</label>";
                    }
                },
                {
                    targets: 4,
                    render: function (v, type, _row) {
                        return "<label id='sfrq_"+_row.pidsj+"'>"+ v + "</label>";
                    }
                },
                {
                    targets: 5,
                    render: function (v) {
                        return v == 0 ? "长嘱" : "临嘱";
                    }
                }
            ]
        });
    }

    function queryList(param){
        paramTemp = param;
        queryYZ();
    }

    function queryYZ(param){
        datatable.ajax.reload();
    }

    function showYzDetail(pidsj) {
        if (pidsj && pidsj != "") {
            $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/doctorAdvice/dctrAdvcInfo',
                dataType : 'json',
                cache : false,
                showDialog : false,
                data : {
                    "pidsj" : pidsj
                },
                success : function(result) {
                    if (result && result.code > 0) {
                        var yzMain = result.data.yzMain;

                        var parentNo = yzMain['parentNo'];
                        $("#parentnoId").html(parentNo);

                        $("#yzInfo__1 .fontBold").each(
                            function() {
                                if ($(this).attr("name") == "sex") {
                                    $(this).html(
                                        yzMain[$(this).attr("name")] ? getDicValue("sex",yzMain[$(this).attr("name")])
                                            :
                                            (yzMain[$(this).attr("name")] === 0 ? getDicValue("sex",yzMain[$(this).attr("name")]): ""));
                                } else if ($(this).attr("name") == "age") {
                                    var age = yzMain[$(this).attr("name")]|| '';
                                    if (age) {
                                        if (age != 0) {
                                            $(this).html(age+ getDicValue("ageUnit",yzMain["ageunit"]));
                                        }
                                    }
                                } else {
                                    $(this).html(yzMain[$(this).attr("name")] ? yzMain[$(this).attr("name")]: "");
                                }

                            });
                        $("#yzInfo__1 [name=yzzt]").html(getDicValue('yzzt', yzMain['yzzt']));
                        $("#yzInfo__1 [name=medNames]").html(result.data.medNames ? result.data.medNames: "");

                        if (yzMain.yzshzt == 1) {
                            $("#checkPassNot").hide();
                            $("#yzshbtglx__1").val("");
                            $("#yzshbtgyy__1").val("");
                            $("#recheckcause").val("");
                        } else if (yzMain.yzshzt == 2) {
                            $("#checkPassNot").show();
                            $("#yzshbtglx__1").val(yzMain.yzshbtglx);
                            $("#yzshbtgyy__1").val(yzMain.yzshbtgyy);
                            $("#recheckcause").val(yzMain.recheckcause);
                        } else if (yzMain.yzshzt == 0) {
                            $("#checkPassNot").hide();
                            $("#yzshbtglx__1").val("");
                            $("#yzshbtgyy__1").val("");
                            $("#recheckcause").val("");
                        }
                        $("#medicTab__1 .medicTd").remove();
                        var yzList = result.data.yzList;
                        for ( var i in yzList) {
                            $("#medicTab__1")
                                .append(
                                    "<tr class='medicTd'><td>"
                                    + yzList[i].chargeCode
                                    + "</td><td>"
                                    + yzList[i].medicamentsName
                                    + "</td><td>"
                                    + yzList[i].specifications2
                                    + "</td><td>"
                                    + yzList[i].dose
                                    + yzList[i].doseUnit
                                    + "</td><td>"
                                    + yzList[i].quantity
                                    + yzList[i].medicamentsPackingUnit
                                    + "</td></tr>");
                        }
                    } else {
                        $("#yzInfo__1 .fontBold").each(function() {
                            $(this).html("");
                        });
                        $("#medicTab__1 .medicTd").remove();
                    }
                }
            });
        }


        $("#refuseButton").bind("click", function() {
            var pidsj = $("#yzInfo__1 [name=pidsj]").html();
            if (!pidsj) {
                return;
            }
            // 申诉
            to_review(pidsj, 1);
        });

        $("#compulsoryButton").bind("click", function() {
            var pidsj = $("#yzInfo__1 [name=pidsj]").html();
            if (!pidsj) {
                return;
            }
            // 强制打包
            to_review(pidsj, 2);
        });

        function to_review(pidsj, reviewType) {
            var remark = "申诉:";
            if (reviewType == 2)
            {
                remark = "强制打包:";
            }
            var param = {
                "pidsj" : pidsj,
                "yzshbtgyy" : remark +　$("#recheckcause").val()
            };

            $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/doctorAdvice/toReview',
                dataType : 'json',
                cache : false,
                //showDialog: false,
                data : param,
                success : function(response) {
                    if (response.code == 0) {
                        message({
                            html : response.msg
                        });
                    } else {
                        message({
                            html : response.msg
                        });
                    }
                }
            });
        }
    }

</script>

</html>