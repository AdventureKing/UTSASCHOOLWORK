package InventoryDetailGUI;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDetailModel {

	// required are part#,part name, and quantity

	private String itemName;

	private int quantity;

	private int uuid;
	private String itemId;
	private String location;
	private InventoryDetailGateWay gateway;

	public InventoryDetailModel(int uuid, String itemId, String itemName, int quantity, String location) {
		this.uuid = uuid;
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.location = location;
		gateway = new InventoryDetailGateWay();
	}

	//create part object for the table
	public Object[] getItemInfo() {
		Object[] itemInfo = { this.uuid, this.itemId, this.itemName, this.quantity, this.location };
		return itemInfo;
	}

	//database editing functions

	public int lockRecord(int itemUuid) {
		try {
			return gateway.lockRow(itemUuid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemUuid;
	}
	
	public void closeConnIfOpen() {
		gateway.closeConn();
	}
	
	public int getOriginalProductQuan(String id){
		int itemId = gateway.getOriginalProductQuan(id);
		return itemId;
	}
	
	public void removeFromInventory() {
		gateway.removeRowInInventory(this);
	}

	public void updateInventory() {
		gateway.updateRowInInventory(this);
	}
	
	public String getProductDescript(String id){
		String itemId = gateway.getProductDescript(id);
		return itemId;
	}
	
	public String getProductTemplateNum(String tempNum){
		String num = gateway.getProductTemplateNum(tempNum);
		return num;
	}
	
	public ArrayList<String> getProductReqs(String productTemplateId) {
		ArrayList<String> partReqs = gateway.getProductReqs(productTemplateId);
		return partReqs;
	}
	
	public ArrayList<String> getPartsAtLocation(String location) {
		ArrayList<String> locationParts = gateway.getPartsAtLocation(location);
		return locationParts;
	}
	
	public void updateSinglePartQuanAtLocation(String location, String locationPartName, int newPartQuan) {
		gateway.updateSinglePartAtLocation(location, locationPartName, newPartQuan);
	}

	public ArrayList<String> getItemListNames() {
		ArrayList<String> partNameList = gateway.getPartListNames();
		ArrayList<String> productNameList = gateway.getProductListNames();
		ArrayList<String> itemNameList = new ArrayList<String>(partNameList);
		itemNameList.addAll(productNameList);
		return itemNameList;
	}
	
	//getters and setters
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setItemQuan(String quantity) {
		int var = Integer.valueOf(quantity);
		this.quantity = var;
	}

	public String getLocation() {
		return location;
	}
	
	public int getNextProductIdForDatabase(){
		int newId = gateway.getNextProductIdForDatabase();
		return newId;
	}
	
	public int getNextPartIdForDatabase(){
		int newId = gateway.getNextPartIdForDatabase();
		return newId;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public int getItemUuid() {
		return uuid;
	}
	
	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void closeConnection() {
	}
}
