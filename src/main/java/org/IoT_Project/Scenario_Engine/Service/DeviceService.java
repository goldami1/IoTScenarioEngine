package org.IoT_Project.Scenario_Engine.Service;

import java.util.*;
import java.util.Map.Entry;
import org.IoT_Project.Scenario_Engine.Models.*;
import DataBase.DBHandler;

public class DeviceService {
	
	
	public void HandleCall(short event_id, boolean i_value) throws Exception
	{	
		//
		DBHandler DB = DBHandler.getInstance();
		Scenario scenario = DB.getScenario(event_id);
		scenario.getEventById(event_id).setTriggered(i_value);
		
		if(scenario.resolveScenario())
		{
			activateActions(scenario);
		}
	}
	
	private void activateActions(Scenario i_scenario) throws Exception {
		
		Iterator<Action> itr = i_scenario.getActions().iterator();
		while(itr.hasNext())
		{
			Action currentAction = itr.next();
			currentAction.toggleAction();
		}
	}

	/*
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
	}
	*/
}

//Continue implementation!
