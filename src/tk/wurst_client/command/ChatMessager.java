package tk.wurst_client.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import tk.wurst_client.Client;

public class ChatMessager
{
	public void message(String message)
	{
		Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(new ChatComponentText("§c[§6" + Client.Wurst.CLIENT_NAME + "§c]§f " + message));
	}
	
	public void info(String message)
	{
		message("§8[§7§lINFO§8]§f " + message);
	}
	
	public void debug(String message)
	{
		message("§8[§7§lDEBUG-INFO§8]§f " + message);
	}
	
	public void warning(String message)
	{
		message("§c[§6§lWARNING§c]§f " + message);
	}
	
	public void error(String message)
	{
		message("§c[§4§lERROR§c]§f " + message);
	}
	
	public void success(String message)
	{
		message("§a[§2§lSUCCESS§a]§f " + message);
	}
	
	public void failure(String message)
	{
		message("§c[§4§lFAILURE§c]§f " + message);
	}
}
