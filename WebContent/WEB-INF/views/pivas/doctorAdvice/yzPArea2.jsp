<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/assets/multDropList/multDropList.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/pivas/css/doctor.advice.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/assets/pivas/js/srvs.js" type="application/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<style type="text/css">
.viewText{
	height: 30px;
    line-height:30px;
    font-size: 12px;
}

.rightDiv table tr:nth-child(2n) {
	background: transparent;
}

.rightDiv table tr td {
	padding: 12px 8px;
}
.table-yz-info  tr td{
	border:1px solid #3f51b53b;
}

.fontBold {
	font-weight: bold;
}

.trSelect {
	background-color: #ffeece; /*#E1E9F8;*/
}

.button {
	margin-left: 5px;
}

.button:hover{
 	color:blue;
}

#searchModel table td{
vertical-align:middle;
}

#mainTable table > td{
vertical-align: top;
}

a.multiSelect:link, a.multiSelect:visited, a.multiSelect:hover, a.multiSelect:active {
margin-top: -1px;
}


#tabs-yp .cbit-grid div.bDiv{
    background: white;
}
.popup div.row div.column{
	border: 1px solid #ddd;
	width: 60%;
}
.medicine-tab table td{
	padding:0px;
}

</style>
</head>
<body>
<div class="medicine-tab" style="width: 100%;">
	<div id="yzMainDiv__1" class="main-div" style="min-width:1200px;">
		<div id="searchModel" class="search_div">
			<table  style="width:100%;" data-qryMethod funname="condiQuery">
				<tr>
					<td ><input name="bednoS" placeholder="<spring:message code='pivas.yz2.bedno'/>" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz2.patname'/>" name="patnameS" size="8" data-cnd="true"></td>
					<td><input  placeholder="<spring:message code='pivas.yz1.parentNo'/>"  name="parentNoS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz1.drugname'/>" name="drugnameQry" size="8" data-cnd="true"></td>
					<td>
						用药日期：
						<select id="sfrqSelect" class="select_new" style="height:26px;width:80px;">
							<option value=""  selected="selected" >--请选择--</option>
							<option value="today">今日</option>
							<option value="tommorrow">明日</option>
						</select>
					</td>

					<td >
						审核状态：
						<select id="stateSelect" class="select_new" style="height:26px;">
							<option value="todayNew">新医嘱</option>
							<option value="unCheck" selected="selected" >未审核</option>
							<option value="checkOK">审方通过</option>
							<option value="checkNO">审方不通过</option>
							<option value="checkNOHasYD">不通过有药单</option>
							<option value="mismatchYD">药单不匹配</option>
						</select>
					</td>
					<td>
						医嘱类型：
						<select id="yzlxSelect" class="select_new" style="height:26px;">
							<option value=""  selected="selected" >全部</option>
							<option value="0" >长嘱</option>
							<option value="1">临嘱</option>
							<option value="2">单药医嘱</option>
						</select>
					</td>
					<td >
						<span>审方分类：</span>
						<select id="shSelect" class="select_new" style="height:26px;width:80px;">
							<option value="" selected="selected">请选择</option>
							<option value="1">TPN审方</option>
							<option value="2">化疗审方</option>
							<option value="3">其他审方</option>
						</select>
					</td>
					<td>
						<span>医嘱状态：</span>
						<select id="yzState" class="select_new" style="height:26px;">
							<option value="" selected="selected" >请选择</option>
							<option value="1">系统判断通过</option>
							<option value="2">系统判断不通过</option>
						</select>
					</td>
					<td><button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button>&nbsp;&nbsp;<button class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button></td>
				</tr>

			</table>
		</div>

		<div id="mainContainer">
			<div id="mainTable" style="height: 100%; width: 100%;">
				<div>
					<div style="width:49%;float:left;" id="listTable">
						<div style="width:100%;margin-top: 5px;margin-bottom:15px;">
							<shiro:hasPermission name="PIVAS_BTN_516">
								<b>批量审方</b> >>
								<a class="button_new green_jianbian" id="aCheckMany__1">通过</a>
								<a class="button_new red_jianbian" id="aCheckManyNO__1">不通过</a>
								<a class="button_new green" id="aCheckYSHMany__1">预审确认</a>
							</shiro:hasPermission>
						</div>
						<div style="width:100%;height:100%;">
							<table id="yzDataTable" class="table datatable ui-data-table display dataTable no-footer">
								<thead>
								<tr>
									<th><input type="checkbox" id="all_checked"></th>
									<th>审方结果</th>
									<th>床号</th>
									<th>病人姓名</th>
									<th>组号</th>
									<th>审方日期</th>
								</tr>
								</thead>
							</table>
						</div>
					</div>
					<div  style="padding:10px;width:49%;float:left;background:#ffeece;border-radius: 10px; box-shadow: 1px 1px 5px #cccccc">
						<div style="width:100%;margin-top: 5px;margin-bottom: 5px;">
							<b>医嘱详情</b> >>
						</div>
						<div class="rightDiv" style="background-color: white; width: 100%;height:100%;">
							<div style="background-color:#777777; text-align: left; color: white; height:33px; padding-top:7px;padding-left: 10px; font-size: 14px;">
								组号：<span id="parentnoId"></span>
								<div style="float: right;text-align: right; color: white; height: 40px; font-size: 14px;padding-right: 10px;">
									<shiro:hasPermission name="PIVAS_BTN_516">
										<label id="labYSHzt__1" style="font-weight: bold;"></label>
										<a class="button_new ui-btn-bg-green green_jianbian" id="aCheckOK__1" >通过</a>
										<a class="button_new red_jianbian" id="aCheckNO__1">不通过</a>
										<a class="button_new green" id="aCheckYSH__1" style="display: none;">预审确认</a>
									</shiro:hasPermission>
								</div>
							</div>
							<div style="padding:5px;background:#ffffff;">
								<table id="yzInfo__1" class="table-yz-info">
									<colgroup>
										<col width="10%">
										<col width="10%">
										<col width="10%">
										<col width="20%">
										<col width="11%">
										<col width="10%">
										<col width="10%">
										<col width="19%">
									</colgroup>
									<tr>
										<td colspan="8" style="height: 7px;">
											<div class="fontBold" name="pidsj"
												 style="visibility: hidden; height: 0px; max-height: 0px;"></div>
											<div class="fontBold" name="drugname"
												 style="visibility: hidden; height: 0px; max-height: 0px;"></div>
											<div class="fontBold" name="yzzdshzt"
												 style="visibility: hidden; height: 0px; max-height: 0px;"></div>
											<div class="fontBold" name="yzzdshbtglx"
												 style="visibility: hidden; height: 0px; max-height: 0px;"></div>
										</td>
									</tr>
									<tr style="background: #e1f8f4;">
										<td style="text-align: right;">开立医生：</td>
										<td class="fontBold" name="doctorName"></td>
										<td style="text-align: right;">开立时间：</td>
										<td class="fontBold" name="startTimeS"></td>
										<td style="text-align: right;">病区：</td>
										<td class="fontBold" name="wardName"></td>
										<td style="text-align: right;">结束时间：</td>
										<td class="fontBold" name="endTimeS"></td>
									</tr>
									<tr style="background: #e1f8f4;">
										<td style="text-align: right;">病人姓名：</td>
										<td class="fontBold" name="patname"></td>
										<td style="text-align: right;">床号：</td>
										<td class="fontBold" name="bedno"></td>
										<td style="text-align: right;">性别：</td>
										<td class="fontBold" name="sex"></td>
										<td style="text-align: right;">体重：</td>
										<td class="fontBold" name="avdp"></td>
									</tr>

									<tr style="background: #e1f8f4;">
										<td style="text-align: right;">年龄：</td>
										<td class="fontBold" name="age"></td>
										<td style="text-align: right;">出生日期：</td>
										<td class="fontBold" name="birthdayS"></td>
										<td style="text-align: right;">频次\用法：</td>
										<td class="fontBold" name="freqCode"></td>
										<td colspan="2">
											<div style="float:left;width:65px;line-height:28px;">诊断信息：</div>
											<div class="fontBold" name="zdxx" style="float:left;"></div>
										</td>
									</tr>
									<tr style="display:none;">
										<td width="30%" class="fontBold" name="freqCode" ></td>
										<td width="30%" class="fontBold" name="yzshzt" ></td>
									</tr>
								</table>

								<div id="tabs-yp" style="margin-top: 10px;height:130px;">
									<table id="ypDataTable">
										<thead>
										<tr>
											<th>药品编码</th>
											<th>药品名称</th>
											<th>规格</th>
											<th>剂量</th>
											<th>数量</th>
										</tr>
										</thead>
									</table>
								</div>
								<table style="border-collapse: separate;border-spacing: 10px;background: #e3d6cf;">
									<tr style="height: 20px; font-size: 15px;">
										<td style="width:112px;padding:0 0px;" >不通过原因：</td>
										<td style="padding:0 0px;">
											<label>备注：</label>
										</td>
									</tr>
									<tr>
										<td style="padding:0 0px;width:100px; vertical-align:top;">
											<select style="height: 23px;" id="yzshbtglx__1">
												<option value=""><spring:message code='comm.mess19' /></option>
												<c:forEach items="${errTypeList}" var="errType">
													<option value="${errType.gid}">${errType.name}</option>
												</c:forEach>
											</select>
											<span class="tip" id="yzshbtglxTit__1" style="display: none;">
												<div class="arrow" style="left: 0px;">
													<div class="tip-content"
														 style="width: 93px; top: -16px; left: 15px;">
														请选择原因
													</div>
												</div>
											</span>
										</td>
										<td style="padding:0 0px;">
											<textarea style="width: 300px;height:100px;" id="yzshbtgyy__1" maxlength="256"></textarea>
										</td>
									</tr>
									<tr id="fhModel" style="display:none;">
										<td><label>病区复核：</label></td>
										<td>
											<input style="width: 200px;" id="bqfh" maxlength="256">
											<a class="button" id="fhButtonNo" >拒绝</a><a class="button" id="fhButtonOk" >强制打包</a>
										</td>
									</tr>
								</table>
							</div>
						</div>
				</div>
				</div>
			</div>
		<div style="display:none;">
			<ul id="batchRule" style="visibility: hidden;height: 0px;" >
				<c:forEach items="${batchRuleList}" var="batchRule" >
					<c:if test="${empty batchRule.ru_key}">
						<li name="${batchRule.pinc_code}" style="height: 0px;" >${batchRule.pinc_name}</li>
					</c:if>
				</c:forEach>
			</ul>

			<ul id="ruleKey" style="visibility: hidden;height: 0px;" >
				<c:forEach items="${ruleList}" var="rule" >
					<c:if test="${!empty rule.ru_key}">
						<li name="${rule.pinc_code}" style="height: 0px;" >${rule.ru_key}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
