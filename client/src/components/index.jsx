import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { Route, Switch } from 'react-router-dom';
import App from './app/';
import LoginPage from './login/';
import RegisterPage from './register/';
import { Link , withRouter } from 'react-router-dom';
import DevicesPage from './devices/';
import DeviceForm from './devices/add/';
import ScenarioForm from './scenarios/add/';
import ScenariosPage from './scenarios/';
import ProductsPage from './products/';

import NoMatch from './nomatch/';
import requireAuth from '../utilities/require_auth';

class MainRouter extends PureComponent {
	constructor(props) {
		super(props);
		this.state = {
			navOpenState: {
				isOpen: true,
				width: 304,
			}
		}
	}

	getChildContext () {
		return {
			navOpenState: this.state.navOpenState,
		};
	}

	onNavResize = (navOpenState) => {
		this.setState({
			navOpenState,
		});
	}

	render() {
		return (
			<App  onNavResize={this.onNavResize}>
				<Switch>
					<Route path="/login" component={LoginPage} />
					<Route path="/signup" component={RegisterPage}  />
					<Route exact path="/devices" component={requireAuth(DevicesPage)} />
					<Route path='/devices/add' component={DeviceForm} />
					<Route exact path="/scenarios" component={requireAuth(ScenariosPage)} />
					<Route exact path ="/scenarios/add" component={requireAuth(ScenarioForm)} />
					<Route exact path="/devices" component={requireAuth(DevicesPage)} />
					<Route path="/products" component={requireAuth(ProductsPage)}  />
					<Route component={NoMatch}/>
				</Switch>
			</App>
		);
	}
}

export default withRouter(MainRouter);

MainRouter.childContextTypes = {
	navOpenState: PropTypes.object,
}
