<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>
<style type="text/css">
.medicine-tab {
	margin-top: 10px;
}

.tabs-title {
	height: 28px;
}

.content {
	width: 99%;
}

.search_txt {
	border-style: solid;
	border-width: 0;
	background: transparent;
	border: 0;
	outline: none;
	width: 120px;
}

.oe_searchview_search1 {
	font-size: 1px;
	letter-spacing: 5px;
	color: transparent;
	text-shadow: none;
	font-weight: normal;
	-moz-box-shadow: none;
	-webkit-box-shadow: none;
	box-shadow: none;
	-moz-border-radius: 0;
	-webkit-border-radius: 0;
	border-radius: 0;
	border: none;
	background: transparent;
}

.oe_searchview_search1:before {
	font: 21px "mnmliconsRegular";
	content: ".";
	color: white;
	background:
		url('${pageContext.request.contextPath}/assets/search/images/searchblue.png')
		no-repeat center center;
}

.oe_searchview .oe_searchview_clear1 {
	cursor: pointer;
	top: 10px;
	width: 15px;
	height: 20px;
	margin-left: 250px;
	/*background: url('${pageContext.request.contextPath}/assets/search/images/search_reset.gif') center center no-repeat;*/
}

.oe_searchview .oe_searchview_facets {
	min-height: 22px;
	margin: 0 0px 0 -10x;
}

.oe_searchview_input {
	padding: 0 0 0 1px;
	margin-left: -6px;
}

.oe_searchview .oe_searchview_facets .oe_searchview_input {
	padding: 0 0 0 1px;
}
</style>

