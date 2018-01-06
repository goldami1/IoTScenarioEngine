import React, { Component } from 'react';
import { Link , withRouter} from 'react-router-dom';
import { userSignupRequest } from "../../actions/signup_actions";
import { connect } from "react-redux";

class RegisterForm extends Component {

	constructor(props){
		super(props);
		this.state = {
			firstname: '',
			lastname: '',
			username: '',
			email: '',
			password: '',
			company_name: '',
			description: '',
			is_vendor: false,
			is_loading: false,
			error:''
		}

		this.onChange = this.onChange.bind(this);
		this.onSubmit = this.onSubmit.bind(this);
	}




	toggleType(type){
		 this.setState({is_vendor:type})
	}
	onChange( event ){
		this.setState({
			[event.target.name] : event.target.value
		}) 
	}
	onSubmit (event ){
		event.preventDefault();
		this.setState({
			error: '',
			isLoading: true 
		});

		console.log(this.props);

		var user;
		if (this.state.is_vendor) {
			user = {
				UserName:this.state.username,
				Name: this.state.company_name,
				Password: this.state.password,
				Email: this.state.email,
			}
		}else{
			user = {
				isCustomer:true,
				UserName:this.state.username,
				Name: this.state.firstname,
				Password: this.state.password,
				Email: this.state.email
			}			
		}
		console.log("signup");
		console.log(user);
		this.props.userSignupRequest(user)
			.then(
				(res) => this.props.history.push("/login"),
				(err) => {
					this.setState({
						// error: err.response.data.error,
						isLoading: false
					});
				}
			);

	}



	render() {
		const vendorForm = (
			<div>
				<div className="form-group">
					<label >Company name</label>
					<input 
						onChange={this.onChange}
						value={this.state.company_name}
						type="text" 
						className="form-control" 
						name="company_name"  
						placeholder="Company name"/>
					
				</div>
				<div className="form-group">
					<label >Description</label>
					<textarea  
						onChange={this.onChange}
						value={this.state.description}
						type="text" 
						className="form-control" 
						name="description"  
						placeholder="Description"/>
				</div>
			</div>
		);


		const userForm = (
			<div>
				<div className="form-group">
					<label >First name</label>
					<input 
						onChange={this.onChange}
						value={this.state.firstname}
						type="text" 
						className="form-control" 
						name="firstname"  
						placeholder="firstname"/>
					
				</div>
				<div className="form-group">
					<label >Last name</label>
					<input 
						onChange={this.onChange}
						value={this.state.lastname}
						type="text" 
						className="form-control" 
						name="lastname"  
						placeholder="lastname"/>
				</div>
			</div>
		);

		return (
			<form className="col-lg-4 offset-lg-4 card card-body " onSubmit={this.onSubmit}>

				<div className="form-group">
					<label >Account type</label>
					<div className="btn-group btn-block mb-3"  data-toggle="buttons">
						<label 
							className={`btn btn-lg btn-outline-primary w-50 ${!this.state.is_vendor && "active"}`}
							onClick={this.toggleType.bind(this,false)}>
							<input type="radio"  name="options" value="enduser"/> Enduser
						</label>
						<label 
							className={`btn btn-lg btn-outline-primary w-50 ${this.state.is_vendor && "active"}`}
							onClick={this.toggleType.bind(this,true)}>
							<input type="radio" name="options" value="vendor" />Vendor
						</label>
						</div>
					</div>

				{ this.state.is_vendor ? vendorForm:userForm }

				<div className="form-group">
					<label >Email</label>
					<input 
						onChange={this.onChange}
						value={this.state.email}
						type="text" 
						className="form-control" 
						name="email"  
						placeholder="email"/>
				</div>
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
					onSubmit={this.onSubmit}
					type="submit" 
					className="btn btn-primary btn-lg btn-block mt-3 mb-3">
					Register
				</button>
					{
						this.state.error &&
	             	  	<div className=" m-3  text-danger">
	                        {this.state.error}
	                    </div>	
					}
				<Link to="/login" className=" m-3 text-center">Have a user <strong>Login</strong></Link>
			</form>
		);
	}
}

// export default RegisterForm;
export default connect(null, { userSignupRequest })(withRouter(RegisterForm));