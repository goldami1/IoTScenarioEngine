import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import ContentWrapper from '../../common/ContentWrapper';
import { Button, Icon,Row, Col,Layout } from 'antd';
const { Header, Footer, Sider, Content } = Layout;

<Row>
	<Col span={12}>col-12</Col>
	<Col span={12}>col-12</Col>
</Row>

class ScenarioForm extends Component {
	constructor(props) {
		super(props);
		this.state = {

		};
	}

	render() {
		return (		
			<Layout style={{margin:'40px', borderRadius:'5px solid '}}>
				<Content style={{ background: '#fff', padding: '10px' }} >Content</Content>
				<Sider width={220} style={{color:'white', padding:'10px',background: 'white',borderLeft:'1px solid #ebedf0'}}>
					<Row>
						<Button.Group >
							<Button type="default" >Events</Button>
							<Button type="default" >Actions</Button>
							<Button><Icon type="plus" /></Button>
						</Button.Group>
					</Row>
					<Row>

					</Row>
				</Sider>
			</Layout>
		);
	}
}

export default withRouter(ScenarioForm);
