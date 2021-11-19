package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conn.Getconn;
import member.MemberVO;

public class AdminDAO {
	Getconn getconn = Getconn.getInstance();
	
	private Connection conn = getconn.getconn();
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String sql = "";
	
	MemberVO vo = null;
	
		// 회원 등급 변경처리
		public void setMemberLevelChange(int idx, int level) {
			try {
				sql = "update member set level = ? where idx = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, level);
				pstmt.setInt(2, idx);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("SQL 오류 : " + e.getMessage());
			} finally {
				getconn.pstmtClose();
			}
			
		}

}
