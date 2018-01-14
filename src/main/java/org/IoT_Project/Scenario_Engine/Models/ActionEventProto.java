package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import DataBase.DBHandler;

public class ActionEventProto {
	@SerializedName("id")
	private short id;
	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("type")
	private String type;
	@SerializedName("prodId")
	private short prodId;
	@SerializedName("productEP")
	protected String productEP;
	@SerializedName("isEvent")
	private boolean isEvent;
	@SerializedName("supportedValues")
	private List<String> supportedValues;
	@SerializedName("minValue")
	private String min;
	@SerializedName("maxValue")
	private String max;
	
	public ActionEventProto()
	{
		this.id = this.prodId = -1;
		this.isEvent = false;
		this.name = this.type = this.productEP = this.min = this.max = this.description = null;
		this.supportedValues = null;
	}
	
	public ActionEventProto(short id,
						    String name,
						    String description,
						    String type,
						    List<String> supportedValues,
						    String min,
						    String max,
						    short prodId,
						    String productEp,
						    boolean isEvent)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.supportedValues = supportedValues;
		this.min = min;
		this.max = max;
		this.prodId = prodId;
		this.productEP = productEp;
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

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getSupportedValues() {
		return supportedValues;
	}

	public void setSupportedValues(List<String> supportedValues) {
		this.supportedValues = supportedValues;
	}
	
	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}
}
