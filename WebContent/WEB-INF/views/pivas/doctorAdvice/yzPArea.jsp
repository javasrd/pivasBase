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
<link href="${pageContext.request.contextPath}/assets/multDropList/multDropList.css" rel="stylesheet" type="text/css"/>
<script src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<style type="text/css">
#searchModel table td{
vertical-align: top;
}

</style>
</head>
<body>
<div id="tabs-content" style="margin-top: 15px;min-width:1000px;">

<div class="content-container" style="width:100%">
	<div id="searchModel">
	<table  style="width:100%;height:45px;" >
	<tr>
	<td width="170">
	用药日期：
	<select id="sfrqSelect" class="select_new" style="height:26px;">
	<option value=""  selected="selected" >--请选择--</option>
	<option value="today">今日</option>
	<option value="tommorrow">明日</option>
	</select>
	</td>
	
	<td width="130">
	<select id="stateSelect" class="select_new" style="height:26px;">
	<option value="todayNew">新医嘱</option>
	<option value="unCheck"  selected="selected" >未审核</option>
	<option value="checkOK">审方通过</option>
	<option value="checkNO">审方不通过</option>
	<option value="checkNOHasYD">不通过有药单</option>
	<option value="mismatchYD">药单不匹配</option>
	</select>
	</td>
	<td width="170">
	医嘱类型： 
	<select id="yzlxSelect" class="select_new" style="height:26px;">
	<option value="" selected="selected">全部</option>
	<option value="0">长嘱</option>
	<option value="1">临嘱</option>
	<option value="2">单药医嘱</option>
	</select>
	</td>
	<%-- <td width="200">
	<span style="position: relative;top: 4px;">药品分类：</span>
	<select id="ypSelect"  name="ypSelect[]" size="5" style="height:26px;width:80px;" multiple="multiple">
		<c:forEach items="${medicList}" var="medicList">
		<option value="${medicList.categoryId}">${medicList.categoryName}</option>
		</c:forEach> 
	</select>
	</td> --%>
	<td width="180">
	<span>审方分类：</span>
	<select id="shSelect" class="select_new" style="height:26px;">
		<option value="" selected="selected">请选择</option>
		<option value="1">TPN审方</option>
		<option value="2">化疗审方</option>
		<option value="3">其他审方</option>
	</select>
	</td>
	<td width="200">
	<span>医嘱状态：</span>
	<select id="yzState" class="select_new" style="height:26px;">
		<option value="" selected="selected" >请选择</option>
		<option value="1">系统判断通过</option>
		<option value="2">系统判断不通过</option>
	</select>
	</td>
	<td>
	<div class="oe_searchview" style="margin-top:-1px; width: 260px;z-index: 999; ">
			<div class="oe_searchview_facets">
				<div class="oe_searchview_input oe_searchview_head"></div>
				<div class="oe_searchview_input" id="inputsearch__1">
					<input id="txt__1" type="text" class="oe_search_txt" style="border: none; max-height:23px; width: 200px;" placeholder="搜索" />
				</div>
			</div>
			<img alt="" style="padding:0 5px;top:20px;position: absolute;" src="${pageContext.request.contextPath}/assets/search/images/searchblue.png">
			<div class="oe_searchview_clear" onclick="clearclosedinputall();" style="left: 1083px;position: absolute;top: 17px;"></div>
			<div class="oe-autocomplete"></div>
			<div style="border: 1px solid #D2D2D2; display: none;" width="50px" heiht="50px" class="divselect" >
			<cite>请选择...</cite>
			<ul class="ulQry" style="-webkit-border-radius: 20;" funname="condiQuery">
			<li show="<spring:message code='pivas.yz2.bedno'/>" name="bednoS"><spring:message code='pivas_yz1.search' /><spring:message code='pivas.yz2.bedno' />：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz2.patname'/>" name="patnameS"><spring:message code='pivas_yz1.search' /><spring:message code='pivas.yz2.patname' />：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.parentNo'/>" name="parentNoS"><spring:message code='pivas_yz1.search' /><spring:message code='pivas.yz1.parentNo' />：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS"><spring:message code='pivas_yz1.search' /><spring:message code='pivas.yz1.freqCode' />：<span class="searchVal"></span></li>
			<li show="<spring:message code='pivas.yz1.drugname'/>" name="drugnameQry" ><spring:message code='pivas_yz1.search'/><spring:message code='pivas.yz1.drugname'/>：<span class="searchVal"></span></li>
			</ul>
			</div>
			</div> <!-- 搜索条件--结束 -->
	</td>
	</tr>
	</table>
	</div>

	<div id="yzInfo" style="overflow-y:auto;width:98%;">
	</div>
	<div style="height: 0px;display:none;" >
		<ul id="batchRule" style="height: 0px;">
			<c:forEach items="${batchRuleList}" var="batchRule">
				<c:if test="${empty batchRule.ru_key}">
					<li name="${batchRule.pinc_code}" style="height: 0px;">${batchRule.pinc_name}</li>
				</c:if>
			</c:forEach>
		</ul>

		<ul id="ruleKey" style="height: 0px;">
			<c:forEach items="${ruleList}" var="rule">
				<c:if test="${!empty rule.ru_key}">
					<li name="${rule.pinc_code}" style="height: 0px;">${rule.ru_key}</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
