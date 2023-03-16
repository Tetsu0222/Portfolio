package controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BusinessLogic;
import model.ShinrinDto;



public class SurveyByQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SurveyByQuestionnaire() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name       = request.getParameter( "NAME"    );
		String mail       = request.getParameter( "EMAIL"   ); 
		String message    = request.getParameter( "MESSAGE" );

		ShinrinDto dto = new ShinrinDto();
		dto.setName   ( name    );
		dto.setMail   ( mail    );
		dto.setMessage( message );
		dto.setTime   ( new Timestamp( System.currentTimeMillis() ) );

		BusinessLogic logic  = new BusinessLogic();
		boolean succesInsert = logic.executeInsertSurvey( dto );

		if ( succesInsert ) {
			response.sendRedirect( "view/finish.html" );
			
		} else {
			response.sendRedirect( "view/error.html" );
			
		}
	}
}
