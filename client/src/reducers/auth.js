import { 
	SET_CURRENT_USER ,
	SET_LOGIN_REQUEST_SENT,
	SET_LOGIN_REQUEST_DONE, 
	USER_CREATED, 
	USER_CREATION_FAILED,
    USER_CREATION_REQUEST
} from "../actions/types";
import isEmpty from "lodash/isEmpty";

const initialState = {
	isAuthenticated: false,
	id:'',
	username:'',
	password:'',
	name:'',
	type:'',
	isLoading:false,
	userCreated:false,
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
		case USER_CREATED:
			return {
				userCreated:true,
				isLoading:false
			};
			break;
		case USER_CREATION_FAILED:
			return {
				userCreated:false,
				isLoading:false,
			};
			break;
		case USER_CREATION_REQUEST:
			return {
				userCreated:false,
				isLoading:true,
			};
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
