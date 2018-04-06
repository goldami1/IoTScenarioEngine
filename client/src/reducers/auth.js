import { SET_CURRENT_USER ,SET_LOGIN_REQUEST_SENT,SET_LOGIN_REQUEST_DONE} from "../actions/types";
import isEmpty from "lodash/isEmpty";

const initialState = {
	isAuthenticated: false,
	id:'',
	username:'',
	password:'',
	name:'',
	type:'',
	isLoading:false
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
			break;
		case SET_LOGIN_REQUEST_SENT:
			return {
				isLoading:true
			}
			break;
		case SET_LOGIN_REQUEST_DONE:
			return {
				isLoading:false
			}
			break;
		default:
			return state;
	}
};
