package bank.controller;

import bank.beans.*;
import java.util.Scanner;

class Login
{
	//Test program to check classes
	public static void main(String[] args)
	{
		//set up objects
		Customer customer1 = new Customer();
		Employee employee1 = new Employee();
		PasswordEncryption passEncrypt = new PasswordEncryption();
		//ask for Account variables
		Scanner input1 = new Scanner(System.in);
		System.out.println("Please enter: full name, username, password");
		String info = input1.nextLine();
		//all in one line and use .split to seperate
		String[] infoList = info.split(", ");
		customer1.setName(infoList[0]);
		System.out.println("Your name is: " + customer1.getName());
		customer1.setUsername(infoList[1]);
		System.out.println("your username is: " + customer1.getUsername());
		//save the salt and then use it for all future customer1 login attempts
		customer1.setSalt(passEncrypt.getSalt());
		//sets the password
		customer1.setPassword(passEncrypt.hashThePass(infoList[2], customer1.getSalt()));
		System.out.println("Hashed pass: " + customer1.getPassword());
		System.out.println("Please re-enter password");
		String passCheck = input1.nextLine();

		if(passEncrypt.checkPass(passCheck, customer1.getPassword(), customer1.getSalt()))
		{
			System.out.println("Account created");		
		}
		else
		{
			System.out.println("Passwords do not match");
		}


	}
}
