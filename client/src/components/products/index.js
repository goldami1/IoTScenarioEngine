import React, { Component } from 'react';
import {List, Tabs, Upload, Icon, message,Button, Modal, Form, Input, Radio ,Row, Col, Tag, Tooltip,Collapse} from 'antd';
import { connect } from "react-redux";
import  ContentWrapper from "../common/ContentWrapper";
import ActionEvent from './ActionEvent';
const {Panel} = Collapse.Panel;
const FormItem = Form.Item;
const { TextArea } = Input;

function getBase64(img, callback) {
	const reader = new FileReader();
	reader.addEventListener('load', () => callback(reader.result));
	reader.readAsDataURL(img);
}


class ProductsPage extends Component {

	constructor(props){
		super(props);
		this.state = {
			name:'',
			description:'',
			endpoint:'',
			image:'',
			actions:[
				{
					name:'',
					description:'',
					properties: [
						{
							name: '',
							description:'',
							type:'',
							options: []
						}
					]
				}
			],
			events:[
				{
					name:'',
					description:'',
					properties: [
						{
							name: '',
							description:'',
							type:'',
							options: []
						}
					]
				}
			],

			inputValue:'',
			tags: ['Unremovable', 'Tag 2', 'Tag 3'],
			inputVisible: false,
			loading: false,
		};

	}

	handleChange = (info) => {
		if (info.file.status === 'uploading') {
			this.setState({ loading: true });
			return;
		}
		if (info.file.status === 'done') {
			// Get this url from response in real world.
			getBase64(info.file.originFileObj, image => this.setState({
				image,
				loading: false,
			}));
		}
	}

	handleInputConfirm = () => {

		const state = this.state;
		const inputValue = state.inputValue;
		let tags = state.tags;
		if (inputValue && tags.indexOf(inputValue) === -1) {
			tags = [...tags, inputValue];
		}
		console.log(tags);
		this.setState({
			tags,
			inputVisible: false,
			inputValue: '',
		});
	}

	saveInputRef = input => this.input = input
		showInput = () => {
		this.setState({ inputVisible: true }, () => this.input.focus());

	}

	hideInput = () => {
		this.setState({ inputVisible: false,inputValue:'' });
	}

	onChange = (event) =>{
		this.setState({
			[event.target.name] : event.target.value
		}) 
	}

	handleRemoveAction = (aid) => () => {
		this.setState({
			actions: this.state.actions.filter((s, said) => aid !== said)
		});
	}

	handleRemoveProperty = (aid,pid) => (event) => {
		this.setState({
			actions: this.state.actions.map((action, index) => {
				var prop;
				if(index === aid){
					prop = action.properties.filter((property,pindex) => pindex !== pid)
					return {
						...action, properties:prop
					}	
				}
				return action;
			})

		});
	}

	handleRemoveProperty = (aid,pid) => (event) => {
		this.setState({
			actions: this.state.actions.map((action, index) => {
				var prop;
				if(index === aid){
					prop = action.properties.filter((property,pindex) => pindex !== pid)
					return {
						...action, properties:prop
					}	
				}
				return action;
			})

		});
	}
	handlePropertyChange = (aid,pid,isOption) => (event) => {
		const value = this.state.inputValue;
		this.setState({
			actions: this.state.actions.map((action, index) => {
				var prop;
				if(index === aid){
					prop = action.properties.map((property,pindex) => {
						if (pindex === pid) {
							if (isOption) {
								return {
									...property,
									options:[...property.options, value]							
								};								
							}
							return {
								...property, [event.target.name]: event.target.value
							};
						}
						return property;
					})
					return {
						...action, properties:prop
					}	
				}
				return action;
			})

		});
	}


	handleActionChange = (aid) => (event) => {
		const newAction = this.state.actions.map((action, said) => {
		if (aid !== said) return action;
			return { ...action, [event.target.name]: event.target.value };
		});

		this.setState({ actions: newAction });
	}




	handleAddProperty = (aid) => ()=>{
		console.log(this.state.actions[aid]);
		this.setState({
			actions: this.state.actions.map((action, index) => {
				if(index === aid){
					return {
						...action,
						properties: [...action.properties,
						{
							name: '',
							description:'',
							type:'',
							options:[]
						}]
					};
				}
				return action;
			})
		});
	}

	handleAddAction = () => {
		this.setState({
			actions: this.state.actions.concat([
				{
					name:'',
					description:'',
					properties: [
						{
							name: '',
							description:'',
							type:'',
							options:[]
						}
					]
				}
			])
		});
	}


