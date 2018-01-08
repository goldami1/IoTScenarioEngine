import React, { Component } from 'react';
import { Upload, Icon, message,Button, Modal, Form, Input, Radio ,Row, Col} from 'antd';
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
							type:''
						}
					]
				}
			],

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

	onChange = (event) =>{
		this.setState({
			[event.target.name] : event.target.value
		}) 
	}

	handleRemoveProperty = (idx,pid) => (evt) => {
		console.log(idx,pid);
	}

	handlePropertyChange = (idx,pid) => (evt) => {
		this.setState({

			actions: this.state.actions.map((action, index) => {
				var prop;
				if(index === idx){
					prop = action.properties.map((property,pindex) => {
						if (pindex === pid) {
							console.log(...property, [evt.target.name]: evt.target.value);
							return {
								...property, [evt.target.name]: evt.target.value
							};
						}
						return property;
					})
					console.log({...action, properties:prop});
					return {
						...action, properties:prop
					}	
				}
				return action;
			})

		});
	}

	handleActionChange = (idx) => (evt) => {
		const newAction = this.state.actions.map((action, sidx) => {
		if (idx !== sidx) return action;
			return { ...action, [evt.target.name]: evt.target.value };
		});

		this.setState({ actions: newAction });
	}

	handleAddProperty = (idx) => ()=>{
		console.log(this.state.actions[idx]);
		this.setState({
			actions: this.state.actions.map((action, index) => {
				if(index === idx){
					return {
						...action,
						properties: [...action.properties,
						{
							name: '',
							description:'',
							type:''
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
							type:''
						}
					]
				}
			])
		});
	}


	handleRemoveAction = (idx) => () => {
		this.setState({
			actions: this.state.actions.filter((s, sidx) => idx !== sidx)
		});
	}

 	render() {
		const uploadButton = (
			<div>
				<Icon type={this.state.loading ? 'loading' : 'plus'} />
				<div className="ant-upload-text">Upload</div>
			</div>
		);
		const {image, endpoint, name, description} = this.state;


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

			<Form style={{width:'20%'}}>

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


				{
					this.state.actions.map((action, idx) => (
						<FormItem >
							<Input
								name="name"
								placeholder={`Action ${idx} name`}
								value={action.name}
								onChange={this.handleActionChange(idx)}/>
							<Input
								name="description"
								placeholder={`Action ${idx} description`}
								value={action.description}
								onChange={this.handleActionChange(idx)}/>

							{
								action.properties.map((property,pid) => (
								<FormItem >	
									<Input
										name="name"
										placeholder={`Action ${idx}  Property  ${pid} name`}
										value={property.name}
										onChange={this.handlePropertyChange(idx,pid)}/>
									<Input
										name="description"
										placeholder={`Action ${idx}  Property  ${pid} description`}
										value={property.description}
										onChange={this.handlePropertyChange(idx,pid)}/>
									<Input
										name="type"
										placeholder={`Action ${idx}  Property  ${pid} type`}
										value={property.type}
										onChange={this.handlePropertyChange(idx,pid)}/>
									<Button onClick={this.handleRemoveProperty(idx,pid)} >
										<Icon type="minus" />
									</Button>
								</FormItem>
								))
							}

							<Button type="dashed" onClick={this.handleAddProperty(idx)} >
								<Icon type="plus" />Add property
							</Button>
							<Button onClick={this.handleRemoveAction(idx)} >
								<Icon type="minus" />
							</Button>
						</FormItem>
					))
				}
				<FormItem>
					<Button type="dashed" onClick={this.handleAddAction} >
					<Icon type="plus" />Add action
					</Button>
				</FormItem>
			</Form>
		);
  }
}

export default connect(null, null)(ProductsPage);