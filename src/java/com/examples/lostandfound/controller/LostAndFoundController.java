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
		if (lostItemRepository.findById(lostItem.getId()) == null) {
			lostItemRepository.save(lostItem);
			lostItemView.lostItemAdded(lostItem);
		}
	}
}
