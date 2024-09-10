package kr.co.iei.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.dto.MemberRowMapper;


//이클래스가 DAO클래스임을 알리고 객체를 생성하라고 하는 어노테이션
@Repository
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MemberRowMapper memberRowMapper;

	public List selectAllMember() {
		//1. query문 작성
		String query="select * from member_tbl order by 1";
		//2. 쿼리문에 위치홀더가 존재한다면 (위치홀더가 들어갈 값을 object[]에 순서대로 대입)
		//3. jdbc객체의 메소드로 쿼리문 수행 (조회:query()/삽입,수정,삭제 : update())
		//query(param1, parma2) 
		//query(param1, parma2, param3) 
		//param1 : 수행할 쿼리문
		//param2 : select 결과를 처리할 rowmapper객체
		//param3 : 위치홀더에 들어갈 데이터를 저장한 object[]
		List list = jdbc.query(query,memberRowMapper);
		return list;
	}

	public Member selectOneMember(String memberId) {
		String query="select * from member_tbl where member_id=?";
		//2.위치홀더에 들어갈 값을 배열에 순서대로 대입
		Object[] params= {memberId};
		//qeury 메소드는 결과가 항상 List타입
		List list = jdbc.query(query,memberRowMapper,params);
		//아이디를 조건으로 사용한 쿼리문이므로 조회결과가 0개 or 1개
		if(list.isEmpty()) {
			return null;
		}else {
			//list에 데이터는 object타입으로 업캐스팅되어있으므로, 꺼낸데이터를 다운캐스팅해서 사용
			return (Member)list.get(0);			
		}
	}

	public int insertMember(Member m) {
		String query = "insert into member_tbl values(member_seq.nextval,?,?,?,?,?,3,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {m.getMemberId(),m.getMemberPw(),m.getMemberName(),m.getMemberPhone(),m.getMemberAddr()};
		int result = jdbc.update(query,params);
		return result;
	}
}
