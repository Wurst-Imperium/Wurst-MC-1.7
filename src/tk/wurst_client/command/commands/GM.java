package tk.wurst_client.command.commands;

import net.minecraft.client.Minecraft;
import tk.wurst_client.command.Command;

public class GM extends Command
{
	private static String[] commandHelp =
	{
		"Types \"/gamemode <args>\".",
		".gm <0 | 1 | 2>",
		".gm <s | c | a>",
		".gm <survival | creative | adventure>"
	};
	
	public GM()
	{
		super("gm", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		Minecraft.getMinecraft().thePlayer.sendChatMessage("/gamemode " + args[0]);
	}
}
