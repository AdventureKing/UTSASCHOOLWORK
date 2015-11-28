package PartListGUI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import PartDetailGUI.PartDetailModel;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class PartListGateway {
	
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
	
	public ArrayList<PartDetailModel> generatePartInventory() {
		
		ArrayList<PartDetailModel> currentPartList = new ArrayList<PartDetailModel>();
		
		// create connection with the database
		
		
		 ds = new MysqlDataSource();
			setDsUrl();
			setDsUser();
			setDsPassword();

		// declare SQL variables to be used
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;

		// try to fetch the data
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			System.out.println("Connection Made in GATEWAYPARTLIST_GeneratePartInventory");

			// create statement to get row size
			rs = stmt.executeQuery("SELECT COUNT(*) FROM part_table");
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
			rs = stmt.executeQuery("SELECT * FROM part_table");			

			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				int id = rs.getInt("id");
				String partNum = rs.getString("part_number");
				String partName = rs.getString("part_name").toLowerCase();
				String vendor = rs.getString("vendor").toLowerCase();
				String quanUnit = rs.getString("unit_of_quantity").toLowerCase();
				String extPartNum = rs.getString("external_part_number");
				PartDetailModel tempPart = new PartDetailModel(id, partNum, partName, vendor, quanUnit, extPartNum);
				currentPartList.add(tempPart);
				
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
			return currentPartList;
	}

	// add to the database
	public void addToPartList(PartDetailModel partModel) {
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
			System.out.println("connection made in PARTDETAILMODEL_ADDTOPARTLIST");

			// create statment to push to database

			String sql = "INSERT INTO part_table" + "(part_number,part_name,vendor,unit_of_quantity,external_part_number) VALUES (?,?,?,?,?)";
			stmt = (PreparedStatement) conn.prepareStatement(sql);

			// get data from partmodel
			String partNum = partModel.getPartNum();
			String partName = partModel.getPartName();
			String partVendor = partModel.getVendor();
			String unitQuan = partModel.getQuanUnit();
			String extPartNum = partModel.getExtPartNum();

			// put data into query
			stmt.setString(1, partNum);
			stmt.setString(2, partName);
			stmt.setString(3, partVendor);
			stmt.setString(4, unitQuan);
			stmt.setString(5, extPartNum);
			stmt.execute();

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

	
	public PartDetailModel getSelectedPart(PartDetailModel partModel) {
		// TODO Auto-generated method stub
		PartDetailModel tempModel = null;
		ds = new MysqlDataSource();
		setDsUrl();
		setDsUser();
		setDsPassword();

		// declare SQL variables to be used
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			System.out.println("connection made in PARTGATEWAY_GETSELECTEDPart");
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM part_table");

			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				int id = rs.getInt("id");
				String partNum = rs.getString("part_number");
				String partName = rs.getString("part_name").toLowerCase();
				String vendor = rs.getString("vendor").toLowerCase();
				String quanUnit = rs.getString("unit_of_quanitity").toLowerCase();
				String extPartNum = rs.getString("external_part_number");
				if(partModel.getPartName().toLowerCase().equals(partName) && partModel.getVendor().toLowerCase().equals(vendor)){
					tempModel = new PartDetailModel(id, partNum, partName, vendor, quanUnit, extPartNum);
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
