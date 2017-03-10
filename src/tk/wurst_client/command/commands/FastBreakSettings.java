package tk.wurst_client.command.commands;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;
import tk.wurst_client.module.modules.FastBreak;

public class FastBreakSettings extends Command
{

	private static String[] commandHelp =
	{
		"Changes the settings of FastBreak or toggles it.",
		".fastbreak",
		".fastbreak mode <normal | instant>"
	};
	
	public FastBreakSettings()
	{
		super("fastbreak", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(args == null)
		{
			Client.Wurst.moduleManager.getModuleFromClass(FastBreak.class).toggleModule();
			Client.Wurst.chat.message("FastBreak turned " + (Client.Wurst.moduleManager.getModuleFromClass(FastBreak.class).getToggled() == true ? "on" : "off") + ".");
		}else if(args[0].toLowerCase().equals("mode"))
		{//0=normal, 1=instant
			if(args[1].toLowerCase().equals("normal"))
			{
				Client.Wurst.options.fastbreakMode = 0;
			}else if(args[1].toLowerCase().equals("instant"))
			{
				Client.Wurst.options.fastbreakMode = 1;
			}else
			{
				commandError();
				return;
			}
			Client.Wurst.fileManager.saveValues();
			Client.Wurst.chat.message("FastBreak mode set to \"" + args[1] + "\".");
		}
	}
}
