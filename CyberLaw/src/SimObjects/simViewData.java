package SimObjects;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Simulation.simStateModel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import java.awt.ScrollPane;

public class simViewData extends JFrame{
	
	private simStateModel masterModel;
	private JButton runSim;
	private JTextField userCount;
	private JTextField cycleNumber;
	private JTextField textFieldNumberOfUsers;
	
	private JTextField textFieldCycles;
	private JPanel panel;
	private JButton btnRunSimulation; 
	private JTabbedPane tabbedPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JTextArea textArea;
	
	private JTextArea textArea_1;
	private JTextArea textArea_2;
	public simViewData(simStateModel masterModel){
		
		this.masterModel = masterModel;
		 panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNumberOfUsers = new JLabel("Number Of Users (Max 20)");
		lblNumberOfUsers.setBounds(117, 30, 191, 20);
		panel.add(lblNumberOfUsers);
		
		JLabel lblNumberOfCycles = new JLabel("Number Of Cycles");
		lblNumberOfCycles.setBounds(510, 30, 128, 20);
		panel.add(lblNumberOfCycles);
		 
		 textFieldNumberOfUsers = new JTextField(30);
		 textFieldNumberOfUsers.setBounds(117, 55, 123, 26);
		 panel.add(textFieldNumberOfUsers);
		 textFieldNumberOfUsers.setColumns(10);
		 
		 textFieldCycles = new JTextField(30);
		 textFieldCycles.setBounds(520, 55, 118, 26);
		 panel.add(textFieldCycles);
		 textFieldCycles.setColumns(10);
		 
		 tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		 tabbedPane.setBounds(117, 105, 700, 564);
		 panel.add(tabbedPane);
		 
		 //panel in the tab pane
		 panel_1 = new JPanel();
		 tabbedPane.addTab("Original Data", null, panel_1, null);
		 //scroll area
		 textArea = new JTextArea(29, 50);
		 JScrollPane sp = new JScrollPane(textArea); 
		 panel_1.add(sp);
		 //panel in the tab pane
		 panel_2 = new JPanel();
		 tabbedPane.addTab("Government Adjusted Data", null, panel_2, null);
		 //scroll area
		 textArea_1 = new JTextArea(29, 50);
		 JScrollPane sp1 = new JScrollPane(textArea_1); 
		 panel_2.add(sp1);
		 //panel in the tab
		 panel_3 = new JPanel();
		 tabbedPane.addTab("Government Punishment Data", null, panel_3, null);
		 //scroll area
		 textArea_2 = new JTextArea(29, 50);
		 JScrollPane sp2 = new JScrollPane(textArea_2); 
		 panel_3.add(sp2);
		 
		  btnRunSimulation = new JButton("Run Simulation");
		  btnRunSimulation.setBounds(299, 685, 141, 29);
		  panel.add(btnRunSimulation);
		
		
	}
	public void registerListerners(simViewController Controller) {
		
		btnRunSimulation.addActionListener(Controller);
	}
	public simStateModel getMasterModel() {
		return masterModel;
	}
	public void setMasterModel(simStateModel masterModel) {
		this.masterModel = masterModel;
	}
	public JButton getRunSim() {
		return runSim;
	}
	public void setRunSim(JButton runSim) {
		this.runSim = runSim;
	}

	public JTextField getTextFieldCycles() {
		return textFieldCycles;
	}
	public void setTextFieldCycles(JTextField textFieldCycles) {
		this.textFieldCycles = textFieldCycles;
	}
	public JTextField getTextFieldNumberOfUsers() {
		return textFieldNumberOfUsers;
	}
	public void setTextFieldNumberOfUsers(JTextField textFieldNumberOfUsers) {
		this.textFieldNumberOfUsers = textFieldNumberOfUsers;
	}
	public JTextArea getTextArea() {
		return textArea;
	}
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	public JTextArea getTextArea_1() {
		return textArea_1;
	}
	public void setTextArea_1(JTextArea textArea_1) {
		this.textArea_1 = textArea_1;
	}
	public JTextArea getTextArea_2() {
		return textArea_2;
	}
	public void setTextArea_2(JTextArea textArea_2) {
		this.textArea_2 = textArea_2;
	}
}
