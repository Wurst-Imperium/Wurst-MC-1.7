package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class NoWeb extends Module
{
	public NoWeb()
	{
		super
		(
			"NoWeb",
			"Prevents you from getting slowed down in webs.\n"
			+ "Note: This has nothing to do with websites.",
			0,
			Category.MOVEMENT
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		Minecraft.getMinecraft().thePlayer.isInWeb = false;
	}
}
