package org.IoT_Project.Scenario_Engine.Models;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.SerializedName;

import DataBase.DBHandler;
import DataBase.NDBHandler;

@Entity
@Table(name = "SCENARIOS")
public class Scenario{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column (name = "scenario_id")
	@SerializedName("id")
	short id;
	@Column (name = "scenario_name")
	@SerializedName("name")
	String name;
	@Column (name = "scenario_description")
	@SerializedName("description")
	String description;
	@Column (name = "customer_id")
	@SerializedName("cust_id")
	short cust_id;
	@OneToMany
	@JoinTable(name = "SCENARIOS_ACTIONS", joinColumns=@JoinColumn(name = "scenario_id"),
				inverseJoinColumns=@JoinColumn(name = "action_id"))
	@SerializedName("actions")
	private List<Action> actions;
	@OneToMany
	@JoinTable(name = "SCENARIOS_EVENTS", joinColumns=@JoinColumn(name = "scenario_id"),
				inverseJoinColumns=@JoinColumn(name = "event_id"))
	@SerializedName("eventsToHappen")
	private Map<Short, Event> eventsToHappen;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToOne
	@JoinColumn(name = "cases_id")
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
				    List<Event> Scenario_events,
				    List<Action> Scenario_actions)
				    //CaseGroup Scenario_cases) 
	{
		this.id = Scenario_id;
		this.cust_id = Scenario_cust_id;
		this.name = Scenario_name;
		this.description = Scenario_description;
		this.eventsToHappen = new HashMap<Short, Event>();
		this.actions = Scenario_actions;
		
		List<Case> casesFromDB = new LinkedList<Case>();
		List<Event> caseEvents = new LinkedList<Event>();
		boolean createCase = true;
		for(Event e : Scenario_events)
		{
			/*
			 *  Checking if case #i has ended.
			 *   if YES than createCase = TRUE
			 */
			if(e.getLogicOperator() == '|')
			{
				e.setLogicOperator('&');
				createCase = true;
			}
			
			caseEvents.add(e);
			boolean isInMap = this.eventsToHappen.containsKey(e.getId());
			if(!isInMap)
				this.eventsToHappen.put(e.getId(), e);
			
			/*
			 *  In case that case #i has ended then insert it into the list of Cases, and create a new List
			 *  of Events for the next case.
			 */
			if(createCase)
			{
				casesFromDB.add(new Case(caseEvents, '|'));
				caseEvents = new LinkedList<Event>();
				createCase = false;
			}
		}
		this.cases = new CaseGroup(casesFromDB, '|');
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
					NDBHandler.getInstance().addEvent(e);
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
				if(a.actionDescription.getName() != MailAction.mailActionProto.getName())
					a = new Action(a);
				else
					a = new MailAction(a);
				NDBHandler.getInstance().addAction(a);
			}
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
	
	public static List<Event> getLogicEquation(Scenario s)
	{
		/*
		 *  Transform the cases structure into a list of events.
		 */
		List<Event> result = new LinkedList<Event>();
		for(Case cse : s.getCases().getCases())
		{
			Iterator<Event> itr_events = cse.getEvents().iterator();
			while(itr_events.hasNext())
			{
				Event e = itr_events.next();
				boolean lastEvent = !itr_events.hasNext();
				if(lastEvent)
					e.setLogicOperator('|');
				else
					e.setLogicOperator('&');
				result.add(e);
			}
		}
		return result;
	}
}
