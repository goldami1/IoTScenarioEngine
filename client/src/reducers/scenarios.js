import {
	RECEIVE_SCENARIOS,
	SCENARIO_ERROR_OCCURED,
	SCENARIO_ADD_REQUEST
	// REQUEST_SCENARIOS,
	// DELETE_SCENARIO ,
	// ADD_SCENARIO
} from "../actions/types";

import isEmpty from "lodash/isEmpty";

const initialState = {
	scenarios: [],
	error: false,
	isLoading:false
	
};

export default (state = initialState, action = {}) => {
	switch (action.type) {
		case RECEIVE_SCENARIOS:
			return Object.assign({}, state, {
				scenarios: action.scenarios,
				isLoading : false
			});
		case SCENARIO_ERROR_OCCURED:
			return Object.assign({}, state, {
				isLoading : false,
				error:true
			});
		case SCENARIO_ADD_REQUEST:
			return Object.assign({}, state, {
				isLoading : true,
				error:false
			});
		default:
			return state;
	}
};
