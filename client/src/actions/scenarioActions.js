import axios from "axios";
import {
	FETCH_SCENARIOS,
	RECEIVE_SCENARIOS,
	ADD_SCENARIO,
	SCENARIO_ERROR_OCCURED,
} from "./types";
import {
	getFromApi,
	postToApi
}from './appActions'
import {
	api,
	endpoints,
	scenarios,
} from './restapi';

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
	return (dispatch, getState) => {
		const { auth } = getState();
		// dispatch(getFromApi(FETCH_PRODUCTS,`${REST_PRODUCTS}/${auth.id}`,receiveProducts));
		dispatch(getFromApi(FETCH_SCENARIOS,`${api(endpoints.scenario)}/${auth.id}`,{res:receiveScenarios}));
	};
}


export function addScenario(scenario) {
	return (dispatch, getState) => {
		const { auth } = getState();
		dispatch(postToApi(ADD_SCENARIO,`${api(endpoints.scenario)}/${auth.id}`,{res:receiveScenarios},scenario));
	};
}


// export function deleteScenario(scenario) {
// 	return (dispatch, getState) => {
// 		const { auth } = getState();
// 		//todo build address
// 		const user = auth.user.id;
// 		return axios.delete(delete_ulr).then(
// 			res => {
// 				dispatch(fetchScenarios(scenario.customer_id));
// 			},
// 			err => dispatch(errorOccured(err.response.data.error))
// 		);
// 	};
// }
