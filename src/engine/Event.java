package engine;

public class Event extends Action{

	private boolean triggred;
	public Event(String name, String type, String param, String deviceEP, short deviceId) {
		super(name, type, param, deviceEP, deviceId);
		this.triggred = false;
	}
	
	public void setTrigger(boolean value)
	{
		this.triggred = value;
	}
	
	public boolean getTrigger()
	{
		return this.triggred;
	}
	
	public int toggleEvent() throws Exception
	{
		return this.toggleAction();
	}
}
