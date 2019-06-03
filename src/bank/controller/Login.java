package bank.controller;

import bank.beans.*;
import bank.database.*;
import java.util.Scanner;

class Login
{
	public static void enterInfoCustomer(String user, String pass)
	{
		//set up objects
		InitDatabase db = new InitDatabase(user, pass);
		Customer customer1 = new Customer();
		PasswordEncryption passEncrypt = new PasswordEncryption();
		//ask for Account variables
		Scanner input1 = new Scanner(System.in);
		System.out.println("Please enter: full name, username, password");
		String info = input1.nextLine();
		//all in one line and use .split to seperate
		String[] infoList = info.split(", ");
		if(infoList.length == 3)
		{
			customer1.setName(infoList[0]);
			System.out.println("Your name is: " + customer1.getName());
			customer1.setUsername(infoList[1]);
			System.out.println("your username is: " + customer1.getUsername());
			//save the salt and then use it for all future customer1 login attempts
			customer1.setSalt(passEncrypt.getSalt());
			//sets the password
			customer1.setPassword(passEncrypt.hashThePass(infoList[2], customer1.getSalt()));
			System.out.println("Please re-enter password");
			String passCheck = input1.nextLine();
			
			db.createDB();
			db.createCustomers();
			if(passEncrypt.checkPass(passCheck, customer1.getPassword(), customer1.getSalt()))
			{
				System.out.println("Account created");
				//puts in db user and pass to object
				StoreCustomer cusObj = new StoreCustomer(user, pass);
				cusObj.insertCustomer(customer1.getName(), 
						customer1.getBalance(), 
						customer1.getUsername(), 
						customer1.getPassword(),
						customer1.getSalt());
				System.out.println("Info stored in db");
			}
			else
			{
				System.out.println("Passwords do not match");
			}
		}
		else
		{
			System.out.println("Please enter the format as: fullName, Username, password");
		}
	}

	public static void enterInfoEmployee(String user, String pass)
	{
		//set up objects
		InitDatabase db = new InitDatabase(user, pass);
		Employee employee1 = new Employee();
		PasswordEncryption passEncrypt = new PasswordEncryption();
		//ask for Account variables
		Scanner input1 = new Scanner(System.in);
		System.out.println("Please enter: full name, username, password");
		String info = input1.nextLine();
		//all in one line and use .split to seperate
		String[] infoList = info.split(", ");
		if(infoList.length == 3)
		{
			employee1.setName(infoList[0]);
			System.out.println("Name set: " + employee1.getName());
			employee1.setUsername(infoList[1]);
			System.out.println("Username set: " + employee1.getUsername());
			//save the salt and then use it for all future customer1 login attempts
			employee1.setSalt(passEncrypt.getSalt());
			//sets the password
			employee1.setPassword(passEncrypt.hashThePass(infoList[2], employee1.getSalt()));
			System.out.println("Please re-enter password");
			String passCheck = input1.nextLine();
			
			db.createDB();
			db.createEmployees();

			if(passEncrypt.checkPass(passCheck, employee1.getPassword(), employee1.getSalt()))
			{
				System.out.println("Account created");
				//puts in db user and pass to object
				StoreEmployee empObj = new StoreEmployee(user, pass);
				empObj.insertEmployee(employee1.getName(), 
						employee1.getPosition(), 
						employee1.getUsername(), 
						employee1.getPassword(),
						employee1.getSalt());
				System.out.println("Info stored in db");
				Employee testEmp = empObj.pullEmployee(1);
				System.out.println("Name: " + testEmp.getName());
			}
			else
			{
				System.out.println("Passwords do not match");
			}
		}
		else
		{
			System.out.println("Please enter the format as: fullName, Username, password");
		}
	}

	//Test program to check classes
	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			boolean stop = true;
			System.out.println("\nBanking Application. To close, enter exit");
			System.out.println("=========================================");
			while (stop)
			{
				Scanner input1 = new Scanner(System.in);
				System.out.println("\nCustomer or Employee info?(c or e)");
				String choice = input1.nextLine();
				if(choice.equals("c"))
				{
					enterInfoCustomer(args[0], args[1]);
				}
				else if(choice.equals("e"))
				{
					enterInfoEmployee(args[0], args[1]);
				}
				else if(choice.equals("exit"))
				{
					stop = false;
				}
				else
				{
					System.out.println("Please enter c, e, or exit.");
				}
			}
		}
		else
		{
			System.out.println("Please add the user and pass for db when running");
		}
	}
}
