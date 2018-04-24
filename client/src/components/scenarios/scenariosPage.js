import React, { Component } from "react";
import { connect } from "react-redux";
import { fetchScenarios } from "../../actions/scenarioActions";
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
			selectedProduct:{}
		};
	}

    componentDidMount() {
        this.props.fetchScenarios();
    }

	onAddProductClick = () => {
		this.setState({
			modalVisble: true,
			selectedProduct:{}
		});
		console.log(this.state);
	}

	onModalCancel = () => {
		this.setState({
			modalVisble: false,
			selectedProduct:{}
		})
	}
	
	onProductClick = (product) => {
		this.setState({
			modalVisble: true,
			selectedProduct:product
		});
		console.log(this.state);		
	}

	render() {
		return (
			<ContentWrapper size="small">
				<Button 
					onClick={this.onAddProductClick}
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
					product={this.state.selectedProduct}
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

export default connect(mapStateToProps, { fetchScenarios })(withRouter(ScenariosPage));

