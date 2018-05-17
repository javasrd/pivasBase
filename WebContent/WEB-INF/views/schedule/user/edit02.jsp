<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <%@ include file="../../common/head.jsp"%>
		

		<script type="text/javascript" >
			var mmg = null ;
            $(document).ready(function(){
            	
            	mmg = $('#mmg').mmGrid({
                	url: '${contextPath}/user/userList',
                	params: {},
                	method: 'post',
                	autoLoad: true,
                    height: 397, /*10行 如果有边框高度为407，如果去掉边框，高度为397*/
                    sortName: 'row',//排序的字段名
                    sortStatus: 'asc',
                    remoteSort:true,
                    multiSelect: true,
                    checkCol: true,
                    fullWidthRows: true,
                    plugins: [$('#mmg').next().mmPaginator({})],
                    cols: [
						{ title:'用户ID', name:'userId' ,width:100, align:'center', sortable: true, type: 'number', "hidden":true},
						{ title:'账号', name:'account' ,width:100, align:'center', sortable: true, renderer: function(val,row){
							return val;}
						},
						{ title:'用户名', name:'name' ,width:100, align:'center', sortable: true},
						{ title:'操作', width:100, align:'center', sortable: false, renderer: function(val,row){
							return "<a href='"+row.userId+"'>修改</a>";}
						}
                    ]
                });
                
                $('#btnSearch').on('click',function(){
                	qryList(1);
                });
                
            	$('#addBt').click(function (e) {
                    $('#addModal').modal();
                });
            	
            	$("#addMBt").click(function(){
            		$.alert("暂无此功能");
            	});
            	
            	$("#btSave").click(function(){
            		var vparam = {"account":$("#account").val(),"telephone":$("#telephone").val(),"delFlag":$("#delFlag").val()} ;
            		$.ajax({
            			type: 'POST',
            		    url: "${contextPath}/user/addUser",
            			dataType: "json",
            			contentType:'application/json',
            		    data: JSON.stringify(vparam) ,
            		    success: function(response){
            		    	if(response.code && response.code>0){
            		    		$('#addModal').modal('hide');
            		    		qryList();
            		    	}
            		    	$.alert(response.mess);
            		    } 
            		});
            	});
            	
            });
            
            function qryList(page){
            	var params = {"qryStr":$("#qryStr").val()};
            	if(page)params['page']=page;
            	mmg.load(params);
            }
            
        </script>

</head>
<body>

<div class="ch-container">
        


   <div id="content" >
   
   <div class="breadcrumb">
    <button class="btn btn-primary" id="addBt" >添加</button>
    <button class="btn btn-primary" id="addMBt" >批量添加</button>
    <button class="btn btn-primary" id="delBt">删除</button>
    <button class="btn btn-primary" id="impBt">导入</button>
    <button class="btn btn-primary" id="impBt11">测试</button>
    
    <button class="btn btn-primary glyphicon glyphicon-zoom-in" onClick="qryList()" style="float: right;padding: 5px 8px;" ></button>
    <input placeholder="账号 用户名 搜索" id="qryStr" name="query" type="text" class="search-query form-control" style="width:auto;float: right;" >
    
    
            
            
   </div>
   
  
	<div>
	<table id="mmg"></table>
  	<div class="pageRight"></div>
	</div>

	
    </div><!--content-->
    
</div><!--container-->


    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" backdrop="static" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3 class="modal-header-h3" >添加用户</h3>
                </div>
                <div class="modal-body">
                <form id="addForm" class="inputF" role="form">
                	<input type="hidden" name="userId">
                    <div class="form-group">
                        <label class="formTit">用户名</label>
                        <input type="text" class="form-control formInp" name="account" placeholder="用户名">
                        <label class="formDesc corRed">* 最少3个字母</label>
                    </div>
                    <div class="form-group has-error">
                        <label for="telephone" class="formTit">手机号</label>
                        <input type="text" class="form-control formInp" id="telephone" placeholder="手机号">
                        <label class="formDesc">最少6位字母+数字</label>
                    </div>
                    <div class="form-group has-success">
                        <label for="exampleInputFile" class="formTit">附件</label>
                        <input type="file" class="exampleInputFile formInp">
                        <label class="formDesc">请选择附件(jpg)</label>
                    </div>
                    <div class="form-group has-success">
                        <label for="delFlag" class="formTit">是否删除</label>
                        <input type="checkbox" id="delFlag" class="exampleInputFile formChk">
                        <label class="formDesc"></label>
                    </div>
                    <div class="form-group">
                        <label for="delFlag" class="formTit">用户类型</label>
                        <select id="idAdmin" class="form-control formInp" >
                        	<option value="1">管理员</option>
                        	<option value="0">普通用户</option>
                        </select>
                        <label class="formDesc"></label>
                    </div>
                </form>  
                </div>
                <div class="modal-footer">
                	<a href="#" class="btn btn-primary" id="btSave" >保存</a>
                    <a href="#" class="btn btn-default" id="btCancel" data-dismiss="modal">取消</a>
                </div>
            </div>
        </div>
    </div>

</body>
</html>