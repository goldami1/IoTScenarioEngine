import React, { Component } from "react";
import {
	List,
	Tabs,
	Upload,
	Icon,
	message,
	Button,
	Modal,
	Form,
	Input,
	Radio,
	Row,
	Col,
	Tag,
	Tooltip,
	Collapse
} from "antd";
import { connect } from "react-redux";
import ContentWrapper from "../common/ContentWrapper";
import { addProduct } from "../../actions/productActions";
import ActionEvent from "./ActionEvent";
const { Panel } = Collapse.Panel;
const TabPane = Tabs.TabPane;

const FormItem = Form.Item;
const { TextArea } = Input;

function getBase64(img, callback) {
	const reader = new FileReader();
	reader.addEventListener("load", () => callback(reader.result));
	reader.readAsDataURL(img);
}

class ProductsPage extends Component {
	constructor(props) {
		super(props);
		this.state = {
			name: "",
			description: "",
			endpoint: "",
			image: "",
			actions: [],
			events: [],

			inputValue: "",
			inputVisible: false,
			loading: false
		};
	}

	handleChange = info => {
		if (info.file.status === "uploading") {
			this.setState({ loading: true });
			return;
		}
		if (info.file.status === "done") {
			// Get this url from response in real world.
			getBase64(info.file.originFileObj, image =>
				this.setState({
					image,
					loading: false
				})
			);
		}
	};

	saveInputRef = fuck => input => {
		console.log("asdasdasdasdasdasd");
	};

	showInput = () => {
		this.setState({ inputVisible: true });
	};

	clearInput = () => {
		this.setState({ inputValue: "" });
	};
	hideInput = () => {
		this.setState({ inputVisible: false, inputValue: "" });
	};

	onChange = event => {
		this.setState({
			[event.target.name]: event.target.value
		});
	};

	handleRemoveAE = (aeIndex, ae) => () => {
		this.setState({
			[ae]: this.state[ae].filter((s, saeIndex) => aeIndex !== saeIndex)
		});
	};

	handleRemoveProperty = (aeIndex, aePropId, ae) => event => {
		this.setState({
			[ae]: this.state[ae].map((singleAe, index) => {
				var prop;
				if (index === aeIndex) {
					prop = singleAe.properties.filter(
						(property, pindex) => pindex !== aePropId
					);
					return {
						...singleAe,
						properties: prop
					};
				}
				return singleAe;
			})
		});
	};
	handleChangeProperty = (aeIndex, aePropId, ae, isOption) => event => {
		const value = this.state.inputValue;
		this.setState({
			[ae]: this.state[ae].map((singleAe, index) => {
				var prop;
				if (index === aeIndex) {
					prop = singleAe.properties.map((property, pindex) => {
						if (pindex === aePropId) {
							if (isOption) {
								return {
									...property,
									options: [...property.options, value]
								};
							}
							return {
								...property,
								[event.target.name]: event.target.value
							};
						}
						return property;
					});
					return {
						...singleAe,
						properties: prop
					};
				}
				return singleAe;
			}),
			inputValue: ""
		});
	};

	handleChangeAE = (aeIndex, ae) => event => {
		const newAE = this.state[ae].map((singleAe, saeIndex) => {
			if (aeIndex !== saeIndex) return singleAe;
			return { ...singleAe, [event.target.name]: event.target.value };
		});

		this.setState({ [ae]: newAE });
	};

	handleAddProperty = (aeIndex, ae) => () => {
		this.setState({
			[ae]: this.state[ae].map((singleAe, index) => {
				if (index === aeIndex) {
					return {
						...singleAe,
						properties: [
							...singleAe.properties,
							{
								name: "",
								description: "",
								type: "",
								options: []
							}
						]
					};
				}
				return singleAe;
			})
		});
	};

