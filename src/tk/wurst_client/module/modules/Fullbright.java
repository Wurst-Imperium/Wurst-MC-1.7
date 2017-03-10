package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.XRayUtils;

public class Fullbright extends Module
{

	public Fullbright() {
		super
		(
			"Fullbright",
			"Allows you to see in the dark.",
			Keyboard.KEY_C,
			Category.RENDER
		);
	}
	
	public void onUpdate()
	{
		if(this.getToggled() || XRayUtils.isXRay)
		{
			if(Minecraft.getMinecraft().gameSettings.gammaSetting < 16F)
			{
				Minecraft.getMinecraft().gameSettings.gammaSetting += 0.5F;
			}
		}else
			if(Minecraft.getMinecraft().gameSettings.gammaSetting > 0.5F)
			{
				if(Minecraft.getMinecraft().gameSettings.gammaSetting < 1F)
					Minecraft.getMinecraft().gameSettings.gammaSetting = 0.5F;
				else
					Minecraft.getMinecraft().gameSettings.gammaSetting -= 0.5F;
			}
	}
}
