package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.*;

import DataBase.DBHandler;

public class Product {
	protected short id, vendor_id;
	protected String name,picURL;
	protected boolean events_stat, actions_stat;
	protected LinkedList<ActionEventProto> actionAndEventList;
	protected String endPoint;
	
	public Product()
	{
		actionAndEventList = new LinkedList<ActionEventProto>();
		id=vendor_id=-1;
		name = picURL = null;
		events_stat = actions_stat = false;
	}
	
	public Product(Product i_product) throws SQLException
	{
		this.endPoint = i_product.getEndPoint();
		this.name = i_product.name;
		this.picURL = i_product.picURL;
		this.id = DBHandler.getInstance().getProductsMaxAvailableIdx();
		this.actionAndEventList = (LinkedList<ActionEventProto>) i_product.getSupportedActionsAndEvents();
	}
	
	public Product(Product i_product, short i_vendor_id) throws SQLException
	{
		this(i_product);
		this.vendor_id = i_vendor_id;
	}
	
	public Product(Product i_product, short i_vendor_id, short i_prod_id) throws SQLException
	{
		this(i_product);
		this.id = i_prod_id;
		this.vendor_id = i_vendor_id;
	}
	
	public Product(Product i_product, boolean i_eve_stat, boolean i_act_stat) throws SQLException
	{
		this(i_product);
		this.events_stat = i_eve_stat;
		this.actions_stat = i_act_stat;
	}
	
	public Product(String name, String picURL, String endPoint, List<ActionEventProto> actionsAndEvents)
	{
		this();
		this.name = name;
		this.picURL = picURL;
		this.endPoint = endPoint;
		this.actionAndEventList = (LinkedList<ActionEventProto>) actionsAndEvents;
		
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
	
	
	public Product addAction(ActionEventProto i_actionToAdd)
	{
		if(actions_stat == false)
			actions_stat = true;
		i_actionToAdd.setIsEvent(false);
		actionAndEventList.add(i_actionToAdd);
		return this;
	}
	
	public List<ActionEventProto> getActions()
	{
		List <ActionEventProto> res = new LinkedList<ActionEventProto>();
		for(ActionEventProto aep : this.actionAndEventList)
		{
			if(!aep.getIsEvent())
			{
				res.add(aep);
			}
		}
		return res;
	}
	
	public List<ActionEventProto> getEvents()
	{
		List <ActionEventProto> res = new LinkedList<ActionEventProto>();
		for(ActionEventProto aep : this.actionAndEventList)
		{
			if(aep.getIsEvent())
			{
				res.add(aep);
			}
		}
		return res;
	}
	
	public Product addEvent(ActionEventProto i_eventToAdd)
	{
		if(actions_stat == false)
			actions_stat = true;
		i_eventToAdd.setIsEvent(true);
		actionAndEventList.add(i_eventToAdd);
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
	
	
	public List<ActionEventProto> getSupportedActionsAndEvents()
	{
		return this.actionAndEventList;
	}

	public short getVenID() {
		return vendor_id;
	}

	public String getName() {
		return name;
	}

	public String getPicURL() {
		return picURL;
	}
	
	public boolean[] getEAState()
	{
		boolean[] res = new boolean[]{false,false};
		for(ActionEventProto aep: actionAndEventList)
		{
			if(!res[0]&&aep.getIsEvent())
			{
				res[0]=!res[0];
			}
			if(!res[1]&&!aep.getIsEvent())
			{
				res[1]=!res[1];
			}
			if(res[0]&&res[1])
			{
				break;
			}
		}
		return res;
	}
	
}
