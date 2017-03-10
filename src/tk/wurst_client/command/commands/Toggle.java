package tk.wurst_client.command.commands;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;

public class Toggle extends Command
{
	private static String[] commandHelp =
	{
		"Toggles a command.",
		".t <command>"
	};
	
	public Toggle()
	{
		super("t", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		for(int i = 0; i < Client.Wurst.moduleManager.activeModules.size(); i++)
		{
			if(Client.Wurst.moduleManager.activeModules.get(i).getName().toLowerCase().equals(args[0].toLowerCase()))
			{
				Client.Wurst.moduleManager.activeModules.get(i).toggleModule();
				return;
			}
		}
		commandError();
	}
}
