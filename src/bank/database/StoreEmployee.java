package bank.database;

import bank.beans.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreEmployee
{
	private String user;
	private String pass;

	StoreEmployee(String user, String pass)
	{
		this.user = user;
		this.pass = pass;
	}


	public void insertEmployee(String fullName, String position, String username, 
			String password, byte[] salt)
	{
		try
		{
			//sets db driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//sets timezone and connects
			String connString = "jdbc:mysql://mysql://localhost/bank?"+
				"user=" + user + "&password=" + pass;
			Connection conn = DriverManager.getConnection(connString);
			//sql statement
			String stmtString = "INSERT INTO employees "
					+ "(fullName, position, username, password, salt)"
					+ "VALUES (?, ?, ?, ?, ?)";
			//prepared statement setup
			PreparedStatement pstmt = conn.prepareStatement(stmtString);
			pstmt.setString(1, fullName);
			pstmt.setString(2, position);
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


	public Employee pullEmployee(int id)
	{
		try
		{
			//set db driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//connection setup and setting timezone
			String connString = "jdbc:mysql://localhost/bank?user=" + user +
				"&password=" + pass + "&useLegacyDateTimeCode=false&" + 
				"serverTimezone=America/New_York";
			Connection conn = DriverManager.getConnection(connString);
			//search by ID
			String stmtString = "SELECT * FROM employees WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(stmtString);
			ResultSet rs = pstmt.executeQuery(stmtString);
			Employee tempE = new Employee();
			//checks if result set is pulled
			if(rs.absolute(1))
			{
				tempE.setId(rs.getInt(1));
				tempE.setName(rs.getString(2));
				tempE.setPosition(rs.getString(3));
				tempE.setUsername(rs.getString(4));
				tempE.setPassword(rs.getString(5));
				tempE.setSalt(rs.getBytes(6));
			}
			else
			{
				System.out.println("No such data");
			}
			rs.close();
			pstmt.close();
			conn.close();
			return tempE;
		}
		catch(SQLException | ClassNotFoundException e)
		{
			System.err.println("Error opening database");
			e.printStackTrace();
			return null;
		}
		
	}
}
