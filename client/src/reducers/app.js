import { SET_BANNER ,REMOVE_BANNER} from '../actions/types';
import isEmpty from 'lodash/isEmpty';


const initialState = {
	isOpen: false,
	appearance:'warning',
	content:'fuck you'
};

export default (state = initialState, action = {}) => {
  switch(action.type) {
    case SET_BANNER:
		return Object.assign({}, state, {
			isOpen: action.banner.isOpen,
			appearance: action.banner.appearance,
			content : action.banner.content
		});
	case REMOVE_BANNER:
		return Object.assign({}, state, { isOpen: false });
    default: return state;
  }
}
