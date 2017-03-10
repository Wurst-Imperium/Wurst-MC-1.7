package tk.wurst_client.module.modules;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Liquids extends Module
{
	public Liquids()
	{
		super
		(
			"Liquids",
			"Allows you to interact with liquid blocks.",
			0,
			Category.BLOCKS
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
			return;
		}
	}
}
