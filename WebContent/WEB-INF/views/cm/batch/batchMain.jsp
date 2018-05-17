<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/colorpicker/spectrum.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/assets/colorpicker/spectrum.js" type="text/javascript"></script>
<head>
	<script>
    var datatable;
    var qryParam;
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
                "url": "${pageContext.request.contextPath}/cm/bat/batList",
                "type": "post",
                "data": function (d) {
                    var params = [];
                    if (qryParam) {
                        params = qryParam.concat();
                    }
                    for (var index in params) {
                        d[params[index].name] = params[index].value;
                    }

                    d.rp = d.length;
                    d.page = d.start / d.length + 1;
                },
                "dataSrc": function (data) {
                    data.data = data.rawRecords;
                    //data.draw = 1;
                    data.recordsFiltered = data.total;
                    data.recordsTotal = data.total;
                    return data.data;
                }
            },
            "columns": [
                {"data": "id", "bSortable": false},
                {"data": "num", "bSortable": false},
                {"data": "name", "bSortable": false},
                {"data": "orderNum", "bSortable": false},
                {"data": "isEmptyBatch", "bSortable": false},
                {"data": "startTime", "bSortable": false},
                {"data": "endTime", "bSortable": false},
                {"data": "enabled", "bSortable": false},
                {"data": "is0P", "bSortable": false},
                {"data": "timeType", "bSortable": false},
                {"data": "isForce", "bSortable": false},
                {"data": "isVolume", "bSortable": false},
                {"data": "id", "bSortable": false}
            ],
            columnDefs: [
                {
                    targets: 0,
                    render: function (data, type, row) {
                        return "<input type='checkbox' name='cbs' value='" + data + "'/>";
                    }
                }, {
                    targets: [4, 7, 8],
                    render: function (data, type, row) {
                        return data == 0 ? "<spring:message code="common.no"/>" : "<spring:message code="common.yes"/>";
                    }
                }, {
                    targets: [5, 6],
                    render: function (data, type, row) {
                        return data == undefined ? "" : data;
                    }
                }, {
                    targets: 9,
                    render: function (data, type, row) {
                        return data == "morning" ? "上午" : (data == "afternoon" ? "下午" : "非上下午");
                    }
                }, {
                    targets: [10,11],
                    render: function (data, type, row) {
                        return data == "1" ? "开启" : "关闭";
                    }
                }, {
                    targets: 12,
                    render: function (data, type, row) {
                        <shiro:hasPermission name="PIVAS_BTN_223">
                        return "<a class='button btn-bg-green' href='javascript:editBatch(" + data + ");'><spring:message code='common.edit'/></a>";
                        </shiro:hasPermission>
                        <shiro:lacksPermission name="PIVAS_BTN_223">
                        return "";
                        </shiro:lacksPermission>
                    }
                }
            ]
        });
    }

	function editBatch(id) {
        if (id != "") {
            $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/cm/bat/getBat',
                dataType : 'json',
                cache : false,
                data : [{name : 'id',value : id}],
                success : function(data) {
                    if(data.success == false){
                        $(".pReload").click();
                        message({
                            data: data
                        });
                        return;
                    }else{
                        setFormValue("editView-div",data);
                    }
                    $("#editView-div").dialog("option","title","<spring:message code='common.edit'/>");
                    $("#editView-div").dialog("open");
                }
            });
        } else {
            message({html: '<spring:message code="common.plzSelectOneRecourd"/>',showConfirm: true});
        }
    }
	
	$(function() {
		$("#editView-form select").combobox();
		
		initDatatable();

        $(".datatable > thead").on("change","input:checkbox",function(){
            var selected = $(this).attr("checked") == "checked";
            $(".datatable > tbody").find("input:checkbox").each(function (i, v) {
                if (selected) {
                    $(v).attr("checked", 'checked');
                } else {
                    $(v).removeAttr("checked");
                }
            });
        });
		
		<%-- 修改建筑Form的ajax交互 --%>
		$('#editView-form').ajaxForm({
            dataType: "json",
            success : function(data){
            	if(data.success || data.code == '<%=ExceptionCodeConstants.RECORD_DELETE%>') {
            		$('#editView-div').dialog('close');
		    	}
            	$(".pReload").click();
            	<%-- 弹出提示信息 --%>
		    	message({
	    			data: data
            	});
            }
    	});
		
		$("#editView-div").dialog({
			autoOpen: false,
			height: 350,
			width: 700,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var bValid = validateInput("editView-div") == null?true:false;
					if (!bValid ) {
						return ;
					}
					var url = "${pageContext.request.contextPath}/cm/bat/addBat";
					var id = $("#editView-div").find("input[name='id']").val();
					if(id && id!=""){
						url = "${pageContext.request.contextPath}/cm/bat/updBat";
					}
					$("#editView-form").attr("action", url);
					$("#editView-form").submit();
				},
				"<spring:message code='common.cancel'/>": function() {
					$(this).dialog("close");
				}
			},
			open: function() {
				/* $('#colorButton').colorpicker({
					'float':'left'
				}); */
				$('#colorButton').spectrum({
					showPalette: true,
					allowEmpty:true,
					change:function(color){
						var val = "";
						if(color != null){
							val = color.toHexString();
						}
						$('#colorButton').val(val);
					}
				});
			},
			close: function() {
				resetForm("editView-div");
				//$('#colorButton').colorpicker('destroy');
			}
		});
		
		<%-- 新增按钮 --%>
		$( "#addDataDicRoleBtn").bind("click",function(){
			$("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='common.add'/>");
			$("#editView-div").dialog("open");
		});
		
		$("#deleteDataDicRoleBtn").bind("click",function(){
			var ids = getSelectedRow();
			if (ids && ids.length >0) {
                layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                    btn: ['确定', '取消'], icon: 3, title: '提示'
                }, function () {
                    $.ajax({
                        type : 'POST',
                        url : '${pageContext.request.contextPath}/cm/bat/delBat',
                        dataType : 'json',
                        cache : false,
                        data : [{name : 'ids', value : ids}],
                        success : function(data) {
                            qryList();
                            layer.alert(data.description, {'title': '操作提示', icon: 0});
                        }
                    });
                });
			} else {
                layer.alert('<spring:message code="common.plzSelectOneRecourd"/>', {'title': '操作提示', icon: 0});
			}
		});
	});

    function getSelectedRow() {
        var arr = new Array(0);
        $(".datatable > tbody").find("input:checked").each(function (i, v) {
            arr.push($(v).val())
        });
        return arr;
    }

	function qryList(param){
        qryParam = param;
        datatable.ajax.reload();
	}
	
