package tk.wurst_client.utils;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import tk.wurst_client.Client;

public class EntityUtils
{
	/**
	 * Bypasses NoCheat+.
	 * @param entity
	 */
	public synchronized static void faceEntityClient(EntityLivingBase entity)
    {
    	float[] rotations = getRotationsNeeded(entity);
    	if (rotations != null)
    	{
            Minecraft.getMinecraft().thePlayer.rotationYaw = limitAngleChange(Minecraft.getMinecraft().thePlayer.prevRotationYaw, rotations[0], 55);//NoCheat+ bypass!!!
    		Minecraft.getMinecraft().thePlayer.rotationPitch = rotations[1];
    	}
    }

	/**
	 * Doesn't bypass NoCheat+.
	 * @param entity
	 */
	public synchronized static void faceEntityPacket(EntityLivingBase entity)
    {
    	float[] rotations = getRotationsNeeded(entity);
    	if (rotations != null)
    	{
            float yaw = rotations[0];
    		float pitch = rotations[1];
    		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, Minecraft.getMinecraft().thePlayer.onGround));
    	}
    }
	
	public static float[] getRotationsNeeded(Entity entity)
	{
    	if(entity == null)
    		return null;
        double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY;
        if(entity instanceof EntityLivingBase)
        {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            diffY = entityLivingBase.posY + (double)entityLivingBase.getEyeHeight() * 0.9 - (Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }else
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double dist = (double)MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / Math.PI));
         return new float[] {
        		 Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw),
        		 Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch)
         };
    
	}
	
	private final static float limitAngleChange(final float current, final float intended, final float maxChange)
	{
		float change = intended - current;

		if(change > maxChange)
		{
			change = maxChange;
		} else if(change < -maxChange)
		{
			change = -maxChange;
		}

		return current + change;
	}
	
	public static int getDistanceFromMouse(EntityLivingBase entity)
	{
		float[] neededRotations = getRotationsNeeded(entity);
		if (neededRotations != null) {
			float neededYaw = Minecraft.getMinecraft().thePlayer.rotationYaw - neededRotations[0], 
			neededPitch = Minecraft.getMinecraft().thePlayer.rotationPitch - neededRotations[1];
			float distanceFromMouse = MathHelper.sqrt_float(neededYaw * neededYaw + neededPitch * neededPitch);
			return (int) distanceFromMouse;
		}
		return -1;
	}
	
	public static boolean isCorrectEntity(Object o, boolean ignoreFriends)
	{
		boolean condition;
		if(Client.Wurst.options.targetMode == 0)
			condition = o instanceof EntityLivingBase;
		else if(Client.Wurst.options.targetMode == 1)
			condition = o instanceof EntityPlayer;
		else if(Client.Wurst.options.targetMode == 2)
			condition = o instanceof EntityLiving;
		else if(Client.Wurst.options.targetMode == 3)
			condition = o instanceof EntityAnimal;
		else if(Client.Wurst.options.targetMode == 4)
			condition = o instanceof EntityMob;
		else
			throw new IllegalArgumentException("Unknown target mode selected: " + Client.Wurst.options.targetMode);
		if(ignoreFriends && o instanceof EntityPlayer)
			for(int i = 0; i < Client.Wurst.options.friends.size(); i++)
				if(((EntityPlayer) o).getCommandSenderName().equals(Client.Wurst.options.friends.get(i)))
					condition = false;
		return condition;
	}
	
	public static EntityLivingBase getClosestEntity(boolean ignoreFriends)
	{
		EntityLivingBase closestEntity = null;
		for(Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
		{
			if(isCorrectEntity(o, ignoreFriends))
			{
				EntityLivingBase en = (EntityLivingBase)o;
				if(!(o instanceof EntityPlayerSP) && !en.isDead && en.getHealth() > 0 && Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en))
				{
					if(closestEntity == null || Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) < Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEntity))
					{
						closestEntity = en;
					}
				}
			}
		}
		return closestEntity;
	}
	
	public static ArrayList<EntityLivingBase> getCloseEntities(boolean ignoreFriends, float range)
	{
		ArrayList<EntityLivingBase> closeEntities = new ArrayList<EntityLivingBase>();
		for(Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
		{
			if(isCorrectEntity(o, ignoreFriends))
			{
				EntityLivingBase en = (EntityLivingBase)o;
				if
				(
					!(o instanceof EntityPlayerSP)
					&& !en.isDead && en.getHealth() > 0
					&& Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en)
					&& Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) <= range
				)
					closeEntities.add(en);
			}
		}
		return closeEntities;
	}
	
	public static EntityLivingBase getClosestEntityRaw(boolean ignoreFriends)
	{
		EntityLivingBase closestEntity = null;
		for(Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
		{
			if(isCorrectEntity(o, ignoreFriends))
			{
				EntityLivingBase en = (EntityLivingBase)o;
				if(!(o instanceof EntityPlayerSP) && !en.isDead && en.getHealth() > 0)
				{
					if(closestEntity == null || Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) < Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEntity))
					{
						closestEntity = en;
					}
				}
			}
		}
		return closestEntity;
	}
	
	public static EntityLivingBase getClosestEnemy(EntityLivingBase friend)
	{
		EntityLivingBase closestEnemy = null;
		for(Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
		{
			if(isCorrectEntity(o, true))
			{
				EntityLivingBase en = (EntityLivingBase)o;
				if(!(o instanceof EntityPlayerSP) && o != friend && !en.isDead && en.getHealth() <= 0 == false && Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en))
				{
					if(closestEnemy == null || Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) < Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEnemy))
					{
						closestEnemy = en;
					}
				}
			}
		}
		return closestEnemy;
	}
	
	public static EntityLivingBase searchEntityByIdRaw(UUID ID)
	{
		EntityLivingBase newEntity = null;
		for(Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
		{
			if(isCorrectEntity(o, false))
			{
				EntityLivingBase en = (EntityLivingBase)o;
				if(!(o instanceof EntityPlayerSP) && !en.isDead)
				{
					if(newEntity == null && en.getUniqueID().equals(ID))
					{
						newEntity = en;
					}
				}
			}
		}
		return newEntity;
	}
	
	public static EntityLivingBase searchEntityByName(String name)
	{
		EntityLivingBase newEntity = null;
		for(Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
		{
			if(isCorrectEntity(o, false))
			{
				EntityLivingBase en = (EntityLivingBase)o;
				if(!(o instanceof EntityPlayerSP) && !en.isDead && Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en))
				{
					if(newEntity == null && en.getCommandSenderName().equals(name))
					{
						newEntity = en;
					}
				}
			}
		}
		return newEntity;
	}
	
	public static EntityLivingBase searchEntityByNameRaw(String name)
	{
		EntityLivingBase newEntity = null;
		for(Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
		{
			if(isCorrectEntity(o, false))
			{
				EntityLivingBase en = (EntityLivingBase)o;
				if(!(o instanceof EntityPlayerSP) && !en.isDead)
				{
					if(newEntity == null && en.getCommandSenderName().equals(name))
					{
						newEntity = en;
					}
				}
			}
		}
		return newEntity;
	}
}
