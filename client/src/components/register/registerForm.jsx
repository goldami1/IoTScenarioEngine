import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import { userSignupRequest } from "../../actions/signupActions";
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

		var user = {
			isCustomer: !this.state.isVendor,
			userName: this.state.username,
			name: this.state.isVendor ? this.state.companyName : `${this.state.firstName} ${this.state.lastLame}`,
			description:this.state.description,
			password: this.state.password,
			email: this.state.email
		};

		console.log("signup");
		console.log(this.state);
		console.log(user);
		this.props.userSignupRequest(user);
	}

	render() {
		const vendorForm = (
			<div>
				<FormItem label="Comapny name">
					<Input value={this.state.companyName} name="companyName" onChange={this.onChange}/>
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

					<FormItem>
						<Button
							size="large"
							type="primary"
							htmlType="submit"
							className="login-form-button"
							style={{width:"100%"}}
							loading={this.props.isLoading}
							onClick={this.onSubmit}
						>
							Register
						</Button>
					</FormItem>
					

					<p style={{textAlign:"center", marginTop:"10px"}}><Link to="/login" > Have a user <strong>Login</strong> </Link></p>
						
					
				</Form>
		);
	}
}

function mapStateToProps({ auth }) {
	return {
		isLoading:auth.isLoading
	};
}

// export default RegisterForm;
export default connect(mapStateToProps, { userSignupRequest })(withRouter(RegisterForm));
