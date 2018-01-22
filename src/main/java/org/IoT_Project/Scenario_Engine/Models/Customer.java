package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.*;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.SerializedName;

import DataBase.DBHandler;

@Entity
@Table (name = "CUSTOMERS")
@AttributeOverrides({
	@AttributeOverride(name="user_id", column=@Column(name = "customer_id")),
	@AttributeOverride(name ="name", column=@Column(name = "customer_name"))
	})
public class Customer extends User {
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinTable(name = "CUSTOMERS_DEVICES", joinColumns=@JoinColumn(name = "customer_id"),
				inverseJoinColumns=@JoinColumn(name = "device_id"))
	@SerializedName("devices")
	private List<Device> devices;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinTable(name = "CUSTOMERS_SCENARIOS", joinColumns=@JoinColumn(name = "customer_id"),
				inverseJoinColumns=@JoinColumn(name = "scenario_id"))
	@SerializedName("customerScenarios")
	private List<Scenario> customerScenarios;
	
	public Customer()
	{
		super();
		this.devices = null;
		this.customerScenarios = null;
		this.isCustomer = true;
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
		this.isCustomer = i_user.isCustomer();
		this.devices = null;
		this.customerScenarios = null;
	}
	/**
	 * @throws SQLException ****************************************************************/

	
	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(LinkedList<Device> devices) {
		this.devices = devices;
	}

	public List<Scenario> getCustomerScenarios() {
		return customerScenarios;
	}

	public void setCustomerScenarios(LinkedList<Scenario> customerScenarios) {
		this.customerScenarios = customerScenarios;
	}
}
