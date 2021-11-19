package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conn.Getconn;

public class MemberDAO {
	Getconn getconn = Getconn.getInstance();
	
	private Connection conn = getconn.getconn();
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String sql = "";
	
	MemberVO vo = null;
	
	// 아이디 중복 체크
	public String idCheck(String mid) {
		String name = "";
		try {
			sql = "select * from member where mid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();
			if(rs.next()) name = rs.getString("name");
		} catch (SQLException e) {
			System.out.println("SQL 오류 : " + e.getMessage());
		} finally {
			getconn.rsClose();
		}
		return name;
	}
	//닉네임 중복 체크
	public String nickCheck(String nickName) {
		String name = "";
		
		try {
			sql = "select * from member where nickName = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickName);
			rs = pstmt.executeQuery();
			if(rs.next()) name = rs.getString("name");
		} catch (SQLException e) {
			System.out.println("SQL 오류 : " + e.getMessage());
		} finally {
			getconn.rsClose();
		}
		return name;
	}
	
	// hashTable에서 비밀번호 pwdKey에 해당하는 pwdValue을 찾아서 돌려준다.
	public long gethashTableSearch(int pwdKey) {
		long pwdValue = 0;
		try {
			sql = "select * from hashTable where pwdkey = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pwdKey);
			rs = pstmt.executeQuery();
			rs.next();
			pwdValue = rs.getLong("pwdValue");
		} catch (SQLException e) {
			System.out.println("SQL 오류 : " + e.getMessage());
		} finally {
			getconn.rsClose();
		}
		
		return pwdValue;
	}
	
