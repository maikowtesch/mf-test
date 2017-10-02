import React, { Component } from 'react';
import { Button, Form, Grid, Segment, Label, Header } from 'semantic-ui-react';
import 'semantic-ui/dist/semantic.min.css';


class ManagedTasks extends Component {
	constructor(props) {
		super(props);

		this.state = { cardNumber:'', holderName:'', expiryDate:'', insertButtonColor:'grey', insertButtonDisabled:true }

		this.onChangeNumber = this.onChangeNumber.bind(this);
		this.onChangeName = this.onChangeName.bind(this);
		this.onChangeExpiry = this.onChangeExpiry.bind(this);
		this.onClickInsertButton = this.onClickInsertButton.bind(this);
		this.onClickInsertButtonCallback = this.onClickInsertButtonCallback.bind(this);
	}

	onChangeNumber(event) {
		let number = this.onlyDigits(event.target.value);
		this.enableInsertButton(number, this.state.holderName, this.state.expiryDate)

		this.setState({ cardNumber:number });
	}

	onChangeName(event) {
		this.enableInsertButton(this.state.cardNumber, event.target.value, this.state.expiryDate)

		this.setState({ holderName:event.target.value });
	}

	onChangeExpiry(event) {
		let number = this.onlyDigits(event.target.value);
		this.enableInsertButton(this.state.cardNumber, this.state.holderName, number)

		this.setState({ expiryDate:number });
	}

	enableInsertButton(cardNumber, holderName, expiryDate) {
		if (cardNumber !== '' && holderName !== '' && expiryDate !== '') {
			this.setState({ insertButtonDisabled:false, insertButtonColor:'teal' });
		} else {
			this.setState({ insertButtonDisabled:true, insertButtonColor:'grey' });
		}
	}

	onlyDigits(str) {
		return str.replace(/[^0-9//]/g,'');
	}

	onClickInsertButton(event) {
		let data = new FormData();
		data.append('cardNumber', this.state.cardNumber);
		data.append('holderName', this.state.holderName);
		data.append('expiryDate', this.state.expiryDate);

		let request = {method: 'POST', headers:{token:this.props.token.token}, body: data};

		fetch('http://localhost:8080/services/card/insert', request)
			.then(this.checkStatus)
			.then((response) => { return response.json() })
			.then(this.onClickInsertButtonCallback)
			.catch(this.onClickInsertButtonError);
	}

	onClickInsertButtonCallback(status) {
		alert(status.message);

		if (status.code === 0) {
			this.setState({ cardNumber:'', holderName:'', expiryDate:'' });
		}
	}

	// Verify the response HTTP status
	checkStatus(response) {
		if (response.status >= 200 && response.status < 300) {
			return response;
		} else {
			var error = new Error(response.statusText);
			error.response = response;
			throw error;
		}
	}

	onClickInsertButtonError(ex) {
		alert('Error during server call.');
	}



	render() {
		return (
			<Grid container stackable verticalAlign='middle'>
				<Grid.Row>
					<Grid.Column >
						
						<Form size='large'>
							<Segment stacked>
								
								<Form.Input
									fluid
									placeholder='Card number'
									onChange={this.onChangeNumber}
									value={this.state.cardNumber}
								/>
								<Form.Input
									fluid
									placeholder='Card holder name'
									onChange={this.onChangeName}
									value={this.state.holderName}
								/>
								<Form.Input
									fluid
									placeholder='Expiry date (YY/MM)'
									onChange={this.onChangeExpiry}
									value={this.state.expiryDate}
								/>

								<Button size='large' color={this.state.insertButtonColor} disabled={this.state.insertButtonDisabled} onClick={this.onClickInsertButton}>Insert</Button>
								
							</Segment>
						</Form>

					</Grid.Column>
				</Grid.Row>
			</Grid>
		);
	}
}

export default ManagedTasks;
