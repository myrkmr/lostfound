package com.examples.lostandfound.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
}
