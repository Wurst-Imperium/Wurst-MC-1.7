package tk.wurst_client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;

import tk.wurst_client.Client;
import tk.wurst_client.font.Fonts;
import tk.wurst_client.module.Module;
import tk.wurst_client.module.modules.ClickGUI;

public class UIRenderer
{
	private static void renderArrayList()
	{
		if(Client.Wurst.options.arrayListMode == 2)
			return;
		int arrayListLength = 0;
		for(Module arrayModule : Client.Wurst.moduleManager.activeModules)
		{
			if(arrayModule instanceof ClickGUI)
				continue;
			if(arrayModule.getToggled())
			{
				arrayListLength++;
			}
		}
		int yCount = 19;
		ScaledResolution sr = new ScaledResolution
		(
			Minecraft.getMinecraft().gameSettings,
			Minecraft.getMinecraft().displayWidth,
			Minecraft.getMinecraft().displayHeight
		);
		if(yCount + arrayListLength * 9 > sr.getScaledHeight() || Client.Wurst.options.arrayListMode == 1)
		{
			String tooManyMods =  arrayListLength + " mods active";
			Fonts.segoe18.drawString(tooManyMods, 3, yCount + 1, 0xFF000000);
			Fonts.segoe18.drawString(tooManyMods, 2, yCount, 0xFFFFFFFF);
		}else
		{
			for(Module arrayModule : Client.Wurst.moduleManager.activeModules)
			{
				if(arrayModule instanceof ClickGUI)
					continue;
				if(arrayModule.getToggled())
				{
					Fonts.segoe18.drawString(arrayModule.getRenderName(), 3, yCount + 1, 0xFF000000);
					Fonts.segoe18.drawString(arrayModule.getRenderName(), 2, yCount, 0xFFFFFFFF);
					yCount += 9;
				}
			}
		}
	}
	
	public static void renderUI()
	{
		Fonts.segoe22.drawString("v" + Client.Wurst.CLIENT_VERSION, 74, 4, 0xFF000000);
		renderArrayList();
	}
	
	public static void renderPinnedFrames()
	{
		for(Frame moduleFrame : Client.Wurst.guiManager.getFrames())
		{
			if(moduleFrame.isPinned() && !(Minecraft.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen))
				moduleFrame.render();
		}
	}
}
