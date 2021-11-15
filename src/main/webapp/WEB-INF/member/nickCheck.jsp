<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String nickName = (String)request.getAttribute("nickName");
	int res = (int) request.getAttribute("res");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>nickCheck.jsp</title> <!-- 아주작은 자식창 -->
    <%@include file="../../include/bs4.jsp" %>
    <script>
    	function sendCheck() {
    		opener.window.document.myform.nickName.value = "<%=nickName %>";
    		opener.window.document.myform.name.focus();
    		window.close();
    	}
    	
    	function nickCheck() {
    		var nickName = childForm.nickName.value;
    		
    		if(nickName=="") {
    			alert("별명을 입력하세요") //주말에 정규식 체크
    			childForm.nickName.focus();
    		}
    		else {
    			childForm.submit();
    		}
    	}
    </script>
</head>
<body>
<p><br/></p>
	<div class="container">
		<h4>별명 체크 폼</h4> <!-- 닉네임체크 폼과 동일 -->
	<%if(res == 1) { %>
		<h4><font color="orange"><%=nickName %></font>별명은 사용가능 합니다.</h4>
		<p><input type="button" value="창닫기" onclick="sendCheck()"/> </p>
	<% } else { %>
		<h4><font color="red"><%=nickName %></font>이미 중복된 별명 입니다.</h4>
		<form name="childForm" method="post" action="<%=request.getContextPath()%>/nickCheck.mem";>
			<input type="text" name="nickname"/>
			<input type="button" value="별명검색" onclick="nickCheck()"/>
		</form>
	<% } %>
	</div>
<br/>
</body>
</html>