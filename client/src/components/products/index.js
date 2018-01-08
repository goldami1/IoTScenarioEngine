import React, { Component } from 'react';
import { connect } from "react-redux";
import { Link } from 'react-router-dom';
import { withRouter } from "react-router-dom";
import  ContentWrapper from '../common/ContentWrapper'
import TextField  from '@atlaskit/field-text'
import FieldTextArea from '@atlaskit/field-text-area';
import { Grid, GridColumn } from '@atlaskit/page';
class ProductAdd extends Component {

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
				<ContentWrapper>
					<h1>Add new product</h1>
					<form>
						<Grid>
							<GridColumn medium={6}>
								<TextField
									shouldFitContainer
									label="Product name"
									placeholder="Product name" />
								<FieldTextArea
									shouldFitContainer
									enableResize
									label="Product description" 
									placeholder="Product description"/>
							</GridColumn>
						</Grid>
					</form>
				</ContentWrapper>

		);
	}
}

// function mapDispatchToProps(dispatch) {
//   return bindActionCreators({ loginUser }, dispatch);
// }


// export default connect(null, mapDispatchToProps)(LoginForm);
export default connect(null, null)(withRouter(ProductAdd));
// export default LoginForm;