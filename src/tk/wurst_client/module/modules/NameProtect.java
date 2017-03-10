package tk.wurst_client.module.modules;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class NameProtect extends Module
{
	public NameProtect()
	{
		super
		(
			"NameProtect",
			"Hides all player names.\n"
			+ "Some YouTubers like to censor out all names in their\n"
			+ "videos.",
			0,
			Category.RENDER
		);
	}
}
