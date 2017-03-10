package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Blink extends Module
{
	public Blink()
	{
		super
		(
			"Blink",
			"Makes it harder for other players to see where you are.\n"
			+ "They will think you are lagging badly, because your\n"
			+ "position will only be updated every 3 seconds.",
			0,
			Category.MOVEMENT
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		updateMS();
		if(hasTimePassedM(3000))
		{
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue
            (
            	new C03PacketPlayer.C06PacketPlayerPosLook
            	(
            		Minecraft.getMinecraft().thePlayer.posX,
            		Minecraft.getMinecraft().thePlayer.boundingBox.minY,
            		Minecraft.getMinecraft().thePlayer.posY,
            		Minecraft.getMinecraft().thePlayer.posZ,
            		Minecraft.getMinecraft().thePlayer.rotationYaw,
            		Minecraft.getMinecraft().thePlayer.rotationPitch,
            		Minecraft.getMinecraft().thePlayer.onGround
            	)
            );
			updateLastMS();
		}
	}
}
