import { 
	RECEIVE_SCENARIOS,
	SCENARIO_ERROR_OCCURED
	// REQUEST_SCENARIOS,
	// DELETE_SCENARIO ,
	// ADD_SCENARIO
} from '../actions/types';

import isEmpty from 'lodash/isEmpty';


const initialState = {
	scenarios:[],
	error:''
};


export default (state = initialState, action = {}) => {
	switch(action.type) {
		case RECEIVE_SCENARIOS:
			return Object.assign({}, state, {
				scenarios: action.scenarios
			})
		case SCENARIO_ERROR_OCCURED:
			return Object.assign({}, state, {
				error: action.error
			})
		default: 
			return state;
	}
}
