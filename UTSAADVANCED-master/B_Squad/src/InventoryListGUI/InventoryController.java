package InventoryListGUI;

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

/**
 * This class is the overall controller for the inventory system. It handles all
 * events that will trigger in this program.
 * 
 */
public class InventoryController extends MouseAdapter implements ActionListener {
	private InventoryView view;
	private InventoryModel model;

	private int allow = 0;

	private InventoryDetailView itemView;
	private InventoryDetailController itemController;
	private String buttonName;
	private MasterFrame master;
	private UserState programState;

	public InventoryController(InventoryView view, InventoryModel model,
			MasterFrame master, UserState programState) {
		this.view = view;
		this.model = model;
		this.master = master;
		this.programState = programState;
	}

	// if a action happens do shit
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		if (command.equals("Add Inventory Item")) {
			if (this.programState.canAddInventory() == allow) {

				view.repaint();

				// button name to create correct window
				buttonName = "Add";

				// create a fake temp model so that way i can show view
				InventoryDetailModel tempModel;
				tempModel = new InventoryDetailModel(0, null, null, 1, "Unknown");

				itemView = new InventoryDetailView(view, tempModel, buttonName);
				itemController = new InventoryDetailController(tempModel,
						itemView, model, view, programState);
				itemView.registerListerners(itemController);

				// create child window view and put it in the mdi view

				ChildWindow child;
				child = new MasterFrame().new ChildWindow(this.master,
						"New Part", itemView, null, null, null, null,
						programState);
				master.openChildWindow(child);
			} else {
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"Get access scrub!");
			}

		} else if (command.equals("Delete Inventory Item")) {
			if (this.programState.canDeleteInventory() == allow) {
				// make sure something is selected
				int row[] = view.getItemList().getSelectedRows();
				if (row.length == 0) {
					Component frame = null;
					JOptionPane.showMessageDialog(frame,
							"Please Select a Entry.");

				} else {
					// get row data
					row = view.getItemList().getSelectedRows();
					for (int i = 0; i < row.length; i++) {
					int partUuid = (int) (view.getItemList().getValueAt(row[i],0));
					String itemId = view.getItemList().getValueAt(row[i], 1).toString();
					String partSelected = (view.getItemList().getValueAt(row[i], 2).toString());
					int partQuantity = (int) (view.getItemList().getValueAt(row[i], 3));
					String partLocation = (view.getItemList().getValueAt(row[i], 4).toString());
						if (partQuantity > 0) {
							Component frame = null;
							JOptionPane
									.showMessageDialog(frame,
											"Please Select a Entry with quantity equal to 0.");

						} else {
							// create temp model
							InventoryDetailModel tempModel = new InventoryDetailModel(
									partUuid,itemId, partSelected, partQuantity,
									partLocation);
							// remove that from the inventory and database
							tempModel.removeFromInventory();
						}
					}
				}

				// update and recall the inventory
				view.updateRow();
			} else {
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"Get access scrub!");
			}

		} else if (command.equals("Edit Inventory Item")) {
			if (this.programState.canAddInventory() == allow) {
				// make sure something is selected
				int row[] = view.getItemList().getSelectedRows();
				if (row.length == 0) {
					Component frame = null;
					JOptionPane.showMessageDialog(frame,
							"Please Select a Entry.");
				} else {
					// set button name for window
					buttonName = "Save";

					for (int i = 0; i < row.length; i++) {

						int partUuid = (int) (view.getItemList().getValueAt(
								row[i], 0));
						String itemId = view.getItemList().getValueAt(row[i], 1).toString();
						String partSelected = (view.getItemList().getValueAt(
								row[i], 2).toString());
						
						int partQuantity = Integer.parseInt((view.getItemList()
								.getValueAt(row[i], 3).toString()));
						String partLocation = (view.getItemList().getValueAt(
								row[i], 4).toString());
						System.out.println(partUuid);

						// create temp model
						InventoryDetailModel tempModel = new InventoryDetailModel(
								partUuid,itemId, partSelected, partQuantity,
								partLocation);

						// if the windows not already open then open it
						if (view.isopen(partUuid) == false) {
							if (tempModel.lockRecord(partUuid) == 0) {
								itemView = new InventoryDetailView(view,
										tempModel, buttonName);

								itemController = new InventoryDetailController(
										tempModel, itemView, model, view,
										programState);

								// create child view in mdi

								ChildWindow child;
								child = new MasterFrame().new ChildWindow(
										this.master, "Existing Part", itemView,
										null, null, null, null, programState);
								master.openChildWindow(child);

								// add to int array so multiple windows cant be
								// opened
								// at the same time
								view.addPartView(partUuid);

								// register listeners
								itemView.registerListerners(itemController);
								// lock the record
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
						"Get access scrub!");
			}
		}
	}

	// copy of above function but its a double click
	@Override
	public void mouseClicked(MouseEvent me) {
		int row = view.getItemList().getSelectedRow();
		if (me.getClickCount() == 2) {
			if (this.programState.canAddInventory() == allow) {
				// your valueChanged overridden method
				System.out.println("rowInfo:" + row);
				buttonName = "Save";

				int partUuid = (int) (view.getItemList().getValueAt(row, 0));
				String itemId = view.getItemList().getValueAt(row, 1).toString();
				String partSelected = (view.getItemList().getValueAt(row, 2)
						.toString());

				int partQuantity = (int) (view.getItemList().getValueAt(row, 3));
				String partLocation = (view.getItemList().getValueAt(row, 4)
						.toString());
				
				// create temp model
				InventoryDetailModel tempModel = new InventoryDetailModel(
						partUuid, itemId, partSelected, partQuantity, partLocation);
				if (view.isopen(partUuid) == false) {

					if (tempModel.lockRecord(partUuid) == 0) {
						itemView = new InventoryDetailView(view, tempModel,
								buttonName);
						itemController = new InventoryDetailController(
								tempModel, itemView, model, view, programState);

						// create child window
						ChildWindow child;

						child = new MasterFrame().new ChildWindow(this.master,
								"Existing Part", itemView, null, null, null,
								null, programState);
						master.openChildWindow(child);

						// add to int array so multiple windows cant be opened
						// at the same time
						view.addPartView(partUuid);
						// register listeners
						itemView.registerListerners(itemController);

					}

				} else {
					Component frame = null;
					JOptionPane
							.showMessageDialog(frame,
									"View for this selection is already shown. Please select a different Entry.");
				}
			} else {
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"Get access scrub!");
			}
		}
	}

}
