package kr.co.iei.member.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;

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
	public int updateMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		int result = memberDao.updateMember(conn,m);
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}
	public int deleteMember(String memberId) {
		Connection conn = JDBCTemplate.getConnection();
		int result = memberDao.deleteMember(conn,memberId);
		
		if(result > 0) {
			JDBCTemplate.commit(conn); 
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}
	
}