<script type="text/javascript">
	var _gridWidth = 0;
	var _gridHeight = 0; 
	
	function isIE() {
	    if (!!window.ActiveXObject || "ActiveXObject" in window)  
	        return true;  
	    else  
	        return false;  
	}
	
	//页面自适应
	function resizePageSize(){
		_gridWidth = $(document).width()-200-12;/*  -189 是去掉左侧 菜单的宽度，   -12 是防止浏览器缩小页面 出现滚动条 恢复页面时  折行的问题 */
		_gridHeight = $(document).height()-32-30-130; /* -32 顶部主菜单高度，   -90 查询条件高度*/
		//alert($(document).height()+"--"+_gridHeight);
		$("#flexGrid__1").flexResize();
		//$("#flexGrid__2").flexResize();
		$(".categoryList").height($(document).height()-32-70);
		$("#yzInfo__1").height($(document).height()-32-60);
		$("#yzInfo__2").height($(document).height()-32-60);
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
		
		$("#yzMainDiv__1").css("display","block");
		$("#yzMainDiv__1 .oe_searchview222").removeClass("oe_searchview222").addClass("oe_searchview");
		$("#yzMainDiv__1 .oe_searchview_head222").removeClass("oe_searchview_head222").addClass("oe_searchview_head");
		$("#yzMainDiv__1 .divselect222").removeClass("divselect222").addClass("divselect");
		$("#yzMainDiv__1 .oe_search_txt222").removeClass("oe_search_txt222").addClass("oe_search_txt");
		$("#yzMainDiv__1 .oe_tag_dark222").removeClass("oe_tag_dark222").addClass("oe_tag_dark");
		//$("#yzMainDiv__1 .oe_search_txt").css("display","block");
		
		$("#yzMainDiv__2").css("display","none");
		$("#yzMainDiv__2 .oe_searchview").removeClass("oe_searchview").addClass("oe_searchview222");
		$("#yzMainDiv__2 .oe_searchview_head").removeClass("oe_searchview_head").addClass("oe_searchview_head222");
		$("#yzMainDiv__2 .divselect").removeClass("divselect").addClass("divselect222");
		$("#yzMainDiv__2 .oe_search_txt").removeClass("oe_search_txt").addClass("oe_search_txt222");
		$("#yzMainDiv__2 .oe_tag_dark").removeClass("oe_tag_dark").addClass("oe_tag_dark222");
		//$("#ydMain__2 .oe_search_txt222").css("display","none");
	}
	function showSearch2(){
		$(".tab-title-2").removeClass("select").addClass("select");
		$(".tab-title-1").removeClass("select");
		
		$("#yzMainDiv__2").css("display","block");
		$("#yzMainDiv__2 .oe_searchview222").removeClass("oe_searchview222").addClass("oe_searchview");
		$("#yzMainDiv__2 .oe_searchview_head222").removeClass("oe_searchview_head222").addClass("oe_searchview_head");
		$("#yzMainDiv__2 .divselect222").removeClass("divselect222").addClass("divselect");
		$("#yzMainDiv__2 .oe_search_txt222").removeClass("oe_search_txt222").addClass("oe_search_txt");
		$("#yzMainDiv__2 .oe_tag_dark222").removeClass("oe_tag_dark222").addClass("oe_tag_dark");
		//$("#yzMainDiv__2 .oe_search_txt").css("display","block");
		
		$("#yzMainDiv__1").css("display","none");
		$("#yzMainDiv__1 .oe_searchview").removeClass("oe_searchview").addClass("oe_searchview222");
		$("#yzMainDiv__1 .oe_searchview_head").removeClass("oe_searchview_head").addClass("oe_searchview_head222");
		$("#yzMainDiv__1 .divselect").removeClass("divselect").addClass("divselect222");
		$("#yzMainDiv__1 .oe_search_txt").removeClass("oe_search_txt").addClass("oe_search_txt222");
		$("#yzMainDiv__1 .oe_tag_dark").removeClass("oe_tag_dark").addClass("oe_tag_dark222");
		//$("#ydMain__1 .oe_search_txt222").css("display","none");
	}
	
	function showHidePat(obj){
		if($("#yzMainDiv__2").css("display")=="none"){
			if($(obj).attr("isShow")=="hide"){
				$(obj).attr("isShow","show");
				$("#yzInfo__1 [name='patient_"+$(obj).attr("inpatientNo")+"']").show();
			}else{
				$(obj).attr("isShow","hide");
				$("#yzInfo__1 [name='patient_"+$(obj).attr("inpatientNo")+"']").hide();
			}
		}else{
			if($(obj).attr("isShow")=="hide"){
				$(obj).attr("isShow","show");
				$("#yzInfo__2 [name='patient_"+$(obj).attr("inpatientNo")+"']").show();
			}else{
				$(obj).attr("isShow","hide");
				$("#yzInfo__2 [name='patient_"+$(obj).attr("inpatientNo")+"']").hide();
			}
		}
	}
	
	function backMain(){
		if($("#yzMainDiv__2").css("display")=="none"){
			//$("#yzInfo__1").hide().html("");
			//$("#yzMain__1").show();
		}else{
			//$("#yzInfo__2").hide().html("");
			//$("#yzMain__2").show();
		}
	}
	var checkIdS = "";
	var repeatCheck = "N";
	var newYzshzt = "" ;
	function checkOK(obj){
		newYzshzt = "1" ;
		$("#checkOKBt").css("background-color","#73ab65");
		$("#checkNOBt").css("background-color","#ebb800");
	}
	function checkNO(obj){
		newYzshzt = "2" ;
		$("#checkOKBt").css("background-color","#ebb800");
		$("#checkNOBt").css("background-color","#d45120");
	}
	var checkErr = "" ;
	function checkOne(pidsj,obj,checkType,freqCode,yzlx,drugname,inpatientNo){
		var yzshzt = $(obj).parent().attr("yzshzt");
		if(checkType=='checkOK'){//审核新的状态为通过
			
			checkErr = "" ;
			if("${SYN_YZ_DATA_MODE}"=="3"){
				if(checkErr==""){
					var f = 0 ;
					var freqCode = freqCode.toUpperCase();
					if($("#ruleKey [name="+freqCode+"]").length>0){//如果 关键字规则 没有
						$("#ruleKey [name="+freqCode+"]").each(function(){
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
			
			if(yzshzt==1){//原状态为通过，重新审核通过
				message({
					html: "<spring:message code='comm.mess20'/>",
					showCancel:true,
					confirm:function(){
						checkSubmit(pidsj,"Y","1",yzshzt,inpatientNo,obj);
					}
		    	});
			}else{
				checkSubmit(pidsj,"N","1",yzshzt,inpatientNo,obj);
			}
			$("#checkNOYY_"+pidsj).hide();
			$("#result_"+pidsj).hide();
			$("#checkNOReason_"+pidsj).hide();
		}else{//审核新的状态为不通过
			if($("#checkNOSele_"+pidsj).val() && $("#checkNOSele_"+pidsj).val()!=""){
			}else{
				$("#checkNOSele_"+pidsj+"_yy").show();
            	//$("#checkNOSele_"+pidsj).show();
				$("#checkNOYY_"+pidsj).show();
				$("#checkNOReason_"+pidsj).show();
				$("#result_"+pidsj).show();
				return ;
			}
			$("#checkNOYY_"+pidsj).hide();
			if(yzshzt==1){//原状态为通过，重新审核通过
				message({
					html: "<spring:message code='comm.mess20'/>",
					showCancel:true,
					confirm:function(){
						checkSubmit(pidsj,"N","2",yzshzt,inpatientNo,obj);
					}
		    	});
			}else{
				checkSubmit(pidsj,"N","2",yzshzt,inpatientNo,obj);
			}
		}
	}
	
	function checkSubmit(pidsj,repeatCheck,newYzshzt,yzshzt,inpatientNo,obj){
		$("#checkNOYY_"+pidsj).hide();
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
			dataType : 'json',
			cache : false,
			//showDialog: false,
			data : {"pidsjN":pidsj,"repeatCheck":repeatCheck,"newYzshzt":newYzshzt,yzshbtglx:$("#checkNOSele_"+pidsj).val(),"yzshbtgyy":$("#yzshbtgyy_"+pidsj).val(),"yzParea":"1","checkType":"yssf"},
			success : function(response) {
				if(response.code==0){
					$("#yshCheck_"+pidsj).remove();
					if(newYzshzt==1){
						$(obj).parent().attr("yzshzt","1");
						$("#checkOK_"+pidsj).removeClass("Ok").removeClass("OkPass").addClass("OkPass");
						$("#checkNO_"+pidsj).removeClass("Error").removeClass("ErrorPass").addClass("Error");
						$("#checkNOSele_"+pidsj).val("");
						$("#yzshbtgyy_"+pidsj).val("");
						
						$err = $("#checkNOReason_"+pidsj);
						$err.css("display","none");
					}else{
						$(obj).parent().attr("yzshzt","2");
						$("#checkOK_"+pidsj).removeClass("Ok").removeClass("OkPass").addClass("Ok");
						$("#checkNO_"+pidsj).removeClass("Error").removeClass("ErrorPass").addClass("ErrorPass");
						
						$err = $("#checkNOReason_"+pidsj);
						$err.css("display","none");
					}
					var row = 0 ;
					var row2 = 0 ;
					$("[name=patient_"+inpatientNo+"]").each(function(){
						row = row+1;
						$(this).find(".OkPass").each(function(){
							row2 ++ ;
						});
						$(this).find(".ErrorPass").each(function(){
							row2 ++ ;
						});
					});
					if(row==row2){
						$("[name=patient_"+inpatientNo+"]").each(function(){
							$(this).css("display","none");
						});
					}
					if($("#yzMainDiv__2").css("display")=="none"){
						inpatientNo__1 = inpatientNo;
						refushPArea__1(false);
					}else{
						inpatientNo__2 = inpatientNo;
						refushPArea__2(false);
					}
					if(inpatientNo!=""){
						if(isIE()){
							var controls=document.getElementsByName('patient_'+inpatientNo);
							for(var i=0;i<controls.length;i++)
							{
							    controls[i].scrollIntoView();
							    break;
							}
						}
							
					}
				}else{
					message({html: response.msg});
				}
			}
		});
	}
	function yshCheck(pidsj,obj,yzzdshzt,freqCode,yzlx,drugname,inpatientNo,yzzdshbtglx){
		if("${SYN_YZ_DATA_MODE}"=="3"){
			if(checkErr=="" && yzlx==0){
				var f = 0 ;
				var freqCode = freqCode.toUpperCase();
				if($("#ruleKey [name="+freqCode+"]").length>0){//如果 关键字规则 没有
					$("#ruleKey [name="+freqCode+"]").each(function(){
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
						if(yzzdshzt==1){
							$(obj).parent().attr("yzshzt","1");
							$("#checkOK_"+pidsj).removeClass("Ok").removeClass("OkPass").addClass("OkPass");
							$("#checkNO_"+pidsj).removeClass("Error").removeClass("ErrorPass").addClass("Error");
							$("#checkNOSele_"+pidsj).val("");
							$("#yzshbtgyy_"+pidsj).val("");
							
							$err = $("#checkNOReason_"+pidsj);
							$err.css("display","none");
						}else{
							$(obj).parent().attr("yzshzt","2");
							$("#checkOK_"+pidsj).removeClass("Ok").removeClass("OkPass").addClass("Ok");
							$("#checkNO_"+pidsj).removeClass("Error").removeClass("ErrorPass").addClass("ErrorPass");
							
							$("#checkNOSele_"+pidsj).val(yzzdshbtglx);
							
							$err = $("#checkNOReason_"+pidsj);
							$err.css("display","none");
						}
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
	}
	
	</script>



<script type="text/javascript">
	var param1__1 = [];
	var thisPArea__1 = '';
	var inpatientNo__1 = "" ;

	function qryListByArea__1(refushPAreaId){
		if(refushPAreaId && refushPAreaId!=undefined){
			thisPArea__1 = refushPAreaId;
			$("#ulDept__1 li").each(function(){
				$(this).removeClass("completed");
			});
			$("#area__1_"+thisPArea__1).parent().parent().addClass("completed");
			$("#bar__1").css("width",parseFloat(($("#area__1_"+thisPArea__1).attr("present")+"E+2"))+"%");
			$("#prenums__1").html($("#area__1_"+thisPArea__1).html());
		}
		if(thisPArea__1!=-1){
			qry__1(true);
			backMain();
		}
	}
	function refushPArea__1(isRefushYZ){
		var parms = {} ;
		if(thisPArea__1!=null && thisPArea__1!=""){
			//parms['wardCode'] = thisPArea__1;
		}
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
				var list = data || [];
				var rownum = 0 ;
				var checkOkNum = 0 ;
				$("#ulDept__1").html("");
				if(list && list.length>0){
					var indexNum = 0 ;
					for(var i in list){
						var row = list[i];
						if(row.countNum>0){
							indexNum ++ ;
							rownum = rownum + row.countNum;
							checkOkNum = checkOkNum + row.checkOkNum;
							if(indexNum==1 && thisPArea__1==""){
								thisPArea__1 = row.wardCode;
							}
						}
					}
					indexNum = 0 ;
					$("#ulDept__1 li").each(function(){
						$(this).removeClass("completed");
					});
					for(var i in list){
						var row = list[i];
						$("#area__1_"+row.wardCode).html(row.checkOkNum+"/"+row.countNum).attr("present",row.checkOkNum/row.countNum);
						if(row.countNum>0){
							indexNum ++ ;
							var compHtml = "" ;
							if(thisPArea__1!=null && thisPArea__1!=''){
								if(thisPArea__1==row.wardCode){
									compHtml = "class='completed'" ;
									$("#bar__1").css("width",parseFloat(((row.checkOkNum/row.countNum)+"E+2"))+"%");
									$("#prenums__1").html(row.checkOkNum+"/"+row.countNum);
								}
							}else if(indexNum==1){
								compHtml = "class='completed'" ;
								thisPArea__1 = row.wardCode;
								$("#bar__1").css("width",parseFloat(((row.checkOkNum/row.countNum)+"E+2"))+"%");
								$("#prenums__1").html(row.checkOkNum+"/"+row.countNum);
							}
							var rowHtml = "<li id='areaLi__1_"+row.wardCode+"' "+ compHtml +" onclick='qryListByArea__1(\""+row.wardCode+"\")' ><a href='javascript:void(0)' class='category'>"+row.wardName+"<div id='area__1_"+row.wardCode+"' present='"+(row.checkOkNum/row.countNum)+"' class='category-progressor'>"+row.checkOkNum+"/"+row.countNum+"</div></a></li>" ;
							$("#ulDept__1").append(rowHtml);							
							//$("#area_"+row.wardCode).parent().parent().show();
						}
					}
					if(thisPArea__1!=null && thisPArea__1!=''){
						if(inpatientNo__1!=""){
							//if ((navigator.userAgent.indexOf('MSIE') >= 0) 
							//	    && (navigator.userAgent.indexOf('Opera') < 0)){
								if(isIE()){
								var controls=document.getElementsByName('patient_'+inpatientNo__1);
								for(var i=0;i<controls.length;i++)
								{
								    controls[i].scrollIntoView();
								    break;
								}
								}
							//	}
						}
						if(isRefushYZ){
							qry__1(true);
						}
					}
					$("#area_all__1").html(checkOkNum+"/"+rownum);
				}else{
					
				}
			}
		});
	}
	
	$(function() {
		refushPArea__1(true);
		
		var _columnWidth = (parseInt(_gridWidth)-55) / 10;
		$("#flexGrid__1").flexigrid({
			width : _gridWidth,
			height : _gridHeight,
			url: "${pageContext.request.contextPath}/pati/patiList",
			dataType : 'json',
			colModel : [ 
						{display: 'inhospNo', name : 'inhospno', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: '<spring:message code="pivas.patient.code"/>', name : 'inhospno', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.name"/>', name : 'patname', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.sex"/>', name : 'sexName', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.state"/>', name : 'state', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.caseid"/>', name : 'caseID', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.birthday"/>', name : 'birthDay', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.age"/>', name : 'ageDetail', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.avdp"/>', name : 'avdp', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.bedNo"/>', name : 'bedno', width : _columnWidth, sortable : true, align: 'center'}
					],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			rowbinddata : true,
			rowhandler : rowClick__1
		});
		
		function rowClick__1(r) {
			$(r).click(
			function() {
				//获取该行的所有列数据
				var columnsArray = $(r).attr('ch').split("_FG$SP_");
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/doctorAdvice/yzListByPati',
					dataType : 'html',
					cache : false,
					showDialog: false,
 					data : {"inpatientNo":columnsArray[0]},
					success : function(data) {
						$("#yzInfo__1").html(data).show();
						$("#yzMain__1").hide();
						$("#yzInfo__1 .panelHeadBtTD").bind("click",function(){
							$err = $("#checkNOReason_"+$(this).attr("pidsj"));
							if($err.css("display")=="block"){
								$err.css("display","none");
							}else{
								$err.css("display","block");
							}
						});
						$("#yzInfo__1 .panelHeadBtTD").each(function(){$(this).parent().css("background-color","transparent")});
						$("[name='yzshbtglx']").bind("change",function(){
							 //name="yzshbtglx" id="checkNOSele_${yzMain.pidsj}
							 if($(this).val() && $(this).val()!=""){
								$("#"+$(this).attr("id")+"_yy").css("display","block"); 
							 }else{
								 $("#"+$(this).attr("id")+"_yy").css("display","none"); 
							 }
						});
					}
				});
			});
		}
		
		//qry(false);
		
		$("#aSearch__1").bind("click",function(){
			qry__1(false);
		});
	});
	function qry__1(clear){
		if(clear){
			clearclosedinputall();
		}
		if(thisPArea__1!=undefined && thisPArea__1!="-1"){
			
			var params = [];
			if(param1__1){
				params = param1__1.concat();
			}
			params.push({"name":"wardCode","value":thisPArea__1});
			params.push({"name":"ydztLowN","value":5});
			params.push({"name":"yzztLowN","value":1});
			params.push({"name":"yzlx","value":0});
			params.push({"name":"yzResource","value":1});
			//params.push({"name":"yzlx","value":1});
			//params.push({"name":"yzResourceUpEQN","value":2});
			
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/doctorAdvice/toYzList',
				dataType : 'html',
				cache : false,
				showDialog: false,
					data : params,
				success : function(data) {
					$("#yzInfo__1").html(data).show();
					//$("#yzMain__1").hide();
					debugger;
					$("#yzInfo__1 .labSex").each(function(){
						$(this).html(getDicValue("sex",$(this).html()));
					});
					$("#yzInfo__1 .labAgeUnit").each(function(){
						$(this).html(getDicValue("ageUnit",$(this).html()));
					}); 
					
					$(".tdPatient").each(function(){
						var inpatientNo = $(this).attr("inpatientNo");
						var row = 0 ;
						var row2 = 0 ;
						//$(".panelMain [name=patient_"+inpatientNo+"]").each(function(){
						$("[name=patient_"+inpatientNo+"]").each(function(){
							row = row+1;
							$(this).find(".OkPass").each(function(){
								row2 ++ ;
							});
							$(this).find(".ErrorPass").each(function(){
								row2 ++ ;
							});
						});
						if(row==row2){
							$("[name=patient_"+inpatientNo+"]").each(function(){
								$(this).css("display","none");
							});
							$(this).attr("isShow","hide");
						}else{
							$(this).attr("isShow","show");
						}
					});
					
					$("#yzInfo__1 .panelHeadBtTD").bind("click",function(){
						$err = $("#checkNOReason_"+$(this).attr("pidsj"));
						if($err.css("display")=="block"){
							$err.css("display","none");
						}else{
							$err.css("display","block");
						}
					});
					$("#yzInfo__1 .panelHeadBtTD").each(function(){$(this).parent().css("background-color","transparent")});
					$("[name='yzshbtglx']").bind("change",function(){
						 //name="yzshbtglx" id="checkNOSele_${yzMain.pidsj}
						 if($(this).val() && $(this).val()!=""){
							$("#"+$(this).attr("id")+"_yy").css("display","block"); 
						 }else{
							 $("#"+$(this).attr("id")+"_yy").css("display","none"); 
						 }
					});
				}
			});
		}
	}
	function qryList__1(param){
		if(param instanceof Array){
			param1__1 = [];
			param1__1 = param ;
		}else{
			param1__1 = [];
		}
		
	}
	</script>



<script type="text/javascript">
	var param1__2 = [];
	var thisPArea__2 = '';
	var inpatientNo__2 = "" ;

	function qryListByArea__2(refushPAreaId){
		if(refushPAreaId && refushPAreaId!=undefined){
			thisPArea__2 = refushPAreaId;
			$("#ulDept__2 li").each(function(){
				$(this).removeClass("completed");
			});
			$("#area__2_"+thisPArea__2).parent().parent().addClass("completed");
			$("#bar__2").css("width",parseFloat(($("#area__2_"+thisPArea__2).attr("present")+"E+2"))+"%");
			$("#prenums__2").html($("#area__2_"+thisPArea__2).html());
		}
		if(thisPArea__2!=-1){
			qry__2(true);
			backMain();
		}
	}
	function refushPArea__2(isRefushYZ){
		var parms = {} ;
		if(thisPArea__2!=null && thisPArea__2!=""){
		}
		parms['ydztLowN'] = 5;
		parms['yzztLowN'] = 1;
		parms['areaEnabled'] = 1;
		//parms['yzlx'] = 0;
		//parms['yzResource'] = 1;
		parms['yzlx'] = 1;
		parms['yzResourceUpEQN'] = 2;
		//parms['filterTimeLow12']=1;
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/groupNumByInpArea',
			dataType : 'json',
			cache : false,
			showDialog: false,
			data : parms,
			success : function(data) {
				var list = data || [];
				var rownum = 0 ;
				var checkOkNum = 0 ;
				$("#ulDept__2").html("");
				if(list && list.length>0){
					var indexNum = 0 ;
					for(var i in list){
						var row = list[i];
						if(row.countNum>0){
							indexNum ++ ;
							rownum = rownum + row.countNum;
							checkOkNum = checkOkNum + row.checkOkNum;
							if(indexNum==1 && thisPArea__2==""){
								thisPArea__2 = row.wardCode;
							}
						}
					}
					indexNum = 0 ;
					$("#ulDept__2 li").each(function(){
						$(this).removeClass("completed");
					});
					for(var i in list){
						var row = list[i];
						$("#area__2_"+row.wardCode).html(row.checkOkNum+"/"+row.countNum).attr("present",row.checkOkNum/row.countNum);
						if(row.countNum>0){
							indexNum ++ ;
							var compHtml = "" ;
							if(thisPArea__2!=null && thisPArea__2!=''){
								if(thisPArea__2==row.wardCode){
									compHtml = "class='completed'" ;
									$("#bar__2").css("width",parseFloat(((row.checkOkNum/row.countNum)+"E+2"))+"%");
									$("#prenums__2").html(row.checkOkNum+"/"+row.countNum);
								}
							}else if(indexNum==1){
								compHtml = "class='completed'" ;
								thisPArea__2 = row.wardCode;
								$("#bar__2").css("width",parseFloat(((row.checkOkNum/row.countNum)+"E+2"))+"%");
								$("#prenums__2").html(row.checkOkNum+"/"+row.countNum);
							}
							var rowHtml = "<li id='areaLi__2_"+row.wardCode+"' "+ compHtml +" onclick='qryListByArea__2(\""+row.wardCode+"\")' ><a href='javascript:void(0)' class='category'>"+row.wardName+"<div id='area__2_"+row.wardCode+"' present='"+(row.checkOkNum/row.countNum)+"' class='category-progressor'>"+row.checkOkNum+"/"+row.countNum+"</div></a></li>" ;
							$("#ulDept__2").append(rowHtml);							
							//$("#area_"+row.wardCode).parent().parent().show();
						}
					}
					if(thisPArea__2!=null && thisPArea__2!=''){
						if(inpatientNo__2!=""){
							//if ((navigator.userAgent.indexOf('MSIE') >= 0) 
							//	    && (navigator.userAgent.indexOf('Opera') < 0)){
								if(isIE()){
								var controls=document.getElementsByName('patient_'+inpatientNo__2);
								for(var i=0;i<controls.length;i++)
								{
								    controls[i].scrollIntoView();
								    break;
								}
								}
							//	}
						}
						if(isRefushYZ){
							qry__2(true);
						}
					}
					$("#area_all__2").html(checkOkNum+"/"+rownum);
				}else{
					
				}
			}
		});
	}
	
	$(function() {
		refushPArea__2(true);
		
		var _columnWidth = (parseInt(_gridWidth)-55) / 10;
		$("#flexGrid__2").flexigrid({
			width : _gridWidth,
			height : _gridHeight,
			url: "${pageContext.request.contextPath}/pati/patiList",
			dataType : 'json',
			colModel : [ 
						{display: 'inhospNo', name : 'inhospno', width : 0, sortable : false, align: 'center',hide:'true'},
						{display: '<spring:message code="pivas.patient.code"/>', name : 'inhospno', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.name"/>', name : 'patname', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.sex"/>', name : 'sexName', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.state"/>', name : 'state', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.caseid"/>', name : 'caseID', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.birthday"/>', name : 'birthDay', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.age"/>', name : 'ageDetail', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.avdp"/>', name : 'avdp', width : _columnWidth, sortable : true, align: 'center'},
						{display: '<spring:message code="pivas.patient.bedNo"/>', name : 'bedno', width : _columnWidth, sortable : true, align: 'center'}
					],
			resizable : false, //resizable table是否可伸缩
			usepager : true,
			useRp : true,
			usepager : true, //是否分页
			autoload : false, //自动加载，即第一次发起ajax请求
			hideOnSubmit : true, //是否在回调时显示遮盖
			rowbinddata : true,
			rowhandler : rowClick__2
		});
		
		function rowClick__2(r) {
			$(r).click(
			function() {
				//获取该行的所有列数据
				var columnsArray = $(r).attr('ch').split("_FG$SP_");
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/doctorAdvice/yzListByPati',
					dataType : 'html',
					cache : false,
					showDialog: false,
 					data : {"inpatientNo":columnsArray[0]},
					success : function(data) {
						$("#yzInfo__2").html(data).show();
						$("#yzMain__2").hide();
						$("#yzInfo__2 .panelHeadBtTD").bind("click",function(){
							$err = $("#checkNOReason_"+$(this).attr("pidsj"));
							if($err.css("display")=="block"){
								$err.css("display","none");
							}else{
								$err.css("display","block");
							}
						});
						$("#yzInfo__2 .panelHeadBtTD").each(function(){$(this).parent().css("background-color","transparent")});
						$("[name='yzshbtglx']").bind("change",function(){
							 //name="yzshbtglx" id="checkNOSele_${yzMain.pidsj}
							 if($(this).val() && $(this).val()!=""){
								$("#"+$(this).attr("id")+"_yy").css("display","block"); 
							 }else{
								 $("#"+$(this).attr("id")+"_yy").css("display","none"); 
							 }
						});
					}
				});
			});
		}
		
		//qry(false);
		
		$("#aSearch__2").bind("click",function(){
			qry__2(false);
		});
	});
	function qry__2(clear){
		if(clear){
			clearclosedinputall();
		}
		if(thisPArea__2!=undefined && thisPArea__2!="-1"){
			
			var params = [];
			if(param1__2){
				params = param1__2.concat();
			}
			params.push({"name":"wardCode","value":thisPArea__2});
			params.push({"name":"ydztLowN","value":5});
			params.push({"name":"yzztLowN","value":1});
			//params.push({"name":"yzlx","value":0});
			//params.push({"name":"yzResource","value":1});
			params.push({"name":"yzlx","value":1});
			params.push({"name":"yzResourceUpEQN","value":2});
			
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/doctorAdvice/toYzList',
				dataType : 'html',
				cache : false,
				showDialog: false,
					data : params,
				success : function(data) {
					$("#yzInfo__2").html(data).show();
					//$("#yzMain__2").hide();
					
					$("#yzInfo__2 .labSex").each(function(){
						$(this).html(getDicValue("sex",$(this).html()));
					});
					$("#yzInfo__2 .labAgeUnit").each(function(){
						$(this).html(getDicValue("ageUnit",$(this).html()));
					}); 
					
					$(".tdPatient").each(function(){
						var inpatientNo = $(this).attr("inpatientNo");
						var row = 0 ;
						var row2 = 0 ;
						//$(".panelMain [name=patient_"+inpatientNo+"]").each(function(){
						$("[name=patient_"+inpatientNo+"]").each(function(){
							row = row+1;
							$(this).find(".OkPass").each(function(){
								row2 ++ ;
							});
							$(this).find(".ErrorPass").each(function(){
								row2 ++ ;
							});
						});
						if(row==row2){
							$("[name=patient_"+inpatientNo+"]").each(function(){
								$(this).css("display","none");
							});
							$(this).attr("isShow","hide");
						}else{
							$(this).attr("isShow","show");
						}
					});
					
					$("#yzInfo__2 .panelHeadBtTD").bind("click",function(){
						$err = $("#checkNOReason_"+$(this).attr("pidsj"));
						if($err.css("display")=="block"){
							$err.css("display","none");
						}else{
							$err.css("display","block");
						}
					});
					$("#yzInfo__2 .panelHeadBtTD").each(function(){$(this).parent().css("background-color","transparent")});
					$("[name='yzshbtglx']").bind("change",function(){
						 //name="yzshbtglx" id="checkNOSele_${yzMain.pidsj}
						 if($(this).val() && $(this).val()!=""){
							$("#"+$(this).attr("id")+"_yy").css("display","block"); 
						 }else{
							 $("#"+$(this).attr("id")+"_yy").css("display","none"); 
						 }
					});

				}
			});
		}
	}
	function qryList__2(param){
		if(param instanceof Array){
			param1__2 = [];
			param1__2 = param ;
		}else{
			param1__2 = [];
		}
		
		//qry__2(false);
	}
	</script>

