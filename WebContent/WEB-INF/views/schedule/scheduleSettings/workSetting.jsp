<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../../common/head.jsp"%>
<script src="${contextPath}/assets/tinymce/tinymce.min.js"></script>
<script src="${contextPath}/assets/js/schedule-custom.js"></script>
<link href="${contextPath}/assets/common/css/jquery.bigcolorpicker.css" rel="stylesheet" />
<script src="${contextPath}/assets/js/jquery.bigcolorpicker.js" type="text/javascript"></script>


</head>
<style>
.toolbar-btn{
	float: right;
}

.page-header {
	padding-bottom: 0; 
	margin-top:15px;
    margin-bottom:10px; 
	border-color: #E0E4E8;
	-moz-box-shadow:0 1px 0px rgba(255, 255, 255, 1);
	-webkit-box-shadow: 0 1px 0px rgba(255, 255, 255, 1);
	box-shadow: 0 1px 0px rgba(255, 255, 255, 1);
}

.clearfix{*zoom:1}
.clearfix:before,.clearfix:after{display:table;line-height:0;content:""}
.clearfix:after{clear:both;}

.noneEditable span
{
	filter:alpha(opacity=40); 
	-moz-opacity:0.4; 
	opacity:0.4;
	cursor:default;
	
}

.div_color {
    border: 1px solid #cccccc;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    height: 20px;
    width: 65px;

}
</style>

<script type="text/javascript">

//全局变量保存班次信息
var workData;

//总时间
var totalTimes = 0;

//列表显示的grid
var tabGrid = null ;

//历史数据显示的grid
var oldTabGrid = null;

//获取json文件中的数据 （时间段json数据）
var dataroot = "${pageContext.request.contextPath}/assets/js/times.json";

