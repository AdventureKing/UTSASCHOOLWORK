package SimObjects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import Simulation.simStateModel;

public class simViewController implements ActionListener{

	private simStateModel simStateData;
	private simViewData masterView;
	public simViewController(simStateModel masterModel, simViewData masterView) {
		// TODO Auto-generated constructor stub
		this.simStateData = masterModel;
		this.masterView = masterView;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		String command = event.getActionCommand();
		if(command.equals("Run Simulation")){
			masterView.repaint();
			if(simStateData.getSimOriginalData().size() != 0){
				//clear textarea
				masterView.getTextArea().setText("");
				masterView.getTextArea_1().setText("");
				masterView.getTextArea_2().setText("");
				//RESET MODEL	
				simStateData.setSimOriginalData(new ArrayList<LinkedList>());
				simStateData.setGovernmentSimDataClear();
				simStateData.setSimGovernmentPunishmentDataClear();
			}
			
			//System.out.println(masterView.getTextFieldCycles().getText().toString());
			
			if(masterView.getTextFieldCycles().getText().length() != 0 && masterView.getTextFieldNumberOfUsers().getText().length() != 0){
			int simCount = Integer.parseInt(masterView.getTextFieldNumberOfUsers().getText().toString());
			int simCycle =Integer.parseInt(masterView.getTextFieldCycles().getText().toString());
			
			
			
			simStateData.generateData(simStateData.getSimOriginalData(), simCount, simCycle);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			simStateData.generateGovernmentScore();
			//generate punishment data
			simStateData.generatePunishmentScore();
			
			//MASTER PRINT STATEMENT
			for (int i = 0; i < simStateData.getSimOriginalData().size(); i++) {
			ListIterator<simPersonModel> listIteratorOriginalData = simStateData.getSimOriginalData().get(i).listIterator();
			ListIterator<simPersonModel> listIterator = simStateData.getSimGovernmentData().get(i).listIterator();
			ListIterator<simPersonModel> listIteratorPunishment = simStateData.getSimGovernmentPunishmentData().get(i).listIterator();
			//System.out.println("Original scores: ");
			masterView.getTextArea().append("Run Data: " + simStateData.getRunSums().get(i).toString() + "\n");
			masterView.getTextArea_1().append("Run Data: " + simStateData.getRunSums().get(i).toString()+ "\n");
			masterView.getTextArea_2().append("Run Data: " + simStateData.getRunSums().get(i).toString()+ "\n");
			while (listIteratorOriginalData.hasNext()) {
				int j = i + 1;
				
				masterView.getTextArea().append("Cycle  " + j + " : " + listIteratorOriginalData.next());
				//System.out.println("Cycle  " + j + " : " + listIteratorOriginalData.next());
			}
			//System.out.println("Government adjusted scores: ");
			while (listIterator.hasNext()) {
				int j = i + 1;
				
				masterView.getTextArea_1().append("Cycle  " + j + " : " + listIterator.next());
				//System.out.println("Cycle  " + j + " : " + listIterator.next());
			}
			
			//System.out.println("Government Punishment adjusted scores: ");
			while (listIteratorPunishment.hasNext()) {
				int j = i + 1;
				
				masterView.getTextArea_2().append("Cycle  " + j + " : " + listIteratorPunishment.next());
				//System.out.println("Cycle  " + j + " : " + listIteratorPunishment.next());
			}
		}
		
		//System.out.println("BUTTON WAS PRESSED");
		}else{
			
		}
		}
	}
}
