package tk.wurst_client.module.modules;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class HealthTags extends Module
{
	public HealthTags()
	{
		super
		(
			"HealthTags",
			"Adds the health of players to their nametags.",
			0,
			Category.RENDER
		);
	}
}
