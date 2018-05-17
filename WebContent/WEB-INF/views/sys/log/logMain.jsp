<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>

<head>
<style type="text/css">
tr:nth-child(2n){
background: transparent;
}
.popup div.row {
margin-top: 8px;
min-height: 25px;
width: 110%; 
}
.popup div.row div.column select{
width: 184px;
}
</style>
	<script type="text/javascript">
	
	var ajax_params = {
		account: null,
		status: null,
		system: null,
		module: null,
		start: null,
		end: null,
    }
	var datatable;
	function initDatatable() {
        datatable = $('.datatable').DataTable({
            "dom": '<"toolbar">frtip',
            "searching": false,
            "processing": true,
            "serverSide": true,
            "select": true,
            "ordering": false,
            "pageLength": 20,
            "language": {
                "url": "${pageContext.request.contextPath}/assets/datatables/cn.json"
            },
            "preDrawCallback": function () {
                //sublime.showLoadingbar($(".main-content"));
            },
            "drawCallback": function () {
                //sublime.closeLoadingbar($(".main-content"));
            },
            "ajax": {
                "url": "${pageContext.request.contextPath}/log/selectLog",
                "type": "post",
                "data": function (d) {
                    d.rp = d.length;
                    d.page = d.start / d.length + 1;
                    $.each(ajax_params, function(k, v) {
                        d[k] = v;
                    });
                },
                "dataSrc": function (json) {
                	json.data = json.rawRecords;
                    json.recordsFiltered = json.total;
                    json.recordsTotal = json.total;
                    return json.data;
                }
            },
            "columns": [
            	{"data": "account", "bSortable": false, "defaultContent":""},
                {"data": "userName", "bSortable": false, "defaultContent":""},
                {"data": "systemName", "bSortable": false, "defaultContent":""},
                {"data": "moduleName", "bSortable": false, "defaultContent":""},
                {"data": "content", "bSortable": false, "defaultContent":""},
                {"data": "statusName", "bSortable": false, "defaultContent":""},
                {"data": "time", "bSortable": false, "defaultContent":""},
            ],
        });
    }
	
	$(function() {
		initDatatable();
		
		//移除空格绑定
		sdfun.fn.trimAll("conditionForm");
			
		//渲染下拉样式
		$("#subSystem, #operationResult, #operationModule").combobox();
		//查询按钮
		$('#aSearch').click(function() {
			var operationResult = $.trim($('#operationResult').val());
			var subSystem = $.trim($('#subSystem').val());
			var operationModule = $.trim($('#operationModule').val());
			
			//校验日期范围是否正确
			var beginTime = $.trim($('#beginTime').val());
			var endTime = $.trim($('#endTime').val());
			if (beginTime != '' && endTime != '' && beginTime > endTime)
			{
				message({
            		html: '开始时间不能大于结束时间',
            		showConfirm: true
            	});
				return false;
			}
		});

		<%--loadList();--%>
		
		//查询按钮
		$('#searchBtn').click(function() {
			var operationResult = $.trim($('#operationResult').val());
			var subSystem = $.trim($('#subSystem').val());
			var operationModule = $.trim($('#operationModule').val());
			
			//校验日期范围是否正确
			var beginTime = $.trim($('#beginTime').val());
			var endTime = $.trim($('#endTime').val());
			if (beginTime != '' && endTime != '' && beginTime > endTime)
			{
				message({
            		html: '开始时间不能大于结束时间',
            		showConfirm: true
            	});
				return false;
			}
	        
	        ajax_params.account = $.trim($("#operator").val());
	        ajax_params.status = operationResult;
	        ajax_params.system = subSystem;
	        ajax_params.module = operationModule;
	        ajax_params.start = beginTime;
	        ajax_params.end = endTime;
	        
			datatable.ajax.reload();
		});
		
		//重置按钮
		$('#resetBtn').click(function(){
			$("#operator, #beginTime, #endTime").val("");
			//重置下拉
			$("#operationResult, #subSystem, #operationModule").val("");
			
			$("#operationResult, #subSystem, #operationModule").combobox('destroy').combobox();
			
			//重新加载操作模块
			changeSubSystem();
		});
		
		// 指定表单提交方式为ajax提交
		$('#addForm').ajaxForm({
		});
		
	});
	

	function changeSubSystem()
	{
		var subSystem = $("#subSystem").val();
		$("#operationModule").empty().combobox('destroy');
		$("#operationModule").append('<option value=""><spring:message code="common.select"/></option>');
		
		if(subSystem != ""){
			//根据子系统获取模块
			$.ajax({
				type : 'POST', 
	            url : '${pageContext.request.contextPath}/log/qryModBySys',   
	            dataType : 'json',  
	            cache : false,
	            data : [{name : 'subSysCode', value : subSystem}],
	            success : function(data) {
	            	//返回失败信息则提示用户并刷新列表
	            	if(data == null){
	            		return ;
	            	}
	            	
	            	if(data.success == false){
	            		message({
	    	    			data: data
	                	});
	            	}
	            	//成功，拼接所有模块
	            	else{
	            		for(var i = 0; i < data.length; i++){
	            			$("#operationModule").append('<option value="'+data[i][0]+'">'+data[i][1]+'</option>');
	            		}
	            	}
	            }
			});
		}
		
		$("#operationModule").combobox();
	}
	
	//切换子系统，联动操作模块
	function changeSubSystem()
	{
		var subSystem = $("#subSystem").val();
		$("#operationModule").empty().combobox('destroy');
		$("#operationModule").append('<option value=""><spring:message code="common.select"/></option>');
		
		if(subSystem != ""){
			//根据子系统获取模块
			$.ajax({
				type : 'POST', 
	            url : '${pageContext.request.contextPath}/log/qryModBySys',   
	            dataType : 'json',  
	            cache : false,
	            data : [{name : 'subSysCode', value : subSystem}],
	            success : function(data) {
	            	if(data == null){
	            		return ;
	            	}
	            	//返回失败信息则提示用户并刷新列表
	            	if(data.success == false){
	            		message({
	    	    			data: data
	                	});
	            	}
	            	//成功，拼接所有模块
	            	else{
	            		for(var i = 0; i < data.length; i++){
	            			$("#operationModule").append('<option value="'+data[i][0]+'">'+data[i][1]+'</option>');
	            		}
	            	}
	            }
			});
		}
		
		$("#operationModule").combobox();
	}

	</script>

