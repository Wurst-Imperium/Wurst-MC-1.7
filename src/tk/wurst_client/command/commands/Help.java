package tk.wurst_client.command.commands;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;
import tk.wurst_client.utils.MiscUtils;

public class Help extends Command
{
	private static String[] commandHelp =
	{
		"Shows the command list or helps you with a command.",
		".help",
		".help <page>",
		".help <command>"
	};
	
	public Help()
	{
		super("help", commandHelp);
	}
	
	private int commandsPerPage = 8;
	
	public void onEnable(String input, String[] args)
	{
		commandsPerPage = 8;
		float pagesF = (float)Client.Wurst.commandManager.activeCommands.size() / commandsPerPage;
		int pages = (int) (Math.floor(pagesF) == pagesF ? pagesF : pagesF + 1);
		if(args == null)
		{
			if(pages <= 1)
			{
				commandsPerPage = Client.Wurst.commandManager.activeCommands.size();
				Client.Wurst.chat.message("Available commands: " + Integer.toString(Client.Wurst.commandManager.activeCommands.size()));
				for(int i = 0; i < commandsPerPage; i++)
					Client.Wurst.chat.message("." + Client.Wurst.commandManager.activeCommands.get(i).getName());
			}else
			{
				Client.Wurst.chat.message("Available commands: " + Integer.toString(Client.Wurst.commandManager.activeCommands.size()));
				Client.Wurst.chat.message("Command list (page 1/" + pages + "):");
				for(int i = 0; i < commandsPerPage; i++)
					Client.Wurst.chat.message("." + Client.Wurst.commandManager.activeCommands.get(i).getName());
			}
		}else
		{
			for(int i = 0; i < Client.Wurst.commandManager.activeCommands.size(); i++)
			{
				if(Client.Wurst.commandManager.activeCommands.get(i).getName().equals(args[0]))
				{
					Client.Wurst.chat.message("Available help for \"." + args[0] + "\":");
					for(int i2 = 0; i2 < Client.Wurst.commandManager.activeCommands.get(i).getHelp().length; i2++)
						Client.Wurst.chat.message(Client.Wurst.commandManager.activeCommands.get(i).getHelp()[i2]);
					return;
				}else if(MiscUtils.isInteger(args[0]))
				{
					int page = Integer.valueOf(args[0]);
					if(page > pages || page == 0)
					{
						commandError();
						return;
					}
					Client.Wurst.chat.message("Available commands: " + Integer.toString(Client.Wurst.commandManager.activeCommands.size()));
					Client.Wurst.chat.message("Command list (page " + page + "/" + pages + "):");
					for(int i2 = (page - 1) * commandsPerPage; i2 < (page - 1) * commandsPerPage + commandsPerPage && i2 < Client.Wurst.commandManager.activeCommands.size(); i2++)
						Client.Wurst.chat.message("." + Client.Wurst.commandManager.activeCommands.get(i2).getName());
					return;
				}
			}
			commandError();
		}
	}
}
