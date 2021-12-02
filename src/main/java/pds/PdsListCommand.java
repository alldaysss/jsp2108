package pds;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardVO;

public class PdsListCommand implements PdsInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String part = request.getParameter("part")== null ? "전체" : request.getParameter("part");
		
		PdsDAO dao = new PdsDAO();
		
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
		  int pag = request.getParameter("pag")==null ? 1 : Integer.parseInt(request.getParameter("pag"));	// 현재페이지 구하기
		  int pageSize = 5;				  					// 1. 한 페이지 분량
		  int totRecCnt = dao.totRecCnt(part);
		  int totPage = (totRecCnt % pageSize)==0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;  //3. 총 페이지 수를 구한다.		  
		  int startIndexNo = (pag - 1) * pageSize;			// 4. 현재페이지의 시작 index번호
		  int curScrStrarNo = totRecCnt - startIndexNo;	// 5. 현재 화면에 보이는 방문소감 시작번호
		  int blockSize = 3; // 한 블록의 크기를 3개의 page로 본다. (사용자가 지정한다.)
		  int curBlock = (pag - 1) / blockSize;
		  int lastBlock = (totPage % blockSize)==0 ? ((totPage / blockSize) -1) : (totPage / blockSize) ;
		  /* 블록페이징처리 끝 */
		  
		  // 최근글 수정시 아래 1줄 수정
		  List<PdsVO> vos = dao.getPdsList(startIndexNo, pageSize, part);

		    request.setAttribute("vos", vos);
			request.setAttribute("pag", pag);
			request.setAttribute("totPage", totPage);
			request.setAttribute("curScrStrarNo", curScrStrarNo);
			request.setAttribute("blockSize", blockSize);
			request.setAttribute("curBlock", curBlock);
			request.setAttribute("lastBlock", lastBlock);
			
			request.setAttribute("part", part);
	}

}
