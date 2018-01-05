import {isEmpty} from 'lodash';
import { SET_BANNER , REMOVE_BANNER } from './types';



export function setBanner(banner) {
  return {
	type: SET_BANNER,
	banner
  };
}

export function unsetBanner() {
  return {
	type: REMOVE_BANNER
  };
}

export function addBanner(appearance,content) {
	return dispatch => {
		dispatch(setBanner({
			isOpen:true, 
			appearance:appearance,
			content:content
		}));
	}
}


export function removeBanner() {
	return dispatch => {
		dispatch(unsetBanner());
	}
}
