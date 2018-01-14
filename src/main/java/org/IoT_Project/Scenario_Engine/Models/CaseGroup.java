package org.IoT_Project.Scenario_Engine.Models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import DataBase.DBHandler;

public class CaseGroup implements ICase {
	@SerializedName("cases")
	private List<Case> cases;
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
	
}
