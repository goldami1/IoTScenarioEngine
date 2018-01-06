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
const URL_ROOT = 'http://localhost:9090/Scenario_Engine/webapi/customer';
const vendors_url = 'http://localhost:9090/Scenario_Engine/webapi/customer/vendors';
const products_url = 'http://localhost:9090/Scenario_Engine/webapi/vendor/product/';
const device_url = 'http://localhost:9090/Scenario_Engine/webapi/customer/device/';

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
	console.log('add device');
	console.log(device);
	return (dispatch,getState) => {
		const {auth} = getState();
		console.log('add device');
		console.log(auth);
		return axios.post(device_url+auth.user.id,device).then(
			res => {
				dispatch(fetchDevices(device.customer_id))
			},
		  	err => dispatch(errorOccured(err.response.data.error))
		);
	}
}

export function fetchProducts(vendor) {
	return dispatch => {

		return axios.get(products_url+vendor).then(
			res => {
				console.log('products get');
				console.log(res.data);
				dispatch(receiveProducts(res.data))
			},
		  	err => {
				console.log('products err');
				console.log(err.response.data);		  		
		  		dispatch(errorOccured(err.response.data.error))}
		);
	}
}


export function fetchVendors() {
	return dispatch => {
		return axios.get(vendors_url).then(
			res => {
				console.log('vendors');
				console.log(res.data);
				dispatch(receiveVendors(res.data));
			}
				,
		  	err => {
		  		console.log('vendors err');
				console.log(err.response.data);
		  		dispatch(errorOccured(err.response.data.error))
		  	}
		);
	}
}
