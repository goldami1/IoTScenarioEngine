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
	
	@Path("{cust_id}/{device_serial_number}/{event_triggered}")
	@POST
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public void handleEvent(
			@PathParam("cust_id") short cust_id,
			@PathParam("device_serial_number") short device_serial_number,
			@PathParam("event_triggered") boolean eventTriggered)
	{
		try {
			ds.HandleCall(cust_id, device_serial_number, eventTriggered);
		}
		catch(Exception ex)
		{
			//TODO -- handle exceptions from devices call.
			ex.printStackTrace();
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
