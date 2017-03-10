package tk.wurst_client.module.modules;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class AntiKnockback extends Module
{
	public AntiKnockback()
	{
		super
		(
			"AntiKnockback",
			"Protects you from getting pushed by players, mobs and\n"
			+ "fluids.",
			0,
			Category.COMBAT
		);
	}
}