<%-- 新增编辑弹出框 --%>
<div id="divYZCheckMany" title="<spring:message code='pivas_yz1.shenfangMore'/>" align="center" style="display: none;">
	<form id="editView-form" action="" method="post" style="text-align:left;">
		<div style="margin-top:30px;margin-left:50px">
			<label><spring:message code='comm.mess18'/>:</label>
			<select name="yzshbtglx" id="yzshbtglx" style="width: 183px;  height: 28px;">
				<option value=""><spring:message code='comm.mess19'/></option>
				<c:forEach items="${errTypeList}" var="errType"  >
					<option value="${errType.gid}">${errType.name}</option>
				</c:forEach>
			</select>
		</div>
		<div style="margin-top:5px;margin-left:50px">
			<label><spring:message code='common.remark'/>:</label>
		</div>
		<div style="margin-left:50px">
			<textarea name="yzshbtgyy" id="yzshbtgyy" style="width: 400px;height:100px;" maxlength="256" title="<spring:message code='common.op.remind6'/>"></textarea>
		</div>
	</form>
</div>

<div id="editView-info" title="诊断信息" align="center" style="display: none;">
	<form id="editView-form-info" method="post" style="height:300px;">
		<div class="popup">
			<%--<h3></h3>--%>
			<%--<div style="border-top: solid 1px #CCCCCC;margin-top: 10px;"></div>--%>
			<div class="row" style="float:left">
				<div class="column" style="width:40%;text-align: left;">
					<label class="tit" style="width:35px;" >姓名:</label>
					<label class="viewText" id="xm"></label>
				</div>
				<div class="column" style="">
					<label class="tit" style="width:55px;">住院号:</label>
					<label class="viewText" id="zyh"></label>
				</div>
			</div>
			<div class="row" style="float:left">
				<div class="column" style="width:40%;text-align: left;">
					<label class="tit" style="width:35px;" >性别:</label>
					<label class="viewText" id="xb"></label>
				</div>
				<div class="column" style="">
					<label class="tit" style="width:70px;">住院日期:</label>
					<label class="viewText" id="zyrq"></label>
				</div>
			</div>
			<div class="row" style="float:left">
				<div class="column" style="width:40%;text-align: left;">
					<label class="tit" style="width:35px;" >年龄:</label>
					<label class="viewText" id="nl"></label>
				</div>
				<div class="column" style="">
					<label class="tit" style="width:55px;">病理号:</label>
					<label class="viewText" id="blh"></label>
				</div>
			</div>

			<table style="width:100%;height:100px;">
				<thead>
				<tr style="background: #eeeeee;">
					<td width="30%" style="padding:6px 10px;">诊断信息</td>
					<td width="70%"  style="padding:6px 10px;">备注</td>
				</tr>
				</thead>
				<tbody id="">
				</tbody>
			</table>
		</div>
	</form>
	<div style="border-top: solid 1px #CCCCCC;margin-top: 10px;"></div>
