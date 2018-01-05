package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;

import DataBase.DBHandler;

public class User  
{

	protected short id;
	protected String name, userPicURL, email;
	protected String userName, password;

	protected boolean isCustomer;		//true - Customer, False - vendor
	
	public User(short id,
			String userName,
			String password,
			String i_name,
			String i_email,
			String i_userPicURL,
			boolean isCustomer) 
	{
		this.userName = userName;
		this.password = password;
		this.name = i_name;
		this.email = i_email;
		this.userPicURL = i_userPicURL;
		this.id = id;
		this.isCustomer = isCustomer;
	}
	
	//Constructors
	public User()
	{
		this.id = -1;
		this.name = this.userPicURL = this.email = this.userName = this.password = null;
		this.isCustomer = false;
	}
	
	public User(User i_user) throws Exception
	{
		DBHandler db = DBHandler.getInstance();
		if(db.isUsernameAvailable(i_user.getUserName()))
		{
			if(i_user.isCustomer)
			{
				i_user.setId(db.getCustomersMaxAvailableIdx());
			}
			else
			{
				i_user.setId(db.getVendorsMaxAvailableIdx());
			}
		}
	}
	
	
	public void setUserName(String userName) throws Exception {
		this.userName = userName;
	}

	public void setPassword(String password) throws Exception {
		this.password = password;
	}
	
	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserPicURL() {
		return userPicURL;
	}

	public void setUserPicURL(String userPicURL) {
		this.userPicURL = userPicURL;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isCustomer() {
		return isCustomer;
	}

	public void setCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	public short getID() {
		return this.getId();
	}
	public void setIsCustomer(boolean i_isCustomer) {
		this.isCustomer = i_isCustomer;
		
	}

}
