package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class AutoSprint extends Module
{

	public AutoSprint()
	{
		super
		(
			"AutoSprint",
			"Makes you sprint whenever you walk.",
			0,
			Category.MOVEMENT
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if
		(
			!Minecraft.getMinecraft().thePlayer.isCollidedHorizontally
			&& Minecraft.getMinecraft().thePlayer.moveForward > 0
			&& !Minecraft.getMinecraft().thePlayer.isSneaking()
		)
			Minecraft.getMinecraft().thePlayer.setSprinting(true);
	}
}
