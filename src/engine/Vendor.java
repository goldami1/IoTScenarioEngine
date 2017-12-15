package engine;

public class Vendor extends User implements IUser {

	public Vendor(User i_User)
	{
		super(i_User.getID(), i_User.getName(), i_User.getEmail(), i_User.getUserPicURL());
	}

	//Continue implementation
}
