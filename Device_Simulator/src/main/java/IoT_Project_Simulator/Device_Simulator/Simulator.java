package IoT_Project_Simulator.Device_Simulator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("123")
public class Simulator {
	
	@Path("Turn TV on state X")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String Action1(@QueryParam("value") int chan)
	{
		return "changing TV channel to " + chan;
	}
}
