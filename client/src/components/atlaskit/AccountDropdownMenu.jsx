import React, {Component} from 'react';
import DropdownMenu ,{DropdownItem}from '@atlaskit/dropdown-menu';
import { connect } from 'react-redux';
import { logout } from '../../actions/auth_actions';
import { AkGlobalItem } from '@atlaskit/navigation';
import Avatar from '@atlaskit/avatar';


class UserDropdown extends Component {

		constructor(props) {
		super(props);
		this.logout = this.logout.bind(this);

	}
	logout(event) {
		event.preventDefault();
		this.props.logout();
	}

	render(){
		return (
		<DropdownMenu
			appearance="tall"
			position="right bottom"
			items={[
				{
					heading: 'Joshua Nelson',
					items: [
						{ content: <div onClick={this.props.onLogout}>Logout</div> },
					],
				},
			]}
		>

			
			<AkGlobalItem>
				<Avatar size="small" />
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