</head>
<body>
	<div class="medicine-tab" style="max-width: 1100px;">
		<div class="tabs-title">
			<ul style="float: left;">
				<li><a
					style="width: 90px; display: block; height: 26px; line-height: 26px; width: 90px; text-align: center; cursor: pointer;"
					class="tab-title-1 select">长嘱</a></li>
				<li><a
					style="width: 90px; display: block; height: 26px; line-height: 26px; width: 90px; text-align: center; cursor: pointer;"
					class="tab-title-2 ">临嘱</a></li>
			</ul>
		</div>
		<div class="tabs-content"
			style="margin-top: -2px; border-top: 1.5px solid;">
			<div style="height: 0px;">
				<ul id="batchRule" style="visibility: hidden; height: 0px;">
					<c:forEach items="${batchRuleList}" var="batchRule">
						<c:if test="${empty batchRule.ru_key}">
							<li name="${batchRule.pinc_code}" style="height: 0px;">${batchRule.pinc_name}</li>
						</c:if>
					</c:forEach>
				</ul>

				<ul id="ruleKey" style="visibility: hidden; height: 0px;">
					<c:forEach items="${ruleList}" var="rule">
						<c:if test="${!empty rule.ru_key}">
							<li name="${rule.pinc_code}" style="height: 0px;">${rule.ru_key}</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<div class="tab-content-1" style="display: block;">
				<div id="yzMainDiv__1" class="main-div">
					<div class="content-container" style="width: 100%">
						<div class="content">
							<div class="category-list" style="">
								<ul>
									<li><a href="#" class="category"> <spring:message
												code='comm.mess21' />
											<div id="area_all__1" class="category-progressor">0/0</div>
									</a></li>
								</ul>
								<div class="categoryList"
									style="height: 300px; overflow-x: hidden; overflow-y: auto;">
									<ul id="ulDept__1">
										
									</ul>
								</div>

							</div>
							<div class="list-info" style="margin-top: 15px;">
								<div class="progressor-div">
									<span class="text"><spring:message code='comm.mess22' /></span>
									<div class="progressor-bar">
										<div class="progressor-bar-outer">
											<div id="bar__1" class="progressor-bar-inner"
												style="width: 0px;"></div>
										</div>
									</div>
									<span id="prenums__1" class="pre-nums">0/0</span>
								</div>

								<div id="yzMain__1" style="display: none;">
									<div class="oe_searchview">
										<div class="oe_searchview_facets" style="height: 25px;">
											<button class="oe_searchview_search1" title="重新搜索"
												type="button"
												style="width: 3px; margin-left: -20px; margin-top: -3px;">搜索</button>
											<div class="oe_searchview_input oe_searchview_head"></div>
											<div class="oe_searchview_input"
												style="padding: 0 0 0 1px; margin-left: -6px;">
												<input type="text" class="search_txt oe_search_txt"
													style="max-height: 25px; width: 250px; z-index: 1000;" />
											</div>
										</div>
										<div class="oe-autocomplete"></div>
										<div style="border: 1px solid #D2D2D2; display: none;"
											width="50px" heiht="50px" class="divselect" style="">
											<cite>请选择...</cite>
											<ul class="ulQry" style="-webkit-border-radius: 20;"
												funname="qryList__1">
												<li show="<spring:message code='pivas.yz2.patname'/>"
													name="patNames"><spring:message
														code='pivas_yz1.search' /> <spring:message
														code='pivas.yz2.patname' />：<span class="searchVal"></span></li>
												<li show="<spring:message code='scans.drugName'/>"
													name="drugnames"><spring:message
														code='pivas_yz1.search' /> <spring:message
														code='scans.drugName' />：<span class="searchVal"></span></li>
												<li show="<spring:message code='pivas.yz1.doctor2'/>"
													name="doctors"><spring:message code='pivas_yz1.search' />
													<spring:message code='pivas.yz1.doctor2' />：<span
													class="searchVal"></span></li>
											</ul>
										</div>
									</div>
									<div
										style="position: relative; left: -20px; top: 7px; height: 3px; z-index: 99;">
										<!-- <img alt="" src="${pageContext.request.contextPath}/assets/search/images/search_reset.gif" height="10px;" width="10px;" style="margin-top: 10px;" onclick="clearclosedinputall();"> -->
									</div>
									<div id="qryView-div__1">
										<div class="search-div">
											<div class="oe_view_manager_view_search"></div>
											<a class="button" style="visibility: hidden;"></a>
											<ul id="ulYZCheckMany__1" class="pre-more" tabindex='-1'
												style="display: none;">
												<li class="liBtH"><a class="button2"><spring:message
															code='comm.mess17' /></a></li>
											</ul>
										</div>
									</div>

									<div class="tbl" style="margin-top: 10px;">
										<table id="flexGrid__1" style="display: block;"></table>
									</div>
								</div>

								<div id="yzInfo__1" style="overflow-y: auto;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="yzMainDiv__2" class="tab-content-2" style="display: none;">

				<div class="main-div">
					<div class="content-container" style="width: 100%">
						<div class="content">

							<div class="category-list" style="">
								<ul>
									<li><a href="#" class="category"> <spring:message
												code='comm.mess21' />
											<div id="area_all__2" class="category-progressor">0/0</div>
									</a></li>
								</ul>
								<div class="categoryList"
									style="height: 300px; overflow-x: hidden; overflow-y: auto;">
									<ul id="ulDept__2">
										
									</ul>
								</div>

							</div>
							<div class="list-info" style="margin-top: 15px;">
								<div class="progressor-div">
									<span class="text"><spring:message code='comm.mess22' /></span>
									<div class="progressor-bar">
										<div class="progressor-bar-outer">
											<div id="bar__2" class="progressor-bar-inner"
												style="width: 0px;"></div>
										</div>
									</div>
									<span id="prenums__2" class="pre-nums">0/0</span>
								</div>


								<div id="yzMain__2" style="display: none;">


									<div class="oe_searchview">
										<div class="oe_searchview_facets" style="height: 25px;">
											<button class="oe_searchview_search1" title="重新搜索"
												type="button"
												style="width: 3px; margin-left: -20px; margin-top: -3px;">搜索</button>
											<div class="oe_searchview_input oe_searchview_head"></div>
											<div class="oe_searchview_input"
												style="padding: 0 0 0 1px; margin-left: -6px;">
												<input type="text" class="search_txt oe_search_txt"
													style="max-height: 25px; width: 250px; z-index: 1000;" />
											</div>
										</div>
										<div class="oe-autocomplete"></div>
										<div style="border: 1px solid #D2D2D2; display: none;"
											width="50px" heiht="50px" class="divselect" style="">
											<cite>请选择...</cite>
											<ul class="ulQry" style="-webkit-border-radius: 20;"
												funname="qryList__2">
												<li show="<spring:message code='pivas.yz2.patname'/>"
													name="patNames"><spring:message
														code='pivas_yz1.search' /> <spring:message
														code='pivas.yz2.patname' />：<span class="searchVal"></span></li>
												<li show="<spring:message code='scans.drugName'/>"
													name="drugnames"><spring:message
														code='pivas_yz1.search' /> <spring:message
														code='scans.drugName' />：<span class="searchVal"></span></li>
												<li show="<spring:message code='pivas.yz1.doctor2'/>"
													name="doctors"><spring:message code='pivas_yz1.search' />
													<spring:message code='pivas.yz1.doctor2' />：<span
													class="searchVal"></span></li>
											</ul>
										</div>
									</div>
									<div
										style="position: relative; left: -20px; top: 7px; height: 3px; z-index: 99;">
										<!-- <img alt="" src="${pageContext.request.contextPath}/assets/search/images/search_reset.gif" height="10px;" width="10px;" style="margin-top: 10px;" onclick="clearclosedinputall();"> -->
									</div>
									<div id="qryView-div__2">
										<div class="search-div">
											<div class="oe_view_manager_view_search"></div>
											<a class="button" style="visibility: hidden;"></a>
											<ul id="ulYZCheckMany__2" class="pre-more" tabindex='-1'
												style="display: none;">
												<li class="liBtH"><a class="button2"><spring:message
															code='comm.mess17' /></a></li>
											</ul>
										</div>
									</div>

									<div class="tbl" style="margin-top: 10px;">
										<table id="flexGrid__2" style="display: block;"></table>
									</div>
								</div>

								<div id="yzInfo__2" style="overflow-y: auto;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>