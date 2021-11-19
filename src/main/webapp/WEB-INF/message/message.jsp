<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String msg = (String)request.getAttribute("msg");
	String url = (String)request.getAttribute("url");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Message.jsp</title>
    <script>
    	var msg = "<%=msg%>";
    	var url = "<%=url%>";
    	
    	if(msg == "memberDeleteOk") msg = "회원정보가 삭제 되었습니다!";
    	//else if(msg == "memberDeleteNo") msg = "회원정보가 삭제 되지 않았습니다.";
    	else if(msg == "memberJoinOk") msg = "회원 가입 되었습니다!";
    	else if(msg == "memberJoinNo") msg = "회원 가입 되지 않았습니다.";
    	else if(msg == "memberLoginOk") msg = "로그인 되었습니다!";
    	else if(msg == "memberLoginNo") msg = "로그인에 실패 했습니다.";
    	else if(msg == "memberLogoutOk") msg = "로그아웃 되었습니다.";
    	else if(msg == "memberLoginPwdNo") msg = "비밀번호가 틀렸습니다.";
    	else if(msg == "memberUpdateOk") msg = "정보가 수정 되었습니다!";
    	else if(msg == "memberUpdateNo") msg = "정보가 수정 되지 않았습니다.";
    	else if(msg == "MemberLevelChangeOk") msg = "회원등급이 변경 되었습니다.";
    	else if(msg == "MemberResetOk") msg = "회원정보가 DB에서 삭제 되었습니다.";
    
    	alert(msg);
    	if(url != "")location.href = url;
    </script>
</head>
<body>
</body>
</html>