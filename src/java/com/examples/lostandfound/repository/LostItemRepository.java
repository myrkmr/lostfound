package com.examples.lostandfound.repository;

import java.util.List;

import com.examples.lostandfound.model.LostItem;

public interface LostItemRepository {

	List<LostItem> findAll();

	LostItem findById(String id);

	void save(LostItem lostItem);
}
