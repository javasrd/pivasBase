<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="/WEB-INF/views/common/common.jsp" %>

<!DOCTYPE html>
<html>
<head>

	<style type="text/css">
	<%-- 药物优先级 左中右样式 --开始------------------%>
	.td200Left{
		width: 200px;vertical-align: top;
	}
	.divLeftMenuAll{
		height: 55px;
		padding-top: 20px;
		padding-left: 24px;
		font-size: 0.75rem;
		border-bottom: 1px solid #ddd;
	}
	.divLeftMenuList{
		overflow-y: auto;  padding-left: 0px;padding-right: 0px; font-size: 0.75rem;
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
		height: 100%;width:100%;padding-left: 10px;padding-top: 0px;
		border-spacing: 0px;

		margin-left:10px;
	}
	.tdSortTitle{
		vertical-align: bottom ;padding-right: 5px;color: white;
	}
	.divSortTitle{
		height: 35px;
		background-color: #6F96E5;
		font-size: 0.75rem;
		line-height: 35px;
		padding-left: 10px;
	}
	.aSortTitle{
		float: right;
		padding-left: 10px;
		margin-top: 7px;
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
	.button{
		margin-left: 0px;
	}
	.ui-dialog .ui-dialog-buttonpane button{
		    border-color: transparent;
	}
	<%--药物优先级 左中右样式 --结束------------------%>
	
	.td_checkbox{
		margin: 0px;
		padding:0px;
	}
	
	.td_span{
		margin: 0px;
		padding:0px;	
	}

	.ui-td-left {
		border: 1px solid #ddd;
	}
	
	</style>

	<script type="text/javascript">
	var _pageWidth = 0;
	var _pageHeight = 0;
	
	function resizePageSize(){
		_pageWidth = $(window).width();
		_pageHeight = $(window).height();
		$("#divMain1").css("height",_pageHeight);
		$("#divArea").css("height",_pageHeight-70);
		
	}
	var dialogWidth = 900 ;
	var dialogHeight = 520 ;
	var medicGridWidth = dialogWidth - 40 ;<%--宽度比弹窗的宽度小--%>
	var medicGridheight = dialogHeight - 210 ;<%--宽度比弹窗的高度小--%>
	
	var prType = "${prType}" ;
	var areaCodeNow = "${areaCodeNow}" ;
	var areaNameNow = "${areaNameNow}" ;
	var medicIDs=[];
	
	$(function() {
		$(window).resize(function(){
			resizePageSize();
		});
		resizePageSize();
		$("#tabRule tr td").each(function(i){
			var medicId=$(this).text();
			medicIDs.push(medicId.substring(medicId.indexOf("[")+1,medicId.indexOf("]")));
		}); 
		$("#divSynToOther").dialog({
			autoOpen: false,
			width: 560,
			height: 400,
			modal: true,
			resizable: false,
			buttons: {
				"<spring:message code='common.confirm'/>": function() {
					var areaCodeN = "" ;
					var areaNameN = "" ;
					$(".chkOneArea").each(function(){
						if($(this).attr("checked")=="checked"){
							if(areaCodeNow!= $(this).val()){
								areaCodeN = areaCodeN + $(this).val() + "," ;
								areaNameN = areaNameN + $(this).attr("deptname") + "," ;
							}
						}
					});
					if(areaCodeN.endWith(",")){
						areaCodeN = areaCodeN.substring(0,areaCodeN.length-1);
						areaNameN = areaNameN.substring(0,areaNameN.length-1);
					}
					var medicCodeStr = ""; 
					var medicCodeTmp = "";
					$("#tabRule tr td:nth-child(1)").each(function(i,v){
						medicCodeTmp=$(this).text();
						medicCodeStr += medicCodeTmp.substring(medicCodeTmp.indexOf("[")+1,medicCodeTmp.indexOf("]"))+",";
					});
					if(medicCodeStr.endWith(",")){
						medicCodeStr = medicCodeStr.substring(0,medicCodeStr.length-1);
					}
					if(areaCodeN!=""){
						$.ajax({
							type : 'POST',
							url : '${pageContext.request.contextPath}/ydRule/synMdcmRuleToOtherArea',
							dataType : 'json',
							cache : false,
							showDialog: false,
							data : {"areaCodeNow":areaCodeNow,"areaNameNow":areaNameNow,"areaCodeN":areaCodeN,"areaNameN":areaNameN,"prType":prType,"medicCodeStr":medicCodeStr},
							success : function(result) {
								if(result.code>0){
									$("#divSynToOther").dialog("close");
									message({html: "已成功同步到相关病区"});
								}else{
									message({html: result.mess||"操作异常，请稍后再试"});
								}
							}
						});
					}else{
						message({html: "请选择病区"});
					}
				},
				"<spring:message code='common.cancel'/>": function() {
					$(this).dialog("close");
				}
			},
			close: function() {
			}
		});
		
		$(".chkAllArea").bind("change",function(){
			if($(this).attr("checked")=="checked"){
				$(".chkOneArea").each(function(){
					if($(this).val()==areaCodeNow){
						
					}else{
						$(this).attr("checked","checked");
					}
				});
				<%--$(".chkOneArea").attr("checked","checked");--%>
			}else{
				$(".chkOneArea").each(function(){
					if($(this).val()==areaCodeNow){
						
					}else{
						$(this).removeAttr("checked");
					}
				});
                <%--$(".chkOneArea").removeAttr("checked");--%>
			}
		});
		
		$(".chkOneArea").bind("change",function(){
			var checkAll = true ;
			$(".chkOneArea").each(function(){
				if($(this).attr("checked")==undefined){
					checkAll = false ;
				}
			});
			if(checkAll){
				$(".chkAllArea").attr("checked","checked");
			}else{
				$(".chkAllArea").removeAttr("checked");
			}
		});

		$("#synDataDicBtn").bind("click",function(){
			if($("#tabRule tr").length == 0){
				message({html: "没有药物优先级规则，无法应用到其他病区"});
				return ;
			}
			$(".chkAllArea").removeAttr("checked");
			$(".chkOneArea").each(function(){
				$(this).removeAttr("checked");
				if($(this).val()==areaCodeNow){
					$(this).attr("checked","checked");
					$(this).attr("disabled","disabled");
					$(this).parent().next().css("background-color","rgba(216, 216, 216, 0.8)")
				}else{
					$(this).removeAttr("disabled");
					$(this).parent().next().css("background-color","transparent")
				}
			});
			$("#divSynToOther").dialog("open");
		});
		

		$("#tabArea td").bind("click",function(){
			areaCodeNow = $(this).attr("areacode");
			areaNameNow = $(this).html();
			$(this).parent().parent().find("tr").each(function(){
				$(this).removeClass("areaSel");
				$(this).css("background","transparent");
			});
			$(this).parent().addClass("areaSel"); 
			$(this).parent().css("background","#6F96E5");
			showAreaData(areaCodeNow);
		});
	});

	 function processYes(v){
		if(v == 0){
			return "<spring:message code="common.yes"/>";
		}else if(v == 1){
			return "<spring:message code="common.no"/>";
		}
	}
	 
	 
	 
	 
	 $(function(){
			<%--药物优先级处理--%>
			var _columnWidth = (medicGridWidth-60) / 10;
			$("#medicGrid").flexigrid({
				width : medicGridWidth,
				height :medicGridheight,
				url: "${pageContext.request.contextPath}/mdcm/mdcmslist",
				dataType : 'json',
				colModel : [ 
					{display: 'medicamentsId', name : 'medicamentsId', width : 0,hide:'true'},
					{display: '<spring:message code="medicaments.categoryId"/>', name : 'categoryName', width : _columnWidth,align: 'left'},
					{display: '<spring:message code="medicaments.medicamentsName"/>', name : 'medicamentsName', width : _columnWidth,align: 'left'},
					{display: '<spring:message code="medicaments.medicamentsCode"/>', name : 'medicamentsCode', width : _columnWidth,align: 'left'},
					{display: '<spring:message code="medicaments.medicamentsSpecifications"/>', name : 'medicamentsSpecifications', width : _columnWidth,align: 'left'},
					{display: '<spring:message code="medicaments.medicamentsDosage"/>', name : 'medicamentsDosage', width : _columnWidth,align: 'left'},
					{display: '<spring:message code="medicaments.medicamentsSuchama"/>', name : 'medicamentsSuchama', width : _columnWidth,align: 'left'},
					{display: '<spring:message code="medicaments.medicamentsPackingUnit"/>', name : 'medicamentsPackingUnit', width : _columnWidth,align: 'left'},
					{display: '<spring:message code="medicaments.medicamentsMenstruum"/>', name : 'medicamentsMenstruum', width : 0,process: processYes,hide:'true',align: 'left'},
					{display: '<spring:message code="medicaments.medicamentsPlace"/>', name : 'medicamentsPlace', width : _columnWidth,align: 'left',align: 'left'},
					{display: '<spring:message code="medicaments.medicamentsUserInfo"/>', name : 'labelNames', width : _columnWidth},
					{display: '是否高危', name : 'medicamentsDanger', width : _columnWidth,process: processYes,align: 'left'}
				],
				resizable : false,
				usepager : true,
				useRp : true,
				usepager : true,
				autoload : false,
				hideOnSubmit : true,
				showcheckbox : true,
				rowbinddata : true,
				numCheckBoxTitle : "<spring:message code='common.selectall'/>"
			});
			
			$("#divAddMed").dialog({
				autoOpen: false,
				width: dialogWidth,
				height: dialogHeight+15,
				modal: true,
				resizable: false,
				buttons: {
					"<spring:message code='common.confirm'/>": function() {
						var medIdS = getFlexiGridSelectedRowText($("#medicGrid"), 5);
						var medNameS = getFlexiGridSelectedRowText($("#medicGrid"), 4);
						var medCodeS = getFlexiGridSelectedRowText($("#medicGrid"), 5);
						
						var medNameMap = {};
						var medCodeMap = {};
						if(medIdS && medIdS.length>0){
							var medIdN = "" ;
							var medNameN = "" ;
							for(var row in medIdS){
								medIdN = medIdN + medIdS[row] + "," ;
								medNameN = medNameN + medNameS[row] + "," ;
								medNameMap[medIdS[row]]=medNameS[row];
								medCodeMap[medIdS[row]]=medCodeS[row];
							}
							if(medIdN.endWith(",")){
								medIdN = medIdN.substring(0,medIdN.length-1);
								medNameN = medNameN.substring(0,medNameN.length-1);
							}
							sendToAdd(medNameMap,medCodeMap,{
								"deptcode":areaCodeNow,
								"deptname":areaNameNow,
								"prType":prType,
								"medIdN":medIdN,
								"medNameN":medNameN,
								"medicType":"1"});
						}else{
							var medicaType=$("#medicamentCateDIV input:checked");
							if($(medicaType).val()==-1){
								message({html: "请选择药品或者药品种类再保存"});
							}else{
								var medTypeIdS=$(medicaType).val();
								if($.inArray(medTypeIdS,medicIDs)!=-1){
									$("#divAddMed").dialog("close"); 
									message({html: "该类型的药品已经添加"});
									return;
								}else{
									medicIDs.push(medTypeIdS);
								}
								var medTypeNameN=$(medicaType).next().text();
								medNameMap[medTypeIdS]=medTypeNameN;
								medCodeMap[medTypeIdS]=medTypeIdS;
								sendToAdd(medNameMap,medCodeMap,{
									"deptcode":areaCodeNow,
									"deptname":areaNameNow,
									"prType":prType,
									"medIdN":$(medicaType).val(),
									"medNameN":$(medicaType).next().text(),
									"medicType":"2"});
							}
						}
					},
					"<spring:message code='common.cancel'/>": function() {
						$(this).dialog("close");
					}
				},
				close: function() {
					
				}
			});
			
			function sendToAdd(medNameMap,medCodeMap,param){
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/ydRule/addPLRule',
					dataType : 'json',
					cache : false,
					data : param,
					success : function(result) {
						if(result.code>0){
							for(var row in result.data){
								$("#tabRule").append("<tr><td class='tdSortRow' id='pr_"+result.data[row].prId+"' prorder='"+result.data[row].prOrder+"' >"+medNameMap[result.data[row].medicId]+"["+medCodeMap[result.data[row].medicId]+"]"+"</td></tr>");
							}
							initTab();
							$("#divAddMed").dialog("close"); 
						}else{
							message({html: result.data.mess||"操作异常，请稍后再试"});
						}
					}
				});
			}
			
			$("#aAddMedN").bind("click",function(){
				$("#divAddMed").dialog("open");
				if($("#medicamentCateDIV div").length===1){
					qryMedicamentCategory();
				}else{
					$("#medicamentCateDIV input:checkbox").each(function(i,v){
						if(i===0){
							$(this).attr("checked",true);
						}else{
							$(this).removeAttr("checked");
						}
					});
				}
				qryList();
			});
			
			$("#aDelMedN").bind("click",function(){
				var $row = $("#tabRule .tdMedSelec");
				if($row && $row.length>0){
					var id = $row.attr("id").replace("pr_","");
					var medicId=$row.text();
					medicId=medicId.substring(medicId.indexOf("[")+1,medicId.indexOf("]"));
					$.ajax({
						type : 'POST',
						url : '${pageContext.request.contextPath}/ydRule/delPLRule',
						dataType : 'json',
						cache : false,
						showDialog: false,
						data : {"prId":id,"deptcode":areaCodeNow,"deptname":areaNameNow,"prType":prType},
						success : function(result) {
							if(result.code>0){
								removeByValue(medicIDs,medicId);
								$row.parent().remove();
								initTab();
							}
							message({html: result.mess||"操作异常，请稍后再试"});
						}
					});
				}else{
					message({html: "请选择数据再操作"});
				}
			});
			$("#aUpMed").bind("click",function(){
				updateSort("up");
			});
			$("#aDownMed").bind("click",function(){
				updateSort("down");
			});
			initTab();
	 });

    function removeByValue(arr, val) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == val) {
                arr.splice(i, 1);
                break;
            }
        }
    }

	 function showAreaData(areaCode){
		 $.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/ydRule/getPLRuleByArea',
				dataType : 'json',
				cache : false,
				showDialog: false,
				data : {"areaCode":areaCode,"prType":prType},
				success : function(result) {
					$("#tabRule").html("");
					medicIDs=[];
					for(var row in result){
						$("#tabRule").append("<tr><td class='tdSortRow' id='pr_"+result[row].prId+"' prorder='"+result[row].prOrder+"' >"+result[row].medicName +"["+result[row].medicId   +"]</td></tr>");
						medicIDs.push(result[row].medicId);
					}
					initTab();
				}
			});
	 }
	var updateSortRuning = false ;
	<%--药物优先级处理--%>
	function updateSort(type){
		if(updateSortRuning){
			return ;
		}
		var $row = $("#tabRule .tdMedSelec");
		var $row2 ;
		if(type=="up"){
			$row2 = $row.parent().prev();
		}else{
			$row2 = $row.parent().next();
		}
		if($row){
			if($row2.length>0){
				$row2 = $row2.children();
				var prorder  = $row.attr("prorder");
				var prorder2 = $row2.attr("prorder");
				var params = {
						"deptname":areaNameNow,
						"prId1":$row.attr("id").replace("pr_",""),
						"prOrder1":prorder2,
						"prId2":$row2.attr("id").replace("pr_",""),
						"prOrder2":prorder,
						"type":type
				} ;
				updateSortRuning = true ;
				$.ajax({
					type : 'POST',
					url : '${pageContext.request.contextPath}/ydRule/updPLRules',
					dataType : 'json',
					cache : false,
					showDialog: false,
					data : params,
					success : function(result) {
						updateSortRuning = false;
						if(result.code>0){
							$row.attr("prorder",prorder2);
							$row2.attr("prorder",prorder);
							if(type=="up"){
								$row2.parent().insertAfter($row.parent()); 
							}else{
								$row.parent().insertAfter($row2.parent()); 
							}
							initTab();
						}else{
							message({html: result.mess||"操作异常，请稍后再试"});
						}
					},fail: function(){
						updateSortRuning = false;
					}
				});
			}else{
				message({html: type=="up"?"已是第一条，无法上移":"已是最后一条，无法下移"});
			}
		}else{
			message({html: "请选择数据再操作"});
		}
	}

	
	function initTab(){
		$(".tabMedList td").bind("click",function(){
			$(this).parent().parent().find("td").each(function(){
				$(this).removeClass("tdMedSelec");
			});
			$(this).addClass("tdMedSelec");
		});
		
	}
	var searchParm;
	function qryList(otherParm){
		if(typeof(otherParm) != "undefined"){
			searchParm = otherParm;
		}
		var categoryId=$("#medicamentCateDIV input:checked").val();
		var param=[];
		param.push({"name":"filterMedicArea","value":areaCodeNow});
		param.push({"name":"prType","value":prType});
		if(categoryId != -1){
			param.push({"name":"categoryId","value":categoryId});
		}
		if(typeof(searchParm) != "undefined"){
			param = param.concat(searchParm);
		}
		$("#medicGrid").flexOptions({
			newp: 1,
			extParam: param,
        	url: "${pageContext.request.contextPath}/mdcm/mdcmslist"
        }).flexReload();
	}
	
	function qryMedicamentCategory(){
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/ydRule/getMdcmCate',
			dataType : 'json',
			cache : false,
			showDialog: false,
			success : function(result) {
				$.each(result,function(index,value){
					$("#medicamentCateDIV").append("<div><input type='checkbox' style='vertical-align: middle' onchange='medicamentTypeChanged(this)' value='"+value.categoryId+"'/><label>"+value.categoryName+"</label></div>");
				});
			}
		});
	}
	
	function medicamentTypeChanged(obj){
		$("#medicamentCateDIV input[type='checkbox']").each(function(checkIndex){
			$(this).removeAttr("checked");
		});
		$(obj).attr("checked", true);
		qryList();
	}
	</script>
	
