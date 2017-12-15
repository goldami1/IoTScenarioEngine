package utils;

public final class Constants {
	//Datamembers
	
	private static final String k_DatabaseConnectionURL = "jdbc:mysql://localhost:3306/scenario_engine?useSSL=false";  
	private static final String k_UserName= "student";
	private static final String k_Password= "student";
	
	//Methods
	@SuppressWarnings("unused")
	public static String getDatabaseConnectionURL() { return k_DatabaseConnectionURL;}
	public static String getDatabaseUserName() { return k_UserName;}
	public static String getDatabasePassword() { return k_Password;}
	
	//Constractors
	private Constants()
	{
		
	}
}
