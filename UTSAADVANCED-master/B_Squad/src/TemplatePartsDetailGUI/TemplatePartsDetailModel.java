package TemplatePartsDetailGUI;


import java.sql.SQLException;
import java.util.ArrayList;

public class TemplatePartsDetailModel {
	
	private String templateNum;
	private String partNum;
	private int quantity;
	private TemplatePartsDetailGateway gateway;
	
	public TemplatePartsDetailModel(String templateNum, String partNum, int quan){
		this.templateNum = templateNum;
		this.partNum = partNum;
		this.quantity = quan;
		this.gateway = new TemplatePartsDetailGateway();
		
	}
	
	public String getTemplateNum(){
		return templateNum;
	}
	
	public void setTemplateNum(String num){
		this.templateNum = num;
	}
	
	public String getPartNum(){
		return partNum;
	}
	
	public void setPartNum(String num){
		this.partNum = num;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	
	public void addPartToTable(){
		gateway.addPartToTable(templateNum, partNum, quantity);
	}
	
	public void updatePartInTable(){
		gateway.updatePartInTable(templateNum, partNum, quantity);
	}
	
	public boolean checkPartNumber(String num){
		ArrayList<String> templateNums = gateway.getPartNumsInTemplateTable(templateNum);
		if(templateNums.contains(num)){
			return true;
		}else{
			return false;
		}
	}
	
	public ArrayList<String> getTemplateParts(String templateNum){
		ArrayList<String> parts = gateway.getTemplateParts(templateNum);
		return parts;
	}
	
	public ArrayList<String> getAllPartNumbers(){
		ArrayList<String> partNums = gateway.getAllPartNumbers();
		return partNums;
	}

	public int lockpart(String tableName, String id) {
		try {
			return gateway.lockRow(tableName,id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
		
	}

	public void closeConnIfOpen() {
		// TODO Auto-generated method stub
		gateway.closeConn();
	}

}