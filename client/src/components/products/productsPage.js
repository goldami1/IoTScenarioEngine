import React, { Component } from "react";
import { connect } from "react-redux";
import { login } from "../../actions/auth_actions";
import { Link } from "react-router-dom";
import { withRouter } from "react-router-dom";
import ContentWrapper from "../common/ContentWrapper";
import ProductList from "./productsList"

class ProductsPage extends Component {
	constructor(props) {
		super(props);
		this.state = {
            modalVisble:false
		};
	}

	onClick = (event) => {
		// event.preventDefault();
		// console.log(this.state);
	}

	render() {
		return (
			<ContentWrapper size="small">
                <ProductList 
                    onClick={this.onClick} 
                    products={this.props.products}>
                </ProductList>
			</ContentWrapper>
		);
	}
}

function mapStateToProps({ products }) {
	return {
		products:products.products
	};
}

export default connect(mapStateToProps, { login })(withRouter(ProductsPage));

