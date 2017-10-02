package com.mftest.dto;

import java.util.List;

import com.mftest.core.responsemodel.CardInfo;

/**
 * Class representing Card search results.
 *
 */
public class SearchResult {
	Status status;
	List<CardInfo> cards;
	
	public SearchResult(Status status, List<CardInfo> cards) {
		this.status = status;
		this.cards = cards;
	}

	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public List<CardInfo> getCards() {
		return cards;
	}
	
	public void setCards(List<CardInfo> cards) {
		this.cards = cards;
	}
	
}