</head>
<body>

<div id="divMain1" style="width:100%;height:100%;margin-top:10px;" >
	
	<table style="height: 100%;width:100%;" >
	<tr>
		<td class="td200Left ui-td-left"  >
			<div id="leftTop" class="divLeftMenuAll" >
			全部
			</div>
			<div id="divArea" class="divLeftMenuList" >
				<table id="tabArea" class="tabWith100" >
					<c:forEach items="${inpAreaList}" var="area" varStatus="rowStatus" >
					<tr class="trArea <c:if test="${rowStatus.index==0}">areaSel</c:if>" >
						<td class="tdLeft10" areacode="${area.deptCode}" >${area.deptName}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</td>
		<td>
			
			<table class="tabMain2" >
				<tr style="height: 26px;" >
					<td colspan="2" >
						<shiro:hasPermission name="PIVAS_BTN_643"><a class="button" id="synDataDicBtn" >是否应用到其他病区</a></shiro:hasPermission>
					</td>
				</tr>
				<tr style="height: 45px;" >
					<td class="tabWith50 tdSortTitle" >
						<div class="divSortTitle" >
							高优先级化学成分/类别
							<shiro:hasPermission name="PIVAS_BTN_647">
							<a href="javascript: void(0)" class="aSortTitle" id="aDelMedN" style="padding-right: 10px;">
								<img src="${pageContext.request.contextPath}/assets/pivas/images/del.png">
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="PIVAS_BTN_646">
							<a href="javascript: void(0)" class="aSortTitle" id="aDownMed" >
								<img src="${pageContext.request.contextPath}/assets/pivas/images/down.png">
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="PIVAS_BTN_645">
							<a href="javascript: void(0)" class="aSortTitle" id="aUpMed" >
								<img src="${pageContext.request.contextPath}/assets/pivas/images/up.png">
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="PIVAS_BTN_644">
							<a href="javascript: void(0)" class="aSortTitle" id="aAddMedN" >
								<img src="${pageContext.request.contextPath}/assets/pivas/images/add.png">
							</a>
							</shiro:hasPermission>
						</div>
					</td>
					<td class="tabWith50 tdSortTitle" >
						<%-- 右侧 菜单 --%>
					</td>
				</tr>
			
			<tr>
				<td class="tdSortContent" style="width: 50%;">
					<div class="divSortContent"> 
						<table id="tabRule" class="tabMedList tabWith100" >
							<c:forEach items="${prioRulesList}" var="prioRule" >
							<tr>
								<td class="tdSortRow" id="pr_${prioRule.prId}" prorder="${prioRule.prOrder}">
								${prioRule.medicName}[${prioRule.medicCode}]
								</td>
							</tr>
							</c:forEach>
						</table>
					</div>
				</td>
				<td class="tdSortContent" style="width: 50%;">
					<%-- 右侧药品列表 --%>
				</td>
			</tr>
			</table>
			
		</td>
	</tr>
	</table>
