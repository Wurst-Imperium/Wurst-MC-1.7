package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Home extends Module
{
	public Home()
	{
		super
		(
			"/home",
			"Types \"/home\" instantly.",
			Keyboard.KEY_H,
			Category.CHAT
		);
	}
	
	private int disableTimer;
	
	public void onUpdate()
	{
		if(this.getToggled())
		{
			if(disableTimer == 4)
				this.setToggled(false);
			else if(disableTimer == 0)
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/home");
			disableTimer++;
		}
	}
	
	public void onEnable()
	{
		disableTimer = 0;
	}
	
	public void onReceivedMessage(String message)
	{
		if(!this.getToggled() || message.startsWith("§c[§6" + Client.Wurst.CLIENT_NAME + "§c]§f "))
			return;
		if(message.toLowerCase().contains("/help") || message.toLowerCase().contains("permission"))
		{
			Client.Wurst.chat.error("This server doesn't have /home.");
		}
	}
}
