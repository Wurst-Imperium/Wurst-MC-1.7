package tk.wurst_client.module.modules;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class HighJump extends Module
{
	public HighJump()
	{
		super
		(
			"HighJump",
			"Makes you jump six times higher.",
			0,
			Category.MOVEMENT
		);
	}
	
	public static double jumpHeight = 0.41999998688697815D * 6;
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
		{
			noCheatMessage();
			this.setToggled(false);
		}
	}
}
