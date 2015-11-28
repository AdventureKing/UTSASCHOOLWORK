package InventoryListGUI;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class InventoryView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int gobalX = 800;
	private int gobalY = 400;
	private InventoryModel inventoryModel;
	private ArrayList<Integer> currentOpenInventory = new ArrayList<Integer>();

	//private JMenuBar menuBar;
	private JMenu mnFile;
	private JTable itemList;

	private DefaultTableModel tableModel;

	/**
	 * Create the frame.
	 */
	public InventoryView(InventoryModel inventoryModel) {

		this.inventoryModel = inventoryModel;
		
	
		TitledBorder titleWordList = BorderFactory
				.createTitledBorder("Items In Inventory");

		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] { 530, 220, 0, 0, 0 };
		gbl_this.rowHeights = new int[] { 120, 0, 50, 66, 0 };
		gbl_this.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_this.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		this.setLayout(gbl_this);
		itemList = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		itemList.getTableHeader().setReorderingAllowed(false);
		itemList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane scroll = new JScrollPane(getItemList());
		scroll.setBorder(titleWordList);
		GridBagConstraints gbc_scroll = new GridBagConstraints();
		gbc_scroll.gridwidth = 2;
		gbc_scroll.fill = GridBagConstraints.BOTH;
		gbc_scroll.insets = new Insets(0, 0, 5, 5);
		gbc_scroll.gridheight = 4;
		gbc_scroll.gridx = 0;
		gbc_scroll.gridy = 0;
		this.add(scroll, gbc_scroll);

		// Edit part Button
		JButton btnViewPart = new JButton("Edit Inventory Item");
		btnViewPart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Add part Button for inventory view
		JButton btnAddPart = new JButton("Add Inventory Item");
		btnAddPart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	
		//Add Part button
		GridBagConstraints gbc_btnAddPart = new GridBagConstraints();
		gbc_btnAddPart.anchor = GridBagConstraints.SOUTH;
		gbc_btnAddPart.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddPart.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddPart.gridx = 2;
		gbc_btnAddPart.gridy = 1;
		this.add(btnAddPart, gbc_btnAddPart);

		// view part list button
		GridBagConstraints gbc_btnViewPart = new GridBagConstraints();
		gbc_btnViewPart.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewPart.anchor = GridBagConstraints.SOUTH;
		gbc_btnViewPart.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnViewPart.gridx = 2;
		gbc_btnViewPart.gridy = 2;
		this.add(btnViewPart, gbc_btnViewPart);

		// Delete Part Button
		JButton btnDeletePart = new JButton("Delete Inventory Item");
		GridBagConstraints gbc_btnDeletePart = new GridBagConstraints();
		gbc_btnDeletePart.insets = new Insets(0, 0, 0, 5);
		gbc_btnDeletePart.gridx = 2;
		gbc_btnDeletePart.gridy = 3;
		this.add(btnDeletePart, gbc_btnDeletePart);
		btnDeletePart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// populate list
		this.updateRow();
	}

	// Registers the listeners
	public void registerListerners(InventoryController inventoryController) {
		Component[] components = this.getComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(inventoryController);
			}
		
			
		}
		itemList.addMouseListener(inventoryController);
	}

	// grabs the current state of the model
	public void updateModel(InventoryModel model) {
		this.inventoryModel = model;
	}

	// Returns the partlist from the view table
	public JTable getItemList() {
		return itemList;
	}

	// changes the view table partlist
	public void setItemList(JTable itemList) {
		this.itemList = itemList;
	}

	// updates the rows for our JTable
	public void updateRow() {
		itemList.setModel(new DefaultTableModel(inventoryModel.getData(), inventoryModel.getColumnNames()));
	}

	// determines if multiple attempts at creating views of the same part
	public boolean isopen(int itemUuid) {
		if (getCurrentOpenInventory().isEmpty())
			return false;
		for (int itemName : getCurrentOpenInventory()) {
			if (itemName == itemUuid) {
				return true;
			}
		}
		return false;
	}

	// opens the view of the selected part
	public void addPartView(Integer partSelected) {
		getCurrentOpenInventory().add(partSelected);
	}

	public void removePart(int uuid) {
		
		currentOpenInventory.remove((Integer) uuid);
	}

	public ArrayList<Integer> getCurrentOpenInventory() {
		return currentOpenInventory;
	}

	public void setCurrentOpenInventory(ArrayList<Integer> currentOpenInventory) {
		this.currentOpenInventory = currentOpenInventory;
	}
}
