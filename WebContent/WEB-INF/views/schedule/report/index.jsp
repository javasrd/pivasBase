<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../common/head.jsp"%>

<link rel="stylesheet" href="${contextPath}/assets/jquery-date-range-picker/daterangepicker.css" />
<script src="${contextPath}/assets/jquery-date-range-picker/moment.min.js"></script>
<script src="${contextPath}/assets/jquery-date-range-picker/jquery.daterangepicker.js"></script>
<script src="${contextPath}/assets/js/schedule-custom.js"></script>
<!-- 图标 -->
<script src="${contextPath}/assets/common/js/echarts.min.js"></script>
<script>
    var minDateStr = "${minDateStr}";
</script>

</head>
<style>
.redrow{
	background-color: #fdf7e1 !important;
}

</style>
<script type="text/javascript">
var reportType = "work";
var resetOption;
var resetChart;
var workOption;
var workChart;
var workGrid;
var resetGrid;

var contextPath = "${contextPath}";
$(function() {
	window.onresize = function () {
		resetChart.resize(); //使第一个图表适应
		workChart.resize();
	}
	
	
	$("#tabs").tabs({
		beforeActivate: function( event, ui ) {
			var tab = event.currentTarget;
			var text = $(tab).text();
			if(text == "休假报表"){
				reportType = "reset";
				resetCharts();
				
			}else if(text == "工作报表"){
				reportType = "work";
				workCharts();
			}
			gridLoad();
		}
	});
	
	workCharts();
	gridLoad();

});
</script>
<body style="background:#F5F5F5">
<div class="ch-container clearfix">
	
	<div id="content" style="margin: 20px 10px 0px 10px;"><!-- content -->
	
	<div class="breadcrumb" style="height:40px;"><!-- breadcrumb -->
	<div class="toolbar-btn"><!-- toolbar -->
	
	<div class="btn-group" data-toggle="buttons" id="view">
   		<label class="btn btn-custom" id="dayViewBtn" title="今日" data-value="day" onclick="changeView(this)">
      		<input type="radio">当日
   		</label>
   		<label class="btn btn-primary active" id="weekViewBtn" title="本周" data-value="week" onclick="changeView(this)">
     		 <input type="radio">本周
   		</label>
   		<label class="btn btn-custom" id="monthViewBtn" title="本月" data-value="month" onclick="changeView(this)">
     		 <input type="radio">本月
   		</label>
   		<label class="btn btn-custom" id="quarterViewBtn" title="本季度" data-value="quarter" onclick="changeView(this)">
     		 <input type="radio">本季度
   		</label>
		
	</div>
	
	<div class="btn-group" data-toggle="buttons" style="margin: 0px 20px 0px 10px;">
	<div class="group" style="float:right; width: 170px;">
			<input type="text"  class="form-control" id="dateRange">
	</div>
	</div>
	
	<button class="btn" id="excelBtn" title="导出" data-toggle="modal" data-target="#excelModal" >
		<span class="glyphicon glyphicon-save" aria-hidden="true"></span>
	</button>
	
	</div><!-- toolbar -->
	</div><!-- breadcrumb -->
	
	<div id="tabs" style="border-radius: 0px;">
  	<ul>
    	<li><a href="#tabs-1">工作报表</a></li>
    	<li><a href="#tabs-2">休假报表</a></li>
  	</ul>
  	<div id="tabs-1">
  			<div style="border:1px solid #DDDDDD" >
				<div id="workMain" style="width:100%;height:350px;"></div>
			</div>
			<div class="modal-body" id="workModal">
                    <table id="workGrid"></table><!-- 列表 -->
            </div>
  	</div>
  	<div id="tabs-2">
  			<div style="border:1px solid #DDDDDD" >
				<div id="resetMain" style="width:100%;height:350px;"></div>
			</div>
			<div class="modal-body" id="resetModal">
                    <table id="resetGrid"></table><!-- 列表 -->
            </div>
 	</div>
	
	</div><!-- content -->
    
    <!-- 弹窗 导出-->
    <div class="modal fade" id="excelModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <div class="modal-header-h3" >导出数据</div>
                </div>
                <div class="modal-body center-block" >
                
                <div style="margin-bottom:20px;"></div>
                
                <button class="btn btn-primary" id="excelDay" title="导出当日" onclick="excelExport('day')">导出当日</button>
                
                <button class="btn btn-primary" id="excelWeek" title="导出本周" onclick="excelExport('week')">导出本周</button>
                
                <button class="btn btn-primary" id="excelMonth" title="导出本月" onclick="excelExport('month')">导出本月</button>
                
                <button class="btn btn-primary" id="excelQuarter" title="打印本月" onclick="excelExport('quarter')">导出本季度</button>
                
                <button class="btn btn-primary" id="excelCustom" title="打印本月" onclick="excelExport('custom')">导出自定义日期</button>
                
                </div>
                <div class="modal-footer">
                    <button  class="btn btn-default" id="excelBtCancel"  data-dismiss="modal">关闭</button>
                </div>
                <form id="excelForm" name="excelForm" method="post" action="${contextPath}/stat/excelExport">
    			<input type="hidden" id="excelType" name="type" />  
    			<input type="hidden" id="excelStartDate" name="startDate" />
    			<input type="hidden" id="excelEndDate" name="endDate" />
    			<input type="hidden" id="excelReportType" name="reportType" />
				</form>  
            </div>
        </div>
    </div>
    </div>
</div>
<script src="${contextPath}/assets/js/product/scheduleStat.js"></script>
</body>
</html>