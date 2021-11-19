<!-- sCheck.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String checkmid = (String) session.getAttribute("sMid");
	if(checkmid == null) {
		out.println("<script>");		
		out.println("alert('로그인 후 사용하세요!');");		
		out.println("location.href='"+request.getContextPath()+"/memLogin.mem';");		
		out.println("</script>");		
	}
%>