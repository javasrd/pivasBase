<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<head>
	<script>
    var datatable;
    var qryParam;
	function editIpta(code) {
        var tr = undefined;
        $(".datatable > tbody > tr").each(function(i,v){
           $(v).children().each(function(ii,vv){
               if($(vv).children().first().val() == code){
                   tr = $(v);
			   }
		   });
		});

        if(tr != undefined){

            var id = tr.children().eq(1).text();
            var name = tr.children().eq(2).text();
            var dan = tr.children().eq(3).text();
            var enble = tr.children().eq(4).text();


            if(enble=="是"){
                enble="1";
            }else{
                enble="0";
            }
            $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/ipta/iptaList',
                dataType : 'json',
                cache : false,
                data : [ {name : 'deptCode',value : id} ],
                success : function(data) {
                    if(data.success == false){
                        $(".pReload").click();
                        message({
                            data: data
                        });
                        return;
                    }
                    else{
                        $("#deptCode").val(id);
                        $("#deptName").val(name);
                        $("#enabled").val(enble);
                        $("#deptAliasName").val(dan);
                    }
                    $("#editView-div").prev().find('.ui-dialog-title').text("修改病区");
                    $("#editView-div").dialog("open");
                },
                error : function() {}
            });
		}else{
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
                "url": "${pageContext.request.contextPath}/ipta/iptaList",
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
                {"data": "deptCode", "bSortable": false},
                {"data": "deptCode", "bSortable": false},
                {"data": "deptName", "bSortable": false},
                {"data": "deptAliasName", "bSortable": false},
                {"data": "enabled", "bSortable": false},
                {"data": "deptCode", "bSortable": false}
            ],
            columnDefs: [
                {
                    targets: 0,
                    render: function (data, type, row) {
                        return "<input type='checkbox' name='cbs' value='"+data+"'/>";
                    }
                },
                {
                    targets: 4,
                    render: function (data, type, row) {
                       return data == 1 ? "<spring:message code="common.yes"/>" : "<spring:message code="common.no"/>";
                    }
                },
                {
                    targets: 5,
                    render: function (data, type, row) {
                        <shiro:hasPermission name="PIVAS_BTN_223">
                        return "<a class='button btn-bg-green' href='javascript:editIpta(" + data + ");'><i class='am-icon-edit'></i><span>&nbsp;<spring:message code='common.edit'/></a>";
                        </shiro:hasPermission>
                        <shiro:lacksPermission name="PIVAS_BTN_223">
                        return "";
                        </shiro:lacksPermission>
                    }
                }
            ]
        });
    }
	
	$(function() {
		sdfun.fn.trimAll("editView-div");
		var deptCode = $("#deptCode");
		var deptName = $("#deptName");
		var	allFieldsInfo = $( [] ).add(deptCode).add(deptName);
		
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

		$("#editView-div").dialog({
			autoOpen: false,
			height: 300,
			width: 500,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var bValid = true;
					allFieldsInfo.removeClass( "ui-state-error");
					bValid = bValid && checkLength( deptCode, 1, 32);
					if (!bValid ) {
						return ;
					}
					var url = "${pageContext.request.contextPath}/ipta/addIptaList";
					var params = {"deptName":$("#deptName").val(),"enabled":$("#enabled").val(),"deptAliasName":$("#deptAliasName").val()} ;
					if($("#deptCode").val() && $("#deptCode").val()!=""){
						url = "${pageContext.request.contextPath}/ipta/updIptaList";
						params["deptCode"]=$("#deptCode").val() ;
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
			            	qryList();
					    	message({
				    			data: data
			            	});
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
				resetForm("editView-div");
				allFieldsInfo.val("").removeClass("ui-state-error");
			}
		});

		<%-- 同步按钮 --%>
		$("#synDataDicBtn").bind("click",function(){
            layer.confirm('<spring:message code="common.startSynTask"/>', {
                btn: ['确定', '取消'], icon: 3, title: '提示'
            }, function () {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/ipta/synIpta',
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
		

		<%-- 启动病区 --%>
		$("#startBtn").bind("click",function(){
			var ids = getSelectedRow();
			if (ids && ids.length >0) {
                layer.confirm('是否确认启动病区？', {
                    btn: ['确定', '取消'], icon: 3, title: '提示'
                }, function () {
                    $.ajax({
                        type : 'POST',
                        url : '${pageContext.request.contextPath}/ipta/updIptaState',
                        dataType : 'json',
                        cache : false,
                        data : [ {name : 'deptCode',value : ids} ,
                            {name : 'enabled',value:'1'}],
                        success : function(data) {
                            qryList();
                            layer.alert(data.description, {'title': '操作提示', icon: 0});
                        },
                        error : function () {
                            layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
                        }
                    });
                });
			} else {
                layer.alert('<spring:message code="common.plzSelectOneRecourd"/>', {'title': '操作提示', icon: 0});
			}
		});
		
		<%-- 关闭病区 --%>
		$("#stopBtn").bind("click",function(){
			var ids = getSelectedRow();
			if (ids && ids.length >0) {
                layer.confirm('是否确认停止病区？', {
                    btn: ['确定', '取消'], icon: 3, title: '提示'
                }, function () {
                    $.ajax({
                        type : 'POST',
                        url : '${pageContext.request.contextPath}/ipta/updIptaState',
                        dataType : 'json',
                        cache : false,
                        data : [ {name : 'deptCode',value : ids} ,
                            {name : 'enabled',value:'0'}],
                        success : function(data) {
                            qryList();
                            layer.alert(data.description, {'title': '操作提示', icon: 0});
                        },
                        error : function () {
                            layer.alert('<spring:message code="common.op.error"/>', {'title': '操作提示', icon: 0});
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

<div class="main-div" style="width:100%">
	<%-- 搜索条件--开始 --%>
	<div data-qryMethod funname="qryList" class="ui-search-header ui-search-box" id="qryView-div" style="display: inline; ">
		<div style="float: right">
		编码：<input name="deptCodes" size="8" data-cnd="true">&nbsp;&nbsp;
		名称：<input name="deptNames" size="8" data-cnd="true">&nbsp;&nbsp;
		<button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
		<button  class="ui-search-btn ui-btn-bg-yellow"  onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
		</div>
		<shiro:hasPermission name="PIVAS_BTN_234"><a class="ui-search-btn ui-btn-bg-green" id="synDataDicBtn"><i class="am-icon-refresh"></i><span><spring:message code='pivas.datasyn'/></span></a></shiro:hasPermission>
		<shiro:hasPermission name="PIVAS_BTN_235">
			<a class="ui-search-btn ui-btn-bg-green" id="startBtn"><i class="am-icon-play"></i><span>启用病区</span></a>
		</shiro:hasPermission>
		<shiro:hasPermission name="PIVAS_BTN_236">
			<a class="ui-search-btn ui-btn-bg-red" id="stopBtn"><i class="am-icon-stop"></i><span>停用病区</span></a>
		</shiro:hasPermission>
	</div>
	<%-- 搜索条件--结束 --%>
	<table class="table datatable ui-data-table display dataTable no-footer">
		<thead>
		<tr>
			<th><input type='checkbox' name='cbs'/></th>
			<th><spring:message code="pivas.inpatientarea.code"/></th>
			<th><spring:message code="pivas.inpatientarea.name"/></th>
			<th>病区别名</th>
			<th>是否可用</th>
			<th>操作</th>
		</tr>
		</thead>
	</table>

        	
	
	<div id="editView-div" title="<spring:message code='pivas.druglabel.update'/>" align="center" style="display: none;">
		<form id="editView-form" action="" method="post">
			<input type="hidden" id="labelId" name="labelId"/>
				<div class="popup">
					<div class="row">
						<div class="column">
							<label class="tit">编码</label>
							<input type="text" style="background-color: #F5F5F5;" class="condition" name="deptCode" id="deptCode" maxlength="32" readonly="readonly" title="<spring:message code='common.op.remind2'/>"/>
						</div>
						<div class="column">
							<label class="tit">病区名</label>
							<input type="text" style="background-color: #F5F5F5;"  class="condition" name="deptName" id="deptName"  readonly="readonly"  maxlength="32" title="<spring:message code='common.op.remind2'/>"/>
						</div>
						
						<div class="column">
							<label class="tit">病区别名</label>
							<input type="text" style="background-color: #F5F5F5;"  class="condition" name="deptAliasName" id="deptAliasName"  maxlength="32" title="<spring:message code='common.op.remind2'/>"/>
						</div>
						
						<div class="column">
							<label class="tit"><spring:message code='medic.labelisActive'/></label>
							<select id="enabled"  name="enabled" readonly empty="false" style="width: 184px;">
								<option value="0"><spring:message code="common.no"/></option>
								<option value="1"><spring:message code="common.yes"/></option>
							</select>
						</div>
					</div>
				</div>
		</form>
	</div>
        
        
</div>

</body>

</html>