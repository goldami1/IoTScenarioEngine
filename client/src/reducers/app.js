import { SET_MESSAGE } from "../actions/types";
import isEmpty from "lodash/isEmpty";

const initialState = {
	content: "",
	type: "warning"
};

export default (state = initialState, action = {}) => {
	switch (action.type) {
		case SET_MESSAGE:
			return Object.assign({}, state, {
				content: action.payload.content,
				type: action.payload.type
			});
		default:
			return state;
	}
};