	handleAddAE = ae => () => {
		this.setState({
			[ae]: this.state[ae].concat([
				{
					name: "",
					description: "",
					endpoint: "",
					properties: [
						{
							name: "",
							description: "",
							type: "",
							options: []
						}
					]
				}
			])
		});
	};

	handleClose = (aeIndex, aePropId, optionId, ae) => event => {
		this.setState({
			[ae]: this.state[ae].map((singleAe, index) => {
				var prop, opts;
				if (index === aeIndex) {
					prop = singleAe.properties.map((property, pindex) => {
						if (pindex === aePropId) {
							opts = property.options.filter(
								(option, oindex) => oindex !== optionId
							);

							return {
								...property,
								options: opts
							};
						}
						return property;
					});
					return {
						...singleAe,
						properties: prop
					};
				}
				return singleAe;
			})
		});
	};

	onSubmit = event => {
		event.preventDefault();
		this.setState({
			isLoading: true
		});

		const product = {
			name: this.state.name,
			description: this.state.description,
			endpoint: this.state.endpoint,
			image: this.state.image,
			actions: this.state.actions,
			events: this.state.events
		};
		console.log("PRODUCT EXAMPLE", product);
		this.props.addProduct(product);
	};

	getInputsAccordingToType = (
		property,
		aeIndex,
		aePropId,
		layout,
		lableLessLayout,
		ae
	) => {
		switch (property.type) {
			case "int":
			case "double":
				return (
					<div>
						<FormItem label="Range" {...layout}>
							<Input.Group size="large">
								<Input
									style={{ width: "25%" }}
									placeholder="minimum"
									name="min"
									value={property.min}
									onChange={this.handleChangeProperty(
										aeIndex,
										aePropId,
										ae
									)}
								/>
								<Input
									style={{ width: "25%" }}
									placeholder="maximum"
									name="max"
									value={property.max}
									onChange={this.handleChangeProperty(
										aeIndex,
										aePropId,
										ae
									)}
								/>
							</Input.Group>
						</FormItem>
					</div>
				);
			case "discrete":
				const { inputVisible, inputValue } = this.state;
				return (
					<div>
						<FormItem label="Options" {...layout}>
							{
								<Button
									size="large"
									onClick={this.showInput}
									style={{
										background: "#fff",
										borderStyle: "dashed",
										width: "100%"
									}}
								>
									<Icon type="plus" />Add option
								</Button>
							}
						</FormItem>

						<FormItem {...lableLessLayout}>
							{property.options.map((option, optionId) => {
								const h = this.handleClose;
								return (
									<Tag
										key={option}
										closable
										afterClose={this.handleClose(
											aeIndex,
											aePropId,
											optionId,
											ae
										)}
									>
										{option}
									</Tag>
								);
							})}

							{inputVisible && (
								<Input
									type="text"
									style={{ width: 78 }}
									name="inputValue"
									value={this.state.inputValue}
									onChange={this.onChange}
									onPressEnter={this.handleChangeProperty(
										aeIndex,
										aePropId,
										ae,
										true
									)}
									onBlur={this.hideInput}
								/>
							)}
						</FormItem>
					</div>
				);
			default:
		}
	};

	handleRemoveAE = (aeIndex, ae) => () => {
		this.setState({
			[ae]: this.state[ae].filter((singleAe, index) => aeIndex !== index)
		});
	};

