package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.RenderUtils;

public class PlayerESP extends Module
{
	public PlayerESP()
	{
		super
		(
			"PlayerESP",
			"Allows you to see players through walls.",
			0,
			Category.RENDER
		);
	}
	
	public void onRender()
	{
		if(!this.getToggled() || Client.Wurst.moduleManager.getModuleFromClass(ArenaBrawl.class).getToggled())
			return;
		for(Object entity : Minecraft.getMinecraft().theWorld.loadedEntityList)
		{
			if(entity instanceof EntityPlayer && !((Entity) entity).getCommandSenderName().equals(Minecraft.getMinecraft().getSession().getUsername()))
			{
				RenderUtils.entityESPBox((Entity)entity, (Client.Wurst.options.friends.contains(((EntityPlayer) entity).getCommandSenderName()) ? 1 : 0));
			}
		}
	}
}
