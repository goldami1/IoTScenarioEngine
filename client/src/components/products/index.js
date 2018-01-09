import React, { Component } from 'react';
import { Upload, Icon, message,Button, Modal, Form, Input, Radio ,Row, Col, Tag, Tooltip} from 'antd';
import { connect } from "react-redux";

const FormItem = Form.Item;

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


	getAdditional (property,aid,pid) {
		switch (property.type){
			case 'int':
			case 'double':
				return (
					<div>
						<FormItem label="Minimum" >
							<Input
								name="min"
								value={property.min}
								onChange={this.handlePropertyChange(aid,pid)}/>
						</FormItem>						
						<FormItem label="Maximum" >
							<Input
								name="max"
								value={property.max}
								onChange={this.handlePropertyChange(aid,pid)}/>
						</FormItem>	
					</div>
				)
			case 'discrete':
				const {image, endpoint, name, description, tags, inputVisible, inputValue } = this.state;
				return (
					<div>{
							property.options.map((option, oid) => {
								const h= this.handleClose;
								return  (
									<Tag key={option} closable afterClose={this.handleClose(aid,pid,oid)}>
										{option}
									</Tag>
								);

							})
						}											
						{inputVisible && 
							(
								<Input
									ref={this.saveInputRef}
									type="text"
									size="small"
									style={{ width: 78 }}
									name="inputValue"
									onChange={this.onChange}
									onPressEnter={this.handlePropertyChange(aid,pid,true)}
								/>
							)
						}
						{!inputVisible &&
							(
								<Tag
									onClick={this.showInput}
									style={{ background: '#fff', borderStyle: 'dashed' }}
									>
									<Icon type="plus" /> New Tag
								</Tag>
							)
						}</div>							
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
				<div className="ant-upload-text">Upload</div>
			</div>
		);
		const {image, endpoint, name, description, tags, inputVisible, inputValue } = this.state;
		const formItemLayout = {
			labelCol: {
				xs: { span: 24 },
				sm: { span: 8 },
			},
			wrapperCol: {
				xs: { span: 24 },
				sm: { span: 16 },
			}
		};
		return (

			<Form >

					<FormItem label="Name" >
					  <Input placeholder="Product name" name="name" 
						value={name} 
						onChange={this.onChange}/>
					</FormItem>
					<FormItem label="Description">
					  <Input placeholder="Product name" name="description" 
						value={description} 
						onChange={this.onChange}/>
					</FormItem>

					<FormItem label="Image">
						<Upload
							name="avatar"
							listType="picture-card"
							className="avatar-uploader"
							showUploadList={false}
							action="//jsonplaceholder.typicode.com/posts/"
							onChange={this.handleChange}
							>
							{image ? <img src={image} alt="" /> : uploadButton}
						</Upload>
					</FormItem>
	

					<FormItem label="Endpoint" >
					  <Input placeholder="Endpoint" name="endpoint" 
						value={endpoint} 
						onChange={this.onChange}/>
					</FormItem>					

				<div>
					{
						this.state.actions.map((action, aid) => (
							<div >
							<FormItem label="Name">
								<Input
									name="name"
									placeholder={`Action ${aid} name`}
									value={action.name}
									onChange={this.handleActionChange(aid)}/>
							</FormItem>
							<FormItem label="Description">
								<Input
									name="description"
									placeholder={`Action ${aid} description`}
									value={action.description}
									onChange={this.handleActionChange(aid)}/>
							</FormItem>
								{
									action.properties.map((property,pid) => (
									<div>
										<FormItem label="Name">	
											<Input
												name="name"
												placeholder={`Action ${aid}  Property  ${pid} name`}
												value={property.name}
												onChange={this.handlePropertyChange(aid,pid)}/>
										</FormItem>
										<FormItem label="Description">
											<Input
												name="description"
												placeholder={`Action ${aid}  Property  ${pid} description`}
												value={property.description}
												onChange={this.handlePropertyChange(aid,pid)}/>
										</FormItem>
										<FormItem label="Type">
											<Radio.Group value={property.type} name="type" onChange={this.handlePropertyChange(aid,pid)}>
												<Radio.Button value="string">String</Radio.Button>
												<Radio.Button value="int">Int</Radio.Button>
												<Radio.Button value="double">Double</Radio.Button>
												<Radio.Button value="discrete">Discrete</Radio.Button>
											</Radio.Group>
										</FormItem>
											{ this.getAdditional(property,aid,pid) }
	
											<Button onClick={this.handleRemoveProperty(aid,pid)} >
												<Icon type="minus" />Remove property
											</Button>
									</div>
									))
								}

								<Button type="dashed" onClick={this.handleAddProperty(aid)} >
									<Icon type="plus" />Add property
								</Button>
								<FormItem>
									<Button onClick={this.handleRemoveAction(aid)} >
										<Icon type="minus" />Remove action
									</Button>
								</FormItem>
							</div>
						))
					}
					<FormItem>
						<Button type="dashed" onClick={this.handleAddAction} >
						<Icon type="plus" />Add action
						</Button>
					</FormItem>
				</div>
			</Form>
		);
  }
}

export default connect(null, null)(ProductsPage);