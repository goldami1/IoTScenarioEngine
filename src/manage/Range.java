
public class Range {
	double max;
	double min;
	double currentVal;
	String type;
	

	public Range(String param)
	{
		//Assuming each parameter is parsed by ','
		
		String parsedString[] = param.split(",");
		this.max = Double.parseDouble(parsedString[0]);
		this.min = Double.parseDouble(parsedString[1]);
		this.currentVal = Double.parseDouble(parsedString[2]);
		this.type = parsedString[3];
	}
}
