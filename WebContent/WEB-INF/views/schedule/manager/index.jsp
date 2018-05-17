<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../common/head.jsp"%>
<script src="${contextPath}/assets/tinymce/tinymce.min.js"></script>
<script src="${contextPath}/assets/js/schedule-custom.js"></script>
</head>

<style>
.toolbar-btn{
	float: left;
    width:100%;
}

.ui-state-hoverDate{
	border: 1px solid #999999;
    background: #778899 50% repeat-x;
    font-weight: normal;
    color: #212121;
}

table tbody tr.odd {
    background-color: #ffffff;
}

table tbody tr.even {
    background-color: white;
}

table tbody tr.group_row{

	background-color: #eeeeee;
}

button.close {
    padding: 0;
    cursor: pointer;
    background: transparent;
    border: 0;
    -webkit-appearance: none;
}
.close {
    float: right;
    font-size: 20px;
    font-weight: bold;
    line-height: 20px;
    color: #000;
    text-shadow: 0 1px 0 #fff;
    opacity: .2;
    filter: alpha(opacity=20);
}

.btn-custom{
    color: #333;
    background-color: #DDDDDD;
}

#operationBar .float_button {
    min-width: 40px;
    height: 27px;
}

#operationBar{
	border: 1px solid #c4c4c4;
    text-align: left;
    position: absolute;
    background-color: #eeeeee;
    width:500px;
	display:none;
	z-index:99;
}

#operationBar button{
    float: left;
    margin: 4px;
    line-height: 25px!important;
    background-color: #5BB75B;
    background-image: linear-gradient(to bottom, #62C462, #51A351);
    background-repeat: repeat-x;
    border-color: rgba(0, 0, 0, 0) rgba(0, 0, 0, 0) rgba(0, 0, 0, 0);
    color: #FFFFFF;
    text-shadow: 0 0 0 rgba(0, 0, 0, 0.25);
    border-radius: 3px;
    border-style: solid;
    border-width: 1px;
    box-shadow: 0 0 0 rgba(255, 255, 255, 0.2) inset, 0 0px 0px rgba(0, 0, 0, 0.05);
    padding: 0px 3px;
    vertical-align: middle;
    text-align: center;
    cursor: pointer;
    font-size: 14px;
}

#operationBar .paiban_seperator {
    float: left;
    height: 35px;
    line-height: 35px;
    border-left: 1px solid rgb(185, 193, 248);
    margin-bottom: 2px;
}

.topLevel{

	z-index:0;
}

.lastLevel{

	z-index:-999;
}

</style>

<script type="text/javascript">

var contextPath = "${contextPath}";
var minDateStr = "${minDateStr}";

$(function() {
	//班次删除
	$("#scheduleWeek").delegate("table td .paibai_xiao_lists button.close","click",function(e){
		
		
		var day = $(this).closest("div.paibai_xiao_lists");
		var work = $(this).closest("div.schedualitem");
		var workType = $(work).attr("data-worktype");
		var workTime = $(work).attr("data-worktime");
		
		var td = $(this).closest("td.paiban_item");
		
		if(workType=='0'||workType=='1'){
			
			var time = $(day).attr("data-daytime");
			time = parseFloat(time) - parseFloat(workTime);
			time = parseFloat(parseFloat(time).toFixed(2));
			$(day).attr("data-daytime",time);
			
		}	
			
		$(this).closest("[data-isactive='True']").remove();
		//重新计算欠休和累计欠休
		recount(td);
	});
	
	$("#scheduleWeek table input.hisinput").valueType();

    //富文本编辑器
    tinymce.init({
        selector: '#textareaContext',
        height:200,
        toolbar: 'mybutton',
        menubar: false,
        forced_root_block:'',
        plugins: 'paste',
        paste_auto_cleanup_on_paste : true,
        paste_remove_styles: true,
        paste_remove_styles_if_webkit: true,
        paste_strip_class_attributes: true,
        resize: false,
        maxlength:20,
        init_instance_callback : function(editor) {

            var remark = "${fn:escapeXml(remark)}";
            remark = htmlDecode(remark);

            //填充备注
            editor.setContent(remark);

        },
        setup: function (editor) {
            editor.addButton('mybutton', {
                text: '保存',
                icon: false,
                onclick: function (editor) {
                    //获取备注内容
                    var remark = tinyMCE.activeEditor.getContent();

                    var len = getLength(remark);
                    if(len > 2000){

                        $.alert("文本内容超过2000个字符");
                        return ;
                    }

                    var params = {
                        "startDate":p.startDate,
                        "endDate":p.endDate,
                        "remark":remark
                    };

                    $.ajaxFPostJson(contextPath + "/scheduleMgr/saveRemark",params,{
                        "success": function(response){
                            layer.msg('备注保存成功');
                        }
                    });

                }
            });
        }

    });
});

