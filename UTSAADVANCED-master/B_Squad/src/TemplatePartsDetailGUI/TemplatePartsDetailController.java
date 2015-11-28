package TemplatePartsDetailGUI;



import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import TemplateGUI.TemplateView;
import TemplateGUI.TemplateModel;
import TemplatePartsGUI.TemplatePartsModel;
import TemplatePartsGUI.TemplatePartsView;

public class TemplatePartsDetailController implements ActionListener {

	private TemplatePartsDetailView templatePartsDetailView;
	private TemplatePartsDetailModel templatePartsDetailModel;
	private TemplatePartsModel model;
	private TemplatePartsView view;

	// instantiates the part window controller
	public TemplatePartsDetailController(TemplatePartsDetailModel templatePartsDetailModel, TemplatePartsDetailView templatePartsDetailView, TemplatePartsModel model, TemplatePartsView view) {
		this.templatePartsDetailView = templatePartsDetailView;
		this.templatePartsDetailModel = templatePartsDetailModel;
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		// determines what to do if add is pressed in part window
		if (command.equals("Add")) {
			//all checks needed to pass
			
			if (Integer.parseInt(templatePartsDetailView.getTxtfldQuan().getText().toString()) <= 0){	
				view.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,"Error. Quantity must be greater than 0.");
			} else if(templatePartsDetailModel.checkPartNumber(templatePartsDetailView.getPartNumbers().getSelectedItem().toString())){
				templatePartsDetailView.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,"Error, That Part already Exists For This Template. Try again.");		
				
			} else {
			
				String templateNum = templatePartsDetailView.getTxtfldProductTemplateId().getText().toString();
				String partId = templatePartsDetailView.getPartNumbers().getSelectedItem().toString();
				int partQuan = Integer.parseInt(templatePartsDetailView.getTxtfldQuan().getText().toString());
				
				// need checks right here for the part model to before inserting
				 templatePartsDetailModel = new TemplatePartsDetailModel(templateNum, partId, partQuan);
				 templatePartsDetailModel.addPartToTable();
				 
			
				model.updateParts();
				view.updatePartsStrings();
			}
		} else if (command.equals("Save")) {
			
			//all checks needed to pass 
			if (Integer.parseInt(templatePartsDetailView.getTxtfldQuan().getText().toString()) <= 0){	
				view.repaint();
				Component frame = null;
				JOptionPane.showMessageDialog(frame,"Error. Quantity must be greater than 0.");
			}else{
			
				String templateNum = templatePartsDetailView.getTxtfldProductTemplateId().getText().toString();
				String partId = templatePartsDetailView.getTxtfldPartId().getText().toString();
				int partQuan = Integer.parseInt(templatePartsDetailView.getTxtfldQuan().getText().toString());

				// need checks right here for the part model to before inserting
				templatePartsDetailModel.setTemplateNum(templateNum);
				templatePartsDetailModel.setPartNum(partId);
				templatePartsDetailModel.setQuantity(partQuan);
				templatePartsDetailModel.updatePartInTable();
				
				model.updateParts();
				view.updatePartsStrings();
				
			}
		}
	}
}
