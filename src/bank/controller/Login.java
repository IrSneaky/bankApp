package bank.controller;

import bank.beans.*;
import bank.database.*;
import java.util.Scanner;

class Login
{
	//Test program to check classes
	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			//set up objects
			InitDatabase db = new InitDatabase(args[0], args[1]);
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
					StoreCustomer cusObj = new StoreCustomer(args[0], args[1]);
					cusObj.insertCustomer(customer1.getName(), 
							customer1.getBalance(), 
							customer1.getUsername(), 
							customer1.getPassword(),
							customer1.getSalt());
					System.out.println("Info stored in db");
					Customer testCust = cusObj.pullCustomer(1);
					System.out.println("Name: " + testCust.getName());
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
		else
		{
			System.out.println("Please add the user and pass for db when running");
		}
	}
}
