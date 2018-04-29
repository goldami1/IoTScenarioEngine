import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import ContentWrapper from "../../common/ContentWrapper";
import _ from 'lodash';
import {
	DatePicker,
	Select,
	Checkbox,
	TimePicker,
	Form,
	Cascader,
	Modal,
	Radio,
	Button,
	Icon,
	Row,
	Col,
	InputNumber,
	Input,
	Layout
} from "antd";

const RadioButton = Radio.Button;
const RadioGroup = Radio.Group;
const { Header, Footer, Sider, Content } = Layout;
const CheckboxGroup = Checkbox.Group;
const Option = Select.Option;
const FormItem = Form.Item;

const formItemLayout = {
	labelCol: {
		xs: { span: 24 },
		sm: { span: 5 }
	},
	wrapperCol: {
		xs: { span: 24 },
		sm: { span: 16 }
	}
};

function mapDevicesToDropdown (devices,type) {
	return _.map(devices, (device,index) =>{
		return {
			key:{index},
			label:`${device.name} ${device.serial}`,
			value:index,
			children :_.map(device[`${type}s`],(ae,index)=>{
				return {value:index,label:ae.name}
			})
		}	
	});
}

class CreateModal extends Component {
	constructor(props) {
		super(props);
		this.state = {
			aeProp:[],
			triggerTypeOptions: [
				{
					value: "device",
					label: "Device"
				},
				{
					value: "time",
					label: "Time",
					children: [
						{
							value: "daily",
							label: "Daily"
						},
						{
							value: "date",
							label: "Specific date"
						}
					]
				}
			],
			trigger: ["device"]
		};
	}

	componentWillReceiveProps(nextProps) {

		if(this.props !== nextProps) {
		  this.setState({
			options: mapDevicesToDropdown(nextProps.devices,nextProps.type),
			deviceProps:'',
			trigger: ["device"]
		  });
		}
	 }

	onChange = (event) => {
		// this.setState({ aeProp[2] : event.target.value });
		const props = this.state.aeProp;
		props[event.target.name] =  event.target.value;
		this.setState ({aeProp:props}); 
		console.log(this.state)
	};

	onChangeOther = state => data => {	
		this.setState({ [state]: data });
	};


	getInput = (prop,index) => {
		switch (prop.type) {
			case 'int':
				return <InputNumber  onChange={ (e) => this.onChange({target:{name:index,value:e}}) } value={ this.state.aeProp[index] } name={index} min={prop.min} max={prop.max} step={1} />
			case 'double':
				return   <InputNumber onChange={ (e) => this.onChange({target:{name:index,value:e}})} value={ this.state.aeProp[index] } name={index} min={prop.min} max={prop.max} step={0.1} />
			case 'string':
				return <Input onChange={this.onChange} value={ this.state.aeProp[index] } name={index}  placeholder="Basic usage" />
			case 'discrete':
				const options = _.map(prop.options,(option,index) => {
					return <RadioButton value={option}>{option}</RadioButton>
				})
				return <RadioGroup   onChange={this.onChange} value={ this.state.aeProp[index] } name={index} >{options}</RadioGroup>		
			default:
				return "error"; 
		}
	}
	aeForm = () =>{
		const [deviceId,aeId] = this.state.deviceProps;
		const type = `${this.props.type}s`;
		const device = this.props.devices[deviceId]
		const ae = device[type][aeId];
		const fields = _.map(ae.properties,(prop,index)=>{
			return <FormItem label={prop.name} extra={prop.description} {...formItemLayout}>
				{this.getInput(prop,index)}
			</FormItem>
		})
		
		return (
			<div>
				<FormItem label='Description' {...formItemLayout}  style={{marginBottom:"60px"}} >
					{ae.description}
				</FormItem>
				{fields}
			</div>
			 
		)
	}

	timeForm = () => {
		const days = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];

		const children = [];
		for (let i = 1; i < 8; i++) {
			children.push(<Option key={i}>{days[i - 1]}</Option>);
		}
		if (this.state.trigger[1] == "date")
			return (
				<FormItem label="Date and Time" {...formItemLayout}>
					<DatePicker
						value={this.state.date}
						onChange={this.onChangeOther('date')}
						showToday={false}
						showTime
						format="DD/MMM/YY HH:mm"
						placeholder="Select Date and Time"
					/>
				</FormItem>
			);
		else
			return (
				<Form>
					<FormItem label="Time" {...formItemLayout}>
						<TimePicker
							value={this.state.time}
							onChange={this.onChangeOther('time')}
							format="HH:mm"
						/>
					</FormItem>
					<FormItem label="Day" {...formItemLayout}>
						<Select
							mode="tags"
							value={this.state.day}
							onChange={this.onChangeOther('day')}
							defaultValue={["1", "2", "3", "4", "5", "6", "7"]}
						>
							{children}
						</Select>
					</FormItem>
				</Form>
			);
	};
	render() {

		const deviceForm = (
			
				!_.isEmpty(this.state.options) &&

				<div>
					<FormItem label={this.props.type} {...formItemLayout}>
					<Cascader 
						options={this.state.options} 
						value={this.state.deviceProps} 
						onChange={this.onChangeOther('deviceProps')}/>
					</FormItem>
					{this.state.deviceProps && this.aeForm()}
				</div>
			

		);
		return (
			<Modal
				destroyOnClose ={true}
				visible={this.props.visible}
				title={`Create a new ${this.props.type}`}
				okText="Create"
				onCancel={this.props.onCancel}
				onOk={this.props.onOk(
					{
						device	:this.state.deviceProps,
						props	:this.state.aeProp,
						type	:this.props.type == "event" ? 0 : 1
					})}
			>
				{this.props.type == "event" ? (
					<div>
						<FormItem label="Trigger type" {...formItemLayout}>
							<Cascader
								size="large"
								value={this.state.trigger}
								options={this.state.triggerTypeOptions}
								onChange={this.onChangeOther('trigger')}
							/>
						</FormItem>
						{this.state.trigger[0] == "time"
							? this.timeForm()
							: deviceForm}
					</div>
				) : deviceForm }
			</Modal>
		);
	}
}

export default withRouter(CreateModal);
