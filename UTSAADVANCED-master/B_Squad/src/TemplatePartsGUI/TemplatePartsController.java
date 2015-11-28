package TemplatePartsGUI;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Cabinetron.MasterFrame;
import Cabinetron.MasterFrame.ChildWindow;
import Session.UserState;
import TemplateDetailGUI.TemplateDetailView;
import TemplateGUI.TemplateView;
import TemplateGUI.TemplateModel;
import TemplatePartsDetailGUI.TemplatePartsDetailController;
import TemplatePartsDetailGUI.TemplatePartsDetailGateway;
import TemplatePartsDetailGUI.TemplatePartsDetailModel;
import TemplatePartsDetailGUI.TemplatePartsDetailView;

public class TemplatePartsController implements ActionListener {

	private TemplatePartsModel model;
	private TemplatePartsView view;
	private MasterFrame master;
	private TemplatePartsDetailModel detailmodel;
	private TemplatePartsDetailController detailcontroller;
	
	private TemplatePartsDetailView detailview;
	private UserState programState;
	

	// instantiates the part window controller
	public TemplatePartsController(TemplatePartsModel templatePartsModel, TemplatePartsView templatePartsView, MasterFrame master,UserState programState) {
		this.view = templatePartsView;
		this.model = templatePartsModel;
		this.master = master;
		this.programState = programState;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();


		// determines what to do if add is pressed in part window
		if (command.equals("Add Part")) {
			
		
				
			String templateNum = model.getTemplateNum();
			String partNum = "";
			int quan = 0;
			
			 detailmodel = new TemplatePartsDetailModel(templateNum,partNum,quan);
			 detailview = new TemplatePartsDetailView(model, "Add", detailmodel);
			 detailcontroller = new TemplatePartsDetailController(detailmodel, detailview, model, view);
			 
			 ChildWindow child;
			 child = new MasterFrame().new ChildWindow(this.master,"Add Template Part",null,null, null, null,detailview,programState);
			 master.openChildWindow(child);
				
				//register controller
			detailview.registerListerners(detailcontroller);

		} else if(command.equals("Edit Part")) {
			
			
			int selFromList = view.getList().getSelectedIndex();
			System.out.println(selFromList);
			if(selFromList == -1){
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Please Select a Entry.");
				
			}else{
				
				//Break up the needed values
				String templateNum = view.getList().getSelectedValue();
				String[] elements = templateNum.split(" | ");
				String templateInfo = elements[0];
				String partInfo = elements[1];
				String partQuanInfo = elements[2];
				
				String[] templateInfoElements = templateInfo.split(" : ");
				templateNum = templateInfoElements[2];
				
				String[] partInfoElements = partInfo.split(" : ");
				String partNum = partInfoElements[2];
				
				String[] quanInfoElements = partQuanInfo.split(" : ");
				int quan = Integer.parseInt(quanInfoElements[2]);
				
			
				String partNam = templateNum + partNum;
				System.out.println(partNam);
				
				
				 detailmodel = new TemplatePartsDetailModel(templateNum,partNum, quan);
				 String id = partNam;
				 String tableName = templateNum + "_parts";
				 System.out.println(tableName);
				 
				
				 detailview = new TemplatePartsDetailView(model, "Save", detailmodel);
				 detailcontroller = new TemplatePartsDetailController(detailmodel, detailview, model, view);
				 detailview.registerListerners(detailcontroller);
				
				 if(detailmodel.lockpart(tableName,id) == 0)
				 {
					 ChildWindow child;
					 child = new MasterFrame().new ChildWindow(this.master,"Edit Template Part",null,null, null, null,detailview,programState);
					 master.openChildWindow(child);
						
						//register controller
					
					
					//locks record here
					
					 }else{
						 //add frame
						 
						 
						 
						 
					 }
				
			}

			
			
		} else if(command.equals("Delete Part")) {
			System.out.println("Begin Delete");
			
			int selFromList = -1;
			selFromList = view.getList().getSelectedIndex();
			
			if(selFromList == -1){
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Please Select a ntry.");
				
			}else{
				
				String templateNum = view.getList().getSelectedValue();
				String[] elements = templateNum.split(" ");
				templateNum = elements[0];
				String partNum = elements[1];
				model.removePart(templateNum, partNum);
				view.updateList(model.getParts());
				view.repaint();
			}
		}
	}
}
