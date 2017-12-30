package org.IoT_Project.Scenario_Engine.Models;

import DataBase.DBHandler;

public class User implements IUser 
{

	protected short id;
	protected String name, userPicURL, email;
	protected String userName, password;
	protected boolean isCustomer;		//true - Customer, False - vendor
	
	//Constructors
	public User()
	{
		this.id = -1;
		this.name = this.userPicURL = this.email = this.userName = this.password = null;
		this.isCustomer = false;
	}
	
	public User(String userName, String password, String i_name, String i_email, String i_userPicURL) 
	{
		this.name = i_name;
		this.email = i_email;
		this.id = -1;
		this.userName = userName;
		this.password = password;
		this.userPicURL = i_userPicURL;
	}
	
	//Getters&Setters
	public short getID()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public String getUsername()
	{
		return userName;
	}
	public String getPassword()
	{
		return password;
	}
	public String getEmail()
	{
		return email;
	}
	public String getUserPicURL()
	{
		return userPicURL;
	}
	public void setIsCustomer(boolean isCustomer)
	{
		this.isCustomer = isCustomer;
	}
	public boolean getIsCustomer()
	{
		return this.isCustomer;
	}
	//Methods
	public User setUserName(String i_userName) throws Exception 
	{
		boolean isTaken = true;
		try 
		{
			isTaken = DBHandler.getInstance().isUsernameAvailable(i_userName);
		} catch (Exception e)
		{
			throw new Exception("There's a problem with setUserName in User", e);
		}

		if (!isTaken) 
		{
			userName = i_userName;
		}

		return this;
	}
	public User setPassword(String i_password) throws Exception
	{
		boolean isExists = false;
		if (userName != null)
		{
			try
			{
			isExists = DBHandler.getInstance().isUsernameAvailable(userName);
			}
			catch(Exception e)
			{
				throw new Exception("There's a problem with setPassword in User", e);
			}
			
			if (isExists) 
			{
				password = i_password;
			}
		}
		return this;
	}

}
