package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoDao {
	String JDBC_URL    = "jdbc:mysql://localhost/test_db?characterEncoding=UTF-8&useSSL=false";
	String USER_ID     = "test_user";
	String USER_PASS   = "test_pass";
	String ddl         = " SELECT USER_ID , USER_NAME , PASSWORD FROM USER_INFO WHERE USER_ID = ? AND PASSWORD = ? ";

	public UserInfoDto doSelect( String inputUserId, String inputPassWord ) {
		UserInfoDto dto = new UserInfoDto();

		try (Connection        con = DriverManager.getConnection( JDBC_URL , USER_ID , USER_PASS );
			 PreparedStatement ps  = con.prepareStatement( ddl.toString() );
			){
			ps.setString( 1, inputUserId   );
			ps.setString( 2, inputPassWord );
			ResultSet rs  =  ps.executeQuery();
			if ( rs.next() ) {
				dto.setUserId  ( rs.getString("USER_ID")   );
				dto.setUserName( rs.getString("USER_NAME") );
				dto.setPassWord( rs.getString("PASSWORD")  ); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
}
