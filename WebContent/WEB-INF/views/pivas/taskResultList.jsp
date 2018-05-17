<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet"/>

<head>
    <style type="text/css">
        .search-div {
            padding: 2.5em 1em 1em 0em;
        }
    </style>
    <script>

        var _gridWidth = 0;
        var _gridHeight = 0;
        var datatable;
        var qryParam;
        //页面自适应
        function resizePageSize() {
            _gridWidth = $(document).width() - 12;
            /*  -189 是去掉左侧 菜单的宽度，   -12 是防止浏览器缩小页面 出现滚动条 恢复页面时  折行的问题 */
            _gridHeight = $(document).height() - 32 - 100;
            /* -32 顶部主菜单高度，   -90 查询条件高度*/
        }
        function initDatatable(){
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
                    "url": "${pageContext.request.contextPath}/taskRsut/getTaskResultList",
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
                    {"data": "taskID", "bSortable": false},
                    {"data": "taskName", "bSortable": false},
                    {"data": "taskTypeName", "bSortable": false},
                    {"data": "taskResultName", "bSortable": false},
                    {"data": "taskStartTime", "bSortable": false},
                    {"data": "taskStopTime", "bSortable": false},
                    {"data": "taskContentTypeName", "bSortable": false},

                ],
                columnDefs: [
                    {
                        targets: 0,
                        render: function (data, type, row) {
                            return "<input type='checkbox' name='cbs' value='"+data+"'/>";
                        }
                    },
                ],
            });
        }
        $(function () {
            $("#editView-form sdfun:select").combobox();
            $(window).resize(function () {
                //resizePageSize();
            });
            resizePageSize();
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
         /*   var _columnWidth = (parseInt(_gridWidth) - 55) / 6;
            $("#flexiGridId").flexigrid({
                width: _gridWidth,
                height: _gridHeight,
                url: "${pageContext.request.contextPath}/taskRsut/getTaskResultList",
                dataType: 'json',
                colModel: [
                    {display: 'taskID', name: 'taskID', width: 50, sortable: false, align: 'center', hide: 'true'}
                    {
                        display: '<spring:message code="pivas.confingfee.costname"/>',
                        name: 'taskName',
                        width: _columnWidth,
                        sortable: true,
                        align: 'left'
                    },
                    {
                        display: '<spring:message code="pivas.synsetting.tasktype"/>',
                        name: 'taskTypeName',
                        width: _columnWidth,
                        sortable: false,
                        align: 'left'
                    },
                    {
                        display: '<spring:message code="pivas.synsetting.taskresult"/>',
                        name: 'taskResultName',
                        width: _columnWidth,
                        sortable: false,
                        align: 'left'
                    },
                    {
                        display: '<spring:message code="pivas.synsetting.taskexecstarttime"/>',
                        name: 'taskStartTime',
                        width: _columnWidth,
                        sortable: false,
                        align: 'left'
                    },
                    {
                        display: '<spring:message code="pivas.synsetting.taskexecstoptime"/>',
                        name: 'taskStopTime',
                        width: _columnWidth,
                        sortable: false,
                        align: 'left'
                    },
                    {
                        display: '<spring:message code="pivas.synsetting.contenttype"/>',
                        name: 'taskContentTypeName',
                        width: _columnWidth,
                        sortable: false,
                        align: 'left'
                    }
                ],
                resizable: false, //resizable table是否可伸缩
                usepager: true,
                useRp: true,
                usepager: true, //是否分页
                autoload: false, //自动加载，即第一次发起ajax请求
                hideOnSubmit: true, //是否在回调时显示遮盖
                showcheckbox: true, //是否显示多选框
                rowbinddata: true,
                numCheckBoxTitle: "<spring:message code='common.selectall'/>"
            });
            qryList();*/

            $("#aSearch").bind("click", function () {
                qryList();
            });

        });

        function qryList(param) {
           /* $('#flexiGridId').flexOptions({
                newp: 1,
                extParam: param || [],
                url: "${pageContext.request.contextPath}/taskRsut/getTaskResultList"
            }).flexReload();*/
            qryParam = param;
            datatable.ajax.reload();
        }

        function closeDeal() {
            $("#editView-div").find("input").each(function () {
                $(this).val("");
            });
            $("#editView-div").find("textarea").each(function () {
                $(this).val("");
            });
        }
    </script>
</head>
<body>

<div class="main-div" style="width:100%">
    <div data-qryMethod funname="qryList" class="ui-search-header ui-search-box" id="qryView-div" style="display: inline; ">
        <input placeholder="名称" name="taskNames" size="8" data-cnd="true">&nbsp;&nbsp;
        <button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;
        <button  class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button>&nbsp;&nbsp;
    </div>
    <%--<div class="search-div">--%>
    <%--</div>--%>
    <%-- 搜索条件--结束 --%>
    <div class="tbl">
        <%--<table id="flexiGridId" style="display: block;margin: 0px;"></table>--%>
        <table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
            <thead>
            <tr>
                <th><input type='checkbox' name='cbs'/></th>
                <th><spring:message code="pivas.confingfee.costname"/></th>
                <th><spring:message code="pivas.synsetting.tasktype"/></th>
                <th><spring:message code="pivas.synsetting.taskresult"/></th>
                <th><spring:message code="pivas.synsetting.taskexecstarttime"/></th>
                <th><spring:message code="pivas.synsetting.taskexecstoptime"/></th>
                <th><spring:message code="pivas.synsetting.contenttype"/></th>
            </tr>
            </thead>
        </table>
    </div>
    <%--
    <table data-qryMethod  funname="qryList">
        <td>&nbsp;&nbsp;<div>名称:</div></td>
        <td>&nbsp;<input name="taskNames" size="8" data-cnd="true"></td>
        <td>&nbsp;&nbsp;<button onclick="qryByCnd()">搜索</button></td>
        <td>&nbsp;&nbsp;<button onclick="cndRest()">清空</button></td>
    </table>--%>
    <!-- 搜索条件--开始 -->
    <%--<div class="oe_searchview">
        <div class="oe_searchview_facets">
            <div class="oe_searchview_input oe_searchview_head"></div>
            <div class="oe_searchview_input" id="inputsearch">
                <input id="txt" type="text" style="width:280px;" class="oe_search_txt" onkeydown="this.onkeyup();"
                       onkeyup="this.size=(this.value.length>1?this.value.length:1);" size="1"/>
            </div>
        </div>
        <div class="oe_searchview_clear" onclick="clearclosedinputall();"></div>
        <button class="oe_searchview_search" type="button">搜索</button>
        <button class="oe_searchview_search" type="button" id="searchbtn">搜索</button>
        <div class="oe-autocomplete"></div>
        <div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
            <cite>请选择...</cite>
            <ul class="ulQry" style="-webkit-border-radius: 20;" funname="qryList">
                <li show="名称" name="taskNames">搜索 名称：<span class="searchVal"></span></li>
            </ul>
        </div>
    </div>--%>
    <!-- 搜索条件--结束 -->
 <%--   <div id="qryView-div">
        <div class="search-div">
        </div>
        <div class="tbl">
            <table id="flexiGridId" class="table-data" style="display: block;margin: 0px;"></table>
        </div>
    </div>--%>


</div>
</body>

</html>