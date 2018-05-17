<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page
	import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../../common/common.jsp"%>

<html>
<link
	href="${pageContext.request.contextPath}/assets/sysCss/common/style.css"
	type="text/css" rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/assets/common/js/my97DatePicker/WdatePicker.js"></script>

<head>

<style type="text/css">
#qryRQ {
	width: 100px;
}
</style>

<script>
	
	var _gridWidth = 0;
	var _gridHeight = 0; 
	
	//页面自适应
	function resizePageSize(){
		//_gridWidth = $(document).width()-12;/*  -189 是去掉左侧 菜单的宽度，   -12 是防止浏览器缩小页面 出现滚动条 恢复页面时  折行的问题 */
		//_gridHeight = $(document).height()-32-100; /* -32 顶部主菜单高度，   -90 查询条件高度*/
		_gridWidth = $(document).width();
		var spit = (_gridWidth - 900)/2;
		if(spit > 0)
		{
			//$('#main-div').css("margin-left",spit*0.6);
			//$('#main-div').css("margin-right",spit*1.4);	
		}
		$("#main-div").css("max-height", $(window).height()-20);
	}
	
	function scansMissResize(o){
		var mainDoc = window.parent.document;
		if(parseFloat($(mainDoc).find('.left-menu').css("width")) <=0 )
		{
			$(mainDoc).find('.left-menu').css("width","204px");
			$(mainDoc).find('.dl-second-slib').css("left","198px").css("background","url('${pageContext.request.contextPath}/assets/pivas/images/left-slib.gif') no-repeat 0px center transparent");
			$(mainDoc).find('#mainFrame').css("width",$(window).width()-204-10);
			

		}

	}
	function dataPick(){
		window.location = "${pageContext.request.contextPath}/scans/initScans?qryRQ="+$("#qryRQ").val();
	}
	function scansMaxResize(){
		var mainDoc = window.parent.document;
		if(parseFloat($(mainDoc).find('.left-menu').css("width")) > 0)
		{
			$(mainDoc).find('.left-menu').css("width","0");
			$(mainDoc).find('.dl-second-slib').css("left","0").css("background","url('${pageContext.request.contextPath}/assets/pivas/images/left-slib.gif') no-repeat -6px center transparent");
			$(mainDoc).find('#mainFrame').css("width","100%");	
			//alert("aa");
			//alert($(mainDoc).find('#mainFrame').css("width"));
		}
	}
	
	
	function checkType(params)
	{
		var src = "${pageContext.request.contextPath}/scans/scansCheck?" + encodeURI(params);
		$('#scansCheckDialog').dialog('open').find('iframe').attr('src', src);
		$('#main-div').css("display","none");
		//scansMaxResize();
	}
//checkType2('${dataItem.pcname}','出仓扫描',1);
	function checkType2(batchName,checkName,checkType)
	{
		var src = "${pageContext.request.contextPath}/scans/scansCheck?batchName=" + encodeURI(batchName)+"&checkName="+encodeURI(checkName)+"&checkType="+checkType+"&qryRQ="+$("#qryRQ").val();
		var midFlag = ${midFlag};
		$('#scansCheckDialog').dialog('open').find('iframe').attr('src', src);
		if(checkType==0){
			if(midFlag){
				$('#scansCheckDialog').dialog({
					buttons: {
					'<spring:message code="scans.scansClose" />': function() {
						//scansMissResize();
						$(this).dialog('close');
						//location.reload();
						window.location = "${pageContext.request.contextPath}/scans/initScans?qryRQ="+$("#qryRQ").val();
						},
					'仓内扫描':function(){
						checkType2(batchName,'仓内扫描',1);
					}
					}
				});
			}else{
				$('#scansCheckDialog').dialog({
					buttons: {
					'<spring:message code="scans.scansClose" />': function() {
						//scansMissResize();
						$(this).dialog('close');
						//location.reload();
						window.location = "${pageContext.request.contextPath}/scans/initScans?qryRQ="+$("#qryRQ").val();
						},
					'出仓扫描':function(){
						checkType2(batchName,'出仓扫描',2);
					}
					}
					
				});
			}
		}else if(checkType==1){
			$('#scansCheckDialog').dialog({
				buttons: {
				'<spring:message code="scans.scansClose" />': function() {
					//scansMissResize();
					$(this).dialog('close');
					//location.reload();
					window.location = "${pageContext.request.contextPath}/scans/initScans?qryRQ="+$("#qryRQ").val();
					},
				'出仓扫描':function(){
					checkType2(batchName,'出仓扫描',2);
				}
				}
				
			});
		}else{
			$('#scansCheckDialog').dialog({
				buttons: {
				'<spring:message code="scans.scansClose" />': function() {
					//scansMissResize();
					$(this).dialog('close');
					//location.reload();
					window.location = "${pageContext.request.contextPath}/scans/initScans?qryRQ="+$("#qryRQ").val();
					}
				}
				
			});
		}
		
		$('#main-div').css("display","none");
		//scansMaxResize();
	}
	
	$(function() {
		//$("#qryRQ").val(getCurrentDate("yyyy-MM-dd"));
		$("#qryRQ").val('${qryRQ}');
		
		resizePageSize();
		$(window).resize(function(){
			resizePageSize();
		});
		$('#scansCheckDialog').dialog({
			title: '仪表管理',
			autoOpen: false,
			width: $(window).width(),
			height: $(window).height()-6,/*0115bianxw修改 -6*/
			modal: true,
			resizable: false
		}).prev().hide().parent().css({
			border: 'none',
			borderRadius: 0
		});
		

	});
	
	
		//resizePageSize();

	</script>
