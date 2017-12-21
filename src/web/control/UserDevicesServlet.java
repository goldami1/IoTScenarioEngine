package web.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBHandler;
import web.model.UserDevicesService;

/**
 * Servlet implementation class UserDevicesServlet
 */
@WebServlet("/devices")
public class UserDevicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDevicesService service = new UserDevicesService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
			if	(!request.getAttribute("user").equals(null)) {
				request.setAttribute("exercises",service.getDevices((short)request.getAttribute("user")));
			    request.getRequestDispatcher("pages/enduser/devices.jsp").forward(request, response);				
			}
			response.sendRedirect(request.getContextPath()+"/login");
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (service.addDevice((short)request.getAttribute("user"))) {
			response.sendRedirect(request.getContextPath()+"/devices");
		}else {
			request.setAttribute("error","Error occured device has not added");
			request.getRequestDispatcher("/pages/enduser/devices.jsp").forward(request, response);
		}
	}

}
