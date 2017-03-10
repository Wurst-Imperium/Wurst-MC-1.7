package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.util.StringUtils;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class MassTPA extends Module
{
	public MassTPA()
	{
		super
		(
			"MassTPA",
			"Sends a TPA requests to all players.\n"
			+ "Stops if someone accepts.",
			0,
			Category.CHAT
		);
	}
	
	private float speed = 1F;
	private int i;
	
	public void onEnable()
	{
		i = 0;
	}
	
	public void onReceivedMessage(String message)
	{
		if(!this.getToggled() || message.startsWith("§c[§6" + Client.Wurst.CLIENT_NAME + "§c]§f "))
			return;
		if(message.toLowerCase().contains("/help") || message.toLowerCase().contains("permission"))
		{
			Client.Wurst.chat.message("§4§lERROR:§f This server doesn't have TPA.");
			this.setToggled(false);
		}else if((message.toLowerCase().contains("accepted") && message.toLowerCase().contains("request")) || (message.toLowerCase().contains("akzeptiert") && message.toLowerCase().contains("anfrage")))
		{
			Client.Wurst.chat.message("Someone accepted your TPA request. Stopping.");
			this.setToggled(false);
		}
	}
	
	public void onUpdate()
	{
		if(this.getToggled())
		{
			updateMS();
			if(hasTimePassedS(speed))
			{
				GuiPlayerInfo playerInfo = (GuiPlayerInfo) Minecraft.getMinecraft().getNetHandler().playerInfoList.get(i);
				String name = StringUtils.stripControlCodes(playerInfo.name);
				if(!name.equals(Minecraft.getMinecraft().thePlayer.getCommandSenderName()))
					Minecraft.getMinecraft().thePlayer.sendChatMessage("/tpa " + name);
				updateLastMS();
				if(i < Minecraft.getMinecraft().getNetHandler().playerInfoList.size() - 1)
					i++;
				else
					this.setToggled(false);
			}
		}
	}
}
