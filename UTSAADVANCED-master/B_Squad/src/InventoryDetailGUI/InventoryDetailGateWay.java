package InventoryDetailGUI;

import java.awt.Component;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class InventoryDetailGateWay {
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
	public InventoryDetailGateWay(){
		setDsUrl();
		setDsUser();
		setDsPassword();
		conn = null;
	}
	public void updateRowInInventory(InventoryDetailModel itemModel) {

		// declare SQL variables to be used

		ResultSet rs = null;

		PreparedStatement stmt = null;
		try {
			if(conn == null)
			{
			conn = ds.getConnection();
			System.out.println("connection made in INVENTORYDETAILGATEWAY_UPDATEROWININVENTORY");
			}
			// create statment to push to database

			String sql = "UPDATE inventory_table SET item_id=?, part_name=?,location=?,quantity=? WHERE id=?";
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			
			//set time out so no lag
			stmt.setQueryTimeout(1);
			
			// get data from partmodel
			String partName = itemModel.getItemName();
			String itemId = itemModel.getItemId();
			String partLocation = itemModel.getLocation();
			int quantity = itemModel.getQuantity();
			int uuid = itemModel.getItemUuid();
			
			// put data into query
			stmt.setString(1, itemId);
			stmt.setString(2, partName);
			stmt.setString(3, partLocation);
			stmt.setInt(4, quantity);
			stmt.setInt(5, uuid);
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
	
public void updateSinglePartAtLocation(String location, String locationPartName, int newPartQuan) {
		// declare SQL variables to be used

		ResultSet rs = null;

		PreparedStatement stmt = null;
		
		try {
			if(conn == null)
			{
			conn = ds.getConnection();
			System.out.println("connection made in INVENTORYDETAILGATEWAY_UPDATESinglePartAtLocation");
			}
			// create statment to push to database

			String sql = "UPDATE inventory_table SET quantity=? WHERE part_name=?";
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			
			//set time out so no lag
			stmt.setQueryTimeout(1);
	
			// put data into query
			stmt.setInt(1, newPartQuan);
			stmt.setString(2, locationPartName);
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

	public void removeRowInInventory(InventoryDetailModel itemModel) {

		ResultSet rs = null;

		PreparedStatement stmt = null;
		Connection conn2 = null;
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			System.out.println("connection made in INVENTORYDETAILGATEWAY_REMOVEROWININVENTORY");

			// create statment to push to database

			String sql = "DELETE FROM inventory_table  WHERE id=?";
			stmt = (PreparedStatement) conn2.prepareStatement(sql);
			stmt.setQueryTimeout(2);
			// getData from part model
			int uuid = itemModel.getItemUuid();
			// put data into query

			stmt.setInt(1, uuid);
			stmt.executeUpdate();

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

	public ArrayList<String> getPartListNames() {
		ArrayList<String> currentPartList = new ArrayList<String>();
		// create connection with the database
		

		// declare SQL variables to be used
		Statement stmt = null;
		
		ResultSet rs = null;
		Connection conn2 = null;

		
		// try to fetch the data
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			System.out.println("Connection Made in INVENTORYDETAILGATEWAY_GETPARTLISTNAMES");

			// create statement to get row size
			rs = stmt.executeQuery("SELECT part_Name FROM part_table");
		
			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				String partName = "part : " + rs.getString("part_name").toLowerCase();
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
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//return the inventory
			return currentPartList;
	}
	
	public int getOriginalProductQuan(String itemId) {
		// create connection with the database
		
		int oldQuan = 0;

		// declare SQL variables to be used
		Statement stmt = null;
		
		ResultSet rs = null;
		Connection conn2 = null;

		
		// try to fetch the data
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			System.out.println("Connection Made in INVENTORYDETAILGATEWAY_GETOriginalProductQuan");

			// create statement to get row size
			rs = stmt.executeQuery("SELECT item_id, quantity FROM inventory_table");
		
			// grabs the data from SQL and populates our local table
			
			while (rs.next()) {
				if(rs.getString("item_id").toUpperCase().equals(itemId.toUpperCase())){
					oldQuan = rs.getInt("quantity");
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
					conn2 = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//return the inventory
			return oldQuan;
	}
	
	
	public String getProductDescript(String itemId) {
		// create connection with the database
		
		String descript = "";

		// declare SQL variables to be used
		Statement stmt = null;
		
		ResultSet rs = null;
		Connection conn2 = null;

		
		// try to fetch the data
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			System.out.println("Connection Made in INVENTORYDETAILGATEWAY_GETProductDescript");

			// create statement to get row size
			rs = stmt.executeQuery("SELECT item_id, part_name FROM inventory_table");
		
			// grabs the data from SQL and populates our local table
			
			while (rs.next()) {
				if(rs.getString("item_id").toUpperCase().equals(itemId.toUpperCase())){
					descript = rs.getString("part_name");
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
					conn2 = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//return the inventory
		return descript;
	}
	
	public String getProductTemplateNum(String prodDescript) {
		// create connection with the database
		
		String tempNum = "";

		// declare SQL variables to be used
		Statement stmt = null;
		
		ResultSet rs = null;
		Connection conn2 = null;

		
		// try to fetch the data
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			System.out.println("Connection Made in INVENTORYDETAILGATEWAY_GETProductTemplateNum");

			// create statement to get row size
			rs = stmt.executeQuery("SELECT product_number, product_description FROM template_table");
		
			// grabs the data from SQL and populates our local table
			
			while (rs.next()) {
				if(rs.getString("product_description").toUpperCase().equals(prodDescript.toUpperCase())){
					tempNum = rs.getString("product_number");
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
					conn2 = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//return the inventory
		return tempNum;
	}
	
	public int getNextProductIdForDatabase() {
		// create connection with the database
		
		int newIdNum = 0;

		// declare SQL variables to be used
		Statement stmt = null;
		
		ResultSet rs = null;
		Connection conn2 = null;

		
		// try to fetch the data
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			System.out.println("Connection Made in INVENTORYDETAILGATEWAY_GETNextProductIdForDatabase");

			// create statement to get row size
			rs = stmt.executeQuery("SELECT item_id FROM inventory_table");
		
			// grabs the data from SQL and populates our local table
			
			while (rs.next()) {
				// fetching records 1 at a time
				String idString = rs.getString("item_id").toUpperCase();
				if(idString.startsWith("A")){
				   idString = idString.substring(1);
				   int id = Integer.parseInt(idString);
				   if(id > newIdNum){
					   newIdNum = id;
				   }		   
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
					conn2 = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//return the inventory
			return newIdNum+1;
	}
	
	public int getNextPartIdForDatabase() {
		// create connection with the database
		
		int newIdNum = 0;

		// declare SQL variables to be used
		Statement stmt = null;
		
		ResultSet rs = null;
		Connection conn2 = null;

		
		// try to fetch the data
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			System.out.println("Connection Made in INVENTORYDETAILGATEWAY_GETNextPartIdForDatabase");

			// create statement to get row size
			rs = stmt.executeQuery("SELECT item_id FROM inventory_table");
		
			// grabs the data from SQL and populates our local table
			
			while (rs.next()) {
				// fetching records 1 at a time
				String idString = rs.getString("item_id").toUpperCase();
				if(idString.startsWith("P")){
				   idString = idString.substring(1);
				   int id = Integer.parseInt(idString);
				   if(id > newIdNum){
					   newIdNum = id;
				   }		   
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
					conn2 = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//return the inventory
			return newIdNum+1;
	}
	
	
	public ArrayList<String> getProductListNames() {
		ArrayList<String> currentProductList = new ArrayList<String>();
		// create connection with the database
		

		// declare SQL variables to be used
		Statement stmt = null;
		Statement stmt2 = null;
		
		ResultSet rs = null;
		ResultSet rs2 = null;
		Connection conn2 = null;

		
		// try to fetch the data
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			stmt2 = conn2.createStatement();
			System.out.println("Connection Made in INVENTORYDETAILGATEWAY_GETPARTLISTNAMES");

			// create statement to get row size
			rs = stmt.executeQuery("SELECT product_description FROM template_table");
		    rs2 = stmt2.executeQuery("SELECT product_number FROM template_table");
			// grabs the data from SQL and populates our local table
			while (rs2.next()) {
				// fetching records 1 at a time
				String productName = "product : " + rs2.getString("product_number") + " : ";
				currentProductList.add(productName);
			}
			
			int i = 0;
			while (rs.next()) {
				String temp = rs.getString("product_description");
				if(temp.length() > 12){
					temp = temp.substring(0,11);
				}
				currentProductList.set(i, currentProductList.get(i) + temp);
				i++;
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
			
			if (rs2 != null) {
				try {
					rs2.close();
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

		//return the inventory
			return currentProductList;
	}
	
	public ArrayList<String> getProductReqs(String templateId) {

		ArrayList<String> currentPartReqsList = new ArrayList<String>();
		
		// declare SQL variables to be used
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		Connection conn2 = null;

		
		// try to fetch the data
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			stmt2 = conn2.createStatement();
			stmt3 = conn2.createStatement();
			
			System.out.println("Connection Made in INVENTORYDETAILGATEWAY_GETPARTLISTNAMES");

			// create statement to get row size
			rs = stmt.executeQuery("SELECT part_number FROM " + templateId + "_parts");
		    rs2 = stmt2.executeQuery("SELECT Quantity FROM " + templateId + "_parts");
		    String sql = "SELECT part_number, part_name FROM part_table";
		    rs3 = stmt3.executeQuery(sql);
		    
			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				String partNumber = rs.getString("part_number");
				/*String sql = "SELECT part_name FROM part_table WHERE part_number=?";
				stmt3 = (PreparedStatement) conn2.prepareStatement(sql);
				stmt3.setString(1, partNumber);
				rs3 = stmt3.executeQuery(sql);*/
				rs3 = stmt3.executeQuery(sql);
				while(rs3.next()){
				   if(rs3.getString("part_number").equals(partNumber)){	
				      String partName = rs3.getString("part_name") + " : ";
				      currentPartReqsList.add(partName);
				   }
				}
			}
			
			int i = 0;
			while (rs2.next()) {
				String temp = rs2.getString("Quantity");
				currentPartReqsList.set(i, currentPartReqsList.get(i) + temp);
				i++;
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
			
			if (rs2 != null) {
				try {
					rs2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (rs3 != null) {
				try {
					rs3.close();
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

		//return the inventory
			return currentPartReqsList;
	}
	
	public ArrayList<String> getPartsAtLocation(String location) {

		ArrayList<String> currentLocationParts = new ArrayList<String>();
		
		// declare SQL variables to be used
		Statement stmt = null;
		Statement stmt2 = null;
		
		ResultSet rs = null;
		ResultSet rs2 = null;
		Connection conn2 = null;

		
		// try to fetch the data
		try {
			if(conn2 == null)
			conn2 = ds.getConnection();
			stmt = conn2.createStatement();
			stmt2 = conn2.createStatement();
			System.out.println("Connection Made in INVENTORYDETAILGATEWAY_GETPARTLISTNAMES");

			rs = stmt.executeQuery("SELECT part_name, location, quantity FROM inventory_table");
		    
		    int i = 0;
			while (rs.next()) {
				String temp;
				if(rs.getString("location").equals(location)){
				   temp = rs.getString("part_name") + " : " + rs.getString("quantity");
				   System.out.println("TEMP: " + temp);
				   currentLocationParts.add(temp);
				}
				i++;
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
			
			if (rs2 != null) {
				try {
					rs2.close();
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

		//return the inventory
			return currentLocationParts;
	}
	
	public int lockRow(int partUuid) throws SQLException {
		System.out.println("LOCK");
	
	

		PreparedStatement stmt = null;
		try {
			if(conn == null){
				conn = ds.getConnection();
				System.out.println("connection made in INVENTORYDETAILGATEWAY_LOCKROW");
			}
		

			// create statment to push to database

			String sql = "SELECT * FROM inventory_table  WHERE id=? FOR UPDATE";
			
			conn.setAutoCommit(false);
			
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setQueryTimeout(3);

			stmt.setInt(1, partUuid);
			stmt.executeQuery();

		} catch (SQLException e) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame,"Record Locked");
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
				System.out.println("INVENOTRYDETAILGATEWAY_USER CLOSED OUT WITHOUT A UPDATE");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
		}
	}

}