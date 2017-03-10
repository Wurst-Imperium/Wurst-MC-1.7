package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class DotDrop extends Module
{
	public DotDrop()
	{
		super
		(
			"Drop",
			"",
			0,
			Category.HIDDEN
		);
	}
	
	private int timer;
	private int counter;
	
	public void onEnable()
	{
		timer = 0;
		counter = 9;
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
		{
			timer++;
			if(timer >= 5)
			{
				Minecraft.getMinecraft().playerController.windowClick(0, counter, 1, 4, Minecraft.getMinecraft().thePlayer);
				counter++;
				timer = 0;
				if(counter >= 45)
					this.setToggled(false);
			}
		}else
		{
			for(int i = 9; i < 45; i++)
				Minecraft.getMinecraft().playerController.windowClick(0, i, 1, 4, Minecraft.getMinecraft().thePlayer);
			this.setToggled(false);
		}
	}
}
