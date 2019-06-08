package bank.database;

import bank.beans.*;

import java.io.File;
import java.util.List;
import java.util.Base64;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

public class CreateXML
{

	//sets up a xml file for customers
	public void customerXML(Customer newCust)
	{
		try
		{
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.newDocument();
			//Main element in file
			Element mainEle = doc.createElement("bankCustomer");
			doc.appendChild(mainEle);
			//creates the customer wrap for info
			Element customer = doc.createElement("customer");
			mainEle.appendChild(customer);
			//customer account number
			Element accNum = doc.createElement("accountNumber");
			accNum.appendChild(doc.createTextNode(
					Integer.toString(newCust.getAccountNumber())));
			customer.appendChild(accNum);
			//customer name
			Element fullName = doc.createElement("fullName");
			fullName.appendChild(doc.createTextNode(newCust.getName()));
			customer.appendChild(fullName);
			//customer balance
			Element balance = doc.createElement("balance");
			balance.appendChild(doc.createTextNode(
					Double.toString(newCust.getBalance())));
			customer.appendChild(balance);
			//customer username
			Element username = doc.createElement("username");
			username.appendChild(doc.createTextNode(newCust.getUsername()));
			customer.appendChild(username);
			//customer password
			Element password = doc.createElement("password");
			password.appendChild(doc.createTextNode(newCust.getPassword()));
			customer.appendChild(password);
			//customer salt
			Element salt = doc.createElement("salt");
			salt.appendChild(doc.createTextNode(newCust.getSalt()));
			customer.appendChild(salt);
			//creates file from info
			TransformerFactory tranFac = TransformerFactory.newInstance();
			Transformer tran = tranFac.newTransformer();
			tran.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("xml/customers/customer" 
						+ newCust.getAccountNumber() + ".xml"));
			tran.transform(source, result);
		}
		catch(Exception e)
		{
			System.out.println("XML file creation failed");
			e.printStackTrace();
		}
		
	}

	//sets up a xml file for customers
	public void employeeXML(Employee newEmpl)
	{
		try
		{
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.newDocument();
			Element employee = doc.createElement("employee");
			doc.appendChild(employee);
			//Element mainEle = doc.createElement("employees");
			//doc.appendChild(mainEle);
			//create amount of customers determined by size
			//int size = employeeList.size();
			//creates the employee wrap for info
			//Element employee = doc.createElement("employee");
			//mainEle.appendChild(employee);
			//employee id
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(
					Integer.toString(newEmpl.getId())));
			employee.appendChild(id);
			//employee name
			Element fullName = doc.createElement("fullName");
			fullName.appendChild(doc.createTextNode(newEmpl.getName()));
			employee.appendChild(fullName);
			//employee balance
			Element position = doc.createElement("position");
			position.appendChild(doc.createTextNode(newEmpl.getPosition()));
			employee.appendChild(position);
			//employee username
			Element username = doc.createElement("username");
			username.appendChild(doc.createTextNode(newEmpl.getUsername()));
			employee.appendChild(username);
			//employee password
			Element password = doc.createElement("password");
			password.appendChild(doc.createTextNode(newEmpl.getPassword()));
			employee.appendChild(password);
			//employee salt
			Element salt = doc.createElement("salt");
			salt.appendChild(doc.createTextNode(newEmpl.getSalt()));
			employee.appendChild(salt);
			//creates file from info
			TransformerFactory tranFac = TransformerFactory.newInstance();
			Transformer tran = tranFac.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("xml/employees/employee"
						+ newEmpl.getId() + ".xml"));
			tran.transform(source, result);
		}
		catch(Exception e)
		{
			System.out.println("XML file creation failed");
			e.printStackTrace();
		}
		
	}

}
