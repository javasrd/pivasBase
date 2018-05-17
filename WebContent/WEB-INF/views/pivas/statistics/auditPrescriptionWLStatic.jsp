<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../common/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
.header {
	margin: 10px 0px 10px 0px;
}

body {
	font-size: 0.75em
}

select {
	height: 24px;
	padding-left: 5px;
	padding-right: 5px;
}

input[type='text'] {
	padding-left: 5px;
	padding-right: 5px;
}
.ui-combobox-input
</style>
<script type="text/javascript">
	var userLanguage = '${sessionScope.language}' === 'zh' ? 'zh-cn' : 'en';
</script>
<script src="${pageContext.request.contextPath}/assets/echarts/echarts.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/echarts/echartsUtil.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/multDropList/multDropList.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<link href="${pageContext.request.contextPath}/assets/multDropList/multDropList.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
    var myChart2;
    var myChart

    function clear(chart) {
        if (chart != undefined) {
            chart.clear();
            chart = null;
        }
    }

    function toBirt() {
        $("#birtImg").addClass("ui-btn-bg-green");
        $("#barImg").removeClass("ui-btn-bg-green");
        $("#pieImg").removeClass("ui-btn-bg-green");

        $("#barDiv").hide();
        $("#pieDiv").hide();
        $("#birtReport").show();
        $("#stDimValueDivID").hide();
        $("#doctorSearchDivID").show();
        <%-- $("#deptNameSearchDivID").hide();--%>
        $("#flowStatusID").val("0");

        var userLanguage = '${sessionScope.language}' === 'en' ? 'en_US' : 'zh';
        var statisticsTime = $('#statisticsTime').val();
        var doctorSearchIDStr = $("#doctorSearchID").selectedValuesString();
        var deptNameSearchstr = $("#deptNameSearchID").selectedValuesString();

        var url = contextPath
            + "/run?__report=report/auditPrescriptionStatistics.rptdesign&__parameterpage=false&__toolbar=false&__locale="
            + userLanguage
            + encodeURI("&title=<spring:message code='pivas_shtj.title'/>(")
            + statisticsTime
            + ")"
            + encodeURI("&zbr=<spring:message code='pivas.statistics.tab'/>")
            + "<spring:escapeBody htmlEscape='true'><shiro:principal property='name'/></spring:escapeBody>";

        url += "&searchTime=";
        if (statisticsTime != null && statisticsTime !== '') {
            url += statisticsTime;
        }
        url += "&searchDoctor=";
        if (doctorSearchIDStr != null && doctorSearchIDStr !== '') {
            url += doctorSearchIDStr;
        }
        url += "&searchDeptName=";
        if (deptNameSearchstr != null && deptNameSearchstr !== '') {
            url += deptNameSearchstr;
        }

        var iheight = parseFloat($(document).height()) - 100;
        $("#birtReport iframe").css("height", iheight).attr("src", url);
        clear(myChart2);
        clear(myChart);
        $("#exportImg").show();
    }

    function toBar() {
        $("#birtImg").removeClass("ui-btn-bg-green");
        $("#barImg").addClass("ui-btn-bg-green");
        $("#pieImg").removeClass("ui-btn-bg-green");

        $("#birtReport").hide();
        $("#birtReport iframe").attr("src", "");

        $("#barDiv").show();
        $("#pieDiv").hide();
        $("#stDimValueDivID").show();
        $("#flowStatusID").val("1");

        clear(myChart2);
        clear(myChart);

        $("#doctorSearchDivID").show();
        <%-- $("#deptNameSearchDivID").hide();--%>
        queryAuditPrescriptionBar();

        $("#exportImg").hide();
    }

    function toPie() {
        $("#birtImg").removeClass("ui-btn-bg-green");
        $("#barImg").removeClass("ui-btn-bg-green");
        $("#pieImg").addClass("ui-btn-bg-green");

        $("#birtReport").hide();
        $("#birtReport iframe").attr("src", "");
        $("#barDiv").hide();
        $("#pieDiv").show();

        $("#stDimValueDivID").show();
        $("#flowStatusID").val("2");

        clear(myChart);
        clear(myChart2);
        $("#doctorSearchDivID").show();
        <%-- $("#deptNameSearchDivID").hide();--%>
        initPieDoctor();

        $("#exportImg").hide();
    }

    function changeState() {
        var flowStatusID = $("#flowStatusID").val();
        if (flowStatusID == 1) {
            toBar();
        } else if (flowStatusID == 2) {
            toPie();
        } else if (flowStatusID == 0) {
            toBirt();
        }
    }


    function queryAuditPrescriptionBar() {
        var option = {
            domId: "barDiv",
            legendNames: [],
            xAxisData: [],
            seriesData: []
        };

        var statisticsTime = $('#statisticsTime').val();
        var doctorSearchIDStr = $("#doctorSearchID").selectedValuesString();

        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/statistics/auditPrescription/queryAuditPrescriptionBar',
            dataType: 'json',
            cache: false,
            data: [{name: 'searchTime', value: statisticsTime},
                {name: 'searchDoctorIds', value: doctorSearchIDStr}],
            success: function (data) {
                if (data.doctorNameList != null && data.doctorNameList.length > 0) {
                    $.each(data.doctorNameList, function (index, value) {
                        option.xAxisData.push(value);
                    });

                    if (data.status2DoctorList != null) {
                        $.each(
                            data.status2DoctorList,
                            function (index, value) {
                                var configFeeType = {
                                    name: '',
                                    type: 'bar',
                                    stack: ' ',
                                    data: []
                                };

                                option.legendNames
                                    .push(value.statusName);
                                configFeeType.name = value.statusName;
                                configFeeType.data = value.doctorNameCountList;
                                option.seriesData
                                    .push(configFeeType);

                            });
                    }
                }

                createBar(option);
            }
        });

    }

    function initPieDoctor() {
        option = {
            seriesName: '审方工作量：',
            data: [{
                value: 0,
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                }
            }],
            domId: 'main3',
            clickCall: clickPieDoctor
        };
        var statisticsTime = $('#statisticsTime').val();
        var doctorSearchIDStr = $("#doctorSearchID").selectedValuesString();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/statistics/auditPrescription/queryDoctorPieList',
            dataType: 'json',
            cache: false,
            data: [{
                name: 'searchTime',
                value: statisticsTime
            }, {
                name: 'searchDoctorIds',
                value: doctorSearchIDStr
            }, {
                name: 'compareResult',
                value: compareTime($('#statisticsTime').val())
            }],
            success: function (data) {
                $.each(data, function (index, value) {
                    option.data.push(value);
                });
                myChart = createPie(option);
            }
        });

    }

    function clickPieDoctor(param) {
        // 释放资源重新创建
        if (myChart2) {
            myChart2.clear();
            myChart2 = null;
        }

        var option = {
            seriesName: '审方结果：',
            domId: 'main4',
            legendNames: [],
            data: [{
                value: 0,
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                }
            }

            ],
            radius: '40%'
        };

        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/statistics/auditPrescription/queryDoctorStatusPieList',
            dataType: 'json',
            cache: false,
            data: [{
                name: 'doctorID',
                value: param.data.doctorId
            }, {
                name: 'searchTime',
                value: $('#statisticsTime').val()
            }, {
                name: 'compareResult',
                value: compareTime($('#statisticsTime').val())
            }],
            success: function (data) {
                //option.data = data;
                $.each(data, function (index, value) {
                    var item = {
                        name: '',
                        value: 10
                    };
                    //var configFreeTypes = $('#configFreeType option[value=' + value.statusKey +']').text();
                    item.name = value.statusName;
                    item.value = value.ststsCount;
                    option.legendNames.push(value.statusName);
                    option.data.push(item);
                });
                myChart2 = createPie(option);
            }
        });
    }

    function time() {
        var dateFmt = 'yyyy-MM-dd';
        var dateType = $("#dateType").val();
        if ("0" == dateType) {
            dateFmt = 'yyyy-MM';
        }

        WdatePicker({
            isShowClear: false,
            dateFmt: dateFmt,
            readOnly: true,
            onpicked: function (dp) {
                changeState();
            }
        });
    }

    function changeDateType() {
        var dateFmt = 'yyyy-MM-dd';
        var dateType = $("#dateType").val();
        if ("0" == dateType) {
            dateFmt = 'yyyy-MM';
        }

        var currentDate = getCurrentDate(dateFmt, null, 0);
        $('#statisticsTime').val(currentDate);
        changeState();
    }

    var english = true;
    var contextPath;
    $(function () {
        english = "zh-cn" == userLanguage ? false : true;

        // 设置默认统计时间
        changeDateType();

        //querySelectData();

        contextPath = '${pageContext.request.contextPath}';
        // 初始化echarts
        initEcharts(contextPath);

        toBirt();

        $("#doctorSearchID").multiSelect({
            "selectAll": false,
            "noneSelected": "选择审方药师",
            "oneOrMoreSelected": "选择审方药师"
        }, function () {
            changeState();
        });

        $("#deptNameSearchID").multiSelect({
            "selectAll": false,
            "noneSelected": "选择病区",
            "oneOrMoreSelected": "选择病区"
        }, function () {
            changeState();
        });

    });

    function exportExcel() {
        if ($(window.frames["frame1"].document).find("table .style_8 tbody")
                .children().length > 4) {
            var exportDoc = document.getElementById("export");
            var url = $("#birtReport iframe").attr("src");
            if (url) {
                // 根据需求修改想要的文件类型例如docx
                url += "&__format=xlsx";
                exportDoc.action = url;
                exportDoc.submit();
            }
        } else {
            layer.alert('表格无数据，未导出Excel', {'title': '操作提示', icon: 0});
        }

    }
