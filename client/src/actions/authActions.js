import axios from "axios";

import { isEmpty } from "lodash";
import setAuthorizationToken from "../utilities/set_auth_token";
import {setMessage} from "./appActions";
import {

	SET_LOGIN_REQUEST_SENT,
	SET_LOGIN_REQUEST_DONE,
	SET_CURRENT_USER 
} from "./types";

import { 
	endpoints ,
	api
} from "./restapi";

export function setCurrentUser(user) {

	return {
		type: SET_CURRENT_USER,
		user
	};
}

export function loginRequestSent() {
	return {
		type: SET_LOGIN_REQUEST_SENT,
	};
}

export function loginRequestReceived() {
	return {
		type: SET_LOGIN_REQUEST_DONE,
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

export function loginRequest(data,history) {
	return dispatch => {
		dispatch(loginRequestSent());
		return axios.post( api(endpoints.login) , data).then(
				res => {
					var user;
					dispatch(loginRequestReceived()); 
					try {
						console.log(res.data);
						user = {
							name: res.data.name,
							username: res.data.userName,
							password: res.data.password,
							id: res.data.id,
							type: (res.data.isCustomer  || res.data.customer) ?'enduser':'vendor'
						};
						if (user.type =="enduser") {
							history.push("/scenarios");
						}else{
							history.push('/products');
						}
						localStorage.setItem("user", JSON.stringify(user));
						setAuthorizationToken(user);
						dispatch(setCurrentUser(user));
						
					} catch (e) {console.error(`LOGIN_ERROR_RES: ${e}`);}

				},
			err => {
				dispatch(loginRequestReceived()); 
				try {
					dispatch(setMessage({content: err.response.data.description, type:"error"}));
					console.log(err.response.data.description);
				} catch (e) {
					console.error(`LOGIN_ERROR_ERR: ${e}`);
				}
				
			}
		);
	};
}
