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
	
	<link href="${pageContext.request.contextPath}/assets/pivas/css/edit.css" type="text/css" rel="stylesheet" />
	<style type="text/css">
		.oe_searchview .oe_searchview_search{
			  margin-top: 36px;
		}

		#rightContaint table td {
			text-align:left;
			height:40px;
			color:#6F6F6F;
		}
		
		#rightContaint table th{
			padding:8px 0px;
			text-align:center;
			font-size:14px;
		}
		
		#rightContaint table tr{
			border:1px solid #A3A3A3;
		}
		
		#rightBatchContaint table td {
			text-align:left;
			height:40px;
			color:#6F6F6F;
		}
		
		#rightBatchContaint table th{
			padding:8px 0px;
			text-align:center;
		}
		
		#rightBatchContaint table tr{
			border:1px solid #A3A3A3;
		}
		
		.shuyeliang{
			border-right:solid #322A2D 1px;
		}
		
		.span_one{
			font-size:13px;
			color:#000;
		}
		
		.span_two{
			color:#797979;
		}
		
		.patientInfo{
			height:40px;
			line-height: 40px;
		}
		
		.cbit-grid div.bDiv td div {
    		font-size: 13px;
		}
	</style>
</head>
<body>
	<div>
		<div id="searchTitlePanel" class="search_div ui-search-header">
			<table funname="qry__1" data-qryMethod>
				<tr style="height:40px;">
					<td><input placeholder="<spring:message code='pivas.yz2.bedno'/>" name="bednoS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz2.patname'/>" name="patnameS" size="8" data-cnd="true"></td>
					<td><input placeholder="<spring:message code='pivas.yz1.parentNo'/>" name="parentNoS" size="8" data-cnd="true"></td>
					<td></td>
					<td><input   placeholder="<spring:message code='pivas.yz1.freqCode'/>" name="freqCodeS" size="8" data-cnd="true"></td>
					<td></td>
					<td><input placeholder="<spring:message code='pivas.yz1.drugname'/>" name="drugnameQry" size="8" data-cnd="true"></td>
					<td style="width:65px;text-align:right;">用药日期:</td>
					<td style="width:100px">
						<input type="text" id="yyrq" style="color: #555555;height:26px;width: 100px;" class="Wdate" empty="false" readonly="readonly" onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',onpicked:dataPick});">
					</td>
					<td style="width:65px;text-align:right;">确认状态:</td>
					<td style="width:80px">
						<select id="confirmStatus" class="select_new"  style="width:80px;height: 26px;">
							<option value="">--请选择--</option>
							<option value="0" selected>未确认</option>
							<option value="1">已确认</option>
						</select>
					</td>
					<td style="width:65px;text-align:right;">药品分类:</td>
					<td style="width:110px">
						<select id="medicamentType" style="width:80px;height: 26px;" multiple="multiple">
							<option value="">--请选择--</option>
							<c:forEach items="${medicamentTypeList}" var="medicamentType" >
								<option value="${medicamentType.categoryId}">${medicamentType.categoryName}</option>
							</c:forEach>
						</select>
					</td>
					<td id="batchIdTr" style="width:35px;text-align:right;">批次:</td>
					<td style="width:110px">
						<select id="batchId" style="width:80px;height: 26px;" multiple="multiple">
							<option value="">--请选择--</option>
							<c:forEach items="${batchList}" var="batch" >
								<option value="${batch.id}">${batch.name}</option>
							</c:forEach>
						</select>
					</td>
					<td><button class="ui-search-btn ui-btn-bg-green" onclick="qryByCnd()"><i class="am-icon-search"></i><span>搜索</span></button></td>
					<td><button class="ui-search-btn ui-btn-bg-yellow" onclick="cndRest()"><i class="am-icon-trash"></i><span>清空</span></button></td>
					<shiro:hasPermission name="PIVAS_BTN_519">
						<td name="bigScreen" style="display:none">
							<a class="ui-search-btn ui-btn-bg-green" id="autoCheckRun" style="" ><i class="am-icon-refresh"></i><span>1、同步刷新</span></a>
						</td>
					</shiro:hasPermission>
					<shiro:hasPermission name="PIVAS_BTN_518">
						<td name="bigScreen" style="display:none">
							<a class="ui-search-btn ui-btn-bg-green" id="aRuleRun__1"><i class="am-icon-retweet"></i><span>2、优化</span></a>
						</td>
					</shiro:hasPermission>
				</tr>
			</table>
			<table id="smallScreen" style="width:100%;display:none;margin-bottom: 5px;">
				<tr style="float:left;margin: 0px;padding:0px;">
					<shiro:hasPermission name="PIVAS_BTN_519">
					<td>
						<a class="button" id="autoCheckRun_2" style="margin-left: -5px;" ><i class="am-icon-refresh"></i><span>1、同步刷新</span></a>
					</td>
					</shiro:hasPermission>
					<shiro:hasPermission name="PIVAS_BTN_518">
					<td>
						<a class="button" id="aRuleRun__2" style="margin-left:10px ;">2、优化</a>
					</td>
					</shiro:hasPermission>
				</tr>
			</table>
		</div>
		<div id="contextPanel" >
			
			<div style="width:37%; float:left;height:100%;">
				<table id="dataTable_1" class="table datatable ui-data-table display dataTable no-footer">
					<thead>
					<tr>
						<th><input type="checkbox" id="all_checked"></th>
						<th>床号</th>
						<th>病人姓名</th>
						<th>药单数量</th>
						<th>住院流水号</th>
					</tr>
					</thead>
				</table>
			</div>
			<!-- 正常显示的界面 -->
			<div id="rightContaint" class="tbl" style="float:right;width:62%;height:100%;">
				<div id="table_title_div" style="height:32px"> 
					<table border="0" cellpadding="0"cellspacing="0" >
						<tr style="border:1px solid #B5B5B5">
						    <th width="15%">批次 输液量</th>
						    <th width="10%">频次</th>
						    <th width="11%">组号</th>
						    <th width="27%">药品名称</th>
						    <th width="11%">单次剂量</th>
						    <th width="11%">单位</th>
						    <th width="15%">用药时间</th>
						    <th width="12px">&nbsp;</th>
					  	</tr>
					</table>
				</div>
				<div id="table_content_div" style="overflow: auto;padding:0px 1px;"> 
				</div>
			</div>
			<!-- 编辑状态下的界面 -->
			<div id="rightBatchContaint" class="tbl" style="float:right;width:62%;height:100%;">
			     <div id="batch_title_div" style="height:32px"> 
					<table border="0" cellpadding="0"cellspacing="0" >
						<tr style="border:1px solid #B5B5B5">
						    <th width="11%">批次</th>
						    <th width="7%">输液量</th>
						    <th width="7%">频次</th>
						    <th width="11%">组号</th>
						    <th width="27%">药品名称</th>
						    <th width="11%">单次剂量</th>
						    <th width="11%">单位</th>
						    <th width="15%">用药时间</th>
						    <th width="12px">&nbsp;</th>
					  	</tr>
					</table>
				</div>
				<div id="batch_content_div" style="overflow: auto;padding:0px 1px;"> 
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function resizePageSize(){
			_gridWidth = $(document).width()-12;
			_gridHeight = $(document).height()-80;
			/* $("#flexGrid__1").flexResize(); */
		}

		var _gridWidth = 0;
		var _gridHeight = 0;
		var inpatientString = function(){
			return window.parent.getInpatientInfo();
		}
		
		var batchInfoObj={};
		var isARuleRun=false;
		var isFirstLoad=false;
		var isButtonShowOne=true;
		var isButtonShowTwo=true;
		
		var dateChange = ${dateChange};
		//dataTable API
		var dataTable;
		//dataTable params
		var paramAll;
		$(function(){
			
			var windowWidth=$(document).width();
			if(windowWidth >= 1200){
				$("#smallScreen").css("display","none");
				$("td[name='bigScreen']").css("display","table-cell");
			}else{
				$("#smallScreen").css("display","block");
				$("td[name='bigScreen']").css("display","none");
			}

			$(window).resize(function(){
				resizePageSize();
			});
			resizePageSize();
			if(dateChange){
				$("#yyrq").val(getCurrentDate("yyyy-MM-dd",null,1));
			}else{
				$("#yyrq").val(getCurrentDate("yyyy-MM-dd",null,0));
			}
			$("#searchTitlePanel").width(_gridWidth);
			$("#contextPanel").width(_gridWidth);
			$("#contextPanel").height(_gridHeight);
			var rightContaintHeight=$("#rightContaint").height();
			var rightBatchContaintHeight=$("#rightBatchContaint").height();
			
			var table_title_div=$("#table_title_div").height();
			var batch_title_div=$("#batch_title_div").height();
			
			$("#table_content_div").height(rightContaintHeight-table_title_div);
			$("#batch_content_div").height(rightBatchContaintHeight-batch_title_div);
			
			//确认状态
			$("#confirmStatus").bind("change",function(){
				qry__1();
			});
			
			//药品分类
			$("#medicamentType").multiSelect({ "selectAll": false,"noneSelected": "--请选择--","oneOrMoreSelected":"*" },function(){
				qry__1();
			});
			
			//批次
			$("#batchId").multiSelect({ "selectAll": false,"noneSelected": "--请选择--","oneOrMoreSelected":"*" },function(){
				qry__1();
			});
			
			$("#aSearch__1").bind("click",function(){
				qry__1();
			});
			
			var _columnWidth = (parseInt(_gridWidth*0.37)-60) / 4;

			qry__1();
			$(".hDivBox").css("background","#39aeaa");
			closeBatchChangePanel();

			//优化按钮
			$("#aRuleRun__1").bind("click",function(){
				goToRuleRun();
			});
			
			//优化按钮
			$("#aRuleRun__2").bind("click",function(){
				goToRuleRun();
			});
			
			//确认按钮
			$("#aRuleConfirm__1").bind("click",function(){
				goToRuleConfirm();
			});
			
			//确认按钮
			$("#aRuleConfirm__2").bind("click",function(){
				goToRuleConfirm();
			});
			
			//自动落批次检查
			$("#autoCheckRun").bind("click",function(){
				goToAutoCheckRun();
			});
			
			//自动落批次检查
			$("#autoCheckRun_2").bind("click",function(){
				goToAutoCheckRun();
			});
		});// init end

        function initDataTable(){
            dataTable = $('#dataTable_1').DataTable({
                "searching": false,
                "processing": true,
                "serverSide": true,
                "select": true,
                "ordering": true,
                "order": [],
                "paging": false,
                //"scrollX": true,
                "language": {
                    "url": "${pageContext.request.contextPath}/assets/datatables/cn.json"
                },
                "ajax": {
                    "url": "${pageContext.request.contextPath}/doctorAdvice/ydList1",
                    "type": "post",
                    "data": function (d) {
                        var params = [];
                        if (paramAll) {
                            params = paramAll.concat();
                        }
                        for (var index in params) {
                            d[params[index].name] = params[index].value;
                        }

                        d.rp = 20000;
                        d.page = 1;
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
                    {"data": "patname", "bSortable": false},
                    {"data": "ydCount", "bSortable": false},
                    {"data": "inpatientNo", "bSortable": false},
                ],
                columnDefs: [
                    {
                        targets:0,
                        data: null,
                        defaultContent:"<input type ='checkbox' name='pidsj'>",
                    }
                ],
                "createdRow":function (row, data, idx) {
                    $(row).on('click' , 'td:gt(0)', function(){
                        $(row).siblings().removeClass("selected");
                        $(row).addClass("selected");
                        changeCheckedTwo();
                    });
                },
                "fnDrawCallback": function(){
                    if($.isEmptyObject(batchInfoObj)){
                        $("#batchIdTr").next().children("div").children("label").each(function(i,v){
                            var labelVal=$(this).children("input").val();
                            var labelText=$(this).text();
                            batchInfoObj[labelVal] = labelText;
                        });
                    }
                }
            });
        }

		function goToRuleRun(){
			isARuleRun=true;
			var inpatientNo= getInpatientNOS(2);
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/doctorAdvice/ydRuleRun',
				dataType : 'json',
				cache : false,
				showDialog: true,
				data : {"yyrq":$("#yyrq").val(),
						"inpatientString":inpatientString(),
						"inpatientNo":inpatientNo},
				success : function(result) {
					if(result.code>0){
						changeRightShowInfo();
						qry__1();
						message({html: "已处理完成"});
					}else{
						message({html: result.mess||"操作异常，请稍后再试"});
					}
				}
			});
		}
		
		function goToRuleConfirm(){
			if(!isARuleRun ){
				message({html: "请选择进行批次优化"});
			}else{
				var inpatientNo= getInpatientNOS(2);
				message({
					html: "是否确认药单？",
					showCancel:true,
					confirm:function(){
						$.ajax({
							type : 'POST',
							url : '${pageContext.request.contextPath}/doctorAdvice/ydRuleConfirm',
							dataType : 'json',
							cache : false,
							showDialog: true,
							data : {"yyrq":$("#yyrq").val(),
									"inpatientString":inpatientString(),
									"inpatientNo":inpatientNo},
							success : function(result) {
								if(result.code>0){
									if($("#confirmStatus").val()==0){
										qry__1();
									}
									message({html: "药单确认成功"});
								}else{
									message({html: result.mess||"操作异常，请稍后再试"});
								}
							}
						});
					}
		    	});
			}
		}
		
		
		function goToAutoCheckRun(){
			$.ajax({
				type : 'POST', 
				url : '${pageContext.request.contextPath}/doctorAdvice/autoCheckRun',
				dataType : 'html',  
				cache : false,
				data : {"yyrq":$("#yyrq").val(),},
				success : function(data) {
					 changeRightShowInfo();
					 message({html: "处理完成"});
				},
				error : function (err) {
					 message({html: "自动生成批次失败"});
				}
		    });
		}
		
		function changeCheckedOne(event){
			/* event.stopPropagation(); */
			changeRightShowInfo();
		} 
		
		function changeCheckedTwo(){
			changeRightShowInfo();
		}
		
		function getInpatientNOS(type){
			/* var inpatientNoArray = getFlexiGridSelectedRowText($("#flexGrid__1"), 5); */
			var inpatientNoArray =[];
			if(type == 1){
				inpatientNoArray.push($(".selected td:eq(4)").text());
			}else if(type == 2){
				inpatientNoArray = getDataTableSelectRowData($("#dataTable_1"), 'inpatientNo');
			}
			var inpatientNo="";
			$.each(inpatientNoArray,function(i,v){
				inpatientNo += v+",";
			});
			inpatientNo = inpatientNo.substring(0,inpatientNo.length-1);
			return inpatientNo;
		}
		
		function changeRightShowInfo(){
			var inpatientNo =getInpatientNOS(1);
			var params = [];
			params.push({"name":"yyrq","value":$("#yyrq").val()});//用药日期
			params.push({"name":"confirmStatus","value":$("#confirmStatus").val()});//确认状态
			params.push({"name":"medicamentType","value":$("#medicamentType").selectedValuesString()});//药品分类
			params.push({"name":"batchId","value":$("#batchId").selectedValuesString()});//批次
			params.push({"name":"inpatientString","value":inpatientString()});//病区
			params.push({"name":"inpatientNo","value":inpatientNo});//住院号
			if(typeof(inputSearchParam) !="undefined" && inputSearchParam.length>0){
				$.each(inputSearchParam,function(i,v){
					params.push(v);
				});
			}
			$.ajax({
	   			type: "POST",
	   			url: "${pageContext.request.contextPath}/doctorAdvice/ydListInfol",
	   			data:params,
	   			success: function(data){
					$("#table_content_div").html("");
					$("#batch_content_div").html("");
					isButtonShowOne=true;
					isButtonShowTwo=true;
	   				var dataArray={};
	   				$.each(data,function(i,v){
	   					if(typeof(dataArray[data[i].inpatientNo])=="undefined"){
	   						dataArray[data[i].inpatientNo]=[];
	   					}
	   					dataArray[data[i].inpatientNo].push(v);
	   				});
	   				var array=inpatientNo.split(",");
	   				var arrayLength=array.length;
	   				$.each(dataArray,function(i,v){
	   					arrayLength--;
	   					doShowPanel(dataArray[array[arrayLength]]);
	   					
	   				});
	   			}
			});
		}
		
		function closeBatchChange(){
			closeBatchChangePanel();
			changeCheckedOne();
		}
		
		function doShowPanel(data){
			var patientInfoArray=data;
			$.each(data,function(i_two,patVal){
				var sexStr= (typeof(patVal.sex)=="undefined" ? "" : (patVal.sex == 1   ?"男" :"女"));
				if(i_two==0){
					$("#table_content_div").append(
							"<div class='patientInfo'>"+
							"<span class='span_one'>"+patVal.bedno+"&nbsp;&nbsp;&nbsp;&nbsp;</span>"+
							"<span class='span_one'>病区：</span><span class='span_one span_two'>"+patVal.wardName+"</span>&nbsp;"+
							"<span class='span_one'>姓名：</span><span class='span_one span_two'>"+patVal.patname+"</span>&nbsp;"+
							"<span class='span_one'>年龄：</span><span class='span_one span_two'>"+patVal.age+"</span>&nbsp;"+
							"<span class='span_one'>性别：</span><span class='span_one span_two'>"+sexStr+"</span>&nbsp;"+
							"<span class='span_one'>体重：</span><span class='span_one span_two'>"+(typeof(patVal.avdp)=="undefined"?'':patVal.avdp)+"</span>&nbsp;"+
							"<span class='button' style='float:right;padding:3px 23px;margin-right:15px;margin-top:8px;display:"+(isButtonShowOne ? "block":"none")+"' onclick='showBatchChangePanel()'>编辑</span></div>"			
					);
				}
				splitPatientInfo(patVal);
			});
			isButtonShowOne=false;
				
			//保存页面样式
			var html=[];
			var patientVal = data;
			if(patientVal.length >0 ){
				html.push(
						"<div class='patientInfo'>"+
						"<span class='span_one'>"+patientVal[0].bedno+"&nbsp;&nbsp;&nbsp;&nbsp;</span>"+
						"<span class='span_one'>病区：</span><span class='span_one span_two'>"+patientVal[0].wardName+"</span>&nbsp;"+
						"<span class='span_one'>姓名：</span><span class='span_one span_two'>"+patientVal[0].patname+"</span>&nbsp;"+
						"<span class='span_one'>年龄：</span><span class='span_one span_two'>"+patientVal[0].age+"</span>&nbsp;"+
						"<span class='span_one'>性别：</span><span class='span_one span_two'>"+patientVal[0].sex+"</span>&nbsp;"+
						"<span class='span_one'>体重：</span><span class='span_one span_two'>"+(typeof(patientVal[0].avdp)=="undefined"?"":patientVal[0].avdp)+"</span>&nbsp;"+
						"<span class='button' style='float:right;padding:3px 23px;margin-right:15px;margin-top:8px;display:"+(isButtonShowTwo ? "block":"none")+"' onclick='closeBatchChange()'>保存</span></div>"		
				);
				html.push("<table border='0' style='border-collapse:separate;' cellpadding='0' cellspacing='0'>");
				$.each(patientVal,function(i_two,patVal){
					html.push(
							(i_two%2 === 1?"<tr style='background:#ffffff'>":"<tr style='background:#EDEDED'>")+
							"<td width='11%' style='text-align:center;'>"+
							"<select id='yd__1_"+patVal.pidsj+ "' oldvalue='"+patVal.pb_id+"' parentno='"+patVal.parentNo+"' pidsj='"+patVal.pidsj+"' scrqS='"+patVal.scrqS+"' onchange='changePC__1(this)' style='width:70px'  >"  //"<option value='' >--请选择--</option>" 
							+"<c:forEach items='${batchList}' var='batch' ><option value='${batch.id}' issecendadvice='${batch.isSecendAdvice}' "+ (patVal.pb_id=='${batch.id}'?"selected='selected'":"") + " >${batch.name}</option></c:forEach>" 
							+"</select>"+"</td>"+
							"<td width='7%' style='text-align:center;'>"+patVal.transfusion+"</td>"+
							"<td width='7%' style='text-align:center;'>"+patVal.freqCode+"</td>"+
							"<td width='11%' style='text-align:center;'>"+patVal.parentNo+"</td>"+
							"<td width='27%' >"+patVal.drugname.replace(new RegExp("@@","g"),"<br>")+"</td>"+
							"<td width='11%' style='text-align:center;'>"+patVal.dose.replace(new RegExp("@@","g"),"<br>")+"</td>"+
							"<td width='11%' style='text-align:center;'>"+patVal.doseUnit.replace(new RegExp("@@","g"),"<br>")+"</td>"+
							"<td width='15%' style='text-align:center;'>"+patVal.yyrqS+"</td></tr>");
				});
				html.push("</table>");
				html.push("<div style='height:20px;'></div>");
				$("#batch_content_div").append(html.join(""));
				isButtonShowTwo=false;
			}
		}
		
		//处理界面右边的数据用的
		function splitPatientInfo(patientInfo){
			var chargeCodes=patientInfo["chargeCode"].split("@@");
			var doseArray=patientInfo["dose"].split("@@");
			var drugnameArray=patientInfo["drugname"].split("@@");
			var doseUnitArray=patientInfo["doseUnit"].split("@@");
			var html = [];
			if($("#table_"+patientInfo.inpatientNo+"_"+patientInfo.zxbc).length==0){
				html.push("<table id='table_"+patientInfo.inpatientNo+"_"+patientInfo.zxbc+"' border='0' style='border-collapse:separate;border:1px solid #5d6ebd63' cellpadding='0' cellspacing='0'>");
				 for(var index = 0;index < chargeCodes.length;index++){
					if(index==0){
						html.push(
								( patientInfo.ydreorderMess.indexOf("当前批次容积") != -1 ? "<tr style='background:rgba(205,10,10,0.3)'>":"<tr style='background:#EDEDED'>")+
								"<td id='td_"+patientInfo.inpatientNo+"_"+patientInfo.zxbc+"' width='15%' rowspan='"+chargeCodes.length+
								"' class='' style='color:#797979;font-weight:500;text-align:center;background:#ffffff' >"+batchInfoObj[patientInfo.zxbc]+
								"<br><span style='margin-top:10px;font-weight:600;display:block;text-align:center;' id='span_"+patientInfo.inpatientNo+"_"+patientInfo.zxbc+"'>"+parseFloat(patientInfo.transfusion)+"</span></td>"+
								"<td width='10%' style='text-align:center;'>"+patientInfo.freqCode+"</td>"+
								"<td width='11%' style='text-align:center;'>"+patientInfo.parentNo+"</td>"+
								"<td width='27%' >"+drugnameArray[index]+"</td>"+
								"<td width='11%' style='text-align:center;'>"+doseArray[index]+"</td>"+
								"<td width='11%' style='text-align:center;'>"+doseUnitArray[index]+"</td>"+
								"<td width='15%' style='text-align:center;'>"+patientInfo.yyrqS+"</td></tr>")
					}else{
						html.push(
								( patientInfo.ydreorderMess.indexOf("当前批次容积") != -1 ? "<tr style='background:rgba(205,10,10,0.3)'>" : (index%2 === 1?"<tr style='background:#ffffff'>":"<tr style='background:#EDEDED'>"))								
								+"<td width='10%' style='text-align:center;'>"+patientInfo.freqCode+"</td>"+
								"<td width='11%' style='text-align:center;'>"+patientInfo.parentNo+"</td>"+
								"<td width='27%' >"+drugnameArray[index]+"</td>"+
								"<td width='11%' style='text-align:center;'>"+doseArray[index]+"</td>"+
								"<td width='11%' style='text-align:center;'>"+doseUnitArray[index]+"</td>"+
								"<td width='15%' style='text-align:center;'>"+patientInfo.yyrqS+"</td></tr>");
					}
				}
				 html.push("</table>");
				 html.push("<div style='height:20px;'></div>");
				 $("#table_content_div").append(html.join(""));
			}else{
				var oldRowspan = parseInt($("#td_"+patientInfo.inpatientNo+"_"+patientInfo.zxbc).attr("rowspan"));
				var transfusion =parseFloat($("#span_"+patientInfo.inpatientNo+"_"+patientInfo.zxbc).text());
				$("#span_"+patientInfo.inpatientNo+"_"+patientInfo.zxbc).text(transfusion+parseFloat(patientInfo.transfusion));
				
				var trChildrenLength=$("#table_"+patientInfo.inpatientNo+"_"+patientInfo.zxbc).children("tbody").children("tr").length+1;
				trChildrenLength =trChildrenLength%2;
				$("#td_"+patientInfo.inpatientNo+"_"+patientInfo.zxbc).attr("rowspan",chargeCodes.length+oldRowspan);
				 for(var index = 0;index < chargeCodes.length;index++){
					html.push(
							( patientInfo.ydreorderMess.indexOf("当前批次容积") != -1? "<tr style='background:rgba(205,10,10,0.3)'>" :(index%2 === trChildrenLength ?"<tr style='background:#ffffff'>":"<tr style='background:#EDEDED'>"))							
							+"<td width='10%' style='text-align:center;"+((index==0)?"border-top: 1px solid #000000;":"")+"'>"+patientInfo.freqCode+"</td>"+
							"<td width='11%' style='text-align:center;"+((index==0)?"border-top: 1px solid #000000;":"")+"'>"+patientInfo.parentNo+"</td>"+
							"<td width='27%' style='"+((index==0)?"border-top: 1px solid #000000;":"")+"'>"+drugnameArray[index]+"</td>"+
							"<td width='11%' style='text-align:center;"+((index==0)?"border-top: 1px solid #000000;":"")+"'>"+doseArray[index]+"</td>"+
							"<td width='11%' style='text-align:center;"+((index==0)?"border-top: 1px solid #000000;":"")+"'>"+doseUnitArray[index]+"</td>"+
							"<td width='15%' style='text-align:center;"+((index==0)?"border-top: 1px solid #000000;":"")+"'>"+patientInfo.yyrqS+"</td></tr>");
				 }
				 $("#table_"+patientInfo.inpatientNo+"_"+patientInfo.zxbc).append(html.join(""));
			}
		}
		
		function showBatchChangePanel(){
			$("#rightContaint").css("display","none");
			$("#rightBatchContaint").css("display","block");
		}
		
		function closeBatchChangePanel(){
			$("#rightContaint").css("display","block");
			$("#rightBatchContaint").css("display","none");
			
		}
		
		function changePC__1(obj){
			var pidsj = $(obj).attr("pidsj");
			var pcId =  $(obj).val();
			var isSecendAdvice = $(obj).find("option:selected").attr("issecendadvice"); 
			var parent_pc_map = {}; 
			$("#batch_content_div select").each(function(){
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
					}else{
						$(obj).val($(obj).attr("oldvalue"));
						message({html: response.msg});
					}
				}
			});
		}
		
		//用药日期改变调用该方法
		function dataPick(){
			qry__1();
		}
		
		var inputSearchParam;
		
		function qry__1(param){
			if(typeof(param) != "undefined" ){
				inputSearchParam = param;
			}
			patientInfo={};
			$("#table_content_div").html("");
			$("#batch_content_div").html("");
			var params = [];
			params.push({"name":"yyrq","value":$("#yyrq").val()});	
			/* var dateNow = getCurrentDate("yyyy-MM-dd",null,0);
			if($("#yyrq").val() == dateNow && new Date().getHours() >=10){
				//日期为今天 并且大于10点  用药时间变长明天
				params.push({"name":"yyrq","value":getCurrentDate("yyyy-MM-dd",null,1)});	
			}else{
				//用药时间为今天 10点之前的  或者 手动选择时间 用药时间都不变
				params.push({"name":"yyrq","value":$("#yyrq").val()});	
			} */
			
			params.push({"name":"confirmStatus","value":$("#confirmStatus").val()});//确认状态
			params.push({"name":"medicamentType","value":$("#medicamentType").selectedValuesString()});//药品分类
			params.push({"name":"batchId","value":$("#batchId").selectedValuesString()});//批次
			params.push({"name":"inpatientString","value":inpatientString()});//病区
			if(typeof(inputSearchParam) !="undefined" && inputSearchParam.length>0){
				$.each(inputSearchParam,function(i,v){
					params.push(v);
				});
			}
			paramAll = params;
			if (dataTable) {
			    dataTable.ajax.reload();
			} else {
                initDataTable();
			}
		}

        //取datatable 选中行数据
        function getDataTableSelectRowData(dom, key){
            var ary = new Array();
            $(dom).find('tbody input[type="checkbox"]:checked').each(function(){
                var v = dataTable.row($(this).parents('tr')).data()[key];
                if (v) {
                    ary.push(v);
                }
            });
            return ary;
        }
	</script>
</body>
</html>