$(function() {
    
    $(document).ready(function(){
        
        /* $("input").bind("copy cut paste",function(e){
            return false;
        }); */
        
        //页面初始化时需操作
	    $("#changeWork").prop("checked", false);
		$('#addBtn').attr('disabled', true);
		
		//列表数据初始化
    	tabGrid = $('#tabGrid').mmGrid({
        	url: '${contextPath}/workSetting/getWorkList',
        	params: {},
        	method: 'post',
        	autoLoad: true,
            height: 'auto', /*10行 如果有边框高度为407，如果去掉边框，高度为397*/
            //sortName: 'postName',//默认排序的字段名
            //sortStatus: 'asc',
            remoteSort:true,
//            multiSelect: true,
//            checkCol: true,
            fullWidthRows: true,
            plugins: [$('#tabGrid').next().mmPaginator({})],
            cols: [
				{ title:'班次ID', name:'workId' ,width:100, align:'center', sortable: false, type: 'number', "hidden":true},
				{ title:'是否有效', name:'workStatus' ,width:100, align:'center', sortable: false, type: 'checkbox', renderer: function(val,row){
				    
				    //显示字符串
				    var status = '';
				        if (val == '1') {
						    //勾选
						    status = '<input type="checkbox" class="status"  title="" checked="checked" name="status" value="'+ row.workId +'"></input>';
						} else if (val == '0') {
						    //非勾选
						    status = '<input type="checkbox" class="status"  title="" name="status" value="'+ row.workId +'"></input>';
						}
				        status = status + '<input class="workType" name="workType" value="'+ row.workType +'" style="display: none">';
				        return status;
					}
				},
				{ title:'配色', name:'workColor' ,width:100, align:'center', sortable: false, renderer: function(val,row){
				    	return '<div class="input_color" name="inputColor" style="display: none;"><input class="plan_background_color" value="'+ val +'" style="display: none"> '+
				    	       '<a href="#" name="color" maxlength="32" style="text-align:center; margin:0 auto; display:block; border: 1px solid rgb(204, 204, 204); '+
				    	       'background-color: ' + val + '; width: 65px; height: 20px; border-radius: 5px;" title="点击配色框，可选择排班配色" /><input class="" name="workId" value="'+ row.workId +'" style="display: none"> </div>' + 
				    	       '<div class="div_color" style="display: block; background-color: ' + val + '; margin : 0 auto;"></div>';
					}
				},
				{ title:'名称', name:'workName' ,width:100, align:'center', sortable: false, renderer: function(val,row){
					return val.replace(/\</g,"&lt;");
				}},
				{ title:'类型', name:'workType' ,width:100, align:'center', sortable: false, renderer: function(val,row) {
					    if (val == '0') {
					        return '工作';
					    } else if (val == '1') {
					        return '其他';
					    } else if (val == '2') {
					        return '正常休息';;
					    } else if (val == '3') {
					        return '非工作';;
					    }
					}
				},
				{ title:'总时间(小时)', name:'totalTime' ,width:100, align:'center', sortable: false},
				{ title:'起止时间', name:'timeInterval' ,width:200, align:'center', sortable: false},
				{ title:'操作', width:100, align:'center', sortable: false, renderer: function(val,row){
					return '<span class="noneEditable glyphicon glyphicon-zoom-in" aria-hidden="true" style="cursor: pointer;" title="查看班次历史数据" onclick="showOldWork('+ row.workId +')"></span>&nbsp;' + 
						   '<span class="noneEditable glyphicon glyphicon-edit" aria-hidden="true" title="修改班次"></span>&nbsp;' +
						   '<span class="noneEditable glyphicon glyphicon-trash" aria-hidden="true" title="删除班次"></span>' + 
						   '<input class="hiddenWorkId" value="'+ row.workId +'" style="display: none">' + 
						   '<input class="hiddenWorkType" value="'+ row.workType +'" style="display: none">';
					}
				}
            ]
        });
		
    	//历史信息显示
    	oldTabGrid = $('#oldTabGrid').mmGrid({
        	url: '${contextPath}/workSetting/getOldWorkInfo',
        	params: {},
        	method: 'post',
        	autoLoad: false,
            height: 397,
            remoteSort:true,
            multiSelect: false,
            checkCol: false,
            fullWidthRows: true,
            plugins: [$('#oldTabGrid').next().mmPaginator({})],
            cols: [
    			{ title:'班次配色', name:'workColor' ,width:100, align:'center', sortable: false, renderer: function(val,row) {
		    	    return '<div class="div_color" style="display: block; background-color: ' + val + '; margin:0 auto;"></div>';
    				}
    			},
    			{ title:'班次名称', name:'workName' ,width:100, align:'center', sortable: false, renderer: function(val,row){
					return val.replace(/\</g,"&lt;");
    				}
				},
    			{ title:'班次类型', name:'workType' ,width:100, align:'center', sortable: false, renderer: function(val,row){
    			    if (val == '0') {
    			        return '工作';
    			    } else if (val == '1') {
    			        return '其他';
    			    } else if (val == '2') {
    			        return '正常休息';
    			    } else if (val == '3') {
    			        return '非工作';
    			    }
    				}
    			},
    			{ title:'总时间(小时)', name:'totalTime' ,width:100, align:'center', sortable: false},
    			{ title:'起止时间', name:'timeInterval' ,width:300, align:'center', sortable: false}
            ]
        });
    	
    	
    	//列表加载成功后执行代码
    	tabGrid.on("loadSuccess", function() {
    	    
    	    //根据编辑按钮的值判断显示
    	    var value = $('#startBtn')[0].value;
    	    
    	    if (value == "true") {
    	        
    	        //可以编辑功能
    			//全选checkbox
    			$("#tabGrid tr > td .mmg-check").removeClass('disabled');
    			$("#tabGrid tr > td .mmg-check").attr('disabled', false);
    			
    			//选择班次checkbox
    			$(".status").attr('disabled', false);
    			$(".status").attr('style', 'cursor: pointer;');
    			
    			//循环表格数据 对正常休息的班次不允许选择班次操作
    			$('.status').each(function(i) {
    			    //获得班次类型
    		        var $tr = $(this).next('.workType');
    		        //为2，正常休息
    		        if ("2" == $tr[0].value) {
    		    		$(this).attr('disabled', true);
    		    		$(this).removeAttr('style');
    		        }
    			});
    			
    			//选择班次change事件
    			$("table").undelegate( "change" );
    			$("table").delegate("input[name='status']", "change", function() {
    			    changeStatus($(this))
    			});
    			
    			//颜色选择器
    			$(".input_color").css('display','block');
    			$(".div_color").css('display','none');
    			
    			//颜色选择器
    			$("a[name='color']").bigColorpicker(function(el,color) { 
    		        $(el).css("background-color", color);
    		        $(el).val(color)
    		        v_color = color;
    		        c_cobj = $(el).next()[0];
    		    });
    			
    			//获取颜色选择器的点击事件
    			$("#bigLayout").unbind( "click" );
    			$("#bigLayout").bind("click", function(){
    		        changeColor(c_cobj, v_color);
    			});
    			
    			//操作按钮添加样式
    			//$(".glyphicon-zoom-in").attr('style', 'cursor: pointer;');
    			$(".glyphicon-edit").attr('style', 'cursor: pointer;');
    			$(".glyphicon-trash").attr('style', 'cursor: pointer;');
    	        
    	    } else {
    	        //不可以编辑
    	        
    	        //全选checkbox
				$("#tabGrid tr > td .mmg-check").addClass('disabled');
				$("#tabGrid tr > td .mmg-check").attr('disabled', true);
				
    	        //选择班次checkbox
        		$(".status").attr('disabled', true);
        		$(".status").removeAttr('style');
        		
    	    }
    	});
	});
    
    
    /** 对时间段进行初始化数据 */
    $.getJSON(dataroot, function(data) {
        
        $.each(data, function(i, item) { 
            
            //班次时间段1初始时值
            //对开始时间设置默认值
            if (8 == item.value) {
                $('#StartHour1').append('<option selected="selected" value="'+ item.value +'">'+ item.name +'</option>');
            } else {
                $('#StartHour1').append('<option value="'+ item.value +'">'+ item.name +'</option>');
            }
            
            //对结束时间设置默认值
            if (16 == item.value) {
                $('#EndHour1').append('<option selected="selected" value="'+ item.value +'">'+ item.name +'</option>');
            } else {
                $('#EndHour1').append('<option value="'+ item.value +'">'+ item.name +'</option>');
            }
            
            //班次时间段2
            $('#StartHour2').append('<option value="'+ item.value +'">'+ item.name +'</option>');
            $('#EndHour2').append('<option value="'+ item.value +'">'+ item.name +'</option>');
            
        });
    });
    
    
	//勾选隐藏非选班次checkbox事件
	$("#changeWork").change(function(page) { 
	    
		var flag = $("#changeWork").is(":checked");
		
		if (true == flag) {
		    //勾选 隐藏非选班次
			var params = {workstatus: '1' };
			if(page)params['page'] = page;
			//tabGrid.load(params);
			tabGrid.loadInit(params);
			
		} else {
		    //未勾选 显示全部班次
			var params = {"workstatus": null };
			if(page)params['page'] = page;
			//tabGrid.load(params);
			tabGrid.loadInit(params);
		}
	});
	
	/*** 新增页面操作 ***/
	
	//新增页面的勾选两头班checkbox事件
	$("#splitWork").change(function(e) { 
	    
        //获得班次时间段1的起止时间
        var newStartValue1 = $("#StartHour1").val();
        var newEndValue1 = $("#EndHour1").val();
        
        //班次时间段1的时间差
        var diff1 = timeLag(newStartValue1, newEndValue1);
	    
	    //获得勾选两头班的checked事件
		var flag = $("#splitWork").is(":checked");
		
	    //选中
    	if (true == flag) {
    	    
    	    //重新将班次时间段2的禁用属性值改为false
    	    $('#StartHour2').attr('disabled', false);
    	    $('#EndHour2').attr('disabled', false);
    	    
        	//获得班次时间段1的起止时间
        	var newStartValue2 = $("#StartHour2").val();
        	var newEndValue2 = $("#EndHour2").val();
        	
            var diff2 = timeLag(newStartValue2, newEndValue2);
            
            $("#totalTime").val( diff2 + diff1 );
            
    	} else {
    	    //将checkbox和时间段选择框置灰
    	    $("#splitWork").prop("checked", false);
    	    $('#StartHour2').attr('disabled', true);
    	    $('#EndHour2').attr('disabled', true);
    	    
    	    $("#totalTime").val( diff1 );
            
    	}
	});
	
    /**班次时间段1change事件*/
    //班次时间段1的开始时间的change事件
    $('#StartHour1').change(function(e) {
        
        //获得班次时间段1的起止时间
        var newStartValue1 = $("#StartHour1").val();
        var newEndValue1 = $("#EndHour1").val();
        
        //班次时间段1的时间差
        var diff1 = timeLag(newStartValue1, newEndValue1);
        
        //获得勾选两头班的checked事件
        var flag = $("#splitWork").is(":checked");
        
         //选中
        if (true == flag) {
            
            //获得班次时间段2的起止时间
            var newStartValue2 = $("#StartHour2").val();
            var newEndValue2 = $("#EndHour2").val();
            
            //班次时间段2的时间差
            var diff2 = timeLag(newStartValue2, newEndValue2);
            
            $("#totalTime").val( diff2 + diff1 );
            
        } else {
            
            $("#totalTime").val( diff1 );
        }
        
    });
    
    
    ///班次时间段1的结束时间的change事件
    $('#EndHour1').change(function(e) {
        
    	//获得班次时间段1的起止时间
    	var newStartValue1 = $("#StartHour1").val();
    	var newEndValue1 = $("#EndHour1").val();
    	
        //班次时间段1的时间差
        var diff1 = timeLag(newStartValue1, newEndValue1);
        
        //获得勾选两头班的checked事件
		var flag = $("#splitWork").is(":checked");
        
	    //选中
    	if (true == flag) {
        	
        	//获得班次时间段2的起止时间
        	var newStartValue2 = $("#StartHour2").val();
        	var newEndValue2 = $("#EndHour2").val();
        	
            //班次时间段2的时间差
            var diff2 = timeLag(newStartValue2, newEndValue2);
        	
            $("#totalTime").val( diff2 + diff1 );
            
        } else {
            
            $("#totalTime").val( diff1 );
        }
    });
    
    
    /**班次时间段2change事件*/
    //班次时间段2的开始时间的change事件
    $('#StartHour2').change(function(e) {
        
    	//获得班次时间段2的起止时间
    	var newStartValue2 = $("#StartHour2").val();
    	var newEndValue2 = $("#EndHour2").val();
        
    	//获得班次时间段1的起止时间
    	var newStartValue1 = $("#StartHour1").val();
    	var newEndValue1 = $("#EndHour1").val();
    	
        //班次时间段1的时间差
        var diff1 = timeLag(newStartValue1, newEndValue1);
        
        //班次时间段2的时间差
        var diff2 = timeLag(newStartValue2, newEndValue2);
    	
        $("#totalTime").val( diff2 + diff1 );
        
    });
    
    
    ///班次时间段2的结束时间的change事件
    $('#EndHour2').change(function(e) {
        
    	//获得班次时间段2的起止时间
    	var newStartValue2 = $("#StartHour2").val();
    	var newEndValue2 = $("#EndHour2").val();
        
    	//获得班次时间段1的起止时间
    	var newStartValue1 = $("#StartHour1").val();
    	var newEndValue1 = $("#EndHour1").val();
    	
    	
        //班次时间段1的时间差
        var diff1 = timeLag(newStartValue1, newEndValue1);
        
        //班次时间段2的时间差
        var diff2 = timeLag(newStartValue2, newEndValue2);
        
        
        $("#totalTime").val( diff2 + diff1 );
        
    });
	
	//新增保存
	$("#btSave").click(function(){
	    
	    //定义起止时间字符串
    	var timeInterval = "";
    	
		//验证非空
		var workName = $('#workName').val();
		
		//去除左右空格
		workName = $.trim(workName);
        
    	//获得班次时间段1的起止时间
    	var newStartText1 = $("#StartHour1").find("option:selected").text();
    	var newEndText1 = $("#EndHour1").find("option:selected").text();

	    //获得勾选两头班的checked事件
		var flag = $("#splitWork").is(":checked");
	    
	    var splitWork = "";
	    
	    if (true == flag) {
	        //勾选两头班
	    	//获得班次时间段2的起止时间
	    	var newStartText2 = $("#StartHour2").find("option:selected").text();
	    	var newEndText2 = $("#EndHour2").find("option:selected").text();
	    	
	        timeInterval = Trim(newStartText1) + "-" + Trim(newEndText1) + "," + Trim(newStartText2) + "-" + Trim(newEndText2);
	        
	        splitWork = "1";
	    	
	    } else {
	        
	        timeInterval = Trim(newStartText1) + "-" + Trim(newEndText1);
	        
	        splitWork = "0";
	        
	    }
	    
		//总时间
		var totalTime = $("#totalTime").val();
		
		//班次类型
		var workType = $('input[name="workType"]:checked').val();
		
		//所有的验证
		if (!isNotNull(workName)) {
		    //非空
            layer.alert("请输入班次名称", {'title': '操作提示', icon: 0});
		    return;
		} else if (!checkLength( $('#workName'), 1, 32 )) {
		    //班次中文长度
            layer.alert("请输入1~32位字符", {'title': '操作提示', icon: 0});
		    return;
		} else if (!isNotNull(totalTime)) {
		    //非空
            layer.alert("请输入总时间", {'title': '操作提示', icon: 0});
		    return;
		} else if (false == validate($("#totalTime"))) {
		    //时间正则式
            layer.alert("请输入正整数或正浮点数", {'title': '操作提示', icon: 0});
		    return;
		} else if (48 < totalTime) {
		    //总时间填写数不能超过48小时
            layer.alert("总时间不能超过48小时", {'title': '操作提示', icon: 0});
		    return;
		}
		
		//保存数据
	    var param = {workName : workName, timeInterval : timeInterval, totalTime : totalTime, workType : workType , splitWork : splitWork};
	    
		$.ajaxFPostJson("${contextPath}/workSetting/addWork",param,{
			"success": function(response){
				if(response.code && response.code>0){
		    		$('#addModal').modal('hide');
		    		qryList();
		    	}
		    	$.alert(response.mess);
			},
			"error": function(errmess){
                layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
			}
		});
		
	});
	
	/*** 修改页面操作 ***/
	
	//修改页面中的勾选两头班checkbox事件
	$("#msplitWork").change(function(e) { 
	    
    	//获得班次时间段1的起止时间
    	var newStartValue1 = $("#mStartHour1").val();
    	var newEndValue1 = $("#mEndHour1").val();
    	
        //班次时间段1的时间差
        var diff1 = timeLag(newStartValue1, newEndValue1);
	    
	    //获得勾选两头班的checked事件
		var flag = $("#msplitWork").is(":checked");
		
	    //选中
    	if (true == flag) {
    	    
    	    //重新将班次时间段2的禁用属性值改为false
    	    $('#mStartHour2').attr('disabled', false);
    	    $('#mEndHour2').attr('disabled', false);
    	    
        	//获得班次时间段1的起止时间
        	var newStartValue2 = $("#mStartHour2").val();
        	var newEndValue2 = $("#mEndHour2").val();
        	
            //班次时间段2的时间差
            var diff2 = timeLag(newStartValue2, newEndValue2);
            
            $("#mtotalTime").val( diff2 + diff1 );
            
    	} else {
    	    //将checkbox和时间段选择框置灰
    	    $("#msplitWork").prop("checked", false);
    	    $('#mStartHour2').attr('disabled', true);
    	    $('#mEndHour2').attr('disabled', true);
    	    
    	    $("#mtotalTime").val( diff1 );
            
    	}
	});
	
    /**班次时间段1change事件*/
    //班次时间段1的开始时间的change事件
    $('#mStartHour1').change(function(e) {
        
    	//获得班次时间段1的起止时间
    	var newStartValue1 = $("#mStartHour1").val();
    	var newEndValue1 = $("#mEndHour1").val();
    	
        //班次时间段1的时间差
        var diff1 = timeLag(newStartValue1, newEndValue1);
        
        //获得勾选两头班的checked事件
		var flag = $("#msplitWork").is(":checked");
		
	    //选中
    	if (true == flag) {
        	
        	//获得班次时间段2的起止时间
        	var newStartValue2 = $("#mStartHour2").val();
        	var newEndValue2 = $("#mEndHour2").val();
        	
            //班次时间段2的时间差
            var diff2 = timeLag(newStartValue2, newEndValue2);
            
            $("#mtotalTime").val( diff2 + diff1 );
            
        } else {
            
            $("#mtotalTime").val( diff1 );
        }
        
    });
    
    
    ///班次时间段1的结束时间的change事件
    $('#mEndHour1').change(function(e) {
        
    	//获得班次时间段1的起止时间
    	var newStartValue1 = $("#mStartHour1").val();
    	var newEndValue1 = $("#mEndHour1").val();
    	
        //班次时间段1的时间差
        var diff1 = timeLag(newStartValue1, newEndValue1);
        
        //获得勾选两头班的checked事件
		var flag = $("#msplitWork").is(":checked");
        
	    //选中
    	if (true == flag) {
        	
        	//获得班次时间段2的起止时间
        	var newStartValue2 = $("#mStartHour2").val();
        	var newEndValue2 = $("#mEndHour2").val();
        	
            //班次时间段2的时间差
            var diff2 = timeLag(newStartValue2, newEndValue2);
        	
            $("#mtotalTime").val( diff2 + diff1 );
            
        } else {
            
            $("#mtotalTime").val( diff1 );
        }
    });
    
    
    /**班次时间段2change事件*/
    //班次时间段2的开始时间的change事件
    $('#mStartHour2').change(function(e) {
        
    	//获得班次时间段2的起止时间
    	var newStartValue2 = $("#mStartHour2").val();
    	var newEndValue2 = $("#mEndHour2").val();
        
    	//获得班次时间段1的起止时间
    	var newStartValue1 = $("#mStartHour1").val();
    	var newEndValue1 = $("#mEndHour1").val();
    	
        //班次时间段1的时间差
        var diff1 = timeLag(newStartValue1, newEndValue1);
        
        //班次时间段2的时间差
        var diff2 = timeLag(newStartValue2, newEndValue2);
        
        $("#mtotalTime").val( diff2 + diff1 );
        
    });
    
    
    ///班次时间段2的结束时间的change事件
    $('#mEndHour2').change(function(e) {
        
    	//获得班次时间段2的起止时间
    	var newStartValue2 = $("#mStartHour2").val();
    	var newEndValue2 = $("#mEndHour2").val();
        
    	//获得班次时间段1的起止时间
    	var newStartValue1 = $("#mStartHour1").val();
    	var newEndValue1 = $("#mEndHour1").val();
    	
        //班次时间段1的时间差
        var diff1 = timeLag(newStartValue1, newEndValue1);
        
        //班次时间段2的时间差
        var diff2 = timeLag(newStartValue2, newEndValue2);
        
        $("#mtotalTime").val( diff2 + diff1 );
        
    });
	
	//修改保存
	$("#btUpdateSave").click(function(){
	    
	    //定义起止时间字符串
    	var timeInterval = "";
    	
		//验证非空
		var workName = $('#mworkName').val();
		var workId = $('#mworkId').val();
		
		//去除左右空格
		workName = $.trim(workName);
        
    	//获得班次时间段1的起止时间
    	var newStartText1 = $("#mStartHour1").find("option:selected").text();
    	var newEndText1 = $("#mEndHour1").find("option:selected").text();

	    //获得勾选两头班的checked事件
		var flag = $("#msplitWork").is(":checked");
	    
		var splitWork = "";
	    
	    if (true == flag) {
	        //勾选两头班
	    	//获得班次时间段2的起止时间
	    	var newStartText2 = $("#mStartHour2").find("option:selected").text();
	    	var newEndText2 = $("#mEndHour2").find("option:selected").text();
	    	
	        timeInterval = Trim(newStartText1) + "-" + Trim(newEndText1) + "," + Trim(newStartText2) + "-" + Trim(newEndText2);
	        
	        splitWork = "1";
	    	
	    } else {
	        
	        timeInterval = Trim(newStartText1) + "-" + Trim(newEndText1);
	        
	        splitWork = "0";
	    }
	    
		//总时间
		var totalTime = $("#mtotalTime").val();
		
		//班次类型
		var workType = $('input[name="mworkType"]:checked').val();
		
		//正常休息类型
		var normalType = $('#mtype').val();
		
		//未选择则是正常休息类型
		if ( undefined == workType) {
		    workType = normalType;
		}
		
		//所有的验证
		if (!isNotNull(workName)) {
		    //非空
            layer.alert("请输入班次名称", {'title': '操作提示', icon: 0});
		    return;
		} else if (!checkLength( $('#mworkName'), 1, 32 )) {
		    //班次中文长度
            layer.alert("请输入1~32位字符", {'title': '操作提示', icon: 0});
		    return;
		} else if (!isNotNull(totalTime)) {
		    //非空
            layer.alert("请输入总时间", {'title': '操作提示', icon: 0});
		    return;
		} else if (false == validate($("#mtotalTime"))) {
		    //时间正则式
            layer.alert("请输入正整数或正浮点数", {'title': '操作提示', icon: 0});
		    return;
		} else if (48 < totalTime) {
		    //总时间填写数不能超过48小时
            layer.alert("总时间不能超过48小时", {'title': '操作提示', icon: 0});
		    return;
		}
		
		//保存数据
	    var param = {workId : workId, workName : workName, timeInterval : timeInterval, totalTime : totalTime, workType : workType , splitWork : splitWork};
	    
		$.ajaxFPostJson("${contextPath}/workSetting/editWork",param,{
			"success": function(response){
				if(response.code && response.code>0){
		    		$('#updateModal').modal('hide');
		    		qryList();
		    	}
		    	$.alert(response.mess);
			},
			"error": function(errmess){
                layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
			}
		});
	});
	
	
    //批量删除事件
	$("#btDel").bind('click', function(e){
	    
	    //获得checkbox选择的列
	    var workids = tabGrid.selectedValsByName('workId');
	    
	    // 为空提示
	    if (null != workids && "" != workids) {
	        
	        var flags = "";
		    //获得选择的班次类型
		    var types = tabGrid.selectedValsByName('workType');
		    //循环类型
		    for (var j = 0 ;j < types.length; j++) {
		        //正常休息
		        if (types[j] == "2") {
		            flags = flags + "false;";
		        } else {
		            flags = flags + "true;";
		        }
		    }
	        
		    //判断flags是否存在false
		    if (flags.indexOf("false") == -1) {
                layer.confirm('是否删除所选记录？', {
                    btn: ['确定','取消'] //按钮
                }, function(index){
                    layer.close(index);
                    var workIds = "";
                    for (var i = 0 ;i < workids.length; i++) {
                        workIds = workIds + workids[i] + ",";
                    }
                    workIds = workIds.substring(0, workIds.length - 1);

                    //参数
                    var param = {workId: workIds};

                    $.ajaxFPostJson("${contextPath}/workSetting/deleteWork", param, {

                        "success": function(response) {
                            if(response.code && response.code > 0) {
                                qryList();
                            }
                            $.alert2(response.mess);
                        } ,
                        "error": function(errmess) {
                            layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
                        }
                    });
                }, function(index){
                    layer.close(index);
                });

		    } else {
		        //存在
                layer.alert("选中的记录中存在正常休息班次，不能删除", {'title': '操作提示', icon: 0});
		    }
	    } else {
            layer.alert("请选择一条记录", {'title': '操作提示', icon: 0});
	    }
	});

});

