package engine;

import java.util.*;
import java.util.Map.Entry;

import db.*;

public class EngineLogic {
	
	
	public void HandleEvent(Event i_event) throws Exception
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
		boolean isAwakeRes = true;
		Iterator<ICase> itr = i_scenario.getCases();
		while(itr.hasNext())
		{
			ICase currentCase = itr.next();
			boolean caseResult = currentCase.calculateCase();
			if(currentCase.getLogicOperator() == '|')
			{
				isAwakeRes |= caseResult;
			}
			else		//logicOperator = '&'
			{
				isAwakeRes &= caseResult;
			}
		}
		return isAwakeRes;
		/*
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
		*/
		return isAwakeRes;
	}
}

//Continue implementation!
