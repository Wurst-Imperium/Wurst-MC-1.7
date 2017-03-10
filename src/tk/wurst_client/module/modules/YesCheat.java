package tk.wurst_client.module.modules;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class YesCheat extends Module
{
	public YesCheat()
	{
		super
		(
			"YesCheat+",
			"Makes other mods bypass NoCheat+ or disables them if\n"
			+ "they can't.",
			0,
			Category.MISC
		);
	}
}
