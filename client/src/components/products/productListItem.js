import React from "react";
import { List,Card } from 'antd';

const { Meta } = Card;

const ProductListItem = props => {
	return (
		<List.Item  >
			<Card
				onClick ={ () => { props.onProductClick(props.product) } }
				hoverable
				cover={<img alt={props.product.name} src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png" />}
				// cover={<img alt="example" src={product.picUrl} />}
			>
				<Meta
					title={props.product.name}
					description={props.product.description}
				/>
			</Card>
		</List.Item>
	);
};

export default ProductListItem;
