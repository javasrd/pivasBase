<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<%@ include file="../../common/head.jsp"%>
</head>
<style>
#nurse_order_content {
	height: auto;
	margin-top: 10px;
	margin-left: 10px;
}

.group_container {
	float: left;
	margin: 8px 20px 10px 0px;
	/*padding: 1px;*/
	width:250px;
	border: 1px solid #E0E4E8;
	border-radius:5px 5px 0 0;
}

#id_0 .group_top {
	font-size: 1.2em;
	line-height: 30px;
	background: #83867a;
	color: white;
	cursor: move;
	padding: 5px 0;
	display: block;
	padding-left: 10px;
	border-radius:5px 5px 0 0;
}

.group_top {
	font-size: 1.2em;
	line-height: 30px;
	background: #648ceb;
	color: white;
	cursor: move;
	padding: 5px 0;
	display: block;
	padding-left: 10px;
	border-radius:5px 5px 0 0;
}

.group_name, .group_order {
	margin-left: 5px;
	display: inline-block;
}

.group_name_editinput {
	margin-left: 3px;
	margin-top: 5px;
}

.group_operation {
	display: inline-block !important;
	margin: 5px;
	height: 30px;
	line-height: 30px;
	float: right;
	background-color: transparent;
	border: none !important;
	color: white;
}

.group_edit span {
	height: 25px;
	line-height: 25px;
	padding-left: 10px;
}

.nurse_order {
	list-style-type: none;
	margin-left:1%;
	padding: 4px;
	/*width: 95%;*/
	height: 300px;
	width: 98%;
}

#groups .nurse_order>li {
	background: white;
	cursor: move;
}

.nurse_order .nurse_order_container {
	border-right: #cccccc solid 1px;
	/*padding-bottom: 2px;
	position:relative;
	top: 0;
	left:0;*/
	line-height: 22px;
	text-align: center;
	font-size: 1.3em;
	padding: 0;
	min-width: 23px;
	display: inline-block;
	background-color: #9FC569;	
	color: white;
}

.nurse_order .nurse_name_container {
	font-size: 1.2em;
	line-height: 26px;
	color: #959595;
	display: inline-block;
	padding-left: 5px;
}

.nurse_order li {
	width: auto;
	height: 30px;
	padding-top: 1px;
	padding-bottom: 4px;
	padding-left: 3px;
}

.nurse_order li.dummy_placeholder {
	margin: 0;
	width: 1px;
	height: 2px;
	padding: 1px
}

.toolbar-btn {
	float: left;
}

.ui-state-hoverDate {
	border: 1px solid #999999;
	background: #778899 50% repeat-x;
	font-weight: normal;
	color: #212121;
}

.jqx-menu-item-arrow-down {
	padding-right: 0px;
	margin-right: -8px;
	width: 17px;
	height: 15px;
	background-image: url(../assets/images/icon-down.png);
	background-position: 100% 50%;
	background-repeat: no-repeat;
}

li {margin-bottom:7px;}


</style>

