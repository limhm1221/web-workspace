package kr.co.iei.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import kr.co.iei.member.model.dto.Member;

public class MemberDao {

	public ArrayList<Member> selectAllMember(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<Member>();
		String query = "select * from member_tbl order by 1";
		
		try {
			pstmt=conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while(rset.next()){
				Member m = new Member();
				m.setUserAge(rset.getInt("user_age"));
				m.setUserGender(rset.getNString("user_gender"));
				m.setUserNickname(rset.getString("user_nickname"));
				m.setUserNo(rset.getInt("user_no"));
				list.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

}
