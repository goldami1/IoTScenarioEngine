import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import DropdownMenu ,{DropdownItem}from '@atlaskit/dropdown-menu';
import { connect } from 'react-redux';
import { logout } from '../../actions/auth_actions';
import { AkGlobalItem } from '@atlaskit/navigation';
import Avatar from '@atlaskit/avatar';
import LogoutIcon from '@atlaskit/icon/glyph/sign-out';
import Tooltip from '@atlaskit/tooltip';
import {isEmpty} from 'lodash';

class UserDropdown extends Component {

		constructor(props) {
		super(props);
		this.logout = this.logout.bind(this);

	}

	logout(event) {
		event.preventDefault();
		this.props.logout();
	}

	items(type){
		switch(type) {
			case "vendor":
			case "enduser": return (
					[{
						heading: this.props.auth.user.name,
						items: [
							{ content: <div onClick={this.props.onLogout}><LogoutIcon size="small"/>Logout</div> },
						],
					}]
				);
			default: return (
					[{
						heading: "guest",
						items: [
							{ content: <Link to="/login">Login</Link> },
							{ content: <Link to="/signup">Signup</Link> },
						],
					}]
			);
		}
	}

	render(){
		return (
		<DropdownMenu
			appearance="tall"
			position="right bottom"
			items={this.items(this.props.auth.user.type)}
		>

			<AkGlobalItem>
				<Tooltip content="User menu">
					<Avatar size="small" />
				</Tooltip>
			</AkGlobalItem>
		</DropdownMenu>
		);

	}
}


function mapStateToProps(state) {
	return {
	auth: state.auth
	};
}

export default connect(mapStateToProps, { logout })(UserDropdown);
