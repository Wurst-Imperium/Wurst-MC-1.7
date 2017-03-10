package tk.wurst_client.module.modules;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.MovingObjectPosition;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.BlockUtils;

public class BuildRandom extends Module
{
	public BuildRandom()
	{
		super
		(
			"BuildRandom",
			"Builds random stuff around you.",
			0,
			Category.BLOCKS
		);
	}
	
	private float range = 6;
	
	public void onUpdate()
	{
		if(!this.getToggled() || Client.Wurst.moduleManager.getModuleFromClass(Freecam.class).getToggled() || Client.Wurst.moduleManager.getModuleFromClass(RemoteView.class).getToggled())
			return;
		if(Minecraft.getMinecraft().rightClickDelayTimer > 0 && !Client.Wurst.moduleManager.getModuleFromClass(FastPlace.class).getToggled())
			return;
		float xDiff = 0;
		float yDiff = 0;
		float zDiff = 0;
		float distance = range + 1;
		int randomPosX = 0;
		int randomPosY = 0;
		int randomPosZ = 0;
		Block randomBlock = null;
		boolean hasBlocks = false;
		for(int y = (int) range; y >= -range; y--)
		{
			for(int x = (int) range; x >= -range - 1; x--)
			{
				for(int z = (int) range; z >= -range; z--)
				{
					if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock((int)(x + Minecraft.getMinecraft().thePlayer.posX), (int)(y + Minecraft.getMinecraft().thePlayer.posY), (int)(z + Minecraft.getMinecraft().thePlayer.posZ))) != 0 && BlockUtils.getBlockDistance(x, y, z) <= range)
					{
						hasBlocks = true;
						break;
					}
				}
				if(hasBlocks)
					break;
			}
			if(hasBlocks)
				break;
		}
		if(!hasBlocks)
			return;
		while(distance > range || distance < -range || randomBlock == null || Block.getIdFromBlock(randomBlock) == 0)
		{
			xDiff = (int) (Math.random() * range * 2 - range - 1);
			yDiff = (int) (Math.random() * range * 2 - range);
			zDiff = (int) (Math.random() * range * 2 - range);
			distance = BlockUtils.getBlockDistance(xDiff, yDiff, zDiff);
			randomPosX = (int) (xDiff + Minecraft.getMinecraft().thePlayer.posX);
			randomPosY = (int) (yDiff + Minecraft.getMinecraft().thePlayer.posY);
			randomPosZ = (int) (zDiff + Minecraft.getMinecraft().thePlayer.posZ);
			randomBlock = Minecraft.getMinecraft().theWorld.getBlock(randomPosX, randomPosY, randomPosZ);
		}
		MovingObjectPosition fakeObjectMouseOver = Minecraft.getMinecraft().objectMouseOver;
		if(fakeObjectMouseOver == null)
			return;
		fakeObjectMouseOver.blockX = (int) randomPosX;
		fakeObjectMouseOver.blockY = (int) randomPosY;
		fakeObjectMouseOver.blockZ = (int) randomPosZ;
		BlockUtils.faceBlockPacket(randomPosX, randomPosY, randomPosZ);
		Minecraft.getMinecraft().thePlayer.swingItem();
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement
		(
			(int) randomPosX,
			(int) randomPosY,
			(int) randomPosZ,
			fakeObjectMouseOver.sideHit,
			Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem(),
			(float) fakeObjectMouseOver.hitVec.xCoord - (float) fakeObjectMouseOver.blockX,
			(float) fakeObjectMouseOver.hitVec.yCoord - (float) fakeObjectMouseOver.blockY,
			(float) fakeObjectMouseOver.hitVec.zCoord - (float) fakeObjectMouseOver.blockZ
		));
	}
}
