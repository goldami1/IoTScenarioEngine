package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.*;

import DataBase.DBHandler;

public class Customer extends User implements IUser {
	
	private LinkedList<Device> devices;
	private LinkedList<Scenario> customerScenarios;
	
	public Customer(User i_User) throws Exception 
	{
		super(i_User.getUsername(), i_User.getPassword(), i_User.getName(), i_User.getEmail(), i_User.getUserPicURL());
		devices = new LinkedList<Device>();
		customerScenarios = new LinkedList<Scenario>();
		DBHandler db = DBHandler.getInstance();
		this.id = db.getCustomersMaxAvailableIdx();
		this.setIsCustomer(true);
		db.addCustomer((Customer) i_User);
	}	
	
	public void addDevice(Product i_device, short cust_id, short serialNumber) throws SQLException
	{
		Device productInstance = new Device(i_device , cust_id, serialNumber);
		devices.add(productInstance);
	}
	
	public void addScenario(Scenario i_Scenario)
	{
		customerScenarios.add(i_Scenario);
	}
	
	public void insertScenarios() throws SQLException
	{
		List<Scenario> scenariosToAdd = DBHandler.getInstance().getScenarios(this.getID());
		for(Scenario s : scenariosToAdd)
		{
			this.customerScenarios.add(s);
		}
	}

	private void insertDevices() throws SQLException
	{
		List<Device> devicesToAdd = DBHandler.getInstance().getDevices(this.getID());
		for(Device d : devicesToAdd)
		{
			this.devices.add(d);
		}
	}
/*<<<<<<< HEAD
=======*/
	
	
	public boolean isEnduser() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
/*>>>>>>> 9c1b789b192224712d3b93430df172c204c381cd*/
}
