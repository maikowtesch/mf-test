import React, { Component } from 'react';
import { Table } from 'semantic-ui-react';
import 'semantic-ui/dist/semantic.min.css';


class CardTable extends Component {
	constructor(props) {
		super(props);
	}


	render() {
		const listItems = this.props.cardList.map((card) =>
							<Table.Row>
								<Table.Cell>{card.cardNumber}</Table.Cell>
								<Table.Cell>{card.holderName}</Table.Cell>
								<Table.Cell>{card.expiryDate}</Table.Cell>
							</Table.Row>
							);

		return (
			<Table celled>
				<Table.Header>
					<Table.Row>
						<Table.HeaderCell>Card Number</Table.HeaderCell>
						<Table.HeaderCell>Card Holder Name</Table.HeaderCell>
						<Table.HeaderCell>Expiry Date</Table.HeaderCell>
					</Table.Row>
				</Table.Header>

				<Table.Body>
					{listItems}
				</Table.Body>
			</Table>
		);
	}
}

export default CardTable;

