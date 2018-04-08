import React from "react";

const ProductListItem = ({ product, onClick }) => {
	return (
		<h5>{product.name}</h5>
	);
};

export default ProductListItem;
