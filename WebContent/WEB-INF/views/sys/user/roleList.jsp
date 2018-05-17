<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>

<head>

<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/zTree3.3/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/pivas/js/strings.js"></script>
	
	
	<script>
	function chkAll(self) {
		$('input[name="checklist"]').each(function(i){
		     this.checked = $(self).attr("checked");
		 });
	}
	
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
                "url": "${pageContext.request.contextPath}/sys/role/rolelist",
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
				{"data": "roleId", "bSortable": false},
                {"data": "name", "bSortable": false, "defaultContent":""},
                {"data": "description", "bSortable": false, "defaultContent":""}
            ],
            columnDefs: [
            	 {
                    targets: 0,
                    render: function(data, type, row, meta) {
                        return '<input type="checkbox" style="margin-top:3px;" class="itemchk" name="checklist" value="' + data + '" />';
                    }
                }, 
                {
					targets: 3,
                    "width": "15%",
					render: function (data, type, row) {
						var str = "";
                     	<shiro:hasPermission name="PIVAS_BTN_126">
                     		str +=  '&nbsp;<a class="button btn-bg-green" href="javascript:;" onclick="editRole({0});">修改</a>'.format([row.roleId])
                		</shiro:hasPermission>
                     		<shiro:hasPermission name="PIVAS_BTN_128">
                     		str +=  "&nbsp;<a class=\"button btn-bg-green\" href=\"javascript:;\" onclick=\"role_auth({0});\">授权</a>".format([row.roleId]);
                    		</shiro:hasPermission>
                     	
                        return str;
					}
            	},
            ],
        });
    }
	
	function editRole(id) {
		var ids = [id];
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/sys/role/initUpdateRole',
				dataType : 'json',
				cache : false,
				data : [ {name : 'roleId',value : ids} ],
				success : function(data) {
					if(data.success == false){
	            		$(".pReload").click();
	            		layer.alert(data.description, {'title': '操作成功', icon: 1});
	                	return;
	            	}
					else{
						$("#roleId").val(data.roleId);
						$("#nameID").val(data.name);
						$("#descriptionID").val(data.description); 
	            	}
					$("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='role.updateRole'/>");
					$("#editView-div").dialog("open");
				},
				error : function() {}
			});
	}
	
	var roleIds
	function role_auth(id) {
		 var ids = [id];
		 roleIds = ids;
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/sys/role/treeRole',
			dataType : 'json',
			cache : false,
			data : [{name : 'roleId', value : ids}],
			success : function(data) {
			if(data.success == false) {
					layer.alert('操作失败', {'title': '', icon: 2});
				}
				else
				{
					$( "#departmentTreeID" ).dialog( "open" );
					var setting = {
							check: {
								enable: true,
								checkParent : true
							},
							data: {
								simpleData: {
									enable: true
								}
							},
							callback : {
								onCheck : onClickNode
							}
					};
					$.fn.zTree.init($("#roleTreeID"), setting, data);
				}
				
			}
		});
	}
	
	//回调
	function onClickNode(event, treeId, treeNode)
	{
	   rolePrivilege = '';
		$.each($.fn.zTree.getZTreeObj("roleTreeID").getCheckedNodes(),function(index, node) {
			rolePrivilege += node.id + ","
		});
	}
	
	$(function() {
		sdfun.fn.trimAll("editView-div");
		
		var name = $("#nameID");
		var descriptionID = $("#descriptionID");
		var	allFieldsInfo = $( [] ).add(name).add(descriptionID);
		
		initDatatable();
		
		$("#aSearch").bind("click",function(){
			datatable.ajax.reload(null, false); // 刷新表格数据，分页信息不会重置
		});
		
		$("#editView-div").dialog({
			autoOpen: false,
			height: 260,
			width: 500,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var bValid = true;
					allFieldsInfo.removeClass( "ui-state-error");
					bValid = bValid && checkLength( name, 1, 32);
					bValid = bValid && checkLength( descriptionID, 0, 256);
					if (!bValid ) {
						return ;
					}
					var url = "${pageContext.request.contextPath}/sys/role/addRole";
					var params = {"name":$("#nameID").val(),"description":$("#descriptionID").val()} ;
					if($("#roleId").val() && $("#roleId").val()!=""){
						url = "${pageContext.request.contextPath}/sys/role/updateRole";
						params["roleId"]=$("#roleId").val() ;
					}
					$.ajax({
						type : 'POST',
						url : url,
						dataType : 'json',
						cache : false,
						data : params,
						success : function(data) {
			            	if(data.success == true || data.code == '<%=ExceptionCodeConstants.RECORD_DELETE%>') {
			            		$("#editView-div").dialog("close");
					    	}
			            	datatable.ajax.reload(null, false); // 刷新表格数据，分页信息不会重置
					    	layer.alert('操作成功', {'title': '', icon: 1});
						},
						error : function() {}
					});
				},
				"<spring:message code='common.cancel'/>": function() {
					allFieldsInfo.val("").removeClass("ui-state-error");
					$("#editView-div").dialog("close");
				}
			},
			close: function() {
				allFieldsInfo.val("").removeClass("ui-state-error");
			}
		});
		
		//新增按钮
		$( "#addDataDicRoleBtn").bind("click",function(){
			$("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='role.addRole'/>");
			$("#editView-div").dialog("open");
			$("#roleId").val("");
		});
		
		function getCheckedId() {
			var arr = new Array(0); 
			$('input[name="checklist"]:checked').each(function(i){
				arr.push(this.value);  
			 });
			
			return arr;
		}

		$("#deleteDataDicRoleBtn").bind("click",function(){
			var ids = getCheckedId();
			if (ids && ids.length >0) {
				message({
	    			html: '<spring:message code="common.deleteConfirm"/>',
	    			showCancel:true,
	    			confirm:function(){
						$.ajax({
							type : 'POST', 
				            url : '${pageContext.request.contextPath}/sys/role/delRole',
				            dataType : 'json',  
				            cache : false,
				            data : [{name : 'roleId', value : ids}],
				            success : function(data) {
			            		datatable.ajax.reload(null, false); // 刷新表格数据，分页信息不会重置
						    	message({
					    			data: data
				            	});
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
			} else {
				layer.alert('<spring:message code="common.plzSelectOneRecourd"/>', {'title': '', icon: 0});
			}
		});

		//选择权限树节点
		$("#departmentTreeID").dialog({
			autoOpen : false,
			height : 400,
			width : 350,
			modal : true,
			resizable : false,
			buttons : {
				"<spring:message code='common.confirm'/>" : function() {
						var ps = new Array();
						var ids = roleIds;
						var treeObj = $.fn.zTree.getZTreeObj("roleTreeID");
						//获取所有选中节点，不包括禁用
						var nodes = treeObj.getCheckedNodes(true);
						if(nodes && nodes.length >0){
							for(var i=0,j = nodes.length; i < j; i++){
								ps.push(nodes[i].id);
							}
						}
						//获取禁用并选中节点
						var ignoreNodes = treeObj.getNodesByFilter(function(node){
							return (node.chkDisabled==true&&node.checked==true);
						}); 
						var ignorePs = null;
						if(ignoreNodes&&ignoreNodes.length > 0){
							ignorePs = new Array();
							$.each(ignoreNodes,function(i,j){
								ignorePs.push(j.id);
							});
						}
// 						alert(JSON.stringify({pris:ps,roleId:ids}));
						$.ajax({
						type : 'POST',
						url : '${pageContext.request.contextPath}/sys/role/addRoleRefPrivilege',
						contentType:'application/json',
						data : JSON.stringify({pris:ps,roleId:ids[0],ignorePris:ignorePs}),
						success : function(data) {
							//新增成功，关闭新增弹出框
			            /* 	if(data.success) {
					    		} */
			            		$('#departmentTreeID').dialog('close');
			            	//不管成功还是失败，刷新列表
			            	$(".pReload").click();
			            	//弹出提示信息
					    		message({
				    				data: data
			            	});
						},
						error : function() {
						}
					});
				},
				"<spring:message code='common.cancel'/>" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {

			}
		});
		
		
	});
	
	function qry() {
		$("#tbSearch input:text").each(function() {
			if ($(this).attr("name")) {
				ajax_params[$(this).attr("name")] = $(this).val();
			}
		});
		
		datatable.ajax.reload();
	}
	
	function closeDeal(){
		$("#editView-div").find("input").each(function(){
			$(this).val("");
		});
		$("#editView-div").find("textarea").each(function(){
			$(this).val("");
		});
	}
	
	</script>
</head>

<body>
        <div id="searchDiv">
			<div data-qryMethod class="ui-search-header ui-search-box" id="tbSearch" style="display: inline; ">
				<div style="float:right ">
					<input placeholder="角色名称" id="nameList" name="nameList" size="8" data-cnd="true">
					<input placeholder="角色描述" id="descList" name="descList" size="20" data-cnd="true">
					<button class="button ui-search-btn ui-btn-bg-green" onclick="qry()"><i class="am-icon-search"></i><span>搜索</span></button>
					<button class="button ui-search-btn ui-btn-bg-yellow" onclick='$("#tbSearch input:text").val("");'><i class="am-icon-trash"></i><span>清空</span></button>
				</div>
				<shiro:hasPermission name="PIVAS_BTN_125">
					<a class="button ui-search-btn ui-btn-bg-green" id="addDataDicRoleBtn"><i class="am-icon-plus"></i><span><spring:message code='common.add'/></span></a>
				</shiro:hasPermission>
				<shiro:hasPermission name="PIVAS_BTN_127"><a class="button ui-search-btn ui-btn-bg-red" id="deleteDataDicRoleBtn"><i class="am-icon-trash"></i><span><spring:message code='common.del'/></span></a></shiro:hasPermission>
			</div>
		</div>
        
	<table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
		<thead>
		<tr>
			<th>
				<input style="margin-top:2px;" type="checkbox" onclick="chkAll(this);" title="全选" class="noborder">
			</th>
			<th>角色名称</th>
			<th>角色描述</th>
			<th>操作</th>
		</tr>
		</thead>
	</table>


	<%-- 新增编辑弹出框 --%>
	<div id="editView-div" title="<spring:message code='role.addRole'/>" align="center" style="display: none;">
		<input type="hidden" id="roleId" name="roleId"/>
		<div class="popup">
	    	<div class="row">
				<div class="column">
					<label class="tit"><spring:message code='role.roleName'/></label>
					<input type="text" id="nameID" name="name" maxlength="32" title="<spring:message code='common.op.remind2'/>"/>
					<span class="mand">*</span>
				</div>
				<div class="column">
					<label class="tit"><spring:message code='role.memo'/></label>
					<textarea rows="1"  name="description" id="descriptionID" maxlength="256" title="<spring:message code='common.op.remind6'/>" style="resize:none" ></textarea>
					<span class="mand"></span>
				</div>
			</div>
		</div>
	</div>
		
		
		
		<%-- 修改弹出框
		<div id="updateDataRole" title="<spring:message code='role.updateRole'/>" align="center" style="display: none;">
			<form id="updateDataDicFormRole" action="${pageContext.request.contextPath}/sys/role/updateRole" method="post">
			<div class="layeForm">
				<table>
				<input type="hidden" id="roleId" name="roleId"/>
					<tr>
						<td class="text" style=" width:17%;"><spring:message code="role.roleName"/></td>
						<td class="in" style=" width:29%;">
							<input type="text" name="name" id="nameAccount" maxlength="32"  title="<spring:message code='common.op.remind2'/>"/>
						</td>
						<td class="mand">*</td>
					</tr>
					
					<tr height="32px;">
						<td class="text tbox" style=" width:17%;"><spring:message code="role.memo"/></td>
						<td class="in ibox" style=" width:29%;">
							<textarea name="description" id="descriptionNumber" value="" maxlength="256"
							 title="<spring:message code='common.op.remind6'/>"></textarea>
						</td>
						<td class="mand"></td>
					</tr>
				</table>
				</div>
			</form>
		</div> --%>
	
	<!-- 权限树 -->
	<div id="departmentTreeID" class="zTreeDemoBackground left"
		title="<spring:message code='role.rolePermission'/>" align="center" style="display: none;">
			<ul id="roleTreeID" class="ztree"></ul>
	</div>
	
	
	

</body>

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/zTree3.3/js/jquery.ztree.core-3.5.js"></script>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/zTree3.3/js/jquery.ztree.excheck-3.5.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/zTree3.3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	
</html>