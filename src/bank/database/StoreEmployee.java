package bank.database;

import bank.beans.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.ArrayList;

public class StoreEmployee
{
	private String user;
	private String pass;

	public StoreEmployee(String user, String pass)
	{
		this.user = user;
		this.pass = pass;
	}


	public void insertEmployee(Employee employee) 
	{
		try
		{
			System.out.println("Inserting employee " + employee.getId()
					+ "into database...");
			//sets db driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//sets timezone and connects
			String connString = "jdbc:mysql://localhost/bank?user=" + user +
				"&password=" + pass + "&useLegacyDateTimeCode=false&" + 
				"serverTimezone=America/New_York";
			Connection conn = DriverManager.getConnection(connString);
			//sql statement
			String stmtString = "INSERT INTO employees "
					+ "(fullName, position, username, password, salt)"
					+ "VALUES (?, ?, ?, ?, ?)";
			//prepared statement setup
			PreparedStatement pstmt = conn.prepareStatement(stmtString);
			pstmt.setString(1, employee.getName());
			pstmt.setString(2, employee.getPosition());
			pstmt.setString(3, employee.getUsername());
			pstmt.setString(4, employee.getPassword());
			pstmt.setString(5, employee.getSalt());
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			System.out.println("Insert successful.");
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
			//search by accNum
			String stmtString = "SELECT * FROM employees WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(stmtString);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			Employee tempE = new Employee();
			//checks if result set is pulled
			if(rs.absolute(1))
			{
				tempE.setId(rs.getInt(1));
				tempE.setName(rs.getString(2));
				tempE.setPosition(rs.getString(3));
				tempE.setUsername(rs.getString(4));
				tempE.setPassword(rs.getString(5));
				tempE.setSalt(rs.getString(6));
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

	public List<Employee> getEmployees()
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
			//search by accNum
			String stmtString = "SELECT * FROM employees";
			PreparedStatement pstmt = conn.prepareStatement(stmtString);
			ResultSet rs = pstmt.executeQuery();
			List<Employee> eList = new ArrayList<Employee>();
			while(rs.next())
			{
				Employee tempE = new Employee();
				//checks if result set is pulled
				tempE.setId(rs.getInt(1));
				tempE.setName(rs.getString(2));
				tempE.setPosition(rs.getString(3));
				tempE.setUsername(rs.getString(4));
				tempE.setPassword(rs.getString(5));
				tempE.setSalt(rs.getString(6));
				eList.add(tempE);
			}
			rs.close();
			pstmt.close();
			conn.close();
			return eList;
		}
		catch(SQLException | ClassNotFoundException e)
		{
			System.err.println("Error opening database");
			e.printStackTrace();
			return null;
		}
		
	}
}
