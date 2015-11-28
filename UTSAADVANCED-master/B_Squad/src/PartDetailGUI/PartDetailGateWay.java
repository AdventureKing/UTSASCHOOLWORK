package PartDetailGUI;

import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class PartDetailGateWay {
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
	public PartDetailGateWay(){
		setDsUrl();
		setDsUser();
		setDsPassword();
		conn = null;
	}
	
	public void updateRowInPartList(PartDetailModel partModel) {
		
		setDsUrl();
		setDsUser();
		setDsPassword();
		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ds.getConnection();
			System.out.println("connection made in PartDetailGateWay_UPDATEROWInPartList");

			// create statement to push to database

			String sql = "UPDATE part_table SET part_number=?,part_name=?,vendor=?,unit_of_quantity=?,external_part_number=? WHERE id=?";
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setQueryTimeout(2);
			
			// get data from part model
			int id = partModel.getUuid();
			System.out.println(id);
			String partNum = partModel.getPartNum();
			String partName = partModel.getPartName();
			String vendor = partModel.getVendor();
			String quanUnit = partModel.getQuanUnit();
			String extPartNum = partModel.getExtPartNum();
			
			
			// put data into query
			stmt.setString(1, partNum);
			stmt.setString(2, partName);
			stmt.setString(3, vendor);
			stmt.setString(4, quanUnit);
			stmt.setString(5, extPartNum);
			stmt.setInt(6, id);
			
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

	public void removeRowInPartList(PartDetailModel partModel) {
		setDsUrl();
		setDsUser();
		setDsPassword();

		Connection conn2 = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		try {
			conn2 = ds.getConnection();
			System.out.println("connection made in DETAILPARTGATEWAY_REMOVERowInPartList");

			// create statement to push to database
			String sql = "DELETE FROM part_table  WHERE id=?";
			stmt = (PreparedStatement) conn2.prepareStatement(sql);
			stmt.setQueryTimeout(1);
			// getData from part model
			int uuid = partModel.getPartUuid();
			// put data into query

			stmt.setInt(1, uuid);
			stmt.executeUpdate();

		} catch (SQLException e) {
			Component frame = null;
			JOptionPane
					.showMessageDialog(frame,
							"Locked Record");
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
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<String> getPartListNames() {
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
			System.out.println("Connection Made in DETAILPARTGATEWAY_GETPARTListNAMES");
			stmt.setQueryTimeout(3);
			// create statement to get row size
			rs = stmt.executeQuery("SELECT part_name FROM part_table");
		
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
	
	public ArrayList<String> getTemplateNumbers() {
		ArrayList<String> currentTemplateList = new ArrayList<String>();
		// create connection with the database
		
		setDsUrl();
		setDsUser();
		setDsPassword();


		
		Statement stmt = null;
		Connection conn2 = null;
		ResultSet rs = null;
		
		// try to fetch the data
		try {
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			System.out.println("Connection Made in PartDetailGateway_GetTemplateNumbers");
			stmt.setQueryTimeout(3);
			// create statement to get row size
			rs = stmt.executeQuery("SELECT product_number FROM template_table");
		
			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				String templateNum = rs.getString("product_number").toUpperCase();
				currentTemplateList.add(templateNum);
				
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
			return currentTemplateList;
	}
	
	public ArrayList<String> getPartsInTemplates() {
		ArrayList<String> currentTemplatesList = getTemplateNumbers();
		ArrayList<String> currentPartsList = new ArrayList<String>();
		// create connection with the database
		
		setDsUrl();
		setDsUser();
		setDsPassword();


		
		Statement stmt = null;
		Connection conn2 = null;
		ResultSet rs = null;
		
		// try to fetch the data
		try {
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			System.out.println("Connection Made in PartDetailGateway_GetPartsInTemplates");
			stmt.setQueryTimeout(3);
			// create statement to get row size
			
			for(String templateNum : currentTemplatesList){
				
				String tableName = templateNum + "_parts";
				rs = stmt.executeQuery("SELECT part_number FROM " + tableName);
			
				// grabs the data from SQL and populates our local table
				while (rs.next()) {
					// fetching records 1 at a time
					String partNum = rs.getString("part_number").toUpperCase();
					currentPartsList.add(partNum);
					
				}
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
			return currentPartsList;
	}
	
	
	public int lockRow(int partUuid) throws SQLException {
		System.out.println("LOCK in PARTSLIST");
	
	

		PreparedStatement stmt = null;
		try {
			if(conn == null)
			{
			conn = ds.getConnection();
			System.out.println("connection made in PARTDETAILGATEWAY_LOCKROW");
			}
		

			// create statment to push to database

			String sql = "SELECT * FROM part_table  WHERE id=? FOR UPDATE";
			
			conn.setAutoCommit(false);
			
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setQueryTimeout(3);

			stmt.setInt(1, partUuid);
			stmt.executeQuery();

		} catch (SQLException e) {
			Component frame = null;
			JOptionPane
					.showMessageDialog(frame,
							"Locked");
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
				System.out.println("PARTDETAILGATEWAY_USER CLOSED OUT WITHOUT A UPDATE");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}