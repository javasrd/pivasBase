<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../common/head.jsp"%>
<script src="${contextPath}/assets/tinymce/tinymce.min.js"></script>
<script src="${contextPath}/assets/js/schedule-custom.js"></script>
</head>
<style>
.toolbar-btn {
	float: right;
}

.page-header {
	padding-bottom: 0;
	margin-top: 15px;
	margin-bottom: 10px;
	border-color: #E0E4E8;
	-moz-box-shadow: 0 1px 0px rgba(255, 255, 255, 1);
	-webkit-box-shadow: 0 1px 0px rgba(255, 255, 255, 1);
	box-shadow: 0 1px 0px rgba(255, 255, 255, 1);
}

.clearfix {
	*zoom: 1
}

.clearfix:before, .clearfix:after {
	display: table;
	line-height: 0;
	content: ""
}

.clearfix:after {
	clear: both;
}

.noneEditable span {
	filter: alpha(opacity = 40);
	-moz-opacity: 0.4;
	opacity: 0.4;
	cursor: default;
}

.div_color {
	border: 1px solid #cccccc;
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	height: 20px;
	width: 65px;
}

#btnSearch {
	height: 26px;
	margin-left: 3px;
}
</style>

<script type="text/javascript">
	//列表显示的grid
	var tabGrid = null;

	$(function() {
	        
		$("#tabGrid tbody tr td span").addClass('noneEditable');
    	
		//
    	tabGrid = $('#tabGrid').mmGrid({
        	url: '${contextPath}/holidayMgr/getForPage',
        	params: {},
        	method: 'post',
        	autoLoad: true,
            height: 397, /*10行 如果有边框高度为407，如果去掉边框，高度为397*/
            //sortName: 'postName',//默认排序的字段名
            //sortStatus: 'asc',
            remoteSort:true,
//            multiSelect: true,
//            checkCol: true,
            fullWidthRows: true,
            plugins: [$('#tabGrid').next().mmPaginator({})],
            cols: [
				{ title:'ID', name:'id' ,width:100, align:'center', sortable: false, type: 'number', "hidden":true},
				{ title:'节假日名称', name:'name' ,width:200, align:'center', sortable: false, renderer: function(val,row){
					return val.replace(/\</g,"&lt;");
				}},
				{ title:'开始日期', name:'startDate' ,width:100, align:'center', sortable: false},
				{ title:'结束日期', name:'endDate' ,width:100, align:'center', sortable: false},
				{ title:'操作', width:100, align:'center', sortable: false, renderer: 
					function(val,row){
							return '<span style=" cursor: pointer;" class="noneEditable glyphicon glyphicon-edit" aria-hidden="true" onclick="javascript:edit(' + row.id
									+ ')"></span>&nbsp;<span style=" cursor: pointer;" class="noneEditable glyphicon glyphicon-trash" aria-hidden="true" onclick="javascript:deleteHolidays(' + row.id + ')"></span>';;
				}
				}
            ]
        });
	    	
	    
	    $('#btAdd').click(function() {
	    	$('#addHoliday .modal-header-h3').html("添加节假日");
	    	$('#opr').val('add');
	    	$('#id').val('');
    		$('#name').val('');
    		$('#startDate').val('');
    		$('#endDate').val('');
    		$('#addHoliday').modal();
    	});
    	
    	$('#btSave').click(function() {
    		var name = $.trim($('#name').val());
    		if (name.length == 0) {
    			$.alert('节假日名称不能为空！');
    			return;
    		} else if (!checkLength($('#name'), 1, 50)) {
				$.alert('节假日名称长度不能大于50！');
				return;
			}
    		$('#name').val(name)
    		
    		var startDate = $.trim($('#startDate').val());
    		if (startDate.length == 0) {
    			$.alert('开始日期不能为空！');
    			return;
    		}
    		
    		var endDate = $.trim($('#endDate').val());
    		if (endDate.length == 0) {
    			$.alert('结束日期不能为空！');
    			return;
    		}
    		
    		$.ajaxFPostJson("${contextPath}/holidayMgr/save", $("#formInfo").serialize(), {
                
                "success": function(response) {
                    if(response.code && response.code > 0) {
                        qryList();
                        $('#addHoliday').modal('hide');
                    }else{
                        $.alert(response.msg);
					}
                } ,
                "error": function(errmess) {
                    $.alert("服务异常["+errmess+"]，请联系管理员");
                }
            });
    	});
    	
    	//批量删除事件
		$("#btDel").click(function(){
		    
		    //获得checkbox选择的列
		    var ids = tabGrid.selectedValsByName('id');
		    
		    // 为空提示
		    if (null != ids && "" != ids) {
		        
		        //提示信息
			    $.alert("是否删除所选记录?", {
			        "cancleShow": true,
			        "config": function() {
		    	        var userIds = "";
		    	        for (var i = 0 ;i < ids.length; i++) {
		    	        	userIds = userIds + ids[i] + ",";
		    	        }
		    	        userIds = userIds.substring(0, userIds.length - 1);
		    	        
		    	        
		    	        $.ajaxFPostJson("${contextPath}/holidayMgr/del", {'ids': userIds}, {
		    	            
		    	            "success": function(response) {
		    	                if(response.code && response.code > 0) {
		    	                    qryList();
		    	                }
		    	                $.alert2(response.mess);
		    	            } ,
		    	            "error": function(errmess) {
		    	                $.alert2("服务异常["+errmess+"]，请联系管理员");
		    	            }
		    	        });
			        }
			    });
		    } else {
		        $.alert("请选择一条记录!");
		    }
		});
	});
		
	//查询
	function qryList(page) {
		var params = {
			"k" : $("#qryStr").val()
		};
		if (page)
			params['page'] = page;
		tabGrid.load(params);
	}
	
	function deleteHolidays(id) {
		$.alert("是否删除所选记录?", {
	        "cancleShow": true,
	        "config": function() {
	        	$.ajaxFPostJson("${contextPath}/holidayMgr/del", {'ids': id}, {
	                "success": function(response) {
	                	$.alert2(response.mess);
	                	if(response.code && response.code > 0) {
	                        qryList();
	                    }
	                } ,
	                "error": function(errmess) {
	                    $.alert2("服务异常["+errmess+"]，请联系管理员");
	                }
	            });
	        }
	    });
	}
	
	function edit(id) {
		$.ajaxFPostJson("${contextPath}/holidayMgr/getById", {'id': id, '_r': new Date().getTime()}, {
            "success": function(response) {
                if(response.code && response.code > 0) {
                	$('#opr').val("update");
            		$('#id').val(response.data.id);
            		$('#name').val(response.data.name);
            		$('#startDate').val(response.data.startDate);
            		$('#endDate').val(response.data.endDate);
            		$('#addHoliday .modal-header-h3').html("修改节假日");
            		$('#addHoliday').modal();
                }
            } ,
            "error": function(errmess) {
                $.alert("服务异常["+errmess+"]，请联系管理员");
            }
        });
	}
	
