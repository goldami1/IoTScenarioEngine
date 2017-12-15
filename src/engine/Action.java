package engine;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;


public class Action {
	private int id;
	private String name;
	private String type;
	private Object parameter;
	
	public Action(String name, String type, String param)
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
	}
	public Object getValue()
	{
		return this.parameter;
	}
	
	public Class getType()
	{
		return this.parameter.getClass();
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}
}
