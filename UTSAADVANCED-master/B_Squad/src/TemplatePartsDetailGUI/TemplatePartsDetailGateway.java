package TemplatePartsDetailGUI;


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

public class TemplatePartsDetailGateway {
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
	
	public TemplatePartsDetailGateway(){
		setDsUrl();
		setDsUser();
		setDsPassword();
		conn = null;
	}
	
	
	public void addPartToTable(String templateNum, String partNum, int Quan){
		
		// create connection with the database
		setDsUrl();
		setDsUser();
		setDsPassword();

		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn2 = null;
		try {
		
			if(conn2 == null) {
				conn2 = ds.getConnection();
				System.out.println("connection made in TemplatePartsDETAILGATEWAY_addPartToTable");
			}
			// create statment to push to database
			
			templateNum = templateNum.toUpperCase();
			String partTableName = templateNum + "_parts";
			
			String sql = "INSERT INTO " + partTableName + "(id, template_number, part_number, Quantity) VALUES (?,?,?,?)";
			stmt = (PreparedStatement) conn2.prepareStatement(sql);

			// put data into query
			partNum = partNum.toUpperCase();
			String partKey = templateNum + partNum;
			
			stmt.setString(1, partKey);
			stmt.setString(2, templateNum);
			stmt.setString(3, partNum);
			stmt.setInt(4, Quan);
			stmt.execute();
		    
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
					conn2 = null;
					System.out.println("PARTSLISTCLOSECONNECTION");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
public void updatePartInTable(String templateNum, String partNum, int quant) {
		
		// declare SQL variables to be used

		ResultSet rs = null;

		PreparedStatement stmt = null;
		try {
			if(conn == null) {
				conn = ds.getConnection();
				System.out.println("connection made in TemplatePartsDetailGateway_UPDATEPartInTable");
			}
			// create statment to push to database
			
			templateNum = templateNum.toUpperCase();
			String templateTableName = templateNum + "_parts";

			String sql = "UPDATE " + templateTableName + " SET template_number=?,part_number=?,Quantity=? WHERE id=?";
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setQueryTimeout(1);
			
			partNum = partNum.toUpperCase();
			String partKey = templateNum + partNum;
			
			// get data from partmodel
			
			// put data into query
			stmt.setString(1, templateNum);
			stmt.setString(2, partNum);
			stmt.setInt(3, quant);
			stmt.setString(4, partKey);
			stmt.executeUpdate();

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
					conn.setAutoCommit(true);
					conn.close();
					
					conn = null;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

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
			System.out.println("connection made in TemplatePartsDetailGateWay_GetTemplateParts");
			
			templateNum = templateNum.toUpperCase();
			String partTableName = templateNum + "_parts";
			System.out.println("getPartsForTemp: " + partTableName);
			
			String sql = "SELECT * FROM " + partTableName;
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			stmt.setQueryTimeout(3);
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
				String partEntry = "" + templateNumber + " " + part_num + " " + quantity;
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
	
	public ArrayList<String> getAllPartNumbers() {
		ArrayList<String> currentPartNumberList = new ArrayList<String>();
		// create connection with the database
		

		setDsUrl();
		setDsUser();
		setDsPassword();

		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			if(conn == null) {
				conn = ds.getConnection();
				System.out.println("connection made in TemplatePartsDetailGATEWAY_getAllPartNumbers");
			}

			// create statement to get row size
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT part_number FROM part_table");
			stmt.setQueryTimeout(3);
			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				String partNumber = rs.getString("part_number").toUpperCase();
				currentPartNumberList.add(partNumber);	
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
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		//return the inventory
			return currentPartNumberList;
	}
	
	public ArrayList<String> getPartNumsInTemplateTable(String templateNum) {
		ArrayList<String> currentPartNumberList = new ArrayList<String>();
		// create connection with the database
		

		setDsUrl();
		setDsUser();
		setDsPassword();

		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			if(conn == null) {
				conn = ds.getConnection();
				System.out.println("connection made in TemplatePartsDetailGateway_getPartNumsInTemplateTable");
			}

			// create statement to get row size
			stmt = conn.createStatement();
			stmt.setQueryTimeout(3);
			String tableName = templateNum + "_parts";
			rs = stmt.executeQuery("SELECT part_number FROM " + tableName);
		
			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				String partNumber = rs.getString("part_number").toUpperCase();
				currentPartNumberList.add(partNumber);	
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
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		//return the inventory
			return currentPartNumberList;
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
	
	public int lockRow(String tableName,String id) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("LOCK");
		System.out.println(tableName);
		System.out.println(id);
		

		PreparedStatement stmt = null;
		try {
			if(conn == null)
			{
			conn = ds.getConnection();
			System.out.println("connection made in TEMPLATEPARTSDETAILGATEWAY_LOCKROW");
			}

			

			String sql = "SELECT * FROM " + tableName + " WHERE id=? FOR UPDATE";

			conn.setAutoCommit(false);
			
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setQueryTimeout(3);

			stmt.setString(1,id);
			
			stmt.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Component frame = null;
			JOptionPane
					.showMessageDialog(frame,
							"Record Locked");
			conn.rollback();
			conn.setAutoCommit(true);
			conn.close();
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
}

