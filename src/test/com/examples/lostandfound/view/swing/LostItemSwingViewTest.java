package com.examples.lostandfound.view.swing;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.DefaultListModel;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.examples.lostandfound.controller.LostAndFoundController;
import com.examples.lostandfound.model.LostItem;

@RunWith(GUITestRunner.class)
public class LostItemSwingViewTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private LostItemSwingView lostItemSwingView;

	@Mock
	private LostAndFoundController lostAndFoundController;

	private AutoCloseable closeable;

	@Override
	protected void onSetUp() {
		closeable = MockitoAnnotations.openMocks(this);
		lostItemSwingView = GuiActionRunner.execute(() -> {
			LostItemSwingView view = new LostItemSwingView();
			view.setLostAndFoundController(lostAndFoundController);
			return view;
		});
		window = new FrameFixture(robot(), lostItemSwingView);
		window.show();
	}

	@Override
	protected void onTearDown() throws Exception {
		closeable.close();
	}

	@Test
	@GUITest
	public void testControlsInitialStates() {
		window.label(JLabelMatcher.withText("id"));
		window.textBox("idTextBox").requireEnabled();
		window.label(JLabelMatcher.withText("item name"));
		window.textBox("itemNameTextBox").requireEnabled();
		window.label(JLabelMatcher.withText("description"));
		window.textBox("descriptionTextBox").requireEnabled();
		window.label(JLabelMatcher.withText("lost date"));
		window.textBox("lostDateTextBox").requireEnabled();
		window.button(JButtonMatcher.withText("Add")).requireDisabled();
		window.list("lostItemList");
		window.button(JButtonMatcher.withText("Delete Selected")).requireDisabled();
		window.label("errorMessageLabel").requireText(" ");
	}

	@Test
	@GUITest
	public void testAddButtonIsEnabledWhenAllFieldsAreNonBlank() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("itemNameTextBox").enterText("Wallet");
		window.textBox("descriptionTextBox").enterText("Black wallet");
		window.textBox("lostDateTextBox").enterText("2026-08-26");
		window.button(JButtonMatcher.withText("Add")).requireEnabled();
	}

	@Test
	@GUITest
	public void testAddButtonIsDisabledWhenAFieldIsBlank() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("itemNameTextBox").enterText("Wallet");
		window.textBox("descriptionTextBox").enterText(" ");
		window.textBox("lostDateTextBox").enterText("2026-08-26");
		window.button(JButtonMatcher.withText("Add")).requireDisabled();
	}

	@Test
	@GUITest
	@SuppressWarnings("unchecked")
	public void testDeleteButtonIsEnabledOnlyWhenAnItemIsSelected() {
		DefaultListModel<LostItem> lostItems = new DefaultListModel<>();
		lostItems.addElement(new LostItem("1", "Wallet"));
		GuiActionRunner.execute(() -> window.list("lostItemList").target()
				.setModel(lostItems));
		window.list("lostItemList").selectItem(0);
		window.button(JButtonMatcher.withText("Delete Selected")).requireEnabled();
		window.list("lostItemList").clearSelection();
		window.button(JButtonMatcher.withText("Delete Selected")).requireDisabled();
	}

	@Test
	@GUITest
	public void testShowAllLostItemsAddsItemDescriptionsToTheList() {
		LostItem firstLostItem = new LostItem("1", "Wallet");
		LostItem secondLostItem = new LostItem("2", "Keys");
		GuiActionRunner.execute(
				() -> lostItemSwingView.showAllLostItems(asList(firstLostItem, secondLostItem)));
		assertThat(window.list("lostItemList").contents())
				.containsExactly("1 - Wallet", "2 - Keys");
	}

	@Test
	@GUITest
	public void testShowErrorDisplaysMessageAndLostItem() {
		LostItem lostItem = new LostItem("1", "Wallet");
		GuiActionRunner.execute(() -> lostItemSwingView.showError("error message", lostItem));
		window.label("errorMessageLabel").requireText("error message: 1 - Wallet");
	}
}
