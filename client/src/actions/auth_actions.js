import axios from "axios";
import { isEmpty } from "lodash";
import setAuthorizationToken from "../utilities/set_auth_token";

import { SET_CURRENT_USER } from "./types";

const URL_ROOT = "http://localhost:9090/Scenario_Engine/webapi/customer";
const LOGIN_URL = URL_ROOT + "customer";
const SIGN_URL = URL_ROOT + "/add";
const POST_RET = "https://httpbin.org/post";

export function setCurrentUser(user) {
	return {
		type: SET_CURRENT_USER,
		user
	};
}

export function logout(history) {
	return dispatch => {
		localStorage.removeItem("user");
		setAuthorizationToken(false);
		dispatch(setCurrentUser({}));
		history.push('/login');
	};
}

export function login(data,history) {
	return dispatch => {
		return axios.post(URL_ROOT, data).then(res => {
			var user;
			try {
				user = {
					name: res.data.Name,
					username: res.data.UserName,
					password: res.data.Password,
					id: res.data.Id,
					type: res.data.IsCustomer ?'enduser':'vendor'
				};
				localStorage.setItem("user", JSON.stringify(user));
				setAuthorizationToken(user);
				if (user.type =="enduser") {
					history.push("/scenarios");
				}else{
					history.push('/products');
				}
				dispatch(setCurrentUser(user));
				
			} catch (e) {console.error(`LOGIN_ERROR: ${e}`);}

		});
	};
}
