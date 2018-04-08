import { combineReducers } from "redux";
import auth from "./auth";
import app from "./app";
import devices from "./devices";
import products from "./devices";
import scenarios from "./scenarios";

const rootReducer = combineReducers({
	auth,
	devices,
	products,
	scenarios,
	app
});

export default rootReducer;
