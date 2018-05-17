<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp"%>

<html>
<head>
<link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
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
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<script type="text/javascript">
	var paramAll, area = "", datatable;
	function datePick() {
		var searchStartTime = $('#yyrqStart').val();
		var searchEndTime = $('#yyrqEnd').val();
		if (searchEndTime < searchStartTime) {
			layer.alert('结束时间必须大于等于起始时间！', {'title': '操作提示', icon: 0});
		}
		if (searchEndTime >= searchStartTime) {
			search();
		}
	}

	$(function () {
		$("#yyrqStart").val(getCurrentDate("yyyy-MM-dd", null, 0));
		$("#yyrqEnd").val(getCurrentDate("yyyy-MM-dd", null, 0));
		$("#deptNameSelect").multiSelect({
			"selectAll": false,
			"noneSelected": "选择病区",
			"oneOrMoreSelected": "*"
		}, function () {
			area = $("#deptNameSelect").selectedValuesString();
			search();
		});

		$("#checkRefund").bind("click", function () {
			layer.confirm('检查不合理药单？', {
				btn: ['确定', '取消'], icon: 3, title: '提示'
			}, function () {
				$.ajax({
					type: 'POST',
					url: '${pageContext.request.contextPath}/refundRP/checkRefund',
					dataType: 'json',
					data: [{name: 'yyrqStart', value: $("#yyrqStart").val()},
						{name: 'yyrqEnd', value: $("#yyrqEnd").val()}
					],
					cache: false,
					success: function (data) {
						layer.alert(data.msg, {'title': '操作提示', icon: 1});
						search();
					},
					error: function () {
						layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
					}
				});
			});
		});

		initDatatable();
	});

	function search() {
		datatable.ajax.reload();
	}

	function qryList(param) {
		paramAll = param;
		search();
	}

	function operate(obj) {
		var id = $(obj).attr("ydRefundID");
		layer.confirm('确定要置为已处理吗？', {
			btn: ['确定', '取消'], icon: 3, title: '提示'
		}, function (index) {
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/refundRP/processRefund',
				dataType: 'json',
				cache: false,
				data: [{name: 'id', value: id}
				],
				success: function (data) {
					layer.alert("操作成功！", {'title': '操作提示', icon: 0});
					datatable.ajax.reload(null,false);
				},
				error: function () {
					layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 2});
				}
			});
		});
	}

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
            "preDrawCallback": function () {
            },
            "drawCallback": function () {
            },
			"ajax": {
				url: "${pageContext.request.contextPath}/refundRP/list",
				"type": "post",
				"data": function (d) {
					var params = [];
					if (paramAll) {
						params = paramAll.concat();
					}
					params.push({"name": "yyrqStart", "value": $("#yyrqStart").val()});
					params.push({"name": "yyrqEnd", "value": $("#yyrqEnd").val()});
					params.push({"name": "areaS", "value": area});
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
				{"data": "wardName", "bSortable": false},
				{"data": "bedNo", "bSortable": false},
				{"data": "patName", "bSortable": false},
				{"data": "groupNo", "bSortable": false},
				{"data": "occDT", "bSortable": false},
				{"data": "drugFreq", "bSortable": false},
				{"data": "drugName", "bSortable": false},
				{"data": "quantity", "bSortable": false},
				{"data": "quantityUnit", "bSortable": false},
				{"data": "errorTypeName", "bSortable": false},
				{"data": "state", "bSortable": false}
			],
			columnDefs: [
				{
					targets: 11,
					render: function (data, type, row) {
                        var ctx = row.processState;
                        if (ctx == "未处理"){
                            ctx="<span onclick='operate(this);' id='confirmImg_" + row.id + "' ydRefundID = '" + row.id + "' class=\'ui-search-btn ui-btn-bg-green\'><i class=\"el-icon-circle-check\"></i><span>&nbsp;处理</span></span>";
                        }
                        return ctx;
					}
				}
			],
		});
	}
</script>
</head>
<body>
		<div class="tabs-content-main"
			style="margin-top: -2px;border-top: 1.5px solid;">
			<div class="tab-content-1">
				<div id="ydHis" class="main-div" style="width:100%">
					<div style="height: 32px;float: left;margin-top: 12px;z-index: 1000000;float:right">
						<table data-qryMethod funname="qryList">
							<tr>
								<td>用药日期：</td>
								<td><input type="text" id="yyrqStart"
									style="color: #555555;height:26px;width: 100px;" class="Wdate"
									empty="false" readonly="readonly"
									onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:datePick});">
								</td>
								
								<td>
								<span>~</span>
								<input type="text" id="yyrqEnd"
									style="color: #555555;height:26px;width: 100px;" class="Wdate"
									empty="false" readonly="readonly"
									onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:datePick});">
								</td>
								<td>
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
								<%--<td><input placeholder="<spring:message code='pivas.yz1.wardname'/>" name="wardNameS" size="8" data-cnd="true"></td>--%>
								<td>&nbsp;&nbsp;<button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button></td>
								<td>&nbsp;&nbsp;<button   class="ui-search-btn ui-btn-bg-yellow"  onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button></td>
								<td><a class="ui-search-btn ui-btn-bg-green" style="margin-left:10px;" id="checkRefund"><i class="am-icon-check"></i><span>药单检查</span></a></td>
							</tr>
						</table>
					</div>
						<table class="table datatable ui-data-table display">
							<thead>
							<tr>
								<th>病区</th>
								<th>床号</th>
								<th>病人</th>
								<th>组号</th>
								<th>用药时间</th>
								<th>频次</th>
								<th>药品名称</th>
								<th>药品数量</th>
								<th>单位</th>
								<th>错误类型</th>
								<th>药单状态</th>
								<th>药单处理</th>
							</tr>
							</thead>
						</table>
				</div>
			</div>
		</div>
</body>

</html>