import axios from "axios";
import { 
	ADD_PRODUCT, 
	PRODUCT_ERROR_OCCURED ,
	FETCH_PRODUCTS,RECEIVE_PRODUCTS
} from "./types";

import { REST_VENDORS ,REST_PRODUCTS} from "./restapi";

import { setMessage, setUnknownError } from "./appActions";

export function errorOccured(error) {
	return {
		type: PRODUCT_ERROR_OCCURED,
		error
	};
}

export function receiveProducts(products) {
	return {
		type: RECEIVE_PRODUCTS,
		products
	};
}

export function fetchProducts() {
	console.log("fetch product");
	console.log(product);
	return (dispatch, getState) => {
		const { auth } = getState();
		return axios.get(`${REST_PRODUCTS}/${auth.id}`).then(
			res => {

			},
			err => {

			}
		);
	};
}


export function addProduct(product) {
	console.log("add product");
	console.log(product);
	return (dispatch, getState) => {
		const { auth } = getState();
		product.vendorName = auth.name;
		return axios.post(`${REST_VENDORS}/${auth.id}`, product).then(
			res => {
				console.log("res");
				try {
					console.log(res.data);
				} catch (e) {
					console.error(`ADD_PRODUCT_RESPONSE_ERROR: ${e}`, res.data);
				}
			},
			err => {
				try {
					const error = err.response.data.error;
					if (error) {
						dispatch(errorOccured(err.response.data.error));
					} else {
						dispatch(setUnknownError());
					}
				} catch (e) {
					console.log("err catch");
					dispatch(setUnknownError());
					dispatch(setMessage({ content: "UNKOWN ERROR OCCURED" }));
					setTimeout(() => {
						dispatch(setMessage({}));
					}, 50);
					console.error(`ADD_PRODUCT_ERROR: ${e}`, err.response);
				}
			}
		);
	};
}
