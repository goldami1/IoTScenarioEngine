package web.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import engine.User;
import web.model.LoginService;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/pages/login/login.jsp").forward(request,	 response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer  = response.getWriter();
		
		String username;
		String password;

		username = request.getParameter("username");
		password = request.getParameter("password");
		
		LoginService loginService = new LoginService();
		User user = loginService.getUser(username, password);
		
		if	(user != null) {
				request.getSession().setAttribute("user", user);
			// TODO 
			if	(user.isEnduser()) {
				response.sendRedirect("/IoT_Proj_ScenarioEngine/devices");
			}else {
					response.sendRedirect("/IoT_Proj_ScenarioEngine/products");
			}
			return;
			
		}else {
			request.setAttribute("error","Wrong user name or password");
			request.getRequestDispatcher("/pages/login/login.jsp").forward(request, response);
		}
	}
}