</script>

<body style="background:#F5F5F5">

<div class="ch-container clearfix">
        
    <div id="content" style="margin: 20px 10px 0px 10px;"><!-- content -->
    
    <div class="breadcrumb" style="height:40px; padding-left: 0;"><!-- breadcrumb -->
   	
   	<div class="toolbar-btn"><!-- toolbar -->


    <div class="btn-group" data-toggle="buttons" style="float:left;margin:0">
        <label class="btn btn-primary active" id="weekViewBtn" title="周视图" data-value="week" onclick="changeView(this)"style="width: 100px;">
            <input type="radio">周视图
        </label>
        <label class="btn btn-custom" id="dayViewBtn" title="日视图" data-value="day" onclick="changeView(this)">
            <input type="radio">日视图
        </label>
        <div style="margin:auto;float:left;">
            <input type="text" id="datepicker" style="display:none">
            <div class="shangyizhou" id="lastWeekBtn" title="上一周" onclick="lastWeekDataFn()"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span></div>
            <div id="checkDateBtn" style="margin:0 5px;margin-top: 5px;padding:0 10px;float:left;font-weight:bold;background:#ffffff;color:#4054b3;cursor: pointer;border-radius:3px;">
                <div id="timeText"></div>
            </div>
            <!--<button class="btn" title="自选日期" id="checkDateBtn"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span></button>-->
            <div class="shangyizhou" id="nextWeekBtn" title="下一周" onclick="nextWeekDataFn()"><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></div>
            <button class="btn btn-group btn-primary" style="float:left; margin-left: 10px;" id="startBtn" value="false" onclick="changeSwitch(this)" >开始排班</button>
            <div id="autoBtn" class="paibanxianshi" style="display:none; margin-left: 10px;"><a  onclick="autoFn(this)">自动排班</a></div>
        </div>
    </div>
        <!--

        <div id="oneDayBtn" class="paibanxianshi" onclick="oneDayFn(this)" data-value="false"><a class="paibanxianshi_false" aria-hidden="true">设置一人单班</a></div>
        <div id="showPostBtn" class="paibanxianshi" onclick="showPostFn(this)" data-value="false"><a class="paibanxianshi_false" aria-hidden="true">显示员工岗位</a></div>
        <div id="showLastWeekBtn" class="paibanxianshi" onclick="showLastWeekFn(this)" data-value="false" ><a class="paibanxianshi_false" aria-hidden="true">显示上周排班</a> </div>
        -->

        <!--<div class="btn-group" >
           <!--<button class="btn dropdown-toggle" id="moreBtn" title="更多" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                     更多
                   <span class="caret"></span>
                 </button>
                 <ul class="dropdown-menu dropdown-menu-right" raria-labelledby="dropdownMenu1">
                     <li id="autoBtn" style="display:none"><a  href="#" onclick="autoFn(this)">自动排班</a></li>

                     <li class="divider hide" id="hengXian"></li>

                     <li id="showPostBtn"><a href="#" onclick="showPostFn(this)" data-value="false"><span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span> 显示员工岗位</a></li>

                     <li id="showLastWeekBtn"><a  href="#" onclick="showLastWeekFn(this)" data-value="false" ><span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span> 显示上周排班</a></li>

                    <li id="oneDayBtn"><a href="#" onclick="oneDayFn(this)" data-value="false"><span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span> 设置一人单班</a></li>
               </ul>
           </div>-->

    <div id="moreBtn" style="float:right;width:105px;line-height:30px;">
        <button class="btn" id="printBtn" title="打印预览" data-toggle="modal" data-target="#printModal" >打印<!--<span class="glyphicon glyphicon-print" aria-hidden="true"></span>--></button>
        <button class="btn" id="excelBtn" title="导出" data-toggle="modal" data-target="#excelModal" >导出<!--<span class="glyphicon glyphicon-save" aria-hidden="true"></span>--></button>

    </div>
    </div><!-- toolbar -->
    
    </div><!-- breadcrumb -->
   
    </div><!--content-->


    <div id = "helpContainer" style="margin:10px 10px;height:30px;line-height:30px;">

        <div style="float:right">
            <span class="label label-success">本周欠休</span> = 本周上班时间 - 40 小时 &nbsp;&nbsp;<span class="label label-success">累计欠休</span> = 上周累计欠休 + 本周欠休
        </div>
    </div>

    <div id="tablePanel">
    <div id="scheduleWeek" style="margin:0px 10px 0px 10px;">
    <!-- table -->
    <jsp:include page="../manager/gridView.jsp"/>
    
	</div>
	<div id="dayView" style="margin: 20px 10px 0px 10px;display:none" >
    <!-- table -->
    <jsp:include page="../manager/dayView.jsp"/>
    
	</div>
	
	<div id="operationBar" class="operationBar" >
	<c:forEach var="work" items="${workLiist}">
	<button class="float_button" data-type="${work.workType}" data-colour="${work.workColour}" 
							     data-workid="${work.workId}" data-worktime="${work.totalTime}">${fn:escapeXml(work.workName)}</button>
	</c:forEach>
	<button class="float_button" data-type="del"><span class="glyphicon glyphicon-trash" aria-hidden="true"></button>
	</div>
	</div>


	<div id="commentContainer" style="margin: 20px 10px 0px 10px;" data-commentid="" data-commentstring="">
        <div>
            <strong>【备注】</strong>
        </div>
        <textarea id="textareaContext"></textarea>
        
    </div>
    

    
