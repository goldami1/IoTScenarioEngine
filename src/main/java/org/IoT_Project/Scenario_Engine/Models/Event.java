package org.IoT_Project.Scenario_Engine.Models;

import DataBase.DBHandler;

public class Event extends Action{

	private char logicOperator;
	private boolean triggered;

	public Event()
	{
		super();
		this.logicOperator = 0;
		this.triggered = false;
	}
	
	public Event(short Action_id,
				 short Action_deviceSerialNum,
				 Object Action_parameter,
				 ActionEventProto Action_descriptor,
				 char Event_logicOperator,
				 boolean Event_triggered)
	{
		super(Action_id, Action_deviceSerialNum, Action_parameter, Action_descriptor);
		this.logicOperator = Event_logicOperator;
		this.triggered = Event_triggered;
	}

	public char getLogicOperator() {
		return logicOperator;
	}

	public void setLogicOperator(char logicOperator) {
		this.logicOperator = logicOperator;
	}

	public boolean isTriggered() {
		return triggered;
	}

	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}
	

	
	/*
	 * Need to think where to trigger event!!***
	private int toggleEvent() throws Exception
	{
		if(!DBHandler.getInstance().isEventUpdated(this))
			return this.toggleAction();
		else
			return 200;
	}
	*/
}
