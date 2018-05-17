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
	float: right;
}


.clearfix{*zoom:1}
.clearfix:before,.clearfix:after{display:table;line-height:0;content:""}
.clearfix:after{clear:both;}
</style>

<script type="text/javascript">
var postData;

var tabGrid = null ;

$(function() {
    
    $(document).ready(function(){
        
    	tabGrid = $('#tabGrid').mmGrid({
        	url: '${contextPath}/position/getPostList',
        	params: {},
        	method: 'post',
        	autoLoad: true,
            height: 397,
            remoteSort:true,
            multiSelect: true,
//            checkCol: true,
            fullWidthRows: true,
            cols: [
				{ title:'岗位ID', name:'postId' ,width:100, align:'center', sortable: false, type: 'number', "hidden":true},
				{ title:'岗位名称', name:'postName' ,width:100, align:'center', sortable: false, renderer : function(val, row){
				    return val.replace(/\</g,"&lt;");
				}},
				{ title:'是否可用', name:'enabled' ,width:100, align:'center', sortable: false, renderer: function(val,row){
				    if (val == '1') {
				        return '<span style="color: green;">是</span>';
				    } else if (val == '0') {
				        return '<span  style="color: red;">否</span>';
				    }
					}
				},
				{ title:'操作', width:100, align:'center', sortable: false, renderer: function(val,row){
					return '<span style=" cursor: pointer;" title="修改" class="glyphicon glyphicon-edit" aria-hidden="true" onclick="javascript:edit(' + row.postId +
					       ')"></span>&nbsp;<span style=" cursor: pointer;" title="删除" class="glyphicon glyphicon-trash" aria-hidden="true" onclick="javascript:deletePost(' + row.postId + ')"></span>';
					}
				}
            ]
        });
        
        //新增点击事件
    	$('#btAdd').click(function (e) {
    	    
    	    //清空岗位名
    	    $('#postName').val("");
    	    $("#enabled").prop("checked", true);
            $('#addModal').modal();
        });
    	

    	//新增保存
    	$("#btSave").click(function(){
    		
    		//获得岗位名的值
    		var postName = $('#postName').val();
    		//去除左右空格
    		postName = $.trim(postName);
    		
    		//表示checkbox的勾选状态
			var flag = $("#enabled").is(":checked");
    		var enabled;
    		if (true == flag) {
    		    enabled = '1'; //可用
    		} else {
    		    enabled = '0'; //不可用
    		}
    		
    		//非空验证通过
    		if (isNotNull(postName)) {
    		    
    		    if (checkLength( $('#postName'), 1, 32 )) {
    		        
        		    var param = {postName : postName, enabled : enabled};
        		    
            		$.ajaxFPostJson("${contextPath}/position/addPost",param,{
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
    		    } else {
                    layer.alert("请输入1~32个字符", {'title': '操作提示', icon: 0});
    		    }
    		    
    		} else {
                layer.alert("请输入岗位名", {'title': '操作提示', icon: 0});
    		}
    	});
    	
    });
    
    
	//修改保存
	$("#btUpdateSave").click(function(){
	    
		//获得岗位信息
		var postName = $('#mpostName').val();
		var postId = $('#mpostId').val();
		
		//去除左右空格
		postName = $.trim(postName);
		
		//表示checkbox的勾选状态
		var flag = $("#menabled").is(":checked");
		var enabled;
		if (true == flag) {
		    enabled = '1'; //可用
		} else {
		    enabled = '0'; //不可用
		}
		
		//非空验证通过
		if (isNotNull(postName)) {
		    
		    //修改判断与全局变量对比
		    if (postName == postData.postName && enabled == postData.enabled) {
		        
		        $('#updateModal').modal('hide');
                layer.alert("未作任何修改", {'title': '操作提示', icon: 0});
		    } else {
		        //验证文本框内容长度
    		    if (checkLength( $('#mpostName'), 1, 32 )) {
    		        
    		        //参数
    			    var param = {postId : postId, postName: postName, enabled: enabled};
    			    
    	    		$.ajaxFPostJson("${contextPath}/position/editPost",param,{
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
    		    } else {
                    layer.alert("请输入1~32个字符", {'title': '操作提示', icon: 0});
    		    }
		    }
		} else {
            layer.alert("请输入岗位名称", {'title': '操作提示', icon: 0});
		}
	});

});

//查询
function qryList(page){
	var params = {"qryStr":$("#qryStr").val()};
	if(page)params['page'] = page;
	tabGrid.load(params);
}

//修改
function edit(postId) {
    
    //参数
    var param = {postId: postId};
    
    //获取选择岗位的信息
    $.ajaxFPostJson("${contextPath}/position/getPostById", param, {
        
        "success": function(response) {
            
            if(response.code && response.code>0) {
                
                //返回json数据
                postData = response.data;
                
                var post = response.data;
                $('#mpostId').val(post.postId);
                $('#mpostName').val(post.postName);
                
                //对checkbox勾选状态
                if (post.enabled == 1) {
                    $("#menabled").prop("checked", true);
                }
                $('#updateModal').modal();
            }
        } ,
        "error": function(errmess) {
            layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
        }
    });
}

//单条记录删除
function deletePost(postId){
    layer.confirm('是否删除所选记录？', {
        btn: ['确定','取消'] //按钮
    }, function(index){
        layer.close(index);
        //参数
        var param = {postId: postId};

        //删除选择岗位的信息
        $.ajaxFPostJson("${contextPath}/position/deletePost", param, {

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
}
</script>

<body style="background:#F5F5F5">

<div class="ch-container clearfix">
        
    <div id="content" style="margin: 20px 10px 0px 10px;"><!-- content -->
    
    <div class="breadcrumb" style="height:40px;"><!-- breadcrumb -->

   	
   	<!-- <div class="toolbar-btn"> --><!-- toolbar -->
	    <button class="btn btn-primary" style="color:#fff" id="btAdd" >新增</button>

    <!-- </div> --><!-- toolbar -->
    
    </div><!-- breadcrumb -->
    
	<div id = "helpContainer" style="margin: 0px 0px 20px 0px;"></div>
    
    <div class="tablePageDiv">
		<table id="tabGrid"></table><!-- 列表 -->
		<div class="pageRight"></div><!-- 分页 -->
	</div>
    </div><!--content-->
    
</div><!--container-->

<!-- 新增表单 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" backdrop="static" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h3 class="modal-header-h3" >添加岗位</h3>
            </div>
            <div class="modal-body">
                <form id="formInfo" class="inputF" role="form">
                    <input type="hidden" name="postId">
                    <div class="form-group">
                        <label class="formTit">岗位名</label>
                        <input type="text" class="form-control formInp" name="postName" id="postName" placeholder="岗位名" maxlength="32" />
                        <label class="formDesc corRed">* 最多32个字符</label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">是否可用</label>
                        <input type="checkbox" class="exampleInputFile formChk" name="enabled" id="enabled" checked="checked" />
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
                <h3 class="modal-header-h3" >修改岗位</h3>
            </div>
            <div class="modal-body">
                <form id="formInfo" class="inputF" role="form">
                    <input type="hidden" name="mpostId" id="mpostId">
                    <div class="form-group">
                        <label class="formTit">岗位名</label>
                        <input type="text" class="form-control formInp" name="postName" id="mpostName" placeholder="岗位名" maxlength="32" />
                        <label class="formDesc corRed">* 最多32个字符</label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">是否可用</label>
                        <input type="checkbox" class="exampleInputFile formChk" name="enabled" id="menabled" />
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
</body>
</html>