package Session;

import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import InventoryDetailGUI.InventoryDetailModel;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class loginGateway {
	
	private int productionManager = 0;
	private int inventoryManager = 1;
	
	private int admin = -1;
	
	MysqlDataSource ds= new MysqlDataSource();
	Connection conn;
	private Statement stmt;
	
	void setDsUrl(){
		ds.setURL("jdbc:mysql://devcloud.fulgentcorp.com:3306/tfv024");
	}
	void setDsUser(){
		ds.setUser("tfv024");
	}
	void setDsPassword(){
		ds.setPassword("Z3D6erXZkxHvhO6ra4UD");
	}
	public loginGateway(){
		setDsUrl();
		setDsUser();
		setDsPassword();
		conn = null;
	}
	
	public int checklogin(String userName, String password){
		ResultSet rs = null;

		stmt = null;
		
		int access = -2;
		try {
			if(conn == null)
			conn = ds.getConnection();
			System.out.println("connection made in loginGateway");

			// create statment to push to database
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM login_table");
			

			// instantiate table data for MVC
			

			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				int id = rs.getInt("id");
				String tempUserName = rs.getString("username").toLowerCase();
				String tempPassword = rs.getString("password").toLowerCase();
				int tempaccess = rs.getInt("access");
				if(tempUserName.contains(userName.toLowerCase()) && tempPassword.contains(password.toLowerCase())){
					System.out.println("Logged in correctly");
					access = tempaccess;
					break;
				}
				
			}

		} catch (SQLException e) {
			Component frame = null;
			JOptionPane
			.showMessageDialog(frame,"Locked");
			e.printStackTrace();
		} finally {
			// close rs
			if (rs != null) {
				try {
					rs.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			// close stmt
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			// close conn
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return access;
		
		
	}
}
