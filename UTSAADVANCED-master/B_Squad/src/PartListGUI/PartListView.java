package PartListGUI;

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

public class PartListView extends JPanel{

	private int gobalX = 800;
	private int gobalY = 400;
	private PartListModel partListModel;
	private ArrayList<Integer> currentOpenPart = new ArrayList<Integer>();
	

	private JTable partList;

	private DefaultTableModel tableModel;

	/**
	 * Create the frame.
	 */
	public PartListView(PartListModel partListModel) {

		System.out.println("wtf!");

		this.partListModel = partListModel;

		

		// menu bar for file and settings
	

		// file on menu bar
		
		// exit menu item
		

		// creates new content Pane
		
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
	

		// Creates the table and border
		TitledBorder titleWordList = BorderFactory.createTitledBorder("Parts List");

		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] { 530, 220, 0, 0, 0 };
		gbl_this.rowHeights = new int[] { 120, 0, 50, 66, 0 };
		gbl_this.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_this.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_this);
		
		partList = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		partList.getTableHeader().setReorderingAllowed(false);
		partList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane scroll = new JScrollPane(getPartList());
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
		JButton btnViewPart = new JButton("Edit Part");
		btnViewPart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		// Add part Button for inventory view
		JButton btnAddPart = new JButton("Add Part");
		btnAddPart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
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
		JButton btnDeletePart = new JButton("Delete Part");
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
	public void registerListerners(PartListController partListController) {
		Component[] components = this.getComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(partListController);
			}
			
		}
		partList.addMouseListener(partListController);
	}

	// grabs the current state of the model
	public void updateModel(PartListModel model) {
		this.partListModel = model;
	}

	// Returns the part list from the view table
	public JTable getPartList() {
		return partList;
	}

	// changes the view table partlist
	public void setPartList(JTable partList) {
		this.partList = partList;
	}

	// updates the rows for our JTable
	public void updateRow() {
		partList.setModel(new DefaultTableModel(partListModel.getData(), partListModel.getColumnNames()));
	}

	// determines if multiple attempts at creating views of the same part
	public boolean isopen(int partUuid) {
		if (getCurrentOpenPart().isEmpty())
			return false;
		for (int partName : getCurrentOpenPart()) {
			if (partName == partUuid) {
				return true;
			}
		}
		return false;
	}

	// opens the view of the selected part
	public void addPartDetailView(Integer partSelected) {
		getCurrentOpenPart().add(partSelected);
	}

	public void removePart(int uuid) {	
		currentOpenPart.remove((Integer) uuid);
	}

	public ArrayList<Integer> getCurrentOpenPart() {
		return currentOpenPart;
	}

	public void setCurrentOpenInventory(ArrayList<Integer> currentOpenPart) {
		this.currentOpenPart = currentOpenPart;
	}
}
