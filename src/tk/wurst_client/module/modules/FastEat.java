package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class FastEat extends Module
{
	public FastEat()
	{
		super
		(
			"FastEat",
			"Allows you to eat food ten times faster.\n"
			+ "OM! NOM! NOM!",
			0,
			Category.MISC
		);
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
		if
		(
			Minecraft.getMinecraft().thePlayer.getHealth() > 0
			&& Minecraft.getMinecraft().thePlayer.onGround
			&& Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null
			&& Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood
			&& Minecraft.getMinecraft().thePlayer.getFoodStats().needFood()
			&& Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed
		)
		{
			for(int i = 0; i < 10; i++)
				Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
		}
	}
}
