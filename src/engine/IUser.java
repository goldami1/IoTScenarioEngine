package engine;

public interface IUser {
	public User setUserName(String i_userName);
	public User setPassword(String i_password);
	public short getID();
	public String getName();
	public String getEmail();
	public String getUserPicURL();
}