<script type="text/javascript">
	var p;
	
	var isEdit = false;
	
	$(function() {

		//日期对象
	p = {
			isWeekModel:true,
			startDate: null ,
		    endDate: null ,
	        setupDatePicker: function() {
	            $("#datepicker").datepicker({
	                showOtherMonths: !0,
	                selectOtherMonths: !0,
	                dateFormat: "yy-mm-dd",
	                onSelect: p.onSelect,
	                beforeShow: p.beforeShow,
	                beforeShowDay: p.beforeShowDay,
	                showButtonPanel :true,
	               // onChangeMonthYear: p.onChangeMonthYear
	               	dayNames : ['星期天','星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
	            	dayNamesMin : ['日','一', '二', '三', '四', '五', '六'],
	            	monthNames  :['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
	            	nextText : "下个月",
	            	prevText : "上个月",
	            	currentText :"今天",
	            	closeText:"关闭"
	
	            });
	            $("#datepicker").datepicker("option", "firstDay", 1);
	            
	            p.selectNowWeek();
	            changeDateStrFn();
	            
	            $("#checkDateBtn").click(function() {
	            	
	            	if (isEdit) {
	            		$.alert("请先结束编辑！");
	            		return;
	            	}
	            	
	                $("#datepicker").datepicker("show");
	                
	                if(p.isWeekModel){
		            	
		            	$(".ui-datepicker-calendar tr").on("mousemove", function() {
		                	$("#datepicker")[0] && $(this).find("td a").switchClass("ui-state-default","ui-state-hoverDate");
		            	});
		            	$(".ui-datepicker-calendar tr").on("mouseleave", function() {
		                	$("#datepicker")[0] && $(this).find("td a").switchClass("ui-state-hoverDate","ui-state-default");
		           		});
		            	
		            }
	                
	            });
	        }, 
	        selectCurrentWeek: function() {
	            p.isWeekModel && window.setTimeout(function() {
	                $("#datepicker").find(".ui-datepicker-current-day a").addClass("ui-state-active")
	            }, 1)
	        },
	        onSelect: function(i, u) {
	            var f = $(this).datepicker("getDate"), o, e;
	            
	            var number = f.getDay();
	            if(f.getDay() == 0){
	            	number = 7;
	            }
	            
	            p.startDate = new Date(f.getFullYear(),f.getMonth(),f.getDate() - number + 1),
			    p.endDate = new Date(f.getFullYear(),f.getMonth(),f.getDate() - number + 7);
	           		            
	            o = u.settings.dateFormat || $.datepicker._defaults.dateFormat,
	            e = $.datepicker.formatDate(o, f, u.settings);
	            p.startDate = $.datepicker.formatDate(o, p.startDate, u.settings);
	            p.endDate = $.datepicker.formatDate(o, p.endDate, u.settings);
	            p.todayDate = e;
	            
	            
	            p.selectCurrentWeek();
	            
	            if(p.isWeekModel){
		            //切换日期显示
		            changeDateStrFn();
		            
	            }else{
	            	//改变日期显示值
	        		changeDayDateFun();
	            }
	            
	             /*
	            var s = e
	              , c = "week"
	              , h = "none";
	            r.onSelectedCallback(s); */
	        },
	        beforeShow: function(n, t) {
	            setTimeout(function() {
	                var n = $("#datepicker").position();
	                t.dpDiv.css({
	                    top: n.top + 65,
	                    "z-index": 99999
	                })
	            }, 0)
	        },
	        beforeShowDay: function(t) {
	            var i = "";
	            return t >= p.startDate && t <= p.endDate && (i = "ui-datepicker-current-day"),
	            [!0, i]
	        },
	        selectNowWeek: function (){
	        	
	        	var now = new Date();
	        	var number = now.getDay();
	            if(now.getDay() == 0){
	            	number = 7;
	            }
	        	
	        	p.startDate = new Date(now.getFullYear(),now.getMonth(),now.getDate() - number + 1),
	            p.endDate = new Date(now.getFullYear(),now.getMonth(),now.getDate() - number + 7);
	        	nowStartDate = p.startDate ,nowEndDate = p.endDate;
	        	p.startDate = $.datepicker.formatDate("yy-mm-dd", p.startDate);
		        p.endDate = $.datepicker.formatDate("yy-mm-dd", p.endDate);
	        	
		        
		        
	        }
	        /*,
	        onChangeMonthYear: function() {
	            t.selectCurrentWeek();
	        } */
	    };

		p.setupDatePicker();

		//表单提交
		$("#btSave").click(function(){
			var groupName = $.trim($('#groupName').val());
			if (!isNotNull(groupName)) {
				$.alert('分组名称不能为空！');
				return;
			} else if (!checkLength($('#groupName'), 1, 32)) {
				$.alert('分组名称长度不能大于32！');
				return;
			}
			if(groupName == '待分配人员') {
				$.alert('分组名称不能为待分配人员！');
				return;
			}
			
			var isPass = true;
			var groupId = $('#groupId').val();
			
			$('.group_top').each(function() {
				if ($(this).attr('title') == groupName) {
					if (groupId == '' || groupId != $(this).parent().attr('id').replace('id_', '')) {
						$.alert('分组名称重复！');
						isPass = false;
					} 
				}
			});
			
			if (isPass) {
				$.ajaxFPostJson("${contextPath}/userGroup/saveGroup",
						{'groupName': groupName, 
						'startDate': p.startDate, 
						'endDate': p.endDate, 
						'groupId': $('#groupId').val(), 
						'opr':$('#opr').val()},
						{"success": function(response){
							$.alert(response.mess);
		    				if(response.code == 1){
		    					if ($('#opr').val() == 'add') {
		    						buildGroupHtml(response.data, groupName, null, true);
		    					} else {
		    						$('#id_' + $('#groupId').val()).find('.group_top').attr('title', groupName.replace(/\</g,"&lt;").replace(/\"/g,"'"));
		    						$('#id_' + $('#groupId').val()).find('.group_name').html(subGrouName(groupName));
		    					}
		    		    		$('#addGroup').modal('hide');
		    		    	}
		    			},
		    			"error": function(errmess){
                            layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
		    			}
		    		});
			}
		});
		
	});

	function updateGroupOrder() {
		if ($(".group_container").length != 1) {
			$(".group_container").each(function() {
				var n = $(this).prevAll(".group_container").length;
				$(this).find(".group_order").html(n + 1)
			});
		}
	}
	
	function updateNurseOrder(){
		$('.nurse_order li:not(".dummy_placeholder")').each(function(){
			var n = $(this).prevAll('li:not(".dummy_placeholder")').length;
			$(this).find(".nurse_order_container").html(n+1)
		});
		$(".group_container").each(function(){
			var n = $(this).find('.nurse_order li:not(".dummy_placeholder")').length;
			$(this).find(".group_people_count").html(n)
		});
	}
	

	//改变时间戳
	function changeDateStrFn() {

		var startDate = p.startDate.split("-");
		endDate = p.endDate.split("-");
		
		if (new Date(p.endDate).getTime() < new Date().getTime()) {
			$('#startBtn').attr('disabled', 'disabled');
		} else {
			$('#startBtn').removeAttr('disabled');
		}

		$("#timeText").html(
				"<strong>" + startDate[0] + " 年 " + startDate[1]
						+ " 月 " + startDate[2] + " 日 - " + endDate[0] + " 年 "
						+ endDate[1] + " 月 " + endDate[2] + " 日</strong>");
		
		//查询对应周的分组信息
		$('#groups').html("");
		$.ajaxFPostJson("${contextPath}/userGroup/getGroupInfos",
				{startDate: p.startDate, '_r': new Date().getTime()},
				{"success": function(response){
    				if(response.code == 1){
    					$.each(response.data, function() {
    						buildGroupHtml(this.groupId, this.groupName, this.personnelList, false);
    					});
    					
    					$("#groups").sortable({
    						cursor: "move",
    						stop: updateGroupOrder,
    						handle: ".group_top",
    						items: "div.group_container:not(#id_0)"
    					}).disableSelection();
    					$("#groups").sortable("disable");
    					
    					$(".nurse_order").sortable({
    						cursor: "move",
    						stop: updateNurseOrder,
    						connectWith: "ul.nurse_order",
    						dropOnEmpty: true,
    						items: "li:not(.dummy_placeholder)"
    					}).disableSelection();
    					$(".nurse_order").sortable("disable");
    		    	}
    			},
    			"error": function(errmess){
                    layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
    			}
    	});
	}
	
	function buildGroupHtml(groupId, groupName, personList, isEdit) {
		groupName1 = groupName.replace(/\</g,"&lt;").replace(/\"/g,"&quot;");
		var personLen = 0;
		if (personList != null && typeof(personList) != 'undefined') {
			personLen = personList.length;
		}
		var html = '<div class="content noPad clearfix group_container" id="id_' + groupId + '" data-groupid="' + groupId + '"><div class="group_top" title="' + groupName1 + '"><span style="margin-top: 5px"></span><div class="group_order">'
			+ ($('.group_container').size() + 1) + '</div><div class="group_name">' 
			+ subGrouName(groupName) + '</div><span>(<span class="group_people_count">' 
			+ personLen + '</span> 人)</span>';
		if (groupName != '待分配人员') {
			html += '<div class="group_operation"><ul><li style="width: 38px;"><a href="javascript:void(0);">操作</a><ul><li><a href="javascript:void(0);" class="group_edit" onclick="editGroup(this)" data-groupid="' 
				+ groupId + '">修改</a></li><li><a href="javascript:void(0);" class="group_delete" onclick="deleteGroup(this)" data-groupid="' + groupId + '">删除</a></li></ul></li></ul></div>';
		} else {
			$('#groups').html("");
		}
			
		html += '</div><ul class="nurse_order"><li class="dummy_placeholder"></li>';
		if (personLen > 0) {
			var name;
			for (var i = 1; i <= personList.length; i++) {
				if (personList[i - 1].userName.length > 12) {
					name = personList[i - 1].userName.substr(0, 12) + '...';
				} else {
					name = personList[i - 1].userName;
				}
				html += '<li class="ui-state-default" data-userid="' + personList[i - 1].userId + '" title="' + (personList[i - 1].userName.replace(/\</g, "&lt;").replace(/\"/g, "&quot;")) + '"><div><div class="nurse_order_container">' + i + '</div><div class="nurse_name_container">' + (name.replace(/\</g, "&lt;").replace(/\"/g, "&quot;")) + '</div></div></li>'
			}
		}
		html += '</ul></div>';
		$('#groups').append(html);
		
		if (groupName != '待分配人员') {
			$(".group_operation").jqxMenu({
				width : "60",
				mode : "horizontal",
				showTopLevelArrows: false
			});
			if (isEdit) {
				$(".group_operation").css("visibility", "visible");
			} else {
				$(".group_operation").css("visibility", "hidden");
			}
		}
		
		$("#groups").sortable({
			cursor: "move",
			stop: updateGroupOrder,
			handle: ".group_top",
			items: "div.group_container:not(#id_0)"
		}).disableSelection();
		
		$(".nurse_order").sortable({
			cursor: "move",
			stop: updateNurseOrder,
			connectWith: "ul.nurse_order",
			dropOnEmpty: true,
			items: "li:not(.dummy_placeholder)"
		}).disableSelection();
	}

	//编辑按钮变更
	function changeSwitch(e) {
		if (e.value == "true") {
			$(e).switchClass("btn-danger", "btn-primary");
			e.value = "false", e.innerText = "开始编辑";
			$("#autoBtn").hide();
			$('#addBtn').attr('disabled', 'disabled');

			$("#groups").sortable({
				cursor: "move",
				stop: updateGroupOrder,
				handle: ".group_top",
				items: "div.group_container:not(#id_0)"
			}).disableSelection();
			$("#groups").sortable("disable");
			
			$(".nurse_order").sortable({
				cursor: "move",
				stop: updateNurseOrder,
				connectWith: "ul.nurse_order",
				dropOnEmpty: true,
				items: "li:not(.dummy_placeholder)"
			}).disableSelection();
			$(".nurse_order").sortable("disable");
			
			$(".group_operation").each(function() {
				$(this).css("visibility", "hidden");
				$(this).jqxMenu({
					showTopLevelArrows : false
				});
			});
			
			//保存分组排序及分组人员关联关系
			var datas = '';
			
			$('.group_container').each(function() {
				var groupId = $(this).attr('data-groupid') * 1;
				var userIds = '';
				if (groupId > 0) {
					$(this).find('.ui-state-default').each(function() {
						userIds += $(this).attr('data-userid') + ',';
					});
					if (userIds.length > 0) {
						userIds = userIds.substr(0, userIds.length - 1);
					}
					datas += groupId + ':' +  userIds + '@';
				}
			});
			if (datas.length > 0) {
				datas = datas.substr(0, datas.length - 1);
			}
			$.ajaxFPostJson("${contextPath}/userGroup/saveGroupInfos",
					{'datas': datas, 'startDate': p.startDate, 'endDate': p.endDate},
					{"success": function(response){
						$.alert(response.mess);
	    			},
	    			"error": function(errmess){
                        layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
	    			}
	    		});
			
			isEdit = false;//非编辑状态
		} else if (e.value == "false") {
			$(e).switchClass("btn-primary", "btn-danger");
			e.value = "true", e.innerText = "结束编辑";
			$("#autoBtn").show();
			$('#addBtn').removeAttr('disabled');

			$("#groups").sortable("enable");
			$(".nurse_order").sortable("enable");
			
			$(".group_operation").each(function() {
				$(this).css("visibility", "visible");
				$(this).jqxMenu({
					showTopLevelArrows : true
				});
			});
			
			isEdit = true;//编辑状态
		}
	}

	//上周按钮
	function lastWeekDataFn() {
		if (isEdit) {
            layer.alert('请先结束编辑', {icon: 6});
    		return;
    	}
		
		var sD = new Date(p.startDate), eD = new Date(p.endDate);

		var startDate = new Date(sD.getFullYear(), sD.getMonth(),
				sD.getDate() - 7), endDate = new Date(eD.getFullYear(), eD
				.getMonth(), eD.getDate() - 7);

		var startString = $.datepicker.formatDate("yy-mm-dd", startDate), endString = $.datepicker
				.formatDate("yy-mm-dd", endDate);

		p.startDate = startString, p.endDate = endString;
		changeDateStrFn();

	}

	//下周按钮
	function nextWeekDataFn() {
		if (isEdit) {
            layer.alert('请先结束编辑', {icon: 6});
    		return;
    	}
		
		var sD = new Date(p.startDate), eD = new Date(p.endDate);

		var startDate = new Date(sD.getFullYear(), sD.getMonth(),
				sD.getDate() + 7), endDate = new Date(eD.getFullYear(), eD
				.getMonth(), eD.getDate() + 7);

		var startString = $.datepicker.formatDate("yy-mm-dd", startDate), endString = $.datepicker
				.formatDate("yy-mm-dd", endDate);

		p.startDate = startString, p.endDate = endString;
		changeDateStrFn();

	}
	
	function addGroup() {
		 $('#opr').val("add");
		 $('#groupName').val("新分组");
		 $('#groupId').val("");
		 $('#addGroup .modal-header-h3').html('添加分组');
		 $('#addGroup').modal();
	}
	
	function editGroup(obj) {
		var groupId = $(obj).attr('data-groupid');
		var groupName = $('#id_' + groupId).find('.group_name').text();
		$('#opr').val('edit');
		$('#groupId').val(groupId);
		$('#groupName').val($('#id_' + groupId).find('.group_top').attr('title'));
		$('#addGroup .modal-header-h3').html('修改分组');
		$('#addGroup').modal();
	}
	
	function deleteGroup(obj) {
		var groupId = $(obj).attr('data-groupid');
		$('#addGroup .modal-header-h3').html('删除分组');
		if ($('#id_' + groupId).find('.nurse_name_container').size() > 0) {
			$.alert("无法删除，请先移除该分组下的人员");
		} else {
            layer.confirm('确定删除此分组吗？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajaxFPostJson("${contextPath}/userGroup/del",
                    {'groupId': groupId, 'startDate': p.startDate, 'endDate': p.endDate},
                    {"success": function(response){
                        $.alert(response.mess);
                        if(response.code == 1){
                            $('#id_' + groupId).remove();
                            updateGroupOrder();
                        }
                        layer.msg('删除成功！', {icon: 1});
                    },
                        "error": function(errmess){
                            layer.alert("服务异常["+errmess+"]，请联系管理员", {'title': '操作提示', icon: 0});
                        }
                    });
            }, function(){
            });

		}
	}
	
	function subGrouName(name) {
		if (name.length <= 7) {
			return name.replace(/\</g, "&lt;");
		} else {
			return (name.substr(0, 6) + '...').replace(/\</g, "&lt;");
		}
	}
</script>

<body style="background: #F3F5F9;">

	<div class="ch-container clearfix">

		<div style="margin-bottom: 20px;"></div>

		<div id="content" style="margin: 0px 10px 0px 10px;">
			<!-- content -->

			<div class="breadcrumb" style="height:40px;">
				<!-- breadcrumb -->

				<div class="toolbar-btn">
					<!-- toolbar -->

					<button class="btn btn-group btn-primary" style="color: #fff"
						id="startBtn" value="false" onclick="changeSwitch(this)">开始编辑</button>

					<button class="btn btn-group btn-primary" disabled="disabled" id="addBtn"
						value="false" onclick="addGroup()" style="margin: 0 8px;">添加分组</button>

					<div class="btn-group">

						<input type="text" id="datepicker" style="display: none">

						<button class="btn" id="lastWeekBtn" title="上一周"
							onclick="lastWeekDataFn()" style="margin:auto;padding:6px 8px;">
							<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						</button>

						<button class="btn" title="自选日期" id="checkDateBtn" style="margin:0 5px;padding:4px 5px;color:#4054b3;font-size:14px;">
							<div id="timeText" style="margin:0;"></div>
						</button>

						<button class="btn" id="nextWeekBtn" title="下一周"
							onclick="nextWeekDataFn()" style="margin:auto;padding:6px 8px;">
							<span class="glyphicon glyphicon-chevron-right"
								aria-hidden="true"></span>
						</button>
					</div>

				</div>
				<!-- toolbar -->

			</div>
			<!-- breadcrumb -->

		</div>
		<!--content-->

		<div id="nurse_order_content" class=" content noPad clearfix">
			<div id="groups">
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="addGroup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" backdrop="static" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal">×</button>
	                <h3 class="modal-header-h3" >添加分组</h3>
	            </div>
	            <div class="modal-body">
	                <form id="formInfo" class="inputF" role="form">
	                    <input type="hidden" name="groupId" id="groupId" value="">
	                    <input type="hidden" name="opr" id="opr" value="">
	                    <div class="form-group">
	                        <label class="formTit">分组名称</label>
	                        <input type="text" class="form-control formInp" name="groupName" id="groupName" placeholder="分组名称" maxlength="32">
	                        <label class="formDesc corRed">* 最多32个字符</label>
	                    </div>
	                </form>
	            </div>
	            <div class="modal-footer">
	                <a href="#" class="btn btn-primary" id="btSave"">确定</a>
	                <a href="#" class="btn btn-default" id="btCancel" data-dismiss="modal">取消</a>
	            </div>
	        </div>
	    </div>
	</div>

</body>
</html>