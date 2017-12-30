package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.*;
import DataBase.DBHandler;

public class Vendor extends User implements IUser {

	private String description, logoPicURL;
	private LinkedList<Product> products;
	
	public Vendor(User i_User) throws SQLException
	{
		super(i_User.getName(), i_User.getEmail(), i_User.getUserPicURL());
		DBHandler db = DBHandler.getInstance();
		this.setIsCustomer(false);
		this.id = DBHandler.getInstance().getidForVendor();
		db.addVendor(i_User.getUsername(), i_User.getName(), i_User.getPassword(), i_User.getEmail(), "", "");
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

	@Override
	public boolean isEnduser() {
		// TODO Auto-generated method stub
		return false;
	}
}
