package org.IoT_Project.Scenario_Engine.Service;

import java.sql.SQLException;

import org.IoT_Project.Scenario_Engine.Models.Customer;
import org.IoT_Project.Scenario_Engine.Models.IUser;
import org.IoT_Project.Scenario_Engine.Models.User;
import org.IoT_Project.Scenario_Engine.Models.Vendor;

import DataBase.DBHandler;

public class UserService {
	public IUser fetch(User i_user) throws Exception{
		//return DBHandler.getInstance().getCustomer(i_user.getID());
		return DBHandler.getInstance().getUser(i_user.getUserName(), i_user.getPassword());
	}

	public User addCustomer(User i_user) throws Exception {
		Customer cust = new Customer(i_user);
		return (User)cust;
	}

	public User addVendor(User i_user) throws SQLException {
		Vendor vendor = new Vendor(i_user);
		return vendor;
	}
}
