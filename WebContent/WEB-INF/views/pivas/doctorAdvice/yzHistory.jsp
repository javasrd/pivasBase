<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>



<head>
<!-- 
<link href="${pageContext.request.contextPath}/assets/sysCss/common/style.css" type="text/css" rel="stylesheet" />
 -->
	<style type="text/css">
	.cbit-grid div.bDiv td div {
	  cursor: pointer;
	}
	.oe_searchview{
   	line-height: 18px; 
   	border: 1px solid #ababab;
   	background: white; 
   	width:500px;
    -moz-border-radius: 13px;
    cursor: text;
    padding: 1px 0;
    float:left;
    border: 1px solid #ababab;
    margin-top: 10px;
}
	</style>
	<link href="${pageContext.request.contextPath}/assets/pivas/css/edit.css" type="text/css" rel="stylesheet" />
	
	<script type="text/javascript">
	
	var _gridWidth = 0;
	var _gridHeight = 0; 
	
	//页面自适应
	function resizePageSize(){
		_gridWidth = $(document).width()-12;/*  -189 是去掉左侧 菜单的宽度，   -12 是防止浏览器缩小页面 出现滚动条 恢复页面时  折行的问题 */
		_gridHeight = $(document).height()-32-100; /* -32 顶部主菜单高度，   -90 查询条件高度*/
		$("#flexGrid").flexResize();
	}

	$(function() {
		$(window).resize(function(){
			resizePageSize();
		});
		resizePageSize();
		
		//var _columnWidth = (parseInt(_gridWidth)-55) / 15;
		$("#flexGrid").flexigrid({
			width : _gridWidth,
			height : _gridHeight,
			url: "${pageContext.request.contextPath}/doctorAdvice/yzPageData",
			dataType : 'json',
			colModel : [ 
						{display: 'yzMainId', name : 'yzMainId', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'parentNo', name : 'parentNo', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzlx', name : 'yzlx', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzzt', name : 'yzzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzshzt', name : 'yzshzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'rucangOKNum', name : 'rucangOKNum', width : 0, sortable : false, align: 'center',hide:'true'},
						
						{display: '<spring:message code="pivas.yz2.bedno"/>', name : 'p_bedno', width : '30', sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.wardname"/>', name : 'wardName', width : '40', sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz2.patname"/>', name : 'p_patname', width : '40', sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz2.age"/>', name : 'p_age', width : 30, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return _row.p_age+ getDicValue('ageUnit',_row.p_ageunit);
						}},
						{display: '<spring:message code="pivas.yz1.freqCode"/>', name : 'freqCode', width : 60, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.drugname"/>', name : 'drugname', width : 280, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace("  ","<br>");
						}},
						{display: '<spring:message code="pivas.yz1.dose"/>', name : 'dose', width : 50, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace("  ","<br>");
						}},
						{display: '<spring:message code="pivas.yz1.doseUnit"/>', name : 'doseUnit', width : 65, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace("  ","<br>");
						}},
						{display: '<spring:message code="pivas.yz1.quantity"/>', name : 'quantity', width : 65, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace("  ","<br>");
						}},
						{display: '<spring:message code="pivas.yz1.medicamentsPackingUnit"/>', name : 'medicamentsPackingUnit', width : 65, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace("  ","<br>");
						}},
						{display: '<spring:message code="pivas.yz1.yzlx"/>', name : 'yzlx', width : 65, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return getDicValue('yzlx',v);
						}},
						{display: '<spring:message code="pivas.yz1.yzzt"/>', name : 'yzzt', width : 65, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return getDicValue('yzzt',v);
						}},
						{display: '<spring:message code="pivas.yz1.sfysmc"/>', name : 'sfysmc', width : 65, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.sfrq2"/>', name : 'sfrqS', width : 80, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.yzshzt2"/>', name : 'yzshzt', width : 70, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return getDicValue('yzshzt',v);
						}},
						{display: '<spring:message code="pivas.yz1.yzshbtgyy2"/>', name : 'yzshbtgyy', width : 120, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return _row.yzshzt==1?"":v;
						}}
						],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			//showcheckbox : true, //是否显示多选框
			rowbinddata : true,
			rowhandler : rowDbclick,
			//onrowchecked : rowChecked,
			numCheckBoxTitle : "<spring:message code='common.selectall'/>"
		});
		
		function rowDbclick(r) {
			$(r).dblclick(
			function() {
				//获取该行的所有列数据
				var columnsArray = $(r).attr('ch').split("_FG$SP_");
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/doctorAdvice/toYzInfo',
					dataType : 'html',
					cache : false,
 					data : {"parentNo":columnsArray[1]},
					success : function(data) {
						$("#yzInfo").html(data).show();
						$("#yzMain").hide();
					}
				});
			});
		}
		
		function rowChecked(r){
			var parentNoN = getFlexiGridSelectedRowText($("#flexGrid"), 3);//parentNo
			if(parentNoN && parentNoN.length >0) {
				$("#ulYZCheckMany").show();
			}else {
				$("#ulYZCheckMany").hide();
			}
		}
		
		qryList();
		
		$("#aSearch").bind("click",function(){
			qryList();
		});
		
		$("#aYZCheckMany").bind("click",function(){
			var parentNoN = getFlexiGridSelectedRowText($("#flexGrid"), 3);//parentNo 医嘱父ID
			if(parentNoN && parentNoN.length <1) {
				message({html: "<spring:message code='comm.err.param3'/>"});
				return;
			}
			var yzlxN = getFlexiGridSelectedRowText($("#flexGrid"), 4);//yzlxN	医嘱类型
			var yzztN = getFlexiGridSelectedRowText($("#flexGrid"), 5);//yzztN	医嘱状态
			var yzshztN = getFlexiGridSelectedRowText($("#flexGrid"), 6);//yzshztN	医嘱审核状态
			var rucangOKNumN = getFlexiGridSelectedRowText($("#flexGrid"), 7);//rucangOKNum	医嘱审核状态
			var row_partHasStop = 0 ;//部分数据 源端数据已停止，提示 部分医嘱已停止//0：执行 1：停止 2：撤销
			var row_partHasCheck = 0 ;//部分数据 已审核，提示是否重新审核//0：未审核 1：审核通过 2：审核不通过
			var row_partHasRuCang = 0 ;//部分数据 已进入入仓扫描阶段，不可审核
			var row_canCheck = 0 ;
			checkIdS = "";
			for(var i=0;i<parentNoN.length;i++){
				if(yzztN[i]==0){
					if(rucangOKNumN[i]==0){
						row_canCheck ++ ;
						if(checkIdS==""){
							checkIdS = parentNoN[i];
						}else{
							checkIdS = checkIdS + ","+parentNoN[i];
						}
					}else{
						row_partHasRuCang ++ ;
					}
				}else{
					row_partHasStop ++ ;
				}
				if(yzshztN[i]==1){
					row_partHasCheck ++ ;
				}
			}
			if(row_canCheck>0){
				if(row_partHasCheck){
					message({
		    			html: '<spring:message code='comm.err.param4'/>',
		    			showCancel:true,
		    			confirmText: "<spring:message code='comm.mess1'/>",
		    			cancelText: "<spring:message code='comm.mess2'/>",
		    			confirm:function(){
		    				repeatCheck = "Y" ;
		    				gotoCheckView();
		    			},
		    			cancel:function(){
		    				repeatCheck = "N" ;
		    				gotoCheckView();
		    			}
		        	});
				}else if(row_partHasStop>0){
					var mess = (row_partHasStop>0?"[<spring:message code='comm.mess3'/>]":"") + (row_partHasRuCang>0?"[<spring:message code='comm.mess4'/>]":"") ;
					message({
		    			html: mess+"，<spring:message code='comm.mess5'/>！",
		    			showCancel:false,
		    			confirm:function(){
		    				gotoCheckView();
		    			}
		        	});
				}else{
					gotoCheckView();
				}
			}else{
				message({html: "<spring:message code='comm.mess6'/>"});
			}
		});
		
		$("#divYZCheckMany").dialog({
			autoOpen: false,
			height: 370,
			width: 650,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='comm.mess7'/>": function() {
					checkSubmit(1);
				},
				"<spring:message code='comm.mess8'/>": function() {
					checkSubmit(2);
				}
			},
			close: function() {
			}
		});
		
		//新增按钮
		$( "#addDataDicRoleBtn").bind("click",function(){
			$("#divYZCheckMany").dialog("open");
		});
		
		// 同步按钮
		$("#synDataDicBtn").bind("click",function(){
			message({
				 html: '<spring:message code="common.startSynTask"/>',
				 showCancel:true,
				 confirm:function(){
					$.ajax({
						type : 'POST', 
						url : '${pageContext.request.contextPath}/doctorAdvice/synYz',
						dataType : 'json',  
						cache : false,
						data : [],
						success : function(data) {
						    qryList();
							message({data: data});
							},
						error : function () {
							 message({
							     html: '<spring:message code="common.op.error"/>',
							     showConfirm: true
							    });
							}
						});
				    }
			    });
			});
		
	});
	function qryList(){
		$('#flexGrid').flexOptions({
			newp: 1,
			extParam: [
				{"rucangOKNum":"Y"}
			],
        	url: "${pageContext.request.contextPath}/doctorAdvice/yzPageData"
        }).flexReload();
	}
	function backMain(){
		$("#yzInfo").hide().html("");
		$("#yzMain").show();
	}
	var checkIdS = "";
	var repeatCheck = "N";
	function gotoCheckView(){
		$("#divYZCheckMany").dialog("open");
	}
	function checkSubmit(newYzshzt){
		if(!$("#checkAccount").val()){
			message({html: "<spring:message code='comm.mess9'/>"});
			return ;
		}
		if(!$("#checkPass").val()){
			message({html: "<spring:message code='comm.mess10'/>"});
			return ;
		}
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
			dataType : 'json',
			cache : false,
			data : {"parentNoN":checkIdS,
				"repeatCheck":repeatCheck,
				"newYzshzt":newYzshzt,
				yzshbtglx:$("#yzshbtglx").val(),
				"yzshbtgyy":$("#yzshbtgyy").val(),
				"checkAccount":$("#checkAccount").val(),
				"checkPass":$("#checkPass").val()
			},
			success : function(response) {
				message({html: response.msg});
				if(response.code==0){
					$("#divYZCheckMany").dialog("close");
					//message({html: "审方已置为不通过"});
					$("#ulYZCheckMany").hide();
					backMain();
					qryList();
				}else{
					message({html: response.msg});
					//message(respone.mess);
				}
				
			}
		});
	}
	</script>
