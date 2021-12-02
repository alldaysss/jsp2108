package member;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class memJoinOkcommand implements MemberInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = request.getServletContext();
		String realPath = application.getRealPath("/data/pdsTest");	// 파일 업로드 위치는 ContextPath아래 '/data/member'로 한다.
		int maxSize = 1024 * 1024 * 2;	// 업로드할 최대 용량은 2MByte로 한다.
		String encoding = "UTF-8";
		
		MultipartRequest multipartRequest = new MultipartRequest(request, realPath, maxSize, encoding, new DefaultFileRenamePolicy());	// 파일 업로드 완료...
		
		
		String mid = multipartRequest.getParameter("mid")==null ? "" : multipartRequest.getParameter("mid").trim();
		String pwd = multipartRequest.getParameter("pwd")==null ? "" : multipartRequest.getParameter("pwd").trim();
		String nickName = multipartRequest.getParameter("nickName")==null ? "" : multipartRequest.getParameter("nickName").trim();
		String name = multipartRequest.getParameter("name")==null ? "" : multipartRequest.getParameter("name").trim();
		String name_ = name;
		String email1 = multipartRequest.getParameter("email1")==null ? "" : multipartRequest.getParameter("email1").trim();
		String email2 = multipartRequest.getParameter("email2")==null ? "" : multipartRequest.getParameter("email2").trim();
		String email = email1 + "@" + email2;
		String gender = multipartRequest.getParameter("gender")==null ? "" : multipartRequest.getParameter("gender").trim();
		String birthday = multipartRequest.getParameter("birthday")==null ? "" : multipartRequest.getParameter("birthday").trim();
		String tel1 = multipartRequest.getParameter("tel1")==null ? "" : multipartRequest.getParameter("tel1").trim();
		String tel2 = multipartRequest.getParameter("tel2")==null ? "" : multipartRequest.getParameter("tel2").trim();
		String tel3 = multipartRequest.getParameter("tel3")==null ? "" : multipartRequest.getParameter("tel3").trim();
		String tel = tel1 + "-" + tel2 + "-" + tel3;
		String address = multipartRequest.getParameter("address")==null ? "" : multipartRequest.getParameter("address");
		String homePage = multipartRequest.getParameter("homePage")==null ? "" : multipartRequest.getParameter("homePage").trim();
		String job = multipartRequest.getParameter("job")==null ? "" : multipartRequest.getParameter("job").trim();
		String userInfor = multipartRequest.getParameter("userInfor")==null ? "" : multipartRequest.getParameter("userInfor");
		String[] hobbys = multipartRequest.getParameterValues("hobby"); // 배열로 들어온것 처리 여러개 선택 시 
		String hobby = ""; // 누적변수는 공백만 주면 됩니다.
		for(int i=0 ; i<hobbys.length; i++) {
			hobby += hobbys[i] + "/"; // 배열에 계속 넣어주는 방식으로
		}
		hobby = hobby.substring(0, hobby.lastIndexOf("/"));
		String content = multipartRequest.getParameter("content")==null ? "" : multipartRequest.getParameter("content");
		
		// 회원사진이 업로드 되었을때 처리
		String photo = multipartRequest.getParameter("photo");
		String filesystemName = "";
		if(!photo.equals("noimage")) {
			filesystemName = multipartRequest.getFilesystemName("fName");			// 실제 서버에 저장되는 파일명
		}
		else {
			filesystemName = "noimage.jpg";
		}
		
		// 아이디와 닉네임 중복체크를 저장전에 한번 더 수행시켜 준다.
		MemberDAO dao = new MemberDAO();
		
		name = dao.idCheck(mid);
		if(!name.equals("")) { // 사용 가능한 아이디입니다.
			request.setAttribute("msg", "IdCheckNo"); // (이미 사용중인 아이디) 사용불가임
			request.setAttribute("url", request.getContextPath()+"/memJoin.mem");
			return;
		}
		
		// 닉네임 체크 하기
		name = dao.nickCheck(nickName);
		if(!name.equals("")) { // 사용 가능한 별명입니다.
			request.setAttribute("msg", "nickCheckNo"); // (이미 사용중인 닉네임) 사용불가임
			request.setAttribute("url", request.getContextPath()+"/memJoin.mem");
			return;
		}
		
		// DB에 저장된 각각의 필드의 길이 체크..
		
		// 비밀번호 암호화 처리
		long intPwd;
		String strPwd = "";
		for(int i=0; i<pwd.length(); i++) {
			intPwd = (long)pwd.charAt(i);
			strPwd += intPwd;
		}
		intPwd = Long.parseLong(strPwd); // DB에 넣었던 stePwd를 다시 불러서 복호화를 위해 정수형을 변환했다.
		
		int pwdKey = (int)(Math.random()*20); // 강제 형변환 시켜줌
		long pwdValue = dao.gethashTableSearch(pwdKey);
		
		long encPwd;
		
		// 암호화 작업(인코딩)
		encPwd = intPwd ^ pwdValue; // 원본비번과 암호키값을 배타적OR(exclusive OR)시킨다.
		strPwd = String.valueOf(encPwd); // DB에 저장하기 위해 문자로 변환했다.
		
		// 모든 체크 완료 후 정확 한 회원정보를 DB에 저장할 준비(vo)를 한다.
		MemberVO vo = new MemberVO();
		vo.setMid(mid);
		vo.setPwd(strPwd);
		vo.setPwdKey(pwdKey);
		vo.setNickName(nickName);
		vo.setName(name_);
		vo.setGender(gender);
		vo.setBirthday(birthday);
		vo.setTel(tel);
		vo.setAddress(address);
		vo.setEmail(email);
		vo.setHomePage(homePage);
		vo.setJob(job);
		vo.setHobby(hobby);
		vo.setPhoto(filesystemName);// 이미지 처리....
		vo.setContent(content);
		vo.setUserInfor(userInfor);
		
		int res = dao.setMemberJoinOk(vo);
		
		if(res == 1) { // 정상적으로 회원가입 완료
			request.setAttribute("msg", "memberJoinOk");
			request.setAttribute("url", request.getContextPath()+"/memLogin.mem");
		}
		else {
			request.setAttribute("msg", "memberJoinNo");
			request.setAttribute("url", request.getContextPath()+"/memJoin.mem");
		}
			
	}

}
