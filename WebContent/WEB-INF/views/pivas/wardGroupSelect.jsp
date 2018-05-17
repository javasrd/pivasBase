<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style>
.checkBox{
float: left;
height: 30px;
margin-left: 50px;
margin-top: 2px;
}
.span1{
line-height: 30px;
float: left;
margin-left: 5px;
width: 150px;
text-align: left;
white-space: nowrap;
text-overflow: ellipsis;
overflow: hidden;
}
</style>
</head>
<body>
	<c:forEach items="${wardList}" var="wardList" varStatus="status" >
	<div style="width:220px;float:left;">
	<input type="checkbox" class="checkBox" value="${wardList.id}" 
	<c:if test="${wardList.state == 'true' }">checked</c:if>
	><label class="span1">${wardList.deptname}</label>
	</div>
	</c:forEach>
</body>
</html>