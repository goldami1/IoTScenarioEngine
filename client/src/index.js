import registerServiceWorker from "./registerServiceWorker";
import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import { createStore, applyMiddleware, compose } from "redux";
import { Provider } from "react-redux";
import rootReducer from "./reducers";
import ReduxPromise from "redux-promise";
import ReduxThunk from "redux-thunk";
import { setCurrentUser } from "./actions/auth_actions";
import { fetchDevices } from "./actions/devices_actions";
import setAuthorizationToken from "./utilities/set_auth_token";
import MainRouter from "./components/MainRouter";
// import 'bootstrap/dist/css/bootstrap.min.css';
// import '@atlaskit/css-reset';

import "antd/dist/antd.css";

const store = createStore(
	rootReducer,
	compose(
		applyMiddleware(ReduxPromise, ReduxThunk),
		window.devToolsExtension ? window.devToolsExtension() : f => f
	)
);

if (localStorage.user) {
	const user = JSON.parse(localStorage.user);
	setAuthorizationToken(user);
	store.dispatch(setCurrentUser(user));
	// store.dispatch(fetchDevices());node
}

ReactDOM.render(
	<Provider store={store}>
		<BrowserRouter exact path="/">
			<MainRouter />
		</BrowserRouter>
	</Provider>,

	document.getElementById("root")
);

registerServiceWorker();
