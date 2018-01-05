import React from 'react';
import { connect } from 'react-redux';
import {withRouter} from "react-router-dom";
import { addBanner,removeBanner } from "../actions/app_actions";

export default function(ComposedComponent) {
  class Authenticate extends React.Component {
    componentWillMount() {
      if (!this.props.isAuthenticated) {
        this.props.addBanner('The page you asked is autthorized please log in');
        this.props.history.push("/login");
        setTimeout( () => { this.props.removeBanner() }, 5000);
      }
    }

    componentWillUpdate(nextProps) {
      if (!nextProps.isAuthenticated) {
        this.props.history.push("/")
      }
    }

    render() {
      return (
        <ComposedComponent {...this.props} />
      );
    }
  }

  function mapStateToProps(state) {
    return {
      isAuthenticated: state.auth.isAuthenticated
    };
  }
  
  return connect(mapStateToProps, {addBanner , removeBanner} )(withRouter(Authenticate));
}
