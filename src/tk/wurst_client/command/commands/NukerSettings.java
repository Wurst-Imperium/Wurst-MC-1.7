package tk.wurst_client.command.commands;

import net.minecraft.block.Block;
import tk.wurst_client.Client;
import tk.wurst_client.command.Command;
import tk.wurst_client.module.modules.Nuker;
import tk.wurst_client.utils.MiscUtils;

public class NukerSettings extends Command
{

	private static String[] commandHelp =
	{
		"Changes the settings of Nuker or toggles it.",
		".nuker",
		".nuker mode <normal | id | flat>",
		".nuker id <block id>",
		".nuker name <block name>"
	};
	
	public NukerSettings()
	{
		super("nuker", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(args == null)
		{
			Client.Wurst.moduleManager.getModuleFromClass(Nuker.class).toggleModule();
			Client.Wurst.chat.message("Nuker turned " + (Client.Wurst.moduleManager.getModuleFromClass(Nuker.class).getToggled() == true ? "on" : "off") + ".");
		}
		else if(args[0].toLowerCase().equals("mode".toLowerCase()))
		{//0=normal, 1=id, 2=flat
			if(args[1].toLowerCase().equals("normal".toLowerCase()))
			{
				Client.Wurst.options.nukerMode = 0;
				Nuker.id = 0;
			}else if(args[1].toLowerCase().equals("id".toLowerCase()))
			{
				Client.Wurst.options.nukerMode = 1;
				Nuker.id = 0;
			}else if(args[1].toLowerCase().equals("flat".toLowerCase()))
			{
				Client.Wurst.options.nukerMode = 2;
				Nuker.id = 0;
			}else
			{
				commandError();
				return;
			}
			Client.Wurst.fileManager.saveValues();
			Client.Wurst.chat.message("Nuker mode set to \"" + args[1] + "\".");
		}else if(args[0].equalsIgnoreCase("id") && MiscUtils.isInteger(args[1]))
		{
			if(Client.Wurst.options.nukerMode != 1)
			{
				Client.Wurst.options.nukerMode = 1;
				Client.Wurst.chat.message("Nuker mode set to \"" + args[0] + "\".");
			}
			Nuker.id = Integer.valueOf(args[1]);
			Client.Wurst.fileManager.saveValues();
			Client.Wurst.chat.message("Nuker ID set to " + args[1] + ".");
		}else if(args[0].equalsIgnoreCase("name"))
		{
			if(Client.Wurst.options.nukerMode != 1)
			{
				Client.Wurst.options.nukerMode = 1;
				Client.Wurst.chat.message("Nuker mode set to \"" + args[0] + "\".");
			}
			int newID = Block.getIdFromBlock(Block.getBlockFromName(args[1]));
			if(newID == -1)
			{
				Client.Wurst.chat.message("The block \"" + args[1] + "\" could not be found.");
				return;
			}
			Nuker.id = Integer.valueOf(newID);
			Client.Wurst.fileManager.saveValues();
			Client.Wurst.chat.message("Nuker ID set to " + newID + " (" + args[1] + ").");
		}else
			commandError();
	}
}
