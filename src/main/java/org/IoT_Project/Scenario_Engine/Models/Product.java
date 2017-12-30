package org.IoT_Project.Scenario_Engine.Models;

import java.util.*;

import DataBase.DBHandler;

public class Product {
	protected short id, vendor_id;
	protected String name,picURL;
	protected boolean events_stat, actions_stat;
	protected LinkedList<Event> eventsList;
	protected LinkedList<Action> actionsList;
	protected String endPoint;
	
	public Product()
	{
		eventsList = new LinkedList<Event>();
		actionsList = new LinkedList<Action>();
		id=vendor_id=-1;
		name = picURL = null;
		events_stat = actions_stat = false;
	}
	
	public Product(Product product, short vendor_id)
	{
		this.endPoint = product.getEndPoint();
		this.name = product.name;
		this.picURL = product.picURL;
		this.id = DBHandler.getInstance().getIdForProduct();
		this.vendor_id = vendor_id;
		this.eventsList = (LinkedList<Event>) product.getSupportedEvents();
		this.actionsList = (LinkedList<Action>) product.getSupportedActions();
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
	
	public String getEndPoint()
	{
		return this.endPoint;
	}
	
	//getters
	public short getID()
	{
		return id;
	}
	
	public List<Event> getSupportedEvents()
	{
		return this.eventsList;
	}
	
	public List<Action> getSupportedActions()
	{
		return this.actionsList;
	}
	
}
