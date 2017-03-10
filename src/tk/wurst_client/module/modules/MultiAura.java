package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;
import tk.wurst_client.utils.EntityUtils;

public class MultiAura extends Module
{

	public MultiAura()
	{
		super
		(
			"MultiAura",
			"Faster Killaura that attacks multiple entities at once.",
			0,
			Category.COMBAT
		);
	}
	private float range = 6F;
	
	public void onEnable()
	{
		Client.Wurst.moduleManager.getModuleFromClass(Killaura.class).setToggled(false);
		Client.Wurst.moduleManager.getModuleFromClass(KillauraLegit.class).setToggled(false);
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if(Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
		{
			noCheatMessage();
			this.setToggled(false);
			Client.Wurst.chat.message("Switching to \"" + Client.Wurst.moduleManager.getModuleFromClass(Killaura.class).getName() + "\".");
			Client.Wurst.moduleManager.getModuleFromClass(Killaura.class).setToggled(true);
			return;
		}
		updateMS();
		if(EntityUtils.getClosestEntity(true) != null)
		{
			for(int i = 0; i < Math.min(EntityUtils.getCloseEntities(true, range).size(), 64); i++)
			{
				EntityLivingBase en = EntityUtils.getCloseEntities(true, range).get(i);
				Criticals.doCritical();
				EntityUtils.faceEntityPacket(en);
				Minecraft.getMinecraft().thePlayer.swingItem();
				Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, en);
			}
			updateLastMS();
		}
	}
}
