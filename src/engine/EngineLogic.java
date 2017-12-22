package engine;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
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
		StringBuilder URI = new StringBuilder();
		
		Iterator<Entry<Short, Action>> itr = i_scenario.getActions();
		while(itr.hasNext())
		{
			Action currentAction = itr.next().getValue();
			URI.append(currentAction.getEndPoint());
			URI.append("/");
			URI.append(currentAction.getDeviceId());
			URI.append("/");
			URI.append(currentAction.getName());
			URL ep = new URL(URI.toString());
			HttpURLConnection con = (HttpsURLConnection)ep.openConnection();
			
			
			con.setRequestMethod("POST");
			switch(currentAction.getType())
			{
			case "int":
				con.setRequestProperty(currentAction.getName(), Integer.toString((int)currentAction.getValue()));
				break;
			case "double":
				con.setRequestProperty(currentAction.getName(), Double.toString((double)currentAction.getValue()));
				break;
			case "Range":
				Range rng = (Range)currentAction.getValue();
				if(rng.getType() == "int")
				{
					con.setRequestProperty(currentAction.getName(), Integer.toString((int)rng.getValue()));
				}
				else
				{
					con.setRequestProperty(currentAction.getName(), Double.toString((double)rng.getValue()));
				}
				break;
			case "bool":
				con.setRequestProperty(currentAction.getName(), Boolean.toString((boolean)currentAction.getValue()));
				break;
			}
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
