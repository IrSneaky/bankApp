package bank.controller;

import bank.database.StoreCustomer;
import bank.database.ReadCustomerXML;
import bank.beans.Customer;

import java.nio.file.WatchService;
import java.nio.file.FileSystems;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;
import java.nio.file.StandardWatchEventKinds;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

public class FileWatchCustomer implements Runnable
{
	private String user;
	private String pass;
	private final ExecutorService pool = Executors.newFixedThreadPool(10);

	public FileWatchCustomer(String user, String pass)
	{
		this.user = user;
		this.pass = pass;
	}

	private void watchForCustomerXML()
	{
		try
		{
			//set up watch
			Path dir = Paths.get("xml/customers/");
			WatchService watcher = dir.getFileSystem().newWatchService();
			//directory path to xml files for customers
	
			//check for new and modified files
			//dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, 
			//		StandardWatchEventKinds.ENTRY_MODIFY);
			dir.register(watcher, 
					StandardWatchEventKinds.ENTRY_MODIFY);


			//initialize objects for while loop
			StoreCustomer customer = new StoreCustomer(user, pass);
			WatchKey key;
			while ((key = watcher.take()) != null)
			{
				for (WatchEvent<?> event : key.pollEvents())
				{
					System.out.println("Event kind: " + event.kind());
					//cast file name into string
					String fileName = event.context().toString();
					//add full path to name
					String filePath = "xml/customers/" + fileName;
					Callable<Customer> task = new ReadCustomerXML(filePath);
					Future<Customer> future = pool.submit(task);
					Customer newCust = future.get();
					//store customer in database
					if (newCust != null)
					{
						customer.insertCustomer(newCust);
					}
					else
					{
						System.out.println("No xml data to read");
					}
				}
				key.reset();
			}
			
		}
		catch(Exception e)
		{
			System.out.println("File watching stopped for xml/customers.");
			//e.printStackTrace();
		}
	}

	public void run()
	{
		try
		{
			watchForCustomerXML();
			pool.shutdown();
		}
		catch(Exception e)
		{
			System.out.println("Thread failed");
			e.printStackTrace();
		}
	}
}
