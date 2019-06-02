package bank.controller;

import bank.beans.Customer;

import java.io.File;
import java.util.Base64;
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
	public void customerXML(Customer[] customerList)
	{
		try
		{
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.newDocument();
			Element mainEle = doc.createElement("customers");
			doc.appendChild(mainEle);
			//create amount of customers determined by size
			int size = customerList.length;
			for(int i = 0; i < size; i++)
			{
				//creates the customer wrap for info
				Element customer = doc.createElement("customer");
				mainEle.appendChild(customer);
				//customer account number
				Element accNum = doc.createElement("accountNumber");
				accNum.appendChild(doc.createTextNode(
							Integer.toString(customerList[i].getAccountNumber())));
				customer.appendChild(accNum);
				//customer name
				Element fullName = doc.createElement("fullName");
				fullName.appendChild(doc.createTextNode(customerList[i].getName()));
				customer.appendChild(fullName);
				//customer balance
				Element balance = doc.createElement("balance");
				balance.appendChild(doc.createTextNode(
							Double.toString(customerList[i].getBalance())));
				customer.appendChild(balance);
				//customer username
				Element username = doc.createElement("username");
				username.appendChild(doc.createTextNode(customerList[i].getUsername()));
				customer.appendChild(username);
				//customer password
				Element password = doc.createElement("password");
				password.appendChild(doc.createTextNode(customerList[i].getPassword()));
				customer.appendChild(password);
				//customer salt
				Element salt = doc.createElement("salt");
				salt.appendChild(doc.createTextNode(customerList[i].getSalt()));
				customer.appendChild(salt);
			}
			//creates file from info
			TransformerFactory tranFac = TransformerFactory.newInstance();
			Transformer tran = tranFac.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("customers.xml"));
			tran.transform(source, result);
		}
		catch(Exception e)
		{
			System.out.println("XML file creation failed");
			e.printStackTrace();
		}
		
	}

	public Customer[] readCustomerXML()
	{
		try
		{
			//grabs generated xml file from customerXML function
			File xmlFile = new File("customers.xml");
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.parse(xmlFile);
			doc.getDocumentElement().normalize();
			//gets list of customers
			NodeList customerNodes = doc.getElementsByTagName("customer");
			int size = customerNodes.getLength();
			//list of customers init
			Customer[] customers = new Customer[size];
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
				customers[i] = cTemp;
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
}
