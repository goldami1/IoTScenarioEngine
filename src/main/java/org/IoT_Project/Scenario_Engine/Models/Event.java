package org.IoT_Project.Scenario_Engine.Models;

import DataBase.DBHandler;

public class Event extends Action{

	private char logicOperator;
	private boolean triggred;
	private boolean updated = false;

	public Event(Action protoEvent, char logicOperator) throws Exception {
		super(protoEvent);
		this.logicOperator = logicOperator;
		this.triggred = false;
		this.toggleEvent();
	}
	

	public void setTrigger(boolean value)
	{
		this.triggred = value;
	}
	
	public boolean getTrigger()
	{
		return this.triggred;
	}
	
	private int toggleEvent() throws Exception
	{
		if(!DBHandler.getInstance().checkifEventUpdated(this))
			return this.toggleAction();
		else
			return 200;
	}
	
	public char getLogicOperator()
	{
		return this.logicOperator;
	}
}
