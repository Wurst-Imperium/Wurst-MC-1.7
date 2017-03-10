package tk.wurst_client.module.modules;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class OnlyMobs extends Module
{
	public OnlyMobs()
	{
		super("Only mobs", "Makes other mods only target mobs.", 0, Category.COMBAT);
	}
	
	public void onEnable()
	{
		Client.Wurst.moduleManager.getModuleFromClass(OnlyPlayers.class).setToggled(false);
	}
}
