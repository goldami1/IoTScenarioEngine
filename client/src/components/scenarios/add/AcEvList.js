import React from "react";
import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";



// using some little inline style helpers to make the app look okay
const grid = 9;
const getItemStyle = (isDragging, draggableStyle) => ({
	// some basic styles to make the items look a bit nicer
	userSelect: "none",
	padding: grid * 2,
	margin: `0 0 ${grid}px 0`,

	// change background colour if dragging
	background: isDragging ? "lightgreen" : "yellow",

	// styles we need to apply on draggables
	...draggableStyle
});
const getListStyle = isDraggingOver => ({
	background: isDraggingOver ? "lightblue" : "white",
	float: "left",
	padding: 10,
	width: 193
});


const AcEvList = props => {
	
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
									<div
										ref={provided.innerRef}
										{...provided.draggableProps}
										{...provided.dragHandleProps}
										style={getItemStyle(
											snapshot.isDragging,
											provided.draggableProps.style
										)}
									>
										{item.content}
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
