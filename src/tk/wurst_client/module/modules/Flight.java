package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Flight extends Module
{
	public Flight() {
		super
		(
			"Flight",
			"Makes you fly.\n"
			+ "This is one of the oldest hacks in Minecraft.",
			Keyboard.KEY_G,
			Category.MOVEMENT
		);
	}
	
	public static float speed = 1F;
	
	public void initSliders()
	{
		this.moduleSliders.add(new BasicSlider("Flight speed", speed, 0.05, 5, 0.05, ValueDisplay.DECIMAL));
	}
	
	public void updateSettings()
	{
		this.speed = (float) this.moduleSliders.get(0).getValue();
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
		{
			noCheatMessage();
			this.setToggled(false);
		}else
		{
			Minecraft.getMinecraft().thePlayer.capabilities.isFlying = false;
			Minecraft.getMinecraft().thePlayer.motionX = 0;
			Minecraft.getMinecraft().thePlayer.motionY = 0;
			Minecraft.getMinecraft().thePlayer.motionZ = 0;
			Minecraft.getMinecraft().thePlayer.jumpMovementFactor = speed;
			if(Minecraft.getMinecraft().gameSettings.keyBindJump.pressed)
			{
				Minecraft.getMinecraft().thePlayer.motionY += speed;
			}
			if(Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed)
			{
				Minecraft.getMinecraft().thePlayer.motionY -= speed;
			}
		}
	}
}