</script>
</head>
<body>

<div class="main-div" style="width:100%">
	<%-- 搜索条件--开始 --%>
	<div data-qryMethod funname="qryList"  class="ui-search-header ui-search-box" id="qryView-div" style="display: inline; ">
		<div style="float: right">
		<input placeholder="名称" name="names" size="20" data-cnd="true">&nbsp;&nbsp;
		<button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
		<button class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
		</div>
		<shiro:hasPermission name="PIVAS_BTN_313">
			<a class="ui-search-btn ui-btn-bg-green" id="addDataDicRoleBtn"><i class="am-icon-plus"></i><span><spring:message code='common.add'/></span></a>
		</shiro:hasPermission>
		<shiro:hasPermission name="PIVAS_BTN_314">
			<a class="ui-search-btn ui-btn-bg-red" id="deleteDataDicRoleBtn"><i class="am-icon-trash"></i><span><spring:message code='common.del'/></span></a>
		</shiro:hasPermission>
	</div>
	<%-- 搜索条件--结束 --%>
	<table class="table datatable ui-data-table display dataTable no-footer">
		<thead>
		<tr>
			<th><input type='checkbox' name='cbs'/></th>
			<th><spring:message code="batch.num"/></th>
			<th><spring:message code="batch.name"/></th>
			<th><spring:message code="pivas.synsetting.priority"/></th>
			<th><spring:message code="batch.empty"/></th>
			<th><spring:message code="batch.startTime"/></th>
			<th><spring:message code="batch.endTime"/></th>
			<th><spring:message code="batch.enabled"/></th>
			<th>临批</th>
			<th>上下午</th>
			<th>强制</th>
			<th>容积</th>
			<th>操作</th>
		</tr>
		</thead>
	</table>
</div>

