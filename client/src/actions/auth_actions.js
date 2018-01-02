import axios from 'axios';
import {isEmpty} from 'lodash';
import setAuthorizationToken from '../utilities/set_auth_token';

import { SET_CURRENT_USER } from './types';

const URL_ROOT = 'http://demo6475105.mockable.io/';
const LOGIN_URL = URL_ROOT + 'login';
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
		return axios.post(LOGIN_URL, data).then(
			res => {
				const user = {
					username 	:res.data.username,
					password 	:res.data.password,
					id 			:res.data.id,
					type 		:res.data.type

				};

				localStorage.setItem('user', JSON.stringify(user));
				setAuthorizationToken(user);
				dispatch(setCurrentUser(user));

		  	}
		);
	}
}

