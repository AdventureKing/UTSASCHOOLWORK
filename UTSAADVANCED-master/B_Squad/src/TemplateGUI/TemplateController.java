package TemplateGUI;

import java.awt.Component;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import Cabinetron.MasterFrame;
import Cabinetron.MasterFrame.ChildWindow;
import InventoryDetailGUI.InventoryDetailController;
import InventoryDetailGUI.InventoryDetailModel;
import InventoryDetailGUI.InventoryDetailView;
import PartListGUI.PartListController;
import PartListGUI.PartListModel;
import PartListGUI.PartListView;
import Session.UserState;
import TemplateDetailGUI.TemplateDetailController;
import TemplateDetailGUI.TemplateDetailModel;
import TemplateDetailGUI.TemplateDetailView;
import TemplatePartsGUI.TemplatePartsController;
import TemplatePartsGUI.TemplatePartsGateway;
import TemplatePartsGUI.TemplatePartsModel;
import TemplatePartsGUI.TemplatePartsView;

public class TemplateController extends MouseAdapter implements ActionListener {
	private TemplateView view;
	private TemplateModel model;

	private TemplateDetailView templateDetailView;
	private TemplateDetailController templateDetailController;
	private String buttonName;
	private MasterFrame master;
	private UserState programState;

	private int allow = 0;

	public TemplateController(TemplateView view, TemplateModel model,
			MasterFrame master, UserState programState) {
		this.view = view;
		this.model = model;
		this.master = master;
		this.programState = programState;
	}

