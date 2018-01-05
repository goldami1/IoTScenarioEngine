import React, { Component } from 'react';
import { connect } from "react-redux";
import { addScenario } from "../../../actions/scenario_actions";
import { Link } from 'react-router-dom';
import { withRouter } from "react-router-dom";
import {flatten , unflatten} from '../../../utilities/flat';
import { fetchDevices } from '../../../actions/devices_actions';


class ScenarioForm extends Component {
	constructor(props){
		super(props);
		this.state = {

			"name":'',
			"event.device.id":'',
			"event.event.type":'',
			"event.event.name":'',
			"event.event.value":'',
			"action.device.id":'',
			"action.action.type":'',
			"action.action.name":'',
			"action.action.value":'',
		
			error:'',
			isLoading:false
		}
		this.onChange = this.onChange.bind(this);
		this.handleChange = this.handleChange.bind(this);
		this.onSubmit = this.onSubmit.bind(this);
	}

	onChange( event ){
		this.setState({
			[event.target.name] : event.target.value
		})
	}

	handleChange( event ){
		this.setState({
			[event.target.name] : event.target.value
		})
		// ,
		// this.setState({ isLoading: true });
		// this.props.fetchProducts(event.target.value)
		// 	.then((res) => this.setState({ isLoading: false }));
	}

	onSubmit (event ){
		event.preventDefault();
		this.setState({
			error: '',
			isLoading: true
		});
		this.props.addDevice(this.state)
			.then(
				(res) => {
					this.props.history.goBack();
					this.setState({
						isLoading: false
					});
				},
				(err) => {
					this.setState({
						error: err.response.data.error,
						isLoading: false
					});
				}
			);

	}

	componentDidMount() {
		this.setState({ isLoading: true });
		this.props.fetchDevices();

	}


	render() {


 	// 	// TODO :: make reusable funtion
		const eventDeviceItems = this.props.devices.map(device => {
			return (
				<option value={device.id}>
					{device.name}
				</option>
				);
			}
		);

		const actionDeviceItems = this.props.devices.map(device => {
			return (
				<option value={device.id}>
					{device.name}
				</option>
				);
			}
		);

		return (
			<form className=" col-lg-8 offset-lg-2 card card-body mt-5" 	onSubmit={this.onSubmit} >
				<div className="form-group">
					<label >Scenrio Description</label>
					<textarea
						onChange={this.onChange}
						value={this.state["name"]}
						type="text"
						className="form-control"
						name="name"
						placeholder="Describe your scenario"/>
				</div>
				<div className="row">
					<div className="col-6">
					<h5 className="m-3 text-muted">event</h5>
						<div
						className="  card card-body  ">

						<div className="form-group">
							<label >Device</label>
							<select
								className="custom-select w-100"
								onChange={this.handleChange}
								name="event.device.id"
								value={this.state["event.device.id"]}>
								{eventDeviceItems}
							</select>
						</div>

						<div className="form-group">
							<label >Event</label>
							<select
								onChange={this.onChange}
								className="custom-select w-100"
								value={this.state["event.event.id"]}
								name="event.event.id"
								>
							</select>
						</div>
						<div className="form-group">
							<label >Type</label>
							<select
								onChange={this.onChange}
								className="custom-select w-100"
								value={this.state["event.event.type"]}
								name="event.event.type"
								>
							</select>
						</div>
						<div className="form-group">
							<label>Value</label>
							<input
								onChange={this.onChange}
								value={this.state["event.event.value"]}
								type="text"
								className="form-control"
								name="event.event.value"
								placeholder="value"/>
							</div>
						</div>
					</div>
					<div className="col-6">
						<h5 className="m-3 text-muted">action</h5>
						<div className="  card card-body  ">

						<div className="form-group">
							<label >Device</label>
							<select
								className="custom-select w-100"
								onChange={this.handleChange}
								name="action.device.id"
								value={this.state["action.device.id"]}>
								{actionDeviceItems}

							</select>
						</div>

						<div className="form-group">
							<label >Action</label>
							<select
								onChange={this.onChange}
								className="custom-select w-100"
								value={this.state["action.action.id"]}
								name="action.action.id"
								>
							</select>
						</div>
						<div className="form-group">
							<label >Type</label>
							<select
								onChange={this.onChange}
								className="custom-select w-100"
								value={this.state["action.action.type"]}
								name="action.action.type"
								>
							</select>
						</div>
						<div className="form-group">
							<label>Value</label>
							<input
								onChange={this.onChange}
								value={this.state["action.action.value"]}
								type="text"
								className="form-control"
								name="action.action.value"
								placeholder="value"/>
							</div>
						</div>
					</div>
				</div>
				<div className="row ">
					<div className="col-12">
						<button
							disabled={this.state.isLoading}
							type="submit"
							className="  mt-3 btn btn-primary btn-lg btn-block ">
							Add Scenario
						</button>
						
						{
							this.state.error &&
							<div className=" m-3 text-center" >
		             	  	<div className=" m-3  text-danger">
		                        {this.state.error}
		                    </div>
		                    </div>
						}
					</div>
				</div>
			</form>
		);
	}
}

function mapStateToProps({devices}) {
	return {
		devices: devices.devices
	}
}


export default connect(mapStateToProps, { addScenario,fetchDevices })(withRouter(ScenarioForm));
