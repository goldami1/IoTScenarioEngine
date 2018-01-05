package org.IoT_Project.Scenario_Engine.Service;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.IoT_Project.Scenario_Engine.Models.*;

import DataBase.DBHandler;

public class CustomerService {

	public Customer fetch(User i_user) throws Exception{
		return DBHandler.getInstance().getCustomer(i_user.getID());
	}
	
	public Customer fetch(String i_name, String i_password) throws SQLException
	{
		return DBHandler.getInstance().getCustomer(i_name, i_password);
	}

	public List<Device> addDevice(short i_CustomerId, Device newDevice) throws Exception{
		Device deviceToAdd = new Device(newDevice);
		if(DBHandler.getInstance().addDevice(deviceToAdd))
		{
			return DBHandler.getInstance().getDevices(i_CustomerId);
		}
		else
		{
			ErrorException ex = new ErrorException("user doesnt exist");
			ex.setStatus(Status.NOT_FOUND);
			throw ex;
		}
	}

	public List<Device> removeDevice(short cust_id, short device_id) throws Exception{
			return DBHandler.getInstance().getDevices(cust_id);
	}

	public List<Scenario> addScenario(short cust_id, Scenario scenarioToAdd) throws Exception{
		if(DBHandler.getInstance().addScenario(scenarioToAdd))
		{
			return DBHandler.getInstance().getScenarios(cust_id);
		}
		else
		{
			throw new Exception("no user");
		}
		
	}

	public List<Scenario> updateScenario(short cust_id, short origionalScenario_id, Scenario newScenario) throws Exception 
	{
		if(DBHandler.getInstance().updateScenario(origionalScenario_id, newScenario))
		{
			return DBHandler.getInstance().getScenarios(cust_id);
		}
		else
			throw new Exception("no user");
		
	}

	public List<Scenario> removeScenario(short cust_id, int scenarioToRemove) throws Exception{
		if(DBHandler.getInstance().removeScenario(scenarioToRemove))
		{
			return DBHandler.getInstance().getScenarios(cust_id);
		}
		else
			throw new Exception("no user");
		
	}

	public List<Device> fetchDevices(short i_user) throws Exception {
		try {
			
			return DBHandler.getInstance().getDevices(i_user);
			
		} catch (SQLException e) {
			ErrorException eex = new ErrorException(e.getMessage());
			eex.setStatus(Status.INTERNAL_SERVER_ERROR);
			throw eex;
		}	
	}

	public List<Scenario> fetchScenarios(short i_user) throws SQLException {
		return DBHandler.getInstance().getScenarios(i_user);
	}

	public List<Device> updateDevice(short cust_id, short dev_id, Device newDevice) throws Exception {
			return DBHandler.getInstance().getDevices(cust_id);
	}
	


}
