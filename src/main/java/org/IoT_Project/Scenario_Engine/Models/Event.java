package org.IoT_Project.Scenario_Engine.Models;

public class Event extends Action{

	private char logicOperator;
	private boolean triggred;

	public Event(String name, String type, String param, char logicOperator , String deviceEP, short deviceId) throws Exception {
		super(name, type, param, deviceEP, deviceId);
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
	
	public int toggleEvent() throws Exception
	{
		return this.toggleAction();
	}
	
	public char getLogicOperator()
	{
		return this.logicOperator;
	}
}
