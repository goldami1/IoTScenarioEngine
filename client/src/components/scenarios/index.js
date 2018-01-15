import React, { Component } from "react";
import { connect } from "react-redux";
import ScenarioList from "./scenarios_list";
import { fetchScenarios, deleteScenario } from "../../actions/scenario_actions";
import { Link } from "react-router-dom";

// import ScenarioDetail from './scenarios_detail';

// import ScenarioForm from './scenario_form';

class ScenariosPage extends Component {
	constructor(props) {
		super(props);
		this.state = {
			showForm: false,
			isLoading: false
		};
	}

	componentDidMount() {
		this.setState({ isLoading: true });
		this.props.fetchScenarios();
		//TODO add login to prevent fetching if data is available
		// if (!this.props.devices.length) {
		// 	this.props.fetchDevices()
		// 		.then((res) => this.setState({ isLoading: false }));
		// 	console.log(this.props.devices);
		// }
	}

	render() {
		return (
			<div className="m-5">
				<Link to="/scenarios/add">
					<button
						type="button"
						className="mb-5 ml-2  btn btn-primary "
					>
						Add scenario
					</button>
				</Link>

				{/*<div className="col-md-4">*/}
				<ScenarioList
					scenarios={this.props.scenarios}
					onScenarioSelect={selectedScenario =>
						this.setState({ selectedScenario })
					}
					onScenarioDelete={selectedScenario =>
						this.props.deleteScenario({ selectedScenario })
					}
				/>
				{/*</div>*/}
				{/*				<div className="col-md-8 ">
					<DeviceDetail device={this.state.selectedDevice} />
				</div>*/}
			</div>
		);
	}
}

function mapStateToProps({ scenarios }) {
	return {
		scenarios: scenarios.scenarios
	};
}

export default connect(mapStateToProps, { fetchScenarios, deleteScenario })(
	ScenariosPage
);
