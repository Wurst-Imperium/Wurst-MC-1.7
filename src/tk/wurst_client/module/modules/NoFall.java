package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class NoFall extends Module
{
	public NoFall()
	{
		super
		(
			"NoFall",
			"Protects you from fall damage.",
			0,
			Category.MOVEMENT
		);
	}
	 
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
	    if(Minecraft.getMinecraft().thePlayer.fallDistance > 2)
	    {
	    	Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
	    }
	}
} 
