<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="/WEB-INF/views/common/common.jsp" %>

<!DOCTYPE html>
<html>
<head>

    <link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>
	 <style type="text/css">
	.medicine-tab{
		margin-top: 10px;
	}
	.tabs-title{
		height: 28px;
	}
	.content {
	  width: 99%;
  	}
  	
  	.classX{
  		width: 15px;
  		min-height: 15px;
  	 	margin-top: -17px;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
  	 	margin-left: 250px;
    	background: url('${pageContext.request.contextPath}/assets/search/images/search_reset.gif') center center no-repeat;
  	}
	.rightDiv table tr:nth-child(2n){
		background: transparent;
	}
	.rightDiv table tr td{
		 padding: 0px 2px;
	}
	.fontBold{
		 font-weight: bold;
	}
	.cbit-grid div.bDiv td{
		background-color: transparent;
	}
	.cbit-grid tr.erow td{
		background-color: transparent;
		background: transparent;
	}
	/*
	tr:nth-child(2n){
		background-color: white;
		background: white;
	}*/
	.trSelect{
		background-color: #ffeece;/*#E1E9F8;*/
	}
	
</style>


	<style type="text/css">
	/*药物优先级 左中右样式 --开始------------------*/
	.td200Left{
		width: 200px;vertical-align: top;
	}
	.divLeftMenuAll{
		height: 32px;padding-top: 10px; padding-left: 24px; font-size: 0.75rem;
	}
	.divLeftMenuList{
		overflow-y: auto;  padding-left: 10px;padding-right: 5px; font-size: 0.75rem;
	}
	.tabWith100{
		width:100%;
	}
	.tabWith50{
		width:50%;
	}
	.tdLeft10{
		padding-left: 10px;cursor: pointer;
	}
	.tabMain2{
		height: 100%;width:100%;padding-left: 10px;padding-top: 20px;
	}
	.tdSortTitle{
		vertical-align: bottom ;padding-right: 5px;color: white;
	}
	.divSortTitle{
		height:100%;padding-top: 8px;padding-left:5px; background-color: #6F96E5;font-size: 0.75rem;
	}
	.aSortTitle{
		float: right;padding-left: 10px;
	}
	.tdSortContent{
		vertical-align: top;padding-right: 5px;
	}
	.divSortContent{
		height:100%;overflow-y: auto;
	}
	.tdSortRow{
		width: 100%;height: 30px;cursor: pointer;padding-left: 10px;
	}
	
	.tdMedSelec{
		background-color: #99bbe8;
	}
	.trArea{
		height: 30px;
	}
	
	.areaSel{
		background: #6F96E5;
		color: white;
	}
	.trArea td{
		padding-left: 10px;cursor: pointer;
	}
					
	#divSynToOther tr:nth-child(2n) {
	    background: transparent;
	}
	
	/*药物优先级 左中右样式 --结束------------------*/
	.category-progressor{
		
	}
	.areaSel .category-progressor{
		background-color: white;
    	color: black;
	}
	.button {
    	margin-left: 5px;
    }
	</style>
	
	<script type="text/javascript">
	var _pageWidth = 0;
	var _pageHeight = 0;
	var areaCodeNow__1 = "${deptCodeFirst}";
	var areaCodeNow__2 = "${deptCodeFirst}";
	var paramTemp__1 ;
	var paramTemp__2 ;
	
	function resizePageSize(){
		_pageWidth = $(window).width();
		_pageHeight = $(window).height();
		$(".rightDiv").css("height",_pageHeight-60);
	}
	
	$(function() {
		$(window).resize(function(){
			resizePageSize();
		});
		resizePageSize();
		
		showSearch1();
		$(".tab-title-1").bind("click",function(){
			showSearch1();
			/*if(areaCodeNow__1){
				var f = false ;
				$("#tabArea__1 tr").each(function(){
					if($(this).css("display")!="none"){
						if($(this).hasClass("areaSel")){
							f = true ;
						}
					}
				});
				if(!f){
					$("#tabArea__1 tr").each(function(){
						if($(this).css("display")!="none"){
							if(!f){
								$(this).addClass("areaSel");
								f = true ;
							}
						}
					});
				}
			}*/
		});
		$(".tab-title-2").bind("click",function(){
			showSearch2();
			if(areaCodeNow__2){
				var f = false ;
				$("#tabArea__2 tr").each(function(){
					if($(this).css("display")!="none"){
						if($(this).hasClass("areaSel")){
							f = true ;
						}
					}
				});
				if(!f){
					$("#tabArea__2 tr").each(function(){
						if($(this).css("display")!="none"){
							if(!f){
								$(this).addClass("areaSel");
								f = true ;
							}
						}
					});
				}
			}
		});
		
		$(".divLeftMenuList").css("height",_pageHeight-100);

		refushAreaNum__1();
		refushAreaNum__2();
		
		var widthTemp = (parseInt(_pageWidth)-250)*0.5;
		if(widthTemp<370-10){
			widthTemp = 360 ;
		}
		
		var _columnWidth__1 = widthTemp / 7;
		$("#flexGrid__1").flexigrid({
			width : widthTemp,
			height : _pageHeight-180,
			url: "${pageContext.request.contextPath}/doctorAdvice/qryPtPageByYZ",
			dataType : 'json',
			colModel : [ 
						{display: 'inhospno', name : 'inhospno', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'parentNo', name : 'parentNo', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'pidsj', name : 'pidsj', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'drugName', name : 'drugname', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'freqCode', name : 'freqCode', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzzt', name : 'yzzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzshzt', name : 'yzshzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzzdshzt', name : 'yzzdshzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzzdshbtglx', name : 'yzzdshbtglx', width : 0, sortable : false, align: 'center',hide:'true'},
						
						{display: '预审方状态', name : 'yzzdshzt', width : _columnWidth__1, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return "<label title='"+(_row.yzzdshbtglxS||'')+"' >"+(v==0?"":(v==1?"预审通过":(v==2?"预审不通过":"")))+"</label>";//""<label title='"+_row.yzzdshbtglxS+"'>"+v==0?"":(v==1?"预审通过":(v==2?"预审不通过":""))+"</label>";/*医嘱自动审核状态。0：未审核 1：审核通过 2：审核不通过*/
						}},
						{display: '审方结果', name : 'yzshzt', width : _columnWidth__1, sortable : true, align: 'center',process: function(v,_trid,_row) {
							return "<label id='pidsj_"+_row.pidsj+"'>"+(v==0?"未审核":(v==1?"通过":"不通过"))+"</label>";	
						}},
						{display: '床号', name : 'bedno', width : _columnWidth__1, sortable : true, align: 'center'},
						{display: '病人姓名', name : 'patname', width : _columnWidth__1, sortable : true, align: 'center'},
						{display: '组号', name : 'parentNo', width : _columnWidth__1, sortable : true, align: 'center'},
						{display: '审方日期', name : 'sfrqS', width : _columnWidth__1, sortable : true, align: 'center',process: function(v,_trid,_row) {
							return "<label id='sfrq_"+_row.pidsj+"'>"+v+"</label>";
						}}
						
						//{display: '床号', name : 'bedno', width : _columnWidth__1, sortable : true, align: 'center'},
						//{display: '姓名', name : 'patname', width : _columnWidth__1, sortable : true, align: 'center'},
						//{display: '出生日期', name : 'birthday', width : _columnWidth__1, sortable : true, align: 'center'},
						//{display: '年龄', name : 'age', width : _columnWidth__1, sortable : true, align: 'center',process: function(v,_trid,_row) {
						//	return v+getDicValue("ageUnit",_row.ageunit);	
						//}},
						//{display: '<spring:message code="pivas.patient.avdp"/>', name : 'avdp', width : _columnWidth__1, sortable : true, align: 'center'}
						],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			rowbinddata : true,
			showcheckbox : true, //是否显示多选框
			rowhandler : rowClick__1,
			onSuccess: gridSucc__1
		});
		
		$("#aSearch__1").bind("click",function(){
			qry__1();
		});
		
		$("#tabArea__1 td").bind("click",function(){
			areaCodeNow__1 = $(this).attr("areacode");
			areaNameNow__1 = $(this).html();
			$(this).parent().parent().find("tr").each(function(){
				$(this).removeClass("areaSel");
				$(this).css("background","transparent");
			});
			$(this).parent().addClass("areaSel"); 
			$(this).parent().css("background","#6F96E5");
			qry__1();
		});
		
		$("#aCheckOK__1").bind("click",function(){
			var pidsj = $("#yzInfo__1 [name=pidsj]").html();
			if(!pidsj){
				return ;
			}
			var freqCode = $("#yzInfo__1 [name=freqCode]").html();
			var drugname = $("#yzInfo__1 [name=drugname]").html();
			checkErr = "" ;
			if("${SYN_YZ_DATA_MODE}"=="3"){
				if(checkErr==""){// && yzlx==0
					var f = 0 ;
					var freqCode = freqCode.toUpperCase();
					if($("#ruleKey__1 [name="+freqCode+"]").length>0){//如果 关键字规则 没有
						$("#ruleKey__1 [name="+freqCode+"]").each(function(){
							f = -1 ;
							if( drugname.indexOf($(this).html())>-1 ){
								f = 1; 
							}
						});
					}
					if(f<1){
						if($("#batchRule [name="+freqCode+"]").length>0){//如果 没有一般规则对应的频次
							f = 2 ;
						}else{
							f = f -1 ;
						}
					}
					if(f==-2){
						checkErr = "频次["+freqCode+"] 没有找到对应规则，无法审核通过";
					}else if(f<1){
						checkErr = "频次["+freqCode+"] 没有对应的批次数据，无法审核通过";
					}
				
				}
				if(checkErr!=""){
					message({
		    			html: checkErr
		        	});
					return ;
				}
			}
			checkOne__1(pidsj,1);
		});

		$("#aCheckYSH__1").bind("click",function(){
			var pidsj = $("#yzInfo__1 [name=pidsj]").html();
			if(!pidsj){
				return ;
			}
			var freqCode = $("#yzInfo__1 [name=freqCode]").html();
			var drugname = $("#yzInfo__1 [name=drugname]").html();
			var yzzdshzt = $("#yzInfo__1 [name=yzzdshzt]").html();
			var yzzdshbtglx = $("#yzInfo__1 [name=yzzdshbtglx]").html();
			checkErr = "" ;
			if("${SYN_YZ_DATA_MODE}"=="3"){
				if(checkErr==""){// && yzlx==0
					var f = 0 ;
					var freqCode = freqCode.toUpperCase();
					if($("#ruleKey__1 [name="+freqCode+"]").length>0){//如果 关键字规则 没有
						$("#ruleKey__1 [name="+freqCode+"]").each(function(){
							f = -1 ;
							if( drugname.indexOf($(this).html())>-1 ){
								f = 1; 
							}
						});
					}
					if(f<1){
						if($("#batchRule [name="+freqCode+"]").length>0){//如果 没有一般规则对应的频次
							f = 2 ;
						}else{
							f = f -1 ;
						}
					}
					if(f==-2){
						checkErr = "频次["+freqCode+"] 没有找到对应规则，无法确认";
					}else if(f<1){
						checkErr = "频次["+freqCode+"] 没有对应的批次数据，无法确认";
					}
				
				}
				if(checkErr!=""){
					message({
		    			html: checkErr
		        	});
					return ;
				}
			}
			if(yzzdshzt>0){
				var pidsjNSucc = (yzzdshzt==1?pidsj:"") ;
				var pidsjNFail = (yzzdshzt==2?pidsj:"") ;
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/doctorAdvice/yshCfm',
					dataType : 'json',
					cache : false,
					data : {"pidsjNSucc":pidsjNSucc,"pidsjNFail":pidsjNFail},
					success : function(response) {
						if(response.code>0){
							$("#aCheckYSH__1").hide();
							if(yzzdshzt==1){
								$("#aCheckOK__1").css("background-color","#73AB66");
								$("#aCheckNO__1").css("background-color","#ebb800");
								$("#yzshbtglx__1").val("");
								$("#yzshbtglxTit__1").hide();
								$("#yzshbtgyy__1").val("");
								$("#pidsj_"+pidsj).html("通过");
							}else{
								$("#aCheckOK__1").css("background-color","#ebb800");
								$("#aCheckNO__1").css("background-color","#d45120");
								
								$("#yzshbtglx__1").val(yzzdshbtglx);
								$("#pidsj_"+pidsj).html("不通过");
							}
							$("#sfrq_"+pidsj).html(getCurrentDate("yyyy-MM-dd",null,0));
							refushAreaNum__1();
						}else{
							message({html: response.mess});
						}
						$(obj).remove();
					}
				});
			}else{
				message({
	    			html: "当前医嘱无法预审确认！"
	        	});
			}
		});
		
		$("#aCheckNO__1").bind("click",function(){
			var pidsj = $("#yzInfo__1 [name=pidsj]").html();
			if(!pidsj){
				return ;
			}
			if($("#yzshbtglx__1").val() && $("#yzshbtglx__1").val()!=""){
				$("#yzshbtglxTit__1").hide();
			}else{
				$("#yzshbtglxTit__1").show();
				return ;
			}
			checkOne__1(pidsj,2);
		});
		
		
		$("#aCheckMany__1").bind("click",function(){
			var pidsjN = getFlexiGridSelectedRowText($("#flexGrid__1"), 4);//parentNo 医嘱父ID
			var pidsjS = "" ;
			if(pidsjN && pidsjN.length <1) {
				message({html: "请选择医嘱再审方"});
				return;
			}
			var drugNameN = getFlexiGridSelectedRowText($("#flexGrid__1"), 5);
			var freqCodeN = getFlexiGridSelectedRowText($("#flexGrid__1"), 6);
			if("${SYN_YZ_DATA_MODE}"=="3"){
				var checkErr ="";
				for(var i=0;i<pidsjN.length;i++){
					if(pidsjS==""){
						pidsjS = pidsjN[i];
					}else{
						pidsjS = pidsjS + ","+pidsjN[i];
					}
					if(checkErr==""){// && yzlxN[i]==0
						var f = 0 ;
						var freqCode = freqCodeN[i].toUpperCase();
						if($("#ruleKey [name="+freqCode+"]").length>0){//如果 关键字规则 没有
							$("#ruleKey [name="+freqCode+"]").each(function(){
								f = -1 ;
								if( drugNameN[i].indexOf($(this).html())>-1 ){
									f = 1; 
								}
							});
						}
						if(f<1){
							if($("#batchRule [name="+freqCode+"]").length>0){//如果 没有一般规则对应的频次
								f = 2 ;
							}else{
								f = f -1 ;
							}
						}
						if(f==-2){
							checkErr = "频次["+freqCodeN[i]+"] 没有找到对应规则，无法审核通过";
						}else if(f<1){
							checkErr = "频次["+freqCodeN[i]+"] 没有对应的批次数据，无法审核通过";
						}
					}
				}
				if(checkErr!=""){
					message({
		    			html: checkErr
		        	});
					return ;
				}
				var param = {"pidsjN":pidsjS,"repeatCheck":"Y","newYzshzt":1,yzshbtglx:"","yzshbtgyy":"","yzParea":"1","checkType":"yssf"};
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
					dataType : 'json',
					cache : false,
					//showDialog: false,
					data : param,
					success : function(response) {
						if(response.code==0){
							message({html: "审方成功！"});
							refushAreaNum__1();
							qry__1();
						}else{
							message({html: response.msg});
						}
					}
				});
			}
		});
		$("#aCheckYSHMany__1").bind("click",function(){
			var pidsjNSucc = "";
			var pidsjNFail = "";
			var yzztN = getFlexiGridSelectedRowText($("#flexGrid__1"), 7);//rucangOKNum	医嘱审核状态
			var yzshztN = getFlexiGridSelectedRowText($("#flexGrid__1"), 8);//rucangOKNum	医嘱审核状态
			var yzzdshztN = getFlexiGridSelectedRowText($("#flexGrid__1"), 9);//rucangOKNum	医嘱审核状态

			var pidsjN = getFlexiGridSelectedRowText($("#flexGrid__1"), 4);//parentNo 医嘱父ID
			var pidsjS = "" ;
			if(pidsjN && pidsjN.length <1) {
				message({html: "请选择医嘱再确认"});
				return;
			}
			var drugNameN = getFlexiGridSelectedRowText($("#flexGrid__1"), 5);
			var freqCodeN = getFlexiGridSelectedRowText($("#flexGrid__1"), 6);
			if("${SYN_YZ_DATA_MODE}"=="3"){
				var checkErr ="";
				for(var i=0;i<pidsjN.length;i++){
					if(yzztN[i]==0 && yzshztN[i]==0 && yzzdshztN[i]==1){
						if(pidsjNSucc==""){
							pidsjNSucc = pidsjN[i];
						}else{
							pidsjNSucc = pidsjNSucc + "," + pidsjN[i];
						}
					}else if(yzztN[i]==0 && yzshztN[i]==0 && yzzdshztN[i]==2){
						if(pidsjNFail==""){
							pidsjNFail = pidsjN[i];
						}else{
							pidsjNFail = pidsjNFail + "," + pidsjN[i];
						}
					}else{
						continue;
					}
					var f = 0 ;
					var freqCode = freqCodeN[i].toUpperCase();
					if($("#ruleKey [name="+freqCode+"]").length>0){//如果 关键字规则 没有
						$("#ruleKey [name="+freqCode+"]").each(function(){
							f = -1 ;
							if( drugNameN[i].indexOf($(this).html())>-1 ){
								f = 1; 
							}
						});
					}
					if(f<1){
						if($("#batchRule [name="+freqCode+"]").length>0){//如果 没有一般规则对应的频次
							f = 2 ;
						}else{
							f = f -1 ;
						}
					}
					if(f==-2){
						checkErr = "频次["+freqCodeN[i]+"] 没有找到对应规则，无法确认";
						message({html: checkErr});
						return ;
					}else if(f<1){
						checkErr = "频次["+freqCodeN[i]+"] 没有对应的批次数据，无法确认";
						message({html: checkErr});
						return ;
					}
				}
				if(pidsjNSucc!="" || pidsjNFail!=""){
					$.ajax({
						type : 'POST',
						url : '${pageContext.request.contextPath}/doctorAdvice/yshCfm',
						dataType : 'json',
						cache : false,
						data :  {"pidsjNSucc":pidsjNSucc,"pidsjNFail":pidsjNFail},
						success : function(response) {
							message({html: response.mess});
							if(response.code>0){
								refushAreaNum__1();
								qry__1();
							}
						}
					});
				}else{
					checkErr = "您选择的医嘱无法进行预审确认";
					message({html: checkErr});
				}
			}
		})
		
		/*复制	 __1		变成		__2		*/
		var _columnWidth__2 = widthTemp / 7;
		$("#flexGrid__2").flexigrid({
			width : widthTemp,
			height : _pageHeight-180,
			url: "${pageContext.request.contextPath}/doctorAdvice/qryPtPageByYZ",
			dataType : 'json',
			colModel : [ 
						{display: 'inhospno', name : 'inhospno', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'parentNo', name : 'parentNo', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'pidsj', name : 'pidsj', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'drugName', name : 'drugname', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'freqCode', name : 'freqCode', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzzt', name : 'yzzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzshzt', name : 'yzshzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzzdshzt', name : 'yzzdshzt', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: 'yzzdshbtglx', name : 'yzzdshbtglx', width : 0, sortable : false, align: 'center',hide:'true'},
						
						{display: '预审方状态', name : 'yzzdshzt', width : _columnWidth__2, sortable : true, align: 'left',process: function(v,_trid,_row) {
							return "<label title='"+(_row.yzzdshbtglxS||'')+"' >"+(v==0?"":(v==1?"预审通过":(v==2?"预审不通过":"")))+"</label>";//""<label title='"+_row.yzzdshbtglxS+"'>"+v==0?"":(v==1?"预审通过":(v==2?"预审不通过":""))+"</label>";/*医嘱自动审核状态。0：未审核 1：审核通过 2：审核不通过*/
						}},
						{display: '审方结果', name : 'yzshzt', width : _columnWidth__2, sortable : true, align: 'center',process: function(v,_trid,_row) {
							return "<label id='pidsj_"+_row.pidsj+"'>"+(v==0?"未审核":(v==1?"通过":"不通过"))+"</label>";	
						}},
						{display: '床号', name : 'bedno', width : _columnWidth__2, sortable : true, align: 'center'},
						{display: '病人姓名', name : 'patname', width : _columnWidth__2, sortable : true, align: 'center'},
						{display: '组号', name : 'parentNo', width : _columnWidth__2, sortable : true, align: 'center'},
						{display: '审方日期', name : 'sfrqS', width : _columnWidth__2, sortable : true, align: 'center',process: function(v,_trid,_row) {
							return "<label id='sfrq_"+_row.pidsj+"'>"+v+"</label>";
						}}
						
						//{display: '床号', name : 'bedno', width : _columnWidth__2, sortable : true, align: 'center'},
						//{display: '姓名', name : 'patname', width : _columnWidth__2, sortable : true, align: 'center'},
						//{display: '出生日期', name : 'birthday', width : _columnWidth__2, sortable : true, align: 'center'},
						//{display: '年龄', name : 'age', width : _columnWidth__2, sortable : true, align: 'center',process: function(v,_trid,_row) {
						//	return v+getDicValue("ageUnit",_row.ageunit);	
						//}},
						//{display: '<spring:message code="pivas.patient.avdp"/>', name : 'avdp', width : _columnWidth__2, sortable : true, align: 'center'}
						],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			rowbinddata : true,
			showcheckbox : true, //是否显示多选框
			rowhandler : rowClick__2,
			onSuccess: gridSucc__2
		});
		
		$("#aSearch__2").bind("click",function(){
			qry__2();
		});
		
		$("#tabArea__2 td").bind("click",function(){
			areaCodeNow__2 = $(this).attr("areacode");
			areaNameNow__2 = $(this).html();
			$(this).parent().parent().find("tr").each(function(){
				$(this).removeClass("areaSel");
				$(this).css("background","transparent");
			});
			$(this).parent().addClass("areaSel"); 
			$(this).parent().css("background","#6F96E5");
			qry__2();
		});
		
		$("#aCheckOK__2").bind("click",function(){
			var pidsj = $("#yzInfo__2 [name=pidsj]").html();
			if(!pidsj){
				return ;
			}
			var freqCode = $("#yzInfo__2 [name=freqCode]").html();
			var drugname = $("#yzInfo__2 [name=drugname]").html();
			checkErr = "" ;
			if("${SYN_YZ_DATA_MODE}"=="3"){
				if(checkErr==""){// && yzlx==0
					var f = 0 ;
					var freqCode = freqCode.toUpperCase();
					if($("#ruleKey__2 [name="+freqCode+"]").length>0){//如果 关键字规则 没有
						$("#ruleKey__2 [name="+freqCode+"]").each(function(){
							f = -1 ;
							if( drugname.indexOf($(this).html())>-1 ){
								f = 1; 
							}
						});
					}
					if(f<1){
						if($("#batchRule [name="+freqCode+"]").length>0){//如果 没有一般规则对应的频次
							f = 2 ;
						}else{
							f = f -1 ;
						}
					}
					if(f==-2){
						checkErr = "频次["+freqCode+"] 没有找到对应规则，无法审核通过";
					}else if(f<1){
						checkErr = "频次["+freqCode+"] 没有对应的批次数据，无法审核通过";
					}
				
				}
				if(checkErr!=""){
					message({
		    			html: checkErr
		        	});
					return ;
				}
			}
			checkOne__2(pidsj,1);
		});

		$("#aCheckYSH__2").bind("click",function(){
			var pidsj = $("#yzInfo__2 [name=pidsj]").html();
			if(!pidsj){
				return ;
			}
			var freqCode = $("#yzInfo__2 [name=freqCode]").html();
			var drugname = $("#yzInfo__2 [name=drugname]").html();
			var yzzdshzt = $("#yzInfo__2 [name=yzzdshzt]").html();
			var yzzdshbtglx = $("#yzInfo__2 [name=yzzdshbtglx]").html();
			checkErr = "" ;
			if("${SYN_YZ_DATA_MODE}"=="3"){
				if(checkErr==""){// && yzlx==0
					var f = 0 ;
					var freqCode = freqCode.toUpperCase();
					if($("#ruleKey__2 [name="+freqCode+"]").length>0){//如果 关键字规则 没有
						$("#ruleKey__2 [name="+freqCode+"]").each(function(){
							f = -1 ;
							if( drugname.indexOf($(this).html())>-1 ){
								f = 1; 
							}
						});
					}
					if(f<1){
						if($("#batchRule [name="+freqCode+"]").length>0){//如果 没有一般规则对应的频次
							f = 2 ;
						}else{
							f = f -1 ;
						}
					}
					if(f==-2){
						checkErr = "频次["+freqCode+"] 没有找到对应规则，无法确认";
					}else if(f<1){
						checkErr = "频次["+freqCode+"] 没有对应的批次数据，无法确认";
					}
				
				}
				if(checkErr!=""){
					message({
		    			html: checkErr
		        	});
					return ;
				}
			}
			if(yzzdshzt>0){
				var pidsjNSucc = (yzzdshzt==1?pidsj:"") ;
				var pidsjNFail = (yzzdshzt==2?pidsj:"") ;
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/doctorAdvice/yshCfm',
					dataType : 'json',
					cache : false,
					data : {"pidsjNSucc":pidsjNSucc,"pidsjNFail":pidsjNFail},
					success : function(response) {
						if(response.code>0){
							$("#aCheckYSH__2").hide();
							if(yzzdshzt==1){
								$("#aCheckOK__2").css("background-color","#73AB66");
								$("#aCheckNO__2").css("background-color","#ebb800");
								$("#yzshbtglx__2").val("");
								$("#yzshbtglxTit__2").hide();
								$("#yzshbtgyy__2").val("");
								$("#pidsj_"+pidsj).html("通过");
							}else{
								$("#aCheckOK__2").css("background-color","#ebb800");
								$("#aCheckNO__2").css("background-color","#d45120");
								
								$("#yzshbtglx__2").val(yzzdshbtglx);
								$("#pidsj_"+pidsj).html("不通过");
							}
							$("#sfrq_"+pidsj).html(getCurrentDate("yyyy-MM-dd",null,0));
							refushAreaNum__2();
						}else{
							message({html: response.mess});
						}
						$(obj).remove();
					}
				});
			}else{
				message({
	    			html: "当前医嘱无法预审确认！"
	        	});
			}
		});
		
		$("#aCheckNO__2").bind("click",function(){
			var pidsj = $("#yzInfo__2 [name=pidsj]").html();
			if(!pidsj){
				return ;
			}
			if($("#yzshbtglx__2").val() && $("#yzshbtglx__2").val()!=""){
				$("#yzshbtglxTit__2").hide();
			}else{
				$("#yzshbtglxTit__2").show();
				return ;
			}
			checkOne__2(pidsj,2);
		});
		
		
		$("#aCheckMany__2").bind("click",function(){
			var pidsjN = getFlexiGridSelectedRowText($("#flexGrid__2"), 4);//parentNo 医嘱父ID
			var pidsjS = "" ;
			if(pidsjN && pidsjN.length <1) {
				message({html: "请选择医嘱再审方"});
				return;
			}
			var drugNameN = getFlexiGridSelectedRowText($("#flexGrid__2"), 5);
			var freqCodeN = getFlexiGridSelectedRowText($("#flexGrid__2"), 6);
			if("${SYN_YZ_DATA_MODE}"=="3"){
				var checkErr ="";
				for(var i=0;i<pidsjN.length;i++){
					if(pidsjS==""){
						pidsjS = pidsjN[i];
					}else{
						pidsjS = pidsjS + ","+pidsjN[i];
					}
					if(checkErr==""){// && yzlxN[i]==0
						var f = 0 ;
						var freqCode = freqCodeN[i].toUpperCase();
						if($("#ruleKey [name="+freqCode+"]").length>0){//如果 关键字规则 没有
							$("#ruleKey [name="+freqCode+"]").each(function(){
								f = -1 ;
								if( drugNameN[i].indexOf($(this).html())>-1 ){
									f = 1; 
								}
							});
						}
						if(f<1){
							if($("#batchRule [name="+freqCode+"]").length>0){//如果 没有一般规则对应的频次
								f = 2 ;
							}else{
								f = f -1 ;
							}
						}
						if(f==-2){
							checkErr = "频次["+freqCodeN[i]+"] 没有找到对应规则，无法审核通过";
						}else if(f<1){
							checkErr = "频次["+freqCodeN[i]+"] 没有对应的批次数据，无法审核通过";
						}
					}
				}
				if(checkErr!=""){
					message({
		    			html: checkErr
		        	});
					return ;
				}
				var param = {"pidsjN":pidsjS,"repeatCheck":"Y","newYzshzt":1,yzshbtglx:"","yzshbtgyy":"","yzParea":"1","checkType":"yssf"};
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
					dataType : 'json',
					cache : false,
					//showDialog: false,
					data : param,
					success : function(response) {
						if(response.code==0){
							message({html: "审方成功！"});
							refushAreaNum__2();
							qry__2();
						}else{
							message({html: response.msg});
						}
					}
				});
			}
		});
		$("#aCheckYSHMany__2").bind("click",function(){
			var pidsjNSucc = "";
			var pidsjNFail = "";
			var yzztN = getFlexiGridSelectedRowText($("#flexGrid__2"), 7);//rucangOKNum	医嘱审核状态
			var yzshztN = getFlexiGridSelectedRowText($("#flexGrid__2"), 8);//rucangOKNum	医嘱审核状态
			var yzzdshztN = getFlexiGridSelectedRowText($("#flexGrid__2"), 9);//rucangOKNum	医嘱审核状态

			var pidsjN = getFlexiGridSelectedRowText($("#flexGrid__2"), 4);//parentNo 医嘱父ID
			var pidsjS = "" ;
			if(pidsjN && pidsjN.length <1) {
				message({html: "请选择医嘱再确认"});
				return;
			}
			var drugNameN = getFlexiGridSelectedRowText($("#flexGrid__2"), 5);
			var freqCodeN = getFlexiGridSelectedRowText($("#flexGrid__2"), 6);
			if("${SYN_YZ_DATA_MODE}"=="3"){
				var checkErr ="";
				for(var i=0;i<pidsjN.length;i++){
					if(yzztN[i]==0 && yzshztN[i]==0 && yzzdshztN[i]==1){
						if(pidsjNSucc==""){
							pidsjNSucc = pidsjN[i];
						}else{
							pidsjNSucc = pidsjNSucc + "," + pidsjN[i];
						}
					}else if(yzztN[i]==0 && yzshztN[i]==0 && yzzdshztN[i]==2){
						if(pidsjNFail==""){
							pidsjNFail = pidsjN[i];
						}else{
							pidsjNFail = pidsjNFail + "," + pidsjN[i];
						}
					}else{
						continue;
					}
					var f = 0 ;
					var freqCode = freqCodeN[i].toUpperCase();
					if($("#ruleKey [name="+freqCode+"]").length>0){//如果 关键字规则 没有
						$("#ruleKey [name="+freqCode+"]").each(function(){
							f = -1 ;
							if( drugNameN[i].indexOf($(this).html())>-1 ){
								f = 1; 
							}
						});
					}
					if(f<1){
						if($("#batchRule [name="+freqCode+"]").length>0){//如果 没有一般规则对应的频次
							f = 2 ;
						}else{
							f = f -1 ;
						}
					}
					if(f==-2){
						checkErr = "频次["+freqCodeN[i]+"] 没有找到对应规则，无法确认";
						message({html: checkErr});
						return ;
					}else if(f<1){
						checkErr = "频次["+freqCodeN[i]+"] 没有对应的批次数据，无法确认";
						message({html: checkErr});
						return ;
					}
				}
				if(pidsjNSucc!="" || pidsjNFail!=""){
					$.ajax({
						type : 'POST',
						url : '${pageContext.request.contextPath}/doctorAdvice/yshCfm',
						dataType : 'json',
						cache : false,
						data :  {"pidsjNSucc":pidsjNSucc,"pidsjNFail":pidsjNFail},
						success : function(response) {
							message({html: response.mess});
							if(response.code>0){
								refushAreaNum__2();
								qry__2();
							}
						}
					});
				}else{
					checkErr = "您选择的医嘱无法进行预审确认";
					message({html: checkErr});
				}
			}
		});
		/*复制	 __1		变成		__2		*/
		
	});
	
	function checkOne__1(pidsj,newYzshzt){
		var param = {"pidsjN":pidsj,"repeatCheck":"Y","newYzshzt":newYzshzt,yzshbtglx:$("#yzshbtglx__1").val(),"yzshbtgyy":$("#yzshbtgyy__1").val(),"yzParea":"1","checkType":"yssf"};
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
			dataType : 'json',
			cache : false,
			//showDialog: false,
			data : param,
			success : function(response) {
				if(response.code==0){
					if(newYzshzt==1){
						$("#aCheckOK__1").css("background-color","#73AB66");
						$("#aCheckNO__1").css("background-color","#ebb800");
						$("#yzshbtglx__1").val("");
						$("#yzshbtglxTit__1").hide();
						$("#yzshbtgyy__1").val("");
						$("#pidsj_"+pidsj).html("通过");
					}else{
						$("#aCheckOK__1").css("background-color","#ebb800");
						$("#aCheckNO__1").css("background-color","#d45120");
						$("#pidsj_"+pidsj).html("不通过");
					}
					$("#sfrq_"+pidsj).html(getCurrentDate("yyyy-MM-dd",null,0));
					refushAreaNum__1();
				}else{
					message({html: response.msg});
				}
			}
		});
	}
	function search__1(param){
		paramTemp__1 = param ;
		qry__1();
	}
	function qry__1(){
		var params = [];
		if(paramTemp__1){
			params = paramTemp__1.concat();
		}
		//初始化右侧页面
		$("#yzInfo__1 .fontBold").each(function(){
			$(this).html("");
		});
		$("#yzInfo__1 [name=yzzt]").html("");
		$("#yzInfo__1 [name=medNames]").html("");
		$("#aCheckOK__1").css("background-color","#ebb800");
		$("#aCheckNO__1").css("background-color","#ebb800");
		
		$("#medicTab__1 .medicTd").remove();
		
		params.push({"name":"ydztLowN","value":5});
		params.push({"name":"yzztLowN","value":1});
		params.push({"name":"areaEnabled","value":1});
		//params.push({"name":"filterTimeLow12","value":1});
		params.push({"name":"wardCode","value":areaCodeNow__1});
		params.push({"name":"leftWithPati","value":1});
		params.push({"name":"yzlx","value":0});
		params.push({"name":"yzResource","value":1});
		//params.push({"name":"yzlx","value":1});
		//params.push({"name":"yzResourceUpEQN","value":2});
		$('#flexGrid__1').flexOptions({
			newp: 1,
			extParam: params,
        	url: "${pageContext.request.contextPath}/doctorAdvice/qryPtPageByYZ"
        }).flexReload();
	}
	function gridSucc__1(grid){
		var inhospno = "" ;
		$("#flexGrid__1 tr").each(function(i){
			var inhospno2 =  $.trim($(this).find("td:eq(3) div").text());
			if(inhospno!="" && inhospno!=inhospno2){
				$(this).find("td").each(function(i){
					$(this).css("border-top", "2px solid black");
				});
			}
			inhospno = inhospno2;
		});
		$("#flexGrid__1 tr:first").addClass("trSelect");
		var columnsArray =$("#flexGrid__1 tr:first").attr('ch').split("_FG$SP_");
		showYZ__1(columnsArray[2]);
	}
	function rowClick__1(r) {
		
		$(r).click(
		function() {
			$("#flexGrid__1 tr").each(function(){
				$(this).removeClass("trSelect");
			});
			$(r).addClass("trSelect");
			//获取该行的所有列数据
			var columnsArray = $(r).attr('ch').split("_FG$SP_");
			showYZ__1(columnsArray[2]);
		});
	}
	function refushAreaNum__1(){
		var parms = {} ;
		parms['ydztLowN'] = 5;
		parms['yzztLowN'] = 1;
		parms['areaEnabled'] = 1;
		parms['yzlx'] = 0;
		parms['yzResource'] = 1;
		//parms['filterTimeLow12']=1;
		//parms['yzlx'] = 1;
		//parms['yzResourceUpEQN'] = 2;
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/groupNumByInpArea',
			dataType : 'json',
			cache : false,
			showDialog: false,
			data : parms,
			success : function(data) {
				$("#tabArea__1 tr").hide();
				var list = data || [];
				var checkOkNumAll = 0 ;
				var countNumAll = 0 ;
				if(list && list.length>0){
					areaCodeNow__1=list[0].wardCode;
					for(var i in list){
						var row = list[i];
						if(row.countNum>0){
							checkOkNumAll = checkOkNumAll + row.checkOkNum;
							countNumAll = countNumAll + row.countNum;
							$("#area__1_"+row.wardCode).html(row.checkOkNum+"/"+row.countNum).attr("present",row.checkOkNum/row.countNum);
							$("#area__1_"+row.wardCode).parent().parent().show();
						}
					}
				}
				$("#area__1_0").html(checkOkNumAll+"/"+countNumAll);
				if(areaCodeNow__1){
					$("#tabArea__1 tr[style='display: table-row;'] :first").addClass("areaSel");
					qry__1();
				}
			}
		});
	}
	
	function showYZ__1(pidsj){
		if(pidsj && pidsj!=""){
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/doctorAdvice/dctrAdvcInfo',
				dataType : 'json',
				cache : false,
				showDialog: false,
				data : {"pidsj":pidsj},
				success : function(result) {
					if(result && result.code>0){
						var yzMain = result.data.yzMain;
						$("#yzInfo__1 .fontBold").each(function(){
							if($(this).attr("name")=="sex"){
								$(this).html(yzMain[$(this).attr("name")]? getDicValue("sex",yzMain[$(this).attr("name")]) :(yzMain[$(this).attr("name")]===0 ?getDicValue("sex",yzMain[$(this).attr("name")]):""));	
							}else if($(this).attr("name")=="age"){
								var age=yzMain[$(this).attr("name")] || '';
								if(age){
									if(age!=0){
										$(this).html(age+getDicValue("ageUnit",yzMain["ageunit"]));
									}
								}
							}else{
								$(this).html(yzMain[$(this).attr("name")]?yzMain[$(this).attr("name")]:"");	
							}
							
						});
						$("#yzInfo__1 [name=yzzt]").html(getDicValue('yzzt',yzMain['yzzt']));
						$("#yzInfo__1 [name=medNames]").html(result.data.medNames?result.data.medNames:"");
						
						if(yzMain.yzzdshzt==1){
							$("#labYSHzt__1").html("预审通过").attr("title","");
							if(yzMain.yzzt==0 && yzMain.yzshzt==0){
								$("#aCheckYSH__1").show();
							}else{
								$("#aCheckYSH__1").hide();
							}
						}else if(yzMain.yzzdshzt==2){
							$("#labYSHzt__1").html("预审不通过").attr("title",yzMain.yzzdshbtglxS||'');
							if(yzMain.yzzt==0 && yzMain.yzshzt==0){
								$("#aCheckYSH__1").show();
							}else{
								$("#aCheckYSH__1").hide();
							}
						}else{
							$("#labYSHzt__1").html("").attr("title",yzMain.yzzdshbtglxS||'');
							$("#aCheckYSH__1").hide();
						}
						
						//ebb800 黄色。73AB66 绿色，d45120 红色
						if(yzMain.yzshzt==1){
							$("#aCheckOK__1").css("background-color","#73AB66");
							$("#aCheckNO__1").css("background-color","#ebb800");
							$("#yzshbtglxTit__1").hide();
							$("#yzshbtglx__1").val("");
							$("#yzshbtgyy__1").val("");
						}else if(yzMain.yzshzt==2){
							$("#aCheckOK__1").css("background-color","#ebb800");
							$("#aCheckNO__1").css("background-color","#d45120");
							
							$("#yzshbtglx__1").val(yzMain.yzshbtglx);
							$("#yzshbtgyy__1").val(yzMain.yzshbtgyy);
						}else if(yzMain.yzshzt==0){
							$("#aCheckOK__1").css("background-color","#ebb800");
							$("#aCheckNO__1").css("background-color","#ebb800");
							
							$("#yzshbtglx__1").val("");
							$("#yzshbtgyy__1").val("");
						}
						$("#medicTab__1 .medicTd").remove();
						var yzList = result.data.yzList;
						for(var i in yzList){
							$("#medicTab__1").append("<tr class='medicTd'><td>"+yzList[i].chargeCode+"</td><td>"+yzList[i].medicamentsName+"</td><td>"+yzList[i].specifications2+"</td><td>"+yzList[i].dose+yzList[i].doseUnit+"</td><td>"+yzList[i].quantity+yzList[i].medicamentsPackingUnit+"</td></tr>");
						}
					}else{
						$("#yzInfo__1 .fontBold").each(function(){
							$(this).html("");
						});
						$("#medicTab__1 .medicTd").remove();
					}
				}
			});
		}else{
		}
	}
	
	/*复制	 __1		变成		__2		*/
	function checkOne__2(pidsj,newYzshzt){
		var param = {"pidsjN":pidsj,"repeatCheck":"Y","newYzshzt":newYzshzt,yzshbtglx:$("#yzshbtglx__2").val(),"yzshbtgyy":$("#yzshbtgyy__2").val(),"yzParea":"1","checkType":"yssf"};
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
			dataType : 'json',
			cache : false,
			//showDialog: false,
			data : param,
			success : function(response) {
				if(response.code==0){
					if(newYzshzt==1){
						$("#aCheckOK__2").css("background-color","#73AB66");
						$("#aCheckNO__2").css("background-color","#ebb800");
						$("#yzshbtglx__2").val("");
						$("#yzshbtglxTit__2").hide();
						$("#yzshbtgyy__2").val("");
						$("#pidsj_"+pidsj).html("通过");
					}else{
						$("#aCheckOK__2").css("background-color","#ebb800");
						$("#aCheckNO__2").css("background-color","#d45120");
						$("#pidsj_"+pidsj).html("不通过");
					}
					$("#sfrq_"+pidsj).html(getCurrentDate("yyyy-MM-dd",null,0));
					refushAreaNum__2();
				}else{
					message({html: response.msg});
				}
			}
		});
	}
	function search__2(param){
		paramTemp__2 = param ;
		qry__2();
	}
	function qry__2(){
		var params = [];
		if(paramTemp__2){
			params = paramTemp__2.concat();
		}
		//初始化右侧页面
		$("#yzInfo__2 .fontBold").each(function(){
			$(this).html("");
		});
		$("#yzInfo__2 [name=yzzt]").html("");
		$("#yzInfo__2 [name=medNames]").html("");
		$("#aCheckOK__2").css("background-color","#ebb800");
		$("#aCheckNO__2").css("background-color","#ebb800");
		
		$("#medicTab__2 .medicTd").remove();
		
		params.push({"name":"ydztLowN","value":5});
		params.push({"name":"yzztLowN","value":1});
		params.push({"name":"areaEnabled","value":1});
		//params.push({"name":"filterTimeLow12","value":1});
		params.push({"name":"wardCode","value":areaCodeNow__2});
		params.push({"name":"leftWithPati","value":1});
		//params.push({"name":"yzlx","value":0});
		//params.push({"name":"yzResource","value":1});
		params.push({"name":"yzlx","value":1});
		params.push({"name":"yzResourceUpEQN","value":2});
		
		$('#flexGrid__2').flexOptions({
			newp: 1,
			extParam: params,
        	url: "${pageContext.request.contextPath}/doctorAdvice/qryPtPageByYZ"
        }).flexReload();
	}
	function gridSucc__2(grid){
		var inhospno = "" ;
		$("#flexGrid__2 tr").each(function(i){
			var inhospno2 =  $.trim($(this).find("td:eq(3) div").text());
			if(inhospno!="" && inhospno!=inhospno2){
				$(this).find("td").each(function(i){
					$(this).css("border-top", "2px solid black");
				});
			}
			inhospno = inhospno2;
		});
		$("#flexGrid__2 tr:first").addClass("trSelect");
		var columnsArray =$("#flexGrid__2 tr:first").attr('ch').split("_FG$SP_");
		showYZ__2(columnsArray[2]);
	}
	function rowClick__2(r) {
		
		$(r).click(
		function() {
			$("#flexGrid__2 tr").each(function(){
				$(this).removeClass("trSelect");
			});
			$(r).addClass("trSelect");
			//获取该行的所有列数据
			var columnsArray = $(r).attr('ch').split("_FG$SP_");
			showYZ__2(columnsArray[2]);
		});
	}
	function refushAreaNum__2(){
		var parms = {} ;
		parms['ydztLowN'] = 5;
		parms['yzztLowN'] = 1;
		parms['areaEnabled'] = 1;
		parms['yzlx'] = 0;
		//parms['yzResource'] = 1;
		//parms['filterTimeLow12']=1;
		parms['yzlx'] = 1;
		parms['yzResourceUpEQN'] = 2;
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/groupNumByInpArea',
			dataType : 'json',
			cache : false,
			showDialog: false,
			data : parms,
			success : function(data) {
				$("#tabArea__2 tr").hide();
				var list = data || [];
				var checkOkNumAll = 0 ;
				var countNumAll = 0 ;
				if(list && list.length>0){
					areaCodeNow__2=list[0].wardCode;
					for(var i in list){
						var row = list[i];
						if(row.countNum>0){
							checkOkNumAll = checkOkNumAll + row.checkOkNum;
							countNumAll = countNumAll + row.countNum;
							$("#area__2_"+row.wardCode).html(row.checkOkNum+"/"+row.countNum).attr("present",row.checkOkNum/row.countNum);
							$("#area__2_"+row.wardCode).parent().parent().show();
						}
					}
				}
				$("#area__2_0").html(checkOkNumAll+"/"+countNumAll);
				
				if(areaCodeNow__2){
					$("#tabArea__2 tr[style='display: table-row;']:first").addClass("areaSel");
					qry__2();
				}
			}
		});
	}
	
	function showYZ__2(pidsj){
		if(pidsj && pidsj!=""){
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/doctorAdvice/dctrAdvcInfo',
				dataType : 'json',
				cache : false,
				showDialog: false,
				data : {"pidsj":pidsj},
				success : function(result) {
					if(result && result.code>0){
						var yzMain = result.data.yzMain;
						$("#yzInfo__2 .fontBold").each(function(){
							if($(this).attr("name")=="sex"){
								$(this).html(yzMain[$(this).attr("name")] ? getDicValue("sex",yzMain[$(this).attr("name")]) :(yzMain[$(this).attr("name")]===0 ?getDicValue("sex",yzMain[$(this).attr("name")]):""));	
							}else if($(this).attr("name")=="age"){
								var age=yzMain[$(this).attr("name")] || '';
								if(age){
									if(age!=0){
										$(this).html(age+getDicValue("ageUnit",yzMain["ageunit"]));
									}	
								}
							}else{
								$(this).html(yzMain[$(this).attr("name")]?yzMain[$(this).attr("name")]:"");	
							}
						});
						$("#yzInfo__2 [name=yzzt]").html(getDicValue('yzzt',yzMain['yzzt']));
						$("#yzInfo__2 [name=medNames]").html(result.data.medNames?result.data.medNames:"");

						if(yzMain.yzzdshzt==1){
							$("#labYSHzt__2").html("预审通过").attr("title","");
							if(yzMain.yzzt==0 && yzMain.yzshzt==0){
								$("#aCheckYSH__2").show();
							}else{
								$("#aCheckYSH__2").hide();
							}
						}else if(yzMain.yzzdshzt==2){
							$("#labYSHzt__2").html("预审不通过").attr("title",yzMain.yzzdshbtglxS||'');
							if(yzMain.yzzt==0 && yzMain.yzshzt==0){
								$("#aCheckYSH__2").show();
							}else{
								$("#aCheckYSH__2").hide();
							}
						}else{
							$("#labYSHzt__2").html("").attr("title",yzMain.yzzdshbtglxS||'');
							$("#aCheckYSH__2").hide();
						}
						
						//ebb800 黄色。73AB66 绿色，d45120 红色
						if(yzMain.yzshzt==1){
							$("#aCheckOK__2").css("background-color","#73AB66");
							$("#aCheckNO__2").css("background-color","#ebb800");
							$("#yzshbtglxTit__2").hide();
							$("#yzshbtglx__2").val("");
							$("#yzshbtgyy__2").val("");
						}else if(yzMain.yzshzt==2){
							$("#aCheckOK__2").css("background-color","#ebb800");
							$("#aCheckNO__2").css("background-color","#d45120");
							
							$("#yzshbtglx__2").val(yzMain.yzshbtglx);
							$("#yzshbtgyy__2").val(yzMain.yzshbtgyy);
						}else if(yzMain.yzshzt==0){
							$("#aCheckOK__2").css("background-color","#ebb800");
							$("#aCheckNO__2").css("background-color","#ebb800");
							
							$("#yzshbtglx__2").val("");
							$("#yzshbtgyy__2").val("");
						}
						$("#medicTab__2 .medicTd").remove();
						var yzList = result.data.yzList;
						for(var i in yzList){
							$("#medicTab__2").append("<tr class='medicTd'><td>"+yzList[i].chargeCode+"</td><td>"+yzList[i].medicamentsName+"</td><td>"+yzList[i].specifications2+"</td><td>"+yzList[i].dose+yzList[i].doseUnit+"</td><td>"+yzList[i].quantity+yzList[i].medicamentsPackingUnit+"</td></tr>");
						}
					}else{
						$("#yzInfo__2 .fontBold").each(function(){
							$(this).html("");
						});
						$("#medicTab__2 .medicTd").remove();
					}
				}
			});
		}else{
		}
	}
	/*复制	 __1		变成		__2		*/
	
	
	function showSearch1(){
		$("#yzMainDiv__1").css("display","block");
		$(".tab-title-1").addClass("select");
		$("#yzMainDiv__2").css("display","none");
		$(".tab-title-2").removeClass("select");
		
		$("#yzMainDiv__1").css("display","block");
		$("#yzMainDiv__1 .oe_searchview222").removeClass("oe_searchview222").addClass("oe_searchview");
		$("#yzMainDiv__1 .oe_searchview_head222").removeClass("oe_searchview_head222").addClass("oe_searchview_head");
		$("#yzMainDiv__1 .divselect222").removeClass("divselect222").addClass("divselect");
		$("#yzMainDiv__1 .oe_search_txt222").removeClass("oe_search_txt222").addClass("oe_search_txt");
		$("#yzMainDiv__1 .oe_tag_dark222").removeClass("oe_tag_dark222").addClass("oe_tag_dark");
		
		$("#yzMainDiv__2").css("display","none");
		$("#yzMainDiv__2 .oe_searchview").removeClass("oe_searchview").addClass("oe_searchview222");
		$("#yzMainDiv__2 .oe_searchview_head").removeClass("oe_searchview_head").addClass("oe_searchview_head222");
		$("#yzMainDiv__2 .divselect").removeClass("divselect").addClass("divselect222");
		$("#yzMainDiv__2 .oe_search_txt").removeClass("oe_search_txt").addClass("oe_search_txt222");
		$("#yzMainDiv__2 .oe_tag_dark").removeClass("oe_tag_dark").addClass("oe_tag_dark222");
	}
	function showSearch2(){
		$("#yzMainDiv__2").css("display","block");
		$(".tab-title-2").addClass("select");
		$("#yzMainDiv__1").css("display","none");
		$(".tab-title-1").removeClass("select");
		
		$("#yzMainDiv__2").css("display","block");
		$("#yzMainDiv__2 .oe_searchview222").removeClass("oe_searchview222").addClass("oe_searchview");
		$("#yzMainDiv__2 .oe_searchview_head222").removeClass("oe_searchview_head222").addClass("oe_searchview_head");
		$("#yzMainDiv__2 .divselect222").removeClass("divselect222").addClass("divselect");
		$("#yzMainDiv__2 .oe_search_txt222").removeClass("oe_search_txt222").addClass("oe_search_txt");
		$("#yzMainDiv__2 .oe_tag_dark222").removeClass("oe_tag_dark222").addClass("oe_tag_dark");
		
		$("#yzMainDiv__1").css("display","none");
		$("#yzMainDiv__1 .oe_searchview").removeClass("oe_searchview").addClass("oe_searchview222");
		$("#yzMainDiv__1 .oe_searchview_head").removeClass("oe_searchview_head").addClass("oe_searchview_head222");
		$("#yzMainDiv__1 .divselect").removeClass("divselect").addClass("divselect222");
		$("#yzMainDiv__1 .oe_search_txt").removeClass("oe_search_txt").addClass("oe_search_txt222");
		$("#yzMainDiv__1 .oe_tag_dark").removeClass("oe_tag_dark").addClass("oe_tag_dark222");
	}
	
	</script>
	
	
