package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class AutoSwitch extends Module
{
	public AutoSwitch()
	{
		super
		(
			"AutoSwitch",
			"Switches the item in your hand all the time.\n"
			+ "Tip: Use this in combination with BuildRandom while\n"
			+ "having a lot of different colored wool blocks in your\n"
			+ "hotbar.",
			0,
			Category.MISC
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(Minecraft.getMinecraft().thePlayer.inventory.currentItem == 8)
			Minecraft.getMinecraft().thePlayer.inventory.currentItem = 0;
		else
			Minecraft.getMinecraft().thePlayer.inventory.currentItem++;
	}
}
