import React, { Component } from "react";
import { connect } from "react-redux";
import {
	addDevice,
	fetchVendors,
	fetchProducts
} from "../../../actions/deviceActions";
import { Link } from "react-router-dom";
import { withRouter } from "react-router-dom";

import {Form, Modal,Cascader, Input} from "antd"

const FormItem = Form.Item;


function updateProductsToOptions(products)
{
	var newProducts = [];
	products = [
		{name:"hello"},
		{name:"fuck"},
		{name:"me"}
	];

	products.forEach((vendor,index)=>{
		newProducts.push({
			value:index,
			label:vendor.name,
		})
	});
	return newProducts;
}

function updateVendorsToOptions(vendors)
{
	var options = [];
	vendors = [
		{name:"hello"},
		{name:"fuck"},
		{name:"me"}
	];

	vendors.forEach((vendor,index)=>{
		options.push({
			value:index,
			label:vendor.name,
			isLeaf: false,
		})
	});
	return options;
}

class DeviceForm extends Component {
	constructor(props) {
		super(props);
		this.state = {
			options:[],
			selectedOptions:[],
			serial: "",
			
		};
	}

	componentDidMount() {
		this.props.fetchVendors();
	}

	componentWillReceiveProps(nextProps) {
		this.setState({options:updateVendorsToOptions(nextProps.vendors)});
	}
	
	onSubmit = () =>
	{
		this.props.addDevice({
			customer_id: this.props.customer,
			serial_number: this.state.serial,
			protoDevice:this.props.products[this.state.selectedOptions[1]]
		})
	}
	
	onChange = (value) => {
		this.setState({selectedOptions: value});
	} 

	onInputChange = (e) =>
	{
		this.setState({serial:e.target.value});
	}
	
	loadData = (selectedOptions) => {
		const targetOption = selectedOptions[selectedOptions.length - 1];
		targetOption.loading = true;
	
		// load options lazily

		  targetOption.loading = false;
		  this.props.fetchProducts(targetOption.value)
		  			.then(targetOption.children = updateProductsToOptions(this.props.products));
		  this.setState({
			options: [...this.state.options],
		  });

	  }

	render() {
		const formItemLayout = {
			labelCol: {
			  xs: { span: 24 },
			  sm: { span: 8 },
			},
			wrapperCol: {
			  xs: { span: 24 },
			  sm: { span: 12 },
			},
		  };

		return (
			
			<Modal
				visible={this.props.visible}
				onCancel={this.props.onCancel}
				onOk={this.onSubmit}
				okText="Add new device"
			>
				<Form>
					<FormItem
						{...formItemLayout}
						label="Device"
			  		>
						<Cascader
							options={this.state.options}
							loadData={this.loadData}
							onChange={this.onChange}
							value={this.state.selectedOptions}
							changeOnSelect
						/>
					</FormItem>
					<FormItem
						{...formItemLayout}
						label="Serial number"
						>
							<Input 
								value = {this.state.serial}
								onChange={this.onInputChange}
								value = {this.state.serial}
								placeholder="Enter serial number"/>
					</FormItem>
				</Form>

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
