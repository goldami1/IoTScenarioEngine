import { SET_MESSAGE } from '../actions/types';
import isEmpty from 'lodash/isEmpty';


const initialState = {
	message:'',
};

export default (state = initialState, action = {}) => {
  switch(action.type) {
    case SET_MESSAGE:
		return Object.assign({}, state, {
			message:action.message.content
		});
    default: return state;
  }
}
