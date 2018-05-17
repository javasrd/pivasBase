<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../common/head.jsp"%>
<link rel="stylesheet" href="${contextPath}/assets/jquery-date-range-picker/daterangepicker.css" />
<link href="${contextPath}/assets/multDropList/multDropList.css" rel="stylesheet" type="text/css"/>
<script src="${contextPath}/assets/jquery-date-range-picker/moment.min.js"></script>
<script src="${contextPath}/assets/jquery-date-range-picker/jquery.daterangepicker.js"></script>
<script src="${contextPath}/assets/js/schedule-custom.js"></script>
<script src="${contextPath}/assets/multDropList/jquery.multiSelect.js"></script>
<script src="${contextPath}/assets/multDropList/multDropList.js" type="text/javascript"></script>

<style type="text/css">
a.multiSelect {
	background: #FFF url(../assets/image/dropdown.blue.png) right center no-repeat;
	border: solid 1px #ABABAB;
	padding-right: 20px;
	position: relative;
	cursor: default;
	text-decoration: none;
	color: #666666;
	display: -moz-inline-stack;
	display: inline-block;
	vertical-align: top;
}

a.multiSelect:link, a.multiSelect:visited, a.multiSelect:hover, a.multiSelect:active {
	color:#666666;
	text-decoration: none;
	height: 26px;
  	/*margin-left: 10px;*/
  	margin-top: -2px;
}

a.multiSelect span
{
	margin: 4px 0px 1px 3px;
	overflow: hidden;
	display: -moz-inline-stack;
	display: inline-block;
	white-space: nowrap;
	color:#666666;
}

a.multiSelect.hover {
	background-image: url(../assets/image/dropdown.blue.hover.png);
}

a.multiSelect.active, 
a.multiSelect.focus {
	border: inset 1px #666666;
}

a.multiSelect.active {
	background-image: url(../assets/image/dropdown.blue.active.png);
}

.multiSelectOptions {
	margin-top: -1px;
	overflow-y: auto;
	overflow-x: hidden;
	border: solid 1px #666666;
	background: #FFF;
}

.multiSelectOptions LABEL {
	padding: 0px 2px;
	display: block;
	white-space: nowrap;
}

.multiSelectOptions LABEL.optGroup
{
	font-weight: bold;
	color:#666666;
}

.multiSelectOptions .optGroupContainer LABEL
{
	padding-left: 10px;
}

.multiSelectOptions.optGroupHasCheckboxes .optGroupContainer LABEL
{
	padding-left: 18px;
}

.multiSelectOptions input{
	vertical-align: middle;
	margin-left: 3px;
 	width: auto;
  	height: 20px;
  	color:#666666;
}

.multiSelectOptions LABEL.checked {
	background-color: #dce5f8;
}

.multiSelectOptions LABEL.selectAll {
	border-bottom: dotted 1px #CCC;
}

.multiSelectOptions LABEL.hover {
	background-color: #3399ff;
	color: white;
} 
</style>
</head>
<body>
<div class="ch-container clearfix">
 <div id="content" style="margin: 20px 10px 0px 20px;"><!-- content -->
    <div class="breadcrumb" style="height:40px;">
   	<label>统计日期：</label><input type="text" id="dateRange" >
	<label style="margin-left:50px;">班次：</label>
	<select id="workSelect"  name="workSelect[]" size="5" style="height: 25px;" multiple="multiple">
		<option value="">--请选择--</option>
		<c:forEach items="${workList}" var="work">
		<option value="${work.workId}">${work.workName}</option>
		</c:forEach>
	</select> 
    </div>
 </div>
</div>
</body>
<script type="text/javascript">
var p;
var bcStr;
$(function() {
	
	//日期对象
	p = {
			startDate: null ,
		    endDate: null ,
		    todayDate:null,
	        setupDatePicker: function() {
	        	
	        	$('#dateRange').dateRangePicker({maxDays:31}).bind('datepicker-closed',function(event,obj)
	        	{
	        		var time = $(this).val();
	        		var timeArr = new Array();
	        		timeArr = time.split("to");
	        		
	        		p.startDate = timeArr[0].trim();
		            p.endDate = timeArr[1].trim();
	        		
	        	});
	        	p.selectNowWeek();
	        	$('#dateRange').data('dateRangePicker').setDateRange(p.startDate,p.endDate);
	            
	        },
	        selectNowWeek:function(){
	        	  
	        	var f = new Date();
	        	var number = f.getDay();
	            if(f.getDay() == 0){
	            	number = 7;
	            }
	            
	            p.startDate = new Date(f.getFullYear(),f.getMonth(),f.getDate() - number + 1),
			    p.endDate = new Date(f.getFullYear(),f.getMonth(),f.getDate() - number + 7);
	            
	            p.startDate = $.datepicker.formatDate("yy-mm-dd", p.startDate);
	            p.endDate = $.datepicker.formatDate("yy-mm-dd", p.endDate);
	        	
	        }
	           
	    };
	
	p.setupDatePicker();
	
	$("#workSelect").multiSelect({ "selectAll": false,"noneSelected": "选择班次","oneOrMoreSelected":"*" },function(){
		bcStr = $("#workSelect").selectedValuesString();
	});
	
});
</script>
</html>