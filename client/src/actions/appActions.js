import { isEmpty } from "lodash";
import { SET_MESSAGE } from "./types";
import axios from "axios";

function setMessageCreator(msg) {
	return {
		type: SET_MESSAGE,
		payload: msg
	};
}

function apiRequest(config,action,type) {
	return dispatch => {
		return axios(config).then(
			res => { 
				dispatch(action(res.data)); 
			},
			err => {
				try {
					dispatch(setMessage({ 
						content	: err.response.data.description ,
						type	: "error" 
					}));
				} catch (e) {
					console.error(`Failed to ${config.method} ${type} ${config.method == 'get' ? 'from' : 'to'} ${config.url}`);
				}			 
			}
		);
	};
}

export function getFromApi(type,url,action)
{
	return apiRequest({method:'get',url:url,},action,type);
}

export function postToApi(type,url,action,payload)
{
	return apiRequest({method:'post',url:url,data:payload},action,type);
}




export function setMessage(message, timeout = 50) {
	var msg = {
		content: message.content ? message.content : "UNKNOW ERROR",
		type: message.type ? message.type : "warning"
	};

	return dispatch => {
		dispatch(setMessageCreator(msg));
		setTimeout(() => {
			dispatch(setMessageCreator({}));
		}, timeout);

	};
}
