package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class FastBow extends Module
{
	public FastBow()
	{
		super
		(
			"FastBow",
			"Turns your bow into a machine gun.\n"
			+ "Tip: This works with BowAimbot.",
			0,
			Category.COMBAT
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
			&& (Minecraft.getMinecraft().thePlayer.onGround || Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
			&& Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null
			&& Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow
			&& Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed
		)
		{
			Minecraft.getMinecraft().playerController.sendUseItem
			(
				Minecraft.getMinecraft().thePlayer,
				Minecraft.getMinecraft().theWorld,
				Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem()
			);
			Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem().onItemRightClick
			(
				Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem(),
				Minecraft.getMinecraft().theWorld,
				Minecraft.getMinecraft().thePlayer
			);
			for(int i = 0; i < 20; i++)
				Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
	        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(5, 0, 0, 0, 255));
			Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing
			(
				Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem(),
				Minecraft.getMinecraft().theWorld,
				Minecraft.getMinecraft().thePlayer,
				10
			);
		}
	}
}
