import React from "react";
import { List,Card } from 'antd';
const { Meta } = Card;

const DeviceListItem = ({ device, onDeviceSelect }) => {
	return (

			<Card
				style= {{borderRadius:"5px", overflow:"hidden"}}
				onClick ={ () => { onDeviceSelect(device) } }
				hoverable
				cover={<img alt={device.protoDevice.name} src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png" />}
				// cover={<img alt="example" src={device.picUrl} />}
			>
				<div>
					<strong>Product :</strong> {`${device.protoDevice.vendorName}/${device.protoDevice.name}`}
				</div>
				<div>
					<strong>Description :</strong> {`${device.protoDevice.description}`}
				</div>
				<div>
					<strong>Serial :</strong> {`${device.serial_number}`}
				</div>
			</Card>
	);
};

export default DeviceListItem;
