package org.IoT_Project.Scenario_Engine.WebSrevice;

import org.IoT_Project.Scenario_Engine.Service.CustomerService;

import DataBase.DBHandler;

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
public class Customer extends org.IoT_Project.Scenario_Engine.WebSrevice.User{

	CustomerService cs = new CustomerService();
	
	@Path("/device/{user_id}")
	@GET
	//@Produces(MediaType.APPLICATION_JSON)
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
	
	@Path("/device/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(Device i_product, @PathParam("id") short i_id)
	{
		List<Device> UpdatedDevices;
		Response res = null;
		try {
			UpdatedDevices = cs.addDevice(i_id, i_product);
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
	//@Produces(MediaType.APPLICATION_JSON)
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
	//@Produces(MediaType.APPLICATION_JSON)
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
	//@Produces(MediaType.APPLICATION_JSON)
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
	//@Produces(MediaType.APPLICATION_JSON)
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
	
	@Path("scenario/{id}/{origion_scenario_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response updateScenario(Scenario newScenario, @PathParam("id") short cust_id, @PathParam("origion_scenario_id")  short origionalScenario_id)
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
	
	@Path("scenario/{id}/{origion_scenario_id}")
	@DELETE
	//@Produces(MediaType.APPLICATION_JSON)
	public Response removeScenario(@PathParam("id") short cust_id, @PathParam("origion_scenario_id") int scenarioToRemove)
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
}
