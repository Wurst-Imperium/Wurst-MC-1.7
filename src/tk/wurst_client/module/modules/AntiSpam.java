package tk.wurst_client.module.modules;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class AntiSpam extends Module
{
	public AntiSpam()
	{
		super
		(
			"AntiSpam",
			"Blocks chat spam.\n"
			+ "Example:\n"
			+ "Spam!\n"
			+ "Spam!\n"
			+ "Spam!\n"
			+ "Will be changed to:\n"
			+ "Spam! [x3]",
			0,
			Category.CHAT
		);
	}
}
