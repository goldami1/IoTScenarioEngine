package org.IoT_Project.Scenario_Engine.Models;

import javax.persistence.Embeddable;

import com.google.gson.annotations.SerializedName;

@Embeddable
public class Range {
	@SerializedName("max")
	double max;
	@SerializedName("min")
	double min;
	@SerializedName("currentVal")
	double currentVal;
	@SerializedName("type")
	String type;
	

	public Range(String param)
	{
		//Assuming each parameter is parsed by ','
		
		String parsedString[] = param.split(",");
		this.max = Double.parseDouble(parsedString[0]);
		this.min = Double.parseDouble(parsedString[1]);
		this.currentVal = Double.parseDouble(parsedString[2]);
		this.type = parsedString[3];
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public Object getValue()
	{
		Object res = new Object();
		if(type == "int")
		{
			res = (int)this.currentVal;
		}
		else
		{
			res = this.currentVal;
		}
		return res;
	}
}
