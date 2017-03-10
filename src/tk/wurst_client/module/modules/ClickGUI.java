package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;

import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;
import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class ClickGUI extends Module
{

	public ClickGUI() {
		super
		(
			"ClickGUI",
			"",
			Keyboard.KEY_LCONTROL,
			Category.HIDDEN
		);
	}
	
	public void onToggle()
	{
		if(!(Minecraft.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen))
			Minecraft.getMinecraft().displayGuiScreen(new GuiManagerDisplayScreen(Client.Wurst.guiManager));
	}
	
	public void onUpdate()
	{
		Client.Wurst.guiManager.update();
	}
}
