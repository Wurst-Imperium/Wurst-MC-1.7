package tk.wurst_client.module.modules;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.RenderUtils;

public class BaseFinder extends Module
{
	public BaseFinder()
	{
		super
		(
			"BaseFinder",
			"Finds player bases by searching for man-made blocks.\n"
			+ "Good for finding faction bases.",
			0,
			Category.RENDER
		);
		initBlocks();
	}

	private ArrayList<Block> naturalBlocks = new ArrayList<Block>();
	private ArrayList<int[]> matchingBlocks = new ArrayList<int[]>();
	private int range = 64;
	private int maxBlocks = 1024;
	private boolean shouldInform = true;
	
	private void initBlocks()
	{
		this.naturalBlocks.add(Block.getBlockFromName("air"));
		this.naturalBlocks.add(Block.getBlockFromName("stone"));
		this.naturalBlocks.add(Block.getBlockFromName("dirt"));
		this.naturalBlocks.add(Block.getBlockFromName("grass"));
		this.naturalBlocks.add(Block.getBlockFromName("gravel"));
		this.naturalBlocks.add(Block.getBlockFromName("sand"));
		this.naturalBlocks.add(Block.getBlockFromName("clay"));
		this.naturalBlocks.add(Block.getBlockFromName("sandstone"));
		this.naturalBlocks.add(Block.getBlockById(8));
		this.naturalBlocks.add(Block.getBlockById(9));
		this.naturalBlocks.add(Block.getBlockById(10));
		this.naturalBlocks.add(Block.getBlockById(11));
		this.naturalBlocks.add(Block.getBlockFromName("log"));
		this.naturalBlocks.add(Block.getBlockFromName("log2"));
		this.naturalBlocks.add(Block.getBlockFromName("leaves"));
		this.naturalBlocks.add(Block.getBlockFromName("leaves2"));
		this.naturalBlocks.add(Block.getBlockFromName("deadbush"));
		this.naturalBlocks.add(Block.getBlockFromName("iron_ore"));
		this.naturalBlocks.add(Block.getBlockFromName("coal_ore"));
		this.naturalBlocks.add(Block.getBlockFromName("gold_ore"));
		this.naturalBlocks.add(Block.getBlockFromName("diamond_ore"));
		this.naturalBlocks.add(Block.getBlockFromName("redstone_ore"));
		this.naturalBlocks.add(Block.getBlockFromName("lapis_ore"));
		this.naturalBlocks.add(Block.getBlockFromName("bedrock"));
		this.naturalBlocks.add(Block.getBlockFromName("mob_spawner"));
		this.naturalBlocks.add(Block.getBlockFromName("mossy_cobblestone"));
		this.naturalBlocks.add(Block.getBlockFromName("tallgrass"));
		this.naturalBlocks.add(Block.getBlockFromName("yellow_flower"));
		this.naturalBlocks.add(Block.getBlockFromName("red_flower"));
		this.naturalBlocks.add(Block.getBlockFromName("cobweb"));
		this.naturalBlocks.add(Block.getBlockFromName("brown_mushroom"));
		this.naturalBlocks.add(Block.getBlockFromName("red_mushroom"));
		this.naturalBlocks.add(Block.getBlockFromName("snow_layer"));
		this.naturalBlocks.add(Block.getBlockFromName("vine"));
		this.naturalBlocks.add(Block.getBlockFromName("waterlily"));
		this.naturalBlocks.add(Block.getBlockFromName("double_plant"));
		this.naturalBlocks.add(Block.getBlockFromName("hardened_clay"));
		this.naturalBlocks.add(Block.getBlockFromName("red_sandstone"));
		this.naturalBlocks.add(Block.getBlockFromName("ice"));
		this.naturalBlocks.add(Block.getBlockFromName("quartz_ore"));
		this.naturalBlocks.add(Block.getBlockFromName("obsidian"));
	}
	
	public void onEnable()
	{
		shouldInform = true;
	}
	
	public void onRender()
	{
		if(!this.getToggled())
			return;
		for(int[] blockPos : matchingBlocks)
		{
			double renderX = blockPos[0] - RenderManager.renderPosX;
			double renderY = blockPos[1] - RenderManager.renderPosY;
			double renderZ = blockPos[2] - RenderManager.renderPosZ;
			RenderUtils.framelessBlockESP(renderX, renderY, renderZ, new Color(255, 0, 0));
		}
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
					for(int z = range ; z >= -range; z--)
					{
						int posX = (int) (Minecraft.getMinecraft().thePlayer.posX + x);
						int posY = (int) (Minecraft.getMinecraft().thePlayer.posY + y);
						int posZ = (int) (Minecraft.getMinecraft().thePlayer.posZ + z);
						if(!naturalBlocks.contains(Minecraft.getMinecraft().theWorld.getBlock(posX, posY, posZ)))
							matchingBlocks.add(new int[] {posX, posY, posZ});
						if(matchingBlocks.size() >= maxBlocks)
							break;
					}
					if(matchingBlocks.size() >= maxBlocks)
						break;
				}
				if(matchingBlocks.size() >= maxBlocks)
					break;
			}
			if(matchingBlocks.size() >= maxBlocks && shouldInform )
			{
				Client.Wurst.chat.warning(getName() + " found §lA LOT§r of blocks.");
				Client.Wurst.chat.message("To prevent lag, it will only show the first " + maxBlocks + " blocks.");
				shouldInform = false;
			}else if(matchingBlocks.size() < maxBlocks)
				shouldInform = true;
			updateLastMS();
		}
	}
}
