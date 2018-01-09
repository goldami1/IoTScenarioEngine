
import React, { PureComponent } from 'react';
import {isEmpty} from 'lodash';
import { Link , withRouter } from 'react-router-dom';
import { connect } from "react-redux";
import { Layout, Menu, Icon , Dropdown, Button, message} from 'antd';
import Navigation from './navigation';
const { Header, Sider, Content , Footer } = Layout;


class App extends PureComponent {

 	constructor(props){
		super(props);
		this.state = {
			collapsed: false,
			selectedKeys:[]
		};
	}


	onCollapse = (collapsed) => {
		console.log(collapsed);
		this.setState({ collapsed });
	}
	
	links = (user) =>{

		var type = isEmpty(user) ? 'none' : user.type;
		switch(type) {
			case 'enduser': return (
				[
					['/login', 'Login', 'login'],
					['/signup', 'Singup', 'user-add'],
				]
			);
			case 'vendor':  return (
				[
					['/login', 'Login', 'login'],
					['/signup', 'Singup', 'user-add'],
				]
			);
			default: return (
				[
					['/login', 'Login', 'login'],
					['/signup', 'Singup', 'user-add'],
					['/devices', 'Devices', 'appstore-o'],
					['/products', 'Products', 'appstore-o'],
					['/scenarios', 'Scenarios', 'fork'],
				]
			);
		}
	}




	render() {



		return (
		<Layout  style={{ minHeight: '100vh' }}>
			<Sider
          		collapsible
          		collapsed={this.state.collapsed}
          		onCollapse={this.onCollapse}
				style={{ position: 'relative'}}
				>
				<Icon onClick={this.toggle} type="api" style={{height: '100px',marginLeft:20,fontSize: 36, color: '#08c' ,lineHeight:'100px'}}/>

				<Navigation
					links={this.links(this.props.auth)}
					selected={ this.props.location.pathname}/>
					<div  style={{textAlign: 'center',position: 'absolute',bottom: 0,height: '100px' ,width:'100%',marginBottom:'48px'}}>
						<span  style={{background: '#08c' ,lineHeight:'100px' }}>asdf </span>
					</div>
			</Sider>
			<Layout style={{height: '100vh'}}>
				<Content style={{ margin: '40px', padding: 24, background: '#fff' }}>
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


function mapStateToProps({auth}) {
	return {
		auth
	}
}


export default connect(mapStateToProps, null)(withRouter(App));