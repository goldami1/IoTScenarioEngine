package org.IoT_Project.Scenario_Engine.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CaseGroup implements ICase {
	@SerializedName("cases")
	private List<ICase> cases;
	@SerializedName("logicOperator")
	private char logicOperator;
	
	public CaseGroup()
	{
		this.cases = new ArrayList<ICase>();
		this.logicOperator = '&';
	}
	
	public void addCase(ICase caseToAdd)
	{
		this.cases.add(caseToAdd);
	}
	
	public void setLogicOperator(char logicOperator)
	{
		this.logicOperator = logicOperator;
	}

	@Override
	public boolean calculateCase() {
		boolean isTrue = true;
		boolean caseResult;
		Iterator<ICase> itr = this.cases.iterator();
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
	
}
