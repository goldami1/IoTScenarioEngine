import React from "react";
import ProductListItem from "./productListItem";
import { List } from 'antd';

const ProductList = props => {
	const productItems = props.products.map(product => {
		return (
			<div 
				key={product.id}
				style={{
					width:"200px", 
					float:"left",
					marginLeft:"10px",
					marginTop:"10px"
			}}>
				<ProductListItem
					onProductClick={props.onProductClick}
					key={product.id}
					product={product}
				/>
			</div>
		);
	});

	return  (
		<List style = {{overflow:"hidden"}} >
			{productItems}
		</List>);
};

export default ProductList;
