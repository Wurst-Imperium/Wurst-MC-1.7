package tk.wurst_client.module.modules;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class AntiBlind extends Module
{
	public AntiBlind()
	{
		super
		(
			"AntiBlind",
			"Blocks blindness and nausea.",
			0,
			Category.RENDER
		);
	}
}
