package PartDetailGUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import PartListGUI.PartListModel;
import PartListGUI.PartListView;
import Session.UserState;

public class PartDetailController implements ActionListener {

	private PartDetailView partDetailView;
	private PartDetailModel partDetailModel;
	private PartListView partListView;
	private PartListModel partListModel;
	private UserState programState;

	// instantiates the part window controller
	public PartDetailController(PartDetailModel partDetailModel,
			PartDetailView partDetailView, PartListModel partListModel,
			PartListView partListView, UserState programState) {
		this.partListView = partListView;
		this.partListModel = partListModel;
		this.partDetailView = partDetailView;
		this.partDetailModel = partDetailModel;
		this.programState = programState;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		System.out.println("Test" + event);

		// determines what to do if add is pressed in part window
		if (command.equals("Add")) {
			//all checks needed to pass
			
			if (partDetailView.getTxtfldVendor().getText().length() == 0
				|| partDetailView.getTxtfldPartName().getText().toString().length() == 0
			    || partDetailView.getTxtfldPartNum().getText().length() == 0 
			    || !partDetailView.getTxtfldPartNum().getText().substring(0,1).equals("P") 
			    || partDetailView.getTxtfldQuanUnit().getText().toString().length() == 0
			    || partDetailView.getTxtfldExtPartNum().getText().length() == 0 
			    ||  partDetailView.getTxtfldExtPartNum().getText().length() > 50 
				|| partDetailView.getTxtfldPartNum().getText().length() > 20  )
		    {	
				partListView.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,"Error Check data and resubmit.");
			} else {
				
				/* quan = Integer.parseInt(partDetailView.getTxtfldQuan().getText()); */
				String partNum = partDetailView.getTxtfldPartNum().getText();
				String partName = partDetailView.getTxtfldPartName().getText().toString();
				String vendor = partDetailView.getTxtfldVendor().getText().toString();
				String quanUnit = partDetailView.getTxtfldQuanUnit().getText().toString();
				String extPartNum = partDetailView.getTxtfldExtPartNum().getText().toString();
				
				// need checks right here for the part model to before inserting
				partDetailModel = new PartDetailModel(0, partNum, partName, vendor, quanUnit, extPartNum);
				if (partListModel.inventoryPartCheck(partDetailModel) == 0) {
					// insert into the database then update the inventory view
					// with current database
					partListModel.addtoInventory(partDetailModel);
					partListView.updateRow();
					
				
					
				} else {
					//if data isn't good then don't open
					Component frame = null;
					JOptionPane.showMessageDialog(frame,"Error Check data and resubmit.");
				}
			}
		} else if (command.equals("Save")) {
			
			//all checks needed to pass 
			if (partDetailView.getTxtfldVendor().getText().length() == 0
					|| partDetailView.getTxtfldPartName().getText().toString().length() == 0
				    || partDetailView.getTxtfldPartNum().getText().length() == 0 
				    || !partDetailView.getTxtfldPartNum().getText().substring(0,1).equals("P") 
				    || partDetailView.getTxtfldQuanUnit().getText().toString().length() == 0
				    || partDetailView.getTxtfldExtPartNum().getText().length() == 0 
				    ||  partDetailView.getTxtfldExtPartNum().getText().length() > 50 
					|| partDetailView.getTxtfldPartNum().getText().length() > 20)   {
				
				partListView.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,"Error Check data and resubmit.");
			} else {
				//refresh text fields
				partDetailView.repaintTextFields();
	
				int partUuid = Integer.parseInt(partDetailView.getTxtfldUuid().getText());
				String partNum = partDetailView.getTxtfldPartNum().getText().toString();
				String partName = partDetailView.getTxtfldPartName().getText().toString();
				String vendor = partDetailView.getTxtfldVendor().getText().toString();
				String quanUnit = partDetailView.getTxtfldQuanUnit().getText().toString();
				String extPartNum = partDetailView.getTxtfldExtPartNum().getText();
				
				// need checks right here for the part model to before inserting
				PartDetailModel tempModel = new PartDetailModel(partUuid, partNum, partName, vendor, quanUnit, extPartNum);
				
				//check inventory to make sure changes are acceptable
				if (partListModel.inventoryPartCheck(tempModel) == 0) {
					//update inventory
					// with current database
					System.out.println(partName);
					partDetailModel.setPartNum(partNum);
					partDetailModel.setPartName(partName);
					partDetailModel.setVendor(vendor);
					partDetailModel.setQuanUnit(quanUnit);
					partDetailModel.setExtPartNum(extPartNum);
					partDetailModel.updateInventory();
				
					partListView.updateRow();

					
				} else {
					//if data isnt good then dont open
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "Error Check data and resubmit.");
				}	
			}
		}
	}
}
