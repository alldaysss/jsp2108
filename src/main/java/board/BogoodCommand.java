package board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BogoodCommand implements BoardInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idx = request.getParameter("idx")==null ? 0 : Integer.parseInt(request.getParameter("idx"));
		int pag = request.getParameter("pag")==null ? 0 : Integer.parseInt(request.getParameter("pag"));
		int pageSize = request.getParameter("pageSize")==null ? 5 : Integer.parseInt(request.getParameter("pageSize"));
		
		// 검색폼에서 값이 넘어올경우 ...
		String sw = request.getParameter("sw")==null ? "" : request.getParameter("sw");
		request.setAttribute("sw", sw);
		
		BoardDAO dao = new BoardDAO();
		
		// 좋아요 수 증가처리(좋아요 수 중복 방지)
		// 세션배열(객체배열:ArrayList()) : 고유세션아이디 + 'good' + '현재글의 고유번호'
		HttpSession session = request.getSession();
		ArrayList<String> goodIdx = (ArrayList) session.getAttribute("sGoodIdx");
		if(goodIdx == null) {
			goodIdx = new ArrayList<String>();
			session.setAttribute("sGoodIdx", goodIdx);
		}
		String imsiGoodIdx = session.getId() + "good" + idx;
		if(!goodIdx.contains(imsiGoodIdx)) {
			dao.setGoodUpdate(idx);
			goodIdx.add(imsiGoodIdx);
		}
		
		BoardVO vo = dao.getBoardContent(idx);
		
		request.setAttribute("vo", vo);
		request.setAttribute("pag", pag);
		request.setAttribute("pageSize", pageSize);
	}
}
