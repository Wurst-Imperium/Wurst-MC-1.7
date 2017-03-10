package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class MileyCyrus extends Module
{
	public MileyCyrus()
	{
		super
		(
			"Miley Cyrus",
			"Makes you twerk all the time.\n"
			+ "Miley Cyrus used this.",
			0,
			Category.MISC
		);
	}
	
	private boolean shouldSneak = true;
	private float speed = 5;
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		updateMS();
		if(hasTimePassedS(speed))
		{
			Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed = shouldSneak;
			shouldSneak = !shouldSneak;
			updateLastMS();
		}
	}
	
	public void onDisable()
	{
		Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed = false;
	}
}
