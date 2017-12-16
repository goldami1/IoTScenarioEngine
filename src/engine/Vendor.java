package engine;

import java.util.*;
import db.DBHandler;

public class Vendor extends User implements IUser {

	private String description, logoPicURL;
	private LinkedList<Product> products;
	
	public Vendor(User i_User)
	{
		super(i_User.getName(), i_User.getEmail(), i_User.getUserPicURL());
		products = new LinkedList<Product>();
		DBHandler db = DBHandler.getInstance();
		db.addVendor(this.getName(), this.getUsername(), this.getPassword(), this.getEmail(), description, logoPicURL);
	}

	public void addProduct(Product i_Product)
	{
		products.add(i_Product);
	} 
	
	public String getDescription()
	{
		return description;
	}
	
	public String getLogoURL()
	{
		return logoPicURL;
	}
}
