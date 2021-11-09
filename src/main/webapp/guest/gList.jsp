<%@page import="guest.GuestVO"%>
<%@page import="java.util.List"%>
<%@page import="guest.GuestDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	GuestDAO dao = new GuestDAO();
	List<GuestVO> vos = dao.gList();
	GuestVO vo = new GuestVO();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>gList.jsp(방명록리스트)</title>
    <%@include file="../include/bs4.jsp" %>
</head>
<body>
<%@ include file="../include/header_home.jsp" %> 
<%@ include file="../include/nav.jsp" %> 
<p><br/></p>
<div class="container">
	<table class="table table-borderless">
		<tr>
			<th><h2>방 명 록 리 스 트</h2></th>
		</tr>
		<tr>
			<td><a href="<%=request.getContextPath() %>/" class="btn btn-secondary">관 리 자</a></td>
			<td stlye="text-align:right;"><a href="<%=request.getContextPath() %>/guest/gInput.jsp" class="btn btn-secondary">글쓰기</a></td>
		</tr>
<%
	for(int i=0; i<vos.size(); i++) {
		vo = vos.get(i); // vo에 있는거 담는거
	String email = vo.getEmail();
		if(email.equals("") || email == null) email ="-없음-";
	String homepage = vo.getHomepage();
		// if(homepage.equals("") || homepage == null) homepage ="-없음-";
		
	String content = vo.getContent().replace("\n", "<br/>");
%>
			<tr>
				<td>
					방문번호 : <%=vo.getIdx() %>
				</td>
				<td style="text-align:right;">
					방문IP : <%=vo.getHostIp() %>
				</td>
			</tr>
		</table>
		<table class="table table-bordered">
			<tr>
				<th>성명</th>
				<td><%=vo.getName() %></td>
				<th>방문일자</th>
				<td><%=vo.getvDate() %> </td>
			</tr>
			<tr>
				<th>전자우편</th>
				<td colspan="3"><%=email %></td>
			</tr>
			<tr>
				<th>홈페이지</th>
				<td colspan="3">
				<%
					if(homepage.length() <=8) {
						out.println("-없음-");
					}
					else {
						out.println("<a href='"+homepage+"' target='_blank'>"+homepage+"</a>");
					}
				%>
				</td>
			</tr>
			<tr>
				<th>글 내용</th>
				<td colspan="3"><%=content %></td>
			</tr>
		</table>
		<br/>
<%} %>
</div>
<br/>
<%@ include file="../include/footer.jsp" %> 
</body>
</html>