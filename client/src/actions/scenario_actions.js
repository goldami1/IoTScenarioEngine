import axios from "axios";
import {
	RECEIVE_SCENARIOS,
	SCENARIO_ERROR_OCCURED
	// REQUEST_SCENARIOS,
	// DELETE_SCENARIO ,
	// ADD_SCENARIO
} from "./types";

const test_url = "http://demo6475105.mockable.io/devices";
const error_url = "http://demo6475105.mockable.io/error";
const delete_ulr = "https://httpbin.org/delete";
const scenario_url = "http://demo6475105.mockable.io/scenarios";
const vendors_url = "http://demo6475105.mockable.io/vendors";

export function errorOccured(error) {
	return {
		type: SCENARIO_ERROR_OCCURED,
		error
	};
}

export function receiveScenarios(scenarios) {
	return {
		type: RECEIVE_SCENARIOS,
		scenarios
	};
}

export function fetchScenarios() {
	return dispatch => {
		return axios.get(scenario_url).then(
			res => {
				dispatch(receiveScenarios(res.data));
				console.log(res.data);
			},
			err => dispatch(errorOccured(err.response.data.error))
		);
	};
}

export function deleteScenario(scenario) {
	return (dispatch, getState) => {
		const { auth } = getState();
		//todo build address
		const user = auth.user.id;
		return axios.delete(delete_ulr).then(
			res => {
				dispatch(fetchScenarios(scenario.customer_id));
			},
			err => dispatch(errorOccured(err.response.data.error))
		);
	};
}

export function addScenario(scenario) {
	return (dispatch, getState) => {
		const { auth } = getState();
		console.log(auth);
		return axios.post(delete_ulr).then(
			res => {
				dispatch(fetchScenarios(scenario.customer_id));
			},
			err => dispatch(errorOccured(err.response.data.error))
		);
	};
}
