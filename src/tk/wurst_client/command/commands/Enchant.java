package tk.wurst_client.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import tk.wurst_client.Client;
import tk.wurst_client.command.Command;

public class Enchant extends Command
{
	private static String[] commandHelp =
	{
		"Enchants items with everything.",
		".enchant",
		".enchant all"
	};
	
	public Enchant()
	{
		super("enchant", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(!Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
		{
			Client.Wurst.chat.error("You have to be in creative mode.");
			return;
		}
		if(args == null)
		{
			ItemStack currentItem = Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem();
			if(currentItem == null)
			{
				Client.Wurst.chat.error("There is no item in your hand.");
				return;
			}
			for(Enchantment enchantment : Enchantment.enchantmentsList)
			{
				try
				{
					if(enchantment == Enchantment.silkTouch)
						continue;
					currentItem.addEnchantment(enchantment, 127);
				}catch(Exception e)
				{
					
				}
			}
		}else if(args[0].equals("all"))
		{
			int items = 0;
			for(int i = 0; i < 40; i++)
			{
				ItemStack currentItem = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
				if(currentItem == null)
					continue;
				items++;
				for(Enchantment enchantment : Enchantment.enchantmentsList)
				{
					try
					{
						if(enchantment == Enchantment.silkTouch)
							continue;
						currentItem.addEnchantment(enchantment, 127);
					}catch(Exception e)
					{
						
					}
				}
			}
			if(items == 1)
				Client.Wurst.chat.message("Enchanted 1 item.");
			else
				Client.Wurst.chat.message("Enchanted " + items + " items.");
		}else
			commandError();
	}
}
