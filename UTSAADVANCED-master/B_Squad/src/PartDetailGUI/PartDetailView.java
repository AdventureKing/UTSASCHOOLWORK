package PartDetailGUI;

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
import java.util.ArrayList;

import PartListGUI.PartListView;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

public class PartDetailView extends JPanel {

	private int gobalX = 400;
	private int gobalY = 300;
	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> listModel;
	private ArrayList<String> partNamesList;
	private PartListView view;
	
	
	private JButton btnSave;
	private JLabel lblUuid;
	private JTextField txtfldUuid;
	private JTextField txtfldPartNum;
	private JTextField txtfldVendor;
	private JTextField txtfldQuanUnit;
	private JTextField txtfldExtPartNum;
	private JTextField txtfldPartName;
	
	private final PartDetailModel part;
	private JComboBox<String> partNames;

	/**
	 * Create the frame.
	 */
	public PartDetailView(final PartListView view, final PartDetailModel part, String buttonName) {
		
		this.part = part;
		this.view = view;
		TitledBorder titleView;
		if (buttonName.equals("Save"))
			titleView = BorderFactory.createTitledBorder(part.getPartName()
					+ " Detail View");
		else
			titleView = BorderFactory
					.createTitledBorder("New Inventory Entry Detail View");

	
		
		// observer creation
		

		this.setBorder(titleView);
		
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] { 33, 96, 115, 89, 0 };
		gbl_this.rowHeights = new int[] { 25, 27, 32, 30, 32, 33, 39, 33 };
		gbl_this.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_this.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0 };
		this.setLayout(gbl_this);

		
		//label for Uuid
		lblUuid = new JLabel("UUID:");
		GridBagConstraints gbc_lblUuid = new GridBagConstraints();
		gbc_lblUuid.anchor = GridBagConstraints.WEST;
		gbc_lblUuid.insets = new Insets(0, 0, 5, 5);
		gbc_lblUuid.gridx = 1;
		gbc_lblUuid.gridy = 1;
		this.add(lblUuid, gbc_lblUuid);
		
		//text field for Uuid
		if (buttonName.equals("Save")) {
			txtfldUuid = new JTextField(part.getUuid() + "");
		} else
			txtfldUuid = new JTextField("Will Automatically be assigned");
		
		//cant edit the uuid
		txtfldUuid.setEditable(false);
		
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		txtfldUuid.setColumns(10);
		this.add(txtfldUuid, gbc_textField);
		

		// label for the part number
		JLabel lblPartNum = new JLabel("Part Number:");
		GridBagConstraints gbc_lblPartNum = new GridBagConstraints();
		gbc_lblPartNum.anchor = GridBagConstraints.NORTH;
		gbc_lblPartNum.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPartNum.insets = new Insets(0, 0, 5, 5);
		gbc_lblPartNum.gridx = 1;
		gbc_lblPartNum.gridy = 2;
		this.add(lblPartNum, gbc_lblPartNum);

		// text field for part number
		txtfldPartNum = new JTextField((part.getPartNum() + ""));
		GridBagConstraints gbc_txtfldPartNum = new GridBagConstraints();
		gbc_txtfldPartNum.gridwidth = 2;
		gbc_txtfldPartNum.fill = GridBagConstraints.BOTH;
		gbc_txtfldPartNum.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldPartNum.gridx = 2;
		gbc_txtfldPartNum.gridy = 2;
		this.add(txtfldPartNum, gbc_txtfldPartNum);
		
		// label for the part name
		JLabel lblPartName = new JLabel("Part Name:");
		GridBagConstraints gbc_lblPartName = new GridBagConstraints();
		gbc_lblPartName.anchor = GridBagConstraints.NORTH;
		gbc_lblPartName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPartName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPartName.gridx = 1;
		gbc_lblPartName.gridy = 3;
		this.add(lblPartName, gbc_lblPartName);
		
		// text field for part number
		txtfldPartName = new JTextField((part.getPartName() + ""));
		GridBagConstraints gbc_txtfldPartName = new GridBagConstraints();
		gbc_txtfldPartName.gridwidth = 2;
		gbc_txtfldPartName.fill = GridBagConstraints.BOTH;
		gbc_txtfldPartName.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldPartName.gridx = 2;
		gbc_txtfldPartName.gridy = 3;
		this.add(txtfldPartName, gbc_txtfldPartName);

		// vendor label field
		JLabel lblVend = new JLabel("Vendor:");
		GridBagConstraints gbc_lblVend = new GridBagConstraints();
		gbc_lblVend.anchor = GridBagConstraints.NORTH;
		gbc_lblVend.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblVend.insets = new Insets(0, 0, 5, 5);
		gbc_lblVend.gridx = 1;
		gbc_lblVend.gridy = 4;
		this.add(lblVend, gbc_lblVend);

		// vendor text field
		txtfldVendor = new JTextField((part.getVendor() + ""));
		GridBagConstraints gbc_txtfldVendor = new GridBagConstraints();
		gbc_txtfldVendor.gridwidth = 2;
		gbc_txtfldVendor.fill = GridBagConstraints.BOTH;
		gbc_txtfldVendor.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldVendor.gridx = 2;
		gbc_txtfldVendor.gridy = 4;
		this.add(txtfldVendor, gbc_txtfldVendor);

		//quanitity unit label
		JLabel lblQuanUnit = new JLabel("Unit of Quantity:");
		GridBagConstraints gbc_lblQU = new GridBagConstraints();
		gbc_lblQU.anchor = GridBagConstraints.NORTH;
		gbc_lblQU.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblQU.insets = new Insets(0, 0, 5, 5);
		gbc_lblQU.gridx = 1;
		gbc_lblQU.gridy = 5;
		this.add(lblQuanUnit, gbc_lblQU);

		// quantity unit text field
		txtfldQuanUnit = new JTextField((part.getQuanUnit() + ""));
		GridBagConstraints gbc_txtfldQU = new GridBagConstraints();
		gbc_txtfldQU.gridwidth = 2;
		gbc_txtfldQU.fill = GridBagConstraints.BOTH;
		gbc_txtfldQU.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldQU.gridx = 2;
		gbc_txtfldQU.gridy = 5;
		this.add(txtfldQuanUnit, gbc_txtfldQU);
		
		//external part num label
		JLabel lblExtPartNum = new JLabel("Ext. Part#:");
		GridBagConstraints gbc_lblExt = new GridBagConstraints();
		gbc_lblExt.anchor = GridBagConstraints.NORTH;
		gbc_lblExt.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblExt.insets = new Insets(0, 0, 5, 5);
		gbc_lblExt.gridx = 1;
		gbc_lblExt.gridy = 6;
		this.add(lblExtPartNum, gbc_lblExt);

		// quantity unit text field
		txtfldExtPartNum = new JTextField((part.getExtPartNum() + ""));
		GridBagConstraints gbc_txtfldExt = new GridBagConstraints();
		gbc_txtfldExt.gridwidth = 2;
		gbc_txtfldExt.fill = GridBagConstraints.BOTH;
		gbc_txtfldExt.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldExt.gridx = 2;
		gbc_txtfldExt.gridy = 6;
		this.add(txtfldExtPartNum, gbc_txtfldExt);
		

		btnSave = new JButton(buttonName);
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 7;
		this.add(btnSave, gbc_btnSave);
	}

	// getters for the part view



	public PartListView getView() {
		return view;
	}

	public void setView(PartListView view) {
		this.view = view;
	}


	
	// repaints the values of the part view to display proper info
	public void repaintTextFields() {
		this.txtfldPartName.repaint();
		this.txtfldPartNum.repaint();
		this.txtfldVendor.repaint();
		this.txtfldQuanUnit.repaint();
		this.txtfldExtPartNum.repaint();
		this.txtfldPartName.repaint();
	}

	public JComboBox<String> getPartNames() {
		return partNames;
	}

	public void setPartNames(JComboBox<String> partNames) {
		this.partNames = partNames;
	}
	
	// registers the part window listeners
	public void registerListerners(PartDetailController partController) {
		btnSave.addActionListener(partController);
	}


	public ArrayList<String> getPartNamesList() {
		return partNamesList;
	}
	
	//pull from database
	public void setPartNamesList() {
		this.partNamesList = getPart().getPartListNames();
	}
	
	public JTextField getTxtfldVendor() {return txtfldVendor;}
	public JTextField getTxtfldPartNum() {return txtfldPartNum;}
	public JTextField getTxtfldPartName() {return txtfldPartName;}
	public JTextField getTxtfldExtPartNum() {return txtfldExtPartNum;}
	public JTextField getTxtfldQuanUnit() {return txtfldQuanUnit;}
	public JTextComponent getTxtfldUuid() {return this.txtfldUuid;}

	public PartDetailModel getPart() {
		return part;
	}
}
