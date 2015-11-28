package TemplatePartsDetailGUI;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;






import PartListGUI.PartListView;
import TemplateGUI.TemplateModel;
import TemplatePartsGUI.TemplatePartsModel;

public class TemplatePartsDetailView extends JPanel {
	private int gobalX = 400;
	private int gobalY = 300;
	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> listModel;
	private ArrayList<String> partNamesList;
	private PartListView view;
	
	
	private JButton btnSave;
	private JLabel lblUuid;
	private JTextField txtfldUuid;
	private JTextField txtfldPartId;
	private JTextField txtfldQuan;
	private JTextField txtfldProductTemplateId;
	
	private ArrayList<String> partNumsList;
	private final TemplatePartsDetailModel part;
	private JComboBox<String> partNumbers;
	private TemplatePartsModel model;
	
	public TemplatePartsDetailView(TemplatePartsModel model, String buttonName,TemplatePartsDetailModel part){
		this.part = part;
		this.setModel(model);
		
		
		// observer creation
		

	
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] { 33, 96, 115, 89, 0 };
		gbl_this.rowHeights = new int[] { 25, 27, 32, 30, 32, 33, 39, 33 };
		gbl_this.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_this.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0 };
		this.setLayout(gbl_this);
				
		//label for Uuid
		lblUuid = new JLabel("Template Number:");
		GridBagConstraints gbc_lblUuid = new GridBagConstraints();
		gbc_lblUuid.anchor = GridBagConstraints.WEST;
		gbc_lblUuid.insets = new Insets(0, 0, 5, 5);
		gbc_lblUuid.gridx = 1;
		gbc_lblUuid.gridy = 3;
		this.add(lblUuid, gbc_lblUuid);
		
		// text field for part number
		txtfldProductTemplateId = new JTextField((part.getTemplateNum() + ""));
		txtfldProductTemplateId.setEditable(false);
		GridBagConstraints gbc_txtfldProductTemplateId = new GridBagConstraints();
		gbc_txtfldProductTemplateId.gridwidth = 2;
		gbc_txtfldProductTemplateId.fill = GridBagConstraints.BOTH;
		gbc_txtfldProductTemplateId.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldProductTemplateId.gridx = 2;
		gbc_txtfldProductTemplateId.gridy = 3;
		this.add(txtfldProductTemplateId, gbc_txtfldProductTemplateId);

		
		JLabel lblPartId = new JLabel("Part Number:");
		GridBagConstraints gbc_lblPartId = new GridBagConstraints();
		gbc_lblPartId.anchor = GridBagConstraints.NORTH;
		gbc_lblPartId.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPartId.insets = new Insets(0, 0, 5, 5);
		gbc_lblPartId.gridx = 1;
		gbc_lblPartId.gridy = 4;
		this.add(lblPartId, gbc_lblPartId);
		
		if(buttonName == "Add"){
		
			partNumsList = part.getAllPartNumbers();
			String[] entryNumbers = new String[partNumsList.size()];
			entryNumbers = partNumsList.toArray(entryNumbers);	
			partNumbers = new JComboBox<String>(entryNumbers);
			partNumbers.setEditable(false);
			if (part.getPartNum() != null)
				partNumbers.setSelectedItem(part.getPartNum());
			GridBagConstraints gbc_partNums = new GridBagConstraints();
			gbc_partNums.gridwidth = 2;
			gbc_partNums.fill = GridBagConstraints.BOTH;
			gbc_partNums.insets = new Insets(0, 0, 5, 0);
			gbc_partNums.gridx = 2;
			gbc_partNums.gridy = 4;
			this.add(partNumbers, gbc_partNums);
		}else {
			txtfldPartId = new JTextField((part.getPartNum() + ""));
			txtfldPartId.setEditable(false);
			GridBagConstraints gbc_txtfldPartId = new GridBagConstraints();
			gbc_txtfldPartId.gridwidth = 2;
			gbc_txtfldPartId.fill = GridBagConstraints.BOTH;
			gbc_txtfldPartId.insets = new Insets(0, 0, 5, 0);
			gbc_txtfldPartId.gridx = 2;
			gbc_txtfldPartId.gridy = 4;
			this.add(txtfldPartId, gbc_txtfldPartId);
		}

		//quanitity unit label
		JLabel lblQuan = new JLabel("Quantity:");
		GridBagConstraints gbc_lblQuan = new GridBagConstraints();
		gbc_lblQuan.anchor = GridBagConstraints.NORTH;
		gbc_lblQuan.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblQuan.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuan.gridx = 1;
		gbc_lblQuan.gridy = 5;
		this.add(lblQuan, gbc_lblQuan);

		// quantity unit text field
		txtfldQuan = new JTextField((part.getQuantity() + ""));
		GridBagConstraints gbc_txtfldQuan = new GridBagConstraints();
		gbc_txtfldQuan.gridwidth = 2;
		gbc_txtfldQuan.fill = GridBagConstraints.BOTH;
		gbc_txtfldQuan.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldQuan.gridx = 2;
		gbc_txtfldQuan.gridy = 5;
		this.add(txtfldQuan, gbc_txtfldQuan);
		

		btnSave = new JButton(buttonName);
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 7;
		this.add(btnSave, gbc_btnSave);
	}
	
	public JTextField getTxtfldQuan() {
		return txtfldQuan;
	}
	
	public JTextField getTxtfldUuid() {
		return txtfldUuid;
	}
	
	public JComboBox<String> getPartNumbers() {
		return partNumbers;
	}
	
	public JTextField getTxtfldPartId() {
		return txtfldPartId;
	}
	
	public JTextField getTxtfldProductTemplateId() {
		return txtfldProductTemplateId;
	}
	
	public void updateModel(TemplatePartsModel model) {
		this.setModel(model);
	}

	public void registerListerners(TemplatePartsDetailController detailcontroller) {
		btnSave.addActionListener(detailcontroller);
	}

	public TemplatePartsDetailModel getModel() {
		return part;
	}

	public void setModel(TemplatePartsModel model) {
		this.model = model;
	}
}