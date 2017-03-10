package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.RenderUtils;

public class MobESP extends Module
{
	public MobESP()
	{
		super
		(
			"MobESP",
			"Allows you to see mobs through walls.",
			0,
			Category.RENDER
		);
	}
	
	public void onRender()
	{
		if(!this.getToggled())
			return;
		for(Object entity : Minecraft.getMinecraft().theWorld.loadedEntityList)
		{
			if(entity instanceof EntityLiving)
			{
				RenderUtils.entityESPBox((Entity)entity, 0);
			}
		}
	}
}
