package tk.wurst_client.module.modules;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.BlockUtils;

public class Kaboom extends Module
{
	public Kaboom()
	{
		super
		(
			"Kaboom",
			"Breaks blocks around you like an explosion.\n"
			+ "This can be a lot faster than Nuker if the server\n"
			+ "doesn't have NoCheat+. It works best with fast tools\n"
			+ "and weak blocks.\n"
			+ "Note that this is not an actual explosion.",
			Keyboard.KEY_GRAVE,
			Category.BLOCKS
		);
	}
	
	private int range = 6;
	public static int power = 128;
	
	public void initSliders()
	{
		this.moduleSliders.add(new BasicSlider("Kaboom power", power, 32, 512, 32, ValueDisplay.INTEGER));
	}
	
	public void updateSettings()
	{
		this.power = (int) this.moduleSliders.get(0).getValue();
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
		{
			noCheatMessage();
			this.setToggled(false);
			return;
		}
		if(Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
		{
			Client.Wurst.chat.warning("Surivival mode only.");
			this.setToggled(false);
			return;
		}
		new Thread("Kaboom")
		{
			public void run()
			{
				for(int y = range; y >= -range; y--)
				{
					new Explosion
					(
						Minecraft.getMinecraft().theWorld,
						Minecraft.getMinecraft().thePlayer,
						Minecraft.getMinecraft().thePlayer.posX,
						Minecraft.getMinecraft().thePlayer.posY,
						Minecraft.getMinecraft().thePlayer.posZ,
						6F
					).doExplosionB(true);
					for(int x = range; x >= -range - 1; x--)
					{
						for(int z = range; z >= -range; z--)
						{
							if(x == 0 && y == -2 && z == 0)
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
							fakeObjectMouseOver.blockX = posX;
							fakeObjectMouseOver.blockY = posY;
							fakeObjectMouseOver.blockZ = posZ;
							if(Block.getIdFromBlock(block) != 0 && posY >= 0 && currentDistance <= range)
							{
								if(!Minecraft.getMinecraft().thePlayer.onGround)
									continue;
								int side = fakeObjectMouseOver.sideHit;
								BlockUtils.faceBlockPacket(posX, posY, posZ);
								Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation(Minecraft.getMinecraft().thePlayer, 1));
								Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(0, posX, posY, posZ, side));
								for(int i = 0; i < power; i++)
									Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(2, posX, posY, posZ, side));
								block.onBlockDestroyedByPlayer(Minecraft.getMinecraft().theWorld, posX, posY, posZ, Minecraft.getMinecraft().theWorld.getBlockMetadata(posX, posY, posZ));
							}
						}
					}
				}
			}
		}.start();
		this.setToggled(false);
	}
}
