package model;

import java.sql.*;

public class PowerUsage {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ceb_power_usage", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPowerUsage(int puId, int units, int amount, int month, int cusId, int empId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into power_usage (`power_usage_id`,`units`,`amount`,`month`,`customer_id`, `employee_id`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, puId);
			preparedStmt.setInt(2, units);
			preparedStmt.setInt(3, amount);
			preparedStmt.setInt(4, month);
			preparedStmt.setInt(5, cusId);
			preparedStmt.setInt(6, empId);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the power usage detail.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPowerUsage() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Power Usage Id</th><th>Units</th><th>Amount</th><th>Month</th><th>Customer Id</th><th>Employee Id</th></tr>";
			String query = "select * from power_usage";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				int puId = rs.getInt("power_usage_id");
				int units = rs.getInt("units");
				int amount = rs.getInt("amount");
				int month = rs.getInt("month");
				int cusId = rs.getInt("customer_id");
				int empId = rs.getInt("employee_id");

				// Add into the html table
				output += "<tr><td>" + puId + "</td>";
				output += "<td>" + units + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + month + "</td>";
				output += "<td>" + cusId + "</td>";
				output += "<td>" + empId + "</td>";
			}
			con.close();
			// Complete the html table
			output += "</tr></table>";
		} catch (Exception e) {
			output = "Error while reading the power usage details.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePowerUsage(int puId, int units, int amount, int month, int cusId, int empId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
// create a prepared statement
			String query = "UPDATE power_usage SET `power_usage_id`=? ,`units`=?, `amount`=?, `month`=?, `customer_id`=?, `employee_id`=? WHERE `power_usage_id` = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setInt(1, puId);
			preparedStmt.setInt(2, units);
			preparedStmt.setInt(3, amount);
			preparedStmt.setInt(4, month);
			preparedStmt.setInt(5, cusId);
			preparedStmt.setInt(6, empId);
			preparedStmt.setInt(7, puId);
// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating a power usage details.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteHospital(int puId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from power_usage where power_usage_id =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, puId);
			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the power usage details.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}