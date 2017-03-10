package tk.wurst_client.module.modules;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class OnlyPlayers extends Module
{
	public OnlyPlayers()
	{
		super("Only players", "Makes other mods only target players.", 0, Category.COMBAT);
	}
	
	public void onEnable()
	{
		Client.Wurst.moduleManager.getModuleFromClass(OnlyMobs.class).setToggled(false);
	}
}
