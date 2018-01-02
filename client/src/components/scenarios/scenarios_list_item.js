import React from "react";



function valueType(type){
	if ( typeof(type) ==="boolean") {
		if (type) {
			return "true";
		}
		else{
			return "false";
		}
	}else{
		return type;
	}			
}
const ScenarioListItem = ({ scenario, onScenarioDelete }) => {



	return (


		<div className="card p-4">
			<div className="card-block">
				<div>
				<h2 className="card-title   m-4">{scenario.name}</h2>
				</div>
				
				{/*<p class="card-text">With supporting text below as a natural lead-in to additional content.</p>*/}
				<div className ="row">
					<div className="col">
						
						<div className=" col-12 m-auto">
						<ul className="list-group">
							<li className="list-group-item ">
								<h3 className="m-2">Event</h3>
							</li>
							<li className="list-group-item">								
								<span className="text-muted d-inline-block w-25">Device name</span>
								{scenario.event.device.name} 
							</li>
							<li className="list-group-item">								
								<span className="text-muted d-inline-block w-25">Action</span>
								{scenario.event.device.event.name} 
							</li>
							<li className="list-group-item">								
								<span className="text-muted d-inline-block w-25">Value</span>
								{scenario.event.device.event.value && "true"} 
							</li>
						</ul>
						</div>
					</div>
					<div className="col">
					<div className=" col-12  m-auto">
						<ul className="list-group">
							<li className="list-group-item ">
								<h3 className="m-2">Action</h3>
							</li>
							<li className="list-group-item">								
								<span className="text-muted d-inline-block w-25">Device name</span>
								{scenario.action.device.name} 
							</li>
							<li className="list-group-item">								
								<span className="text-muted d-inline-block w-25">Action</span>
								{scenario.action.device.action.name} 
							</li>
							<li className="list-group-item">								
								<span className="text-muted d-inline-block w-25">Value</span>
								{ valueType(scenario.action.device.action.value) } 
									
								
							</li>
						</ul>
					</div>
					</div>
				</div>
				<button className="btn btn-danger  m-3 " onClick={() => onScenarioDelete(scenario)}>Delete</button>
			</div>
		</div>
	);
};

export default ScenarioListItem;
