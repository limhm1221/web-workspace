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

	public ArrayList<Member> selectAllMember() {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Member> list = memberDao.selectAllMember(conn);
		JDBCTemplate.close(conn);
		return list;
	}

}