	render() {
		const uploadButton = (
			<div>
				<Icon type={this.state.loading ? "loading" : "plus"} />
				<div>Upload</div>
			</div>
		);
		const {
			image,
			endpoint,
			name,
			description,
			inputVisible,
			inputValue
		} = this.state;
		const formItemLayout = {
			labelCol: {
				xs: { span: 24, offset: 0 },
				sm: { span: 6, offset: 0 }
			},
			wrapperCol: {
				xs: { span: 24, offset: 0 },
				sm: { span: 10, offset: 0 }
			}
		};
		const formItemLayoutWithOutLabel = {
			wrapperCol: {
				xs: { span: 24, offset: 0 },
				sm: { span: 10, offset: 6 }
			}
		};

		const formItemLayout1 = {
			labelCol: {
				xs: { span: 24, offset: 0 },
				sm: { span: 6, offset: 0 }
			},
			wrapperCol: {
				xs: { span: 24, offset: 0 },
				sm: { span: 10, offset: 0 }
			}
		};
		const formItemLayoutWithOutLabel1 = {
			wrapperCol: {
				xs: { span: 24, offset: 0 },
				sm: { span: 10, offset: 6 }
			}
		};

		const Actions = (
			<ActionEvent
				formItemLayoutWithOutLabel={formItemLayoutWithOutLabel1}
				formItemLayout={formItemLayout}
				aeCollection={this.state.actions}
				ae="actions"
				handleChangeAE={this.handleChangeAE}
				getInputsAccordingToType={this.getInputsAccordingToType}
				handleChangeProperty={this.handleChangeProperty}
				handleRemoveProperty={this.handleRemoveProperty}
				handleAddProperty={this.handleAddProperty}
				handleRemoveAE={this.handleRemoveAE}
				handleAddAE={this.handleAddAE}
			/>
		);

		const Events = (
			<ActionEvent
				formItemLayoutWithOutLabel={formItemLayoutWithOutLabel}
				formItemLayout={formItemLayout}
				aeCollection={this.state.events}
				ae="events"
				handleChangeAE={this.handleChangeAE}
				getInputsAccordingToType={this.getInputsAccordingToType}
				handleChangeProperty={this.handleChangeProperty}
				handleRemoveProperty={this.handleRemoveProperty}
				handleAddProperty={this.handleAddProperty}
				handleRemoveAE={this.handleRemoveAE}
				handleAddAE={this.handleAddAE}
			/>
		);

		return (
			<ContentWrapper>
				<Form>
					<Tabs
						tabBarExtraContent={
							<Button.Group>
								<Button onClick={this.handleAddAE("actions")}>
									<Icon type="plus" />Add action
								</Button>
								<Button onClick={this.handleAddAE("events")}>
									<Icon type="plus" />Add event
								</Button>
							</Button.Group>
						}
					>
						<TabPane tab="Base info" key="1">
							<FormItem label="Name" {...formItemLayout}>
								<Input
									placeholder="Product name"
									name="name"
									size="large"
									value={name}
									onChange={this.onChange}
								/>
							</FormItem>
							<FormItem label="Description" {...formItemLayout}>
								<TextArea
									placeholder="Product name"
									name="description"
									size="large"
									value={description}
									onChange={this.onChange}
								/>
							</FormItem>

							<FormItem label="Image" {...formItemLayout}>
								<Upload
									showUploadList={false}
									action="//jsonplaceholder.typicode.com/posts/"
									name="avatar"
									listType="picture-card"
									showUploadList={false}
									onChange={this.handleChange}
								>
									{image ? (
										<img src={image} alt="" />
									) : (
										uploadButton
									)}
								</Upload>
							</FormItem>

							<FormItem label="Endpoint" {...formItemLayout}>
								<Input
									placeholder="Endpoint"
									name="endpoint"
									size="large"
									value={endpoint}
									onChange={this.onChange}
								/>
							</FormItem>
						</TabPane>
						<TabPane tab="Actions" key="2">
							{Actions}
						</TabPane>
						<TabPane tab="Events" key="3">
							{Events}
						</TabPane>
					</Tabs>
					<FormItem {...formItemLayoutWithOutLabel}>
						<Button
							loading={this.state.isLoading}
							type="primary"
							size="large"
							height={200}
							onClick={this.onSubmit}
							style={{
								marginTop: "60px",
								width: "100%",
								height: "60px"
							}}
						>
							<Icon type="plus" />Submit
						</Button>
					</FormItem>
				</Form>
			</ContentWrapper>
		);
	}
}

export default connect(null, { addProduct })(ProductsPage);
