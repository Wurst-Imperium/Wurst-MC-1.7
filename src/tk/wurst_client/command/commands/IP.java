package tk.wurst_client.command.commands;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;

public class IP extends Command
{
	private static String[] commandHelp =
	{
		"Tells you the IP of this server.",
		".ip"
	};
	
	public IP()
	{
		super("ip", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		Client.Wurst.chat.message("IP: " + Client.Wurst.currentServerIP);
	}
}
