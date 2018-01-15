
import React from 'react';
import {List, Tabs, Upload, Icon, message,Button, Modal, Form, Input, Radio ,Row, Col, Tag, Tooltip,Collapse} from 'antd';
const {Panel} = Collapse.Panel;
const FormItem = Form.Item;
const { TextArea } = Input;
const TabPane = Tabs.TabPane;
const ActionEvent = props => {



				

	return (

				<div> 
					<Tabs defaultActiveKey="1"  animated={false} tabPosition='left' size="small">		
						
					{
		       
									props.aeCollection.map((singleAe, aeIndex) => (

										<TabPane 
											key={aeIndex} tab={
												<div>
													{singleAe.name?singleAe.name:`New ${props.ae}`}
													<Icon className="dynamic-delete-button" type="minus-circle-o" onClick={props.handleRemoveAE(aeIndex,props.ae)} style={{marginLeft:"20px"}}/>
												</div>
											
											} 	
										>
													<Tabs defaultActiveKey="1" tabPosition='top' size="small" type="line" tabBarExtraContent={(<Button onClick={props.handleAddProperty(aeIndex,props.ae)} ><Icon type="plus" />Add property</Button>)}	>






																			<TabPane tab="Base info" key={aeIndex}>
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
																					<FormItem label="Endpoint" {...props.formItemLayout}>	
																					<Input
																						size="large"
																						name="endpoint"
																						placeholder={`${props.ae} ${aeIndex}  endpoint` }
																						value={singleAe.endpoint}
																						onChange={props.handleChangeAE(aeIndex,props.ae)}/>
																					</FormItem>
																			</TabPane>
																			<TabPane tab="Properties">
																					<Tabs
																						defaultActiveKey="1"
																						tabPosition='left'
																						>			

																					
																								{
																									singleAe.properties.map((property,aePropId) => (


																									<TabPane 
																										tab={
																											<div>
																												{property.name?property.name:`New property`}
																												<Icon
																													className="dynamic-delete-button"
																													type="minus-circle-o"
																													onClick={props.handleRemoveProperty(aeIndex,aePropId,props.ae)}
																													style={{marginLeft:"20px"}}
																												/>
																											</div>
																										
																										} 
																										key={aePropId}
																									>
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

																										<FormItem label="Type" {...props.formItemLayout}>
																											<Radio.Group value={property.type} name="type" onChange={props.handleChangeProperty(aeIndex,aePropId,props.ae)}>
																												<Radio.Button value="string">String</Radio.Button>
																												<Radio.Button value="int">Int</Radio.Button>
																												<Radio.Button value="double">Double</Radio.Button>
																												<Radio.Button value="discrete">Discrete</Radio.Button>
																											</Radio.Group>
																										</FormItem>
																											{ props.getInputsAccordingToType(property,aeIndex,aePropId,props.formItemLayout,props.formItemLayoutWithOutLabel,props.ae) }


																						</TabPane>

																						))
																						}
																						</Tabs>
																			</TabPane>
													</Tabs>

										</TabPane>
										 
									))
									
								}
					</Tabs>
				</div>
	);
};

export default ActionEvent;