</div>
</div>
</body>
<script type="text/javascript">
var ypStr = "";
var paramTemp;
$(function() {
	
	$("#ypSelect").multiSelect({ "selectAll": false,"noneSelected": "选择分类","oneOrMoreSelected":"*" },function(){
		ypStr = $("#ypSelect").selectedValuesString();
		queryYZ();
	});
	
	$("#shSelect").change(function(){
		queryYZ();
	});
	
	$("#yzlxSelect").change(function(){
		queryYZ();
	});
	
	$("#stateSelect").change(function(){
		queryYZ();
	});
	
	$("#sfrqSelect").change(function(){
		queryYZ();
	});
	
	$("#yzState").change(function(){
		queryYZ();
	});
	
	queryYZ();
});

function condiQuery(param){
	paramTemp = param;
	queryYZ();
}

function queryYZ(){
	
	var inpatientString = function(){
		return window.parent.getInpatientInfo();
	}
	
	var params = [
		{name:"inpatientString",value:inpatientString},
		{name:"ydztLowN",value:5},
		{name:"yzztLowN",value:1},
		{name:"yzlx",value:$("#yzlxSelect").val()},
		{name:"state",value:$("#stateSelect").val()},
		{name:"sfrqSelect",value:$("#sfrqSelect").val()},
		{name:"ydflStr",value:ypStr},
		{name:"yzState",value:$("#yzState").val()},
		{name:"shState",value:$("#shSelect").val()}
    ];
	
	var yzlx = $("#yzlxSelect").val();
	if(yzlx == "0"){
		params.push({name:"yzResource",value:1});
	}else if(yzlx == "1"){
		params.push({"name":"yzResourceUpEQN","value":2});
	}else{
		params.push({"name":"yzResourceUpEQN","value":0});
	}
	
	if (paramTemp) {
		params = params.concat(paramTemp);
	}
	
	$.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/doctorAdvice/toYzList',
		dataType : 'html',
		data : params,
		success : function(data) {
			
			$("#yzInfo").html(data);
			$("#yzInfo .labSex").each(function(){
				$(this).html(getDicValue("sex",$(this).html()));
			});
			$("#yzInfo .labAgeUnit").each(function(){
				$(this).html(getDicValue("ageUnit",$(this).html()));
			}); 
			
			
			//判断医嘱中的药单是否都审核，是则不展示，不则展示药单详情
			$(".tdPatient").each(function(){
				var inpatientNo = $(this).attr("inpatientNo");
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
					$(this).attr("isShow","hide");
				}else{
					$(this).attr("isShow","show");
				}
				
				var flag = false;
				$("[name=patient_"+inpatientNo+"]").each(function(){
					
					var fH  = $(this).find(".bingquFH");
					var existV = fH.length;
					if(existV == 1){
						flag = true;
						$(this).find(".panelBody").show();
					}
				});
				
				if(flag){
					$("[name=patient_"+inpatientNo+"]").each(function(){
						$(this).show();
					});
					$(this).attr("isShow","show");
				}
				
			});
			
			//点击医嘱头部展示药单详情
			$("#yzInfo .panelHeadBtTD").click(function(){
				$err = $("#checkNOReason_"+$(this).attr("pidsj"));
				if($err.css("display")=="block"){
					$err.css("display","none");
				}else{
					$err.css("display","block");
				}
			});
			
			$("#yzInfo .panelHeadBtTD").each(function(){
				$(this).parent().css("background-color","transparent");
			});
			
		},
		error : function(data){
			message({html:'<spring:message code="common.op.error"/>'});
		}
	});
	
}

