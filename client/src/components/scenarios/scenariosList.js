import React from "react";
import ScenariosListItem from "./scenariosListItem";
import { List } from 'antd';

const ScenariosList = props => {
	const scenrioItems = props.scenarios.map(scenario => {
		return (
			<div 
				key={scenario.id}
				style={{
					width:"200px", 
					float:"left",
					marginLeft:"10px",
					marginTop:"10px"
			}}>
				<ScenariosListItem
					onScenarioClick={props.onScenarioClick}
					key={scenario.id}
					scenario={scenario}
				/>
			</div>
		);
	});

	return  (
		<List style = {{overflow:"hidden"}} >
			{scenrioItems}
		</List>);
};

export default ScenariosList;
