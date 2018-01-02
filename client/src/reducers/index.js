import { combineReducers } from "redux";
import auth from "./auth";
import devices from "./devices";
import scenarios from "./scenarios";

const rootReducer = combineReducers({
  auth,
  devices,
  scenarios
});

export default rootReducer;
