package tk.wurst_client.module.modules;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Panic extends Module
{
	public Panic()
	{
		super
		(
			"Panic",
			"Instantly turns off all enabled mods.\n"
			+ "Be careful with this!",
			0,
			Category.MISC
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		for(int i = 0; i < Client.Wurst.moduleManager.activeModules.size(); i++)
		{
			if(!Client.Wurst.moduleManager.activeModules.get(i).equals(this) && Client.Wurst.moduleManager.activeModules.get(i).getCategory() != Category.HIDDEN && Client.Wurst.moduleManager.activeModules.get(i).getToggled())
				Client.Wurst.moduleManager.activeModules.get(i).setToggled(false);
		}
		this.setToggled(false);
	}
}
