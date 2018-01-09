import React, { Component } from 'react';
import { connect } from "react-redux";
import { login } from "../../actions/auth_actions";
import { Link } from 'react-router-dom';
import { withRouter } from "react-router-dom";

import { Form, Icon, Input, Button, Checkbox } from 'antd';
const FormItem = Form.Item;

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

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received values of form: ', values);
      }
    });
  }
	render() {
		const formItemLayout = {
			wrapperCol: {
				xs: { span: 24 },
				sm: { span: 6, offset:9},
			},
		};
		const { getFieldDecorator } = this.props.form;
		return (
			<Form onSubmit={this.handleSubmit} className="login-form" >
				<FormItem {...formItemLayout}>
					{getFieldDecorator('userName', {
						rules: [{ required: true, message: 'Please input your username!' }],
					})(
						<Input size="large" prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Username" />
					)}
				</FormItem>
				<FormItem {...formItemLayout}>
					{getFieldDecorator('password', {
						rules: [{ required: true, message: 'Please input your Password!' }],
						})(
							<Input size="large" prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Password" />
						)}
				</FormItem>
				<FormItem {...formItemLayout}>
						<Button type="primary" htmlType="submit" className="login-form-button" style={{width:'100%'}} size="large">
							Log in
						</Button>
						<div style={{textAlign:'center'}}><span>Have a user  <a href="">register now!</a></span></div>
				</FormItem>
			</Form>
		);
	}
}


// function mapDispatchToProps(dispatch) {
//   return bindActionCreators({ loginUser }, dispatch);
// }


// export default connect(null, mapDispatchToProps)(LoginForm);
export default connect(null, { login })(withRouter(Form.create()(LoginForm)));
// export default LoginForm;