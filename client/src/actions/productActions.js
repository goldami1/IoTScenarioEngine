import axios from "axios";
import { 
	ADD_PRODUCT, 
	PRODUCT_ERROR_OCCURED ,
	FETCH_PRODUCTS,RECEIVE_PRODUCTS
} from "./types";

import { 
	api,
	endpoints,
	REST_VENDORS ,
	REST_PRODUCTS
} from "./restapi";

import { setMessage ,getFromApi,postToApi} from "./appActions";


export function receiveProducts(products) {
	return {
		type: RECEIVE_PRODUCTS,
		products
	};
}


export function fetchProducts() {
	return (dispatch, getState) => {
		const { auth } = getState();
		// dispatch(getFromApi(FETCH_PRODUCTS,`${REST_PRODUCTS}/${auth.id}`,receiveProducts));
		dispatch(getFromApi(FETCH_PRODUCTS,`${api(endpoints.product)}/${auth.id}`,{res:receiveProducts}));
	};
}

export function addProduct(product) {
	return (dispatch, getState) => {
		const { auth } = getState();
		product.vendorName = auth.name;
		dispatch(postToApi(ADD_PRODUCT,`${api(endpoints.product)}/${auth.id}`,{res:receiveProducts},product))
	};
}

