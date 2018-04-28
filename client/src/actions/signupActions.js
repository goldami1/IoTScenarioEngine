import axios from "axios";
import { postToApi } from './appActions';
import {
	SIGNUP_USER,
	USER_CREATED,
	USER_CREATION_FAILED,
	USER_CREATION_REQUEST
} from './types';
import {api, endpoints} from './restapi'


function userCreated(user) {
	return {
		type: USER_CREATED
	};
}

function userCreationRequest() {
	return {
		type: USER_CREATION_REQUEST
	};
}

function userCreationFailed() {
	return {
		type: USER_CREATION_FAILED
	};
}



export function userSignupRequest(userData) {
	return (dispatch, getState) => {
		dispatch(postToApi(
			SIGNUP_USER,
			api( endpoints[userData.isCustomer ? "addCustomer" : "addVendor"] ),
			{
				res:userCreated,
				err:userCreationFailed,
				pre:userCreationRequest
			},userData))
	};
}
