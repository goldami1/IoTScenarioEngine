package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.*;
import DataBase.DBHandler;

public class Vendor extends User implements IUser {

	private String description, logoPicURL;
	private LinkedList<Product> products;
	
	public Vendor()
	{
		super();
		this.description = this.logoPicURL = null;
		this.products = new LinkedList<Product>();
	}
	
	public Vendor(User i_User) throws SQLException
	{
		super(i_User.getUserName(), i_User.getPassword(), i_User.getName(), i_User.getEmail(), i_User.getUserPicURL());
		DBHandler db = DBHandler.getInstance();
		this.setIsCustomer(false);
		this.id = db.getVendorsMaxAvailableIdx();
		db.addVendor(i_User.getUserName(), i_User.getName(), i_User.getPassword(), i_User.getEmail(), "", "");
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
	
	public void setDescription(String i_description)
	{
		this.description =i_description; 
	}
}
