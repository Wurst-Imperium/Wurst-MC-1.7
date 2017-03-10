package tk.wurst_client.command.commands;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;
import tk.wurst_client.module.modules.Throw;
import tk.wurst_client.utils.MiscUtils;

public class ThrowSettings extends Command
{

	private static String[] commandHelp =
	{
		"Changes the speed of Throw or toggles it.",
		".throw",
		".throw speed <speed>",
	};
	
	public ThrowSettings()
	{
		super("throw", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(args == null)
		{
			Client.Wurst.moduleManager.getModuleFromClass(Throw.class).toggleModule();
			Client.Wurst.chat.message("Throw turned " + (Client.Wurst.moduleManager.getModuleFromClass(Throw.class).getToggled() == true ? "on" : "off") + ".");
		}else if(args[0].equalsIgnoreCase("speed") && MiscUtils.isInteger(args[1]))
		{
			if(Integer.valueOf(args[1]) < 1)
			{
				Client.Wurst.chat.error("Throw speed must be at least 1.");
				return;
			}
			Client.Wurst.options.throwSpeed = Integer.valueOf(args[1]);
			Client.Wurst.fileManager.saveValues();
			Client.Wurst.chat.message("Throw speed set to " + args[1] + ".");
		}else
			commandError();
	}
}
