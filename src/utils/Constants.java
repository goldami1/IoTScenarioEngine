package utils;

public final class Constants {
	//Datamembers
	private static final String k_DatabaseConnectionURL = "jdbc:mysql://localhost:8080/dbNH";  
	
	//Methods
	@SuppressWarnings("unused")
	public static String getDatabaseConnectionURL() { return k_DatabaseConnectionURL;}
	
	//Constractors
	private Constants()
	{
		
	}
}
