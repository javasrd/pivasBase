<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">

.subUL{
	float:left;
	margin:0;
}
.span3 {
	padding-left:15px !important;
	word-break:break-all;
	float:left;
	width:120px;
}
	.span-border-radius{
		/*border: 1px solid #ddd;*/
		width: 30px;
		text-align: center;
		color:#fff;
	}
#bottomMenu li span.span-border-radius{
	color:#fff;
}
</style>
</head>
<body>
<ul style="margin:15px 17px;">
<!--<li><span class="span1" style="margin-left: 5px;width: 130px;">名称</span><span class="span2">数量</span></li>-->
<c:forEach items="${menuList}" var="menuList" varStatus="status" >
<c:choose> 
  <c:when test="${status.first}">
  	<li><a onclick="checkBox(this,0)"><span class="span1"><input id="inpatient_area_id" type="checkbox" data-value="${menuList.deptcode}" data-state="${menuList.state}" data-level="${menuList.levelnum}" style="float:left;" checked="checked" onclick="clickBox(this)" >${menuList.deptname}</span><span class="span2 span-border-radius">${menuList.num1}/${menuList.num2}</span></a>
  	</li>
  </c:when> 
  <c:otherwise>   
  	<li><a onclick="checkBox(this,1)"><span class="span1"><input type="checkbox" data-value="${menuList.deptcode}" data-state="${menuList.state}" data-level="${menuList.levelnum}" style="float:left;" checked="checked" onclick="clickBox(this)" >${menuList.deptname}</span><span class="span2 span-border-radius">${menuList.num1}/${menuList.num2}</span></a>
  	<c:if test="${menuList.subMenuList ne null}">
  	<ul class="subUL">
  	<c:forEach items="${menuList.subMenuList}" var="subList" varStatus="subStatus" >
  	<li><a onclick="checkBox(this,1)"><span class="span3"><input type="checkbox" data-value="${subList.deptcode}" data-state="${subList.state}" data-level="${subList.levelnum}" style="float:left;" checked="checked" onclick="clickBox(this)" >${subList.deptname}</span><span class="span2 span-border-radius">${subList.num1}/${subList.num2}</span></a>
  	</li>
  	</c:forEach>
  	</ul>
  	</c:if>
  	</li>
  </c:otherwise> 
</c:choose> 
</c:forEach>
</ul>
</body>
<script>
function clickBox(e){
	var checked = $(e).is(':checked');
	if(checked){
		$(e).attr("checked",false);
	}else{
		$(e).attr("checked",true);
	}
}
</script>
</html>