import React, {Component} from 'react';
import { connect } from 'react-redux';
import { logout } from '../../actions/auth_actions';
import PropTypes from 'prop-types';
import { Link } from "react-router-dom";

class NavigationBar extends Component {
	constructor(props) {
		super(props);
		this.logout = this.logout.bind(this);

	}
	logout(event) {
		event.preventDefault();
		this.props.logout();
	}

  render() {

	const userLinks = (
		<ul className="ml-sm-auto  navbar-nav navbar-right">
			<li className="nav-item" ><Link className="nav-link"  to="/devices" >Devices</Link></li>
			<li className="nav-item" ><Link className="nav-link"  to="/Scenarios" >Scenarios</Link></li>
			<li className="nav-item" ><a className="nav-link"  onClick={this.logout}>Logout</a></li>
		</ul>
	);

	const vendorLinks = (
		<ul className="ml-sm-auto  navbar-nav navbar-right">
			<li className="nav-item" ><Link className="nav-link"  to="/products" >Devices</Link></li>
			<li className="nav-item" ><a className="nav-link"  onClick={this.logout}>Logout</a></li>
		</ul>
	);

	const guestLinks = (
	  <ul className="ml-sm-auto navbar-nav">
		<li className="nav-item" ><Link className="nav-link" to="/signup">Sign up</Link></li>
		<li className="nav-item" ><Link className="nav-link"to="/login">Login</Link></li>
	  </ul>
	);


	return (
		<nav className="navbar navbar-expand-sm  bg-dark navbar-dark">
			<div className=" container ">
				<Link to="/" className="navbar-brand">Scenario engine</Link>

				{(() => {	switch (this.props.auth.user.type) {
								case "enduser": return userLinks;
								case "vendor": 	return vendorLinks;
								default:      	return guestLinks; }})()}
					
				
			</div>
		</nav>
	);
  }
}




function mapStateToProps(state) {
  return {
	auth: state.auth
  };
}

export default connect(mapStateToProps, { logout })(NavigationBar);
