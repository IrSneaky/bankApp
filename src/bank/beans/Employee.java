package bank.beans;

public class Employee extends Account
{
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
}
