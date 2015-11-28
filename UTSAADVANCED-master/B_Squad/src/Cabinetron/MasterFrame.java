package Cabinetron;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;




import Session.StringBeanRemote;

import Cabinetron.MasterFrame.ChildWindow;
import InventoryDetailGUI.InventoryDetailView;
import InventoryListGUI.InventoryController;
import InventoryListGUI.InventoryModel;
import InventoryListGUI.InventoryView;
import PartDetailGUI.PartDetailView;
import PartListGUI.PartListController;
import PartListGUI.PartListModel;
import PartListGUI.PartListView;
import Session.UserState;
import Session.loginGateway;
import TemplateDetailGUI.TemplateDetailView;
import TemplateGUI.TemplateController;
import TemplateGUI.TemplateModel;
import TemplateGUI.TemplateView;
import TemplatePartsDetailGUI.TemplatePartsDetailView;
import TemplatePartsGUI.TemplatePartsView;

public class MasterFrame extends JFrame{
	

	private JMenuItem menuLogOut;
	
	//users for the program
	UserState programState = null;
	private int productionManager = 0;
	private int inventoryManager = 1;
	private int admin = -1;
	private loginWindow login;
	ArrayList<ChildWindow> openWindows = new ArrayList<ChildWindow>();
	
	//desktop view you see on start up
	private JDesktopPane desktop;
	
	//stagger the frames
	private int newFrameX = 100, newFrameY = 100;
	
	public MasterFrame() {
	
	
	//main menu bar
	JMenuBar menuBar = new JMenuBar();
	
	//create mdi desktop
	desktop = new JDesktopPane(){
		 
		private static final long serialVersionUID = 1L;
		private ImageIcon imageIcon;
		    {
		    	//background image
		        imageIcon = new ImageIcon(this.getClass().getResource("20121219-1.jpg"));
		    }
		    Image image1 = imageIcon.getImage();
		    Image newimage = image1.getScaledInstance(500, 800, Image.SCALE_SMOOTH);
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		       g.drawImage(newimage,0,0,this);
		    }
		 };
	
	add(desktop);
	
