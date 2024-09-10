package kr.co.iei.member.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component //이클래스를 객체로 만들어놔
public class MemberRowMapper implements RowMapper<Member>{

	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		Member member = new Member();
		member.setEnrollDate(rs.getString("enroll_date"));
		member.setMemberAddr(rs.getString("member_addr"));
		member.setMemberId(rs.getString("member_id"));
		member.setMemberLevel(rs.getInt("member_level"));
		member.setMemberName(rs.getString("member_name"));
		member.setMemberNo(rs.getInt("member_no"));
		member.setMemberPhone(rs.getString("member_phone"));
		member.setMemberPw(rs.getString("member_pw"));
		return member;
	}

	
}
