package tk.wurst_client.command;

import tk.wurst_client.Client;

public class Command
{
	private String commandName;

	private String[] commandHelp;
	
	public Command(String commandName, String[] commandHelp)
	{
		this.commandName = commandName;
		this.commandHelp = commandHelp;
	}
	
	public String getName()
	{
		return this.commandName;
	}
	
	public String[] getHelp()
	{
		return this.commandHelp;
	}
	
	public void commandError()
	{
		Client.Wurst.chat.error("Something went wrong.");
		Client.Wurst.chat.message("If you need help, type \".help " + this.commandName + "\".");
	}
	
	public void onEnable(String input, String[] args){}
}
