package tk.wurst_client.module.modules;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.entity.RenderManager;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.RenderUtils;

public class Overlay extends Module
{
	public Overlay()
	{
		super
		(
			"Overlay",
			"Renders the Nuker animation when you mine a block.",
			0,
			Category.RENDER
		);
	}
	
	public void onRender()
	{
		if(!this.getToggled() || Minecraft.getMinecraft().objectMouseOver == null)
			return;
		int posX = Minecraft.getMinecraft().objectMouseOver.blockX;
		int posY = Minecraft.getMinecraft().objectMouseOver.blockY;
		int posZ = Minecraft.getMinecraft().objectMouseOver.blockZ;
		Block mouseOverBlock = Minecraft.getMinecraft().theWorld.getBlock(posX, posY, posZ);
		if(Block.getIdFromBlock(mouseOverBlock) != 0)
		{
			double renderX = posX - RenderManager.renderPosX;
			double renderY = posY - RenderManager.renderPosY;
			double renderZ = posZ - RenderManager.renderPosZ;
			RenderUtils.nukerBox(renderX, renderY, renderZ, PlayerControllerMP.curBlockDamageMP);
		}
	}
}
