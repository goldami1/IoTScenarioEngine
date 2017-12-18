package engine;

import java.util.*;

import db.DBHandler;

public class Customer extends User implements IUser {
	
	private LinkedList<Device> devices;
	private LinkedList<Scenario> customerScenarios;
	
	public Customer(User i_User) throws Exception 
	{
		super(i_User.getName(), i_User.getEmail(), i_User.getUserPicURL());
		devices = new LinkedList<Device>();
		customerScenarios = new LinkedList<Scenario>();
		DBHandler db = DBHandler.getInstance();
		db.addCustomer(this.getName(), this.getName(), this.getUsername(), this.getPassword(), this.getEmail());
	}	
	
	public void addDevice(Product i_Product)
	{
		Device productInstance = new Device(i_Product, this);
		devices.add(productInstance);
	}
	
	public void addScenario(Scenario i_Scenario)
	{
		customerScenarios.add(i_Scenario);
	}
	
	
	
	
}