</div>


		<div id="divAddMed" title="选择药品" align="center">
			<div style="width:20%;height:100%;float:left;padding:15px 10px">
				<p style="text-align: left;margin:0px 0px;margin-bottom:10px;font-size: 16px;font-family: 微软雅黑;">药品分类</p>
				<div id="medicamentCateDIV" style="text-align: left;width:100%">
					<div><input type="checkbox" style="vertical-align: middle" onchange="medicamentTypeChanged(this)" value="-1" checked /><label>全部</label></div>
				</div>
			</div>
			<div style="width:80%;float:right; ">
				<div class="main-div">
					<div class="oe_searchview" style="margin-left: 6px;margin-bottom: 10px;">
			     		<div class="oe_searchview_facets" style="text-align: left;" >
				    		<div class="oe_searchview_input oe_searchview_head" style="padding-left: 10px;"></div>
						    <div class="oe_searchview_input"  id="inputsearch" style="width: 150px;" >
						    	  <input id="txt" type="text" class="oe_search_txt" style="width:100%;" onkeydown="this.onkeyup();" onkeyup="this.size=(this.value.length>1?this.value.length:1);" size="1"/>
						    </div>
			    		</div>
			   			<div class="oe_searchview_clear" onclick="clearclosedinputall();" style="left:480px;margin-top: 7px;"></div>
			    		<button class="oe_searchview_search" type="button" id="searchbtn" style="margin-left:200px; margin-top: 7px;">搜索</button>
						<div class="oe-autocomplete" ></div>
						<div style="border:1px solid #D2D2D2;display:none;text-align: left;" width="50px" heiht="50px" class="divselect" >
							<cite>请选择...</cite>
							<ul class="ulQry" style="-webkit-border-radius: 20;" funname="qryList" >
								<li show="药品分类" name="categoryNames" >搜索 药品分类：<span class="searchVal"></span></li>
								<li show="药品名称" name="medicamentsName" >搜索 药品名称：<span class="searchVal"></span></li>
								<li show="药品编码" name="medicamentsCode" >搜索 药品编码：<span class="searchVal"></span></li>
								<li show="药品产地" name="medicamentsPlace" >搜索 药品产地：<span class="searchVal"></span></li>
									
							</ul>
						</div>
					</div>
				</div>
				<div style="width:100%;overflow-x:auto ">
			        <div class="tbl" >
			            <table id="medicGrid" style="display: block;margin: 0px;"></table>
			        </div>
			    </div>
	        </div>
		</div>
		
		
		 <%--新增编辑弹出框 --%>
		<div id="divSynToOther" title="选择病区" align="center" style="display: none;">
			<table style="width: 100%;" >
				<tr style="height:35px;">
					<td style="width: 10%;text-align: right;" >
						<input type="checkbox" class="chkAllArea" >
					</td>
					<td style="width: 40%;" >
						全部
					</td>
					<c:forEach items="${inpAreaList}" var="area" varStatus="rowStatus" >
					<c:if test="${rowStatus.index==0}">
					<td style="width: 10%;text-align: right;" >
						<input type="checkbox" class="chkOneArea" value="${area.deptCode}" deptcode="${area.deptCode}" deptname="${area.deptName}" >
					</td>
					<td style="width: 40%;" >
						${area.deptName}
					</td>
					</c:if>
					</c:forEach>
				</tr>
				
				<c:forEach items="${inpAreaList}" var="area" varStatus="rowStatus" >
				<c:if test="${rowStatus.index>0}">
				
				<c:if test="${rowStatus.index%2==1}">
				<tr style="height:35px;">
				</c:if>
				
					<td style="width: 10%;text-align: right;" >
						<input type="checkbox" class="chkOneArea" value="${area.deptCode}" deptcode="${area.deptCode}" deptname="${area.deptName}" >
					</td>
					<td style="width: 40%;" >
						${area.deptName}
					</td>
				
				<c:if test="${rowStatus.index%2==0}">
				</tr>
				</c:if>
				
				</c:if>
				</c:forEach>
				
			</table>
		</div>
</body>
</html>