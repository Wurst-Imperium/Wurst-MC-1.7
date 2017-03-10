package tk.wurst_client.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import tk.wurst_client.command.Command;

public class Say extends Command
{
	private static String[] commandHelp =
	{
		"Sends a chat message, even if the message starts",
		"with a dot.",
		".say <message>"
	};
	
	public Say()
	{
		super("say", commandHelp);
	}

	public void onEnable(String input, String[] args)
	{
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(input.substring(4)));
	}
}
