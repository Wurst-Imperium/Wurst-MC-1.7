package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.util.JsonException;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class LSD extends Module
{
	public LSD()
	{
		super
		(
			"LSD",
			"Thousands of colors!\n"
			+ "Just for fun. Doesn't have any use.",
			0,
			Category.RENDER
		);
	}
	
	private static float speed = 2;
	private static long currentMS = 0L;
	private static long lastMS = -1L;
	private static int[] random = {256, 256, 256};
	private int count = 0;
	
	public void onEnable()
	{
        try
        {
			Minecraft.getMinecraft().entityRenderer.theShaderGroup = new ShaderGroup(Minecraft.getMinecraft().getResourceManager(), Minecraft.getMinecraft().getFramebuffer(), new ResourceLocation("shaders/post/wobble.json"));
			Minecraft.getMinecraft().entityRenderer.theShaderGroup.createBindFramebuffers(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		}catch(JsonException e)
		{
			e.printStackTrace();
			Tessellator.shouldRenderLSD = true;
		}
	}
	
	public void onToggle()
	{
		if(!OpenGlHelper.shadersSupported)
			Minecraft.getMinecraft().renderGlobal.loadRenderers();
	}
	
	public void onUpdate()
	{
		if(!this.getToggled() && Tessellator.shouldRenderLSD)
		{
			this.setToggled(false);
			return;
		}
		if(!this.getToggled())
			return;
		if(!OpenGlHelper.shadersSupported)
			Minecraft.getMinecraft().thePlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 10801220));
		Minecraft.getMinecraft().gameSettings.smoothCamera = true;
	}
	
	public void onDisable()
	{
		Minecraft.getMinecraft().thePlayer.removePotionEffect(Potion.confusion.getId());
		Minecraft.getMinecraft().gameSettings.smoothCamera = false;
		Minecraft.getMinecraft().entityRenderer.deactivateShader();
		Tessellator.shouldRenderLSD = false;
	}
	
	public static int randomColor(int number, int par)
	{
		currentMS = System.currentTimeMillis();
		if(currentMS >= lastMS + (long)(1000 / speed))
		{
			random[0] = 256;
			random[1] = 256;
			random[2] = 256;
			lastMS = System.currentTimeMillis();
		}
		while(!(random[number] <=255 && random[number] >= 0 && random[0] + random[1] + random[2] >= 255))
			random[number] = par + (int) (Math.random() * 510) - 255;
		return random[number];
	}
}
