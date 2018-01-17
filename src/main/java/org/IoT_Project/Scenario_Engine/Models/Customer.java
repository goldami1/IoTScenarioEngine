package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.*;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

import DataBase.DBHandler;

@Entity
@Table (name = "CUSTOMERS")
@AttributeOverrides({
	@AttributeOverride(name="user_id", column=@Column(name = "customer_id")),
	@AttributeOverride(name ="name", column=@Column(name = "customer_name"))
	})
public class Customer extends User {
	
	@ElementCollection
	@JoinTable(name = "CUSTOMERS_DEVICES")
	@SerializedName("devices")
	private LinkedList<Device> devices;
	@ElementCollection
	@JoinTable(name = "CUSTOMERS_SCENARIOS")
	@SerializedName("customerScenarios")
	private LinkedList<Scenario> customerScenarios;
	
	public Customer()
	{
		super();
		this.devices = null;
		this.customerScenarios = null;
	}
	
	public Customer(short User_id,
				  String User_userName,
				  String User_password,
				  String User_name,
				  String User_picURL,
				  String User_email,
				  boolean User_isCustomer,
				  LinkedList<Device> Customer_devices,
				  LinkedList<Scenario> Customer_scenarios)
	{
		super(User_id, User_userName, User_password, User_name, User_email, User_picURL, User_isCustomer);
		this.devices = Customer_devices;
		this.customerScenarios = Customer_scenarios;
	}
	
	/************   ONLY FOR CUSTOMER NEW CREATION IN DB  *************/
	public Customer(User i_user) throws Exception
	{
		super(i_user);
		this.isCustomer = true;
		this.devices = null;
		this.customerScenarios = null;
	}
	/**
	 * @throws SQLException ****************************************************************/

	
	public LinkedList<Device> getDevices() {
		return devices;
	}

	public void setDevices(LinkedList<Device> devices) {
		this.devices = devices;
	}

	public LinkedList<Scenario> getCustomerScenarios() {
		return customerScenarios;
	}

	public void setCustomerScenarios(LinkedList<Scenario> customerScenarios) {
		this.customerScenarios = customerScenarios;
	}
}
