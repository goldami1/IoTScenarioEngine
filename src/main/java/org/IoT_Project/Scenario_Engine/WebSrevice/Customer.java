package org.IoT_Project.Scenario_Engine.WebSrevice;

import org.IoT_Project.Scenario_Engine.Service.CustomerService;
import org.IoT_Project.Scenario_Engine.Service.UserService;

import org.IoT_Project.Scenario_Engine.Models.Scenario;
import org.IoT_Project.Scenario_Engine.Models.Device;
import org.IoT_Project.Scenario_Engine.Models.User;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("customer")
public class Customer {

	CustomerService cs = new CustomerService();
	private static UserService us = new UserService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getLogInPageCustomer()
	{
		return "Should return the login page";
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)	
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchCustomer(User i_user) throws Exception
	{
		return fetch(i_user);
	}
	
	@Path("/new")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCustomer(User i_user)
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
	
	@Path("/device/{user_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchDevices(@PathParam("user_id")short i_user) throws Exception
	{
		Response res = null;
		List<org.IoT_Project.Scenario_Engine.Models.Device> devices;
		try {
			devices = cs.fetchDevices(i_user);
			res = Response.status(Status.OK).entity(devices).build();
		}
		catch(Exception ex)
		{
			res = handleError(ex);
		}
		return res;
		
	}
	
	@Path("/device/{cust_id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addDevice(Device i_product, @PathParam("cust_id") short cust_id)
	{
		List<Device> UpdatedDevices;
		Response res = null;
		try {
			UpdatedDevices = cs.addDevice(cust_id, i_product);
			res = Response.status(Status.CREATED).entity(UpdatedDevices).build();
		}
		catch(Exception ex)
		{
			res = handleError(ex);
		}
		return res;
	}
	
	@Path("device/{user_id}/{dev_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateDevice(Device newDevice, @PathParam("user_id") short cust_id, @PathParam("dev_id") short origionalDevice_id)
	{
		Response res = null;
		List<Device> updatedDeviceList;
		try {
			updatedDeviceList = cs.updateDevice(cust_id, origionalDevice_id, newDevice);
			res = Response.status(Status.OK).entity(updatedDeviceList).build();
		}
		catch(Exception ex)
		{
			res = handleError(ex);
		}
		return res;
	}
	
	@Path("device/{id}/{dev_id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeDevice(@PathParam("id") short cust_id, @PathParam("dev_id") short device_id)
	{
		Response res = null;
		List<Device> updatedDeviceList;
		try {
			updatedDeviceList = cs.removeDevice(cust_id, device_id);
			res = Response.status(Status.OK).entity(updatedDeviceList).build();
		}
		catch(Exception ex)
		{
			res = handleError(ex);
		}
		return res;
	}
	@Path("/scenario/{user_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchScenarios(@PathParam("user_id")short i_user)
	{
		Response res = null;
		try {
			List<Scenario> scenarios = cs.fetchScenarios(i_user);
			res = Response.status(Status.OK).entity(scenarios).build();
		}
		catch(Exception ex)
		{
			res = handleError(ex);
		}
		return res;
	}
	
	@Path("scenario/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addScenario(Scenario scenarioToAdd, @PathParam("id") short cust_id)
	{
		Response res = null;
		try {
			List<Scenario> updatedScenarios = cs.addScenario(cust_id, scenarioToAdd);
			res = Response.status(Status.CREATED).entity(updatedScenarios).build();
		}
		catch(Exception ex)
		{
			res = handleError(ex);
		}
		return res;
	}
	
	@Path("scenario/{cust_id}/{origion_scenario_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateScenario(Scenario newScenario, @PathParam("cust_id") short cust_id, @PathParam("origion_scenario_id")  short origionalScenario_id)
	{
		Response res = null;
		try {
			List<Scenario> updatedScenarios = cs.updateScenario(cust_id, origionalScenario_id, newScenario);
			res = Response.status(Status.OK).entity(updatedScenarios).build();
		}
		catch(Exception ex)
		{
			res = handleError(ex);
		}
		return res;
	}
	
	@Path("scenario/{cust_id}/{origion_scenario_id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeScenario(@PathParam("cust_id") short cust_id, @PathParam("origion_scenario_id") int scenarioToRemove)
	{
		Response res = null;
		try {
			List<Scenario> updatedScenarios = cs.removeScenario(cust_id, scenarioToRemove);
			res = Response.status(Status.OK).entity(updatedScenarios).build();
		}
		catch(Exception ex)
		{
			res = handleError(ex);
		}
		return res;
	}
	
	private Response fetch(User i_user)
	{
		try {
			User user = us.fetch(i_user);
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
