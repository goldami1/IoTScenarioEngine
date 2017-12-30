package utils;

public final class Constants {
	//Datamembers
	
	private static final String k_DatabaseConnectionURL = "jdbc:mysql://mtatest.ctssrz2bfct5.us-east-2.rds.amazonaws.com:3306/scenario_engine";  
	private static final String k_UserName= "scengine";
	private static final String k_Password= "mtaAmiRomaGalGil";
	
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
