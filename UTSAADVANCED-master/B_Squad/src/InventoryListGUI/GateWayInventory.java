package InventoryListGUI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import InventoryDetailGUI.InventoryDetailModel;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class GateWayInventory {
	MysqlDataSource ds;
	
	void setDsUrl(){
		ds.setURL("jdbc:mysql://devcloud.fulgentcorp.com:3306/tfv024");
	}
	void setDsUser(){
		ds.setUser("tfv024");
	}
	void setDsPassword(){
		ds.setPassword("Z3D6erXZkxHvhO6ra4UD");
	}
	public ArrayList<InventoryDetailModel> generateInventory() {
		ArrayList<InventoryDetailModel> currentInventoryList = new ArrayList<InventoryDetailModel>();
		// create connection with the database
		 ds = new MysqlDataSource();
		setDsUrl();
		setDsUser();
		setDsPassword();

		// declare SQL variables to be used
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
	
	

		
		// try to fetch the data
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			System.out.println("Connection Made in GATEWAYINVENTORY");

			// create statement to get row size
			rs = stmt.executeQuery("SELECT COUNT(*) FROM inventory_table");
			rs.next();
			int rowcount = rs.getInt(1);
			System.out.println("total entries:" + rowcount);

			// close first query
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// get column size and query the whole SQL table
			rs = stmt.executeQuery("SELECT * FROM inventory_table");
			

			// instantiate table data for MVC
			

			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				int id = rs.getInt("id");
				String itemId = rs.getString("item_id").toUpperCase();
				String partName = rs.getString("part_name").toLowerCase();
				String partLocation = rs.getString("location").toLowerCase();
				int partQuantity = rs.getInt("quantity");
				InventoryDetailModel tempPart = new InventoryDetailModel(id,itemId,partName,partQuantity,partLocation);
				currentInventoryList.add(tempPart);
				
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
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		//return the inventory
			return currentInventoryList;
	}

	// add to the database
	public void addToInventory(InventoryDetailModel itemModel) {
		 ds = new MysqlDataSource();
	
			setDsUrl();
			setDsUser();
			setDsPassword();

		// declare SQL variables to be used

		Connection conn = null;
		ResultSet rs = null;

		PreparedStatement stmt = null;
		try {
			conn = ds.getConnection();
			System.out.println("connection made");

			// create statment to push to database

			String sql = "INSERT INTO inventory_table"
					+ "(item_id,part_name,location,quantity) VALUES (?,?,?,?)";
			stmt = (PreparedStatement) conn.prepareStatement(sql);

			// get data from partmodel
			String partName = itemModel.getItemName();
			String itemId = itemModel.getItemId();
			String partLocation = itemModel.getLocation();
			int quantity = itemModel.getQuantity();

			// put data into query
			stmt.setString(1, itemId);
			stmt.setString(2, partName);
			stmt.setString(3, partLocation);
			stmt.setInt(4, quantity);
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
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	
	public InventoryDetailModel getSelectedPart(InventoryDetailModel itemModel) {
		InventoryDetailModel tempModel = null;
		
		 ds = new MysqlDataSource();
			setDsUrl();
			setDsUser();
			setDsPassword();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM inventory_table");

			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				int id = rs.getInt("id");
				String itemId = rs.getString("item_id").toLowerCase();
				int quantity = rs.getInt("quantity");
				String itemName = rs.getString("part_name").toLowerCase();
				String itemLocation = rs.getString("location").toLowerCase();
				if(itemModel.getItemName().toLowerCase().equals(itemName) && itemModel.getLocation().toLowerCase().equals(itemLocation)){
					tempModel = new InventoryDetailModel(id, itemId, itemName,quantity,itemLocation);
					return tempModel;
				}

				// catches the data fetch exceptions
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
		return null;
	}
}
