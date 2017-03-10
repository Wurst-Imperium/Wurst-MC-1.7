package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class DotAnnoy extends Module
{
	public DotAnnoy()
	{
		super
		(
			"Annoy",
			"",
			0,
			Category.HIDDEN
		);
	}
	
	private static boolean toggled;
	private static String name;
	
	public String getRenderName()
	{
		if(name != null)
			return "Annoying " + name;
		else
			return "Annoy";
	}
	
	public static void onToggledByCommand(String name)
	{
		if(name == null)
		{
			if(toggled)
				toggled = false;
			else
				Client.Wurst.chat.message("\"Annoy\" is already turned off. Type \".annoy <name>\" to annoy someone.");
			return;
		}
		toggled = !toggled;
		DotAnnoy.name = name;
	}
	
	public void onEnable()
	{
		if(name == null)
		{
			Client.Wurst.chat.message("\"Annoy\" is already turned off. Type \".annoy <name>\" to annoy someone.");
			toggled = false;
			return;
		}
		Client.Wurst.chat.message("Now annoying " + name + ".");
		if(name.equals(Minecraft.getMinecraft().thePlayer.getCommandSenderName()))
			Client.Wurst.chat.warning("Annoying yourself is a bad idea!");
	}
	
	public void onUpdate()
	{
		if(this.getToggled() != toggled)
			this.setToggled(toggled);
	}
	
	public void onReceivedMessage(String message)
	{
		if(!this.getToggled() || message.startsWith("§c[§6" + Client.Wurst.CLIENT_NAME + "§c]§f "))
			return;
		if(message.startsWith("<" + name + ">") || message.contains(name + ">"))
		{
			String repeatMessage = message.substring(message.indexOf(">") + 1);
			Minecraft.getMinecraft().thePlayer.sendChatMessage(repeatMessage);
		}else if(message.contains("] " + name + ":") || message.contains("]" + name + ":"))
		{
			String repeatMessage = message.substring(message.indexOf(":") + 1);
			Minecraft.getMinecraft().thePlayer.sendChatMessage(repeatMessage);
		}
	}
	
	public void onDisable()
	{
		if(name != null)
		{
			Client.Wurst.chat.message("No longer annoying " + name + ".");
			name = null;
		}
		toggled = false;
	}
}
