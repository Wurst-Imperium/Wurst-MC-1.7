package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class FastPlace extends Module
{

	public FastPlace() {
		super
		(
			"FastPlace",
			"Allows you to place blocks 5 times faster.\n"
			+ "Tip: This can speed up AutoBuild in YesCheat+ mode.",
			Keyboard.KEY_F,
			Category.BLOCKS
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		Minecraft.getMinecraft().rightClickDelayTimer = 0;
	}
}
