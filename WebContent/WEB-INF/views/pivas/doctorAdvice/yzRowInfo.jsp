<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:if test="${success==true}">
<link href="${pageContext.request.contextPath}/assets/pivas/css/common.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/assets/pivas/js/cssjs.js" type="application/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>

<style>
.patient {
	color: #ffffff;
	background-color: #39aeaa;
	background: -webkit-linear-gradient(#8f8f8f, #797979); /* Safari 5.1 - 6.0 */
	background: -o-linear-gradient(#8f8f8f, #797979); /* Opera 11.1 - 12.0 */
	background: -moz-linear-gradient(#8f8f8f, #797979); /* Firefox 3.6 - 15 */
	background: linear-gradient(#8f8f8f, #797979); /* 标准的语法 */
	padding: 10px 15px;
	border-radius:5px 5px 0 0;
}
.way-tittle{
	color:#39aeaa;
}
.popup div.row div.column{
	border: 1px solid #ddd;
	width: 60%;
}
.auto1 {
	color: white;
	background-color: #EDD681;
	margin-top: -4px;
	cursor: pointer;
}

.auto2 {
	color: white;
	background-color: #39aeaa;
	margin-top: -4px;
	cursor: pointer;
}

.shenfangDiv input[type="button"] {
	width: 60px;
	height: 24px;
	cursor: pointer;
}

.shenfangDiv .auto {
	color: white;
}

.shenfangDiv .autoSucc {
	color: white;
	background-color: #73ab65;
}

.shenfangDiv .autoFail {
	color: white;
	background-color: #d45120;
}

.shenfangDiv .Ok {
	background:
		url("${pageContext.request.contextPath}/assets/pivas/images/icons.png")
		no-repeat;
	background-position:-13px -192px;
	background-color: #39aeaa;
	border:1px solid #257e7b;
}

.shenfangDiv .OkPass {
	background:
		url("${pageContext.request.contextPath}/assets/pivas/images/icons.png")
		no-repeat;
	background-position:-13px -192px;
	background-color: #73ab65;
}

.shenfangDiv .Ok:hover {
	background-color: #73ab65;
}

.shenfangDiv .OkPass:hover {
	background-color: #73ab65;
}

.shenfangDiv .Error {
	background:
		url("${pageContext.request.contextPath}/assets/pivas/images/icons.png")
		no-repeat;
	background-position:-12px -226px;
	background-color: #e84d4d;
	border:1px solid #bc3434;
}

.shenfangDiv .ErrorPass {
	background:
		url("${pageContext.request.contextPath}/assets/pivas/images/icons.png")
		no-repeat;
	background-position:-12px -226px;
	background-color: #d45120;
}

.shenfangDiv .Error:hover {
	background-color: #d45120;
}

.shenfangDiv .ErrorPass:hover {
	background-color: #d45120;
}

.panelMain {
	padding: 10px 15px;
	background:#eaeaea;

}

.panel {
	border: 1px solid #ddd;
	border-radius: 4px;
	-webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
}

.panelHead {
	padding: 6px;
	color: #333;
	background-color: #f5f5f5;
	border-bottom: 1px solid #ddd;
	border-top-left-radius: 3px;
	border-top-right-radius: 3px;
}

.panelHead headBtSpan {
	padding: 5px;
	color: #333;
	background-color: #f5f5f5;
	border-bottom: 1px solid #ddd;
	border-top-left-radius: 3px;
	border-top-right-radius: 3px;
}

textarea {
	padding: 2px 4px;
	border: 1px solid #ccc;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	border-radius: 3px;
	background: white;
}

.detail-result {
	padding-top: 5px;
	padding-left: 12px;
}

#showTable td{
height:26px;
	border: 1px solid #e4e4e4;
}
.button:hover{
 	color:blue;
}

.viewText{
	height: 25px;
    line-height: 25px;
    font-size: 12px;
}
.table-wrapper tr td{
	text-indent:30px;
	padding:10px 20px;
}
</style>

<script type="application/javascript">
$(function() {
	/*
	$(".labSex").each(function(){
		$(this).html(getDicValue("sex",$(this).html()));
	});
	$(".labAgeUnit").each(function(){
		$(this).html(getDicValue("ageUnit",$(this).html()));
	});
	*/
	/*
	$("#yzzt").val(getDicValue("yzzt",$("#yzzt").val()));
	$("#yzlx").val(getDicValue("yzlx",$("#yzlx").val()));
	$("#sex").val(getDicValue("sex",$("#sex").val()));
	$(".dic_yzzt").each(function(){
		$(this).html(getDicValue("yzzt",$(this).html()));
	});
	$("#yzshzt").html(getDicValue("yzshzt",$("#yzshzt").html()));
	*/
	
	$(".result-title [name='yzshbtglx']").change(function(){
		var value = $(this).val();
		if(value == ""){
			$(this).parents("table").find("tr:eq(1)").hide();
		}else{
			var hidden =$(this).parents("table").find("tr:eq(1)").is(":hidden");
			if(hidden){
				$(this).parents("table").find("tr:eq(1)").show();
			}
		}
		
	});
	
	var fieldsInfo = $("#editView-form").find("input.edit");
	sdfun.fn.trimAll("editView-info"); 
	
	$("#editView-info").dialog({
		autoOpen: false,
		height: 500,
		width: 500,
		modal: true,
		resizable: false,
		buttons: {
			"<spring:message code='common.confirm'/>": function() {
				resetForm("editView-info");
				$("#editView-info").dialog("close");
			}
		},
		close: function() {
			resetForm("editView-info");
		}
	});
	
});
function lookDetail(patname,inpatientNo,sex,age,ageunit){
	
	event.stopPropagation();
	$("#xm").text(patname);
	$("#zyh").text(inpatientNo);
	if(sex == "0"){
		$("#xb").text("女");
	}else if(sex =="1"){
		
		$("#xb").text("男");
	}
	$("#zyrq").text();
	$("#nl").text(age+getDicValue("ageUnit",ageunit));
	$("#blh").text();
	
	$("#editView-info").dialog("open");
	
}
</script>
	<c:if test="${yzMainListSize>0}">
		<c:set var="inpatientNo" value="-1" />
		<c:forEach items="${yzMainList}" var="yzMain" varStatus="status">
			<c:if test="${inpatientNo!=yzMain.inpatientNo}">
				<div style="margin-top:10px;">
				<div class="patient">
					<table>
						<tr style="">
							<td style="width: 800px;" class="tdPatient"
								onclick="showHidePat(this)" inpatientNo="${yzMain.inpatientNo}">
								<span class="patient-name" style="margin-right: 25px;">
								<label style="margin-right: 25px;">${yzMain.wardName}</label>
								<label style="margin-right: 25px;">${yzMain.bedno}</label>
								<label style="margin-right: 25px;">${yzMain.patname} </label>
								<label style="margin-right: 25px;">${yzMain.inpatientNo} </label>
								<label class="labSex" style="margin-right: 25px;">${yzMain.sex}</label>
								<label>${yzMain.age}</label>
								<label class="labAgeUnit">${yzMain.ageunit}</label>
								</span> 
								<span >体重：${yzMain.avdp}</span>
								
								<label style="margin-left:25px;">诊断信息：无 &nbsp;</label><a class="button btn-bg-green" href="#" onclick="lookDetail('${yzMain.patname}','${yzMain.inpatientNo}','${yzMain.sex}','${yzMain.age}','${yzMain.ageunit}')">查看详情</a>
								</td>
								<!-- <td style="width: 240px; background-color: #d9edf7;">
								<span style="float: right !important;"> 
									<input class="auto1" value="自动审方" id="checkAuto_all" onclick="autoApprovalMany('${yzMain.inpatientNo}')" type="button"  />
								</span>
								</td> -->
							</tr>
						</table>
					</div>
				</div>
			</c:if>
			<c:set var="inpatientNo" value="${yzMain.inpatientNo}" />
			<div class="panelMain" name="patient_${yzMain.inpatientNo}">
				<div class="panel">
					<div class="panelHead shenfangDiv"
						style="background-color: 
                    <c:choose><c:when test="${yzMain.startDayDelNum>=0 && yzMain.startHour>=12}">rgba(234,147,147,1);</c:when>
                    <c:when test="${yzMain.startDayDelNum>=0 && yzMain.startHour>=11}">#6F96E5;</c:when>
                    <c:otherwise></c:otherwise>
                    </c:choose>
                    ">
						<table>
							<tbody>
							<tr>
							<td class="panelHeadBtTD" pidsj="${yzMain.pidsj}">
							<span style="font-size:15px;font-weight:bold;margin-right:25px;"class="way-tittle">频次\用法：${yzMain.freqCode}\ ${yzMain.supplyCode}</span>
							<span style="margin-right: 25px;">组号：${yzMain.parentNo}</span> 
							<span style="margin-right: 25px;">输液量：${yzMain.transfusion}ml</span>
							<span style="margin-right: 25px;"> 开立时间：${yzMain.startTimeS}</span> 
							<!-- 0115bianxw修改 先注释掉
                     		<span style="  float: right !important;  margin-top: 3px;" >自动审方状态：
                     		<label id="checkAutoBak_${yzMain.pidsj}" >
                     		<c:choose>
                     		<c:when test="${yzMain.yzzdshzt==1}">审方通过</c:when>
                     		<c:when test="${yzMain.yzzdshzt==2}">一般问题</c:when>
                     		<c:when test="${yzMain.yzzdshzt==3}">严重问题</c:when>
                     		<c:otherwise>未审</c:otherwise>
                     		</c:choose>
                     		</label> 
                     		</span>440 -->	
                      		</td>
							<td style="width: 300px;">
							<shiro:hasPermission name="PIVAS_BTN_513">
							<span class="headBtSpan  navbar-right validate_button" mo-id="9773" 
								mo-state="draft" style="float: right !important; font-weight: bold;" yzshzt="${yzMain.yzshzt}"> 
							<c:if test="${yzMain.yzzdshzt>0}"> ${yzMain.yzzdshzt==1?["预审通过"]:"<label style='color: red' >[预审不通过]</label>"}
							<c:if test="${yzMain.yzshzt==0}">
								<input class="auto2" value="预审确认" id="yshCheck_${yzMain.pidsj}"
								onclick="yshCheck('${yzMain.pidsj}',this,'${yzMain.yzzdshzt}','${yzMain.freqCode}','${yzMain.yzlx}','${yzMain.drugname}','${yzMain.inpatientNo}','${yzMain.yzzdshbtglx}')"
								type="button" style="width: 75px;" />
							</c:if>
							</c:if> 
							<!-- 
                        	<input class='<c:choose><c:when test="${yzMain.yzzdshzt==1}">autoSucc</c:when><c:when test="${yzMain.yzzdshzt==2 || yzMain.yzzdshzt==3}">autoFail</c:when><c:otherwise>auto</c:otherwise></c:choose>' value="自动审方"	 id="checkAuto_${yzMain.pidsj}" onclick="autoApprovalOne('${yzMain.pidsj}')" type="button" style="width: 75px;" />
						 	--> 
						 	<input 
						 		class="<c:if test="${yzMain.yzshzt==1}">OkPass</c:if><c:if test="${yzMain.yzshzt!=1}">Ok</c:if>"
								value="&nbsp;" id="checkOK_${yzMain.pidsj}" data-drugname = "${yzMain.drugname}"
								onclick="checkOne('${yzMain.pidsj}',this,'checkOK','${yzMain.freqCode}','${yzMain.yzlx}','${yzMain.inpatientNo}')"
								type="button" />
							<input
								class="<c:if test="${yzMain.yzshzt==2}">ErrorPass</c:if><c:if test="${yzMain.yzshzt!=2}">Error</c:if>"
								value="&nbsp;" id="checkNO_${yzMain.pidsj}" data-drugname = "${yzMain.drugname}"
								onclick="checkOne('${yzMain.pidsj}',this,'checkNO','${yzMain.freqCode}','${yzMain.yzlx}','${yzMain.inpatientNo}')"
								type="button" />
							</span>
							</shiro:hasPermission>
							</td>
							</tr>
						</table>
					</div>

					<div id="checkNOReason_${yzMain.pidsj}" class="panelBody"
						style="<c:if test="${yzMain.yzshzt!=0}">display:none;</c:if>">
						<div class="medical_order_line_detail"
							style="overflow: hidden; padding-bottom: 10px;">
							<div class="medical_order" data-id="9773">
							<table
								class="table-striped table-condensed table-hover medical_order_line table-wrapper" style="width:100%;" id="showTable">
								<tbody>
									<c:forEach items="${yzMain.yzList}" var="yz" step="1"
										varStatus="status">
										<tr style='<c:if test="${status.index/2==1}" >background-color: rgb(245, 245, 245);</c:if><c:if test="${status.index/2!=1}" >background-color: rgb(237, 237, 237);</c:if>'>
										<td width="35%" style="text-align: left;">${yz.yz_medicamentsName}</td>
										<td width="20%" style="text-align: left;">${yz.yz_specifications}</td>
										<td width="15%" style="text-align: left;">${yz.yz_dose}${yz.yz_doseUnit}</td>
										<td width="15%" style="text-align: left;">${yz.yz_quantity}${yz.yz_medicamentsPackingUnit}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							</div>
							
							
							<div class="detail-result" 
								style="<c:if test='${yzMain.yzshzt == 1}'>display:none;</c:if>"
								id="result_${yzMain.pidsj}">
								<div class="result-title" id="checkNOSele_${yzMain.pidsj}_yy">
									<table style="width: 100%">
										<tr style="height:35px;">
										<td style="width:80px;">不通过原因：</td>
										<!-- <spring:message code='comm.mess25'/> -->
										<td style="width:800px;"> 
										<a class="search select" style="height: 20px; width: 58px;">
										<select name="yzshbtglx" id="checkNOSele_${yzMain.pidsj}"
												style="width: 100px; height: 28px;margin-top: -5px;">
														<!-- 0115bianxw修改 width: 183px -->
											<option value=""><spring:message code='comm.mess19' /></option>
											<c:forEach items="${errTypeList}" var="errType">
											<option value="${errType.gid}"
											<c:if test="${yzMain.yzshzt==2 && yzMain.yzshbtglx==errType.gid}" >selected="selected"</c:if>>${errType.name}</option>
											</c:forEach>
										</select>
										</a> 
										<span class="tip" id="checkNOYY_${yzMain.pidsj}" style="display: none;">
											<div class="arrow" style="left:32px;">
												<div class="tip-content" style="width: 93px; top: -16px; left: 15px;">
													请选择原因
												</div>
											</div>
										</span>
										<c:if test="${yzMain.yzshzt ==2 && yzMain.recheckstate != null && yzMain.recheckstate == '0'}">
										<input class="bingquFH" type="hidden">
										<span  style="margin-left: 100px;">病区复核：</span>
										<input style="width: 200px;" id="bqfh__${yzMain.pidsj}" maxlength="256" value="${yzMain.recheckcause}">
										<a class="button" id="fhButtonNo" onclick="checkFH('${yzMain.pidsj}',this,'checkNO','${yzMain.freqCode}','${yzMain.yzlx}','${yzMain.drugname}','${yzMain.inpatientNo}')">拒绝</a>
										<a class="button" id="fhButtonOk" onclick="checkFH('${yzMain.pidsj}',this,'checkOK','${yzMain.freqCode}','${yzMain.yzlx}','${yzMain.drugname}','${yzMain.inpatientNo}')">强制打包</a>
										</c:if>
										</td>
										
										<%-- <td style="width: 40%">
											<a class="search" style="height: 20px;">备注：
											<input type="text" style="height: 25px; width: 55%"
													name="yzshbtgyy" id="yzshbtgyy_${yzMain.pidsj}"
													maxlength="256"
													value="<c:if test="${yzMain.yzshzt==2}" >${yzMain.yzshbtgyy}</c:if>">
											</a>
										</td> --%>
										</tr>
										<tr style="height:100px;<c:if test='${yzMain.yzshzt != 2}'>display:none;</c:if>">
										<td style="width:80px;">
										<span style="vertical-align: top;">备注：</span>
										</td>
										<td>
										<c:if test="${yzMain.yzshzt==2}">
										<textarea style="height: 100px; width: 300px;"
												name="yzshbtgyy" id="yzshbtgyy_${yzMain.pidsj}"
												maxlength="256" >${yzMain.yzshbtgyy}</textarea>
										</c:if>
										<c:if test="${yzMain.yzshzt!=2}">
										<textarea style="height: 100px; width: 300px;"
												name="yzshbtgyy" id="yzshbtgyy_${yzMain.pidsj}"
												maxlength="256" ></textarea>
										</c:if>
										</td>
										
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${yzMainListSize==0}">
		<div class="detail-type-1">
			<div class="detail-item">
				<spring:message code='comm.mess27' />
			</div>
		</div>
	</c:if>
	<%-- <div class="item-submit-bar" style="display: none;">
		<input type="button" class="cancel" onclick="backMain()"
			value="<spring:message code='comm.mess28'/>"
			style="background-color: #ebb800; cursor: pointer;" />
	</div> --%>
	</c:if>
<c:if test="${success!=true}">
	<spring:message code='comm.mess27' />
</c:if>
<div id="editView-info" title="诊断信息" align="center" style="display: none;">
	<form id="editView-form" method="post" style="height:300px;">
		<div class="popup">
			<div style="border-top: solid 1px #CCCCCC;margin-top: 10px;"></div>
			<div class="row" style="float:left">
				<div class="column" style="width:40%;text-align: left;">
					<label class="tit" style="width:30px;" >姓名</label>
					<label class="viewText" id="xm"></label>
				</div>
				<div class="column" style="">
					<label class="tit" style="width:50px;">住院号</label>
					<label class="viewText" id="zyh"></label>
				</div>
			</div>
			<div class="row" style="float:left">
				<div class="column" style="width:40%;text-align: left;">
					<label class="tit" style="width:30px;" >性别</label>
					<label class="viewText" id="xb"></label>
				</div>
				<div class="column" style="">
					<label class="tit" style="width:60px;">住院日期</label>
					<label class="viewText" id="zyrq"></label>
				</div>
			</div>
			<div class="row" style="float:left">
				<div class="column" style="width:40%;text-align: left;">
					<label class="tit" style="width:30px;" >年龄</label>
					<label class="viewText" id="nl"></label>
				</div>
				<div class="column" style="">
					<label class="tit" style="width:50px;">病理号</label>
					<label class="viewText" id="blh"></label>
				</div>
			</div>
			
			<table style="width:100%;height:100px;">
			<thead>
			<tr style="background: #7480b770;">
				<td width="30%"  style="padding: 6px 10px;">诊断信息</td>
				<td width="70%"  style="padding: 6px 10px;">备注</td>
			</tr>
			</thead>
			<tbody id="">
			</tbody>
			</table>
		</div>
	</form>
	<div style="border-top: solid 1px #CCCCCC;margin-top: 10px;"></div>
</div>

