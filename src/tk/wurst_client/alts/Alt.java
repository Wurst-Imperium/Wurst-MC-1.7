package tk.wurst_client.alts;

public class Alt
{
	public String name;
	public String password;
	public boolean cracked;
	
	public Alt(String name, String password)
	{
		if(password == null)
			password = "";
		this.name = name;
		this.password = password;
		if(password.length() == 0)
			this.cracked = true;
		else
			this.cracked = false;
	}
}
