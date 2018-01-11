
import React from 'react';
import {List, Tabs, Upload, Icon, message,Button, Modal, Form, Input, Radio ,Row, Col, Tag, Tooltip,Collapse} from 'antd';
const {Panel} = Collapse.Panel;
const FormItem = Form.Item;
const { TextArea } = Input;

const ActionEvent = props => {
	
	// const links = props.links.map(link => {
	// 		const [url, title, icon] = link;
	// 		return (
	// 			<Menu.Item key={url}>
							
	// 				<Link key={url} to={url}>
	// 					<Icon type={icon} />
	// 					<span>{title}</span>
	// 				</Link>
				
	// 			</Menu.Item>	
	// 		);
	// 	}
	// );

	return (
				<div>
					<Row style={{margin:'40px 0 10px'}}>
						<Col  offset={6} >
							<h3>Actions</h3>
						</Col>
					</Row>
					{

						props.state.actions.map((action, aid) => (
									<div
										key={action,aid}  
										style={{
										border: '3px solid #ebedf0',
										borderRadius: '10px',
										padding: '20px',
										margin:' 0 0 60px'
									}}>
							<div >
							<FormItem label="Name" {...props.formItemLayout}>
								<Input
									size="large"
									name="name"
									placeholder={`Action ${aid} name`}
									value={action.name}
									onChange={props.handleActionChange(aid)}/>
							</FormItem>
							<FormItem label="Description" {...props.formItemLayout}>
								<TextArea
									size="large"
									name="description"
									placeholder={`Action ${aid} description`}
									value={action.description}
									onChange={props.handleActionChange(aid)}/>
							</FormItem>
												<Row style={{margin:'40px 0 10px'}}>
						<Col  offset={6} >
							<h3>Properties</h3>
						</Col>
					</Row>
								{
									action.properties.map((property,pid) => (

									<div
										key={property,pid} 
										style={{
										border: '1px solid #ebedf0',
										borderRadius: '2px',
										padding: '20px',
										margin:' 0 0 16px'
									}}>

										<FormItem label="Name" {...props.formItemLayout}>	
											<Input
												size="large"
												name="name"
												placeholder={`Action ${aid}  Property  ${pid} name`}
												value={property.name}
												onChange={props.handlePropertyChange(aid,pid)}/>
										</FormItem>
										<FormItem label="Description" {...props.formItemLayout}>
											<TextArea
												size="large"
												name="description"
												placeholder={`Action ${aid}  Property  ${pid} description`}
												value={property.description}
												onChange={props.handlePropertyChange(aid,pid)}/>
										</FormItem>
										<FormItem label="Endpoint" {...props.formItemLayout}>	
											<Input
												size="large"
												name="endpoint"
												placeholder={`Action ${aid}  Property  ${pid} name`}
												value={property.endpoint}
												onChange={props.handlePropertyChange(aid,pid)}/>
										</FormItem>		
										<FormItem label="Type" {...props.formItemLayout}>
											<Radio.Group value={property.type} name="type" onChange={props.handlePropertyChange(aid,pid)}>
												<Radio.Button value="string">String</Radio.Button>
												<Radio.Button value="int">Int</Radio.Button>
												<Radio.Button value="double">Double</Radio.Button>
												<Radio.Button value="discrete">Discrete</Radio.Button>
											</Radio.Group>
										</FormItem>
											{ props.getAdditional(property,aid, props.formItemLayout,props.formItemLayoutWithOutLabel) }

										<FormItem {...props.formItemLayoutWithOutLabel}>
											<Button onClick={props.handleRemoveProperty(aid,pid)} size="large"  style={{width:'100%' }}>
												<Icon type="minus" />Remove property
											</Button>
										</FormItem>

									</div>
		
									))
								}
		
								<FormItem {...props.formItemLayoutWithOutLabel}>
									<Button type="dashed" onClick={props.handleAddProperty(aid)} size="large"  style={{width:'100%' }}>
										<Icon type="plus" />Add property
									</Button>
								</FormItem>
								<FormItem {...props.formItemLayoutWithOutLabel}>
									<Button onClick={props.handleRemoveAction(aid)}  size="large" style={{width:'100%' }}>
										<Icon type="minus" />Remove action
									</Button>
								</FormItem>
							</div>
							</div>
						))
					}
					<FormItem {...props.formItemLayoutWithOutLabel} >
						<Button type="dashed" size="large" height={200} onClick={props.handleAddAction}  style={{width:'100%' }}>
						<Icon type="plus" />Add action
						</Button>
					</FormItem>
					</div>
	);
};

export default ActionEvent;

