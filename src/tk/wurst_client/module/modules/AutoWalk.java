package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class AutoWalk extends Module
{
	public AutoWalk()
	{
		super
		(
			"AutoWalk",
			"Automatically walks all the time.",
			0,
			Category.MOVEMENT
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(!Minecraft.getMinecraft().gameSettings.keyBindForward.pressed)
			Minecraft.getMinecraft().gameSettings.keyBindForward.pressed = true;
	}
	
	public void onDisable()
	{
		Minecraft.getMinecraft().gameSettings.keyBindForward.pressed = false;
	}
}
