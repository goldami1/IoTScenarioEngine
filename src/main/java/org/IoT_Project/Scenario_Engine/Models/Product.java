package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.*;

import DataBase.DBHandler;

public class Product {
	protected short id, vendor_id;
	protected String name, picURL, description;
	protected LinkedList<ActionEventProto> actionAndEventList;
	protected String endPoint;
	protected boolean actionState, eventState;
	
	public Product()
	{
		this.actionAndEventList = new LinkedList<ActionEventProto>();
		this.id=this.vendor_id=-1;
		this.name = this.picURL = null;
		this.actionState = this.eventState = false;
	}
	
	public Product(short Product_id,
				   short Vendor_id,
				   String Product_name,
				   String Product_description,
				   String Product_picURL,
				   String Product_endpoint,
				   LinkedList<ActionEventProto> Product_actionsAndEvents)
	{
		this.id = Product_id;
		this.vendor_id = Vendor_id;
		this.name = Product_name;
		this.description = Product_description;
		this.picURL = Product_picURL;
		this.endPoint = Product_endpoint;
		this.actionAndEventList = Product_actionsAndEvents;
		this.setStates();
	}
	
	/************   ONLY FOR PRODUCT NEW CREATION IN DB   *************/
	public Product(Product i_product) throws Exception
	{
		DBHandler db = DBHandler.getInstance();
		this.id = db.getProductsMaxAvailableIdx();
		for(ActionEventProto Current_aep : this.actionAndEventList)
		{
			Current_aep.setId(db.getActionsProtoMaxAvailableIdx());
			if(Current_aep.getIsEvent())
				this.eventState = true;
			else
				this.actionState = true;
		}
	}
	/*****************************************************************/

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public short getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(short vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicURL() {
		return picURL;
	}

	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LinkedList<ActionEventProto> getActionAndEventList() {
		return actionAndEventList;
	}

	public void setActionAndEventList(LinkedList<ActionEventProto> actionAndEventList) {
		this.actionAndEventList = actionAndEventList;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
	public LinkedList<ActionEventProto> getSupportedActions()
	{
		LinkedList<ActionEventProto> res = null;
		res = this.getObjectsFromAEList(false);
		return res;
	}
	public LinkedList<ActionEventProto> getSupportedEvents()
	{
		LinkedList<ActionEventProto> res = null;
		res = this.getObjectsFromAEList(true);
		return res;
	}
	
	public boolean[] getEAState()
	{
		boolean res[] = {this.actionState, this.eventState};
		return res;
	}
	
	private LinkedList<ActionEventProto> getObjectsFromAEList(boolean isEventRequired)
	{
		LinkedList<ActionEventProto> res = new LinkedList<ActionEventProto>();
		for(ActionEventProto Current_aep : this.actionAndEventList)
		{
			if(Current_aep.getIsEvent() == isEventRequired)
			{
				res.add(Current_aep);
			}
		}
		return res;
	}
	
	private void setStates() {
		for(ActionEventProto Current_aep : this.actionAndEventList)
		{
			if(this.actionState && this.eventState)
				break;
			if(Current_aep.getIsEvent())
				this.eventState = true;
			else
				this.actionState = true;
		}
	}
}
