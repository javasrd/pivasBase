<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>
<script src="${pageContext.request.contextPath}/assets/common/js/my97DatePicker/WdatePicker.js"></script>
<html>

<head>
    <link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">

	<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>

	<style type="text/css">
	.button:hover{
 	color:blue;
}
	
	.medicine-tab table td {
   	 	padding: 0px 0px;
    	height: 26px;
	}
	.multiSelectOptions{
		width: 99px;
	}

	.cbit-grid div.bDiv td div {
	  cursor: pointer;
	}
	#txt__1 {
	  border-style: solid;
	  border-width: 0;
	  background: transparent;
	  border: 0;
	  outline: none;
	  width: 120px;
	}
	#txt__2 {
	  border-style: solid;
	  border-width: 0;
	  background: transparent;
	  border: 0;
	  outline: none;
	  width: 120px;
	}
	.medicine-tab{
		margin-top: 10px;
	}
	.tabs-title{
		height: 28px;
	}
	
	</style>
	<link href="${pageContext.request.contextPath}/assets/pivas/css/edit.css" type="text/css" rel="stylesheet" />
	
	<script type="text/javascript">
	_gridWidth = $(document).width()-12;//1200;//1340   //var _gridWidth = 1150;
	var _gridHeight = 600;
	
	//页面自适应
	function resizePageSize(){
		//_gridWidth = $(document).width()-12;/*  -189 是去掉左侧 菜单的宽度，   -12 是防止浏览器缩小页面 出现滚动条 恢复页面时  折行的问题 */
		_gridWidth = $(document).width()-12;//1430;
		_gridHeight = $(document).height()-32-150; /* -32 顶部主菜单高度，   -90 查询条件高度*/
		$("#flexGrid__1").flexResize();
		$("#flexGrid__2").flexResize();
	}
	
	$(function() {
		$(window).resize(function(){
			resizePageSize();
		});
		resizePageSize();
		
		showSearch1();
		$(".tab-title-1").bind("click",function(){
			showSearch1();
		});
		$(".tab-title-2").bind("click",function(){
			showSearch2();
		});
	});
	
	function showSearch1(){
		$(".tab-title-1").removeClass("select").addClass("select");
		$(".tab-title-2").removeClass("select");
		
		$("#ydMain__1").css("display","block");
		$("#ydMain__1 .oe_searchview222").removeClass("oe_searchview222").addClass("oe_searchview");
		$("#ydMain__1 .oe_searchview_head222").removeClass("oe_searchview_head222").addClass("oe_searchview_head");
		$("#ydMain__1 .divselect222").removeClass("divselect222").addClass("divselect");
		$("#ydMain__1 .oe_search_txt222").removeClass("oe_search_txt222").addClass("oe_search_txt");
		$("#ydMain__1 .oe_tag_dark222").removeClass("oe_tag_dark222").addClass("oe_tag_dark");
		//$("#ydMain__1 .oe_search_txt").css("display","block");
		
		$("#ydMain__2").css("display","none");
		$("#ydMain__2 .oe_searchview").removeClass("oe_searchview").addClass("oe_searchview222");
		$("#ydMain__2 .oe_searchview_head").removeClass("oe_searchview_head").addClass("oe_searchview_head222");
		$("#ydMain__2 .divselect").removeClass("divselect").addClass("divselect222");
		$("#ydMain__2 .oe_search_txt").removeClass("oe_search_txt").addClass("oe_search_txt222");
		$("#ydMain__2 .oe_tag_dark").removeClass("oe_tag_dark").addClass("oe_tag_dark222");
		//$("#ydMain__2 .oe_search_txt222").css("display","none");
	}
	function showSearch2(){
		$(".tab-title-2").removeClass("select").addClass("select");
		$(".tab-title-1").removeClass("select");
		
		$("#ydMain__2").css("display","block");
		$("#ydMain__2 .oe_searchview222").removeClass("oe_searchview222").addClass("oe_searchview");
		$("#ydMain__2 .oe_searchview_head222").removeClass("oe_searchview_head222").addClass("oe_searchview_head");
		$("#ydMain__2 .divselect222").removeClass("divselect222").addClass("divselect");
		$("#ydMain__2 .oe_search_txt222").removeClass("oe_search_txt222").addClass("oe_search_txt");
		$("#ydMain__2 .oe_tag_dark222").removeClass("oe_tag_dark222").addClass("oe_tag_dark");
		//$("#ydMain__2 .oe_search_txt").css("display","block");
		
		$("#ydMain__1").css("display","none");
		$("#ydMain__1 .oe_searchview").removeClass("oe_searchview").addClass("oe_searchview222");
		$("#ydMain__1 .oe_searchview_head").removeClass("oe_searchview_head").addClass("oe_searchview_head222");
		$("#ydMain__1 .divselect").removeClass("divselect").addClass("divselect222");
		$("#ydMain__1 .oe_search_txt").removeClass("oe_search_txt").addClass("oe_search_txt222");
		$("#ydMain__1 .oe_tag_dark").removeClass("oe_tag_dark").addClass("oe_tag_dark222");
		//$("#ydMain__1 .oe_search_txt222").css("display","none");
	}
	</script>
	
	<script type="text/javascript">

	var pcDataList__1 = "";
	var paramTemp__1 ;
	var filter__1 ;
	var area__1 ;
	var pc__1 ;

	var currentDay = getCurrentDate("yyyy-MM-dd",null,0);
	
	$(function() {
		/*
		var init_filter_1 = ['today'];
		filter__1 = 'today' ;
		$("#filter__1").val(init_filter_1);
		$("#filter__1").multiSelect({ "selectAll": false,"noneSelected": "无","oneOrMoreSelected":"*" },function(){
			filter__1 = $("#filter__1").selectedValuesString();
			qry__1();
		});
		$("#area__1").multiSelect({ "selectAll": false,"noneSelected": "无","oneOrMoreSelected":"*" },function(){
			area__1 = $("#area__1").selectedValuesString();
			qry__1();
		});
		$("#pc__1").multiSelect({ "selectAll": false,"noneSelected": "无","oneOrMoreSelected":"*" },function(){
			pc__1 = $("#pc__1").selectedValuesString();
			qry__1();
		});*/
		filter__1 = 'today' ;
		$("#filter__1").bind("change",function(){
		//已打印、未打印、未选日期时，日期控件的日期有效
			if($(this).val()=="unprint"||$(this).val()=="print" || $(this).val()==""){
				//$("#scrq").val(getCurrentDate("yyyy-MM-dd",null,0));
			}else{
				$("#yyrq1").val("");
			}
			filter__1 = $(this).val();
			qry__1();
		});
		$("#area__1").bind("change",function(){
			area__1 = $(this).val();
			qry__1();
		});
		$("#pc__1").bind("change",function(){
			pc__1 = $(this).val();
			qry__1();
		});
		
		$("#aPrint__1").bind("click",function(){
			var bottleLabelNumN = getFlexiGridSelectedRowText($("#flexGrid__1"), 12);
			var bottleLabelNumS = "" ;
			for(var k=0;k<bottleLabelNumN.length;k++){
				if(k==0){
					bottleLabelNumS = bottleLabelNumN[k];
				}else{
					bottleLabelNumS = bottleLabelNumS + "," + bottleLabelNumN[k];
				}
			}
			if(bottleLabelNumS!=""){
					$.ajax({
						url:"${pageContext.request.contextPath}/printLabel/print",
						type:"POST",
						data:{
							printType:0,//此参数是固定的表示打印瓶签
							bottleNumList:bottleLabelNumS//需要打印的瓶签
						},
						success:function(data){
							if(data.success){
								for(var k=0;k<bottleLabelNumN.length;k++){
									$("#print__1_"+bottleLabelNumN[k]).html("已打印");
								}
					    		window.open("${pageContext.request.contextPath}/printLabelDownLoad/<shiro:principal property="account"/>/"+data.msg);
					    	}else{
						    	message({
									data: data
						    	});
					    	}
						}
					});
			}else{
				message({
					html: "请选择要打印的数据"
		    	});
			}
		});
		
		var _columnWidth = (parseInt(_gridWidth)-60) / 12;
		$("#flexGrid__1").flexigrid({
			width : _gridWidth,
			height : _gridHeight,
			url: "${pageContext.request.contextPath}/doctorAdvice/qryYdPage",
			dataType : 'json',
			colModel : [ 
						{display: 'ydMainId', name : 'ydMainId', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'parentNo', name : 'parentNo', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'bottleLabelNum', name : 'bottleLabelNum', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'ydzxzt', name : 'ydzxzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzshzt', name : 'yzshzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'freqCode', name : 'freqCode', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'zxrq', name : 'zxrq', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'zxsj', name : 'zxsj', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'zxbc', name : 'zxbc', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'rucangOKNum', name : 'rucangOKNum', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'pidsj', name : 'pidsj', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'caseId', name : 'caseId', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'startTimeS', name : 'startTimeS', width : 0, sortable : false, align: 'center',hide:'true'},
						
						{display: '<spring:message code="pivas.yz2.bedno"/>', name : 'bedno', width : _columnWidth, sortable : true, align: 'left'},
						
						{display: '<spring:message code="pivas.yd.pb_name"/>', name : 'zxbc', width : _columnWidth*1.5, sortable : true, align: 'left',process: function(v,_trid,_row) {
							//if(currentDay<=_row.scrqS && _row.rucangOKNum < 1 && _row.ydzxzt==0 ){//&& _row.ydreorderStatus>0 
								return "<select id='yd__1_"+_row.pidsj+ "' oldvalue='"+v+"' parentno='"+_row.parentNo+"' pidsj='"+_row.pidsj+"' scrqS='"+_row.scrqS+"' onchange='changePC__1(this)' style='width:70px'  >"  //"<option value='' >--请选择--</option>" 
								 + "<c:forEach items='${batchList}' var='batch' ><option value='${batch.id}' issecendadvice='${batch.isSecendAdvice}' "+ (v=='${batch.id}'?"selected='selected'":"") + " >${batch.num}</option></c:forEach>" 
								 + "</select>" ;
							//}else{
							//	return _row.pb_num;
							//}
						}},
						
						{display: '<spring:message code="pivas.yz1.parentNo"/>', name : 'parentNo', width : _columnWidth, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yzyd.yyrq"/>', name : 'yyrqS', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return "<label id='yyrq__1_"+_row.pidsj+ "'>"+v+"</label>"; 
						}},
						{display: '<spring:message code="pivas.yz1.wardname"/>', name : 'wardName', width : _columnWidth, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.freqCode"/>', name : 'freqCode', width : _columnWidth, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz2.patname"/>', name : 'patname', width : _columnWidth, sortable : true, align: 'left'},
						
						{display: 'startDayDelNum', name : 'startDayDelNum', width : 0, sortable : false, align: 'center',hide:'true'},
						
						{display: '<spring:message code="pivas.yz2.age"/>', name : 'age', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return _row.age+ getDicValue('ageUnit',_row.ageunit);
						}},
						{display: '<spring:message code="pivas.yz3.transfusion"/>(ML)', name : 'transfusion', width : _columnWidth, sortable : true, align: 'left'},
						
						{display: '<spring:message code="pivas.yz1.drugname"/>', name : 'drugname', width : 250, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.dose"/>', name : 'dose', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.doseUnit"/>', name : 'doseUnit', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.quantity"/>', name : 'quantity', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.medicamentsPackingUnit"/>', name : 'medicamentsPackingUnit', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						
						//{display: '<spring:message code="pivas.yz1.zxrq"/>', name : 'zxrq', width : 65, sortable : true, align: 'center'},
						//{display: '<spring:message code="pivas.yz1.zxsj"/>', name : 'zxsj', width : 65, sortable : true, align: 'center'},
						
						{display: '<spring:message code="pivas.yd.dybz"/>', name : 'dybz', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return "<label id='print__1_"+_row.bottleLabelNum+"'>"+getDicValue('printStatus',v)+"</label>"    ;
						}},
						{display: '开立时间', name : 'startTimeS', width : _columnWidth*2, sortable : true, align: 'left'},
						
						{display: '异常信息', name : 'ydreorderMess', width : _columnWidth*2, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v;//_row.ydreorderCode<21?v:"";
						}},
						{display: '操作记录', name : 'operationLog', width : _columnWidth*2, sortable : false, align: 'left'},
						{display: '批次确认人', name : 'yzconfigUname', width : _columnWidth, sortable : true, align: 'left'},
						
						{display: 'ydreorderCode', name : 'ydreorderCode', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'ydreorderTimeS', name : 'ydreorderTimeS', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'zxbcChangeBeforeS', name : 'zxbcChangeBeforeS', width : 0, sortable : false, align: 'center',hide:'true'},
						],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			showcheckbox : true, //是否显示多选框
			rowbinddata : true,
			rowhandler : rowClick,
			onrowchecked : rowChecked__1,
			rp: 200, // results per page,每页默认的结果数
			rpOptions: [20, 50, 100, 200],
			numCheckBoxTitle : "<spring:message code='common.selectall'/>",
			onSuccess:function(){
				var patientName = "" ;
				//var colorF = false ;
				$("#flexGrid__1 tr").each(function(i){
					var startDayDelNum = $.trim($(this).find("td:eq(21) div").text());
					var startTimeS = $.trim($(this).find("td:eq(13) div").text());
					var hour = startTimeS.substring(startTimeS.indexOf(" ")+1,startTimeS.indexOf(":"));
					var ydreorderCode = $.trim($(this).find("td:eq(33) div").text());
					if(startDayDelNum>=0){
						if(hour>=12){
							$(this).find("td").each(function(i){
								$(this).css("color", "red");
							});
						}else if(hour>=11){
							$(this).find("td").each(function(i){
								$(this).css("color", "blue");
							});
						}else{
							$(this).find("td").each(function(i){
								$(this).css("color", "black");
							});
						}
					}else{
						$(this).find("td").each(function(i){
							$(this).css("color", "black");
						});
					}
					
					/*
					if(hour<11){
						$(this).find("td").each(function(i){
							$(this).css("color", "black");//绿色
							$(this).css("border-bottom", "1px dotted #ddd");
						});
					}else{
						$(this).find("td").each(function(i){
							$(this).css("color", "blue");//蓝色
							$(this).css("border-bottom", "1px dotted #ddd");
						});
					}
					*/
					var patientName2 =  $.trim($(this).find("td:eq(12) div").text());
					if(patientName!="" && patientName!=patientName2){
						$(this).find("td").each(function(i){
							$(this).css("border-top", "2px solid black");
						});
						//colorF = !colorF ;
					}
					/*
					if(colorF){
						$(this).find("td").each(function(i){
							$(this).css("background-color", "#efeff7");
						});
					}else{
						$(this).find("td").each(function(i){
							$(this).css("background-color", "white");
						});
					}
					colorF = !colorF ;
					*/
					
					if(ydreorderCode>0 && ydreorderCode<21){
						$(this).find("td").each(function(i){
							$(this).css("background-color", "rgba(205,10,10,0.3)");
						});
					}
					patientName = patientName2;
					
					//护士修改过批次则显示颜色
					var operationLog = $(this).find("td:eq(32) div").text();
					if(operationLog != "" && operationLog !=null){
						$(this).find("td").each(function(i){
							$(this).css("color", "#C71585");
						});
					}

				});
			},
		});
		function rowClick(r) {
			$(r).click(
			function() {
				$("#flexGrid__1 tr").each(function(i){
					var ydreorderCode = $.trim($(this).find("td:eq(33) div").text());
					if(ydreorderCode<21){
						$(this).find("td").each(function(i){
							$(this).css("background-color", "rgba(205,10,10,0.3)");
						});
					}else{
						$(this).find("td").each(function(i){
							$(this).css("background-color", "transparent");
						});
					}
				});
				var ydreorderCode = $.trim($(this).find("td:eq(33) div").text());
				if(ydreorderCode<21){
					$(this).find("td").each(function(i){
						$(this).css("background-color", "rgba(205,10,10,0.3)");
					});
				}else{
					$(this).find("td").each(function(i){
						$(this).css("background-color", "#c6e0b0");
					});
				}
			});
		}
		function rowChecked__1(r){
			var bottleLabelNumN = getFlexiGridSelectedRowText($("#flexGrid__1"), 4);
			if(bottleLabelNumN && bottleLabelNumN.length >0) {
				$("#aPrint__1").show();
			}else {
				$("#aPrint__1").hide();
			}
		}
		
		qry__1();
		
		$("#aSearch__1").bind("click",function(){
			qry__1();
		});
	});
	function changePC__1(obj){
		var pidsj = $(obj).attr("pidsj");
		var pcId =  $(obj).val();
		var isSecendAdvice = $(obj).find("option:selected").attr("issecendadvice"); 
		var parent_pc_map = {}; 
		$("#flexGrid__1 select").each(function(){
			if($(this).attr("pidsj")!=pidsj){
				parent_pc_map[$(this).attr("parentno")+"__1_"+$(this).attr("oldvalue")]=1;
			}
		});
		if(parent_pc_map[$(obj).attr("parentno")+"__1_"+pcId]!=null){
			$(obj).val($(obj).attr("oldvalue"));
			message({html: "同一组中已存在这个批次，请重排"});
			return ;
		}
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/ydPCSubmit',
			dataType : 'json',
			//contentType: "application/json",
			cache : false,
			data : {
				"pcDataList":pidsj,
				"piId":pcId,
				"isSecendAdvice":isSecendAdvice,
				"oldPcIdListS":$(obj).attr("oldvalue"),
				"oldPcNameS":$.trim($("#batchSel__1 option[value='"+$(obj).attr("oldvalue")+"']").text()),  
				"newPcNameS":$.trim($("#batchSel__1 option[value='"+pcId+"']").text())
			},
			success : function(response) {
				if(response.code==0){
					$(obj).attr("oldvalue",pcId);
					/*2016-0910 取消用药日期变化情况
					if(isSecendAdvice=="1" && $(obj).attr("scrqS")==getCurrentDate("yyyy-MM-dd") ){
						$("#yyrq__1_"+pidsj).html(getCurrentDate("yyyy-MM-dd",null,1));
					}else if($(obj).attr("scrqS")==getCurrentDate("yyyy-MM-dd") ){ 
						//var date = new Date();
						$("#yyrq__1_"+pidsj).html(getCurrentDate("yyyy-MM-dd"));
					}
					*/
					/*)
					if(isSecendAdvice=="1"){	
						var date = new Date();
						date.setDate(date.getDate()+1);
						$("#yyrq__1_"+pidsj).html(date.getYear()+"-"+date.getMonth()+"-"+date.getDate());
					}else{
						var date = new Date();
						$("#yyrq__1_"+pidsj).html(date.getYear()+"-"+date.getMonth()+"-"+date.getDate());
					}*/
					
				}else{
					$(obj).val($(obj).attr("oldvalue"));
					message({html: response.msg});
				}
			}
		});
	}
	
	function qry__1(){
		var params = [];
		if(paramTemp__1){
			params = paramTemp__1.concat();
		}
		//params.push({"name":"rucangOKNum","value":"Y"});
		params.push({"name":"ydztLowN","value":5});
		params.push({"name":"ydzxzt","value":0});
		params.push({"name":"yzztLowN","value":1});
		params.push({"name":"areaEnabled","value":1});
		params.push({"name":"yzlx","value":"0"});
		params.push({"name":"yzResource","value":"1"});
		params.push({"name":"filter","value":filter__1});
		params.push({"name":"areaS","value":area__1});
		params.push({"name":"pcS","value":pc__1});
		params.push({"name":"ydreorderStatus","value":1});
		//用药日期
		params.push({"name":"yyrq","value":$("#yyrq1").val()});
		//批次界面进行批次排序操作
		params.push({"name":"pici","value":"1"});
		//是否关联批次修改记录
		params.push({"name":"operationLog","value":"1"});
		//params.push({"name":"yzlx","value":"1"});
		//params.push({"name":"yzResourceUpEQN","value":"2"});
		var inpatientString = "${inpatientString}";
		params.push({"name":"inpatientString","value":inpatientString});
		
		$('#flexGrid__1').flexOptions({
			newp: 1,
			extParam: params,
        	url: "${pageContext.request.contextPath}/doctorAdvice/qryYdPage"
        }).flexReload();
		$("#aPrint__1").hide();
	}
	function qryList__1(param){
		paramTemp__1 = param ;
		qry__1();
	}
	
	function dataPick1(){
		//已打印、未打印、未选日期时，日期控件的日期有效
		var filterV = $("#filter__1").val();
		if(filterV =="unprint" || filterV =="print" || filterV ==""){
			//$("#scrq").val(getCurrentDate("yyyy-MM-dd",null,0));
		}else{
			filter__1 = "";
		}
		
		qry__1();
	}
	</script>

	
	<script type="text/javascript">

	var pcDataList__2 = "";
	var paramTemp__2 ;
	var filter__2 ;
	var area__2 ;
	var pc__2 ;
	
	$(function() {
		/*
		var init_filter_1 = ['today'];
		filter__2 = 'today' ;
		$("#filter__2").val(init_filter_1);
		$("#filter__2").multiSelect({ "selectAll": false,"noneSelected": "无","oneOrMoreSelected":"*" },function(){
			filter__2 = $("#filter__2").selectedValuesString();
			qry__2();
		});
		$("#area__2").multiSelect({ "selectAll": false,"noneSelected": "无","oneOrMoreSelected":"*" },function(){
			area__2 = $("#area__2").selectedValuesString();
			qry__2();
		});
		$("#pc__2").multiSelect({ "selectAll": false,"noneSelected": "无","oneOrMoreSelected":"*" },function(){
			pc__2 = $("#pc__2").selectedValuesString();
			qry__2();
		});*/
		filter__2 = 'today' ;
		$("#filter__2").bind("change",function(){
			if($(this).val() =="unprint"||$(this).val() =="print" || $(this).val() == ""){
				//$("#scrq").val(getCurrentDate("yyyy-MM-dd",null,0));
			}else{
				$("#yyrq2").val("");
			}
			
			filter__2 = $(this).val();
			qry__2();
		});
		$("#area__2").bind("change",function(){
			area__2 = $(this).val();
			qry__2();
		});
		$("#pc__2").bind("change",function(){
			pc__2 = $(this).val();
			qry__2();
		});
		
		$("#aPrint__2").bind("click",function(){
			var bottleLabelNumN = getFlexiGridSelectedRowText($("#flexGrid__2"), 12);
			var bottleLabelNumS = "" ;
			for(var k=0;k<bottleLabelNumN.length;k++){
				if(k==0){
					bottleLabelNumS = bottleLabelNumN[k];
				}else{
					bottleLabelNumS = bottleLabelNumS + "," + bottleLabelNumN[k];
				}
			}
			if(bottleLabelNumS!=""){
					$.ajax({
						url:"${pageContext.request.contextPath}/printLabel/print",
						type:"POST",
						data:{
							printType:0,//此参数是固定的表示打印瓶签
							bottleNumList:bottleLabelNumS//需要打印的瓶签
						},
						success:function(data){
							if(data.success){
								for(var k=0;k<bottleLabelNumN.length;k++){
									$("#print__2_"+bottleLabelNumN[k]).html("已打印");
								}
					    		window.open("${pageContext.request.contextPath}/printLabelDownLoad/<shiro:principal property="account"/>/"+data.msg);
					    	}else{
						    	message({
									data: data
						    	});
					    	}
						}
					});
			}else{
				message({
					html: "请选择要打印的数据"
		    	});
			}
		});
		
		var _columnWidth = (parseInt(_gridWidth)-60) / 12;
		$("#flexGrid__2").flexigrid({
			width : _gridWidth,
			height : _gridHeight,
			url: "${pageContext.request.contextPath}/doctorAdvice/qryYdPage",
			dataType : 'json',
			colModel : [ 
						{display: 'ydMainId', name : 'ydMainId', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'parentNo', name : 'parentNo', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'bottleLabelNum', name : 'bottleLabelNum', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'ydzxzt', name : 'ydzxzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzshzt', name : 'yzshzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'freqCode', name : 'freqCode', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'zxrq', name : 'zxrq', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'zxsj', name : 'zxsj', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'zxbc', name : 'zxbc', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'rucangOKNum', name : 'rucangOKNum', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'pidsj', name : 'pidsj', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'caseId', name : 'caseId', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'startTimeS', name : 'startTimeS', width : 0, sortable : false, align: 'center',hide:'true'},
						
						{display: '<spring:message code="pivas.yz2.bedno"/>', name : 'bedno', width : _columnWidth, sortable : true, align: 'left'},
						
						{display: '<spring:message code="pivas.yd.pb_name"/>', name : 'zxbc', width : _columnWidth*1.5, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return "<select id='yd__2_"+_row.pidsj+ "' oldvalue='"+v+"' parentno='"+_row.parentNo+"' pidsj='"+_row.pidsj+"' scrqS='"+_row.scrqS+"' onchange='changePC__2(this)' style='width:70px'  >"  //"<option value='' >--请选择--</option>" 
							 + "<c:forEach items='${batchList}' var='batch' ><option value='${batch.id}' issecendadvice='${batch.isSecendAdvice}' "+ (v=='${batch.id}'?"selected='selected'":"") + " >${batch.num}</option></c:forEach>" 
							 + "</select>" ;
						}},
						
						{display: '<spring:message code="pivas.yz1.parentNo"/>', name : 'parentNo', width : _columnWidth, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yzyd.yyrq"/>', name : 'yyrqS', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return "<label id='yyrq__2_"+_row.pidsj+ "'>"+v+"</label>"; 
						}},
						{display: '<spring:message code="pivas.yz1.wardname"/>', name : 'wardName', width : _columnWidth, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz1.freqCode"/>', name : 'freqCode', width : _columnWidth, sortable : true, align: 'left'},
						{display: '<spring:message code="pivas.yz2.patname"/>', name : 'patname', width : _columnWidth, sortable : true, align: 'left'},
						
						{display: 'startDayDelNum', name : 'startDayDelNum', width : 0, sortable : false, align: 'center',hide:'true'},
						
						{display: '<spring:message code="pivas.yz2.age"/>', name : 'age', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return _row.age+ getDicValue('ageUnit',_row.ageunit);
						}},
						{display: '<spring:message code="pivas.yz3.transfusion"/>(ML)', name : 'transfusion', width : _columnWidth, sortable : true, align: 'left'},
						
						{display: '<spring:message code="pivas.yz1.drugname"/>', name : 'drugname', width : 250, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.dose"/>', name : 'dose', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.doseUnit"/>', name : 'doseUnit', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.quantity"/>', name : 'quantity', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						{display: '<spring:message code="pivas.yz1.medicamentsPackingUnit"/>', name : 'medicamentsPackingUnit', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v.replace(new RegExp("@@","g"),"<br>");
						}},
						
						//{display: '<spring:message code="pivas.yz1.zxrq"/>', name : 'zxrq', width : 65, sortable : true, align: 'center'},
						//{display: '<spring:message code="pivas.yz1.zxsj"/>', name : 'zxsj', width : 65, sortable : true, align: 'center'},
						
						{display: '<spring:message code="pivas.yd.dybz"/>', name : 'dybz', width : _columnWidth, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return "<label id='print__2_"+_row.bottleLabelNum+"'>"+getDicValue('printStatus',v)+"</label>"    ;
						}},
						{display: '开立时间', name : 'startTimeS', width : _columnWidth*2, sortable : true, align: 'left'},
						
						{display: '异常信息', name : 'ydreorderMess', width : _columnWidth*2, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return v;//_row.ydreorderCode<21?v:"";
						}},
						{display: '操作记录', name : 'operationLog', width : _columnWidth*2, sortable : false, align: 'left'},
						{display: '批次确认人', name : 'yzconfigUname', width : _columnWidth, sortable : true, align: 'left'},
						
						{display: 'ydreorderCode', name : 'ydreorderCode', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'ydreorderTimeS', name : 'ydreorderTimeS', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'zxbcChangeBeforeS', name : 'zxbcChangeBeforeS', width : 0, sortable : false, align: 'center',hide:'true'},
						],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			showcheckbox : true, //是否显示多选框
			rowbinddata : true,
			rowhandler : rowClick,
			onrowchecked : rowChecked__2,
			rp: 200, // results per page,每页默认的结果数
			rpOptions: [20, 50, 100, 200],
			numCheckBoxTitle : "<spring:message code='common.selectall'/>",
			onSuccess:function(){
				var patientName = "" ;
				//var colorF = false ;
				$("#flexGrid__2 tr").each(function(i){
					var startDayDelNum = $.trim($(this).find("td:eq(21) div").text());
					var startTimeS = $.trim($(this).find("td:eq(13) div").text());
					var hour = startTimeS.substring(startTimeS.indexOf(" ")+1,startTimeS.indexOf(":"));
					var ydreorderCode = $.trim($(this).find("td:eq(33) div").text());
					if(startDayDelNum>=0){
						if(hour>=12){
							$(this).find("td").each(function(i){
								$(this).css("color", "red");
							});
						}else if(hour>=11){
							$(this).find("td").each(function(i){
								$(this).css("color", "blue");
							});
						}else{
							$(this).find("td").each(function(i){
								$(this).css("color", "black");
							});
						}
					}else{
						$(this).find("td").each(function(i){
							$(this).css("color", "black");
						});
					}
					
					/*
					if(hour<11){
						$(this).find("td").each(function(i){
							$(this).css("color", "black");//绿色
							$(this).css("border-bottom", "1px dotted #ddd");
						});
					}else{
						$(this).find("td").each(function(i){
							$(this).css("color", "blue");//蓝色
							$(this).css("border-bottom", "1px dotted #ddd");
						});
					}
					*/
					var patientName2 =  $.trim($(this).find("td:eq(12) div").text());
					if(patientName!="" && patientName!=patientName2){
						$(this).find("td").each(function(i){
							$(this).css("border-top", "2px solid black");
						});
						//colorF = !colorF ;
					}
					/*
					if(colorF){
						$(this).find("td").each(function(i){
							$(this).css("background-color", "#efeff7");
						});
					}else{
						$(this).find("td").each(function(i){
							$(this).css("background-color", "white");
						});
					}
					colorF = !colorF ;
					*/
					
					if(ydreorderCode>0 && ydreorderCode<21){
						$(this).find("td").each(function(i){
							$(this).css("background-color", "rgba(205,10,10,0.3)");
						});
					}
					patientName = patientName2;
					
					
					//护士修改过批次则显示颜色
					var operationLog = $(this).find("td:eq(32) div").text();
					if(operationLog != "" && operationLog !=null){
						$(this).find("td").each(function(i){
							$(this).css("color", "#C71585");
						});
					}
					
				});
			},
		});
		function rowClick(r) {
			$(r).click(
			function() {
				$("#flexGrid__2 tr").each(function(i){
					var ydreorderCode = $.trim($(this).find("td:eq(33) div").text());
					if(ydreorderCode<21){
						$(this).find("td").each(function(i){
							$(this).css("background-color", "rgba(205,10,10,0.3)");
						});
					}else{
						$(this).find("td").each(function(i){
							$(this).css("background-color", "transparent");
						});
					}
				});
				var ydreorderCode = $.trim($(this).find("td:eq(33) div").text());
				if(ydreorderCode<21){
					$(this).find("td").each(function(i){
						$(this).css("background-color", "rgba(205,10,10,0.3)");
					});
				}else{
					$(this).find("td").each(function(i){
						$(this).css("background-color", "#c6e0b0");
					});
				}
			});
		}
		function rowChecked__2(r){
			var bottleLabelNumN = getFlexiGridSelectedRowText($("#flexGrid__2"), 4);
			if(bottleLabelNumN && bottleLabelNumN.length >0) {
				$("#aPrint__2").show();
			}else {
				$("#aPrint__2").hide();
			}
		}
		
		qry__2();
		
		$("#aSearch__2").bind("click",function(){
			qry__2();
		});
	});
	function changePC__2(obj){
		var pidsj = $(obj).attr("pidsj");
		var pcId =  $(obj).val();
		var isSecendAdvice = $(obj).find("option:selected").attr("issecendadvice"); 
		var parent_pc_map = {}; 
		$("#flexGrid__2 select").each(function(){
			if($(this).attr("pidsj")!=pidsj){
				parent_pc_map[$(this).attr("parentno")+"__2_"+$(this).attr("oldvalue")]=1;
			}
		});
		if(parent_pc_map[$(obj).attr("parentno")+"__2_"+pcId]!=null){
			$(obj).val($(obj).attr("oldvalue"));
			message({html: "同一组中已存在这个批次，请重排"});
			return ;
		}
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/ydPCSubmit',
			dataType : 'json',
			//contentType: "application/json",
			cache : false,
			data : {
				"pcDataList":pidsj,
				"piId":pcId,
				"isSecendAdvice":isSecendAdvice,
				"oldPcIdListS":$(obj).attr("oldvalue"),
				"oldPcNameS":$.trim($("#batchSel__2 option[value='"+$(obj).attr("oldvalue")+"']").text()),  
				"newPcNameS":$.trim($("#batchSel__2 option[value='"+pcId+"']").text())
			},
			success : function(response) {
				if(response.code==0){
					$(obj).attr("oldvalue",pcId);
					/*2016-0910 取消用药日期变化情况
					if(isSecendAdvice=="1" && $(obj).attr("scrqS")==getCurrentDate("yyyy-MM-dd") ){
						$("#yyrq__2_"+pidsj).html(getCurrentDate("yyyy-MM-dd",null,1));
					}else if($(obj).attr("scrqS")==getCurrentDate("yyyy-MM-dd") ){
						//var date = new Date();
						$("#yyrq__2_"+pidsj).html(getCurrentDate("yyyy-MM-dd"));
					}*/
					/*)
					if(isSecendAdvice=="1"){	
						var date = new Date();
						date.setDate(date.getDate()+1);
						$("#yyrq__2_"+pidsj).html(date.getYear()+"-"+date.getMonth()+"-"+date.getDate());
					}else{
						var date = new Date();
						$("#yyrq__2_"+pidsj).html(date.getYear()+"-"+date.getMonth()+"-"+date.getDate());
					}*/
					
				}else{
					$(obj).val($(obj).attr("oldvalue"));
					message({html: response.msg});
				}
			}
		});
	}
	
	function qry__2(){
		var params = [];
		if(paramTemp__2){
			params = paramTemp__2.concat();
		}
		//params.push({"name":"rucangOKNum","value":"Y"});
		params.push({"name":"ydztLowN","value":5});
		params.push({"name":"ydzxzt","value":0});
		params.push({"name":"yzztLowN","value":1});
		params.push({"name":"areaEnabled","value":1});
		//params.push({"name":"yzlx","value":"0"});
		//params.push({"name":"yzResource","value":"1"});
		params.push({"name":"yzlx","value":"1"});
		params.push({"name":"yzResourceUpEQN","value":"2"});
		params.push({"name":"filter","value":filter__2});
		params.push({"name":"areaS","value":area__2});
		params.push({"name":"pcS","value":pc__2});
		params.push({"name":"ydreorderStatus","value":1});
		//用药日期
		params.push({"name":"yyrq","value":$("#yyrq2").val()});
		//批次界面进行批次排序操作
		params.push({"name":"pici","value":"1"});
		//是否关联批次修改记录
		params.push({"name":"operationLog","value":"1"});
		
		var inpatientString = "${inpatientString}";
		params.push({"name":"inpatientString","value":inpatientString});
		
		$('#flexGrid__2').flexOptions({
			newp: 1,
			extParam: params,
        	url: "${pageContext.request.contextPath}/doctorAdvice/qryYdPage"
        }).flexReload();
		$("#aPrint__2").hide();
	}
	function qryList__2(param){
		paramTemp__2 = param ;
		qry__2();
	}
	function dataPick2(){
		//已打印、未打印、未选日期时，日期控件的日期有效
		var filterV = $("#filter__2").val();
		if(filterV =="unprint"||filterV =="print"|| filterV ==""){
			//$("#scrq").val(getCurrentDate("yyyy-MM-dd",null,0));
		}else{
			filter__2 = "";
		}
		
		qry__2();
	}
	</script>

	
	
	
