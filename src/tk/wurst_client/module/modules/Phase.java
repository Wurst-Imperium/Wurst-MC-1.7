package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Phase extends Module
{
	public Phase()
	{
		super
		(
			"Phase",
			"Exploits a bug in NoCheat+ that allows you to glitch\n"
			+ "through blocks.",
			Keyboard.KEY_J,
			Category.MOVEMENT
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		Minecraft.getMinecraft().thePlayer.noClip = true;
		Minecraft.getMinecraft().thePlayer.fallDistance = 0;
		Minecraft.getMinecraft().thePlayer.onGround = true;
	}
	
	public void onDisable()
	{
		Minecraft.getMinecraft().thePlayer.noClip = false;
	}
}
