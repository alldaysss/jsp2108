package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

public class memLogOutcommand implements MemberInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession Session = request.getSession();
		String nickName = (String) Session.getAttribute("sNickName");
		
		
		Session.invalidate();
		
		request.setAttribute("msg", "memberLogoutOk");
		request.setAttribute("url", request.getContextPath()+"/memLogin.mem");
		request.setAttribute("val", nickName);
	}

}
