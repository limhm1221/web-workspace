package kr.co.iei.member.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import common.JDBCTemplate;
import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.dto.Member;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		super();
		memberDao = new MemberDao();
	}
	public ArrayList<Member> selectAllMember(){
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Member> list = memberDao.selectAllMember(conn);
		JDBCTemplate.close(conn);
		return list;
	}
	
	public Member selectOneMember(String memberId) {
		Connection conn = JDBCTemplate.getConnection();
		Member m = memberDao.selectOneMember(conn, memberId);
		JDBCTemplate.close(conn);
		return m;
	}
	public int insertMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		int result = memberDao.insertMember(conn,m);
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		return result;
	}
}
