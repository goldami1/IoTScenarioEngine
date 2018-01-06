package org.IoT_Project.Scenario_Engine.Models;

import DataBase.DBHandler;

public class Event extends Action{

	private char logicOperator;
	private boolean triggered;
	public enum ElogicOperand
	{
		oper_AND('&'),
		oper_OR('|');
		
		private char logOper;
		
		private ElogicOperand(char i_logicOper) {
		logOper = i_logicOper;
		}
		
		public static ElogicOperand setLogicOperFromChar(char i_charInput) throws IllegalArgumentException
		{
			if(i_charInput == '&')
				return ElogicOperand.oper_AND;
			else if(i_charInput == '|')
				return ElogicOperand.oper_OR;
			else
				throw new IllegalArgumentException("Wrong input as logic operand provided!");
		}
		
		public static char charFromELogicOperand(ElogicOperand i_logicOperand)
		{
			if(i_logicOperand == ElogicOperand.oper_AND)
			{
				return '&';
			}
			else
			{
				return '|';
			}
		}
	};
	
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
	
	public void setLogicOper(ElogicOperand i_logicOper)
	{
		logicOperator = ElogicOperand.charFromELogicOperand(i_logicOper);
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
