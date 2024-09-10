package kr.co.iei.user.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.model.dto.UserDTO;
import kr.co.iei.user.model.dao.UserDao;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public UserDTO selectOneUser(UserDTO u) {
		System.out.println("평문 pw : "+u.getUserPw());
		//bcrypt는 동일한 값을 hash로 암호화해도 salt값으로 인해서 다른 결과를 가져옴 
		//pw를 검증하지 않고 id로만 회원을 조회 
		UserDTO user = userDao.selectOneUser(u);
		//회원이 조회되지 않으면 id가 잘못된 케이스
		if(user != null) {
			//bcrypt는평문패스워드와 암호된 패스워드를 비교해서 일치하는지 결과를 알려주는 함수를 제공
			//matches(평문패스워드, 암호화패스워드) -> 일치하면 true /일치하지 않으면 false
			if(encoder.matches(u.getUserPw(), user.getUserPw())) {
				user.setUserPw(null);
				return user;
		}else{
			return null;
		 }
		}else{
			return null;
		}
	}

	public UserDTO checkId2(UserDTO u) {
		UserDTO user = userDao.selectOneUser(u);
		return user;
	}

	public List selectAllUser() {
		List list = userDao.selectAllUser();
		return list;
	}

	@Transactional
	public int insertUser(UserDTO user) {
		//db에 insert하기 전에 pw 암호화
		String defaultPw = user.getUserPw();
		System.out.println("사용자가 입력 한 pw : "+defaultPw);
		String encPw = encoder.encode(defaultPw);
		System.out.println("암호화 된 pw : " +encPw);
		user.setUserPw(encPw);
		int result = userDao.insertUser(user);
		return result;				
			
	}
	
	@Transactional
	public int userUpdate(UserDTO u) {
		int result = userDao.userUpdate(u);
		return result;
	}

	
	@Transactional
	public int deleteUser(UserDTO u) {
		int result = userDao.deleteUser(u);
		return result;
	}

	public UserDTO checkId(String userId) {
		UserDTO user= userDao.checkId(userId);
		return user;
	}

	public List searchUser1(String type, String keyword) {
		//mybatis에 매개변수로 데이터를 전송할때는 하나의 객체로 묶어서 줘야함 
		//전송할 데이터를 모두담을 DTO, VO객체가 있으면 사용하면 됨
		//->DTO나 VO가 없으면 - > DTO나 VO 생성 or HashMap을 생성해서 전달
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type",type);
		map.put("keyword",keyword);
		List list = userDao.selectUserIdOrName(map);
		return list;
	}

	public List searchUser2(UserDTO u) {
		List list = userDao.selectUserIdOrName2(u);
		return list;
	}

	public List selectAllId() {
		List list = userDao.selectAllId();
		return list;
	}
	
	public List searchUser3(String[] id) {
		List list = userDao.searchUser(id);
		return list;

	}
	
	@Transactional
	public int updatePw(UserDTO u) {
		String encPw = encoder.encode(u.getUserPw());
		u.setUserPw(encPw);
		int result = userDao.changePw(u);
		return result;
	}
}
