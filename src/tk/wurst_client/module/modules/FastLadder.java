package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class FastLadder extends Module
{
	public FastLadder()
	{
		super
		(
			"FastLadder",
			"Allows you to climb up ladders twice as fast.",
			0,
			Category.MOVEMENT
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(Minecraft.getMinecraft().thePlayer.isOnLadder() && Minecraft.getMinecraft().thePlayer.isCollidedHorizontally)
		{
			Minecraft.getMinecraft().thePlayer.motionY = 0.25;
		}
	}
}
