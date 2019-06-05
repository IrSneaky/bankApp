package bank.controller;

import bank.database.*;
import bank.beans.Customer;
import bank.beans.Employee;

import java.nio.file.WatchService;
import java.nio.file.FileSystems;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;
import java.nio.file.StandardWatchEventKinds;

public class FileWatch
{
	public FileWatch(){}

	public void watchForCustomerXML(String user, String pass)
	{
		try
		{
			//set up watch
			WatchService watcher = FileSystems.getDefault().newWatchService();
			//directory path to xml files for customers
			Path dir = Paths.get("xml/customers/");
	
			//only check for new files
			dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

			//initialize objects for while loop
			WatchKey key;
			StoreCustomer customer = new StoreCustomer(user, pass);
			TranslateXML xml = new TranslateXML();
			while((key = watcher.take()) != null)
			{
				for (WatchEvent<?> event : key.pollEvents())
				{
					//cast file name into string
					String fileName = (String)(Object)event.context();
					//add full path to name
					String filePath = "xml/customers/" + fileName;
					//pass full name into xml file reader into object
					Customer newCust = xml.readCustomerXML(filePath);
					//store customer in database]
					customer.insertCustomer(newCust);
				}

			}
		}
		catch(Exception e)
		{
			System.out.println("File watching failed");
			e.printStackTrace();
		}
	}
}
