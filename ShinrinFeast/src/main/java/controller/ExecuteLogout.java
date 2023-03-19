package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserInfoDto;



public class ExecuteLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteLogout() {
		super();
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {

		HttpSession session           = request.getSession();
		UserInfoDto userInfoOnSession = ( UserInfoDto )session.getAttribute( "LOGIN_INFO" );

		if ( userInfoOnSession != null ) {
			session.invalidate();
			response.sendRedirect( "view/Logout.html" );
			
		} else {
			response.sendRedirect( "view/Login.html"  );
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}