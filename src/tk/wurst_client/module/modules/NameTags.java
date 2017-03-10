package tk.wurst_client.module.modules;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class NameTags extends Module
{
	public NameTags()
	{
		super
		(
			"NameTags",
			"Changes the scale of the nametags so you can always\n"
			+ "read them.",
			0,
			Category.RENDER
		);
	}
}
