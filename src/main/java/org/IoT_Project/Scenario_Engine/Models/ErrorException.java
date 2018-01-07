package org.IoT_Project.Scenario_Engine.Models;

import javax.ws.rs.core.Response.Status;

import com.google.gson.annotations.SerializedName;

public class ErrorException extends Exception
{
	@SerializedName("status")
	private Status status;
	
	public ErrorException(String string) {
		super(string);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
