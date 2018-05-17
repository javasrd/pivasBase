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

	var resource = '${resource}';
	
	var canOpr = false;
	if ('1' == resource) {
		canOpr = true;
	}

	$(function() {

		/*JQuery 限制文本框只能输入数字和小数点*/
		$(".NumDecText").keyup(function() {
			$(this).val($(this).val().replace(/[^0-9.]/g, ''));
		}).bind("paste", function() {
			//CTR+V事件处理
			$(this).val($(this).val().replace(/[^0-9.]/g, ''));
		}).css("ime-mode", "disabled"); //CSS设置输入法不可用

		//
		$(document).ready(function() {
		    //$("#tabGrid tbody tr td input").addClass('disabled');
			//$("#tabGrid tbody tr td input").attr('disabled', true);
			$("#tabGrid tbody tr td span").addClass('noneEditable');

			//
			tabGrid = $('#tabGrid').mmGrid(
			        {
						url : '${contextPath}/user/getPersonnels',
						params : {},
						method : 'post',
						autoLoad : true,
						height : 397, /*10行 如果有边框高度为407，如果去掉边框，高度为397*/
						//sortName: 'postName',//默认排序的字段名
						//sortStatus: 'asc',
						remoteSort : true,
						multiSelect : true,
						checkCol : canOpr,
						fullWidthRows : true,
						plugins : [ $('#tabGrid').next().mmPaginator({}) ],
						cols : [
								{
									title : '人员ID',
									name : 'userId',
									width : 100,
									align : 'center',
									sortable : false,
									type : 'number',
									hidden : true
								},
								{
									title : '姓名',
									name : 'userName',
									width : 100,
									align : 'center',
									sortable : false, renderer: function(val,row){
										return val.replace(/\</g,"&lt;");
										}
								},
								{
									title : '参与排班',
									name : 'partake',
									width : 100,
									align : 'center',
									sortable : false,
									type : 'checkbox',
									renderer : function(val, row) {
										if (val == '1') {
											//勾选
											return '<input type="checkbox" title="" checked="checked" name="status" onchange="changePartake('
													+ row.userId + ', this)" ></input>';
										} else if (val == '0') {
											//非勾选
											return '<input type="checkbox" title="" name="status" onchange="changePartake('
													+ row.userId + ', this)" ></input>';
										}
									}
								},
								{
									title : 'email',
									name : 'email',
									width : 100,
									align : 'center',
									sortable : false, renderer: function(val,row){
										if (val && val.length > 0) {
											return val.replace(/\</g,"&lt;");
										} else {
											return val;
										}
									}
								},
								{
									title : '电话',
									name : 'tellphone',
									width : 100,
									align : 'center',
									sortable : false
								},
								/* { title:'账号状态', name:'accountStatus' ,width:100, align:'center', sortable: false, renderer: function(val,row){
															    if (val == '0') {
															        return '未创建';
															    } else if (val == '1') {	
															        return '创建';
															    } 
															}
								}, */
								{
									title : '操作',
									width : 100,
									align : 'center',
									sortable : false,
									hidden: !canOpr,
									renderer : function( val, row) {
										if (resource == '1') {
											return '<span style=" cursor: pointer;" class="noneEditable glyphicon glyphicon-pencil" aria-hidden="true" onclick="javascript:edit('
													+ row.userId + ')"></span>&nbsp;<span style=" cursor: pointer;" class="noneEditable glyphicon glyphicon-trash" aria-hidden="true" onclick="javascript:deleteUser('
													+ row.userId + ')"></span>';
											} else {
												return '';
											}
										}
								} ]
							});

							//批量删除事件
							$("#btDel").bind('click',function() {

								//获得checkbox选择的列
								var userids = tabGrid.selectedValsByName('userId');

								// 为空提示
								if (null != userids && "" != userids) {

									//提示信息
									$.alert("是否删除所选记录?", {
										"cancleShow" : true,
										"config" : function() {
											var userIds = "";
											for (var i = 0; i < userids.length; i++) {
												userIds = userIds + userids[i] + ",";
											}
											
											userIds = userIds.substring(0, userIds.length - 1);
											
											$.ajaxFPostJson("${contextPath}/user/del",
											        {'userIds' : userIds },
													{
														"success" : function(response) {
															if (true == response) {
																$.alert2("删除成功");
																qryList();
															}
															/* if (response.code && response.code > 0) {
																qryList();
															}
															$.alert2(response.mess); */
														},
														"error" : function(errmess) {
															$.alert2("服务异常["+ errmess+ "]，请联系管理员");
														}
													});
										}
									});
								} else {
									$.alert2("请选择一条记录!");
								}
							});
						});

		$('#btAdd').click(function() {
			$('#opr').val("add");
			$('#userName').val('');
			$('#partake1').prop('checked', 'checked');
			$('#email').val('');
			$('#tellphone').val('');
			$('.modal-header-h3').html('添加人员');
			$('#addPersonnel').modal();
		});

		$('#btBatchAdd').click(function() {
			var html = '<tr><td style="text-align: center; width: 60px;">1</td>' 
				+ '<td style="width: 80px;"><input id="name" name="name" style="width: 70px; margin: 3px" type="text" value="" maxlength="32"></td>'
				+ '<td style="width: 100px; text-align: center;"><input checked="checked" id="partake" name="partake" type="checkbox" /></td>'
				+ '<td style="width: 120px;"><input autocomplete="off" id="email" name="email" style="width: 120px; margin: 3px" type="text" value="" maxlength="128"></td>'
				+ '<td style="width: 120px;"><input autocomplete="off" id="tellphone" name="tellphone" style="margin: 3px" type="text" value="" maxlength="32"></td></tr>';
			$('#batchAddTable > tbody').html(html);
			$('#batchAddPersonnel').modal();
		});

		$('#addnewone').click(function() {
			var html = '<tr><td style="text-align: center; width: 60px;">' + ($('#batchAddTable > tbody > tr').size() + 1) + '</td>'
				+ '<td style="width: 80px;"><input id="name" name="name" style="width: 70px; margin: 3px" type="text" value="" maxlength="32"></td>'
				+ '<td style="width: 100px; text-align: center;"><input checked="checked" id="partake" name="partake" type="checkbox" /></td>'
				+ '<td style="width: 120px;"><input autocomplete="off" id="email" name="email" style="width: 120px; margin: 3px" type="text" value="" maxlength="128"></td>'
				+ '<td style="width: 120px;"><input autocomplete="off" id="tellphone" name="tellphone" style="margin: 3px" type="text" value="" maxlength="32"></td></tr>';
			$('#batchAddTable > tbody').append(html);
		});
		
		$('#removeone').click(function() {
			if ($('#batchAddTable > tbody > tr').size() > 1) {
				$('#batchAddTable > tbody > tr:last ').remove();
			} else {
				$.alert("必须要留一条数据！");
			}
		});
		
		$('#btBatchSave').click(function() {
			var dataArray = [];
			var flag = 1;
			$('#batchAddTable > tbody > tr').each(function(index) {
				var data = {};
				
				var obj = $(this);
				var name = $.trim(obj.find('#name').val());
				if (name.length == 0) {
					$.alert("序号为" + (index + 1) + "的姓名为空！");
					flag = 0;
					return false;
				} else if (!checkLength(obj.find('#name'), 1, 32)) {
					$.alert('序号为' + (index + 1) + '的姓名长度不能大于32！');
					flag = 0;
					return false;
				}
				var partake = '1';
				if (!obj.find('#partake')[0].checked) {
					partake = '0';
				}
				var email = $.trim(obj.find('#email').val());
				if (email.length == 0) {
					$.alert("序号为" + (index + 1) + "的email不能为空！");
					flag = 0;
					return false;
				} else if (!isEmail(email)) {
					$.alert("序号为" + (index + 1) + "的email格式不正确！");
					flag = 0;
					return false;
				}
				var tellphone = $.trim(obj.find('#tellphone').val());
				if (tellphone.length == 0) {
					$.alert("序号为" + (index + 1) + "的电话不能为空！");
					flag = 0;
					return false;
				} else if (!isTellphone(tellphone)) {
					$.alert("序号为" + (index + 1) + "的电话只能为纯数字！");
					flag = 0;
					return false;
				}
				data = {"userName": name, "partake": partake, "email": email, "tellphone": tellphone};
				dataArray.push(data);
			});
			if (flag == 1) {
				$.ajaxFPostJson("${contextPath}/user/batchSave",
						{"json": JSON.stringify(dataArray)}, {

					"success" : function(response) {
						if (response.code && response.code > 0) {
							qryList();
							$('#batchAddPersonnel').modal('hide');
					}
					$.alert(response.mess);
					},
						"error" : function(errmess) {
					$.alert("服务异常[" + errmess + "]，请联系管理员");
					}
				});	
			}
		});

		$('#btSave').click(
				function() {
					var userName = $.trim($('#userName').val());
					if (userName.length == 0) {
						$.alert('姓名不能为空！');
						return;
					} else if (!checkLength($('#userName'), 1, 32)) {
						$.alert('姓名长度不能大于32！');
						return;
					}
					$('#userName').val(userName)

					var email = $.trim($('#email').val());
					if (email.length == 0) {
						$.alert('email不能为空！');
						return;
					} else if (!isEmail(email)) {
						$.alert('email格式不正确！');
						return;
					} else if (!checkLength($('#email'), 1, 128)) {
						$.alert('email长度不能大于128！');
						return;
					}
					$('#email').val(email)

					var tellphone = $.trim($('#tellphone').val());
					if (tellphone.length == 0) {
						$.alert('电话不能为空！');
						return;
					} else if (!isTellphone(tellphone)) {
						$.alert('电话只能为纯数字！');
						return;
					} else if (!checkLength($('#tellphone'), 1, 32)) {
						$.alert('电话长度不能大于32！');
						return;
					}
					$('#tellphone').val(tellphone)

					$.ajaxFPostJson("${contextPath}/user/save", $(
							"#formInfo").serialize(), {

						"success" : function(response) {
							if (response.code && response.code > 0) {
								qryList();
								$('#addPersonnel').modal('hide');
							}
							$.alert(response.mess);
						},
						"error" : function(errmess) {
							$.alert("服务异常[" + errmess + "]，请联系管理员");
						}
					});
				});

	});

	//查询
	function qryList(page) {
		var params = {
			"userName" : $("#qryStr").val()
		};
		if (page)
			params['page'] = page;
		tabGrid.loadInit(params);
	}

	//删除用户
	function deleteUser(userId) {
	    
		$.alert("是否删除所选记录?", {
			"cancleShow" : true,
			"config" : function() {
				$.ajaxFPostJson("${contextPath}/user/del", {
					'userIds' : userId
				}, {
					"success" : function(response) {
					    if (true == response) {
					        $.alert2("删除成功");
					        qryList();
					    }
						/* $.alert(response.mess);
						if (response.code && response.code > 0) {
							qryList();
						} */
					},
					"error" : function(errmess) {
						$.alert("服务异常[" + errmess + "]，请联系管理员");
					}
				});
			}
		});
	}

	function edit(userId) {
		$.ajaxFPostJson("${contextPath}/user/getById", {
			'userId' : userId,
			'_r' : new Date().getTime()
		}, {
			"success" : function(response) {
				if (response.code && response.code > 0) {
					$('#opr').val("update");
					$('#userId').val(response.data.userId);
					$('#userName').val(response.data.userName);
					if (response.data.partake == '1') {
						$('#partake1').prop('checked', true);
					} else {
						$('#partake1').prop('checked', false);
					}
					$('#email').val(response.data.email);
					$('#tellphone').val(response.data.tellphone);
					$('.modal-header-h3').html('修改人员');
					$('#addPersonnel').modal();
				}
			},
			"error" : function(errmess) {
				$.alert("服务异常[" + errmess + "]，请联系管理员");
			}
		});
	}

	function changePartake(userId, obj) {
		var partake = '1';
		if (!obj.checked) {
			partake = '0';
		}
		$.ajaxFPostJson("${contextPath}/user/changePartake", {
			'userId' : userId,
			'partake' : partake,
			'_r' : new Date().getTime()
		}, {
			"success" : function(response) {
				$.alert(response.mess);
			},
			"error" : function(errmess) {
				$.alert("服务异常[" + errmess + "]，请联系管理员");
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
				<c:if test="${resource eq '1'}">
					<button class="btn btn-primary" style="color: #fff" id="btAdd">添加人员</button>
					<button class="btn btn-primary" style="color: #fff" id="btBatchAdd">批量添加</button>
					<button class="btn btn-primary" style="color: #fff" id="btDel">批量删除</button>
				</c:if>

				<input placeholder="姓名" id="qryStr" name="query" type="text"
					   class="search-query form-control" style="float:left;width:auto;">
				<button class="btn btn-primary glyphicon glyphicon-zoom-in" style="float:left;padding:4px 8px;"
					id="btnSearch" onclick="qryList()"></button>
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
	<div class="modal fade" id="addPersonnel" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" backdrop="static" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3 class="modal-header-h3">添加人员</h3>
				</div>
				<div class="modal-body">
					<form id="formInfo" class="inputF" role="form">
						<input type="hidden" name="opr" id="opr"> <input
							type="hidden" name="userId" id="userId">
						<div class="form-group">
							<label class="formTit">姓名</label> <input type="text"
								class="form-control formInp" name="userName" id="userName"
								placeholder="姓名" maxlength="32" /> <label
								class="formDesc corRed">* 最多32字符</label>
						</div>
						<div class="form-group has-success">
							<label class="formTit">参与排班</label> <input type="checkbox"
								class="exampleInputFile formChk" name="partake" id="partake1"
								value="1" /> <label class="formDesc"></label>
						</div>
						<div class="form-group">
							<label class="formTit">email</label> <input type="text"
								class="form-control formInp" name="email" id="email"
								placeholder="email" maxlength="128" /> <label
								class="formDesc corRed">* 最多128个字符</label>
						</div>
						<div class="form-group">
							<label class="formTit">电话</label> <input type="text"
								class="form-control formInp" name="tellphone" id="tellphone"
								placeholder="电话" maxlength="32" /> <label
								class="formDesc corRed">* 最多32个数字</label>
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

	<!-- 批量新增表单 -->
	<div class="modal fade" id="batchAddPersonnel" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3 class="modal-header-h3">批量添加人员</h3>
				</div>
				<div class="modal-body"
					style="height: 300px; overflow-y: auto; overflow-x: hidden;">
					<form id="formInfo" class="inputF" role="form">
						<div style="margin: 5px;">
							<div class="btn btn-info" id="addnewone">再加一行</div>
							<div class="btn btn-info" id="removeone">删除一行</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0"
							class="display table table-bordered" id="batchAddTable">
							<thead>
								<tr>
									<th role="columnheader" tabindex="0" rowspan="1" colspan="1"
										style="width: 60px; text-align: center;">序号</th>
									<th role="columnheader" tabindex="0" rowspan="1" colspan="1"
										style="width: 80px; text-align: center;">姓名<label class="formDesc corRed">*</label></th>
									<th role="columnheader" tabindex="0" rowspan="1" colspan="1"
										style="width: 100px; text-align: center;">参与排班</th>
									<th role="columnheader" tabindex="0" rowspan="1" colspan="1"
										style="width: 120px; text-align: center;">email<label class="formDesc corRed">*</label></th>
									<th role="columnheader" tabindex="0" rowspan="1" colspan="1"
										style="width: 120px; text-align: center;">电话<label class="formDesc corRed">*</label></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<a href="#" class="btn btn-primary" id="btBatchSave">确定</a> <a
						href="#" class="btn btn-default" id="btCancel"
						data-dismiss="modal">取消</a>
				</div>
			</div>
		</div>
	</div>

</body>
</html>