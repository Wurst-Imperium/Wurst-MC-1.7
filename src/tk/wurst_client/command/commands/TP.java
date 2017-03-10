package tk.wurst_client.command.commands;

import net.minecraft.client.Minecraft;
import tk.wurst_client.command.Command;
import tk.wurst_client.utils.MiscUtils;

public class TP extends Command
{
	private static String[] commandHelp =
	{
		"Teleports you up to 100 blocks away.",
		"Only works on vanilla servers.",
		".tp <x> <y> <z>"
	};
	
	public TP()
	{
		super("tp", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(MiscUtils.isInteger(args[0]) && MiscUtils.isInteger(args[1]) && MiscUtils.isInteger(args[2]))
		{
			Minecraft.getMinecraft().thePlayer.setPosition
			(
				Integer.valueOf(args[0]),
				Integer.valueOf(args[1]),
				Integer.valueOf(args[2])
			);
		}else
			commandError();
	}
}
