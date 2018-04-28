import React from "react";
import DeviceListItem from "./deviceListItem";
import { List } from 'antd';
import {isEmpty} from 'lodash';

const DeviceList = props => {
	const deviceItems = props.devices.filter(device => !isEmpty(device.protoDevice)).map(device => {
		return (
			<div 
				key={device.id}
				style={{
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


	return 	(	
		<div style = {{overflow:"hidden"}} 	>
			{deviceItems}
		</div>
	);

};

export default DeviceList;