	//all the drop down menus
	JMenu menu = new JMenu("File");
	JMenu menuList = new JMenu("List");
	JMenu menuTemplate = new JMenu("Templates");
	JMenu menuUsers = new JMenu("Login");
	
	
	//elements in the drop downs
	JMenuItem menuQuit = new JMenuItem("Quit");
	//menu login names
	JMenuItem menuLogin = new JMenuItem("Login as a User");
	menuLogin.addActionListener((new ActionListener(){
	public void actionPerformed(ActionEvent e){
		if(programState == null){
			 login = new loginWindow();
			login.setSize(300, 200);
			login.show();
		}else{
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "You're already logged in.");
		}
	}
	}));

	
	
	menuLogOut = new JMenuItem("Log Out");
	menuLogOut.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			closeChildren();
			programState = null;
			updateTitle();
		}

	});
	
	menuQuit.addActionListener((new ActionListener(){
		public void actionPerformed(ActionEvent e){
			MasterFrame.this.dispatchEvent(new WindowEvent(MasterFrame.this, WindowEvent.WINDOW_CLOSING));
		}
	}));
		
	JMenuItem menuInventoryList = new JMenuItem("Inventory List");
	menuInventoryList.addActionListener((new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(programState == null) {
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Please log In");
			}else if(programState != null) {
				ChildWindow window = new ChildWindow(MasterFrame.this,"Inventory List",null,null,null, null, null, programState);
				openChildWindow(window);
			}
		}
	}));
	
	JMenuItem menuPartList = new JMenuItem("Part List");
	menuPartList.addActionListener((new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(programState == null)
			{
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Please log In");
			}else if(programState != null){
				ChildWindow window = new ChildWindow(MasterFrame.this,"Parts List",null,null,null,null, null,programState);	
				openChildWindow(window);
			}
		}
	}));
	
	JMenuItem menuTemplateList = new JMenuItem("Item Templates");
	menuTemplateList.addActionListener((new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int allow = 0;
			if(programState == null)
			{
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Please log In");
			}else if(programState != null && programState.canViewProductTemplates() == allow  ) {
				ChildWindow window = new ChildWindow(MasterFrame.this,"Item Templates",null,null,null,null, null,programState);
				openChildWindow(window);
			}else {
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "You aint got enough permissions bro!");
			}
		}
	}));
	
	
	
	//add elements under menu
	menu.add(menuQuit);
	
	//add items to the under list
	menuList.add(menuInventoryList);
	menuList.add(menuPartList);
	menuTemplate.add(menuTemplateList);
	
	//add login names to menu
	menuUsers.add(menuLogin);

	
	menuUsers.add(menuLogOut);
	
	//add elements to the main menu bar
	menuBar.add(menu);
	menuBar.add(menuUsers);
	menuBar.add(menuList);
	menuBar.add(menuTemplate);
	
	//set the menu bar
	setJMenuBar(menuBar);
	updateTitle();
	}
	
	private void closeChildren() {
		JInternalFrame [] children = desktop.getAllFrames();
		for(int i = children.length - 1; i >= 0; i--)
			children[i].setVisible(false);
	}
	
	private void updateTitle() {
		setTitle("Logged Out");
	}
	
	private void hitCancel(){
		login.dispose();
	}
	
	
	
	public void logUserIn(int access,String loginName, String password){
		//create program state
		programState = new UserState(access,loginName,password);
		
		
		if(access == productionManager){
			setTitle("Logged in as Production Manager " + loginName);
		}
		else if(access == inventoryManager){
			setTitle("Logged in as Inventory Manager " + loginName);
		}
		else if( access == admin){
			setTitle("Logged in as Administrator " + loginName);
		}
		login.dispose();
	
	}

	
	
	
	
	
	public class ChildWindow extends JPanel{
		private static final long serialVersionUID = 1L;
		
		//child window set up
		private MasterFrame master;
		private int allow = 0;
		private String windowName;
		public InventoryDetailView detailInventoryView;
		public PartDetailView detailPartView;
		public TemplateDetailView templateView;
		public TemplatePartsDetailView detailview;

		private UserState programState;
		
		
		//this is where you will create the window
		//send a unique window name into this function so the if checks map the right stuff
		public ChildWindow(MasterFrame master,String windowName, InventoryDetailView partView, PartDetailView detailPartView, TemplateDetailView templateView, TemplatePartsView templatePartsView, TemplatePartsDetailView detailview, UserState userSession){
			this.master = master;
			this.templateView = templateView;
			this.detailInventoryView = partView;
			this.detailPartView = detailPartView;
			this.detailview = detailview;
			this.programState = userSession;
			this.setWindowName(windowName);
			this.setLayout(new BorderLayout());
			
			
			if(windowName.equals("Inventory List")){
				
				//if a user wants to see the inventory list
				InventoryModel model = new InventoryModel();
				InventoryView view = new InventoryView(model);
				InventoryController inventoryController = new InventoryController(view,model,master,programState);
				view.registerListerners(inventoryController);
				
				this.add(view,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(700,300));
				
				
			}else if(windowName.equals("Parts List")){
				
				//if a user wants to see the par list
				PartListModel partListModel = new PartListModel();
				PartListView partListView = new PartListView(partListModel);
				PartListController partListCont = new PartListController(partListView, partListModel,master,programState);
				partListView.registerListerners(partListCont);
				
				partListView.setVisible(true);
				this.add(partListView,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(700,300));
		
				
			}else if(windowName.equals("Item Templates")){
				
				//if a user wants to see the templates
				
				TemplateModel templateModel = new TemplateModel();
				TemplateView tempView = new TemplateView(templateModel);
				TemplateController templateCont = new TemplateController(tempView, templateModel, master,programState);
				
			    tempView.registerListerners(templateCont);
				tempView.setVisible(true);
				this.add(tempView,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(700,300));
		
				
				
			}else if(windowName.equals("New Part")){
				this.add(partView,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(400,400));
				
			}else if(windowName.equals("Add List Part")){
				
				this.add(detailPartView,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(400,300));
				
			}else if(windowName.equals("Existing Part")){
				this.add(partView,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(400,400));
				
			}else if(windowName.equals("Existing List Part")){
				this.add(detailPartView,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(400,300));
				
			}else if(windowName.equals("New Template")){
				this.add(templateView,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(400,300));
				
			}else if(windowName.equals("Existing Template")){
				this.add(templateView,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(400,300));
				
			}else if(windowName.equals("Parts For Template")){
				this.add(templatePartsView,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(400,300));
				
			}else if(windowName.equals("Add Template Part")){
				this.add(detailview,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(400,300));
				
			}else if(windowName.equals("Edit Template Part")){
				this.add(detailview,BorderLayout.CENTER);
				this.setPreferredSize(new Dimension(400,300));
			}
		}

		public String getWindowName() {
			return windowName;
		}

		public void setWindowName(String windowName) {
			this.windowName = windowName;
		}
		
	}
	
	
	public void openChildWindow(final ChildWindow child){
		
		//create a internal frame in the master frame
		final JInternalFrame childWindow = new JInternalFrame(child.getWindowName(),true,true,true,true);
		

		childWindow.addInternalFrameListener((new InternalFrameListener(){
			
				@Override
				public void internalFrameActivated(InternalFrameEvent e) {
			
				}

				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
				
				}
				
				//user needs to hit x in the top corner to close window
				@Override
				public void internalFrameClosing(InternalFrameEvent e) {
					//check
					System.out.println("x pressed");
					//if a partview is open 
					if(child.detailInventoryView != null && !child.detailInventoryView.getTxtfldUuid().getText().equals("Will Automatically be assigned") )
					{
					int uuid = Integer.parseInt(child.detailInventoryView.getTxtfldUuid().getText());
					System.out.println(uuid);
					child.detailInventoryView.getView().removePart(uuid);
					child.detailInventoryView.getItem().closeConnIfOpen();
					childWindow.dispose();
					}else if(child.detailPartView != null && !child.detailPartView.getTxtfldUuid().getText().equals("Will Automatically be assigned"))
					{
						
					int uuid = Integer.parseInt(child.detailPartView.getTxtfldUuid().getText());
					System.out.println(uuid);
					child.detailPartView.getPart().closeConnIfOpen();
					child.detailPartView.getView().removePart(uuid);
					 childWindow.dispose();
					}else if(child.templateView != null && !child.templateView.getTxtfldUuid().getText().equals("Will Automatically be assigned"))
					{
						
				    int uuid = Integer.parseInt(child.templateView.getTxtfldUuid().getText());
					child.templateView.getTemplate().closeConnIfOpen();
					child.templateView.getView().removeTemplate(uuid);
					 childWindow.dispose();
					}else if(child.detailview != null)
					{
						
					    
						child.detailview.getModel().closeConnIfOpen();
						
						 childWindow.dispose();
						}	
				}

				@Override
				public void internalFrameDeactivated(InternalFrameEvent e) {
	
				}

				@Override
				public void internalFrameDeiconified(InternalFrameEvent e) {
			
				}

				@Override
				public void internalFrameIconified(InternalFrameEvent e) {
			
				}

				@Override
				public void internalFrameOpened(InternalFrameEvent e) {
			
				}

			}));
		
		childWindow.add(child,BorderLayout.CENTER);
		childWindow.pack();
		
		//set location of the window
		childWindow.setLocation(newFrameX, newFrameY);
		newFrameX = (newFrameX + 30) % desktop.getWidth();
		newFrameY = (newFrameY + 30) % desktop.getHeight();
		childWindow.setVisible(true);
		desktop.add(childWindow);
		childWindow.setVisible(true);
		childWindow.moveToFront();
	}
	

	public class loginWindow extends JFrame{
		private loginGateway login = new loginGateway();
		private JPasswordField passwordField;
		private JTextField textField;
		public loginWindow() {
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{0, 0, 0, 92, 0, 0};
			gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
			gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			getContentPane().setLayout(gridBagLayout);
			
			JLabel lblUsername = new JLabel("Username:");
			GridBagConstraints gbc_lblUsername = new GridBagConstraints();
			gbc_lblUsername.anchor = GridBagConstraints.EAST;
			gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsername.gridx = 2;
			gbc_lblUsername.gridy = 2;
			getContentPane().add(lblUsername, gbc_lblUsername);
			
			textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 3;
			gbc_textField.gridy = 2;
			getContentPane().add(textField, gbc_textField);
			textField.setColumns(10);
			
			JLabel lblPassword = new JLabel("Password:");
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.gridx = 2;
			gbc_lblPassword.gridy = 3;
			getContentPane().add(lblPassword, gbc_lblPassword);
			
			passwordField = new JPasswordField();
			GridBagConstraints gbc_passwordField = new GridBagConstraints();
			gbc_passwordField.insets = new Insets(0, 0, 5, 5);
			gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordField.gridx = 3;
			gbc_passwordField.gridy = 3;
			getContentPane().add(passwordField, gbc_passwordField);
			
			Button button = new Button("Login");
			button.addActionListener(new ActionListener() {
				
				
				
				public void actionPerformed(ActionEvent arg0) {
					// add validation
					String password = new String(passwordField.getPassword());
					int access = login.checklogin(textField.getText(), password);
					if(access == 1 || access  == 0||access  == -1 ){
						System.out.println("logged in in main frame");
						logUserIn(access,textField.getText(),password);
						
						try {
							Properties props = new Properties();
							props.put("org.omg.CORBA.ORBInitialHost", "localhost");
				            props.put("org.omg.CORBA.ORBInitialPort", "3700");
				            
				            //connect to the remote EJB
							InitialContext itx = new InitialContext(props);
					        //sessionState = (StateBeanRemote) itx.lookup("java:global/cs4743_session_bean/StateBean!session.StateBeanRemote");
							Session.StringBeanRemote sessionState = (Session.StringBeanRemote) itx.lookup("java:global/cs4743_demo2_ejb/StringBean!session.StringBeanRemote");
							
							//create an observer for this frame and register it with the remote EJB
							//Cabinetron.StringObserver stateObserver = new StringObserver(this);
							//sessionState.registerObserver(stateObserver);
							
						} catch (NamingException e1) {
							e1.printStackTrace();
						}
					}else{
						Component frame = null;
						JOptionPane.showMessageDialog(frame, "Failed To log in!");
					}
					
				}
				
				
			});
			
			
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.insets = new Insets(0, 0, 5, 5);
			gbc_button.gridx = 2;
			gbc_button.gridy = 5;
			getContentPane().add(button, gbc_button);
			
			Button button_1 = new Button("Cancel");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hitCancel();
				}
			});
			GridBagConstraints gbc_button_1 = new GridBagConstraints();
			gbc_button_1.insets = new Insets(0, 0, 5, 5);
			gbc_button_1.gridx = 3;
			gbc_button_1.gridy = 5;
			getContentPane().add(button_1, gbc_button_1);
		}
	
	
	}
}




