package engine;

import db.DBHandler;

public abstract class User implements IUser 
{

	private short id;
	private String name, userPicURL, email;
	private String userName, password;
	
	//Constructors
	public User(String i_name, String i_email) 
	{
		id = -1;
		name = i_name;
		email = i_email;
	}
	public User(String i_name, String i_email, String i_userPicURL) 
	{
		this(i_name, i_email);
		id = -1;
		userPicURL = i_userPicURL;
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
	
	//Methods
	public User setUserName(String i_userName) 
	{
		boolean isTaken = true;
		isTaken = DBHandler.isUsernameAvailable(i_userName);

		if (!isTaken) 
		{
			userName = i_userName;
		}

		return this;
	}
	public User setPassword(String i_password) 
	{
		boolean isExists = false;
		if (userName != null)
		{
			isExists = DBHandler.isUsernameAvailable(userName);

			if (isExists) 
			{
				password = i_password;
			}
		}
		return this;
	}

}
