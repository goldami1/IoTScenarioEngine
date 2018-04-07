package org.IoT_Project.Scenario_Engine.Service;

import java.util.*;
import java.util.Map.Entry;
import org.IoT_Project.Scenario_Engine.Models.*;
import DataBase.NDBHandler;

public class DeviceService {
	
	
	public void HandleCall(short device_id, String event_name, short cust_id, boolean device_signal) throws Exception
	{	
		
		/*NDBHandler DB = NDBHandler.getInstance();
		List<Scenario> scenarios = DB.getScenarios(cust_id);
		for(Scenario scenario : scenarios) {
			scenario.HandleEvents(device_id, event_name, device_signal);
		}*/
		
		LinkedList<Event> events = new LinkedList<Event>();
		LinkedList<Action> actions = new LinkedList<Action>();
		ActionEventProto aep1 = new ActionEventProto((short)1,
												   "TestEvent1",
												   "TestEvent_description",
												   null,
												   null,
												   null,
												   null,
												   null,
												   null,
												   (short)1,
												   "stam Test_EP",
												   true);
		ActionEventProto aep2 = new ActionEventProto((short)1,
												   "TestEvent2",
												   "TestEvent_description",
												   null,
												   null,
												   null,
												   null,
												   null,
												   null,
												   (short)1,
												   "stam Test_EP",
												   true);
		events.add(new Event((short)1,
							(short)1,
							null,
							aep1,
							'|',
							false
							));
		events.add(new Event((short)2,
							(short)1,
							null,
							aep2,
							'|',
							false
							));
		
		Scenario Test = new Scenario((short)1,
									(short)1,
									"TestScenario_name",
									"Test_description",
									events,
									actions);
		Test.HandleEvents((short)1, "TestEvent1", true);
		Test.HandleEvents((short)1, "TestEvent2", true);
		
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
