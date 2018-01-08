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
			properties: [{ name: '' }],
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

	handlePropertyNameChange = (idx) => (evt) => {
		const newProperty = this.state.properties.map((property, sidx) => {
		if (idx !== sidx) return property;
			return { ...property, name: evt.target.value };
		});

		this.setState({ properties: newProperty });
	}


	handleAddProperty = () => {
		this.setState({
			properties: this.state.properties.concat([{ name: '' }])
		});
	}

  	handleRemoveProperty = (idx) => () => {
	    this.setState({
	    	properties: this.state.properties.filter((s, sidx) => idx !== sidx)
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

			<Form layout={'horizontal'}>
				<Row gutter={16}>
				<Col span={8}>
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
	      		</Col>
	      		<Col span={4}>
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
				</Col>
				</Row>
				<Row gutter={16}>
				<Col span={10}>
					<FormItem label="Endpoint" >
					  <Input placeholder="Product name" name="endpoint" 
					  	value={endpoint} 
					  	onChange={this.onChange}/>
					</FormItem>					
				</Col>
				</Row>

				{
					this.state.properties.map((property, idx) => (
						<FormItem>
							<Input
								placeholder={`Property ${idx + 1} name`}
								value={property.name}
								onChange={this.handlePropertyNameChange(idx)}/>							
							<Button onClick={this.handleRemoveProperty(idx)} >-</Button>
						</FormItem>
					))
				}
				<Button onClick={this.handleAddProperty} >Add property</Button>

			</Form>
		);
  }
}

export default connect(null, null)(ProductsPage);