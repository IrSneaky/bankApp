package bank.beans;

public class Account
{
	private String fullName;
	private String username;
	private String password;
	private byte[] salt;

	public Account(){}

	public String getName()
	{
		return fullName;
	}

	public void setName(String fullName)
	{
		this.fullName = fullName;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public byte[] getSalt()
	{
		return salt;
	}

	public void setSalt(byte[] salt)
	{
		this.salt = salt;
	}


}
