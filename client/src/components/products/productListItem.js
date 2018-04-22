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
				<div>
					<strong>Product :</strong> {`${props.product.name}`}
				</div>
				<div>
					<strong>Description :</strong> {`${props.product.description}`}
				</div>
			</Card>
		</List.Item>
	);
};

export default ProductListItem;
