package org.IoT_Project.Scenario_Engine.Service;

import java.util.*;
import java.util.Map.Entry;
import org.IoT_Project.Scenario_Engine.Models.*;
import DataBase.NDBHandler;

public class DeviceService {
	
	
	public void HandleCall(short cust_id, short serial_num, boolean value) throws Exception
	{	
		//
		NDBHandler DB = NDBHandler.getInstance();
		List<Scenario> scenarios = DB.getScenarios(cust_id);
		for (Scenario s : scenarios) {
			for(Event event : s.getEventsToHappen().values()) {
				if(event.getDevice_serialNum() == serial_num) {
					Event mapped_event = s.getEventById(event.getId());
					mapped_event.setTriggered(value);
				}
				if(s.resolveScenario())
				{
					activateActions(s);
				}
			}
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
