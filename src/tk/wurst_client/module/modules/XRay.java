package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.XRayUtils;

public class XRay extends Module
{
	public XRay()
	{
		super
		(
			"X-Ray",
			"Allows you to see ores and caves through walls.",
			Keyboard.KEY_X,
			Category.RENDER
		);
	}
	
	public String getRenderName()
	{
		return "X-Wurst";
	}
	
	public void onEnable()
	{
		XRayUtils.isXRay = true;
		Minecraft.getMinecraft().renderGlobal.loadRenderers();
	}
	
	public void onDisable()
	{
		XRayUtils.isXRay = false;
		Minecraft.getMinecraft().renderGlobal.loadRenderers();
	}
}
