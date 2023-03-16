package model;

import java.io.Serializable;

public class UserInfoDto implements Serializable{
	private String userId   ;
	private String userName ;
	private String passWord ;
	
	public UserInfoDto() {}

	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	public String getPassWord() { return passWord; }
	public void setPassWord(String passWord) { this.passWord = passWord; }

}