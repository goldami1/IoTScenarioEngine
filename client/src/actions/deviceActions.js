import isEmpty from "lodash";
import axios from "axios";
import {
	SELECT_DEVICE,
	DELETE_DEVICE,
	ADD_DEVICE,
	RECEIVE_DEVICES,
	DEVICE_ERROR_OCCURED,
	RECEIVE_VENDORS,
	RECEIVE_PRODUCTS
} from "./types";
import {
	REST_DEVICES,
	REST_VENDORS,
	REST_PRODUCTS
} from "./restapi";
import  {setMessage} from "./appActions";

export function errorOccured(error) {
	return {
		type: DEVICE_ERROR_OCCURED,
		error
	};
}

export function receiveDevices(devices) {
	return {
		type: RECEIVE_DEVICES,
		devices
	};
}

export function deviceDeleted(device) {
	return {
		type: DELETE_DEVICE
	};
}

export function receiveProducts(products) {
	return {
		type: RECEIVE_PRODUCTS,
		products
	};
}

export function receiveVendors(vendors) {
	return {
		type: RECEIVE_VENDORS,
		vendors
	};
}

export function fetchDevices() {
	return (dispatch, getState) => {
		const { auth } = getState();
		
		return axios.get(`${REST_DEVICES}/${auth.id}`).then(
			res => {
				dispatch(receiveDevices(res.data));
			},
			err => {
				try {
					dispatch(setMessage({ content: err.response.data ,type: "error" },50));
				} catch (e) {
					console.error(`FETCH_PRODUCT_RESPONSE_ERROR: ${e}`, err.response.data);
				}			
			}
		);
	};
}


export function addDevice(device) {
	console.log("add device");
	console.log(device);
	return (dispatch, getState) => {
		const { auth } = getState();
		return axios.post(`${REST_DEVICES}/${auth.id}`, device).then(
			res => {
				try {
					dispatch(fetchDevices(device.customer_id));
				} catch (e) {
					console.error(`ADD_DEVICE_RESPONSE_ERROR: ${e}`, res.data);
				}
			},
			err => {
				try {
					dispatch(errorOccured(err.response.data.error));
				} catch (e) {
					console.error(`ADD_DEVICE_ERROR: ${e}`, err.response);
				}
			}
		);
	};
}

export function fetchProducts(vendor) {
	return dispatch => {
		return axios.get(`${REST_PRODUCTS}/${vendor}`).then(
			res => {
				try {
					dispatch(receiveProducts(res.data));
				} catch (e) {
					console.error(
						`FETCH_PRODUCTS_RESPONSE_ERROR: ${e}`,
						res.data
					);
				}
			},
			err => {
				try {
					dispatch(errorOccured(err.response.data.error));
				} catch (e) {
					console.error(`FETCH_PRODUCTS_ERROR: ${e}`, err.response);
				}
			}
		);
	};
}

export function fetchVendors() {
	return dispatch => {
		return axios.get(`${REST_VENDORS}`).then(
			res => {
				try {
					dispatch(receiveVendors(res.data));
				} catch (e) {
					console.error(`FETCH_VENDORS_RESPONSE_ERROR: ${e}`);
				}
			},
			err => {
				try {
					dispatch(errorOccured(err.response.data));
				} catch (e) {
					console.error(`FETCH_VENDORS_ERROR: ${e}`);
				}
			}
		);
	};
}
