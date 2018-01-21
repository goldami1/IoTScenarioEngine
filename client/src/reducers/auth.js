import { SET_CURRENT_USER } from "../actions/types";
import isEmpty from "lodash/isEmpty";

const initialState = {
	isAuthenticated: false,
	id:'',
	username:'',
	password:'',
	name:'',
	type:''
};

export default (state = initialState, action = {}) => {
	switch (action.type) {
		case SET_CURRENT_USER:
			return {
				isAuthenticated: !isEmpty(action.user),
				id: action.user.id,
				username: action.user.username,
				password: action.user.password,
				name:action.user.name,
				type:action.user.type,
			};
		default:
			return state;
	}
};
