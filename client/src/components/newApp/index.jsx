
import React, { PureComponent } from 'react';
import { Link , withRouter } from 'react-router-dom';
import { connect } from "react-redux";
import {Avatar,Popover, Layout, Menu, Icon , Dropdown, Button, message} from 'antd';
import Navigation from './navigation';
import links  from './links';
import {setMessage} from '../../actions/appActions';


const { Header, Sider, Content , Footer } = Layout;

class App extends PureComponent {

 	constructor(props){
		super(props);
		this.state = {
			collapsed: false,
		};
	}


	onCollapse = (collapsed) => {
		this.setState({ collapsed });
	}
	
	message = () => {
		if (this.props.message) {
			message.error(this.props.message);
		}
	};

	render() {
		{this.message()}
		const { auth, location } = this.props;
		return (
			<Layout  style={{ minHeight: '100vh' }}>
				<Sider collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse}
					style={{ position: 'relative', overflow:'hidden', paddingTop:60}}
				>
					
					<Navigation links={links(auth)} selected={location.pathname} />
				
						<div style={{textAlign: 'center',position: 'absolute',bottom: 0 ,width:'100%',marginBottom:'60px'}}>
							<Avatar shape="square" size="large" icon="user" style={{background:'#08c'}} />
						</div>
				</Sider>
				<Layout style={{height: '100vh'}}>
					<Content>
						{this.props.children}
					</Content>
					<Footer style={{ textAlign: 'center' }}>
						Scenario Engine  ©2018 Created by Gil
					</Footer>
				</Layout>
			</Layout>
		);
	}
}


function mapStateToProps({auth,app}) {
	return {
		auth,
		message:app.message

	}
}


export default connect(mapStateToProps, {setMessage})(withRouter(App));