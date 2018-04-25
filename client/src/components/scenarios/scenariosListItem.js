import React from "react";
import { List,Card } from 'antd';

const { Meta } = Card;

const ScenariosListItem = props => {
	return (
		<List.Item  >
			<Card
				style ={{width:"100%"}}
				onClick ={ () => { props.onScenarioClick(props.scenario) } }
				hoverable
			>
				<div>
					<strong>Scenario :</strong> {`${props.scenario.name}`}
				</div>
				<div>
					<strong>Description :</strong> {`${props.scenario.description}`}
				</div>
			</Card>
		</List.Item>
	);
};

export default ScenariosListItem;
