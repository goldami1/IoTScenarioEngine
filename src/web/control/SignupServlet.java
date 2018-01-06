package web.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.model.LoginService;
import web.model.SignupService;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/pages/signup/signup.jsp").forward(request,	 response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String username  = request.getParameter("username");
		String password = request.getParameter("password");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String companyname = request.getParameter("companyname");
		String description = request.getParameter("description");
		String logo = request.getParameter("logo");
		String type = request.getParameter("account_type");
		

	    SignupService service = new SignupService();
	    
		if	(service.addUser(type,firstname,lastname,username,password,companyname,description,email,logo)) {
			response.sendRedirect(request.getContextPath()+"/login");
		}else {
			request.setAttribute("error","Error");
			request.getRequestDispatcher("/pages/signup/signup.jsp").forward(request, response);
		}
	   
		
	}

}
