import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import ContentWrapper from "../../common/ContentWrapper";
import CreateModal from "./CreateModal";
import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";
import { reorder, reorderQuoteMap } from "./reorder";
import AcEvList from "./AcEvList";
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

const getItems = (count, offset) =>
	Array.from({ length: count }, (v, k) => k).map(k => ({
		id: `item-${k + offset}`,
		content: `item ${k + offset}`
	}));

class ScenarioForm extends Component {
	constructor(props) {
		super(props);
		this.state = {
			lists: [
				getItems(5, 0),
				getItems(4, 20),
				getItems(2, 40),
				getItems(7, 60),
				getItems(3, 80),
				getItems(7, 100)
			],
			items: getItems(5),
			items2: getItems(4),
			inventory: [],
			inventoryType: "event",
			modalVisible: false
		};
	}

	onDragEnd = result => {
		if (!result.destination) {
			return;
		}

		this.setState(
			reorderQuoteMap({
				quoteMap: this.state.lists,
				source: result.source,
				destination: result.destination
			})
		);
	};

	addAE = ae => () => {
		this.setState({
			inventory: this.state.inventory.concat([ae])
		});
	};

	removeFromInventory = index => () => {
		this.setState({
			inventory: this.state.inventory.filter((s, ind) => ind !== index)
		});
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
			<DragDropContext onDragEnd={this.onDragEnd}>
				<Layout style={{ margin: "40px", borderRadius: "5px solid " }}>
					<Content
						style={{ background: "#fff", padding: "10px", overflow: "hidden" }}
					>
						<AcEvList lists={this.state.lists} id={2} />
						<AcEvList lists={this.state.lists} id={3} />
						<AcEvList lists={this.state.lists} id={4} />
						<AcEvList lists={this.state.lists} id={5} />
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
								name="inventoryType"
								value={this.state.inventoryType}
							>
								<RadioButton value="event">Events</RadioButton>
								<RadioButton value="action">Actions</RadioButton>

								<RadioButton>
									<Icon type="plus" />
								</RadioButton>
							</RadioGroup>
							<CreateModal
								type={this.state.inventoryType}
								visible={this.state.modalVisible}
								onCancel={this.onModalCancel}
								onOk={this.addAE}
							/>
						</Row>
						<Row>
							<AcEvList
								lists={this.state.lists}
								id={this.state.inventoryType == "event" ? 0 : 1}
							/>
						</Row>
						<Row>
							<ul>
								{this.state.inventory.map((item, index) => (
									<li onClick={this.removeFromInventory(index)}>{index}</li>
								))}
							</ul>
						</Row>
					</Sider>
				</Layout>
			</DragDropContext>
		);
	}
}

export default withRouter(ScenarioForm);
