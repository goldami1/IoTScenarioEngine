
import React, { PureComponent } from 'react';
import { Link , withRouter } from 'react-router-dom';
import { connect } from "react-redux";
import {Avatar, Popover, Layout, Menu, Icon , Dropdown, Button, message} from 'antd';
import Navigation from './navigation';
import links  from './links';

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
	

	render() {

		const { auth, location } = this.props;
const menu = (
  <Menu >
    <Menu.Item size="small">
      <a target="_blank" rel="noopener noreferrer" href="http://www.alipay.com/">login</a>
    </Menu.Item>
    <Menu.Item>
      <a target="_blank" rel="noopener noreferrer" href="http://www.taobao.com/">lignup</a>
    </Menu.Item>
  </Menu>
);
		return (
		<Layout  style={{ minHeight: '100vh' }}>
			<Sider collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse}
				style={{ position: 'relative', overflow:'hidden', paddingTop:60}}>
				
				<Navigation links={links(auth)} selected={location.pathname} />
				 <Dropdown overlay={menu} placement="topRight"  trigger={['click']} size="20" style={{width:'60px', left:'50px' }}>
					<div style={{textAlign: 'center',position: 'absolute',bottom: 0 ,width:'100%',marginBottom:'60px'}}>
						<Avatar shape="square" size="large" icon="user" style={{background:'#08c'}} />
					</div>
				    </Dropdown>	


			</Sider>
			<Layout style={{height: '100vh'}}>
				<Content style={{ margin: '40px', padding: 40, background: '#fff' }}>
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