package org.IoT_Project.Scenario_Engine.Models;

import java.util.List;

import DataBase.DBHandler;

public class Device {
	
	private short id, serial_number, product_id, customer_id;
	//private List<Event> events;
	//private List<Action> actions;
	
	public Device(Customer i_Customer, short serial_number)
	{
		customer_id = i_Customer.getID();
		this.serial_number = serial_number;
		DBHandler db = DBHandler.getInstance();
		db.addDevice(this);
	}
	
	public Device()
	{
		this.id = this.serial_number = this.customer_id = this.product_id = -1;
	}
	
	public Device(Product i_product, short cust_id, short serial_num)
	{
		this.customer_id = cust_id;
		this.id = DBHandler.getInstance().getIdForDevice();
		this.serial_number = serial_number;
	}


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

	public short getProduct_id() {
		return product_id;
	}

	public void setProduct_id(short product_id) {
		this.product_id = product_id;
	}

	public short getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(short customer_id) {
		this.customer_id = customer_id;
	}

	/*
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
