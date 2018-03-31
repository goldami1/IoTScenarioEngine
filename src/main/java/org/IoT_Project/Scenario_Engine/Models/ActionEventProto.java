package org.IoT_Project.Scenario_Engine.Models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.SerializedName;

@Entity
@Table (name = "AEPROTOS")
public class ActionEventProto {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ae_id")
	@SerializedName("id")
	private short id;
	@Column(name = "ae_name")
	@SerializedName("name")
	private String name;
	@Column(name = "ae_description")
	@SerializedName("description")
	private String description;
	@Column(name = "product_id")
	@SerializedName("prodId")
	private short prodId;
	@Column(name = "ae_prod_ep")
	@SerializedName("productEP")
	protected String productEP;
	@Column(name = "ae_is_event")
	@SerializedName("isEvent")
	private boolean isEvent;
	
	// list of eventActionProperties
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "AEPARAMS_NAME", joinColumns = @JoinColumn(name = "ae_id"))
	@GenericGenerator(name = "hilo-gen", strategy = "sequence")
	@CollectionId(columns = { @Column(name = "param_idx") }, generator = "hilo-gen", type = @org.hibernate.annotations.Type(type = "long"))
	@SerializedName("supportedParametersName")
	private List<String> supportedParametersName;
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "AEPARAMS_DESC", joinColumns = @JoinColumn(name = "ae_id"))
	@GenericGenerator(name = "hilo-gen", strategy = "sequence")
	@CollectionId(columns = { @Column(name = "param_idx") }, generator = "hilo-gen", type = @org.hibernate.annotations.Type(type = "long"))
	@SerializedName("paramDescription")
	private List<String> paramDesc;
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "AEPARAMS_TYPE", joinColumns = @JoinColumn(name = "ae_id"))
	@GenericGenerator(name = "hilo-gen", strategy = "sequence")
	@CollectionId(columns = { @Column(name = "type_idx") }, generator = "hilo-gen", type = @org.hibernate.annotations.Type(type = "long"))
	@SerializedName("types")
	private List<String> types;
													//I M P L E M E N T        I T   IN HIBERNATE!!!!!!
	@Transient
	@SerializedName("supportedValues")/* Such like Low, Medium, High */
	private List<List<String>> supportedValues;
													//I M P L E M E N T        I T   IN HIBERNATE!!!!!!
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "AEPARAMS_MIN", joinColumns = @JoinColumn(name = "ae_id"))
	@GenericGenerator(name = "hilo-gen", strategy = "sequence")
	@CollectionId(columns = { @Column(name = "min_idx") }, generator = "hilo-gen", type = @org.hibernate.annotations.Type(type = "long"))
	@SerializedName("minValues")
	private List<String> min;
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "AEPARAMS_MAX", joinColumns = @JoinColumn(name = "ae_id"))
	@GenericGenerator(name = "hilo-gen", strategy = "sequence")
	@CollectionId(columns = { @Column(name = "max_idx") }, generator = "hilo-gen", type = @org.hibernate.annotations.Type(type = "long"))
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
		this.paramDesc = null;
	}
	
	public ActionEventProto(short id,
						    String name,
						    String description,
						    List<String> types,
						    List<List<String>> supportedValues,
						    List<String> supportedParametersName,
						    List<String> i_paramDesc,
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
		this.paramDesc = i_paramDesc;
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

	public void setSupportedParametersName(List<String> supportedParametersName) {
		this.supportedParametersName = supportedParametersName;
	}

	public List<String> getSupportedParametersName() {
		return supportedParametersName;
	}

	public void setSupportedParametersName(LinkedList<String> supportedParametersName) {
		this.supportedParametersName = supportedParametersName;
	}
	
	public List<String> getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(List<String> paramDesc) {
		this.paramDesc = paramDesc;
	}
}
