import PropTypes from "prop-types";
import React, { PureComponent } from "react";
import { Route, Switch } from "react-router-dom";
import App from "./app/";
import LoginPage from "./login/loginForm";
import RegisterPage from "./register/";
import { Link, withRouter } from "react-router-dom";
import DevicesPage from "./devices/";
import DeviceForm from "./devices/add/";
import ScenarioForm from "./scenarios/add/";
import ProductsPage from "./products/productsPage";

import requireAuth from "../utilities/require_auth";
import NoMatch from "./nomatch/";

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
					<Route path="/devices/add" component={DeviceForm} />
					<Route
						exact
						path="/scenarios"
						component={ScenarioForm}
					/>
					<Route
						exact
						path="/devices"
						component={requireAuth(DevicesPage)}
					/>
					<Route path="/products" component={ProductsPage} />
					<Route component={NoMatch} />
				</Switch>
			</App>
		);
	}
}

export default withRouter(MainRouter);
