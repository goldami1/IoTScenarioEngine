import PropTypes from "prop-types";
import React from "react";
import { Link } from "react-router-dom";
import {Avatar, Dropdown, Menu, Icon, Badge } from "antd";

const { SubMenu } = Menu;
const UserMenu = props => {
	const links = props.links.map(link => {
		const [url,title, action] = link;
		const item = <span onClick={props[action]}>{title}</span>
		return (
			<Menu.Item key={title}>
				{url ? <Link key={url} to={url}>{item}</Link>: item}
			</Menu.Item>
		);
	});

	const style = {
		textAlign: "center",
		position: "absolute",
		bottom: 0,
		width: "100%",
		marginBottom: "60px"		
	}

	return (
		<div style={style}>
			<Dropdown overlay={<Menu>{links}</Menu>} trigger={['click']} placement="topCenter" >			
				<Avatar shape="square" size="large" icon="user" style={{ background: "#08c" }}/>
			</Dropdown>
		</div>
	);
};

export default UserMenu;
