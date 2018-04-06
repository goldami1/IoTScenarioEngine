import axios from "axios";

import { isEmpty } from "lodash";
import setAuthorizationToken from "../utilities/set_auth_token";
import {setMessage} from "./appActions";
import { SET_CURRENT_USER } from "./types";
import { REST_LOGIN } from "./restapi";

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
		return axios.post( REST_LOGIN , data).then(
				res => {
					var user; 
					try {
						console.log(res.data);
						user = {
							name: res.data.name,
							username: res.data.userName,
							password: res.data.password,
							id: res.data.id,
							type: res.data.customer ?'enduser':'vendor'
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
