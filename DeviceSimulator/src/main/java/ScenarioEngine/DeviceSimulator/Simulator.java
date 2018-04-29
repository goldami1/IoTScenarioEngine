package ScenarioEngine.DeviceSimulator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.lang.String;
import java.lang.StringBuilder;



@Path("Simulator")
public class Simulator {
	private short serial_number = 1;
	
	
	@Path("/1234/Day time")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response define_evenet(@QueryParam("event_id") int event_id,
								  @QueryParam("Day time") String parameter) {
		StringBuilder data_recieved = new StringBuilder();
		data_recieved.append("event_id = ");
		data_recieved.append(event_id);
		data_recieved.append(System.lineSeparator());
		data_recieved.append("parameter = ");
		data_recieved.append(parameter);
		
		return Response.status(Status.OK).entity(data_recieved.toString()).build();
	}
	
	@Path("/1234/Volume")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response toggleAction(@QueryParam("Alarm volume") String alarm_vol) {
		return Response.status(Status.OK).entity("Alarm volume = " + alarm_vol).build();
	}
	
	@Path("/1234/Reminder")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response toggleAction(@QueryParam("Hour") String hour,
								@QueryParam("Minute") String minute) {
		return Response.status(Status.OK).entity("Reminder: " + hour + ":" + minute).build();
	}
}
