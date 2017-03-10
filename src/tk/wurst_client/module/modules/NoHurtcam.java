package tk.wurst_client.module.modules;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class NoHurtcam extends Module
{
	public NoHurtcam()
	{
		super
		(
			"NoHurtcam",
			"Disables the annoying effect when you get hurt.",
			0,
			Category.RENDER
		);
	}
}
