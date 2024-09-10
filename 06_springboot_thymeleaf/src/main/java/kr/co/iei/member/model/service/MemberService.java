package kr.co.iei.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.dto.Member;

//이 클래스가 Service를 담당하는 클래스임을 알리고 객체를 생성하는 어노테이션

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	public List selectAllMember() {
		List list = memberDao.selectAllMember();
		return list;
	}

	public Member selectOneMember(String memberId) {
		Member m = memberDao.selectOneMember(memberId);
		return m;
	}

	@Transactional //commit, rollback을 처리하는 어노테이션
	public int insertMember(Member m) {
		int result = memberDao.insertMember(m);
		
		return result;
	}

}
