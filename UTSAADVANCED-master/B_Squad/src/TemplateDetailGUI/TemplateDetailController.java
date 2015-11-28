package TemplateDetailGUI;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Session.UserState;
import TemplateGUI.TemplateView;
import TemplateGUI.TemplateModel;

public class TemplateDetailController implements ActionListener {

	private TemplateDetailView templateDetailView;
	private TemplateDetailModel templateDetailModel;
	private TemplateView templateView;
	private TemplateModel templateModel;
	private UserState programState;

	// instantiates the part window controller
	public TemplateDetailController(TemplateDetailModel tempDetailModel, TemplateDetailView tempDetailView, TemplateModel tempModel, TemplateView templateView, UserState programState) {
		this.templateView = templateView;
		this.templateModel = tempModel;
		this.templateDetailView = tempDetailView;
		this.templateDetailModel = tempDetailModel;
		this.programState = programState;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		System.out.println("Testicular: " + command);

		// determines what to do if add is pressed in part window
		if (command.equals("Add")) {
			//all checks needed to pass
			
			if (!templateDetailView.getTemplateNumber().getText().toString().startsWith("A"))  {
				templateDetailView.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,"Error, Template Numbers must start with 'A'.");
			} else if(templateDetailModel.checkTemplateNumber(templateDetailView.getTemplateNumber().getText().toString())){
				templateDetailView.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,"Error, That Template already Exists. Try again.");	
			} else { 
				
			
				String templateNum = templateDetailView.getTemplateNumber().getText().toString();
				String description = templateDetailView.getTemplateDescript().getText().toString();
				
				// need checks right here for the part model to before inserting
				templateDetailModel = new TemplateDetailModel(0, templateNum, description);
				if (templateModel.templateCheck(templateDetailModel) == 0) {
					// insert into the database then update the inventory view
					// with current database
					templateModel.addTemplate(templateDetailModel);
					templateView.updateRow();
					templateDetailModel.createNewTemplatePartsTable(templateNum);
					
				
					
				} else {
					//if data isn't good then don't open
					Component frame = null;
					JOptionPane.showMessageDialog(frame,"Error Check data and resubmit.");
				}
			}
		} else if (command.equals("Save")) {
			System.out.println("TRYING TO SAVE!!");
			//all checks needed to pass 
			if (!templateDetailView.getTemplateNumber().getText().toString().startsWith("A"))  {
				templateDetailView.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,"Error, Template Numbers must start with 'A'.");
			} else { 
				//refresh text fields
	
				int templateUuid = Integer.parseInt(templateDetailView.getTxtfldUuid().getText().toString());
				String templateNum = templateDetailView.getTemplateNumber().getText().toString();
				String description = templateDetailView.getTemplateDescript().getText().toString();
				
				// need checks right here for the part model to before inserting
				TemplateDetailModel tempModel = new TemplateDetailModel(templateUuid, templateNum, description);
				
				//check inventory to make sure changes are acceptable
				if (templateModel.templateCheck(tempModel) == 0) {
					//update inventory
					// with current database
					System.out.println(templateNum);
					
					templateDetailModel.setTemplateNum(templateNum);
					templateDetailModel.setTemplateDescript(description);
					
					templateDetailModel.updateInventory();
					templateDetailView.repaintTextFields();
					templateView.updateRow();

					
				} else {
					//if data isnt good then dont open
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "Error Check data and resubmit.");
				}	
			}
		}
	}
}
