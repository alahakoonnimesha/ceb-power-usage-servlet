package model;

//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.sql.*;

public class Customer {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ceb_power_usage", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertCustomer(int customerId, String customerName, String address, int telepohneNo) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error nothing in the database.";
			}
			// create a prepared statement
			String query = " insert into customer(`customer_id`,`customer_name`,`address`,`telepohne_no`)"
					+ " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, customerId);
			preparedStmt.setString(2, customerName);
			preparedStmt.setString(3, address);
			preparedStmt.setInt(4, telepohneNo);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readCustomer() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Customer Id</th><th>Customer Name</th><th>Address</th><th>Telepohne No</th></tr>";
			String query = "select * from customer";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				int customerId = rs.getInt("customer_id");
				String customerName = rs.getString("customer_name");
				String address = rs.getString("address");
				int telepohneNo = rs.getInt("telepohne_no");

				// Add into the html table
				output += "<tr><td>" + customerId + "</td>";
				output += "<td>" + customerName + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + telepohneNo + "</td>";
			}
			con.close();
			// Complete the html table
			output += "</tr></table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateCustomer(int customerId, String customerName, String address, int telepohneNo) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE customer SET customer_id=?, customer_name=?, address=?, telepohne_no=? WHERE customer_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, customerId);
			preparedStmt.setString(2, customerName);
			preparedStmt.setString(3, address);
			preparedStmt.setInt(4, telepohneNo);
			preparedStmt.setInt(5, customerId);
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating a customer details.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteCustomer(int customerId) {
		String output = " ";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from customer where customer_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, customerId);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the appointment detail.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
