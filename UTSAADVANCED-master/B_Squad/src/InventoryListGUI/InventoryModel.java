package InventoryListGUI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import InventoryDetailGUI.InventoryDetailModel;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

//Manages the SQL database and holds needed stored values
public class InventoryModel {
	ArrayList<InventoryDetailModel> inventoryList;
	GateWayInventory gateway;
	
	// declares storage variables
	//names of columns
	private String[] columnNames = { "Item Uuid", "Item ID", "Item Name", "Quantity", "Location" };

	// instantiates the base model
	public InventoryModel() {
		inventoryList = new ArrayList<InventoryDetailModel>();
		gateway = new GateWayInventory();
	}
	
	// gets the column names for the JTable
	public String[] getColumnNames() {
		return columnNames;
	}

	// need to change to generate a array list of part models then i need
	//to pick that apart and put that data base into the local copy
	//then i need to update the table with a object created off the new list
	public Object[][] getData() {
		
		if(inventoryList.size() == 0)
		inventoryList = gateway.generateInventory();
		else
		{
			inventoryList.clear();
			inventoryList = gateway.generateInventory();
		}
		int i=0;
		Object[][] data = new Object[inventoryList.size()][columnNames.length] ;
		for(InventoryDetailModel item : inventoryList){
			int id = item.getItemUuid();
			String itemId = item.getItemId();
			String partName = item.getItemName();
			int partQuantity = item.getQuantity();
			String partLocation = item.getLocation();
			Object object[] = { id, itemId, partName, partQuantity, partLocation };
			data[i] = object;
			i++;
		}
		return data;
		
	}
	
	//check against local
	public int inventoryPartCheck(InventoryDetailModel inventoryPart){
		for(InventoryDetailModel part : inventoryList){
			if(part.getItemName().toLowerCase().equals(inventoryPart.getItemName().toLowerCase()) && part.getLocation().toLowerCase().equals(inventoryPart.getLocation().toLowerCase()) && inventoryPart.getItemUuid() != part.getItemUuid() ){
				return 1;
			}
		}
		return 0;
	}
	//add inventory to database
	public void addtoInventory(InventoryDetailModel partModel){
		gateway.addToInventory(partModel);
		
	}

	

}
