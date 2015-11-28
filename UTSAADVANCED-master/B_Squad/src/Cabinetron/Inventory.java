package Cabinetron;
import javax.swing.JFrame;
import javax.swing.Timer;


import InventoryListGUI.InventoryController;
import InventoryListGUI.InventoryModel;
import InventoryListGUI.InventoryView;


public class Inventory {
	static Timer t = null;
	public static void main(String[] args) {		
		System.out.println("Im the shit");

		MasterFrame frame = new MasterFrame();
		frame.setSize(900, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setVisible(true);		
	}
}
