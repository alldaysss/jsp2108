<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../include/sCheck.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>memMain.jsp</title>
    <%@include file="../../include/bs4.jsp" %>
</head>
<body>
<%@ include file="../../include/header_home.jsp" %> 
<%@ include file="../../include/nav.jsp" %> 
<p><br/></p>
	<div class="container">
		<h2>정규 회원방</h2>
		<hr/>
		<font color="blue">${sNickName}</font>님 로그인 중입니다.
		<p>현재 <font color="green"> ${strLevel}</font>입니다.</p>
		<p>최종 접속일 : <font color="green">${sLastDate}</font></p>
		<p>총 방문횟수:<font color="green"> ${visitCnt}</font></p>
		<p>오늘 방문횟수 <font color="green">${todayCnt}</font> </p>
		<p>포인트 합계 : <font color="green">${point}</font></p>
		<p>활동내역 : 
			방명록에 올린글수 :<font color="green">${guestCnt}개</font> <br/> <!-- 게시판에 글을 올릴 때 (전체) -->
			게시판에 올린글수 : __개 <br/>
			자료실 업로드 횟수 : __개 <br/>
		</p>
		<hr/>
	</div>
<br/>
<%@ include file="../../include/footer.jsp" %> 
</body>
</html>