</div>

</body>

<script type="text/javascript">
var paramTemp;
var ypStr = "";
var allFields;
var clickStr = "";
var yzDataTable;
var paramAll;
var ypDataTable;
$(function() {

	$("#ypSelect").multiSelect({ "selectAll": false,"noneSelected": "选择分类","oneOrMoreSelected":"*" },function(){
		ypStr = $("#ypSelect").selectedValuesString();
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
	
	$("#shSelect").change(function(){
		queryYZ();
	});

	queryYZ();

	$("#aCheckOK__1").click(
			function() {
				var pidsj = $("#yzInfo__1 [name=pidsj]").html();
				if (!pidsj) {
					return;
				}
				var freqCode = $("#yzInfo__1 [name=freqCode]").html();
				var drugname = $("#yzInfo__1 [name=drugname]").html();
				checkErr = "";
				if ("${SYN_YZ_DATA_MODE}" == "3") {
					if (checkErr == "") {// && yzlx==0
						var f = 0;
						var freqCode = freqCode.toUpperCase().split(' ')[0];
						if ($("#ruleKey [name=" + freqCode + "]").length > 0) 
						{//如果 关键字规则 没有
							$("#ruleKey [name=" + freqCode + "]").each(
							function() 
							{
								f = -1;
								if (drugname.indexOf($(this).html()) > -1) {
									f = 1;
								}
							});
						}
						if (f < 1) {
							if ($("#batchRule [name=" + freqCode+ "]").length > 0) {//如果 没有一般规则对应的频次
								f = 2;
							} else {
								f = f - 1;
							}
						}
						if (f == -2) {
							checkErr = "频次[" + freqCode + "] 没有找到对应规则，无法审核通过";
						} else if (f < 1) {
							checkErr = "频次[" + freqCode + "] 没有对应的批次数据，无法审核通过";
						}

					}
					if (checkErr != "") {
						message({html : checkErr});
						return;
					}
				}
				var yzshzt = $("#yzInfo__1 [name=yzshzt]").html();
				
				if(yzshzt==1){//原状态为通过，重新审核通过
					message({
						html: "<spring:message code='comm.mess20'/>",
						showCancel:true,
						confirm:function(){
							checkOne__1(pidsj, 1,"false");
						}
			    	});
				}else{
					checkOne__1(pidsj, 1,"false");
				}
				
			});
	
	$("#aCheckNO__1").click(function() {
		var pidsj = $("#yzInfo__1 [name=pidsj]").html();
		if (!pidsj) {
			return;
		}
		if ($("#yzshbtglx__1").val() && $("#yzshbtglx__1").val() != "") {
			$("#yzshbtglxTit__1").hide();
		} else {
			$("#yzshbtglxTit__1").show();
			return;
		}
		
		var yzshzt =  $("#yzInfo__1 [name=yzshzt]").html();
		if(yzshzt==1){//原状态为通过，重新审核通过
			message({
				html: "<spring:message code='comm.mess20'/>",
				showCancel:true,
				confirm:function(){
					checkOne__1(pidsj, 2,"false");
				}
	    	});
		}else{
			checkOne__1(pidsj, 2,"false");
		}
		
		});
	
	$("#fhButtonOk").click(function(){
		var pidsj = $("#yzInfo__1 [name=pidsj]").html();
		if (!pidsj) {
			return;
		}
		var freqCode = $("#yzInfo__1 [name=freqCode]").html();
		var drugname = $("#yzInfo__1 [name=drugname]").html();
		checkErr = "";
		if ("${SYN_YZ_DATA_MODE}" == "3") {
			if (checkErr == "") {// && yzlx==0
				var f = 0;
				var freqCode = freqCode.toUpperCase().split(' ')[0];
				if ($("#ruleKey [name=" + freqCode + "]").length > 0) 
				{//如果 关键字规则 没有
					$("#ruleKey [name=" + freqCode + "]").each(
					function() 
					{
						f = -1;
						if (drugname.indexOf($(this).html()) > -1) {
							f = 1;
						}
					});
				}
				if (f < 1) {
					if ($("#batchRule [name=" + freqCode+ "]").length > 0) {//如果 没有一般规则对应的频次
						f = 2;
					} else {
						f = f - 1;
					}
				}
				if (f == -2) {
					checkErr = "频次[" + freqCode + "] 没有找到对应规则，无法审核通过";
				} else if (f < 1) {
					checkErr = "频次[" + freqCode + "] 没有对应的批次数据，无法审核通过";
				}

			}
			if (checkErr != "") {
				message({html : checkErr});
				return;
			}
		}
		checkOne__1(pidsj, 1,"true");
	});
	
	$("#fhButtonNo").click(function(){
		var pidsj = $("#yzInfo__1 [name=pidsj]").html();
		if (!pidsj) {
			return;
		}
		
		if ($("#yzshbtglx__1").val() && $("#yzshbtglx__1").val() != "") {
			$("#yzshbtglxTit__1").hide();
		} else {
			$("#yzshbtglxTit__1").show();
			return;
		}
		
		checkOne__1(pidsj, 2,"true");
		$("#fhModel").hide();
		
	});
	
	
	$("#aCheckYSH__1").click(function() {
				var pidsj = $("#yzInfo__1 [name=pidsj]").html();
				if (!pidsj) {
					return;
				}
				var freqCode = $("#yzInfo__1 [name=freqCode]").html();
				var drugname = $("#yzInfo__1 [name=drugname]").html();
				var yzzdshzt = $("#yzInfo__1 [name=yzzdshzt]").html();
				var yzzdshbtglx = $("#yzInfo__1 [name=yzzdshbtglx]").html();
				checkErr = "";
				if ("${SYN_YZ_DATA_MODE}" == "3") {
					if (checkErr == "") {// && yzlx==0
						var f = 0;
						var freqCode = freqCode.toUpperCase().split(' ')[0];
						if ($("#ruleKey [name=" + freqCode + "]").length > 0) {//如果 关键字规则 没有
							$("#ruleKey [name=" + freqCode + "]").each(
							function() {
								f = -1;
								if (drugname.indexOf($(this).html()) > -1) {
									f = 1;
								}
							});
						}
						if (f < 1) {
							if ($("#batchRule [name=" + freqCode + "]").length > 0) {//如果 没有一般规则对应的频次
								f = 2;
							} else {
								f = f - 1;
							}
						}
						if (f == -2) {
							checkErr = "频次[" + freqCode + "] 没有找到对应规则，无法确认";
						} else if (f < 1) {
							checkErr = "频次[" + freqCode + "] 没有对应的批次数据，无法确认";
						}

					}
					if (checkErr != "") {
						message({
							html : checkErr
						});
						return;
					}
				}
				if (yzzdshzt > 0) {
					var pidsjNSucc = (yzzdshzt == 1 ? pidsj : "");
					var pidsjNFail = (yzzdshzt == 2 ? pidsj : "");
					
					$.ajax({
						type : 'POST',
						url : '${pageContext.request.contextPath}/doctorAdvice/yshCfm',
						dataType : 'json',
						cache : false,
						data : {
							"pidsjNSucc" : pidsjNSucc,
							"pidsjNFail" : pidsjNFail
						},
						success : function(response) {
							if (response.code > 0) {
								$("#aCheckYSH__1").hide();
								if (yzzdshzt == 1) {
									$("#aCheckOK__1").css("background-color","#73AB66");
									$("#aCheckNO__1").css("background-color","#ebb800");
									$("#yzshbtglx__1").val("");
									$("#yzshbtglxTit__1").hide();
									$("#yzshbtgyy__1").val("");
									$("#pidsj_" + pidsj).html("通过");
								} else {
									$("#aCheckOK__1").css("background-color","#ebb800");
									$("#aCheckNO__1").css("background-color","#d45120");

									$("#yzshbtglx__1").val(yzzdshbtglx);
									$("#pidsj_" + pidsj).html("不通过");
								}
								$("#sfrq_" + pidsj).html(getCurrentDate("yyyy-MM-dd",null,0));
							} else {
								message({
									html : response.mess
								});
							}
							$(obj).remove();
						}
					});
				} else {
					message({
						html : "当前医嘱无法预审确认！"
					});
				}
			});

	allFields = $( [] ).add(yzshbtglx).add(yzshbtgyy);
	
	$("#divYZCheckMany").dialog({
		autoOpen: false,
		height: 370,
		width: 650,
		modal: true,
		resizable: false,
		buttons: {
			"<spring:message code='comm.mess8'/>": function() {
				
				var yzshbtglx = $("#yzshbtglx").val();
				if(yzshbtglx == null || yzshbtglx == ""){
					message({
						html : "请选择不通过原因"
					});
					return;
				}
                var yzshztArray = getDataTableSelectRowData($('#yzDataTable'), 'yzshzt');
				var flag = false;
				for(var i in yzshztArray){
					if(yzshztArray[i] == "1"){
						flag = true;
						break;
					}
				}
				
				if(flag){
					message({
						html: "<spring:message code='comm.mess13'/>",
						showCancel:true,
						confirm:function(){
							checkManyNO();
						}
			    	});
				}else{
					checkManyNO();
				}
				
			},
			"取消": function() {
				$("#divYZCheckMany").dialog('close');
				allFields.val( "" );
			}
			
		},
		close: function() {
			allFields.val( "" );
		}
	});
	
	$("#aCheckManyNO__1").click(function(){
		var pidsjN = getDataTableSelectRowData($('#yzDataTable'), 'pidsj');
		if (pidsjN && pidsjN.length < 1) {
			message({
				html : "请选择医嘱再审方"
			});
			return;
		}
		$("#divYZCheckMany").dialog('open');
	});
	
	$("#aCheckMany__1").click(function() {
		var repeatCheck = "Y";
        var pidsjN = getDataTableSelectRowData($('#yzDataTable'), 'pidsj');
		console.log(pidsjN);
		if (pidsjN && pidsjN.length < 1) {
			message({
				html : "请选择医嘱再审方"
			});
			return;
		}
        var yzshztArray = getDataTableSelectRowData($('#yzDataTable'), 'yzshzt');
		var flag = false;
		for(var i in yzshztArray){
			if(yzshztArray[i] == "1"){
				flag = true;
				break;
			}
		}
		
		if(flag){
			message({
				html: "<spring:message code='comm.mess14'/>",
				showCancel:true,
				confirmText: "<spring:message code='comm.mess1'/>",
				cancelText: "<spring:message code='comm.mess2'/>",
				confirm:function(){
					repeatCheck = "Y" ;
					checkMayYes(pidsjN,repeatCheck);
				},
				cancel:function(){
					repeatCheck = "N" ;
					checkMayYes(pidsjN,repeatCheck);
				}
	    	});
		}else{
			checkMayYes(pidsjN,repeatCheck);
		}
		
	});

	$("#aCheckYSHMany__1").bind("click",
			function() {
				var pidsjNSucc = "";
				var pidsjNFail = "";

                var yzztN = getDataTableSelectRowData($('#yzDataTable'), 'yzzt');//	医嘱状态
                var yzshztN = getDataTableSelectRowData($('#yzDataTable'), 'yzshzt');//	医嘱审核状态
                var yzzdshztN = getDataTableSelectRowData($('#yzDataTable'), 'yzzdshzt');//rucangOKNum	医嘱自动审核状态
                var pidsjN = getDataTableSelectRowData($('#yzDataTable'), 'pidsj');
				var pidsjS = "";
				if (pidsjN && pidsjN.length < 1) {
					message({
						html : "请选择医嘱再确认"
					});
					return;
				}
                var drugNameN = getDataTableSelectRowData($('#yzDataTable'), 'DRUGNAME');
                var freqCodeN = getDataTableSelectRowData($('#yzDataTable'), 'freqCode');
				if ("${SYN_YZ_DATA_MODE}" == "3") {
					var checkErr = "";
					for (var i = 0; i < pidsjN.length; i++) {
						if (yzztN[i] == 0 && yzshztN[i] == 0
								&& yzzdshztN[i] == 1) {
							if (pidsjNSucc == "") {
								pidsjNSucc = pidsjN[i];
							} else {
								pidsjNSucc = pidsjNSucc + "," + pidsjN[i];
							}
						} else if (yzztN[i] == 0 && yzshztN[i] == 0
								&& yzzdshztN[i] == 2) {
							if (pidsjNFail == "") {
								pidsjNFail = pidsjN[i];
							} else {
								pidsjNFail = pidsjNFail + "," + pidsjN[i];
							}
						} else {
							continue;
						}
						var f = 0;
						var freqCode = freqCodeN[i].toUpperCase();
						if ($("#ruleKey [name=" + freqCode + "]").length > 0) {//如果 关键字规则 没有
							$("#ruleKey [name=" + freqCode + "]").each(
							function() {
								f = -1;
								if (drugNameN[i].indexOf($(this).html()) > -1) {
									f = 1;
								}
							});
						}
						if (f < 1) {
							if ($("#batchRule [name=" + freqCode + "]").length > 0) {//如果 没有一般规则对应的频次
								f = 2;
							} else {
								f = f - 1;
							}
						}
						if (f == -2) {
							checkErr = "频次[" + freqCodeN[i] + "] 没有找到对应规则，无法确认";
							message({
								html : checkErr
							});
							return;
						} else if (f < 1) {
							checkErr = "频次[" + freqCodeN[i] + "] 没有对应的批次数据，无法确认";
							message({
								html : checkErr
							});
							return;
						}
					}
					if (pidsjNSucc != "" || pidsjNFail != "") {
						$.ajax({
							type : 'POST',
							url : '${pageContext.request.contextPath}/doctorAdvice/yshCfm',
							dataType : 'json',
							cache : false,
							data : {
								"pidsjNSucc" : pidsjNSucc,
								"pidsjNFail" : pidsjNFail
							},
							success : function(response) {
								message({
									html : response.mess
								});
								if (response.code > 0) {
									queryYZ();
								}
							},error : function(data){
								message({html:'<spring:message code="common.op.error"/>'});
							}
						});
					} else {
						checkErr = "您选择的医嘱无法进行预审确认";
						message({
							html : checkErr
						});
					}
				}
			});
	
	
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
	
	
});//init  end

//药品信息
function initYpDataTable(pidsj){
    ypDataTable = $('#ypDataTable').DataTable({
        "searching": false,
        "processing": true,
        "serverSide": true,
        "select": true,
        "ordering": true,
        "order": [],
        "paging": false,
        "language": {
            "url": "${pageContext.request.contextPath}/assets/datatables/cn.json"
        },
        "ajax": {
            "url": "${pageContext.request.contextPath}/doctorAdvice/initYP",
            "type": "post",
            "data": {
                'pidsj': pidsj
            },
            "dataSrc": function (data) {
                data.data = data.rawRecords;
                data.recordsFiltered = data.total;
                data.recordsTotal = data.total;
                return data.data;
            }
        },
        "columns": [
            {"data": "chargeCode", "bSortable": false},
            {"data": "medicamentsName", "bSortable": false},
            {"data": "specifications2", "bSortable": false},
            {"data": "dose", "bSortable": false},
            {"data": "quantity", "bSortable": false},
        ],
        columnDefs: [
            {
                targets:3,
                render: function(data, type, row) {
                    return data + row.doseUnit;
                }
            },
            {
                targets: 4,
                render: function (data, type, row) {
                    return data + row.medicamentsPackingUnit;
                }
            }
        ]
    });
}

//医嘱信息
function initYzDataTable() {
    yzDataTable = $('#yzDataTable').DataTable({
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
            "url": "${pageContext.request.contextPath}/doctorAdvice/qryPtPageByYZ",
            "type": "post",
            "data": function (d) {
                var params = [];
                if (paramAll) {
                    params = paramAll.concat();
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
            {"data": "", "bSortable": false},
            {"data": "yzshzt", "bSortable": false},
            {"data": "bedno", "bSortable": false},
            {"data": "patname", "bSortable": false},
            {"data": "parentNo", "bSortable": false},
            {"data": "sfrqS", "bSortable": false},
        ],
        columnDefs: [
            {
                targets:0,
                data: null,
                defaultContent:"<input type ='checkbox' name='pidsj'>",
            },
            {
                targets: 1,
                render: function (data, type, row) {
                    var recheckstate = row.recheckstate;
                    return "<label id='pidsj_"+row['pidsj']+"'>"
                        + (data == 0 ? "未审核": (data == 1 ? ( recheckstate == 1? "强制打包":"通过"):(recheckstate == 2? "拒绝":"不通过")))
                        + "</label>";
                }
            },
            {
                targets: 5,
                render: function (data, type, row) {
                    return "<label id='sfrq_"+row.pidsj+"'>"+ data + "</label>";
                }
            }
        ],
        "createdRow":function (row, data, idx) {
            $(row).on('click' , 'td:gt(0)', function(){
                $("#yzDataTable tbody tr").each(function() {
                    $(this).removeClass("selected");
                    $(this).children("td").css("background","");
                });
                $(row).addClass("selected");
                showYZ__1(data['pidsj']);
			});

            //不同流水号添加样式
            if (idx > 0) {
                if ( yzDataTable.row(idx - 1).data()['inhospno'] != data['inhospno'] ) {
                    $(row).find("td").css("border-top", "2px solid black");
                }
            } else {
                $(row).addClass("selected");
                showYZ__1(data['pidsj']);
			}
		},
        "fnDrawCallback": function(){
            $("#all_checked").prop("checked",false);
        }
    });
}

//datatable下checkbox实现全选功能
$("#all_checked").click(function(){
    $('[name=pidsj]:checkbox').prop('checked',this.checked).change();;//checked为true时为默认显示的状态
});

function qryYpInfo(pidsj){

	if (ypDataTable) {
	    ypDataTable.settings()[0].ajax.data = {'pidsj' : pidsj};
	    ypDataTable.ajax.reload();
	} else {
		initYpDataTable(pidsj);
	}
}


function lookDetail(){
	
	$("#xm").text($("#yzInfo__1  [name='patname']").html());
	$("#zyh").text(clickStr);
	$("#xb").text($("#yzInfo__1  [name='sex']").html());
	$("#zyrq").text();
	$("#nl").text($("#yzInfo__1  [name='age']").html());
	$("#blh").text();
	
	$("#editView-info").dialog("open");
	
}


function checkMayYes(pidsjN,repeatCheck){
	var pidsjS = "";
    var drugNameN = getDataTableSelectRowData($('#yzDataTable'), 'DRUGNAME');
    var freqCodeN = getDataTableSelectRowData($('#yzDataTable'), 'freqCode');
	
	if(pidsjN.length > 1000){
			message({
				html : "医嘱批量审核最大数为1000条"
			});
			return ;
	}
	
	if ("${SYN_YZ_DATA_MODE}" == "3") {
		var checkErr = "";
		for (var i = 0; i < pidsjN.length; i++) {
			if (pidsjS == "") {
				pidsjS = pidsjN[i];
			} else {
				pidsjS = pidsjS + "," + pidsjN[i];
			}
			if (checkErr == "") {// && yzlxN[i]==0
				var f = 0;
				var freqCode = freqCodeN[i].toUpperCase();
				if ($("#ruleKey [name=" + freqCode + "]").length > 0) {//如果 关键字规则 没有
					$("#ruleKey [name="+ freqCode + "]").each(
					function() {
						f = -1;
						if (drugNameN[i].indexOf($(this).html()) > -1) {
							f = 1;
						}
					});
				}
				if (f < 1) {
					if ($("#batchRule [name="+ freqCode + "]").length > 0) {//如果 没有一般规则对应的频次
						f = 2;
					} else {
						f = f - 1;
					}
				}
				if (f == -2) {
					checkErr = "频次[" + freqCodeN[i]+ "] 没有找到对应规则，无法审核通过";
				} else if (f < 1) {
					checkErr = "频次[" + freqCodeN[i]+ "] 没有对应的批次数据，无法审核通过";
				}
			}
		}
		if (checkErr != "") {
			message({
				html : checkErr
			});
			return;
		}
		
		var param = {
			"pidsjN" : pidsjS,
			"repeatCheck" : repeatCheck,
			"newYzshzt" : 1,
			"yzshbtglx" : "",
			"yzshbtgyy" : "",
			"yzParea" : "1",
			"checkType" : "yssf"
		};
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
			dataType : 'json',
			cache : false,
			data : param,
			success : function(response) {
				if (response.code == 0) {
					message({
						html : "审方成功！"
					});
					queryYZ();
				} else {
					message({
						html : response.msg
					});
				}
			},error : function(data){
				message({html:'<spring:message code="common.op.error"/>'});
			}
		});
	}
}

function checkManyNO(){
	
    var pidsjN = getDataTableSelectRowData($('#yzDataTable'), 'pidsj');
	var pidsjS = pidsjN.toString();
	
	if(pidsjN.length > 1000){
		message({
			html : "医嘱批量审核最大数为1000条"
		});
		return ;
	}
	
	var param = {
			"pidsjN" : pidsjS,
			"repeatCheck" : "Y",
			"newYzshzt" : 2,
			"yzshbtglx" : $("#yzshbtglx").val(),
			"yzshbtgyy" : $("#yzshbtgyy").val().trim(),
			"yzParea" : "1",
			"checkType" : "yssf"
	};
	 $.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
		dataType : 'json',
		cache : false,
		data : param,
		success : function(response) {
			
			$("#divYZCheckMany").dialog('close');
			allFields.val( "" );
			if (response.code == 0) {
				message({
					html : "审方成功！"
				});
				queryYZ();
			} else {
				message({
					html : response.msg
				});
			}
			
			
		},error : function(data){
			message({html:'<spring:message code="common.op.error"/>'});
		}
	}); 
	
}

