package com.examples.lostandfound.model;

public class LostItem {

	private String id;
	private String description;

	public LostItem() {
	}

	public LostItem(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
