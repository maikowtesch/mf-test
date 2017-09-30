import React, { Component } from 'react';
import { Button, Form, Grid, Segment, Label, Header, Table } from 'semantic-ui-react';
import 'semantic-ui/dist/semantic.min.css';
import CardTable from './components/CardTable';


class TaskBoard extends Component {
	constructor(props) {
		super(props);

		let test = [
						{cardNumber:'6666666666666666', holderName:'July Agust March', expiryDate:'20/11'},
						{cardNumber:'5555555555555555', holderName:'Test Something Wrong', expiryDate:'18/02'}, 
						{cardNumber:'5555555555555555', holderName:'John Snow', expiryDate:'22/07'}
					]

		this.state = {cardList:[], searchString:'', searchButtonColor:'grey', searchButtonDisabled:true};

		this.onChangeSearch = this.onChangeSearch.bind(this);
		this.onClickSearchButton = this.onClickSearchButton.bind(this);
		this.onClickSearchButtonCallback = this.onClickSearchButtonCallback.bind(this);
	}

	onChangeSearch(event) {
		let number = this.onlyDigits(event.target.value);

		this.setState({ searchString:number });

		if (number !== '') {
			this.setState({ searchButtonColor:'teal', searchButtonDisabled:false });
		} else {
			this.setState({ searchButtonColor:'grey', searchButtonDisabled:true });
		}
	}

	onlyDigits(str) {
		return str.replace(/[^0-9//]/g,'');
	}

	onClickSearchButton(event) {
		let request = {method: 'GET', headers:{token:this.props.token.token}};

		fetch('http://localhost:8080/services/card/search/'+this.state.searchString, request)
			.then(this.checkStatus)
			.then((response) => { return response.json() })
			.then(this.onClickSearchButtonCallback)
			.catch(this.onClickSearchButtonError);
	}

	onClickSearchButtonCallback(search) {
		if (search.status.code === 3) {
			this.setState({ cardList:search.cards });
		} else {
			alert(search.status.message);
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
									onChange={this.onChangeSearch}
									value={this.state.searchString}
								/>
								
								<Button size='large' color={this.state.searchButtonColor} disabled={this.state.searchButtonDisabled} onClick={this.onClickSearchButton}>Search</Button>

								
							</Segment>
						</Form>

						<CardTable cardList={this.state.cardList}/>

					</Grid.Column>
				</Grid.Row>
			</Grid>
		);
	}
}

export default TaskBoard;
