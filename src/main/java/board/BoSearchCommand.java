package board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoSearchCommand implements BoardInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("search") == null ? "" : request.getParameter("search");
		String searchString= request.getParameter("searchString") == null ? "" : request.getParameter("searchString");
		
		BoardDAO dao = new BoardDAO();
		
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
	  int pag = request.getParameter("pag")==null ? 1 : Integer.parseInt(request.getParameter("pag"));	// 현재페이지 구하기
	  int pageSize = request.getParameter("pageSize")==null ? 5 : Integer.parseInt(request.getParameter("pageSize"));	// 1. 한 페이지 분량
	  int totRecCnt = dao.totRecCnt(search, searchString); // 검색기에 입력된 전체 레코드 수 구해오기
	  int totPage = (totRecCnt % pageSize)==0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;  //3. 총 페이지 수를 구한다.
	  int startIndexNo = (pag - 1) * pageSize;			// 4. 현재페이지의 시작 index번호
	  int curScrStrarNo = totRecCnt - startIndexNo;	// 5. 현재 화면에 보이는 방문소감 시작번호
	  int blockSize = 3; // 한 블록의 크기를 3개의 page로 본다. (사용자가 지정한다.)
	  int curBlock = (pag - 1) / blockSize;
	  int lastBlock = (totPage % blockSize)==0 ? ((totPage / blockSize) -1) : (totPage / blockSize) ;
	  /* 블록페이징처리 끝 */
	  
	  List<BoardVO> vos = dao.getBoardsearch(search, searchString,startIndexNo, pageSize); 
	  
		request.setAttribute("vos", vos);
		request.setAttribute("pag", pag);
		request.setAttribute("totPage", totPage);
		request.setAttribute("curScrStrarNo", curScrStrarNo);
		request.setAttribute("blockSize", blockSize);
		request.setAttribute("curBlock", curBlock);
		request.setAttribute("lastBlock", lastBlock);
		request.setAttribute("pageSize", pageSize);
		
		//검색기에서 추가
		String searchTitle = "";
		if(search.equals("title")) searchTitle = "글제목";
		else if(search.equals("nickName")) searchTitle = "글쓴이";
		else searchTitle = "글내용";
		
		request.setAttribute("searchTitle", searchTitle);
		request.setAttribute("search", search);
		request.setAttribute("searchString", searchString);
		request.setAttribute("searchCount", totRecCnt);
	}

}
