package tk.wurst_client.module.modules;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class AutoTool extends Module
{
	public AutoTool()
	{
		super
		(
			"AutoTool",
			"Automatically uses the best tool in your hotbar to\n"
			+ "mine blocks. Tip: This works with Nuker.",
			0,
			Category.BLOCKS
		);
	}
	
	private boolean isActive = false;
	private int oldSlot;
	
	public void onLeftClick()
	{
		if
		(
			!this.getToggled()
			|| Minecraft.getMinecraft().objectMouseOver == null
		)
			return;
		if
		(
			Minecraft.getMinecraft().theWorld.getBlock
			(
				Minecraft.getMinecraft().objectMouseOver.blockX,
				Minecraft.getMinecraft().objectMouseOver.blockY,
				Minecraft.getMinecraft().objectMouseOver.blockZ
			).getMaterial() != Material.air
		)
		{
			isActive = true;
			oldSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
			setSlot
			(
				Minecraft.getMinecraft().objectMouseOver.blockX,
				Minecraft.getMinecraft().objectMouseOver.blockY,
				Minecraft.getMinecraft().objectMouseOver.blockZ
			);
		}
	}
	
	public static void setSlot(int x, int y, int z)
	{
		float bestSpeed = 1F;
		int bestSlot = -1;
		Block block = Minecraft.getMinecraft().theWorld.getBlock
		(
			x,
			y,
			z
		);
		for(int i = 0; i < 9; i++)
		{
			ItemStack item = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
			if(item == null)
				continue;
			float speed = item.func_150997_a(block);
			if(speed > bestSpeed)
			{
				bestSpeed = speed;
				bestSlot = i;
			}
		}
		if(bestSlot != -1)
			Minecraft.getMinecraft().thePlayer.inventory.currentItem = bestSlot;
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(!Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed && isActive)
			onDisable();
		else if
		(
			this.getToggled()
			&& isActive
			&& Minecraft.getMinecraft().objectMouseOver != null
			&& Minecraft.getMinecraft().theWorld.getBlock(Minecraft.getMinecraft().objectMouseOver.blockX, Minecraft.getMinecraft().objectMouseOver.blockY, Minecraft.getMinecraft().objectMouseOver.blockZ).getMaterial() != Material.air
		)
			setSlot
			(
				Minecraft.getMinecraft().objectMouseOver.blockX,
				Minecraft.getMinecraft().objectMouseOver.blockY,
				Minecraft.getMinecraft().objectMouseOver.blockZ
			);
	}
	
	public void onDisable()
	{
		isActive = false;
		Minecraft.getMinecraft().thePlayer.inventory.currentItem = oldSlot;
	}
}