	// 신규 회원가입 처리
	public int setMemberJoinOk(MemberVO vo) {
		int res = 0;
		try {
			sql = "insert into member values (default,?,?,?,?,?,?,?,?,?,?,?,?,?,default,?,?,default,default,default,default,default,default,default)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getPwd());
			pstmt.setInt(3, vo.getPwdKey());
			pstmt.setString(4, vo.getNickName());
			pstmt.setString(5, vo.getName());
			pstmt.setString(6, vo.getGender());
			pstmt.setString(7, vo.getBirthday());
			pstmt.setString(8, vo.getTel());
			pstmt.setString(9, vo.getAddress());
			pstmt.setString(10, vo.getEmail());
			pstmt.setString(11, vo.getHomePage());
			pstmt.setString(12, vo.getJob());
			pstmt.setString(13, vo.getHobby());
			pstmt.setString(14, vo.getContent());
			pstmt.setString(15, vo.getUserInfor());
			pstmt.executeUpdate();
			res = 1;
		} catch (SQLException e) {
			System.out.println("SQL 오류 : " + e.getMessage());
		} finally {
			getconn.pstmtClose();
		}
		return res;
	}
	// 로그인체크(아이디가 동일한 자료가 있으면 해당 자료를 Vo에 담아서 넘긴다)
	public MemberVO loginCheck(String mid) {
		vo = new MemberVO();
		try {
			sql = "select * from member where mid = ? and userDel = 'NO'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setMid(mid);
				vo.setPwd(rs.getString("pwd"));
				vo.setPwdKey(rs.getInt("pwdKey"));
				vo.setNickName(rs.getString("nickName"));
				vo.setLevel(rs.getInt("level"));
				vo.setLastDate(rs.getString("lastDate"));
				vo.setPoint(rs.getInt("point"));
				vo.setTodayCnt(rs.getInt("todayCnt"));
				
				// 회원정보 수정을 위해서 member.sql에 있는 모든 정보를 다 담아서 넘겨준다.
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));
				vo.setGender(rs.getString("gender"));
				vo.setBirthday(rs.getString("birthday"));
				vo.setTel(rs.getString("tel"));
				vo.setAddress(rs.getString("address"));
				vo.setHomePage(rs.getString("homePage"));
				vo.setJob(rs.getString("job"));
				vo.setHobby(rs.getString("hobby"));
				vo.setContent(rs.getString("content"));
				vo.setUserInfor(rs.getString("userInfor"));
			}
			else {
				vo = null;
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류 : " + e.getMessage());
		} finally {
			getconn.rsClose();
		}
		
		return vo;
	}
	public void setLastDateUpdate(String mid, int newPoint, int todayCnt) {
		try {
			sql = "update member set lastDate = now(), point = point + ?, visitCnt = visitCnt + 1, todayCnt = ? where mid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			pstmt.setInt(1, newPoint);
			pstmt.setInt(2, todayCnt);
			pstmt.setString(3, mid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 오류 : " + e.getMessage());
		} finally {
			getconn.pstmtClose();
		}
	}
	
	// 로그인사용자의 접속정보 가져오기(총방문회수, 오늘 방문횟수)
		public MemberVO getUserInfor(String mid) {
			vo = new MemberVO();
			try {
				sql = "select * from member where mid = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, mid);
				rs = pstmt.executeQuery();
				rs.next();

				vo.setVisitCnt(rs.getInt("visitCnt"));
				vo.setTodayCnt(rs.getInt("todayCnt"));
				vo.setPoint(rs.getInt("point"));
				vo.setName(rs.getString("Name"));
			} catch (SQLException e) {
				System.out.println("SQL 오류 : " + e.getMessage());
			} finally {
				getconn.rsClose();
			}
			return vo;
		}
		public int setMemberUpdateOk(MemberVO vo) {
			int res = 0;
			try {
				sql = "update member set pwd=?,nickName=?,name=?,email=?,gender=?,"
					+ "birthday=?, tel=?, address=?, homePage=?, job=?, hobby=?,"
					+ "content=?, userInfor=?, pwdKey=? where mid=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getPwd());
				pstmt.setString(2, vo.getNickName());
				pstmt.setString(3, vo.getName());
				pstmt.setString(4, vo.getEmail());
				pstmt.setString(5, vo.getGender());
				pstmt.setString(6, vo.getBirthday());
				pstmt.setString(7, vo.getTel());
				pstmt.setString(8, vo.getAddress());
				pstmt.setString(9, vo.getHomePage());
				pstmt.setString(10, vo.getJob());
				pstmt.setString(11, vo.getHobby());
				pstmt.setString(12, vo.getContent());
				pstmt.setString(13, vo.getUserInfor());
				pstmt.setInt(14, vo.getPwdKey());
				pstmt.setString(15, vo.getMid());
				pstmt.executeUpdate();
				res = 1;
			} catch (SQLException e) {
				System.out.println("SQL 오류 : " + e.getMessage());
			} finally {
				getconn.pstmtClose();
			}
			return res;
		}
		
		// 회원 탈퇴처리(UserDel값을 'OK'로 변경한다.)
		public void memberDelete(String mid) {
			try {
				sql = "update member set userDel = 'OK' where mid = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, mid);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("SQL 오류 : " + e.getMessage());
			} finally {
				getconn.pstmtClose();
			}
		}
		
		// 신규회원(준회원)의 갯수 가져오기
		public int getNewMember() {
			int newMember = 0;
			try {
				sql = "select count(*) from member where level = 1";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rs.next();
				newMember = rs.getInt(1);
						
			} catch (SQLException e) {
				System.out.println("SQL 오류 : " + e.getMessage());
			} finally {
				getconn.rsClose();
			}
			return newMember;
		}
		// 회원 전체 리스트 가져오기		
		public ArrayList<MemberVO> getMemberList(int startIndexNo, int pageSize, int level, String mid) {
			ArrayList<MemberVO> vos = new ArrayList<MemberVO>();
			try {
				if(level == 99 && mid.equals("") ) {
					sql = "select * from member order by idx desc limit ?, ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, startIndexNo);
					pstmt.setInt(2, pageSize);
				}
				else if(level != 99 && mid.equals("")){
					sql = "select * from member where level = ? order by idx desc limit ?, ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, level);
					pstmt.setInt(2, startIndexNo);
					pstmt.setInt(3, pageSize);
				}
				else {
					sql = "select * from member where mid like ? order by idx desc limit ?, ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%"+mid+"%"); 
					pstmt.setInt(2, startIndexNo);
					pstmt.setInt(3, pageSize);
				}
				
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					vo = new MemberVO();
					vo.setIdx(rs.getInt("idx"));
					vo.setMid(rs.getString("mid"));
					vo.setPwd(rs.getString("pwd"));
					vo.setPwdKey(rs.getInt("pwdKey"));
					vo.setNickName(rs.getString("nickName"));
					vo.setName(rs.getString("name"));
					vo.setGender(rs.getString("gender"));
					vo.setBirthday(rs.getString("birthday"));
					vo.setTel(rs.getString("tel"));
					vo.setAddress(rs.getString("address"));
					vo.setEmail(rs.getString("email"));
					vo.setHomePage(rs.getString("homePage"));
					vo.setJob(rs.getString("job"));
					vo.setHobby(rs.getString("hobby"));
					vo.setPhoto(rs.getString("photo"));
					vo.setContent(rs.getString("content"));
					vo.setUserInfor(rs.getString("userInfor"));
					vo.setUserDel(rs.getString("userDel"));
					vo.setPoint(rs.getInt("point"));
					vo.setLevel(rs.getInt("level"));
					vo.setVisitCnt(rs.getInt("visitCnt"));
					vo.setLastDate(rs.getString("lastDate"));
					vo.setStartDate(rs.getString("startDate"));
					vo.setTodayCnt(rs.getInt("todayCnt"));
					vos.add(vo);
				}
			} catch (SQLException e) {
				System.out.println("SQL 오류 : " + e.getMessage());
			} finally {
				getconn.rsClose();
			}
			return vos;
		}
		// 개별정보 상세보기 처리
		public MemberVO getMemberInfor(int idx) {
			vo = new MemberVO();
			try {
				sql = "select * from member where idx =?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, idx);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					vo.setIdx(rs.getInt("idx"));
					vo.setMid(rs.getString("mid"));
					vo.setPwd(rs.getString("pwd"));
					vo.setPwdKey(rs.getInt("pwdKey"));
					vo.setNickName(rs.getString("nickName"));
					vo.setName(rs.getString("name"));
					vo.setGender(rs.getString("gender"));
					vo.setBirthday(rs.getString("birthday"));
					vo.setTel(rs.getString("tel"));
					vo.setAddress(rs.getString("address"));
					vo.setEmail(rs.getString("email"));
					vo.setHomePage(rs.getString("homePage"));
					vo.setJob(rs.getString("job"));
					vo.setHobby(rs.getString("hobby"));
					vo.setPhoto(rs.getString("photo"));
					vo.setContent(rs.getString("content"));
					vo.setUserInfor(rs.getString("userInfor"));
					vo.setUserDel(rs.getString("userDel"));
					vo.setPoint(rs.getInt("point"));
					vo.setLevel(rs.getInt("level"));
					vo.setVisitCnt(rs.getInt("visitCnt"));
					vo.setLastDate(rs.getString("lastDate"));
					vo.setStartDate(rs.getString("startDate"));
					vo.setTodayCnt(rs.getInt("todayCnt"));
				}
			} catch (SQLException e) {
				System.out.println("SQL 오류 : " + e.getMessage());
			} finally {
				getconn.rsClose();
			}
			return vo;
		}
		// 페이징처리를 위한 총 회원수 구하기
		public int totRecCnt(int level, String mid) {
			int totRecCnt = 0;
			try {
				if(level == 99 && mid.equals("") ) {
					sql = "select count(*) from member";
					pstmt = conn.prepareStatement(sql);
				}
				else if(level != 99 && mid.equals("")){
					sql = "select count()* from member where level = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, level);
					
				}
				else {
					sql = "select count()* from member where mid like ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%"+mid+"%");
					
				}
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rs.next();
				totRecCnt = rs.getInt(1);
			} catch (SQLException e) {
				System.out.println("SQL 오류 : " + e.getMessage());
			} finally {
				getconn.rsClose();
			}
			return totRecCnt;
		}
		public void setMemberReset(int idx) {
			try {
				sql = "delete from member where idx = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, idx);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("SQL 오류 : " + e.getMessage());
			} finally {
				getconn.pstmtClose();
			}
			
		}
}
