import axios from 'axios';
import {isEmpty} from 'lodash';
import setAuthorizationToken from '../utilities/set_auth_token';

import { SET_CURRENT_USER } from './types';

const URL_ROOT = 'http://localhost:9090/Scenario_Engine/webapi/customer';
const LOGIN_URL = URL_ROOT + 'customer';
const SIGN_URL = URL_ROOT + '/add';
const POST_RET = 'https://httpbin.org/post';


export function setCurrentUser(user) {
  return {
	type: SET_CURRENT_USER,
	user
  };
}

export function logout() {
	return dispatch => {
		localStorage.removeItem('user');
		setAuthorizationToken(false);
		dispatch(setCurrentUser({}));
	}
}

export function login(data) {
	return dispatch => {
		return axios.post(URL_ROOT, data).then(
			res => {
				console.log("hello");
				console.log(res.data);		
				console.log("hello");
				const user = {
					name 		:res.data.Name,
					username 	:res.data.userName,
					password 	:res.data.Password,
					id 			:res.data.Id,
					isCustomer 	:res.data.IsCustomer
				};

				localStorage.setItem('user', JSON.stringify(user));
				setAuthorizationToken(user);
				dispatch(setCurrentUser(user));
		  	}
		);
	}
}

