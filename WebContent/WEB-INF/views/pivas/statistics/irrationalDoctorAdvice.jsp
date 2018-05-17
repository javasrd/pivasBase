<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	margin: 10px 0px -10px 0px;
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
<script src="${pageContext.request.contextPath}/assets/jquery/ajaxfileupload.js" type="text/javascript" ></script>
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
		$("#batchSearchDivID").show();
		$("#deptNameSearchDivID").show();
		$("#flowStatusID").val("0");

		var userLanguage = '${sessionScope.language}' === 'en' ? 'en_US' : 'zh';
		var statisticsTime = $('#statisticsTime').val();
		var batchSearchIDStr = $("#batchSearchID").selectedValuesString();
		var deptNameSearchstr = $("#deptNameSearchID").selectedValuesString();

		var url = contextPath
				+ "/frameset?__report=report/doctorAdvice.rptdesign&__parameterpage=false&__navigationbar=true&__toolbar=false&__locale="
				+ userLanguage
				+ encodeURI("&title=<spring:message code='pivas_yzTJ.title'/>(")
				+ statisticsTime
				+ ")"
				+ encodeURI("&zbr=<spring:message code='pivas.statistics.tab'/>")
				+ "<spring:escapeBody htmlEscape='true'><shiro:principal property='name'/></spring:escapeBody>";

		url += "&searchTime=";
		if (statisticsTime != null && statisticsTime !== '') {
			url += statisticsTime;
		}
		url += "&searchDoctor=";
		if (batchSearchIDStr != null && batchSearchIDStr !== '') {
			url += batchSearchIDStr;
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
		$("#ydStatisticsType").css('display', 'inline-block');
		$("#ydStatisticsType").css('height', '26');
		$("#ydStatisticsType").parent().find(".ui-combobox").css('display',
				'none');
		//$("#ydStatisticsType").parent().find(".ui-combobox").css("border","1px solid #ccc");
		//("#ydStatisticsType").parent().find(".ui-combobox").re

		clear(myChart2);
		clear(myChart);

		//initBar();
		var ydStatisticsType = $('#ydStatisticsType').val();
		if (ydStatisticsType == 0) {
			$("#batchSearchDivID").show();
			$("#deptNameSearchDivID").hide();
            queryIrrationalDoctorAdviceBar();
		} else {
			$("#batchSearchDivID").hide();
			$("#deptNameSearchDivID").show();
			queryDeptBar();
		}

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
		$("#ydStatisticsType").parent().find(".ui-combobox").css("border", "1px solid #ccc");

		clear(myChart);
		clear(myChart2);
		var ydStatisticsType = $('#ydStatisticsType').val();
		//按批次
		if (ydStatisticsType == 0) {
			$("#batchSearchDivID").show();
			$("#deptNameSearchDivID").hide();
			initPieBatch();
		} else {
			$("#batchSearchDivID").hide();
			$("#deptNameSearchDivID").show();
			initPieDept();
		}
		//initPie();
		//initPieBatch();
		//initPieDept();

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


    function queryIrrationalDoctorAdviceBar() {
        var option = {domId: "barDiv", legendNames: [], xAxisData: [], seriesData: []};

        var statisticsTime = $('#statisticsTime').val();
        var batchSearchIDStr = $("#batchSearchID").selectedValuesString();

        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/statistics/queryDoctorAdviceStatusBarData',
            dataType: 'json',
            cache: false,
            data: [{
                name: 'searchTime',
                value: statisticsTime
            }, {
                name: 'searchDoctorIds',
                value: batchSearchIDStr
            }, {
                name: 'compareResult',
                value: compareTime($('#statisticsTime').val())
            }],
            success: function (data) {
                //alert(data.batchNameList);
                //option.xAxisData.push(data.batchNameList);
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

                                option.legendNames.push(value.statusName);
                                configFeeType.name = value.statusName;
                                configFeeType.data = value.doctorNameCountList;
                                option.seriesData.push(configFeeType);

                            });
                    }
                }
                createBar(option);
            }
        });
    }

	/**
	 *按病区查询药单状态
	 */
    function queryDeptBar() {
        var option = {
            domId: "barDiv",
            legendNames: [],
            xAxisData: [],
            seriesData: []
        };

        var statisticsTime = $('#statisticsTime').val();
        var deptNameSearchstr = $("#deptNameSearchID").selectedValuesString();

        $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/statistics/queryDoctorAdviceDeptStatusBar',
                dataType: 'json',
                cache: false,
                data: [{
                    name: 'searchTime',
                    value: statisticsTime
                }, {
                    name: 'searchDeptNames',
                    value: deptNameSearchstr
                }, {
                    name: 'compareResult',
                    value: compareTime($('#statisticsTime').val())
                }],
                success: function (data) {
                    //alert(data.batchNameList);
                    //option.xAxisData.push(data.batchNameList);
                    if (data.deptNameList != null
                        && data.deptNameList.length > 0) {
                        $.each(data.deptNameList, function (index, value) {
                            option.xAxisData.push(value);
                        });

                        if (data.status2DeptList != null
                            && data.status2DeptList.length > 0) {
                            $
                                .each(
                                    data.status2DeptList,
                                    function (index, value) {
                                        //alert(value.statusKey);
                                        //alert(value.ydBatchCountList);
                                        var configFeeType = {
                                            name: '',
                                            type: 'bar',
                                            stack: ' ',
                                            data: []
                                        };
                                        //var configFreeTypes = $('#configFreeType option[value=' + value.statusKey +']').text();
                                        var f = false;
                                        for (var k in value.dctrDeptCountList) {
                                            if (value.dctrDeptCountList[k] > 0) {
                                                f = true;
                                            }
                                        }
                                        if (f) {
                                            option.legendNames
                                                .push(value.statusName);
                                        }
                                        configFeeType.name = value.statusName;
                                        configFeeType.data = value.dctrDeptCountList;
                                        option.seriesData
                                            .push(configFeeType);

                                    });
                        }
                    }

                    createBar(option);
                }
            });
    }

	function initPieBatch() {
		option = {
			seriesName : ' ',
			data : [ {
				value : 0,
				itemStyle : {
					normal : {
						label : {
							show : false
						},
						labelLine : {
							show : false
						}
					}
				}
			} ],
			domId : 'main3',
			clickCall : clickPieBatch
		};
		var statisticsTime = $('#statisticsTime').val();
		var batchSearchIDStr = $("#batchSearchID").selectedValuesString();

        $.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryDoctorAdvicePieList',
			dataType : 'json',
			cache : false,
			data : [ {
				name : 'searchTime',
				value : statisticsTime
			}, {
				name : 'searchDoctorIds',
				value : batchSearchIDStr
			}, {
				name : 'compareResult',
				value : compareTime($('#statisticsTime').val())
			} ],
			success : function(data) {
				$.each(data, function(index, value) {
					option.data.push(value);
				});
				myChart = createPie(option);
			}
		});

	}

	function clickPieBatch(param) {
		if (myChart2) {
			myChart2.clear();
			myChart2 = null;
		}

		var option = {
			seriesName : '审方错误类型',
			domId : 'main4',
			legendNames : [],
			data : [ {
				value : 0,
				itemStyle : {
					normal : {
						label : {
							show : false
						},
						labelLine : {
							show : false
						}
					}
				}
			}

			],
			radius : '40%'
		};

        $.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryDoctorAdviceStatusPieData',
			dataType : 'json',
			cache : false,
			data : [ {
				name : 'doctorID',
				value : param.data.doctorId
			},
			{name : 'searchTime',value :$('#statisticsTime').val()},
			{
				name : 'compareResult',
				value : compareTime($('#statisticsTime').val())
			} ],
			success : function(data) {
				//option.data = data;
				$.each(data, function(index, value) {
					var item = {
						name : '',
						value : 10
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

	function initPieDept() {
		option = {
			seriesName : ' ',
			data : [ {
				value : 0,
				itemStyle : {
					normal : {
						label : {
							show : false
						},
						labelLine : {
							show : false
						}
					}
				}
			} ],
			domId : 'main3',
			clickCall : clickPieDept
		};
		var statisticsTime = $('#statisticsTime').val();
		var deptNameSearchstr = $("#deptNameSearchID").selectedValuesString();

        $.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryDoctorAdviceDeptPieList',
			dataType : 'json',
			cache : false,
			data : [ {
				name : 'searchTime',
				value : statisticsTime
			}, {
				name : 'searchDeptNames',
				value : deptNameSearchstr
			}, {
				name : 'compareResult',
				value : compareTime($('#statisticsTime').val())
			} ],
			success : function(data) {
				$.each(data, function(index, value) {
					option.data.push(value);
				});

				myChart = createPie(option);
			}
		});

	}

	function clickPieDept(param) {
		// 释放资源重新创建
		if (myChart2) {
			myChart2.clear();
			myChart2 = null;
		}

		var option = {
			seriesName : '审方错误类型',
			domId : 'main4',
			legendNames : [],
			data : [ {
				value : 0,
				itemStyle : {
					normal : {
						label : {
							show : false
						},
						labelLine : {
							show : false
						}
					}
				}
			} ],
			radius : '40%'
		};

		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/statistics/queryDoctorAdviceDeptStatusPieList',
			dataType : 'json',
			cache : false,
			data : [ {
				name : 'deptName',
				value : param.data.name
			},
			{name : 'searchTime',value :$('#statisticsTime').val()},
			{
				name : 'compareResult',
				value : compareTime($('#statisticsTime').val())
			} ],
			success : function(data) {
				$.each(data, function(index, value) {
					var item = {
						name : '',
						value : 10
					};
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
			isShowClear : false,
			dateFmt : dateFmt,
			readOnly : true,
			onpicked : function(dp) {
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
	$(function() {
		english = "zh-cn" == userLanguage ? false : true;

		changeDateType();

		//querySelectData();

		contextPath = '${pageContext.request.contextPath}';
		// 初始化echarts
		initEcharts(contextPath);

		toBirt();

		$("#batchSearchID").multiSelect({
			"selectAll" : false,
			"noneSelected" : "选择医生",
			"oneOrMoreSelected" : "选择医生"
		}, function() {
			changeState();
		});

		$("#deptNameSearchID").multiSelect({
			"selectAll" : false,
			"noneSelected" : "选择病区",
			"oneOrMoreSelected" : "选择病区"
		}, function() {
			changeState();
		});

	});

	function changeCustType(value) {
		var ydStatisticsType = $('#ydStatisticsType').val();
		//按批次
		if (ydStatisticsType == 0) {
			$("#batchSearchDivID").show();
			$("#deptNameSearchDivID").hide();
		} else {
			$("#batchSearchDivID").hide();
			$("#deptNameSearchDivID").show();
		}
		changeState();
	}

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
			message({
				html : "表格无数据，未导出Excel"
			});
		}

	}
	
	
	$(function() {

		$( "#importImg").bind("click",function(){
			$("#importView-div").dialog("open");
		});
		
		$( "#import-submit").bind("click",function(){
			
			var bValid = true;
			bValid = bValid && checkEmpty($( "#file" ));
			if (!bValid ) {
				return ;
			}
			
			$.ajaxFileUpload
            (
                {
                    url: '${pageContext.request.contextPath}/statistics/importDianp', //用于文件上传的服务器端请求地址
                    type: 'post',
                    secureuri: false, //一般设置为false
                    fileElementId: 'file', //文件上传域的ID
                    dataType : 'json', //返回值类型 一般设置为json
                    success: function (data, status)  //服务器成功响应处理函数
                    {
                    	if(data.success){
                    		$("#description").val(data.msg);
		            	}
                    },
                    error: function (data, status, e)//服务器响应失败处理函数
                    {
                    	$("#description").val('');
                    }
                }
            )

		});
		
		$("#importView-div").dialog({
			autoOpen: false,
			height: 350,
			width: 800,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {	
					$("#importView-div").dialog("close");
				},
				"<spring:message code='common.cancel'/>": function() {
					$("#importView-div").dialog("close");
				}
			}
		});
		
		$("#importView-div").dialog({
			autoOpen: false,
			height: 350,
			width: 530,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {	
					$("#importView-div").dialog("close");
				},
				"<spring:message code='common.cancel'/>": function() {
					$("#importView-div").dialog("close");
				}
			}
		});
	});
	
	
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
					<shiro:hasPermission name="PIVAS_BTN_573">
						<div style="float: left;border: 1px solid #ccc;" onclick="exportExcel();"  class="ui-search-btn ui-btn-bg-green" id="exportImg"><i class="am-icon-download"></i><span>下载</span></div>
					</shiro:hasPermission>
					<div style="float: left;border: 1px solid #ccc;" id="importImg" class="ui-search-btn">
						<i class="am-icon-arrow-circle-left"></i><span>导入点评</span>
						<%--<img src="${pageContext.request.contextPath}/assets/sysImage/statistics/importDianp.png" />--%>
					</div>
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


				<div>
					<td>
						<div id="stDimValueDivID" style="width: 130px;margin-left: 10px">
							<sd:select style="width: 120px;height:26px;"
								name="ydStatisticsType" id="ydStatisticsType" required="true"
								onchange="changeCustType(value)"
								categoryName="staticDoctor.Statistics.type"
								tableName="sys_dict"></sd:select>
						</div>
						<div style="display: none">
							<sd:select name="configFreeType" id="configFreeType"
								required="false" categoryName="pivas.configfee.type"
								tableName="sys_dict"></sd:select>
						</div>

					</td>
				</div>

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

				<td>
					<div id="batchSearchDivID" style="margin-left: 10px;">
						<select id="batchSearchID" name="batchSearch[]"
							multiple="multiple" size="5" style="width:100px;height: 23px">
							<c:forEach items="${doctorNameList}" var="batchItem"
								varStatus="batchStatus">
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
		<%--<shiro:hasPermission name="PIVAS_BTN_573">--%>
		<%--<div style="float: left;border: 1px solid #ccc;" id="exportImg">--%>
			<%--<img onclick="exportExcel();"--%>
				<%--src="${pageContext.request.contextPath}/assets/sysImage/statistics/export.png" />--%>
		<%--</div>--%>
		<%--</shiro:hasPermission>--%>
		<%--<div style="float: left;border: 1px solid #ccc;" id="importImg">--%>
			<%--<img src="${pageContext.request.contextPath}/assets/sysImage/statistics/importDianp.png" />--%>
		<%--</div>--%>
	<%--</div>--%>

	<br>

	<div id="birtReport">
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
	
	
	<%-- 导入库存数据弹出框 --%>
		<div id="importView-div" title="不合理医嘱点评导入"
			align="left" style="display: none;">
			<form id="editView-form" action="" method="post">
				<div class="popup">
					<div class="row">
						<div class="column">
							<label class="tit" >不合理医嘱点评</label> <input type="file"
								id="file" name="file"> <a class="button"
								id="import-submit"><spring:message code='common.import' /></a>
						</div>
					</div>

					<div class="row">
						<div class="column" align="left">
							<textarea name="description" id="description" style="margin: 0px; height: 143px; width: 456px;resize:none" readonly="readonly"></textarea>
						</div>
					</div>
				</div>
			</form>
		</div>
</body>
</html>