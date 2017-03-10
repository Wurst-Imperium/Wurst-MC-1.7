package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Headless extends Module
{
	public Headless()
	{
		super
		(
			"Headless",
			"While this is active, other people will think you are\n"
			+ "headless. It looks hilarious.",
			0,
			Category.MISC
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook
		(
			Minecraft.getMinecraft().thePlayer.rotationYaw,
			180F,
			Minecraft.getMinecraft().thePlayer.onGround
		));
	}
}
