import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import Flag, { FlagGroup } from '@atlaskit/flag';
import Modal from '@atlaskit/modal-dialog';
import Page from '@atlaskit/page';
import Banner from '@atlaskit/banner';
import { removeBanner } from "../../actions/app_actions";
import Navigation from './navigation';
import { connect } from "react-redux";

class App extends PureComponent {
 
	state = {
		flags: [],
		isModalOpen: false,
	};

	static contextTypes = {
		navOpenState: PropTypes.object,
		router: PropTypes.object,
	};

	static PropTypes = {
		navOpenState: PropTypes.object,
		onNavResize: PropTypes.func,
	};

	static childContextTypes = {
		showModal: PropTypes.func,
		addFlag: PropTypes.func,
	}

	getChildContext() {
		return {
			showModal: this.showModal,
			addFlag: this.addFlag,
		};
	}

	showModal = () => {
		this.setState({ isModalOpen: true });
	}

	hideModal = () => {
		this.setState({ isModalOpen: false });
	}

	addFlag = () => {
		this.setState({ flags: [{ id: Date.now() }].concat(this.state.flags) });
	}

	onFlagDismissed = (dismissedFlagId) => {
		this.setState({
			flags: this.state.flags.filter(flag => flag.id !== dismissedFlagId),
		})
	}

	
	render() {
		console.log(this.props.banner);
		return (
			<div>
				<Page
					navigationWidth={this.context.navOpenState.width}
					navigation={<Navigation  onNavResize={this.props.onNavResize}/>}
				>
					<Banner appearance={this.props.banner.appearance} isOpen={this.props.banner.isOpen}>
						{this.props.banner.content}
					</Banner>
					{this.props.children}
				</Page>
				<div>
					<FlagGroup onDismissed={this.onFlagDismissed}>
						{
							this.state.flags.map(flag => (
								<Flag
									id={flag.id}
									key={flag.id}
									title="Flag goes here"
									description="Flag description goes here"
								/>
							))
						}
					</FlagGroup>
					{
						this.state.isModalOpen && (
							<Modal
								heading="Candy bar"
								actions={[{ text: 'Exit candy bar', onClick: this.hideModal }]}
								onClose={this.hideModal}
							>
								<p style={{ textAlign: 'center' }}>
									<img src="http://i.giphy.com/yidUztgRB2w2gtDwL6.gif" alt="Moar cupcakes" />
								</p>
							</Modal>
						)
					}
				</div>
			</div>
		);
	}
}


function mapStateToProps({app}) {
	return {
		banner:{
			isOpen:app.isOpen,
			content:app.content,
			appearance:app.appearance
		}
	}
}


export default connect(mapStateToProps, { removeBanner })(App);