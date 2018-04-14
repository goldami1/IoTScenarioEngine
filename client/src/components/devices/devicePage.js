import React, { Component } from "react";
import { connect } from "react-redux";
import DeviceList from "./deviceList";
import DeviceDetail from "./deviceDetail";
import { fetchDevices, deleteDevice } from "../../actions/deviceActions";
import DeviceForm from "./add/addDevice";
import { Link } from "react-router-dom";
import ContentWrapper from "../common/ContentWrapper";
import { Button } from 'antd';

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
	}

	onDeviceSelect = (selectedDevice) =>
	{
		this.setState({ selectedDevice }); 
		console.log(selectedDevice);  
	}

	render() {
		return (
			<ContentWrapper size="small">
			
					<Button> Add device </Button>
					<DeviceList 
						onDeviceSelect={this.onDeviceSelect}
						devices={this.props.devices}>
					</DeviceList>
			</ContentWrapper>
		);
	}
}

function mapStateToProps({ devices }) {
	return {
		devices: devices.devices
	};
}


export default connect(mapStateToProps, { fetchDevices })(DevicesPage);


