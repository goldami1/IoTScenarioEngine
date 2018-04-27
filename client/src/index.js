import registerServiceWorker from "./registerServiceWorker";
import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import { createStore, applyMiddleware, compose } from "redux";
import { Provider } from "react-redux";
import rootReducer from "./reducers";
import ReduxPromise from "redux-promise";
import ReduxThunk from "redux-thunk";
import { setCurrentUser } from "./actions/authActions";
import { fetchDevices, fetchProducts } from "./actions/deviceActions";
import setAuthorizationToken from "./utilities/set_auth_token";
import MainRouter from "./components/MainRouter";


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
	store.dispatch(fetchDevices());
	// store.dispatch(fetchProducts());
	// store.dispatch(fetchSenarios());
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
