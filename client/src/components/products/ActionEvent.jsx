
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
							<h3>{props.ae}</h3>
						</Col>
					</Row>
					{

						props.aeCollection.map((singleAe, aeIndex) => (
									<div
										key={singleAe,aeIndex}  
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
									placeholder={`${props.ae} ${aeIndex} name`}
									value={singleAe.name}
									onChange={props.handleChangeAE(aeIndex,props.ae)}/>
							</FormItem>
							<FormItem label="Description" {...props.formItemLayout}>
								<TextArea
									size="large"
									name="description"
									placeholder={`${props.ae} ${aeIndex} description`}
									value={singleAe.description}
									onChange={props.handleChangeAE(aeIndex,props.ae)}/>
							</FormItem>
												<Row style={{margin:'40px 0 10px'}}>
						<Col  offset={6} >
							<h3>Properties</h3>
						</Col>
					</Row>
								{
									singleAe.properties.map((property,aePropId) => (

									<div
										key={property,aePropId} 
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
												placeholder={`${props.ae} ${aeIndex}  Property  ${aePropId} name`}
												value={property.name}
												onChange={props.handleChangeProperty(aeIndex,aePropId,props.ae)}/>
										</FormItem>
										<FormItem label="Description" {...props.formItemLayout}>
											<TextArea
												size="large"
												name="description"
												placeholder={`${props.ae} ${aeIndex}  Property  ${aePropId} description`}
												value={property.description}
												onChange={props.handleChangeProperty(aeIndex,aePropId,props.ae)}/>
										</FormItem>
										<FormItem label="Endpoint" {...props.formItemLayout}>	
											<Input
												size="large"
												name="endpoint"
												placeholder={`${props.ae} ${aeIndex}  Property  ${aePropId} name`}
												value={property.endpoint}
												onChange={props.handleChangeProperty(aeIndex,aePropId,props.ae)}/>
										</FormItem>		
										<FormItem label="Type" {...props.formItemLayout}>
											<Radio.Group value={property.type} name="type" onChange={props.handleChangeProperty(aeIndex,aePropId,props.ae)}>
												<Radio.Button value="string">String</Radio.Button>
												<Radio.Button value="int">Int</Radio.Button>
												<Radio.Button value="double">Double</Radio.Button>
												<Radio.Button value="discrete">Discrete</Radio.Button>
											</Radio.Group>
										</FormItem>
											{ props.getInputsAccordingToType(property,aeIndex,aePropId,props.formItemLayout,props.formItemLayoutWithOutLabel,props.ae) }

										<FormItem {...props.formItemLayoutWithOutLabel}>
											<Button onClick={props.handleRemoveProperty(aeIndex,aePropId,props.ae)} size="large"  style={{width:'100%' }}>
												<Icon type="minus" />Remove property
											</Button>
										</FormItem>

									</div>
		
									))
								}
		
								<FormItem {...props.formItemLayoutWithOutLabel}>
									<Button type="dashed" onClick={props.handleAddProperty(aeIndex,props.ae)} size="large"  style={{width:'100%' }}>
										<Icon type="plus" />Add property
									</Button>
								</FormItem>
								<FormItem {...props.formItemLayoutWithOutLabel}>
									<Button onClick={props.handleRemoveAE(aeIndex,props.ae)}  size="large" style={{width:'100%' }}>
										<Icon type="minus" />Remove {props.ae}
									</Button>
								</FormItem>
							</div>
							</div>
						))
					}
					<FormItem {...props.formItemLayoutWithOutLabel} >
						<Button type="dashed" size="large" height={200} onClick={props.handleAddAE(props.ae)}  style={{width:'100%' }}>
						<Icon type="plus" />Add {props.ae}
						</Button>
					</FormItem>
				</div>
	);
};

export default ActionEvent;

