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
<td width="15%">${result.yd_chargeCode}</td>
<td width="40%">${result.yd_drugname}</td>
<td width="20%">${result.yd_specifications}</td>
<td width="15%">${result.yd_dose}${result.yd_doseUnit}</td>
<td width="10%">${result.yd_quantity}${result.yd_medicamentsPackingUnit}</td>
</tr>
</c:forEach>
</body>
</html>