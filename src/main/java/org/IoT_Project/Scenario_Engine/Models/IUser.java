package org.IoT_Project.Scenario_Engine.Models;

public interface IUser {
	public User setUserName(String i_userName) throws Exception;
	public User setPassword(String i_password) throws Exception;
	public void setUserID(short i_id);
	public short getID();
	public String getName();
	public String getEmail();
	public String getUserPicURL();
	
	//TODO:
	//public boolean isEnduser();
}