var v_color = null;
var c_cobj = null;

//编辑按钮变更
function changeSwitch(e) {
    
	if(e.value == "true") {
	    
		$(e).switchClass("btn-danger","btn-primary");
		e.value = "false",
		e.innerText = "开始编辑";
		
		$('#addBtn').attr('disabled', true);
		$('#btDel').attr('disabled', true);
		
		//全选checkbox
		$("#tabGrid tr > td .mmg-check").addClass('disabled');
		$("#tabGrid tr > td .mmg-check").attr('disabled', true);
		
		//选择班次checkbox
		$(".status").attr('disabled', true);
		$(".status").removeAttr('style');
		
		//颜色选择器
		$(".input_color").css('display','none');
		$(".div_color").css('display','block');
		
		//清除事件
		$("table").undelegate( "click" );
		//清除样式
		//$(".glyphicon-zoom-in").removeAttr('style');
		$(".glyphicon-edit").removeAttr('style');
		$(".glyphicon-trash").removeAttr('style');

		
	}else if(e.value == "false"){
	    
		$(e).switchClass("btn-primary","btn-danger");
		e.value = "true",
		e.innerText = "结束编辑";
		
		//按钮
		$('#addBtn').attr('disabled', false);
		$('#btDel').attr('disabled', false);
		
		//全选checkbox
		$("#tabGrid tr > td .mmg-check").removeClass('disabled');
		$("#tabGrid tr > td .mmg-check").attr('disabled', false);
		
		//选择班次checkbox
		$(".status").attr('disabled', false);
		$(".status").attr('style', 'cursor: pointer;');
		
		//循环表格数据 对正常休息的班次不允许选择班次操作
		$('.status').each(function(i) {
		    //获得班次类型
	        var $tr = $(this).next('.workType');
	        //为2，正常休息
	        if ("2" == $tr[0].value) {
	    		$(this).attr('disabled', true);
	    		$(this).removeAttr('style');
	        }
		});
		
		//选择班次change事件
		$("table").delegate("input[name='status']", "click", function() {
		    changeStatus($(this))
		});
		
		//颜色选择器
		$(".input_color").css('display','block');
		$(".div_color").css('display','none');
		
		//颜色选择器
		$("a[name='color']").bigColorpicker(function(el,color) { 
	        $(el).css("background-color", color);
	        $(el).val(color)
	        v_color = color;
	        c_cobj = $(el).next()[0];
	    });
		
		//获取颜色选择器的点击事件
		$("#bigLayout").click(function(e){
	        changeColor(c_cobj, v_color);
		});
		

		//操作按钮添加样式
		$(".glyphicon-edit").attr('style', 'cursor: pointer;');
		$(".glyphicon-trash").attr('style', 'cursor: pointer;');
		
		//2、修改
		$("table").delegate(".glyphicon-edit", "click", function() {
		    
		    //获得选中的班次Id
		    var objInput = $(this).siblings(".hiddenWorkId")[0];
		    if (null != objInput) {
		        var workId = objInput.value;

                layer.confirm('修改班次后，需要对已使用该班次的周重新排班，请确认是否修改？', {
                    btn: ['确定','取消'] //按钮
                }, function(index){
                    layer.close(index);
                    edit(workId);
                }, function(index){
                    layer.close(index);
                });


		    }
		});
		//3、删除
		$("table").delegate(".glyphicon-trash", "click", function() {
		    
		    //获得选中的班次Id
		    var objInput = $(this).siblings(".hiddenWorkId")[0];
		    var objInput1 = $(this).siblings('.hiddenWorkType')[0];
		    if (null != objInput && null != objInput1) {
		        var workId = objInput.value;
		        var workType = objInput1.value;
		        deletePost(workId, workType);
		    }
		});
	}
}



