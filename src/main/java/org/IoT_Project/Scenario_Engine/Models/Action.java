package org.IoT_Project.Scenario_Engine.Models;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Action {
	protected short id;
	protected String name;
	protected String type;
	protected Object parameter;
	protected String deviceEP;
	protected short deviceId;
	
	public Action(String name, String type, String param, String deviceEP, short deviceId)
	{
		this.name = name;
		this.type = type;
		switch(this.type)
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
		this.setEndPoint(deviceEP);
		this.deviceId = deviceId;
	}
	public Object getValue()
	{
		return this.parameter;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public void setId(short id)
	{
		this.id = id;
	}
	
	public short getId()
	{
		return this.id;
	}
	
	public short getDeviceId()
	{
		return this.deviceId;
	}
	
	public void setEndPoint(String ep)
	{
		this.deviceEP = ep;
	}
	
	public String getEndPoint()
	{
		return this.deviceEP;
	}
	public String getName()
	{
		return this.name;
	}
	
	public final int toggleAction() throws Exception
	{
		StringBuilder URI = new StringBuilder();
		URI.append(this.getEndPoint());
		URI.append("/");
		URI.append(this.getDeviceId());
		URI.append("/");
		URI.append(this.getName());
		URI.append("/");
		switch(this.getType())
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