</script>
</head>
<body>
	<div id="filterCondition" class="header">
		<table class="report-search">
			<tr>
				<td>
					<div style="float: left;border: 1px solid #ccc;" class="ui-search-btn ui-btn-bg-green"   onclick="toBirt();" id="birtImg"><i class="am-icon-table"></i><span>表格</span></div>
					<div style="float: left;border: 1px solid #ccc;" class="ui-search-btn"  onclick="toBar();" id="barImg" ><i class="am-icon-bar-chart"></i><span>图表</span></div>
					<div style="float: left;border: 1px solid #ccc;"  class="ui-search-btn" onclick="toPie();" id="pieImg" ><i class="am-icon-pie-chart"></i><span>饼图</span></div>
					<shiro:hasPermission name="PIVAS_BTN_713">
						<div style="float: left;border: 1px solid #ccc;" onclick="exportExcel();"  class="ui-search-btn ui-btn-bg-green" id="exportImg"><i class="am-icon-download"></i><span>下载</span></div>
					</shiro:hasPermission>
				</td>
			</tr>
			<tbody class="ui-tool-right">
			<tr>
				<td><span><spring:message code="MedicSingle.statisticalCycle" />:</span></td>
				<td><input id="flowStatusID" type="hidden" /> <select
					id="dateType" onchange="changeDateType()" style="height: 26px">
						<option value="0">
							<spring:message code="common.month" />
						</option>
						<option value="1">
							<spring:message code="common.day" />
						</option>
				</select></td>

				<td><input type="text"
					style="height: 26px;width:100px;display: none;" id="" name="" /> <input
					type="text" style="height: 26px;width: 100px;" id="statisticsTime"
					name="statisticsTime" class="Wdate" maxlength="32" onclick="time()" /></td>
