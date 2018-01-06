package web.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBHandler;
import engine.User;
import web.model.UserDevicesService;

/**
 * Servlet implementation class UserDevicesServlet
 */
@WebServlet("/devices")
public class UserDevicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDevicesService service = new UserDevicesService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
			if	(request.getAttribute("user") != null ) {		
				User user = (User)request.getAttribute("user");
				request.setAttribute("devices",service.getDevices(user.getID()));
			    request.getRequestDispatcher("pages/enduser/devices.jsp").forward(request, response);				
			}
			response.sendRedirect(request.getContextPath()+"/login");
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int vendorID = Integer.parseInt(request.getParameter("vendor"));
		int productID = Integer.parseInt(request.getParameter("product"));
		int deviceSerial = Integer.parseInt(request.getParameter("serial"));
		
		User user = (User)request.getAttribute("user");
		if	(service.addDevice(user.getID(),productID,deviceSerial)) {
			response.sendRedirect(request.getContextPath()+"/devices");
		}else {
			request.setAttribute("error","Error");
			request.getRequestDispatcher("/pages/enduser/devices.jsp").forward(request, response);
		}
	}

}
