package tk.wurst_client.module.modules;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.RenderUtils;

public class ChestESP extends Module
{
	public ChestESP()
	{
		super
		(
			"ChestESP",
			"Allows you to see chests through walls.\n"
			+ "Tip: This works with the piston crates on HiveMC.",
			0,
			Category.RENDER
		);
	}

	private int range = 64;
	private int maxChests = 1000;
	public boolean shouldInform = true;
	private ArrayList<int[]> matchingBlocks = new ArrayList<int[]>();
	
	public void onRender()
	{
		if(!this.getToggled())
			return;
		int i = 0;
		for(Object o : Minecraft.getMinecraft().theWorld.field_147482_g)
		{
			if(i >= maxChests)
				break;
			if(o instanceof TileEntityChest)
			{
				i++;
				double renderX = ((TileEntityChest) o).field_145851_c - RenderManager.renderPosX;
				double renderY = ((TileEntityChest) o).field_145848_d - RenderManager.renderPosY;
				double renderZ = ((TileEntityChest) o).field_145849_e - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}else if(o instanceof TileEntityEnderChest)
			{
				i++;
				double renderX = ((TileEntityEnderChest) o).field_145851_c - RenderManager.renderPosX;
				double renderY = ((TileEntityEnderChest) o).field_145848_d - RenderManager.renderPosY;
				double renderZ = ((TileEntityEnderChest) o).field_145849_e - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}
		}
		for(Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
		{
			if(i >= maxChests)
				break;
			if(o instanceof EntityMinecartChest)
			{
				i++;
				double renderX = ((EntityMinecartChest) o).posX - 0.5 - RenderManager.renderPosX;
				double renderY = ((EntityMinecartChest) o).posY - 0.35 - RenderManager.renderPosY;
				double renderZ = ((EntityMinecartChest) o).posZ - 0.5 - RenderManager.renderPosZ;
				RenderUtils.blockESPBox(renderX, renderY, renderZ);
			}
		}
		for(int[] blockPos : matchingBlocks)
		{
			if(i >= maxChests)
				break;
			i++;
			Block block = Minecraft.getMinecraft().theWorld.getBlock(blockPos[0], blockPos[1], blockPos[2]);
			double renderX = blockPos[0] - RenderManager.renderPosX;
			double renderY = blockPos[1] - RenderManager.renderPosY;
			double renderZ = blockPos[2] - RenderManager.renderPosZ;
			RenderUtils.blockESPBox(renderX, renderY, renderZ);
		}
		if(i >= maxChests && shouldInform)
		{
			Client.Wurst.chat.warning(getName() + " found §lA LOT§r of chests.");
			Client.Wurst.chat.message("To prevent lag, it will only show the first " + maxChests + " chests.");
			shouldInform = false;
		}else if(i < maxChests)
			shouldInform = true;
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		updateMS();
		if(hasTimePassedM(3000))
		{
			matchingBlocks.clear();
			for(int y = range; y >= -range; y--)
			{
				for(int x = range; x >= -range; x--)
				{
					for(int z = range; z >= -range; z--)
					{
						int posX = (int) (Minecraft.getMinecraft().thePlayer.posX + x);
						int posY = (int) (Minecraft.getMinecraft().thePlayer.posY + y);
						int posZ = (int) (Minecraft.getMinecraft().thePlayer.posZ + z);
						Block block = Minecraft.getMinecraft().theWorld.getBlock(posX, posY, posZ);
						if(Block.getIdFromBlock(block) == 33 &&
						(
							Minecraft.getMinecraft().theWorld.getBlockMetadata(posX, posY, posZ) == 6
							|| Minecraft.getMinecraft().theWorld.getBlockMetadata(posX, posY, posZ) == 7
							|| Minecraft.getMinecraft().theWorld.getBlockMetadata(posX, posY, posZ) == 15
						))
							matchingBlocks.add(new int[]{posX, posY, posZ});
					}
				}
			}
			updateLastMS();
		}
	}
}
