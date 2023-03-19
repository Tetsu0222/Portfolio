package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ExecuteLoginBL;
import model.UserInfoDto;

public class ExecuteLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteLogin() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session           = request.getSession();
		UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute( "LOGIN_INFO" );

		if( userInfoOnSession != null ) {
			response.sendRedirect( "view/Login.jsp" );
			
		}else{
			boolean succesFlg = true;
			if( !( validatePrmUserId(request.getParameter( "USER_ID" ))&&
				   validatePrmPassword(request.getParameter( "PASSWORD" )))){
				succesFlg = false ;
				
			}else{
				String userId   = request.getParameter( "USER_ID"  );
				String passWord = request.getParameter( "PASSWORD" );
				
				ExecuteLoginBL logic = new ExecuteLoginBL();
				UserInfoDto    dto   = logic.executeSelectUserInfo( userId , passWord ) ;
				
				if  ( dto.getUserId() == null ) {
					succesFlg = false ;
					
				}else {
					session.setAttribute( "LOGIN_INFO" , dto );
				}
			}
			
			if ( succesFlg ){
				response.sendRedirect( "ShowSurveyByMessege" );
				
			} else {
				response.sendRedirect( "view/Login.html" );
				
			}
		}
	}


	private boolean validatePrmUserId( String pr ){

		boolean validateResult = true ;
		if( pr == null || pr.equals("") ) {
			validateResult = false ;
		}
		return validateResult ;
	}


	private boolean validatePrmPassword( String pr ){
		boolean validateResult = true ;
		if( pr == null || pr.equals("") ) {
			validateResult = false ;
		}
		return validateResult ;
	}

}