package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.gson.annotations.SerializedName;

import DataBase.DBHandler;

@Entity
@Table(name = "DEVICES", uniqueConstraints = {@UniqueConstraint(columnNames = {"device_sn"})})
public class Device {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "device_id")
	@SerializedName("id")
	private short id;
	@Column(name = "device_sn")
	@SerializedName("serial_number")
	private short serial_number;
	@Column(name = "customer_id")
	@SerializedName("customer_id")
	private short customer_id;
	@Embedded
	@Column(name = "protoDevice")
	@SerializedName("protoDevice")
	private Product protoDevice;

	public Device()
	{
		this.id = this.serial_number = this.customer_id = -1;
		this.protoDevice = null;
	}
	
	public Device(short Device_id,
			   	  short Device_serialNum,
			   	  short Device_customer_id,
			   	  Product Device_prototype)
	{
		this.id = Device_id;
		this.serial_number = Device_serialNum;
		this.customer_id = Device_customer_id;
		this.protoDevice = Device_prototype;
	}
	
	/************   ONLY FOR CUSTOMER NEW CREATION IN DB  *************/
	public Device(Device i_device) throws Exception
	{
		DBHandler db = DBHandler.getInstance();
		boolean isUpdated = i_device.getId() > -1;
		if(!isUpdated)
		{
			this.id = db.getDevicesMaxAvailableIdx();
		}
		else
			this.id = i_device.getId();
		this.serial_number = i_device.getSerial_number();
		this.protoDevice = i_device.getProtoDevice();
	}
	/*******************************************************************/
	
	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public short getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(short serial_number) {
		this.serial_number = serial_number;
	}

	public short getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(short customer_id) {
		this.customer_id = customer_id;
	}

	public Product getProtoDevice() {
		return protoDevice;
	}

	public void setProtoDevice(Product protoDevice) {
		this.protoDevice = protoDevice;
	}
	
	
	/*
	public Device(Customer i_Customer, short serial_number) throws SQLException
	{
		customer_id = i_Customer.getID();
		this.serial_number = serial_number;
		DBHandler db = DBHandler.getInstance();
		db.addDevice(this);
	}
	
	public Device(Product i_product, short i_cust_id, short i_serial_num) throws SQLException
	{
		customer_id = i_cust_id;
		id = DBHandler.getInstance().getProductsMaxAvailableIdx();
		serial_number = i_serial_num;
		protoDevice = i_product;
	}
	
	public Device(Product i_product, short i_cust_id, short i_serial_num, short i_prod_id, short i_dev_id) throws SQLException
	{
		this(i_product, i_cust_id, i_serial_num);
		product_id = i_prod_id;
		id = i_dev_id;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	*/
	

}
