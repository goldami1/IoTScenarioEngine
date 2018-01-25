import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import ContentWrapper from "../../common/ContentWrapper";
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
	Layout
} from "antd";

const { Header, Footer, Sider, Content } = Layout;
const CheckboxGroup = Checkbox.Group;
const RadioButton = Radio.Button;
const RadioGroup = Radio.Group;
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

class CreateModal extends Component {
	constructor(props) {
		super(props);
		this.state = {
			options: [
				{
					value: "clock",
					label: "clock",
					children: [
						{
							value: "alram clock",
							label: "alram clock"
						},
						{
							value: "play music",
							label: "play music"
						},
						{
							value: "say time",
							label: "say time"
						}
					]
				},
				{
					value: "lamp",
					label: "lamp",
					children: [
						{
							value: "turn light",
							label: "turn light"
						},
						{
							value: "change color to black",
							label: "change color to black"
						},
						{
							value: "change color to red",
							label: "change color to red"
						},
						{
							value: "change color to pink",
							label: "change color to pink"
						}
					]
				}
			],
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

	onChange = event => {
		this.setState({ [event.target.name]: event.target.value });
	};

	onChangeOther = state => data => {
		console.log(state);
		this.setState({ [state]: data });
	};


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
			<FormItem label={this.props.type} {...formItemLayout}>
				<Cascader 
					options={this.state.options} 
					value={this.state.deviceProps} 
					onChange={this.onChangeOther('deviceProps')}/>
			</FormItem>
		);
		return (
			<Modal
				visible={this.props.visible}
				title={`Create a new ${this.props.type}`}
				okText="Create"
				onCancel={this.props.onCancel}
				onOk={this.props.onOk(this.props.type== "event" ? 0 : 1,
					{
						day:this.state.day,
						date:this.state.date,
						time:this.state.time,
						deviceProps:this.state.deviceProps
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
