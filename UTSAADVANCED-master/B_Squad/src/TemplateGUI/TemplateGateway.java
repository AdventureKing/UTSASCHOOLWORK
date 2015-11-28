package TemplateGUI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TemplateDetailGUI.TemplateDetailModel;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TemplateGateway {
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
	
	public ArrayList<TemplateDetailModel> generateTemplates() {
		ArrayList<TemplateDetailModel> currentTemplateList = new ArrayList<TemplateDetailModel>();
		// create connection with the database
		
		setDsUrl();
		setDsUser();
		setDsPassword();

		// declare SQL variables to be used
		Statement stmt = null;
		ResultSet rs = null;
		
		// try to fetch the data
		try {
			conn = ds.getConnection();
			System.out.println("connection made in TemplateGateWay_generateTemplates");
			
			stmt = conn.createStatement();
			System.out.println("Connection Made in TEMPLATEGATEWAY");

			// create statement to get row size
			rs = stmt.executeQuery("SELECT COUNT(*) FROM template_table");
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
			rs = stmt.executeQuery("SELECT * FROM template_table");
			

			// instantiate table data for MVC
			

			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				int id = rs.getInt("id");
				String templateNum = rs.getString("product_number").toUpperCase();
				String templateDescript = rs.getString("product_description");
				TemplateDetailModel tempPart = new TemplateDetailModel(id, templateNum, templateDescript);
				currentTemplateList.add(tempPart);
				
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
			return currentTemplateList;
	}

	// add to the database
	public void addToTemplates(TemplateDetailModel tempModel) {
		setDsUrl();
		setDsUser();
		setDsPassword();

		// declare SQL variables to be used
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// try to fetch the data
		try {
			conn = ds.getConnection();
			System.out.println("connection made in TemplateGateWay_addToTemplates");
			
			// create statment to push to database
			String sql = "INSERT INTO template_table (product_number,product_description) VALUES (?,?)";
			stmt = (PreparedStatement) conn.prepareStatement(sql);

			// get data from template model
			String templateNum = tempModel.getTemplateNum();
			String templateDescript = tempModel.getTemplateDescript();

			// put data into query
			System.out.println("TEMPLATE NUM: " + templateNum);
			stmt.setString(1, templateNum);
			stmt.setString(2, templateDescript);
			
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

	
	public TemplateDetailModel getSelectedTemplate(TemplateDetailModel templateModel) {
		TemplateDetailModel tempModel = null;
		
		setDsUrl();
		setDsUser();
		setDsPassword();

		// declare SQL variables to be used
		Statement stmt = null;
		ResultSet rs = null;
		
		// try to fetch the data
		try {
			conn = ds.getConnection();
			System.out.println("connection made in TemplateGateWay_GetSelectedTemplate");

			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM template_table");

			// grabs the data from SQL and populates our local table
			while (rs.next()) {
				// fetching records 1 at a time
				int id = rs.getInt("id");
				String templateNum = rs.getString("product_number");
				String templateDescript = rs.getString("product_description");
				if(templateModel.getTemplateNum().equals(templateNum) && templateModel.getTemplateDescript().equals(templateDescript)){
					tempModel = new TemplateDetailModel(id, templateNum, templateDescript);
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
	
	public ArrayList<String> getPartsForTemplate(int uuid, String templateNum) {
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
			System.out.println("connection made in TemplateGateWay_GetPartsForTemplate");
			
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
				String part_number = rs.getString("part_number");
				int quantity = rs.getInt("Quantity");
				// catches the data fetch exceptions
				String partEntry = "Template Number : " + templateNumber + " | Part Number : " + part_number + " | Quant : " + quantity;
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
}

