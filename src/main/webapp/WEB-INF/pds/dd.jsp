<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>title</title>
    <%@include file="/include/bs4.jsp" %>
</head>
<body>
<%@ include file="/include/header_home.jsp" %> 
<jsp:include page="/include/nav.jsp"/>
<p><br/></p>
	<div class="container">
		<h2></h2>
	</div>
<br/>
<%@ include file="/include/footer.jsp" %> 
</body>
</html>