<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp" %>

<html>
<head>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.multiSelect.js"></script>
<link href="${pageContext.request.contextPath}/assets/pivas/css/edit.css" type="text/css" rel="stylesheet" />
<style type="text/css">

.button:hover{
 	color:blue;
}
.button2:hover{
 	color:blue; 
}
.x-toolbar-default{
background-image:-webkit-linear-gradient(top,#B5B5B5,#B5B5B5);
}
.x-toolbar .x-toolbar-separator-horizontal{
    border-left: 1px solid #ACA899;
    border-right: 1px solid #FFF;
}
.x-column-header-over, .x-column-header-sort-ASC, .x-column-header-sort-DESC{
background-image:-webkit-linear-gradient(top,#B5B5B5,#B5B5B5 39%,#B5B5B5 40%,#B5B5B5);
}
.x-column-header{
background-image:-webkit-linear-gradient(top,#B5B5B5,#B5B5B5);
}
.x-panel .x-grid-body{
	border-color:#F5F5F5;
	font-family: "宋体",Arial, sans-serif;
}

.x-grid-header-ct{
    border: 0px solid white;
}
.oe_searchview_search:before{
margin-left: 425px;
}
.oe_searchview .oe_searchview_clear{
  left: 755px;
}

.x-grid-table{
width:100%;
font-family: "宋体",Arial, sans-serif;
}
.x-column-header{
width:100%;
}
.x-grid-row .x-grid-cell{
}
.x-grid-row{
height:26px;
background-color: transparent;
}
.x-grid-td{
font-family: "宋体",Arial, sans-serif;
}
.x-grid-row-over {
    background: transparent;
    background-color: transparent;
}
tr:nth-child(2n)
{
    background: #EDEDED;
}

.x-grid-header-ct{
	background-image:url("");
}

.cbit-grid div.bDiv table tr {
  border-bottom: 1px solid rgb(236, 219, 219);
  font-size: 15px;
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
    margin-top: 8px;
    margin-left: 15px;
}
.search-div{
  padding: 0em 0em 0em 0em;
}
.divselect{
color:#747474;
}
.cbit-grid div.bDiv td div {
	  cursor: pointer;
}
</style>
</head>

<body>
<div id="yzMain" class="main-div" style="width:100%;height: 100%;">
<!-- 搜索条件--开始 -->
	<div class="search_div" style="height:40px;overflow: hidden">
		<div style="height: 32px;float: right;margin-top: 7px;z-index: 999;">
			<table data-qryMethod funname="qryList">
				<tr>
					<td>过滤器：</td>
					<td>
						<select id="filter_1" name="filter_1[]" multiple="multiple" size="5" style="width:67px;height: 26px;margin-top: -2px;">
							<!-- <option value="before12">12点前</option>
                            <option value="after12">12点后</option> -->
							<option value="running">执行中</option>
							<option value="stoping">停止</option>
							<option value="todayNew">新医嘱</option>
							<!-- <option value="yesterdayStop">昨天停止</option>
                            <option value="todayStop">今天停止</option> -->
							<option value="unCheck">未审核</option>
							<!-- <option value="hasYSH">已预审待确认</option> -->
							<option value="checkOK">审方通过</option>
							<option value="checkNO">审方不通过</option>
						</select>
					</td>
					<td style="padding-top:0;">&nbsp;&nbsp;医嘱类型：</td>
					<td>
						<%--<span style="margin-left: 10px;"><input type="radio" name="yzlx" style="vertical-align: middle" />长嘱</span>--%>
						<%--<span style="margin-left: 10px;"><input type="radio" name="yzlx" style="vertical-align: middle"  />临嘱</span>--%>
						<select id="yzlx" style="height: 25px;width:80px;margin-top: -2px;" class="select_new">
							<option value="">全部</option>
							<option value="0" selected="selected">长嘱</option>
							<option value="1">临嘱</option>
						</select>
					</td>

					<td>&nbsp;&nbsp;&nbsp;&nbsp;<input placeholder="<spring:message code='pivas.yz2.bedno'/>" name="bednoS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz2.patname'/>" name="patnameS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz1.parentNo'/>" name="parentNoS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz1.drugname'/>" name="drugnameQry" size="8" data-cnd="true"></td>
					<td>&nbsp;&nbsp;<button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button></td>
					<td>&nbsp;&nbsp;<button class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button></td>
				</tr>
			</table>
		</div>
		<div id="qryView-div" style="margin-top:10px;float: left">
			<div class="search-div">
				<div class="oe_view_manager_view_search"></div>

				<%-- <shiro:hasPermission name="PIVAS_BTN_505"> <spring:message code='pivas_yz1.syn'/>--%>
				<a class="ui-search-btn ui-btn-bg-green" id="synDataDicBtn" style=""><i
						class="am-icon-refresh"></i><span>手动同步医嘱</span></a>
				<%-- </shiro:hasPermission> --%>
				<ul id="ulYZCheckMany" class="pre-more" tabindex='-1'
					style="display: none;  margin-top: -21px;margin-left: 415px;">

					<li class="liBtH">
						<a class="button2"><spring:message code='comm.mess17'/></a>
					</li>
					<shiro:hasPermission name="PIVAS_BTN_504">
						<li class="liBtN" style="display: none;">
							<a class="button2" id="aYZCheckMany"><spring:message code='pivas_yz1.shenfangMore'/></a>
						</li>
					</shiro:hasPermission>
					<!-- <li class="liBtN" style="display: none;">
                       <a class="button2" id="aYZYSHCheck">预审确认</a>
                    </li> -->
				</ul>

				<ul id="batchRule" style="visibility: hidden;height: 0px;">
					<c:forEach items="${batchRuleList}" var="batchRule">
						<c:if test="${empty batchRule.ru_key}">
							<li name="${batchRule.pinc_code}" style="height: 0px;">${batchRule.pinc_name}</li>
						</c:if>
					</c:forEach>
				</ul>

				<ul id="ruleKey" style="visibility: hidden;height: 0px;">
					<c:forEach items="${ruleList}" var="rule">
						<c:if test="${!empty rule.ru_key}">
							<li name="${rule.pinc_code}" style="height: 0px;">${rule.ru_key}</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>

        <div id="flexDiv" class="tbl" style="margin-top: 15px;" >
				<table id="datatable" class="table datatable ui-data-table display dataTable no-footer">
					<thead>
					<tr>
						<th><input type="checkbox" id="all_checked"></th>
						<th>床号</th>
						<th>病区</th>
						<th>病人</th>
						<th>年龄</th>
						<th>频次</th>
						<th>药品名称</th>
						<th>单次剂量</th>
						<th>剂量单位</th>
						<th>药品数量</th>
						<th>单位</th>
						<th>医嘱类型</th>
						<th>医嘱状态</th>
						<th>审方药师</th>
						<th>审方时期</th>
						<th>审方结果</th>
						<th>审方问题</th>
					</tr>
					</thead>
				</table>
        </div>
       
       	<div id="treeDiv" style="display:none;" ></div>
</div>

<div id="yzInfo" class="main-div" style="width:100%;dispaly:none">
</div>

<%-- 新增编辑弹出框 --%>
<div id="divYZCheckMany" title="<spring:message code='pivas_yz1.shenfangMore'/>" align="center" style="display: none;">
	<form id="editView-form" action="" method="post">
		<input type="hidden" id="gid" name="gid"/>
			<div class="popup" style="padding-top: 24px;">
				<div class="row">
					<div class="column">
						<label class="tit2"><spring:message code='user.zhanghao'/></label>
						<input type="text" class="edit" name="checkAccount" id="checkAccount" value="<spring:escapeBody htmlEscape="true"><shiro:principal property="account"/></spring:escapeBody>" maxlength="32" style="height: 28px;" title="<spring:message code='common.op.remind2'/>"/>
		            	<span class="mand">*</span>
					</div>
					<div class="column">
						<label class="tit2"><spring:message code='user.mima'/></label>
						<input type="password" class="edit" name="checkPass" id="checkPass" maxlength="32" style="height: 28px;" title="<spring:message code='common.op.remind2'/>"/>
		            	<span class="mand">*</span>
					</div>
				</div>
				
				<div class="row">
					<div class="column">
						<label class="tit2"><spring:message code='comm.mess18'/></label>
						<select name="yzshbtglx" id="yzshbtglx" style="width: 183px;  height: 28px;">
							<option value=""><spring:message code='comm.mess19'/></option>
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
<script type="text/javascript">

	var checkIdS = "";
	var repeatCheck = "N";
	var partHasCheck = 0 ;
	var paramTemp ;
	var paramTemp2 ;
	var checkedIDMap = {} ;
	var checkErr = "" ;
	var titleStrs = "${titleStrs}";
	var titleArray = titleStrs.split(",");
	var datatable;
    var paramAll;
	$(function() {
		$("#yzlx").bind("change",function(){
			qry();
		});
		var init_filter_1 = ['running'];
		paramTemp2 = 'running' ;
		$("#filter_1").val(init_filter_1);
		$("#filter_1").multiSelect({ "selectAll": false,"noneSelected": "无","oneOrMoreSelected":"*" },function(){
			paramTemp2 = $("#filter_1").selectedValuesString();
			qry();
		});

		qry();

        //datatable下checkbox实现全选功能
        $("#all_checked").click(function(){
            $('[name=pidsj]:checkbox').prop('checked',this.checked).change();;//checked为true时为默认显示的状态
        });

		$("#aSearch").bind("click",function(){
			qry();
		});

		$("#aYZCheckMany").bind("click",function(){
			var row_partHasStop = 0 ;//部分数据 源端数据已停止，提示 部分医嘱已停止//0：执行 1：停止 2：撤销
			var row_partHasRuCang = 0 ;//部分数据 已进入入仓扫描阶段，不可审核
			var row_canCheck = 0 ;
			checkIdS = "";
			partHasCheck = 0 ;//部分数据 已审核，提示是否重新审核//0：未审核 1：审核通过 2：审核不通过
			checkErr = "" ;
			
			if($("#flexDiv").css("display")=="block"){
                var pidsjN = getDataTableSelectRowData($('.datatable'), 'pidsj');
				if(pidsjN && pidsjN.length <1) {
					message({html: "<spring:message code='comm.err.param3'/>"});
					return;
				}
                var yzshztN = getDataTableSelectRowData($('.datatable'), 'yzshzt');
                var rucangOKNumN = getDataTableSelectRowData($('.datatable'), 'rucangOKNum');
                var freqCodeN = getDataTableSelectRowData($('.datatable'), 'freqCode');
                var drugNameN = getDataTableSelectRowData($('.datatable'), 'drugname');
				for(var i=0;i<pidsjN.length;i++){
					//yzztN[i] 
					if(0==0){
						if(rucangOKNumN[i]==0){
							row_canCheck ++ ;
							if(checkIdS==""){
								checkIdS = pidsjN[i];
							}else{
								checkIdS = checkIdS + ","+pidsjN[i];
							}
							if(yzshztN[i]==1){
								partHasCheck ++ ;
							}
						}else{
							row_partHasRuCang ++ ;
						}
					}else{
						row_partHasStop ++ ;
					}
					if("${SYN_YZ_DATA_MODE}"=="3"){
						if(checkErr==""){
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
				}
			}else{
				checkIdS = "";
				
				var row = null ;
				partHasCheck = 0 ;//部分数据 已审核，提示是否重新审核//0：未审核 1：审核通过 2：审核不通过
				for(var key in checkedIDMap){
					row = checkedIDMap[key];
					if(row && row!=undefined){
						if(row.yzzt==0){
							if(row.rucangOKNum==0){
								row_canCheck ++ ;
								if(checkIdS==""){
									checkIdS = row.pidsj;
								}else{
									checkIdS = checkIdS + ","+row.pidsj;
								}
								if(row.yzshzt==1){
									partHasCheck ++ ;
								}
							}else{
								row_partHasRuCang ++ ;
							}
						}else{
							row_partHasStop ++ ;
						}
						if("${SYN_YZ_DATA_MODE}"=="3"){
							if(checkErr==""){
								var f = 0 ;
								var freqCode = row.freqCode.toUpperCase();
								if($("#ruleKey [name="+freqCode+"]").length>0){//如果 关键字规则 没有
									$("#ruleKey [name="+freqCode+"]").each(function(){
										f = -1 ;
										if( row.drugname.indexOf($(this).html())>-1 ){
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
									checkErr = "频次["+row.freqCode+"] 没有找到对应规则，无法审核通过";
								}else if(f<1){
									checkErr = "频次["+row.freqCode+"] 没有对应的批次数据，无法审核通过";
								}
							}
							
						}
						
					}
				}
			}
			
			if(row_canCheck>0){
				if(checkErr!=""){
					message({
		    			html: checkErr,
		    			showCancel:false,
		    			confirm:function(){
		    				gotoCheckView();
		    			}
		        	});
					return ;
				}
				if(row_partHasStop>0){
					var mess = (row_partHasStop>0?"[<spring:message code='comm.mess3'/>]":"") + (row_partHasRuCang>0?"[<spring:message code='comm.mess4'/>]":"") ;
					message({
		    			html: mess+'，将不会被审核！',
		    			showCancel:false,
		    			confirm:function(){
		    				gotoCheckView();
		    			}
		        	});
				}else{
					gotoCheckView();
				}
			}else{
				
				message({html: "<spring:message code='comm.mess11'/>"});
			}
		});
		$("#aYZYSHCheck").bind("click",function(){
			var pidsjNSucc = "";
			var pidsjNFail = "";
			var checkErr = "" ;
			if($("#flexDiv").css("display")=="block"){
				var pidsjN = getFlexiGridSelectedRowText($("#flexGrid"), 8);//parentNo 医嘱父ID
				if(pidsjN && pidsjN.length <1) {
					message({html: "<spring:message code='comm.err.param3'/>"});
					return;
				}
				var yzlxN = getFlexiGridSelectedRowText($("#flexGrid"), 4);//yzlxN	医嘱类型 0长嘱 1临嘱
				var yzztN = getFlexiGridSelectedRowText($("#flexGrid"), 5);//yzztN	医嘱状态
				var yzshztN = getFlexiGridSelectedRowText($("#flexGrid"), 6);//yzshztN	医嘱审核状态
				//var rucangOKNumN = getFlexiGridSelectedRowText($("#flexGrid"), 7);//rucangOKNum	医嘱审核状态
				var freqCodeN = getFlexiGridSelectedRowText($("#flexGrid"), 12);//
				var drugNameN = getFlexiGridSelectedRowText($("#flexGrid"), 13);//
				var yzzdshztN = getFlexiGridSelectedRowText($("#flexGrid"), 18);//
				
				for(var i=0;i<pidsjN.length;i++){
					if(yzztN[i]==0 && yzshztN[i]==0){
						if(yzzdshztN[i]=="预审通过"){
							if(pidsjNSucc==""){
								pidsjNSucc = pidsjN[i];
							}else{
								pidsjNSucc = pidsjNSucc + "," + pidsjN[i];
							}
						}else if(yzzdshztN[i]=="预审不通过"){
							if(pidsjNFail==""){
								pidsjNFail = pidsjN[i];
							}else{
								pidsjNFail = pidsjNFail + "," + pidsjN[i];
							}
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
				}
			}
			if(pidsjNSucc!="" || pidsjNFail!=""){
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/doctorAdvice/yshCfm',
					dataType : 'json',
					cache : false,
					data : {"pidsjNSucc":pidsjNSucc,"pidsjNFail":pidsjNFail},
					success : function(response) {
						message({html: response.mess});
						if(response.code>0){
							qry();
						}
					}
				});
			}else{
				checkErr = "您选择的医嘱无法进行预审确认";
				message({html: checkErr});
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
		
		/* //新增按钮
		$( "#addDataDicRoleBtn").bind("click",function(){
			$("#divYZCheckMany").dialog("open");
		}); */
		
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
							qry();
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

        //页面初始化END
	});

    function getDataTableSelectRowData(dom, key){
        var ary = [];
        $(dom).find('tbody input[type="checkbox"]:checked').each(function(){
            var v = datatable.row($(this).parents('tr')).data()[key];
			if (v) {
                ary.push(v);
			}
        });
        return ary;
    }

    //组装table请求参数
    function asmParams(){
        var params = [];
        if(paramTemp){
            params = paramTemp.concat();
        }
        params.push({"name":"rucangOKNum","value":"Y"});
        params.push({"name":"ydztLowN","value":5});
        //params.push({"name":"yzztLowN","value":1});
        params.push({"name":"areaEnabled","value":1});
        params.push({"name":"filter","value":paramTemp2});
        params.push({"name":"yzlx","value":$("#yzlx").val()});

        if($("#yzlx").val()== "1"){
            params.push({"name":"yzResourceUpEQN","value":2});//医嘱来源 >=2 复制化疗 或 临时医嘱化疗
        }else if($("#yzlx").val()== "0"){
            params.push({"name":"yzResource","value":1});//医嘱来源 =1  长嘱 非临嘱
        }else{
            params.push({"name":"yzResourceUpEQN","value":0});
        }

        var inpatientString = function(){
            return window.parent.getInpatientInfo();
        }
        params.push({"name":"inpatientString","value":inpatientString});

        paramAll = params;
        return params;
	}

    function initDatatTable() {
        datatable = $('.datatable').DataTable({
            "dom": '<"toolbar">frtip',
            "searching": false,
            "processing": true,
            "serverSide": true,
            "select": true,
            "ordering": true,
            "order": [],
            "pageLength": 20,
            //"scrollX": true,
            "language": {
                "url": "${pageContext.request.contextPath}/assets/datatables/cn.json"
            },
            "ajax": {
                "url": "${pageContext.request.contextPath}/doctorAdvice/yzPageData",
                "type": "post",
                "data": function (d) {
                    var params = [];
                    if (paramAll) {
                        params = paramAll.concat();
                    }
                    // params.push({"name": "areaS", "value": areaStr});
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
                {"data": "bedno", "bSortable": false},
                {"data": "wardName", "bSortable": false},
                {"data": "patname", "bSortable": false},
                {"data": "age", "bSortable": false},
                {"data": "freqCode", "bSortable": false},
                {"data": "drugname", "bSortable": false, 'with':'180px'},
                {"data": "dose", "bSortable": false},
                {"data": "doseUnit", "bSortable": false},
                {"data": "quantity", "bSortable": false},
                {"data": "medicamentsPackingUnit", "bSortable": false},
                {"data": "yzlx", "bSortable": false},
                {"data": "yzzt", "bSortable": false},
                {"data": "sfysmc", "bSortable": false},
                {"data": "sfrqS", "bSortable": false},
                {"data": "yzshzt", "bSortable": false},
                {"data": "yzshbtglxS", "bSortable": false}
            ],
            columnDefs: [
                {
                    targets:0,
                    data: null,
                    defaultContent:"<input type ='checkbox' name='pidsj'>",
                },
                {
                    targets:4,
                    render: function (data, type, row) {
                        return row.age+ getDicValue('ageUnit',row.ageunit);
                    }
                },
                {
                    targets: 6,
                    render: function (data) {
                        return data.replace(new RegExp("@@","g"),"<br>");
                    }
                },
                {
                    targets: 7,
                    render: function (data) {
                        return data.replace(new RegExp("@@","g"),"<br>");
                    }
                },
                {
                    targets: 8,
                    render: function (data) {
                        return data.replace(new RegExp("@@","g"),"<br>");
                    }
                },
                {
                    targets: 9,
                    render: function (data) {
                        return data.replace(new RegExp("@@","g"),"<br>");
                    }
                },
                {
                    targets: 10,
                    render: function (data) {
                        return data.replace(new RegExp("@@","g"),"<br>");
                    }
                },
                {
                    targets: 11,
                    render: function (data) {
                        return getDicValue('yzlx',data);
                    }
                },
                {
                    targets: 12,
                    render: function (data) {
                        return data=="0"?getDicValue('yzzt',data):"<font color='red'>"+getDicValue('yzzt',data)+"</font>" ;
                    }
                },
                {
                    targets: 15,
                    render: function (data, type, row) {
                        var reCheckState = row.recheckstate;
                        if(data == "1" && reCheckState == "1"){
                            return "强制打包";
                        }else if(data == "2" && reCheckState == "2"){
                            return "拒绝";
                        }else{
                            return getDicValue('yzshzt',data);
                        }
                    }
                },
                {
                    targets: 16,
                    render: function (data, type, row) {
                        return row.yzshzt==1?"":data;
                    }
                }
            ],
            "createdRow":function (row, data, idx) {
                $(row).on('dblclick' , 'td:gt(0)', function(){
                    goToInfo(data.pidsj);
                });
                $(row).on('change', 'td:eq(0) > input[type="checkbox"]', function(){
                    if ( $(this).parents('tbody').find('input[type="checkbox"]:checked').size() > 0 ) {
                        $("#ulYZCheckMany").show();
                    } else {
                        $("#ulYZCheckMany").hide();
                    }
                });

                var startDayDelNum = data['startDayDelNum'];
                var startTimeS = data['startTimeS'];
                var hour = startTimeS.substring(startTimeS.indexOf(" ")+1,startTimeS.indexOf(":"));
                var yzzt = data['yzzt'];
                var yzshzt = data['yzshzt'];
                var yzshbtglxColor =data['yzshbtglxColor'];
                if(startDayDelNum>=0 && hour>=12){
                    $(row).find("td").css("color", "red");
                }else if(startDayDelNum>=0 && hour>=11){
                    $(row).find("td").css("color", "blue");
                }else if(yzzt=="0" && yzshzt=="1"){
                    $(row).find("td").css("color", "#009f0f");//green
                }else{
                    $(row).find("td").css("color", "black");
                }
                if(yzshzt=="2"){
                    $(row).find("td").css("color", yzshbtglxColor);
                }

                /*if(yzshzt=="0"){
                    if(hour<11){
                        $(row).find("td").css("color", "black");
                    }else{
                        $(row).find("td").css("color", "red");
                    }
                }else if(yzshzt=="1"){
                    if(hour<11){
                        $(row).find("td").css("color", "green");
                    }else{
                        $(row).find("td").css("color", "blue");
                    }
                }else if(yzshzt=="2"){
                    $(row).find("td").css("color", "black");
                }*/

                //不同病人进行黑线分割

                if (idx > 0) {
                    if ( datatable.row(idx - 1).data()['patname'] != data['patname'] ) {
                        $(row).find("td").css("border-top", "2px solid black");
                    }
                }

                //表格灰白间隔
                if(idx % 2 == 0){
                    $(this).find("td").css("background-color", "#efeff7");
                }else{
                    $(this).find("td").css("background-color", "white");
                }
            },
            "fnDrawCallback": function(){
                $("#all_checked").prop("checked",false);
            }
        });
    }

	function qry(){
		asmParams();

		if (datatable) {
            datatable.ajax.reload();
		} else {
            initDatatTable();
		}

	}

	function qryList(param){
		paramTemp = param ;
		qry();
	}
	function backMain(){
		$("#yzInfo").hide().html("");
		$("#yzMain").show();
	}
	
	function gotoCheckView(){
		$("#checkPass").val("");
		$("#yzshbtglx").val("");
		$("#yzshbtgyy").val("");
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
		if(newYzshzt==2){
			if($("#yzshbtglx").val() && $("#yzshbtglx").val()!=""){
				
			}else{
				message({html: "<spring:message code='comm.mess12'/>"});
				return ;
			}
		}else{
			if(checkErr!=""){
				message({html: checkErr});
				return ;
			}
		}
		if(newYzshzt==1){
			if(partHasCheck>0){
				message({
					html: "<spring:message code='comm.mess14'/>",
					showCancel:true,
					confirmText: "<spring:message code='comm.mess1'/>",
					cancelText: "<spring:message code='comm.mess2'/>",
					confirm:function(){
						repeatCheck = "Y" ;
						checkSubmitData(newYzshzt);
					},
					cancel:function(){
						repeatCheck = "N" ;
						checkSubmitData(newYzshzt);
					}
		    	});
			}else{
				repeatCheck = "N" ;
				checkSubmitData(newYzshzt);
			}
		}else{
			if(partHasCheck>0){
				message({
					html: "<spring:message code='comm.mess13'/>",
					showCancel:true,
					confirm:function(){
						repeatCheck = "N" ;
						checkSubmitData(newYzshzt);
					}
		    	});
			}else{
				repeatCheck = "N" ;
				checkSubmitData(newYzshzt);
			}
		}
	}
	function checkSubmitData(newYzshzt){
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/checkMany',
			dataType : 'json',
			cache : false,
			data : {"pidsjN":checkIdS,
				"repeatCheck":repeatCheck,
				"newYzshzt":newYzshzt,
				"yzshbtglx":$("#yzshbtglx").val(),
				"yzshbtgyy":$("#yzshbtgyy").val(),
				"checkAccount":$("#checkAccount").val(),
				"checkPass":$("#checkPass").val(),
				"checkType":"yz"
			},
			success : function(response) {
				message({html: response.msg});
				if(response.code==0){
					$("#divYZCheckMany").dialog("close");
					//message({html: "审方已置为不通过"});
					$("#ulYZCheckMany").hide();
					qry();
					backMain();
				}else{
					message({html: response.msg});
				}
				
			}
		});
	}
	function goToInfo(pidsj){
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/doctorAdvice/toYzInfo',
			dataType : 'html',
			cache : false,
			data : {"pidsj":pidsj},
			success : function(data) {
				$("#yzInfo").html(data).show();
				$("#yzMain").hide();
			}
		});
	}
	
	</script>
</html>