package com.examples.lostandfound.model;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(description, id);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		LostItem other = (LostItem) object;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id);
	}
}
