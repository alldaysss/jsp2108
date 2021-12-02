package admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import admin.board.AdBoardListCommand;
import admin.member.AdContentCommand;
import admin.member.AdMemberInforCommand;
import admin.member.AdMemberLevelCommand;
import admin.member.AdMemberListCommand;
import admin.member.AdMemberResetCommand;

@WebServlet("*.ad")
public class AdminController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminInterface command = null;
		String viewPage = "/WEB-INF/admin";
		
		String uri = request.getRequestURI();
		String com = uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
		
		// admin이 아닌곳에서의 호출 처리 ...
		/*
		 * if(com.indexOf("?") != -1) { com = "/" +
		 * uri.substring(uri.lastIndexOf("?")+1, uri.lastIndexOf(".")); } else { com =
		 * "/" + uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf(".")); }
		 */
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("sMid");
		int level = session.getAttribute("sLevel")==null ? 99 :  (int) session.getAttribute("sLevel");
		if(mid == null || level >= 4) {
			viewPage = "/WEB_INF/member/memLogin.jsp";	
		}
		
		else if(com.equals("/adMenu")) {
			viewPage += "/adMenu.jsp";
		}
		else if(com.equals("/adLeft")) {
			viewPage += "/adLeft.jsp";
		}
		else if(com.equals("/adContent")) {
			command = new AdContentCommand();
			command.execute(request, response);
			viewPage += "/adContent.jsp";
		}
		else if(com.equals("/adMemberList")) {
			command = new AdMemberListCommand();
			command.execute(request, response);
			viewPage += "/member/adMemberList.jsp";
		}
		else if(com.equals("/memList")) {
			command = new AdMemberListCommand();
			command.execute(request, response);
			viewPage = "/WEB-INF/member/memList.jsp";
		}
		
		else if(com.equals("/adMemberLevel")) {
			command = new AdMemberLevelCommand();
			command.execute(request, response);
			viewPage = "/WEB-INF/message/message.jsp";
		}
		else if(com.equals("/adMemberInfor")) {
			command = new AdMemberInforCommand();
			command.execute(request, response);
			viewPage += "/member/adMemberInfor.jsp";
		}
		else if(com.equals("/memInfor")) {
			command = new AdMemberInforCommand();
			command.execute(request, response);
			viewPage = "/WEB-INF/member/memInfor.jsp";
		}
		else if(com.equals("/adMemberReset")) {
			command = new AdMemberResetCommand();
			command.execute(request, response);
			viewPage = "/WEB-INF/message/message.jsp";
		}
		else if(com.equals("/adBoardList")) {
			command = new AdBoardListCommand();
			command.execute(request, response);
			viewPage += "/board/adBoardList.jsp";
		}
		
		// 컨트롤러 다루는 순서 : jsp입력 받을것생성 -> 해당되는것 컨트롤러에 > 커맨드
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
}
