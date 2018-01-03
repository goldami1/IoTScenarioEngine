import React from 'react';
import Routes from '../';
import { Link } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import '../../style/style.css';
import Navigation from './navbar';
// import MainRouter from '../../modules/MainRouter'

export default () => (
	
	<div>	
		<Navigation />
		<div className="container">
			{/*<MainRouter />*/}
			<Routes />
		</div>	
	</div>				

);