<%--
				<td>
					<div id="deptNameSearchDivID" style="margin-left: 10px;">
						<select id="deptNameSearchID" name="deptNameSearch[]"
							multiple="multiple" size="5" style="width:100px;">
							<c:forEach items="${inpatientAreaList}" var="inpatientAreaItem"
								varStatus="inpatientAreaStatus">
								<option value="${inpatientAreaItem.deptName}">${inpatientAreaItem.deptName}</option>
							</c:forEach>
						</select>
					</div>
				</td>
--%>
				<td>
					<div id="doctorSearchDivID" style="margin-left: 10px;">
						<select id="doctorSearchID" name="doctorSearch[]" multiple="multiple" size="5" style="width:100px;height: 23px">
							<c:forEach items="${doctorNameList}" var="batchItem" varStatus="batchStatus">
								<c:if test="${not empty batchItem.doctorName}">
									<option value="${batchItem.doctorId}">${batchItem.doctorName}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			</tbody>
		</table>
	</div>

	<%--<div class="header">--%>
		<%--<div style="float: left;border: 1px solid #ccc;">--%>
			<%--<img onclick="toBirt();" id="birtImg"--%>
				<%--src="${pageContext.request.contextPath}/assets/sysImage/statistics/table2.png" />--%>
		<%--</div>--%>
		<%--<div style="float: left;border: 1px solid #ccc;">--%>
			<%--<img onclick="toBar();" id="barImg"--%>
				<%--src="${pageContext.request.contextPath}/assets/sysImage/statistics/bar.png" />--%>
		<%--</div>--%>
		<%--<div style="float: left;border: 1px solid #ccc;">--%>
			<%--<img onclick="toPie();" id="pieImg"--%>
				<%--src="${pageContext.request.contextPath}/assets/sysImage/statistics/pie.png" />--%>
		<%--</div>--%>
		<%----%>
		<%--<shiro:hasPermission name="PIVAS_BTN_713">--%>
		<%--<div style="float: left;border: 1px solid #ccc;" id="exportImg">--%>
			<%--<img onclick="exportExcel();"--%>
				<%--src="${pageContext.request.contextPath}/assets/sysImage/statistics/export.png" />--%>
		<%--</div>--%>
		<%--</shiro:hasPermission>--%>
		<%----%>
	<%--</div>--%>

	<div id="birtReport" style="margin-top:10px;">
		<iframe name="frame1" width="100%" height="450" frameborder="0" src="" style="margin-top: 10px;margin-left: 0px"></iframe>
	</div>


	<div id="barDiv" style="height:400px;" width="500px;"></div>


	<div style="float:left;width:100%;height:400px;" id="pieDiv">
		<div id="main3" style="height:400px;width:45%;display:inline-block;"></div>
		<div id="main4" style="height:400px;width:45%;display:inline-block;"></div>
	</div>


	<script type="text/javascript">
		
	</script>

	<form method="post" id="export"></form>
</body>
</html>