package org.IoT_Project.Scenario_Engine.Models;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import com.google.gson.annotations.SerializedName;
import javax.net.ssl.HttpsURLConnection;



import DataBase.DBHandler;


public class Action {
	@SerializedName("id")
	protected short id;
	@SerializedName("parameter")
	protected String parameter;
	@SerializedName("device_serialNum")
	protected short device_serialNum;
	@SerializedName("actionDescription")
	protected ActionEventProto actionDescription;
	
	public Action()
	{
		this.parameter = null;
		this.actionDescription = null;
		this.id = this.device_serialNum = -1;
	}
	
	public Action(short Action_id,
				  short Action_deviceSerialNum,
				  String Action_parameter,
				  ActionEventProto Action_descriptor)
	{
		this.id = Action_id;
		this.device_serialNum = Action_deviceSerialNum;
		this.parameter = Action_parameter;
		this.actionDescription = Action_descriptor;
	}
	
	/************   ONLY FOR ACTION NEW CREATION IN DB   *************/
	public Action(Action i_action) throws Exception
	{
		/*
		 * this constructor usement is only for registering new Action object to DB.
		 */
		boolean isUpdated = i_action.getId() > 0;
		DBHandler db = DBHandler.getInstance();
		this.device_serialNum = i_action.getDevice_serialNum();
		this.parameter = i_action.getParameter();
		this.actionDescription = i_action.getActionDescription();
		if(isUpdated)
			this.id = i_action.getId();
		else
		{
			this.id = db.getActionsMaxAvailableIdx();
		}
	}
	/****************************************************************/
	public final int toggleAction() throws Exception
	{
		StringBuilder URI = new StringBuilder();
		
		/*
		 * creating the URI:
		 * (server-url)/product_endpoint/device_serialNum/{if this is an event => event_id}/name of Action_Event/parameter.
		 */
		
		URI.append(this.actionDescription.getProductEP());
		URI.append("/");
		URI.append(this.device_serialNum);
		URI.append("/");
		
		URI.append(this.actionDescription.getName());
		URI.append("?parameter=" + this.parameter);
		if(this.actionDescription.getIsEvent())
		{
			URI.append("&event_id=" + this.id);
		}
		String USER_AGENT = "Mozilla/5.0";

		URL ep = new URL(URI.toString());
		//URL ep = new URL(null, URI.toString(), new sun.net.www.protocol.https.Handler());
		HttpURLConnection con = (HttpURLConnection)ep.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestMethod("POST");
		//con.setDoOutput(true);
		
		/*
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.flush();
		wr.close();
		*/
		int status = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		content.append("status response - " + status + System.lineSeparator());
		while ((inputLine = in.readLine()) != null)
		{
			content.append(inputLine);
		}
		in.close();
		return status;
	}
	/////////////////////////////////////////////////////////////////////////////////////////
	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public short getDevice_serialNum() {
		return device_serialNum;
	}

	public void setDevice_serialNum(short device_serialNum) {
		this.device_serialNum = device_serialNum;
	}

	public ActionEventProto getActionDescription() {
		return actionDescription;
	}

	public void setActionDescription(ActionEventProto actionDescription) {
		this.actionDescription = actionDescription;
	}
}

/*
 * NOTE:
 * This code is in case that parameter is an object.
public String getParameterToString() {
	String result = null;
	switch(this.actionDescription.getType())
	{
	case "int":
		result = Integer.toString((int)this.parameter);
		break;
	case "double":
		result = Double.toString((double)this.parameter);
		break;
	case "Range":
		Range rng = (Range)this.parameter;
		result = Double.toString(rng.min);
		result += "-";
		result += Double.toString(rng.max);
		break;
	case "bool":
		result = Boolean.toString((boolean)this.parameter);
		break;
	}
	return result;
}
*/
