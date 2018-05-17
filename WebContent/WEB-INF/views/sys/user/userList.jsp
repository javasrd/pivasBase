<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp"%>

<html>

<head>
<link
	href="${pageContext.request.contextPath}/assets/sysCss/common/style.css"
	type="text/css" rel="stylesheet" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/zTree3.3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/zTree3.3/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/jquery/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/pivas/js/strings.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/zTree3.3/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript">

	var rolePrivilege = '';
	var currUserId = '${currUserId}';

	var areaList = null ;
	var areaChange = false ;
	var dialogType = null ;
	
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
                "url": "${pageContext.request.contextPath}/sys/user/userlist",
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
	                {"data": "userId", "bSortable": false},
	                {"data": "account", "bSortable": false},
                {"data": "name", "bSortable": false},
                {"data": "gender", "bSortable": false},
                {"data": "locked", "bSortable": false},
                {"data": "telephone", "bSortable": false, "defaultContent":""},
                {"data": "email", "bSortable": false, "defaultContent":""},
                {"data": "description", "bSortable": false, "defaultContent":""},
                {"data": "accountType", "bSortable": false},
            ],
            columnDefs: [
            	 {
                    //   指定第一列，从0开始，0表示第一列，1表示第二列……
                    targets: 0,
                    render: function(data, type, row, meta) {
                        return '<input type="checkbox" style="margin-top:3px;" class="itemchk" name="checklist" value="' + data + '" />';
                    }
                }, 
            	 {
                    targets: 3,
                    render: function(data, type, row, meta) {
                        return data=='0'?'女':'男';
                    }
                }, 
            	 {
                    targets: 4,
                    render: function(data, type, row, meta) {
                        return data=='0'?'<spring:message code="common.no"/>':'<spring:message code="common.yes"/>';
                    }
                }, 
            	 {
                    targets: 8,
                    render: function(data, type, row, meta) {
                        return data=='1'?'药师':(data=='6'?'护士':'');
                    }
                }, 
                {
					targets: 9,
                    "width": "20%",
					render: function (data, type, row) {
						var str = "";
                        <shiro:hasPermission name="PIVAS_BTN_120">
				     		str += "<a class=\"button btn-bg-green\" href='javascript:;' onclick='onEditUser(" + row.userId + ")'>修改</a>";
					 	</shiro:hasPermission>
                     	<shiro:hasPermission name="PIVAS_BTN_123">
                     	 	str += "&nbsp;<a class=\"button btn-bg-green\" href='javascript:;' onclick='onUnlockUser({0}, {1})'>解锁</a>".format([row.userId, row.locked]);
                     	</shiro:hasPermission>
                     	<shiro:hasPermission name="PIVAS_BTN_124">
                     		str +=  '&nbsp;<a class="button btn-bg-green" href="javascript:;" onclick="resetPsw({0},\'{1}\');">重置密码</a>'.format([row.userId, row.account])
                		</shiro:hasPermission>
                     		<shiro:hasPermission name="PIVAS_BTN_122">
                     		str +=  "&nbsp;<a class=\"button btn-bg-green\" href=\"javascript:;\" onclick=\"user_auth({0});\">授权</a>".format([row.userId]);
                    		</shiro:hasPermission>
                     	
                        return str;
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
	
	function onClickNode(event, treeId, treeNode)
	{
	   rolePrivilege = '';
		$.each($.fn.zTree.getZTreeObj("userTree").getCheckedNodes(),function(index, node) {
			rolePrivilege += node.id + ","
		});
	}
	
	var userIds;
	function user_auth(id) {
		var ids = [];
		ids.push(id);
		userIds = ids;
		if(currUserId==ids[0]){
			layer.alert("<spring:message code='user.zijishouquan'/>", {'title': '', icon: 0});
			return;
		}
		
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/sys/user/treeUser',
			dataType : 'json',
			cache : false,
			data : [{name : 'ids', value : ids.toString()}],
			success : function(data) {
				if(data.success == false){
					message({
						data:data
					});
				}else{
					$("#roleUserTreeID" ).dialog( "open" );
					var setting = {
						check: {
							enable: true
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
					$.fn.zTree.init($("#userTree"), setting, data);
				}
				
			}
		});
	}
	
	function resetPsw(id, account) {

		if ('${currUserId}' == id) {
			layer.alert("<spring:message code='user.chongzhidangqian'/>", {'title': '', icon: 2});
			return;
		}
		$("#resetAccount").val(account);
		$("#resetUserId").val(id);
		$('#resetPassword').dialog('open');
	}
	
	function getCheckedId() {
		var arr = new Array(0); 
		$('input[name="checklist"]:checked').each(function(i){
			arr.push(this.value);  
		 });
		
		return arr;
	}

    function onEditUser(userId) {
        $.ajax({
            type : 'POST',
            url : '${pageContext.request.contextPath}/sys/user/initUpdateUser',
            dataType : 'json',
            cache : false,
            data : [ {
                name : 'userId',
                value : userId
            } ],
            success : function(data) {
                if(data.success == false){
                    $(".pReload").click();
                    message({
                        data: data
                    });
                    return;
                }
                else{
                    //将返回的值设置到修改页面的控件中
                    $('#upUserDataID').find(':input').each(function() {
                        //调用解码方法后再设置value
                        $(this).val(sdfun.fn.htmlDecode(data[this.name]));
                    }).end().dialog('open');
                    $("#synDataDicBtn2").val("关联");
                    if($("#upUserDataID [name='userType']").val()==6){
                        $("#synDataDicDiv2").show();
                    }else{
                        $("#synDataDicDiv2").hide();
                    }
                    //清楚修改页面错误提示信息
                    $('#upUserDataID').removeClass( "ui-state-error" );
                }

                $("#upUserDataID [name=glAreaDel]").val("N");
                $("#upUserDataID [name=glAreaAddN]").val("");
                areaList = data.glAreaList;
                dialogType = "update" ;
                $("#upUserDataID" ).dialog( "open" );
            },
            error : function() {
            }
        });
    }

    function onUnlockUser(value, isLock) {
		
        var ids = value;
		if(currUserId == ids){
			message({
				html: "<spring:message code='user.zijijiesuo'/>",
				showConfirm: true
			});
			return;
		}
		if(0== isLock){
			message({
				html: "<spring:message code='user.wuxujiesuo'/>",
				showConfirm: true
			});
			return;
		}
		message({
			html: "<spring:message code='user.querenjiesuo'/>",
			showCancel:true,
			confirm:function(){
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/sys/user/unlock',
					dataType : 'json',
					cache : false,
					data : "userId=" + ids,
					success : function(data) {
						//不管成功还是失败，刷新列表
						$(".pReload").click();
						//弹出提示信息
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
	}
	$(function() {
		
		$("#addDataDicDialogID select").combobox();
		
		sdfun.fn.trimAll('conditionForm,addUserForm,upUserDataFormID');
		var department = "";
		var account = $("#accountID");
		var name = $("#nameID");
		var pwd = $(" #passwordID");
		var password = $("#pwdID");
		var addUnId = $("#unitId_add");
		var addUnIdName = $("#unId_add");
		var depId = $("#depId");
		var email = $("#emailID");
		var teleph = $(" #telephID");
		var description = $(" #description");
		var userType = $(" #userType1");
		
		var allFields = $([]).add(account).add(name).add(pwd).add(password)
				.add(depId).add(addUnId).add(email).add(teleph).add(description).add(addUnIdName).add(userType);

		//新增用户弹出框初始化
		$( "#addDataDicDialogID" ).dialog({
			autoOpen: false,
			height: 490,
			width: 500,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					
					var bValid = true;
					allFields.removeClass("ui-state-error");

					bValid = bValid && checkLength(account, 1, 32);
					bValid = bValid && checkRegexp(account, /^([0-9a-zA-Z_])+$/);
					bValid = bValid && checkLength(name, 1, 32);
					bValid = bValid && checkLength(pwd, '${minLength}', '${maxLength}');
					//密码
					bValid = bValid && checkLength(password, '${minLength}', '${maxLength}');
						
					//电话
					bValid = bValid && checkLength(teleph, 0, 32);
					bValid = bValid && checkRegexp(teleph, /^([0-9-]){0,32}$/);
					
					if(!bValid)
						return;	
					
					var pwdNumber1 = $(" #passwordID").val();
					var pwdNumber = $("#pwdID").val();
					if (pwdNumber1 === pwdNumber)
					{
					}
					else
					{
						message({
			    			html: "<spring:message code='user.mimabuyizhi'/>",
		            		showConfirm: true
		            	});
		            	return;
					}
					//建筑
					if(addUnId.val() == ""){
						$("#unId_add").addClass( "ui-state-error" );
						$("#unId_add").focus();
						bValid = false;
					}
					
					//邮箱
					bValid = bValid && checkEmail($("#emailID"));
					
					bValid = bValid && checkLength(description, 0, 256);

					if ( bValid ) 
					{
						$('#addUserForm').submit();
					}
				},
				"<spring:message code='common.cancel'/>": function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				allFields.val( "" ).removeClass( "ui-state-error" );
			}
		});
		
		$('#addUserForm').ajaxForm({
			   dataType: "json",
	            success : function(data) {
	            	//新增成功，关闭新增弹出框
	            	if(data.success) {
	            		$('#addDataDicDialogID').dialog('close');
			    	}
	            	
	            	//不管成功还是失败，刷新列表
	            	datatable.ajax.reload();
	            
			    	layer.alert("操作成功", {'title': '', icon: 1});
	            	
	             },
	            error : function() {
                  	layer.alert("<spring:message code="common.op.error"/>", {'title': '', icon: 0});
	            },
	    		complete : function(response, status) {
	            
	    		}
		});
		var accountID = $("#accID");
		var empCodeNu = $("#empCodeID");
		var accountName = $("#naID");
		var phoneInfo = $(" #phoneID");
		var faxInfo = $("#faxID");
		var editUnId = $("#unitId_update");
		var editUnName = $("#unitName");
		var emailInfo = $("#email");
		var addressInfo = $("#addressID");
		var descriptionInfo = $(" #descriptionID");
		var allFieldsInfo = $([]).add(accountID).add(empCodeNu).add(accountName)
				.add(phoneInfo).add(faxInfo).add(emailInfo).add(addressInfo)
				.add(descriptionInfo).add(editUnId).add(editUnName);
		//修改用户弹出框初始化
		$( "#upUserDataID" ).dialog({
			autoOpen: false,
			height: 400,
			width: 460,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					
					var unitID = $("#unId_add").val();
					var bValid = true;
					allFields.removeClass("ui-state-error");

					allFieldsInfo.removeClass("ui-state-error");
					//<spring:message code='user.zhanghao'/>
					bValid = bValid && checkLength(accountID, 1, 32);
					bValid = bValid && checkRegexp(accountID, /^([0-9a-zA-Z_])+$/);
					
					bValid = bValid && checkLength(accountName, 1, 32);
					//电话
					bValid = bValid && checkLength(phoneInfo, 0, 32);
					bValid = bValid && checkRegexp(phoneInfo,/^([0-9-]){0,32}$/);
					
					
					//建筑
					if(!bValid)
					return;	
					if(editUnId.val() == ""){
						$("#unitName").addClass( "ui-state-error" );
						$("#unitName").focus();
						bValid = false;
					}
					
					
					//邮箱
					bValid = bValid
					&& checkRegexp(emailInfo,sdfun.constant.reg.email);

					//<spring:message code='user.beizhu'/>
					bValid = bValid && checkLength(descriptionInfo, 0,
									256);
					if ( bValid ) {
						$('#upUserDataFormID').submit();
					}
				},
				"<spring:message code='common.cancel'/>": function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				allFields.val( "" ).removeClass( "ui-state-error" );
				
			}
		});
		
		$('#upUserDataFormID').ajaxForm({
			 dataType: "json",
	            success : function(data) {
	            	//修改成功，关闭新增弹出框
	            	if(data.success || data.code == '<%=ExceptionCodeConstants.RECORD_DELETE%>') {
	            		$('#upUserDataID').dialog('close');
			    		}
	            	
	            	datatable.ajax.reload();
		            
			    	layer.alert("操作成功", {'title': '', icon: 1});
	             },
	            error : function() {
	                  	message({
				            	html: '<spring:message code="common.op.error"/>',
				            	showConfirm: true
				     });

	            },
	    		complete : function(response, status) {
	            
	    		}
	    	});

		$("#parentDicDialog").dialog({
			autoOpen : false,
			height : 450,
			width : 300,
			modal : true,
			resizable : false,
			buttons : {
				"<spring:message code='common.confirm'/>" : function() {
					$(this).dialog("close");
				},
				"<spring:message code='common.cancel'/>" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {

			}
		});

		//新增按钮
		$("#addDataDicBtn").click(function() {
			areaList = null ;
			$("#addDataDicDialogID [name=glAreaDel]").val("");
			$("#addDataDicDialogID [name=glAreaAddN]").val("");
			dialogType = "add";
			$("#addDataDicDialogID").dialog("open");
			resetForm("addUserForm");
			
		});
		
		$("#btn_export").bind("click",function(){
			var params = [];
			
			message({
					html: '<spring:message code="common.exportConfirm"/>',
	    			showCancel:true,
	    			confirm:function(){
						$.ajax({
							type : 'POST', 
							url: '${pageContext.request.contextPath}/sys/user/exportUser',
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
		
		
		// 用户导入
		$( "#btn_import").bind("click",function(){
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
                    url: '${pageContext.request.contextPath}/sys/user/importUser', //用于文件上传的服务器端请求地址
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
					$(".pReload").click();
					$("#importView-div").dialog("close");
				},
				"<spring:message code='common.cancel'/>": function() {
					$(".pReload").click();
					$("#importView-div").dialog("close");
				}
			},
			close: function() {
				resetForm("importView-div");
			}
		});
		
		//删除
		$("#btn_delete" ).click(function() {
		 var ids = getCheckedId();
			if(ids && ids.length > 0) {
				//$("#delUser" ).dialog( "open" );
				message({
	    			html: '<spring:message code="common.deleteConfirm"/>',
	    			showCancel:true,
	    			confirm:function(){
						$.ajax({
							type : 'POST', 
				            url : '${pageContext.request.contextPath}/sys/user/deleteUser',
				            dataType : 'json',  
				            cache : false,
				            data : [{name : 'userId', value : ids}],
				            success : function(data) {
				            	//不管成功还是失败，刷新列表
				            	$(".pReload").click();
				            	//弹出提示信息
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
			}
			else
			{
				layer.alert('<spring:message code="common.plzSelectOneOrMoreRecourd"/>', {'title': '', icon: 2});
			}
		});

		//授权
		$( "#roleUserTreeID" ).dialog({
			autoOpen: false,
			height: 300,
			width: 350,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var idsUser = userIds;
					
					if (idsUser) {
						var ps = new Array();
						var treeObj = $.fn.zTree.getZTreeObj("userTree");
						//获取所有选中节点，不包括禁用
						var nodes = treeObj.getCheckedNodes(true);
						if(nodes && nodes.length >0){
							for(var i=0,j = nodes.length; i < j; i++){
								if(nodes[i].id!=0){
									ps.push(nodes[i].id);
								}
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
						
						$.ajax({
							type : 'POST',
							url : '${pageContext.request.contextPath}/sys/user/addTreeUserRole',
							dataType : 'json',
							contentType:'application/json',
							data : JSON.stringify({pris:ps,roleId:idsUser.toString(),ignorePris:ignorePs}),
							success : function(data) {
								//新增成功，关闭新增弹出框
				            	$('#roleUserTreeID').dialog('close');
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
					}
				},
				"<spring:message code='common.cancel'/>": function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				allFields.val( "" ).removeClass( "ui-state-error" );
			}
		});

		//查询按钮
		$("#searchBtn").click(function (){
			qry();
		});
		
		//重置按钮
		$("#resetBtn").click(function(){
			document.getElementById("conditionForm").reset();
		});

		$("#parentDicName").click(function() {
			$("#parentDicDialog").dialog("open");
		});
		
		//====================start  新增部门树=============
		//弹出部门树
		$("#depId").click(
				function() {
					$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/sys/user/departmentTree',
					dataType : 'json',
					cache : false,
					//data : [{name : 'userId', value : ids}],
					success : function(data) {
						if(data.success==false){
							message({
                               data:data
                            });
						}else{
							var setting = {
								data : {
									simpleData : {
										enable : true
									}
								},
								callback : {
									onClick : onClicknode
								}
							};
							$.fn.zTree.init($("#addDepTree"),
									setting, data);
// 							$(".pReload").click();
							$("#addDepartmentDialog").dialog("open");
						}
					}
				});
				});
		
		//选择部门树节点
		$("#addDepartmentDialog").dialog({
			autoOpen : false,
			height : 450,
			width : 300,
			modal : true,
			resizable : false,
			buttons : {
				"<spring:message code='common.confirm'/>" : function() {
					
					$("#depId").val($("#treeNodeName").val());
					$("#depNodeID").val($("#treeNodeId").val());
					$(this).dialog("close");
				},
				"<spring:message code='common.cancel'/>" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {

			}
		});
		
		function onClicknode(event, treeId, treeNode, clickFlag) {
			//alert(treeId + treeNode.name + treeNode.id);
			//非根节点
			if(treeNode.id != 0){
				$("#treeNodeId").val(treeNode.id);
				$("#treeNodeName").val(sdfun.fn.htmlDecode(treeNode.name));
			}else{
				$("#treeNodeId").val("");
				$("#treeNodeName").val("");
			}
		}
		
		//====================end=============================
		
		//选择建筑树节点
		$("#addUnitDialog").dialog({
			autoOpen : false,
			height : 450,
			width : 300,
			modal : true,
			resizable : false,
			buttons : {
				"<spring:message code='common.confirm'/>" : function() {
					var treeObj = $.fn.zTree.getZTreeObj("addUnitTree");
					//获取所有选中节点，不包括禁用
					var nodes = treeObj.getCheckedNodes(true);
					var nodeIds = "";
					var nodeNames = "";
					if(nodes && nodes.length >0){
						for(var i=0,j = nodes.length; i < j; i++){
							if(nodes[i].id!=1){
								nodeIds+=nodes[i].id+",";
								nodeNames+=sdfun.fn.htmlDecode(nodes[i].name)+",";
							}
						}
					}
					if(nodeIds.length > 0){
						nodeIds =  nodeIds.substring(0,nodeIds.length - 1);
					}
					if(nodeNames.length > 0){
						nodeNames =  nodeNames.substring(0,nodeNames.length - 1);
					}
					$("#unId_add").val(nodeNames);
					$("#unitId_add").val(nodeIds);
					$(this).dialog("close");
				},
				"<spring:message code='common.cancel'/>" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {

			}
		});
		
		//弹建筑门树
		$("#unId_add").click(function() {
					$.ajax({
						type : 'POST',
						url : '${pageContext.request.contextPath}/tree/queryAllArchisByMinLevel',
						dataType : 'json',
						cache : false,
						data : [{name : 'minLevel', value : 1}],
						success : function(data) {
							if(data.success==false){
								message({
	                               data:data
	                            });
							}else{
								var setting = {
									data : {
										simpleData : {
											enable : true
										}
									},
									check:{
										enable : true
										
									},
									callback : {
										onClick : onClickUnit
									}
								};
								var treeObj = $.fn.zTree.init($("#addUnitTree"),
										setting, data);
								treeObj.expandAll(true);		
// 								$(".pReload").click();
								$("#addUnitDialog").dialog("open");
							}
						},
						error : function() {
						}
					});
					
			});
		
		function onClickUnit(event, treeId, treeNode, clickFlag)
		{
			$("#unitNodeId").val(treeNode.id);
			$("#unitNodeName").val(treeNode.name);
		}
		
		//========修改树=====
		
		//弹出部门树
		$("#departmentNu").click(function() {
					$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/sys/user/departmentTree',
					dataType : 'json',
					cache : false,
					success : function(data) {
						if(data.success==false){
								message({
	                               data:data
	                            });
						}else{
							var setting = {
								data : {
									simpleData : {
										enable : true
									}
								},
								callback : {
									onClick : onClicknode
								}
							};
							$.fn.zTree.init($("#depTree"),
									setting, data);
// 							$(".pReload").click();
							$("#departmentDialog").dialog("open");
						}
					},
					error : function() {
					}
				});
		});
		
		//选择部门树节点
		$("#departmentDialog").dialog({
			autoOpen : false,
			height : 450,
			width : 300,
			modal : true,
			resizable : false,
			buttons : {
				"<spring:message code='common.confirm'/>" : function() {
					$("#departmentNu").val($("#treeNodeName").val());
					$("#deparId").val($("#treeNodeId").val());
					$(this).dialog("close");
				},
				"<spring:message code='common.cancel'/>" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {

			}
		});
		
		//弹建筑门树
		$("#unitName").click(function() {
					$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/tree/queryArchisByLevelAndUserId',
					dataType : 'json',
					cache : false,
					data : [{name : 'limitLevel', value : 1},{name : 'userId', value : $("#userUpId").val()}],
					success : function(data) {
						if(data.success==false){
								message({
	                               data:data
	                            });
							}else{
							var setting = {
								data : {
									simpleData : {
										enable : true
									}
								},
								check:{
										enable : true
								},
								callback : {
									onClick : onClickUnit
								}
							};
							var treeObj = $.fn.zTree.init($("#unitTree"), setting, data);
								treeObj.expandAll(true);	
						$("#unitDialog").dialog("open");
					}
					},
					error : function() {
					}
				});
			});
		
		//选择建筑树节点
		$("#unitDialog").dialog({
			autoOpen : false,
			height : 450,
			width : 300,
			modal : true,
			resizable : false,
			buttons : {
				"<spring:message code='common.confirm'/>" : function() {
					var treeObj = $.fn.zTree.getZTreeObj("unitTree");
					//获取所有选中节点，不包括禁用
					var nodes = treeObj.getCheckedNodes(true);
					var nodeIds = "";
					var nodeNames = "";
					if(nodes && nodes.length >0){
						for(var i=0,j = nodes.length; i < j; i++){
							if(nodes[i].id!=1){
								nodeIds+=nodes[i].id+",";
								nodeNames+=nodes[i].name+",";
							}
						}
					}
					
					if(nodeIds.length > 0){
						nodeIds = nodeIds.substring(0,nodeIds.length -1 );
					}
					if(nodeNames.length > 0){
						nodeNames = nodeNames.substring(0,nodeNames.length -1 );
					}
				
					$("#unitName").val(nodeNames);
					$("#unitId_update").val(nodeIds);
					$(this).dialog("close");
				},
				"<spring:message code='common.cancel'/>" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
			}
		});
		
		
		function rowDbclick(r) {
			$(r).dblclick(
			function() {
				//获取该行的所有列数据
				var columnsArray = $(r).attr('ch').split("_FG$SP_");
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/sys/user/userDetail',
					dataType : 'json',
					cache : false,
 					data : [ {name : 'userId',value : columnsArray[0]}
 						   ],
					success : function(data) {
						if(data.success == false){
							message({
								data:data
							});
						}else{
							$('#upUserDataDetailID').find(':input').each(
								function() {
			            			//调用解码方法后再设置value
				            		$(this).val(sdfun.fn.htmlDecode(data[this.name]));
								}).end().dialog('open');
							$('#upUserDataDetailID').removeClass("ui-state-error");
						}

					}
				});
			});
		}
		
		$("#upUserDataDetailID").dialog({
			autoOpen : false,
			height : 500,
			width : 400,
			modal : true,
			resizable : false,
			buttons : {
				'<spring:message code="common.cancel"/>' : function() {
					$(this).dialog("close");
				}
			}
		});
		
		initDatatable();

		$(".chkAllArea").bind("change",function(){
			areaChange = true ;
			if($(this).attr("checked")=="checked"){
				$(".chkOneArea").each(function(){
					$(this).attr("checked","checked");
				});
			}else{
				$(".chkOneArea").each(function(){
					$(this).removeAttr("checked");
				});
			}
		});
		
		$(".chkOneArea").bind("change",function(){
			areaChange = true ;
			var checkAll = true ;
			$(".chkOneArea").each(function(){
				if($(this).attr("checked")==undefined){
					checkAll = false ;
				}
			});
			if(checkAll){
				$(".chkAllArea").attr("checked","checked");
			}else{
				$(".chkAllArea").removeAttr("checked");
			}
		});
		
		$("#divSynToOther").dialog({
			autoOpen: false,
			width: 560,
			height: 400,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					if(dialogType=="add"){
						if(areaChange){
							$("#addDataDicDialogID [name=glAreaDel]").val("Y");
							var areaCodeN = "" ;
							$(".chkOneArea").each(function(){
								if($(this).attr("checked")=="checked"){
									areaCodeN = areaCodeN + $(this).attr("deptcode") + ",";
								}
							});
							$("#addDataDicDialogID [name=glAreaAddN]").val(areaCodeN);
						}else{
							$("#addDataDicDialogID [name=glAreaDel]").val("N");
							$("#addDataDicDialogID [name=glAreaAddN]").val("");
						}
					}else{
						if(areaChange){
							$("#upUserDataID [name=glAreaDel]").val("Y");
							var areaCodeN = "" ;
							$(".chkOneArea").each(function(){
								if($(this).attr("checked")=="checked"){
									areaCodeN = areaCodeN + $(this).attr("deptcode") + ",";
								}
							});
							$("#upUserDataID [name=glAreaAddN]").val(areaCodeN);
						}else{
							$("#upUserDataID [name=glAreaDel]").val("N");
							$("#upUserDataID [name=glAreaAddN]").val("");
						}
					}
					$(this).dialog("close");
				},
				"<spring:message code='common.cancel'/>": function() {
					$(this).dialog("close");
				}
			},
			close: function() {
				
			}
		});
	
		$("#synDataDicBtn").bind("click",function(){
			areaChange = false ; 
			$(".chkAllArea").removeAttr("checked");
			$(".chkOneArea").each(function(){
				$(this).removeAttr("checked");
			});
			$("#divSynToOther").dialog("open");
		});

		$("#synDataDicBtn2").bind("click",function(){
			areaChange = false ; 
			$(".chkAllArea").removeAttr("checked");
			$(".chkOneArea").each(function(){
				$(this).removeAttr("checked");
			});
			if(areaList && areaList.length>0){
				for(var row in areaList){
					$("#dept_"+areaList[row].deptCode).attr("checked","checked");
				}
			}
			$("#divSynToOther").dialog("open");
		});
		
		qry();
	});
	function userTypeChange(obj){
		if($(obj).val()==6){//护士
			$("#synDataDicDiv").show();
		}else{
			$("#synDataDicDiv").hide();
		}
	}
	function userTypeChange2(obj){
		if($(obj).val()==6){//护士
			$("#synDataDicDiv2").show();
		}else{
			$("#synDataDicDiv2").hide();
		}
	}
	
	function qry() {
		$("#tbSearch input:text").each(function() {
			if ($(this).attr("name")) {
				ajax_params[$(this).attr("name")] = $(this).val();
			}
		});
		
		datatable.ajax.reload();
	}
	
	function addLine(){
		var username = '' ;
		var lastObj = null ;
		$("#datatable").find('tr').each(function(){
			if(username!='' && username!=$(this).find(".checkSame").val()){
				$(lastObj).css("border-bottom","1.5px solid #cbcbcb");
			}
			username = $(this).find(".checkSame").val();
			lastObj =  $(this);
		});
		$(lastObj).css("border-bottom","1.5px solid #cbcbcb");
	}
</script>
</head>

<body style="overflow: auto;">

<div id="searchDiv">
	<div data-qryMethod class="ui-search-header ui-search-box" id="tbSearch" style="display: inline; ">
		<div style="float:right ">
			<input placeholder="账号" name="accounts" size="8" data-cnd="true">&nbsp;&nbsp;
			<input placeholder="用户名称" name="names" size="8" data-cnd="true">&nbsp;&nbsp;
			<input placeholder="联系电话" name="telephones" size="8" data-cnd="true">&nbsp;&nbsp;
			<button class="button ui-search-btn ui-btn-bg-green" onclick="qry()"><i class="am-icon-search"></i><span>搜索</span></button>
			<button class="button ui-search-btn ui-btn-bg-yellow" onclick='$("#tbSearch input:text").val("");'><i class="am-icon-trash"></i><span>清空</span></button>
		</div>
		<shiro:hasPermission name="PIVAS_BTN_119">
			<a class="button ui-search-btn ui-btn-bg-green" id="addDataDicBtn"><i class="am-icon-plus"></i><span><spring:message code='common.add' /></span></a>
		</shiro:hasPermission>
        <a class="button ui-search-btn ui-btn-bg-green" id="btn_export"><i class="am-icon-arrow-up"></i><span><spring:message code='common.export' /></span></a>
		<a class="button ui-search-btn ui-btn-bg-green" id="btn_import"><i class="am-icon-arrow-down"></i><span><spring:message code='common.import' /></span></a>
	</div>
</div>
<table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
	<thead>
	<tr>
		<th>
			<input style="margin-top:2px;" type="checkbox" onclick="chkAll(this);" title="全选" class="noborder">						
		</th>
		<th>账号</th>
		<th>用户名称</th>
		<th>性别</th>
		<th>锁定</th>
		<th>联系电话</th>
		<th>电子邮箱</th>
		<th>备注</th>
		<th>用户类型</th>
		<th>操作</th>
	</tr>
	</thead>
</table>

	<%-- 导入用户数据弹出框 --%>
	<div id="importView-div" title="导入用户" align="left"
		style="display: none;">
		<form id="editView-form" action="" method="post">
			<div class="popup">
				<div class="row">
					<div class="column">
						<label class="tit">用户：</label> <input type="file" id="file"
							name="file"> <a class="button" id="import-submit"><spring:message
								code='common.import' /></a>
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

	<%-- 新增弹出框 --%>
	<div id="addDataDicDialogID"
		title="<spring:message code='user.xinzeng'/>" align="center"
		style="display: none;">
		<form id="addUserForm"
			action="${pageContext.request.contextPath}/sys/user/addUser"
			method="post">
			<div class="popup">
				<div class="row">
					<div class="column">
						<label class="tit"><spring:message code='user.zhanghao' /></label>
						<input type="text" name="account" id="accountID" maxlength="32"
							title="<spring:message code='common.op.remind1'/>" /> <span
							class="mand">*</span>
					</div>
					<div class="column">
						<label class="tit"><spring:message code='user.yonghuming' /></label>
						<input type="text" name="name" id="nameID" maxlength="32"
							title="<spring:message code='common.op.remind2'/>" /> <span
							class="mand">*</span>
					</div>
				</div>
				<div class="row">
					<div class="column">
						<label class="tit"><spring:message code='user.sex' /></label> <select
							name="gender" empty="false" style="width: 184px;">
							<option value="1"><spring:message code="user.sex.man" /></option>
							<option value="0"><spring:message code="user.sex.woman" /></option>
						</select> <span class="mand">*</span>
					</div>
					<div class="column">
						<label class="tit"><spring:message code='user.mima' /></label> <input
							type="password" name="password" id="passwordID"
							title="<spring:message code='user.mimajiaoyan'/>"
							maxlength="${maxLength}" /> <span class="mand">*</span>
					</div>
				</div>
				<div class="row">
					<div class="column">
						<label class="tit"><spring:message code='user.querenmima' /></label>
						<input type="password" name="pwdName" id="pwdID"
							title="<spring:message code='user.querenmimatishi'/>"
							maxlength="${maxLength}" /> <span class="mand">*</span>
					</div>
					<div class="column">
						<label class="tit"><spring:message code='user.dianhua' /></label>
						<input type="text" name="telephone" id="telephID" maxlength="32"
							title="<spring:message code='user.dianhuajiaoyan'/>" /> <span
							class="mand"></span>
					</div>
				</div>
				<div class="row">
					<div class="column">
						<label class="tit"><spring:message code='user.youxiang' /></label>
						<input type="text" name="email" id="emailID" maxlength="128"
							title="<spring:message code='user.youxiangjiaoyan'/>" />
					</div>
					<div class="column">
						<label class="tit"><spring:message code='common.remark' /></label>
						<textarea name="description" id="description" maxlength="256"
							title="<spring:message code='common.op.remind6'/>"
							style="resize: none"></textarea>
						<span class="mand"></span>
					</div>
				</div>

				<div class="row">
					<div class="column">
						<label class="tit">用户类型</label> <select id="userType1"
							name="userType" onchange="userTypeChange(this)">
							<option value="0">--请选择--</option>
							<option value="1">药师</option>
							<option value="6">护士</option>
						</select>
					</div>
					<div class="column" id="synDataDicDiv" style="display: none">
						<label class="tit">关联病区</label> <input type="button"
							id="synDataDicBtn" value="关联"> <span class="mand"></span>
					</div>
				</div>


			</div>
			<input type="hidden" name="sickRoomIds" value="1,2" /> <input
				type="hidden" name="glAreaDel" value="N" /> <input type="hidden"
				name="glAreaAddN" value="" />
		</form>
	</div>

	<!-- 修改 -->
	<div id="upUserDataID"
		title="<spring:message code='user.xiugaiyonghu'/>" align="center"
		style="display: none;">
		<form id="upUserDataFormID"
			action="${pageContext.request.contextPath}/sys/user/updateUser"
			method="post">
			<input type="hidden" id="userUpId" name="userId" /> <input
				type="hidden" name="glAreaDel" value="N" /> <input type="hidden"
				name="glAreaAddN" value="" />
			<div class="popup">
				<div class="row">
					<div class="column">
						<label class="tit"><spring:message code='user.zhanghao' /></label>
						<input type="text" name="account" id="accID" maxlength="32"
							title="<spring:message code='common.op.remind1'/>"
							disabled="disabled" /> <span class="mand">*</span>
					</div>
					<div class="column">
						<label class="tit"><spring:message code='user.yonghuming' /></label>
						<input type="text" name="name" id="naID" value="" maxlength="32"
							title="<spring:message code='common.op.remind2'/>" /> <span
							class="mand">*</span>
					</div>
				</div>
				<div class="row">
					<!--
			<div class="column">
				<label class="tit"><spring:message code='user.sex'/></label>
				<select name="gender" empty="false">
					<option value="1"><spring:message code="user.sex.man"/></option>
            		<option value="0"><spring:message code="user.sex.woman"/></option>
            	</select>
				<span class="mand">*</span>
			</div> -->
					<div class="column">
						<label class="tit"><spring:message code='user.dianhua' /></label>
						<input type="text" name="telephone" id="phoneID" value=""
							maxlength="32"
							title="<spring:message code='user.dianhuajiaoyan'/>" /> <span
							class="mand"></span>
					</div>
					<div class="column">
						<label class="tit"><spring:message code='user.youxiang' /></label>
						<input type="text" name="email" id="email" value=""
							maxlength="128"
							title="<spring:message code='user.youxiangjiaoyan'/>" />
					</div>
				</div>
				<div class="row">
					<div class="column">
						<label class="tit"><spring:message code='common.remark' /></label>
						<textarea name="description" id="descriptionID" maxlength="256"
							title="<spring:message code='common.op.remind6'/>"></textarea>
					</div>
				</div>

				<div class="row">
					<div class="column">
						<label class="tit">用户类型</label> <select id="userType2"
							name="userType" onchange="userTypeChange2(this)">
							<option value="0">--请选择--</option>
							<option value="1">药师</option>
							<option value="6">护士</option>
						</select>
					</div>
					<div class="column" id="synDataDicDiv2" style="display: none">
						<label class="tit">关联病区</label> <input type="button"
							id="synDataDicBtn2" value="关联"> <span class="mand"></span>
					</div>
				</div>

			</div>
		</form>
	</div>


	<!-- 详情 -->
	<div id="upUserDataDetailID"
		title="<spring:message code='user.yonghuxiangqing'/>" align="center"
		style="display: none;">
		<form id="upUserDataDetailID" method="post">
			<div class="layeForm">
				<table>
					<%-- <spring:message code='user.zhanghao'/> --%>
					<tr>
						<td class="text" style="width: 17%;"><spring:message
								code='user.zhanghao' /></td>
						<td class="in" style="width: 29%;"><input type="hidden"
							id="userId" name="userId" /> <input type="text" name="account"
							id="accountID" disabled="disabled" /></td>
						<td class="mand"></td>
					</tr>
					<%-- <spring:message code='user.yonghuming'/> --%>
					<tr>
						<td class="text" style="width: 17%;"><spring:message
								code='user.yonghuming' /></td>
						<td class="in" style="width: 29%;"><input type="text"
							name="name" id="nameID" disabled="disabled" /></td>
						<td class="mand"></td>
					</tr>

					<%-- 电话--%>
					<tr>
						<td class="text" style="width: 17%;"><spring:message
								code='user.dianhua' /></td>
						<td class="in" style="width: 29%;"><input type="text"
							name="telephone" id="telephID" disabled="disabled" /></td>
						<td class="mand"></td>
					</tr>

					<%-- 电子邮箱--%>
					<tr>
						<td class="text" style="width: 17%;"><spring:message
								code='user.youxiang' /></td>
						<td class="in" style="width: 29%;"><input type="text"
							name="email" id="emailID" disabled="disabled" /></td>
					</tr>


					<%-- <spring:message code='user.beizhu'/> --%>
					<tr>
						<td class="text tbox" style="width: 17%;"><spring:message
								code='common.remark' /></td>
						<td class="in ibox" style="width: 29%;"><textarea
								name="description" maxlength="256" readonly="readonly"></textarea>
						</td>
						<td class="mand"></td>
					</tr>
				</table>
			</div>
		</form>
	</div>

	<%-- 部门弹出框 --%>
	<div id="departmentDialog" class="zTreeDemoBackground left"
		title="<spring:message code='user.bumen'/>" align="center"
		style="display: none;">
		<ul id="depTree" class="ztree"></ul>
	</div>

	<%-- 建筑弹出框 --%>
	<div id="unitDialog" class="zTreeDemoBackground left"
		title="<spring:message code='user.jianzhu'/>" align="center"
		style="display: none;">
		<ul id="unitTree" class="ztree"></ul>
	</div>

	<%-- 部门弹出框  添加 --%>
	<div id="addDepartmentDialog" class="zTreeDemoBackground left"
		title="<spring:message code='user.bumen'/>" align="center"
		style="display: none;">
		<ul id="addDepTree" class="ztree"></ul>
	</div>
	<%-- 建筑弹出框  新增--%>
	<div id="addUnitDialog" class="zTreeDemoBackground left"
		title="<spring:message code='user.jianzhu'/>" align="center"
		style="display: none;">
		<ul id="addUnitTree" class="ztree"></ul>
	</div>

	<input id="treeNodeId" name="treeNodeId" type="hidden" />
	<input id="treeNodeName" name="treeNodeName" type="hidden" />

	<input id="unitNodeId" name="unitNodeId" type="hidden" />
	<input id="unitNodeName" name="unitNodeName" type="hidden" />


	<!-- 记录当前存在用户的id -->
	<input id=user_id type="hidden"
		value="<shiro:principal property="userId"/>">

	<!-- 用户授权 -->
	<div id="roleUserTreeID" align="center"
		title="<spring:message code='user.yonghushouquan'/>"
		style="display: none;">
		<ul id="userTree" class="ztree"></ul>
	</div>

	<!-- 用户加组 -->
	<div id="userAddGroup" align="center"
		title="用户加组" style="display: none;">
		<ul id="addGroupTree" class="ztree"></ul>
	</div>

	<!-- 密码重置弹出框 -->
	<div id="resetPassword"
		title="<spring:message code='user.mimachongzhi'/>" align="center"
		style="display: none;">
		<form id="resetPasswordForm"
			action="${pageContext.request.contextPath}/sys/user/resetUpdatePassword"
			method="post">
			<input type="hidden" name="userId" id="resetUserId">
			<div class="popup">
				<div class="row">
					<div class="column">
						<label class="tit"><spring:message code='user.zhanghao' /></label>
						<input type="text" name="account" id="resetAccount"
							disabled="disabled" /> <span class="mand">*</span>
					</div>
					<div class="column">
						<label class="tit"><spring:message code='user.xinmima' /></label>
						<input type="password" name="password" id="newPwd"
							title="<spring:message code='user.mimajiaoyan'/>"
							maxlength="${maxLength}" /> <span class="mand">*</span>
					</div>
				</div>
				<div class="row">
					<div class="column">
						<label class="tit"><spring:message code='user.querenmima' /></label>
						<input type="password" name="newPwd1" id="newPwd1"
							title="<spring:message code='user.querenmimatishi'/>"
							maxlength="${maxLength}" /> <span class="mand">*</span>
					</div>
				</div>
			</div>
		</form>
	</div>




	<%-- 新增编辑弹出框 --%>
	<div id="divSynToOther" title="选择病区" align="center"
		style="display: none;">
		<table style="width: 100%;">
			<tr style="height: 35px;">
				<td style="width: 10%; text-align: right;"><input
					type="checkbox" class="chkAllArea"></td>
				<td style="width: 40%;">全部</td>
				<c:forEach items="${inpAreaList}" var="area" varStatus="rowStatus">
					<c:if test="${rowStatus.index==0}">
						<td style="width: 10%; text-align: right;"><input
							type="checkbox" class="chkOneArea" id="dept_${area.deptCode}"
							value="${area.deptCode}" deptcode="${area.deptCode}"
							deptname="${area.deptName}"></td>
						<td style="width: 40%;">${area.deptName}</td>
					</c:if>
				</c:forEach>
			</tr>

			<c:forEach items="${inpAreaList}" var="area" varStatus="rowStatus">
				<c:if test="${rowStatus.index>0}">

					<c:if test="${rowStatus.index%2==1}">
						<tr style="height: 35px;">
					</c:if>

					<td style="width: 10%; text-align: right;"><input
						type="checkbox" class="chkOneArea" id="dept_${area.deptCode}"
						value="${area.deptCode}" deptcode="${area.deptCode}"
						deptname="${area.deptName}"></td>
					<td style="width: 40%;">${area.deptName}</td>

					<c:if test="${rowStatus.index%2==0}">
						</tr>
					</c:if>

				</c:if>
			</c:forEach>

		</table>
	</div>


	<script type="text/javascript">
			$(function(){
				$("#aSearch").bind("click",function(){
					qry();
				});
				
				var newPwd = $("#newPwd");
				var newPwd1 = $("#newPwd1");
				var	allFields = $( [] ).add(newPwd).add(newPwd1);
				$( "#resetPassword" ).dialog({
					autoOpen: false,
					height: 250,
					width: 400,
					modal: true,
					resizable: false,
					buttons: {
						"<spring:message code='common.confirm'/>": function() {
							var bValid = true;
							allFields.removeClass( "ui-state-error" );
							bValid = bValid && checkLength(newPwd, '${minLength}', '${maxLength}');
							bValid = bValid && checkLength(newPwd1, '${minLength}', '${maxLength}');
							
							if ( !bValid ) {
								return;
							}
							
							if(newPwd.val()!=newPwd1.val()){
								layer.alert('<spring:message code='user.mimabuyizhi'/>', {'title': '', icon: 2});
				            	return;
							}
							
							if ( bValid ) {
								$('#resetPasswordForm').submit();
							}
						},
						"<spring:message code='common.cancel'/>": function() {
							$( this ).dialog( "close" );
						}
					},
					close: function() {
						allFields.val( "" ).removeClass( "ui-state-error" );
						
					}
				});
				
				
				$('#resetPasswordForm').ajaxForm({
				   	dataType: "json",
		            success : function(data) {
		            	//更新成功，关闭新增弹出框
		            	if(data.results == "sendMailClose")
		            	{
		            		$('#resetPassword').dialog('close');
	            			layer.alert("<spring:message code='common.op.success'/>", {'title': '', icon: 1});
		            	}
		            	else if(data.success){
		            		$('#resetPassword').dialog('close');
	            			layer.alert("<spring:message code='user.chongzhimimachenggong'/>", {'title': '', icon: 1});
		            	}else{
		            		if(data.success==false && data.code === '<%=ExceptionCodeConstants.MAIL_SEND_ERROR%>') {
											$('#resetPassword').dialog('close');
										}
										message({
											data : data
										});
									}

								},
								error : function() {
									layer.alert('<spring:message code="common.op.error"/>', {'title': '', icon: 2});

								},
								complete : function(response, status) {

								}
							});

			//密码重置
			$("#reset_password").click(function() {
								var ids = getCheckedId();

								if (ids && ids.length == 1) {
									if ('${currUserId}' == ids[0]) {
										layer.alert("<spring:message code='user.chongzhidangqian'/>", {'title': '', icon: 2});
										return;
									}
									var account = $('input[name="checklist"]:checked').parent().next().text();
									$("#resetAccount").val(account);
									$("#resetUserId").val(ids[0]);
									$('#resetPassword').dialog('open');
								} else {
									//提示请选择一条记录
									layer.alert('<spring:message code="common.plzSelectOneRecourd"/>', {'title': '', icon: 0});
									return;
								}

							});
			
			function onClickNodeGroup(event, treeId, treeNode) {
				rolePrivilege = '';
				$.each(
						$.fn.zTree.getZTreeObj("addGroupTree")
								.getCheckedNodes(), function(index, node) {
							rolePrivilege += node.id + ","
						});
			}
			//加组
			$("#addGroup").click(function() {
								var ids = getCheckedId(); 
								if (ids && ids.length == 1) {
									$.ajax({type : 'POST',
												url : '${pageContext.request.contextPath}/sys/userGroup/queryPartGroupInfo',
												dataType : 'json',
												cache : false,
												data : [ {
													name : 'userId',
													value : ids
												} ],
												success : function(data) {
													if (data.success == false) {
														message({
															data : data
														});
													} else {
														$("#userAddGroup").dialog("open");
														var setting = {
															check : {
																enable : true
															},
															data : {
																simpleData : {
																	enable : true
																}
															},
															callback : {
																onCheck : onClickNodeGroup
															}
														};
														$.fn.zTree
																.init(
																		$("#addGroupTree"),
																		setting,
																		data);
													}

												}
											});
								} else {
									//提示请选择一条记录
									message({
										html : '<spring:message code="common.plzSelectOneRecourd"/>',
										showConfirm : true
									});
								}
							});
			$("#userAddGroup")
					.dialog(
							{
								autoOpen : false,
								height : 500,
								width : 350,
								modal : true,
								resizable : false,
								buttons : {
									"<spring:message code='common.confirm'/>" : function() {
										var idsUser = getFlexiGridSelectedRowText(
												$("#datatable"), 2);
										if (idsUser && idsUser.length == 1) {
											var ps = new Array();
											var treeObj = $.fn.zTree
													.getZTreeObj("addGroupTree");
											//获取所有选中节点，不包括禁用
											var nodes = treeObj
													.getCheckedNodes(true);
											if (nodes && nodes.length > 0) {
												for (var i = 0, j = nodes.length; i < j; i++) {
													if (nodes[i].id != 0) {
														ps.push(nodes[i].id);
													}
												}
											}
											//获取禁用并选中节点
											var ignoreNodes = treeObj
													.getNodesByFilter(function(
															node) {
														return (node.chkDisabled == true && node.checked == true);
													});
											var ignorePs = null;
											if (ignoreNodes
													&& ignoreNodes.length > 0) {
												ignorePs = new Array();
												$.each(ignoreNodes, function(i,
														j) {
													ignorePs.push(j.id);
												});
											}
											$
													.ajax({
														type : 'POST',
														url : '${pageContext.request.contextPath}/sys/userGroup/addGroupAndUser',
														dataType : 'json',
														contentType : 'application/json',
														data : JSON
																.stringify({
																	demandGroup : ps,
																	userId : idsUser[0],
																	cannotAddGroup : ignorePs
																}),
														success : function(data) {
															$('#userAddGroup')
																	.dialog(
																			'close');
															//不管成功还是失败，刷新列表
															$(".pReload")
																	.click();
															//弹出提示信息
															message({
																data : data
															});

														},
														error : function() {
														}
													});
										}
									},
									"<spring:message code='common.cancel'/>" : function() {
										$(this).dialog("close");
									}
								},
								close : function() {
									allFields.val("").removeClass(
											"ui-state-error");

								}
							});
			//密码长度
			var minLength = '${minLength}';
			var maxLength = '${maxLength}';
			var pswdMsg = "<spring:message code='user.mimajiaoyan'/>".replace(
					'a', minLength).replace('b', maxLength);
			$("#newPwd").attr("title", pswdMsg);
			$("#passwordID").attr("title", pswdMsg);
		});
	</script>
</body>
</html>