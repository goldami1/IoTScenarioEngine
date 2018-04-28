import {
	SELECT_DEVICE,
	DELETE_DEVICE,
	ADD_DEVICE,
	RECEIVE_DEVICES,
	RECEIVE_VENDORS,
	RECEIVE_PRODUCTS,
	DEVICE_ERROR_OCCURED
} from "../actions/types";

import isEmpty from "lodash/isEmpty";

const initialState = {
	devices: [],
	vendors: [],
	products: [],
	selectedDevice: {},
	isLoading:false,
	error: false
};

export default (state = initialState, action = {}) => {
	switch (action.type) {
		case SELECT_DEVICE:
			return state;
		case DELETE_DEVICE:
			return state;
		case ADD_DEVICE:
			return Object.assign({}, state, {
				isLoading:true,
				error:false
			});
		case RECEIVE_DEVICES:
			return Object.assign({}, state, {
				devices: action.devices,
				isLoading:false
			});
		case RECEIVE_VENDORS:
			return Object.assign({}, state, {
				vendors: action.vendors
			});
		case RECEIVE_PRODUCTS:
			return Object.assign({}, state, {
				products: action.products
			});
		case DEVICE_ERROR_OCCURED:
			return Object.assign({}, state, {
				error: true,
				isLoading:false
			});
		default:
			return state;
	}
};
