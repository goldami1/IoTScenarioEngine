import java.util.ArrayList;
import java.util.Iterator;


public class EngineLogic {
	public void HandleEvent(Event evnt)
	{
		ArrayList<Scenario> scenarios = DB.getScenarios(evnt);
		for(Scenario s:scenarios)
		{
			Event curEvent = s.getEventById(evnt.getId());
			curEvent.setTrigger(true);
			if(CheckScenarioTriggers(s))
			{
				activateActions(s);
			}
		}
		
	}
	
	private void activateActions(Scenario s) {
		// TODO Auto-generated method stub
		
	}

	private boolean CheckScenarioTriggers(Scenario scnrio)
	{
		Iterator<Event> itr = scnrio.getEvents();
		while(itr.hasNext())
		{
			Event curEvent = itr.next();
			if(!curEvent.getTriggered())
				return false;
		}
		return true;
	}
}
