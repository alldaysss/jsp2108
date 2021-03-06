package member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.mem")
public class MemberController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberInterface command = null;
		String viewPage = "/WEB-INF/member";
		String uri = request.getRequestURI();
		String com = uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf(".")); //.과 확장자를 제외한 인덱스를 가지고 와라
		
		if(com.equals("/memLogin")) {
			viewPage += "/memLogin.jsp";
		}
		else if(com.equals("/memLoginOk")) {
			command = new memLoginOkcommand();
			command.execute(request, response);
			viewPage = "WEB-INF/message/message.jsp";
		}
		else if(com.equals("/memLogOut")) {
			command = new memLogOutcommand();
			command.execute(request, response);
			viewPage = "WEB-INF/message/message.jsp";
		}
		else if(com.equals("/memJoin")) {
			viewPage += "/memJoin.jsp";
		}
		else if(com.equals("/memJoinOk")) {
			command = new memJoinOkcommand();
			command.execute(request, response);
			viewPage = "WEB-INF/message/message.jsp";
		}
		else if(com.equals("/idCheck")) {
			command = new IdCheckcommand();
			command.execute(request, response);
			viewPage += "/idCheck.jsp";
		}
		else if(com.equals("/nickCheck")) {
			command = new nickCheckcommand();
			command.execute(request, response);
			viewPage += "/nickCheck.jsp";
		}
		else if(com.equals("/memMain")) {
			command = new memMaincommand();
			command.execute(request, response);
			viewPage += "/memMain.jsp";
		}	
		else if(com.equals("/memUpdate")) {
			command = new MemUpdatecommand();
			command.execute(request, response);
			viewPage += "/memUpdate.jsp";
		}
		else if(com.equals("/memUpdateOk")) {
			command = new MemUpdateOkcommand();
			command.execute(request, response);
			viewPage = "WEB-INF/message/message.jsp";
		}
		else if(com.equals("/memDelete")) {
			command = new MemDeletecommand();
			command.execute(request, response);
			viewPage = "WEB-INF/message/message.jsp";
		}
//		else if(com.equals("/memList")) {
//			command = new AdMemberListCommand();
//			command.execute(request, response);
//			viewPage += "/memList.jsp";
//		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
}
