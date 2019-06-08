package bank.database;

import bank.beans.Customer;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import java.util.concurrent.Callable;

public class ReadCustomerXML implements Callable<Customer>
{
	private final String filePath;

	public ReadCustomerXML(String filePath)
	{
		this.filePath = filePath;
	}

	public Customer readXML()
	{
		try
		{
			//grabs generated xml file from customerXML function
			File xmlFile = new File(filePath);
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.parse(xmlFile);
			doc.getDocumentElement().normalize();
			//gets list of customers
			NodeList customerNodes = doc.getElementsByTagName("customer");
			//creates temp customer to insert into db
			Customer cTemp = new Customer();
			Node cNode = customerNodes.item(0);
			if (cNode.getNodeType() == Node.ELEMENT_NODE)
			{
				//reads XML into customer temp
				Element cEle = (Element) cNode;
				cTemp.setAccountNumber(
						Integer.parseInt(
						cEle.getElementsByTagName("accountNumber")
						.item(0).getTextContent()));
				cTemp.setName(cEle.getElementsByTagName("fullName")
						.item(0).getTextContent());
				cTemp.setBalance(
						Double.parseDouble(
						cEle.getElementsByTagName("balance")
						.item(0).getTextContent()));
				cTemp.setUsername(cEle.getElementsByTagName("username")
						.item(0).getTextContent());
				cTemp.setPassword(cEle.getElementsByTagName("password")
						.item(0).getTextContent());
				cTemp.setSalt(cEle.getElementsByTagName("salt")
						.item(0).getTextContent());
			}
			//puts temp into list
			return cTemp;
		}
		catch(Exception e)
		{
			System.out.println("XML read failed");
			e.printStackTrace();
		}
		return null;
	}

	public Customer call()
	{
		return readXML();
	}
}
