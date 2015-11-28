package InventoryDetailGUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Cabinetron.MasterFrame;
import InventoryListGUI.InventoryModel;
import InventoryListGUI.InventoryView;
import Session.UserState;

public class InventoryDetailController implements ActionListener {

	private InventoryDetailView itemView;
	private InventoryDetailModel itemModel;
	private InventoryView inventoryView;
	private InventoryModel inventoryModel;
	private MasterFrame master;
	private UserState programState;
	
	// instantiates the part window controller
	public InventoryDetailController(InventoryDetailModel itemModel,
			InventoryDetailView itemView, InventoryModel inventoryModel,
			InventoryView inventoryView, UserState programState) {
		this.itemView = itemView;
		this.itemModel = itemModel;
		this.inventoryView = inventoryView;
		this.inventoryModel = inventoryModel;
		this.programState = programState;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		// determines what to do if add is pressed in part window
		if (command.equals("Add")) {
			
			int productRequirementsMetFlag = 0;
			String btnName = "Add";
			int quan = 0;
			
			String selection = itemView.getItemNames().getSelectedItem().toString();
			if(selection.startsWith("product")){
			  productRequirementsMetFlag = checkProductRequirements(btnName);
			}
				
			if(productRequirementsMetFlag == -1){
				
				inventoryView.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"Not enough parts for Product at location. Try a different location");
				
			}else if (itemView.getTxtfldQuan().getText().length() == 0
					||itemView.getItemNames().getSelectedItem().toString().length() == 0 
					|| itemView.getItemLocation().getSelectedItem().toString().equals("Unknown") 
					|| itemView.getTxtfldQuan().getText().equals("0")) {
				
				inventoryView.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"Error Check data and resubmit. Step it UP!!!");
				
			} else {
				String itemSelection =  itemView.getItemNames().getSelectedItem().toString();
				String[] split = itemSelection.split(" : ");
				
				String itemId = "";
				if(itemView.getItemNames().getSelectedItem().toString().startsWith("product")){
					itemId = "A" + itemModel.getNextProductIdForDatabase();
				    itemSelection = split[2];
				}else{
					itemId = "P" + itemModel.getNextPartIdForDatabase();
					itemSelection = split[1];
				}
				
				quan = Integer.parseInt(itemView.getTxtfldQuan().getText());
				
				itemModel = new InventoryDetailModel(0, itemId, itemSelection,
						    quan, itemView.getItemLocation().getSelectedItem().toString());
				
				//check if inventory piece exists somewhere in the inventory list
				if (inventoryModel.inventoryPartCheck(itemModel) == 0  && quan >= 0) {
					// insert into the database then update the inventory view
					// with current database
					inventoryModel.addtoInventory(itemModel);
					inventoryView.updateRow();
					
				} else {
					//if data isnt good then dont open
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "Error Check data and resubmit.");
				}
				
			}
			
		} else if (command.equals("Save")) {
			
			
			// refresh text fields
			itemView.repaintTextFields();
			//make sure fields are filled
			
			String btnName = "Save";
			
			int productRequirementsMetFlag = 0;
			String selection = itemView.getItemNames().getSelectedItem().toString();
			if(selection.startsWith("product")){
			   productRequirementsMetFlag = checkProductRequirements(btnName);
			}
				
			if(productRequirementsMetFlag == -1){
				
				inventoryView.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"Not enough parts for Product at location. Try a different location");
				
			
			}else if (itemView.getTxtfldQuan().getText().length() == 0
					|| itemView.getItemNames().getSelectedItem().toString()
							.length() == 0
					|| itemView.getItemLocation().getSelectedItem().toString()
							.equals("Unknown")
					|| itemView.getTxtfldQuan().getText().length() == 0) {

				inventoryView.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"Error Check data and resubmit. Step it UP!!!");

			} else {
				// create temp model

				int quan = Integer.parseInt(itemView.getTxtfldQuan().getText());
				String itemId = itemView.getTxtfldItemId().getText().toString();
				
				

				String partName = itemView.getItemNames().getSelectedItem().toString();
				String[] split = partName.split(" : ");
				
				if(partName.startsWith("product :")){
				   partName = split[2];
				}else{
					partName = split[1];
				}
				
				String location = itemView.getItemLocation().getSelectedItem().toString();

				int uuid = Integer.parseInt(itemView.getTxtfldUuid().getText());

				 InventoryDetailModel tempModel = new InventoryDetailModel(uuid, itemId, partName, quan, location);
				

				// check inventory to make sure changes are accecptable
				if (inventoryModel.inventoryPartCheck(tempModel) == 0
						&& !itemView.getItemLocation().getSelectedItem()
								.toString().equals("Unknown") && quan >= 0) {
					
					// update inventory
					// with current database
					//need to make temp model stuff == to the original models stuff
					System.out.println(partName);
					itemModel.setItemName(partName);
					itemModel.setItemId(itemId);
					itemModel.setItemQuan(itemView.getTxtfldQuan().getText());
					itemModel.setLocation(location);
					itemModel.updateInventory();
					
					//update list
					inventoryView.updateRow();

				} else {
					
					// if data isnt good then dont open
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "Error Check data and resubmit.");
				}	
			}
		}
	}
	
	//check part quantities for a product template;
	public int checkProductRequirements(String btnName){
		System.out.println("Checking REQS!\n\n");
		String selection = itemView.getItemNames().getSelectedItem().toString();
		System.out.println("Selection: " + selection);
		if(selection.startsWith("product")){
			
			String templateChosen = selection;
			String[] templateChosenSplit = templateChosen.split(" : ");
			String productTemplateId = templateChosenSplit[1].toUpperCase();
			System.out.println("productTemplateId: " + productTemplateId);
			String location = itemView.getItemLocation().getSelectedItem().toString();
			int productQuan = Integer.parseInt(itemView.getTxtfldQuan().getText().toString());
			
			ArrayList<String> partReqs = itemModel.getProductReqs(productTemplateId);
			ArrayList<String> partsAtLocation = itemModel.getPartsAtLocation(location);
			
			for(String partInReqs : partReqs){
				System.out.println("Sys req: " + partInReqs);
				String[] split = partInReqs.split(" : ");
				String reqPartName = split[0];
				int reqPartQuan = Integer.parseInt(split[1]);
				
				for(String partAtLocation : partsAtLocation){
					System.out.println("Part at Location: " + partAtLocation);
					String[] split2 = partAtLocation.split(" : ");
					String locationPartName = split2[0];
					int locationPartQuan = Integer.parseInt(split2[1]);
					
					if(locationPartName.equals(reqPartName)){
						String itemId = itemView.getTxtfldItemId().getText();	
						int oldQuan = itemModel.getOriginalProductQuan(itemId);
						int totalNeededPartQuan = 0;
						if(btnName.equals("Add")){
							totalNeededPartQuan = reqPartQuan*productQuan;
						}else if(btnName.equals("Save")){
							if(productQuan > oldQuan){
								totalNeededPartQuan =  (productQuan - oldQuan) * reqPartQuan;
							}
						}else{
							System.out.println("Button is neither save nor add?");
							System.exit(-1);
						}
						if(totalNeededPartQuan <= locationPartQuan){
							int newPartQuan = locationPartQuan - totalNeededPartQuan;
							itemModel.updateSinglePartQuanAtLocation(location, locationPartName, newPartQuan); 
							return 1;
						}else {
							return -1;
						}			
					}
				}
			}
		}
		System.out.println("\n\n");
		return -1;
	}
}
