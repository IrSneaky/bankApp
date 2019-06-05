package bank.controller;

import bank.beans.*;
import bank.database.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
class Login
{
	private List<Customer> cList = new ArrayList<Customer>();
	private List<Employee> eList = new ArrayList<Employee>();

	public void enterInfoCustomer(String user, String pass)
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
				cusObj.insertCustomer(customer1);
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

	public void enterInfoEmployee(String user, String pass)
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
				empObj.insertEmployee(employee1); 
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


	//Test program to check classes
	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			System.out.println("\nBanking Application. To close, enter exit");
			System.out.println("=========================================");
			System.out.println("Loading database...");
			//for while loop
			boolean stop = true;
			//set up objects to execute program
			Login login = new Login();
			TranslateXML xml = new TranslateXML();
			StoreCustomer setupC = new StoreCustomer(args[0], args[1]);
			StoreEmployee setupE = new StoreEmployee(args[0], args[1]);
			login.cList = setupC.getCustomers();
			login.eList = setupE.getEmployees();
			while (stop)
			{
				//pulls data from db to set to list 
				login.cList = setupC.getCustomers();
				login.eList = setupE.getEmployees();
				Scanner input1 = new Scanner(System.in);
				System.out.println("\nCustomer or Employee info?(c or e)");
				System.out.println("Enter xml to generate xml files.");
				System.out.println("Enter display to show current tables.");
				System.out.print("--> ");
				String choice = input1.nextLine();
				//gives choice to what feature to use
				if(choice.equals("c"))
				{
					login.enterInfoCustomer(args[0], args[1]);
				}
				else if(choice.equals("e"))
				{
					login.enterInfoEmployee(args[0], args[1]);
				}
				else if(choice.equals("exit"))
				{
					stop = false;
				}
				else if(choice.equals("xml"))
				{
					//creates xml files for each customer and employee loaded
					//into list
					System.out.println("Generating XML...");
					xml.customerXML(login.cList);
					xml.employeeXML(login.eList);
				}
				else if(choice.equals("display"))
				{
					//display current cList and eList which are the tables in db
					System.out.println("\n=================================");
					System.out.println("Customer info:");
					for(int i = 0; i < login.cList.size(); i++)
					{
						System.out.println("---------------------------------");
						System.out.println("Name: " + login.cList.get(i).getName());
						System.out.println("Username: " 
								+ login.cList.get(i).getUsername());
						System.out.println("accNum: " 
								+ login.cList.get(i).getAccountNumber());
						System.out.println("balance: " 
								+ login.cList.get(i).getBalance());
					}
					System.out.println("=================================");
					System.out.println("\n=================================");
					System.out.println("Employee info:");
					for(int j = 0; j < login.eList.size(); j++)
					{
						System.out.println("---------------------------------");
						System.out.println("Name: " + login.eList.get(j).getName());
						System.out.println("Username: " 
								+ login.eList.get(j).getUsername());
						System.out.println("Id: " + login.eList.get(j).getId());
						System.out.println("Position: " 
								+ login.eList.get(j).getPosition());
					}
					System.out.println("=================================");
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
