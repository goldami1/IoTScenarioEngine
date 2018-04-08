import axios from "axios";

import { isEmpty } from "lodash";
import setAuthorizationToken from "../utilities/set_auth_token";
import {setMessage} from "./appActions";
import { 
	SET_LOGIN_REQUEST_SENT,
	SET_LOGIN_REQUEST_DONE,
	SET_CURRENT_USER 
} from "./types";
import { REST_LOGIN } from "./restapi";

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

export function login(data,history) {
	return dispatch => {
		dispatch(loginRequestSent());
		return axios.post( REST_LOGIN , data).then(
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
							type: res.data.isCustomer ?'enduser':'vendor'
						};
						
						localStorage.setItem("user", JSON.stringify(user));
						setAuthorizationToken(user);
						if (user.type =="enduser") {
							history.push("/scenarios");
						}else{
							history.push('/products');
						}
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
