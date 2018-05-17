<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>
<html>

<head>
<style type="text/css">
*{margin:0 auto;padding:0;list-style-type:none;}
body{font:12px/180% Arial, Helvetica, sans-serif, "新宋体";}
/* tab */
#tab{position:relative;}
#tab .tab_menu{width:100%;}
#tab .tab_menu li{float:left;width:92px;height:30px;line-height:30px;border:1px solid #ccc;border-bottom:0px;cursor:pointer;text-align:center;margin:0 -50px 0 0;}
#tab .tab_box{height:100px;clear:both;top:15px;border:1px solid #CCC;background-color:#ffffff;}
#tab .tab_menu .selected{background-color:#5B7EC2;cursor:pointer;color:#FFFFFF;}
.hide{display:none;}
.tab_box div{padding:5px;} 
spane{ text-align:left;
}
input, textarea {
background-color:#F5F5F5 ;
}
/*heard*/
#top {
    border-bottom: 1px solid #cacaca;
    padding-left: 2px;
    background-color: #ededed;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#fcfcfc), to(#dedede));
    background-image: -webkit-linear-gradient(top, #fcfcfc, #dedede);
    background-image: -moz-linear-gradient(top, #fcfcfc, #dedede);
    background-image: -ms-linear-gradient(top, #fcfcfc, #dedede);
    background-image: -o-linear-gradient(top, #fcfcfc, #dedede);
    background-image: linear-gradient(to bottom, #fcfcfc, #dedede);
}
#onetd {
    border-left: 1px solid #cacaca;
    padding-left: 14px;
}
#twotd {
    background-color: #5382b9;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#729fcf), to(#3465a4));
    background-image: -webkit-linear-gradient(top, #729fcf, #3465a4);
    background-image: -moz-linear-gradient(top, #729fcf, #3465a4);
    background-image: -ms-linear-gradient(top, #729fcf, #3465a4);
    background-image: -o-linear-gradient(top, #729fcf, #3465a4);
    background-image: linear-gradient(to bottom, #729fcf, #2A77DA);
}
#spane{
    position: relative;
    width: 24px;
    height: 24px;
    display: inline-block;
    margin-left: -12px;
    margin-top: 3px;
    box-shadow: -1px 1px 2px rgba(255, 255, 255, 0.2), inset -1px 1px 1px rgba(0, 0, 0, 0.2);
    background-color: #dedede;
    background: -moz-linear-gradient(135deg, #dedede, #fcfcfc);
    background: -o-linear-gradient(135deg, #fcfcfc, #dedede);
    background: -webkit-gradient(linear, left top, right bottom, from(#fcfcfc), to(#dedede));
    background: -ms-linear-gradient(top, #fcfcfc, #dedede);
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
    -webkit-transform: rotate(45deg);
    -moz-transform: rotate(45deg);
    -ms-transform: rotate(45deg);
    -o-transform: rotate(45deg);
    transform: rotate(45deg);
}
</style>
<script type="text/javascript">
var _gridWidth = 0;
	var _gridHeight = 0; 
	var rowHeight = 35;
	//var mainDoc = window.parent.parent.document;
	//页面自适应
	function resizePageSize(){
		//alert('1');
		_gridWidth = getGridWidth(0.78);
		$('#flexGridRole').flexResize(_gridWidth,0);

		//重置主页面内容的高度
		if (top.resetMainContent) {
			top.resetMainContent();
		}
	}
$(function() {
		$(window).resize(function(){
		});
		resizePageSize();
		var aaaa=<%=request.getParameter("aGE")%>;
		var _columnWidth = _gridWidth / 6 * 0.99;
		$("#chargedetailsinfo").flexigrid({
			width : _gridWidth,
			dataType : 'json',
			url: "${pageContext.request.contextPath}/chargedetails/chargeDetailList",
			colModel : [ 
						 {display : "操作人",name : 'sFYSMC',width : _columnWidth, sortable : true,align : 'center'},
						 {display : "单价",name : 'dRUGNAME',width : _columnWidth, sortable : true,align : 'center'},
						 {display : "收费步骤",name : 'yDZT',width : _columnWidth, sortable : true,align : 'center'},
						 {display : "配置费收取状态",name : 'pZFZT',width : _columnWidth, sortable : true,align : 'center'},
						 {display : "操作",name : 'dRUGNAME',width : _columnWidth, sortable : true,align : 'center'},
						 {display : "备注",name : 'dRUGNAME',width : _columnWidth, sortable : true,align : 'center'}
			      ],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : false, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			showcheckbox : false, //是否显示多选框
			rowhandler : null, //是否启用行的扩展事情功能,在生成行时绑定事件，如双击，右键等
			rowbinddata : true,
			numCheckBoxTitle : "<spring:message code='common.selectall'/>"
		});
		qryList();
	});
	function qryList(param){
		///alert("chaxun");
		$('#chargedetails').flexOptions({
			newp: 1,
			extParam: param||[],
			url: "${pageContext.request.contextPath}/chargedetails/chargeDetailList"
        }).flexReload();
	}
	
</script>
</head>

<body>
<table border="0" cellpadding="0" cellspacing="20"  width="100%" style="font-weight: bold;">
<tr>
	<td>
	<label>
	<div align="right">医嘱编号：</div>
	</label>
	<td><input type="text" value="<%=request.getParameter("aCTORDER_NO")%>"></td>
	</td>
	<td>
	<label>
	<div align="right">病人：</div>
	</label>
	<td><input type="text" value="<%=request.getParameter("pATNAME")%>"></td>
	</td>
	<td><label>
        <div align="right">病人住院号：</div>
	  </label>
    <td><input type="text" value="<%=request.getParameter("cASE_ID")%>"></td>    
    </td>
</tr>
<tr>
	<td>
	<label>
	<div align="right">医嘱状态：</div>
	</label>
	<td><input type="text" value="<%=request.getParameter("yDZT")%>"></td>
	</td>
	<td>
	<label>
	<div align="right">病人床号：</div>
	</label>
	<td><input type="text" value="<%=request.getParameter("bEDNO")%>"></td>
	</td>
	<td><label>
        <div align="right">性别：</div>
	  </label>
    <td><input type="text" value="<%=request.getParameter("sEX")%>"></td>
</tr>

<tr>
	<td>
	<label>
	<div align="right">病区：</div>
	</label>
	<td><input type="text" value="<%=request.getParameter("dEPTNAME")%>"></td>
	</td>
	<td><label>
        <div align="right">操作人：</div>
	  </label>
    <td><input type="text" value="<%=request.getParameter("sFYSMC")%>"></td>
    </td>
	<td><label>
        <div align="right">药品名称：</div>
	  </label>
    <td><input type="text" value="<%=request.getParameter("qUANTITY")%>"></td>
    </td>
</tr>
<tr>
  <td><label>
      <div align="right">开方医生：</div>
    </label>
  <td><input type="text" value="<%=request.getParameter("sFYSMC")%>"></td>
	</td>
	<td><label>
        <div align="right">单次计量：</div>
	  </label>
    <td><input type="text" value="<%=request.getParameter("dOSE")%>"></td>
    </td>
	<td><label>
        <div align="right">年龄：</div>
	  </label>
    <td><input type="text" value="<%=request.getParameter("aGE")%>"></td>
    </td>
</tr>

</table>
<div id="tab" style="float: left;margin-left: 50px;">
	<ul class="tab_menu">
    	<li class="selected"><strong>药单</strong></li>
    </ul>
</div>
<div style="height: 100%;float:left;margin-top: 30px;margin-left: -41px;width: 70%;">
 <div class="tbl">
            <table id="chargedetailsinfo" style="display: block;margin: 0px;"></table>
</div>
</div>
</body>

</html>