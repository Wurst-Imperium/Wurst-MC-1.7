package tk.wurst_client.module.modules;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class AutoSign extends Module
{
	public AutoSign()
	{
		super
		(
			"AutoSign",
			"Instantly writes whatever text you want on every sign\n"
			+ "you place. Once activated, you can write normally on\n"
			+ "one sign to specify the text for all other signs.",
			0,
			Category.BLOCKS
		);
	}
	
	public static String[] signText;
	
	public void onEnable()
	{
		signText = null;
	}
}
