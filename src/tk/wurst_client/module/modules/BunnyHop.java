package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class BunnyHop extends Module
{
	public BunnyHop()
	{
		super
		(
			"BunnyHop",
			"Automatically jumps whenever you walk.\n"
			+ "Tip: Jumping while sprintig is a faster way to move.",
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
			(Minecraft.getMinecraft().thePlayer.moveForward != 0 || Minecraft.getMinecraft().thePlayer.moveStrafing != 0)
			&& !Minecraft.getMinecraft().thePlayer.isSneaking()
			&& Minecraft.getMinecraft().thePlayer.onGround
		)
			Minecraft.getMinecraft().thePlayer.jump();
	}
}