</head>

<body>






<div id="yzMain" class="main-div" style="width:100%">
<!-- 搜索条件--开始 -->
<div class="oe_searchview">
      <div class="oe_searchview_facets" >
	    <div class="oe_searchview_input oe_searchview_head"></div>
	    <div class="oe_searchview_input"  id="inputsearch" >
	    	  <input id="txt" type="text" class="oe_search_txt" onkeydown="this.onkeyup();" onkeyup="this.size=(this.value.length>1?this.value.length:1);" size="1"/>
	    </div>
    </div>
    <div class="oe_searchview_clear" onclick="clearclosedinputall();"></div>
    <button class="oe_searchview_search" type="button" id="searchbtn">搜索</button>
	<div class="oe-autocomplete" ></div>
	<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
		<cite>请选择...</cite>
		<ul class="ulQry" style="-webkit-border-radius: 20;" funname="qryList" >
		</ul>
	</div>
</div>
<!-- 搜索条件--结束 -->

        <div id="qryView-div">
        <div class="search-div">
    		<div class="oe_view_manager_view_search"></div>
	        <a class="button" id="" style="visibility: hidden;" ></a>
	        <ul id="ulYZCheckMany" class="pre-more" tabindex='-1' style="display: none;" >
	        		<li class="liBtH">
						<a class="button2">更多</a>
                     </li>
                     <li class="liBtN" style="display: none;">
						<a class="button2" id="aYZCheckMany">批量审方</a>
                     </li>
             </ul> 
        </div>
        </div>
        <div class="tbl">
            <table id="flexGrid" style="display: block;margin: 0px;"></table>
        </div>
        
