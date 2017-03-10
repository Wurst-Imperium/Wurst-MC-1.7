package tk.wurst_client.module.modules;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MovingObjectPosition;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.BlockUtils;
import tk.wurst_client.utils.RenderUtils;

public class Nuker extends Module
{
	public Nuker()
	{
		super
		(
			"Nuker",
			"Destroys blocks around you.\n"
			+ "Use .nuker mode <mode> to change the mode.",
			Keyboard.KEY_L,
			Category.BLOCKS
		);
	}
	
	public static float normalRange = 5F;
	public static float yesCheatRange = 4.25F;
	private float realRange;
	private static Block currentBlock;
	private float currentDamage;
	private int side = 1;
	private byte blockHitDelay = 0;
	public static int id = 0;
	private int posX;
	private int posY;
	private int posZ;
	private boolean shouldRenderESP;
	private int oldSlot = -1;
	
	public String getRenderName()
	{
		if(Client.Wurst.options.nukerMode == 1)
			return "IDNuker [" + id + "]";
		else if(Client.Wurst.options.nukerMode == 2)
			return "FlatNuker";
		else
			return "Nuker";
	}
	
	public void initSliders()
	{
		this.moduleSliders.add(new BasicSlider("Nuker range", normalRange, 1, 6, 0.05, ValueDisplay.DECIMAL));
	}
	
	public void updateSettings()
	{
		this.normalRange = (float) this.moduleSliders.get(0).getValue();
		this.yesCheatRange = Math.min(normalRange, 4.25F);
	}
	
	public void onEnable()
	{
		if(Client.Wurst.moduleManager.getModuleFromClass(NukerLegit.class).getToggled())
			Client.Wurst.moduleManager.getModuleFromClass(NukerLegit.class).setToggled(false);
	}
	
