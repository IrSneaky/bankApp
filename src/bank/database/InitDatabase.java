package bank.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InitDatabase
{
	private String user;
	private String pass;

	public InitDatabase(String user, String pass)
	{
		this.user = user;
		this.pass = pass;
	}

	//checks if db is made
	public void createDB()
	{
		try
		{
			//db driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//sets timezone
			String connString = "jdbc:mysql://localhost?user=" + user +
				"&password=" + pass + "&useLegacyDateTimeCode=false&" + 
				"serverTimezone=America/New_York";
			Connection conn = DriverManager.getConnection(connString);
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE DATABASE IF NOT EXISTS bank");
			System.out.println("Database bank created");
			stmt.close();
			conn.close();
		}
		catch(SQLException | ClassNotFoundException e)
		{
			System.err.println("Error opening database");
			e.printStackTrace();
		}
	}
	//checks for table
	public void createCustomers()
	{
		try
		{
			//sets db driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//sets proper timezone
			String connString = "jdbc:mysql://localhost/bank?user=" + user +
				"&password=" + pass + "&useLegacyDateTimeCode=false&" + 
				"serverTimezone=America/New_York";
			Connection conn = DriverManager.getConnection(connString);
			Statement stmt = conn.createStatement();
			//creates the customer table
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS customers (accNum INT AUTO_INCREMENT, "
					+ "fullName VARCHAR(40), "
					+ "balance DOUBLE(50, 2), "
					+ "username VARCHAR(24), "
					+ "password VARCHAR(64), "
					+ "salt VARBINARY(64), "
					+ "PRIMARY KEY (accNum))");
			System.out.println("Table customers created in the database");
			stmt.close();
			conn.close();
		}
		catch(SQLException | ClassNotFoundException e)
		{
			System.err.println("Error opening database");
			e.printStackTrace();
		}
	}
	//checks if table is made
	public void createEmployees()
	{
		try
		{
			//sets db driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//sets proper timezone
			String connString = "jdbc:mysql://localhost/bank?user=" + user +
				"&password=" + pass + "&useLegacyDateTimeCode=false&" + 
				"serverTimezone=America/New_York";
			Connection conn = DriverManager.getConnection(connString);
			Statement stmt = conn.createStatement();
			//creates employee table
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS employees (id INT AUTO_INCREMENT, " +
					"fullName VARCHAR(40), " +
					"position VARCHAR(24), " + 
					"username VARCHAR(24), " + 
					"password VARCHAR(64), " +
					"salt VARCHAR(64)), " +
					"PRIMARY KEY (id))");
			System.out.println("Table employees created in the database");
			stmt.close();
			conn.close();
		}
		catch(SQLException | ClassNotFoundException e)
		{
			System.err.println("Error opening database");
			e.printStackTrace();
		}
	}
}