//查询
function qryList(page) {
	
	var params = {"qryStr":$("#qryStr").val()};
	if(page)params['page'] = page;
	//tabGrid.load(params);
	tabGrid.loadInit(params);

}

var v_workId = null;
//查询该班次中历史信息
function showOldWork(workId) {
    
    //弹出窗口显示历史数据
    v_workId = workId;
    qryOldList();
    
    $('#oldModal').modal();
    
}

//历史班次信息查询
function qryOldList(page){
    
    var params = {"workId" : v_workId};
    if(page)params['page'] = page;
    
    oldTabGrid.load(params);
}

//选择班次change事件
function changeStatus(e) {
    
    //获得选择的班次信息
//    var workid = tabGrid.selectedValsByName('workId');
    var workid = $(e)[0].value;
    var workstatus;
    
    //判断checkbox是否勾选
    var flag = $(e).is(":checked");
    if (true == flag) {
        workstatus = '1';
    } else {
        workstatus = '0';
    }
    
    //参数
    var param = {workId: workid, workStatus : workstatus};
    
    //更改选择班次的工作状态
    $.ajaxFPostJson("${contextPath}/workSetting/changeWorkStatus", param, {
        
        "success": function(response) {
            if(response.code && response.code > 0) {
                qryList();
            }
            //$.alert(response.mess);
        } ,
        "error": function(errmess) {
            layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
        }
    });
}


