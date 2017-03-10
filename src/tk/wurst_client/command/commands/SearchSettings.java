package tk.wurst_client.command.commands;

import net.minecraft.block.Block;
import tk.wurst_client.Client;
import tk.wurst_client.command.Command;
import tk.wurst_client.module.modules.Search;
import tk.wurst_client.utils.MiscUtils;

public class SearchSettings extends Command
{

	private static String[] commandHelp =
	{
		"Changes the settings of Search or toggles it.",
		".search",
		".search id <block id>",
		".search name <block name>"
	};
	
	public SearchSettings()
	{
		super("search", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(args == null)
		{
			Client.Wurst.moduleManager.getModuleFromClass(Search.class).toggleModule();
			Client.Wurst.chat.message("Search turned " + (Client.Wurst.moduleManager.getModuleFromClass(Search.class).getToggled() == true ? "on" : "off") + ".");
		}else if(args[0].toLowerCase().equals("id"))
		{
			if(MiscUtils.isInteger(args[1]))
			{
				Client.Wurst.options.searchID = Integer.valueOf(args[1]);
			}else
			{
				commandError();
				return;
			}
			Client.Wurst.fileManager.saveValues();
			Search.shouldInform = true;
			Client.Wurst.chat.message("Search ID set to " + args[1] + ".");
		}else if(args[0].equalsIgnoreCase("name"))
		{
			int newID = Block.getIdFromBlock(Block.getBlockFromName(args[1]));
			if(newID == -1)
			{
				Client.Wurst.chat.message("The block \"" + args[1] + "\" could not be found.");
				return;
			}
			Client.Wurst.options.searchID = Integer.valueOf(newID);
			Client.Wurst.fileManager.saveValues();
			Search.shouldInform = true;
			Client.Wurst.chat.message("Search ID set to " + newID + " (" + args[1] + ").");
		}else
			commandError();
	}
}
