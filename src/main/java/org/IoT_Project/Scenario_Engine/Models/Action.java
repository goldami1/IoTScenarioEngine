package org.IoT_Project.Scenario_Engine.Models;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import javax.net.ssl.HttpsURLConnection;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import DataBase.DBHandler;
import DataBase.NDBHandler;

@Entity
@Table(name = "ACTIONS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Action implements IAction{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "action_id")
	@SerializedName("id")
	protected short id;
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "AE_PARAM_VALS", joinColumns=@JoinColumn(name = "ae_id"))
	@GenericGenerator(name = "hilo-gen", strategy = "sequence")
	@CollectionId(columns = { @Column(name = "param_val_idx") }, generator = "hilo-gen", type = @org.hibernate.annotations.Type(type = "long"))
	@SerializedName("parameters")
	protected List<String> parameters;
	@Column(name = "device_sn")
	@SerializedName("device_serialNum")
	protected short device_serialNum;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "actionproto_id")
	@SerializedName("actionDescription")
	protected ActionEventProto actionDescription;
	
	
	public Action()
	{
		this.parameters = null;
		this.actionDescription = null;
		this.id = this.device_serialNum = -1;
	}
	
	public Action(short Action_id,
				  short Action_deviceSerialNum,
				  List<String> Action_parameters,
				  ActionEventProto Action_descriptor)
	{
		this.id = Action_id;
		this.device_serialNum = Action_deviceSerialNum;
		this.parameters = Action_parameters;
		this.actionDescription = Action_descriptor;
	}
	
	/************   ONLY FOR ACTION NEW CREATION IN DB   *************/
	public Action(Action i_action) throws Exception
	{
		/*
		 * this constructor usement is only for registering new Action object to DB.
		 */
		DBHandler db = DBHandler.getInstance();
		this.device_serialNum = i_action.getDevice_serialNum();
		this.parameters = i_action.getParameters();
		this.actionDescription = i_action.getActionDescription();
	}
	/****************************************************************/
	public int toggleAction() throws Exception
	{
		StringBuilder URI = new StringBuilder();
		/*
		 * creating the URI:
		 * (server-url)/product_endpoint/device_serialNum/{if this is an event => event_id}/name of Action_Event/parameter.
		 */
		
		URI.append(this.actionDescription.getProductEP());
		URI.append("/");
		URI.append(this.device_serialNum);
		URI.append("/");
		URI.append(figureSpacesInName(this.actionDescription.getName()));
		URI.append("/");
		boolean firstEntry = true;
		if(this.actionDescription.getIsEvent())
		{
			URI.append("?event_id=" + this.id);
			firstEntry = false;
		}
		
		/*
		 *  Generate the string: ?[param1_name]=[value1]&[param2_name]=[value2]...&[paramN_name]=[valueN]/
		 */
		int paramIndex = 0;
		for(String s : this.parameters)
		{
			/*
			 *  Attaching parameter to URL.
			 */
			if(firstEntry)
			{
				URI.append("?" + figureSpacesInName(this.actionDescription.getSupportedParametersName().get(paramIndex++)) + "=" + s);
				firstEntry = false;
			}
			else
				URI.append("&" + figureSpacesInName(this.actionDescription.getSupportedParametersName().get(paramIndex++)) + "=" + s);		
		}
		
		String USER_AGENT = "Mozilla/5.0";
		
		
		URL ep = new URL(URI.toString());
		//URL ep = new URL(null, URI.toString(), new sun.net.www.protocol.https.Handler());
		HttpURLConnection con = (HttpURLConnection)ep.openConnection();
		con.setRequestMethod("GET");
		//con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestMethod("POST");
		//con.setDoOutput(true);
		
		/*
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.flush();
		wr.close();
		*/
		int status = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		content.append("status response - " + status + System.lineSeparator());
		while ((inputLine = in.readLine()) != null)
		{
			content.append(inputLine);
		}
		in.close();
		return status;
	}
	/////////////////////////////////////////////////////////////////////////////////////////
	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public List<String> getParameters() {
		return this.parameters;
	}

	public void setParameters(List<String> parameter) {
		this.parameters = parameter;
	}

	public short getDevice_serialNum() {
		return device_serialNum;
	}

	public void setDevice_serialNum(short device_serialNum) {
		this.device_serialNum = device_serialNum;
	}

	public ActionEventProto getActionDescription() {
		return actionDescription;
	}

	public void setActionDescription(ActionEventProto actionDescription) {
		this.actionDescription = actionDescription;
	}
	
	private String figureSpacesInName(String actionName_arg)
	{

		/*
		 *  Handeling spaces in Action name for the URL.
		 */
		String actionNameSplitted[] = actionName_arg.split(" ");
		StringBuilder actionName = new StringBuilder();
		for(int i=0; i<actionNameSplitted.length; i++)
		{
			if(i == 0)
			{
				actionName.append(actionNameSplitted[i]);
			}
			else
			{
				actionName.append("%20");
				actionName.append(actionNameSplitted[i]);
			}
		}
		return actionName.toString();
	}
}

/*
 * NOTE:
 * This code is in case that parameter is an object.
public String getParameterToString() {
	String result = null;
	switch(this.actionDescription.getType())
	{
	case "int":
		result = Integer.toString((int)this.parameter);
		break;
	case "double":
		result = Double.toString((double)this.parameter);
		break;
	case "Range":
		Range rng = (Range)this.parameter;
		result = Double.toString(rng.min);
		result += "-";
		result += Double.toString(rng.max);
		break;
	case "bool":
		result = Boolean.toString((boolean)this.parameter);
		break;
	}
	return result;
}
*/
