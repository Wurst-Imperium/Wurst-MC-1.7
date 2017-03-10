package tk.wurst_client.module.modules;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class AutoMine extends Module
{
	public AutoMine()
	{
		super
		(
			"AutoMine",
			"Automatically mines a block as soon as you look at it.",
			0,
			Category.BLOCKS
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		int posX = Minecraft.getMinecraft().objectMouseOver.blockX;
		int posY = Minecraft.getMinecraft().objectMouseOver.blockY;
		int posZ = Minecraft.getMinecraft().objectMouseOver.blockZ;
		Block mouseOverBlock = Minecraft.getMinecraft().theWorld.getBlock(posX, posY, posZ);
		if(Block.getIdFromBlock(mouseOverBlock) != 0)
			Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed = true;
		else
			Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed = false;
	}
	
	public void onDisable()
	{
		Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed = false;
	}
}
