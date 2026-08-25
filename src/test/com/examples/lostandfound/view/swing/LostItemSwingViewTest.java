package com.examples.lostandfound.view.swing;

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
}
