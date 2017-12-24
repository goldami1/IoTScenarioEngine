package org.IoT_Project.Scenario_Engine.Models;

import java.util.*;

import DataBase.DBHandler;

public class Customer extends User implements IUser {
	
	private LinkedList<Device> devices;
	private LinkedList<Scenario> customerScenarios;
	
	public Customer(User i_User) throws Exception 
	{
		super(i_User.getName(), i_User.getEmail(), i_User.getUserPicURL());
		devices = new LinkedList<Device>();
		this.insertDevices();
		customerScenarios = new LinkedList<Scenario>();
		this.insertScenarios();
		DBHandler db = DBHandler.getInstance();
		db.addCustomer((Customer) i_User);
	}	
	
	public void addDevice(Product i_Product, short serialNumber)
	{
		Device productInstance = new Device(i_Product, this, serialNumber);
		devices.add(productInstance);
	}
	
	public void addScenario(Scenario i_Scenario)
	{
		customerScenarios.add(i_Scenario);
	}
	
	public void insertScenarios()
	{
		List<Scenario> scenariosToAdd = DBHandler.getInstance().getScenarios(this.getID());
		for(Scenario s : scenariosToAdd)
		{
			this.customerScenarios.add(s);
		}
	}

	private void insertDevices()
	{
		List<Device> devicesToAdd = DBHandler.getInstance().getDevices(this.getID());
		for(Device d : devicesToAdd)
		{
			this.devices.add(d);
		}
	}
	
	@Override
	public boolean isEnduser() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
}
