package InventoryDetailGUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import InventoryListGUI.InventoryView;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

public class InventoryDetailView extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> listModel;

	
	private JLabel lblQuan;

	private JButton btnSave;
	private JTextField txtfldQuan;
	private InventoryView view;
	
	ArrayList<String> itemNamesList;

	private JLabel lblUuid;
	private JTextField txtfldUuid;
	private JTextField txtfldItemId;
	private JLabel lblLocation;
	private JLabel lblitemId;
	
	private final InventoryDetailModel item;
	private JComboBox<String> itemNames;
	private JComboBox<String> itemLocation;

	private String[] locationNames={"facility 1 warehouse 1","facility 1 warehouse 2","facility 2","Unknown"};

	/**
	 * Create the frame.
	 */
	public InventoryDetailView(final InventoryView view, final InventoryDetailModel item, String buttonName) {
		this.item = item;
		this.setView(view);
		TitledBorder titleView;
		if (buttonName.equals("Save"))
			titleView = BorderFactory.createTitledBorder(item.getItemName() + " Detail View");
		else
			titleView = BorderFactory.createTitledBorder("New Inventory Entry Detail View");

		// sets bounds for the part window
		setBounds(100, 100, 363, 301);
	
		// observer creation
		
		this.setBorder(titleView);
		
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] { 33, 96, 115, 89, 0 };
		gbl_this.rowHeights = new int[] { 35, 31, 32, 30, 32, 33, 0, 33 };
		gbl_this.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_this.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0,
				1.0, 0.0, 1.0 };
		this.setLayout(gbl_this);

		lblUuid = new JLabel("UUID:");
		GridBagConstraints gbc_lblUuid = new GridBagConstraints();
		gbc_lblUuid.anchor = GridBagConstraints.WEST;
		gbc_lblUuid.insets = new Insets(0, 0, 5, 5);
		gbc_lblUuid.gridx = 1;
		gbc_lblUuid.gridy = 1;
		this.add(lblUuid, gbc_lblUuid);
		if (buttonName.equals("Save")) {
			txtfldUuid = new JTextField(item.getUuid() + "");
		} else
			txtfldUuid = new JTextField("Will Automatically be assigned");

		txtfldUuid.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		this.add(txtfldUuid, gbc_textField);
		txtfldUuid.setColumns(10);
		
		
		lblitemId = new JLabel("Item ID:");
		GridBagConstraints gbc_lblitemId = new GridBagConstraints();
		gbc_lblitemId.anchor = GridBagConstraints.WEST;
		gbc_lblitemId.insets = new Insets(0, 0, 5, 5);
		gbc_lblitemId.gridx = 1;
		gbc_lblitemId.gridy = 2;
		this.add(lblitemId, gbc_lblitemId);
		if (buttonName.equals("Save")) {
			txtfldItemId = new JTextField(item.getItemId() + "");
		} else
			txtfldItemId = new JTextField("Will Automatically be assigned");

		txtfldItemId.setEditable(false);
		GridBagConstraints gbc_itemIdField = new GridBagConstraints();
		gbc_itemIdField.gridwidth = 2;
		gbc_itemIdField.insets = new Insets(0, 0, 5, 0);
		gbc_itemIdField.fill = GridBagConstraints.BOTH;
		gbc_itemIdField.gridx = 2;
		gbc_itemIdField.gridy = 2;
		this.add(txtfldItemId, gbc_itemIdField);
		txtfldUuid.setColumns(10);

		// text field for the part number

		// unit of quantity text field

		// label for part name
		JLabel lblPartNm = new JLabel("Item Name:");
		GridBagConstraints gbc_lblPartNm = new GridBagConstraints();
		gbc_lblPartNm.anchor = GridBagConstraints.NORTH;
		gbc_lblPartNm.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPartNm.insets = new Insets(0, 0, 5, 5);
		gbc_lblPartNm.gridx = 1;
		gbc_lblPartNm.gridy = 3;
		this.add(lblPartNm, gbc_lblPartNm);
		
		// jcombobox for part names
		setItemNamesList();
		// get string to put into
		String[] entryNames = new String[itemNamesList.size()];
		entryNames = itemNamesList.toArray(entryNames);
		
		itemNames = new JComboBox<String>(entryNames);
		itemNames.setEditable(false);
		System.out.println("IN VIEW: " + item.getItemName());
		System.out.println("IN VIEW: " + item.getItemId());
		if (item.getItemName() != null){
		   System.out.println("MADE IT IN!!");
		   if(item.getItemId().toUpperCase().startsWith("A")){
			   System.out.println("MADE IT IN TOO!!");
			   String descript = item.getProductDescript(item.getItemId());
			   String selected = "product : " + item.getProductTemplateNum(descript) + " : " + descript;
			   System.out.println("SELECTED STRING AFTER IN->" + selected);
		       itemNames.setSelectedItem(selected);
		       //System.out.println("SELECTED1->" + itemNames.getSelectedItem().toString());
		   }else{
			   itemNames.setSelectedItem("part : " + item.getItemName());
		   }
		}
		System.out.println("SELECTED2->" + itemNames.getSelectedItem().toString());
		GridBagConstraints gbc_partNames = new GridBagConstraints();
		gbc_partNames.gridwidth = 2;
		gbc_partNames.fill = GridBagConstraints.BOTH;
		gbc_partNames.insets = new Insets(0, 0, 5, 0);
		gbc_partNames.gridx = 2;
		gbc_partNames.gridy = 3;
		this.add(itemNames, gbc_partNames);

		// quantity label field
		lblQuan = new JLabel("Quantity:");
		GridBagConstraints gbc_lblQuan = new GridBagConstraints();
		gbc_lblQuan.anchor = GridBagConstraints.NORTH;
		gbc_lblQuan.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblQuan.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuan.gridx = 1;
		gbc_lblQuan.gridy = 4;
		this.add(lblQuan, gbc_lblQuan);

		// quantity text field
		txtfldQuan = new JTextField((item.getQuantity() + ""));
		GridBagConstraints gbc_txtfldQuan = new GridBagConstraints();
		gbc_txtfldQuan.gridwidth = 2;
		gbc_txtfldQuan.fill = GridBagConstraints.BOTH;
		gbc_txtfldQuan.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldQuan.gridx = 2;
		gbc_txtfldQuan.gridy = 4;
		this.add(txtfldQuan, gbc_txtfldQuan);

		
		
		// Location label
		lblLocation = new JLabel("Location:");
		GridBagConstraints gbc_lblLocation = new GridBagConstraints();
		gbc_lblLocation.anchor = GridBagConstraints.NORTH;
		gbc_lblLocation.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocation.gridx = 1;
		gbc_lblLocation.gridy = 5;
		this.add(lblLocation, gbc_lblLocation);

		// Location text field
		itemLocation = new JComboBox<String>(getLocationNames());
		//partLocation.setEditable(true);
		
		GridBagConstraints gbc_txtfldLocation = new GridBagConstraints();
		gbc_txtfldLocation.gridwidth = 2;
		gbc_txtfldLocation.fill = GridBagConstraints.BOTH;
		gbc_txtfldLocation.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldLocation.gridx = 2;
		gbc_txtfldLocation.gridy = 5;
		this.add(itemLocation, gbc_txtfldLocation);
		
		if (buttonName.equals("Save"))
		{
			itemLocation.setSelectedItem(item.getLocation());
		}
		else
			itemLocation.setSelectedIndex(3);
		
		btnSave = new JButton(buttonName);
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 6;
		this.add(btnSave, gbc_btnSave);

		// System.out.println("Test Quan2!!: " + part.getQuantity());
		if (buttonName.equals("Save")) {

			// txtfldPartName.setEditable(false);

		}
		
		
	}

	// getters for the part view

	public JComboBox<String> getItemLocation() {
		return itemLocation;
	}

	public void setItemLocation(JComboBox<String> partLocation) {
		this.itemLocation = partLocation;
	}

	public JTextField getTxtfldQuan() {
		return txtfldQuan;
	}

	public JTextField getTxtfldItemId() {
		return txtfldItemId;
	}
	// repaints the values of the part view to display proper info
	public void repaintTextFields() {
		this.txtfldQuan.repaint();
		this.txtfldUuid.repaint();

	}

	public JComboBox<String> getItemNames() {
		return itemNames;
	}

	public void setPartNames(JComboBox<String> itemNames) {
		this.itemNames = itemNames;
	}

	// registers the item window listeners
	public void registerListerners(InventoryDetailController itemController) {
		btnSave.addActionListener(itemController);
	}

	public JTextComponent getTxtfldUuid() {
		return this.txtfldUuid;
	}

	public ArrayList<String> getItemNamesList() {
		return itemNamesList;
	}

	public void setItemNamesList() {
		this.itemNamesList = getItem().getItemListNames();
	}

	public InventoryView getView() {
		return view;
	}

	public void setView(InventoryView view) {
		this.view = view;
	}

	public String[] getLocationNames() {
		return locationNames;
	}

	public void setLocationNames(String[] locationNames) {
		this.locationNames = locationNames;
	}

	public InventoryDetailModel getItem() {
		return item;
	}
}