</head>

<body>



<div class="medicine-tab" >
	<div class="tabs-title" >
		<ul style="  float: left;">
			<li><a class="tab-title-1 select">长嘱药单</a></li>
			<li><a class="tab-title-2 ">临嘱药单</a></li>
		</ul>
	</div>
	<div class="tabs-content" style="margin-top: -2px;border-top: 1.5px solid;">
	
	
<div class="tab-content-1" >


<div id="ydMain__1" class="main-div" style="width:100%">

<div style="height: 32px;float: left;margin-top: 12px;z-index: 1000000;">
<table>
<tr>
	<td>用药时间：</td>
	<td>
	<input type="text" id="yyrq1" style="color: #555555;height:26px;width: 100px;" class="Wdate" empty="false" readonly="readonly" onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:dataPick1});">
	&nbsp;
	</td>
	<td>过滤器：</td>
	<td>
	<select id="filter__1" style="width:108px;height: 26px;margin-top: -2px;"><!-- name="filter__1[]" multiple="multiple" size="5"  -->
	<option value="">--请选择--</option>
	<option value="today" selected="selected" >今日生成药单</option>
	<option value="yesterday">昨日生成药单</option>
	<option value="print">已打印</option>
	<option value="unprint">未打印</option>
	<option value="yyrqToday">今日用药</option>
	<option value="yyrqNextDay">明日用药</option>
	</select>
	&nbsp;
	</td>
	<td>病区：</td>
	<td>
	<select id="area__1" style="width:80px;margin-top: -2px;height: 26px;"><!--  name="area__2[]" multiple="multiple" size="5" height: 26px;-->
	<option value="">--请选择--</option>
	<c:forEach items="${inpAreaList}" var="area" >
	<option value="${area.deptCode}">${area.deptName}</option>
	</c:forEach>
	</select>
	&nbsp;
	</td>
	<td>批次：</td>
	<td>
	<select id="pc__1" style="width:80px;margin-top: -2px;  height: 26px;"><!-- size="5" name="pc__1[]" height: 26px;   multiple="multiple"  -->
	<option value="">--请选择--</option>
	<c:forEach items="${batchList}" var="batch" >
	<option value="${batch.id}">${batch.num}</option>
	</c:forEach>
	</select>
	</td>
