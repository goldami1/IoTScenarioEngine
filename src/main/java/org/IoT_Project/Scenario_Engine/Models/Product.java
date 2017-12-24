package org.IoT_Project.Scenario_Engine.Models;

import java.util.*;

public class Product {
	private short id, vendor_id;
	private String name,picURL;
	private boolean events_stat, actions_stat;
	private LinkedList<Event> eventsList;
	private LinkedList<Event> actionsList;
	String endPoint;
	
	public Product()
	{
		eventsList = new LinkedList<Event>();
		actionsList = new LinkedList<Event>();
		id=vendor_id=-1;
		name = picURL = null;
		events_stat = actions_stat = false;
	}
	
	public Product setVendorID(short i_vendor_id)
	{
		vendor_id = i_vendor_id;
		return this;
	}
	
	public Product setName(String i_name)
	{
		name = i_name;
		return this;
	}
	
	public Product setPicURL(String i_picURL)
	{
		picURL = i_picURL;
		return this;
	}
	
	public Product addEvent(Event i_eventToAdd)
	{
		if(events_stat == false)
			events_stat = true;
		eventsList.add(i_eventToAdd);
		return this;
	}
	
	public Product addAction(Event i_actionToAdd)
	{
		if(actions_stat == false)
			actions_stat = true;
		actionsList.add(i_actionToAdd);
		return this;
	}
	
	public void setEndPoint(String ep)
	{
		this.endPoint = ep;
	}
	
	//getters
	public short getID()
	{
		return id;
	}
	
}
