<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../common/head.jsp"%>
<script src="${contextPath}/assets/tinymce/tinymce.min.js"></script>
<script src="${contextPath}/assets/js/schedule-custom.js"></script>
<!-- 多选框 -->
<link rel="stylesheet" href="${contextPath}/assets/common/css/select2.css" />
<script src="${contextPath}/assets/js/select2.js"></script>
<script type="text/javascript" src="${contextPath}/assets/js/select2_locale_zh-CN.js"></script>
</head>

<script type="text/javascript">
//初始化列表
var workTabGrid = null;
var groupTabGrid = null;

    $(document).ready(function(){
        
        $("input").bind("copy cut paste",function(e){
            return false;
        });
        
    	//班次规则
        workTabGrid = $('#workTabGrid').mmGrid({
        	url: "${contextPath}/scheduleRule/getWorkCountInfo",
        	params: {},
        	method: 'post',
        	autoLoad: true,
            height: 'auto',
            remoteSort:true,
            multiSelect: false,
            checkCol: false,
            fullWidthRows: true,
            cols: [
				{ title:'班次ID', name:'workId' , width:100, align:'center', sortable: false, type: 'number', "hidden":true},
				{ title:'班次名称', name:'workName' , width:100, align:'center', sortable: false, renderer :function(val, row){
				    return val.replace(/\</g,"&lt;");
				}},
				{ title:'每日次数', name:'count' , width:100, align:'center',type: 'number', sortable: false, renderer: function(val,row) {
				        return '<input type="text" style="width: 200px;" class="form-control formInp" valueType="time" onblur="saveCount(this);" name="count" id="count" value="'+ val +'"></input>' + 
				               '<span style="display:none; " name="wcount" id="wcount" value="'+ val +'">' + val + '</span>' + 
				               '<span style="display:none; " name="workid" id="workid" value="'+ row.workId +'">' + row.workId + '</span>'
				    }
				}
            ]
        });
        
        //分组规则
        groupTabGrid = $('#groupTabGrid').mmGrid({
        	url: "${contextPath}/scheduleRule/getGroupInfo",
        	params: {},
        	method: 'post',
        	autoLoad: true,   //自动加载
            height: 'auto', 
            //sortName: 'account',//默认排序的字段名
            //sortStatus: 'asc',    //简短状态
            remoteSort: true,     //远程排序
            multiSelect: false,   //多重选择
            checkCol: false,      //检查上校
            fullWidthRows: true,    //全宽行
            cols: [
				{ title:'分组名称', name:'groupName' , width: 30, align:'center', sortable: false, renderer :function(val, row){
					    return val.replace(/\</g,"&lt;");
				    }
				},
				{ title:'指定关联班次', name:'workList' , width:200, align:'center', sortable: false, renderer : function (val, row) {
				    //显示关联的班次，下拉框显示全部的班次
				    
				    var select = '<select name="id_tipo_equipo" id="id_tipo_equipo" class="multiSelect" tabindex="1" multiple style="width:80%;" data-placeholder="请选择班次" >';
				    var str = '';
				    for (var  i = 0 ;i < val.length; i++) {
				        if (1 == val[i].isUse) {
				            //选择
				            str = str + '<option selected value=' + val[i].workId + '>' + val[i].workName.replace(/\</g,"&lt;") + '</option>';
				        } else{
				            str = str + '<option value=' + val[i].workId + '>' + val[i].workName.replace(/\</g,"&lt;") + '</option>';
				        }
				    }
				    select = select + str + '</select>' + '<span style="display:none; " class="hiddenWorkId" name="workid" id="workid" value="'+ row.groupId +'">' + row.groupId + '</span>';
				    return select;
				}}
            ]
        });
        
        $("#workTabGrid table input[name='count']").valueType();
        
        //分组列表加载成功后
        groupTabGrid.on("loadSuccess",function(){
    	    //初始化多选框
    	    selectChosen();
    	});
        
    });
    

//班次规则查询
function qryCountList(page){
    
	var params = {"qryStr":$("#qryStr").val()};
	if(page)params['page'] = page;
	workTabGrid.load(params);
}


/**
 * 保存每日次数
 */
