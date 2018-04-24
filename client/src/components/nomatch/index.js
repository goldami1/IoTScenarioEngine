
import React, { Component } from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

class NoMatch extends Component {
	constructor(props) {
		super(props);
		this.state = {};
	}

	componentWillMount() {
		if (!this.props.isAuthenticated) {
			this.props.history.push("/login");	
		}else
		{
			switch (this.props.type) {
				case "vendor":
					this.props.history.push("/products");
					break;
				case "enduser":
					this.props.history.push("/scenarios");
					break;
				default:
					this.props.history.push("/login");
					break;
			}
		}
	}

	componentWillUpdate(nextProps) {
		if (!nextProps.isAuthenticated) {
			this.props.history.push("/login");	
		}else
		{
			switch (nextProps.type) {
				case "vendor":
					this.props.history.push("/products");
					break;
				case "enduser":
					this.props.history.push("/scenarios");
					break;
				default:
					this.props.history.push("/login");
					break;
			}
		}
	}

	render() {
		return (
			<h1>{this.props.type}</h1>
		);
	}
}

function mapStateToProps({auth}) {
	return {
		isAuthenticated: auth.isAuthenticated,
		type: auth.type
	};
}

export default connect(mapStateToProps)(withRouter(NoMatch));