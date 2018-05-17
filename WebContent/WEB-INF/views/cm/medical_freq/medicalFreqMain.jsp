<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>
<head>
	<script>
    var datatable;
    var qryParam;
	 function editMdcFreq(id) {
		 if (id != "") {
			 $.ajax({
				 type : 'POST',
				 url : '${pageContext.request.contextPath}/cm/mdcFreq/get',
				 dataType : 'json',
				 cache : false,
				 data : [ {name : 'id',value : id} ],
				 success : function(data) {
					 if(data.success == false){
						 $(".pReload").click();
						 message({
							 data: data
						 });
						 return;
					 }
					 else{
						 setFormValue("editView-div",data);
					 }
					 $("#editView-div input[name='code'],#editView-div input[name='name']")
						 .attr('readonly','readonly')
						 .addClass('gray');

					 $("#editView-div").dialog("option","title","<spring:message code='common.edit'/>");
					 $("#editView-div").dialog("open");
				 }
			 });
		 } else {
			 message({html: '<spring:message code="common.plzSelectOneRecourd"/>',showConfirm: true});
		 }
	 }

    function delMdcFreq(id) {
        if (id != "") {
            message({
                html: '<spring:message code="common.deleteConfirm"/>',
                showCancel:true,
                confirm:function(){
                    $.ajax({
                        type : 'POST',
                        url : '${pageContext.request.contextPath}/cm/mdcFreq/delMdcFreq',
                        dataType : 'json',
                        cache : false,
                        data : [{name : 'id', value : id}],
                        success : function(data) {
                            qryList();
                            message({
                                data: data
                            });
                        }
                    });
                }
            });
        } else {
            message({html: '<spring:message code="common.plzSelectOneRecourd"/>',showConfirm: true});
        }
    }

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
                "url": "${pageContext.request.contextPath}/cm/mdcFreq/mdcFreqList",
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
                    data.recordsFiltered = data.total;
                    data.recordsTotal = data.total;
                    return data.data;
                }
            },
            "columns": [
                {"data": "code", "bSortable": false},
                {"data": "name", "bSortable": false},
                {"data": "interval", "bSortable": false},
                {"data": "timeOfDay", "bSortable": false},
                {"data": "startTime", "bSortable": false},
                {"data": "endTime", "bSortable": false},
                {"data": "id", "bSortable": false}
            ],
            columnDefs: [
                {
                    targets: 6,
                    render: function (data, type, row) {
                        <shiro:hasPermission name="PIVAS_BTN_325">
                        return "<a class='button btn-bg-green' href='javascript:editMdcFreq(" + data + ");'><spring:message code='common.edit'/></a>";
                        </shiro:hasPermission>
                        <shiro:lacksPermission name="PIVAS_BTN_325">
                        return "";
                        </shiro:lacksPermission>
                        <%--<shiro:hasPermission name="PIVAS_BTN_323">--%>
                        <%--return "<a href='javascript:delMdcFreq(" + v + ");'><spring:message code='common.del'/></a>";--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:lacksPermission name="PIVAS_BTN_323">--%>
                        <%--return "";--%>
                        <%--</shiro:lacksPermission>--%>
                    }
                }
            ]
        });
    }
	
	$(function() {
		$("#editView-form select").combobox();

        initDatatable();
		
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
			width: 500,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var bValid = validateInput("editView-div") == null?true:false;
					if (!bValid ) {
						return ;
					}
					var url = "${pageContext.request.contextPath}/cm/mdcFreq/addMdcFreq";
					
					var id = $("#editView-div").find("input[name='id']").val();
					if(id && id!=""){
						url = "${pageContext.request.contextPath}/cm/mdcFreq/updMdcFreq";
					}
					
					$("#editView-form").attr("action", url);
					
					$("#editView-form").submit();
				},
				"<spring:message code='common.cancel'/>": function() {
					$(this).dialog("close");
				}
			},
			close: function() {
				resetForm("editView-div");
				$("#editView-div input[name='code'],#editView-div input[name='name']")
				.removeAttr('readonly','readonly')
				.removeClass("gray");
			}
		});
		
		<%-- 新增按钮 --%>
		$( "#addDataDicRoleBtn").bind("click",function(){
			$("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='common.add'/>");
			$("#editView-div").dialog("open");
		});
		
		<%-- 同步按钮 --%>
        $("#syncDataDicRoleBtn").bind("click", function () {
            layer.confirm('<spring:message code="common.startSynTask"/>', {
                btn: ['确定', '取消'], icon: 3, title: '提示'
            }, function () {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/cm/mdcFreq/synMdcFreq',
                    dataType: 'json',
                    cache: false,
                    data: [],
                    success: function (data) {
                        qryList();
                        layer.alert(data.description, {'title': '操作提示', icon: 0});
                    },
                    error: function () {
                        layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
                    }
                });
            });
        });
	});

    function qryList(param) {
        qryParam = param;
        datatable.ajax.reload();
    }
