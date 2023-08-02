package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {
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

	public String insertpaymentdetails(int paymentId, String date, int amount, int customerId) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payment (`payment_id`,`date`,`amount`,`customer_id`)" + " values (?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, paymentId);
			preparedStmt.setString(2, date);
			preparedStmt.setInt(3, amount);
			preparedStmt.setInt(4, customerId);
			preparedStmt.execute();
			con.close();

			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the payment details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readpaymentdetails() {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Payment Id</th><th>Date</th><th>Amount</th><th>Customer Id</th></tr>";

			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				int paymentId = rs.getInt("payment_id");
				String date = rs.getString("date");
				int amount = rs.getInt("amount");
				int customerId = rs.getInt("customer_id");

				// Add into the html table
				output += "<tr>" + "<td>" + paymentId + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + customerId + "</td>";

			}

			con.close();

			// Complete the html table
			output += "</tr></table>";
		} catch (Exception e) {
			output = "Error while reading the payment.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updatepaymentdetails(int paymentId, String date, int amount, int customerId) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE payment SET `payment_id`=?,date=?,amount=?,customer_id=?   WHERE payment_id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, paymentId);
			preparedStmt.setString(2, date);
			preparedStmt.setInt(3, amount);
			preparedStmt.setInt(4, customerId);
			preparedStmt.setInt(5, paymentId);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the payment details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deletepaymentdetails(int paymentId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from payment where payment_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, paymentId);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the payment detail.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}