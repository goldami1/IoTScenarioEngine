import React from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { setMessage } from "../actions/appActions";

export default function(ComposedComponent) {
	class Authenticate extends React.Component {
		componentWillMount() {
			if (!this.props.isAuthenticated) {
				this.props.setMessage({content:"The page you asked for require authentication . Please log in"});
				this.props.history.push("/login");
			}
		}

		componentWillUpdate(nextProps) {
			if (!nextProps.isAuthenticated) {
				this.props.history.push("/login");	
			}
		}

		render() {
			return <ComposedComponent {...this.props} />;
		}
	}

	function mapStateToProps({auth}) {
		return {
			isAuthenticated: auth.isAuthenticated,
			type: auth.type
		};
	}

	return connect(mapStateToProps, { setMessage })(withRouter(Authenticate));
}
