package utils;

public final class Constants {
	//Datamembers
	
	private static final String k_DatabaseConnectionURL = "jdbc:mysql://db4free.net:3306/scengine";  
	private static final String k_UserName= "scengine";
	private static final String k_Password= "Aa123456";
	
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
