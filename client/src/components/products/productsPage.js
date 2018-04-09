import React, { Component } from "react";
import { connect } from "react-redux";
import { fetchProducts } from "../../actions/productActions";
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

    componentDidMount() {
        this.props.fetchProducts();
    }

	onProductClick = (product) => {
        
	}

	render() {
		return (
			<ContentWrapper size="small">
                <ProductList 
                    onProductClick={this.onProductClick} 
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

export default connect(mapStateToProps, { fetchProducts })(withRouter(ProductsPage));

