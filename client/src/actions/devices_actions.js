import axios from 'axios';
import { 
	SELECT_DEVICE, 			
	DELETE_DEVICE, 			
	ADD_DEVICE, 			
	RECEIVE_DEVICES, 		
	DEVICE_ERROR_OCCURED, 	
	RECEIVE_VENDORS,		
	RECEIVE_PRODUCTS
} from './types';



const test_url = 'http://demo6475105.mockable.io/devices';
const error_url  = 'http://demo6475105.mockable.io/error';
const delete_ulr  = 'https://httpbin.org/delete';

const vendors_url = 'http://demo6475105.mockable.io/vendors';

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
	type: DELETE_DEVICE,
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

export function fetchDevices(user) {
	return dispatch => {
		return axios.get(test_url).then(
			res => dispatch(receiveDevices(res.data)),
		  	err => dispatch(errorOccured(err.response.data.error))
		);
	}
}

export function deleteDevice(device) {
	return dispatch => {
		return axios.delete(delete_ulr).then(
			res => {
				dispatch(deviceDeleted(res.data));
				dispatch(fetchDevices(device.customer_id))
			},
		  	err => dispatch(errorOccured(err.response.data.error))
		);
	}
}


export function addDevice(device) {
	return (dispatch,getState) => {
		const {auth} = getState();
		console.log(auth);
		return axios.post(delete_ulr).then(
			res => {
				dispatch(fetchDevices(device.customer_id))
			},
		  	err => dispatch(errorOccured(err.response.data.error))
		);
	}
}

export function fetchProducts(vendor) {
	return dispatch => {
		return axios.get(vendors_url).then(
			res => dispatch(receiveProducts(res.data)),
		  	err => dispatch(errorOccured(err.response.data.error))
		);
	}
}


export function fetchVendors() {
	return dispatch => {
		return axios.get(vendors_url).then(
			res => dispatch(receiveVendors(res.data)),
		  	err => dispatch(errorOccured(err.response.data.error))
		);
	}
}
