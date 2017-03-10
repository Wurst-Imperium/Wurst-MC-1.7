package tk.wurst_client.command.commands;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;
import tk.wurst_client.utils.MiscUtils;

public class Friends extends Command
{
	private static String[] commandHelp =
		{
			"Adds or removes a friend or lists all friends.",
			".friends <add|remove> <player name>",
			".friends list",
			".friends list <page>"
		};
	
	public Friends()
	{
		super("friends", commandHelp);
	}
	
	private int friendsPerPage = 8;
	
	public void onEnable(String input, String[] args)
	{
		if(args[0].equalsIgnoreCase("list"))
		{
			int totalFriends = Client.Wurst.options.friends.size();
			float pagesF = (float) ((double)totalFriends / (double)friendsPerPage);
			int pages = (int) (Math.round(pagesF) == pagesF ? pagesF : pagesF + 1);
			friendsPerPage = 8;
			if(args.length == 1)
			{
				if(pages <= 1)
				{
					friendsPerPage = totalFriends;
					Client.Wurst.chat.message("Current friends: " + totalFriends);
					for(int i = 0; i < Client.Wurst.options.friends.size() && i < friendsPerPage; i++)
						Client.Wurst.chat.message(Client.Wurst.options.friends.get(i));
				}else
				{
					Client.Wurst.chat.message("Current friends: " + totalFriends);
					Client.Wurst.chat.message("Friends list (page 1/" + pages + "):");
					for(int i = 0; i < Client.Wurst.options.friends.size() && i < friendsPerPage; i++)
						Client.Wurst.chat.message(Client.Wurst.options.friends.get(i));
				}
			}else
			{
					if(MiscUtils.isInteger(args[1]))
					{
						int page = Integer.valueOf(args[1]);
						if(page > pages || page == 0)
						{
							commandError();
							return;
						}
						Client.Wurst.chat.message("Current friends: " + Integer.toString(totalFriends));
						Client.Wurst.chat.message("Friends list (page " + page + "/" + pages + "):");
						int i2 = 0;
						for(int i = 0; i < Client.Wurst.options.friends.size() && i2 < (page - 1) * friendsPerPage + friendsPerPage; i++)
						{
							if(i2 >= (page - 1) * friendsPerPage)
								Client.Wurst.chat.message(Client.Wurst.options.friends.get(i));
							i2++;
						}
						return;
					}
				commandError();
			}
		}else if(args[0].equalsIgnoreCase("add"))
		{
			if(Client.Wurst.options.friends.contains(args[1]))
			{
				Client.Wurst.chat.error("\"" + args[1] + "\" is already in your friends list.");
				return;
			}
			Client.Wurst.options.friends.add(args[1]);
			Client.Wurst.fileManager.saveFriends();
			Client.Wurst.chat.message("Added friend \"" + args[1] + "\".");
		}else if(args[0].equalsIgnoreCase("remove"))
		{
			for(int i = 0; i < Client.Wurst.options.friends.size(); i++)
			{
				if(Client.Wurst.options.friends.get(i).toLowerCase().equals(args[1].toLowerCase()))
				{
					Client.Wurst.options.friends.remove(i);
					Client.Wurst.fileManager.saveFriends();
					Client.Wurst.chat.message("Removed friend \"" + args[1] + "\".");
					return;
				}
			}
			Client.Wurst.chat.error("\"" + args[1] + "\" is not in your friends list.");
		}else
			commandError();
	}
}