function checkOne__1(pidsj, newYzshzt,fh) {
	var param = {
		"pidsjN" : pidsj,
		"repeatCheck" : "Y",
		"newYzshzt" : newYzshzt,
		"yzshbtglx" : $("#yzshbtglx__1").val(),
		"yzshbtgyy" : $("#yzshbtgyy__1").val(),
		"yzParea" : "1",
		"checkType" : "yssf",
		"fhCheck":fh
	};
	$.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
		dataType : 'json',
		cache : false,
		//showDialog: false,
		data : param,
		success : function(response) {
			if (response.code == 0) {
				if (newYzshzt == 1) {
					$("#aCheckOK__1").css("background-color", "#4caf50");
					$("#aCheckNO__1").css("background-color", "#f44336");
					$("#yzshbtglx__1").val("");
					$("#yzshbtglxTit__1").hide();
					$("#yzshbtgyy__1").val("");
					$("#pidsj_" + pidsj).html("通过");
				} else {
					$("#aCheckOK__1").css("background-color", "#4caf50");
					$("#aCheckNO__1").css("background-color", "#f44336");
					$("#pidsj_" + pidsj).html("不通过");
				}
				$("#sfrq_" + pidsj).html(getCurrentDate("yyyy-MM-dd", null, 0));
			} else {
				message({
					html : response.msg
				});
			}
			
			queryYZ();
		},error : function(data){
			message({html:'<spring:message code="common.op.error"/>'});
		}
	});
}

