package org.IoT_Project.Scenario_Engine.WebSrevice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.IoT_Project.Scenario_Engine.Service.UserService;


public class User {
	
	UserService us = new UserService();
	@Path("customer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getLogInPageCustomer()
	{
		return "Should return the login page";
	}
	
	@Path("customer")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)	//MediaType.MULTIPART_FORM_DATA)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response fetchCustomer(org.IoT_Project.Scenario_Engine.Models.User i_user) throws Exception
	{
		return fetch(i_user);
	}
	
	@Path("customer/new")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addCustomer(org.IoT_Project.Scenario_Engine.Models.User i_user)
	{
		try {
			org.IoT_Project.Scenario_Engine.Models.User userToAdd = us.addCustomer(i_user);
			return Response.status(Status.CREATED).entity(userToAdd).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	@Path("vendor")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getLogInPageVendor()
	{
		return "Should return the login page";
	}
	
	@Path("vendor")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)	//MediaType.MULTIPART_FORM_DATA)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response fetchVendor(org.IoT_Project.Scenario_Engine.Models.User i_user) throws Exception
	{
		return fetch(i_user);
	}
	
	@Path("vendor/new")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addVendor(org.IoT_Project.Scenario_Engine.Models.User i_user)
	{
		try {
			org.IoT_Project.Scenario_Engine.Models.User userToAdd = us.addVendor(i_user);
			return Response.status(Status.CREATED).entity(userToAdd).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	private Response fetch(org.IoT_Project.Scenario_Engine.Models.User i_user)
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