</tr>
</table>
</div>&nbsp;

<!-- 搜索条件--开始 -->
<div class="oe_searchview" style="margin-left: 10px;width:220px;"><!-- 0115bianxw修改 添加宽度设置 -->
    <div class="oe_searchview_facets" >
	    <div class="oe_searchview_input oe_searchview_head"></div>
	    <div class="oe_searchview_input"  id="inputsearch__1" >
	    	  <input id="txt__1" type="text" class="oe_search_txt" style="max-height: 18px;" width="170px" />
	    </div>
    </div>
    <img alt="" style="top:52px;position: absolute;" src="${pageContext.request.contextPath}/assets/search/images/searchblue.png">
    <div class="oe_searchview_clear" onclick="clearclosedinputall();" style="left: 815px;" ></div><!-- 0115bianxw修改 left: 735px; -->
	<div class="oe-autocomplete" ></div>
	<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
		<cite>请选择...</cite>
		<ul class="ulQry" style="-webkit-border-radius: 20;" funname="qryList__1" >
			<li show="<spring:message code='pivas.yz2.bedno'/>" name="bednoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.bedno'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz2.patname'/>" name="patnameS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.patname'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.parentNo'/>" name="parentNoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.parentNo'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.wardname'/>" name="wardNameS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.wardname'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.freqCode'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yzyd.zxbc'/>" name="zxbcS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yzyd.zxbc'/>：<span class="searchVal"></span></li>
		</ul>
	</div>
