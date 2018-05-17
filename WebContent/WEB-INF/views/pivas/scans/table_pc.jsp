<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css">

#pcTable tbody > tr > td{
word-break:break-all;
height:34px;
}

</style>
</head>
<body>
<table id="pcTable" width="100%"></table>
<%--<table id="pcDataTable" >
    <thead>
    <tr>
        <td align="left" width="120px">病区</td>
    </tr>
    </thead>
</table>--%>
</body>
<script type="text/javascript">
var tableHeight = $("#tabs").height() - 60;
var tableWidth = $("#tabs").width() - 10;

var pcTitle = ${pcTitle};
var yyrq = "${yyrq}";
var inpatientStr = "${inpatientStr}";
var pcStr = "${pcStr}";
var checkType = "${checkType}";
var pcDataTable;
var pcColumns = [{"data": "", "bSortable": false}];
var paramAll;
$(function(){
	//pici信息表头
	var pcCol = 
	[	
	   	{display: '病区', name : 'deptname', width : 120, sortable : false, align: 'left'}
	];

    parseColumn(pcColumns);
	addColumn(pcCol);
	console.log(pcColumns);
	//批次信息
	$("#pcTable").flexigrid({
		width : tableWidth,
		height : tableHeight,
		url: "${pageContext.request.contextPath}/scans/queryPC",
		dataType : 'json',
		colModel : pcCol,
		resizable : false, //resizable table是否可伸缩
		useRp : false,
		usepager : false, //是否分页
		autoload : false, //自动加载，即第一次发起ajax请求
		hideOnSubmit : true, //是否在回调时显示遮盖
		onSuccess:function(){
		}
	});
	
	qryPcTable();
	
});

function initPcDataTable() {
    pcDataTable = $('#pcDataTable').DataTable({
        "dom": '<"toolbar">frtip',
        "searching": false,
        "processing": true,
        "serverSide": true,
        "select": true,
        "ordering": true,
        "order": [],
        "paging": false,
        "pageLength": 20,
        "scrollX": true,
        "language": {
            "url": "${pageContext.request.contextPath}/assets/datatables/cn.json"
        },
        "ajax": {
            "url": "${pageContext.request.contextPath}/scans/queryPC",
            "type": "post",
            "data": function (d) {
                var params = [];
                if (paramAll) {
                    params = paramAll.concat();
                }
                for (var index in params) {
                    d[params[index].name] = params[index].value;
                }
            },
            "dataSrc": function (data) {
                data.data = data.rawRecords;
                data.recordsFiltered = data.total;
                data.recordsTotal = data.total;
                return data.data;
            }
        },
        "columns": pcColumns,
        "columnDefs": [
            {
                targets:0,
                data: null,
                defaultContent:"总计",
            },
        ]
    });
}

function qryPcTable(){
	var params =
	[
    	{"name":"yyrq","value":yyrq},
   		{"name":"inpatientStr","value":inpatientString},
   		{"name":"pcStr","value":pcStr},
   		{"name":"checkType","value":checkType}
	];
	
	$('#pcTable').flexOptions({
		newp: 1,
		extParam: params,
    }).flexReload();

	paramAll = params;

	if (pcDataTable) {
	    pcDataTable.ajax.reload();
    } else {
	    initPcDataTable();
    }
}

function parseColumn(col) {
    for (var i in pcTitle) {
        col.push({"data": 'pcnumber_'+ i, "bSortable": false});
        $('#pcDataTable').find('thead tr').append('<td  width="120px" align="left">' + pcTitle[i]['pcname'] + '</td>')
    }
}

function addColumn(pcCol){
	
	for(var m in pcTitle){
		var pcname = pcTitle[m].pcname;
		pcCol.push({display: pcname, name : 'pcnumber_'+ m, width : 120, sortable : false, align: 'left'});
	}
	
}

</script>
</html>