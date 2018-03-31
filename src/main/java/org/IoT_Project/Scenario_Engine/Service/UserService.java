package org.IoT_Project.Scenario_Engine.Service;

import java.sql.SQLException;

import javax.ws.rs.core.Response.Status;

import org.IoT_Project.Scenario_Engine.Models.Customer;
import org.IoT_Project.Scenario_Engine.Models.ErrorException;
import org.IoT_Project.Scenario_Engine.Models.User;
import org.IoT_Project.Scenario_Engine.Models.Vendor;

import DataBase.DBHandler;
import DataBase.NDBHandler;

public class UserService {
	public User fetch(User i_user) throws Exception{
		return NDBHandler.getInstance().getUser(i_user.getUserName(), i_user.getPassword());
	}

	public Customer addCustomer(User i_user) throws Exception {
		Customer cust = new Customer(i_user);
		return cust;
	}

	public Vendor addVendor(Vendor i_user) throws Exception {
		Vendor vendor = new Vendor(i_user);
		return vendor;
	}
}
