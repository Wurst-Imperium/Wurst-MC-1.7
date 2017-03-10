package tk.wurst_client.module.modules;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MovingObjectPosition;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.BlockUtils;
import tk.wurst_client.utils.RenderUtils;

public class NukerLegit extends Module
{
	public NukerLegit()
	{
		super
		(
			"NukerLegit",
			"Slower Nuker that bypasses any cheat prevention\n"
			+ "PlugIn. Not required on most NoCheat+ servers!",
			0,
			Category.BLOCKS
		);
	}
	
	private static Block currentBlock;
	private float currentDamage;
	private int side = 1;
	private byte blockHitDelay = 0;
	private int posX;
	private int posY;
	private int posZ;
	private boolean shouldRenderESP;
	
	public String getRenderName()
	{
		if(Client.Wurst.options.nukerMode == 1)
			return "IDNukerLegit [" + Nuker.id + "]";
		else if(Client.Wurst.options.nukerMode == 2)
			return "FlatNukerLegit";
		else
			return "NukerLegit";
	}
	
	public void onEnable()
	{
		if(Client.Wurst.moduleManager.getModuleFromClass(Nuker.class).getToggled())
			Client.Wurst.moduleManager.getModuleFromClass(Nuker.class).setToggled(false);
	}
	
	public void onLeftClick()
	{
		if(!this.getToggled())
			return;
		if(Client.Wurst.options.nukerMode == 1 && Minecraft.getMinecraft().theWorld.getBlock(Minecraft.getMinecraft().objectMouseOver.blockX, Minecraft.getMinecraft().objectMouseOver.blockY, Minecraft.getMinecraft().objectMouseOver.blockZ).getMaterial() != Material.air)
		{
			Nuker.id = Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock(Minecraft.getMinecraft().objectMouseOver.blockX, Minecraft.getMinecraft().objectMouseOver.blockY, Minecraft.getMinecraft().objectMouseOver.blockZ));
			Client.Wurst.fileManager.saveValues();
		}
	}
	
	public void onRender()
	{
		if (blockHitDelay == 0 && shouldRenderESP)
		{
			double renderX = posX - RenderManager.renderPosX;
			double renderY = posY - RenderManager.renderPosY;
			double renderZ = posZ - RenderManager.renderPosZ;
			if(!Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode && currentBlock.getPlayerRelativeBlockHardness(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, (int) posX, (int) posY, (int) posZ) < 1)
				RenderUtils.nukerBox(renderX, renderY, renderZ, currentDamage);
			else
				RenderUtils.nukerBox(renderX, renderY, renderZ, 1);
		}
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		shouldRenderESP = false;
		int[] pos = find();
		if(pos == null)
			return;
		if(posX != pos[0] || posY != pos[1] || posZ != pos[2])
			currentDamage = 0;
		posX = pos[0];
		posY = pos[1];
		posZ = pos[2];
		currentBlock = Minecraft.getMinecraft().theWorld.getBlock(posX, posY, posZ);
		if (blockHitDelay > 0)
		{
			blockHitDelay--;
			return;
		}
		BlockUtils.faceBlockClient(posX, posY, posZ);
		if (currentDamage == 0)
		{
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(0, posX, posY, posZ, side));
			if (Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode || currentBlock.getPlayerRelativeBlockHardness(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, (int) posX, (int) posY, (int) posZ) >= 1)
			{
				currentDamage = 0;
				shouldRenderESP = true;
				Minecraft.getMinecraft().thePlayer.swingItem();
				Minecraft.getMinecraft().playerController.onPlayerDestroyBlock((int) posX, (int) posY, (int) posZ, side);
				blockHitDelay = (byte) (4);
				return;
			}
		}
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation(Minecraft.getMinecraft().thePlayer, 1));
		shouldRenderESP = true;
		BlockUtils.faceBlockClient(posX, posY, posZ);
		currentDamage += currentBlock.getPlayerRelativeBlockHardness(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, (int) posX, (int) posY, (int) posZ);
		Minecraft.getMinecraft().theWorld.destroyBlockInWorldPartially(Minecraft.getMinecraft().thePlayer.getEntityId(), (int) posX, (int) posY, (int) posZ, (int)(this.currentDamage * 10.0F) - 1);
		if (currentDamage >= 1)
		{
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(2, posX, posY, posZ, side));
			Minecraft.getMinecraft().playerController.onPlayerDestroyBlock((int) posX, (int) posY, (int) posZ, side);
			blockHitDelay = (byte) (4);
			currentDamage = 0;
		}
	}
	
	public void onDisable()
	{
		currentDamage = 0;
		shouldRenderESP = false;
		Nuker.id = 0;
		Client.Wurst.fileManager.saveValues();
	}
	
	public int[] find()
	{
		int[] closest = null;
		float closestDistance = Nuker.yesCheatRange + 1;
		for(int y = (int) Nuker.yesCheatRange; y >= (Client.Wurst.options.nukerMode == 2 ? -1 : -Nuker.yesCheatRange); y--)
		{
			for(int x = (int) Nuker.yesCheatRange; x >= -Nuker.yesCheatRange - 1; x--)
			{
				for(int z = (int) Nuker.yesCheatRange; z >= -Nuker.yesCheatRange; z--)
				{
					if(Minecraft.getMinecraft().thePlayer == null)
						continue;
					int posX = (int) (Minecraft.getMinecraft().thePlayer.posX + x);
					int posY = (int) (Minecraft.getMinecraft().thePlayer.posY + y);
					int posZ = (int) (Minecraft.getMinecraft().thePlayer.posZ + z);
					Block block = Minecraft.getMinecraft().theWorld.getBlock(posX, posY, posZ);
					float xDiff = (float)(Minecraft.getMinecraft().thePlayer.posX - posX);
					float yDiff = (float)(Minecraft.getMinecraft().thePlayer.posY - posY);
					float zDiff = (float)(Minecraft.getMinecraft().thePlayer.posZ - posZ);
					float currentDistance = BlockUtils.getBlockDistance(xDiff, yDiff, zDiff);
					MovingObjectPosition fakeObjectMouseOver = Minecraft.getMinecraft().objectMouseOver;
					if(fakeObjectMouseOver == null)
						continue;
					fakeObjectMouseOver.blockX = posX;
					fakeObjectMouseOver.blockY = posY;
					fakeObjectMouseOver.blockZ = posZ;
					if(Block.getIdFromBlock(block) != 0 && posY >= 0 && currentDistance <= Nuker.yesCheatRange && (Client.Wurst.options.nukerMode != 1 || (Client.Wurst.options.nukerMode == 1 && Block.getIdFromBlock(block) == Nuker.id)))
					{
						int[] pos = {posX, posY, posZ};
						side = fakeObjectMouseOver.sideHit;
						if(closest == null)
						{
							closest = pos;
							closestDistance = currentDistance;
						}else if(currentDistance < closestDistance)
						{
							closest = pos;
							closestDistance = currentDistance;
						}
					}
				}
			}
		}
		return closest;
	}
}
