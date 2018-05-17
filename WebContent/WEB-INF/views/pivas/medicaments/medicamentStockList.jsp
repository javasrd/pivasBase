<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page
	import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp"%>

<html>

<head>
<link
	href="${pageContext.request.contextPath}/assets/sysCss/common/style.css"
	type="text/css" rel="stylesheet" />
<script>
	var paramTemp__1 ;
	
	// 无查看药品消耗量权限
	var power = 0;
	
	var ajax_params = {
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
                "url": "${pageContext.request.contextPath}/mdictStk/mdictStkList",
                "type": "post",
                "data": function (d) {
                    d.rp = d.length;
                    d.page = d.start / d.length + 1;
                    
                    $.each(ajax_params, function(k, v) {
                    	if (v) {
	                        d[k] = v;
                    	}
                    });
                },
                "dataSrc": function (json) {
                	json.data = json.rawRecords;
                    //data.draw = 1;
                    json.recordsFiltered = json.total;
                    json.recordsTotal = json.total;
                    return json.data;
                }
            },
            "columns": [
	             {"data": "medicamentsId", "bSortable": false},
                {"data": "categoryName", "bSortable": false, "defaultContent":""},
                {"data": "medicamentsName", "bSortable": false, "defaultContent":""},
                {"data": "medicamentsCode", "bSortable": false, "defaultContent":""},
                {"data": "medicamentsSpecifications", "bSortable": false, "defaultContent":""},
                {"data": "medicamentsDosage", "bSortable": false, "defaultContent":""},
                {"data": "medicamentsSuchama", "bSortable": false, "defaultContent":""},
                {"data": "medicamentsPackingUnit", "bSortable": false, "defaultContent":""},
                {"data": "medicamentsPlace", "bSortable": false, "defaultContent":""},
                {"data": "medicamentsStock", "bSortable": false, "defaultContent":""},
                {"data": "medicamentsLimit", "bSortable": false, "defaultContent":""},
                {"data": "consumption", "bSortable": false, "defaultContent":""},
                {"data": "used", "bSortable": false, "defaultContent":""},
            ],
            columnDefs: [
            	 {
                    //   指定第一列，从0开始，0表示第一列，1表示第二列……
                    targets: 0,
                    render: function(data, type, row, meta) {
                        return '<input type="checkbox" style="margin-top:3px;" class="itemchk" name="checklist" value="' + data + '" />';
                    }
                }, 
            ],
        });
    }
	
	function chkAll(self) {
		$('input[name="checklist"]').each(function(i){
		     this.checked = $(self).attr("checked");
		 });
	}
	
	$(function() {
		initDatatable();

		$("#aSearch").bind("click",function(){
			qry();
		});
		
		function getCheckedId() {
			var arr = new Array(0); 
			$('input[name="checklist"]:checked').each(function(i){
				arr.push(this.value);  
			 });
			
			return arr;
		}
		
		$("#export").bind("click",function(){
			var params = [];
			var ids = getCheckedId() + '_' + power;
			
			if(paramTemp__1){
				params = paramTemp__1.concat();
			}
			params.push({"name":"stockStatus","value":$("#stockStatus").val()});
			params.push({"name":"yyrq","value":$("#yyrq").val()});
			params.push({"name":"medicamentIDs","value":ids});
			
			message({
					html: '<spring:message code="common.exportConfirm"/>',
	    			showCancel:true,
	    			confirm:function(){
						$.ajax({
							type : 'POST', 
							url: '${pageContext.request.contextPath}/mdictStk/expMdcts',   
				            dataType : 'json',  
				            cache : false,
				            data : params || [],
				            success : function(data) {
				            	window.open("${pageContext.request.contextPath}/export/<shiro:principal property="account"/>/"+data.msg);
				            },
				            error : function () {
				            	message({
				            		html: '<spring:message code="common.op.error"/>',
				            		showConfirm: true
				            	});
					        }
						});
	    			}
            	});
		});
		
		$("#checkStock").bind("click",function(){
			message({
					html: '确认检查库存？',
	    			showCancel:true,
	    			confirm:function(){
						$.ajax({
							type : 'POST', 
							url: '${pageContext.request.contextPath}/mdictStk/chkMdctStk',   
				            dataType : 'json',  
				            cache : false,
				            success : function(data) {
				            	layer.alert(data.msg, {'title': '', icon: 1});
				            	qry();
				            },
				            error : function () {
				            	layer.alert('<spring:message code="common.op.error"/>', {'title': '', icon: 0});
					        }
						});
	    			}
            	});
		});
		
		// 库存单导入
		$( "#import").bind("click",function(){
			$("#importView-div").dialog("open");
		});
		
		$( "#import-submit").bind("click",function(){
			
			var bValid = true;
			bValid = bValid && checkEmpty($( "#file" ));
			if (!bValid ) {
				return ;
			}
			
			$.ajaxFileUpload
            (
                {
                    url: '${pageContext.request.contextPath}/mdictStk/iptMdctStk', //用于文件上传的服务器端请求地址
                    type: 'post',
                    secureuri: false, //一般设置为false
                    fileElementId: 'file', //文件上传域的ID
                    dataType : 'json', //返回值类型 一般设置为json
                    success: function (data, status)  //服务器成功响应处理函数
                    {
                    	if(data.success){
                    		$("#description").val(data.msg);
		            	}
						else{
                    		$("#description").val(data.msg);
                    		window.open("${pageContext.request.contextPath}/export/<shiro:principal property="account"/>/Drug_Inventory_Error.xls");
		            	}
                    },
                    error: function (data, status, e)//服务器响应失败处理函数
                    {
                    	$("#description").val('');
                    }
                }
            )

		});
		
		$("#importView-div").dialog({
			autoOpen: false,
			height: 350,
			width: 530,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {	
					qry();
					$("#importView-div").dialog("close");
				},
				"<spring:message code='common.cancel'/>": function() {
					qry();
					$("#importView-div").dialog("close");
				}
			},
			close: function() {
				resetForm("importView-div");
			}
		});
	});

	function qry() {
		$("#tbSearch input:text").each(function() {
			if ($(this).attr("name")) {
				ajax_params[$(this).attr("name")] = $(this).val();
			}
            ajax_params['stockStatus'] = $("#stockStatus").val();
		});
		datatable.ajax.reload();
	}
	
	function selectStockStatus(){
		qry();
	}
	
	
	function changeStock(obj){
		var medicamentsId = $(obj).attr("medicamentsId");
		var textdivid = 'divtext_' + medicamentsId;
		var labelid = 'label_' + medicamentsId;
		$("#"+textdivid).show();
		$("#"+labelid).hide();
	}
	
	function changeLimit(obj){
		var medicamentsId = $(obj).attr("medicamentsId");
		var textdivid = 'divtextLimit_' + medicamentsId;
		var labelid = 'labelLimit_' + medicamentsId;
		$("#"+textdivid).show();
		$("#"+labelid).hide();
	}
	
	function toConfirm(obj){
		var medicamentsId = $(obj).attr("medicamentsId");
		var textid = 'text_' + medicamentsId;
		var textdivid = 'divtext_' + medicamentsId;
		var labelid = 'label_' + medicamentsId;
		var medicamentsStock = $('#' + textid).val();
		
		var textLimitid = 'textLimit_' + medicamentsId;
		var textLimitdivid = 'divtextLimit_' + medicamentsId;
		var labelLimitid = 'labelLimit_' + medicamentsId;
		var medicamentsLimit = $('#' + textLimitid).val();
		
		var bValid = true;
		
		bValid = bValid && checkEmpty($('#' + textid));
		bValid = bValid && checkEmpty($('#' + textLimitid));
		if (!bValid ) {
			message({
				html : '该选项必填！',
				showConfirm : true
			});
			return;
		}
		
		bValid = bValid && checkDecimals($('#' + textid));
		if (!bValid ) {
			message({
				html : '药品库存量必须为数字！',
				showConfirm : true
			});
			return;
		}
		
		bValid = bValid && checkDecimals($('#' + textLimitid));
		if (!bValid ) {
			message({
				html : '药品最低量必须为数字！',
				showConfirm : true
			});
			return;
		}
		
		$.ajax({
			type : 'POST', 
			url: '${pageContext.request.contextPath}/mdictStk/updMdctse',   
            dataType : 'json',  
            cache : false,
            data : [{name : 'medicamentsId', value : medicamentsId},
                    {name : 'medicamentsStock', value : medicamentsStock},
                    {name : 'medicamentsLimit', value : medicamentsLimit}
            ],
            success : function(data) {
            	$("#"+labelid).html(medicamentsStock).show();
            	$("#"+textid).html(medicamentsStock);
            	$("#"+textdivid).hide();
            	
            	$("#"+labelLimitid).html(medicamentsLimit).show();
            	$("#"+textLimitid).html(medicamentsLimit);
        		$("#"+textLimitdivid).hide();
        		
        		//qry();
            },
            error : function () {
            	message({
            		html: '<spring:message code="common.op.error"/>',
            		showConfirm: true
            	});
	        }
		});
	}
	
	function toCancel(obj){
		var medicamentsId = $(obj).attr("medicamentsId");
		var textid = 'text_' + medicamentsId;
		var textdivid = 'divtext_' + medicamentsId;
		var labelid = 'label_' + medicamentsId;
		
		$("#"+textdivid).hide();
		$("#"+labelid).show();
		
		var textLimitid = 'textLimit_' + medicamentsId;
		var textdivLimitid = 'divtextLimit_' + medicamentsId;
		var labelLimitid = 'labelLimit_' + medicamentsId;
		
		$("#"+textdivLimitid).hide();
		$("#"+labelLimitid).show();
	}
	
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/jquery/ajaxfileupload.js"></script>