//选取颜色
function changeColor(obj, color) {

    //获得选择的班次信息
//    var workid = tabGrid.selectedValsByName('workId');
    var workid = obj.value;
    
    //截取#号后的字符串
    //var workcolor = color.substring(1, color.length);
    
    var workcolor = color;
    
    //参数
    var param = {workId: workid, workColor : workcolor};
    
    //更改选择班次的颜色
    $.ajaxFPostJson("${contextPath}/workSetting/changeWorkColor", param, {
        
        "success": function(response) {
            if(response.code && response.code > 0) {
                qryList();
            }
            //$.alert(response.mess);
        } ,
        "error": function(errmess) {
            layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
        }
    });
    
    //
    $("#bigpicker").hide();
}


//添加排班按钮事件
function addShift() {
    
    //清空班次名称
    $('#workName').val("");
    $("#splitWork")
    $('#StartHour2').attr('disabled', true);
    $('#EndHour2').attr('disabled', true);
    $("#totalTime").removeClass( "ui-state-error" );
    $("#work").prop("checked", true);
    $("#splitWork").prop("checked", false);
    
    //对select清空
    $("#StartHour1").empty();
    $("#EndHour1").empty();
    $("#StartHour2").empty();
    $("#EndHour2").empty();
    
    /** 对时间段进行初始化数据 */
    $.getJSON(dataroot, function(data){
        
        $.each(data, function(i, item) { 
            
            //班次时间段1初始时值
            //对开始时间设置默认值
            if (8 == item.value) {
                $('#StartHour1').append('<option selected="selected" value="'+ item.value +'">'+ item.name +'</option>');
            } else {
                $('#StartHour1').append('<option value="'+ item.value +'">'+ item.name +'</option>');
            }
            
            //对结束时间设置默认值
            if (16 == item.value) {
                $('#EndHour1').append('<option selected="selected" value="'+ item.value +'">'+ item.name +'</option>');
            } else {
                $('#EndHour1').append('<option value="'+ item.value +'">'+ item.name +'</option>');
            }
            
            //班次时间段2
            $('#StartHour2').append('<option value="'+ item.value +'">'+ item.name +'</option>');
            $('#EndHour2').append('<option value="'+ item.value +'">'+ item.name +'</option>');
            
        });
        
        //获得班次时间段1的起止时间
        var newStartValue1 = $("#StartHour1").val();
        var newEndValue1 = $("#EndHour1").val();
        
        //计算总时间
        $("#totalTime").val(newEndValue1 - newStartValue1);
        
    });
    
    $("#addModal form input").valueType();
    //新增页面打开
    $('#addModal').modal();
}


