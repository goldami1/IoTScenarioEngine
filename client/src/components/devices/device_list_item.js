import React from "react";

const DeviceListItem = ({ device, onDeviceDelete }) => {
	return (
		<div className="card m-2 w-25 float-left" >
			<div className="card-body">
			<h5 className="card-title">{device.name}</h5>
			<h6 className="card-subtitle mb-5  text-muted">{device.vendor.name}</h6>
			{/*<p className="card-text">Descrition</p>*/}

			<div className="btn-group  w-100" role="group" aria-label="Basic example">
				<button type="button" className="btn btn-sm  w-75">Details</button>
				<button type="button" onClick={() => onDeviceDelete(device)} className="btn btn-sm  btn-danger w-25">Delete</button>
			</div>

			
			</div>
		</div>
	);
};

export default DeviceListItem;
