package TemplateDetailGUI;

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

import TemplateGUI.TemplateView;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

public class TemplateDetailView extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> listModel;

	
	
	private JButton btnSave;
	private JButton btnViewParts;
	private TemplateView view;
	
	private final TemplateDetailModel template;
	
	private JLabel templateUuid;
	private JTextField txtfldUuid;
	
	private JLabel lblTemplateNm;
	private JTextField txtTemplateNm;
	
	private JLabel lblDescription;
	private JTextField txtfldTemplateDescript;

	/**
	 * Create the frame.
	 */
	public TemplateDetailView(final TemplateView view, final TemplateDetailModel template, String buttonName) {
		this.template = template;
		this.setView(view);
		TitledBorder titleView;
		if (buttonName.equals("Save"))
			titleView = BorderFactory.createTitledBorder(template.getTemplateNum() + " Detail View");
		else
			titleView = BorderFactory.createTitledBorder("New Template Entry");

		// sets bounds for the part window
		setBounds(100, 100, 363, 301);
	
		// observer creation
		
		this.setBorder(titleView);
		
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] { 33, 96, 115, 89, 0 };
		gbl_this.rowHeights = new int[] { 35, 31, 32, 30, 32, 33, 0, 33 };
		gbl_this.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_this.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0 };
		this.setLayout(gbl_this);

		//template UUID
		templateUuid = new JLabel("UUID:");
		GridBagConstraints gbc_templateUuid = new GridBagConstraints();
		gbc_templateUuid.anchor = GridBagConstraints.WEST;
		gbc_templateUuid.insets = new Insets(0, 0, 5, 5);
		gbc_templateUuid.gridx = 1;
		gbc_templateUuid.gridy = 1;
		this.add(templateUuid, gbc_templateUuid);
		if (buttonName.equals("Save")) {
			txtfldUuid = new JTextField(template.getTemplateUuid() + "");
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
		
		//template number
		lblTemplateNm = new JLabel("Template Number:");
		GridBagConstraints gbc_lblTemplateNm = new GridBagConstraints();
		gbc_lblTemplateNm.anchor = GridBagConstraints.NORTH;
		gbc_lblTemplateNm.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTemplateNm.insets = new Insets(0, 0, 5, 5);
		gbc_lblTemplateNm.gridx = 1;
		gbc_lblTemplateNm.gridy = 2;
		this.add(lblTemplateNm, gbc_lblTemplateNm);
		
		txtTemplateNm = new JTextField((template.getTemplateNum() + ""));
		GridBagConstraints gbc_textTemplateNum = new GridBagConstraints();
		gbc_textTemplateNum.gridwidth = 2;
		gbc_textTemplateNum.insets = new Insets(0, 0, 5, 0);
		gbc_textTemplateNum.fill = GridBagConstraints.BOTH;
		gbc_textTemplateNum.gridx = 2;
		gbc_textTemplateNum.gridy = 2;
		this.add(txtTemplateNm, gbc_textTemplateNum);
		
		// template description
		lblDescription = new JLabel("Description:");
		GridBagConstraints gbc_lblDescript = new GridBagConstraints();
		gbc_lblDescript.anchor = GridBagConstraints.NORTH;
		gbc_lblDescript.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDescript.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescript.gridx = 1;
		gbc_lblDescript.gridy = 3;
		this.add(lblDescription, gbc_lblDescript);

		txtfldTemplateDescript = new JTextField((template.getTemplateDescript() + ""));
		GridBagConstraints gbc_txtfldTemplateDescript = new GridBagConstraints();
		gbc_txtfldTemplateDescript.gridwidth = 2;
		gbc_txtfldTemplateDescript.fill = GridBagConstraints.BOTH;
		gbc_txtfldTemplateDescript.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldTemplateDescript.gridx = 2;
		gbc_txtfldTemplateDescript.gridy = 3;
		this.add(txtfldTemplateDescript, gbc_txtfldTemplateDescript);
		
		btnSave = new JButton(buttonName);
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 4;
		this.add(btnSave, gbc_btnSave);
		
		/*btnViewParts = new JButton("Edit Parts");
		GridBagConstraints gbc_btnViewParts = new GridBagConstraints();
		gbc_btnViewParts.fill = GridBagConstraints.BOTH;
		gbc_btnViewParts.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewParts.gridx = 2;
		gbc_btnViewParts.gridy = 5;
		this.add(btnViewParts, gbc_btnViewParts);*/

	}

	// getters and setters for the template detail view
	public JTextField getTemplateDescript() {
		return txtfldTemplateDescript;
	}
	public void setTemplateDescript(JTextField descript) {
		this.txtfldTemplateDescript = descript;
	}

	public JTextField getTemplateNumber() {
		return txtTemplateNm;
	}
	public void setTemplateNumber(JTextField num){
		this.txtTemplateNm = num; 
	}
	
	// repaints the values of the part view to display proper info
	public void repaintTextFields() {
		this.txtfldTemplateDescript.repaint();
		this.txtfldUuid.repaint();

	}

	// registers the part window listeners
	public void registerListerners(TemplateDetailController tempController) {
		btnSave.addActionListener(tempController);
	}

	public JTextComponent getTxtfldUuid() {
		return this.txtfldUuid;
	}


	public TemplateView getView() {
		return view;
	}

	public void setView(TemplateView view) {
		this.view = view;
	}

	public TemplateDetailModel getTemplate() {
		return template;
	}
}
