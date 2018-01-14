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
	@SerializedName("types")
	private List<String> types;
	@SerializedName("prodId")
	private short prodId;
	@SerializedName("productEP")
	protected String productEP;
	@SerializedName("isEvent")
	private boolean isEvent;
	@SerializedName("supportedValues")
	private List<List<String>> supportedValues;
	@SerializedName("supportedParametersName")
	private List<String> supportedParametersName;
	@SerializedName("minValues")
	private List<String> min;
	@SerializedName("maxValues")
	private List<String> max;
	
	public ActionEventProto()
	{
		this.id = this.prodId = -1;
		this.isEvent = false;
		this.name  = this.productEP = null;
		this.min = this.max = this.types = null;
		this.description = null;
		this.supportedValues = null;
		this.supportedParametersName = null;
	}
	
	public ActionEventProto(short id,
						    String name,
						    String description,
						    List<String> types,
						    List<List<String>> supportedValues,
						    List<String> supportedParametersName,
						    List<String> min,
						    List<String> max,
						    short prodId,
						    String productEp,
						    boolean isEvent)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.types = types;
		this.supportedValues = supportedValues;
		this.supportedParametersName = supportedParametersName;
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

	public List<String> getTypes() {
		return this.types;
	}
	
	public void setTypes(List<String> types) {
		this.types = types;
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

	public List<List<String>> getSupportedValues() {
		return supportedValues;
	}

	public void setSupportedValues(List<List<String>> supportedValues) {
		this.supportedValues = supportedValues;
	}
	
	public List<String> getMin() {
		return min;
	}

	public void setMin(List<String> min) {
		this.min = min;
	}

	public List<String> getMax() {
		return max;
	}

	public void setMax(List<String> max) {
		this.max = max;
	}

	public List<String> getSupportedParametersName() {
		return supportedParametersName;
	}

	public void setSupportedParametersName(List<String> supportedParametersName) {
		this.supportedParametersName = supportedParametersName;
	}
}