<body>


<div class="main-div" style="width:100%">
	<div id="qryView-div">
         <div class="mainContent">
			<table id="mainTable" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				<td valign="top" width="100%">
					<table class="content" width="100%">
					<!-- 查询条件 -->
					<tr id="searchTr">
						<td>
							<div id="search"  style="width: 1000px">
								<form id="conditionForm">
									<div class="search_main">
										<div class="popup">
											<div class="row">
												<div class="column columnL">
													<!-- 操作员 -->
													<label class="tit"><spring:message code="log.operator" /></label>
													<input id="operator" />
												</div>
												<div class="column columnR">
													<!-- 操作子系统-->
													<label class="tit"><spring:message code="log.subSystem" /></label>
													<sd:select id="subSystem" onchange="changeSubSystem();" style="width: 192px; padding-left: 5px;" required="false" categoryName="SYS_LOG_OPER.sub_system" tableName="sys_dict" ></sd:select>
												</div>
											</div>
											
											<div class="row">
												<div class="column columnL">
													<!-- 操作模块-->	
													<label class="tit"><spring:message code="log.operationModule" /></label>
				  					        		<select id="operationModule" readonly="readonly" autoDestroy >
														<option value=""><spring:message code="common.select"/></option>
													</select>
												</div>
												<div class="column columnR">
													<!-- 操作结果  成功、失败-->
													<label class="tit"><spring:message code="log.operationResult" /></label>
													<sd:select id="operationResult" required="false" style="width: 192px; padding-left: 5px;" categoryName="SYS_LOG_OPER.status" tableName="sys_dict" ></sd:select>
												</div>
											</div>
											
											<div class="row">
												<div class="column columnL">
													<!-- 开始时间 -->
													<label class="tit"><spring:message code="common.beginTime" /></label>
													<input id="beginTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true, maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly="readonly" />
												</div>
												<div class="column columnR">
													<!-- 结束时间 -->
													<label class="tit"><spring:message code="common.endTime" /></label>
													<input id="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true, minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly="readonly" />
												</div>
											</div>
											
											<div class="btns" style="    padding-left: 90px;height: 45px;line-height: 45px;vertical-align: middle;">
												<!-- 查询\重置按钮 -->
							                	<a class="button ui-search-btn" id="searchBtn"><i class="am-icon-search"></i><span>搜索</span></a>
	     										<a class="button ui-search-btn ui-btn-bg-yellow" id="resetBtn"><i class="am-icon-trash"></i><span>清空</span></a>
							            	</div>
							            </div>
							            
									</div>
								</form>
							</div>
						</td>
					</tr>
					
					<!-- 列表图层 -->
					<tr>
						<td valign="top">
							<div class="tbl">
								<table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
									<thead>
									<tr>
										<th>操作员</th>
										<th>操作员姓名</th>
										<th>子系统</th>
										<th>子系统</th>
										<th>操作内容</th>
										<th>操作结果</th>
										<th>操作时间</th>
									</tr>
									</thead>
								</table>
							</div>
						</td>
					</tr>
					</table>
			</td>
		</tr>
			</table>
		<div class="clear"></div>
		<input type="hidden" id="subSystem_hidden"/>
	</div>
    </div>
	<input type="hidden" id="subSystem_hidden"/>
</div>

</body>

