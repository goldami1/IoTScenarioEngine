import React from 'react';
import { connect } from 'react-redux';
import {withRouter} from "react-router-dom";
import { setMessage, } from "../actions/appActions";

export default function(ComposedComponent) {
  class Authenticate extends React.Component {
    componentWillMount() {
      if (!this.props.isAuthenticated) {
        this.props.setMessage({content:'The page you asked for require authentication . Please log in'});
        this.props.history.push("/login");
        setTimeout( () => { this.props.setMessage({content:''}) }, 5000);
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
  
  return connect(mapStateToProps, {setMessage} )(withRouter(Authenticate));
}
