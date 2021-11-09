<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>adminLogin.jsp(관리자 인증차)</title>
    <%@include file="../include/bs4.jsp" %>
    <style>
    	table {
    		width: 400px;
    		margin: 0px auto;
    	}
    	table, th, td {
			border: 1px solid gray;
			padding: 15px;
		}
		th {
			background-color: #ddd;
			text-align: center;
		}
    
    </style>
</head>
<body>
<%@ include file="../include/header_home.jsp" %> 
<%@ include file="../include/nav.jsp" %> 
<p><br/></p>
	<div class="container">
		<h2>관리자 인증창</h2>
		<br/>
		<form name="myform" method="post" action="adminLoginOk.jsp">
		<table class="table">
			<tr>
				<th>관리자아이디</th>
				<td><input type="text" name="mid" required autofocus/></td>
			</tr>
			<tr>
				<th>관리자비밀번호</th>
				<td><input type="password" name="pwd" required/></td>

			</tr>
			<tr>
				<td colspan="2" style="text-align:center;">
					<input type="submit" value="관리자로그인"/>
					<input type="reset" value="다시입력"/>
					<button type="button" onclick="location.href='gList.jsp';">돌아가기</button>
			
				</td>
			</tr>
		</table>
		</form>
	</div>
<br/>
<%@ include file="../include/footer.jsp" %> 
</body>
</html>