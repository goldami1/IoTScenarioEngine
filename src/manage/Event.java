
public class Event extends Action{

	private boolean triggred;
	public Event(String name, String type, String param) {
		super(name, type, param);
		this.triggred = false;
	}
	
	public void setTrigger(boolean value)
	{
		this.triggred = value;
	}
	
	public boolean getTriggered()
	{
		return this.triggred;
	}
}