</head>
<body>

	<%-- 搜索条件--开始 --%>
	<div data-qryMethod funname="qryList"  class="ui-search-header ui-search-box" id="tbSearch" style="display: inline; ">
		<div style="float: right">
		库存：
		<select id="stockStatus" style="height: 26px; width:80px" name="stockStatus" data-cnd="true"
				onchange="selectStockStatus()">
			<option value="">请选择</option>
			<option value="0">有</option>
			<option value="1">无</option>
		</select>
		<input placeholder="用药日期" type="text" id="yyrq" class="Wdate" name="yyrq"
			   style="height: 25px; color: #555555;"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
			   onchange="selectStockStatus();" />
		<input placeholder="药品分类" name="categoryNames" size="8" data-cnd="true">&nbsp;&nbsp;
		<input placeholder="药品名称" name="medicamentsName" size="8" data-cnd="true">&nbsp;&nbsp;
		<input placeholder="药品编码" name="medicamentsCode" size="8" data-cnd="true">&nbsp;&nbsp;
		<input  placeholder="药品产地" name="medicamentsPlace" size="8" data-cnd="true">&nbsp;&nbsp;
		<input  placeholder="药品速查码" name="medicamentsSuchama" size="8" data-cnd="true">
		<button class="button ui-search-btn ui-btn-bg-green" onclick="qry()"><i class="am-icon-search"></i><span>搜索</span></button>
		<button  class="button ui-search-btn ui-btn-bg-yellow" onclick="$('#tbSearch input, #tbSearch select').val('');"><i class="am-icon-trash"></i><span>清空</span></button>
		</div>
		<shiro:hasPermission name="PIVAS_BTN_633">
			<button class="button ui-search-btn ui-btn-bg-green" id="export"><i class="am-icon-arrow-up"></i><span><spring:message
					code='common.export' /></span></button>
		</shiro:hasPermission>
		<shiro:hasPermission name="PIVAS_BTN_634">
			<button class="button ui-search-btn ui-btn-bg-green" id="import"><i class="am-icon-arrow-down"></i><span><spring:message
					code='common.import' /></span></button>
		</shiro:hasPermission>
		<shiro:hasPermission name="PIVAS_BTN_637">
			<button class="button ui-search-btn ui-btn-bg-green" id="checkStock"><i class="am-icon-edit"></i><span>库存检查</span></button>
		</shiro:hasPermission>
	</div>
	<!-- 搜索条件--开始 -->

	<%-- 导入库存数据弹出框 --%>
	<div id="importView-div"
		title="<spring:message code='pivas.medicament.stock.import'/>"
		align="left" style="display: none;">
		<form id="editView-form" action="" method="post">
			<div class="popup">
				<div class="row">
					<div class="column">
						<label class="tit"><spring:message
								code='pivas.medicament.stock.name' /></label> <input type="file"
							id="file" name="file"> <a class="button"
							id="import-submit"><spring:message code='common.import' /></a>
					</div>
				</div>

				<div class="row">
					<div class="column" align="left">
						<textarea name="description" id="description"
							style="margin: 0px; height: 143px; width: 456px; resize: none"
							readonly="readonly"></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
	<table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
		<thead>
		<tr>
			<th>
				<input style="margin-top:2px;" type="checkbox" onclick="chkAll(this);" title="全选" class="noborder">						
			</th>
			<th>药品分类</th>
			<th>药品名称</th>
			<th>药品编码</th>
			<th>规格</th>
			<th>剂量</th>
			<th>速查码</th>
			<th>包装单位</th>
			<th>药品产地</th>
			<th>药品库存</th>
			<th>最低量</th>
			<th>实际消耗量</th>
			<th>消耗量</th>
		</tr>
		</thead>
	</table>


</body>
<style type="text/css">
.oe_searchview .oe_searchview_clear {
	margin-top: 10px;
}

.oe_searchview .oe_searchview_search {
	margin-top: 36px;
}

.oe_searchview {
	line-height: 18px;
	border: 1px solid #ababab;
	background: white;
	width: 323px;
	-moz-border-radius: 13px;
	cursor: text;
	padding: 1px 0;
	float: left;
	border: 1px solid #ababab;
	margin-top: 10px;
}
</style>
</html>