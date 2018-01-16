package org.IoT_Project.Scenario_Engine.Models;

import java.util.LinkedList;
import com.google.gson.annotations.SerializedName;

public class ActionEventProto {
	@SerializedName("id")
	private short id;
	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("prodId")
	private short prodId;
	@SerializedName("productEP")
	protected String productEP;
	@SerializedName("isEvent")
	private boolean isEvent;
	
	// list of eventActionProperties
	@SerializedName("supportedParametersName")
	private LinkedList<String> supportedParametersName;
	@SerializedName("descriptions")
	private LinkedList<String> descriptions;
	@SerializedName("types")
	private LinkedList<String> types;
	@SerializedName("supportedValues")
	private LinkedList<LinkedList<String>> supportedValues;
	@SerializedName("minValues")
	private LinkedList<String> min;
	@SerializedName("maxValues")
	private LinkedList<String> max;
	
	
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
						    LinkedList<String> types,
						    LinkedList<LinkedList<String>> supportedValues,
						    LinkedList<String> supportedParametersName,
						    LinkedList<String> min,
						    LinkedList<String> max,
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

	public LinkedList<String> getTypes() {
		return this.types;
	}
	
	public void setTypes(LinkedList<String> types) {
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

	public LinkedList<LinkedList<String>> getSupportedValues() {
		return supportedValues;
	}

	public void setSupportedValues(LinkedList<LinkedList<String>> supportedValues) {
		this.supportedValues = supportedValues;
	}
	
	public LinkedList<String> getMin() {
		return min;
	}

	public void setMin(LinkedList<String> min) {
		this.min = min;
	}

	public LinkedList<String> getMax() {
		return max;
	}

	public void setMax(LinkedList<String> max) {
		this.max = max;
	}

	public LinkedList<String> getSupportedParametersName() {
		return supportedParametersName;
	}

	public void setSupportedParametersName(LinkedList<String> supportedParametersName) {
		this.supportedParametersName = supportedParametersName;
	}
}