//获得单个班次信息
function edit(workId) {
    
    //参数
    var param = {workId: workId};
    
    //获取选择岗位的信息
    $.ajaxFPostJson("${contextPath}/workSetting/getWorkById", param, {
        
        "success": function(response) {
            
            if(response.code && response.code>0) {
                
                //返回json数据
                workData = response.data;
                
                var work = response.data;
                
                $('#mworkId').val(work.workId);
                $('#mworkName').val(work.workName);
                $("#mtotalTime").val(work.totalTime);
                $('#mtype').val(work.workType);
                
                //起止时间
                var timeInterval = work.timeInterval;
                
                //两头班值
                var splitWork = work.splitWork;
                
                //判断是否为两头班
                if (splitWork == "1") {
                    
                    //存在两头班勾选，对班次时间段赋值
                    $("#msplitWork").prop("checked", true);
                    
                    var arrTimes = new Array();
                    arrTimes = timeInterval.split(',');
                    
                    //获得班次时间段1起止时间值
                    var arrInterval1 = new Array();
                    arrInterval1 = addTrim(arrTimes[0]).split(',');
                    
                    //获得班次时间段2起止时间值
                    var arrInterval2 = new Array();
                    arrInterval2 = addTrim(arrTimes[1]).split(',');
                    
                    //对select清空
                    $("#mStartHour1").empty();
                    $("#mEndHour1").empty();
                    $("#mStartHour2").empty();
                    $("#mEndHour2").empty();
                    
                    /** 对时间段进行初始化数据 */
                    $.getJSON(dataroot, function(data){
                        
                        $.each(data, function(i, item) { 
                            
                            //班次时间段1设置值
                            //对开始时间设置值
                            if ($.trim(arrInterval1[0]) == item.name) {
                                $('#mStartHour1').append('<option selected="selected" value="'+ item.value +'">'+ item.name +'</option>');
                            } else {
                                $('#mStartHour1').append('<option value="'+ item.value +'">'+ item.name +'</option>');
                            }
                            
                            //对结束时间设置值
                            if ($.trim(arrInterval1[1]) == item.name) {
                                $('#mEndHour1').append('<option selected="selected" value="'+ item.value +'">'+ item.name +'</option>');
                            } else {
                                $('#mEndHour1').append('<option value="'+ item.value +'">'+ item.name +'</option>');
                            }
                            
                            //班次时间段2
                            //开始时间
                            if ($.trim(arrInterval2[0]) == item.name) {
                                $('#mStartHour2').append('<option selected="selected" value="'+ item.value +'">'+ item.name +'</option>');
                            } else {
                                $('#mStartHour2').append('<option value="'+ item.value +'">'+ item.name +'</option>');
                            }
                            
                            //结束时间
                            if ($.trim(arrInterval2[1]) == item.name) {
                                $('#mEndHour2').append('<option selected="selected" value="'+ item.value +'">'+ item.name +'</option>');
                            } else {
                                $('#mEndHour2').append('<option value="'+ item.value +'">'+ item.name +'</option>');
                            }
                        });
                    });
                    
                } else {
                    
                    //不存在，班次时间段2置灰
                    $("#msplitWork").prop("checked", false);
                    $('#mStartHour2').attr('disabled', true);
                    $('#mEndHour2').attr('disabled', true);
                    
                    //对select清空
                    $("#StartHour1").empty();
                    $("#mEndHour1").empty();
                    $("#mStartHour2").empty();
                    $("#mEndHour2").empty();
                    
                    //获得起止时间值
                    var arrIntervals = new Array();
                    arrIntervals = addTrim(timeInterval).split(',');
                    
                    /** 对时间段进行初始化数据 */
                    $.getJSON(dataroot, function(data) { 
                        
                        $.each(data, function(i, item) { 
                            
                            //班次时间段1初始时值
                            //对开始时间设置默认值
                            if ($.trim(arrIntervals[0]) == item.name) {
                                $('#mStartHour1').append('<option selected="selected" value="'+ item.value +'">'+ item.name +'</option>');
                            } else {
                                $('#mStartHour1').append('<option value="'+ item.value +'">'+ item.name +'</option>');
                            }
                            
                            //对结束时间设置默认值
                            if ($.trim(arrIntervals[1]) == item.name) {
                                $('#mEndHour1').append('<option selected="selected" value="'+ item.value +'">'+ item.name +'</option>');
                            } else {
                                $('#mEndHour1').append('<option value="'+ item.value +'">'+ item.name +'</option>');
                            }
                            
                            //班次时间段2
                            $('#mStartHour2').append('<option value="'+ item.value +'">'+ item.name +'</option>');
                            $('#mEndHour2').append('<option value="'+ item.value +'">'+ item.name +'</option>');
                            
                        });
                    });
                }
                
                //班次类型
                if (2 == work.workType) {
                    //正常休息类型，将班次类型置灰
                    $($("input[name='mworkType']")).each(function(){
                        $(this).prop("disabled", true);
                        $(this).prop("checked", false);
                    });
                } else {
                    $($("input[name='mworkType']")).each(function(){
                        $(this).prop("disabled", false);
                        if ( (this.value) == work.workType ) {
                            $(this).prop("checked", true);
                        }
                    });
                }
                
                $("#updateModal form input").valueType();
                
                //修改页面
                $('#updateModal').modal();
            }
        } ,
        "error": function(errmess) {
            layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
        }
    });
}

