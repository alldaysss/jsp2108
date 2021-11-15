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
		else if(com.equals("/memberJoin")) {
			viewPage += "/memberJoin.jsp";
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
		else if(com.equals("/mbmJoinCheck")) {
			command = new mbmJoinCheckcommand();
			command.execute(request, response);
			viewPage = "WEB-INF/message/message.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
}
