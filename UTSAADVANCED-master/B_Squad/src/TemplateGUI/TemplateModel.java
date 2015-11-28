package TemplateGUI;

import java.util.ArrayList;

import InventoryDetailGUI.InventoryDetailModel;
import InventoryListGUI.GateWayInventory;
import TemplateDetailGUI.TemplateDetailModel;

public class TemplateModel {
	ArrayList<TemplateDetailModel> templateList;
	TemplateGateway gateway;
	
	// declares storage variables
	//names of columns
	private String[] columnNames = { "Part Uuid", "Product #", "Description"};

	// instantiates the base model
	public TemplateModel() {
		System.out.println("TEST1!");
		templateList = new ArrayList<TemplateDetailModel>();
		gateway = new TemplateGateway();
	}
	
	// gets the column names for the JTable
	public String[] getColumnNames() {
		return columnNames;
	}

	// need to change to generate a array list of part models then i need
	//to pick that apart and put that data base into the local copy
	//then i need to update the table with a object created off the new list
	public Object[][] getData() {
		
		if(templateList.size() == 0)
		   templateList = gateway.generateTemplates();
		else {
			templateList.clear();
			templateList = gateway.generateTemplates();
		}
		int i=0;
		Object[][] data = new Object[templateList.size()][columnNames.length] ;
		for(TemplateDetailModel template : templateList){
			int id = template.getTemplateUuid();
			String tempNum = template.getTemplateNum();
			String tempDesc = template.getTemplateDescript();
			Object object[] = { id, tempNum, tempDesc };
			data[i] = object;
			i++;
		}
		return data;
		
	}
	
	//check against local
	public int templateCheck(TemplateDetailModel template){
		
		/*for(InventoryDetailModel part : templateList){
			if(part.getPartName().toLowerCase().equals(template.getPartName().toLowerCase()) && part.getLocation().toLowerCase().equals(template.getLocation().toLowerCase()) && template.getPartUuid() != part.getPartUuid() ){
				return 1;
			}
		}*/
		return 0;
	}
	
	//add inventory to database
	public void addTemplate(TemplateDetailModel templateModel){
		gateway.addToTemplates(templateModel);
	}
	
	public ArrayList<String> getParts(int uuid, String templateNum){
		ArrayList<String> parts = gateway.getPartsForTemplate(uuid, templateNum);
		return parts;
	}
}
