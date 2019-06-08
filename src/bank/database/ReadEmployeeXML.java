package bank.database;

import bank.beans.Employee;

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

public class ReadEmployeeXML implements Callable<Employee>
{
	private final String filePath;

	public ReadEmployeeXML(String filePath)
	{
		this.filePath = filePath;
	}


	public Employee readXML()
	{
		try
		{
			//grabs generated xml file from employeeXML function
			File xmlFile = new File(filePath);
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc = docBuild.parse(xmlFile);
			doc.getDocumentElement().normalize();
			//gets list of employees
			NodeList employeeNodes = doc.getElementsByTagName("employee");
			//creates temp employee to insert into list
			Employee eTemp = new Employee();
			Node eNode = employeeNodes.item(0);
			if (eNode.getNodeType() == Node.ELEMENT_NODE)
			{
				//reads XML into customer temp
				Element eEle = (Element) eNode;
				eTemp.setId(
						Integer.parseInt(
						eEle.getElementsByTagName("id")
						.item(0).getTextContent()));
				eTemp.setName(eEle.getElementsByTagName("fullName")
						.item(0).getTextContent());
				eTemp.setPosition(eEle.getElementsByTagName("position")
						.item(0).getTextContent());
				eTemp.setUsername(eEle.getElementsByTagName("username")
						.item(0).getTextContent());
				eTemp.setPassword(eEle.getElementsByTagName("password")
						.item(0).getTextContent());
				eTemp.setSalt(eEle.getElementsByTagName("salt")
						.item(0).getTextContent());
			}
			return eTemp;
		}
		catch(Exception e)
		{
			System.out.println("XML read failed");
			e.printStackTrace();
		}
		return null;
	}

	public Employee call()
	{
		return readXML();
	}
}
