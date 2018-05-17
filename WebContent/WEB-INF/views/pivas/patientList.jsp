<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../common/common.jsp" %>

<html>
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
                "url": "${pageContext.request.contextPath}/pati/patiList",
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
                {"data": "inhospNo", "bSortable": false},
                {"data": "patName", "bSortable": false},
                {"data": "sexName", "bSortable": false},
                {"data": "wardName", "bSortable": false},
                {"data": "case_ID", "bSortable": false},
                {"data": "birthDay", "bSortable": false},
                {"data": "ageDetail", "bSortable": false},
                {"data": "avdp", "bSortable": false},
                {"data": "bedNo", "bSortable": false},
                {"data": "hosOutTime", "bSortable": false}
            ]
        });
    }
	
	$(function() {
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

        <%-- 同步按钮 --%>
        $("#synDataDicBtn").bind("click", function () {
            layer.confirm('<spring:message code="common.startSynTask"/>', {
                btn: ['确定', '取消'], icon: 3, title: '提示'
            }, function () {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/pati/synPati',
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
    <div data-qryMethod funname="qryList" class="ui-search-header ui-search-box" id="qryView-div" style="display: inline; ">
        <div style="float: right">
        <input placeholder="名称" name="patNames" size="8" data-cnd="true">&nbsp;&nbsp;
        <input placeholder="流水号" name="inhospNos" size="8" data-cnd="true">&nbsp;&nbsp;
        <button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
        <button class="ui-search-btn ui-btn-bg-yellow"  onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
        </div>
        <shiro:hasPermission name="PIVAS_BTN_244"><a class="ui-search-btn ui-btn-bg-green" id="synDataDicBtn"><i class="am-icon-refresh"></i><span><spring:message code='pivas.datasyn'/></span></a></shiro:hasPermission>
    </div>
    <%-- 搜索条件--结束 --%>
    <table class="table datatable ui-data-table display dataTable no-footer">
        <thead>
        <tr>
            <th><spring:message code="pivas.patient.code"/></th>
            <th><spring:message code="pivas.patient.name"/></th>
            <th><spring:message code="pivas.patient.sex"/></th>
            <th><spring:message code="pivas.patient.wardName"/></th>
            <th><spring:message code="pivas.patient.caseid"/></th>
            <th><spring:message code="pivas.patient.birthday"/></th>
            <th><spring:message code="pivas.patient.age"/></th>
            <th><spring:message code="pivas.patient.avdp"/></th>
            <th><spring:message code="pivas.patient.bedNo"/></th>
            <th>预出院时间</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>