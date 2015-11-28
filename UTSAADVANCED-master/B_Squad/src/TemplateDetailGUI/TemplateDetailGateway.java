package TemplateDetailGUI;

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

public class TemplateDetailGateway {
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
	
	public TemplateDetailGateway(){
		setDsUrl();
		setDsUser();
		setDsPassword();
		conn = null;
	}
	
	public void updateRowInTemplateList(TemplateDetailModel templateModel) {
		
		setDsUrl();
		setDsUser();
		setDsPassword();
		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		 
		try {
			if(conn == null){
				conn = ds.getConnection();
				System.out.println("connection made in TemplateDetailGateWay_UPDATEROWInTemplateList");
			}
			// create statement to push to database

			String sql = "UPDATE template_table SET product_number=?,product_description=? WHERE id=?";
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setQueryTimeout(2);
			
			// get data from part model
			int id = templateModel.getTemplateUuid();
			System.out.println(id);
			String templateNum = templateModel.getTemplateNum();
			String templateDesc = templateModel.getTemplateDescript();
			
			// put data into query
			stmt.setString(1, templateNum);
			stmt.setString(2, templateDesc);
			stmt.setInt(3, id);
			
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

	public void removeRowInTemplateList(TemplateDetailModel templateModel) {
		setDsUrl();
		setDsUser();
		setDsPassword();

		Connection conn2 = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		try {
			conn2 = ds.getConnection();
			System.out.println("connection made in TemplateDetailGATEWAY_REMOVERowInTemplateList");

			// create statement to push to database

			String sql = "DELETE FROM template_table  WHERE id=?";
			stmt = (PreparedStatement) conn2.prepareStatement(sql);
			stmt.setQueryTimeout(1);
			// getData from part model
			int uuid = templateModel.getTemplateUuid();
			// put data into query

			stmt.setInt(1, uuid);
			stmt.executeUpdate();

		} catch (SQLException e) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame,"Locked Record");
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
	
	public void removeTemplatePartTable(String templateNum) {
		setDsUrl();
		setDsUser();
		setDsPassword();

		Connection conn2 = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			conn2 = ds.getConnection();
			System.out.println("connection made in TemplateDetailGATEWAY_REMOVETemplatePartTable");

			// create statement to push to database
			stmt = conn2.createStatement();
			String tableName = templateNum.toUpperCase() + "_parts";
			String sql = "DROP TABLE " + tableName;
			stmt.executeUpdate(sql);
			
			System.out.println("Deleted table: " + tableName);

		} catch (SQLException e) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame,"Locked Record");
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
			System.out.println("Connection Made in TEMPLATEDETAILGATEWAY_GETTEMPLATENUMS");
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
		ArrayList<String> currentPartList = new ArrayList<String>();
		// create connection with the database
		

		// declare SQL variables to be used
		Statement stmt = null;
		
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		Connection conn2 = null;

		
		// try to fetch the data
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			System.out.println("Connection Made in TemplateDETAILGATEWAY_GETPARTLISTNAMES");

			// create statement to get row size
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

		//return the currentPartList
			return currentPartList;
	}
	public int lockRow(int partUuid) throws SQLException {
		System.out.println("LOCK in Template LIST");

		PreparedStatement stmt = null;
		try {
			if(conn == null)
			{
			conn = ds.getConnection();
			System.out.println("connection made in TemplateDetailGATEWAY_LOCKROW");
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
	
public void createNewTemplatePartsTable(String templateNum) {
		
		setDsUrl();
		setDsUser();
		setDsPassword();
		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		 
		try {
			if(conn == null){
				conn = ds.getConnection();
				System.out.println("connection made in TemplateDetailGateWay_createNewTemplatePartsTable");
			}
			// create statement to push to database

			templateNum = templateNum.toUpperCase();
			String partTableName = templateNum + "_parts";
			
			String sql = "CREATE TABLE " + partTableName +
	                     "(id VARCHAR(22), " +
	                     " template_number VARCHAR(20), " + 
	                     " part_number VARCHAR(20), " + 
	                     " Quantity INTEGER, " + 
	                     " PRIMARY KEY ( id ))";
			  
			System.out.println("SQL: " + sql);
			
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setQueryTimeout(3);
			stmt.executeUpdate();
			System.out.println("Created table in given database...");
			   
			
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
}

