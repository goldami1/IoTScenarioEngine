import PropTypes from 'prop-types';
import React from 'react';
import { Link , withRouter } from 'react-router-dom';
import { logout } from '../../actions/auth_actions';
import { connect } from 'react-redux';
import Nav, {
	AkContainerTitle,
	AkCreateDrawer,
	AkNavigationItem,
} from '@atlaskit/navigation';
import DashboardIcon from '@atlaskit/icon/glyph/dashboard';
import PullRequestsIcon from '@atlaskit/icon/glyph/bitbucket/pullrequests';
import GearIcon from '@atlaskit/icon/glyph/settings';
import SearchIcon from '@atlaskit/icon/glyph/search';
import CreateIcon from '@atlaskit/icon/glyph/add';

import CreateDrawer from './CreateDrawer';
import HelpDropdownMenu from './HelpDropdownMenu';


import AtlassianIcon from '@atlaskit/icon/glyph/atlassian';
import ArrowleftIcon from '@atlaskit/icon/glyph/arrow-left';

import nucleusImage from '../../images/nucleus.png';
import AccountDropdownMenu from './AccountDropdownMenu';
import '@atlaskit/css-reset';
class StarterNavigation extends React.Component {
	constructor(props){
		super(props);
		this.state = {
			navLinks: [
				['/login', 'Home', DashboardIcon],
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
			case "vendor":  return (
				[
					['/devices', 'Devices', DashboardIcon],
					['/scenarios', 'Scenarios', DashboardIcon],
				]
			);
			default: return (
				[
					['/NOTHING', 'NOTHING ', DashboardIcon],
				]
			);
		}
	}

	getLinks = () => {
		this.setState({navLinks: this.links(this.props.auth.user.type)});
	}

	render() {

		// this.getLinks();
		const backIcon = <ArrowleftIcon label="Back icon" size="medium" />;
		const globalPrimaryIcon = <h1>:)</h1>;
		return (
			<Nav
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
				globalAccountItem={AccountDropdownMenu}
				globalCreateIcon={<CreateIcon label="Create icon" />}
				globalHelpItem={HelpDropdownMenu}
				onCreateDrawerOpen={() => this.openDrawer('create')}
			>
				{
					this.state.navLinks.map(link => {
						const [url, title, Icon] = link;
						return (
							<Link key={url} to={url}>
								<AkNavigationItem
									icon={<Icon label={title} size="medium" />}
									text={title }
									isSelected={this.props.location.pathname==url}
								/>
							</Link>
						);
					}, this)
				}
			</Nav>

		);
	}
}

function mapStateToProps(state) {
	return {
	auth: state.auth
	};
}

export default connect(mapStateToProps, { logout })(withRouter(StarterNavigation));
