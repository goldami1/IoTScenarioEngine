
import React from 'react';
import {List, Tabs, Upload, Icon, message,Button, Modal, Form, Input, Radio ,Row, Col, Tag, Tooltip,Collapse} from 'antd';
const {Panel} = Collapse.Panel;
const FormItem = Form.Item;
const { TextArea } = Input;

const ActionEvent = props => {


	return (

				<div>
					<Row style={{margin:'40px 0 10px'}}>
						<Col  offset={6} >
							<h3>{props.collectionName}</h3>
						</Col>
					</Row>
					{

						props.collection.map((ae, aeid) => (
									<div style={{
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
									placeholder={`${props.collectionName} ${aeid} name`}
									value={ae.name}
									onChange={props.handleAEChange(aeid,props.collectionName)}/>
							</FormItem>
							<FormItem label="Description" {...props.formItemLayout}>
								<TextArea
									size="large"
									name="description"
									placeholder={`${props.collectionName} ${aeid} description`}
									value={ae.description}
									onChange={props.handleAEChange(aeid,props.collectionName)}/>
							</FormItem>
												<Row style={{margin:'40px 0 10px'}}>
						<Col  offset={6} >
							<h3>Properties</h3>
						</Col>
					</Row>
								{
									ae.properties.map((property,pid) => (

									<div style={{
										border: '1px solid #ebedf0',
										borderRadius: '2px',
										padding: '20px',
										margin:' 0 0 16px'
									}}>

										<FormItem label="Name" {...props.formItemLayout}>	
											<Input
												size="large"
												name="name"
												placeholder={`${props.collectionName} ${aeid}  Property  ${pid} name`}
												value={property.name}
												onChange={props.handlePropertyChange(aeid,pid,props.collectionName)}/>
										</FormItem>
										<FormItem label="Description" {...props.formItemLayout}>
											<TextArea
												size="large"
												name="description"
												placeholder={`${props.collectionName} ${aeid}  Property  ${pid} description`}
												value={property.description}
												onChange={props.handlePropertyChange(aeid,pid,props.collectionName)}/>
										</FormItem>
										<FormItem label="Endpoint" {...props.formItemLayout}>	
											<Input
												size="large"
												name="endpoint"
												placeholder={`${props.collectionName} ${aeid}  Property  ${pid} name`}
												value={property.endpoint}
												onChange={props.handlePropertyChange(aeid,pid,props.collectionName)}/>
										</FormItem>		
										<FormItem label="Type" {...props.formItemLayout}>
											<Radio.Group value={property.type} name="type" onChange={props.handlePropertyChange(aeid,pid,props.collectionName)}>
												<Radio.Button value="string">String</Radio.Button>
												<Radio.Button value="int">Int</Radio.Button>
												<Radio.Button value="double">Double</Radio.Button>
												<Radio.Button value="discrete">Discrete</Radio.Button>
											</Radio.Group>
										</FormItem>
											{ props.getAdditional(property,aeid,pid,props.formItemLayout,props.formItemLayoutWithOutLabel,props.collectionName) }

										<FormItem {...props.formItemLayoutWithOutLabel}>
											<Button onClick={props.handleRemoveProperty(aeid,pid,props.collectionName)} size="large"  style={{width:'100%' }}>
												<Icon type="minus" />Remove property
											</Button>
										</FormItem>

									</div>
		
									))
								}
		
								<FormItem {...props.formItemLayoutWithOutLabel}>
									<Button type="dashed" onClick={props.handleAddProperty(aeid,props.collectionName)} size="large"  style={{width:'100%' }}>
										<Icon type="plus" />Add property
									</Button>
								</FormItem>
								<FormItem {...props.formItemLayoutWithOutLabel}>
									<Button onClick={props.handleRemoveAE(aeid,props.collectionName)}  size="large" style={{width:'100%' }}>
										<Icon type="minus" />Remove {props.collectionName}
									</Button>
								</FormItem>
							</div>
							</div>
						))
					}
					<FormItem {...props.formItemLayoutWithOutLabel} >
						<Button type="dashed" size="large" height={200} onClick={props.handleAddAE(props.collectionName)}  style={{width:'100%' }}>
						<Icon type="plus" />Add {props.collectionName}
						</Button>
					</FormItem>
				</div>
	);
};

export default ActionEvent;