</head>
<body onload="resizePageSize()">

	<div class="main-div"
		style="width: 1200px; margin-bottom: 10px; overflow: auto;"
		id="main-div">

		<br>
		<div
			style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px; width: 200px; color: #8DA3D2; font-size: 14px;">
			用药日期：<input type="text" id="qryRQ"
				style="color: #555555; height: 26px; width: 100px; min-width: 100px;"
				class="Wdate" empty="false" readonly="readonly"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:dataPick});">

		</div>

		<c:if test="${scansBatchList2 == null }">
			<div
				style="float: left; margin: 0px 15px; position: Relative; padding: 0; clear: center">
				<spring:message code='scans.PQ.nodata' />
			</div>
		</c:if>
		
		<c:choose> 
			 <c:when test="${typeSize == 0}">   
			   <div style="float: left; margin: 0px 15px; position: Relative; padding: 0; clear: center">
				没有匹配的核对类型
			</div>
			 </c:when> 
			 <c:otherwise>   
			 	<!-- start 批次数据表列表 -->
		<c:forEach items="${scansBatchList2}" var="dataItem" varStatus="dataStatus">
			<div style=
			<c:choose> 
			 <c:when test="${dataStatus.index%2==0}">   
				"float: left; margin: 0px 15px; position: Relative; padding: 0; clear: left"
			 </c:when> 
			 <c:otherwise>
			   <c:if test="${typeSize == 1 }">
				"float: left; margin: 0px 300px; position: Relative; padding: 0;"
			   </c:if>
			   <c:if test="${typeSize == 2 }">
				"float: left; margin: 0px 50px; position: Relative; padding: 0;"
			   </c:if>
			   <c:if test="${typeSize == 3 }">
				"float: left; margin: 0px 15px; position: Relative; padding: 0;clear: left;"
			   </c:if>
			  </c:otherwise> 
			</c:choose>
			>
				
				<c:if test="${inFlag}">
				<div style="color: #8DA3D2; font-size: 20px; margin-left: 5px">${dataItem.pcname}
				</div>
				<div
					style="float: left; width: 200px; border: 1px solid #ccc; background-color: #FFFFFF; -webkit-border-radius: 20px; margin: 10px 5px;">
					<!-- 0115bianxw修改 width:338px; -->
					<div
						style="height: 40px; background-color: #E5E5E5; border-radius: 20px 20px 0px 0px; cursor: pointer;"
						onclick="checkType2('${dataItem.pcname}','进仓核对',0);">
						<%-- onclick="checkType2('${dataItem.pcname}','<c:choose><c:when test="${dataItem.pcname!='上午' && dataItem.pcname!='下午' }">进仓核对</c:when><c:otherwise>核对</c:otherwise></c:choose>',0);"> --%>
						<div
							style="float: left; height: 40px; line-height: 40px; font-weight: bold; margin-left: 10px;">
							进仓核对
							<%-- <c:choose>
								<c:when
									test="${dataItem.pcname!='上午' && dataItem.pcname!='下午' }">进仓核对</c:when>
								<c:otherwise>核对</c:otherwise>
							</c:choose> --%>
						</div>
					</div>
					<div style="float: left;">
						<div style="float: left; font-size: 12px; margin: 25px 30px">
							待扫描</div>
						<div
							style="float: left; font-size: 12px; border: 1px solid #ccc; width: 100px; margin-left: -23px; margin-top: 20px; height: 26px;">
							${dataItem.inNeed}</div>
					</div>
					<div style="float: left;">
						<div style="float: left; font-size: 12px; margin: 0px 30px">
							总数量</div>
						<div
							style="float: left; font-size: 12px; border: 1px solid #ccc; width: 100px; margin-left: -23px; margin: 0px 0px 40px -23px; height: 26px;">
							${dataItem.inAll}</div>
					</div>
				</div>
				</c:if>
				
				<c:if test="${typeSize >= 2}">
				<%-- <c:if test="${dataItem.pcname!='上午' && dataItem.pcname!='下午' }"> --%>
				   <div
						style="float: left; width: 60px; margin: 0; padding: 0; margin: 15px 5px">
						<div style="height: 105px;">
							<img
								src="${pageContext.request.contextPath}/assets/sysImage/scans/gray.png">
						</div>
						<div style="height: 60px;">
							<img
								src="${pageContext.request.contextPath}/assets/sysImage/scans/pink.png">
						</div>
					</div>
				</c:if>
				
				<c:if test="${midFlag}">
					<div
						style="float: left; width: 200px; border: 1px solid #ccc; background-color: #FFFFFF; -webkit-border-radius: 20px; margin: 10px 5px;">
						<!-- 0115bianxw修改 width:338px; -->
						<div
							style="height: 40px; background-color: #CCCCFF; border-radius: 20px 20px 0px 0px; cursor: pointer;"
							onclick="checkType2('${dataItem.pcname}','仓内核对',1);">
							<div
								style="float: left; height: 40px; line-height: 40px; font-weight: bold; margin-left: 10px;">
								仓内核对</div>
						</div>
						<div style="float: left;">
							<div style="float: left; font-size: 12px; margin: 25px 30px">
								待扫描</div>
							<div
								style="float: left; font-size: 12px; border: 1px solid #ccc; width: 100px; margin-left: -23px; margin-top: 20px; height: 26px;">
								${dataItem.midNeed}</div>
						</div>
						<div style="float: left;">
							<div style="float: left; font-size: 12px; margin: 0px 30px">
								总数量</div>
							<div
								style="float: left; font-size: 12px; border: 1px solid #ccc; width: 100px; margin-left: -23px; margin: 0px 0px 40px -23px; height: 26px;">
								${dataItem.midAll}</div>
						</div>
					</div> 
				</c:if>
				<c:if test="${typeSize == 3}">
					<div
						style="float: left; width: 60px; margin: 0; padding: 0; margin: 15px 5px">
						<div style="height: 105px;">
							<img
								src="${pageContext.request.contextPath}/assets/sysImage/scans/gray.png">
						</div>
						<div style="height: 60px;">
							<img
								src="${pageContext.request.contextPath}/assets/sysImage/scans/pink.png">
						</div>
					</div>
				</c:if>
				
				<c:if test="${outFlag}">
					<div
						style="float: left; width: 200px; border: 1px solid #ccc; background-color: #FFFFFF; -webkit-border-radius: 20px; margin: 10px 5px;">
						<!-- 0115bianxw修改 width:338px; -->
						<div
							style="height: 40px; background-color: #FFC7C8; border-radius: 20px 20px 0px 0px; cursor: pointer;"
							onclick="checkType2('${dataItem.pcname}','出仓核对',2);">
							<div
								style="float: left; height: 40px; line-height: 40px; font-weight: bold; margin-left: 10px;">
								出仓核对</div>
						</div>
						<div style="float: left;">
							<div style="float: left; font-size: 12px; margin: 25px 30px">
								待扫描</div>
							<div
								style="float: left; font-size: 12px; border: 1px solid #ccc; width: 100px; margin-left: -23px; margin-top: 20px; height: 26px;">
								${dataItem.outNeed}</div>
						</div>
						<div style="float: left;">
							<div style="float: left; font-size: 12px; margin: 0px 30px">
								总数量</div>
							<div
								style="float: left; font-size: 12px; border: 1px solid #ccc; width: 100px; margin-left: -23px; margin: 0px 0px 40px -23px; height: 26px;">
								${dataItem.outAll}</div>
						</div>
					</div>
				</c:if>	
					
			</div>
			
			<c:if test="${typeSize >= 1 &&  typeSize <= 2}">
			<c:if test="${dataStatus.index%2 !=0}">
				<div
					style="float: left; margin: 0px 15px; position: Relative; padding: 0; clear: left;">
					<div
						style="float: left; width: 1000px; border: 1px dashed #e5e5e5; background-color: #FFFFFF; -webkit-border-radius: 20px; margin: 10px 5px">
						<hr />
					</div>
				</div>
			</c:if>
			</c:if>
			
			<c:if test="${typeSize == 3}">
			<c:if test="${!dataStatus.last}">
				<div
					style="float: left; margin: 0px 15px; position: Relative; padding: 0; clear: left;">
					<div
						style="float: left; width: 1000px; border: 1px dashed #e5e5e5; background-color: #FFFFFF; -webkit-border-radius: 20px; margin: 10px 5px">
						<hr />
					</div>
				</div>
			</c:if>
			</c:if>
			
			

			<!-- end 批次数据表列表-->
		</c:forEach>
		<!-- 重复 -->
			   
		</c:otherwise> 
		</c:choose> 
		

	</div>

	<!-- 扫描 -->
	<div id="scansCheckDialog" align="center"
		style="display: none; padding: 0; margin-top: 3px; overflow: auto;" autoDestroy >
		<iframe frameborder="0" marginheight="0" marginwidth="0" style="width: 100%; height: 100%;"></iframe>
	</div>
</body>

</html>