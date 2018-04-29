import { isEmpty , isFunction} from "lodash";
import { 
	SET_MESSAGE,
	RECEIVE_PRODUCTS,
	ADD_PRODUCT,
	ADD_DEVICE,
	SIGNUP_USER,
	ADD_SCENARIO
} from "./types";
 import {DEBUG} from './restapi';
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
					if(config.method == 'post' && DEBUG)
					{
						switch (type) {
							case ADD_PRODUCT:
							case ADD_DEVICE:
							case ADD_SCENARIO:
							case SIGNUP_USER:
								dispatch(action.res([,res.data])); 
								break;		
							default:
								break;
						}						
					}else
					{
						dispatch(action.res(res.data)); 
					}
					
				}
				switch (type) {
					case ADD_PRODUCT:
					case ADD_DEVICE:
					case ADD_SCENARIO:
					case SIGNUP_USER:
						dispatch(setMessage({content:" ",type:"success"})); 
						break;		
					default:
						break;
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
				
				// setTimeout(() => {
				// 	dispatch(apiRequest(config,action,type));
				// }, 5000);
					 
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
