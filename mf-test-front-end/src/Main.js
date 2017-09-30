import React, { Component } from 'react';
import LoginForm from './LoginForm';
import UserArea from './UserArea';



class Main extends Component {

	constructor(props) {
		super(props);

		// Setting state
		this.state = { token : '' };

		this.registerToken = this.registerToken.bind(this);
		this.unRegisterToken = this.unRegisterToken.bind(this);
	}

	registerToken(returnedToken) {
		this.setState( { token : returnedToken } );
	}

	unRegisterToken() {
		this.setState( { token : '' } );	
	}

	render() {
		let component = null;

		if (this.state.token === '') {
			component = <LoginForm onLogin={this.registerToken} />
		} else {
			component = <UserArea token={this.state.token} onLogout={this.unRegisterToken} />
		}

		return component;
	}
}

export default Main;