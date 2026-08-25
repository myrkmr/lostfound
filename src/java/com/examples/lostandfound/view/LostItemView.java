package com.examples.lostandfound.view;

import java.util.List;

import com.examples.lostandfound.model.LostItem;

public interface LostItemView {

	void showAllLostItems(List<LostItem> lostItems);

	void lostItemAdded(LostItem lostItem);

	void showError(String message, LostItem lostItem);

	void lostItemRemoved(LostItem lostItem);
}
