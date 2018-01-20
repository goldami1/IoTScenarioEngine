package org.IoT_Project.Scenario_Engine.Models;

import java.awt.image.BufferedImage;
import java.util.*;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.SerializedName;

import DataBase.DBHandler;
import utils.serverImgManager;

@Entity
@Table(name = "VENDORS")
@AttributeOverrides({
	@AttributeOverride(name="user_id", column=@Column(name = "vendor_id")),
	@AttributeOverride(name ="name", column=@Column(name = "vendor_name"))
	})
public class Vendor extends User {

	@SerializedName("description")
	private String description;
	@SerializedName("logoPicURL")
	private String logoPicURL;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinTable(name = "VENDORS_PRODUCTS", joinColumns=@JoinColumn(name = "vendor_id"),
				inverseJoinColumns=@JoinColumn(name = "product_id"))
	@SerializedName("products")
	private List<Product> products;
	
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
	
	public BufferedImage getLogoPic()
	{
		return serverImgManager.getImage(this.userPicURL);
	}
	
	/************   ONLY FOR VENDOR NEW CREATION IN DB   *************/
	public Vendor(User i_user) throws Exception
	{
		super(i_user);
		this.isCustomer = i_user.isCustomer();
		this.description = this.logoPicURL = null;
		this.products = null;
	}
	
	public Vendor(Vendor i_vendor) throws Exception
	{
		super(	i_vendor.getId(),
				i_vendor.getUserName(),
				i_vendor.getPassword(),
				i_vendor.getName(),
				i_vendor.getEmail(),
				i_vendor.getLogoPicURL(), false);
		this.isCustomer = this.isCustomer();
		this.description = i_vendor.getDescription();
		this.logoPicURL = i_vendor.getLogoPicURL();
		this.products = null;
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

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(LinkedList<Product> products) {
		this.products = products;
	}
	
}
