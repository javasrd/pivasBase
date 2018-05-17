<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
<style>
.Tcontrol td{text-align: center;}
</style>
<head>
	<script>

	<%-- 批次下拉框html --%>
	var batchSelectHtml = "";
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
                "url": "${pageContext.request.contextPath}/sc/genlRule/genlRuleList",
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
                {"data": "id", "bSortable": false},
                {"data": "medicalFreqName", "bSortable": false},
                {"data": "medicalKeyword", "bSortable": false},
                {"data": "refBatchNum", "bSortable": false},
                {"data": "id", "bSortable": false}
            ],
            columnDefs: [
                {
                    targets: 0,
                    render: function (data) {
                        return "<input type='checkbox' name='cbs' value='" + data + "'/>";
                    }
                }, {
                    targets: 2,
                    render: function (data) {
                        return data == undefined ? "" : data;
                    }
                },{
                    targets: 3,
                    render: function (data) {
                        if (data) {
                            return data + "<spring:message code="rule.record"/>";
                        } else {
                            return data;
                        }
                    }
                }, {
                    targets: 4,
                    render: function (data) {
                        <shiro:hasPermission name="PIVAS_BTN_335">
                        return "<a class='button btn-bg-green' href='javascript:editRule(" + data + ");'><spring:message code='common.edit'/></a>";
                        </shiro:hasPermission>
                        <shiro:lacksPermission name="PIVAS_BTN_335">
                        return "";
                        </shiro:lacksPermission>
                    }
                }
            ]
        });
    }

	function editRule(id) {
        if (id != "") {
            $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/sc/genlRule/getRule',
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
                        $("#editView-div input").each(function(){
                            var name = $(this).attr("name");
                            $(this).val(data[name]);
                        });
                        $("#editView-div select").each(function(){
                            var name = $(this).attr("name");
                            $(this).combobox("reset", data[name]);
                        });
                        if(data.ruleRefBatchList){
                            $.each(data.ruleRefBatchList, function(i, d){
                                addRow();
                                var $tr = $('#batchListTable tbody tr:eq('+ i +')');
                                $tr.find("input[name$='].serialNumber']").val(sdfun.fn.ifnull(d["serialNumber"]));
                                $tr.find("select[name$='].batchId']").val(sdfun.fn.ifnull(d["batchId"]));
                                $tr.find("select[name$='].isFixedBatch']").val(sdfun.fn.ifnull(d["isFixedBatch"]));
                            });
                        }
                    }
                    $("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='common.edit'/>");
                    $("#editView-div").dialog("open");
                }
            });
        } else {
            message({html: '<spring:message code="common.plzSelectOneRecourd"/>',showConfirm: true});
        }
    }
	
	$(function() {
		$("#editView-form select").combobox();
		
		querySelectData();

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
			width: 600,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var bValid = validateInput("editView-div") == null?true:false;
					if (!bValid ) {
						return ;
					}
					var url = "${pageContext.request.contextPath}/sc/genlRule/addRule";
					var id = $("#editView-div").find("input[name='id']").val();
					if(id && id!=""){
						url = "${pageContext.request.contextPath}/sc/genlRule/updRule";
					}
					$("#editView-form").attr("action", url);
					if($('#batchListTable tbody tr').length == 1){
						message({html:"<spring:message code="rule.plzSelectBatch"/>"});
						return;
					}
					_prepareIndex_();
					$("#editView-form").submit();
				},
				"<spring:message code='common.cancel'/>": function() {
					$(this).dialog("close");
				}
			},
			close: function() {
				resetForm("editView-div");
				$('#batchListTable tbody tr').each(function(){
					if($(this).attr("id") != "addTr"){
						$(this).remove();
					}
				});
                qryList();
			}
		});
		
		<%-- 新增按钮 --%>
		$( "#addDataDicRoleBtn").bind("click",function(){
			$("#editView-div").prev().find('.ui-dialog-title').text("<spring:message code='common.add'/>");
			querySelectData("add");
		});
		
		$("#deleteDataDicRoleBtn").bind("click",function(){
			var ids = getSelectedRow();
			if (ids && ids.length >0) {
                layer.confirm('<spring:message code="common.deleteConfirm"/>', {
                    btn: ['确定', '取消'], icon: 3, title: '提示'
                }, function () {
                    $.ajax({
                        type : 'POST',
                        url : '${pageContext.request.contextPath}/sc/genlRule/delRule',
                        dataType : 'json',
                        cache : false,
                        data : [{name : 'id', value : ids}],
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
	
	function querySelectData(type){
		$.ajax({
			type:'POST',
			url:'${pageContext.request.contextPath}/sc/genlRule/queryBatFreq',
			dataType:'json',
			cache : false,
			success:function(data){
				if(data.success == true){
					var html = [];
					<%-- 频次 --%>
					html.push('<option value=""><spring:message code="common.select"/></option>');
					$.each(data.freqList, function(i, d){
						html.push('<option value="'+ d.id +'">');
						html.push(sdfun.fn.htmlDecode(d.name));
						html.push('</option>');
					});
					$("#freqSelect").html(html.join(""));
                    <%-- 批次 --%>
					html = [];
					html.push("<select name='ruleRefBatchList[index].batchId'");
					html.push(" onchange='changeBatchId(this);'");
					html.push(" style='width:130px;' empty='false' readonly >");
					html.push('<option value=""><spring:message code="common.select"/></option>');
					$.each(data.batchList, function(i, d){
						html.push('<option value="'+ d.id +'">');
						html.push(d.name);
						html.push('</option>');
					});
					html.push("</select>");
					batchSelectHtml = html.join("");
				}
				resetForm("editView-div");
				if(type == "add"){
					$("#editView-div").dialog("open");
				}
			}
		});
	}
	
	function changeBatchId(obj){
		var repeat = false;
		$("#batchListTable select").each(function(){
			var val = $(this).val();
			if(val !="" && this != obj && val == $(obj).val()){
				$(obj).val("");
				repeat = true;
				return false;
			}
		});
		
		if(repeat){
			message({html:"<spring:message code="rule.batchRepeat"/>"});
			return false;
		}
	}

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
	
	<%-- 把name=xxx[index]中的index替换成下标数字 --%>
	function _prepareIndex_() {
		$('#batchListTable tbody tr').each(function() {
			var index = this.rowIndex - 1;
			$(this).find(":input[name*='].']").each(function() {
				this.name = this.name.replace(/\[(\d+|index)\]/, '[' + index + ']');
			});
		});
	}
	
	<%-- 动态添加行 --%>
	function addRow(){
		var length = $("#batchListTable tbody").find("tr").length;
		var html=[];
		html.push("<tr>");
		html.push("<td>");
		html.push("<input name='ruleRefBatchList[index].serialNumber' empty='false' regex='int' minvalue='1' maxvalue='2147483647' ");
		html.push("readonly  value='"+length+"' />");
		<%-- html.push("<span>"+length+"</span>"); --%>
		html.push("</td>");
		html.push("<td>");
		html.push(batchSelectHtml);
		html.push('<span style="color:red; margin-left: 5px;">*</span>');
		html.push("</td>");
		html.push("<td>");
		html.push("<select name='ruleRefBatchList[index].isFixedBatch' readonly>");
		html.push('<option value="0"><spring:message code="common.no"/></option>');
		html.push('<option value="1"><spring:message code="common.yes"/></option>');
		html.push("</select>");
		html.push("</td>");
		html.push("<td>");
		html.push('<a onclick="delRow(this)"><spring:message code="common.del"/></a>');
		html.push("</td>");
		html.push("</tr>");
		$("#addTr").before(html.join(""));
	}
	<%-- 删除行 --%>
	function delRow(obj){
		$(obj).parent().parent().remove();
		$("#batchListTable tbody").find("tr").each(function(i, n){
			var td = $(this).find("input");
			$(td).val(i+1)
		});
	}
	
</script>
</head>
<body>

<div class="main-div" style="width:100%">
	<%-- 搜索条件--开始 --%>
	<div data-qryMethod funname="qryList"   class="ui-search-header ui-search-box"  id="qryView-div" style="display: inline; ">
		<div style="float: right">
		<input placeholder="关键字" name="medicalKeywords" size="20" data-cnd="true">&nbsp;&nbsp;
		<button  class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
		<button class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
		</div>
		<shiro:hasPermission name="PIVAS_BTN_332">
			<a class="ui-search-btn ui-btn-bg-green" id="addDataDicRoleBtn"><i class="am-icon-plus"></i><span><spring:message code='common.add'/></span></a>
		</shiro:hasPermission>
		<shiro:hasPermission name="PIVAS_BTN_333">
			<a class="ui-search-btn ui-btn-bg-red" id="deleteDataDicRoleBtn"><i class="am-icon-trash"></i><span><spring:message code='common.del'/></span></a>
		</shiro:hasPermission>
	</div>
	<%-- 搜索条件--结束 --%>
    <table class="table datatable ui-data-table display dataTable no-footer">
        <thead>
        <tr>
            <th><input type='checkbox' name='cbs'/></th>
            <th><spring:message code="rule.freq"/></th>
            <th><spring:message code="rule.medicalKeyword"/></th>
            <th><spring:message code="rule.refBatchNum"/></th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>

<%-- 新增编辑弹出框 --%>
<div id="editView-div" title="<spring:message code='common.add'/>" align="center" style="display: none;">
<form id="editView-form" action="" method="post">
	<input type="hidden" name="id"/>
    <div class="popup" style="height: 60px;">
    	<div class="row">
			<div class="column columnL">
				<label class="tit"><spring:message code='rule.freq'/></label>
				<select id="freqSelect" name="medicalFrequencyId" style="width: 150px;" empty="false" readonly>
				</select>
				<span class="mand">*</span>
			</div>
			<div class="column columnR">
				<label class="tit"><spring:message code='rule.medicalKeyword'/></label>
				<input type="text" name="medicalKeyword" maxlength="32" style="width: 150px;" title="<spring:message code='common.op.remind2'/>"/>
			</div>
		</div>
    </div>
    <div class="layeForm" style="margin: 0;overflow: auto;width: 100%;">
		<table id="batchListTable" class="Tcontrol" style="vertical-align: top; margin-left: 0px;">
			<thead>
				<tr class="sdfunTableHeaderTr" style="font-size:14px;">
					<th class="thC1"><spring:message code="rule.freqOrderNum"/></th>
					<th class="thC1"><spring:message code="rule.batch"/></th>
					<th class="thC1"><spring:message code="rule.fixedBatch"/></th>
					<th class="thC1" style="width: 80px;"><spring:message code="common.op"/></th>
				</tr>
			</thead>
			<tbody>
				<tr id="addTr">
					<td style="text-align: center;"><a onclick="addRow();"><spring:message code="common.add2"/></a></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
</div>

</body>

</html>