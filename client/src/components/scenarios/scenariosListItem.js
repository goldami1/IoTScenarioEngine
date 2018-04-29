import React from "react";
import { Col,List,Card,Switch } from 'antd';

const { Meta } = Card;

const ScenariosListItem = props => {
	return (
		<List.Item  >
			<Card
				style ={{width:"100%"}}
				onClick ={ () => { props.onScenarioClick(props.scenario) } }
				hoverable
			>
			<Col span={2}> 
			<Switch size="small" checked={true}  />
			</Col>
			<Col span={13}>
				<div>
					<strong>Scenario :</strong> {`${props.scenario.name}`}
				</div>
				<div>
					<strong>Description :</strong> {`${props.scenario.description}`}
				</div>
			</Col>

			</Card>
		</List.Item>
	);
};

export default ScenariosListItem;
