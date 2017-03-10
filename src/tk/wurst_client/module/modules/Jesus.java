package tk.wurst_client.module.modules;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Jesus extends Module
{
	public Jesus()
	{
		super
		(
			"Jesus",
			"Allows you to walk on water.\n"
			+ "The real Jesus used this hack ~2000 years ago.\n"
			+ "The Christians will get mad at me for this joke, but\n"
			+ "who cares?",
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
			return;
		}
	}
}
