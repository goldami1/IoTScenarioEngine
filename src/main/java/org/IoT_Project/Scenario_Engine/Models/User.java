package org.IoT_Project.Scenario_Engine.Models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.gson.annotations.SerializedName;
import DataBase.DBHandler;
import DataBase.NDBHandler;

@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_email"}),
											@UniqueConstraint(columnNames = {"user_name"})})
@Inheritance(strategy = InheritanceType.JOINED)

public class User
{
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SerializedName("id")
	protected short id;
	@Column(name = "name")
	@SerializedName("name")
	protected String name;
	@Column(name = "user_picURL")
	@SerializedName("userPicURL")
	protected String userPicURL;
	@Column(name = "user_email")
	@SerializedName("email")
	protected String email;
	@Column(name = "user_name")
	@SerializedName("userName")
	protected String userName;
	@Column(name = "user_password")
	@SerializedName("password")
	protected String password;
	@SerializedName("isCustomer")
	protected boolean isCustomer;		//true - Customer, False - vendor
	
	//---------------------
	/*
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
		User that = (User) o;
		return	Objects.equals(this.id, that.id) ||
				Objects.equals(this.email, that.email) ||
				Objects.equals(this.userName, that.userName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.email, this.userName);
	}
	*/
	//---------------------
	
	public User(short id,
			String userName,
			String password,
			String i_name,
			String i_email,
			String i_userPicURL,
			boolean isCustomer) 
	{
		this.userName = userName;
		this.password = password;
		this.name = i_name;
		this.email = i_email;
		this.userPicURL = i_userPicURL;
		this.id = id;
		this.isCustomer = isCustomer;
	}
	
	//Constructors
	public User()
	{
		//this.id = -1;
		this.name = this.userPicURL = this.email = this.userName = this.password = null;
		this.isCustomer = false;
	}
	
	public User(User i_user) throws Exception
	{
		NDBHandler db = NDBHandler.getInstance();
		boolean isUpdated = i_user.getId() > 0;
		/*
		if(!isUpdated)
		{
			if(db.isUsernameAvailable(i_user.getUserName()))
			{
				if(i_user.isCustomer)
				{
					this.id = db.getCustomersMaxAvailableIdx();
				}
				else
				{
					this.id = db.getVendorsMaxAvailableIdx();
				}
			}
		}
		else
		{
			this.id = i_user.getId();
		}*/
		//this.id = i_user.getId(); //remove
		this.userName = i_user.getUserName();
		this.password = i_user.getPassword();
		this.name = i_user.getName();
		this.email = i_user.getEmail();
		this.userPicURL = i_user.getUserPicURL();
		this.isCustomer = i_user.isCustomer;
	}
	
	
	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserPicURL() {
		return userPicURL;
	}

	public void setUserPicURL(String userPicURL) {
		this.userPicURL = userPicURL;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCustomer() {
		return isCustomer;
	}

	public void setCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public void setIsCustomer(boolean i_isCustomer) {
		this.isCustomer = i_isCustomer;	
	}

}
