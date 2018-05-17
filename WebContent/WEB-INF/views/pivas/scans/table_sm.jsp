<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<c:forEach items="${result}" var="result" varStatus="status" >
<tr>
<td width="35%">${result.smrq}</td>
<td width="25%">${result.ydpq}</td>
<c:choose> 
<c:when test="${result.smjg == 0}">
<td width="20%">扫描成功</td>
</c:when>
<c:otherwise>
<td width="20%">扫描失败</td>
</c:otherwise>
</c:choose>
<td width="20%">${result.operator}</td>
</tr>
</c:forEach>

</body>
</html>