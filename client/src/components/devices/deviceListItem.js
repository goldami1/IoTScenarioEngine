import React from "react";
import { List,Card } from 'antd';
const { Meta } = Card;

const DeviceListItem = ({ device, onDeviceSelect }) => {
	return (

			<Card
				style= {{borderRadius:"5px", overflow:"hidden"}}
				onClick ={ () => { onDeviceSelect(device) } }
				hoverable
				cover={<img alt={device.name} src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png" />}
				// cover={<img alt="example" src={device.picUrl} />}
			>
				<Meta
					// title={device.name}
					// description={device.description}
					title="Fuck me title"
					description="{device.description}"
				/>
			</Card>
	);
};

export default DeviceListItem;
