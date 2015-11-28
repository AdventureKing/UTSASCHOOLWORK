package PartListGUI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import InventoryDetailGUI.InventoryDetailModel;
import PartDetailGUI.PartDetailModel;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

//Manages the SQL database and holds needed stored values
public class PartListModel {
	
	//initialize storage variable
	ArrayList<PartDetailModel> partList;
	PartListGateway gateway;
	private String[] columnNames = { "Part Uuid", "Part Number", "Part Name", "Vendor", "Unit of Quantity", "Ext.Part #" };

	// instantiates the base model
	public PartListModel() {
		partList = new ArrayList<PartDetailModel>();
		gateway = new PartListGateway();
	}
	
	// gets the column names for the JTable
	public String[] getColumnNames() {return columnNames;}

	// need to change to generate a array list of part models then i need
	//to pick that apart and put that data base into the local copy
	//then i need to update the table with a object created off the new list
	public Object[][] getData() {
		if(partList.size() == 0)
			partList = gateway.generatePartInventory();
		else
		{
			partList.clear();
			partList = gateway.generatePartInventory();
		}
		int i=0;
		Object[][] data = new Object[partList.size()][columnNames.length] ;
		for(PartDetailModel part : partList){
			int id = part.getPartUuid();
			String partNum = part.getPartNum();
			String partName = part.getPartName();
			String vendor = part.getVendor();
			String quanUnit = part.getQuanUnit();
			String extPartNum = part.getExtPartNum();
			Object object[] = { id, partNum, partName, vendor, quanUnit, extPartNum };
			data[i] = object;
			i++;
		}
		return data;
		
	}
	
	//check against local STILL NOT UPDATED TO PLM!
	public int inventoryPartCheck(PartDetailModel inventoryPart){
		for(PartDetailModel part : partList){
			if(part.getPartName().toLowerCase().equals(inventoryPart.getPartName().toLowerCase()) 
				&& part.getVendor().toLowerCase().equals(inventoryPart.getVendor().toLowerCase()) 
				&& inventoryPart.getPartUuid() != part.getPartUuid()) {
				
				return 1;
			}
		}
		return 0;
	}
	
	//NOT UPDATED TO PLM
	public void addtoInventory(PartDetailModel partModel){
		gateway.addToPartList(partModel);	
	}
	
	//NOT UPDATED TO PLM
	public void removefromInventory(InventoryDetailModel partModel){
	}
}
