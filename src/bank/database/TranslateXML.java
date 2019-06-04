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
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Document;


public class TranslateXML
{
	//sets up a xml file for customers
	public void customerXML(List<Customer> customerList)
	{
		try
		{
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.newDocument();
			Element mainEle = doc.createElement("customers");
			doc.appendChild(mainEle);
			//create amount of customers determined by size
			int size = customerList.size();
			for(int i = 0; i < size; i++)
			{
				//creates the customer wrap for info
				Element customer = doc.createElement("customer");
				mainEle.appendChild(customer);
				//customer account number
				Element accNum = doc.createElement("accountNumber");
				accNum.appendChild(doc.createTextNode(
						Integer.toString(customerList.get(i).getAccountNumber())));
				customer.appendChild(accNum);
				//customer name
				Element fullName = doc.createElement("fullName");
				fullName.appendChild(doc.createTextNode(customerList.get(i).getName()));
				customer.appendChild(fullName);
				//customer balance
				Element balance = doc.createElement("balance");
				balance.appendChild(doc.createTextNode(
						Double.toString(customerList.get(i).getBalance())));
				customer.appendChild(balance);
				//customer username
				Element username = doc.createElement("username");
				username.appendChild(doc.createTextNode(customerList.get(i).getUsername()));
				customer.appendChild(username);
				//customer password
				Element password = doc.createElement("password");
				password.appendChild(doc.createTextNode(customerList.get(i).getPassword()));
				customer.appendChild(password);
				//customer salt
				Element salt = doc.createElement("salt");
				salt.appendChild(doc.createTextNode(customerList.get(i).getSalt()));
				customer.appendChild(salt);
				//creates file from info
				TransformerFactory tranFac = TransformerFactory.newInstance();
				Transformer tran = tranFac.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("xml/customers" 
							+ customerList.get(i).getAccountNumber() + ".xml"));
				tran.transform(source, result);
			}
		}
		catch(Exception e)
		{
			System.out.println("XML file creation failed");
			e.printStackTrace();
		}
		
	}

	public List<Customer> readCustomerXML()
	{
		try
		{
			//grabs generated xml file from customerXML function
			File xmlFile = new File("xml/customers.xml");
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.parse(xmlFile);
			doc.getDocumentElement().normalize();
			//gets list of customers
			NodeList customerNodes = doc.getElementsByTagName("customer");
			int size = customerNodes.getLength();
			//list of customers init
			List<Customer> customers = new ArrayList<Customer>();
			for(int i = 0; i < size; i++)
			{
				//creates temp customer to insert into list
				Customer cTemp = new Customer();
				Node cNode = customerNodes.item(i);
				if (cNode.getNodeType() == Node.ELEMENT_NODE)
				{
					//reads XML into customer temp
					Element cEle = (Element) cNode;
					cTemp.setAccountNumber(
							Integer.parseInt(cEle.getAttribute("accountNumber")));
					cTemp.setName(cEle.getAttribute("fullName"));
					cTemp.setBalance(
							Double.parseDouble(cEle.getAttribute("balance")));
					cTemp.setUsername(cEle.getAttribute("username"));
					cTemp.setPassword(cEle.getAttribute("password"));
					cTemp.setSalt(cEle.getAttribute("salt"));
				}
				//puts temp into list
				customers.add(cTemp);
			}
			return customers;
		}
		catch(Exception e)
		{
			System.out.println("XML read failed");
			e.printStackTrace();
		}
		return null;
	}

	//sets up a xml file for customers
	public void employeeXML(List<Employee> employeeList)
	{
		try
		{
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.newDocument();
			Element mainEle = doc.createElement("employees");
			doc.appendChild(mainEle);
			//create amount of customers determined by size
			int size = employeeList.size();
			for(int i = 0; i < size; i++)
			{
				//creates the employee wrap for info
				Element employee = doc.createElement("employee");
				mainEle.appendChild(employee);
				//employee id
				Element id = doc.createElement("id");
				id.appendChild(doc.createTextNode(
						Integer.toString(employeeList.get(i).getId())));
				employee.appendChild(id);
				//employee name
				Element fullName = doc.createElement("fullName");
				fullName.appendChild(doc.createTextNode(employeeList.get(i).getName()));
				employee.appendChild(fullName);
				//employee balance
				Element position = doc.createElement("position");
				position.appendChild(doc.createTextNode(employeeList.get(i).getPosition()));
				employee.appendChild(position);
				//employee username
				Element username = doc.createElement("username");
				username.appendChild(doc.createTextNode(employeeList.get(i).getUsername()));
				employee.appendChild(username);
				//employee password
				Element password = doc.createElement("password");
				password.appendChild(doc.createTextNode(employeeList.get(i).getPassword()));
				employee.appendChild(password);
				//employee salt
				Element salt = doc.createElement("salt");
				salt.appendChild(doc.createTextNode(employeeList.get(i).getSalt()));
				employee.appendChild(salt);
				//creates file from info
				TransformerFactory tranFac = TransformerFactory.newInstance();
				Transformer tran = tranFac.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("xml/employees"
							+ employeeList.get(i).getId() + ".xml"));
				tran.transform(source, result);
			}
		}
		catch(Exception e)
		{
			System.out.println("XML file creation failed");
			e.printStackTrace();
		}
		
	}

	public List<Employee> readEmployeeXML()
	{
		try
		{
			//grabs generated xml file from employeeXML function
			File xmlFile = new File("xml/employee.xml");
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.parse(xmlFile);
			doc.getDocumentElement().normalize();
			//gets list of employees
			NodeList employeeNodes = doc.getElementsByTagName("employee");
			int size = employeeNodes.getLength();
			//list of employees init
			List<Employee> employees = new ArrayList<Employee>();
			for(int i = 0; i < size; i++)
			{
				//creates temp employee to insert into list
				Employee eTemp = new Employee();
				Node eNode = employeeNodes.item(i);
				if (eNode.getNodeType() == Node.ELEMENT_NODE)
				{
					//reads XML into customer temp
					Element eEle = (Element) eNode;
					eTemp.setId(Integer.parseInt(eEle.getAttribute("accountNumber")));
					eTemp.setName(eEle.getAttribute("fullName"));
					eTemp.setPosition(eEle.getAttribute("position"));
					eTemp.setUsername(eEle.getAttribute("username"));
					eTemp.setPassword(eEle.getAttribute("password"));
					eTemp.setSalt(eEle.getAttribute("salt"));
				}
				//puts temp into list
				employees.add(eTemp);
			}
			return employees;
		}
		catch(Exception e)
		{
			System.out.println("XML read failed");
			e.printStackTrace();
		}
		return null;
	}
}
