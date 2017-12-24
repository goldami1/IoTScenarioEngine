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
	
	@Path("{device_id}/{Event_id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces()
	public void handleEvent(@PathParam("device_id") int device_id, @PathParam("Event_id") int Event_id)
	{
		try {
			ds.HandleCall(Event_id);
		}
		catch(Exception ex)
		{
			//TODO -- handle exceptions from devices call.
		}
	}
}