</head>
<body>



<div class="medicine-tab" style="    width: 98%;" ><!-- max-width: 1100px; -->
<div class="tabs-title" >
		<ul style="  float: left;">
			<li><a style="width: 90px;display: block;height: 26px;line-height: 26px;width: 90px;text-align: center;cursor: pointer;" class="tab-title-1 select">长嘱</a></li>
			<li><a style="width: 90px;display: block;height: 26px;line-height: 26px;width: 90px;text-align: center;cursor: pointer;" class="tab-title-2 ">临嘱</a></li>
		</ul>
</div>
<div class="tabs-content" style="margin-top: -2px;border-top: 1.5px solid;">
	
	
	<div style="height:0px;">
	
			<ul id="batchRule" style="visibility: hidden;height: 0px;max-height: 0px;" >
			<c:forEach items="${batchRuleList}" var="batchRule" >
			<c:if test="${empty batchRule.ru_key}">
			<li name="${batchRule.pinc_code}" style="height: 0px;" >${batchRule.pinc_name}</li>
			</c:if>
			</c:forEach>
			</ul>
			
			<ul id="ruleKey" style="visibility: hidden;height: 0px;max-height: 0px;" >
			<c:forEach items="${ruleList}" var="rule" >
			<c:if test="${!empty rule.ru_key}">
			<li name="${rule.pinc_code}" style="height: 0px;" >${rule.ru_key}</li>
			</c:if>
			</c:forEach>
			</ul>
			
	</div>
	
	
	<div class="tab-content-1"  style="display: block;">
	<div id="yzMainDiv__1" class="main-div">
	 	
	 	
	<table style="height: 100%;width:100%;" >
	<tr>
		<td class="td200Left" style="padding: 0px 0px;">
			<div class="category-list" style="padding-left: 10px;" >
				<table class="tabWith100">
					<tr class="trArea" >
						<td class="tdLeft10" >全部
							<div id="area__1_0" class="category-progressor" style="right: auto;position: relative;top:auto;    margin-left: 120px; margin-top: -17px;" >0/0</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="divLeftMenuList category-list" style="overflow-y: auto;float:none; " >
				<table class="tabWith100" id="tabArea__1" >
					<c:forEach items="${inpAreaList}" var="area" varStatus="rowStatus" >
					<tr class="trArea <c:if test="${rowStatus.index==0}">areaSel</c:if>" >
						<td class="tdLeft10" areacode="${area.deptCode}" >${area.deptName}
							<div id="area__1_${area.deptCode}" present="0/0" class="category-progressor" style="right: auto;position: relative;top:auto;    margin-left: 120px; margin-top: -17px;" >&nbsp;</div>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</td>
		<td style="vertical-align: top;    padding: 0px 4px;">
			
			
			<table>
			<tr>
			<td style="width: 50%;vertical-align: top;min-width: 370px;    padding: 0px 3px;" >
			
				<!-- 搜索条件--开始 -->
				<div class="oe_searchview" style="margin-top: 12px;width:270px;min-height: 30px;"><!-- 0115bianxw修改 添加宽度设置 -->
				    <div class="oe_searchview_facets" >
					    <div class="oe_searchview_input oe_searchview_head"></div>
					    <div class="oe_searchview_input"  id="inputsearch__1" >
					    	  <input id="txt__1" type="text" class="oe_search_txt" style="border: none;max-height: 18px;width:130px;"/>
					    </div>
				    </div>
				    <img alt="" style="top:55px;position: absolute;" src="${pageContext.request.contextPath}/assets/search/images/searchblue.png">
				    <div class="classX" onclick="clearclosedinputall();" style="" ></div><!-- 0115bianxw修改 left: 735px; -->
					<div class="oe-autocomplete" ></div>
					<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
						<cite>请选择...</cite>
						<ul class="ulQry" style="-webkit-border-radius: 20;" funname="search__1" >
							<li show="<spring:message code='pivas.yz2.bedno'/>" name="bednoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.bedno'/>：<span class="searchVal"></span></li>
							<li show="<spring:message code='pivas.yz2.patname'/>" name="patnameS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.patname'/>：<span class="searchVal"></span></li>
							<li show="<spring:message code='pivas.yz1.parentNo'/>" name="parentNoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.parentNo'/>：<span class="searchVal"></span></li>
							<li show="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.freqCode'/>：<span class="searchVal"></span></li>
						</ul>
					</div>
					
				</div>
				<!-- 搜索条件--结束 -->
				
				<div style="margin-top: 17px;">
					<a class="button" id="aCheckMany__1" style="display: ;background-color:#4CAF50;    margin-left: 15px;">通过</a>
					<a class="button" id="aCheckYSHMany__1" style="display: ;background-color: rgb(235, 184, 0);">预审确认</a>
				</div>
				
				
				<div style="width: 100%;    display: flex;margin-top: 18px;" >
					<table id="flexGrid__1" style="margin: 0px;"></table>
				</div>
				
			</td>
			<td style="vertical-align: top;padding: 0px 3px;">
			
			<div class="rightDiv" style="background-color: white;width:100%;margin-top: 10px;overflow-y: auto;" >
				<div style="background-color: #6E96E4;text-align: center;color:white;height:40px;padding-top: 10px;font-size: 14px;">
				处方信息
				</div>
				<div style="padding-left: 5px;padding-right: 5px;margin-top: 7px;" >
				<table id="yzInfo__1" >
					<tr style="background: transparent;" >
						<td style="width:100%;text-align: right;" colspan="4" >
							<label id="labYSHzt__1" style="font-weight: bold;"></label>
							<a class="button" id="aCheckYSH__1" style="padding: 0.55em 1.5em;display: none;">预审确认</a>
							<a class="button" id="aCheckOK__1" style="padding: 0.55em 1.5em;">通过</a>
							<a class="button" id="aCheckNO__1" style="padding: 0.55em 1.5em;">不通过</a>
						</td>
					</tr>
					<tr>
						<td colspan="4" style="height: 7px;">
							<div class="fontBold" name="pidsj" style="visibility: hidden;height:0px;max-height:0px;" ></div>
							<div class="fontBold" name="drugname" style="visibility: hidden;height:0px;max-height:0px;" ></div>
							<div class="fontBold" name="yzzdshzt" style="visibility: hidden;height:0px;max-height:0px;" ></div>
							<div class="fontBold" name="yzzdshbtglx" style="visibility: hidden;height:0px;max-height:0px;" ></div>
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							开立医生：
						</td>
						<td width="30%" class="fontBold" name="doctorName" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							频次：
						</td>
						<td width="30%" class="fontBold" name="freqCode" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							开立时间：
						</td>
						<td width="30%" class="fontBold" name="startTimeS" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							医嘱组号：
						</td>
						<td width="30%" class="fontBold" name="parentNo" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							结束时间：
						</td>
						<td width="30%" class="fontBold" name="endTimeS" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							药品类型：
						</td>
						<td width="30%" class="fontBold" name="medNames" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							用药途径：
						</td>
						<td width="30%" class="fontBold" name="supplyName" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							滴速：
						</td>
						<td width="30%" class="fontBold" name="dropSpeed" >
							
						</td>
					</tr>
					<tr>
						<td colspan="4" style="height: 7px;"></td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							病人姓名：
						</td>
						<td width="30%" class="fontBold" name="patname" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							床号：
						</td>
						<td width="30%" class="fontBold" name="bedno" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;">
							性别：
						</td>
						<td width="30%" class="fontBold" name="sex" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							年龄：
						</td>
						<td width="30%" class="fontBold" name="age" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							出生日期：
						</td>
						<td width="30%" class="fontBold" name="birthdayS" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							体重：
						</td>
						<td width="30%" class="fontBold" name="avdp" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							住院号：
						</td>
						<td width="30%" class="fontBold" name="caseId" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							流水号：
						</td>
						<td width="30%" class="fontBold" name="inpatientNo" >
							
						</td>
					</tr>
					
					<tr style="background: transparent;" >
						<td colspan="4" style="height: 7px;"></td>
					</tr>
				</table>
				
				<table id="medicTab__1">
					<tr style="background: #B5B5B5;" id="medicTr__1" >
						<td width="15%" >药品编码</td>
						<td width="40%" >药品名称</td>
						<td width="15%" >规格</td>
						<td width="15%" >剂量</td>
						<td width="15%" >数量</td>
					</tr>
					<!-- <tr class="medicTd"><td>125</td><td>达到按到完全带我去</td><td>100ml</td><td>每天一次</td><td></td></tr> -->
				</table>
				<hr style="border : 1px dashed gray;" />
				<table>
					<tr style="height: 30px;font-size: 15px;">
						<td id="yzAutoResult__1">
							不通过原因：
						</td>
					</tr>
					<tr style="height: 40px;">
						<td>
							<select style="height: 23px;" id="yzshbtglx__1" >
								<option value=""><spring:message code='comm.mess19'/></option>
								<c:forEach items="${errTypeList}" var="errType"  >
								<option value="${errType.gid}">${errType.name}</option>
								</c:forEach>
							</select>
							<span class="tip" id="yzshbtglxTit__1" style="display: none;">
                                        <div class="arrow" style="left:0px;">
                                            <div class="tip-content" style="width:93px;top: -16px;left: 15px;"><!-- 0115bianxw修改 top: -28px;left: -130px; -->
                                                		请选择原因<!--请选择不通过原因-->
                                            </div>
                                        </div>
                            </span>
							<input type="text" style="float: right;width: 130px;" id="yzshbtgyy__1" maxlength="256">
							<label style="float: right;right: 176px;">备注：</label>
						</td>
					</tr>
					<tr>
						<td>
							<div style="border: #d4d4d4 1px solid;font-size: 14px; padding-top: 10px;padding-left: 10px;padding-bottom: 10px;" >
							<label style="color:#C02624">年龄禁忌</label>
							<br>
							描述：老年患者身体机能降低，故用药期间注意观察。
							</div>
							
							
							<ul id="batchRule__1" style="visibility: hidden;height: 0px;" >
							<c:forEach items="${batchRuleList}" var="batchRule" >
							<c:if test="${empty batchRule.ru_key}">
							<li name="${batchRule.pinc_code}" style="height: 0px;" >${batchRule.pinc_name}</li>
							</c:if>
							</c:forEach>
							</ul>			
											
							<ul id="ruleKey__1" style="visibility: hidden;height: 0px;" >
							<c:forEach items="${ruleList}" var="rule" >
							<c:if test="${!empty rule.ru_key}">
							<li name="${rule.pinc_code}" style="height: 0px;" >${rule.ru_key}</li>
							</c:if>
							</c:forEach>
							</ul>
							
							
							
						</td>
					</tr>
				</table>
				</div>
			</div>
			

			
			
			</td>
			</tr>
			</table>
	



			
		</td>
	</tr>
	</table>
	</div>


	<div id="yzMainDiv__2" class="tab-content-2" style="display:none;" >
	<!-- 复制	 __1		变成		__2		 -->
	<table style="height: 100%;width:100%;" >
	<tr>
		<td class="td200Left" style="padding: 0px 0px;">
			<div class="category-list" style="padding-left: 10px;" >
				<table class="tabWith100">
					<tr class="trArea" >
						<td class="tdLeft10" >全部
							<div id="area__2_0" class="category-progressor" style="right: auto;position: relative;top:auto;    margin-left: 120px; margin-top: -17px;" >0/0</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="divLeftMenuList category-list" style="overflow-y: auto;float:none; " >
				<table class="tabWith100" id="tabArea__2" >
					<c:forEach items="${inpAreaList}" var="area" varStatus="rowStatus" >
					<tr class="trArea <c:if test="${rowStatus.index==0}">areaSel</c:if>" >
						<td class="tdLeft10" areacode="${area.deptCode}" >${area.deptName}
							<div id="area__2_${area.deptCode}" present="0/0" class="category-progressor" style="right: auto;position: relative;top:auto;    margin-left: 120px; margin-top: -17px;" >&nbsp;</div>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</td>
		<td style="vertical-align: top;    padding: 0px 4px;">
			
			
			<table>
			<tr>
			<td style="width: 50%;vertical-align: top;min-width: 370px;    padding: 0px 3px;" >
			
				<!-- 搜索条件--开始 -->
				<div class="oe_searchview" style="margin-top: 12px;width:270px;min-height: 30px;"><!-- 0115bianxw修改 添加宽度设置 -->
				    <div class="oe_searchview_facets" >
					    <div class="oe_searchview_input oe_searchview_head"></div>
					    <div class="oe_searchview_input"  id="inputsearch__2" >
					    	  <input id="txt__2" type="text" class="oe_search_txt" style="border: none;max-height: 18px;width:130px;"/>
					    </div>
				    </div>
				    <img alt="" style="top:55px;position: absolute;" src="${pageContext.request.contextPath}/assets/search/images/searchblue.png">
				    <div class="classX" onclick="clearclosedinputall();" style="" ></div><!-- 0115bianxw修改 left: 735px; -->
					<div class="oe-autocomplete" ></div>
					<div style="border:1px solid #D2D2D2;display:none;" width="50px" heiht="50px" class="divselect" style="">
						<cite>请选择...</cite>
						<ul class="ulQry" style="-webkit-border-radius: 20;" funname="search__2" >
							<li show="<spring:message code='pivas.yz2.bedno'/>" name="bednoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.bedno'/>：<span class="searchVal"></span></li>
							<li show="<spring:message code='pivas.yz2.patname'/>" name="patnameS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz2.patname'/>：<span class="searchVal"></span></li>
							<li show="<spring:message code='pivas.yz1.parentNo'/>" name="parentNoS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.parentNo'/>：<span class="searchVal"></span></li>
							<li show="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" ><spring:message code='pivas_yz1.search'/> <spring:message code='pivas.yz1.freqCode'/>：<span class="searchVal"></span></li>
						</ul>
					</div>
					
				</div>
				<!-- 搜索条件--结束 -->
				
				<div style="margin-top: 17px;">
					<a class="button" id="aCheckMany__2" style="display: ;background-color: #4CAF50;    margin-left: 15px;">通过</a>
					<a class="button" id="aCheckYSHMany__2" style="display: ;background-color: rgb(235, 184, 0);">预审确认</a>
				</div>
				
				
				<div style="width: 100%;    display: flex;margin-top: 18px;" >
					<table id="flexGrid__2" style="margin: 0px;"></table>
				</div>
				
			</td>
			<td style="vertical-align: top;padding: 0px 3px;">
			
			<div class="rightDiv" style="background-color: white;width:100%;margin-top: 10px;overflow-y: auto;" >
				<div style="background-color: #6E96E4;text-align: center;color:white;height:40px;padding-top: 10px;font-size: 14px;">
				处方信息
				</div>
				<div style="padding-left: 5px;padding-right: 5px;margin-top: 7px;" >
				<table id="yzInfo__2" >
					<tr style="background: transparent;" >
						<td style="width:100%;text-align: right;" colspan="4" >
							<label id="labYSHzt__2" style="font-weight: bold;"></label>
							<a class="button" id="aCheckYSH__2" style="padding: 0.55em 1.5em;display: none;">预审确认</a>
							<a class="button" id="aCheckOK__2" style="padding: 0.55em 1.5em;">通过</a>
							<a class="button" id="aCheckNO__2" style="padding: 0.55em 1.5em;">不通过</a>
						</td>
					</tr>
					<tr>
						<td colspan="4" style="height: 7px;">
							<div class="fontBold" name="pidsj" style="visibility: hidden;height:0px;max-height:0px;" ></div>
							<div class="fontBold" name="drugname" style="visibility: hidden;height:0px;max-height:0px;" ></div>
							<div class="fontBold" name="yzzdshzt" style="visibility: hidden;height:0px;max-height:0px;" ></div>
							<div class="fontBold" name="yzzdshbtglx" style="visibility: hidden;height:0px;max-height:0px;" ></div>
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							开立医生：
						</td>
						<td width="30%" class="fontBold" name="doctorName" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							频次：
						</td>
						<td width="30%" class="fontBold" name="freqCode" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							开立时间：
						</td>
						<td width="30%" class="fontBold" name="startTimeS" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							医嘱组号：
						</td>
						<td width="30%" class="fontBold" name="parentNo" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							结束时间：
						</td>
						<td width="30%" class="fontBold" name="endTimeS" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							药品类型：
						</td>
						<td width="30%" class="fontBold" name="medNames" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							用药途径：
						</td>
						<td width="30%" class="fontBold" name="supplyName" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							滴速：
						</td>
						<td width="30%" class="fontBold" name="dropSpeed" >
							
						</td>
					</tr>
					<tr>
						<td colspan="4" style="height: 7px;"></td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							病人姓名：
						</td>
						<td width="30%" class="fontBold" name="patname" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							床号：
						</td>
						<td width="30%" class="fontBold" name="bedno" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							性别：
						</td>
						<td width="30%" class="fontBold" name="sex" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							年龄：
						</td>
						<td width="30%" class="fontBold" name="age" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							出生日期：
						</td>
						<td width="30%" class="fontBold" name="birthdayS" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							体重：
						</td>
						<td width="30%" class="fontBold" name="avdp" >
							
						</td>
					</tr>
					<tr style="background: #E1E9F8;padding-left: 5px;padding-right: 5px;" >
						<td width="20%" style="text-align: right;" >
							住院号：
						</td>
						<td width="30%" class="fontBold" name="caseId" >
							
						</td>
						<td width="20%" style="text-align: right;" >
							流水号：
						</td>
						<td width="30%" class="fontBold" name="inpatientNo" >
							
						</td>
					</tr>
					
					<tr style="background: transparent;" >
						<td colspan="4" style="height: 7px;"></td>
					</tr>
				</table>
				
				<table id="medicTab__2">
					<tr style="background: #B5B5B5;" id="medicTr__2" >
						<td width="15%" >药品编码</td>
						<td width="40%" >药品名称</td>
						<td width="15%" >规格</td>
						<td width="15%" >剂量</td>
						<td width="15%" >数量</td>
					</tr>
					<!-- <tr class="medicTd"><td>125</td><td>达到按到完全带我去</td><td>100ml</td><td>每天一次</td><td></td></tr> -->
				</table>
				<hr style="border : 1px dashed gray;" />
				<table>
					<tr style="height: 30px;font-size: 15px;">
						<td id="yzAutoResult__2">
							不通过原因：
						</td>
					</tr>
					<tr style="height: 40px;">
						<td>
							<select style="height: 23px;" id="yzshbtglx__2" >
								<option value=""><spring:message code='comm.mess19'/></option>
								<c:forEach items="${errTypeList}" var="errType"  >
								<option value="${errType.gid}">${errType.name}</option>
								</c:forEach>
							</select>
							<span class="tip" id="yzshbtglxTit__2" style="display: none;">
                                        <div class="arrow" style="left:0px;">
                                            <div class="tip-content" style="width:93px;top: -16px;left: 15px;"><!-- 0115bianxw修改 top: -28px;left: -130px; -->
                                                		请选择原因<!--请选择不通过原因-->
                                            </div>
                                        </div>
                            </span>
							<input type="text" style="float: right;width: 130px;" id="yzshbtgyy__2" maxlength="256"> 
							<label style="    float: right;    right: 176px;">备注：</label>
						</td>
					</tr>
					<tr>
						<td>
							<div style="border: #d4d4d4 1px solid;font-size: 14px; padding-top: 10px;padding-left: 10px;padding-bottom: 10px;" >
							<label style="color:#C02624">年龄禁忌</label>
							<br>
							描述：老年患者身体机能降低，故用药期间注意观察。
							</div>
							
							
							<ul id="batchRule__2" style="visibility: hidden;height: 0px;" >
							<c:forEach items="${batchRuleList}" var="batchRule" >
							<c:if test="${empty batchRule.ru_key}">
							<li name="${batchRule.pinc_code}" style="height: 0px;" >${batchRule.pinc_name}</li>
							</c:if>
							</c:forEach>
							</ul>			
											
							<ul id="ruleKey__2" style="visibility: hidden;height: 0px;" >
							<c:forEach items="${ruleList}" var="rule" >
							<c:if test="${!empty rule.ru_key}">
							<li name="${rule.pinc_code}" style="height: 0px;" >${rule.ru_key}</li>
							</c:if>
							</c:forEach>
							</ul>
							
							
							
						</td>
					</tr>
				</table>
				</div>
			</div>
			

			
			
			</td>
			</tr>
			</table>
	



			
		</td>
	</tr>
	</table>
	<!-- 复制	 __1		变成		__2		 -->
	</div>
	
	</div>   

</div>
</div>

</body>

</html>