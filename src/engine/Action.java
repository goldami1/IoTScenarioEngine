package engine;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;


public class Action {
	private short id;
	private String name;
	private String type;
	private Object parameter;
	private String deviceEP;
	private short deviceId;
	
	public Action(String name, String type, String param, String deviceEP, short deviceId)
	{
		this.name = name;
		this.type = type;
		switch(this.type)
		{
		case "int":
			this.parameter = Integer.parseInt(param);
			break;
		case "double":
			this.parameter = Double.parseDouble(param);
			break;
		case "range":
			this.parameter = new Range(param);
			break;
		case "bool":
			this.parameter = Boolean.parseBoolean(param);
			break;
		}
		this.setEndPoint(deviceEP);
		this.deviceId = deviceId;
	}
	public Object getValue()
	{
		return this.parameter;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public void setId(short id)
	{
		this.id = id;
	}
	
	public short getId()
	{
		return this.id;
	}
	
	public short getDeviceId()
	{
		return this.deviceId;
	}
	
	public void setEndPoint(String ep)
	{
		this.deviceEP = ep;
	}
	
	public String getEndPoint()
	{
		return this.deviceEP;
	}
	public String getName() {
		return this.name;
	}
}
