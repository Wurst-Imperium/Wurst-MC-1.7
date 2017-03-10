package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Dolphin extends Module
{
	public Dolphin()
	{
		super
		(
			"Dolphin",
			"Automatically swims like a dolphin.",
			0,
			Category.MOVEMENT
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if (Minecraft.getMinecraft().thePlayer.isInWater() && !Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed)
		{
			Minecraft.getMinecraft().thePlayer.motionY += 0.04;
		}
	}
}
