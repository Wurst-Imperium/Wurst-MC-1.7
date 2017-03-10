package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
import org.lwjgl.input.Keyboard;

import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.EntityUtils;

public class Killaura extends Module
{

	public Killaura()
	{
		super
		(
			"Killaura",
			"Automatically attacks everything in your range.",
			Keyboard.KEY_R,
			Category.COMBAT
		);
	}
	public static float normalSpeed = 20F;
	public static float normalRange = 5F;
	public static float yesCheatSpeed = 12F;
	public static float yesCheatRange = 4.25F;
	private float realSpeed;
	private float realRange;
	
	public void initSliders()
	{
		this.moduleSliders.add(new BasicSlider("Killaura speed", normalSpeed, 2, 20, 0.1, ValueDisplay.DECIMAL));
		this.moduleSliders.add(new BasicSlider("Killaura range", normalRange, 1, 6, 0.05, ValueDisplay.DECIMAL));
	}
	
	public void updateSettings()
	{
		this.normalSpeed = (float) this.moduleSliders.get(0).getValue();
		this.yesCheatSpeed = Math.min(normalSpeed, 12F);
		this.normalRange = (float) this.moduleSliders.get(1).getValue();
		this.yesCheatRange = Math.min(normalRange, 4.25F);
	}
	
	public void onEnable()
	{
		Client.Wurst.moduleManager.getModuleFromClass(MultiAura.class).setToggled(false);
		Client.Wurst.moduleManager.getModuleFromClass(KillauraLegit.class).setToggled(false);
	}
	
	public void onUpdate()
	{
		if(this.getToggled())
		{
			if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
			{
				realSpeed = yesCheatSpeed;
				realRange = yesCheatRange;
			}else
			{
				realSpeed = normalSpeed;
				realRange = normalRange;
			}
			updateMS();
			if(hasTimePassedS(realSpeed) && EntityUtils.getClosestEntity(true) != null)
			{
				EntityLivingBase en = EntityUtils.getClosestEntity(true);
				if(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) <= realRange)
				{
					Criticals.doCritical();
					EntityUtils.faceEntityPacket(en);
					Minecraft.getMinecraft().thePlayer.swingItem();
					Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, en);
					updateLastMS();
				}
			}
		}
	}
}
