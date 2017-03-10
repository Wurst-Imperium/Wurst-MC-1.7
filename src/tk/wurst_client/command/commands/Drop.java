package tk.wurst_client.command.commands;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;
import tk.wurst_client.module.modules.DotDrop;

public class Drop extends Command
{
	private static String[] commandHelp =
	{
		"Drops all your items on the ground.",
		".drop"
	};
	
	public Drop()
	{
		super("drop", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(args == null)
			Client.Wurst.moduleManager.getModuleFromClass(DotDrop.class).setToggled(true);
		else
			commandError();
	}
}
