package TemplateGUI;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


public class TemplateView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int gobalX = 800;
	private int gobalY = 400;
	private TemplateModel templateModel;
	private ArrayList<Integer> currentOpenTemplate = new ArrayList<Integer>();

	//private JMenuBar menuBar;
	private JMenu mnFile;
	private JTable templateList;

	private DefaultTableModel tableModel;

	/**
	 * Create the frame.
	 */
	public TemplateView(TemplateModel templateModel) {

		System.out.println("wtf!");

		this.templateModel = templateModel;
		
	
		TitledBorder titleWordList = BorderFactory.createTitledBorder("Templates");

		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] { 530, 220, 0, 0, 0 };
		gbl_this.rowHeights = new int[] { 120, 0, 50, 66, 0 };
		gbl_this.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_this.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_this);
		templateList = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		templateList.getTableHeader().setReorderingAllowed(false);
		templateList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane scroll = new JScrollPane(getTemplateList());
		scroll.setBorder(titleWordList);
		GridBagConstraints gbc_scroll = new GridBagConstraints();
		gbc_scroll.gridwidth = 2;
		gbc_scroll.fill = GridBagConstraints.BOTH;
		gbc_scroll.insets = new Insets(0, 0, 5, 5);
		gbc_scroll.gridheight = 4;
		gbc_scroll.gridx = 0;
		gbc_scroll.gridy = 0;
		this.add(scroll, gbc_scroll);
		
		// View Template Parts
		JButton btnViewTemplateParts = new JButton("View Template Parts");
		btnViewTemplateParts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		GridBagConstraints gbc_btnViewTemplateParts = new GridBagConstraints();
		gbc_btnViewTemplateParts.anchor = GridBagConstraints.SOUTH;
		gbc_btnViewTemplateParts.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnViewTemplateParts.insets = new Insets(0, 0, 20, 5);
		gbc_btnViewTemplateParts.gridx = 2;
		gbc_btnViewTemplateParts.gridy = 0;
		this.add(btnViewTemplateParts, gbc_btnViewTemplateParts);

		// Add template button
		JButton btnAddTemplate = new JButton("Add Template");
		btnAddTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		GridBagConstraints gbc_btnAddTemplate = new GridBagConstraints();
		gbc_btnAddTemplate.anchor = GridBagConstraints.SOUTH;
		gbc_btnAddTemplate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddTemplate.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddTemplate.gridx = 2;
		gbc_btnAddTemplate.gridy = 1;
		this.add(btnAddTemplate, gbc_btnAddTemplate);
		
		// Edit template button
		JButton btnViewTemplate = new JButton("Edit Template");
		btnViewTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		GridBagConstraints gbc_btnEditTemplate = new GridBagConstraints();
		gbc_btnEditTemplate.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditTemplate.anchor = GridBagConstraints.SOUTH;
		gbc_btnEditTemplate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEditTemplate.gridx = 2;
		gbc_btnEditTemplate.gridy = 2;
		this.add(btnViewTemplate, gbc_btnEditTemplate);


		// Delete template button
		JButton btnDeleteTemplate = new JButton("Delete Template");
		btnDeleteTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		GridBagConstraints gbc_btnDeleteTemplate = new GridBagConstraints();
		gbc_btnDeleteTemplate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteTemplate.insets = new Insets(0, 0, 0, 5);
		gbc_btnDeleteTemplate.gridx = 2;
		gbc_btnDeleteTemplate.gridy = 3;
		this.add(btnDeleteTemplate, gbc_btnDeleteTemplate);

		// populate list
		this.updateRow();
	}

	// Registers the listeners
	public void registerListerners(TemplateController templateController) {
		Component[] components = this.getComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(templateController);
			}
		}
		templateList.addMouseListener(templateController);
	}

	// grabs the current state of the model
	public void updateModel(TemplateModel model) {
		this.templateModel = model;
	}

	// Returns the template from the view table
	public JTable getTemplateList() {
		return templateList;
	}

	// changes the view table templates
	public void setTemplates(JTable templates) {
		this.templateList = templates;
	}

	// updates the rows for our JTable
	public void updateRow() {
		templateList.setModel(new DefaultTableModel(templateModel.getData(), templateModel.getColumnNames()));
	}

	// determines if multiple attempts at creating views of the same template
	public boolean isopen(int templateUuid) {
		if (getCurrentOpenTemplate().isEmpty())
			return false;
		for (int partName : getCurrentOpenTemplate()) {
			if (partName == templateUuid) {
				return true;
			}
		}
		return false;
	}

	// opens the view of the selected template
	public void addPartView(Integer templateSelected) {
		getCurrentOpenTemplate().add(templateSelected);
	}

	public void removeTemplate(int uuid) {
		currentOpenTemplate.remove((Integer) uuid);
	}

	public ArrayList<Integer> getCurrentOpenTemplate() {
		return currentOpenTemplate;
	}

	public void setCurrentOpenTemplate(ArrayList<Integer> currentOpenTemplate) {
		this.currentOpenTemplate = currentOpenTemplate;
	}
}

