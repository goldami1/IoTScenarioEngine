import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import { userSignupRequest } from "../../actions/signup_actions";
import { connect } from "react-redux";

import { Form ,Radio,Input, Button} from 'antd';

const FormItem = Form.Item;
const RadioButton = Radio.Button;
const RadioGroup = Radio.Group;
const { TextArea } = Input;

class RegisterForm extends Component {
	constructor(props) {
		super(props);
		this.state = {
			firstName: "",
			lastLame: "",
			username: "",
			email: "",
			password: "",
			companyName: "",
			description: "",
			isVendor: false,
			is_loading: false,
			error: ""
		};
	}

	onChange = (event) => {
		this.setState({
			[event.target.name]: event.target.value
		});
	}
	onSubmit = (event) =>{
		event.preventDefault();
		this.setState({
			error: "",
			isLoading: true
		});

		console.log(this.props);

		var user;
		if (this.state.isVendor) {
			user = {
				isCustomer: false,
				UserName: this.state.username,
				Name: this.state.companyName,
				Password: this.state.password,
				Email: this.state.email
			};
		} else {
			user = {
				isCustomer: true,
				UserName: this.state.username,
				Name: `${this.state.firstName}  ${this.state.lastName}`,
				Password: this.state.password,
				Email: this.state.email
			};
		}
		console.log("signup");
		console.log(user);
		this.props.userSignupRequest(user).then(
			res => this.props.history.push("/login"),
			err => {
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
				<FormItem label="Comapny name">
					<Input value={this.state.comapnyName} name="comapnyName" onChange={this.onChange}/>
				</FormItem>
				<FormItem label="Description">
					<TextArea value={this.state.description} name="description" onChange={this.onChange}/>
				</FormItem>
			</div>
		);

		const userForm = (
			<div>
				<FormItem label="First name">
					<Input value={this.state.firstName} name="firstName" onChange={this.onChange}/>
				</FormItem>
				<FormItem label="Last name">
					<Input value={this.state.lastName} name="lastName" onChange={this.onChange}/>
				</FormItem>
			</div>
		);

		return (
				<Form layout="vertical">
					<FormItem label="Account type">	
						<RadioGroup defaultValue={false} onChange={this.onChange} value={this.state.isVendor} name="isVendor">
							<RadioButton value={false}>Enduser</RadioButton>
							<RadioButton value={true}>Vendor</RadioButton>
						</RadioGroup>
					</FormItem>


					{this.state.isVendor ? vendorForm : userForm}
					<FormItem label="Email">
						<Input value={this.state.email} name="email" onChange={this.onChange}/>
					</FormItem>
					<FormItem label="Username">
						<Input value={this.state.username} name="username" onChange={this.onChange}/>
					</FormItem>
					<FormItem label="Password">
						<Input  type="password" value={this.state.password} name="password" onChange={this.onChange}/>
					</FormItem>
				
					<Button 
						style={{width:"100%"}}
						type="primary"
						disabled={this.state.isLoading}
						onClick={this.onSubmit}
					>
						Register
					</Button>

					<p style={{textAlign:"center", marginTop:"10px"}}><Link to="/login" > Have a user <strong>Login</strong> </Link></p>
						
					
				</Form>
		);
	}
}

// export default RegisterForm;
export default connect(null, { userSignupRequest })(withRouter(RegisterForm));
