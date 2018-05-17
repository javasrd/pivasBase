<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <%@ include file="../../common/head.jsp"%>
		

		<script type="text/javascript" >
			var tabGrid = null ;
            $(document).ready(function(){
            	
            	tabGrid = $('#tabGrid').mmGrid({
                	url: '${contextPath}/user/userList',
                	params: {},
                	method: 'post',
                	autoLoad: true,
                    height: 397, /*10行 如果有边框高度为407，如果去掉边框，高度为397*/
                    sortName: 'account',//默认排序的字段名
                    sortStatus: 'asc',
                    remoteSort:true,
                    multiSelect: true,
                    checkCol: true,
                    fullWidthRows: true,
                    plugins: [$('#tabGrid').next().mmPaginator({})],
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
                
            	$('#btAdd').click(function (e) {
                    $('#addModal').modal();
                });
            	
            	$("#btAddM").click(function(){
            		$.alert("暂无此功能");
            	});
            	
            	$("#btSave").click(function(){
            		var vparam = initParam('formInfo'); //{"account":$("#account").val(),"telephone":$("#telephone").val(),"delFlag":$("#delFlag").val()} ;
            		$.ajaxFPostJson("${contextPath}/user/addUser",vparam,{
            			/*
            			"errorShow": false,//如果后台有异常，不需要默认的弹窗的提示，可以加上这个配置
            			"error": function(){//如果后台有异常，需要处理其他业务逻辑，可添加此方法
            				$.alert(response.mess);
            			},*/
            			"success": function(response){
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
            	tabGrid.load(params);
            }
            
        </script>

</head>
<body>

<div class="ch-container">

   <div id="content" >
   
	   <div class="breadcrumb">
		    <button class="btn btn-primary" id="btAdd" >添加</button>
		    <button class="btn btn-primary" id="btAddM" >批量添加</button>
		    <button class="btn btn-primary" id="btDel">删除</button>
		    <button class="btn btn-primary" id="btImp">导入</button>
		    
		    <button class="btn btn-primary glyphicon glyphicon-zoom-in searchBt" id="btnSearch" ></button>
		    <input placeholder="账号 用户名 搜索" id="qryStr" name="query" type="text" class="search-query form-control searchInp" >       
	   	</div>
   		
		<div class="tablePageDiv">
			<table id="tabGrid"></table><!-- 列表 -->
		  	<div class="pageRight"></div><!-- 分页 -->
		</div>

    </div><!--content-->
    
</div><!--container-->
	
	<!-- 弹窗 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" backdrop="static" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3 class="modal-header-h3" >添加用户</h3>
                </div>
                <div class="modal-body">
                <form id="formInfo" class="inputF" role="form">
                	<input type="hidden" name="userId">
                    <div class="form-group">
                        <label class="formTit">用户名</label>
                        <input type="text" class="form-control formInp" name="account" placeholder="用户名">
                        <label class="formDesc corRed">* 最少3个字母</label>
                    </div>
                    <div class="form-group has-error">
                        <label class="formTit">手机号</label>
                        <input type="text" class="form-control formInp" name="telephone" placeholder="手机号">
                        <label class="formDesc">最少6位字母+数字</label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">附件</label>
                        <input type="file" class="exampleInputFile formInp" name="file" >
                        <label class="formDesc">请选择附件(jpg)</label>
                    </div>
                    <div class="form-group has-success">
                        <label class="formTit">是否删除</label>
                        <input type="checkbox" class="exampleInputFile formChk" id="delFlag" >
                        <label class="formDesc"></label>
                    </div>
                    <div class="form-group">
                        <label class="formTit">用户类型</label>
                        <select class="form-control formInp" name="idAdmin" >
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