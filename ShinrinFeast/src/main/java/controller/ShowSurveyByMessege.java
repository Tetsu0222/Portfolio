package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BusinessLogic;
import model.ShinrinDto;
import model.UserInfoDto;


public class ShowSurveyByMessege extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowSurveyByMessege() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session           = request.getSession();
		UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute( "LOGIN_INFO" );
		
		if ( userInfoOnSession != null ) {
		
			List<ShinrinDto> shinrinDtoList = new BusinessLogic().executeSelect();                   
			request.setAttribute( "shinrinDtoList" , shinrinDtoList );

			RequestDispatcher dispatch = request.getRequestDispatcher( "view/show_by_message.jsp" );
			dispatch.forward ( request , response );
			
		}else{
			response.sendRedirect( "view/Login.jsp" );
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
