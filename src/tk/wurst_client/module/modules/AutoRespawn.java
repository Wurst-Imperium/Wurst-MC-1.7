package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class AutoRespawn extends Module
{
	public AutoRespawn() {
		super
		(
			"AutoRespawn",
			"Automatically respawns you whenever you die.",
			0,
			Category.COMBAT
		);
	}
	
	public void onDeath()
	{
		if(this.getToggled())
		{
			Minecraft.getMinecraft().thePlayer.respawnPlayer();
            GuiGameOver.mc.displayGuiScreen((GuiScreen)null);
		}
	}
}
