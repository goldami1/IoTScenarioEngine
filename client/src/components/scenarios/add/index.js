import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import ContentWrapper from "../../common/ContentWrapper";
import CreateModal from "./CreateModal";
import _ from 'lodash';
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


const devices = [
	{
		id:'1',
		name:'clock',
		description:'show time',
		img:'some image',
		vendor:'the company',
		endpoint:'',
		

		events:[
			{
				id:'1',
				name:'alarm',
				description:'rings on time',
				endpoint:'clock.com/alarm',
				properties:[
					{
						id:'1',
						name:'hour',
						description:'enter time',
						type:'discrete',
						options:[
							{
								id:1,
								name:'1'
							},
							{
								id:2,
								name:'2'
							}
						],
						value:''
					},
					{
						id:'2',
						name:'am / pm',
						description:'choose am or pm',
						type:'string',
						value:''
					}
				]
			}

		],
		actions:[]
	},
	{
		id:'2',
		name:'clock',
		description:'show time',
		img:'some image',
		vendor:'the company',
		endpoint:'',
		

		actions:[
			{
				id:'1',
				name:'alarm',
				description:'rings on time',
				endpoint:'clock.com/alarm',
				properties:[
					{
						id:'1',
						name:'hour',
						description:'enter time',
						type:'discrete',
						options:[
							{
								id:1,
								name:'1'
							},
							{
								id:2,
								name:'2'
							}
						],
						value:''
					},
					{
						id:'2',
						name:'am / pm',
						description:'choose am or pm',
						type:'string',
						value:''
					}
				]
			}

		],
		events:[]
	},
];


// {
// 	value: "time",
// 	label: "Time",
// 	children: [
// 		{
// 			value: "daily",
// 			label: "Daily"
// 		},
// 		{
// 			value: "date",
// 			label: "Specific date"
// 		}
// 	]
// }


function mapDevicesToDropdown(devices,type)
{
	return _.map(devices, (device) =>{
		console.log(device);
		return {
			label:device.name,
			value:device.id,
			children :_.map(device[type],(ae)=>{
				 return {value:ae.id,label:ae.name
			}})
		}	
	});
}

function getDeviceWith(devices,type)
{
	return _.filter(devices,(item) => {return item[type].length >0}) 
}

const events = getDeviceWith(devices,'events');
const actions = getDeviceWith(devices,'actions');
const test = mapDevicesToDropdown(actions,'actions');

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
				if (index === type) {
					return [...singleAe,ae];
				}
				return singleAe;
			})
		});
		console.log(this.state);
		console.log(Array.isArray(this.state.lists));
	};

	removeAe = (list,id)=> {
		this.setState({
			lists: this.state.lists.map((singleList, listIndex) => {
				var prop;
				if (listIndex === list) {
					return prop = singleList.filter(
						(ae, aeIndex) => aeIndex !== id
					);
				}
				return singleList;
			})
		});
	}

	handleAeDropdown = (item) => (op) =>{
		const {key:operation} = op;
		const {dropId:list,aeId:id} = item;
		this[operation](list,id);
	}

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

		const aeMenu = {
			handle: this.handleAeDropdown,
			items:[
			//	[<text>		,<icon>,	<operation>	],
				['Delete',	'delete',	'removeAe'	],
				['Edit',	'edit',		'editAe'	]
			]
		};

		return (
			<DragDropContext onDragEnd={this.onDragEnd}>
				<Layout style={{ margin: "40px", borderRadius: "5px solid " }}>
					<Content
						style={{ background: "#fff", padding: "10px", overflow: "hidden" }}
					>
						<AcEvList style={{float:'left'}} lists={this.state.lists} id={2} aeMenu={aeMenu}/>
						<AcEvList style={{float:'left'}} lists={this.state.lists} id={3} aeMenu={aeMenu}/>
						<AcEvList style={{float:'left'}} lists={this.state.lists} id={4} aeMenu={aeMenu}/>
						<AcEvList style={{float:'left'}} lists={this.state.lists} id={5} aeMenu={aeMenu}/>
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

						
							<CreateModal
								devices={this.state.inventoryType == "event" ? events : actions}
								type={this.state.inventoryType}
								visible={this.state.modalVisible}
								onCancel={this.onModalCancel}
								onOk={this.addAE}
							/>


							<AcEvList
								aeMenu={aeMenu}	
								lists={this.state.lists}
								id={this.state.inventoryType == "event" ? 0 : 1}
							/>

						</Layout>
					</Sider>
				</Layout>
			</DragDropContext>
		);
	}
}

export default withRouter(ScenarioForm);
