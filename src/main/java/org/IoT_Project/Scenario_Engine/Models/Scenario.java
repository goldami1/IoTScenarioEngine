package org.IoT_Project.Scenario_Engine.Models;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import com.google.gson.annotations.SerializedName;

import DataBase.DBHandler;


public class Scenario{
	@SerializedName("id")
	short id;
	@SerializedName("name")
	String name;
	@SerializedName("description")
	String description;
	@SerializedName("cust_id")
	short cust_id;
	//@SerializedName("Events")
	//private List<Event>  Events;
	@SerializedName("actions")
	private List<Action> actions;
	@SerializedName("eventsToHappen")
	private Map<Short, Event> eventsToHappen;
	@SerializedName("cases")
	private CaseGroup cases;
	
	public Scenario() 
	{
		this.id = this.cust_id = -1;
		this.name = this.description = null;
		this.eventsToHappen = null;
		//this.Events = null;
		this.actions = null;
		this.cases = null;
	}
	
	public Scenario(short Scenario_id,
				    short Scenario_cust_id,
				    String Scenario_name, 
				    String Scenario_description, 
				    //List<Event> Scenario_events,
				    List<Action> Scenario_actions,
				    CaseGroup Scenario_cases) 
	{
		this.id = Scenario_id;
		this.cust_id = Scenario_cust_id;
		this.name = Scenario_name;
		this.description = Scenario_description;
		this.eventsToHappen = new HashMap<Short, Event>();
		//this.Events = new LinkedList<Event>();
		/*
		for(Event event : Scenario_events)
		{
			this.eventsToHappen.put(event.getId(), event);
		}
		*/
		//this.Events = Scenario_events;
		this.actions = Scenario_actions;
		this.cases = Scenario_cases;
		for(Case c : this.cases.getCases())
		{
			for(Event e : c.getEvents())
			{
				//this.Events.add(e);
				boolean isInMap = this.eventsToHappen.containsKey(e.getId());
				if(!isInMap)
					this.eventsToHappen.put(e.getId(), e);
			}
		}
	}
	
	
	/************   ONLY FOR SCENARIO NEW CREATION IN DB  *************/
	public Scenario(Scenario i_scenario) throws Exception
	{
		/*
		 * this constructor is only for usement in case of registering new scenario to the DB
		 */
		DBHandler db = DBHandler.getInstance();
		boolean isUpdated = i_scenario.getId() > 0;
		this.name = i_scenario.getName();
		this.description = i_scenario.getDescription();
		this.cust_id = i_scenario.getCust_id();
		//this.actionsToHandle = new HashMap<Short, Action>();
		this.eventsToHappen = new HashMap<Short, Event>();
		//this.Events = i_scenario.getEvents();
		this.actions = i_scenario.getActions();
		this.cases = i_scenario.getCases();
		
		
		/*
		 * NOTE: after this loop all events has been registered in DB, and toggled(if needed).
		 */
		for(Case c : this.cases.getCases())
		{
			for(Event e : c.getEvents())
			{
				boolean isEventUpdated = e.getId() > 0;
				if(!isEventUpdated)
				{
					e = new Event(e);
					db.addEvent(e);
				}
				this.eventsToHappen.put(e.getId(), e);
			}
		}
		
		/*
		 * NOTE: after this loop all actions has been registered in DB(if needed).
		 */
		for(Action a : this.actions)
		{
			//Action action = new Action(a);
			//this.actionsToHandle.put(action.getId(), action);
			boolean isActionUpdated = a.getId() > 0;
			if(!isActionUpdated)
			{
				a = new Action(a);
				db.addAction(a);
			}
		}
		if(!isUpdated)
		{
			this.id = db.getScenariosMaxAvailableIdx();
		}
		else
		{
			this.id = i_scenario.getId();
		}
	}
	/******************************************************************/

	public boolean resolveScenario()
	{
		boolean shouldBeHandled = true;
		/*
		for(Event e : this.Events)
		{
			shouldBeHandled &= e.isTriggered();
		}
		*/
		shouldBeHandled = this.cases.calculateCase();
		return shouldBeHandled;
	}

	public Event getEventById(short id)
	{
		return eventsToHappen.get(id);
	}
	/////////////////////////////////////////////////////////////////////

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

/*	public List<Event> getEvents() {
		return Events;
	}

	public void setEvents(List<Event> events) {
		Events = events;
	}*/

	public Map<Short, Event> getEventsToHappen() {
		return eventsToHappen;
	}

	public void setEventsToHappen(Map<Short, Event> eventsToHappen) {
		this.eventsToHappen = eventsToHappen;
	}

	public CaseGroup getCases() {
		return this.cases;
	}

	public void setCases(CaseGroup cases) {
		this.cases = cases;
	}
	
	public List<Action> getActions() {
		return this.actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	
}
