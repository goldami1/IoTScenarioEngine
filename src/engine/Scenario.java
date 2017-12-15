package engine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Scenario {
	private ArrayList<Event> eventsToHappen;
	private ArrayList<Action> actionsToHandle;
	
	public Scenario(ArrayList<Event> events, ArrayList<Action>actions)
	{
		this.eventsToHappen = new ArrayList(events);
		this.actionsToHandle = new ArrayList(actions);
	}
	public Scenario()
	{
		this.actionsToHandle = new ArrayList();
		this.eventsToHappen = new ArrayList();
	}
	
	public void addAction(Action actionToAdd)
	{
		this.actionsToHandle.add(actionToAdd);
	}
	public void addEvent(Event eventToAdd)
	{
		this.eventsToHappen.add(eventToAdd);
	}
	
	public Event getEventById(int id)
	{
		Event res = null;
		for(Event e:this.eventsToHappen)
		{
			if(e.getId() == id)
				res = e;
		}
		return res;
	}
	
	public Iterator getEvents()
	{
		return this.eventsToHappen.listIterator();
	}
	public Iterator getActions()
	{
		return this.actionsToHandle.listIterator();
	}
}
