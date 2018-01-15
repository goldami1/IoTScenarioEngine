import React, { Component } from "react";
import { connect } from "react-redux";
import DeviceList from "./device_list";
import DeviceDetail from "./device_detail";
import { fetchDevices, deleteDevice } from "../../actions/devices_actions";
import DeviceForm from "./add/";
import { Link } from "react-router-dom";

class DevicesPage extends Component {
	constructor(props) {
		super(props);
		this.state = {
			showForm: false,
			selectedDevice: {},
			isLoading: false
		};
	}

	componentDidMount() {
		this.setState({ isLoading: true });
		this.props.fetchDevices();
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
				<Link to="/devices/add">
					<button
						type="button"
						className="mb-5 ml-2  btn btn-primary "
					>
						Add device
					</button>
				</Link>

				{/*<div className="col-md-4">*/}
				<DeviceList
					devices={this.props.devices}
					onDeviceSelect={selectedDevice =>
						this.setState({ selectedDevice })
					}
					onDeviceDelete={selectedDevice =>
						this.props.deleteDevice({ selectedDevice })
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

function mapStateToProps({ devices }) {
	return {
		devices: devices.devices
	};
}

export default connect(mapStateToProps, { fetchDevices, deleteDevice })(
	DevicesPage
);
