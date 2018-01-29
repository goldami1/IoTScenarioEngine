import React from 'react';
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
	margin:'10px 0',
	width:'100%',
	background:isDragging ? '#fafafa':'white',
	boxShadow: isDragging  ? '0 4px 12px rgba(0, 0, 0, 0.15)': 'none',
	...draggableStyle
});
const getListStyle = isDraggingOver => ({
	background: isDraggingOver ? "#e6f7ff" : "white",
	padding:'5px'
});


const AcEvList = props => {




	// move to idividual component 
	const menu = (dropId,aeId) => {
		return (
			<Menu onClick={props.aeMenu.handle({dropId,aeId})}>
				<Menu.Item key="removeAe"><Icon type="delete" /><span style={{ marginLeft: 8 }}>Delete</span></Menu.Item>
				<Menu.Item key="editAe"><Icon type="edit" /><span style={{ marginLeft: 8 }}>Edit</span></Menu.Item>
			</Menu>
		)
	}

	const dropDown = (list,item) => {
		return (
			<Dropdown  placement='bottomCenter' overlay={menu(list,item)} trigger='click'> 
				<Icon style={{verticalAlign:'middle', marginLeft:8}} type="bars" />
			</Dropdown>
		)
	}

	const aeTitle = (list,item) => (
		<div style={{background:'inherit'}}>
			<Avatar 
				style={{verticalAlign: 'middle'}} 
				src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" 
			/>
			<span 
				style={{marginLeft: 16,verticalAlign: 'middle'}}
			>
				{item.content} {list}
			</span>
		</div>
	);

	return (
		<Droppable droppableId={`${props.id}`}>
			{(provided, snapshot) => (
				<div
					ref={provided.innerRef}
					style={Object.assign({}, props.style, getListStyle(snapshot.isDraggingOver))}
				>
				<Card bodyStyle={{padding:15}}>
					{props.lists[props.id].map((item, index) => (
						<Draggable
							key={item.id}
							draggableId={item.id}
							index={index}
						>
							{(provided, snapshot) => (
								<div>
									<div 
										ref={provided.innerRef}
										{...provided.draggableProps}
										{...provided.dragHandleProps}
										style={getItemStyle(
											snapshot.isDragging,
											provided.draggableProps.style
										)}
									>
									
										<Card 
											style={{background:'inherit'}}
											bodyStyle={{padding:15}} title={aeTitle(props.id,item)}
											extra=  { dropDown(props.id,index)}

								
										>
											<Card.Meta description="This is the description"/>
												
											
										</Card>
									</div>
									{provided.placeholder}
								</div>

							)}
						</Draggable>
					))}
					{provided.placeholder}
					</Card>
				</div>
			)}
		</Droppable>
	);
};

export default AcEvList;
