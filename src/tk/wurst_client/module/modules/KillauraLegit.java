package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.EntityUtils;

public class KillauraLegit extends Module
{

	public KillauraLegit()
	{
		super
		(
			"KillauraLegit",
			"Slower Killaura that bypasses any cheat prevention\n"
			+ "PlugIn. Not required on most NoCheat+ servers!",
			0,
			Category.COMBAT
		);
	}
	
	public void onEnable()
	{
		Client.Wurst.moduleManager.getModuleFromClass(MultiAura.class).setToggled(false);
		Client.Wurst.moduleManager.getModuleFromClass(Killaura.class).setToggled(false);
	}
	
	public void onUpdate()
	{
		if(this.getToggled())
		{
			updateMS();
			if(hasTimePassedS(Killaura.yesCheatSpeed) && EntityUtils.getClosestEntity(true) != null)
			{
				EntityLivingBase en = EntityUtils.getClosestEntity(true);
				if(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) <= Killaura.yesCheatRange)
				{
					if(Client.Wurst.moduleManager.getModuleFromClass(Criticals.class).getToggled() && Minecraft.getMinecraft().thePlayer.onGround)
						Minecraft.getMinecraft().thePlayer.jump();
					if(EntityUtils.getDistanceFromMouse(en) > 55)
						EntityUtils.faceEntityClient(en);
					else
					{
						EntityUtils.faceEntityClient(en);
						Minecraft.getMinecraft().thePlayer.swingItem();
						Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, en);
					}
					updateLastMS();
				}
			}
		}
	}
}
