<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.zc.base.sys.common.constant.SysConstant"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.zc.base.common.exception.ExceptionCodeConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="/WEB-INF/views/common/common.jsp" %>

<!DOCTYPE html>
<html>
<head>

	<script type="text/javascript">
		function run(){
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/ydRule/ydRuleRun',
				dataType : 'json',
				cache : false,
				showDialog: false,
				data : {},
				success : function(result) {
					if(result.code>0){
						message({html: "已处理完成"});
					}else{
						message({html: result.mess||"操作异常，请稍后再试"});
					}
				}
			});
		}
	</script>
	
</head>
<body>


<input type="button" onclick="run()" value="优化批次">

</body>
</html>