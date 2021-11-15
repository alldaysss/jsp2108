package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MemberInterface {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	// 인터페이스는 작업지시서
}
