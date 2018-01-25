import React from "react";
import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";
import {
	Avatar,
	Card,
	Icon,
	Menu,
	Button,
	Dropdown,
} from "antd";


// using some little inline style helpers to make the app look okay

const getItemStyle = (isDragging, draggableStyle) => ({
	// some basic styles to make the items look a bit nicer
	userSelect: "none",
	margin:10,
	width:250,
	background: isDragging ? "lightgreen" : "yellow",
	...draggableStyle
});
const getListStyle = isDraggingOver => ({
	background: isDraggingOver ? "lightblue" : "white",
	float: "left",
});



const AcEvList = props => {

	const menu = (
		<Menu>
			<Menu.Item key="Delete" ><Icon type="delete" /><span style={{ marginLeft: 8 }}>Delete</span></Menu.Item>
			<Menu.Item key="Edit"><Icon type="edit" /><span style={{ marginLeft: 8 }}>Edit</span></Menu.Item>
		</Menu>
	);

	return (
		<Droppable droppableId={`${props.id}`}>
			{(provided, snapshot) => (
				<div
					ref={provided.innerRef}
					style={getListStyle(snapshot.isDraggingOver)}
				>
					{props.lists[props.id].map((item, index) => (
						<Draggable
							key={item.id}
							draggableId={item.id}
							index={index}
						>
							{(provided, snapshot) => (
								<div>
									<div style={{display:'inline'}}
										ref={provided.innerRef}
										{...provided.draggableProps}
										{...provided.dragHandleProps}
										style={getItemStyle(
											snapshot.isDragging,
											provided.draggableProps.style
										)}
									>
									
										<Card  title={item.content} bodyStyle={{padding:15}}
												extra=  {
													<Dropdown overlay={menu}>
														<Icon style={{marginLeft:8}} type="bars" />
													</Dropdown>
												} 	
										>
											<Card.Meta
												avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
												style={{padding:'0px'}}
												description="This is the description"
											/>
										</Card>
									</div>
									{provided.placeholder}
								</div>

							)}
						</Draggable>
					))}
					{provided.placeholder}
				</div>
			)}
		</Droppable>
	);
};

export default AcEvList;
