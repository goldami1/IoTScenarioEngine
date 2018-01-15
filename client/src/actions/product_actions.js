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

const test_url = "http://demo6475105.mockable.io/devices";
const error_url = "http://demo6475105.mockable.io/error";

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

export function fetchDevices(user) {
	return dispatch => {
		return axios
			.get(test_url)
			.then(
				res => dispatch(receiveDevices(res.data)),
				err => dispatch(errorOccured(err.response.data.error))
			);
	};
}
