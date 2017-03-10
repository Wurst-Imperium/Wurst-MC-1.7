package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.RenderUtils;

public class ItemESP extends Module
{
	public ItemESP()
	{
		super
		(
			"ItemESP",
			"Allows you to see items through walls.",
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
			if(entity instanceof EntityItem)
			{
				RenderUtils.entityESPBox((Entity)entity, 2);
			}
		}
	}
}
