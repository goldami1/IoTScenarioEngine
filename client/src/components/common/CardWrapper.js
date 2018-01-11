import React from 'react';
import {Card ,Col} from 'antd';

const CardWraper = (props) => {
	const style = {
		style:{
			margin: '40px',
			padding: 20,
			background: '#fff'
		}
	}

	const small = {
		xs: { span: 24},//'480px',
		sm: { span: 18, offset:3},//'768px',
		md: { span: 14, offset:5},//'992px',
		lg: { span: 12, offset:6},//'992px',
		xl: { span: 8, offset:8},//'992px',
	};

	const colStyle = props.size=="small" ? small : {}
	return (
		<Col {...colStyle}>
			<Card {...style}>
				{props.children}
			</Card>
		</Col>
	);
}

export default CardWraper;