package kr.co.iei.member.model.dao;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.dto.MemberRowMapper;

@Repository
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MemberRowMapper memberRowMapper;
	public Member selectOneMember(Member m) {
		String query = "select * from member_tbl where member_id = ? and member_pw=?";
		Object[] params = {m.getMemberId(),m.getMemberPw()};
		List list =jdbc.query(query,memberRowMapper,params);
		if(list.isEmpty()) {
			return null;			
		}else {
			return (Member)list.get(0);
		}
	}
	public Member selectOneMember(String checkId) {
		String query = "select * from member_tbl where member_id=?";
		Object[] params = {checkId};
		List list = jdbc.query(query, memberRowMapper, params);
		if(list.isEmpty()) {
			return null;			
		}else {
			return (Member)list.get(0);
		}
	}
	public int insertMember(Member m) {
		String query = " insert into member_tbl values(member_seq.nextval,?,?,?,?,?,3,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {m.getMemberId(),m.getMemberPw(),m.getMemberName(),m.getMemberPhone(),m.getMemberAddr()};
		int result = jdbc.update(query,params);
		return result;
	}
	public int updateMember(Member m) {
		String query = "update member_tbl set member_pw=?,member_phone=?,member_addr=? where member_no =?";
		Object[] params = {m.getMemberPw(),m.getMemberPhone(),m.getMemberAddr(),m.getMemberNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	public int deleteMember(int memberNo) {
		String query = "delete from member_tbl where member_no=?";
		Object[] params = {memberNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public List selectAllMember() {
		String query = "select * from member_tbl order by 1";
		List list = jdbc.query(query,memberRowMapper);
		return list;
	}
	public int changeLevel(Member m) {
		String query = "update member_tbl set member_Level=? where member_no=?";
		Object[] params= {m.getMemberLevel(),m.getMemberNo()};
		int result = jdbc.update(query,params);
		return result;
	}

}
