package org.IoT_Project.Scenario_Engine.WebSrevice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.IoT_Project.Scenario_Engine.Service.UserService;

public class User {
	
	UserService us = new UserService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getLogInPage()
	{
		return "Should return the login page for customer";
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)	//MediaType.MULTIPART_FORM_DATA)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response fetch(org.IoT_Project.Scenario_Engine.Models.User i_user) throws Exception
	{
		try {
			org.IoT_Project.Scenario_Engine.Models.User user = us.fetch(i_user);
			return Response.status(Status.FOUND).entity(user).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	protected Response handleError(Exception ex)
	{
		Response res = null;
		org.IoT_Project.Scenario_Engine.Models.Error er = new org.IoT_Project.Scenario_Engine.Models.Error();
		er.setDescription(ex.getMessage());
		res = Response.status(Status.NOT_FOUND).entity(er).build();
		return res;
	}
}
