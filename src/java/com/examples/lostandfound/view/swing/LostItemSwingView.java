package com.examples.lostandfound.view.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.examples.lostandfound.controller.LostAndFoundController;
import com.examples.lostandfound.model.LostItem;

public class LostItemSwingView extends JFrame {

	private static final long serialVersionUID = 1L;

	private LostAndFoundController lostAndFoundController;

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
		JButton deleteButton = new JButton("Delete Selected");
		deleteButton.setEnabled(false);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);

		JList<LostItem> lostItemList = new JList<>();
		lostItemList.setName("lostItemList");

		JLabel errorMessageLabel = new JLabel(" ");
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

	private JTextField createTextField(String name) {
		JTextField textField = new JTextField(15);
		textField.setName(name);
		return textField;
	}
}
