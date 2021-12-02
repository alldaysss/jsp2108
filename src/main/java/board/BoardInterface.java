package board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BoardInterface {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	// 인터페이스는 작업 지시서 
	
}
