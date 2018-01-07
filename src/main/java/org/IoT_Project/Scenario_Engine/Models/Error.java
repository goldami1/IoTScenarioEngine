package org.IoT_Project.Scenario_Engine.Models;

import com.google.gson.annotations.SerializedName;

public class Error {
	@SerializedName("description")
	private String description;
	
	public Error()
	{
		this.description = null;
	}
	
	public Error(String description)
	{
		this.description = description;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
}
