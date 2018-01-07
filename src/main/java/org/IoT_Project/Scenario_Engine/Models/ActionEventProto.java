package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import com.google.gson.annotations.SerializedName;
import DataBase.DBHandler;

public class ActionEventProto {
	@SerializedName("id")
	private short id;
	@SerializedName("name")
	private String name;
	@SerializedName("type")
	private String type;
	@SerializedName("prodId")
	private short prodId;
	@SerializedName("productEP")
	protected String productEP;
	@SerializedName("isEvent")
	private boolean isEvent;
	
	public ActionEventProto()
	{
		this.id = this.prodId = -1;
		this.isEvent = false;
		this.name = this.type = this.productEP = null;
	}
	
	public ActionEventProto(short id,
						    String name,
						    String type,
						    short prodId,
						    String productEp,
						    boolean isEvent)
	{
		this.id = id;
		this.name = name;
		this.type = type;
		this.prodId = prodId;
		this.isEvent = isEvent;
	}

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public short getProdId() {
		return prodId;
	}

	public void setProdId(short prodId) {
		this.prodId = prodId;
	}

	public String getProductEP() {
		return productEP;
	}

	public void setProductEP(String productEP) {
		this.productEP = productEP;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public void setIsEvent(boolean b) {
		this.isEvent = b;
	}

	public boolean getIsEvent() {
		return this.isEvent;
	}
}
