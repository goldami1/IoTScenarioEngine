import axios from "axios";
import { postToApi } from './appActions';
import {SIGNUP_USER,USER_CREATED} from './types';
import {api, addCustomer, addVendor} from './restapi'
const URL_ROOT = "http://localhost:8080/Scenario_Engine/webapi/customer";
const LOGIN_URL = URL_ROOT + "customer";
const SIGN_URL = URL_ROOT + "/new";
const POST_RET = "https://httpbin.org/post";

function userCreated(user) {
	return {
		type: USER_CREATED,
		payload: user
	};
}

export function userSignupRequest(userData) {
	return (dispatch, getState) => {
		dispatch(postToApi(SIGNUP_USER,api(userData.isCustomer? addCustomer : addVendor),userCreated,userData))
	};
}
