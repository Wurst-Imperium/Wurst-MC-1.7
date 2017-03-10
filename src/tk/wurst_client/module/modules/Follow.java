package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.EntityUtils;

public class Follow extends Module
{
	private EntityLivingBase entity;
	private float range = 6F;
	
	public Follow()
	{
		super
		(
			"Follow",
			"A bot that follows the closest entity.\n"
			+ "Very annoying.",
			0,
			Category.COMBAT
		);
	}
	
	public String getRenderName()
	{
		if(entity != null)
			return "Following " + entity.getCommandSenderName();
		else
			return "Follow";
	}
	
	public void onEnable()
	{
		entity = null;
		if(EntityUtils.getClosestEntity(false) != null)
		{
			EntityLivingBase en = EntityUtils.getClosestEntity(false);
			if(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) <= range)
				entity = en;
		}
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(entity == null)
		{
			this.setToggled(false);
			return;
		}
		if(entity.isDead || Minecraft.getMinecraft().thePlayer.isDead)
		{
			entity = null;
			this.setToggled(false);
			return;
		}
		double xDist = Math.abs(Minecraft.getMinecraft().thePlayer.posX - entity.posX);
		double zDist = Math.abs(Minecraft.getMinecraft().thePlayer.posZ - entity.posZ);
		EntityUtils.faceEntityClient(entity);
		if(xDist > 1D || zDist > 1D)
		{
			Minecraft.getMinecraft().gameSettings.keyBindForward.pressed = true;
		}else
		{
			Minecraft.getMinecraft().gameSettings.keyBindForward.pressed = false;
		}
		if(Minecraft.getMinecraft().thePlayer.isCollidedHorizontally && Minecraft.getMinecraft().thePlayer.onGround)
		{
			Minecraft.getMinecraft().thePlayer.jump();
		}
		if (Minecraft.getMinecraft().thePlayer.isInWater() && Minecraft.getMinecraft().thePlayer.posY < entity.posY)
		{
			Minecraft.getMinecraft().thePlayer.motionY += 0.04;
		}
	}
	
	public void onDisable()
	{
		if(entity != null)
			Minecraft.getMinecraft().gameSettings.keyBindForward.pressed = false;
	}
}
