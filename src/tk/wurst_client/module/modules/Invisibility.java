package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Invisibility extends Module
{

	public Invisibility() {
		super
		(
			"Invisibility",
			"Makes you invisible and invincible.\n"
			+ "If you die and respawn near a certain player while\n"
			+ "this mod is enabled, that player will be unable to see\n"
			+ "you. Only works on vanilla servers!",
			0,
			Category.COMBAT
		);
	}
	
	private boolean isInvisible;
	
	public void onUpdate()
	{
		if(this.getToggled() && Client.Wurst.moduleManager.getModuleFromClass(YesCheat.class).getToggled())
		{
			noCheatMessage();
			this.setToggled(false);
			return;
		}
		if(Minecraft.getMinecraft().thePlayer.getHealth() <= 0)
		{
			if(this.getToggled())
			{
				Minecraft.getMinecraft().thePlayer.respawnPlayer();//This line makes you completely invisible to other people!!!
				isInvisible = true;
			}else
			{
				isInvisible = false;
			}
		}
		if(isInvisible)
		{
			Minecraft.getMinecraft().thePlayer.setInvisible(true);//This is just so you know you are invisible.
			Minecraft.getMinecraft().thePlayer.addPotionEffect(new PotionEffect(Potion.invisibility.getId(), 10801220));	
		}else
		{
			Minecraft.getMinecraft().thePlayer.setInvisible(false);
			Minecraft.getMinecraft().thePlayer.removePotionEffect(Potion.invisibility.getId());
		}
	}
}
