import React, { Component } from 'react';
import { connect } from "react-redux";
import { login } from "../../actions/auth_actions";
import { Link } from 'react-router-dom';
import { withRouter } from "react-router-dom";

class LoginForm extends Component {

	constructor(props){
		super(props);
		this.state = {
			username: '',
			password: '',
			error:'',
			isLoading:false
		}
		this.onChange = this.onChange.bind(this);
		this.onClick = this.onClick.bind(this);
		this.onSubmit = this.onSubmit.bind(this);
	}

	onChange( event ){
		this.setState({
			[event.target.name] : event.target.value
		}) 
	}
	onClick (event ){
		// event.preventDefault();
		// console.log(this.state);
	}
	onSubmit (event ){
		event.preventDefault();
		this.setState({
			error: '',
			isLoading: true 
		});
		this.props.login({"UserName":this.state.username, "Password":this.state.password})
			.then(
				(res) => this.props.history.push("/"),
				(err) => {
					this.setState({
						// error: err.response.data.Description,
						isLoading: false
					});
				}
			);
		}


	render() {


		return (
			<form 
				onSubmit={this.onSubmit} 
				className=" col-lg-4 offset-lg-4  card card-body  card card-body ">
				<div className="form-group">
					<label >Username</label>
					<input 
						onChange={this.onChange}
						value={this.state.username}
						type="text" 
						className="form-control" 
						name="username"  
						placeholder="username"/>
				</div>
				<div className="form-group">
					<label >Password</label>
					<input 
						onChange={this.onChange}
						value={this.state.password}
						type="password" 
						className="form-control" 
						name="password" 
						placeholder="password"/>
				</div>

				<button 

					disabled={this.state.isLoading}
					type="submit" 
					className="mt-3 btn btn-primary btn-lg btn-block ">
					Login
				</button>
				<div className=" m-3 text-center" >

				<Link to="/signup">Dont have a user <strong>Sign up</strong></Link>
					{
						this.state.error &&
	             	  	<div className=" m-3  text-danger">
	                        {this.state.error}
	                    </div>	
					}
				</div>

			</form>
		);
	}
}

// function mapDispatchToProps(dispatch) {
//   return bindActionCreators({ loginUser }, dispatch);
// }


// export default connect(null, mapDispatchToProps)(LoginForm);
export default connect(null, { login })(withRouter(LoginForm));
// export default LoginForm;