</div>

<div id="yzInfo" class="main-div" style="width:100%;dispaly:none">
</div>

<%-- 新增编辑弹出框 --%>
<div id="divYZCheckMany" title="批量审方" align="center" style="display: none;">
			<form id="editView-form" action="" method="post">
				<input type="hidden" id="gid" name="gid"/>
					<div class="popup" style="padding-top: 24px;">
						<div class="row">
							<div class="column">
								<label class="tit2"><spring:message code='user.zhanghao'/></label>
								<input type="text" class="edit" name="checkAccount" id="checkAccount" maxlength="32" style="height: 28px;" title="<spring:message code='common.op.remind2'/>"/>
				            	<span class="mand">* 请输入账号密码，确认操作一致</span>
							</div>
							<div class="column">
								<label class="tit2"><spring:message code='user.mima'/></label>
								<input type="password" class="edit" name="checkPass" id="checkPass" maxlength="32" style="height: 28px;" title="<spring:message code='common.op.remind2'/>"/>
				            	<span class="mand">*</span>
							</div>
						</div>
						
						<div class="row">
							<div class="column">
								<label class="tit2">不通过原因</label>
								<select name="yzshbtglx" id="yzshbtglx" style="width: 190px;  height: 32px;">
									<option value="">--请选择--</option>
									<c:forEach items="${errTypeList}" var="errType"  >
									<option value="${errType.gid}">${errType.name}</option>
									</c:forEach>
								</select>
								<span class="mand2"></span>
							</div>
							<div class="column">
								<label class="tit2"><spring:message code='common.remark'/></label>
								<textarea rows="1"  name="yzshbtgyy" id="yzshbtgyy" style="width: 400px" maxlength="256" title="<spring:message code='common.op.remind6'/>"></textarea>
							</div>
						</div>
					</div>
			</form>
		</div>

		
</body>

</html>