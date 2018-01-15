import axios from "axios";

const URL_ROOT = "http://localhost:9090/Scenario_Engine/webapi/customer";
const LOGIN_URL = URL_ROOT + "customer";
const SIGN_URL = URL_ROOT + "/new";
const POST_RET = "https://httpbin.org/post";

export function userSignupRequest(userData) {
	return dispatch => {
		console.log("signup data");
		console.log(userData);
		return axios.post(SIGN_URL, userData).then(res => {
			console.log("signup res");
			console.log(res);
		});
	};
}
