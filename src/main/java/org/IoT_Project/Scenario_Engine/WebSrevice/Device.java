package org.IoT_Project.Scenario_Engine.WebSrevice;

import org.IoT_Project.Scenario_Engine.Service.DeviceService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("device")
public class Device {
	
	DeviceService ds = new DeviceService();
	
	@Path("{device_id}/{Event_id}/{event_triggered}")
	@POST
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public void handleEvent(
			@PathParam("device_id") short device_id,
			@PathParam("Event_id") short Event_id,
			@PathParam("event_triggered") boolean eventTriggered)
	{
		try {
			ds.HandleCall(Event_id, eventTriggered);
		}
		catch(Exception ex)
		{
			//TODO -- handle exceptions from devices call.
		}
		/*
		String res = "got event #";
		res += Event_id;
		res += " from device id - ";
		res += device_id;
		res += " trigger = ";
		res += eventTriggered;
		return res;
		*/
	}
}