	public void onLeftClick()
	{
		if(!this.getToggled() || Minecraft.getMinecraft().objectMouseOver == null)
			return;
		if(Client.Wurst.options.nukerMode == 1 && Minecraft.getMinecraft().theWorld.getBlock(Minecraft.getMinecraft().objectMouseOver.blockX, Minecraft.getMinecraft().objectMouseOver.blockY, Minecraft.getMinecraft().objectMouseOver.blockZ).getMaterial() != Material.air)
		{
			id = Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock(Minecraft.getMinecraft().objectMouseOver.blockX, Minecraft.getMinecraft().objectMouseOver.blockY, Minecraft.getMinecraft().objectMouseOver.blockZ));
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
		if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
		{
			realRange = yesCheatRange;
		}else
		{
			realRange = normalRange;
		}
		shouldRenderESP = false;
		int[] pos = find();
		if(pos == null)
		{
			if(oldSlot != -1)
			{
				Minecraft.getMinecraft().thePlayer.inventory.currentItem = oldSlot;
				oldSlot = -1;
			}
			return;
		}
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
		BlockUtils.faceBlockPacket(posX, posY, posZ);
		if (currentDamage == 0)
		{
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(0, posX, posY, posZ, side));
			if(Client.Wurst.moduleManager.getModuleFromClass(AutoTool.class).getToggled() && oldSlot == -1)
				oldSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
			if (Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode || currentBlock.getPlayerRelativeBlockHardness(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, (int) posX, (int) posY, (int) posZ) >= 1)
			{
				currentDamage = 0;
				if (Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode && !Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
				{
					nukeAll();
				}
				else
				{
					if(!Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode && Client.Wurst.moduleManager.getModuleFromClass(AutoTool.class).getToggled())
						AutoTool.setSlot(posX, posY, posZ);
					shouldRenderESP = true;
					Minecraft.getMinecraft().thePlayer.swingItem();
					Minecraft.getMinecraft().playerController.onPlayerDestroyBlock((int) posX, (int) posY, (int) posZ, side);
				}
				return;
			}
		}
		if(Client.Wurst.moduleManager.getModuleFromClass(AutoTool.class).getToggled())
			AutoTool.setSlot(posX, posY, posZ);
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation(Minecraft.getMinecraft().thePlayer, 1));
		shouldRenderESP = true;
		BlockUtils.faceBlockPacket(posX, posY, posZ);
		currentDamage += currentBlock.getPlayerRelativeBlockHardness(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, (int) posX, (int) posY, (int) posZ) * (Client.Wurst.moduleManager.getModuleFromClass(FastBreak.class).getToggled() && Client.Wurst.options.fastbreakMode == 0 ? FastBreak.speed : 1);
		Minecraft.getMinecraft().theWorld.destroyBlockInWorldPartially(Minecraft.getMinecraft().thePlayer.getEntityId(), (int) posX, (int) posY, (int) posZ, (int)(this.currentDamage * 10.0F) - 1);
		if (currentDamage >= 1)
		{
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(2, posX, posY, posZ, side));
			Minecraft.getMinecraft().playerController.onPlayerDestroyBlock((int) posX, (int) posY, (int) posZ, side);
			blockHitDelay = (byte) (4);
			currentDamage = 0;
		}else if(Client.Wurst.moduleManager.getModuleFromClass(FastBreak.class).getToggled() && Client.Wurst.options.fastbreakMode == 1)
        {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(2, posX, posY, posZ, side));
        }
	}
	
	public void onDisable()
	{
		if(oldSlot != -1)
		{
			Minecraft.getMinecraft().thePlayer.inventory.currentItem = oldSlot;
			oldSlot = -1;
		}
		currentDamage = 0;
		shouldRenderESP = false;
		id = 0;
		Client.Wurst.fileManager.saveValues();
	}
	
	public int[] find()
	{
		int[] closest = null;
		float closestDistance = realRange + 1;
		for(int y = (int) realRange; y >= (Client.Wurst.options.nukerMode == 2 ? -1 : -realRange); y--)
		{
			for(int x = (int) realRange; x >= -realRange - 1; x--)
			{
				for(int z = (int) realRange; z >= -realRange; z--)
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
					if(Block.getIdFromBlock(block) != 0 && posY >= 0 && currentDistance <= realRange && (Client.Wurst.options.nukerMode != 1 || (Client.Wurst.options.nukerMode == 1 && Block.getIdFromBlock(block) == id)))
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
	
	public void nukeAll()
	{
		for(int y = (int) realRange; y >= (Client.Wurst.options.nukerMode == 2 ? -1 : -realRange); y--)
		{
			for(int x = (int) realRange; x >= -realRange - 1; x--)
			{
				for(int z = (int) realRange; z >= -realRange; z--)
				{
					int posX = (int) (Minecraft.getMinecraft().thePlayer.posX + x);
					int posY = (int) (Minecraft.getMinecraft().thePlayer.posY + y);
					int posZ = (int) (Minecraft.getMinecraft().thePlayer.posZ + z);
					Block block = Minecraft.getMinecraft().theWorld.getBlock(posX, posY, posZ);
					float xDiff = (float)(Minecraft.getMinecraft().thePlayer.posX - posX);
					float yDiff = (float)(Minecraft.getMinecraft().thePlayer.posY - posY);
					float zDiff = (float)(Minecraft.getMinecraft().thePlayer.posZ - posZ);
					float currentDistance = BlockUtils.getBlockDistance(xDiff, yDiff, zDiff);
					MovingObjectPosition fakeObjectMouseOver = Minecraft.getMinecraft().objectMouseOver;
					fakeObjectMouseOver.blockX = posX;
					fakeObjectMouseOver.blockY = posY;
					fakeObjectMouseOver.blockZ = posZ;
					if(Block.getIdFromBlock(block) != 0 && posY >= 0 && currentDistance <= realRange && (Client.Wurst.options.nukerMode != 1 || (Client.Wurst.options.nukerMode == 1 && Block.getIdFromBlock(block) == id)))
					{
						side = fakeObjectMouseOver.sideHit;
						shouldRenderESP = true;
						BlockUtils.faceBlockPacket(posX, posY, posZ);
						Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(0, posX, posY, posZ, side));
						block.onBlockDestroyedByPlayer(Minecraft.getMinecraft().theWorld, posX, posY, posZ, Minecraft.getMinecraft().theWorld.getBlockMetadata(posX, posY, posZ));
					}
				}
			}
		}
	}
}
