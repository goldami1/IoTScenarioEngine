import React, { Component } from "react";
import { connect } from "react-redux";
import { Link, withRouter } from "react-router-dom";
import ContentWrapper from "../../common/ContentWrapper";
import { fetchDevices } from "../../../actions/deviceActions";
import {deviceInputReduction} from "./ioReduction"
import { addScenario ,fetchScenarios} from "../../../actions/scenarioActions";
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

//move from here


// const demo data
const devices = [
	{
		id:1,
		name:'clock',
		description:'show time',
		img:'some image',
		vendor:'the company',
		endpoint:'',
		

		events:[
			{
				id:1,
				name:'alarm',
				description:'rings on time',
				endpoint:'clock.com/alarm',
				properties:[
					{
						id:1,
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
						id:1,
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
		id:1,
		name:'clock action',
		description:'show time',
		img:'some image',
		vendor:'the company',
		endpoint:'',
		

		actions:[
			{
				id:1,
				name:'alarm action',
				description:'rings on time',
				endpoint:'clock.com/alarm',
				properties:[
					{
						id:1,
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
						id:2,
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


function getDeviceWith(devices,type)
{
	return _.filter(devices,(item) => {return item[type].length >0}) 
}

	const events = getDeviceWith(devices,'events');
	const actions = getDeviceWith(devices,'actions');
	// const test = mapDevicesToDropdown(actions,'actions');

const RadioButton = Radio.Button;
const RadioGroup = Radio.Group;
const { Header, Footer, Sider, Content } = Layout;
const FormItem = Form.Item;

const getItems = (count, offset) =>
	Array.from({ length: count }, (v, k) => k).map(k => ({
		id: `item-${k + offset}`,
		content: `item-${k + offset}`
	}));

var num
class ScenarioForm extends Component {
	constructor(props) {
		super(props);
		this.state = {
			lists: [
				[],
				[],
				[],
				[],
				[],
				[]
			],
			aeCounter:0,
			actionDevices:[],
			eventDevices:[],
			inventoryType: "event",
			modalVisible: false
		};


	
	}
	componentDidMount() {
		this.props.fetchDevices();
	}

	componentWillReceiveProps(nextProps) {

		if(this.props !== nextProps) {
		  this.setState({
			actionDevices:getDeviceWith(deviceInputReduction(nextProps.devices,'actions'),'actions'),
			eventDevices:getDeviceWith(deviceInputReduction(nextProps.devices,'events'),'events'),
		  });
		}
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

	addAE = (ae) => () => {
		ae.id = `${this.state.aeCounter}`;
		this.setState({
			aeCounter:ae.id + 1,
			lists: this.state.lists.map((singleAe, index) => {
				if (index === ae.type) 
				{
					return [...singleAe,ae];
				}
				return singleAe;
			})
		});
		this.onModalCancel();
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
		var newState = event.target.value
			? { [event.target.name]: event.target.value }
			: { modalVisible: true };
		this.setState(newState);
	};

	onMenuChange = event => {
		var newState = event.key!="addAe"
			? { inventoryType: event.key }
			: { modalVisible: true };
		this.setState(newState); 		
	}

	onModalCancel = () => {
		this.setState({ modalVisible: false });
	};

	createScenario = () =>
	{
		return "fuck";
	}
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
			<Modal
				width = {1300}
				visible={this.props.visible}
				onCancel={this.props.onCancel}
				onOk={() =>{this.props.onOk(this.createScenario())} }
			>
				<DragDropContext onDragEnd={this.onDragEnd}>
					<Layout style={{ margin: "40px", borderRadius: "5px solid " }}>
						<Content
							style={{ background: "#fff", padding: "10px", overflow: "hidden" }}
						>
							<AcEvList style={{float:'left'}} lists={this.state.lists} id={2} aeMenu={aeMenu} preview={ !_.isEmpty(this.props.preview)}/>
							<AcEvList style={{float:'left'}} lists={this.state.lists} id={3} aeMenu={aeMenu} preview={ !_.isEmpty(this.props.preview)}/>
							<AcEvList style={{float:'left'}} lists={this.state.lists} id={4} aeMenu={aeMenu} preview={ !_.isEmpty(this.props.preview)}/>
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
									{	
										_.isEmpty(this.props.preview) &&
										<Menu.Item key="addAe" ><Icon type="plus" /></Menu.Item>
									}
								</Menu>


									<CreateModal
										devices={this.state.inventoryType == "event" ? this.state.eventDevices : this.state.actionDevices}
										type={this.state.inventoryType}
										visible={this.state.modalVisible}
										onCancel={this.onModalCancel}
										onOk={this.addAE}
									/>


									<AcEvList
										preview={ !_.isEmpty(this.props.preview)}
										aeMenu={aeMenu}	
										lists={this.state.lists}
										id={this.state.inventoryType == "event" ? 0 : 1}
									/>

								</Layout>
						</Sider>
					</Layout>
				</DragDropContext>
			</Modal>
		);
	}
}


function mapStateToProps({devices}) {
	return {
		devices: devices.devices
	};
}

export default connect(mapStateToProps, { fetchDevices,addScenario })(withRouter(ScenarioForm));

