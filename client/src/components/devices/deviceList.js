import React from "react";
import DeviceListItem from "./deviceListItem";
import { List } from 'antd';

const DeviceList = props => {
	const deviceItems = props.devices.map(device => {
		return (
			<DeviceListItem
				onDeviceSelect={props.onDeviceSelect}
				key={device.id}
				device={device}
			/>
		);
	});

	return 	(	
		<List style = {{overflow:"hidden"}}
			grid={{ gutter: 16, xs: 1, sm: 2, md: 4, lg: 4, xl: 6, xxl: 3 }}>
			{deviceItems}
		</List>
	);

};

export default DeviceList;
