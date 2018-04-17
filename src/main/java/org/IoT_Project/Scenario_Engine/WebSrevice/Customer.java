package org.IoT_Project.Scenario_Engine.WebSrevice;

import org.IoT_Project.Scenario_Engine.Service.CustomerService;
import org.IoT_Project.Scenario_Engine.Service.UserService;

import DataBase.DBHandler;
import DataBase.NDBHandler;
import javafx.util.Pair;

import org.IoT_Project.Scenario_Engine.Models.Scenario;
import org.IoT_Project.Scenario_Engine.Models.Action;
import org.IoT_Project.Scenario_Engine.Models.ActionEventProto;
import org.IoT_Project.Scenario_Engine.Models.Case;
import org.IoT_Project.Scenario_Engine.Models.CaseGroup;
import org.IoT_Project.Scenario_Engine.Models.Device;
import org.IoT_Project.Scenario_Engine.Models.ErrorException;
import org.IoT_Project.Scenario_Engine.Models.Event;
import org.IoT_Project.Scenario_Engine.Models.MailAction;
import org.IoT_Project.Scenario_Engine.Models.User;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
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
	public Response getLogInPageCustomer() throws Exception
	{
		//return "Should return the login page";
		List<String> min = new LinkedList<String>();
		List<String> max = new LinkedList<String>();
		min.add("1");
		max.add("55");
		List<String> types = new LinkedList<String>();
		types.add("INT");
		List<Event> events1 = new LinkedList<Event>();
		//List<Event> events2 = new LinkedList<Event>();
		List<Action> actions = new LinkedList<Action>();
		List<String> params1 = new LinkedList<String>();
		List<String> params2 = new LinkedList<String>();
		List<String> paramsName = new LinkedList<String>();
		params1.add("galrotenberg3@gmail.com");
		params1.add("Scenario Engine Test mail");
		params2.add("50");
		paramsName.add("value");
		
		events1.add(new Event((short)-1,
				 			(short) 123,
				 			params1,
				 			new ActionEventProto((short)1,
								     "Is TV on state ON",
								    "description of event",
								     null,
								     null,
								     paramsName,
								     null,
								     null,
								     null,
								    (short)1,
								    "http://localhost:8000",
								    true),
				 			'|',
				 			false));
		events1.add(new Event((short)-1,
	 			(short) 123,
	 			params2,
	 			new ActionEventProto((short)1,
					     "Is TV on channel X",
					    "description of event",
					     null,
					     null,
					     paramsName,
					     null,
					     null,
					     null,
					    (short)1,
					    "http://localhost:8000",
					    true),
	 			'|',
	 			false));
		actions.add(new Action((short)-1,
	 			(short) 123,
	 			params1,
	 			new ActionEventProto((short)1,
					     "Turn TV on state X",
					    "description of event",
					     null,
					     null,
					     paramsName,
					     null,
					     null,
					     null,
					    (short)1,
					    "http://localhost:8000",
					    false)));
		
		List<Case> cases = new LinkedList<Case>();
		CaseGroup cg = new CaseGroup(cases, '&');
		
		Scenario scenario = new Scenario((short)-1,
			    (short)1,
			    "scenario name", 
			    "scenario description",
			    events1,
			    actions);
		Action a = new Action((short)-1,
	 			(short) 123,
	 			params2,
	 			new ActionEventProto((short)1,
					     "Turn TV on state X",
					    "description of event",
					     null,
					     null,
					     paramsName,
					     null,
					     null,
					     null,
					    (short)1,
					    "http://localhost:8011/Device_Simulator/webapi",
					    false));
				
				/*new MailAction((short)-1,
	 			(short) 123,
	 			params1,
	 			new ActionEventProto((short)1,
	 				     "Turn TV on state X",
	 				    "description of event",
	 				     types,
	 				     null,
	 				     paramsName,
	 				     min,
	 				     max,
	 				    (short)1,
	 				    "http://localhost:8000",
	 				    false));*/
		a.toggleAction();

		return Response.status(Status.OK).entity(a).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchCustomer(User i_user) 
	{
		try {
			return fetch(i_user);
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	@Path("/new")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCustomer(User i_user)
	{
		try {
			org.IoT_Project.Scenario_Engine.Models.Customer userToAdd = us.addCustomer(i_user);
			NDBHandler.getInstance().addCustomer(userToAdd);
			org.IoT_Project.Scenario_Engine.Models.Customer userAdded = NDBHandler.getInstance().getCustomer(i_user.getUserName(), i_user.getPassword());
			return Response.status(Status.CREATED).entity(userAdded).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	@Path("/vendors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchVendors() 
	{
		try {
			LinkedList<Pair<Short, String>> vendors_lst = NDBHandler.getInstance().getVendors();
//			LinkedList<tmpContainers.VenNameIDContainer> formattedVenLST = new LinkedList<tmpContainers.VenNameIDContainer>();

//			for(Pair<Short, String> elem:vendors_lst)
//			{
//				formattedVenLST.add(new tmpContainers.VenNameIDContainer(elem.getValue(), elem.getKey()));
//			}
					
		 	return Response.status(Status.OK).entity(vendors_lst).build();
		}
		catch(Exception ex)
		{
			return Response.status(Status.NOT_FOUND).entity(new org.IoT_Project.Scenario_Engine.Models.Error("server error")).build();
		}
	}
	
	@Path("/device/{user_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchDevices(@PathParam("user_id")short i_user) 
	{
		List<org.IoT_Project.Scenario_Engine.Models.Device> devices;
		try {
			devices = cs.fetchDevices(i_user);
			return Response.status(Status.OK).entity(devices).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}		
	}
	
	@Path("/device/{cust_id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addDevice(Device i_device, @PathParam("cust_id") short cust_id)
	{
		List<Device> updatedDevices;
		try {
			updatedDevices = cs.addDevice(cust_id, i_device);
			return Response.status(Status.CREATED).entity(updatedDevices).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	@Path("device/{user_id}/{dev_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateDevice(Device newDevice, @PathParam("user_id") short cust_id, @PathParam("dev_id") short origionalDevice_id)
	{
		List<Device> updatedDeviceList;
		try {
			updatedDeviceList = cs.updateDevice(cust_id, origionalDevice_id, newDevice);
			return Response.status(Status.OK).entity(updatedDeviceList).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	@Path("device/{id}/{dev_id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeDevice(@PathParam("id") short cust_id, @PathParam("dev_id") short device_id)
	{
		List<Device> updatedDeviceList;
		try {
			updatedDeviceList = cs.removeDevice(cust_id, device_id);
			return Response.status(Status.OK).entity(updatedDeviceList).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	@Path("/scenario/{user_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchScenarios(@PathParam("user_id")short i_user)
	{
		try {
			List<Scenario> scenarios = cs.fetchScenarios(i_user);
			return Response.status(Status.OK).entity(scenarios).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	@Path("scenario/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addScenario(Scenario scenarioToAdd, @PathParam("id") short cust_id)
	{
		try {
			List<Scenario> updatedScenarios = cs.addScenario(cust_id, scenarioToAdd);
			//NDBHandler.getInstance().OpenSession();
			return Response.status(Status.CREATED).entity(updatedScenarios).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	@Path("scenario/{cust_id}/{origion_scenario_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateScenario(Scenario newScenario, @PathParam("cust_id") short cust_id, @PathParam("origion_scenario_id")  short origionalScenario_id)
	{
		try {
			List<Scenario> updatedScenarios = cs.updateScenario(cust_id, origionalScenario_id, newScenario);
			return Response.status(Status.OK).entity(updatedScenarios).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	@Path("scenario/{cust_id}/{origion_scenario_id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeScenario(@PathParam("cust_id") short cust_id, @PathParam("origion_scenario_id") int scenarioToRemove)
	{
		try {
			List<Scenario> updatedScenarios = cs.removeScenario(cust_id, scenarioToRemove);
			return Response.status(Status.OK).entity(updatedScenarios).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	private Response fetch(User i_user) 
	{
		try {
			User user = us.fetch(i_user);
			return Response.status(Status.OK).entity(user).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
		
	}
	
	protected Response handleError(Exception ex)
	{
		org.IoT_Project.Scenario_Engine.Models.Error er = new org.IoT_Project.Scenario_Engine.Models.Error();
		er.setDescription(ex.getMessage());
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(er).build();
	}
}
