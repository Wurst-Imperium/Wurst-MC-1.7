package tk.wurst_client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class BlockUtils
{
	public static void faceBlockClient(double posX, double posY, double posZ)
	{
        double diffX = posX + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        double diffZ = posZ + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY = posY  + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double dist = (double)MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / Math.PI));
        Minecraft.getMinecraft().thePlayer.rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw);
        Minecraft.getMinecraft().thePlayer.rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch);
	}
	
	public static void faceBlockPacket(double posX, double posY, double posZ)
	{
        double diffX = posX + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        double diffZ = posZ + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY = posY  + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double dist = (double)MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / Math.PI));
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook
        	(
        			Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw),
        			Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch),
        			Minecraft.getMinecraft().thePlayer.onGround
        	));
    }
	
	public static float getBlockDistance(float xDiff, float yDiff, float zDiff)
	{
		return MathHelper.sqrt_float((xDiff - 0.5F) * (xDiff - 0.5F) + (yDiff - 0.5F) * (yDiff - 0.5F) + (zDiff - 0.5F) *( zDiff - 0.5F));
	}
}
