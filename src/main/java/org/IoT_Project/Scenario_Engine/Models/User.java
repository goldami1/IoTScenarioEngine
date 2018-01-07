package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;

import com.google.gson.annotations.SerializedName;

import DataBase.DBHandler;

public class User  
{

	@SerializedName("id")
	protected short id;
	@SerializedName("name")
	protected String name;
	@SerializedName("userPicURL")
	protected String userPicURL;
	@SerializedName("email")
	protected String email;
	@SerializedName("userName")
	protected String userName;
	@SerializedName("password")
	protected String password;
	@SerializedName("isCustomer")
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
		boolean isUpdated = i_user.getId() > 0;
		if(!isUpdated)
		{
			if(db.isUsernameAvailable(i_user.getUserName()))
			{
				if(i_user.isCustomer)
				{
					this.id = db.getCustomersMaxAvailableIdx();
				}
				else
				{
					this.id = db.getVendorsMaxAvailableIdx();
				}
			}
		}
		else
		{
			this.id = i_user.getId();
		}
		this.userName = i_user.getUserName();
		this.password = i_user.getPassword();
		this.name = i_user.getName();
		this.email = i_user.getEmail();
		this.userPicURL = i_user.getUserPicURL();
		this.isCustomer = i_user.isCustomer;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCustomer() {
		return isCustomer;
	}

	public void setCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public void setIsCustomer(boolean i_isCustomer) {
		this.isCustomer = i_isCustomer;	
	}

}
