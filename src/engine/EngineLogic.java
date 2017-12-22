package engine;

import java.util.*;
import java.util.Map.Entry;

import db.*;

public class EngineLogic {
	
	
	public void HandleEvent(Event i_event) throws Exception
	{
		DBHandler DB = DBHandler.getInstance();
		LinkedList<Scenario> scenarios = DB.getScenariosByEvent(i_event);
		for(Scenario s:scenarios)
		{
			Event curEvent = s.getEventById(i_event.getId());
			curEvent.setTrigger(true);
			if(isScenarioAwaken(s))
			{
				activateActions(s);
			}
		}
		
	}
	
	private void activateActions(Scenario i_scenario) throws Exception {
		
		Iterator<Entry<Short, Action>> itr = i_scenario.getActions();
		while(itr.hasNext())
		{
			Action currentAction = itr.next().getValue();
			currentAction.toggleAction();
		}
	}

	private boolean isScenarioAwaken(Scenario i_scenario)
	{
		Iterator<Entry<Short, Event>> itr = i_scenario.getEvents();
		boolean isAwakeRes = true;
		
		while(itr.hasNext())
		{
			isAwakeRes&=itr.next().getValue().getTrigger();
			if(!isAwakeRes)
				break;
		}
		return isAwakeRes;
	}
}

//Continue implementation!
