import React, { Component } from "react";
import { connect } from "react-redux";
import { fetchScenarios ,} from "../../actions/scenarioActions";
import { fetchDevices ,} from "../../actions/deviceActions";
import { Link } from "react-router-dom";
import { withRouter } from "react-router-dom";
import ContentWrapper from "../common/ContentWrapper";
import ScenariosList from "./scenariosList";
import ScenarioForm from "./add/scenarioForm";
import { Button ,Modal ,Spin,Row} from 'antd';

class ScenariosPage extends Component {
	constructor(props) {
		super(props);
		this.state = {
			modalVisble:false,
			selectedScenario:{}
		};
	}

    componentDidMount() {
		this.props.fetchScenarios();
		this.props.fetchDevices();
    }

	onAddScenarioClick = () => {
		this.setState({
			modalVisble: true,
			selectedScenario:{}
		});
		console.log(this.state);
	}

	onModalCancel = () => {
		this.setState({
			modalVisble: false,
			selectedScenario:{}
		})
	}
	
	onScenarioClick = (scenario) => {
		this.setState({
			modalVisble: true,
			selectedProduct:scenario
		});
		console.log(this.state);		
	}

	render() {
		return (
			<ContentWrapper size="small">
				<Button 
					onClick={this.onAddScenarioClick}
					style={{marginBottom:"20px"}} 
				>
					Add scenario 
				</Button>
				<ScenariosList 
					onScenarioClick={this.onScenarioClick} 
					scenarios={this.props.scenarios}>
				</ScenariosList>
				
				<ScenarioForm
					visible={this.state.modalVisble}
					product={this.state.selectedScenario}
					onCancel={this.onModalCancel}
				/>
			</ContentWrapper> 

		);
	}
}

function mapStateToProps({ scenarios }) {
	return {
		scenarios:scenarios.scenarios
	};
}

export default connect(mapStateToProps, { fetchScenarios,fetchDevices })(withRouter(ScenariosPage));

