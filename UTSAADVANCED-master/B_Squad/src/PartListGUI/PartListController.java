package PartListGUI;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import Cabinetron.MasterFrame;
import Cabinetron.MasterFrame.ChildWindow;
import InventoryDetailGUI.InventoryDetailView;
import PartDetailGUI.PartDetailController;
import PartDetailGUI.PartDetailModel;
import PartDetailGUI.PartDetailView;
import Session.UserState;

public class PartListController extends MouseAdapter implements ActionListener {
	private PartListView view;
	private PartListModel model;

	int allow = 0;

	private PartDetailModel detailPartModel;
	private PartDetailView detailPartView;
	private PartDetailController detailPartController;
	private String buttonName;
	private MasterFrame master;
	private UserState programState;

	public PartListController(PartListView view, PartListModel model,
			MasterFrame master, UserState programState) {
		this.view = view;
		this.model = model;
		this.master = master;
		this.programState = programState;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.equals("Add Part")) {
			if (this.programState.canAddParts() == allow) {
				view.repaint();

				buttonName = "Add";

				// create dummy model for show
				detailPartModel = new PartDetailModel(0, "", "", "", "", "");

				detailPartView = new PartDetailView(view, detailPartModel,
						buttonName);
				detailPartController = new PartDetailController(
						detailPartModel, detailPartView, model, view,
						programState);

				// add child to view
				ChildWindow child;
				child = new MasterFrame().new ChildWindow(this.master,
						"Add List Part", null, detailPartView, null, null,
						null, programState);
				master.openChildWindow(child);

				// register controller
				detailPartView.registerListerners(detailPartController);
			} else {
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"Get access scrub!");
			}

		} else if (command.equals("Delete Part")) {

			if (this.programState.canDeleteParts() == allow) {

				int row[] = view.getPartList().getSelectedRows();
				// make sure something is selected
				if (row.length == 0) {
					Component frame = null;
					JOptionPane.showMessageDialog(frame,
							"Please Select a Entry.");
				} else {
					row = view.getPartList().getSelectedRows();
					for (int i = 0; i < row.length; i++) {

						int partUuid = (int) (view.getPartList().getValueAt(
								row[i], 0));
						String partNum = view.getPartList()
								.getValueAt(row[i], 1).toString();
						String partName = (view.getPartList().getValueAt(
								row[i], 2).toString());
						String vendor = (view.getPartList().getValueAt(row[i],
								3).toString());
						String unitQuan = (view.getPartList().getValueAt(
								row[i], 4).toString());
						String extPartNum = (view.getPartList().getValueAt(
								row[i], 5).toString());

						// create temp model
						PartDetailModel tempModel = new PartDetailModel(
								partUuid, partNum, partName, vendor, unitQuan,
								extPartNum);
						// remove that from the inventory
						System.out.println("Part Number in PartListCont: " + partNum);
						if (tempModel.checkPartInTemplates(partNum.toUpperCase())) {
							Component frame = null;
							JOptionPane
									.showMessageDialog(frame,
											"That Part is Used In A Template. Delete Template, then Try Again.");
						} else {
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

		} else if (command.equals("Edit Part")) {

			if (this.programState.canAddParts() == allow) {

				int row[] = view.getPartList().getSelectedRows();
				if (row.length == 0) {
					Component frame = null;
					JOptionPane.showMessageDialog(frame,
							"Please Select a Entry.");
				} else {
					buttonName = "Save";
					for (int i = 0; i < row.length; i++) {

						int partUuid = (int) (view.getPartList().getValueAt(
								row[i], 0));
						System.out.println("uuid " + partUuid);
						String partNum = (view.getPartList().getValueAt(row[i],
								1).toString());
						String partName = (view.getPartList().getValueAt(
								row[i], 2).toString());
						String vendor = (view.getPartList().getValueAt(row[i],
								3).toString());
						String unitQuan = (view.getPartList().getValueAt(
								row[i], 4).toString());
						String extPartNum = (view.getPartList().getValueAt(
								row[i], 5)).toString();
						;

						// create temp model
						PartDetailModel tempModel = new PartDetailModel(
								partUuid, partNum, partName, vendor, unitQuan,
								extPartNum);
						System.out.println("uuid 2: " + tempModel.getUuid());
						if (view.isopen(partUuid) == false &&  tempModel.lockRecord(partUuid) == 0) {
							
							detailPartView = new PartDetailView(view,
									tempModel, buttonName);
							detailPartController = new PartDetailController(
									tempModel, detailPartView, model, view,
									programState);

							// add to int array so multiple windows cant be
							// opened
							// at the same time

							ChildWindow child;
							child = new MasterFrame().new ChildWindow(
									this.master, "Existing List Part", null,
									detailPartView, null, null, null,
									programState);
							master.openChildWindow(child);

							view.addPartDetailView(partUuid);
							// register listeners
							detailPartView
									.registerListerners(detailPartController);

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

	@Override
	public void mousePressed(MouseEvent me) {

		if (this.programState.canAddParts() == allow) {

			int row = view.getPartList().getSelectedRow();
			if (me.getClickCount() == 2) {
				buttonName = "Save";

				int partUuid = (int) (view.getPartList().getValueAt(row, 0));
				String partNum = view.getPartList().getValueAt(row, 1)
						.toString();
				String partName = (view.getPartList().getValueAt(row, 2)
						.toString());
				String vendor = (view.getPartList().getValueAt(row, 3)
						.toString());
				String unitQuan = (view.getPartList().getValueAt(row, 4)
						.toString());
				String extPartNum = (view.getPartList().getValueAt(row, 5)
						.toString());

				// create temp model
				PartDetailModel tempModel = new PartDetailModel(partUuid,
						partNum, partName, vendor, unitQuan, extPartNum);

				if (view.isopen(partUuid) == false
						&& tempModel.lockRecord(partUuid) == 0) {
					PartDetailView partDetailView = new PartDetailView(view,
							tempModel, buttonName);
					PartDetailController partDetailController = new PartDetailController(
							tempModel, partDetailView, model, view,
							programState);

					// add to int array so multiple windows cant be opened
					// at the same time

					// create child window in mdi
					ChildWindow child;
					child = new MasterFrame().new ChildWindow(this.master,
							"Existing List Part", null, partDetailView, null,
							null, null, programState);
					master.openChildWindow(child);

					// add part view to the list of open ones for that process
					view.addPartDetailView(partUuid);

					// register listeners
					partDetailView.registerListerners(partDetailController);
				}
			}
		} else {
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Get access scrub!");
		}
	}
}
