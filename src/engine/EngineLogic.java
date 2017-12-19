package engine;

import java.util.*;
import java.util.Map.Entry;

import db.*;

public class EngineLogic {
	
	
	public void HandleEvent(Event i_event)
	{
		DBHandler DB = DBHandler.getInstance();
		LinkedList<Scenario> scenarios = DB.getScenariosByEvent(i_event);
		for(Scenario currentScenario:scenarios)
		{
			Event curEvent = currentScenario.getEventById(i_event.getId());
			curEvent.setTrigger(true);
			if(isScenarioAwaken(currentScenario))
			{
				activateActions(currentScenario);
			}
		}
		
	}
	
	private void activateActions(Scenario i_scenario) {
		// TODO Auto-generated method stub
		
	}

	private boolean isScenarioAwaken(Scenario i_scenario)
	{
		Iterator<Entry<Short, Event>> itr = i_scenario.getEvents();
		boolean isAwakeRes = true;
		
		while(itr.hasNext())
		{
			Event currentEvent = itr.next().getValue();
			if(currentEvent.getLogicOperator() == '|')
			{
				isAwakeRes|=currentEvent.getTrigger();
			}
			else
			{
				isAwakeRes&=currentEvent.getTrigger();
			}
			if(!isAwakeRes)
				break;
		}
		return isAwakeRes;
	}
}

//Continue implementation!
