import PropTypes from 'prop-types';
import React from 'react';
import { Link , withRouter } from 'react-router-dom';
import { logout } from '../../actions/auth_actions';
import { connect } from 'react-redux';
import Navigation, {
	AkContainerTitle,
	AkCreateDrawer,
	AkNavigationItem,
} from '@atlaskit/navigation';
import DashboardIcon from '@atlaskit/icon/glyph/dashboard';
import LoginIcon from '@atlaskit/icon/glyph/sign-in';
import SignupIcon from '@atlaskit/icon/glyph/invite-team';
import PullRequestsIcon from '@atlaskit/icon/glyph/bitbucket/pullrequests';
import GearIcon from '@atlaskit/icon/glyph/settings';
import SearchIcon from '@atlaskit/icon/glyph/search';
import CreateIcon from '@atlaskit/icon/glyph/add';
import WarningIcon from '@atlaskit/icon/glyph/warning';
import CreateDrawer from './create_drawer';
import AtlassianIcon from '@atlaskit/icon/glyph/atlassian';
import ArrowleftIcon from '@atlaskit/icon/glyph/arrow-left';
import Tooltip from '@atlaskit/tooltip'
import nucleusImage from '../../images/nucleus.png';
import UserDropdown  from './account_dropdown';
import Banner from '@atlaskit/banner';

class StarterNavigation extends React.Component {
	constructor(props){
		super(props);
		this.state = {
			navLinks: [
				['/login', 'Home', LoginIcon],
				['/pull-requests', 'Pull requests', PullRequestsIcon],
				['/settings', 'Settings', GearIcon],
			]
		};
	}

	static contextTypes = {
		navOpenState: PropTypes.object,
		router: PropTypes.object,
	};

	openDrawer = (openDrawer) => {
		this.setState({ openDrawer });
	};

	shouldComponentUpdate(nextProps, nextContext) {
		return true;
	};


	links(type){
		switch(type) {
			case "vendor": return (
				[
					['/products', 'Products', DashboardIcon]
				]
			);
			case "enduser":  return (
				[
					['/devices', 'Devices', DashboardIcon],
					['/scenarios', 'Scenarios', DashboardIcon],
				]
			);
			default: return (
				[
					['/login', 'Login ', LoginIcon],
					['/signup', 'Singup ', SignupIcon],
				]
			);
		}
	}
	

	render() {

		const backIcon = <ArrowleftIcon label="Back icon" size="medium" />;
		const globalPrimaryIcon = <GearIcon />;
		return (
			<Navigation
				isOpen={this.context.navOpenState.isOpen}
				width={this.context.navOpenState.width}
				onResize={this.props.onNavResize}
				containerHeaderComponent={() => (
					<AkContainerTitle
						href="#foo"
						icon={
							<img alt="nucleus" src={nucleusImage} />
						}
						text="Scenario Engine"
					/>
				)}

				globalPrimaryIcon={globalPrimaryIcon}
				globalPrimaryItemHref="/"
				hasBlanket
				drawers={[
					<AkCreateDrawer
						backIcon={backIcon}
						isOpen={this.state.openDrawer === 'create'}
						key="create"
						onBackButton={() => this.openDrawer(null)}
						primaryIcon={globalPrimaryIcon}
					>
						<CreateDrawer
							onItemClicked={() => this.openDrawer(null)}
						/>
					</AkCreateDrawer>
				]}
				globalCreateIcon={<CreateIcon label="Create icon" />}
				globalSecondaryActions={[<UserDropdown onLogout={this.props.logout}/>]}
				onCreateDrawerOpen={() => this.openDrawer('create')}
			>
			
				{

					this.links(this.props.auth.user.type).map(link => {
						const [url, title, Icon] = link;
						return (
							<Tooltip content={(!this.context.navOpenState.isOpen)?title:''} position='right'>
								<Link key={url} to={url}>
									<AkNavigationItem
										icon={<Icon label={title} size="medium" />}
										text={title }
										isSelected={this.props.location.pathname==url}
									/>
								</Link>
							</Tooltip>
						);
					}, this)
				}
			</Navigation>

		);
	}
}

function mapStateToProps(state) {
	return {
	auth: state.auth
	};
}

export default connect(mapStateToProps, { logout })(withRouter(StarterNavigation));