function checkFH(pidsj,obj,checkType,freqCode,yzlx,drugname,inpatientNo){
	
	var yzshzt = $(obj).parent().attr("yzshzt");
	if(checkType=='checkOK'){
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
					message({html: checkErr});
					return ;
				}else if(f<1){
					checkErr = "频次["+freqCode+"] 没有对应的批次数据，无法审核通过";
					message({html: checkErr});
					return ;
				}
			}
		}
		
		$("#checkNOYY_"+pidsj).hide();
		$("#result_"+pidsj).hide();
		$("#checkNOReason_"+pidsj).hide();
		checkSubmit(pidsj,"Y","1",yzshzt,inpatientNo,obj,"true");
		
		
	}else{
		
		if($("#checkNOSele_"+pidsj).val() ==""){
			$("#checkNOSele_"+pidsj+"_yy").show();
        
			$("#checkNOYY_"+pidsj).show();
			$("#checkNOReason_"+pidsj).show();
			$("#result_"+pidsj).show();
			return ;
		}
		$("#checkNOYY_"+pidsj).hide();
		
		checkSubmit(pidsj,"N","2",yzshzt,inpatientNo,obj,"true");
		
	}
	
	
}

function checkOne(pidsj,obj,checkType,freqCode,yzlx,inpatientNo){
	
	var yzshzt = $(obj).parent().attr("yzshzt");
	var drugname = $(obj).attr("data-drugname");
	
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
					message({html: checkErr});
					return ;
				}else if(f<1){
					checkErr = "频次["+freqCode+"] 没有对应的批次数据，无法审核通过";
					message({html: checkErr});
					return ;
				}
			}
		}
		
		if(yzshzt==1){//原状态为通过，重新审核通过
			message({
				html: "<spring:message code='comm.mess20'/>",
				showCancel:true,
				confirm:function(){
					checkSubmit(pidsj,"Y","1",yzshzt,inpatientNo,obj,"false");
				}
	    	});
		}else{
			checkSubmit(pidsj,"N","1",yzshzt,inpatientNo,obj,"false");
		}
		$("#checkNOYY_"+pidsj).hide();
		$("#result_"+pidsj).hide();
		$("#checkNOReason_"+pidsj).hide();
	}else{//审核新的状态为不通过
		if($("#checkNOSele_"+pidsj).val() ==""){
			$("#checkNOSele_"+pidsj+"_yy").show();
        
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
					checkSubmit(pidsj,"N","2",yzshzt,inpatientNo,obj,"false");
				}
	    	});
		}else{
			checkSubmit(pidsj,"N","2",yzshzt,inpatientNo,obj,"false");
		}
	}
}

function checkSubmit(pidsj,repeatCheck,newYzshzt,yzshzt,inpatientNo,obj,fh){
	$("#checkNOYY_"+pidsj).hide();
	$.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
		dataType : 'json',
		cache : false,
		//showDialog: false,
		data : {"pidsjN":pidsj,
				"repeatCheck":repeatCheck,
				"newYzshzt":newYzshzt,
				"yzshbtglx":$("#checkNOSele_"+pidsj).val(),
				"yzshbtgyy":$("#yzshbtgyy_"+pidsj).val().trim(),
				"yzParea":"1","checkType":"yssf","fhCheck":fh
				},
		success : function(response) {
			
			if(response.code==0){
				
				//$("#yshCheck_"+pidsj).remove();
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
				
				/* if(inpatientNo!=""){
					if(isIE()){
						var controls=document.getElementsByName('patient_'+inpatientNo);
						for(var i=0;i<controls.length;i++)
						{
						    controls[i].scrollIntoView();
						    break;
						}
					}
						
				} */
			}else{
				message({html: response.msg});
			}
		}
	});
}

function showHidePat(obj){
	if($(obj).attr("isShow")=="hide"){
		$(obj).attr("isShow","show");
		$("#yzInfo [name='patient_"+$(obj).attr("inpatientNo")+"']").show();
	}else{
		$(obj).attr("isShow","hide");
		$("#yzInfo [name='patient_"+$(obj).attr("inpatientNo")+"']").hide();
	}
}

function yshCheck(pidsj,obj,yzzdshzt,freqCode,yzlx,drugname,inpatientNo,yzzdshbtglx){
	checkErr = "" ;
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

/* function isIE() {
    if (!!window.ActiveXObject || "ActiveXObject" in window)  
        return true;  
    else  
        return false;  
}
 */
</script>
</html>