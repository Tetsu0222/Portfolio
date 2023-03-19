package model;

public class ExecuteLoginBL {
	public UserInfoDto executeSelectUserInfo( String userId , String passWord ){
		return new UserInfoDao().doSelect( userId , passWord );
	}
}