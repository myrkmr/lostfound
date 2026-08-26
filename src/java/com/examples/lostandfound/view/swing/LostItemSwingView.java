package com.examples.lostandfound.view.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.examples.lostandfound.controller.LostAndFoundController;
import com.examples.lostandfound.model.LostItem;
import com.examples.lostandfound.view.LostItemView;

public class LostItemSwingView extends JFrame implements LostItemView {

	private static final long serialVersionUID = 1L;

	private LostAndFoundController lostAndFoundController;
	private JList<LostItem> lostItemList;
	private DefaultListModel<LostItem> lostItemListModel;
	private JLabel errorMessageLabel;

	public LostItemSwingView() {
		setTitle("Lost and Found Management System");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JTextField idTextBox = createTextField("idTextBox");
		JTextField itemNameTextBox = createTextField("itemNameTextBox");
		JTextField descriptionTextBox = createTextField("descriptionTextBox");
		JTextField lostDateTextBox = createTextField("lostDateTextBox");

		JPanel formPanel = new JPanel(new GridLayout(4, 2));
		formPanel.add(new JLabel("id"));
		formPanel.add(idTextBox);
		formPanel.add(new JLabel("item name"));
		formPanel.add(itemNameTextBox);
		formPanel.add(new JLabel("description"));
		formPanel.add(descriptionTextBox);
		formPanel.add(new JLabel("lost date"));
		formPanel.add(lostDateTextBox);

		JButton addButton = new JButton("Add");
		addButton.setEnabled(false);
		addButton.addActionListener(event -> lostAndFoundController.newLostItem(
				new LostItem(idTextBox.getText(), descriptionTextBox.getText())));
		DocumentListener documentListener = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent event) {
				updateAddButton();
			}

			@Override
			public void removeUpdate(DocumentEvent event) {
				updateAddButton();
			}

			@Override
			public void changedUpdate(DocumentEvent event) {
				updateAddButton();
			}

			private void updateAddButton() {
				addButton.setEnabled(!idTextBox.getText().trim().isEmpty()
						&& !itemNameTextBox.getText().trim().isEmpty()
						&& !descriptionTextBox.getText().trim().isEmpty()
						&& !lostDateTextBox.getText().trim().isEmpty());
			}
		};
		idTextBox.getDocument().addDocumentListener(documentListener);
		itemNameTextBox.getDocument().addDocumentListener(documentListener);
		descriptionTextBox.getDocument().addDocumentListener(documentListener);
		lostDateTextBox.getDocument().addDocumentListener(documentListener);
		JButton deleteButton = new JButton("Delete Selected");
		deleteButton.setEnabled(false);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);

		lostItemListModel = new DefaultListModel<>();
		lostItemList = new JList<>(lostItemListModel);
		lostItemList.setName("lostItemList");
		lostItemList.setCellRenderer((list, lostItem, index, selected, focused) ->
				new DefaultListCellRenderer().getListCellRendererComponent(list,
						lostItem.getId() + " - " + lostItem.getDescription(), index, selected, focused));
		lostItemList.addListSelectionListener(
				event -> deleteButton.setEnabled(!lostItemList.isSelectionEmpty()));
		deleteButton.addActionListener(event ->
				lostAndFoundController.deleteLostItem(lostItemList.getSelectedValue()));

		errorMessageLabel = new JLabel(" ");
		errorMessageLabel.setName("errorMessageLabel");

		JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
		bottomPanel.add(buttonPanel);
		bottomPanel.add(errorMessageLabel);

		setLayout(new BorderLayout());
		add(formPanel, BorderLayout.NORTH);
		add(new JScrollPane(lostItemList), BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		pack();
	}

	public void setLostAndFoundController(LostAndFoundController lostAndFoundController) {
		this.lostAndFoundController = lostAndFoundController;
	}

	public void showAllLostItems(List<LostItem> lostItems) {
		lostItemListModel.clear();
		lostItemListModel.addAll(lostItems);
	}

	public void showError(String message, LostItem lostItem) {
		errorMessageLabel.setText(
				message + ": " + lostItem.getId() + " - " + lostItem.getDescription());
	}

	public void showErrorLostItemNotFound(String message, LostItem lostItem) {
		showError(message, lostItem);
		lostItemListModel.removeElement(lostItem);
	}

	public void lostItemAdded(LostItem lostItem) {
		lostItemListModel.addElement(lostItem);
		errorMessageLabel.setText(" ");
	}

	public void lostItemRemoved(LostItem lostItem) {
		lostItemListModel.removeElement(lostItem);
		errorMessageLabel.setText(" ");
	}

	private JTextField createTextField(String name) {
		JTextField textField = new JTextField(15);
		textField.setName(name);
		return textField;
	}
}
