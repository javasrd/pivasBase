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
                "url": "${pageContext.request.contextPath}/empy/empyList",
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
                {"data": "staff_Code", "bSortable": false},
                {"data": "staff_Name", "bSortable": false}
            ]
        });
    }

	$(function() {
		initDatatable();
        <%-- 同步按钮 --%>
		$("#synDataDicBtn").bind("click",function(){
            layer.confirm('<spring:message code="common.startSynTask"/>', {
                btn: ['确定', '取消'], icon: 3, title: '提示'
            }, function () {
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/empy/synEmpy',
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

	function qryList(param){
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
		<input name="staff_Codes" placeholder="工号" size="8" data-cnd="true">&nbsp;&nbsp;
		<input name="staff_Names" placeholder="姓名" size="8" data-cnd="true">&nbsp;&nbsp;
		<button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
		<button   class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
		</div>
		<shiro:hasPermission name="PIVAS_BTN_927">
			<a class="ui-search-btn ui-btn-bg-green" id="synDataDicBtn"><i class="am-icon-refresh"></i><span><spring:message code='pivas.datasyn'/></span></a>
		</shiro:hasPermission>
	</div>
	<%-- 搜索条件--结束 --%>
    <table class="table datatable ui-data-table display dataTable no-footer">
        <thead>
        <tr>
            <th>员工号</th>
            <th>员工姓名</th>
        </tr>
        </thead>
    </table>
</div>

</body>

</html>