//单条记录删除
function deletePost(workId, workType) {

    //获得当前选择行的班次类型
    if ("2" == workType) {
        layer.alert("班次类型为正常休息不能删除", {'title': '操作提示', icon: 0});
    } else {
        layer.confirm('是否删除所选记录？', {
            btn: ['确定','取消'] //按钮
        }, function(index){
            layer.close(index);
            //参数
            var param = {workId: workId};

            //获取选择岗位的信息
            $.ajaxFPostJson("${contextPath}/workSetting/deleteWork", param, {

                "success": function(response) {
                    if(response.code && response.code > 0) {
                        qryList();
                    }
                    layer.alert(response.msg, {'title': '操作提示', icon: 0});
                } ,
                "error": function(errmess) {
                    layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
                }
            });
        }, function(index){
            layer.close(index);
        });

    }

}

/**
 * 去除字符串中所有的空格
 */
function Trim(str) {

    var result;
    
    var is_global = "g";

    result = str.replace(/(^\s+)|(\s+$)/g,"");

    if(is_global.toLowerCase()=="g") {

        result = result.replace(/\s/g,"");

    }
    return result;
}

//对时间添加空格
function addTrim(str) {

    var arrIntervals = new Array();
    
    //以"-"分割字符串
    arrIntervals = str.split('-');
    
    var result = "";
    
    for (var i = 0; i < arrIntervals.length; i++) {
        
        var times = new Array();
        //以":"分割字符串
        times = arrIntervals[i].split(':');
        result = result + times[0] + " : " + times[1] + ",";
    }
    
    result = result.substring(0, result.length - 1);
    
    return result;
}

//计算时间差
function timeLag(startValue, endValue) {
    
    var result;
    
    startValue = parseFloat(startValue);
    endValue = parseFloat(endValue);
    
    //起止时间相同
    if (startValue == endValue) {
        
        result = parseFloat(endValue) - parseFloat(startValue);
        
    } else if (0 != startValue || 0 != endValue) {        //起止时间不同

        if (0 == endValue) {            //结束时间为0时，重新对结束时间赋值
            
            if (0 != startValue) {
                
                endValue = 24;
                
                result = parseFloat(endValue) - parseFloat(startValue);
            }
            
        } else if (startValue < endValue) {        //起时间小于止时间
            
            result = parseFloat(endValue) - parseFloat(startValue);
            
        } else if (startValue > endValue) {        //起时间大于止时间
            
            result = ( 24 - parseFloat(startValue) ) + parseFloat(endValue);
            
        }
    }
    
    return result;
}

//将分钟转为小时:分钟
function toHourMinute(minutes) {
    var minute;
    //分钟
    if ((minutes%60) < 10) {
        minute = "0" + (minutes%60);
    }
    return (Math.floor(minutes/60) + ":" + minute );
}

//十六进制颜色值的正则表达式  
var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
//16进制转为RGB
function colorRgb(color){  
    var sColor = color.toLowerCase();
    if(sColor && reg.test(sColor)){
        if(sColor.length === 4){
            var sColorNew = "#";
            for(var i=1; i<4; i+=1){
                sColorNew += sColor.slice(i,i+1).concat(sColor.slice(i,i+1));
            }
            sColor = sColorNew;
        }  
        //处理六位的颜色值  
        var sColorChange = [];
        for(var i=1; i<7; i+=2){
            sColorChange.push(parseInt("0x"+sColor.slice(i,i+2)));
        }
        return "rgb(" + sColorChange.join(",") + ")";
    }else{
        return sColor;
    }
}; 

//正则验证总时间
function validate(objTime) {
    var bValid = true;
    //浮点数
    var floatTime = /^([0-9]+)(.[0-9]+)?$/;
    bValid = !objTime.val() || objTime.val() && checkRegexp(objTime, floatTime);
    if(!bValid) {
        return false;
    }
}

