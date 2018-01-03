import React from 'react';
import { Route, Switch , Redirect} from 'react-router-dom';
import LoginPage from './login/login_page';
import RegisterPage from './register/register_page';

import DevicesPage from './devices/devices_page';
import DeviceForm from './devices/device_form';
import ScenarioForm from './scenarios/scenario_form';
import ScenariosPage from './scenarios/scenarios_page';
import ProductsPage from './products/products_page';

import NoMatch from './app/nomatch';
import requireAuth from '../utilities/require_auth';

export default () => (
	<Switch>
		<Route path="/login" component={LoginPage} />
		<Route path="/signup" component={RegisterPage} />
		<Route exact path="/devices" component={requireAuth(DevicesPage)} />
		<Route path='/devices/add' component={DeviceForm}/>
		<Route exact path="/scenarios" component={requireAuth(ScenariosPage)} />
		<Route exact path ="/scenarios/add" component={requireAuth(ScenarioForm)} />
		<Route exact path="/devices" component={requireAuth(DevicesPage)} />
		<Route path="/products" component={requireAuth(ProductsPage)} />
		<Route component={NoMatch}/>
	</Switch>
);
