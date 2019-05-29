package bank.database;

import bank.beans.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class StoreCustomer
{
	private String user;
	private String pass;

	public StoreCustomer(String user, String pass)
	{
		this.user = user;
		this.pass = pass;
	}


	public void insertCustomer(String fullName, double balance, String username, 
			String password, byte[] salt)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String connString = "jdbc:mysql://localhost/bank?user=" + user +
				"&password=" + pass + "&useLegacyDateTimeCode=false&" + 
				"serverTimezone=America/New_York";
			Connection conn = DriverManager.getConnection(connString);
			String stmtString = "INSERT INTO customers "
					+ "(fullName, balance, username, password, salt)"
					+ "VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(stmtString);
			pstmt.setString(1, fullName);
			pstmt.setDouble(2, balance);
			pstmt.setString(3, username);
			pstmt.setString(4, password);
			pstmt.setBytes(5, salt);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}
		catch(SQLException | ClassNotFoundException e)
		{
			System.err.println("Error opening database");
			e.printStackTrace();
		}
	}

	public Customer pullCustomer(int accNum)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String connString = "jdbc:mysql://localhost/bank?user=" + user +
				"&password=" + pass + "&useLegacyDateTimeCode=false&" + 
				"serverTimezone=America/New_York";
			Connection conn = DriverManager.getConnection(connString);
			String stmtString = String.format("SELECT * FROM customers WHERE accNum = %d", accNum);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(stmtString);
			Customer tempC = new Customer();
			if(rs.absolute(1))
			{
				tempC.setAccountNumber(rs.getInt(1));
				tempC.setName(rs.getString(2));
				tempC.setBalance(rs.getDouble(3));
				tempC.setUsername(rs.getString(4));
				tempC.setPassword(rs.getString(5));
				tempC.setSalt(rs.getBytes(6));
			}
			else
			{
				System.out.println("No such data");
			}
			rs.close();
			stmt.close();
			conn.close();
			return tempC;
		}
		catch(SQLException | ClassNotFoundException e)
		{
			System.err.println("Error opening database");
			e.printStackTrace();
			return null;
		}
		
	}
}