</script>

<body style="background:#F5F5F5">

<div class="ch-container clearfix">
        
    <div id="content" style="margin: 20px 10px 0px 10px;"><!-- content -->
    
    <div class="breadcrumb" style="height:40px;"><!-- breadcrumb -->

   	
   	<!-- <div class="toolbar-btn"> --><!-- toolbar -->
    	<button class="btn btn-group btn-primary" id="startBtn" value="false" onclick="changeSwitch(this)">开始编辑</button>
    	<button class="btn btn-group btn-primary" id="addBtn" value="false" onclick="addShift()" disabled="disabled">添加排班</button>

    <!-- </div> --><!-- toolbar -->
    
    </div><!-- breadcrumb -->
    
	<div id = "helpContainer" style="margin: 0px 0px 20px 0px;"></div>
    
    <div class="tablePageDiv">
		<table id="tabGrid"></table><!-- 列表 -->
		<div class="pageRight"></div><!-- 分页 -->
	</div>
    <input type="hidden" name="workcolor" id="workcolor">
    </div><!--content-->
    
</div><!--container-->

<!-- 新增表单 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" backdrop="static" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h3 class="modal-header-h3" >添加新班次</h3>
            </div>
            <div class="modal-body">
                <form id="formInfo" class="inputF" role="form">
                    <input type="hidden" name="workId">
                    <div class="form-group">
                        <label class="formTit">班次名称</label>
                        <input type="text" class="form-control formInp" name="workName" id="workName" placeholder="班次名称" maxlength="32" />
                        <label class="formDesc corRed">* 最多32个字符</label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">两头班</label>
                        <input type="checkbox" class="exampleInputFile formChk" name="splitWork" id="splitWork" />
                        <label class="formDesc"></label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">班次时间段1</label>
                        <select autocomplete="off" style="width: 125px;" class="form-control" id="StartHour1" name="StartHour1">
                        </select>
                        <span>&nbsp; ~ &nbsp;</span>
                        <select autocomplete="off" style="width: 125px;" class="form-control" id="EndHour1" name="EndHour1">
                        </select>
                        <label class="formDesc"></label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">班次时间段2</label>
                        <select autocomplete="off" style="width: 125px;" class="form-control" title="当前排班非两头班，不能编辑第二时间段" id="StartHour2" name="StartHour2">
                        </select>
                        <span>&nbsp; ~ &nbsp;</span>
                        <select autocomplete="off" style="width: 125px;" class="form-control" title="当前排班非两头班，不能编辑第二时间段" id="EndHour2" name="EndHour2">
                        </select>
                        <label class="formDesc"></label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">总时间(小时)</label>
                        <input type="text" class="form-control formInp" name="totalTime" id="totalTime" placeholder="总时间(小时)" valueType="time" />
                        <label class="formDesc"></label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">班次类型</label>
                        <input type="radio" class="exampleInputFile formChk" name="workType" id="work" value="0" checked="checked">工作</input>
                        <input type="radio" class="exampleInputFile formChk" name="workType" id="other" value="1">其他</input>
                        <input type="radio" class="exampleInputFile formChk" name="workType" id="nowork" value="3">非工作</input>
                        <label class="formDesc"></label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="#" class="btn btn-primary" id="btSave" >确定</a>
                <a href="#" class="btn btn-default" id="btCancel" data-dismiss="modal">取消</a>
            </div>
        </div>
    </div>
</div>

<!-- 修改表单 -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" backdrop="static" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h3 class="modal-header-h3" >修改班次信息</h3>
            </div>
            <div class="modal-body">
                <form id="formInfo" class="inputF" role="form">
                    <input type="hidden" name="mworkId" id="mworkId">
                    <input type="hidden" name="mtype" id="mtype">
                    <div class="form-group">
                        <label class="formTit">班次名称</label>
                        <input type="text" class="form-control formInp" name="mworkName" id="mworkName" placeholder="班次名称" maxlength="32" />
                        <label class="formDesc corRed">* 最多32个字符</label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">两头班</label>
                        <input type="checkbox" class="exampleInputFile formChk" name="msplitWork" id="msplitWork" />
                        <label class="formDesc"></label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">班次时间段1</label>
                        <select autocomplete="off" style="width: 125px;" class="form-control" id="mStartHour1" name="mStartHour1">
                        </select>
                        <span>&nbsp; ~ &nbsp;</span>
                        <select autocomplete="off" style="width: 125px;" class="form-control" id="mEndHour1" name="mEndHour1">
                        </select>
                        <label class="formDesc"></label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">班次时间段2</label>
                        <select autocomplete="off" style="width: 125px;" class="form-control" title="当前排班非两头班，不能编辑第二时间段" id="mStartHour2" name="mStartHour2">
                        </select>
                        <span>&nbsp; ~ &nbsp;</span>
                        <select autocomplete="off" style="width: 125px;" class="form-control" title="当前排班非两头班，不能编辑第二时间段" id="mEndHour2" name="mEndHour2">
                        </select>
                        <label class="formDesc"></label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">总时间(小时)</label>
                        <input type="text" class="form-control formInp" name="mtotalTime" id="mtotalTime" placeholder="总时间(小时)" valueType="time" />
                        <label class="formDesc"></label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">班次类型</label>
                        <input type="radio" class="exampleInputFile formChk" name="mworkType" id="mwork" value="0" checked="checked">工作</input>
                        <input type="radio" class="exampleInputFile formChk" name="mworkType" id="mother" value="1">其他</input>
                        <input type="radio" class="exampleInputFile formChk" name="mworkType" id="mnowork" value="3">非工作</input>
                        <label class="formDesc"></label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="#" class="btn btn-primary" id="btUpdateSave" >确定</a>
                <a href="#" class="btn btn-default" id="btUpdateCancel" data-dismiss="modal">取消</a>
            </div>
        </div>
    </div>
</div>

<!-- 历史数据 -->
<div class="modal fade" id="oldModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" backdrop="static" aria-hidden="true" >
    <div class="modal-dialog" style="width: 700px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h3 class="modal-header-h3" >班次历史信息</h3>
            </div>
            <div class="modal-body">
                <div class="tablePageDiv">
                    <table id="oldTabGrid"></table><!-- 列表 -->
                    <div class="pageRight"></div><!-- 分页 -->
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>