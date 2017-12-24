package org.IoT_Project.Scenario_Engine.WebSrevice;

import org.IoT_Project.Scenario_Engine.Service.CustomerService;

import DataBase.DBHandler;

import org.IoT_Project.Scenario_Engine.Models.Scenario;
import org.IoT_Project.Scenario_Engine.Models.Device;
import org.IoT_Project.Scenario_Engine.Models.User;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("customer")
public class Customer {

	CustomerService cs = new CustomerService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getLogInPage()
	{
		return "Should return the login page for customer";
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)	//MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public org.IoT_Project.Scenario_Engine.Models.Customer fetch(User i_user) throws Exception
	{
		return cs.fetch(i_user);
	}
	
	@Path("/device/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addProduct(Device i_product, @PathParam("id") int i_id)
	{
		
		try {
			cs.addDevice(i_id, i_product);
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
		return "added new product";
	}
	
	@Path("device/{id}/{dev_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateDevice(Device newDevice, @PathParam("id") int cust_id, @PathParam("dev_id") int origionalDevice_id)
	{
		try {
			DBHandler.getInstance().updateDevice(origionalDevice_id, newDevice);
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
		return "updated device";
	}
	
	@Path("device/{id}/{dev_id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String removeDevice(@PathParam("id") int cust_id, @PathParam("dev_id") int device_id)
	{
		try {
			cs.removeDevice(device_id);
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
		return "removed device";
	}
	
	@Path("scenario/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addScenario(Scenario scenarioToAdd, @PathParam("id") int cust_id)
	{
		try {
			cs.addScenario(scenarioToAdd);
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
		return "added scenario";
	}
	
	@Path("scenario/{id}/{origion_scenario_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateScenario(Scenario newScenario, @PathParam("id") int Cust_id, @PathParam("origion_scenario_id")  int origionalScenario_id)
	{
		try {
			cs.updateScenario(origionalScenario_id, newScenario);
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
		return "scenario updated";
	}
	
	@Path("scenario/{id}/{origion_scenario_id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String removeScenario(@PathParam("id") int cust_id, @PathParam("origion_scenario_id") int scenarioToRemove)
	{
		try {
			cs.removeScenario(scenarioToRemove);
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
		return "removed scenario";
	}
	
	
}
