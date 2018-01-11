import isEmpty from 'lodash';
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


export function fetchDevices() {
	return (dispatch,getState) => {
		const {auth} = getState();
		return axios.get(device_url+auth.user.id).then(
			res => {
		  		try { dispatch(receiveDevices(res.data)) }	  			
		  		catch(e) { console.error(`FETCH_DEVICES_RESPNSE_ERROR: ${e}`,res.data); } 				
			},
		  	err => {
		  		try { dispatch(errorOccured(err.response.data)) }	  			
		  		catch(e) { 
		  			console.error(`FETCH_DEVICES_ERROR: ${e}`,err,err.response); 
		  		} 
		  	}
		);
	}
}





export function deleteDevice(device) {
	return dispatch => {
		return axios.delete(delete_ulr).then(
			res => {
				try{dispatch(deviceDeleted(res.data));dispatch(fetchDevices(device.customer_id));}
				catch(e){ console.error(`DELETE_DEVICE_RESPONSE_ERROR: ${e}`,res.data); }
			},
		  	err => {
		  		try {dispatch(errorOccured(err.response.data.error))}	  			
		  		catch(e) { console.error(`DELETE_DEVICE_ERROR: ${e}`,err,err.response); }
		  	}
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
				try {dispatch(fetchDevices(device.customer_id)) }	  			
		  		catch(e) { console.error(`ADD_DEVICE_RESPONSE_ERROR: ${e}`,res.data); }						
			},
		  	err => {
		  		try {dispatch(errorOccured(err.response.data.error))}	  			
		  		catch(e) { console.error(`ADD_DEVICE_ERROR: ${e}`,err.response); }
		  	}
		);
	}
}

export function fetchProducts(vendor) {
	return dispatch => {

		return axios.get(products_url+vendor).then(
			res => {
				try { dispatch(receiveProducts(res.data)) }	  			
		  		catch(e) { console.error(`FETCH_PRODUCTS_RESPONSE_ERROR: ${e}`,res.data); }
				
			},
		  	err => {
		  		try { dispatch(errorOccured(err.response.data.error))}	  			
		  		catch(e) { console.error(`FETCH_PRODUCTS_ERROR: ${e}`,err.response); } 
		  	}
		);
	}
}


export function fetchVendors() {
	return dispatch => {
		return axios.get(vendors_url).then(
			res => {
				try { dispatch(receiveVendors(res.data));}	  			
		  		catch(e) { console.error(`FETCH_VENDORS_RESPONSE_ERROR: ${e}`); } 	
			},
		  	err => {
		  		try { dispatch(errorOccured(err.response.data)) }	  			
		  		catch(e) { console.error(`FETCH_VENDORS_ERROR: ${e}`); } 
		  	}
		);
	}
}