function condiQuery(param){
	paramTemp = param;
	queryYZ();
}

function queryYZ(){
	$("#parentnoId").html("");
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
		{name:"pageParams",value:"1"},
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

	//初始化右侧页面
	$("#yzInfo__1 .fontBold").each(function() {
		$(this).html("");
	});
	$("#yzInfo__1 [name=yzzt]").html("");
	$("#yzInfo__1 [name=medNames]").html("");
	$("#aCheckOK__1").css("background-color", "#ebb800");
	$("#aCheckNO__1").css("background-color", "#ebb800");

	/* $("#medicTab__1 .medicTd").remove(); */

    paramAll = params;
	if (yzDataTable) {
        yzDataTable.ajax.reload();
	} else {
	    initYzDataTable();
	}
}

function rowClick__1(r) {
		
	$(r).click(function() {
		$("#flexGrid__1 tr").each(function() {
			$(this).removeClass("trSelect");
			$(this).children("td:visible").css("background","");
		});
		$(r).addClass("trSelect");
		$(r).children("td:visible").css("background","#FFEECE");
		//获取该行的所有列数据
		var columnsArray = $(r).attr('ch').split("_FG$SP_");
		
		clickStr = "";
		showYZ__1(columnsArray[2]);
	});
	
	$(r).find("td.chboxtd").find("input").click(function(event){
		event.stopPropagation();
	});
}

