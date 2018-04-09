import React from "react";
import ProductListItem from "./productListItem";
import { List } from 'antd';

const ProductList = props => {
	const productItems = props.products.map(product => {
		return (
			<ProductListItem
				onProductClick={props.onProductClick}
				key={product.id}
				product={product}
			/>
		);
	});

	return  (
		<List style = {{overflow:"hidden"}}
			grid={{ gutter: 16, xs: 1, sm: 2, md: 4, lg: 4, xl: 6, xxl: 3 }}>
			{productItems}
		</List>);
};

export default ProductList;
