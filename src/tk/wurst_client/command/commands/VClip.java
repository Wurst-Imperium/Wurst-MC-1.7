package tk.wurst_client.command.commands;

import net.minecraft.client.Minecraft;
import tk.wurst_client.command.Command;
import tk.wurst_client.utils.MiscUtils;

public class VClip extends Command
{
	private static String[] commandHelp =
	{
		"Teleports you up/down. Can glitch you through",
		"floors & ceilings.",
		".vclip <height>"
	};
	
	public VClip()
	{
		super("vclip", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(MiscUtils.isInteger(args[0]))
		{
			Minecraft.getMinecraft().thePlayer.setPosition
			(
				Minecraft.getMinecraft().thePlayer.posX,
				Minecraft.getMinecraft().thePlayer.posY + Integer.valueOf(args[0]),
				Minecraft.getMinecraft().thePlayer.posZ
			);
		}else
			commandError();
	}
}
