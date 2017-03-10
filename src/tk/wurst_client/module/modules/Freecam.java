package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import org.lwjgl.input.Keyboard;

import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Freecam extends Module
{
	public Freecam()
	{
		super
		(
			"Freecam",
			"Allows you to fly out of your body.\n"
			+ "Looks similar to spectator mode.",
			Keyboard.KEY_U,
			Category.RENDER
		);
	}
	
	private EntityOtherPlayerMP fakePlayer = null;
	private double oldX;
	private double oldY;
	private double oldZ;
	
	public void onEnable()
	{
		oldX = Minecraft.getMinecraft().thePlayer.posX;
		oldY = Minecraft.getMinecraft().thePlayer.posY;
		oldZ = Minecraft.getMinecraft().thePlayer.posZ;
		Minecraft.getMinecraft().thePlayer.noClip = true;
		EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer.getGameProfile());
		fakePlayer.copyDataFrom(Minecraft.getMinecraft().thePlayer, true);
		fakePlayer.posY -= 1.62;
		fakePlayer.rotationYawHead = Minecraft.getMinecraft().thePlayer.rotationYawHead;
		Minecraft.getMinecraft().theWorld.addEntityToWorld(-69, fakePlayer);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		Minecraft.getMinecraft().thePlayer.capabilities.isFlying = false;
		Minecraft.getMinecraft().thePlayer.motionX = 0;
		Minecraft.getMinecraft().thePlayer.motionY = 0;
		Minecraft.getMinecraft().thePlayer.motionZ = 0;
		Minecraft.getMinecraft().thePlayer.jumpMovementFactor = Flight.speed / 10;
		if(Minecraft.getMinecraft().gameSettings.keyBindJump.pressed)
		{
			Minecraft.getMinecraft().thePlayer.motionY += Flight.speed;
		}
		if(Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed)
		{
			Minecraft.getMinecraft().thePlayer.motionY -= Flight.speed;
		}
	}
	
	public void onDisable()
	{
		Minecraft.getMinecraft().thePlayer.noClip = false;
		Minecraft.getMinecraft().thePlayer.setPositionAndRotation(oldX, oldY, oldZ, Minecraft.getMinecraft().thePlayer.rotationYaw, Minecraft.getMinecraft().thePlayer.rotationPitch);
		Minecraft.getMinecraft().theWorld.removeEntityFromWorld(-69);
		fakePlayer = null;
	}
}