</div><!--container-->

<!-- 弹窗 打印-->
    <div class="modal fade" id="printModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <div class="modal-header-h3" >打印</div>
                </div>
                <div class="modal-body center-block">
                
                <div style="margin-bottom:20px;"></div>
                
                <button class="btn btn-primary" id="printDay" title="打印当日" onclick="printHtml('day')">打印当日</button>
                
                <button class="btn btn-primary" id="printWeek" title="打印本周" onclick="printHtml('week')">打印本周</button>
                
                <button class="btn btn-primary" id="printMonth" title="打印本月" onclick="printHtml('month')">打印本月</button>
                
                </div>
                <div class="modal-footer">
                    <button  class="btn btn-default"id="printBtCancel"  data-dismiss="modal">关闭</button>
                </div>
                <form id="printForm" name="printForm"  target="_blank" method="get" action="${contextPath}/scheduleMgr/printSchedule">
    			<input type="hidden" id="printType" name="type" />  
    			<input type="hidden" id="printDate" name="date" />  
				</form>
                
            </div>
        </div>
    </div>
	
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
                
                </div>
                <div class="modal-footer">
                    <button  class="btn btn-default" id="excelBtCancel"  data-dismiss="modal">关闭</button>
                </div>
                <form id="excelForm" name="excelForm" method="post" action="${contextPath}/scheduleMgr/excelExport">
    			<input type="hidden" id="excelType" name="type" />  
    			<input type="hidden" id="excelDate" name="date" />  
				</form>  
            </div>
        </div>
    </div>
    <div id="dialog-confirm" title="自动排班" style="z-index:999;display:none">
 	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>自动排班结果会覆盖当前排班，是否继续？</p>
	</div>

    <script src="${contextPath}/assets/js/product/schedule.js"></script>
</body>
</html>