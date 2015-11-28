package TemplatePartsGUI;

import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import InventoryDetailGUI.InventoryDetailModel;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TemplatePartsGateway {
	MysqlDataSource ds= new MysqlDataSource();
	Connection conn;
	
	void setDsUrl(){
		ds.setURL("jdbc:mysql://devcloud.fulgentcorp.com:3306/tfv024");
	}
	void setDsUser(){
		ds.setUser("tfv024");
	}
	void setDsPassword(){
		ds.setPassword("Z3D6erXZkxHvhO6ra4UD");
	}
	
	public TemplatePartsGateway(){
		setDsUrl();
		setDsUser();
		setDsPassword();
		conn = null;
	}
	public void removeRowInTemplateParts(String templateNum, String partNum) {
		// TODO Auto-generated method stub

		ResultSet rs = null;

		PreparedStatement stmt = null;
		Connection conn2 = null;
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			System.out.println("connection made in TemplatePartsGateway_removeRowInTemplateParts");

			// create statment to push to database
			
			
			String sql = "DELETE FROM " + templateNum.toUpperCase() + "_parts" + " WHERE id=?";
			stmt = (PreparedStatement) conn2.prepareStatement(sql);
			stmt.setQueryTimeout(2);
			// getData from part model
			
			// put data into query

			String partKey = templateNum + partNum;
			stmt.setString(1, partKey);
			stmt.executeUpdate();

		} catch (SQLException e) {
			Component frame = null;
			JOptionPane
			.showMessageDialog(frame,
					"Locked");
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
			if (conn2 != null) {
				try {
					conn2.close();
					conn2 = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public ArrayList<String> getTemplateNums() {
		ArrayList<String> currentPartList = new ArrayList<String>();
		// create connection with the database
		
		setDsUrl();
		setDsUser();
		setDsPassword();


		
		Statement stmt = null;
		Connection conn2 = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		
		// try to fetch the data
		try {
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			System.out.println("Connection Made in TEMPLATEPartsGATEWAY_GETTEMPLATENUMS");
			stmt.setQueryTimeout(3);
			// create statement to get row size
			rs = stmt.executeQuery("SELECT product_number FROM template_table");
		
			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				String productNum = rs.getString("product_number").toUpperCase();
				currentPartList.add(productNum);
			}
			// catches the data fetch exceptions
		} catch (SQLException e) {
			e.printStackTrace();

			// wraps up by closing all open queries
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
			if (conn2 != null) {
				try {
					conn2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		//return the inventory
			return currentPartList;
	}
	
	public ArrayList<String> getPartListNames() {
		
		setDsUrl();
		setDsUser();
		setDsPassword();
		
		ArrayList<String> currentPartList = new ArrayList<String>();
		// create connection with the database
		

		// declare SQL variables to be used
		Statement stmt = null;
		
		ResultSet rs = null;
		Connection conn2 = null;

		
		// try to fetch the data
		try {
			if(conn2 == null){
				conn2 = ds.getConnection();
				System.out.println("Connection Made in TemplatePartsGATEWAY_GETPARTLISTNAMES");
			}
			// create statement to get row size
			stmt = conn2.createStatement();
			rs = stmt.executeQuery("SELECT part_Name FROM part_table");
		
			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				String partName = rs.getString("part_name").toUpperCase();
				currentPartList.add(partName);
				
			}

			// catches the data fetch exceptions
		} catch (SQLException e) {
			e.printStackTrace();

			// wraps up by closing all open queries
		} finally {
			// close rs
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
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
			if (conn2 != null) {
				try {
					conn2.close();
					conn2 = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		//return the inventory
			return currentPartList;
	}
	
	
	public ArrayList<String> getTemplateParts(String templateNum) {
		ArrayList<String> parts = new ArrayList<String>();
		
		setDsUrl();
		setDsUser();
		setDsPassword();

		// declare SQL variables to be used
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// try to fetch the data
		try {
		
			conn = ds.getConnection();
			System.out.println("connection made in TemplatePartsGateWay_GetTemplateParts");
			
			templateNum = templateNum.toUpperCase();
			String partTableName = templateNum + "_parts";
			System.out.println("getPartsForTemp: " + partTableName);
			
			String sql = "SELECT * FROM " + partTableName;
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			
			
			int resultSize = rs.getFetchSize();
			System.out.println("resultSize: " + resultSize);

			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				String id = rs.getString("id");
				String templateNumber = rs.getString("template_number");
				String part_num = rs.getString("part_number");
				int quantity = rs.getInt("Quantity");
				// catches the data fetch exceptions
				String partEntry = "Template# : " + templateNumber + " Part# : " + part_num + " Quantity : " + quantity;
				parts.add(partEntry);
			}
			
		} catch (SQLException e) {
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
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return parts;
		
	}
	
	public int lockRow(int partUuid) throws SQLException {
		System.out.println("LOCK in Template LIST");
	
	

		PreparedStatement stmt = null;
		try {
			if(conn == null)
			{
			conn = ds.getConnection();
			System.out.println("connection made in TemplatePartsGATEWAY_LOCKROW");
			}
		

			// create statment to push to database

			String sql = "SELECT * FROM template_table  WHERE id=? FOR UPDATE";
			
			conn.setAutoCommit(false);
			
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setQueryTimeout(3);

			stmt.setInt(1, partUuid);
			stmt.executeQuery();

		} catch (SQLException e) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame,"Locked");
			conn.rollback();
			conn.setAutoCommit(true);
			conn.close();
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	
	public void closeConn() {
		if (conn != null) {
			try {
				
				conn.rollback();
				conn.setAutoCommit(true);
				conn.close();
				
				conn = null;
				System.out.println("TemplateDetailGATEWAY_USER CLOSED OUT WITHOUT AN UPDATE");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