function showYZ__1(pidsj) {
	if (pidsj && pidsj != "") {
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/dctrAdvcInfo',
			dataType : 'json',
			cache : false,
			showDialog : false,
			data : {"pidsj" : pidsj},
			success : function(result) {
				if (result && result.code > 0) {
					
					qryYpInfo(pidsj);
					
					var yzMain = result.data.yzMain;
					var parentNo = yzMain['parentNo'];
					$("#parentnoId").html(parentNo);
					
					var recheckcause = yzMain['recheckcause'];
			    	var recheckstate = yzMain['recheckstate'];
			    	
			    	var yzshzt = yzMain['yzshzt'];
			    	$("#fhModel").hide();
			    	if("0" == recheckstate && "2" == yzshzt){
			    		$("#fhModel").show();
			    		if(recheckcause != null){
			    			$("#bqfh").val(recheckcause.trim());
			    		}
			    	}
					
					$("#yzInfo__1 .fontBold").each(
					function() {
						if ($(this).attr("name") == "sex") {
							$(this).html(yzMain[$(this).attr("name")] ? getDicValue("sex",yzMain[$(this).attr("name")])
							: 
							(yzMain[$(this).attr("name")] === 0 ? getDicValue("sex",yzMain[$(this).attr("name")]): ""));
						} else if ($(this).attr("name") == "age") {
							var age = yzMain[$(this).attr("name")]|| '';
							if (age) {
								if (age != 0) {
									$(this).html(age+ getDicValue("ageUnit",yzMain["ageunit"]));
								}
							}
						} else {
							$(this).html(yzMain[$(this).attr("name")] ? yzMain[$(this).attr("name")]: "");
						}

					});
					
					clickStr = yzMain['inpatientNo'];
					$("#yzInfo__1  [name='zdxx']").html('无 &nbsp;<a class="button ui-btn-bg-green" style="text-decoration:none;" href="#" onclick="javascript:lookDetail()" >详情</a>');

					
					$("#yzInfo__1 [name=yzzt]").html(getDicValue('yzzt', yzMain['yzzt']));
					$("#yzInfo__1 [name=medNames]").html(result.data.medNames ? result.data.medNames: "");

					if (yzMain.yzzdshzt == 1) {
						$("#labYSHzt__1").html("预审通过").attr("title", "");
						if (yzMain.yzzt == 0 && yzMain.yzshzt == 0) {
							$("#aCheckYSH__1").show();
						} else {
							$("#aCheckYSH__1").hide();
						}
					} else if (yzMain.yzzdshzt == 2) {
						$("#labYSHzt__1").html("预审不通过").attr("title", yzMain.yzzdshbtglxS || '');
						if (yzMain.yzzt == 0 && yzMain.yzshzt == 0) {
							$("#aCheckYSH__1").show();
						} else {
							$("#aCheckYSH__1").hide();
						}
					} else {
						$("#labYSHzt__1").html("").attr("title",yzMain.yzzdshbtglxS || '');
						$("#aCheckYSH__1").hide();
					}

					if (yzMain.yzshzt == 1) {
						$("#aCheckOK__1").css("background-color","#73AB66");
						$("#aCheckNO__1").css("background-color","#ebb800");
						$("#yzshbtglxTit__1").hide();
						$("#yzshbtglx__1").val("");
						$("#yzshbtgyy__1").val("");
					} else if (yzMain.yzshzt == 2) {
						$("#aCheckOK__1").css("background-color","#ebb800");
						$("#aCheckNO__1").css("background-color","#d45120");

						$("#yzshbtglx__1").val(yzMain.yzshbtglx);
						$("#yzshbtgyy__1").val(yzMain.yzshbtgyy);
					} else if (yzMain.yzshzt == 0) {
						$("#aCheckOK__1").css("background-color","#ebb800");
						$("#aCheckNO__1").css("background-color","#ebb800");

						$("#yzshbtglx__1").val("");
						$("#yzshbtgyy__1").val("");
					}
				} else {
					$("#yzInfo__1 .fontBold").each(function() {
						$(this).html("");
					});
				}
			},error : function(data){
				message({html:'<spring:message code="common.op.error"/>'});
			}
		});
	} 
}

//取datatable 选中行数据
function getDataTableSelectRowData(dom, key){
    var ary = new Array();
    $(dom).find('tbody input[type="checkbox"]:checked').each(function(){
        var v = yzDataTable.row($(this).parents('tr')).data()[key];
        if (v) {
            ary.push(v);
        }
    });
    return ary;
}
</script>
</html>