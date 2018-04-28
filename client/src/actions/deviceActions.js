import isEmpty from "lodash";
import axios from "axios";
import {
	FETCH_DEVICES,
	FETCH_VENDORS,
	SELECT_DEVICE,
	DELETE_DEVICE,
	ADD_DEVICE,
	RECEIVE_DEVICES,
	DEVICE_ERROR_OCCURED,
	RECEIVE_VENDORS,
	RECEIVE_PRODUCTS,
	FETCH_PRODUCTS
} from "./types";
import {
	endpoints,
	api,


	REST_DEVICES,
	REST_VENDORS,
	REST_PRODUCTS
} from "./restapi";
import  {
	setMessage,
	getFromApi,
	postToApi
} from "./appActions";


export function errorOccured() {
	return {
		type: DEVICE_ERROR_OCCURED
	};
}

export function addDeviceRequest() {
	return {
		type: ADD_DEVICE
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
		// dispatch(getFromApi(FETCH_DEVICES,`${REST_DEVICES}/${auth.id}`,receiveDevices));
		 dispatch(getFromApi(FETCH_DEVICES,`${api(endpoints.device)}/${auth.id}`,{res:receiveDevices}));
	};
}

export function fetchProducts(vendorId) {
	return (dispatch, getState) => {
		// return dispatch(getFromApi(FETCH_PRODUCTS,`${REST_PRODUCTS}/${vendorId}?isFull=false`,receiveProducts));
		return dispatch(getFromApi(FETCH_PRODUCTS,`${api(endpoints.product)}/${vendorId}?isFull=true`,{res:receiveProducts}));
	};
}


export function addDevice(device) {
	return (dispatch, getState) => {
		const { auth } = getState();
		dispatch(postToApi(ADD_DEVICE,`${api(endpoints.device)}/${auth.id}`,{res:receiveDevices,pre:addDeviceRequest,err:errorOccured},device))
	};
}


export function fetchVendors() {
	return (dispatch, getState) => {
		// dispatch(getFromApi(FETCH_VENDORS,`${REST_VENDORS}`,receiveVendors));
		dispatch(getFromApi(FETCH_VENDORS,api(endpoints.vendors),{res:receiveVendors}));
	};
}
