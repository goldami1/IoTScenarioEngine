import React, { Component } from "react";
import { connect } from "react-redux";
import { fetchProducts } from "../../actions/productActions";
import { Link } from "react-router-dom";
import { withRouter } from "react-router-dom";
import ContentWrapper from "../common/ContentWrapper";
import ProductList from "./productsList";
import ProductsAddPage from "./add/productsAddPage";
import { Button ,Modal ,Spin,Row} from 'antd';

class ProductsPage extends Component {
	constructor(props) {
		super(props);
		this.state = {
			modalVisble:false,
			selectedProduct:{}
		};
	}

    componentDidMount() {
        this.props.fetchProducts();
    }

	onAddProductClick = () => {
		this.setState({
			modalVisble: true,
			selectedProduct:{}
		});
		console.log(this.state);
	}

	onModalCancel = () => {
		this.setState({
			modalVisble: false,
			selectedProduct:{}
		})
	}
	
	onProductClick = (product) => {
		this.setState({
			modalVisble: true,
			selectedProduct:product
		});
		console.log(this.state);		
	}

	render() {
		return (
						<ContentWrapper size="small">
						<Button 
							onClick={this.onAddProductClick}
							style={{marginBottom:"20px"}} 
						>
							Add product 
						</Button>
						<ProductList 
							onProductClick={this.onProductClick} 
							products={this.props.products}>
						</ProductList>
						
						<ProductsAddPage
							visible={this.state.modalVisble}
							product={this.state.selectedProduct}
							onCancel={this.onModalCancel}
						/>
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

