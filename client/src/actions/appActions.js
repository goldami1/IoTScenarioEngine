import { isEmpty , isFunction} from "lodash";
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
		if(isFunction(action.pre))
		{
			dispatch(action.pre()); 
		}
		return axios(config).then(
			res => {
				if(isFunction(action.res))
				{
					dispatch(action.res(res.data)); 
				}
			},
			err => {
				if(isFunction(action.err))
				{
					dispatch(action.err()); 
				}
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

export function redirect(address,history)
{
	return dispatch => {
		history.push(address);
	}
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
