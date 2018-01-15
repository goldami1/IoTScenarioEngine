import React from "react";
import DeviceListItem from "./device_list_item";

const DeviceList = props => {
	const deviceItems = props.devices.map(device => {
		return (
			<DeviceListItem
				onDeviceDelete={props.onDeviceDelete}
				key={device.id}
				device={device}
			/>
		);
	});

	return <ul className="list-inline">{deviceItems}</ul>;
};

export default DeviceList;
