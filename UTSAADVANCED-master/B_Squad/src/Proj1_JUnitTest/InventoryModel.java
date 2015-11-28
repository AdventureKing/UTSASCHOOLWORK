package Proj1_JUnitTest;

import java.util.ArrayList;


//Database for all my stuff

public class InventoryModel {
	
	//declares storage variables
	ArrayList<PartModel> inventory;
	private ArrayList<String> inventoryListNames;
	private String[] columnNames = {"Part Name","Part Number", "Quantity", " Vendor"};

	//instantiates the base model with three parts added to it
	public InventoryModel() {
		this.inventory  = new ArrayList<PartModel>();
		this.inventoryListNames = new ArrayList<String>();
		inventory.add( new PartModel ("123","Wrench","Brandon Snow",123));
		addPartNameToList("Wrench");
		inventory.add( new PartModel ("345","Pipe","HomeDepot Inc.",324));
		addPartNameToList("Pipe");
		inventory.add( new PartModel ("565","Wheel Barrow","Lowes",2));
		addPartNameToList("Wheel Barrow");
	}
	
	//gets the column names for the JTable 
	public String[] getColumnNames() {
		return columnNames;
	}
	
	//getters and setters for the part database
	public int addInventoryPart(PartModel part){
		int flag;
		if((flag = inventoryCheck(part)) < 0){
			return flag;
		}
		this.inventory.add(part);
		addPartNameToList(part.getPartName());
		return 1;
	}
	public ArrayList<PartModel> getInventory(){	return inventory; }
	public void setInventory(ArrayList<PartModel> inventory){ this.inventory = inventory; }
	
	//keeps a string of the part names
	public void addPartNameToList(String name){ this.inventoryListNames.add(name); }
	public void removePartNameFromList(String name){ this.inventoryListNames.remove(name);}
	public ArrayList<String> getPartNameList(){	return inventoryListNames; }
	public void setInventoryList(ArrayList<String> inventoryListNames) { this.inventoryListNames = inventoryListNames; }
	
	//allows for the information of a part to be updated
	public int updateInventoryPart(PartModel part){
		int flag;
		if((flag = inventoryCheck(part)) < 0 && flag != -4){
			return flag;
		}
		for(PartModel inventoryPart : inventory) {
			if(inventoryPart.getPartName().equals(part.getPartName())){
				inventoryPart.setPartName(part.getPartName());
				inventoryPart.setPartNum(part.getPartNum());
				String quan = String.valueOf(part.getQuantity());
				inventoryPart.setPartQuan(quan);
				inventoryPart.setVendor(part.getVendor());
				inventoryListNames.add(part.getPartName());
			}
		}
		return 1;
	}

	//creates an object with the stored part data for the JTable
	public Object[][] getData() {
		int i = 0;
		Object [][] partEntry = new Object[inventory.size()][columnNames.length];
		 for(i = 0; i < inventory.size(); i++){
				partEntry[i] = inventory.get(i).getPartInfo();
			
			}
		return partEntry;
	}
	
	public int inventoryCheck(PartModel part){
		if(inventory.isEmpty()){
			return -1;
		}
		else
		{
			if(part.getQuantity() < 0 )
			{
				return -2;
			}else if(part.getVendor() == null){
				return -3;
			}
			for(String inventoryPart : getPartNameList()){
				if(inventoryPart.toLowerCase().equals(part.getPartName().toLowerCase())){
					return -4;
				}
			}
		}
		return 1;
	}
	
}