</script>
</head>
<body>

<div class="main-div" style="width:100%">
	<%-- 搜索条件--开始 --%>
	<div data-qryMethod funname="qryList"  class="ui-search-header ui-search-box"  id="qryView-div" style="display: inline; ">
		<div style="float: right">
		<input placeholder="编码" name="codes" size="8" data-cnd="true">&nbsp;&nbsp;
		 <input name="names" placeholder="名称" size="8" data-cnd="true">&nbsp;&nbsp;
		<button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
		<button   class="ui-search-btn ui-btn-bg-yellow"  onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
		</div>
		<shiro:hasPermission name="PIVAS_BTN_322">
			<a class="ui-search-btn ui-btn-bg-green" id="syncDataDicRoleBtn"><i class="am-icon-refresh"></i><span>同步</span></a>
		</shiro:hasPermission>
	</div>
	<%-- 搜索条件--结束 --%>
    <table class="table datatable ui-data-table display dataTable no-footer">
        <thead>
        <tr>
            <th><spring:message code="pivas.measureunit.code"/></th>
            <th><spring:message code="medical.freq.name"/></th>
            <th><spring:message code="medical.freq.interval"/></th>
            <th><spring:message code="medical.freq.timeOfDay"/></th>
            <th><spring:message code="medical.freq.startTime"/></th>
            <th><spring:message code="medical.freq.endTime"/></th>
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
			<div class="column">
				<label class="tit"><spring:message code='pivas.measureunit.code'/></label>
				<input type="text" name="code" maxlength="32" empty="false" />
				<span class="mand">*</span>
			</div>
			<div class="column">
				<label class="tit"><spring:message code='medical.freq.name'/></label>
				<input type="text" name="name" maxlength="32" empty="false" readonly="readonly" />
				<span class="mand">*</span>
			</div>
		</div>
    	<div class="row">
			<div class="column">
				<label class="tit"><spring:message code='medical.freq.interval'/></label>
				<input type="text" name="interval" maxlength="5" empty="false" regex="int" minvalue="1" title="<spring:message code='common.plzInputZInteger5'/>"/>
				<span class="mand">*</span>
			</div>
			<div class="column">
				<label class="tit"><spring:message code='medical.freq.timeOfDay'/></label>
				<input type="text" name="timeOfDay" maxlength="5" empty="false" regex="int" minvalue="1" title="<spring:message code='common.plzInputZInteger5'/>"/>
				<span class="mand">*</span>
			</div>
		</div>
		<div class="row">
			<div class="column">
				<label class="tit"><spring:message code='medical.freq.startTime'/></label>
				<input type="text" id="startTime" name="startTime" class="Wdate" maxlength="32" empty="false" 
					readonly="readonly" onclick= "WdatePicker({dateFmt:'HH:mm',minDate:'00:00',maxDate:'#F{$dp.$D(\'endTime\')}'});"/>
				<span class="mand">*</span>
			</div>
			<div class="column">
				<label class="tit"><spring:message code='medical.freq.endTime'/></label>
				<input type="text" id="endTime" name="endTime" class="Wdate" maxlength="32" empty="false" 
					readonly="readonly" onclick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'24:00'});"/>
				<span class="mand">*</span>
			</div>
		</div>
    </div>
</form>
</div>

</body>

</html>