</div>
<!-- 搜索条件--结束 -->
	
	<div style="height: 0px" >
	<a class="button" id="aPrint__1" style="display: ;">打印</a>
	</div>
        <div id="qryView-div__1">
        <div class="search-div">
    		<div class="oe_view_manager_view_search"></div>
    		<a class="button" id="aSearch__1" style="visibility: hidden;"></a>
	        <ul id="ulYZCheckMany__1" class="pre-more" tabindex='-1' style="display: none;  margin-top: -32px;left: 780px;" >
	        		<li class="liBtH" style="background-color: transparent;" >
						<select id="batchSel__1" style="background-color: #EBB700;color: white; height: 26px; border-color: transparent;">
						<option value="">&nbsp;&nbsp;<spring:message code='pivas.yz1.pleaseSelPC2'/>&nbsp;</option>
						<c:forEach items="${batchList}" var="batch" >
						<c:if test="${batch.enabled==1}">
						<option value="${batch.id}" isSecendAdvice="${batch.isSecendAdvice}" >&nbsp;&nbsp;${batch.name}</option>
						</c:if>
						</c:forEach>
						</select>
                     </li>
             </ul> 
        </div>
        </div>
        <div class="tbl">
            <table id="flexGrid__1" style="margin: 0px;"></table>
        </div>
        
