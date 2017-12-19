package engine;

public class Event extends Action{

	private char logicOperator;
	private boolean triggred;
	public Event(String name, String type, String param, char logicOperator) {
		super(name, type, param);
		this.logicOperator = logicOperator;
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
	
	public char getLogicOperator()
	{
		return this.logicOperator;
	}
}
