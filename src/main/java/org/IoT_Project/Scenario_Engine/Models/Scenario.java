package org.IoT_Project.Scenario_Engine.Models;
import java.util.*;
import java.util.Map.Entry;

import DataBase.DBHandler;


public class Scenario{
	short id;
	String name;
	
	private Map<Short, Event> eventsToHappen;
	private Map<Short, Action> actionsToHandle;
	private List<ICase> cases;
	
	public Scenario()
	{
		this.eventsToHappen = new HashMap<Short, Event>();
		this.actionsToHandle = new HashMap<Short, Action>();
		name = null;
		this.cases = new ArrayList<ICase>();
	}
	
	public Scenario(String i_scenario)
	{
		this();
		this.id = DBHandler.getInstance().getIdForScenario();
		parseScenario(i_scenario);
		
	}

	public Iterator<ICase> getCases()
	{
		return this.cases.iterator();
	}
	/*
	public Scenario(String i_name)
	{
		this();
		name = i_name;
	}
	*/
	
	private void parseScenario(String i_scenario)
	{
		/*
		 * function: parsing String i_scenario representing scenario to a Scenario object.
		 * note: should parse into ICases, which each one will have Events.
		 */
	}
	
	public void addAction(Action actionToAdd)
	{
		//this.actionsToHandle.add(actionToAdd);
		actionsToHandle.put(actionToAdd.getId(), actionToAdd);
	}
	public void addEvent(Event eventToAdd)
	{
		//this.eventsToHappen.add(eventToAdd);
		eventsToHappen.put(eventToAdd.getId(), eventToAdd);
	}
	
	
	public void addScenarioToDB()
	{
		DBHandler db = DBHandler.getInstance();
		db.addScenario(this);
	}
	
	public Event getEventById(short id)
	{
		return eventsToHappen.get(id);
	}
	
	public Iterator<Entry<Short, Event>> getEvents()
	{
		return eventsToHappen.entrySet().iterator();
	}
	public Iterator<Entry<Short, Action>> getActions()
	{
		return actionsToHandle.entrySet().iterator();
	}
	
	public short getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	
}
