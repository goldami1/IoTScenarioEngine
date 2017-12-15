package engine;

public class Customer extends User implements IUser {

	public Customer(User i_User) 
	{
		super(i_User.getID(), i_User.getName(), i_User.getEmail(), i_User.getUserPicURL());
	}
	
	//Continue implementation!
	
}
