package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.SerializedName;

@Entity
@Table (name = "CASEGROUPS")
public class CaseGroup implements ICase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "casegroup_id")
	@SerializedName("id")
	private short id;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinTable(name = "CASEGROUPS_CASES", joinColumns=@JoinColumn(name = "casegroup_id"),
				inverseJoinColumns=@JoinColumn(name = "case_id"))
	@SerializedName("cases")
	private List<Case> cases;
	
	@Column(name = "logicOperator")
	@SerializedName("logicOperator")
	private char logicOperator;
	
	public CaseGroup()
	{
		this.cases = null;
		this.logicOperator = '|';
	}
	
	public CaseGroup(List<Case> cases,
					char logicOperator)
	{
		this.cases = cases;
		this.logicOperator = logicOperator;
	}
	
	public CaseGroup(CaseGroup cg) throws Exception
	{
		this.cases = cg.getCases();
		for(Case c : this.cases)
		{
			c = new Case(c);
		}
		this.logicOperator = cg.getLogicOperator();
	}

	@Override
	public boolean calculateCase() {
		boolean isTrue = true;
		boolean caseResult;
		Iterator<Case> itr = this.cases.iterator();
		while(itr.hasNext())
		{
			ICase currentCase = itr.next();
			caseResult = currentCase.calculateCase();
			if(currentCase.getLogicOperator() == '|') 
			{
				isTrue |= caseResult;
			}
			else			//logicOperator = '&'
			{
				isTrue &= caseResult;
			}
		}
		return isTrue;
	}

	@Override
	public char getLogicOperator() {
		return this.logicOperator;
	}
	
	public List<Case> getCases() {
		return cases;
	}

	public void setCases(List<Case> cases) {
		this.cases = cases;
	}
	
	public void setLogicOperator(char logicOperator)
	{
		this.logicOperator = logicOperator;
	}
	
	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}
}
