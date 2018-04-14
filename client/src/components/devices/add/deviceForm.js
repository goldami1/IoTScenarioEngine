import React, { Component } from "react";
import { connect } from "react-redux";
import {
	addDevice,
	fetchVendors,
	fetchProducts
} from "../../../actions/deviceActions";
import { Link } from "react-router-dom";
import { withRouter } from "react-router-dom";

import {Modal,Cascader} from "antd"




function updateVendorsToOptions(vendors)
{
	var options = [];
	vendors.forEach((vendor,index)=>{
		options.push({
			value:index,
			label:vendor.name,
		})
	});
	return options;
}

class DeviceForm extends Component {
	constructor(props) {
		super(props);
		this.state = {
			options:[],
			serial: "",
		};
	}

	componentDidMount() {
		this.props.fetchVendors();
	}

	componentWillReceiveProps(nextProps) {
		this.setState({options:updateVendorsToOptions(nextProps.vendors)});
	}
	
	render() {
		
		return (
			
			<Modal
				visible={this.props.visible}
				onCancel={this.props.onCancel}
				okText="Add new device"
			>
				<Cascader
					options={this.state.options}
					loadData={this.loadData}
					onChange={this.onChange}
					value={this.state.options}
					changeOnSelect
				/>

			</Modal>
		);
	}
}

function mapStateToProps({ devices, auth }) {
	return {
		customer: auth.id,
		vendors: devices.vendors,
		products: devices.products
	};
}

export default connect(mapStateToProps, {
	addDevice,
	fetchVendors,
	fetchProducts
})(withRouter(DeviceForm));
