import axios from 'axios';

const URL_ROOT = 'http://demo6475105.mockable.io/';
const LOGIN_URL = URL_ROOT + 'login';

export function userSignupRequest(userData) {
	return dispatch => {
		return axios.post(LOGIN_URL, userData);
	}
}
