<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
<head>
<style type="text/css">
#tab{position:relative;}
#tab .tab_menu{width:100%;float:left;;}
#tab .tab_menu li{float:left;width:92px;height:30px;line-height:30px;border:1px solid #ccc;border-bottom:0px;cursor:pointer;text-align:center;margin:0 2px 0 0;}
#tab .tab_box{height:100px;clear:both;top:30px;border:1px solid #CCC;background-color:##5B7EC2;}
#tab .tab_menu .selected{background-color:#5B7EC2;cursor:pointer;color: white;}
.hide{display:none;}
.tab_box div{padding:10px;} 
</style>
	<script>
	
	var _gridWidth = 0;
	var _gridHeight = 0; 
	
	//页面自适应
	function resizePageSize(){
		_gridWidth = $(document).width()-12;/*  -189 是去掉左侧 菜单的宽度，   -12 是防止浏览器缩小页面 出现滚动条 恢复页面时  折行的问题 */
		_gridHeight = $(document).height()-32-100; /* -32 顶部主菜单高度，   -90 查询条件高度*/
	}
	
	$(function() {
		$(window).resize(function(){
		});
		resizePageSize();

		var _columnWidth = (parseInt(_gridWidth)-55) / 16;
		$("#chargedetails").flexigrid({
			width : _gridWidth,
			height : _gridHeight,
			url: "${pageContext.request.contextPath}/chargedetails/chargeDetailList",
			dataType : 'json',
			colModel : [ 
						{display: '<spring:message code="pivas.yz1.wardname"/>', name : 'wARDNAME', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.yz2.patname"/>', name : 'pATNAME',   width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.yz2.age"/>', name : 'aGE',width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.yz1.freqCode"/>', name : 'yDPQ',width : _columnWidth, sortable : true, align: 'center'},
						{display: '批次', name : 'nAME_',width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="scans.drugName"/>', name : 'dRUGNAME', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.yz1.dose"/>', name : 'dOSE',  width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="scans.quantity"/>', name : 'qUANTITY',  width : _columnWidth, sortable : true, align: 'center'},
						{display: '配置费收取状态', name: 'pZFZT', width: _columnWidth, sortable: false, align: 'center'},
						{display: '配置费收取时间', name : 'pZFSQRQ', width : _columnWidth, sortable : true, align: 'center'},
						{display: '医嘱编号', name : 'aCTORDER_NO', width : _columnWidth, sortable : true, align: 'center'},
						{display: '药单执行状态', name : 'yDZXZT', width : _columnWidth, sortable : true, align: 'center'},
						{display: '床号', name : 'bEDNO', width : _columnWidth, sortable : true, align: 'center'},
						{display: '病人唯一住院号', name : 'cASE_ID', width : _columnWidth, sortable : true, align: 'center'},
						{display: '性别', name : 'sEX', width : _columnWidth, sortable : true, align: 'center'},
						{display: '医嘱药师', name : 'sFYSMC', width : _columnWidth, sortable : true, align: 'center'}
			],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			rowbinddata : true,
			rowhandler : rowDbclick
		});
		qryList();
	});
	
	function qryList(param){
		$('#chargedetails').flexOptions({
			newp: 1,
			extParam: param||[],
			url: "${pageContext.request.contextPath}/chargedetails/chargeDetailList"
        }).flexReload();
	}
	
	function rowDbclick(r) {
		$(r).dblclick(
		function() {
			//获取该行的所有列数据
			var columnsArray = $(r).attr('ch').split("_FG$SP_");
			var yaid=columnsArray[0];
			var dEPTNAME=columnsArray[0];
			var pATNAME=columnsArray[1];
			var aGE=columnsArray[2];
			var yDPQ=columnsArray[3];
			var dRUGNAME=columnsArray[4];

			var qUANTITY=columnsArray[5];
			var dOSE=columnsArray[6];
			var pZFZT=columnsArray[7];
			var pZFSQRQ=columnsArray[8];
			var aCTORDER_NO=columnsArray[10];
			var yDZXZT=columnsArray[10];
			var wARDNAME=columnsArray[11];
			var bEDNO=columnsArray[12];
			var cASE_ID=columnsArray[13];
			var sEX=columnsArray[14];
			var sFYSMC=columnsArray[15];
			var src = "${pageContext.request.contextPath}/chargedetails/chargedetailsListinfo?aCTORDER_NO="+aCTORDER_NO+"&dEPTNAME="+dEPTNAME+"&pATNAME="+pATNAME+"&aGE="+aGE+"&yDPQ="+yDPQ+"&dRUGNAME="+dRUGNAME+"&dOSE="+dOSE+"&qUANTITY="+qUANTITY+"&pZFZT="+pZFZT+"&pZFSQRQ="+pZFSQRQ+"&yDZXZT="+yDZXZT+"&wARDNAME="+wARDNAME+"&bEDNO="+bEDNO+"&cASE_ID="+cASE_ID+"&sEX="+sEX+"&columnsArray="+columnsArray+"&sFYSMC="+sFYSMC;
			$('#scansCheckDialog').dialog('open').find('iframe').attr('src', src);
		});
	};
	
	
	$(function() {
		$(window).resize(function(){
			resizePageSize();
		});
		$('#scansCheckDialog').dialog({
			title: '仪表管理',
			autoOpen: false,
			width: $(window).width(),
			height: $(window).height(),
			modal: true,
			resizable: false,
			buttons: {
				'返回': function() {
					$(this).dialog('close');
					location.reload();
				}
			}
		}).prev().hide().parent().css({
			border: 'none',
			borderRadius: 0
		});
		

	});
	</script>
</head>
<body>
<sd:select name="custType" id="custType" required="true"  categoryName="pzfzt_type" tableName="sys_dict"></sd:select>
<div class="main-div" id="cdMain">
		<!-- 搜索条件--开始 -->
		<div class="oe_searchview">
     		<div class="oe_searchview_facets" >
	    	<div class="oe_searchview_input oe_searchview_head"></div>
	    <div class="oe_searchview_input"  id="inputsearch" >
	    	  <input id="txt" type="text" class="oe_search_txt" style="max-height: 18px;" width="325px;"/>
	    </div>
    		</div>
   			<div class="oe_searchview_clear" onclick="clearclosedinputall();"></div>
    			<button class="oe_searchview_search" title="重新搜索" type="button" id="searchbtn">搜索</button>
				<div class="oe-autocomplete" ></div>
				<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
					<cite>请选择...</cite>
					<ul class="ulQry" style="-webkit-border-radius: 20;" funname="qryList" >
						<li show="病区" name="categoryNames" >搜索 病区：<span class="searchVal"></span></li>
					</ul>
				</div>
		</div>
		<!-- 搜索条件--结束 -->
        <div class="tbl">
            <table id="chargedetails" style="display: block;margin: 0px;"></table>
        </div>
</div>
<!-- 扫描 -->
		<div id="scansCheckDialog" align="center" style="display:none;padding:0;margin-top:3px;overflow:auto;" autoDestroy>
			<iframe frameborder="0" marginheight="0" marginwidth="0" style="width:100%;height:100%;"></iframe>
		</div>

</body>
</html>