</script>

<body>

	<div class="ch-container clearfix">

		<div id="content" style="margin: 20px 10px 0px 10px;">
			<!-- content -->
			<div class="breadcrumb" style="height:40px;">
				<!-- breadcrumb -->
				<input placeholder="节假日名称" id="qryStr" name="query" type="text" class="search-query form-control" style="float:left;width:auto;margin:2px 0;">
				<button class="btn btn-primary glyphicon glyphicon-zoom-in" id="btnSearch" onclick="qryList()" style="margin:0 10px 1px 5px;float:left;height:28px;width:28px;padding:3px"></button>
				<button class="btn btn-primary" style="color: #fff" id="btAdd">添加节假日</button>

			</div>
			<!-- breadcrumb -->
			<div style="margin: 20px 0px 0px 0px;"></div>
			<div class="tablePageDiv">
				<table id="tabGrid"></table>
				<!-- 列表 -->
				<div class="pageRight"></div>
				<!-- 分页 -->
			</div>

		</div>
		<!--content-->

	</div>
	<!--container-->

	<!-- 新增表单 -->
	<div class="modal fade" id="addHoliday" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" backdrop="static" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3 class="modal-header-h3">添加节假日</h3>
				</div>
				<div class="modal-body">
					<form id="formInfo" class="inputF" role="form">
						<input type="hidden" name="opr" id="opr">
						<input type="hidden" name="id" id="id">
						<div class="form-group">
							<label class="formTit">节假日名称</label> <input type="text"
								class="form-control formInp" name="name" id="name"
								placeholder="节假日名称" maxlength="50" /> <label
								class="formDesc corRed">* 最多50个字符</label>
						</div>
						<div class="form-group">
							<label class="formTit">开始日期</label> <input type="text"
								class="form-control formInp" name="startDate" id="startDate"
								placeholder="开始日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\');}'})"/> <label class="formDesc corRed">*必填</label>
						</div>
						<div class="form-group">
							<label class="formTit">结束日期</label> <input type="text"
								class="form-control formInp" name="endDate" id="endDate" 
								placeholder="结束日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\');}'})"/> <label class="formDesc corRed">*必填</label>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<a href="#" class="btn btn-primary" id="btSave">确定</a> <a href="#"
						class="btn btn-default" id="btCancel" data-dismiss="modal">取消</a>
				</div>
			</div>
		</div>
	</div>

</body>
</html>