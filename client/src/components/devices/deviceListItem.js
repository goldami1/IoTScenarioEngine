import React from "react";
import { List,Card } from 'antd';
const { Meta } = Card;

const DeviceListItem = ({ device, onDeviceSelect }) => {
	return (
		<List.Item  >
			<Card
				onClick ={ () => { onDeviceSelect(device) } }
				hoverable
				cover={<img alt={device.name} src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png" />}
				// cover={<img alt="example" src={device.picUrl} />}
			>
				<Meta
					title={device.name}
					description={device.description}
				/>
			</Card>
		</List.Item>
	);
};

export default DeviceListItem;
