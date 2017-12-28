package org.IoT_Project.Scenario_Engine.Service;

import java.util.List;

import org.IoT_Project.Scenario_Engine.Models.*;

import DataBase.DBHandler;

public class CustomerService {

	public Customer fetch(User i_user) throws Exception{
		return DBHandler.getInstance().getCustomer(i_user.getID());
	}
	
	public Customer fetch(String i_name, String i_password)
	{
		return DBHandler.getInstance().getCustomer(i_name, i_password);
	}

	public void addDevice(int i_CustomerId, Device newDevice) throws Exception{
		DBHandler.getInstance().addDevice(newDevice);
	}

	public void removeDevice(int device_id) throws Exception{
		DBHandler.getInstance().removeDevice(device_id);
	}

	public void addScenario(Scenario scenarioToAdd) throws Exception{
		DBHandler.getInstance().addScenario(scenarioToAdd);
	}

	public void updateScenario(int origionalScenario_id, Scenario newScenario) 
	{
		DBHandler.getInstance().updateScenario(origionalScenario_id, newScenario);
	}

	public void removeScenario(int scenarioToRemove) throws Exception{
		DBHandler.getInstance().removeScenario(scenarioToRemove);
	}

	public List<Device> fetchDevices(short i_user) {
		return DBHandler.getInstance().getDevices(i_user);
	}

	public List<Scenario> fetchScenarios(short i_user) {
		return DBHandler.getInstance().getScenarios(i_user);
	}
	


}