	handleClose = (aid,pid,oid) => (event) => {
		this.setState({
			actions: this.state.actions.map((action, index) => {
				var prop,opts;
				if(index === aid){
					prop = action.properties.map((property,pindex) => {
						if (pindex === pid) {
							opts = property.options.filter((option,oindex) => oindex !== oid)
							console.log({...property, options:opts})
							return {
								...property, options:opts
							}
						}
						return property
					})
					return {
						action,properties:prop
					}	
				}
				return action;
			})
		});
	}


	getAdditional (property,aid,pid,layout,lableLessLayout) {
		switch (property.type){
			case 'int':
			case 'double':
				return (
					<div>
						<FormItem label="Range" {...layout}>
					        <Input.Group size="large">
									<Input
										style={{ width: '25%' }}
										placeholder="minimum"
										name="min"
										value={property.min}
										onChange={this.handlePropertyChange(aid,pid)}/>
									<Input
										style={{ width: '25%' }}
										placeholder="maximum"
										name="max"
										value={property.max}
										onChange={this.handlePropertyChange(aid,pid)}/>

					        </Input.Group>					
					
						</FormItem>	
					</div>
				)
			case 'discrete':
				const {image, endpoint, name, description, tags, inputVisible, inputValue } = this.state;
				return (
					<div>
					<FormItem label="Options" {...layout} >
						{
							
							(
								<Button
									size="large"
									onClick={this.showInput}
									style={{ background: '#fff', borderStyle: 'dashed',width:'100%' }}
									>
									<Icon type="plus" />Add option
								</Button>
							)
						}
					</FormItem>

					<FormItem {...lableLessLayout} >
						{
							property.options.map((option, oid) => {
								const h= this.handleClose;
								return  (
									<Tag key={option} closable afterClose={this.handleClose(aid,pid,oid)}>
										{option}
									</Tag>
								);

							})
						}
											
		
						{
							inputVisible && 
							(
								<Input
									ref={this.saveInputRef}
									type="text"
									style={{ width: 78 }}
									name="inputValue"
									onChange={this.onChange}
									onPressEnter={this.handlePropertyChange(aid,pid,true)}
									onBlur={this.hideInput}
								/>
							)
						}
						</FormItem>
						</div>							
				)
			default:
		}
	}

	handleRemoveAction = (aid) => () => {
		this.setState({
			actions: this.state.actions.filter((action, index) => aid !== index)
		});
	}

	render() {
		const uploadButton = (
			<div>
				<Icon type={this.state.loading ? 'loading' : 'plus'} />
				<div >Upload</div>
			</div>
		);
		const {image, endpoint, name, description, tags, inputVisible, inputValue } = this.state;
	    const formItemLayout = {
	      labelCol: {
	        xs: { span: 24 , offset: 0},
	        sm: { span: 6 , offset: 0},
	      },
	      wrapperCol: {
	        xs: { span: 24 , offset: 0 },
	        sm: { span: 10 , offset: 0},
	      },
	    };
		const formItemLayoutWithOutLabel = {
			wrapperCol: {
			xs: { span: 24, offset: 0 },
			sm: { span: 10, offset: 6 },
			},
		};
		const Actions = <ActionEvent formItemLayout={formItemLayout} formItemLayoutWithOutLabel={formItemLayoutWithOutLabel}/>;
		const Events = <ActionEvent formItemLayout={formItemLayout} formItemLayoutWithOutLabel={formItemLayoutWithOutLabel}/>;
		return (
			<ContentWrapper>
					<Row style={{margin:'40px 0'}}>
						<Col  offset={6} >
							<h1>Product creation</h1>
						</Col>
					</Row>

			<Form>


					<FormItem label="Name" {...formItemLayout} >
					  <Input placeholder="Product name" name="name" 
					 	size="large"
						value={name} 
						onChange={this.onChange}/>
					</FormItem>
					<FormItem label="Description" {...formItemLayout}>
					  <TextArea placeholder="Product name" name="description"
					 	 size="large"	 
						value={description} 
						onChange={this.onChange}/>
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
							{image ? <img src={image} alt="" /> : uploadButton}
						</Upload>
					</FormItem>
	

					<FormItem label="Endpoint"  {...formItemLayout}>
					  <Input placeholder="Endpoint" name="endpoint"
					  	size="large" 
						value={endpoint} 
						onChange={this.onChange}/>
					</FormItem>					

				<div>
					{/*{Actions}*/}
					{/*{Events}*/}
				</div>
			</Form>	

			</ContentWrapper>
		);
  }
}

export default connect(null, null)(ProductsPage);