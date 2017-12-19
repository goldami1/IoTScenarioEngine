package engine;
import java.util.*;
import java.util.Map.Entry;

import db.DBHandler;


public class Scenario{
	short id;
	String name;
	private Map<Short, Event> eventsToHappen;
	private Map<Short, Action> actionsToHandle;
	
	public Scenario()
	{
		this.eventsToHappen = new HashMap<Short, Event>();
		this.actionsToHandle = new HashMap<Short, Action>();
		name = null;
	}
	
	public Scenario(String i_name)
	{
		this();
		name = i_name;
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
