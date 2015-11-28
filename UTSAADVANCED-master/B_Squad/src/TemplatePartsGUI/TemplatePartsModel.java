package TemplatePartsGUI;

import java.sql.SQLException;
import java.util.ArrayList;


public class TemplatePartsModel {
	
	ArrayList<String> parts = new ArrayList<String>();
	String templateNum;
	TemplatePartsGateway gateway = new TemplatePartsGateway();

	public TemplatePartsModel(String templateNum, ArrayList<String> parts) {
		this.parts = parts;
		this.templateNum = templateNum;
	}

	public ArrayList<String> getParts() {
		return parts;
	}
	
	public String getTemplateNum(){
		return templateNum;
	}

	public void setParts(ArrayList<String> templateParts) {
		this.parts = templateParts;
	}

	public void removePart(String templateNum, String partNum) {
		gateway.removeRowInTemplateParts(templateNum, partNum);
		this.parts = gateway.getTemplateParts(templateNum);
	}
	public void updateParts(){
		this.parts = gateway.getTemplateParts(templateNum);
	}

	public void closeConnIfOpen() {
		gateway.closeConn();
	}
}
