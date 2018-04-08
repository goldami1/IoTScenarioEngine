import React from "react";
import ProductListItem from "./productListItem";

const ProductList = props => {
	const productItems = props.products.map(product => {
		return (
			<ProductListItem
				onClick={props.onClick}
				key={props.product.id}
				product={props.product}
			/>
		);
	});

	return <ul>{productItems}</ul>;
};

export default ProductList;
