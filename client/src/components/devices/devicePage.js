import React, { Component } from "react";
import { connect } from "react-redux";
import DeviceList from "./deviceList";
import DeviceDetail from "./deviceDetail";
import { fetchDevices, deleteDevice } from "../../actions/deviceActions";
import DeviceForm from "./add/deviceForm";
import { Link } from "react-router-dom";
import ContentWrapper from "../common/ContentWrapper";
import { Button ,Modal ,Spin,Row} from 'antd';

class DevicesPage extends Component {
	constructor(props) {
		super(props);
		this.state = {
			showForm: false,
			selectedDevice: {},
			modalVisible: false,
			isLoading: false
		};
	}

	componentDidMount() {
		this.props.fetchDevices();
	}

	


	onDeviceSelect = (selectedDevice) =>
	{
		this.setState({ selectedDevice }); 
		console.log(selectedDevice);  
	}

	onModalOpen = () => {
		this.setState({ modalVisible: true });
	};

	onModalCancel = () => {
		this.setState({ modalVisible: false });
	};

	render() {
		return (
			<ContentWrapper size="small">

						<Button 
							onClick={this.onModalOpen}
							style={{marginBottom:"20px"}} 
						>
							Add device 
						</Button>


						{ 
							this.state.isLoading ?  
							<Spin /> : 
							<div>
								<DeviceList 
									onDeviceSelect={this.onDeviceSelect}
									devices={this.props.devices}>
								</DeviceList>

								<DeviceForm 
									visible={this.state.modalVisible}
									onCancel={this.onModalCancel}
								/>
							</div>
						
						}


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


