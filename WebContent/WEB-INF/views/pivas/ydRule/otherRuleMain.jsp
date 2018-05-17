<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/views/common/common.jsp" %>

<!DOCTYPE html>
<html>
<head>

	<style type="text/css">
	
	</style>

	<script type="text/javascript">
	var _pageWidth = 0;
	var _pageHeight = 0;
	
	function resizePageSize(){
		_pageWidth = $(window).width();
		_pageHeight = $(window).height();
		
	}
	
	 $(function(){
			

	 });
	 

	 function upd(obj){
		 var orId = $(obj).attr("id").replace("rule_","");
		 var enabled = $(obj).attr("checked")=="checked"?"1":"0";
		 var param = {"orId":orId,"enabled":enabled}
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/ydRule/updOtherRule',
				dataType : 'json',
				cache : false,
				data : param,
				success : function(result) {
					if(result.code>0){
						<%--showAreaData();--%>
					}else{
						message({html: result.mess||"操作异常，请稍后再试"});
					}
				}
			});
	 }

		
	</script>
	
</head>
<body>

<div id="divMain1" style="width:100%;height:100%;" >
	
<table style="width:50%;margin-top: 10px;">

	<c:forEach items="${list}" var="row"  >
	<tr>
		<td style="width:10px;">
			<shiro:hasPermission name="PIVAS_BTN_673">
			<input type="checkbox" <c:if test="${row.enabled=='1'}">checked="checked"</c:if> id="rule_${row.orId}" onchange="upd(this)" >
			</shiro:hasPermission>
		</td>
		<td>
			<div>${row.orDesc}</div>
		</td>
	</tr>
	</c:forEach>

</table>


</div><%-- divMain1 --%>
	
	
</body>
</html>