</div>



</div>
<div class="tab-content-2"  style="display:block;">




<div id="ydMain__2" class="main-div" style="width:100%">

<div style="height: 32px;float: left;margin-top: 12px;z-index: 1000000;">
<table>
<tr>
	<td>用药时间：</td>
	<td>
	<input type="text" id="yyrq2" style="color: #555555;height:26px;width: 100px;" class="Wdate" empty="false" readonly="readonly" onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:dataPick2});">
	&nbsp;
	</td>
	<td>过滤器：</td>
	<td>
	<select id="filter__2" style="width:108px;height: 26px;margin-top: -2px;"><!-- name="filter__2[]" multiple="multiple" size="5"  -->
	<option value="">--请选择--</option>
	<option value="today" selected="selected" >今日生成药单</option>
	<option value="yesterday">昨日生成药单</option>
	<option value="print">已打印</option>
	<option value="unprint">未打印</option>
	<option value="yyrqToday">今日用药</option>
	<option value="yyrqNextDay">明日用药</option>
	</select>
	&nbsp;
	</td>
	<td>病区：</td>
	<td>
	<select id="area__2" style="width:80px;margin-top: -2px;height: 26px;"><!--  name="area__2[]" multiple="multiple" size="5" height: 26px;-->
	<option value="">--请选择--</option>
	<c:forEach items="${inpAreaList}" var="area" >
	<option value="${area.deptCode}">${area.deptName}</option>
	</c:forEach>
	</select>
	&nbsp;
	</td>
	<td>批次：</td>
	<td>
	<select id="pc__2" style="width:80px;margin-top: -2px;  height: 26px;"><!-- size="5" name="pc__2[]" height: 26px;   multiple="multiple"  -->
	<option value="">--请选择--</option>
	<c:forEach items="${batchList}" var="batch" >
	<option value="${batch.id}">${batch.num}</option>
	</c:forEach>
	</select>
	</td>
