import React from "react";
import DeviceListItem from "./deviceListItem";
import { List } from 'antd';

const DeviceList = props => {
	const deviceItems = props.devices.map(device => {
		return (
			<div style={{
				width:"200px", 
				float:"left",
				marginLeft:"10px",
				marginTop:"10px"
			}}>
				<DeviceListItem
					onDeviceSelect={props.onDeviceSelect}
					key={device.id}
					device={device}
				/>
			</div>
		);
	});

	const data = props.devices;

	return 	(	
		<div style = {{overflow:"hidden"}} 	>
	
			{deviceItems} {deviceItems}{deviceItems} {deviceItems}{deviceItems}{deviceItems}{deviceItems}{deviceItems} 
		</div>
	);

};

export default DeviceList;
