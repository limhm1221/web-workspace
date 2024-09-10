package kr.co.iei.member.model.dto;

public class Member {
	private int userNo;
	private String userNickname;
	private int userAge;
	private String userGender;
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public Member(int userNo, String userNickname, int userAge, String userGender) {
		super();
		this.userNo = userNo;
		this.userNickname = userNickname;
		this.userAge = userAge;
		this.userGender = userGender;
	}
	@Override
	public String toString() {
		return "Member [userNo=" + userNo + ", userNickname=" + userNickname + ", userAge=" + userAge + ", userGender="
				+ userGender + "]";
	}
	
}
