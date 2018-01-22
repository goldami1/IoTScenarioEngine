import React from "react";
import ScenarioListItem from "./scenarios_list_item";

const ScenarioList = props => {
	const scenarioItems = props.scenarios.map(scenario => {
		return (
			<ScenarioListItem
				onScenarioDelete={props.onScenarioDelete}
				key={scenario.id}
				scenario={scenario}
			/>
		);
	});

	return <ul className="list-group">{scenarioItems}</ul>;
};

export default ScenarioList;
