package org.IoT_Project.Scenario_Engine.Models;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import DataBase.DBHandler;


public class Scenario{
	short id;
	String name;
	String description;
	short cust_id;
	private List<Event>  Events;
	private List<Action> Actions;
	private Map<Short, Event> eventsToHappen;
	//private Map<Short, Action> actionsToHandle;
	private ICase cases;
	
	public Scenario() 
	{
		this.id = this.cust_id = -1;
		this.name = this.description = null;
		this.eventsToHappen = null;
		this.cases = null;
	}
	
	public Scenario(short Scenario_id,
				    short Scenario_cust_id,
				    String Scenario_name, 
				    String Scenario_description, 
				    List<Event> Scenario_events,
				    List<Action> Scenario_actions) 
	{
		this.id = Scenario_id;
		this.cust_id = Scenario_cust_id;
		this.name = Scenario_name;
		this.description = Scenario_description;
		this.eventsToHappen = new HashMap<Short, Event>();
		//this.actiotionsToHandle = new HashMap<Short, Action>();
		for(Event event : Scenario_events)
		{
			this.eventsToHappen.put(event.getId(), event);
		}
		/*this.actionsToHandle = new HashMap<Short, Action>();
		for(Action action : Scenario_actions)
		{
			this.actionsToHandle.put(action.getId(), action);
		}*/
		this.Actions = Scenario_actions;
	}
	
	
	/************   ONLY FOR SCENARIO NEW CREATION IN DB  *************/
	public Scenario(Scenario i_scenario) throws Exception
	{
		/*
		 * this constructor is only for usement in case of registering new scenario to the DB
		 */
		boolean isUpdated = i_scenario.getId() > 0;
		this.name = i_scenario.getName();
		this.description = i_scenario.getDescription();
		this.cust_id = i_scenario.getCust_id();
		//this.actionsToHandle = new HashMap<Short, Action>();
		this.eventsToHappen = new HashMap<Short, Event>();
		
		short Events_StartID, Actions_StartID;
		Events_StartID = DBHandler.getInstance().getEventsMaxAvailableIdx();
		Actions_StartID = DBHandler.getInstance().getActionsMaxAvailableIdx();
		
		/*
		 * NOTE: after this loop all events has been registered in DB, and toggled(if needed).
		 */
		for(Event e : this.Events)
		{
			Event event = new Event(e);
			this.eventsToHappen.put(event.getId(), event);
		}
		/*
		 * NOTE: after this loop all actions has been registered in DB(if needed).
		 */
		for(Action a : this.Actions)
		{
			//Action action = new Action(a);
			//this.actionsToHandle.put(action.getId(), action);
			a = new Action(a);
		}
		if(!isUpdated)
		{
			this.id = DBHandler.getInstance().getScenariosMaxAvailableIdx();
			DBHandler.getInstance().addScenario(this);
		}
		else
		{
			this.id = i_scenario.getId();
		}
		this.cases = null;
	}
	/******************************************************************/

	public short getId() {
		return id;
	}


	public void setId(short id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getCust_id() {
		return cust_id;
	}

	public void setCust_id(short cust_id) {
		this.cust_id = cust_id;
	}

	public Map<Short, Event> getEventsToHappen() {
		return eventsToHappen;
	}


	public List<Event> getEvents() {
		return Events;
	}

	public void setEvents(List<Event> events) {
		Events = events;
	}

	public List<Action> getActions() {
		return Actions;
	}

	public void setActions(List<Action> actions) {
		Actions = actions;
	}

	/*
	 * NOTE: no need of Action map.
	 * 
	public Map<Short, Action> getActionsToHandle() {
		return actionsToHandle;
	}
	*/

	public ICase getCases() {
		return cases;
	}

	public void setCases(ICase cases) {
		this.cases = cases;
	}
	
	public boolean resolveScenario()
	{
		boolean shouldBeHandled = true;
		for(Event e : this.Events)
		{
			shouldBeHandled &= e.isTriggered();
		}
		return shouldBeHandled;
	}

	public Event getEventById(short id)
	{
		return eventsToHappen.get(id);
	}
}
