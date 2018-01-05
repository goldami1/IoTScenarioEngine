package org.IoT_Project.Scenario_Engine.Models;

import javax.ws.rs.core.Response.Status;

public class ErrorException extends Exception {
	public ErrorException(String string) {
		super(string);
	}

	Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
