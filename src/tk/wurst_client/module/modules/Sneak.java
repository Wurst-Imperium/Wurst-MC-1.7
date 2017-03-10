package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C0BPacketEntityAction;

import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Sneak extends Module
{
	public Sneak()
	{
		super
		(
			"Sneak",
			"Automatically sneaks all the time.",
			Keyboard.KEY_Z,
			Category.MOVEMENT
		);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
			Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed = true;
		else
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(Minecraft.getMinecraft().thePlayer, 0));
	}
	
	public void onDisable()
	{
		Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed = false;
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(Minecraft.getMinecraft().thePlayer, 1));
	}
}
