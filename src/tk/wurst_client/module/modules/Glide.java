package tk.wurst_client.module.modules;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Glide extends Module
{
	public Glide()
	{
		super
		(
			"Glide",
			"Makes you fall like if you had a hang glider.",
			0,
			Category.MOVEMENT
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
		{
			noCheatMessage();
			this.setToggled(false);
		}else
		if (Minecraft.getMinecraft().thePlayer.motionY < 0 && Minecraft.getMinecraft().thePlayer.isAirBorne && !Minecraft.getMinecraft().thePlayer.isInWater() && !Minecraft.getMinecraft().thePlayer.isOnLadder() && !Minecraft.getMinecraft().thePlayer.isInsideOfMaterial(Material.lava))
		{
			Minecraft.getMinecraft().thePlayer.motionY = -0.125f;
			Minecraft.getMinecraft().thePlayer.jumpMovementFactor *= 1.21337f;
		}
	}
}
