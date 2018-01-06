package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.*;
import DataBase.DBHandler;

public class Vendor extends User {

	private String description, logoPicURL;
	private LinkedList<Product> products;
	
	public Vendor()
	{
		super();
		 this.description = this.logoPicURL = null;
		 this.products = null;
	}
	
	public Vendor(short User_id,
				  String User_userName,
				  String User_password,
				  String User_name,
				  String User_picURL,
				  String User_email,
				  boolean User_isCustomer,
				  String Vendor_description,
				  String Vendor_logoPicURL,
				  LinkedList<Product> Vendor_products)
	{
		super(User_id, User_userName, User_password, User_name, User_email, User_picURL, User_isCustomer);
		this.description = Vendor_description;
		this.logoPicURL = Vendor_logoPicURL;
		this.products = Vendor_products;
	}
	
	/************   ONLY FOR VENDOR NEW CREATION IN DB   *************/
	public Vendor(User i_user) throws Exception
	{
		super(i_user);
		this.isCustomer = false;
		this.description = this.logoPicURL = null;
		this.products = null;
	}
	
	public Vendor(Vendor i_vendor) throws Exception
	{
		super(i_vendor);
		this.isCustomer = false;
		this.description = i_vendor.getDescription();
		this.logoPicURL = i_vendor.getLogoPicURL();
		this.products = null;
		DBHandler.getInstance().addVndor(i_vendor);

	}
	/*****************************************************************/
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogoPicURL() {
		return this.logoPicURL;
	}

	public void setLogoPicURL(String logoPicURL) {
		this.logoPicURL = logoPicURL;
	}

	public LinkedList<Product> getProducts() {
		return this.products;
	}

	public void setProducts(LinkedList<Product> products) {
		this.products = products;
	}
	
}
