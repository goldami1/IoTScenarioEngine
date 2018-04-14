import { isEmpty } from "lodash";
import { SET_MESSAGE } from "./types";

function setMessageCreator(msg) {
	return {
		type: SET_MESSAGE,
		payload: msg
	};
}

export function setUnknownError() {
	return dispatch => {
		dispatch(
			setMessage(
				{
					content: "UNKOWN ERROR OCCURED",
					type: "error"
				},
				true
			)
		);
	};
}

export function setMessage(message, timeout = 50) {
	var msg = {
		content: message.content ? message.content : "UNKNOW ERROR",
		type: message.type ? message.type : "warning"
	};

	return dispatch => {
		dispatch(setMessageCreator(msg));
		if (timeout) {
			setTimeout(() => {
				dispatch(setMessageCreator({}));
			}, timeout);
		}
	};
}
