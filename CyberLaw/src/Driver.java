import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

import SimObjects.simPersonModel;
import SimObjects.simViewController;
import SimObjects.simViewData;
import Simulation.simStateModel;

public class Driver {

	private simStateModel simStateData;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<LinkedList> simOutPutList = new ArrayList<LinkedList>();

		
		
		simStateModel masterModel = new simStateModel(simOutPutList);
		simViewData masterView = new simViewData(masterModel);
		simViewController masterController = new simViewController(masterModel,masterView);
		masterView.registerListerners(masterController);
		masterView.setVisible(true);
		masterView.setSize(1000,1000);
		masterView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		


	}

	
}
