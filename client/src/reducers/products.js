import { RECEIVE_PRODUCTS } from "../actions/types";

import isEmpty from "lodash/isEmpty";

const initialState = {
	products: [],
};

export default (state = initialState, action = {}) => {
	switch (action.type) {
		case RECEIVE_PRODUCTS:
			return Object.assign({}, state, {
				products: action.products
			});
		default:
			return state;
	}
};
