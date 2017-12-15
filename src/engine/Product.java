package engine;

import java.util.*;

public class Product {
	short vendor_id;
	String name,picURL;
	boolean events_stat, actions_stat;
	LinkedList<Event> eventsList;
	LinkedList<Event> actionsList;
	
	public Product()
	{
		eventsList = new LinkedList<Event>();
		actionsList = new LinkedList<Event>();
		vendor_id=-1;
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
	
}
