import React from 'react';
import {Card ,Col} from 'antd';

const CardWraper = (props) => {
	const style = {
		margin: '40px',
		padding: 20,
		background: '#fff',
	}

	return (
		<Col {...props.style}>
			<Card style={style}>
				{props.children}
			</Card>
		</Col>
	);
}

export default CardWraper;