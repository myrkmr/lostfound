package com.examples.lostandfound.controller;

import com.examples.lostandfound.model.LostItem;
import com.examples.lostandfound.repository.LostItemRepository;
import com.examples.lostandfound.view.LostItemView;

public class LostAndFoundController {

	private final LostItemRepository lostItemRepository;
	private final LostItemView lostItemView;

	public LostAndFoundController(LostItemRepository lostItemRepository, LostItemView lostItemView) {
		this.lostItemRepository = lostItemRepository;
		this.lostItemView = lostItemView;
	}

	public void allLostItems() {
		lostItemView.showAllLostItems(lostItemRepository.findAll());
	}

	public void newLostItem(LostItem lostItem) {
		LostItem existingLostItem = lostItemRepository.findById(lostItem.getId());
		if (existingLostItem != null) {
			lostItemView.showError("Already existing lost item with id " + lostItem.getId(), existingLostItem);
			return;
		}
		lostItemRepository.save(lostItem);
		lostItemView.lostItemAdded(lostItem);
	}
}
