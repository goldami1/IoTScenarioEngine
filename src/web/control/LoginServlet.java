package web.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.model.LoginService;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer  = response.getWriter();
		writer.println("asdasdasda");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer  = response.getWriter();
		
		String username;
		String password;
		System.out.print("er");
		username = request.getParameter("username");
		password = request.getParameter("password");
		
		LoginService loginService = new LoginService();
		boolean result = loginService.authenticate(username, password);	
		if	(result) {
	//		response.sendRedirect("success.js");
			writer.println("yay logged in");
			return;
		}else {
	//		request.getSession().setAttribute("isFailed",result);
//			response.sendRedirect("login.jsp");
			writer.println("fuck you");
		}
	}
}
