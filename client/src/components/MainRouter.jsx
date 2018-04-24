import PropTypes from "prop-types";
import React, { PureComponent } from "react";
import { Route, Switch } from "react-router-dom";
import App from "./app/";
import LoginPage from "./login/loginForm";
import RegisterPage from "./register/";
import { Link, withRouter } from "react-router-dom";
import DevicesPage from "./devices/devicePage";
import ScenariosPage from "./scenarios/scenariosPage";
import ProductsPage from "./products/productsPage";

import requireAuth from "../utilities/require_auth";
import NoMatch from "./nomatch/";
import { scenarios } from "../actions/restapi";

class MainRouter extends PureComponent {
	constructor(props) {
		super(props);
		this.state = {};
	}

	render() {
		return (
			<App>
				<Switch>
					<Route path="/login" component={LoginPage} />
					<Route path="/signup" component={RegisterPage} />
					<Route
						exact
						path="/devices"
						component={requireAuth(DevicesPage)}
					/>
					<Route
						exact
						path="/scenarios"
						component={requireAuth(ScenariosPage)}
					/>
					<Route
						exact
						path="/devices"
						component={requireAuth(DevicesPage)}
					/>
					<Route path="/products" component={requireAuth(ProductsPage)} />
					<Route component={NoMatch} />
				</Switch>
			</App>
		);
	}
}

export default withRouter(MainRouter);
