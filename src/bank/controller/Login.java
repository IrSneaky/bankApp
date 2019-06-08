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

	public Customer enterInfoCustomer(List<Customer> cList)
	{
		//set up objects
		Customer customer1 = new Customer();
		PasswordEncryption passEncrypt = new PasswordEncryption();
		CreateXML xml = new CreateXML();
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
			
			if(passEncrypt.checkPass(passCheck, customer1.getPassword(), customer1.getSalt()))
			{
				System.out.println("Account created");
				//puts in db user and pass to object
				if (cList == null)
				{
					customer1.setAccountNumber(1);
					xml.customerXML(customer1);
				}
				else
				{
					customer1.setAccountNumber(cList.size()+1);
					xml.customerXML(customer1);
				}
			}
			else
			{
				System.out.println("Passwords do not match");
			}
			return customer1;
		}
		else
		{
			System.out.println("Please enter the format as: fullName, Username, password");
			return null;
		}
	}

	public Employee enterInfoEmployee(List<Employee> eList)
	{
		//set up objects
		Employee employee1 = new Employee();
		PasswordEncryption passEncrypt = new PasswordEncryption();
		CreateXML xml = new CreateXML();
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
			
			if(passEncrypt.checkPass(passCheck, employee1.getPassword(), employee1.getSalt()))
			{
				System.out.println("Account created");
				//puts in db user and pass to object
				if (eList == null)
				{
					employee1.setId(1);
					xml.employeeXML(employee1);
				}
				else
				{
					employee1.setId(eList.size()+1);
					xml.employeeXML(employee1);
				}
			}
			else
			{
				System.out.println("Passwords do not match");
			}
			return employee1;
		}
		else
		{
			System.out.println("Please enter the format as: fullName, Username, password");
			return null;
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
			StoreCustomer setupC = new StoreCustomer(args[0], args[1]);
			StoreEmployee setupE = new StoreEmployee(args[0], args[1]);

			Thread custTask = new Thread(new FileWatchCustomer(args[0], args[1]));	
			Thread emplTask = new Thread(new FileWatchEmployee(args[0], args[1]));
			custTask.start();
			emplTask.start();
			
			//ReadCustomerXML xml = new ReadCustomerXML("xml/customers/customer9.xml");
			login.cList = setupC.getCustomers();
			login.eList = setupE.getEmployees();
			while (stop)
			{
				//pulls data from db to set to list 
				Scanner input1 = new Scanner(System.in);
				System.out.println("\nCustomer or Employee info?(c or e)");
				System.out.println("Enter display to show current tables.");
				System.out.print("--> ");
				String choice = input1.nextLine();
				//gives choice to what feature to use
				if(choice.equals("c"))
				{
					login.cList.add(login.enterInfoCustomer(login.cList));
					//Customer c1 = xml.readXML();
					//System.out.println("Name: " + c1.getName());
				}
				else if(choice.equals("e"))
				{
					login.eList.add(login.enterInfoEmployee(login.eList));
				}
				else if(choice.equals("exit"))
				{
					stop = false;
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
			custTask.interrupt();
			emplTask.interrupt();
		}
		else
		{
			System.out.println("Please add the user and pass for db when running");
		}
	}
}
