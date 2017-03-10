package tk.wurst_client.command.commands;

import java.util.ArrayList;

import org.darkstorm.minecraft.gui.component.basic.BasicSlider;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Stats extends Command
{
	private static String[] commandHelp =
		{
			"Lists some facts about the Wurst client.",
			".stats"
		};
	
	public Stats()
	{
		super("stats", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		Client.Wurst.chat.message("Some facts about Wurst:");
		double wurstMods = Client.Wurst.moduleManager.activeModules.size();
		int hiddenMods = 0;
		for(Module module : Client.Wurst.moduleManager.activeModules)
			if(module.getCategory() == Category.HIDDEN || module.getCategory() == Category.WIP)
				hiddenMods++;
		Client.Wurst.chat.message("-Wurst currently has " + (int)wurstMods + " mods. (" + hiddenMods + " of them are hidden)");
		int wurstBinds = 0;
		for(int i = 0; i < Client.Wurst.moduleManager.activeModules.size(); i++)
		{
			if(Client.Wurst.moduleManager.activeModules.get(i).getBind() != 0)
				wurstBinds++;
		}
		Client.Wurst.chat.message("-" + wurstBinds + " of these mods are bound to keys in your current");
		Client.Wurst.chat.message("configuration.");
		int wurstCommands = Client.Wurst.commandManager.activeCommands.size();
		Client.Wurst.chat.message("-Wurst has " + wurstCommands + " commands.");
		ArrayList<BasicSlider> wurstSliders = new ArrayList<BasicSlider>();
		for(Module module : Client.Wurst.moduleManager.activeModules)
		{
			for(BasicSlider slider : module.getSliders())
			{
				wurstSliders.add(slider);
			}
		}
		Client.Wurst.chat.message("-" + wurstSliders.size() + " values in Wurst can be changed by moving sliders");
		Client.Wurst.chat.message("around.");
	}
}