</tr>
</table>
</div>&nbsp;

<!-- 搜索条件--开始 -->
<div class="oe_searchview" style="margin-left: 10px;width:220px;"><!-- 0115bianxw修改 添加宽度设置 -->
    <div class="oe_searchview_facets" >
	    <div class="oe_searchview_input oe_searchview_head"></div>
	    <div class="oe_searchview_input"  id="inputsearch__2" >
	    	  <input id="txt__2" type="text" class="oe_search_txt" style="max-height: 18px;" width="170px" />
	    </div>
    </div>
    <img alt="" style="top:52px;position: absolute;" src="${pageContext.request.contextPath}/assets/search/images/searchblue.png">
    <div class="oe_searchview_clear" onclick="clearclosedinputall();" style="left: 815px;" ></div><!-- 0115bianxw修改 left: 735px; -->
	<div class="oe-autocomplete" ></div>
	<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
		<cite>请选择...</cite>
		<ul class="ulQry" style="-webkit-border-radius: 20;" funname="qryList__2" >
			<li show="<spring:message code='pivas.yz2.bedno'/>" name="bednoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.bedno'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz2.patname'/>" name="patnameS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.patname'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.parentNo'/>" name="parentNoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.parentNo'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.wardname'/>" name="wardNameS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.wardname'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.freqCode'/>：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yzyd.zxbc'/>" name="zxbcS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yzyd.zxbc'/>：<span class="searchVal"></span></li>
		</ul>
	</div>