<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="<spring:message code='common.add'/>" align="center" style="display: none;">
<form id="editView-form" action="" method="post">
	<input type="hidden" name="id"/>
    <div class="popup">
    	<div class="row">
			<div class="column columnL">
				<label class="tit"><spring:message code='batch.num'/></label>
				<input type="text" name="num" maxlength="32" empty="false" title="<spring:message code='common.op.remind2'/>"/>
				<span class="mand">*</span>
			</div>
			<div class="column columnR">
				<label class="tit"><spring:message code='batch.name'/></label>
				<input type="text" name="name" maxlength="32" empty="false" title="<spring:message code='common.op.remind2'/>"/>
				<span class="mand">*</span>
			</div>
		</div>
    	<div class="row">
			<div class="column columnL">
				<label class="tit"><spring:message code='batch.startTime'/></label>
				<input type="text" id="startTime" name="startTime" class="Wdate" maxlength="32" empty="true" readonly="readonly"
					onclick="WdatePicker({dateFmt:'HH:mm',minDate:'00:00',maxDate:'#F{$dp.$D(\'endTime\')}'});"/>
				
			</div>
			<div class="column columnR">
				<label class="tit"><spring:message code='batch.endTime'/></label>
				<input type="text" id="endTime" name="endTime" class="Wdate" maxlength="32" empty="true" readonly="readonly"
					onclick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'24:00'});"/>
				
			</div>
		</div>
		<div class="row">
			<div class="column columnL">
				<label class="tit"><spring:message code='batch.empty'/></label>
				<select name="isEmptyBatch" readonly empty="false" style="width: 184px">
					<option value=""><spring:message code="common.select"/></option>
            		<option value="0"><spring:message code="common.no"/></option>
            		<option value="1"><spring:message code="common.yes"/></option>
            	</select>
            	<span class="mand">*</span>
			</div>
			<div class="column columnR">
				<label class="tit">临批</label>
				<select name="is0P" readonly empty="false" style="width: 184px">
					<option value=""><spring:message code="common.select"/></option>
            		<option value="0"><spring:message code="common.no"/></option>
            		<option value="1"><spring:message code="common.yes"/></option>
            	</select>
            	<span class="mand">*</span>
			</div>
		</div>
		<div class="row">
			<div class="column columnL">
				<label class="tit">是否开启强制</label>
				<select name="isForce" readonly empty="false" style="width: 184px"> 
            		<option value="0">关闭</option>
            		<option value="1"  selected="selected" >开启</option>
            	</select>
            	<span class="mand">*</span>
			</div>
			<div class="column columnR">
				<label class="tit">是否开启容积</label>
				<select name="isVolume" readonly empty="false" style="width: 184px">
            		<option value="0" >关闭</option>
            		<option value="1" selected="selected" >开启</option>
            	</select>
            	<span class="mand">*</span>
			</div>
		</div>
		<div class="row"><%--
			<div class="column columnL">
				<label class="tit"><spring:message code='batch.secondAdvice'/></label>
				<select name="isSecendAdvice" readonly empty="false" style="width: 184px"> 
					<option value=""><spring:message code="common.select"/></option>
            		<option value="0"><spring:message code="common.no"/></option>
            		<option value="1"><spring:message code="common.yes"/></option>
            	</select>
            	<span class="mand">*</span>
			</div> --%>
			<div class="column columnR">
				<label class="tit">上下午</label>
				<select name="timeType" readonly empty="false" style="width: 184px">
					<option value="other" selected="selected" >非上下午</option>
            		<option value="morning">上午</option>
            		<option value="afternoon">下午</option>
            	</select>
            	<span class="mand">*</span>
			</div>
		</div>
		<div class="row">
			<div class="column columnL">
				<label class="tit"><spring:message code='batch.enabled'/></label>
				<select name="enabled" readonly empty="false" style="width: 184px">
					<option value=""><spring:message code="common.select"/></option>
            		<option value="0"><spring:message code="common.no"/></option>
            		<option value="1"><spring:message code="common.yes"/></option>
            	</select>
            	<span class="mand">*</span>
			</div>
			<div class="column columnR" style="text-align: left;">
				<label class="tit"><spring:message code='batch.Color'/></label>
				<input type="text" id="colorButton" name="color" maxlength="32" style="width: 156px;"/>
			</div>
		</div>
		<div class="row">
			<div class="column columnL">
				<label class="tit"><spring:message code='pivas.synsetting.priority'/></label>
				<input type="text" name="orderNum" maxlength="3" empty="false" regex="integer"
						title="<spring:message code='common.plzInputInteger0'/>"/>
				<span class="mand">*</span>
			</div>
		</div>
		<div style="height: 0px;width: 0px;max-height: 0p;max-width: 0px;visibility: hidden;  ">
			<select name="isSecendAdvice" style="height: 0px;width: 0px;max-height: 0p;max-width: 0px;" readonly empty="false" style="width: 184px"> 
            		<option value="0" selected="selected" ><spring:message code="common.no"/></option>
            		<option value="1"><spring:message code="common.yes"/></option>
            	</select>
		</div>
    </div>
</form>
</div>

</body>

</html>