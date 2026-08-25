package com.examples.lostandfound.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.examples.lostandfound.model.LostItem;
import com.examples.lostandfound.repository.LostItemRepository;
import com.examples.lostandfound.view.LostItemView;

public class LostAndFoundControllerTest {

	@Mock
	private LostItemRepository lostItemRepository;

	@Mock
	private LostItemView lostItemView;

	@InjectMocks
	private LostAndFoundController lostAndFoundController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testAllLostItems() {
		List<LostItem> lostItems = asList(new LostItem());
		when(lostItemRepository.findAll())
			.thenReturn(lostItems);
		lostAndFoundController.allLostItems();
		verify(lostItemView)
			.showAllLostItems(lostItems);
	}

	@Test
	public void testNewLostItemWhenLostItemDoesNotAlreadyExist() {
		LostItem lostItem = new LostItem("1", "test");
		when(lostItemRepository.findById("1"))
			.thenReturn(null);
		lostAndFoundController.newLostItem(lostItem);
		InOrder inOrder = inOrder(lostItemRepository, lostItemView);
		inOrder.verify(lostItemRepository).save(lostItem);
		inOrder.verify(lostItemView).lostItemAdded(lostItem);
	}

	@Test
	public void testNewLostItemWhenLostItemAlreadyExists() {
		LostItem lostItemToAdd = new LostItem("1", "test");
		LostItem existingLostItem = new LostItem("1", "description");
		when(lostItemRepository.findById("1"))
			.thenReturn(existingLostItem);
		lostAndFoundController.newLostItem(lostItemToAdd);
		verify(lostItemView)
			.showError("Already existing lost item with id 1", existingLostItem);
		verifyNoMoreInteractions(ignoreStubs(lostItemRepository));
	}
}
