import React, { Component } from "react";
import { connect } from "react-redux";
import { login } from "../../actions/auth_actions";
import { Link } from "react-router-dom";
import { withRouter } from "react-router-dom";
import CardWrapper from "../common/CardWrapper";

import { Form, Icon, Input, Button, Checkbox, Card, Col, Layout } from "antd";
const FormItem = Form.Item;

class LoginForm extends Component {
	constructor(props) {
		super(props);
		this.state = {
			username: "",
			password: "",
			error: "",
			isLoading: false
		};
		this.onChange = this.onChange.bind(this);
		this.onClick = this.onClick.bind(this);
		this.onSubmit = this.onSubmit.bind(this);
	}

	onChange(event,fuck) {
		this.setState({
			[event.target.name]: event.target.value
		});
		this.props.form.setFieldsValue({[event.target.name]: event.target.value});
	}
	onClick(event) {
		// event.preventDefault();
		// console.log(this.state);
	}
	onSubmit(event) {
		event.preventDefault();
		var user  = {
			UserName: this.state.username,
			Password: this.state.password
		};
		console.log(user);

		this.props.login(user,this.props.history)
	}

	handleSubmit = e => {
		e.preventDefault();
		this.props.form.validateFields((err, values) => {
			if (!err) {
				this.onSubmit(e);
			}
		});
	};
	render() {
		const { getFieldDecorator } = this.props.form;
		return (
			<CardWrapper size="small">
				<Form onSubmit={this.handleSubmit} className="login-form">
					<FormItem>
						{getFieldDecorator("username", {
							rules: [
								{
									required: true,
									message: "Please input your username!"
								}
							]
						})(
							<Input
								onChange={this.onChange}
								name="username"
								// value={this.state.username}
								size="large"
								prefix={
									<Icon
										type="user"
										style={{ color: "rgba(0,0,0,.25)" }}
									/>
								}
								placeholder="Username"
							/>
						)}
					</FormItem>
					<FormItem>
						{getFieldDecorator("password", {
							rules: [
								{
									required: true,
									message: "Please input your Password!"
								}
							]
						})(
							<Input
								onChange={this.onChange}
								name="password"
								// value={this.state.password}
								size="large"
								prefix={
									<Icon
										type="lock"
										style={{ color: "rgba(0,0,0,.25)" }}
									/>
								}
								type="password"
								placeholder="Password"
							/>
						)}
					</FormItem>
					<FormItem>
						<Button
							type="primary"
							htmlType="submit"
							className="login-form-button"
							loading={this.props.isLoading}
							style={{ width: "100%" }}
							size="large"
						>
							Log in
						</Button>
						<div style={{ textAlign: "center" }}>
							<span>
								Dont have a user
								<Link to="/signup"> Register now!</Link>
							</span>
						</div>
					</FormItem>
				</Form>
			</CardWrapper>
		);
	}
}

function mapStateToProps({ auth }) {
	return {
		isLoading:auth.isLoading
	};
}
// export default connect(null, mapDispatchToProps)(LoginForm);
export default connect(mapStateToProps, { login })(withRouter(Form.create()(LoginForm)));
// export default LoginForm;
