package tk.wurst_client.utils;

import java.util.ArrayList;

import net.minecraft.block.Block;

public class XRayUtils
{
	public static int xrayOpacity = 110;
	public static boolean isXRay;
	public static ArrayList<Block> xrayBlocks = new ArrayList<Block>();
	
	public static void initXRayBlocks()
	{
		xrayBlocks.add(Block.getBlockFromName("coal_ore"));
		xrayBlocks.add(Block.getBlockFromName("iron_ore"));
		xrayBlocks.add(Block.getBlockFromName("gold_ore"));
		xrayBlocks.add(Block.getBlockFromName("redstone_ore"));
		xrayBlocks.add(Block.getBlockById(74));//Redstone ore glowing
		xrayBlocks.add(Block.getBlockFromName("lapis_ore"));
		xrayBlocks.add(Block.getBlockFromName("diamond_ore"));
		xrayBlocks.add(Block.getBlockFromName("emerald_ore"));
		xrayBlocks.add(Block.getBlockFromName("quartz_ore"));
		xrayBlocks.add(Block.getBlockFromName("clay"));
		xrayBlocks.add(Block.getBlockFromName("glowstone"));
		xrayBlocks.add(Block.getBlockById(8));//Water
		xrayBlocks.add(Block.getBlockById(9));//Water flowing
		xrayBlocks.add(Block.getBlockById(10));//Lava
		xrayBlocks.add(Block.getBlockById(11));//Lava flowing
		xrayBlocks.add(Block.getBlockFromName("crafting_table"));
		xrayBlocks.add(Block.getBlockById(61));//Furnace
		xrayBlocks.add(Block.getBlockById(62));//Furnace on
		xrayBlocks.add(Block.getBlockFromName("torch"));
		xrayBlocks.add(Block.getBlockFromName("ladder"));
		xrayBlocks.add(Block.getBlockFromName("tnt"));
		xrayBlocks.add(Block.getBlockFromName("coal_block"));
		xrayBlocks.add(Block.getBlockFromName("iron_block"));
		xrayBlocks.add(Block.getBlockFromName("gold_block"));
		xrayBlocks.add(Block.getBlockFromName("diamond_block"));
		xrayBlocks.add(Block.getBlockFromName("emerald_block"));
		xrayBlocks.add(Block.getBlockFromName("redstone_block"));
		xrayBlocks.add(Block.getBlockFromName("lapis_block"));
		xrayBlocks.add(Block.getBlockFromName("fire"));
		xrayBlocks.add(Block.getBlockFromName("mossy_cobblestone"));
		xrayBlocks.add(Block.getBlockFromName("mob_spawner"));
		xrayBlocks.add(Block.getBlockFromName("end_portal_frame"));
		xrayBlocks.add(Block.getBlockFromName("enchanting_table"));
		xrayBlocks.add(Block.getBlockFromName("bookshelf"));
		xrayBlocks.add(Block.getBlockFromName("command_block"));
	}
	
	public static boolean isXRayBlock(Block blockToCheck)
	{
		if(xrayBlocks.contains(blockToCheck))
		{
			return true;
		}
		return false;
	}
}
