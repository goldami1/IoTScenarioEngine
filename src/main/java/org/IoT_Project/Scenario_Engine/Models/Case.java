package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.SerializedName;

import DataBase.DBHandler;

@Entity
@Table (name = "CASES")
public class Case implements ICase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "case_id")
	@SerializedName("id")
	private short id;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinTable(name = "CASES_EVENTS", joinColumns=@JoinColumn(name = "case_id"),
				inverseJoinColumns=@JoinColumn(name = "event_id"))
	@SerializedName("events")
	List<Event> events;
	
	@Column(name = "logicOperator")
	@SerializedName("logicOperator")
	char logicOperator;
	

	public Case()
	{
		this.events = null;
		this.logicOperator = '&';
	}
	
	public Case(List<Event> events,
				char logicOperator)
	{
		this.events = events;
		this.logicOperator = logicOperator;
	}
	
	public Case(Case c) throws Exception
	{
		DBHandler db = DBHandler.getInstance();
		this.events = c.getEvents();
		for(Event e : this.events)
		{
			boolean isEventUpdated = e.getId() > 0;
			if(!isEventUpdated)
			{
				e = new Event(e);
				db.addEvent(e);
			}
		}
		this.logicOperator = c.getLogicOperator();
	}

	@Override
	public boolean calculateCase()
	{
		/*
		 * function: calculating each event in the case to determine if true\false.
		 */
		
		boolean isTrue = true;
		Iterator<Event> itr = this.events.iterator();
		while(itr.hasNext() && isTrue)
		{
			Event currentEvent = itr.next();
			isTrue &= currentEvent.isTriggered();
		}
		return isTrue;
	}

	@Override
	public char getLogicOperator() {
		return this.logicOperator;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	public void setLogicOperator(char logicOperator) {
		this.logicOperator = logicOperator;
	}
	
	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}
	
}
