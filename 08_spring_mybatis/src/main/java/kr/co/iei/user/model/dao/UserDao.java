package kr.co.iei.user.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import kr.co.iei.model.dto.UserDTO;


@Mapper
public interface UserDao {

	UserDTO selectOneUser(UserDTO u);

	List selectAllUser();

	int insertUser(UserDTO user);

	int userUpdate(UserDTO u);

	int deleteUser(UserDTO u);

	UserDTO checkId(String userId);

	UserDTO checkId2(UserDTO u);

	List selectUserIdOrName(Map<String, Object> map);

	List selectUserIdOrName2(UserDTO u);

	List selectAllId();

	List searchUser(String[] id);

	int changePw(UserDTO u);

	

}
