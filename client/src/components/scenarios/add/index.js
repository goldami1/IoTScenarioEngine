import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import ContentWrapper from "../../common/ContentWrapper";
import CreateModal from "./CreateModal";
import {
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

const RadioButton = Radio.Button;
const RadioGroup = Radio.Group;
const { Header, Footer, Sider, Content } = Layout;
const FormItem = Form.Item;

class ScenarioForm extends Component {
	constructor(props) {
		super(props);
		this.state = {
			inventory: "action",
			modalVisible: false
		};
	}

	addAE = type => () => {
		console.log(type);
	};

	onChange = event => {
		var newState = event.target.value
			? { [event.target.name]: event.target.value }
			: { modalVisible: true };
		this.setState(newState);
	};

	onModalCancel = () => {
		this.setState({ modalVisible: false });
	};

	render() {
		const formItemLayout = {
			labelCol: {
				xs: { span: 24 },
				sm: { span: 3 }
			},
			wrapperCol: {
				xs: { span: 24 },
				sm: { span: 16 }
			}
		};
		return (
			<Layout style={{ margin: "40px", borderRadius: "5px solid " }}>
				<Content style={{ background: "#fff", padding: "10px" }}>
					Content
				</Content>
				<Sider
					width={220}
					style={{
						padding: "10px",
						background: "white",
						borderLeft: "1px solid #ebedf0"
					}}
				>
					<Row>
						<RadioGroup
							onChange={this.onChange}
							name="inventory"
							value={this.state.inventory}
						>
							<RadioButton value="action">Actions</RadioButton>
							<RadioButton value="event">Events</RadioButton>
							<RadioButton>
								<Icon type="plus" />
							</RadioButton>
						</RadioGroup>
						<CreateModal
							type={this.state.inventory}
							visible={this.state.modalVisible}
							onCancel={this.onModalCancel}
							onOk={this.onModalCancel}
						/>
					</Row>
					<Row />
				</Sider>
			</Layout>
		);
	}
}

export default withRouter(ScenarioForm);