	// if a action happens do stuff
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		if (command.equals("Add Template")) {
			if (programState.canAddProductTemplates() == allow) {

				view.repaint();

				// button name to create correct window
				buttonName = "Add";

				// create a fake temp model so that way i can show view
				TemplateDetailModel tempModel;
				tempModel = new TemplateDetailModel(0, "", "");

				templateDetailView = new TemplateDetailView(view, tempModel,
						buttonName);
				templateDetailController = new TemplateDetailController(
						tempModel, templateDetailView, model, view,
						programState);
				templateDetailView.registerListerners(templateDetailController);

				// create child window view and put it in the mdi view

				ChildWindow child;
				child = new MasterFrame().new ChildWindow(this.master,
						"New Template", null, null, templateDetailView, null,
						null, programState);
				master.openChildWindow(child);
			} else {
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"Get more permissions homie!");
			}

		} else if (command.equals("Delete Template")) {
			if (programState.canDeleteProductTemplates() == allow) {

				// make sure something is selected
				int row[] = view.getTemplateList().getSelectedRows();
				if (row.length == 0) {
					Component frame = null;
					JOptionPane.showMessageDialog(frame,
							"Please Select an Entry.");
				} else {
					// get row data
					row = view.getTemplateList().getSelectedRows();
					for (int i = 0; i < row.length; i++) {
						int templateUuid = (int) (view.getTemplateList()
								.getValueAt(row[i], 0));
						String templateNumberSelected = (view.getTemplateList()
								.getValueAt(row[i], 1).toString());
						String templateDescript = (view.getTemplateList()
								.getValueAt(row[i], 2).toString());

						TemplateDetailModel tempModel = new TemplateDetailModel(
								templateUuid, templateNumberSelected,
								templateDescript);
						// remove that from the inventory and database
						tempModel.removeFromInventory();
					}
				}

				// update and recall the inventory
				view.updateRow();
			} else {
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"You aint got enough permissions bro!");
			}
		} else if (command.equals("Edit Template")) {
			if (programState.canAddProductTemplates() == allow) {

				// make sure something is selected
				int row[] = view.getTemplateList().getSelectedRows();
				if (row.length == 0) {
					Component frame = null;
					JOptionPane.showMessageDialog(frame,
							"Please Select an Entry.");
				} else {
					// set button name for window
					buttonName = "Save";

					for (int i = 0; i < row.length; i++) {

						int templateUuid = (int) (view.getTemplateList()
								.getValueAt(row[i], 0));
						String templateNum = (view.getTemplateList()
								.getValueAt(row[i], 1).toString());
						String templateDescription = (view.getTemplateList()
								.getValueAt(row[i], 2).toString());
						System.out.println(templateUuid);

						// create temp model
						TemplateDetailModel tempModel = new TemplateDetailModel(
								templateUuid, templateNum, templateDescription);

						// if the windows not already open then open it
						if (view.isopen(templateUuid) == false) {
							if (tempModel.lockRecord(templateUuid) == 0) {
								System.out.println("Lock Record returned 0");
								templateDetailView = new TemplateDetailView(
										view, tempModel, buttonName);
								templateDetailController = new TemplateDetailController(
										tempModel, templateDetailView, model,
										view, programState);

								// register listeners
								templateDetailView
										.registerListerners(templateDetailController);
								// lock the record

								// create child view in mdi
								ChildWindow child;
								child = new MasterFrame().new ChildWindow(
										this.master, "Existing Template", null,
										null, templateDetailView, null, null,
										programState);
								master.openChildWindow(child);

								// add to int array so multiple windows cant be
								// opened
								// at the same time
								view.addPartView(templateUuid);
							}

						} else {
							Component frame = null;
							JOptionPane
									.showMessageDialog(frame,
											"View for this selection is already shown. Please select a different Entry.");
						}

					}

				}
			} else {
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"You aint got enough permissions bro!");
			}
		} else if (command.equals("View Template Parts")) {
			if (programState.canAddProductTemplates() == allow) {

				// make sure something is selected
				int row[] = view.getTemplateList().getSelectedRows();

				if (row.length == 0) {
					Component frame = null;
					JOptionPane.showMessageDialog(frame,
							"Please Select an Entry.");
				} else {
					// set button name for window
					buttonName = "Save";

					for (int i = 0; i < row.length; i++) {

						int templateUuid = (int) (view.getTemplateList()
								.getValueAt(row[i], 0));
						String templateNum = view.getTemplateList()
								.getValueAt(row[i], 1).toString();
						System.out.println("View Template Parts Num: "
								+ templateNum);

						ArrayList<String> parts = model.getParts(templateUuid,
								templateNum);

						TemplatePartsModel templatePartsModel = new TemplatePartsModel(
								templateNum, parts);
						TemplatePartsView templatePartsView = new TemplatePartsView(
								templatePartsModel);
						TemplatePartsController templatePartsController = new TemplatePartsController(
								templatePartsModel, templatePartsView, master,
								programState);

						templatePartsView
								.registerListeners(templatePartsController);

						ChildWindow child;
						child = new MasterFrame().new ChildWindow(this.master,
								"Parts For Template", null, null, null,
								templatePartsView, null, programState);
						master.openChildWindow(child);

					}

				}
			} else {
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"You aint got enough permissions bro!");
			}
		}
	}

	// copy of above function but its a double click
	@Override
	public void mouseClicked(MouseEvent me) {
		if (programState.canAddProductTemplates() == allow) {

			int row = view.getTemplateList().getSelectedRow();
			if (me.getClickCount() == 2) {
				// your valueChanged overridden method
				System.out.println("Test Double CLick bitch");
				System.out.println("rowInfo:" + row);
				buttonName = "Save";

				int templateUuid = (int) (view.getTemplateList().getValueAt(
						row, 0));
				String templateNum = (view.getTemplateList().getValueAt(row, 1)
						.toString());
				String templateDescription = (view.getTemplateList()
						.getValueAt(row, 2).toString());
				System.out.println(templateUuid);

				// create temp model
				TemplateDetailModel tempModel = new TemplateDetailModel(
						templateUuid, templateNum, templateDescription);
				if (view.isopen(templateUuid) == false) {

					if (tempModel.lockRecord(templateUuid) == 0) {
						templateDetailView = new TemplateDetailView(view,
								tempModel, buttonName);
						templateDetailController = new TemplateDetailController(
								tempModel, templateDetailView, model, view,
								programState);

						// create child window
						ChildWindow child;

						child = new MasterFrame().new ChildWindow(this.master,
								"Existing Template", null, null,
								templateDetailView, null, null, programState);
						master.openChildWindow(child);

						// add to int array so multiple windows cant be opened
						// at the same time
						view.addPartView(templateUuid);
						// register listeners
						templateDetailView
								.registerListerners(templateDetailController);
					}
				} else {
					Component frame = null;
					JOptionPane
							.showMessageDialog(frame,
									"View for this selection is already shown. Please select a different Entry.");
				}
			}
		} else {
			Component frame = null;
			JOptionPane.showMessageDialog(frame,
					"You aint got enough permissions bro!");

		}
	}
}
