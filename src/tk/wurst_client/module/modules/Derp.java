package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Derp extends Module
{
	public Derp()
	{
		super
		(
			"Derp",
			"While this is active, other people will think you are\n"
			+ "derping around.",
			0,
			Category.MISC
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw + (float) (Math.random() * 360 - 180);
		float pitch = (float) (Math.random() * 180 - 90);
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook
		(
			yaw,
			pitch,
			Minecraft.getMinecraft().thePlayer.onGround
		));
	}
}