</div>
<!-- 搜索条件--结束 -->
	
	<div style="height: 0px" >
	<a class="button" id="aPrint__2" style="display: ;">打印</a>
	</div>
        <div id="qryView-div__2">
        <div class="search-div">
    		<div class="oe_view_manager_view_search"></div>
    		<a class="button" id="aSearch__2" style="visibility: hidden;"></a>
	        <ul id="ulYZCheckMany__2" class="pre-more" tabindex='-1' style="display: none;  margin-top: -32px;left: 780px;" >
	        		<li class="liBtH" style="background-color: transparent;" >
						<select id="batchSel__2" style="background-color: #EBB700;color: white; height: 26px; border-color: transparent;">
						<option value="">&nbsp;&nbsp;<spring:message code='pivas.yz1.pleaseSelPC2'/>&nbsp;</option>
						<c:forEach items="${batchList}" var="batch" >
						<c:if test="${batch.enabled==1}">
						<option value="${batch.id}" isSecendAdvice="${batch.isSecendAdvice}" >&nbsp;&nbsp;${batch.num}</option>
						</c:if>
						</c:forEach>
						</select>
                     </li>
             </ul> 
        </div>
        </div>
        <div class="tbl">
            <table id="flexGrid__2" style="margin: 0px;"></table>
        </div>
        
</div>


	</div>
</div>




</div>   






</body>

<style type="text/css" >
.oe_searchview .oe_searchview_clear{
		  margin-top: 36px;
	}
	.oe_searchview .oe_searchview_search{
		  margin-top: 36px;
	}
	.oe_searchview{
   	line-height: 18px; 
   	border: 1px solid #ababab;
   	background: white; 
   	width:323px;
    -moz-border-radius: 13px;
    cursor: text;
    padding: 1px 0;
    float:left;
    border: 1px solid #ababab;
    margin-top: 10px;
	}
</style>


</html>
