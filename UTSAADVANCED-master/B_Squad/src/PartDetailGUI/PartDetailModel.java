package PartDetailGUI;

import java.sql.SQLException;
import java.util.ArrayList;

public class PartDetailModel {

	// required are part#,part name, and quantity
	private String partName;
	private String quanUnit;
	private int uuid;
	private String vendor;
	private String extPartNum;
	private String partNum;
	
	private PartDetailGateWay gateway;
	

	public PartDetailModel(int uuid, String partNum, String partName, String vendor, String quanUnit, String extPartNum) {
		this.uuid = uuid;
		this.partName = partName;
		this.partNum = partNum;
		this.vendor = vendor;
		this.quanUnit = quanUnit;
		this.extPartNum = extPartNum;
		gateway = new PartDetailGateWay();
	}

	//database edits
	
	public void removeFromInventory() {
		gateway.removeRowInPartList(this);
	}
	
	public boolean checkPartInTemplates(String partNum){
		ArrayList<String> partNumbers = gateway.getPartsInTemplates();
		if(partNumbers.contains(partNum)){
			return true;
		}else {
			return false;
		}
	}

	public void updateInventory() {
		gateway.updateRowInPartList(this);
	}

	public ArrayList<String> getPartListNames() {
		ArrayList<String> partNameList = gateway.getPartListNames();
		return partNameList;
	}
	
	
	//get/set part name
	public String getPartName() {return partName;}
	public void setPartName(String partName) {this.partName = partName;}

	//get/set part number
		public String getPartNum() {return partNum;}
		public void setPartNum(String partNum) {this.partNum = partNum;}

	//get/set vendor
	public String getVendor() {return vendor;}
	public void setVendor(String vendor) {this.vendor = vendor;}

	//get/set uuid
	public int getUuid() {return uuid;}
	public void setUuid(int uuid) {this.uuid = uuid;}
	
	//get/set part quantity unit
	public String getQuanUnit() {return quanUnit;}
	public void setQuanUnit(String quanUnit) {this.quanUnit = quanUnit;}
	
	//get/set part external part number
	public String getExtPartNum() {return extPartNum;}
	public void setExtPartNum(String extPartNum) {this.extPartNum = extPartNum;}
	//create object for the table
	public Object[] getPartInfo() {
		Object[] partInfo = { this.uuid, this.partNum, this.partName, this.vendor, this.quanUnit, this.extPartNum };
		return partInfo;
	}

	public int getPartUuid() {return uuid;}
	
	public int lockRecord(int partUuid) {
		try {
			return gateway.lockRow(partUuid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partUuid;
	}

	public void closeConnIfOpen() {
		gateway.closeConn();
	}

	
}
