<%@page import="java.io.Writer"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	// 한글처리 
	//request.setCharacterEncoding("utf-8");
	String title = request.getParameter("title");
	String content = request.getParameter("content");

	String fileName = "outputFile.txt";
	
	PrintWriter writer = null;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>writeFormOk.jsp</title>
    <%@include file="../include/bs4.jsp" %>
</head>
<body>
<%@ include file="../include/header_home.jsp" %> 
<%@ include file="../include/nav.jsp" %> 
<p><br/></p>
	<div class="container">
		<h2>저장된 파일의 내용보기</h2>
<%
	try {
	  String filePath = application.getRealPath("/textfile/" + fileName);
	  writer = new PrintWriter(filePath);
	  writer.println("제목 : " + title);
	  writer.println("내용 : \n------------------------------------------\n" + content);
	  out.println( "<p>"+filePath+"저장되었습니다.</p>" ); // 브라우저에 쓰는건 out으로
	  out.println("<p><a href='writeform.jsp' class=btn btn-secondary>돌아가기</a></p>"); // 브라우저에 쓰는건 out으로

	} catch (IOException e) {
		out.println("파일에 데이터를 쓸수 없습니다.");
		out.println("<p><a href='writeform.jsp' class=btn btn-secondary>돌아가기</a></p>"); // 브라우저에 쓰는건 out으로
	} finally {
		try {
			writer.close();
		} catch (Exception e) {}
	}
%>		

	</div>
<br/>
</body>
</html>