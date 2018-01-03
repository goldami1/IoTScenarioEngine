package org.IoT_Project.Scenario_Engine.Models;

import org.eclipse.persistence.internal.expressions.ArgumentListFunctionExpression;

import DataBase.DBHandler;

public class Event extends Action{

	private char logicOperator;
	private boolean triggred;
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
		if(!DBHandler.getInstance().isEventUpdated(this))
			return this.toggleAction();
		else
			return 200;
	}
	
	public char getLogicOperator()
	{
		return this.logicOperator;
	}
	
	public void setLogicOper(ElogicOperand i_logicOper)
	{
		logicOperator = ElogicOperand.charFromELogicOperand(i_logicOper);
	}
}
