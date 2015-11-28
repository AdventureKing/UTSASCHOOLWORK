package TemplateDetailGUI;

import java.sql.SQLException;
import java.util.ArrayList;

public class TemplateDetailModel {

	private String partName;

	private int quantity;

	private int uuid;
	private String templateNum;
	private String templateDescript;
	private TemplateDetailGateway gateway;

	public TemplateDetailModel(int uuid, String templateNum, String templateDescript) {
		this.uuid = uuid;
		this.templateNum = templateNum;
		this.templateDescript = templateDescript;
		gateway = new TemplateDetailGateway();
	}

	//create part object for the table
	public Object[] getTemplateInfo() {

		Object[] partInfo = { this.uuid, this.templateNum, this.templateDescript };
		return partInfo;
	}

	//database editing functions

	public int lockRecord(int partUuid) {
		try {
			return gateway.lockRow(partUuid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partUuid;
	}
	
	public void removeFromInventory() {
		gateway.removeRowInTemplateList(this);
		gateway.removeTemplatePartTable(templateNum);
	}

	public void updateInventory() {
		gateway.updateRowInTemplateList(this);
	}

	public ArrayList<String> getTemplateNumbers() {
		ArrayList<String> templateNumbers = gateway.getTemplateNums();
		return templateNumbers;
	}
	
	//getters and setters
	public String getTemplateNum() {
		return templateNum;
	}

	public void setTemplateNum(String tempNum) {
		this.templateNum = tempNum;
	}
	
	public void createNewTemplatePartsTable(String templateNum) {
		gateway.createNewTemplatePartsTable(templateNum);
	}


	public String getTemplateDescript() {
		return templateDescript;
	}

	public void setTemplateDescript(String descript) {
		this.templateDescript = descript;
	}

	public int getTemplateUuid() {
		return uuid;
	}
	

	public ArrayList<String> getPartListNames() {
		ArrayList<String> partNameList = gateway.getPartListNames();
		return partNameList;
	}

	public void setTemplateUuid(int uuid) {
		this.uuid = uuid;
	}
	
	public boolean checkTemplateNumber(String num){
		ArrayList<String> templateNums = gateway.getTemplateNums();
		if(templateNums.contains(num)){
			return true;
		}else{
			return false;
		}
		
	}

	public void closeConnIfOpen() {
		gateway.closeConn();
	}
}
