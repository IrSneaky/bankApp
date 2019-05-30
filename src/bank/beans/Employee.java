package bank.beans;

public class Employee extends Account
{
	private int id;
	private String position;
	
	public Employee(){}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
