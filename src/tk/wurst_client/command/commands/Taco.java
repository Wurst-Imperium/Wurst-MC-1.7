package tk.wurst_client.command.commands;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;
import tk.wurst_client.module.modules.DotTaco;

public class Taco extends Command
{
	private static String[] commandHelp =
	{
		"Made for WiZARDHAX.",
		".taco"
	};
	
	public Taco()
	{
		super("taco", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		Client.Wurst.moduleManager.getModuleFromClass(DotTaco.class).toggleModule();
	}
}
