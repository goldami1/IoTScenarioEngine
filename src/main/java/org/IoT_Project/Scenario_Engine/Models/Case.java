package org.IoT_Project.Scenario_Engine.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Case implements ICase {

	List<Event> events;
	char logicOperator;
	public Case(String caseObj)	//include only Events.
	{
		events = new ArrayList<Event>();
		this.logicOperator = '&';
		String Evnts[] = caseObj.split("|"); //Evnt1 | Evnt2 | ... | Evntn
		for(String currentEvent: Evnts)
		{
			events.add(ParseEvent(currentEvent));
		}
		
	}
	@Override
	public boolean calculateCase()
	{
		/*
		 * function: calculating each event in the case to determine if true\false.
		 */
		
		boolean isTrue = true;
		Iterator<Event> itr = this.events.iterator();
		while(itr.hasNext())
		{
			Event currentEvent = itr.next();
			if(currentEvent.getLogicOperator() == '|')
			{
				isTrue |= currentEvent.getTrigger();
			}
			else			//logicOperator = '&'
			{
				isTrue &= currentEvent.getTrigger();
			}
		}
		return isTrue;
	}

	private Event ParseEvent(String event)
	{
		/*
		 * function: parsing a String representation of event into an Event object
		 */
		Event result = null;
		
		return result;
	}
	@Override
	public char getLogicOperator() {
		return this.logicOperator;
	}
	
}
