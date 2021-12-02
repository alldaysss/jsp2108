package study.pdsTest;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import study.StudyInterface;

public class FileDeleteCommand implements StudyInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("file");
		
		String realPath = request.getServletContext().getRealPath("/data/pdsTest/");
		
		File file = new File(realPath + fileName);
		
		if(file.exists()) {
			file.delete(); // 서버의 파일 시스템에서 생성된 객체 파일이 삭제 된다. (실제파일 삭제)
			response.getWriter().write("fileDeleteOk");
		}
		else {
			response.getWriter().write("fileDeleteNo");
		}
	}

}
