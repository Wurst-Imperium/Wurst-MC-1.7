package tk.wurst_client.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.util.StringUtils;
import tk.wurst_client.Client;
import tk.wurst_client.alts.Alt;
import tk.wurst_client.alts.GuiAlts;
import tk.wurst_client.command.Command;

public class AddAlt extends Command
{
	private static String[] commandHelp =
	{
		"Adds a player or all players on a server to your alt",
		"list.",
		".addalt <player>",
		".addalt all"
	};
	
	public AddAlt()
	{
		super("addalt", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(args[0].equals("all"))
		{
			int alts = 0;
			for(int i = 0; i < Minecraft.getMinecraft().getNetHandler().playerInfoList.size(); i++)
			{
				GuiPlayerInfo playerInfo = (GuiPlayerInfo) Minecraft.getMinecraft().getNetHandler().playerInfoList.get(i);
				String name = StringUtils.stripControlCodes(playerInfo.name);
				if
				(
					name.equals(Minecraft.getMinecraft().thePlayer.getCommandSenderName())
					|| name.equals("Alexander01998")
					|| GuiAlts.altList.alts.contains(new Alt(name, null))
				)
					continue;
				GuiAlts.altList.alts.add(new Alt(name, null));
				alts = i + 1;
			}
			if(alts == 1)
				Client.Wurst.chat.message("Added 1 alt to the alt list.");
			else
				Client.Wurst.chat.message("Added " + alts + " alts to the alt list.");
			GuiAlts.altList.sortAlts();
			Client.Wurst.fileManager.saveAlts();
		}else if(!args[0].equals("Alexander01998"))
		{
			GuiAlts.altList.alts.add(new Alt(args[0], null));
			GuiAlts.altList.sortAlts();
			Client.Wurst.fileManager.saveAlts();
			Client.Wurst.chat.message("Added \"" + args[0] + "\" to the alt list.");
		}
	}
}
