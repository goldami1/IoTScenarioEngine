import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import ContentWrapper from "../../common/ContentWrapper";
import CreateModal from "./CreateModal";
import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";
import { reorder, reorderQuoteMap } from "./reorder";
import AcEvList from "./AcEvList";
import {
	Menu,
	Dropdown,
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
				getItems(2, 0),
				getItems(1, 20),
				getItems(2, 40),
				getItems(3, 60),
				getItems(4, 80),
				getItems(1, 100)
			],
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

	addAE = (type,ae) => () => {
		ae.id= 999;
		console.log(this.state);
		console.log(Array.isArray(this.state.lists));

		this.setState({

			lists: this.state.lists.map((singleAe, index) => {
				//fuck js
				// console.log(singleAe.concat(ae));
				// console.log(singleAe);
				// console.log(...singleAe);
				// console.log([...singleAe]);
				// console.log([...singleAe,ae]);
				if (index === type) {
					return [...singleAe,ae];
				}
				return singleAe;
			})
		});
		console.log(this.state);
		console.log(Array.isArray(this.state.lists));
	};

	removeFromInventory = index => () => {
		this.setState({
			inventory: this.state.inventory.filter((s, ind) => ind !== index)
		});
	};

	onChange = event => {
		console.log(event)
		var newState = event.target.value
			? { [event.target.name]: event.target.value }
			: { modalVisible: true };
		this.setState(newState);
	};

	onMenuChange = event => {
		console.log(event)
		var newState = event.key!="addAe"
			? { inventoryType: event.key }
			: { modalVisible: true };
		this.setState(newState); 		
	}

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
						<AcEvList style={{float:'left'}} lists={this.state.lists} id={2} />
						<AcEvList style={{float:'left'}} lists={this.state.lists} id={3} />
						<AcEvList style={{float:'left'}} lists={this.state.lists} id={4} />
						<AcEvList style={{float:'left'}} lists={this.state.lists} id={5} />
					</Content>
					<Sider
						width={270}
						style={{
							background: "white",
							borderLeft: "1px solid #ebedf0"
						}}
					>
					<Layout  style={{background:'white'}}>
			
					 	<Menu
							mode="horizontal"
							style={{ lineHeight: '64px' }}
							selectedKeys={[this.state.inventoryType]}
							onSelect={this.onMenuChange}
						>
							<Menu.Item key="event" >Events</Menu.Item>
							<Menu.Item key="action">Actions</Menu.Item>
							<Menu.Item key="addAe" ><Icon type="plus" /></Menu.Item>
						</Menu>

							
							{
							// 	<RadioGroup
							//  	style={{width:'100%', border:'5px'}}
							// 	onChange={this.onChange}
							// 	name="inventoryType"
							// 	value={this.state.inventoryType}
							// >

							// 	<RadioButton  value="event">Events</RadioButton>
							// 	<RadioButton value="action">Actions</RadioButton>	
							// 	<RadioButton>
							// 		<Icon type="plus" />
							// 	</RadioButton>
							// </RadioGroup>
							}

							<CreateModal
								type={this.state.inventoryType}
								visible={this.state.modalVisible}
								onCancel={this.onModalCancel}
								onOk={this.addAE}
							/>


							<AcEvList
								lists={this.state.lists}
								id={this.state.inventoryType == "event" ? 0 : 1}
							/>

							<ul>
								{
									// this.state.inventory.map((item, index) => (
									// <li onClick={this.removeFromInventory(index)}>{index}</li>
									// 	))
								}
							</ul>

						</Layout>
					</Sider>
				</Layout>
			</DragDropContext>
		);
	}
}

export default withRouter(ScenarioForm);
