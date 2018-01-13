import axios from 'axios';
import { 
	ADD_PRODUCT, 
	PRODUCT_ERROR_OCCURED			
} from './types';

import {setMessage,setUnknownError} from './appActions';

const test_url = 'http://demo2037819.mockable.io/product';
const error_url  = 'http://demo6475105.mockable.io/error';


export function errorOccured(error) {
  return {
	type: PRODUCT_ERROR_OCCURED,
	error
  };
}

export function addProduct(product) {
	console.log('add product');
	console.log(product);
	return (dispatch,getState) => {
		const {auth} = getState();
		// return axios.post(test_url+auth.user.id,product).then(
		return axios.post(test_url,product).then(
			res => {
				console.log('res')
				try {console.log(res.data)}	  			
		  		catch(e) { console.error(`ADD_PRODUCT_RESPONSE_ERROR: ${e}`,res.data); }						
			},
		  	err => {
		  		try {
		  			const error = err.response.data.error;
		  			if (error) {
		  				dispatch(errorOccured(err.response.data.error));
		  			}else{
						dispatch(setUnknownError());  				
		  			}
		  		}	  			
		  		catch(e) { 
		  				console.log('err catch');
						dispatch(setUnknownError());
			  			dispatch(setMessage({content:'UNKOWN ERROR OCCURED'}));
	        			setTimeout( () => { dispatch(setMessage({})) }, 50);
			  			console.error(`ADD_PRODUCT_ERROR: ${e}`,err.response); 
		  			}
		  	}
		);
	}
}