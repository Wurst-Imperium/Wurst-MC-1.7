package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Jetpack extends Module
{
	public Jetpack()
	{
		super
		(
			"Jetpack",
			"Allows you to jump in mid-air.\n"
			+ "Looks like if you had a jetpack.",
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
		if(Minecraft.getMinecraft().gameSettings.keyBindJump.pressed)
		{
			Minecraft.getMinecraft().thePlayer.jump();
		}
	}
}
