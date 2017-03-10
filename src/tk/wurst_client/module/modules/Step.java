package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Step extends Module
{	
	public Step() {
		super
		(
			"Step",
			"Allows you to step up full blocks.",
			0,
			Category.MOVEMENT
		);
	}
	
	public void onUpdate()
	{
		if(this.getToggled())
		{
			if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
			{
				Minecraft.getMinecraft().thePlayer.stepHeight = 0.5F;
				if(Minecraft.getMinecraft().thePlayer.isCollidedHorizontally && Minecraft.getMinecraft().thePlayer.onGround)
				{
					Minecraft.getMinecraft().thePlayer.jump();
				}
			}else
			Minecraft.getMinecraft().thePlayer.stepHeight = 1.0F;
		}
	}
	
	public void onDisable()
	{
		Minecraft.getMinecraft().thePlayer.stepHeight = 0.5F;
	}
}
