import { 
	RECEIVE_PRODUCTS ,
	ADD_PRODUCT_ERROR,
	ADD_PRODUCT_REQUEST
} from "../actions/types";

import isEmpty from "lodash/isEmpty";

const initialState = {
	products: [],
	error:false,
	isLoading:false
};

export default (state = initialState, action = {}) => {
	switch (action.type) {
		case ADD_PRODUCT_ERROR:
			return Object.assign({}, state, {
				isLoading:false,
				error:true
			});
		case ADD_PRODUCT_REQUEST:
			return Object.assign({}, state, {
				error: false,
				isLoading:true
			});	
		case RECEIVE_PRODUCTS:
			return Object.assign({}, state, {
				products: action.products,
				error: false,
				isLoading:false
			});
		default:
			return state;
	}
};
