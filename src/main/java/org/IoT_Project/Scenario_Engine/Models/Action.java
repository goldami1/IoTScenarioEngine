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

import javax.net.ssl.HttpsURLConnection;

import DataBase.DBHandler;


public class Action {
	protected short id;
	protected Object parameter;
	protected short device_serialNum;
	protected ActionEventProto actionDescription;
	
	public Action()
	{
		this.parameter = this.actionDescription = null;
		this.id = this.device_serialNum = -1;
	}
	
	public Action(Action i_action)
	{
		this();
		this.id = i_action.getId();
		this.parameter = i_action.getParameter();
		this.device_serialNum = i_action.getDevice_serialNum();
		this.actionDescription = i_action.getActionDescription();
	}
	
	public Action(ActionEventProto actionDescription, String param, short device_serialNum) throws SQLException
	{
		this.actionDescription = actionDescription;
		switch(this.actionDescription.getType())
		{
		case "int":
			this.parameter = Integer.parseInt(param);
			break;
		case "double":
			this.parameter = Double.parseDouble(param);
			break;
		case "range":
			this.parameter = new Range(param);
			break;
		case "bool":
			this.parameter = Boolean.parseBoolean(param);
			break;
		}
		this.device_serialNum = device_serialNum;
		if(actionDescription.getIsEvent())
			this.id = DBHandler.getInstance().getEventsMaxAvailableIdx();
		else
			this.id = DBHandler.getInstance().getActionsMaxAvailableIdx();
	}
	
	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public Object getParameter() {
		return parameter;
	}
	
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

	public void setParameter(Object parameter) {
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
	
	public ActionEventProto getPrototype()
	{
		return getActionDescription();
	}

	public void setActionDescription(ActionEventProto actionDescription) {
		this.actionDescription = actionDescription;
	}

	public final int toggleAction() throws Exception
	{
		StringBuilder URI = new StringBuilder();
		URI.append(this.actionDescription.getProductEP());
		URI.append("/");
		URI.append(this.actionDescription.getProdId());
		URI.append("/");
		if(this.actionDescription.getIsEvent())
		{
			URI.append(this.id);
			URI.append("/");
		}
		URI.append(this.actionDescription.getName());
		URI.append("/");
		switch(this.actionDescription.getType())
		{
		case "int":
			URI.append(Integer.toString((int)this.parameter));
			break;
		case "double":
			URI.append(Double.toString((double)this.parameter));
			break;
		case "bool":
			URI.append(Boolean.toString((boolean)this.parameter));
			break;
		case "Range":
			Range rng = (Range)this.parameter;
			if(rng.getType() == "int")
			{
				URI.append(Integer.toString((int)rng.min));
				URI.append("/");
				URI.append(Integer.toString((int)rng.max));
			}
			else
			{
				URI.append(Double.toString((double)rng.min));
				URI.append("/");
				URI.append(Double.toString((double)rng.max));
			}
			break;
		}
		URL ep = new URL(URI.toString());
		HttpURLConnection con = (HttpsURLConnection)ep.openConnection();
		con.setRequestMethod("POST");
		
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
}