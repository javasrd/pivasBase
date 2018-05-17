<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../../common/head.jsp"%>
<script src="${contextPath}/assets/tinymce/tinymce.min.js"></script>
<script src="${contextPath}/assets/js/schedule-custom.js"></script>
</head>
<body style="background: auto">
<div class="ch-container clearfix">
 <div id="content" style="margin: 20px 10px 0px 10px;"><!-- content -->
    <div class="breadcrumb" style="height:35px;">
   	日期：<input type="text" id="datepicker" readonly="readonly">
    </div>
    <div class="tablePage">
		<table id="tabGrid">
		
		
		</table>
	</div>
    
 </div>


</div>
</body>
<script type="text/javascript">

var todayDate = "${todayDate}";
var minDateStr = "${minDateStr}";

var tabGrid = null;
$(function() {
$("#datepicker").datepicker({
    showOtherMonths: !0,
    selectOtherMonths: !0,
    dateFormat: "yy-mm-dd",
    showButtonPanel :true,
   	dayNames : ['星期天','星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
	dayNamesMin : ['日','一', '二', '三', '四', '五', '六'],
	monthNames  :['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
	nextText : "下个月",
	prevText : "上个月",
	currentText :"今天",
	closeText:"关闭",
	minDate:minDateStr,
	onClose:function(){
		qryList();
	}
	
});

$("#datepicker").datepicker("option", "firstDay", 1);
$("#datepicker").datepicker( "setDate", todayDate);


tabGrid = $('#tabGrid').mmGrid({
	url: '${contextPath}/signIn/signList',
	params:{"workDate" : $("#datepicker").val()},
	method: 'post',
	autoLoad: true,
    height: 397,
    remoteSort:true,
    fullWidthRows: true,
    cols: [
		{ title:'user_id', name:'user_id' ,width:0, align:'center', sortable: false, hidden:true},
		{ title:'姓名', name:'user_name' ,width:100, align:'center', sortable: false},
		{ title:'workId', name:'workId' ,width:0, align:'center', sortable: false, hidden:true},
		{ title:'班次', name:'workName' ,width:100, align:'center', sortable: false},
		{ title:'起止时间', name:'time_interval' ,width:100, align:'center', sortable: false},
		{ title:'时长', name:'totaltime' ,width:100, align:'center', sortable: false},
		{ title:'日期', name:'workDate' ,width:100, align:'center', sortable: false},
		{ title:'签到',name:'isSign', width:100, align:'center', sortable: false,renderer:function(val,row){
			if(val =="-1"){
				var workDate = row.workDate;
				if(workDate == todayDate){
					return '<button class="btn btn-primary" onclick="javascript:checkSign(this)">签到</button>';
				}else{
					return '未签到';
				}
			}else{
				return '已签到';
			}
			
		}}
    ]
});  

});


//历史班次信息查询
function qryList(){
	
    var params = {"workDate" : $("#datepicker").val()};
    tabGrid.load(params);
}

function checkSign(row){
	
	var user_id = $(row).parents("tr").find("td:eq(0)").text().trim();
	var user_name = $(row).parents("tr").find("td:eq(1)").text().trim();
	var workId = $(row).parents("tr").find("td:eq(2)").text().trim();
	var workName = $(row).parents("tr").find("td:eq(3)").text().trim();
	var totaltime = $(row).parents("tr").find("td:eq(5)").text().trim();
	var workDate = $(row).parents("tr").find("td:eq(6)").text().trim();
	
	 //参数
    var param = 
    {
   		'user_id': user_id, 
   		'user_name' : user_name,
   		'workId' : workId,
   		'workName' : workName,
   		'totaltime' : totaltime,
   		'workDate' : workDate,
	 };
    
	$.ajaxFPostJson("${contextPath}/signIn/checkSign",param,{
		"success": function(response){
			qryList();
	    	$.alert(response.mess);
		},
		"error": function(errmess){
			$.alert(errmess+"，请联系管理员");
		}
	});
	
}
</script>
</html>