package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Spider extends Module
{
	public Spider()
	{
		super
		(
			"Spider",
			"Allows you to climb up walls like a spider.",
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
		if(Minecraft.getMinecraft().thePlayer.isCollidedHorizontally)
		{
			Minecraft.getMinecraft().thePlayer.motionY = 0.2;
		}
	}
}
