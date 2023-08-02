package model;

import java.sql.*;

public class Employee {

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

	public String insertEmployee(int empId, String empName, String address, int telepohneNo) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into employee(`employee_id`,`employee_name`,`address`,`telepohne_no`)"
					+ " values(?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, empId);
			preparedStmt.setString(2, empName);
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

	public String readEmployee() {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Employee Id</th><th>Employee Name</th><th>address</th><th>Telepohne No</th></tr>";

			String query = "select * from employee";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				int empId = rs.getInt("employee_id");
				String empName = rs.getString("employee_name");
				String address = rs.getString("address");
				int telepohneNo = rs.getInt("telepohne_no");

				// Add into the html table
				output += "<tr><td>" + empId + "</td>";
				output += "<td>" + empName + "</td>";
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

	public String updateEmployee(int empId, String empName, String address, int telepohneNo) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE employee SET employee_id=?, employee_name=?, address=?, telepohne_no=? WHERE employee_id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, empId);
			preparedStmt.setString(2, empName);
			preparedStmt.setString(3, address);
			preparedStmt.setInt(4, telepohneNo);
			preparedStmt.setInt(5, empId);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";

		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteEmployee(int empId) {
		String output = "";
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from employee where employee_id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, empId);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
