package bank.controller;

import bank.database.StoreEmployee;
import bank.database.ReadEmployeeXML;
import bank.beans.Employee;

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

public class FileWatchEmployee implements Runnable
{
	private String user;
	private String pass;
	private final ExecutorService pool = Executors.newFixedThreadPool(10);

	public FileWatchEmployee(String user, String pass)
	{
		this.user = user;
		this.pass = pass;
	}

	private void watchForEmployeeXML()
	{
		try
		{
			//set up watch
			Path dir = Paths.get("xml/employees/");
			WatchService watcher = dir.getFileSystem().newWatchService();
			//directory path to xml files for employees
	
			//check for new and modified files
			dir.register(watcher,
					StandardWatchEventKinds.ENTRY_MODIFY);

			//initialize objects for while loop
			StoreEmployee employee = new StoreEmployee(user, pass);
			WatchKey key;
			while ((key = watcher.take()) != null)
			{
				for (WatchEvent<?> event : key.pollEvents())
				{
					//cast file name into string
					String fileName = event.context().toString();
					//add full path to name
					String filePath = "xml/employees/" + fileName;
					Callable<Employee> task = new ReadEmployeeXML(filePath);
					Future<Employee> future = pool.submit(task);
					//pass full name into xml file reader into object
					Employee newEmpl = future.get();
					//store employee in database
					if (newEmpl != null)
					{
						employee.insertEmployee(newEmpl);
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
			System.out.println("File watching stopped for xml/employees.");
			//e.printStackTrace();
		}
	}

	public void run()
	{
		try
		{
			watchForEmployeeXML();
			pool.shutdown();
		}
		catch(Exception e)
		{
			System.out.println("Thread failed");
			e.printStackTrace();
		}
	}
}
