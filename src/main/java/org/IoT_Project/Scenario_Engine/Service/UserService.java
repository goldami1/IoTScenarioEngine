package org.IoT_Project.Scenario_Engine.Service;

import org.IoT_Project.Scenario_Engine.Models.Customer;
import org.IoT_Project.Scenario_Engine.Models.User;

import DataBase.DBHandler;

public class UserService {
	public Customer fetch(User i_user) throws Exception{
		return DBHandler.getInstance().getCustomer(i_user.getID());
	}
}