function saveCount(obj) {
    
    //获得input框的输入值
    var count = $(obj).val();
    
    //获得班次Id
    var workid = $(obj).next().next()[0].innerHTML;
    
    //每日次数非空
    if (null != count && "" != count) {
        
        //去除空格
        count = Trim(count);
        
        //正则表达式
        var reg = /^(0|[1-9][0-9]*)$/;
        
        //正则表达式验证和长度验证
        if (true == reg.test(count) && count.length <= 2) {
            
            //参数
            var param = {workId: workid, count : count};
            
            //更改选择班次的工作状态
            $.ajaxFPostJson("${contextPath}/scheduleRule/updateCount", param, {
                
                "success": function(response) {
                    if(response.code && response.code > 0) {
                        qryCountList();
                    }
                } ,
                "error": function(errmess) {
                    $.alert("服务异常["+errmess+"]，请联系管理员");
                }
            });
            
        } else {
            //需还原之前的数据 ?
            $.alert("请输入0~99的正整数");
        }
    } else {
        $.alert("每日次数不能为空");
    }
}

//分组规则查询
function qryGroupList(page){
    
	var params = {"qryStr":$("#qryStr").val()};
	if(page)params['page'] = page;
	groupTabGrid.load(params);
}


//选择select onchange事件
function changeWork (obj) {
    
    //获得分组Id
    var groupid = $(obj).next()[0].innerHTML;
    
    var param = {groupId: groupid};
    
	$.ajaxFPostJson("${contextPath}/scheduleRule/getWorkInfoByGroup",param,{
		"success": function(response){
			if(response.code && response.code>0) {
			    
			    //清除当前select下的option
			    $(obj).empty();
			    
			    var data = response.items;
			    //对option进行刷新
			    for(var i = 0; i < data.length; i++) {
			        //班次Id和班次名称
			        var workId = data[i].workId;
            		var workName = data[i].workName;
            		//判断是否已关联
			        if (data[i].isUse == 1) {
			            $(obj).append("<option selected value='"+workId+"'>"+workName.replace(/\</g,"&lt;")+"</option>"); 
            		} else {
            		    $(obj).append("<option value='"+workId+"'>"+workName.replace(/\</g,"&lt;")+"</option>"); 
            		}
	            }
	    	}
		},
		"error": function(errmess){
			$.alert("服务异常["+errmess+"]，请联系管理员");
		}
	});
}


//修改班次和分组的关系
function saveGroup (obj, value) {
    
     //获得分组Id
    var groupid = $(obj).next()[0].innerHTML;
    
    //如果不选择班次
    var selectedValues = [];
	
	for (var i = 0; i < value.length; i++) {
	    selectedValues.push(value[i].id);
    }
	
	//班次Id
	workdIds = selectedValues.join(",");
	
	//班次未选
	if (null != workdIds) {
	    
	    var param = {groupId: groupid, workIds : workdIds};
	    
	    $.ajaxFPostJson("${contextPath}/scheduleRule/updateGroup",param,{
	    	"success": function(response){
	    		if(response.code && response.code>0){
	        		qryGroupList();
	        	}
	    	},
	    	"error": function(errmess){
	    		$.alert("服务异常["+errmess+"]，请联系管理员");
	    	}
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

</script>

<body style="background:#F5F5F5">
	<div class="ch-container clearfix">
	    <div id="content" style="margin: 20px 10px 0px 10px;"><!-- content -->
	    	<div style="margin-bottom:10px"></div>
	    	<div class="content-wrapper main-content clear-fix">
				<div class="row-fluid">
    				<div class="span6" style="float: left; height: 100%; width: 46%;">
						<h4>班次规则 <span class="tip tooltip_icon" oldtitle="设置工作相关班次每天出现的次数" title=""></span></h4>
        				<div class="tablePageDiv">
							<table id="workTabGrid"></table><!-- 列表 -->
						</div>
                    </div>
                    <div class="span6" style="float: left; height: 100%; width: 50%; padding-left: 10px;">
						<h4>分组规则 <span class="tip tooltip_icon" oldtitle="设置每个分组相关联的班次" title=""></span></h4>
				        <div class="tablePageDiv">
							<table id="groupTabGrid"></table><!-- 列表 -->
						</div>
                     </div>
                </div>
            </div>
	    </div><!--content-->
	</div><!--container-->

<script>
    //多选框 select2
    function selectChosen() {
        
        //初始化多选框
        $(".multiSelect").select2();
        
        //触发多选框的焦点事件
        $(".multiSelect").on("select2-focus", function(e) { 
            //更新班次
            changeWork ($(this));
        });
        
        //多选框change事件
        $(".multiSelect").on("change", function(e) { 
            //保存分组与班次的关联关系
            saveGroup ($(this), $(this).select2("data"));
        });
    }

</script>
</body>
</html>

