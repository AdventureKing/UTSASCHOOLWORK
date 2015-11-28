package TemplatePartsGUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import TemplateDetailGUI.TemplateDetailController;
import TemplateDetailGUI.TemplateDetailModel;
import TemplateGUI.TemplateView;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TemplatePartsView extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> listModel = new DefaultListModel<String>();

	ArrayList<String> partNamesList;
	
	private JButton btnAddPart;
	private JButton btnEditPart;
	private JButton btnDeletePart;
	
	
	//changes to get list to stop cause dropped
	private JList<String> list = new JList<String>();
	private TemplatePartsModel templatePartsModel;
	
	
	private ArrayList<String> parts;
	private String[] data;


	/**
	 * Create the frame.
	 */
	public TemplatePartsView(final TemplatePartsModel templatePmodel) {
		this.templatePartsModel = templatePmodel;
		
		TitledBorder titleView;
	
		titleView = BorderFactory.createTitledBorder(templatePartsModel.getTemplateNum() + " Part Requirements");

		// sets bounds for the part window
		setBounds(100, 100, 363, 301);
	
		// observer creation
		
		this.setBorder(titleView);
		
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] { 109, 117, 131, 0 };
		gbl_this.rowHeights = new int[] { 35, 31, 32, 30, 32, 23, 45, 33 };
		gbl_this.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_this.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0 };
		this.setLayout(gbl_this);

		
		//Parts List
		parts = templatePartsModel.getParts();
		updateList(parts);
		
		
		
		
		
		getList().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		getList().setLayoutOrientation(JList.HORIZONTAL_WRAP);
		GridBagConstraints gbc_partList = new GridBagConstraints();
		gbc_partList.gridwidth = 3;
		gbc_partList.gridheight = 6;
		gbc_partList.fill = GridBagConstraints.BOTH;
		gbc_partList.insets = new Insets(0, 0, 5, 0);
		gbc_partList.gridx = 0;
		gbc_partList.gridy = 0;
		this.add(getList(), gbc_partList);
		

		btnAddPart = new JButton("Add Part");
		GridBagConstraints gbc_btnAddPart = new GridBagConstraints();
		gbc_btnAddPart.fill = GridBagConstraints.BOTH;
		gbc_btnAddPart.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddPart.gridx = 0;
		gbc_btnAddPart.gridy = 6;
		this.add(btnAddPart, gbc_btnAddPart);
		
		btnEditPart = new JButton("Edit Part");
		GridBagConstraints gbc_btnEditPart = new GridBagConstraints();
		gbc_btnEditPart.fill = GridBagConstraints.BOTH;
		gbc_btnEditPart.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditPart.gridx = 1;
		gbc_btnEditPart.gridy = 6;
		this.add(btnEditPart, gbc_btnEditPart);
		
		btnDeletePart = new JButton("Delete Part");
		btnDeletePart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_btnDeletePart = new GridBagConstraints();
		gbc_btnDeletePart.fill = GridBagConstraints.BOTH;
		gbc_btnDeletePart.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeletePart.gridx = 2;
		gbc_btnDeletePart.gridy = 6;
		this.add(btnDeletePart, gbc_btnDeletePart);
		
		
	}

	// registers the part window listeners
	public void registerListeners(TemplatePartsController tempController) {
		btnAddPart.addActionListener(tempController);
		btnEditPart.addActionListener(tempController);
		btnDeletePart.addActionListener(tempController);
	}

	public JList<String> getList() {
		return list;
	}
	
	public void setModel(TemplatePartsModel model){
		this.templatePartsModel = model;
	}
	
	public void updateList(ArrayList<String> parts){
		
		
		//this stays
		listModel.removeAllElements();
		
		
		int length = parts.size();
		data = new String[length];
		int i = 0;
		for (String part : parts) {
		   System.out.println("TEST: " + part);
	     
	     listModel.addElement(part);
	       i++;
	    }
		list.setModel(listModel);

	}

	public void setList(JList<String> list) {
		
		
		//comments
		this.list = list;
		list.setModel(listModel);
	}
	
	
	public void callFromController(){
		parts = templatePartsModel.getParts();
		updateList(parts);
	
		
	}
	
	public void updatePartsStrings(){

		parts = templatePartsModel.getParts();
		updateList(parts);
	

	}

}
